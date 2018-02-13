package br.ufpi.datagenerator.util;

public class NotKnowTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotKnowTypeException(String typeName) {
		super("Não foi encontrado o tipo " + typeName);
	}

}
