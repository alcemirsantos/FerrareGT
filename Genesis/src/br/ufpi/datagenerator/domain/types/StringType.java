package br.ufpi.datagenerator.domain.types;

/**
 * Tipo de dados texto
 * 
 * @author iure
 * 
 */
public class StringType extends Type {

	private long length;

	public StringType() {
		super();
	}

	public StringType(String name, Group group, long length) {
		super();
		super.setName(name);
		super.setGroup(group);
		this.length = length;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (length ^ (length >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StringType other = (StringType) obj;
		if (length != other.length)
			return false;
		return true;
	}

}
