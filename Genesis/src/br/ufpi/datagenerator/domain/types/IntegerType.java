package br.ufpi.datagenerator.domain.types;

/**
 * tipo de dados inteiro
 * 
 * @author iure
 * 
 */
public class IntegerType extends Type {

	private long maxValue;

	private long minValue;

	private long unsignedValue;

	public IntegerType() {
		super();
	}

	public IntegerType(String name, Group group, long maxValue, long minValue,
			long unsignedValue) {
		super();
		super.setName(name);
		super.setGroup(group);
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.unsignedValue = unsignedValue;
	}

	public long getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(long maxValue) {
		this.maxValue = maxValue;
	}

	public long getMinValue() {
		return minValue;
	}

	public void setMinValue(long minValue) {
		this.minValue = minValue;
	}

	public long getUnsignedValue() {
		return unsignedValue;
	}

	public void setUnsignedValue(long unsignedValue) {
		this.unsignedValue = unsignedValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (maxValue ^ (maxValue >>> 32));
		result = prime * result + (int) (minValue ^ (minValue >>> 32));
		result = prime * result
				+ (int) (unsignedValue ^ (unsignedValue >>> 32));
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
		IntegerType other = (IntegerType) obj;
		if (maxValue != other.maxValue)
			return false;
		if (minValue != other.minValue)
			return false;
		if (unsignedValue != other.unsignedValue)
			return false;
		return true;
	}

}
