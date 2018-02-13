package br.ufpi.datagenerator.tablescans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * È responsavel por receber os paramentro executar querys
 * 
 * @author Iure
 * 
 */
public class Research {

	private String[] atributes;

	private PreparedStatement preparedStatement;

	public Research(String sqlQuery, Connection connection, String... atributes)
			throws SQLException {

		this.atributes = atributes;

		preparedStatement = connection.prepareStatement(sqlQuery);
	}

	/**
	 * tem a responsabilidade de retornar o resultado de uma pesquisa
	 * 
	 * @author Iure
	 * @return ResultSet - contendo o resultado da query executada
	 * @throws SQLException
	 */
	public ResultSet executeQuery() throws SQLException {

		int i = 1;

		for (String atribute : atributes) {

			preparedStatement.setString(i, atribute);

			i++;
		}

		ResultSet resultSet = preparedStatement.executeQuery();

		return resultSet;

	}

	/**
	 * Fecha o statment
	 * 
	 * @throws SQLException
	 */
	public void closeStatment() throws SQLException {
		preparedStatement.close();
	}

}
