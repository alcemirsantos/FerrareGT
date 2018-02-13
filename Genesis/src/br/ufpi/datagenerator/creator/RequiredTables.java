/**
 * 
 */
package br.ufpi.datagenerator.creator;

import java.util.Collection;
import java.util.HashMap;

/**
 * Possui os nomes das tabelas e colunas que serão usados na replicação de dados
 * 
 * @author iure
 * 
 */
public class RequiredTables {

	/**
	 * Chave nome da tabela, e possui uma lista com o nome das colunas
	 */
	private HashMap<String, Collection<String>> tables;

	/**
	 * Nome do schema utilizado
	 */
	private String schema;

	/**
	 * Numero de replicas
	 */
	private long numberOfReplications;

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public HashMap<String, Collection<String>> getTables() {
		return tables;
	}

	public void setTables(HashMap<String, Collection<String>> tables) {
		this.tables = tables;
	}

	public long getNumberOfReplications() {
		return numberOfReplications;
	}

	public void setNumberOfReplications(long numberOfReplications) {
		this.numberOfReplications = numberOfReplications;
	}

}
