package br.ufpi.datagenerator.tablescans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import br.ufpi.datagenerator.domain.NumericAttribute;
import br.ufpi.datagenerator.domain.PrimitiveAttributes;
import br.ufpi.datagenerator.domain.Relationship;
import br.ufpi.datagenerator.domain.Table;
import br.ufpi.datagenerator.domain.TextAttribute;
import br.ufpi.datagenerator.domain.TimeAttribute;
import br.ufpi.datagenerator.domain.types.RealType;
import br.ufpi.datagenerator.domain.types.Type;
import br.ufpi.datagenerator.initialconfiguration.InitialValues;
import br.ufpi.datagenerator.util.NotKnowTypeException;

/**
 * Uma entidade que realiza pesquisas no schema procurando nomes de tabelas,
 * relacionamentos e colunas
 * 
 * @author iure
 * 
 */
public class PersistenceDiscoverer {

	private String schema;

	private Connection connection;

	private HashMap<String, Table> addTables = new HashMap<String, Table>();

	public PersistenceDiscoverer(String schema, Connection connection)
			throws SQLException {

		this.schema = schema;

		this.connection = connection;

	}

	/**
	 * Recupera o nome de todas as tabelas do esquema definido no construtor
	 * 
	 * @author Iure
	 * @return ArrayList<String> - Nomes das tabelas
	 * @throws SQLException
	 */
	public ArrayList<String> tableNamesDiscoverer() throws SQLException {

		// retorna table_name que é o nome de cada uma das tabelas existentes no
		// schema
		final String tableNamesSQL = InitialValues.dataBaseParameters
				.getTableNamesSQL();

		Research research = new Research(tableNamesSQL, connection, schema);

		ResultSet tableNamesResultSet = research.executeQuery();

		ArrayList<String> allTableNames = new ArrayList<String>();

		while (tableNamesResultSet.next()) {
			String possibleTableName = tableNamesResultSet
					.getString("table_name");

			allTableNames.add(possibleTableName);

		}

		tableNamesResultSet.close();
		research.closeStatment();

		return allTableNames;
	}

	/**
	 * Retorna todos os relacionamentos entre tabelas e atributos de um schema
	 * definido no construtor da classe
	 * 
	 * @author Iure
	 * @return HashMap<String, Table> - Onde a chave é o nome da tabela
	 * @throws SQLException
	 * @throws NotKnowTypeException
	 */
	public HashMap<String, Table> dependenceDiscoverer() throws SQLException,
			NotKnowTypeException {

		ArrayList<String> allTableNames = tableNamesDiscoverer();

		HashMap<String, Table> tables = new HashMap<String, Table>();

		Table table;

		// percorrer todas as tabelas
		for (String tableName : allTableNames) {
			// verifica se a tabela ja havia sido adicionada devido a adição na
			// função que procura
			// referencias
			if (addTables.containsKey(tableName)) {

				table = addTables.get(tableName);

			} else {

				table = new Table();

				table.setName(tableName);
			}

			// adiciona a tabela ao map de retorno

			addTables.put(tableName, table);
			tables.put(tableName, table);

			// adiciona os relacionamentos a tabela
			table.setRelationship(relationshipFinder(tableName));

			// adiciona os atributos a tabela
			table.setPrimitiveAtributes(atributesFinder(tableName));

		}

		return tables;
	}

