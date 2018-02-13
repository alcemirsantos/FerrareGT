/**
 * 
 */
package br.ufpi.datagenerator.util;

/**
 * altera os ' por \' evitando problemas de fim de string em sql
 * 
 * @author iure
 * 
 */
public class PrepareString {

	/**
	 * 
	 * @param value
	 * @return uma string com os ' alterados para \'
	 */
	public String attendantString(String value) {

		if (value != null && value.contains("'")) {
			value = value.replace("'", "\\'");

		}

		return value;
	}

}
