package functionalTestRepresentation.requirements;

/**
 * Classe parametrizada para os requisitos
 * @author Ismayle de Sousa Santos
 */
public class Requirement<T> {

	public Requirement() {
	}

	private Class<? extends T> type;

	private T value;

	private String name;

	/**
	 * M�todo para pegar o nome do requisito
	 * @return nome do requisito
	 */
	public String getName() {
		return name;
	}

	/**
	 * M�todo para alterar o nome do requisito
	 * @param name novo nome
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * M�todo para  pegar o tipo de requisito
	 * @return tipo do requisito
	 */
	public Class<? extends T> getType() {
		return type;
	}

	/**
	 * M�todo para alterar o tipo do requisito
	 * @param type novo tipo
	 */
	public void setType(Class<? extends T> type) {
		this.type = type;
	}

	/**
	 * M�todo para pegar o valor do requisito
	 * @return valor do requisito
	 */
	public T getValue() {
		return value;
	}

	/**
	 * M�todo para alterar o valor do requisito
	 * @param value novo valor
	 */
	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object arg0) {
		return arg0.getClass().equals(this.getClass());
	}
}
