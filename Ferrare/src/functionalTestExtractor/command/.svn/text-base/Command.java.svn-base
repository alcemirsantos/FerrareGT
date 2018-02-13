package functionalTestExtractor.command;

/** 
 * Classe que Representa um Comando 
 * @author Ismayle de Sousa Santos
 */

public class Command {	
	private CommandType type;
	private String target;
	private String value;

	/**
	 * Método para pegar o tipo do Comando
	 * @return CommandType - Tipo do Comando
	 */
	public CommandType getType() {
		return type;
	}

	/**
	 * Método para alterar o tipo do Comando
	 * @param type Novo tipo
	 */
	public void setType(CommandType type) {
		this.type = type;
	}
	
	/**
	 * Método para pegar o alvo do Comando
	 * @return String - Alvo do Comando
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Método para alterar o alvo do Comando
	 * @param target Novo alvo
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * Método para pegar o Valor do Comando
	 * @return String - Valor do Comando
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Método para alterar o valor do Comando
	 * @param value Novo valor 
	 */
	
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * Construtor do Comando
	 * @param type Tipo do Comando
	 * @param target Alvo do Comando
	 * @param value Valor do Comando
	 */
	public Command(CommandType type, String target, String value) {
		this.type = type;
		this.target = target;
		this.value = value;
	}

	@Override	
	public String toString() {
		return String.format("Tipo: %s\nAlvo: %s\nValor: %s", type, target
				.equals("") ? "vazio" : target, value.equals("") ? "vazio"
				: value);
	}
}
