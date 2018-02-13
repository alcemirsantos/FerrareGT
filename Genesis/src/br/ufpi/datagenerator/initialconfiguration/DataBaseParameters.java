package br.ufpi.datagenerator.initialconfiguration;

import java.util.Collection;

import br.ufpi.datagenerator.domain.types.Type;

/**
 * Possui todas as informaçoes referentes a um banco de dados especifico para o
 * correto funcionamento das querys
 * 
 * @author iure
 */
public class DataBaseParameters {

	private String dataBaseName;

	private String tableNamesSQL;

	private String collumnsSQL;

	private String driver;

	private String jdbcName;

	private String relationshipSQL;

	private Collection<Type> types;

	/**
	 * 
	 * @return String -Nome do banco de dados
	 */
	public String getDataBaseName() {
		return dataBaseName;
	}

	/**
	 * Nome do Banco de Dados
	 * 
	 * @param dataBaseName
	 */
	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	/**
	 * 
	 * @return String - A sql usada para procurar o nome de todas as tabelas de
	 *         um schema
	 */
	public String getTableNamesSQL() {
		return tableNamesSQL;
	}

	/**
	 * A sql usada para procurar o nome de todas as tabelas de um schema
	 * 
	 * @param tableNamesSQL
	 */
	public void setTableNamesSQL(String tableNamesSQL) {
		this.tableNamesSQL = tableNamesSQL;
	}

	/**
	 * 
	 * @return String - String responsavel por dars as informaçoes necessarias
	 *         as colunas que não são referencia
	 */
	public String getCollumnsSQL() {
		return collumnsSQL;
	}

	/**
	 * String responsavel por dars as informaçoes necessarias as colunas que não
	 * são referencia
	 * 
	 * @param collumnsSQL
	 */
	public void setCollumnsSQL(String collumnsSQL) {
		this.collumnsSQL = collumnsSQL;
	}

	/**
	 * 
	 * @return String - String responsavel por dars as informaçoes necessarias
	 *         as colunas que são referencia
	 */
	public String getRelationshipSQL() {
		return relationshipSQL;
	}

	/**
	 * String responsavel por dars as informaçoes necessarias as colunas que são
	 * referencia
	 * 
	 * @param relationshipSQL
	 */
	public void setRelationshipSQL(String relationshipSQL) {
		this.relationshipSQL = relationshipSQL;
	}

	/**
	 * 
	 * @return Collection<Type> Coleção com os tipos cadastrados para este Banco
	 *         de dados
	 */
	public Collection<Type> getTypes() {
		return types;
	}

	/**
	 * Coleção com os tipos cadastrados para este Banco de dados
	 * 
	 * @param types
	 */
	public void setTypes(Collection<Type> types) {
		this.types = types;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getJdbcName() {
		return jdbcName;
	}

	public void setJdbcName(String jdbcName) {
		this.jdbcName = jdbcName;
	}

}
