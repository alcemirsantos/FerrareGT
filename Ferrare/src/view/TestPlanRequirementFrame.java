package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import performanceTestRepresentation.TestPlan;
import performanceTestRepresentation.URLAccess;
import util.JTextFieldModified;

/**
 * Classe para o frame de requisitos do plano de teste de desempenho/estresse 
 * @author Ismayle de Sousa Santos
 */
public class TestPlanRequirementFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel titleLabel = null;
	private JLabel responseTimeLabel = null;
	private JTextFieldModified responseTimeTextField = null;
	private JLabel numberOfThreadsLabel = null;
	private JTextFieldModified numberOfThreadsTextField = null;
	private JLabel RampUpPeriodLabel = null;
	private JTextFieldModified rampUpPeriodTextField = null;
	private JLabel loopCountLabel = null;
	private JCheckBox foreverCheckBox = null;
	private JTextFieldModified loopCountTextField = null;
	private JCheckBox distributedTestCheckBox = null;
	private JLabel slaveLabel = null;
	private JTextFieldModified numberOfSlavesTextField = null;	
	private JCheckBox threadDelayCheckBox = null;	
	private JLabel minimumDelayLabel = null;
	private JTextFieldModified minimumDelayTextField = null;
	private JLabel maximumDelayLabel = null;
	private JTextFieldModified maximumDelayTextField = null;
	private JScrollPane pagesListScroll = null;
	private JTable pagesListTable = null;
	private JButton saveButton = null;
	private JLabel pagesListLabel = null;	
	
	
	private TestPlan testplan;
	private JButton closeButton = null;
	private FerrareView father;

	/**
	 * This is the default constructor
	 */
	public TestPlanRequirementFrame(TestPlan testplan, String title, FerrareView father) {
		super();
		this.testplan = testplan;
		titleLabel = new JLabel(title);
		this.father = father;
		initialize();
	}

	public void updateFields(){
		if(testplan != null){
			responseTimeTextField.setText(String.valueOf(testplan.getTimeout()));
			numberOfThreadsTextField.setText(String.valueOf(testplan.getConcurrentUsers()));
			rampUpPeriodTextField.setText(String.valueOf(testplan.getRampUpTime()));
			loopCountTextField.setText(String.valueOf(testplan.getLoopCount()));
			foreverCheckBox.setSelected(testplan.isInfiniteLoop());
            
            int random = (int) testplan.getRandomTimer();
            int constant = (int) testplan.getConstantTimer();
            
            if (((random) > 0) || ((constant) > 0)) {
                threadDelayCheckBox.setSelected(true);
                maximumDelayTextField.setText(String.valueOf(constant+random ));
                minimumDelayTextField.setText(String.valueOf(constant));
            } else {
            	threadDelayCheckBox.setSelected(false);                
            }
			
            DefaultTableModel model = (DefaultTableModel) pagesListTable.getModel();
            int rowNumber = model.getRowCount(); 
            for(int i = 0; i < rowNumber;i++){
            	model.removeRow(0);
            }
            for(URLAccess urlAccess: testplan.getPages()){
            	model.addRow(new String[]{urlAccess.getPath().toString(),String.valueOf(urlAccess.getLimitTime())});
            }            
            pagesListTable.setModel(model);
		}
	}
	
	private boolean changesAreSaved(){
		boolean flag = true;
		if(testplan != null){
			flag &= String.valueOf(testplan.getTimeout()).equals(responseTimeTextField.getText().toString());
			
			flag &= String.valueOf(testplan.getConcurrentUsers()).equals(numberOfThreadsTextField.getText().toString());
			
			flag &= String.valueOf(testplan.getRampUpTime()).equals(rampUpPeriodTextField.getText().toString());
			
			if(testplan.isInfiniteLoop()){
				flag &= foreverCheckBox.isSelected();	
			}else{
				flag &= String.valueOf(testplan.getLoopCount()).equals(loopCountTextField.getText().toString());
			}
			
			int random = (int) testplan.getRandomTimer();
            int constant = (int) testplan.getConstantTimer();
            
			if(threadDelayCheckBox.isSelected()){
				flag &= String.valueOf(constant+random).equals(maximumDelayTextField.getText().toString());
				flag &= String.valueOf(constant).equals(minimumDelayTextField.getText().toString());
				
			}
					
			int i = 0;
            for(URLAccess urlAccess: testplan.getPages()){
            	flag &= urlAccess.getPath().equals(pagesListTable.getValueAt(i, 0));
            	flag &= String.valueOf(urlAccess.getLimitTime()).equals(pagesListTable.getValueAt(i, 1));
            	i++;
            }            
            
		}
		return flag;
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(500, 550);
		this.setContentPane(getJContentPane());
		this.setTitle("Requisitos não funcionais");
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
				if(!changesAreSaved()){
					String [] textMessages = {"Sim","Não"};  
	                   
                    int answer = JOptionPane.showOptionDialog(null, "As alterações feitas não foram salvas. Deseja salvá-las?", "Uma Pergunta...",   
                    		                 JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE,   
                    		                 null, textMessages, null); 
                    if(answer == 0){
                    	saveChanges();
                    }					
				}
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
		updateFields();
	}

	private void saveChanges(){
		int amont = pagesListTable.getRowCount();
        for(int i = 0; i < amont; i++){
        	try {
        		Integer.parseInt(pagesListTable.getValueAt(i, 1).toString());
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Coloque somente valores naturais na coluna \"Tempo Limite\"!" ,"Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
	            return; // not save
	        }
        }
        
		int slaves;
		int users;
		if(distributedTestCheckBox.isSelected()){
			slaves = Integer.parseInt(numberOfSlavesTextField.getText());
		}else{
			slaves = 1; 
		}
		users = Integer.parseInt(numberOfThreadsTextField.getText());
		if(users == 0)
			testplan.setConcurrentUsers(0);
		else
			testplan.setConcurrentUsers((users) / (slaves));
		testplan.setTimeout(Integer.parseInt(responseTimeTextField.getText()));
        testplan.setRampUpTime(Integer.parseInt(rampUpPeriodTextField.getText()));
        testplan.setLoopCount(Integer.parseInt(loopCountTextField.getText()));
        testplan.setInfiniteLoop(foreverCheckBox.isSelected());
        int max = 0, min = 0;
        if(minimumDelayTextField.getText().length() > 0){
        	testplan.setConstantTimer(Integer.parseInt(minimumDelayTextField.getText()));
        	min = Integer.parseInt(minimumDelayTextField.getText());
        }
        if(maximumDelayTextField.getText().length() > 0){
        	max = Integer.parseInt(maximumDelayTextField.getText());	
        }
        testplan.setRandomTimer(max - min);
      
        for(int i = 0; i < amont; i++){
        	try {
        		 testplan.getPages().get(i).setPageTimeout(
        			Integer.parseInt(pagesListTable.getValueAt(i, 1).toString()));
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(null, "Coloque somente valores naturais na coluna \"Tempo Limite\"!" ,"Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
	            return; // not save
	        }
        }
        
        JOptionPane.showMessageDialog(null, "Requisitos salvos com sucesso!","Mensagem de Informação",JOptionPane.INFORMATION_MESSAGE);
        if(father != null)
        	father.updateTextAreas();
	}
	
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			maximumDelayLabel = new JLabel();
			maximumDelayLabel.setText("Atraso máximo:");
			minimumDelayLabel = new JLabel();
			minimumDelayLabel.setText("Atraso mínimo:");
			slaveLabel = new JLabel();
			slaveLabel.setText("Número de escravos:");
			loopCountLabel = new JLabel();
			loopCountLabel.setText("Quantidade de Execuções:");
			RampUpPeriodLabel = new JLabel();
			RampUpPeriodLabel.setText("Periodo de Inicialização (s):");
			numberOfThreadsLabel = new JLabel();
			numberOfThreadsLabel.setText("Número de usuários virtuais:");
			responseTimeLabel = new JLabel();
			responseTimeLabel.setText("Tempo de limite de resposta (ms):");
			
			if(titleLabel == null){
				titleLabel = new JLabel();			
				titleLabel.setText("Title");
			}
			jContentPane = new JPanel();
			jContentPane.setLayout(new MigLayout("","[][grow][grow]",""));
			jContentPane.add(titleLabel, "span,align center, wrap 20");
			
			jContentPane.add(numberOfThreadsLabel, null);
			jContentPane.add(getNumberOfThreadsTextField(), "grow,wrap");
			
			jContentPane.add(RampUpPeriodLabel, null);			
			jContentPane.add(getRampUpPeriodTextField(), "grow,wrap");
			
			jContentPane.add(responseTimeLabel, null);
			jContentPane.add(getResponseTimeTextField(), "grow,wrap");	
			
			jContentPane.add(getForeverCheckBox(), "wrap");
			jContentPane.add(loopCountLabel, "align right");
			jContentPane.add(getLoopCountTextField(), "grow,wrap");
			jContentPane.add(getDistributedTestCheckBox(), "wrap");
						
			jContentPane.add(slaveLabel, "align right");
			jContentPane.add(getNumberOfSlavesTextField(), "grow,wrap");
			
			jContentPane.add(getThreadDelayCheckBox(), "wrap");			
			jContentPane.add(minimumDelayLabel, "align right");
			jContentPane.add(getMinimumDelayTextField(), "grow,wrap");
			jContentPane.add(maximumDelayLabel, "align right");
			jContentPane.add(getMaximumDelayTextField(), "grow,wrap 20");
			
			pagesListLabel = new JLabel();
			pagesListLabel.setText("Lista de Páginas");
					
			jContentPane.add(pagesListLabel, "wrap");
			jContentPane.add(getPagesList(), "span,grow, wrap 10");
			jContentPane.add(getSaveButton(), "span 2, align center");		
			jContentPane.add(getCloseButton(), null);
			
		}
		return jContentPane;
	}

	/**
	 * This method initializes responseTimeTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getResponseTimeTextField() {
		if (responseTimeTextField == null) {
			responseTimeTextField = new JTextFieldModified();
		}
		responseTimeTextField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				Character key = arg0.getKeyChar();
				if(Character.isDigit(key) || key == KeyEvent.VK_DELETE || key == KeyEvent.VK_BACK_SPACE){
					DefaultTableModel model = (DefaultTableModel) pagesListTable.getModel();
		            int rowNumber = model.getRowCount(); 
		            for(int i = 0; i < rowNumber;i++){
		            	model.removeRow(0);
		            }
		            for(URLAccess urlAccess: testplan.getPages()){
		            	model.addRow(new String[]{urlAccess.getPath().toString(),responseTimeTextField.getText().toString()});
		            }            
		            pagesListTable.setModel(model);
				}
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		return responseTimeTextField;
	}

	/**
	 * This method initializes numberOfThreadsTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNumberOfThreadsTextField() {
		if (numberOfThreadsTextField == null) {
			numberOfThreadsTextField = new JTextFieldModified();
		}		
		return numberOfThreadsTextField;
	}

	/**
	 * This method initializes RampUpPeriodTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getRampUpPeriodTextField() {
		if (rampUpPeriodTextField == null) {		
			rampUpPeriodTextField = new JTextFieldModified();
		}		
		return rampUpPeriodTextField;
	}

	/**
	 * This method initializes foreverCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getForeverCheckBox() {
		if (foreverCheckBox == null) {
			foreverCheckBox = new JCheckBox("Executar Infinitamente");
			foreverCheckBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(foreverCheckBox.isSelected()){
						loopCountTextField.setEditable(false);
					}else{
						loopCountTextField.setEditable(true);
					}
				}
			});
		}
		return foreverCheckBox;
	}

	/**
	 * This method initializes loopCountTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getLoopCountTextField() {
		if (loopCountTextField == null) {
			loopCountTextField = new JTextFieldModified();
		}		
		return loopCountTextField;
	}

	/**
	 * This method initializes distributedTestCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getDistributedTestCheckBox() {
		if (distributedTestCheckBox == null) {
			distributedTestCheckBox = new JCheckBox("Teste Distribuído");
			distributedTestCheckBox.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(distributedTestCheckBox.isSelected()){
								numberOfSlavesTextField.setEditable(true);
							}else{
								numberOfSlavesTextField.setEditable(false);
							}
						}
			});
		}
		return distributedTestCheckBox;
	}

	/**
	 * This method initializes numberOfSlavesTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNumberOfSlavesTextField() {
		if (numberOfSlavesTextField == null) {
			numberOfSlavesTextField = new JTextFieldModified();
			numberOfSlavesTextField.setEditable(false);
		}
		return numberOfSlavesTextField;
	}

	/**
	 * This method initializes threadDelayCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getThreadDelayCheckBox() {
		if (threadDelayCheckBox == null) {
			threadDelayCheckBox = new JCheckBox("Atraso dos usuários virtuais");
			threadDelayCheckBox.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(threadDelayCheckBox.isSelected()){
						minimumDelayTextField.setEditable(true);
						maximumDelayTextField.setEditable(true);
					}else{
						minimumDelayTextField.setEditable(false);
						maximumDelayTextField.setEditable(false);
					}
				}
			});
		}
		return threadDelayCheckBox;
	}

	/**
	 * This method initializes minimumDelayTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMinimumDelayTextField() {
		if (minimumDelayTextField == null) {
			minimumDelayTextField = new JTextFieldModified();
			minimumDelayTextField.setEditable(false);
		}
		return minimumDelayTextField;
	}

	/**
	 * This method initializes maximumDelayTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMaximumDelayTextField() {
		if (maximumDelayTextField == null) {
			maximumDelayTextField = new JTextFieldModified();
			maximumDelayTextField.setEditable(false);
		}
		return maximumDelayTextField;
	}

	/**
	 * This method initializes pagesList	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getPagesList() {
		if (pagesListScroll == null) {
			pagesListScroll = new JScrollPane();
			pagesListScroll.setViewportView(getPagesListTable());
		}
		return pagesListScroll;
	}

	/**
	 * This method initializes pagesListTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getPagesListTable() {
		if (pagesListTable == null) {			
			String[] colunas = new String []{"Página","Tempo limite"};  
			String[][] dados = new String [][]{};
			DefaultTableModel model = new DefaultTableModel(dados, colunas) {    
				   
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int rowIndex, int mColIndex){  
					   if(mColIndex == 0)  
						   return false;
					   else
						   return true;
				   }}; 
			pagesListTable = new JTable(model);
			TableColumnModel modeloDaColuna = pagesListTable.getColumnModel(); 
			modeloDaColuna.getColumn(1).setMaxWidth(100);
			 
		}
		return pagesListTable;
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
					saveChanges();				
					/*int amont = pagesListTable.getRowCount();
			        for(int i = 0; i < amont; i++){
			        	try {
			        		Integer.parseInt(pagesListTable.getValueAt(i, 1).toString());
				        } catch (Exception e) {
				            JOptionPane.showMessageDialog(null, "Coloque somente valores naturais na coluna \"Tempo Limite\"!" ,"Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
				            return; // not save
				        }
			        }
			        
					int slaves;
					int users;
					if(distributedTestCheckBox.isSelected()){
						slaves = Integer.parseInt(numberOfSlavesTextField.getText());
					}else{
						slaves = 1; 
					}
					users = Integer.parseInt(numberOfThreadsTextField.getText());
					if(users == 0)
						testplan.setConcurrentUsers(0);
					else
						testplan.setConcurrentUsers((users) / (slaves));
					testplan.setTimeout(Integer.parseInt(responseTimeTextField.getText()));
			        testplan.setRampUpTime(Integer.parseInt(rampUpPeriodTextField.getText()));
			        testplan.setLoopCount(Integer.parseInt(loopCountTextField.getText()));
			        testplan.setInfiniteLoop(foreverCheckBox.isSelected());
			        int max = 0, min = 0;
			        if(minimumDelayTextField.getText().length() > 0){
			        	testplan.setConstantTimer(Integer.parseInt(minimumDelayTextField.getText()));
			        	min = Integer.parseInt(minimumDelayTextField.getText());
			        }
			        if(maximumDelayTextField.getText().length() > 0){
			        	max = Integer.parseInt(maximumDelayTextField.getText());	
			        }
			        testplan.setRandomTimer(max - min);
			      
			        for(int i = 0; i < amont; i++){
			        	try {
			        		 testplan.getPages().get(i).setPageTimeout(
			        			Integer.parseInt(pagesListTable.getValueAt(i, 1).toString()));
				        } catch (Exception e) {
				            JOptionPane.showMessageDialog(null, "Coloque somente valores naturais na coluna \"Tempo Limite\"!" ,"Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
				            return; // not save
				        }
			        }
			        
			        JOptionPane.showMessageDialog(null, "Roteiro atualizado com sucesso!","Mensagem de Informa��o",JOptionPane.INFORMATION_MESSAGE);
				}*/
				}
			});
			
		}
		return saveButton;
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
			public void actionPerformed(ActionEvent arg0) {
				if(!changesAreSaved()){
					String [] textMessages = {"Sim","Não"};  
	                   
                    int answer = JOptionPane.showOptionDialog(null, "As alterações feitas não foram salvas. Deseja salvá-las?", "Uma Pergunta...",   
                    		                 JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE,   
                    		                 null, textMessages, null); 
                    if(answer == 0){
                    	saveChanges();
                    }					
				}
				dispose();				
			}
		});
		return closeButton;
	}

}
