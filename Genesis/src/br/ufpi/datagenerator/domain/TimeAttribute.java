/**
 * 
 */
package br.ufpi.datagenerator.domain;

import br.ufpi.datagenerator.domain.types.Type;

/**
 * Tipo de dados caracteres
 * 
 * @author iure
 * 
 */
public class TimeAttribute extends PrimitiveAttributes {

	public TimeAttribute() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TimeAttribute(String name, Type type, boolean unique,
			boolean primaryKey, boolean nullable) {
		super(name, type, unique, primaryKey, nullable);
		// TODO Auto-generated constructor stub
	}

}
