package functionalTestRepresentation;

/**
 * Classe que representa uma Entrada no Caso de Teste
 * @author Ismayle de Sousa Santos 
 */
public class Input {

	private String value;

	private TestCase testCase;

	private Field field;

	/**
	 * Construtor Completo da Classe
	 * @param value Valor da Entrada
	 * @param testCase Caso de Teste ao qual a Entrada esta associada
	 * @param field Campo ao qual pertence a Entrada
	 */
	public Input(String value, TestCase testCase, Field field) {
		setValue(value);
		setTestCase(testCase);
		setField(field);
	}

	/**
	 * Construtor da Classe
	 * @param value Valor da Entrada
	 * @param field Campo ao qual pertence a Entrada
	 */
	public Input(String value, Field field) {
		this(value, null, field);
	}

	/**
	 * Construtor da Classe
	 * @param value Valor da Entrada
	 */
	public Input(String value) {
		this(value, null, null);
	}

	/**
	 * Construtor da Classe
	 */
	public Input() {
		this(null);
	}
	
	/**
	 * Retorna o valor da Entrada 
	 * @return Valor da Entrada
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Altera o valor da Entrada
	 * @param value Novo valor da Entrada
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Retorna o Caso de Teste ao qual a Entrada esta associada
	 * @return Caso de Teste
	 */
	public TestCase getTestCase() {
		return testCase;
	}

	/**
	 * Altera o Caso de Teste ao qual a Entrada esta associada
	 * @param testcase Novo Caso de Teste
	 */
	public void setTestCase(TestCase testcase) {
		this.testCase = testcase;
	}

	/**
	 * Retorna o Campo ao qual pertence a Entrada
	 * @return Campo
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Altera o Campo ao qual pertence a Entrada
	 * @param field Novo Campo
	 */
	public void setField(Field field) {
		this.field = field;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Input)) {
			return false;
		} else {
			Input input = (Input) obj;
			boolean result = input.getValue().equals(this.getValue());
			if (input.getField() != null) {
				result &= input.getField().equals(this.getField());
			} else {
				result &= this.getField() == null;
			}
			return result;
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(" ");
		buffer.append(field);

		buffer.append("\n Value: ");
		buffer.append(value);

		return buffer.toString();
	}
}