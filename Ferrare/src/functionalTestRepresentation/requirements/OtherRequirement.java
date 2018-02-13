package functionalTestRepresentation.requirements;

/**
 * Método para representar um requisito de tamanho máximo
 * @author Ismayle de Sousa Santos
 */

public class OtherRequirement extends Requirement<String> {
	/**
	 * Construtor Completo
	 * @param value valor do tamanho máximo
	 */
	public OtherRequirement(String value) {
		setName(Constants.OTHER);
		setType(String.class);
		setValue(value);
	}

	public OtherRequirement() {
		this("");
	}

	@Override
	public String toString() {
		return "Value = " + getValue();
	}
}
