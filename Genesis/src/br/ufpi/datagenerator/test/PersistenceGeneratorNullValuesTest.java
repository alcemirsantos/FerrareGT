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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.ufpi.datagenerator.creator.ActualState;
import br.ufpi.datagenerator.creator.PersistenceGenerator;
import br.ufpi.datagenerator.domain.Table;
import br.ufpi.datagenerator.domain.connectors.ConnectionFactory;
import br.ufpi.datagenerator.initialconfiguration.InitialValues;
import br.ufpi.datagenerator.tablescans.PersistenceDiscoverer;
import br.ufpi.datagenerator.tablescans.PersistenceReader;
import br.ufpi.datagenerator.util.IrregularDateException;
import br.ufpi.datagenerator.util.NotKnowTypeException;
import br.ufpi.datagenerator.util.SqlScript;

/**
 * @author iure
 * 
 */
@Test(testName = "PersistenceGeneratorNullValuesTest")
public class PersistenceGeneratorNullValuesTest {

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

	private PersistenceGenerator persistenceGeneratorCreator(String schema,
			HashMap<String, Table> tables1, Connection connection)
			throws SQLException, NotKnowTypeException {

		connection.setAutoCommit(false);

		PersistenceReader persistenceReader = new PersistenceReader();

		@SuppressWarnings("unused")
		HashMap<String, ArrayList<HashMap<String, String>>> actualState = persistenceReader
				.actualState(tables1, connection);

		@SuppressWarnings("unused")
		Collection<HashMap<String, String>> actual = new ArrayList<HashMap<String, String>>();

		PersistenceGenerator persistenceGenerator = new PersistenceGenerator(
				connection, tables1);

		return persistenceGenerator;

	}

	private HashMap<String, Table> tableGenerator(String schema)
			throws SQLException, NotKnowTypeException {

		Connection connection = ConnectionFactory.getConnection(
				"com.mysql.jdbc.Driver", TestConstants.host,
				"INFORMATION_SCHEMA", TestConstants.user,
				TestConstants.password, "mysql");
		PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
				schema, connection);

		HashMap<String, Table> tables1 = persistenceDiscoverer
				.dependenceDiscoverer();

		connection.close();

		return tables1;

	}

	@Test(enabled = true, groups = "developed")
	public void ciclicCreatorTest() throws SQLException, NotKnowTypeException,
			IrregularDateException, IOException {

		dbcreator("PersistencegeneratorTestNullValues.sql");

		InitialValues.initValues("mysql");

		HashMap<String, Table> tables = tableGenerator("anyquestion");

		Connection connection;

		connection = ConnectionFactory.getConnection("com.mysql.jdbc.Driver",
				TestConstants.host, "anyquestion", TestConstants.user,
				TestConstants.password, "mysql");

		PersistenceGenerator persistenceGenerator = persistenceGeneratorCreator(
				"anyquestion", tables, connection);

		ActualState actualState2 = new ActualState();

		persistenceGenerator.actualState = actualState2;

		@SuppressWarnings("unused")
		Collection<HashMap<String, String>> resuklt = persistenceGenerator
				.dependenceGenerator(tables.get("questao"));

		persistenceGenerator.criateUpdate();

		connection.commit();

		String sql = "SELECT * FROM grupodequestao ;";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		int k = 0;
		while (resultSet.next()) {
			int value = resultSet.getInt("id_pesquisa");

			Assert.assertEquals(value, 0);
			k++;
		}

		Assert.assertEquals(k, 2);
		resultSet.close();

		connection.close();

	}

	@DataProvider(name = "multipleDependencesCreatorTest")
	Object[][] multipleDependencesCreatorDataProvider() {

		Object[][] result = new Object[2][3];

		result[1] = new Object[] { "PersistencegeneratorTestNullValues2.sql",
				2, 4, 2 };
		result[0] = new Object[] { "anyquestionwhitNullValues.sql", 2, 4, 2 };

		return result;

	}

	@Test(dataProvider = "multipleDependencesCreatorTest", enabled = enabled, groups = "developed")
	public void multipleDependencesCreatorTest(String schema, int numPesq,
			int numgrupoQuestao, int cont) throws SQLException,
			NotKnowTypeException, IrregularDateException, IOException {

		dbcreator(schema);

		InitialValues.initValues("mysql");

		HashMap<String, Table> tables = tableGenerator("anyquestion");

		Connection connection;

		connection = ConnectionFactory.getConnection("com.mysql.jdbc.Driver",
				TestConstants.host, "anyquestion", TestConstants.user,
				TestConstants.password, "mysql");

		PersistenceGenerator persistenceGenerator = persistenceGeneratorCreator(
				"anyquestion", tables, connection);

		ActualState actualState2 = new ActualState();

		persistenceGenerator.actualState = actualState2;
		@SuppressWarnings("unused")
		Collection<HashMap<String, String>> resuklt = persistenceGenerator
				.dependenceGenerator(tables.get("resposta"));

		persistenceGenerator.criateUpdate();

		connection.commit();

		String sql2 = "SELECT * FROM pesquisa ;";

		PreparedStatement preparedStatement2 = connection
				.prepareStatement(sql2);

		ResultSet resultSet2 = preparedStatement2.executeQuery();

		int k2 = 0;
		while (resultSet2.next()) {

			k2++;
		}

		Assert.assertEquals(k2, numPesq);
		resultSet2.close();

		String sql = "SELECT * FROM grupodequestao ;";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		int k = 0;
		while (resultSet.next()) {

			k++;
		}

		Assert.assertEquals(k, numgrupoQuestao);
		resultSet.close();

		connection.close();

	}

}
