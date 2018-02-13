package view;


import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import performanceTestRepresentation.TestPlan;
import util.XMLPersist;
import functionalTestRepresentation.TestBattery;
import net.miginfocom.swing.MigLayout;

/**
 * Classe para o JDialog que salva arquivos  
 * @author Ismayle de Sousa Santos
 */
public class SaveOptionsDialog extends JDialog{
	
	private static final long serialVersionUID = 1L;
	private JCheckBox cb1 = new JCheckBox("XML - Teste Funcional"); 
	private JCheckBox cb2 = new JCheckBox("XML - Teste de Desempenho");
	private JCheckBox cb3 = new JCheckBox("XML - Teste de Estresse");
	private JButton saveButton = null;
	private JPanel panel = null;
	private TestBattery battery = null;
	private TestPlan performanceTest = null;
	private TestPlan stressTest = null;
	
	public SaveOptionsDialog(TestBattery battery, TestPlan performanceTest, TestPlan stressTest) {
	    super();
	    this.battery = battery;
	    this.performanceTest = performanceTest;
	    this.stressTest = stressTest;
		initialize();		
	  }	  

	 /**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setTitle("Salvar um arquivo");
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	    
	    this.setContentPane(getPanel());
	    pack();	    
	}
	
	private JPanel getPanel(){
		if(panel == null){
			panel = new JPanel(new MigLayout());
			panel.add(cb1,"wrap");
			panel.add(cb2,"wrap");
			panel.add(cb3,"wrap 20");
			panel.add(getSaveButton(),"align center");
		}
	    return panel;
	}
	
	private JButton getSaveButton() {		
		if (saveButton == null) {
			saveButton = new JButton("Salvar");
			saveButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(cb1.isSelected()){
						saveFunctionalTestAbstractRepresentation();						
					}
					if(cb2.isSelected()){
						savePerformanceTestAbstractRepresentation();
					}
					if(cb3.isSelected()){
						saveStressTestAbstractRepresentation();
					}
				}
			});
		}
		return saveButton;
	}
	
	private void saveFunctionalTestAbstractRepresentation(){
		this.dispose();
		System.out.println("XML - Functional Test");
		if (battery != null) {
            String filename = File.separator + "tmp";
            JFileChooser fc = new JFileChooser(new File(filename));
            fc.setDialogTitle("Salvar um arquivo XML com o teste funcional");
            fc.showSaveDialog(null);
            File file = fc.getSelectedFile();
            if (file != null) {
                try {
                    XMLPersist.saveBattery(battery, file.getAbsolutePath()+".xml");
                    JOptionPane.showMessageDialog(null, "O arquivo XML com a representação abstrata do teste funcional foi salvo com sucesso!", "Mensagem de Informação", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "O arquivo não pôde ser gerado!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Representação abstrata do teste fucional vazia!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	private void savePerformanceTestAbstractRepresentation(){
		this.dispose();
		System.out.println("XML - Performance Test");		 
        if (performanceTest != null) {
            String filename = File.separator + "tmp";
            JFileChooser fc = new JFileChooser(new File(filename));
            fc.setDialogTitle("Salvar um arquivo XML com o teste de desempenho");
            fc.showSaveDialog(null);
            File file = fc.getSelectedFile();
            if (file != null) {
                try {
                    XMLPersist.savePerformanceTestPlan(performanceTest, file.getAbsolutePath()+".xml");
                    JOptionPane.showMessageDialog(null, "O arquivo XML com a representa��o abstrata do teste de desempenho foi salvo com sucesso!", "Mensagem de Informação", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "O arquivo não pôdeser gerado!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Representação abstrata do teste de desempenho vazia!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
        }   
	}
	
	private void saveStressTestAbstractRepresentation(){
		this.dispose();
		System.out.println("XML - Stress Test");		
        if (stressTest != null) {
            String filename = File.separator + "tmp";
            JFileChooser fc = new JFileChooser(new File(filename));
            fc.setDialogTitle("Save a XML file with stress test");
            fc.showSaveDialog(null);
            File file = fc.getSelectedFile();
            if (file != null) {
                try {
                    XMLPersist.savePerformanceTestPlan(stressTest, file.getAbsolutePath()+".xml");
                    JOptionPane.showMessageDialog(null, "O arquivo XML com a representação abstrata do teste de estresse foi salvo com sucesso!", "Mensagem de Informação", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "O arquivo não pôde ser salvo!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Representação abstrata do teste de estresse vazia!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
        }
	}

}  
