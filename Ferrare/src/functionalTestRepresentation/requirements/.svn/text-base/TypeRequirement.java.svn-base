package functionalTestRepresentation.requirements;

/**
 * Classe que representa um requisito de tipo
 * @author Ismayle de Sousa Santos
 */
public class TypeRequirement extends Requirement<String> {
	public static final String INTEGER = "INTEGER";

	public static final String FLOAT = "FLOAT";

	public static final String BOOLEAN = "BOOLEAN";

	public static final String STRING = "STRING";

	public static final String DATE = "DATE";

	/**
	 * Construtor Completo
	 * @param type tipo do requisito
	 */
	public TypeRequirement(String type) {
		setName(Constants.TYPE);
		setType(String.class);
		setValue(type);
	}

	public TypeRequirement() {
		this(STRING);
	}

	@Override
	public String toString() {
		return "Type = " + getValue();
	}
}
