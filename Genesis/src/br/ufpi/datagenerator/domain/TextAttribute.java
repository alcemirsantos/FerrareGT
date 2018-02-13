package br.ufpi.datagenerator.domain;

import br.ufpi.datagenerator.domain.types.Type;

/**
 * Tipo de dado texto
 * 
 * @author iure
 * 
 */
public class TextAttribute extends PrimitiveAttributes {

	/**
	 * quantidade de caracteres
	 */
	private long nonDefaltLenght = 0;

	public TextAttribute(String name, Type type, boolean unique,
			boolean primaryKey, boolean nullable) {
		super(name, type, unique, primaryKey, nullable);

	}

	public TextAttribute(String name, Type type, boolean unique,
			boolean primaryKey, boolean nullable, long nonDefaltLenght) {
		super(name, type, unique, primaryKey, nullable);
		this.nonDefaltLenght = nonDefaltLenght;

	}

	public TextAttribute() {
		super();

	}

	public long getNonDefaltLenght() {
		return nonDefaltLenght;
	}

	public void setNonDefaltLenght(long nonDefaltLenght) {
		this.nonDefaltLenght = nonDefaltLenght;
	}

}
