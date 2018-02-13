package br.ufpi.datagenerator.tablescans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import br.ufpi.datagenerator.domain.PrimitiveAttributes;
import br.ufpi.datagenerator.domain.Relationship;
import br.ufpi.datagenerator.domain.Table;
import br.ufpi.datagenerator.util.PrepareString;

/**
 * responsavel por ler os dados do schema
 * 
 * @author iure
 * 
 */
public class PersistenceReader {

	final private String searchSQL = "SELECT * FROM ";

	/**
	 * retorna o estado inicial do banco de dados, os valores de todas as tuplas
	 * ja cadastradas
	 * 
	 * @param tables
	 * @param connection
	 * @return HashMap<String, ArrayList<HashMap<String, String>>> onde a chave
	 *         é o nome da tabela, e tem como resultado uma lista ordenada na
	 *         forma que cada coluna é encontrada no banco de dados, a chave do
	 *         hasmap interno é o nome da coluna e o resultado é o seu valor
	 */
	public HashMap<String, ArrayList<HashMap<String, String>>> actualState(
			HashMap<String, Table> tables, Connection connection) {

		HashMap<String, ArrayList<HashMap<String, String>>> tableValues = new HashMap<String, ArrayList<HashMap<String, String>>>();

		Iterator<Table> iter = tables.values().iterator();

		while (iter.hasNext()) {

			Table table = (Table) iter.next();

			try {
				PreparedStatement preparedStatement = connection
						.prepareStatement(searchSQL + table.getName() + ";");

				ResultSet lineResultSet = preparedStatement.executeQuery();

				ArrayList<HashMap<String, String>> lines = new ArrayList<HashMap<String, String>>();

				// enquanto houver linhas pesquisar todos os atributos de uma
				// dada tabela
				while (lineResultSet.next()) {

					HashMap<String, String> attributesValues = new HashMap<String, String>();

					// recupera todos valores atributos
					for (PrimitiveAttributes primitiveAttribute : table
							.getPrimitiveAtributes()) {

						String attributeName = primitiveAttribute.getName();

						String value = lineResultSet.getString(attributeName);

						PrepareString prepareString = new PrepareString();

						value = prepareString.attendantString(value);

						attributesValues.put(attributeName, value);
					}

					// recupera todos os valores chave
					for (Relationship relationship : table.getRelationship()) {

						HashMap<String, String> names = relationship.getName();

						Set<String> columnNames = names.keySet();

						for (String string : columnNames) {

							String value = lineResultSet.getString(string);

							PrepareString prepareString = new PrepareString();

							value = prepareString.attendantString(value);

							attributesValues.put(string, value);
						}

					}

					lines.add(attributesValues);
				}
				tableValues.put(table.getName(), lines);

				preparedStatement.close();

			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return tableValues;

	}
}
