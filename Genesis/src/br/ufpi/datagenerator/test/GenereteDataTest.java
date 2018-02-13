/**
 * 
 */
package br.ufpi.datagenerator.test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import br.ufpi.datagenerator.creator.PersistenceGenerator;
import br.ufpi.datagenerator.creator.RequiredTables;
import br.ufpi.datagenerator.domain.connectors.ConnectionFactory;
import br.ufpi.datagenerator.initialconfiguration.InitialValues;
import br.ufpi.datagenerator.util.IrregularDateException;
import br.ufpi.datagenerator.util.NotKnowTypeException;
import br.ufpi.datagenerator.util.SqlScript;

/**
 * @author iure
 * 
 */
@Test(testName = "GenereteDataTest")
public class GenereteDataTest {

	private final boolean enabled = true;

	private void dbcreator(String script) {

		try {

			File dir1 = new File(".");
			SqlScript sqlScript = new SqlScript(dir1 + "\\test\\mysql\\"
					+ script);
			sqlScript.loadScript();
			sqlScript.execute();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private Connection getConnection(String schemaName) throws SQLException,
			IOException {

		InitialValues.initValues("mysql");

		return ConnectionFactory.getConnection("com.mysql.jdbc.Driver",
				"localhost", schemaName, "root", TestConstants.password,
				"mysql");
	}

	@Test(enabled = enabled, groups = "developed")
	public void generateAnyquestionTest() throws IOException, SQLException,
			NotKnowTypeException, IrregularDateException {

		dbcreator("GenerateDataAnyquestion.sql");

		Connection connection = getConnection("anyquestion");

		PersistenceGenerator persistenceGenerator = new PersistenceGenerator(
				connection);

		HashMap<String, Collection<String>> a = new HashMap<String, Collection<String>>();
		RequiredTables requiredTables = new RequiredTables();

		ArrayList<String> columNames = new ArrayList<String>();

		columNames.add("login");

		columNames.add("senha");

		a.put("usuario", columNames);

		columNames = new ArrayList<String>();

		columNames.add("id");

		a.put("pesquisa", columNames);

		requiredTables.setTables(a);

		requiredTables.setNumberOfReplications(1000);

		requiredTables.setSchema("anyquestion");

		persistenceGenerator.generateData(requiredTables);

		connection = getConnection("anyquestion");

		Assert.assertEquals(numberOfelements(connection, "usuario"), 3003);

		Assert
				.assertEquals(numberOfelements(connection, "administrador"),
						1001);

		Assert.assertEquals(numberOfelements(connection, "campo"), 5005);

		Assert.assertEquals(numberOfelements(connection, "grupodequestao"),
				2002);

		Assert.assertEquals(numberOfelements(connection, "participante"), 1001);

		Assert.assertEquals(numberOfelements(connection, "grupodequestao"),
				2002);

		Assert.assertEquals(numberOfelements(connection, "pesquisa"), 4004);

		Assert.assertEquals(numberOfelements(connection, "possivelresposta"),
				6006);

		Assert.assertEquals(numberOfelements(connection, "questao"), 2002);

		Assert.assertEquals(numberOfelements(connection, "responsavel"), 1001);

		Assert.assertEquals(numberOfelements(connection, "valorvalido"), 3003);

		connection.close();

	}

	@Test(enabled = enabled, groups = "developed")
	public void generatebibliotecaTest() throws IOException, SQLException,
			NotKnowTypeException, IrregularDateException {

		dbcreator("generatebibliotecatest.sql");

		Connection connection = getConnection("biblioteca");

		PersistenceGenerator persistenceGenerator = new PersistenceGenerator(
				connection);

		HashMap<String, Collection<String>> a = new HashMap<String, Collection<String>>();
		RequiredTables requiredTables = new RequiredTables();

		ArrayList<String> columNames = new ArrayList<String>();

		columNames.add("id");

		a.put("copiadolivro", columNames);

		columNames = new ArrayList<String>();

		columNames.add("id");

		a.put("leitor", columNames);

		requiredTables.setTables(a);

		requiredTables.setNumberOfReplications(5000);

		requiredTables.setSchema("biblioteca");

		persistenceGenerator.generateData(requiredTables);

		connection = getConnection("biblioteca");

		Assert.assertEquals(numberOfelements(connection, "copiadolivro"), 5001);

		Assert.assertEquals(numberOfelements(connection, "livro"), 5001);

		Assert.assertEquals(numberOfelements(connection, "leitor"), 5001);

		connection.close();

	}

	@Test(enabled = enabled, groups = "developed")
	public void generatemydbTest() throws IOException, SQLException,
			NotKnowTypeException, IrregularDateException {

		dbcreator("generateDatamydb.sql");

		Connection connection = getConnection("mydb");

		PersistenceGenerator persistenceGenerator = new PersistenceGenerator(
				connection);

		HashMap<String, Collection<String>> a = new HashMap<String, Collection<String>>();
		RequiredTables requiredTables = new RequiredTables();

		ArrayList<String> columNames = new ArrayList<String>();

		columNames.add("idtable1");

		a.put("table1", columNames);

		requiredTables.setTables(a);

		requiredTables.setNumberOfReplications(5000);

		requiredTables.setSchema("mydb");

		persistenceGenerator.generateData(requiredTables);

		connection = getConnection("mydb");

		Assert.assertEquals(numberOfelements(connection, "table1"), 5001);

		Assert.assertEquals(numberOfelements(connection, "a"), 5001);

		Assert.assertEquals(numberOfelements(connection, "b"), 5001);

		Assert.assertEquals(numberOfelements(connection, "c"), 5001);

		connection.close();

	}

	private long numberOfelements(Connection connection, String tableName)
			throws SQLException {

		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT count(*) FROM " + tableName + " u");

		ResultSet resultSet = preparedStatement.executeQuery();

		long quantity = 0;
		while (resultSet.next()) {
			quantity = resultSet.getLong(1);
		}

		resultSet.close();

		return quantity;

	}
}
