package br.ufpi.datagenerator.creator;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import br.ufpi.datagenerator.domain.NumericAttribute;
import br.ufpi.datagenerator.domain.PrimitiveAttributes;
import br.ufpi.datagenerator.domain.Relationship;
import br.ufpi.datagenerator.domain.Table;
import br.ufpi.datagenerator.domain.TextAttribute;
import br.ufpi.datagenerator.domain.TimeAttribute;
import br.ufpi.datagenerator.domain.types.IntegerType;
import br.ufpi.datagenerator.domain.types.RealType;
import br.ufpi.datagenerator.domain.types.Type;
import br.ufpi.datagenerator.tablescans.PersistenceDiscoverer;
import br.ufpi.datagenerator.tablescans.PersistenceReader;
import br.ufpi.datagenerator.util.Assist;
import br.ufpi.datagenerator.util.IrregularDateException;
import br.ufpi.datagenerator.util.NotKnowTypeException;
import br.ufpi.genesis.view.GenesisPanel;

/**
 * Responsavel por replicar os dados e grava-los no banco
 * 
 * @author iure
 * 
 */
public class PersistenceGenerator extends SwingWorker<Void, Void> {

	/**
	 * possui os ultimos valores gerados, utilizado para informar o ultimo valor
	 * criado de uma coluna de uma determinada tabela
	 */
	private HashMap<String, ArrayList<HashMap<String, String>>> lastGeneratedData; // antigo dataModel

	/**
	 * possui os valores iniciais do banco para ser feito o mapeamento da
	 * persistencia
	 */
	private HashMap<String, ArrayList<HashMap<String, String>>> initialBDValues; // antigo model;
	private static HashMap<String, ArrayList<HashMap<String, String>>> copyOfInitialBDValues; // antigo model;

	/**
	 * o mapeamento feito pelo persistenceDiscover com o nome das tabelas,
	 * colunas e relacionamentos
	 */
	private HashMap<String, Table> tables;

	/**
	 * os elementos que ja foram criados para uma replica��o de dados, utilizado
	 * para informar que dependencias ja foram criadas no adicionar
	 * relacionamentos, e atualizar valores em dependencias ciclicas e
	 * reflexivas
	 */
	public ActualState actualState;

	/**
	 * conex�o com o banco
	 */
	private Connection connection;
	
	private JProgressBar progressBar;

	private RequiredTables requiredTables;
	
	private int maxValue;
	
	private JTextArea taskOutput;

	/**
	 * Construtor utilizado em testes
	 * 
	 * @param connection
	 * @param tables
	 */
	public PersistenceGenerator(Connection connection,
			HashMap<String, Table> tables) {

		PersistenceReader persistenceReader = new PersistenceReader();

		this.connection = connection;

		lastGeneratedData = persistenceReader.actualState(tables, connection);

		initialBDValues = persistenceReader.actualState(tables, connection);

		this.tables = tables;

	}

	/**
	 * 
	 * @param connection
	 *            Conexão usada para referenciar ao informationSchema
	 * @param Schema
	 *            schema alvo
	 * @throws SQLException
	 * @throws NotKnowTypeException
	 */
	public PersistenceGenerator(Connection connection) throws SQLException,
			NotKnowTypeException {

		this.connection = connection;

	}

	public static HashMap<String, ArrayList<HashMap<String, String>>> getInitialBDValues() {
		return copyOfInitialBDValues;
	}
	

	@Override
	protected Void doInBackground() throws SQLException, NotKnowTypeException, IrregularDateException,
	IOException {
		
		PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
				requiredTables.getSchema(), this.connection);

		// recupera a vis�o atual do schema selecionado do BD
		tables = persistenceDiscoverer.dependenceDiscoverer();

		// recuperado a vis�o do banco de dados
		PersistenceReader persistenceReader = new PersistenceReader();

		lastGeneratedData = persistenceReader.actualState(tables, connection);

		initialBDValues = persistenceReader.actualState(tables, connection);
		copyOfInitialBDValues = initialBDValues;

		Savepoint savepoint = null;

		connection.setAutoCommit(false);

		Set<String> tableNames = tables.keySet();

