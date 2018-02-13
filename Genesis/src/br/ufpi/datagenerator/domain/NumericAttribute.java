package br.ufpi.datagenerator.domain;

import br.ufpi.datagenerator.domain.types.Type;

/**
 * Tipo de dado Numerico, representa tanto inteiros como tipo real
 * 
 * @author iure
 * 
 */
public class NumericAttribute extends PrimitiveAttributes {

	private double nonDefaltmaxValue;

	private double nonDefaltminValue;

	private double nonDefaltunsignedValue;

	private boolean unsigned;

	private boolean autoIncrement;

	public NumericAttribute(String name, Type type, boolean unique,
			boolean primaryKey, boolean nullable, boolean unsigned,
			boolean autoIncrement) {
		super(name, type, unique, primaryKey, nullable);
		this.unsigned = unsigned;
		this.autoIncrement = autoIncrement;

	}

	public NumericAttribute(String name, Type type, boolean unique,
			boolean primaryKey, boolean nullable, boolean unsigned,
			boolean autoIncrement, double nonDefaltmaxValue,
			double nonDefaltminValue, double nonDefaltunsignedValue) {
		super(name, type, unique, primaryKey, nullable);
		this.unsigned = unsigned;
		this.autoIncrement = autoIncrement;
		this.nonDefaltmaxValue = nonDefaltmaxValue;
		this.nonDefaltminValue = nonDefaltminValue;
		this.nonDefaltunsignedValue = nonDefaltunsignedValue;

	}

	public NumericAttribute() {
		super();

	}

	public double getNonDefaltmaxValue() {
		return nonDefaltmaxValue;
	}

	public void setNonDefaltmaxValue(double nonDefaltmaxValue) {
		this.nonDefaltmaxValue = nonDefaltmaxValue;
	}

	public double getNonDefaltminValue() {
		return nonDefaltminValue;
	}

	public void setNonDefaltminValue(double nonDefaltminValue) {
		this.nonDefaltminValue = nonDefaltminValue;
	}

	public double getNonDefaltunsignedValue() {
		return nonDefaltunsignedValue;
	}

	public void setNonDefaltunsignedValue(double nonDefaltunsignedValue) {
		this.nonDefaltunsignedValue = nonDefaltunsignedValue;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean unsigned) {
		this.autoIncrement = unsigned;
	}

	public boolean isUnsigned() {
		return unsigned;
	}

	public void setUnsigned(boolean unsigned) {
		this.unsigned = unsigned;
	}

}
