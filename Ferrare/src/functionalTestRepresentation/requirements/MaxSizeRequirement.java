package functionalTestRepresentation.requirements;

/**
 * Método para representar um requisito de tamanho máximo
 * @author Ismayle de Sousa Santos
 */

public class MaxSizeRequirement extends Requirement<Integer> {
	/**
	 * Construtor Completo
	 * @param value valor do tamanho máximo
	 */
	public MaxSizeRequirement(Integer value) {
		setName(Constants.MAX_SIZE);
		setType(Integer.class);
		setValue(value);
	}

	public MaxSizeRequirement() {
		this(0);
	}

	@Override
	public String toString() {
		return "Maximun size = " + getValue();
	}
}
