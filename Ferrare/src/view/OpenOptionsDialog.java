package view;

import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import util.XMLPersist;
import functionalTestRepresentation.TestBattery;
import net.miginfocom.swing.MigLayout;

/**
 * Classe para o jDialog que abre um arquivo  
 * @author Ismayle de Sousa Santos
 */
public class OpenOptionsDialog extends JDialog{	
	private static final long serialVersionUID = 1L;
	private JRadioButton[] rb = null;  
    private ButtonGroup bg = null;
	private JButton openButton = null;
	private JPanel panel = null;
	private FerrareView jFrame;
	
	public OpenOptionsDialog(TestBattery battery, FerrareView jFrame) {		
	    super();	    
	    this.jFrame = jFrame; 
	    initialize();		
	  }	  

	 /**
	 * This method initializes this
	 * 
	 */
	private void initialize() {		   
		initializeButtonGroup();
        this.setTitle("Abrir um arquivo");
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	    
	    this.setContentPane(getPanel());
	    pack();
	}
	
	private JPanel getPanel(){
		if(panel == null){
			panel = new JPanel(new MigLayout());
			panel.add(rb[0],"wrap");
			panel.add(rb[1],"wrap");
			panel.add(rb[2],"wrap 20");						
			panel.add(getSaveButton(),"align center");
		}
	    return panel;
	}
	
	private void initializeButtonGroup(){
		if(rb == null){
			rb = new JRadioButton[3];			  
	        rb[0] = new JRadioButton("XML - Teste Funcional");  
	        rb[1] = new JRadioButton("XML - Teste de Desempenho");
	        rb[2] = new JRadioButton("XML - Teste de Estresse");
	        bg = new ButtonGroup();
	        for (int i = 0; i < 3; i++)  
	            bg.add(rb[i]);
		}   
	}
	
	private JButton getSaveButton() {		
		if (openButton == null) {
			openButton = new JButton("ok");
			openButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					 if (rb[0].isSelected()) {  
			        	 openFunctionalTestAbstractRepresentation();			        	 
			         } else if (rb[1].isSelected()) {  
			        	 openPerformanceTestAbstractRepresentation();			        	 
			         }  else if (rb[2].isSelected()) {
			        	 openStressTestAbstractRepresentation();			        	 
			         }				 
				}
			});
		}
		return openButton;
	}
	
	private void openFunctionalTestAbstractRepresentation(){
		this.dispose();
		String filename = File.separator + "tmp";
	    JFileChooser fc = new JFileChooser(new File(filename));
	    fc.addChoosableFileFilter(new FileFilter());
	    fc.setDialogTitle("Abrir um arquivo XML com a representação abstrata do teste funcional");
	    fc.showOpenDialog(null);
	    File file = fc.getSelectedFile();
	    
	    if(file != null){
	       if (file.exists()) {
	               try {
	                    jFrame.setBattery(XMLPersist.loadBattery(file.getAbsolutePath()));	                    
	               } catch (IOException ex) {
	                    JOptionPane.showMessageDialog(null,"Erro na leitura do arquivo!","Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
	               }
	       }else{
	                 JOptionPane.showMessageDialog(null,"Arquivo não encontrado!","Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
	       }
	    }
		
		
		System.out.println("Open a XML - Functional Test");
	}
	
	private void openPerformanceTestAbstractRepresentation(){
		this.dispose();
		System.out.println("Open a XML - Performance Test");
		String filename = File.separator + "tmp";
        JFileChooser fc = new JFileChooser(new File(filename));
        fc.addChoosableFileFilter(new FileFilter());
        fc.setDialogTitle("Abrir um arquivo XML com a representação abstrata do teste de desempenho");
        fc.showOpenDialog(null);
        File file = fc.getSelectedFile();	        
        if(file != null){
            if (file.exists()) {
                try {
                    jFrame.setPerformanceTest(XMLPersist.loadPerfomanceTestPlan(file.getAbsolutePath()));
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"Erro na leitura do arquivo!","Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Arquivo não encontrado!","Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
	}

	private void openStressTestAbstractRepresentation(){
		this.dispose();
		System.out.println("Open a XML - Stress Test");
		String filename = File.separator + "tmp";
        JFileChooser fc = new JFileChooser(new File(filename));
        fc.addChoosableFileFilter(new FileFilter());
        fc.setDialogTitle("Abrir um arquivo XML com a representação abstrata do teste de estresse");
        fc.showOpenDialog(null);
        File file = fc.getSelectedFile();	        
        if(file != null){
            if (file.exists()) {
                try {
                    jFrame.setStressTest(XMLPersist.loadPerfomanceTestPlan(file.getAbsolutePath()));
                }catch (Exception e){
                    JOptionPane.showMessageDialog(null,"Erro na leitura do arquivo!","Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Arquivo não encontrado!","Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
	}	
}  

class FileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith(".xml");
    }
    public String getDescription() {
        return "*.xml";
    }
}