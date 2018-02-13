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
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
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
@Test(testName = "PersistenceGeneratorTest")
public class PersistenceGeneratorTest {

	private final boolean enabled = true;

	@BeforeMethod
	public void dbcreator() {

		try {

			File dir1 = new File(".");
			SqlScript sqlScript = new SqlScript(dir1
					+ "\\test\\mysql\\PersistencegeneratorTest.sql");
			sqlScript.loadScript();
			sqlScript.execute();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@DataProvider(name = "unreferencedColoumnTest")
	Object[][] unreferencedColoumnDataProvider() {

		String[] anyquestionTest = new String[] { "(id,numero,texto",
				"[(]3,1,'resposta 1'" };

		String[] bibliotecaTest = new String[] {
				"(id,CPF,matricula,nome,telefone",
				"[(]2,[0-9]+,'[a-zA-z0-9]+','John Hunter',9999999999" };

		String[] bibliotecaLivro = new String[] {
				"(id,ISBN,ano,autor,edicao,editora,titulo",
				"[(]2,'[a-zA-z0-9]+','2004-01-01','Alves, William Pereira',1,'Érica','Fundamentos de Bancos de Dados'" };

		String[] testatributos = new String[] {
				"(id,doubles,floats,texts,varchar45,decimals,bigints,mediumints,smallinits,integers,tynints,longtexts,mediumTEXTs,tinytexts,chars,tinyblobss",
				"[(]51,1[89].[0-9]+,2[23].[0-9]+,'c1isa7','[a-zA-z0-9]+',1[56].[0-9]+,143,5,5,5,5,'lon1g','me1dop','h1j','[a-zA-Z0-9]','hjgh1jg'" };

		String[] testatributosnull = new String[] {
				"(id,doubles,floats,texts,varchar45,decimals,bigints,mediumints,smallinits,integers,tynints,longtexts,mediumTEXTs,tinytexts,chars,tinyblobss",
				"[(]51,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null" };

		Object[][] result = new Object[5][3];

		result[0] = new Object[] { anyquestionTest, "anyquestion",
				"possivelresposta", 0 };

		result[1] = new Object[] { bibliotecaTest, "biblioteca", "leitor", 0 };

		result[2] = new Object[] { bibliotecaLivro, "biblioteca", "livro", 0 };

		result[3] = new Object[] { testatributos, "testatributos", "table1", 0 };

		result[4] = new Object[] { testatributosnull, "testatributos",
				"table1", 1 };

		return result;
	}

	@Test(dataProvider = "unreferencedColoumnTest", enabled = enabled, groups = "developed")
	public void unreferencedColoumnTest(String[] array, String schema,
			String table, int i) throws SQLException, NotKnowTypeException,
			IrregularDateException, IOException {

		InitialValues.initValues("mysql");

		Connection connection = ConnectionFactory.getConnection(
				"com.mysql.jdbc.Driver", TestConstants.host,
				"INFORMATION_SCHEMA", TestConstants.user,
				TestConstants.password, "mysql");
		PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
				schema, connection);

		HashMap<String, Table> tables1 = persistenceDiscoverer
				.dependenceDiscoverer();

		connection.close();

		connection = ConnectionFactory.getConnection("com.mysql.jdbc.Driver",
				TestConstants.host, schema, TestConstants.user,
				TestConstants.password, "mysql");

		PersistenceReader persistenceReader = new PersistenceReader();

		HashMap<String, ArrayList<HashMap<String, String>>> actualState = persistenceReader
				.actualState(tables1, connection);

		PersistenceGenerator persistenceGenerator = new PersistenceGenerator(
				connection, tables1);

		persistenceGenerator.actualState = new ActualState();

		HashMap<String, String> keys = new HashMap<String, String>();

		String[] att = persistenceGenerator.unreferencedColoumn(tables1.get(
				table).getPrimitiveAtributes(), actualState.get(table).get(i),
				table, 0, keys);

		connection.close();

		String columnNames = array[0];

		String columnVal = array[1];

		Assert.assertEquals(att[0], columnNames);

		boolean isMatches = att[1].matches(columnVal);

		Assert.assertTrue(isMatches);

	}

	@SuppressWarnings("unchecked")
	@DataProvider(name = "dependenceGeneratorTest")
	Object[][] dependenceGeneratorDataProvider() {

		Collection<HashMap<String, String>> keys4 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> h8 = new HashMap<String, String>();
		h8.put("cpf", "[0-9]+");

		HashMap<String, Integer> quantity4 = new HashMap<String, Integer>();

		quantity4.put("funcionarios", 6);

		keys4.add(h8);
		keys4.add((HashMap<String, String>) h8.clone());
		keys4.add((HashMap<String, String>) h8.clone());
		keys4.add((HashMap<String, String>) h8.clone());
		keys4.add((HashMap<String, String>) h8.clone());
		keys4.add((HashMap<String, String>) h8.clone());

		Collection<HashMap<String, String>> keys3 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> h6 = new HashMap<String, String>();
		h6.put("Produtos_nome", "'[0-9a-zA-Z]+'");
		h6.put("Fornecedores_cnpj", "[0-9]+");
		h6.put("Produtos_fabricante", "'[0-9a-zA-Z]+'");

		HashMap<String, Integer> quantity3 = new HashMap<String, Integer>();

		quantity3.put("produtos", 3);
		quantity3.put("fornecedores", 3);
		quantity3.put("itens_fornecidos", 3);
		keys3.add(h6);
		keys3.add((HashMap<String, String>) h6.clone());

		Collection<HashMap<String, String>> keys = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> h = new HashMap<String, String>();
		h.put("id", "2");
		HashMap<String, String> h2 = new HashMap<String, String>();

		h2.put("id", "3");
		keys.add(h);
		keys.add(h2);

		HashMap<String, Integer> quantity = new HashMap<String, Integer>();

		quantity.put("livro", 3);
		quantity.put("copiadolivro", 3);

		Collection<HashMap<String, String>> keys2 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> h3 = new HashMap<String, String>();
		h3.put("id", "2");
		HashMap<String, String> h4 = new HashMap<String, String>();

		h4.put("id", "3");
		keys2.add(h3);
		keys2.add(h4);

		HashMap<String, Integer> quantity2 = new HashMap<String, Integer>();
		quantity2.put("usuario", 9);
		quantity2.put("informacaodeusuario", 3);
		quantity2.put("campo", 3);
		quantity2.put("pesquisa", 3);
		quantity2.put("responsavel", 3);

		Object[][] result = new Object[4][3];

		result[0] = new Object[] { "biblioteca", "copiadolivro", keys, 2,
				quantity };

		result[1] = new Object[] { "anyquestion", "informacaodeusuario", keys2,
				2, quantity2 };

		result[2] = new Object[] { "loja", "itens_fornecidos", keys3, 2,
				quantity3 };
		result[3] = new Object[] { "loja", "funcionarios", keys4, 2, quantity4 };
		return result;
	}

	@Test(dataProvider = "dependenceGeneratorTest", enabled = enabled, groups = "developed")
	public void dependenceGeneratorTest(String schema, String tablename,
			Collection<HashMap<String, String>> keys, int number,
			HashMap<String, Integer> quantity) throws SQLException,
			NotKnowTypeException, IrregularDateException, IOException {

		InitialValues.initValues("mysql");

		Connection connection = ConnectionFactory.getConnection(
				"com.mysql.jdbc.Driver", TestConstants.host,
				"INFORMATION_SCHEMA", TestConstants.user,
				TestConstants.password, "mysql");
		PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
				schema, connection);

		HashMap<String, Table> tables1 = persistenceDiscoverer
				.dependenceDiscoverer();

		connection.close();

		connection = ConnectionFactory.getConnection("com.mysql.jdbc.Driver",
				TestConstants.host, schema, TestConstants.user,
				TestConstants.password, "mysql");

		connection.setAutoCommit(false);

		PersistenceReader persistenceReader = new PersistenceReader();

		@SuppressWarnings("unused")
		HashMap<String, ArrayList<HashMap<String, String>>> actualState = persistenceReader
				.actualState(tables1, connection);

		Collection<HashMap<String, String>> actual = new ArrayList<HashMap<String, String>>();

		PersistenceGenerator persistenceGenerator = new PersistenceGenerator(
				connection, tables1);
		for (int i = 0; i < number; i++) {

			ActualState actualState2 = new ActualState();

			persistenceGenerator.actualState = actualState2;
			actual.addAll(persistenceGenerator.dependenceGenerator(tables1
					.get(tablename)));

			persistenceGenerator.criateUpdate();
		}

		boolean isSame = false;

		for (int i = 0; i < actual.size(); i++) {

			Set<String> name = ((ArrayList<HashMap<String, String>>) actual)
					.get(i).keySet();

			for (String string : name) {
				isSame = ((ArrayList<HashMap<String, String>>) actual).get(i)
						.get(string).matches(
								((ArrayList<HashMap<String, String>>) keys)
										.get(i).get(string));
				Assert.assertTrue(isSame);

			}

		}

		Set<String> set = quantity.keySet();

		for (String name : set) {
			String sql = "SELECT * FROM " + name + ";";

			PreparedStatement preparedStatement = connection
					.prepareStatement(sql);

			ResultSet resultSet = preparedStatement.executeQuery();

			int k = 0;
			while (resultSet.next()) {
				k++;
			}

			Assert.assertEquals(k, quantity.get(name).intValue());

			resultSet.close();
		}

		connection.close();

	}

