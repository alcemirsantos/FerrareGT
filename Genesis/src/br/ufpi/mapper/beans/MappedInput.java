package br.ufpi.mapper.beans;

/**
 * Classe respons�vel pela representa��o dos campos de entrada de dados da tela
 * e da sua correspondente entidade do banco dados
 * 
 * @author Alcemir
 * 
 */
public class MappedInput {
	private String inputFieldName;
	private BDEntity entity;

	/**
	 * Construtor completo da classe.
	 * 
	 * @param inputFieldName
	 * @param entity
	 */
	public MappedInput(String inputFieldName, BDEntity entity) {
		super();
		this.inputFieldName = inputFieldName;
		this.entity = entity;
		
	}

	/**
	 * Retorna o nome do campo da tela relacionado a entidade do banco de dados referenciada nesta classe.
	 * @return
	 */
	public String getInputFieldName() {
		return inputFieldName;
	}

	/**
	 * Seta o nome do campo da tela a relacionar-se a entidade referenciado nesta classe.
	 * @param inputFieldName
	 */
	public void setInputFieldName(String inputFieldName) {
		this.inputFieldName = inputFieldName;
	}

	/**
	 * Retorna a entidade relacionada ao campo da tela referenciado nesta classe
	 * @return
	 */
	public BDEntity getEntity() {
		return entity;
	}

	/**
	 * Seta o nome da entidade do banco de dados a relacionar-se ao campo referenciado nesta classe
	 * @param entity
	 */
	public void setEntity(BDEntity entity) {
		this.entity = entity;
	}

}
