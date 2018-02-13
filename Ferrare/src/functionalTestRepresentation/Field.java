package functionalTestRepresentation;

import java.util.ArrayList;
import java.util.List;
import functionalTestRepresentation.requirements.Requirement;


/**
 * Classe que representa um Campo da página testada 
 * @author Ismayle de Sousa Santos 
 */
public class Field {

	private String name;

	private TestProcedure testProcedure;

	private List<Input> inputs;

	private Output output;

	private boolean outputField;	
	
	private List<Requirement<?>> requirements;
	
	private String referenceToCSV = "";

	/**
	 * Contrutor Completo da Classe
	 * @param name Nome do Campo
	 * @param testProcedure Procedimento de Teste associado ao Campo
	 * @param inputs Lista de Entradas associada ao Campo
	 * @param output Saída Esperada associada ao Campo
	 * @param outputField True se é um campo para entrada de dados; False se é um campo para saída de dados
	 */
	public Field(String name, TestProcedure testProcedure, List<Input> inputs,
			Output output, boolean outputField) {
		setName(name);
		setTestProcedure(testProcedure);
		setInputs(inputs);
		setOutput(output);		
		setOutputField(outputField);
		setRequirements(new ArrayList<Requirement<?>>());
	}

	/**
	 * Contrutor da Classe
	 * @param name Nome do Campo
	 * @param testProcedure Procedimento de Teste associado ao Campo
	 */
	public Field(String name, TestProcedure testProcedure) {
		this(name, testProcedure, new ArrayList<Input>(), null, false);
	}

	/**
	 * Contrutor da Classe
	 * @param name Nome do Campo
	 * @param inputs Lista de Entradas associada ao Campo
	 * @param output Saída Esperada associada ao Campo
	 * @param outputField True se é um campo para entrada de dados; False se é um campo para saída de dados
	 */
	public Field(String name, List<Input> inputs, Output output, boolean outputField) {
		this(name, null, inputs, output, outputField);
	}

	/**
	 * Contrutor da Classe
	 * @param name Nome do Campo
	 */
	public Field(String name) {
		this(name, null, new ArrayList<Input>(), null, false);
	}

	/**
	 *Contrutor da Classe
	 */
	public Field() {
		this("");
	}

	/**
	 * Retorna o Procedimento de Teste ao qual o Campo pertence
	 * @return Procedimento de Teste associado ao Field
	 */
	public TestProcedure getTestProcedure() {
		return testProcedure;
	}
	
	/**
	 * Alterar o Procedimento de Teste ao qual o Campo pertence
	 * @param testProcedure Novo Procedimento de Teste
	 */
	public void setTestProcedure(TestProcedure testProcedure) {
		this.testProcedure = testProcedure;
	}

	/**
	 *Retorna o nome do Campo 
	 * @return Nome do Campo
	 */
	public String getName() {
		return name;
	}

	/**
	 * Altera o Nome do Campo
	 * @param name Novo nome
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Retorna a lista de Entradas associada ao Campo
	 * @return Lista de Entradas
	 */
	public List<Input> getInputs() {
		return inputs;
	}

	/**
	 * Altera a lista de Entradas associada ao Campo
	 * @param inputs Lista de Entradas
	 */
	public void setInputs(List<Input> inputs) {
		this.inputs = inputs;
		for (Input i : inputs) {
			if (i.getField() == null || !i.getField().equals(this)) {
				i.setField(this);
			}
		}
	}
	
	/**
	 * Adiciona uma Entrada a lista de Entradas do Campo
	 * @param input Entrada a ser adicionada
	 */
	public void addInput(Input input) {		
		inputs.add(input);
		if (input.getField() == null || !input.getField().equals(this)) {
			input.setField(this);
		}
	}
	
	/**
	 * Remove uma Entrada da lista de Entradas do Campo
	 * @param input Entrada a ser removida
	 */
	public void removeInput(Input input) {
		while(inputs.contains(input))
			inputs.remove(input);
	}
	
	/**
	 * Retorna a Saída Esperada associada ao Campo
	 * @returno Saída Esperada
	 */
	public Output getOutput() {
		return output;
	}
	
	/**
	 * Altera o Saída Esperada do Campo 
	 * @param output Nova Saída Esperada
	 */
	public void setOutput(Output output) {
		this.output = output;
	}
	
	public boolean isOutputField() {
		return outputField;
	}

	public void setOutputField(boolean outputField) {
		this.outputField = outputField;
	}
	
	/**
	 * Retorna a lista de Requisitos do Campo
	 * @return Lista de Requisitos
	 */
	public List<Requirement<?>> getRequirements() {
		return requirements;
	}
	
	/**
	 * Altera a lista de Requisitos do Campo
	 * @param requirements Nova lista de Requisitos
	 */
	public void setRequirements(List<Requirement<?>> requirements) {
		this.requirements = requirements;
	}
	
	/**
	 * Adiciona um Requisito a lista de Requisitos do Campo
	 * @param requirement Requisito a ser adicionado a lista
	 */
	public void addRequirement(Requirement<?> requirement) {
		if (!requirements.contains(requirement)) {
			this.requirements.add(requirement);
		}
	}
	
	/**
	 * Remove um Requisito da lista de Requisitos
	 * @param position Posição do Requisito a ser removido
	 */
	public void removeRequirement(int position) {
		if(position < requirements.size())
			this.requirements.remove(position);		
	}
	
	public String getReferenceToCSV(){
		return referenceToCSV;
	}
	
	public void setReferenceToCSV(String referenceToCSV){
		this.referenceToCSV = referenceToCSV;		
	}
	
	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Field)) {
			return false;
		} else {
			Field field = (Field) obj;			
			boolean result = field.getReferenceToCSV().equals(this.getReferenceToCSV());
			result &= (field.isOutputField() == this.isOutputField()); 
			result &= field.getName().equals(this.getName());
			if(field.getOutput() != null){
				result &= field.getOutput().equals(this.getOutput());
			}else{
				result &= (this.getOutput()  == null);
			}
			
			if (field.getTestProcedure() != null) {
				result &= field.getTestProcedure().equals(this.getTestProcedure());
			} else {
				result &= (this.getTestProcedure() == null);
			}
			return result;
		}
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("Name: ");
		buffer.append(name);

		if (!isOutputField()) {
			buffer.append(" (Input)");
		} else {
			buffer.append(" (Output)");
		}

		return buffer.toString();
	}
	
}