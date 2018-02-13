package functionalTestRepresentation.requirements;

/**
 * Classe que representa um requisito de tamanho mínimo
 * @author Ismayle de Sousa Santos
 */
public class MinSizeRequirement extends Requirement<Integer> {
	/**
	 * Construtor completo
	 * @param value valor do tamanho mínimo
	 */
	public MinSizeRequirement(Integer value) {
		setName(Constants.MIN_SIZE);
		setType(Integer.class);
		setValue(value);
	}

	public MinSizeRequirement() {
		this(0);
	}

	@Override
	public String toString() {
		return "Minimun size = " + getValue();
	}
}
