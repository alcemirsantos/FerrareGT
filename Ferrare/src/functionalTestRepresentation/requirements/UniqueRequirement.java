package functionalTestRepresentation.requirements;

/**
 * Classe representa um requisito de unicidade
 * @author Ismayle de Sousa Santos
 */
public class UniqueRequirement extends Requirement<Boolean> {
	/**
	 *Construtor Completo
	 * @param value boolean que indica se é único ou não
	 */
	public UniqueRequirement(Boolean value) {
		setName(Constants.UNIQUE);
		setType(Boolean.class);
		setValue(value);
	}

	public UniqueRequirement() {
		this(true);
	}

	@Override
	public String toString() {
		return "Uniqueness = " + getValue();
	}
}
