package br.ufpi.mapper.control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


import functionalTestRepresentation.Field;
import functionalTestRepresentation.TestCase;
import functionalTestRepresentation.TestProcedure;
import functionalTestRepresentation.TestBattery;

import br.ufpi.mapper.beans.BDEntity;
import br.ufpi.mapper.beans.MappedInput;
import br.ufpi.mapper.conexaobd.Conexao;

/**
 * Classe respons�vel pelo controle da opera��es sobre a conexao e o mapeamento propriamente dito.
 * @author Alcemir
 *
 */
public abstract class MapperController {
	private static ArrayList<MappedInput> mappedInputs = new ArrayList<MappedInput>();
	private static HashMap<Integer, BDEntity> entities = new HashMap<Integer, BDEntity>();
	private static ArrayList<Field> fields = new ArrayList<Field>();
	private static ArrayList<String> paths = new ArrayList<String>();
	private static String table_schema;
	
	public static ArrayList<String> getPaths() {
		return paths;
	}

	public static void setPaths(ArrayList<String> pathss) {
		MapperController.paths = pathss;
	}

	/**
	 * Retorna as entradas mapeadas
	 * @return
	 */
	public static ArrayList<MappedInput> getMapedInputs() {
		return mappedInputs;
	}

	/**
	 * Seta um conjunto de entradas mapeadas.
	 * @param mapedInputs
	 */
	public static void setMapedInputs(ArrayList<MappedInput> mapedInputs) {
		MapperController.mappedInputs = mapedInputs;
	}

	/**
	 * Seta um esquema de  banco de dados para realizar a busca de tabelas e colunas.  
	 * @param bd
	 */
	public static void setBDSchematoConnect(String bd){
		MapperController.setTable_schema(bd);
	}

	/**
	 * Seta usu�rio do banco de dados a ser utilizado.
	 * @param user
	 */
	public static void setUserOfBD(String user) {
		Conexao.setBd_user(user);		
	}

	/**
	 * Seta o password do usu�rio do banco de dados 
	 * @param password
	 */
	public static void setBDUserPassoword(String password) {
		Conexao.setBd_user_password( password );		
	}
	
	/**
	 * M�todo respons�vel pelo mapeamento propriamente dito.
	 * @param dataOfMapTable
	 * @param qtde 
	 */
	public static void doMappingEntityInputField(String[][] dataOfMapTable){
		
		System.out.println("\n*********************\nMapeamento Obtido:\n*********************\n");
		for (int i = 0; i < dataOfMapTable.length; i++) {			
			mappedInputs.add(new MappedInput( dataOfMapTable[i][0], new BDEntity(MapperController.getTable_schema(), dataOfMapTable[i][1],dataOfMapTable[i][2])));
			
			System.out.println("Mapped Input #"+(i)+": CampoTela = "+ mappedInputs.get(i).getInputFieldName()+", Entidade = "+mappedInputs.get(i).getEntity().getTableName()+", Coluna = "+mappedInputs.get(i).getEntity().getColumnName());
		
		}	
	}
	
	/**
	 * Retorna o nome do "Esquema de tabelas"
	 * @return table_schema
	 */
	public static String getTable_schema() {
		return table_schema;
	}

	/**
	 * Seta o nome do "Esquema de tabelas"
	 * @param tableSchema
	 */
	public static void setTable_schema(String tableSchema) {
		table_schema = tableSchema;
	}

	/**
	 * Retorna campos obtidos do caso de teste.
	 *  
	 * @return fields
	 */
	public static ArrayList<Field> getFields() {
		return fields;
	}

	/**
	 * Seta o conjunto de campos 	
	 * @param fields
	 */
	public static void setFields(ArrayList<Field> fields) {
		MapperController.fields = fields;
	}

	/**
	 * Adiciona novo campo a lista j� existente.
	 * @param newField
	 */
	public static void putField(Field newField) {
		fields.add(newField);
	}

	/**
	 * Retorna conjunto de entidades retornadas do banco de dados previamente setado.
	 * @return entities
	 */
	public static HashMap<Integer, BDEntity> getEntities() {
		return entities;
	}

	/**
	 * Seta um conjunto de entidades relacionadas a um banco de dados.
	 * @param columns
	 */
	public static void setEntities(HashMap<Integer, BDEntity> columns) {
		entities = columns;
	}

	/**
	 * Adiciona nova entidade a lista j� existente.
	 * @param newEntity
	 */
	public static void putEntity(BDEntity newEntity) {
		entities.put(entities.size(), newEntity);
	}

	/**
	 * 
	 * Busca todas as colunas da tabela INFORMATION_SCHEMA
	 */
	public static void lookForColumns() {

		Connection con = Conexao.getConnection();// pega conexao e conecta.

		try {
			Statement stm = con.createStatement();// instancia um statment

			// "SELECT COUNT( * ) AS `Registros` , `COLUMN_NAME` FROM `COLUMNS` GROUP BY `COLUMN_NAME` ORDER BY `COLUMN_NAME`"
			ResultSet rs = stm
					.executeQuery(" SELECT `TABLE_SCHEMA`, `TABLE_NAME`, `COLUMN_NAME` FROM `COLUMNS` WHERE TABLE_SCHEMA = \""
							+ table_schema + "\" ");

			while (rs.next()) { // executa at� n�o ter mais nenhum resultado a
				// ser mostrado

				// insere nova ENTITY no conjunto de ENTITIES
				putEntity(new BDEntity(rs.getString("table_schema"), rs
						.getString("table_name"), rs.getString("column_name")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	/**
//	 * 
//	 * Busca por todos os campos de entrada de dados da Tela.
//	 */
//	public static void lookForInputs(String path, String fileName) {
//	
//		BaseExtractor extractor = new SeleniumExtractor();
//		TestBattery battery = extractor.extract(path + fileName);
//
//		for (TestCase tc : battery.getTestCases()) {
//			TestProcedure tp = tc.getTestProcedure();
//			System.out.println("URL: " + tp.getURL() + tp.getPath());
//
//			for (Field newField : tp.getFields()) {
//				putField(newField);
//				System.out.println(newField.getName());
//			}
//		}
//	}
	
	/**
	 * 
	 * Busca por todos os campos de entrada de dados da Tela.
	 */
	public static void lookForInputs(TestBattery battery) {

		for (TestCase tc : battery.getTestCases()) {
			TestProcedure tp = tc.getTestProcedure();
			System.out.println("URL: " + tp.getURL() + tp.getPath());

			for (Field newField : tp.getFields()) {
				putField(newField);
				paths.add( tp.getPath() );
				
				System.out.println(newField.getName()+" - "+tp.getPath());
			}
		}
	}


}
