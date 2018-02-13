package br.ufpi.datagenerator.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.ufpi.datagenerator.domain.NumericAttribute;
import br.ufpi.datagenerator.domain.PrimitiveAttributes;
import br.ufpi.datagenerator.domain.Relationship;
import br.ufpi.datagenerator.domain.Table;
import br.ufpi.datagenerator.domain.TextAttribute;
import br.ufpi.datagenerator.domain.connectors.ConnectionFactory;
import br.ufpi.datagenerator.domain.types.Group;
import br.ufpi.datagenerator.domain.types.IntegerType;
import br.ufpi.datagenerator.domain.types.StringType;
import br.ufpi.datagenerator.initialconfiguration.InitialValues;
import br.ufpi.datagenerator.tablescans.PersistenceDiscoverer;
import br.ufpi.datagenerator.util.NotKnowTypeException;

@Test(testName = "PersistenceDiscovererTestForPostgres")
public class PersistenceDiscovererTestForPostgres {

	private final boolean enabled = true;

	// teste o tablenameDiscoverer na tabela biblioteca
	@Test(enabled = true, groups = "developed")
	public void tableNameDiscoverTestPosgres() throws IOException {

		PersistenceDiscoverer persistenceDiscoverer;
		try {

			InitialValues.initValues("postgreSQL8.2");
			Connection connection = getConnection("biblioteca");

			persistenceDiscoverer = new PersistenceDiscoverer("biblioteca",
					connection);

			Collection<String> tableNames = persistenceDiscoverer
					.tableNamesDiscoverer();

			String[] coisa = new String[] { "emprestimo", "copiadolivro",
					"livro", "reserva", "leitor" };

			List<String> result = (List<String>) Arrays.asList(coisa);

			Assert.assertEquals(result, tableNames);
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@DataProvider(name = "attributesFinderDataProvider")
	public Object[][] attributesFinderDataProviderPostgres() {

		PrimitiveAttributes p0 = new NumericAttribute("id", new IntegerType(
				"bigint".toUpperCase(), Group.NUMERIC, 9223372036854775807l,
				-9223372036854775808l, 9223372036854775807l), false, true,
				false, false, true);

		PrimitiveAttributes p = new NumericAttribute("cpf", new IntegerType(
				"bigint".toUpperCase(), Group.NUMERIC, 9223372036854775807l,
				-9223372036854775808l, 9223372036854775807l), true, false,
				false, false, false);

		PrimitiveAttributes p2 = new TextAttribute("matricula", new StringType(
				"character".toUpperCase(), Group.STRING, 255l), true, false,
				false, 255l);

		PrimitiveAttributes p3 = new TextAttribute("nome", new StringType(
				"character".toUpperCase(), Group.STRING, 255l), false, false,
				false, 255l);

		PrimitiveAttributes p4 = new NumericAttribute("telefone",
				new IntegerType("bigint".toUpperCase(), Group.NUMERIC,
						9223372036854775807l, -9223372036854775808l,
						9223372036854775807l), false, false, false, false,
				false);

		ArrayList<PrimitiveAttributes> resul1 = new ArrayList<PrimitiveAttributes>();
		resul1.add(p0);
		resul1.add(p);
		resul1.add(p2);
		resul1.add(p3);
		resul1.add(p4);

		Object[][] result = new Object[1][2];

		result[0] = new Object[] { "biblioteca", "leitor", resul1 };

		return result;
	}

	public Connection getConnection(String schemaName) throws SQLException,
			IOException {
		InitialValues.initValues("postgreSQL8.2");
		return ConnectionFactory.getConnection("org.postgresql.Driver",
				"localhost", schemaName, "postgres", TestConstants.password,
				"postgresql");
	}

	@Test(dataProvider = "attributesFinderDataProvider", enabled = enabled, groups = "developed")
	public void attributesFinderTestpostgres(String schema, String tableNAme,
			ArrayList<PrimitiveAttributes> result) throws NotKnowTypeException,
			IOException {
		ArrayList<PrimitiveAttributes> primitives = null;
		try {

			Connection connection = getConnection(schema);
			PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
					schema, connection);

			primitives = (ArrayList<PrimitiveAttributes>) persistenceDiscoverer
					.atributesFinder(tableNAme);

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		primitives.equals(result);

		Assert.assertEquals(primitives, result);

	}

	@DataProvider(name = "relationshipFindetTest")
	public Object[][] relationshipFinderDataProviderPostgres() {

		HashMap<String, String> name = new HashMap<String, String>();
		name.put("id_copiadolivro", "id");
		Relationship r = new Relationship(name, 0, false, new Table(
				"copiadolivro", null, null), false);
		HashMap<String, String> name2 = new HashMap<String, String>();
		name2.put("id_leitor", "id");
		Relationship r2 = new Relationship(name2, 0, false, new Table("leitor",
				null, null), false);

		ArrayList<Relationship> relations = new ArrayList<Relationship>();
		relations.add(r);
		relations.add(r2);

		HashMap<String, String> name3 = new HashMap<String, String>();
		name3.put("id_livro", "id");
		Relationship r3 = new Relationship(name3, 0, false, new Table("livro",
				null, null), false);

		ArrayList<Relationship> relations2 = new ArrayList<Relationship>();
		relations2.add(r3);

		Object[][] result = new Object[2][3];

		result[0] = new Object[] { "biblioteca", "emprestimo", relations };
		result[1] = new Object[] { "biblioteca", "copiadolivro", relations2 };

		return result;

	}

	@Test(dataProvider = "relationshipFindetTest", enabled = enabled, groups = "developed")
	public void relationshipFindetTestPosgres(String schema, String tablename,
			ArrayList<Relationship> expected) throws IOException {

		ArrayList<Relationship> relationships = null;

		try {

			Connection connection = getConnection(schema);
			PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
					schema, connection);

			relationships = (ArrayList<Relationship>) persistenceDiscoverer
					.relationshipFinder(tablename);

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(relationships, expected);

	}

}
