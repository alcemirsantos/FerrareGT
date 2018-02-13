package functionalTestRepresentation;


import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um Procedimento de Teste
* @author Ismayle de Sousa Santos
*/
public class TestProcedure {

	public static final String GET = "GET";

	public static final String POST = "POST";

	public static final int DEFAULT_PORT = 80;

	private String URL;

	private String path;

	private String method;

	private TestCase testCase;

	private List<Field> fields;
	
	private int port;

	/**
	 * Construtor Completo da Classe
	 * @param url URL base da p�gina acessada
	 * @param path Path da p�gina acessada 
	 * @param method M�todo de envio de dados
	 * @param port Porta para envio dos dados
	 * @param testCase Caso de Teste associado ao Procedimento de Teste
	 */
	public TestProcedure(String url, String path, String method, int port,
			TestCase testCase) {
		setURL(url);
		setPath(path);
		setMethod(method);
		setPort(port);
		setTestCase(testCase);
		setFields(new ArrayList<Field>());
	}

	/**
	 * Construtor da Classe
	 * @param testCase Caso de Teste associado ao Procedimento de Teste
	 */
	public TestProcedure(TestCase testCase) {
		this(null, null, null, DEFAULT_PORT, testCase);
	}

	/**
	 * Construtor da Classe
	 * @param url URL base da p�gina acessada
	 * @param path Path da p�gina acessada 
	 * @param method M�todo de envio de dados
	 * @param port Porta para envio dos dados
	 */
	public TestProcedure(String url, String path, String method, int port) {
		this(url, path, method, port, null);
	}

	/**
	 *Construtor da Classe 
	 */
	public TestProcedure() {
		this(null, null, null, DEFAULT_PORT, null);
	}
	/**
	 * M�todo que retorna a porta usada
	 * @return Porta usada no Procedimento de Teste
	 */
	public int getPort() {
		return port;
	}

	/**
	 * M�todo para alterar a porta 
	 * @param port Nova porta
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 *M�todo que retorna a URL do Procedimento de Teste 
	 * @return A URL usada pelo Procedimento de Teste
	 */
	public String getURL() {
		return URL;
	}
	
	
	/**
	 * M�todo para alterar a URL do Procedimento de Teste
	 * @param URL Nova URL
	 */
	public void setURL(String URL) {
		this.URL = URL;
	}

	/**
	 * M�todo que retorna o m�todo do Procedimento de Teste
	 * @return M�todo do Procedimento de Teste
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * M�todo para alterar o m�todo do Procedimento de Teste
	 * @param method Novo m�todo
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * M�todo que retorna o Caso de Teste do Procedimento de Teste
	 * @return o TestCase
	 */
	public TestCase getTestCase() {
		return testCase;
	}

	/**
	 * M�todo para alterar o Caso de Teste do Procedimento de Teste
	 * @param testCase Novo Caso de Teste
	 */
	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	/**
	 * M�todo que retorna a lista de campos do Procedimento de Teste
	 * @return Lista de campos
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * M�todo para alterar a lista de campos do Procedimento de Teste
	 * @param fields Nova lista de campos
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
		for (Field field : fields) {
			if (field.getTestProcedure() == null || !field.getTestProcedure().equals(this)) {
				field.setTestProcedure(this);
			}
		}
	}

	/**
	 * M�todo para adicionar um campo a lista de campos do Procedimento de Teste
	 * @param field Campo para ser adicionado
	 */
	public void addField(Field field) {
		fields.add(field);
		if (field != null) {
			if (field.getTestProcedure() == null
					|| !field.getTestProcedure().equals(this)) {
				field.setTestProcedure(this);
			}
		}
	}

	/**
	 * M�todo para remover um campo da lista de campos do Procedimento de Teste
	 * @param field Campo a ser removido da lista de campos
	 */
	public void removeField(Field field) {
		fields.remove(field);
	}

	/**
	 * M�todo que retorna o path usado no Procedimento de Teste
	 * @return Path do TestProcedure
	 */
	public String getPath() {
		return path;
	}

	/**
	 * M�todo para alterar o path usado no Procedimento de Teste
	 * @param path Novo path
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * M�todo para verificar se um campo pertence a lista de campos do Procedimento de Teste 
	 * @param fieldName Nome do campo a ser procurado 
	 * @return A posi��o do campo, caso este perten�a a lista, e -1 caso ele n�o perten�a a lista
	 */
	public int containFieldName(String fieldName){
		int	i = 0;
		for(Field field : fields){
			if (field.getName().equals(fieldName))
				return i;
			i++;
		}
		return -1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof TestProcedure)) {
			return false;
		} else {
			TestProcedure testProcedure = (TestProcedure) obj;
			boolean result = testProcedure.getFields().equals(this.getFields());
			if (testProcedure.getMethod() != null) {
				result &= testProcedure.getMethod().equals(this.getMethod());
			} else {
				result &= this.getMethod() == null;
			}
			if (testProcedure.getURL() != null) {
				result &= testProcedure.getURL().equals(this.getURL());
			} else {
				result &= this.getURL() == null;
			}
			if (testProcedure.getPath() != null) {
				result &= testProcedure.getPath().equals(this.getPath());
			} else {
				result &= this.getPath() == null;
			}
			return result;
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("URL: ");
		buffer.append(URL);

		buffer.append("\nPath: ");
		buffer.append(path);

		buffer.append("\nMethod: ");
		buffer.append(method);

		buffer.append("\nPort: ");
		buffer.append(port);

		return buffer.toString();
	}
	
}