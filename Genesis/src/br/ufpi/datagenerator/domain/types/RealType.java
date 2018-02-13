package br.ufpi.datagenerator.domain.types;

/**
 * tipo de dados real
 * 
 * @author iure
 * 
 */
public class RealType extends Type {

	private double maxValue;

	private double minValue;

	public RealType() {
		super();
	}

	public RealType(String name, Group group, double maxValue, double minValue) {
		super();
		super.setName(name);
		super.setGroup(group);
		this.maxValue = maxValue;
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(maxValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		RealType other = (RealType) obj;
		if (Double.doubleToLongBits(maxValue) != Double
				.doubleToLongBits(other.maxValue))
			return false;
		if (Double.doubleToLongBits(minValue) != Double
				.doubleToLongBits(other.minValue))
			return false;
		return true;
	}

}
