/**
 * 
 */
package br.ufpi.datagenerator.domain.types;

/**
 * Tipo de dados data
 * 
 * @author iure
 * 
 */
public class DateType extends Type {

	/**
	 * Formato do tipo de dados data
	 */
	private String formate;

	/**
	 * Menor valor da data
	 */
	private String minValue;

	/**
	 * maior valor da data
	 */
	private String maxValue;

	public DateType() {
		super();
	}

	public DateType(String name, Group group, String formate, String minValue,
			String maxValue) {
		super(name, group);
		this.formate = formate;
		this.minValue = minValue;
		this.maxValue = maxValue;

	}

	public String getFormate() {
		return formate;
	}

	public void setFormate(String formate) {
		this.formate = formate;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

}
