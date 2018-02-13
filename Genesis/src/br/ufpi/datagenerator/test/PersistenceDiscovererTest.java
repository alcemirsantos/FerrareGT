package br.ufpi.datagenerator.test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.ufpi.datagenerator.domain.NumericAttribute;
import br.ufpi.datagenerator.domain.PrimitiveAttributes;
import br.ufpi.datagenerator.domain.Relationship;
import br.ufpi.datagenerator.domain.Table;
import br.ufpi.datagenerator.domain.TextAttribute;
import br.ufpi.datagenerator.domain.TimeAttribute;
import br.ufpi.datagenerator.domain.connectors.ConnectionFactory;
import br.ufpi.datagenerator.domain.types.DateType;
import br.ufpi.datagenerator.domain.types.Group;
import br.ufpi.datagenerator.domain.types.IntegerType;
import br.ufpi.datagenerator.domain.types.RealType;
import br.ufpi.datagenerator.domain.types.StringType;
import br.ufpi.datagenerator.initialconfiguration.InitialValues;
import br.ufpi.datagenerator.tablescans.PersistenceDiscoverer;
import br.ufpi.datagenerator.util.MyArrayList;
import br.ufpi.datagenerator.util.NotKnowTypeException;
import br.ufpi.datagenerator.util.SqlScript;

@Test(testName = "PersistenceDiscovererTest")
public class PersistenceDiscovererTest {

	private final boolean enabled = true;

