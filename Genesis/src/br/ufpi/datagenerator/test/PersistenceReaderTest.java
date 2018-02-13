/**
 * 
 */
package br.ufpi.datagenerator.test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import br.ufpi.datagenerator.domain.Table;
import br.ufpi.datagenerator.domain.connectors.ConnectionFactory;
import br.ufpi.datagenerator.initialconfiguration.InitialValues;
import br.ufpi.datagenerator.tablescans.PersistenceDiscoverer;
import br.ufpi.datagenerator.tablescans.PersistenceReader;
import br.ufpi.datagenerator.util.NotKnowTypeException;
import br.ufpi.datagenerator.util.SqlScript;

/**
 * @author iure
 * 
 */
// os testes são referentes aos bancos biblioteca, banco e loja utilizando mysql
@Test(testName = "PersistenceDiscovererTest")
public class PersistenceReaderTest {

	private final boolean enabled = true;

	@BeforeClass
	public void dbcreator() {
		try {

			File dir1 = new File(".");
			SqlScript sqlScript = new SqlScript(dir1
					+ "\\test\\mysql\\PersistenceReaderTest.sql");
			sqlScript.loadScript();
			sqlScript.execute();

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@DataProvider(name = "actualStateTest")
	public Object[][] actualStateDataProvider() {

		// teste com com banco biblioteca;
		HashMap<String, ArrayList<HashMap<String, String>>> actualState = new HashMap<String, ArrayList<HashMap<String, String>>>();

		ArrayList<HashMap<String, String>> lines0 = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> hashMap0 = new HashMap<String, String>();
		hashMap0.put("id", "1");
		hashMap0.put("tipoDaCopia", "emprestimo");
		hashMap0.put("numeroSequencia", "1");
		hashMap0.put("situacaoDaCopia", "Disponivel");
		hashMap0.put("id_livro", "1");
		lines0.add(hashMap0);
		actualState.put("copiadolivro", lines0);

		ArrayList<HashMap<String, String>> lines1 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hashMap1 = new HashMap<String, String>();
		hashMap1.put("id", "1");
		hashMap1.put("titulo", "Fundamentos de Bancos de Dados");
		hashMap1.put("autor", "Alves, William Pereira");
		hashMap1.put("edicao", "1");
		hashMap1.put("ISBN", "8571949972");
		hashMap1.put("ano", "2004-01-01");
		hashMap1.put("editora", "Érica");
		lines1.add(hashMap1);
		actualState.put("livro", lines1);

		ArrayList<HashMap<String, String>> lines2 = new ArrayList<HashMap<String, String>>();
		actualState.put("emprestimo", lines2);

		ArrayList<HashMap<String, String>> lines3 = new ArrayList<HashMap<String, String>>();
		actualState.put("reserva", lines3);

		ArrayList<HashMap<String, String>> lines4 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hashMap2 = new HashMap<String, String>();

		hashMap2.put("id", "1");
		hashMap2.put("CPF", "42086364083");
		hashMap2.put("telefone", "9999999999");
		hashMap2.put("nome", "John Hunter");
		hashMap2.put("matricula", "05n10169");
		lines4.add(hashMap2);
		actualState.put("leitor", lines4);

		HashMap<String, ArrayList<HashMap<String, String>>> actualState2 = new HashMap<String, ArrayList<HashMap<String, String>>>();

		ArrayList<HashMap<String, String>> lines5 = new ArrayList<HashMap<String, String>>();
		actualState2.put("resposta", lines5);

		ArrayList<HashMap<String, String>> lines6 = new ArrayList<HashMap<String, String>>();
		actualState2.put("campo", lines6);

		ArrayList<HashMap<String, String>> lines7 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hashMap5 = new HashMap<String, String>();
		hashMap5.put("id", "1");
		hashMap5.put("login", "a@a.com");
		hashMap5.put("senha", "123456");
		lines7.add(hashMap5);

		HashMap<String, String> hashMap6 = new HashMap<String, String>();
		hashMap6.put("id", "2");
		hashMap6.put("login", "b@b.com");
		hashMap6.put("senha", "123456");
		lines7.add(hashMap6);
		actualState2.put("usuario", lines7);

		ArrayList<HashMap<String, String>> lines8 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hashMap7 = new HashMap<String, String>();
		hashMap7.put("id", "1");
		hashMap7.put("id_grupoDeQuestao", "1");
		hashMap7.put("texto", "o nome da tela");
		hashMap7.put("numero", "1");
		lines8.add(hashMap7);
		actualState2.put("questao", lines8);

		ArrayList<HashMap<String, String>> lines9 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hashMap8 = new HashMap<String, String>();
		hashMap8.put("id_responsavel", "2");
		lines9.add(hashMap8);
		actualState2.put("responsavel", lines9);

		ArrayList<HashMap<String, String>> lines10 = new ArrayList<HashMap<String, String>>();
		actualState2.put("informacaodeusuario", lines10);

		ArrayList<HashMap<String, String>> lines11 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hashMap9 = new HashMap<String, String>();
		hashMap9.put("id_administrador", "1");
		lines11.add(hashMap9);
		actualState2.put("administrador", lines11);

		ArrayList<HashMap<String, String>> lines12 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hashMap10 = new HashMap<String, String>();
		hashMap10.put("id", "1");
		hashMap10.put("ordem", "1");
		hashMap10.put("id_pesquisa", "1");
		hashMap10.put("nome", "matematica");
		lines12.add(hashMap10);
		actualState2.put("grupodequestao", lines12);

		ArrayList<HashMap<String, String>> lines13 = new ArrayList<HashMap<String, String>>();
		actualState2.put("participante", lines13);

		ArrayList<HashMap<String, String>> lines14 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hashMap11 = new HashMap<String, String>();
		hashMap11.put("id", "1");
		hashMap11.put("texto", "resposta 1");
		hashMap11.put("id_questao", "1");
		hashMap11.put("numero", "1");
		lines14.add(hashMap11);
		HashMap<String, String> hashMap12 = new HashMap<String, String>();
		hashMap12.put("id", "2");
		hashMap12.put("texto", "resposta 2");
		hashMap12.put("id_questao", "1");
		hashMap12.put("numero", "1");
		lines14.add(hashMap12);
		actualState2.put("possivelresposta", lines14);

		ArrayList<HashMap<String, String>> lines15 = new ArrayList<HashMap<String, String>>();
		actualState2.put("valorvalido", lines15);

		ArrayList<HashMap<String, String>> lines16 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hashMap13 = new HashMap<String, String>();
		hashMap13.put("id", "1");
		hashMap13.put("ativa", "0");
		hashMap13.put("id_responsavel", "2");
		hashMap13.put("nome", "pesquisa");
		hashMap13.put("descricao", "pesquisa da ufpi");
		hashMap13.put("aberta", "1");
		lines16.add(hashMap13);
		actualState2.put("pesquisa", lines16);

		Object[][] result = new Object[2][2];

		result[0] = new Object[] { "biblioteca", actualState };

		result[1] = new Object[] { "anyquestion", actualState2 };

		return result;
	}

	@Test(enabled = enabled, groups = "developed", dataProvider = "actualStateTest")
	public void actualStateTest(String schema,
			HashMap<String, ArrayList<HashMap<String, String>>> expected)
			throws SQLException, NotKnowTypeException, IOException {

		InitialValues.initValues("mysql");
		Connection connection = ConnectionFactory.getConnection(
				"com.mysql.jdbc.Driver", TestConstants.host,
				"INFORMATION_SCHEMA", TestConstants.user,
				TestConstants.password, "mysql");
		PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
				schema, connection);
		//
		HashMap<String, Table> tables1 = persistenceDiscoverer
				.dependenceDiscoverer();

		connection.close();

		connection = ConnectionFactory.getConnection("com.mysql.jdbc.Driver",
				TestConstants.host, schema, TestConstants.user,
				TestConstants.password, "mysql");

		PersistenceReader persistenceReader = new PersistenceReader();

		HashMap<String, ArrayList<HashMap<String, String>>> actualState = persistenceReader
				.actualState(tables1, connection);

		Assert.assertEquals(actualState, expected);
	}

}
