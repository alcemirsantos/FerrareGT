package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import functionalTestRepresentation.requirements.MaxSizeRequirement;
import functionalTestRepresentation.requirements.MinSizeRequirement;
import functionalTestRepresentation.requirements.OtherRequirement;
import functionalTestRepresentation.requirements.Requirement;
import functionalTestRepresentation.requirements.TypeRequirement;
import functionalTestRepresentation.requirements.UniqueRequirement;

/**
 * Classe para o JDialog que adiciona requisitos aos testes funcionais  
 * @author Ismayle de Sousa Santos
 */
public class AddRequirementFrame extends JDialog  {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JComboBox typeComboBox = null;
	private JComboBox typeOptionsComboBox = null;
	private JTextField sizeTextField = null;
	private JButton okButton = null;
	private FunctionalRequirementFrame reqFrame;
	
	/**
	 * This is the default constructor
	 */
	public AddRequirementFrame(FunctionalRequirementFrame reqFrame) {
		super();
		this.reqFrame = reqFrame;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setTitle("Adicionar");
		this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    pack();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new MigLayout());
			jContentPane.add(getTypeComboBox(), "wrap,growx");			
			jContentPane.add(getTypeOptionsComboBox(), "wrap,growx");			
			jContentPane.add(getSizeTextField(), "wrap");
			jContentPane.add(getOkButton(),"align center");
		}
		return jContentPane;
	}

	private JButton getOkButton() {		
		if (okButton == null) {
			okButton = new JButton("ok");
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					addRequirement();					 				 
				}				
			});
		}
		return okButton;
	}
	
	public void addRequirement(){
		 int selectedRequirementType = typeComboBox.getSelectedIndex();		 
		 int selectedRequirementTypeOption = typeOptionsComboBox.getSelectedIndex();
		 String sizeField = sizeTextField.getText();
		 Requirement<?> req = null;
	     switch (selectedRequirementType) {
			     case 0:
		             try {
		                 int valor = Integer.parseInt(sizeField);
		                 req = new MaxSizeRequirement(valor);
		             } catch (Exception e) {
		                 JOptionPane.showMessageDialog(null, "Digite o tamanho máximo!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
		             }
		             break;
		         case 1:
		             try {
		                 int valor = Integer.parseInt(sizeField);
		                 req = new MinSizeRequirement(valor);
		             } catch (Exception e) {
		                 JOptionPane.showMessageDialog(null, "Digite o tamanho mínimo!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
		             }
		             break;
		         case 2:
		             req = new OtherRequirement(sizeField);		             
		             break;
		         case 3:
	                switch (selectedRequirementTypeOption) {
	                    case 0:
	                        req = new TypeRequirement(TypeRequirement.BOOLEAN);
	                        break;
	                    case 1:
	                        req = new TypeRequirement(TypeRequirement.DATE);
	                        break;
	                    case 2:
	                        req = new TypeRequirement(TypeRequirement.FLOAT);
	                        break;
	                    case 3:
	                        req = new TypeRequirement(TypeRequirement.INTEGER);
	                        break;
	                    case 4:
	                        req = new TypeRequirement(TypeRequirement.STRING);
	                        break;
	                }
	                break;
	             case 4:
	            	req = new UniqueRequirement(true);
	                break;
	        }
	     	if(req != null )
	     		reqFrame.addRequirementToField(req);
	}
	
	/**
	 * This method initializes typeComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getTypeComboBox() {
		if (typeComboBox == null) {
			typeComboBox = new JComboBox(new String[]{ "Tamanho máximo","Tamanho mínimo","Outro","Tipo","Unicidade"});
			typeComboBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(typeComboBox.getSelectedIndex() == 3){
						typeOptionsComboBox.setEnabled(true);
						sizeTextField.setText("");
						sizeTextField.setEditable(false);
					}else if ((typeComboBox.getSelectedIndex() == 0) || (typeComboBox.getSelectedIndex() == 1) || (typeComboBox.getSelectedIndex() == 2)){
						typeOptionsComboBox.setEnabled(false);
						sizeTextField.setText("");
						sizeTextField.setEditable(true);
					}else{
						sizeTextField.setText("");
						sizeTextField.setEditable(false);
						typeOptionsComboBox.setEnabled(false);
					}
				}
			});
		}
		return typeComboBox;
	}

	/**
	 * This method initializes typeOptionsComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getTypeOptionsComboBox() {
		if (typeOptionsComboBox == null) {
			typeOptionsComboBox = new JComboBox(new String[]{"Boolean","Date","Float","Integer","String"});
			typeOptionsComboBox.setEnabled(false);
		}
		return typeOptionsComboBox;
	}

	/**
	 * This method initializes sizeTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getSizeTextField() {
		if (sizeTextField == null) {
			sizeTextField = new JTextField(10);			
		}
		return sizeTextField;
	}
}
