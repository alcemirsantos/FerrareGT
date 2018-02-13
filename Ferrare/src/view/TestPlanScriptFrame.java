package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import functionalTestRepresentation.Field;
import functionalTestRepresentation.Input;
import performanceTestRepresentation.TestPlan;
import performanceTestRepresentation.URLAccess;
import util.TableCellEdit;

/**
 * Classe para frame de manipula��o do script de teste de sempenho/estresse 
 * @author Ismayle de Sousa Santos
 */
public class TestPlanScriptFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel titleLabel = null;
	private JLabel hiddenFieldsLabel = null;
	private JList hiddenFieldsList = null;
	private JButton addHiddenFieldButton = null;
	private JButton removeHiddenFieldButton = null;
	private JLabel pagesListLabel = null;
	private JList pagesList = null;
	private JButton removePageButton = null;
	private JLabel pageLabel = null;
	private JTextField pageTextField = null;
	private JLabel expectedOutputLabel = null;
	private JTextField expectedOutputTextField = null;
	private JLabel methodLabel = null;
	private JComboBox methodComboBox = null;
	private JLabel fieldsLabel = null;
	private JScrollPane fieldsScrollPane = null;
	private JTable fieldsTable = null;
	private JButton saveButton = null;
	private JButton addFieldButton = null;
	private JButton removeFieldButton = null;

	private TestPlan testPlan;
	private JButton closeButton = null;
	private FerrareView father;
	
	/**
	 * This is the default constructor
	 */
	public TestPlanScriptFrame(TestPlan testPlan, String title, FerrareView father) {
		super();
		this.testPlan = testPlan;
		titleLabel = new JLabel(title);
		this.father = father;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		updateFrameFields();
		this.setSize(600, 600);
		this.setContentPane(getJContentPane());
		this.setTitle("Configurando o roteiro do teste");
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				father.updateTextAreas();				
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		//pack();
	}
	
	private void updateFrameFields() {
		if(testPlan != null){
			//hidden list
			DefaultListModel list = new DefaultListModel();
			LinkedList<String> hiddenFields = null; 
			for (URLAccess page : testPlan.getPages()){
		           hiddenFields = page.getListHidden();
		           break;           
		    }    
		    for(String s : hiddenFields){
		    	list.addElement(s);
		    }
		    if(hiddenFieldsList == null)
		    	hiddenFieldsList = new JList();
			hiddenFieldsList.setModel(list);
			//pages list
			updatePagesList();
		}		
	}
	
	public void updatePagesList(){
		DefaultListModel list = new DefaultListModel();
		System.out.println("--- UPDATE TABLE ---");
		for (URLAccess page : testPlan.getPages()){			
			list.addElement(page.getServer()+"/"+page.getPath());
			System.out.println(page.getServer()+"/"+page.getPath());
	    }
		if (pagesList == null)
			pagesList = new JList();
		pagesList.setModel(list);		
	}
	

	public void updatePageFields(int position){
		if(testPlan != null && testPlan.getPages().size() >= position){
			pageTextField.setText(testPlan.getPages().get(position).getPath());
			expectedOutputTextField.setText(testPlan.getPages().get(position).getResponse());
			methodComboBox.setSelectedItem(testPlan.getPages().get(position).getMethod());
			//fileds list
			URLAccess urlAccess = testPlan.getPages().get(position);
			if(fieldsTable.isEditing())
            	fieldsTable.getCellEditor().stopCellEditing();
			DefaultTableModel model = (DefaultTableModel) fieldsTable.getModel();			
			int rowNumber = model.getRowCount(); 
            for(int i = 0; i < rowNumber;i++){
			    model.removeRow(0);
            }
            for(Field f : urlAccess.getFields()){
            	String value = "";
            	if(f.getInputs() != null){
            		 value = f.getInputs().get(f.getInputs().size()-1).getValue(); // take the last value of field
            	}
            	model.addRow(new String[]{f.getName(),value});
            }            
            fieldsTable.setModel(model);
            TableColumn col = fieldsTable.getColumnModel().getColumn(1);  
			col.setCellEditor(new TableCellEdit(testPlan.getPages().get(position)));
		}		
	}
	
	public void addHiddenFieldToPages(String hiddenFieldName){
		for(URLAccess ua : testPlan.getPages()){
			ua.getListHidden().add(hiddenFieldName);
		}
	}
	
	public void removeHiddenFieldToPages(int position){
		for(URLAccess ua : testPlan.getPages()){
			if(position < ua.getListHidden().size())
				ua.getListHidden().remove(position);
		}
	}
	
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			fieldsLabel = new JLabel();
			fieldsLabel.setText("Lista de Campos");
			methodLabel = new JLabel();
			methodLabel.setText("Metodo");
			expectedOutputLabel = new JLabel();
			expectedOutputLabel.setText("Saída Esperada");
			pageLabel = new JLabel();
			pageLabel.setText("Página");
			pagesListLabel = new JLabel();
			pagesListLabel.setText("Lista de Páginas");
			hiddenFieldsLabel = new JLabel();
			hiddenFieldsLabel.setText("Campos Ocultos");
			if(titleLabel == null){
				titleLabel = new JLabel();
				titleLabel.setText("Roteiro de Teste");
			}
			jContentPane = new JPanel();
			jContentPane.setLayout(new MigLayout("","[][grow][]",""));
			jContentPane.add(titleLabel, "span, align center");
			jContentPane.add(hiddenFieldsLabel, "wrap");
			jContentPane.add(getHiddenFieldsList(), "span 2 2,grow");
			jContentPane.add(getAddHiddenFieldButton(), "grow,wrap");
			jContentPane.add(getRemoveHiddenFieldButton(), "wrap");
			jContentPane.add(pagesListLabel, "wrap");
			jContentPane.add(getPagesList(), "span 2,grow" );
			jContentPane.add(getRemovePageButton(), "wrap 10");
			jContentPane.add(pageLabel, null);
			jContentPane.add(getPageTextField(), "grow,wrap");
			jContentPane.add(expectedOutputLabel, null);
			jContentPane.add(getExpectedOutputTextField(), "grow,wrap");
			jContentPane.add(methodLabel, null);
			jContentPane.add(getMethodComboBox(), "wrap 20");
			jContentPane.add(getSaveButton(), "span,align center");
			jContentPane.add(fieldsLabel, "wrap");
			jContentPane.add(getFieldsScrollPane(), "grow,span 2 2");
			jContentPane.add(getAddFieldButton(), "gaptop 30,grow,wrap");
			jContentPane.add(getRemoveFieldButton(), "wrap 10");
			jContentPane.add(getCloseButton(), "span, align center");
			
		}
		return jContentPane;
	}

	/**
	 * This method initializes hiddenFieldsList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JScrollPane getHiddenFieldsList() {
		if (hiddenFieldsList == null) {
			hiddenFieldsList = new JList();			
		}		
		hiddenFieldsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(hiddenFieldsList);
		return scroll;
	}

	/**
	 * This method initializes addHiddenFieldButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddHiddenFieldButton() {
		if (addHiddenFieldButton == null) {
			addHiddenFieldButton = new JButton("Adicionar");
			addHiddenFieldButton.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent arg0) {	
					Object answer = JOptionPane.showInputDialog(null,"Digite o nome do campo oculto", "Adicionando o nome de um campo oculto", JOptionPane.QUESTION_MESSAGE);
					if(answer != null){
						System.out.println(answer);
						DefaultListModel list = (DefaultListModel) hiddenFieldsList.getModel();		 
						list.addElement(answer.toString());
						hiddenFieldsList.setModel(list);	
						addHiddenFieldToPages(answer.toString());
					}
				}
			});
		}
		return addHiddenFieldButton;
	}

	/**
	 * This method initializes removeHiddenFieldButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRemoveHiddenFieldButton() {
		if (removeHiddenFieldButton == null) {
			removeHiddenFieldButton = new JButton("Remover");
			removeHiddenFieldButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int selectedfield = hiddenFieldsList.getSelectedIndex();
					if ((selectedfield > -1) && (selectedfield <= hiddenFieldsList.getMaxSelectionIndex())){
						System.out.println(selectedfield+";"+hiddenFieldsList.getMaxSelectionIndex());
						DefaultListModel list = (DefaultListModel) hiddenFieldsList.getModel();		 
						list.remove(selectedfield);
						hiddenFieldsList.setModel(list);
						removeHiddenFieldToPages(selectedfield);
					}else{
			            JOptionPane.showMessageDialog(null, "Primeiro selecione um campo na \"Lista de Campos\"!","Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
			        }					
				}
			});
		}
		return removeHiddenFieldButton;
	}

	/**
	 * This method initializes pagesList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JScrollPane getPagesList() {
		if (pagesList == null) {
			pagesList = new JList();			
		}
		pagesList.addListSelectionListener(new ListSelectionListener() {  
			@Override
			public void valueChanged(ListSelectionEvent e) {
				 if (e.getValueIsAdjusting()){					 
		              //ainda selecionando				 
					 return;
				 }
				 if (pagesList.isSelectionEmpty()) {                  
                        System.out.println("nenhuma selecao no pageList");
				 } else {
						System.out.println(pagesList.getSelectedValue());
						updatePageFields(pagesList.getSelectedIndex());
				 }
			}  
        });
		pagesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(pagesList);
		return scroll;
	}

	/**
	 * This method initializes removePageButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRemovePageButton() {
		if (removePageButton == null) {
			removePageButton = new JButton("Remover");
			removePageButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					   int selectedPage = pagesList.getSelectedIndex();
				       if(selectedPage > -1){
				    	   	testPlan.remove(selectedPage);
				    	    updatePagesList();
				    	    pageTextField.setText("");
				    		expectedOutputTextField.setText("");
				    		methodComboBox.setSelectedIndex(0);
				    		
				    		DefaultTableModel model = (DefaultTableModel) fieldsTable.getModel();
				    		int rowNumber = model.getRowCount(); 
				            for(int i = 0; i < rowNumber;i++){
				            	model.removeRow(0);
				            }
				       }
					
				}
			});
		}
		return removePageButton;
	}

	/**
	 * This method initializes pageTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPageTextField() {
		if (pageTextField == null) {
			pageTextField = new JTextField();
		}
		return pageTextField;
	}

	/**
	 * This method initializes expectedOutputTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getExpectedOutputTextField() {
		if (expectedOutputTextField == null) {
			expectedOutputTextField = new JTextField();
		}
		return expectedOutputTextField;
	}

	/**
	 * This method initializes methodComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getMethodComboBox() {
		if (methodComboBox == null) {
			methodComboBox = new JComboBox(new String[]{"GET","POST"});
		}
		return methodComboBox;
	}

	/**
	 * This method initializes fieldsScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getFieldsScrollPane() {
		if (fieldsScrollPane == null) {
			fieldsScrollPane = new JScrollPane();
			fieldsScrollPane.setViewportView(getFieldsTable());
			fieldsScrollPane.setPreferredSize(new java.awt.Dimension(300,200)); 
		}		
		return fieldsScrollPane;
	}

	/**
	 * This method initializes fieldsTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getFieldsTable() {
		if (fieldsTable == null) {
			fieldsTable = new JTable();
			String[] columns = new String []{"Nome","Valor"};  
			String[][] data = new String [][]{};
			DefaultTableModel model = new DefaultTableModel(data, columns) {    
			
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int rowIndex, int mColIndex){  
				   if(mColIndex == 0)  
					   return false;
				   else
					   return true;				   
			   }};
			fieldsTable = new JTable(model);
			TableColumn col = fieldsTable.getColumnModel().getColumn(1);  
			col.setCellEditor(new TableCellEdit(null));

		}
		return fieldsTable;
	}

	/**
	 * This method initializes saveButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton("Salvar");
			saveButton.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(pagesList.getSelectedIndex() >= 0){
						testPlan.getPages().get(pagesList.getSelectedIndex()).setPath(pageTextField.getText());
						testPlan.getPages().get(pagesList.getSelectedIndex()).setResponse(expectedOutputTextField.getText());
						testPlan.getPages().get(pagesList.getSelectedIndex()).setMethod(methodComboBox.getSelectedItem().toString());
						updatePagesList();						
						JOptionPane.showMessageDialog(null, "Alterações na requisição selecionada salvas com sucesso!","Mensagem de Informação",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		return saveButton;
	}

	/**
	 * This method initializes addFieldButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddFieldButton() {
		if (addFieldButton == null) {
			addFieldButton = new JButton("Adicionar");
			addFieldButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(pagesList.getSelectedIndex() >= 0){
						 String answer = (String)JOptionPane.showInputDialog(
			                    null,
			                    "Nome do Campo:",
			                    "Uma Pergunta",
			                    JOptionPane.QUESTION_MESSAGE,
			                    null,
			                    null,
			                    "");
						 if(answer != null){
							 Input newInput = new Input("");
							 Field newField = new Field();
							 newField.setName(answer);
							 newField.addInput(newInput);
							 int position  = pagesList.getSelectedIndex();
							 if( position >= 0){
								 testPlan.getPages().get(pagesList.getSelectedIndex()).addField(newField);
							 }							 
							 updatePageFields(position);
						 }
					}
				}
			});
		}
		return addFieldButton;
	}

	/**
	 * This method initializes removeFieldButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRemoveFieldButton() {
		if (removeFieldButton == null) {
			removeFieldButton = new JButton("Remover");
			removeFieldButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
				    int selectedField = fieldsTable.getSelectedRow();
			        if ((selectedField > -1) && pagesList.getSelectedIndex() > -1){			        	
			            testPlan.getPages().get(pagesList.getSelectedIndex()).getFields().remove(selectedField);
			        	updatePageFields(pagesList.getSelectedIndex());
			        }else{
			            JOptionPane.showMessageDialog(null, "Primeiro selecione um campo na \"Lista de Campos\"!","Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
			        }
					
				}
			});
		}
		return removeFieldButton;
	}

	/**
	 * This method initializes closeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCloseButton() {
		if (closeButton == null) {
			closeButton = new JButton("Fechar");
		}
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		return closeButton;
	}

}


 