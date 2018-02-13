package br.ufpi.datagenerator.test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.ufpi.datagenerator.creator.TypesCreator;
import br.ufpi.datagenerator.domain.NumericAttribute;
import br.ufpi.datagenerator.domain.PrimitiveAttributes;
import br.ufpi.datagenerator.domain.Table;
import br.ufpi.datagenerator.domain.TextAttribute;
import br.ufpi.datagenerator.domain.TimeAttribute;
import br.ufpi.datagenerator.domain.connectors.ConnectionFactory;
import br.ufpi.datagenerator.domain.types.IntegerType;
import br.ufpi.datagenerator.initialconfiguration.InitialValues;
import br.ufpi.datagenerator.tablescans.PersistenceDiscoverer;
import br.ufpi.datagenerator.util.IrregularDateException;
import br.ufpi.datagenerator.util.NotKnowTypeException;
import br.ufpi.datagenerator.util.SqlScript;

/**
 * @author iure
 * 
 */
@Test(testName = "TypesCreatorMysqlTest")
public class TypesCreatorMysqlTest {

	@BeforeClass
	public void dbcreator() {
		try {

			File dir1 = new File(".");
			SqlScript sqlScript = new SqlScript(dir1
					+ "\\test\\mysql\\atributes.sql");
			sqlScript.loadScript();
			sqlScript.execute();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private final boolean enabled = true;

	/**
	 * responsavel pelo teste da criação de numeros inteiros, onde verifica o
	 * limite superir de criação de numeros
	 * 
	 * @return
	 * @throws SQLException
	 * @throws NotKnowTypeException
	 * @throws IOException
	 */
	@DataProvider(name = "createIntegerTest")
	public Object[][] createIntegerDataprovider() throws SQLException,
			NotKnowTypeException, IOException {
		InitialValues.initValues("mysql");
		Connection connection = ConnectionFactory.getConnection(
				"com.mysql.jdbc.Driver", "localhost", "INFORMATION_SCHEMA",
				TestConstants.user, TestConstants.password, "mysql");
		PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
				"testatributos", connection);

		HashMap<String, Table> tables = persistenceDiscoverer
				.dependenceDiscoverer();

		connection.close();

		Collection<PrimitiveAttributes> ps = tables.get("table1")
				.getPrimitiveAtributes();

		Object[][] result = new Object[5][4];

		for (PrimitiveAttributes primitiveAttributes : ps) {
			if (primitiveAttributes.getType().getName().equalsIgnoreCase(
					"TINYINT")) {
				result[0] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 50, 100 };
			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("SMALLINT")) {
				result[1] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 5535, 30000 };

			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("MEDIUMINT")) {
				result[2] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 7216, 8388500 };

			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("BIGINT")) {
				result[3] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 1000, 9223372036854775701l };

			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("INT")) {
				result[4] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 1216, 2147483547 };

			}

		}

		return result;
	}

	@Test(dataProvider = "createIntegerTest", enabled = enabled, groups = "developed")
	public void createIntegerTest(String schema, String tableName,
			PrimitiveAttributes primitiveAttributes, long j, long valant) {

		try {
			TypesCreator typesCreator = new TypesCreator();

			Connection connection = ConnectionFactory.getConnection(
					"com.mysql.jdbc.Driver", "localhost", "testatributos",
					TestConstants.user, TestConstants.password, "mysql");

			long val = 0;

			for (long i = 0; i < j; i++) {

				String sql = "insert into " + tableName + " ("
						+ primitiveAttributes.getName() + ") values (?)";

				PreparedStatement stmt = (PreparedStatement) connection
						.prepareStatement(sql);

				val = typesCreator.nexInteger(
						((IntegerType) primitiveAttributes.getType()), false,
						(valant));

				stmt.setLong(1, val);

				valant = val;

				stmt.execute();
				stmt.close();
			}

			connection.close();

		} catch (SQLException e) {

			e.printStackTrace();
			Assert.fail();
		}

	}

	/**
	 * responsavel pelo teste da criação de numeros reais, onde verifica o
	 * limite superir de criação de numeros
	 * 
	 * @return
	 * @throws SQLException
	 * @throws NotKnowTypeException
	 * @throws IOException
	 */
	@DataProvider(name = "createDoubleTest")
	public Object[][] createDoubleDataprovider() throws SQLException,
			NotKnowTypeException, IOException {
		InitialValues.initValues("mysql");
		Connection connection = ConnectionFactory.getConnection(
				"com.mysql.jdbc.Driver", "localhost", "INFORMATION_SCHEMA",
				TestConstants.user, TestConstants.password, "mysql");
		PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
				"testatributos", connection);

		HashMap<String, Table> tables = persistenceDiscoverer
				.dependenceDiscoverer();

		connection.close();

		Collection<PrimitiveAttributes> ps = tables.get("table1")
				.getPrimitiveAtributes();

		Object[][] result = new Object[3][4];

		for (PrimitiveAttributes primitiveAttributes : ps) {
			if (primitiveAttributes.getType().getName().equalsIgnoreCase(
					"FLOAT")) {

				result[1] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 1000, new BigDecimal(9999991) };
			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("DOUBLE")) {
				result[2] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 1000,
						new BigDecimal(9999999999989999999999.9) };

			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("DECIMAL")) {
				result[0] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 1000, new BigDecimal(996d) };

			}

		}

		return result;
	}

	@Test(dataProvider = "createDoubleTest", enabled = enabled, groups = "developed")
	public void createDoubleTest(String schema, String tableName,
			PrimitiveAttributes primitiveAttributes, long j, BigDecimal valant) {

		try {
			TypesCreator typesCreator = new TypesCreator();

			Connection connection = ConnectionFactory.getConnection(
					"com.mysql.jdbc.Driver", "localhost", "testatributos",
					TestConstants.user, TestConstants.password, "mysql");

			BigDecimal val = new BigDecimal(0.0);

			for (long i = 0; i < j; i++) {

				String sql = "insert into " + tableName + " ("
						+ primitiveAttributes.getName() + ") values (?)";

				PreparedStatement stmt = (PreparedStatement) connection
						.prepareStatement(sql);

				val = typesCreator.nextReal(
						(NumericAttribute) primitiveAttributes, (valant));

				stmt.setBigDecimal(1, val);

				valant = val;

				stmt.execute();
				stmt.close();
			}

			connection.close();

		} catch (SQLException e) {

			e.printStackTrace();
			Assert.fail();
		}

	}

	/**
	 * responsavel pelo teste da criação de numeros inteiros, onde verifica o
	 * limite superir de criação de numeros
	 * 
	 * @return
	 * @throws SQLException
	 * @throws NotKnowTypeException
	 * @throws IOException
	 */
	@DataProvider(name = "createStringTest")
	public Object[][] createStringDataprovider() throws SQLException,
			NotKnowTypeException, IOException {
		InitialValues.initValues("mysql");
		Connection connection = ConnectionFactory.getConnection(
				"com.mysql.jdbc.Driver", "localhost", "INFORMATION_SCHEMA",
				TestConstants.user, TestConstants.password, "mysql");
		PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
				"testatributos", connection);

		HashMap<String, Table> tables = persistenceDiscoverer
				.dependenceDiscoverer();

		connection.close();

		Collection<PrimitiveAttributes> ps = tables.get("table1")
				.getPrimitiveAtributes();

		Object[][] result = new Object[7][4];

		for (PrimitiveAttributes primitiveAttributes : ps) {
			if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("CHAR")) {
				result[0] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 1 };
			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("TEXT")) {
				result[1] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 10 };

			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("MEDIUMTEXT")) {
				result[2] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 10 };

			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("LONGTEXT")) {
				result[3] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 10 };

			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("TINYBLOB")) {
				result[4] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 10 };

			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("TINYTEXT")) {
				result[5] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 10 };

			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("VARCHAR")) {
				result[6] = new Object[] { "testatributos", "table1",
						primitiveAttributes, 10 };

			}

		}

		return result;
	}

	@Test(dataProvider = "createStringTest", enabled = enabled, groups = "developed")
	public void createStringTest(String schema, String tableName,
			PrimitiveAttributes primitiveAttributes, long j) {

		try {
			TypesCreator typesCreator = new TypesCreator();

			Connection connection = ConnectionFactory.getConnection(
					"com.mysql.jdbc.Driver", "localhost", "testatributos",
					TestConstants.user, TestConstants.password, "mysql");

			String val;

			for (long i = 0; i < j; i++) {

				String sql = "insert into " + tableName + " ("
						+ primitiveAttributes.getName() + ") values (?)";

				PreparedStatement stmt = (PreparedStatement) connection
						.prepareStatement(sql);

				val = typesCreator
						.nextString((TextAttribute) primitiveAttributes);

				stmt.setString(1, val);

				stmt.execute();
				stmt.close();
			}

			connection.close();

		} catch (SQLException e) {

			e.printStackTrace();
			Assert.fail();
		}

	}

	/**
	 * responsavel pelo teste da criação de dados do tipo time
	 * 
	 * @return
	 * @throws SQLException
	 * @throws NotKnowTypeException
	 * @throws IOException
	 */
	@DataProvider(name = "createTimeTest")
	public Object[][] createTimeDataprovider() throws SQLException,
			NotKnowTypeException, IOException {
		InitialValues.initValues("mysql");
		Connection connection = ConnectionFactory.getConnection(
				"com.mysql.jdbc.Driver", "localhost", "INFORMATION_SCHEMA",
				TestConstants.user, TestConstants.password, "mysql");
		PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
				"testatributos", connection);

		HashMap<String, Table> tables = persistenceDiscoverer
				.dependenceDiscoverer();

		connection.close();

		Collection<PrimitiveAttributes> ps = tables.get("datetimetable")
				.getPrimitiveAtributes();

		Object[][] result = new Object[5][4];

		for (PrimitiveAttributes primitiveAttributes : ps) {
			if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("DATE")) {
				result[0] = new Object[] { "testatributos", "dateTimeTable",
						primitiveAttributes, 1000 };
			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("DATETIME")) {
				result[1] = new Object[] { "testatributos", "dateTimeTable",
						primitiveAttributes, 1000 };

			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("TIMESTAMP")) {
				result[2] = new Object[] { "testatributos", "dateTimeTable",
						primitiveAttributes, 1000 };

			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("TIME")) {
				result[3] = new Object[] { "testatributos", "dateTimeTable",
						primitiveAttributes, 1000 };

			} else if (primitiveAttributes.getType().getName()
					.equalsIgnoreCase("YEAR")) {
				result[4] = new Object[] { "testatributos", "dateTimeTable",
						primitiveAttributes, 1000 };

			}

		}

		return result;
	}

	@Test(dataProvider = "createTimeTest", enabled = enabled, groups = "developed")
	public void createTimeTest(String schema, String tableName,
			PrimitiveAttributes primitiveAttributes, long j) {

		try {
			TypesCreator typesCreator = new TypesCreator();

			Connection connection = ConnectionFactory.getConnection(
					"com.mysql.jdbc.Driver", TestConstants.host,
					"testatributos", TestConstants.user,
					TestConstants.password, "mysql");

			String val;

			for (long i = 0; i < j; i++) {

				String sql = "insert into " + tableName + " ("
						+ primitiveAttributes.getName() + ") values (?)";

				PreparedStatement stmt = (PreparedStatement) connection
						.prepareStatement(sql);

				val = typesCreator
						.nextDate((TimeAttribute) primitiveAttributes);

				stmt.setString(1, val);

				stmt.execute();
				stmt.close();
			}

			connection.close();

		} catch (SQLException e) {

			e.printStackTrace();
			Assert.fail();
		} catch (IrregularDateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