		long progress = 0;

		OutputCSVCreator outputCSVCreator = new OutputCSVCreator(
				requiredTables, initialBDValues);

		// enquanto n�o possuir o numero de replica�oes necessarias
		while (progress < requiredTables.getNumberOfReplications()) {
			savepoint = connection.setSavepoint("save1");

			actualState = new ActualState();

			try {

				// replica todos os elementos encontrados no schema
				for (String tableName : tableNames) {

					if (!actualState.getTupleCreated().containsKey(tableName)) {

						dependenceGenerator(tables.get(tableName));

					}// fim if

				}// fim for

				// criar os elementos que faltam devido a dependencias ciclicas
				// ou reflexivas
				criateUpdate();

				connection.commit();
				progress++;
				setProgress((int)Math.min(progress, this.maxValue));
				outputCSVCreator.addTeplicate(actualState);

			} catch (SQLException e) {
				e.printStackTrace();
				// se algum erro ocorrer devido a valores duplicados
				// deve retornar ao estado anterior
				connection.rollback(savepoint);

				System.gc();
			}

		}// fim while

		outputCSVCreator.close();

		connection.close();
		
		return null;
	}
	
	
	/**
	 * replica todas as tabelas de um schema
	 * 
	 * @param requiredTables
	 * @param connection
	 * @throws SQLException
	 * @throws NotKnowTypeException
	 * @throws IrregularDateException
	 * @throws IOException
	 */
	public void generateData(RequiredTables requiredTables, JTextArea txtarea ,long maxValue) throws SQLException, NotKnowTypeException, IrregularDateException,
	IOException {

		this.requiredTables = requiredTables;
		this.maxValue = (int)maxValue;
		this.taskOutput = txtarea;
		//Initialize progress property.
        setProgress(0);
			
	}
	/**
	 * replica todas as tabelas de um schema
	 * 
	 * @param requiredTables
	 * @param connection
	 * @throws SQLException
	 * @throws NotKnowTypeException
	 * @throws IrregularDateException
	 * @throws IOException
	 */
	public void generateData(RequiredTables requiredTables)
			throws SQLException, NotKnowTypeException, IrregularDateException,
			IOException {

		PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
				requiredTables.getSchema(), this.connection);

		// recupera a vis�o atual do schema selecionado do BD
		tables = persistenceDiscoverer.dependenceDiscoverer();

		// recuperado a vis�o do banco de dados
		PersistenceReader persistenceReader = new PersistenceReader();

		lastGeneratedData = persistenceReader.actualState(tables, connection);

		initialBDValues = persistenceReader.actualState(tables, connection);
		copyOfInitialBDValues = initialBDValues;

		Savepoint savepoint = null;

		connection.setAutoCommit(false);

		Set<String> tableNames = tables.keySet();

		long progress = 0;

		OutputCSVCreator outputCSVCreator = new OutputCSVCreator(
				requiredTables, initialBDValues);

		// enquanto n�o possuir o numero de replica�oes necessarias
		while (progress < requiredTables.getNumberOfReplications()) {
			savepoint = connection.setSavepoint("save1");

			actualState = new ActualState();

			try {

				// replica todos os elementos encontrados no schema
				for (String tableName : tableNames) {

					if (!actualState.getTupleCreated().containsKey(tableName)) {

						dependenceGenerator(tables.get(tableName));

					}// fim if

				}// fim for

				// criar os elementos que faltam devido a dependencias ciclicas
				// ou reflexivas
				criateUpdate();

				connection.commit();
				progress++;
				setProgress((int)Math.min(progress, this.maxValue));
				outputCSVCreator.addTeplicate(actualState);

			} catch (SQLException e) {
				e.printStackTrace();
				// se algum erro ocorrer devido a valores duplicados
				// deve retornar ao estado anterior
				connection.rollback(savepoint);

				System.gc();
			}

		}// fim while

		outputCSVCreator.close();

		connection.close();
		
	}

	/**
	 * Gera recursivamente as dependencias de uma tabela. Ao chegar em uma
	 * tabela que n�o possui dependencias s�o criados todos os clones do modelo
	 * e no retorno da recurs�o os outros, tambem monta a sql de cria��o
	 * 
	 * @param table
	 * @return Collection<HashMap<String, String>> uma cole��o com a posi��o e i
	 *         nome e valor de cada chave
	 * @throws IrregularDateException
	 * @throws SQLException
	 */
	public Collection<HashMap<String, String>> dependenceGenerator(Table table)
			throws IrregularDateException, SQLException {

		String tableName = table.getName();

		// recebe a quantidade de tuplas criadas na tabela informada
		ArrayList<HashMap<String, String>> existingLines = lastGeneratedData
				.get(tableName);

		ArrayList<PrimitiveAttributes> primitiveAttributes = (ArrayList<PrimitiveAttributes>) table
				.getPrimitiveAtributes();

		ArrayList<Relationship> relationships = (ArrayList<Relationship>) table
				.getRelationship();

		Collection<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		HashMap<Integer, HashMap<HashMap<String, String>, Integer>> indexes = referenceMap(
				tableName, relationships);

		int index = 0;

		HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>> referenceKeys = null;

		for (HashMap<String, String> existingAttributes : existingLines) {

			HashMap<String, String> keys = new HashMap<String, String>();

			String[] attributeColumns = unreferencedColoumn(
					primitiveAttributes, existingAttributes, tableName, index,
					keys);

			String columnNames = attributeColumns[0];

			String columnValues = attributeColumns[1];

			// se n�o possuir referencias deve recursivamente encontrar a ultima
			// dependencia obrigatoria para cria-la
			if (referenceKeys == null) {
				referenceKeys = referencedColoumn(relationships, tableName,
						indexes, index);
			}

			// insserindo valores aleatorios ao colum names e colum values
			String[] referencesColumn = dependenceContructor(columnNames
					.length(), keys, indexes.get(index), relationships,
					referenceKeys, tableName, index);

			columnNames = columnNames + referencesColumn[0] + ")";

			columnValues = columnValues + referencesColumn[1] + ")";

			String sql = "INSERT INTO " + tableName + " " + columnNames
					+ " VALUES " + columnValues + ";";

			criateData(sql, existingAttributes);

			result.add(keys);
			index++;
		}

		return result;
	}

	/**
	 * insere uma sql de cria��o de uma tabela a transa��o
	 * 
	 * @param sql
	 * @param existingAttributes
	 * @throws SQLException
	 */
	private void criateData(String sql,
			HashMap<String, String> existingAttributes) throws SQLException {

		System.out.println(sql);

		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		preparedStatement.execute();

		preparedStatement.close();

		// insere os novos valores para n�o haver repeti�a� de inteiros

	}

	/**
	 *insere os dados pendentes sejam de dependencias ciclicas ou reflexivas
	 * 
	 * @throws SQLException
	 */
	public void criateUpdate() throws SQLException {

		ArrayList<String> sqlUpdates = new ArrayList<String>();

		HashMap<String, HashMap<Integer, HashMap<HashMap<String, String>, Integer>>> dependentValues = actualState
				.getDependentValues();

		Set<String> tableNames = dependentValues.keySet();

		// para cada nome de tabela
		for (String tableName : tableNames) {

			HashMap<Integer, HashMap<HashMap<String, String>, Integer>> tableIndexes = dependentValues
					.get(tableName);

			Set<Integer> indexes = tableIndexes.keySet();

			// recuperar a posi��o, indice
			for (Integer index : indexes) {

				HashMap<HashMap<String, String>, Integer> columnNameXIndexes = tableIndexes
						.get(index);

				Set<HashMap<String, String>> referenceNames = columnNameXIndexes
						.keySet();

				// para esse indice recuperar o nome da coluna
				for (HashMap<String, String> referencedColumNames : referenceNames) {
					String sql = "update " + tableName + " ";

					// recuperar o valor do indice que a coluna se referencia;
					sql = sql
							+ getUpdateValues(tableName, referencedColumNames,
									columnNameXIndexes
											.get(referencedColumNames), index)
							+ ";";

					sqlUpdates.add(sql);

				}

			}

		}

		for (String sql : sqlUpdates) {

			System.out.println(sql);

			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);

			preparedStatement.execute();

			preparedStatement.close();
		}

	}

	/**
	 * Gera uma sql para a atualiza��o de uma linha de pendencias
	 * 
	 * @param tableName
	 * @param columNamesXrefereencedColumName
	 * @param referencedIndexes
	 * @param index
	 * @return String - uma string com a sql de atualiza��o
	 */
	public String getUpdateValues(String tableName,
			HashMap<String, String> columNamesXrefereencedColumName,
			Integer referencedIndexes, Integer index) {

		Table table = tables.get(tableName);

		String sqlpart = " ";

		String wherePart = " WHERE ";

		for (Relationship relationship : table.getRelationship()) {
			if (relationship.getName().equals(columNamesXrefereencedColumName)) {

				String referencedTableName = relationship.getForeignKey()
						.getName();

				Set<String> columNames = columNamesXrefereencedColumName
						.keySet();

				for (String columName : columNames) {

					if (referencedIndexes != null) {
						sqlpart = sqlpart
								+ columName
								+ "="
								+ actualState.getTupleCreated().get(
										referencedTableName).get(
										referencedIndexes).get(
										columNamesXrefereencedColumName
												.get(columName)) + ",";
					} else {

						sqlpart = sqlpart + columName + "=" + "null,";
					}

				}

			}

			if (relationship.isPrimaryKey()) {

				Set<String> columNames = relationship.getName().keySet();

				for (String columName : columNames) {

					wherePart = wherePart
							+ columName
							+ "="
							+ actualState.getTupleCreated().get(tableName).get(
									index).get(columName) + " AND ";

				}

			}

		}

		sqlpart = sqlpart.substring(0, sqlpart.length() - 1);

		for (PrimitiveAttributes primitiveAtribute : table
				.getPrimitiveAtributes()) {

			if (primitiveAtribute.isPrimaryKey()) {
				wherePart = wherePart
						+ primitiveAtribute.getName()
						+ "="
						+ actualState.getTupleCreated().get(tableName).get(
								index).get(primitiveAtribute.getName())
						+ " AND ";
			}

		}

		wherePart = wherePart.substring(0, wherePart.length() - 4);

		return " SET " + sqlpart + wherePart;
	}

	/**
	 * Responsavel por indexar os valores criados
	 * 
	 * @param tableName
	 * @param index
	 * @param relationships
	 * @return HashMap<Integer, HashMap<HashMap<String, String>, Integer>> para
	 *         cada elmento da tabela possui um hashpap em que a chave � o nome
	 *         do relacionamento e o valor o indice que encontra o seu valor
	 *         correspondente para ser usado na verifica��o se um relacionamento
	 *         ja existe
	 */
	private HashMap<Integer, HashMap<HashMap<String, String>, Integer>> referenceMap(
			String tableName, ArrayList<Relationship> relationships) {

		ArrayList<HashMap<String, String>> lines = initialBDValues.get(tableName);

		HashMap<Integer, HashMap<HashMap<String, String>, Integer>> result = new HashMap<Integer, HashMap<HashMap<String, String>, Integer>>();

		for (int j = 0; j < lines.size(); j++) {

			HashMap<HashMap<String, String>, Integer> tableindex = new HashMap<HashMap<String, String>, Integer>();

			// valores da encontrados na tabela atual
			HashMap<String, String> tableValues = initialBDValues.get(tableName).get(j);

			for (Relationship relationship : relationships) {

				ArrayList<HashMap<String, String>> referencedTableValues = initialBDValues
						.get(relationship.getForeignKey().getName());

				Set<String> set = relationship.getName().keySet();

				for (int i = 0; i < referencedTableValues.size(); i++) {
					boolean isSameValue = true;

					for (String tableColumnName : set) {

						String tableValue = tableValues.get(tableColumnName);
						String referencedTableValue = referencedTableValues
								.get(i).get(
										relationship.getName().get(
												tableColumnName));

						if (tableValue == null) {
							tableindex.put(relationship.getName(), null);
							isSameValue = false;
							break;
						}

						if (!tableValue.equals(referencedTableValue)) {
							isSameValue = false;
							break;
						}

					}

					// se os valores das chaves s�o os mesmos entao ele ser�
					// adicionado ao hashpar com o valor do indice que ele ser�
					// encontrado
					if (isSameValue == true) {

						tableindex.put(relationship.getName(), i);
						break;
					}

				}

			}

			result.put(j, tableindex);

		}

		return result;
	}

	/**
	 * responsavel por criar a parte da sql com os nomes das colunas e os
	 * valores das colunas que s�o chave estrangeira
	 * 
	 * @param hasBeforeValue
	 * @param tableKeys
	 * @param indexes
	 * @param relationships
	 * @param referenceKeys
	 * @param tableName
	 * @param index
	 * @return String[] - em que o primeiro valor � uma string com o nome das
	 *         colunas e segundo os valores
	 */
	private String[] dependenceContructor(
			int hasBeforeValue,
			HashMap<String, String> tableKeys,
			HashMap<HashMap<String, String>, Integer> indexes,
			ArrayList<Relationship> relationships,
			HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>> referenceKeys,
			String tableName, int index) {

		String columnNames = "";

		String columnValues = "";

		Assist assist = new Assist();

		for (Relationship relationship : relationships) {

			if (assist.isCiclic(relationship.getForeignKey())
					&& relationship.isNullable()) {

				continue;

			}

			HashMap<String, String> keys = null;
			if (referenceKeys.size() > 0
					&& referenceKeys.get(relationship.getName()).size() > 0
					&& indexes.get(relationship.getName()) != null) {
				keys = referenceKeys.get(relationship.getName()).get(
						indexes.get(relationship.getName()));
			}

			Set<String> set = relationship.getName().keySet();

			for (String keyName : set) {

				if (hasBeforeValue > 1) {
					columnNames = columnNames + ",";

					columnValues = columnValues + ",";
				}

				String columnValue = null;

				if (keys != null) {
					columnValue = keys.get(relationship.getName().get(keyName));
				}

				actualState
						.newAttribute(tableName, index, keyName, columnValue);

				columnNames = columnNames + keyName;

				columnValues = columnValues + columnValue;

				hasBeforeValue++;

				if (relationship.isPrimaryKey()) {
					tableKeys.put(keyName, columnValue);
				}

			}

		}

		String[] result = new String[] { columnNames, columnValues };

		return result;
	}

	/**
	 * responsavel por criar a parte da sql com os nomes das colunas e os
	 * valores das colunas que n�o s�o chave estrangeira
	 * 
	 * @param primitiveAttributes
	 * @param existingAttributes
	 * @param tableName
	 * @param index
	 * @param keys
	 * @return String[] - em que o primeiro valor � uma string com o nome das
	 *         colunas e segundo os valores
	 * @throws IrregularDateException
	 */
	public String[] unreferencedColoumn(
			Collection<PrimitiveAttributes> primitiveAttributes,
			HashMap<String, String> existingAttributes, String tableName,
			int index, HashMap<String, String> keys)
			throws IrregularDateException {

		String values = "(";

		String columnNames = "(";

		for (PrimitiveAttributes primitiveAttribute : primitiveAttributes) {

			String nextValue = "";

			if (primitiveAttribute instanceof NumericAttribute) {

				// se na� for auto increment � necessario inssetir o valor
				if (!((NumericAttribute) primitiveAttribute).isAutoIncrement()
						|| primitiveAttribute.isPrimaryKey()) {

					// insere virgulas se ja existir um valor inserido
					if (columnNames.length() > 1) {
						columnNames = columnNames + ",";
					}

					columnNames = columnNames + primitiveAttribute.getName();

					Type type = primitiveAttribute.getType();

					if (type instanceof IntegerType) {

						nextValue = nextIntValue((IntegerType) type,
								(NumericAttribute) primitiveAttribute,
								existingAttributes, tableName, index)
								+ "";

					} else if (type instanceof RealType) {

						nextValue = nextRealValue(
								(NumericAttribute) primitiveAttribute,
								existingAttributes, tableName, index)
								+ "";

					}

					if ((values.length() > 1)) {
						values = values + ",";
					}

				}

			} else if (primitiveAttribute instanceof TextAttribute) {

				if (columnNames.length() > 1) {
					columnNames = columnNames + ",";
				}

				columnNames = columnNames + primitiveAttribute.getName();

				String nextStringValue = nextStringValue(
						(TextAttribute) primitiveAttribute, existingAttributes,
						tableName, index);

				// coloca aspas simples se for um valor n�o nulo
				if (nextStringValue != null)
					nextValue = "'" + nextStringValue + "'";
				else
					nextValue = null;

				if ((values.length() > 1)) {
					values = values + ",";
				}

			} else if (primitiveAttribute instanceof TimeAttribute) {

				if (columnNames.length() > 1) {
					columnNames = columnNames + ",";
				}

				columnNames = columnNames + primitiveAttribute.getName();

				String nextTimeValue = nextTimeValue(
						(TimeAttribute) primitiveAttribute, existingAttributes,
						tableName, index);

				// coloca aspas se for um valor n�o nulo
				if (nextTimeValue != null)
					nextValue = "'" + nextTimeValue + "'";
				else
					nextValue = null;

				if ((values.length() > 1)) {
					values = values + ",";
				}
			}

			values = values + nextValue;

			if (primitiveAttribute.isPrimaryKey()) {
				keys.put(primitiveAttribute.getName(), nextValue);
			}

		}// fim do for PrimitiveAttribute

		String[] result = new String[] { columnNames, values };

		return result;

	}

	/**
	 * Decide se o proximo valor deve ser replicado ou gerado um novo, no caso
	 * de ser chave ou unico
	 * 
	 * @param type
	 * @param primitiveAttribute
	 * @param existingLines
	 * @param tableName
	 * @param index
	 * @return Long - proximo valor
	 */
	private Long nextIntValue(IntegerType type,
			NumericAttribute primitiveAttribute,
			HashMap<String, String> existingLines, String tableName, int index) {

		long nextValue;

		if (primitiveAttribute.isUnique() || primitiveAttribute.isPrimaryKey()) {

			TypesCreator typesCreator = new TypesCreator();

			String columnValue = existingLines
					.get(primitiveAttribute.getName());

			long beforeValue = 0;

			// verifica se o valor precadastrado � null para replica-lo
			if (columnValue != null)
				beforeValue = Long.parseLong(columnValue);
			else
				return null;

			// certifica-se que o proximo valor a ser criado n�o ser� repetido
			for (HashMap<String, String> lines : lastGeneratedData.get(tableName)) {

				String values = lines.get(primitiveAttribute.getName());

				long otherValue = 0;

				if (values != null)
					otherValue = Long.parseLong(values);
				if (otherValue > beforeValue)
					beforeValue = otherValue;

			}

			nextValue = typesCreator.nexInteger(type, primitiveAttribute
					.isUnsigned(), beforeValue);

			existingLines.put(primitiveAttribute.getName(), nextValue + "");

		} else {

			nextValue = Long.parseLong(existingLines.get(primitiveAttribute
					.getName()));

		}

		actualState.newAttribute(tableName, index,
				primitiveAttribute.getName(), nextValue + "");

		return nextValue;

	}

	// gera o proximo valor real se esse for unico ou chave primaria
	private BigDecimal nextRealValue(NumericAttribute primitiveAttribute,
			HashMap<String, String> existingLines, String tableName, int index) {

		BigDecimal nextValue;

		if (primitiveAttribute.isUnique() || primitiveAttribute.isPrimaryKey()) {

			TypesCreator typesCreator = new TypesCreator();

			String columnValue = existingLines
					.get(primitiveAttribute.getName());

			BigDecimal beforeValue = new BigDecimal(0);

			if (columnValue != null)
				beforeValue = new BigDecimal(columnValue);
			else
				return null;

			// certifica-se que o proximo valor a ser criado n�o ser� repetido
			for (HashMap<String, String> lines : lastGeneratedData.get(tableName)) {

				String otherStringValue = primitiveAttribute.getName();

				BigDecimal otherValue = new BigDecimal(0);

				if (lines.get(otherStringValue) != null)
					otherValue = new BigDecimal(lines.get(otherStringValue));

				if (otherValue.compareTo(beforeValue) > 0)
					beforeValue = otherValue;

			}

			nextValue = typesCreator.nextReal(primitiveAttribute, beforeValue);

			existingLines.put(primitiveAttribute.getName(), nextValue + "");

		} else {

			nextValue = new BigDecimal(existingLines.get(primitiveAttribute
					.getName()));

		}

		actualState.newAttribute(tableName, index,
				primitiveAttribute.getName(), nextValue + "");

		return nextValue;

	}

	/**
	 * Decide se o proximo valor deve ser replicado ou gerado um novo, no caso
	 * de ser chave ou unico
	 * 
	 * @param primitiveAttribute
	 * @param existingLines
	 * @param tableName
	 * @param index
	 * @return -String valor string a ser cadastrado
	 */
	private String nextStringValue(TextAttribute primitiveAttribute,
			HashMap<String, String> existingLines, String tableName, int index) {

		String nextString;

		if ((primitiveAttribute.isUnique() || primitiveAttribute.isPrimaryKey())
				&& existingLines.get(primitiveAttribute.getName()) != null) {

			TypesCreator typesCreator = new TypesCreator();

			nextString = typesCreator.nextString(primitiveAttribute);

		} else {

			nextString = existingLines.get(primitiveAttribute.getName());

		}

		actualState.newAttribute(tableName, index,
				primitiveAttribute.getName(), "'" + nextString + "'");

		return nextString;

	}

	/**
	 * Decide se o proximo valor deve ser replicado ou gerado um novo, no caso
	 * de ser chave ou unico
	 * 
	 * @param primitiveAttribute
	 * @param existingLines
	 * @param tableName
	 * @param index
	 * @return String com o proximo valor Time
	 * @throws IrregularDateException
	 */
	private String nextTimeValue(TimeAttribute primitiveAttribute,
			HashMap<String, String> existingLines, String tableName, int index)
			throws IrregularDateException {

		String nextTime;

		if ((primitiveAttribute.isUnique() || primitiveAttribute.isPrimaryKey())
				&& existingLines.get(primitiveAttribute.getName()) != null) {

			TypesCreator typesCreator = new TypesCreator();

			nextTime = typesCreator.nextDate(primitiveAttribute);

			existingLines.put(primitiveAttribute.getName(), nextTime);

		} else {

			nextTime = existingLines.get(primitiveAttribute.getName());

		}

		actualState.newAttribute(tableName, index,
				primitiveAttribute.getName(), "'" + nextTime + "'");

		return nextTime;

	}

	/**
	 * 
	 * @param relationships
	 * @return HashMap<HashMap<String, String>, ArrayList<HashMap<String,
	 *         String>>> dado um nome de relacionamento ele retorna um array com
	 *         as posi�oes nomes e valores das chaves da referencia
	 * @throws IrregularDateException
	 * @throws SQLException
	 */
	private HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>> referencedColoumn(
			ArrayList<Relationship> relationships, String tableName,
			HashMap<Integer, HashMap<HashMap<String, String>, Integer>> map,
			int index) throws IrregularDateException, SQLException {

		Assist assist = new Assist();

		HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>> referencesColumn = new HashMap<HashMap<String, String>, ArrayList<HashMap<String, String>>>();

		for (Relationship relationship : relationships) {
			if (!assist.isCiclic(relationship.getForeignKey())
					|| (assist.isCiclic(relationship.getForeignKey()) && !relationship
							.isNullable())) {
				ArrayList<HashMap<String, String>> keysValues = actualState
						.getExistingValue(tableName, relationship, map, index);

				if (keysValues != null) {
					referencesColumn.put(relationship.getName(), keysValues);

					continue;
				}

				keysValues = (ArrayList<HashMap<String, String>>) dependenceGenerator(relationship
						.getForeignKey());

				referencesColumn.put(relationship.getName(), keysValues);
			} else {
				actualState.setCiclicdependences(map, index, tableName,
						relationship);
			}

		}

		return referencesColumn;
	}

	/*
     * Executed in event dispatching thread
     */
    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
        taskOutput.append("Done!\n");
    }

	
}