	private PersistenceGenerator persistenceGeneratorCreator(String schema,
			HashMap<String, Table> tables1, Connection connection)
			throws SQLException, NotKnowTypeException {

		connection.setAutoCommit(false);

		PersistenceReader persistenceReader = new PersistenceReader();

		HashMap<String, ArrayList<HashMap<String, String>>> actualState = persistenceReader
				.actualState(tables1, connection);

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

	@Test(enabled = enabled, groups = "developed")
	public void ciclicCreatorTest() throws SQLException, NotKnowTypeException,
			IrregularDateException, IOException {

		InitialValues.initValues("mysql");

		HashMap<String, Table> tables = tableGenerator("mydb");

		Connection connection;

		connection = ConnectionFactory.getConnection("com.mysql.jdbc.Driver",
				TestConstants.host, "mydb", TestConstants.user,
				TestConstants.password, "mysql");

		PersistenceGenerator persistenceGenerator = persistenceGeneratorCreator(
				"mydb", tables, connection);

		ActualState actualState2 = new ActualState();

		persistenceGenerator.actualState = actualState2;
		Collection<HashMap<String, String>> resuklt = persistenceGenerator
				.dependenceGenerator(tables.get("a"));

		persistenceGenerator.criateUpdate();

		String sql = "SELECT * FROM c ;";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		int k = 0;
		while (resultSet.next()) {
			int value = resultSet.getInt("A_idA");
			int id = resultSet.getInt("idC");

			Assert.assertEquals(value, id);
			k++;
		}

		Assert.assertEquals(k, 2);
		resultSet.close();

		connection.close();

	}

	@Test(enabled = enabled, groups = "developed")
	public void reflexiveCreatorTest() throws SQLException,
			NotKnowTypeException, IrregularDateException, IOException {

		InitialValues.initValues("mysql");

		HashMap<String, Table> tables = tableGenerator("loja");

		Connection connection;

		connection = ConnectionFactory.getConnection("com.mysql.jdbc.Driver",
				TestConstants.host, "loja", TestConstants.user,
				TestConstants.password, "mysql");

		PersistenceGenerator persistenceGenerator = persistenceGeneratorCreator(
				"loja", tables, connection);

		ActualState actualState2 = new ActualState();

		persistenceGenerator.actualState = actualState2;
		Collection<HashMap<String, String>> resuklt = persistenceGenerator
				.dependenceGenerator(tables.get("funcionarios"));

		persistenceGenerator.criateUpdate();

		String sql = "SELECT * FROM funcionarios ;";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);

		ResultSet resultSet = preparedStatement.executeQuery();

		int k = 0;
		while (resultSet.next()) {
			Integer value = resultSet.getInt("Funcionarios_cpf");
			Integer id = resultSet.getInt("cpf");

			if (id % 2 == 1) {
				Assert.assertEquals(value, new Integer(0));
			} else {

				if (id == 2)
					Assert.assertEquals(value, new Integer(1));
				else
					Assert.assertEquals(value, new Integer(3));
			}
			k++;
		}

		Assert.assertEquals(k, 4);
		resultSet.close();

		connection.close();

	}

}