	@BeforeClass
	public void dbcreator() {
		try {

			File dir1 = new File(".");
			SqlScript sqlScript = new SqlScript(dir1
					+ "\\test\\mysql\\PersistencediscoveryTest.sql");
			sqlScript.loadScript();
			sqlScript.execute();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// teste o tablenameDiscoverer na tabela biblioteca
	@Test(enabled = enabled, groups = "developed")
	public void tableNameDiscoverTest() {

		PersistenceDiscoverer persistenceDiscoverer;
		try {

			Connection connection = ConnectionFactory.getConnection(
					"com.mysql.jdbc.Driver", "localhost", "biblioteca", "root",
					TestConstants.password, "mysql");
			persistenceDiscoverer = new PersistenceDiscoverer("biblioteca",
					connection);

			Collection<String> tableNames = persistenceDiscoverer
					.tableNamesDiscoverer();

			String[] coisa = new String[] { "copiadolivro", "emprestimo",
					"leitor", "livro", "reserva" };

			List<String> result = (List<String>) Arrays.asList(coisa);

			Assert.assertEquals(result, tableNames);
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@DataProvider(name = "attributesFinderDataProvider")
	public Object[][] attributesFinderDataProvider() {

		PrimitiveAttributes p0 = new NumericAttribute("id", new IntegerType(
				"BIGINT", Group.NUMERIC, 9223372036854775801l,
				-9223372036854775801l, 9223372036854775801l), false, true,
				false, false, true);

		PrimitiveAttributes p = new NumericAttribute("CPF", new IntegerType(
				"BIGINT", Group.NUMERIC, 9223372036854775801l,
				-9223372036854775801l, 9223372036854775801l), true, false,
				false, false, false);

		PrimitiveAttributes p2 = new TextAttribute("matricula", new StringType(
				"VARCHAR", Group.STRING, 255l), true, false, false, 255l);

		PrimitiveAttributes p3 = new TextAttribute("nome", new StringType(
				"VARCHAR", Group.STRING, 255l), false, false, false, 255l);

		PrimitiveAttributes p4 = new NumericAttribute("telefone",
				new IntegerType("BIGINT", Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
				false, false, false, false);

		ArrayList<PrimitiveAttributes> resul1 = new ArrayList<PrimitiveAttributes>();
		resul1.add(p0);
		resul1.add(p);
		resul1.add(p2);
		resul1.add(p3);
		resul1.add(p4);

		PrimitiveAttributes p5 = new NumericAttribute("id", new IntegerType(
				"BIGINT", Group.NUMERIC, 9223372036854775801l,
				-9223372036854775801l, 9223372036854775801l), false, true,
				false, false, true);

		PrimitiveAttributes p6 = new TimeAttribute("dataDeEmprestimo",
				new DateType("DATE", Group.DATE_TIME, "AAAA-MM-DD",
						"1000-01-01", "9999-12-31"), false, false, false);

		PrimitiveAttributes p7 = new TimeAttribute("dataDeEntregaReal",
				new DateType("DATE", Group.DATE_TIME, "AAAA-MM-DD",
						"1000-01-01", "9999-12-31"), false, false, true);

		PrimitiveAttributes p8 = new TimeAttribute("dataPrevistaDeDevolucao",
				new DateType("DATE", Group.DATE_TIME, "AAAA-MM-DD",
						"1000-01-01", "9999-12-31"), false, false, false);

		PrimitiveAttributes p9 = new TextAttribute("situacaoEmprestimo",
				new StringType("VARCHAR", Group.STRING, 255l), false, false,
				true, 255l);

		ArrayList<PrimitiveAttributes> resul2 = new ArrayList<PrimitiveAttributes>();
		resul2.add(p5);
		resul2.add(p6);
		resul2.add(p7);
		resul2.add(p8);
		resul2.add(p9);

		PrimitiveAttributes p10 = new TextAttribute("numero_emprestimo",
				new StringType("CHAR", Group.STRING, 255l), false, true, false,
				10l);

		PrimitiveAttributes p11 = new TextAttribute("nome_agencia",
				new StringType("CHAR", Group.STRING, 255l), false, false, true,
				15l);

		PrimitiveAttributes p12 = new NumericAttribute("quantia", new RealType(
				"DECIMAL", Group.NUMERIC, 1.0E9d, -1.0E9d), false, false, true,
				false, false);

		ArrayList<PrimitiveAttributes> resul3 = new ArrayList<PrimitiveAttributes>();
		resul3.add(p10);
		resul3.add(p11);
		resul3.add(p12);

		Object[][] result = new Object[3][2];

		result[0] = new Object[] { "biblioteca", "leitor", resul1 };
		result[1] = new Object[] { "biblioteca", "emprestimo", resul2 };
		result[2] = new Object[] { "banco", "emprestimo", resul3 };

		return result;
	}

	@Test(dataProvider = "attributesFinderDataProvider", enabled = enabled, groups = "developed")
	public void attributesFinderTest(String schema, String tableNAme,
			ArrayList<PrimitiveAttributes> result) throws NotKnowTypeException,
			IOException {
		ArrayList<PrimitiveAttributes> primitives = null;
		try {

			InitialValues.initValues("mysql");

			Connection connection = ConnectionFactory.getConnection(
					"com.mysql.jdbc.Driver", "localhost", "INFORMATION_SCHEMA",
					"root", TestConstants.password, "mysql");
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
	public Object[][] relationshipFinderDataProvider() {

		HashMap<String, String> name = new HashMap<String, String>();
		name.put("id_copiaDoLivro", "id");
		Relationship r = new Relationship(name, 0, true, new Table(
				"copiadolivro", null, null), false);
		HashMap<String, String> name2 = new HashMap<String, String>();
		name2.put("id_leitor", "id");
		Relationship r2 = new Relationship(name2, 0, true, new Table("leitor",
				null, null), false);

		ArrayList<Relationship> relations = new ArrayList<Relationship>();
		relations.add(r);
		relations.add(r2);

		HashMap<String, String> name3 = new HashMap<String, String>();
		name3.put("id_livro", "id");
		Relationship r3 = new Relationship(name3, 0, true, new Table("livro",
				null, null), false);

		ArrayList<Relationship> relations2 = new ArrayList<Relationship>();
		relations2.add(r3);

		HashMap<String, String> name4 = new HashMap<String, String>();
		name4.put("Funcionarios_cpf", "cpf");
		Relationship r4 = new Relationship(name4, 0, false, new Table(
				"funcionarios", null, null), false);
		ArrayList<Relationship> relations3 = new ArrayList<Relationship>();
		relations3.add(r4);

		Object[][] result = new Object[3][3];

		result[0] = new Object[] { "biblioteca", "emprestimo", relations };
		result[1] = new Object[] { "biblioteca", "copiadolivro", relations2 };
		// testa autorelacionamentos
		result[2] = new Object[] { "loja", "funcionarios", relations3 };

		return result;

	}

	@Test(dataProvider = "relationshipFindetTest", enabled = enabled, groups = "developed")
	public void relationshipFindetTest(String schema, String tablename,
			ArrayList<Relationship> expected) throws IOException {

		ArrayList<Relationship> relationships = null;

		try {

			InitialValues.initValues("mysql");
			Connection connection = ConnectionFactory.getConnection(
					"com.mysql.jdbc.Driver", "localhost", "INFORMATION_SCHEMA",
					"root", TestConstants.password, "mysql");
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

	@SuppressWarnings("unchecked")
	@DataProvider(name = "dependenceDiscoverTest")
	public Object[][] dependenceDyscoverDataProvider() {

		// schema loja
		Table t1 = new Table("fornecedores", new MyArrayList(new Object[] {
				new NumericAttribute("cnpj", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, true, false, false, true),
				new TextAttribute("nome", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, false, 45l),
				new TextAttribute("endereco", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, false, 255l),
				new TextAttribute("fone", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, false, 10l), }),
				new MyArrayList(new Object[] {}));

		Table t2 = null;

		HashMap<String, String> name1 = new HashMap<String, String>();
		name1.put("Funcionarios_cpf", "cpf");
		t2 = new Table("funcionarios", new MyArrayList(new Object[] {
				new NumericAttribute("cpf", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, true, false, false, true),
				new TextAttribute("nome", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 45l),
				new TextAttribute("cargo", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 20l), }),
				new MyArrayList(new Object[] { new Relationship(name1, 0,
						false, new Table("funcionarios", null, null), false) }));

		t2.getRelationship().iterator().next().setForeignKey(t2);

		Table t3 = new Table("produtos", new MyArrayList(new Object[] {
				new TextAttribute("nome", new StringType("VARCHAR",
						Group.STRING, 255l), false, true, false, 45l),
				new TextAttribute("fabricante", new StringType("VARCHAR",
						Group.STRING, 255l), false, true, false, 45l),
				new NumericAttribute("quantidade", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, false, false, false, false),
				new NumericAttribute("preco", new RealType("FLOAT",
						Group.NUMERIC, 9999999.0d, -9999999.0d), false, false,
						false, false, false), }), new MyArrayList(
				new Object[] {}));

		HashMap<String, String> name2 = new HashMap<String, String>();
		name2.put("Funcionarios_cpf", "cpf");
		Table t4 = new Table("vendas", new MyArrayList(new Object[] {
				new NumericAttribute("cod_vendas", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, true, false, false, true),
				new NumericAttribute("valor_total", new RealType("FLOAT",
						Group.NUMERIC, 9999999.0d, -9999999.0d), false, false,
						true, false, false),
				new TimeAttribute("data_2", new DateType("DATE",
						Group.DATE_TIME, "AAAA-MM-DD", "1000-01-01",
						"9999-12-31"), false, false, true), }),
				new MyArrayList(new Object[] { new Relationship(name2, 0,
						false, new Table("funcionarios", null, null), false) }));

		t4.getRelationship().iterator().next().setForeignKey(t2);

		HashMap<String, String> name3 = new HashMap<String, String>();
		name3.put("Fornecedores_cnpj", "cnpj");

		HashMap<String, String> name4 = new HashMap<String, String>();
		name4.put("Produtos_nome", "nome");
		name4.put("Produtos_fabricante", "fabricante");
		Table t5 = new Table("itens_fornecidos", new ArrayList(),
				new MyArrayList(new Object[] {
						new Relationship(name3, 0, false, t1, true),
						new Relationship(name4, 0, false, t3, true) }));

		Table t6 = new Table("remessa", new MyArrayList(new Object[] {
				new TimeAttribute("data_2", new DateType("DATE",
						Group.DATE_TIME, "AAAA-MM-DD", "1000-01-01",
						"9999-12-31"), false, true, false),
				new TextAttribute("Itens_fornecidos_Produtos_nome",
						new StringType("VARCHAR", Group.STRING, 255l), false,
						false, false, 45l),
				new TextAttribute("Itens_fornecidos_Produtos_fabricante",
						new StringType("VARCHAR", Group.STRING, 255l), false,
						false, false, 45l),
				new NumericAttribute("Itens_fornecidos_Fornecedores_cnpj",
						new IntegerType("INT", Group.NUMERIC, 2147483647l,
								-2147483648l, 4294967296l), false, false,
						false, false, true),
				new NumericAttribute("quantidade_fornecida", new IntegerType(
						"INT", Group.NUMERIC, 2147483647l, -2147483648l,
						4294967296l), false, false, true, false, true),
				new NumericAttribute("preco_compra", new RealType("FLOAT",
						Group.NUMERIC, 9999999.0d, -9999999.0d), false, false,
						true, false, false), }), new MyArrayList(
				new Object[] {}));

		HashMap<String, String> name5 = new HashMap<String, String>();
		name5.put("Produtos_nome", "nome");
		name5.put("Produtos_fabricante", "fabricante");

		HashMap<String, String> name6 = new HashMap<String, String>();
		name6.put("Vendas_cod_vendas", "cod_vendas");
		Table t7 = new Table("itens_de_venda", new MyArrayList(new Object[] {
				new NumericAttribute("total", new RealType("FLOAT",
						Group.NUMERIC, 9999999.0d, -9999999.0d), false, false,
						true, false, false),
				new NumericAttribute("quantidade", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, false, true, false, true), }), new MyArrayList(
				new Object[] { new Relationship(name6, 0, false, t4, true),
						new Relationship(name5, 0, false, t3, true) }));

		HashMap<String, Table> h1 = new HashMap<String, Table>();

		h1.put(t1.getName(), t1);

		h1.put(t2.getName(), t2);
		h1.put(t3.getName(), t3);
		h1.put(t4.getName(), t4);
		h1.put(t5.getName(), t5);
		h1.put(t6.getName(), t6);
		h1.put(t7.getName(), t7);

		// schema biblioteca
		Table t9 = new Table("livro", new MyArrayList(new Object[] {
				new NumericAttribute("id", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						true, false, false, true),
				new TextAttribute("ISBN", new StringType("VARCHAR",
						Group.STRING, 255l), true, false, false, 255l),
				new TimeAttribute("ano", new DateType("DATE", Group.DATE_TIME,
						"AAAA-MM-DD", "1000-01-01", "9999-12-31"), false,
						false, false),
				new TextAttribute("autor", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, false, 255l),
				new NumericAttribute("edicao", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, false, false, false, false),
				new TextAttribute("editora", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, false, 255l),
				new TextAttribute("titulo", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, false, 255l), }),
				new MyArrayList(new Object[] {}));

		HashMap<String, String> name7 = new HashMap<String, String>();
		name7.put("id_livro", "id");

		Table t8 = new Table("copiadolivro", new MyArrayList(new Object[] {
				new NumericAttribute("id", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						true, false, false, true),
				new NumericAttribute("numeroSequencia", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						true, false, false, false, false),
				new TextAttribute("situacaoDaCopia", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 255l),
				new TextAttribute("tipoDaCopia", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 255l), }),
				new MyArrayList(new Object[] { new Relationship(name7, 0, true,
						t9, false) }));

		Table t11 = new Table("leitor", new MyArrayList(new Object[] {
				new NumericAttribute("id", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						true, false, false, true),
				new NumericAttribute("CPF", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), true,
						false, false, false, false),
				new TextAttribute("matricula", new StringType("VARCHAR",
						Group.STRING, 255l), true, false, false, 255l),
				new TextAttribute("nome", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, false, 255l),
				new NumericAttribute("telefone", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						false, false, false, false), }), new MyArrayList(
				new Object[] {}));

		HashMap<String, String> name8 = new HashMap<String, String>();
		name8.put("id_copiaDoLivro", "id");
		HashMap<String, String> name9 = new HashMap<String, String>();
		name9.put("id_leitor", "id");
		Table t10 = new Table("emprestimo", new MyArrayList(new Object[] {
				new NumericAttribute("id", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						true, false, false, true),
				new TimeAttribute("dataDeEmprestimo", new DateType("DATE",
						Group.DATE_TIME, "AAAA-MM-DD", "1000-01-01",
						"9999-12-31"), false, false, false),
				new TimeAttribute("dataDeEntregaReal", new DateType("DATE",
						Group.DATE_TIME, "AAAA-MM-DD", "1000-01-01",
						"9999-12-31"), false, false, true),
				new TimeAttribute("dataPrevistaDeDevolucao", new DateType(
						"DATE", Group.DATE_TIME, "AAAA-MM-DD", "1000-01-01",
						"9999-12-31"), false, false, false),
				new TextAttribute("situacaoEmprestimo", new StringType(
						"VARCHAR", Group.STRING, 255l), false, false, true,
						255l) }), new MyArrayList(new Object[] {
				new Relationship(name8, 0, true, t8, false),
				new Relationship(name9, 0, true, t11, false) }));

		HashMap<String, String> name10 = new HashMap<String, String>();
		name10.put("id_leitor", "id");
		HashMap<String, String> name11 = new HashMap<String, String>();
		name11.put("id_livro", "id");
		Table t12 = new Table("reserva", new MyArrayList(new Object[] {
				new NumericAttribute("id", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						true, false, false, true),
				new TimeAttribute("dataDeReserva", new DateType("DATE",
						Group.DATE_TIME, "AAAA-MM-DD", "1000-01-01",
						"9999-12-31"), false, false, true),
				new NumericAttribute("numero", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, false, true, false, false) }), new MyArrayList(
				new Object[] { new Relationship(name10, 0, true, t11, false),
						new Relationship(name11, 0, true, t9, false) }));
		HashMap<String, Table> h2 = new HashMap<String, Table>();
		h2.put(t8.getName(), t8);
		h2.put(t9.getName(), t9);
		h2.put(t10.getName(), t10);
		h2.put(t11.getName(), t11);
		h2.put(t12.getName(), t12);
		//

		Table t13 = new Table("usuario", new MyArrayList(new Object[] {
				new NumericAttribute("id", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						true, false, false, true),
				new TextAttribute("login", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 255l),
				new TextAttribute("senha", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 255l), }),
				new MyArrayList(new Object[] {}));

		HashMap<String, String> name12 = new HashMap<String, String>();
		name12.put("id_administrador", "id");
		Table t14 = new Table("administrador",
				new MyArrayList(new Object[] {}), new MyArrayList(
						new Object[] { new Relationship(name12, 0, false, t13,
								true) }));

		HashMap<String, String> name13 = new HashMap<String, String>();
		name13.put("id_responsavel", "id");
		Table t15 = new Table("responsavel", new MyArrayList(new Object[] {}),
				new MyArrayList(new Object[] { new Relationship(name13, 0,
						false, t13, true) }));

		HashMap<String, String> name14 = new HashMap<String, String>();
		name14.put("id_responsavel", "id_responsavel");
		Table t16 = new Table("pesquisa", new MyArrayList(new Object[] {
				new NumericAttribute("id", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						true, false, false, true),
				new NumericAttribute("aberta", new IntegerType("BIT",
						Group.NUMERIC, 1, 0, 1), false, false, false, false,
						false, 0, 0, 0),
				new NumericAttribute("ativa", new IntegerType("BIT",
						Group.NUMERIC, 1, 0, 1), false, false, false, false,
						false, 0, 0, 0),
				new TextAttribute("descricao", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 255l),
				new TextAttribute("nome", new StringType("VARCHAR",
						Group.STRING, 255l), true, false, true, 255l) }),
				new MyArrayList(new Object[] { new Relationship(name14, 0,
						true, t15, false) }));

		HashMap<String, String> name15 = new HashMap<String, String>();
		name15.put("id_pesquisa", "id");
		Table t17 = new Table("grupodequestao", new MyArrayList(new Object[] {
				new NumericAttribute("id", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						true, false, false, true),
				new TextAttribute("nome", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 255l),
				new NumericAttribute("ordem", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, false, false, false, false) }), new MyArrayList(
				new Object[] { new Relationship(name15, 0, true, t16, false) }));

		HashMap<String, String> name16 = new HashMap<String, String>();
		name16.put("id_grupoDeQuestao", "id");

		Table t18 = new Table("questao", new MyArrayList(new Object[] {
				new NumericAttribute("id", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						true, false, false, true),
				new NumericAttribute("numero", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, false, false, false, false),
				new TextAttribute("texto", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 200l) }),
				new MyArrayList(new Object[] { new Relationship(name16, 0,
						true, t17, false) }));

		HashMap<String, String> name17 = new HashMap<String, String>();
		name17.put("id_questao", "id");

		Table t19 = new Table("possivelresposta", new MyArrayList(new Object[] {
				new NumericAttribute("id", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						true, false, false, true),
				new NumericAttribute("numero", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, false, false, false, false),
				new TextAttribute("texto", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 200l) }),
				new MyArrayList(new Object[] { new Relationship(name17, 0,
						false, t18, false) }));

		HashMap<String, String> name18 = new HashMap<String, String>();
		name18.put("id_participante", "id");

		HashMap<String, String> name19 = new HashMap<String, String>();
		name19.put("id_pesquisa", "id");

		Table t20 = new Table("participante", new MyArrayList(
				new Object[] { new NumericAttribute("finalizou",
						new IntegerType("BIT", Group.NUMERIC, 1, 0, 1), false,
						false, false, false, false, 0, 0, 0) }),
				new MyArrayList(new Object[] {
						new Relationship(name18, 0, false, t13, true),
						new Relationship(name19, 0, false, t16, false) }));

		HashMap<String, String> name20 = new HashMap<String, String>();
		name20.put("id_participante", "id_participante");

		HashMap<String, String> name21 = new HashMap<String, String>();
		name21.put("id_possivelResposta", "id");

		HashMap<String, String> name22 = new HashMap<String, String>();
		name22.put("id_questao", "id");
		Table t21 = new Table("resposta", new MyArrayList(
				new Object[] { new NumericAttribute("id", new IntegerType(
						"BIGINT", Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						true, false, false, true) }), new MyArrayList(
				new Object[] { new Relationship(name20, 0, false, t20, false),
						new Relationship(name21, 0, false, t19, false),
						new Relationship(name22, 0, false, t18, false) }));

		HashMap<String, String> name23 = new HashMap<String, String>();
		name23.put("id_pesquisa", "id");

		Table t22 = new Table("campo", new MyArrayList(new Object[] {
				new NumericAttribute("id", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						true, false, false, true),
				new TextAttribute("nome", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 255l),
				new NumericAttribute("numero", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, false, false, false, false),
				new NumericAttribute("obrigatorio", new IntegerType("BIT",
						Group.NUMERIC, 1, 0, 1), false, false, false, false,
						false, 0, 0, 0),
				new NumericAttribute("tipo", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, false, false, false, false) }),
				new MyArrayList(new Object[] { new Relationship(name23, 0,
						false, t16, false) }));

		HashMap<String, String> name24 = new HashMap<String, String>();
		name24.put("id_campo", "id");
		Table t23 = new Table("valorvalido", new MyArrayList(new Object[] {
				new NumericAttribute("id", new IntegerType("BIGINT",
						Group.NUMERIC, 9223372036854775801l,
						-9223372036854775801l, 9223372036854775801l), false,
						true, false, false, true),
				new TextAttribute("valor", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 255l) }),
				new MyArrayList(new Object[] { new Relationship(name24, 0,
						true, t22, false) }));

		HashMap<String, String> name25 = new HashMap<String, String>();
		name25.put("id_campo", "id");
		HashMap<String, String> name26 = new HashMap<String, String>();
		name26.put("id_usuario", "id");
		Table t24 = new Table("informacaodeusuario",
				new MyArrayList(
						new Object[] {
								new NumericAttribute("id", new IntegerType(
										"BIGINT", Group.NUMERIC,
										9223372036854775801l,
										-9223372036854775801l,
										9223372036854775801l), false, true,
										false, false, true),
								new TextAttribute("valor", new StringType(
										"VARCHAR", Group.STRING, 255l), false,
										false, true, 255l) }),
				new MyArrayList(new Object[] {
						new Relationship(name25, 0, true, t22, false),
						new Relationship(name26, 0, false, t13, false) }));

		HashMap<String, Table> h3 = new HashMap<String, Table>();
		h3.put(t13.getName(), t13);
		h3.put(t14.getName(), t14);
		h3.put(t15.getName(), t15);
		h3.put(t16.getName(), t16);
		h3.put(t17.getName(), t17);
		h3.put(t18.getName(), t18);
		h3.put(t19.getName(), t19);
		h3.put(t20.getName(), t20);
		h3.put(t21.getName(), t21);
		h3.put(t22.getName(), t22);
		h3.put(t23.getName(), t23);
		h3.put(t24.getName(), t24);

		Object[][] result = new Object[3][2];

		// contem associaçoes reflexivas
		result[0] = new Object[] { "loja", h1 };
		result[1] = new Object[] { "biblioteca", h2 };
		result[2] = new Object[] { "anyquestion", h3 };

		return result;
	}

	@Test(dataProvider = "dependenceDiscoverTest", enabled = enabled)
	public void dependenceDiscoverTest(String schema,
			HashMap<String, Table> expected) throws IOException {

		HashMap<String, Table> actual = null;

		try {
			InitialValues.initValues("mysql");
			Connection connection = ConnectionFactory.getConnection(
					"com.mysql.jdbc.Driver", "localhost", "INFORMATION_SCHEMA",
					"root", TestConstants.password, "mysql");
			PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
					schema, connection);

			try {
				actual = persistenceDiscoverer.dependenceDiscoverer();
			} catch (NotKnowTypeException e) {

				e.printStackTrace();
			}

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(actual.size(), expected.size());
		for (Map.Entry<String, Table> tables : actual.entrySet()) {
			String key = tables.getKey();
			Assert.assertEquals(actual.get(key), expected.get(key));
		}

	}

	@SuppressWarnings("unchecked")
	@Test(enabled = enabled)
	public void cliclicDiscoverTest() throws IOException {

		Table t25 = new Table("table1", new MyArrayList(
				new Object[] { new NumericAttribute("idtable1",
						new IntegerType("INT", Group.NUMERIC, 2147483647l,
								-2147483648l, 4294967296l), false, true, false,
						false, false) }), new MyArrayList(new Object[] {}));

		HashMap<String, String> name = new HashMap<String, String>();
		name.put("A_idA", "idA");
		Table t27 = new Table("c", new MyArrayList(new Object[] {
				new NumericAttribute("idC", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, true, false, false, false),
				new TextAttribute("nome", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 45l) }),
				new MyArrayList(new Object[] { new Relationship(name, 0, false,
						new Table("a", null, null), false) }));

		HashMap<String, String> name1 = new HashMap<String, String>();
		name1.put("C_idC", "idC");

		HashMap<String, String> name2 = new HashMap<String, String>();
		name2.put("table1_idtable1", "idtable1");
		Table t26 = new Table("b", new MyArrayList(new Object[] {
				new NumericAttribute("idB", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, true, false, false, false),
				new TextAttribute("nome", new StringType("VARCHAR",
						Group.STRING, 255l), false, false, true, 45l) }),
				new MyArrayList(new Object[] {
						new Relationship(name1, 0, false, new Table("c", null,
								null), false),
						new Relationship(name2, 0, false, new Table("table1",
								null, null), false) }));

		HashMap<String, String> name3 = new HashMap<String, String>();
		name3.put("B_idB", "idB");
		Table t28 = new Table("a", new MyArrayList(new Object[] {
				new NumericAttribute("idA", new IntegerType("INT",
						Group.NUMERIC, 2147483647l, -2147483648l, 4294967296l),
						false, true, false, false, false),
				new NumericAttribute("nome", new RealType("DOUBLE",
						Group.NUMERIC, 2.2250738596294014E80d,
						-2.2250738596294014E80d), false, false, true, false,
						false), }), new MyArrayList(
				new Object[] { new Relationship(name3, 0, false, new Table("b",
						null, null), false) }));

		t27.getRelationship().iterator().next().setForeignKey(t28);

		HashMap<String, Table> h4 = new HashMap<String, Table>();
		h4.put(t25.getName(), t25);
		h4.put(t26.getName(), t26);
		h4.put(t27.getName(), t27);
		h4.put(t28.getName(), t28);

		HashMap<String, Table> actual = null;

		try {
			InitialValues.initValues("mysql");
			Connection connection = ConnectionFactory.getConnection(
					"com.mysql.jdbc.Driver", TestConstants.host,
					"INFORMATION_SCHEMA", TestConstants.user,
					TestConstants.password, "mysql");
			PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
					"mydb", connection);

			try {
				actual = persistenceDiscoverer.dependenceDiscoverer();
			} catch (NotKnowTypeException e) {

				e.printStackTrace();
			}

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(actual.size(), h4.size());
		for (Map.Entry<String, Table> tables : actual.entrySet()) {
			String key = tables.getKey();
			Assert.assertEquals(actual.get(key).getName(), h4.get(key)
					.getName());
			Assert.assertEquals(actual.get(key).getPrimitiveAtributes(), h4
					.get(key).getPrimitiveAtributes());
			Assert.assertEquals(actual.get(key).getRelationship().size(), h4
					.get(key).getRelationship().size());
		}

	}
}
