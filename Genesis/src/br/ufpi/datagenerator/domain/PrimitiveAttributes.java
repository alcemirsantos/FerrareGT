package br.ufpi.datagenerator.domain;

import br.ufpi.datagenerator.domain.types.Type;

/**
 * Representa as colunas que não são chaves estrangeiras
 * 
 * @author iure
 * 
 */
public class PrimitiveAttributes {

	/**
	 * Nome da coluna
	 */
	private String name;

	/**
	 * tipo da coluna
	 */
	private Type type;

	/**
	 * Se é unico
	 */
	private boolean unique;

	/**
	 * se é chave primaria
	 */
	private boolean primaryKey;

	/**
	 * Se pode ser nulo
	 */
	private boolean nullable;

	public PrimitiveAttributes() {
		super();
	}

	public PrimitiveAttributes(String name, Type type, boolean unique,
			boolean primaryKey, boolean nullable) {
		super();
		this.name = name;
		this.type = type;
		this.unique = unique;
		this.primaryKey = primaryKey;
		this.nullable = nullable;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setNullable(boolean nulable) {
		this.nullable = nulable;
	}

	public boolean isNullable() {
		return nullable;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (nullable ? 1231 : 1237);
		result = prime * result + (primaryKey ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (unique ? 1231 : 1237);
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
		PrimitiveAttributes other = (PrimitiveAttributes) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nullable != other.nullable)
			return false;
		if (primaryKey != other.primaryKey)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (unique != other.unique)
			return false;
		return true;
	}

}
