package functionalTestRepresentation;


import java.util.LinkedList;

/**
 * Classe que representa a Bateria de Testes
 * @author Ismayle de Sousa Santos
 */
public class TestBattery {

	private LinkedList<TestCase> testCases;

	/**
	 * Construtor Completo da Classe
	 * @param testCases Lista de Casos de Teste
	 */
	public TestBattery(LinkedList<TestCase> testCases) {
		setTestCases(testCases);
	}

	/**
	 * Construtor da Classe
	 */
	public TestBattery() {
		this(new LinkedList<TestCase>());
	}

	/**
	 * Método que retorna a lista de Casos de Teste
	 * @return Lista de Casos de Teste
	 */
	public LinkedList<TestCase> getTestCases() {
		return testCases;
	}
	
	/**
	 * Método para alterar a lista de Casos de Teste
	 * @param testCases Nova lista de Casos de Teste
	 */
	public void setTestCases(LinkedList<TestCase> testCases) {
		this.testCases = testCases;
	}

	/**
	 * Método adicionar um Caso de Teste a Bateria de Testes
	 * @param testCase Caso de Teste a ser adicionado
	 */
	public void addTestCase(TestCase testCase) {
		testCases.add(testCase);
	}

	/**
	 * Método para remover um Caso de Teste da Bateria de Testes
	 * @param testCase Caso de Teste a ser removido
	 */
	public void removeTestCase(TestCase testCase) {
		testCases.remove(testCase);
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		for (TestCase testCase : testCases) {
			buffer.append(testCase);
			buffer.append("\n");
		}

		return buffer.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof TestBattery)) {
			return false;
		} else {
			TestBattery testBattery = (TestBattery) obj;
			boolean result = testBattery.getTestCases().equals(
					this.getTestCases());
			return result;
		}
	}

}