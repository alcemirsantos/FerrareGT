package br.ufpi.datagenerator.creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import br.ufpi.datagenerator.domain.Relationship;

/**
 * Possui informaçoes do que já foi criado em iteraçoes anteriores, utilizado
 * para atualizar os indices das replicas
 * 
 * @author iure
 * 
 */
public class ActualState {

	/**
	 * é a tupla criada onde a primeira chave é o nome da tabela, a segunda o
	 * indice na qual pode ser encontrada, e o terceiro o nome do atributo no
	 * qual pode ser encontrado o valor da tupla que foi criada
	 */
	private HashMap<String, HashMap<Integer, HashMap<String, String>>> tupleCreated;

	/**
	 * tupla que deve ser criada posteriormente seja por dependencia ciclica ou
	 * reflexiva
	 */
	private HashMap<String, HashMap<Integer, HashMap<HashMap<String, String>, Integer>>> dependentValues;

	public ActualState() {
		super();

		tupleCreated = new HashMap<String, HashMap<Integer, HashMap<String, String>>>();
		dependentValues = new HashMap<String, HashMap<Integer, HashMap<HashMap<String, String>, Integer>>>();

	}

	/**
	 * Adiciona uma nova coluna já criada
	 * 
	 * @param tableName
	 * @param index
	 * @param columnName
	 * @param value
	 */
	public void newAttribute(String tableName, int index, String columnName,
			String value) {

		if (tupleCreated.containsKey(tableName)) {

			if (tupleCreated.get(tableName).containsKey(index)) {

				tupleCreated.get(tableName).get(index).put(columnName, value);

			} else {

				HashMap<String, String> values = new HashMap<String, String>();

				values.put(columnName, value);

				tupleCreated.get(tableName).put(index, values);

			}

		} else {

			HashMap<Integer, HashMap<String, String>> tuple = new HashMap<Integer, HashMap<String, String>>();

			HashMap<String, String> values = new HashMap<String, String>();

			values.put(columnName, value);

			tuple.put(index, values);

			tupleCreated.put(tableName, tuple);

		}// se não contem

	}

	/**
	 * adiciona valores a serem atualizados
	 * 
	 * @param map
	 * @param tableInde
	 * @param tableName
	 * @param relationship
	 */
	public void setCiclicdependences(
			HashMap<Integer, HashMap<HashMap<String, String>, Integer>> map,
			Integer tableInde, String tableName, Relationship relationship) {

		Set<Integer> index = map.keySet();

		for (Integer tableIndex : index) {

			if (dependentValues.containsKey(tableName)) {
				if (dependentValues.get(tableName).containsKey(tableIndex)) {

					if (!dependentValues.get(tableName).get(tableIndex)
							.containsKey(relationship.getName())) {
						// adiciona o valor da referencia resultado
						dependentValues.get(tableName).get(tableIndex)
								.put(
										relationship.getName(),
										map.get(tableIndex).get(
												relationship.getName()));
					}

				} else {

					// se não contem o indece, então um ainda não existe nenhuma
					// chave que tenha esse indice para esta tabela que deve ser
					// adicionado
					HashMap<HashMap<String, String>, Integer> columnReference = new HashMap<HashMap<String, String>, Integer>();
					columnReference.put(relationship.getName(), map.get(
							tableIndex).get(relationship.getName()));

					dependentValues.get(tableName).put(tableIndex,
							columnReference);

				}// conter table index

			} else {

				HashMap<Integer, HashMap<HashMap<String, String>, Integer>> indexValue = new HashMap<Integer, HashMap<HashMap<String, String>, Integer>>();

				HashMap<HashMap<String, String>, Integer> columnReference = new HashMap<HashMap<String, String>, Integer>();
				columnReference.put(relationship.getName(), map.get(tableIndex)
						.get(relationship.getName()));

				indexValue.put(tableIndex, columnReference);

				dependentValues.put(tableName, indexValue);

			}// contem table name

		}

	}

	/**
	 * procura os relacionamentos ja cadastrados, para não replicar a criação
	 * 
	 * @param tableName
	 * @param relationship
	 * @param map
	 * @param tableIndex
	 * @return
	 */
	public ArrayList<HashMap<String, String>> getExistingValue(
			String tableName, Relationship relationship,
			HashMap<Integer, HashMap<HashMap<String, String>, Integer>> map,
			int tableIndex) {

		// TODO retornar a o array organizado com os elemnetos organizados

		ArrayList<HashMap<String, String>> arrayResult = null;

		if (tupleCreated.containsKey(relationship.getForeignKey().getName())) {

			Set<Integer> element = map.keySet();

			arrayResult = new ArrayList<HashMap<String, String>>();

			for (Integer index : element) {
				if (tupleCreated
						.get(relationship.getForeignKey().getName())
						.containsKey(map.get(index).get(relationship.getName()))) {

					HashMap<String, String> columnValue = new HashMap<String, String>();

					Integer referencedTableIndex = map.get(index).get(
							relationship.getName());

					HashMap<String, String> tablevalues = tupleCreated.get(
							relationship.getForeignKey().getName()).get(
							referencedTableIndex);

					Set<String> columnNames = relationship.getName().keySet();

					for (String columnName : columnNames) {
						String referencedColumnName = relationship.getName()
								.get(columnName);
						if (tupleCreated.get(
								relationship.getForeignKey().getName()).get(
								map.get(index).get(relationship.getName()))
								.containsKey(referencedColumnName)) {

							String referencedTableValue = tablevalues
									.get(referencedColumnName);

							columnValue.put(referencedColumnName,
									referencedTableValue);

						} else {
							return null;
						}

					}

					for (int i = arrayResult.size(); i < referencedTableIndex; i++) {
						arrayResult.add(null);
					}
					arrayResult.add(referencedTableIndex, columnValue);

				} else if (map.get(index).containsKey(relationship.getName())
						&& (map.get(index).get(relationship.getName()) == null)) {
					// se referenciar um elemento nulo nada fazer
					continue;

				} else {
					return null;
				}

			}
		} else {
			return null;
		}

		return arrayResult;

	}

	public HashMap<String, HashMap<Integer, HashMap<String, String>>> getTupleCreated() {
		return tupleCreated;
	}

	public void setTupleCreated(
			HashMap<String, HashMap<Integer, HashMap<String, String>>> tupleCreated) {
		this.tupleCreated = tupleCreated;
	}

	public HashMap<String, HashMap<Integer, HashMap<HashMap<String, String>, Integer>>> getDependentValues() {
		return dependentValues;
	}

	public void setDependentValues(
			HashMap<String, HashMap<Integer, HashMap<HashMap<String, String>, Integer>>> dependentValues) {
		this.dependentValues = dependentValues;
	}

}
