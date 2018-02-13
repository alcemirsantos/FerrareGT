package br.ufpi.mapper.beans;

/**
 * Esta classe representa uma entidade de banco de dados.
 * @author Alcemir
 *
 */
public class BDEntity {
	private String tableSchema;
	private String tableName;
	private String columnName;
	
	/**
	 * Construtor completo da classe.
	 * @param tblSchema
	 * @param tblName
	 * @param colName
	 */
	public BDEntity(String tblSchema, String tblName, String colName) {
		this.tableSchema = tblSchema;
		this.tableName = tblName;
		this.columnName =  colName;		
	}
	
	/**
	 * Retorna o nome da Tabela
	 * @return
	 */
	public String getTableName() {
		return tableName;
	}
	
	/**
	 * Seta o nome da tabela.
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * Retorna o nome da coluna
	 * @return
	 */
	public String getColumnName() {
		return columnName;
	}
	
	/**
	 * Seta o nome da coluna
	 * @param columnName
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	/**
	 * Retorna o nome do esquema de tabelas do banco de dados.
	 * @return
	 */
	public String getTableSchema() {
		return tableSchema;
	}
	
	/**
	 * Seta o nome do esquema de tabelas do banco de dados.
	 * @param tableSchema
	 */
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	
	

}
