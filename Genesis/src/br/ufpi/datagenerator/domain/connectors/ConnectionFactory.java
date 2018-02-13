package br.ufpi.datagenerator.domain.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public static Connection getConnection(String driver, String host,
			String schema, String user, String password, String dataBase)
			throws SQLException {
		try {
			Class.forName(driver);

			String conf = "jdbc:" + dataBase + "://" + host + "/" + schema;

			return (Connection) DriverManager.getConnection(conf, user,
					password);
		} catch (ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}
	}
}
