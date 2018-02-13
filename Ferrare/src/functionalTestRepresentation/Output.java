package functionalTestRepresentation;

/**
 * Classe que representa a saída esperada
 * @author Ismayle de Sousa Santos
 */
public class Output {

	private String expectedOutput;

	private TestCase testCase;

	/**
	 * Construtor completo da Classe
	 * @param expectedOutput Resposta esperada
	 * @param testCase TestCase ao qual pertence a Output
	 */
	public Output(String expectedOutput, TestCase testCase) {
		setExpectedOutput(expectedOutput);
		setTestCase(testCase);
	}

	/**
	 * Construtor da Classe
	 * @param expectedOutput Resposta esperada
	 */
	public Output(String expectedOutput) {
		this(expectedOutput, null);
	}

	/**
	 * Construtor da Classe
	 */
	public Output() {
		this("");
	}
	
	/**
	 * Método que retorna a saída esperada
	 * @return Saída Esperada
	 */
	public String getExpectedOutput() {
		return expectedOutput;
	}

	/**
	 * Método para alterar a saída esperada
	 * @param expectedOutput Nova saída esperada
	 */
	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	/**
	 * Método que retorna o Caso de Teste associado
	 * @return Caso de Teste associado
	 */
	public TestCase getTestCase() {
		return testCase;
	}

	/**
	 * Método para altera o Caso de Teste associado 
	 * @param testCase Novo Caso de Teste
	 */
	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;		
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Output)) {
			return false;
		} else {
			Output output = (Output) obj;
			boolean result;
			if (output.getExpectedOutput() != null) {
				result = output.getExpectedOutput().equals(
						this.getExpectedOutput());
			} else {
				result = this.getExpectedOutput() == null;
			}
			return result;
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("\n Expected Output: ");
		buffer.append(expectedOutput);

		return buffer.toString();
	}
}