	/**
	 * Retorna uma coleção com todos os atributos detalhados que não são
	 * referencias a outras tabelas
	 * 
	 * @author Iure
	 * @param tableName
	 * @return Collection<PrimitiveAttributes> -Atributos de uma taabela
	 * @throws SQLException
	 * @throws NotKnowTypeException
	 */
	public Collection<PrimitiveAttributes> atributesFinder(String tableName)
			throws SQLException, NotKnowTypeException {
		// Procura os atributos (que não sejam referencias)
		// COLUMN_NAME,DATA_TYPE,IS_PRIMARY_KEY,IS_UNIQUE,IS_NULLABLE,IS_AUTO_INCREMENT
		// da programa passa por parametro o nome do schema e o nome da tabela
		final String collumnsSQL = InitialValues.dataBaseParameters
				.getCollumnsSQL();

		Research collumnsResearch = new Research(collumnsSQL, connection,
				schema, tableName);

		ResultSet tableCollumnsSet = collumnsResearch.executeQuery();

		Collection<PrimitiveAttributes> primitiveAtributes = new ArrayList<PrimitiveAttributes>();

		while (tableCollumnsSet.next()) {

			PrimitiveAttributes primitiveAttribute = null;

			String columnName = tableCollumnsSet.getString("COLUMN_NAME");

			String typeName = tableCollumnsSet.getString("DATA_TYPE");

			// se possuir espaco no nome
			if (typeName.contains(" "))
				typeName = typeName.trim().substring(0, typeName.indexOf(" "));

			// verifica se o tipo obtido da consulta existe mapeado
			if (InitialValues.types.containsKey(typeName.toUpperCase())) {

				Type type = InitialValues.types.get(typeName.toUpperCase());

				// seta o tipo encontrado ao atributo

				switch (type.getGroup()) {
				case NUMERIC:

					primitiveAttribute = new NumericAttribute();

					if (tableCollumnsSet.getString("IS_AUTO_INCREMENT")
							.equalsIgnoreCase("YES")) {
						((NumericAttribute) primitiveAttribute)
								.setAutoIncrement(true);
					}

					if (tableCollumnsSet.getString("IS_UNSIGNED")
							.equalsIgnoreCase("YES")) {
						((NumericAttribute) primitiveAttribute)
								.setUnsigned(true);
					}

					if (type instanceof RealType) {

						int precision = 0;
						if (tableCollumnsSet.getString("NUMERIC_PRECISION") != null)
							precision = tableCollumnsSet
									.getInt("NUMERIC_PRECISION");

						int scale = 0;
						if (tableCollumnsSet.getString("NUMERIC_SCALE") != null)
							scale = tableCollumnsSet.getInt("NUMERIC_SCALE");

						double absValue = maxValue(precision, scale);

						((NumericAttribute) primitiveAttribute)
								.setNonDefaltmaxValue(absValue);
						((NumericAttribute) primitiveAttribute)
								.setNonDefaltminValue(-absValue);
						((NumericAttribute) primitiveAttribute)
								.setNonDefaltunsignedValue(2 * absValue);
					}
					break;
				case STRING:
					primitiveAttribute = new TextAttribute();

					long nonDefaltlenght = 0;
					if (tableCollumnsSet.getString("TEXT_LENGHT") != null)
						nonDefaltlenght = tableCollumnsSet
								.getLong("TEXT_LENGHT");

					((TextAttribute) primitiveAttribute)
							.setNonDefaltLenght(nonDefaltlenght);

					break;
				case DATE_TIME:
					primitiveAttribute = new TimeAttribute();
					break;
				default:
					break;
				}

			} else {
				// lança a exceção caso não exista
				throw new NotKnowTypeException(typeName);
			}

			primitiveAttribute.setName(columnName);

			primitiveAttribute.setType(InitialValues.types.get(typeName
					.toUpperCase()));
			// verifica o tipo de atributo
			if (tableCollumnsSet.getString("IS_PRIMARY_KEY").equalsIgnoreCase(
					"YES")) {
				primitiveAttribute.setPrimaryKey(true);
			}

			if (tableCollumnsSet.getString("IS_UNIQUE").equalsIgnoreCase("YES")) {
				primitiveAttribute.setUnique(true);
			}

			if (tableCollumnsSet.getString("IS_NULLABLE").equalsIgnoreCase(
					"YES")) {
				primitiveAttribute.setNullable(true);
			}

			primitiveAtributes.add(primitiveAttribute);

		}// termina while que percorre as colunas de uma tabela
		tableCollumnsSet.close();
		collumnsResearch.closeStatment();

		return primitiveAtributes;

	}

