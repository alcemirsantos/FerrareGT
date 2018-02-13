package br.ufpi.datagenerator.domain;

import java.util.HashMap;
import java.util.Set;

/**
 * Representa o atributo que é um relacionamento que existe entre tabelas, no
 * caso de ser uma chava composta pode representar mais de uma coluna
 * 
 * @author iure
 * 
 */
public class Relationship {

	/**
	 * chave nome do atributo nesta tabela, valor nome do atributo na tabela
	 * referenciada
	 */
	private HashMap<String, String> name;

	private int cardinality;
	/**
	 * Se pode ser nulo
	 */
	private boolean nullable;

	/**
	 * uma referencia a tabela
	 */
	private Table foreignKey;

	/**
	 * Se é chave primaria
	 */
	private boolean primaryKey;

	public Relationship() {
		super();
	}

	public Relationship(HashMap<String, String> name, int cardinality,
			boolean nullable, Table foreignKey, boolean primaryKey) {
		super();
		this.name = name;
		this.cardinality = cardinality;
		this.nullable = nullable;
		this.foreignKey = foreignKey;
		this.primaryKey = primaryKey;
	}

	public HashMap<String, String> getName() {
		return name;
	}

	public void setName(HashMap<String, String> name) {
		this.name = name;
	}

	public Table getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(Table foreignKey) {
		this.foreignKey = foreignKey;
	}

	public int getCardinality() {
		return cardinality;
	}

	public void setCardinality(int cardinality) {
		this.cardinality = cardinality;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isNullable() {
		return nullable;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cardinality;
		result = prime * result
				+ ((foreignKey == null) ? 0 : foreignKey.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (nullable ? 1231 : 1237);
		result = prime * result + (primaryKey ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relationship other = (Relationship) obj;
		if (cardinality != other.cardinality)
			return false;
		if (foreignKey == null) {
			if (other.foreignKey != null)
				return false;
		} else if (!foreignKey.equals(other.foreignKey))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nullable != other.nullable)
			return false;
		if (primaryKey != other.primaryKey)
			return false;
		return true;
	}

	@SuppressWarnings( { "unused", "unchecked" })
	private boolean isEquals(HashMap<String, String> hashmap,
			HashMap<String, String> hashmapb) {

		Set keys = hashmap.keySet();

		Set keys2 = hashmapb.keySet();

		if (keys.equals(keys2)) {
			for (Object object : keys2) {
				if (hashmap.size() == 0)
					return true;
				if (!hashmap.get(object).equals(hashmapb.get(object))) {
					return false;
				}

			}
			return true;
		}
		return false;

	}

}
