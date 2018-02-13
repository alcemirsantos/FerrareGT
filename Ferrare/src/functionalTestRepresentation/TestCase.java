package functionalTestRepresentation;

import java.util.LinkedList;

/**
 * Classe que representa um Caso de Teste
 * @author Ismayle de Sousa Santos
 */
public class TestCase {

	private TestProcedure testProcedure;

	private LinkedList<Input> inputs;

	private Output expectedOutput;

	/**
	 * Construtor Completo da Classe
	 * @param battery Bateria de testes ao qual o Caso de Teste esta ligado
	 * @param testProcedure Procedimento de Teste associado ao Caso de Teste
	 * @param inputs Entradas do Caso de Teste
	 * @param expectedOutput Saida esperada do Caso de Teste
	 */
	public TestCase(TestBattery battery, TestProcedure testProcedure,
			LinkedList<Input> inputs, Output expectedOutput) {
		setTestProcedure(testProcedure);
		setInputs(inputs);
		setExpectedOutput(expectedOutput);		
	}

	/**
	 * Construtor da Classe
	 * @param battery Bateria de testes ao qual o Caso de Teste esta ligado
	 */
	public TestCase(TestBattery battery) {
		this(battery, null, new LinkedList<Input>(), null);
	}

	/**
	 * Construtor da Classe
	 */
	public TestCase() {
		this(null);
	}
	
	/**
	 * Método que retorna a saída esperada do Caso de Teste
	 * @return Saída esperada
	 */
	public Output getExpectedOutput() {
		return expectedOutput;
	}

	/**
	 * Método para alterar a saida esperada do Caso de Teste
	 * @param expectedOutput Nova saida esperada
	 */
	public void setExpectedOutput(Output expectedOutput) {
		this.expectedOutput = expectedOutput;
		if (expectedOutput != null) {
			if (expectedOutput.getTestCase() == null
					|| !expectedOutput.getTestCase().equals(this)) {
				expectedOutput.setTestCase(this);
			}
		}
	}

	/**
	 * Método que retorna a lista de Entradas do Caso de Teste
	 * @return Lista de Entradas
	 */
	public LinkedList<Input> getInputs() {
		return inputs;
	}

	/**
	 * Método para alterar a lista de Entradas do Caso de Teste
	 * @param inputs Nova lista de Entradas
	 */
	public void setInputs(LinkedList<Input> inputs) {
		this.inputs = inputs;
		for (Input input : inputs) {
			if (input.getTestCase() == null
					|| !input.getTestCase().equals(this)) {
				input.setTestCase(this);
			}
		}
	}

	/**
	 * Método para adicionar uma entrada a lista de entradas do Caso de Teste
	 * @param input Entrada a ser adicionada
	 */
	public void addInput(Input input) {
		inputs.add(input);
		if (input.getTestCase() == null || !input.getTestCase().equals(this)) {
			input.setTestCase(this);
		}
	}

	/**
	 * Método para remover uma entrada da lista de entradas do Caso de Teste
	 * @param input entrada a ser removida
	 */
	public void removeInput(Input input) {
		inputs.remove(input);		
	}

	/**
	 * Método que retorna o Procedimento de Teste do Caso de Teste
	 * @return Procedimento de teste
	 */
	public TestProcedure getTestProcedure() {
		return testProcedure;
	}

	/**
	 * Método para alterar o Procedimento de Teste associado ao Caso de Teste
	 * @param testProcedure Novo Procedimento de Teste
	 */
	public void setTestProcedure(TestProcedure testProcedure) {
		this.testProcedure = testProcedure;
		if (getTestProcedure() != null) {
			if (testProcedure.getTestCase() == null
					|| !testProcedure.getTestCase().equals(this)) {
				testProcedure.setTestCase(this);
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof TestCase)) {
			return false;
		} else {
			TestCase testCase = (TestCase) obj;
			boolean result = true;			
			if (testCase.getExpectedOutput() != null) {
				result &= testCase.getExpectedOutput().equals(
						this.getExpectedOutput());
			} else {
				result &= this.getExpectedOutput() == null;
			}
			if (testCase.getTestProcedure() != null) {
				result &= testCase.getTestProcedure().equals(
						this.getTestProcedure());
			} else {
				result &= this.getTestProcedure() == null;
			}
			result &= testCase.getInputs().equals(this.getInputs());
			return result;
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("\nTest Procedure\n");
		buffer.append(testProcedure);

		buffer.append("\nInputs:");	
		
		if (inputs.size() == 0)
			buffer.append(" null");
		
		for (Input input : inputs) {
			buffer.append("\n");
			buffer.append(input);			
		}

		buffer.append("\nOutput: ");
		buffer.append(expectedOutput);

		return buffer.toString();
	}
}