	// TODO verificar o possivel problema de chaves compostas
	/**
	 * Procura e adiciona os relacionamentos de uma tabela especifica pelo nome
	 * do sua chave estrangeira
	 * 
	 * @author Iure
	 * @param tableName
	 * @return Collection<Relationship> - a coleção com os relacionamentos de
	 *         uma tabela informada
	 * @throws SQLException
	 */
	public Collection<Relationship> relationshipFinder(String tableName)
			throws SQLException {
		// recebe o nome do schema e o nome da tabela e procura as dependencias
		// diretas nas quais a tabela tenha uma
		// chave estrangeira referênciada retorna os atributos
		// IS_NULLABLE,COLUMN_NAME,REFERENCED_TABLE_NAME,IS_PRIMARY_KEY
		final String relationshipSQL = InitialValues.dataBaseParameters
				.getRelationshipSQL();

		Research relationshipResearch = new Research(relationshipSQL,
				connection, schema, tableName);

		ResultSet relationShipResultSet = relationshipResearch.executeQuery();

		Collection<Relationship> relationships = new ArrayList<Relationship>();

		while (relationShipResultSet.next()) {

			String referenceTableName = relationShipResultSet
					.getString("REFERENCED_TABLE_NAME");

			String referenceColumnName = relationShipResultSet
					.getString("REFERENCED_COLUMN_NAME");

			String columnName = relationShipResultSet.getString("COLUMN_NAME");

			Relationship existingRelationship = hasComposedKey(relationships,
					referenceTableName);

			// se não existe um reacionamento ja cadastrado com essa tabela e as
			// colunãos não são chaves compostaas
			// fazer um novo
			if (existingRelationship == null
					|| (!existingRelationship.isPrimaryKey() || !relationShipResultSet
							.getString("IS_PRIMARY_KEY")
							.equalsIgnoreCase("YES"))) {
				Relationship relationship = new Relationship();
				// verifica se o relacionamento pode ser nulo
				if (relationShipResultSet.getString("IS_NULLABLE")
						.equalsIgnoreCase("YES")) {
					relationship.setNullable(true);
				}

				if (relationShipResultSet.getString("IS_PRIMARY_KEY")
						.equalsIgnoreCase("YES")) {
					relationship.setPrimaryKey(true);
				}

				HashMap<String, String> name = new HashMap<String, String>();

				name.put(columnName, referenceColumnName);

				relationship.setName(name);

				// verificar se a tabela ja foi adicionada se não deve criar uma
				// nova tabela
				if (addTables.containsKey(referenceTableName)) {
					relationship.setForeignKey(addTables
							.get(referenceTableName));
				} else {

					Table table2 = new Table();
					relationship.setForeignKey(table2);
					table2.setName(referenceTableName);
					addTables.put(referenceTableName, table2);

				}

				relationships.add(relationship);
			} else {// se existe um relacionamento deve cadastrar novamente

				existingRelationship.getName().put(columnName,
						referenceColumnName);

			}
		}// fecha a procura por referencia
		relationShipResultSet.close();
		relationshipResearch.closeStatment();

		return relationships;
	}

	/**
	 * Verifica se existe outro atributo que faz parte da chave composta
	 * 
	 * @param relationships
	 * @param referencedTableName
	 * @return
	 */
	private Relationship hasComposedKey(Collection<Relationship> relationships,
			String referencedTableName) {

		for (Relationship relationship : relationships) {
			if (relationship.getForeignKey().getName().equals(
					referencedTableName))
				return relationship;
		}

		return null;
	}

	private double maxValue(int precision, int scale) {

		if (precision == 0)
			return 0;

		double absValue = Math.pow(10, precision - (scale)) - 1;

		return absValue;

	}
}
