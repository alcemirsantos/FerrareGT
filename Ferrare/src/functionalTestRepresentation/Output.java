package functionalTestRepresentation;

/**
 * Classe que representa a sa�da esperada
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
	 * M�todo que retorna a sa�da esperada
	 * @return Sa�da Esperada
	 */
	public String getExpectedOutput() {
		return expectedOutput;
	}

	/**
	 * M�todo para alterar a sa�da esperada
	 * @param expectedOutput Nova sa�da esperada
	 */
	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	/**
	 * M�todo que retorna o Caso de Teste associado
	 * @return Caso de Teste associado
	 */
	public TestCase getTestCase() {
		return testCase;
	}

	/**
	 * M�todo para altera o Caso de Teste associado 
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