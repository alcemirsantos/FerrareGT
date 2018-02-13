package view;

import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Event;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.KeyStroke;
import java.awt.Point;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JTabbedPane;
import javax.swing.JButton;

import br.ufpi.genesis.util.Util;
import br.ufpi.genesis.view.GenesisPanel;
import performanceTestGenerator.BaseScripter;
import performanceTestGenerator.SimpleGenerator;
import performanceTestRepresentation.TestPlan;
import performanceTestRepresentation.URLAccess;
import util.LoaderJar;
import functionalTestExtractor.BaseExtractor;
import functionalTestRepresentation.Field;
import functionalTestRepresentation.TestBattery;
import functionalTestRepresentation.TestCase;
import net.miginfocom.swing.MigLayout;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import java.awt.Dimension;

/**
 * Classe para o frame principal da ferramenta  
 * @author Ismayle de Sousa Santos
 * @author Alcemir Rodrigues Santos
 */
public class FerrareView {

	
	private JFrame jFrame = null;
	private static String GENESIS = "Genesis";
	private static String FERRARE = "Ferrare";
	private JPanel jContentPane = null;
	private JPanel wrapper = null;
	private GenesisPanel genesisPanel = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu extractorMenu = null;
	private JMenu generatorMenu = null;
	private JMenu genesisMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem openMenuItem = null;
	private JMenuItem saveMenuItem = null;
	private JMenuItem exitMenuItem = null;	
	private JMenuItem extractorSelectMenuItem = null;
	private JMenuItem generatorSelectMenuItem = null;	
	private JMenuItem aboutMenuItem = null;
	private JDialog aboutDialog = null;  //  @jve:decl-index=0:visual-constraint="10,788"
	private JPanel aboutContentPane = null;
	private JLabel aboutVersionLabel = null;
	private JTextArea functionalTestScriptTextArea = null;
	private JLabel functionalTestLabel = null;
	private JTabbedPane abstractRepresentationPane = null;
	private JPanel functionalPanel = null;
	private JPanel performancePanel = null;
	private JPanel stressPanel = null;
	private JLabel abstractRepresentationsLabel = null;
	private JTextArea functionalTestRepresentationTextArea = null;
	private JButton functionalTestRequirementButton = null;
	private JTextArea performanceTestRepresentationTextArea = null;
	private JButton performanceTestRequirementButton = null;
	private JButton performanceTestScriptButton = null;
	private JTextArea stressTestRepresentationTextArea = null;
	private JButton stressTestRequirementsButton = null;
	private JButton stressTestScriptButton = null;
	private JLabel stressTestRequirementImageLabel = null;
	private JLabel stressTestScriptImageLabel = null;
	private JLabel performanceTestRequirementImageLabel = null;
	private JLabel performanceTestScriptImageLabel = null;
	private JLabel functionalTestRequirementImageLabel = null;

	private FerrareView ferrareView = this; 
	private TestBattery battery = null;  //  @jve:decl-index=0:
	private TestPlan performanceTest = null;  //  @jve:decl-index=0:
	private TestPlan stressTest = null;  //  @jve:decl-index=0:
	private JMenuItem genesisMenuItem = null;
	private JPanel genesisWrapper = null;
	private JButton btnExit = null;
	
	private boolean flagScriptSaved = false;
	
	public void updateTextAreas(){
		performanceTestRepresentationTextArea.setText(performanceTest.toString());
		stressTestRepresentationTextArea.setText(stressTest.toString());
	}
	
	public void resetImages(){
		functionalTestRequirementImageLabel.setText("<html><img src=" + "file:images/alertImage.png" + "/></html>");		
		performanceTestRequirementImageLabel.setText("<html><img src=" + "file:images/alertImage.png" + "/></html>");
		performanceTestScriptImageLabel.setText("<html><img src=" + "file:images/alertImage.png" + "/></html>");
		stressTestRequirementImageLabel.setText("<html><img src=" + "file:images/alertImage.png" + "/></html>");
		stressTestScriptImageLabel.setText("<html><img src=" + "file:images/alertImage.png" + "/></html>");
	}
	
	public void setPerformanceTest(TestPlan performanceTest){
		this.performanceTest = performanceTest;
		battery = null;
        stressTest = null;
        functionalTestRepresentationTextArea.setText("");
        functionalTestScriptTextArea.setText("");
        resetImages();
        if(performanceTest != null)
        	performanceTestRepresentationTextArea.setText(performanceTest.toString());
        else{        	
        	performanceTestRepresentationTextArea.setText("");
        	JOptionPane.showMessageDialog(null,"Representaçãoo abstrata do teste de desempenho vazia!","Error Message",JOptionPane.ERROR_MESSAGE);
        }
        stressTestRepresentationTextArea.setText("");
	}
	
	public void setStressTest(TestPlan stressTest){
		this.stressTest = stressTest;
		battery = null;
        performanceTest = null;
        functionalTestRepresentationTextArea.setText("");
        functionalTestScriptTextArea.setText("");
        performanceTestRepresentationTextArea.setText("");
        resetImages();
        if(stressTest != null)
        	stressTestRepresentationTextArea.setText(stressTest.toString());
        else{        	
        	stressTestRepresentationTextArea.setText("");
        	JOptionPane.showMessageDialog(null,"Representação abstrata do teste de estresse vazia!","Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
        }
	}
	
	public void setBattery(TestBattery battery) {
        this.battery = battery;
        System.out.println(this.battery);//
        performanceTest = null;
        stressTest = null;
        functionalTestRepresentationTextArea.setText("");
        functionalTestScriptTextArea.setText("");
        performanceTestRepresentationTextArea.setText("");
        stressTestRepresentationTextArea.setText("");
        resetImages();
        if(battery != null){
        	functionalTestRepresentationTextArea.setText(battery.toString());
        	SimpleGenerator generator = new SimpleGenerator(battery);		
    		performanceTest = generator.generateDefaultTestPlan();
    		stressTest = generator.generateDefaultTestPlan();
    		performanceTestRepresentationTextArea.setText(performanceTest.toString());
    		stressTestRepresentationTextArea.setText(stressTest.toString());
        }else{
        	JOptionPane.showMessageDialog(null,"Representação abstrata do teste funcional vazia! Você deve extrair uma repesentação com o extrator.","Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
        }
    }
	
	public JPanel getWrapper() {
		if (wrapper == null) {
			wrapper = new JPanel();
			wrapper.setLayout(new CardLayout());
			wrapper.add(getJContentPane(), FERRARE);
			wrapper.add(getGenesisWrapper(), GENESIS);
		}
		return wrapper;
	}
	public GenesisPanel getGenesisPanel() {
		if (genesisPanel == null) {
			genesisPanel = new GenesisPanel();	
			genesisPanel.setName("genesisPanel");
				
		}
		return genesisPanel;
	}

	public void extractFromFunctionalTest(String extractorName){
		LoaderJar lj = new LoaderJar();		 
		Class<?> classe = lj.loadJar2("extractors"+ Util.slash +extractorName, "extractors."+extractorName.substring(0,extractorName.indexOf(".jar")));
		BaseExtractor extractor;
		
        String filename = File.separator + "tmp";
        JFileChooser fc = new JFileChooser(new File(filename));
        fc.showOpenDialog(null);
        File file = fc.getSelectedFile();
        if(file != null){
            if (file.exists()) {
                FileInputStream fis = null;
                try {
                	extractor = (BaseExtractor)classe.newInstance();
                	battery = extractor.extract(file);
                	functionalTestRepresentationTextArea.setText(battery.toString());
                	SimpleGenerator generator = new SimpleGenerator(battery);		
            		performanceTest = generator.generateDefaultTestPlan();
            		stressTest = generator.generateDefaultTestPlan();
            		resetImages();
            		performanceTestRepresentationTextArea.setText(performanceTest.toString());
            		stressTestRepresentationTextArea.setText(stressTest.toString());            		
                    fis = new FileInputStream(file);
                    BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                    functionalTestScriptTextArea.setText("");
                    String scriptLine = in.readLine();
                    while (scriptLine != null) {
                    	functionalTestScriptTextArea.append(scriptLine);
                    	functionalTestScriptTextArea.append("\n");
                        scriptLine = in.readLine();
                    }
                    
                    String [] textMessages = {"Sim","Não"};  
                   
                    JOptionPane.showMessageDialog(null,"Extração feita com sucesso!","Mensagem de Informação",JOptionPane.INFORMATION_MESSAGE);
                    int answer = JOptionPane.showOptionDialog(null, "Deseja adicionar requisitos para a representação abstrata do teste funcional?", "Uma Pergunta...",   
                    		                 JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE,   
                    		                 null, textMessages, null); 
                    if(answer == 0){
                    	FunctionalRequirementFrame requirementFunctionalFrame = new FunctionalRequirementFrame(battery,ferrareView);
                    	requirementFunctionalFrame.setLocationRelativeTo(null);
                    	requirementFunctionalFrame.setVisible(true);
                    	functionalTestRequirementImageLabel.setText("<html><img src=" + "file:images/okImage.png" + "/></html>");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,"Erro na leitura do arquivo!","Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
                }catch(Exception e){
                	JOptionPane.showMessageDialog(null,"Erro na extração de dados do roteiro teste funcional!","Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
                }finally {
                    try {
                        fis.close();
                    } catch (IOException ex) {
                         JOptionPane.showMessageDialog(null,"Erro no fechamento do arquivo!","Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }else{
                     JOptionPane.showMessageDialog(null,"Arquivo não encontrado!","Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
            }
        }
	}
	
	public void generateFromFunctionalTestRepresentation(String generatorName){
		JCheckBox perfCb = new JCheckBox("Teste de Desempenho");  
		JCheckBox strCb = new JCheckBox("Teste de Estresse");
		String message = "O que você deseja gerar?";  
		Object[] params = {message, perfCb,strCb};  
		
		String [] textMessages = {"Sim","Não"};  
        
		int anwser = JOptionPane.showOptionDialog(null, params, "Gerando...", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,   
                null, textMessages, null);
		if(anwser == 0){
			LoaderJar lj = new LoaderJar();		 
			Class<?> classe = lj.loadJar2("generators"+ Util.slash +generatorName, "generators."+generatorName.substring(0,generatorName.indexOf(".jar")));
			BaseScripter generator;
		 
			if(perfCb.isSelected()){
				if (performanceTest != null) {
	                String filename = File.separator + "tmp";
	                JFileChooser fc = new JFileChooser(new File(filename));
	                fc.showSaveDialog(null);
	                File file = fc.getSelectedFile();
	                if (file != null) {
	                    try {
	                    	generator = (BaseScripter) classe.newInstance();
	                    	for(URLAccess ac : performanceTest.getPages()){
	                    		for(Field f : ac.getFields()){
	                    			System.out.println(f.getName()+"-:"+f.getReferenceToCSV());
	                    		}
	                    	}
	                    	
	                    	
	                        generator.parse(performanceTest, file.getAbsolutePath());
	                        JOptionPane.showMessageDialog(null, "Roteiro de teste de desempenho gerado com sucesso!", "Mensagem de Informação", JOptionPane.INFORMATION_MESSAGE);
	                    } catch (Exception e) {
	                        JOptionPane.showMessageDialog(null, "O roteiro do teste de desempenho não pôde ser gerado.\n"+e.getMessage(), "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
	                    }
	                }
	            } else {
	                JOptionPane.showMessageDialog(null, "Representação abstrata do teste de desempenho vazia!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
	            }
			}
			if(strCb.isSelected()){
				if (stressTest != null) {
	                String filename = File.separator + "tmp";
	                JFileChooser fc = new JFileChooser(new File(filename));
	                fc.showSaveDialog(null);
	                File file = fc.getSelectedFile();
	                if (file != null) {
	                    try {
	                    	generator = (BaseScripter) classe.newInstance();
	                        generator.parse(stressTest, file.getAbsolutePath());	                        
	                        JOptionPane.showMessageDialog(null, "Roteiro de teste de estresse gerado com sucesso!", "Mensagem de Informação", JOptionPane.INFORMATION_MESSAGE);
	                    } catch (Exception e) {
	                    	JOptionPane.showMessageDialog(null, "O roteiro do teste de estresse não pôde ser gerado.\n"+e.getMessage(), "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
	                    }
	                }
	            } else {
	            	JOptionPane.showMessageDialog(null, "Representação abstrata do teste de estresse vazia!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
	            }
			}
		}
		
	}
	
	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	public JFrame getJFrame() {		
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(809, 658);
			jFrame.setContentPane(getWrapper());
			jFrame.setTitle("FERRARE GT");
			jFrame.setLocationRelativeTo(null);
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {			
			abstractRepresentationsLabel = new JLabel();
			abstractRepresentationsLabel.setText("Representação Abstrata");
			functionalTestLabel = new JLabel();
			functionalTestLabel.setText("Teste Funcional");
			jContentPane = new JPanel();
			jContentPane.setLayout(new MigLayout("","[grow]","[grow]"));
			jContentPane.setName("jContentPane");
			jContentPane.add(functionalTestLabel, "wrap");
			jContentPane.add(getFunctionalTestScriptTextArea(), "wrap, grow");
			jContentPane.add(abstractRepresentationsLabel, "wrap");
			jContentPane.add(getAbstractRepresentationPane(), "wrap, grow");
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getExtractorMenu());
			jJMenuBar.add(getGenesisMenu());
			jJMenuBar.add(getGeneratorMenu());			
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("Arquivo");
			fileMenu.add(getOpenMenuItem());
			fileMenu.add(getSaveMenuItem());
			JSeparator separator = new JSeparator();
			fileMenu.add(separator);			
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getExtractorMenu() {
		if (extractorMenu == null) {
			extractorMenu = new JMenu();
			extractorMenu.setText("Extrator");
			extractorMenu.add(getExtractorSelectMenuItem());
		}
		return extractorMenu;
	}
	
	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getGeneratorMenu() {
		if (generatorMenu == null) {
			generatorMenu = new JMenu();
			generatorMenu.setText("Gerador");
			generatorMenu.add(getGeneratorSelectMenuItem());			
		}
		return generatorMenu;
	}
	
	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getGenesisMenu() {
		if (genesisMenu == null) {
			genesisMenu = new JMenu();
			genesisMenu.setText("Genesis");			
			genesisMenu.add(getGenesisMenuItem());
		}
		return genesisMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Ajuda");
			helpMenu.add(getAboutMenuItem());			
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getOpenMenuItem() {
		if (openMenuItem == null) {
			openMenuItem = new JMenuItem();
			openMenuItem.setText("Abrir");
			openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
					Event.CTRL_MASK, true));
			openMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					OpenOptionsDialog openOptionsDialog = new OpenOptionsDialog(battery,ferrareView);					
					openOptionsDialog.setLocationRelativeTo(null);
					openOptionsDialog.setModal(true);
					openOptionsDialog.setVisible(true);	
				}
			});
			
		}
		return openMenuItem;
	}
	
	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Salvar");
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
			saveMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SaveOptionsDialog saveOptionsDialog = new SaveOptionsDialog(battery,performanceTest,stressTest);
					saveOptionsDialog.setLocationRelativeTo(null);
					saveOptionsDialog.setModal(true);
					saveOptionsDialog.setVisible(true);
				}
			});
		}
		return saveMenuItem;
	}
		
	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Sair");
			exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
					Event.CTRL_MASK, true));
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}
	
	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExtractorSelectMenuItem(){
		if (extractorSelectMenuItem == null) {
			extractorSelectMenuItem = new JMenuItem();
			extractorSelectMenuItem.setText("Selecionar");
			extractorSelectMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,0));
			extractorSelectMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Object answer = JOptionPane.showInputDialog( null, "Selecione um extrator", "Extratores", JOptionPane.QUESTION_MESSAGE, null, getOptions("extractors") , 0);
					if(answer != null)
						extractFromFunctionalTest(answer.toString());
				}
			});
		}
		return extractorSelectMenuItem;
	}
	
	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getGeneratorSelectMenuItem(){
		if (generatorSelectMenuItem == null) {
			generatorSelectMenuItem = new JMenuItem();
			generatorSelectMenuItem.setText("Selecionar");
			generatorSelectMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,0));
			generatorSelectMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(battery != null ){						
						Object answer = JOptionPane.showInputDialog( null, "Selecione um gerador", "Geradores", JOptionPane.QUESTION_MESSAGE, null, getOptions("generators") , 0);
						if(answer != null)
							generateFromFunctionalTestRepresentation(answer.toString());
					}else{
						JOptionPane.showMessageDialog(null, "Representação abstrata do teste funcional vazia!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return generatorSelectMenuItem;
	}
	
	/**
	 * This method initializes combobox of Extractors or Generators	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private String[] getOptions(String path){
		 String posibleValues[];
		 File dir = new File(path);  
		 FilenameFilter filter = new FilenameFilter() {  
	         public boolean accept(File dir, String name) {  
	             return !name.startsWith(".") && name.endsWith(".jar");  
	         }  
	     };  		 
	     posibleValues = dir.list(filter);				
		return posibleValues;
	}
		
	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("Sobre");
			aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = getAboutDialog();
					aboutDialog.pack();
					Point loc = getJFrame().getLocation();
					loc.translate(20, 20);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog	
	 * 	
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("Sobre");
			aboutDialog.setSize(new Dimension(384, 224));
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new MigLayout());
			aboutContentPane.add(getAboutVersionLabel(), null);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getAboutVersionLabel() {
		if (aboutVersionLabel == null) {
			aboutVersionLabel = new JLabel();
			aboutVersionLabel.setText("<html><center><u>FERRARE GT</u></center><br><br> <u>FERR</u>amenta para <u>A</u>utomação " +
					"de testes de <u>R</u>equisitos de<br> desempenho e <u>E</u>stresse<br><br> Versão: Genesis Turbo 1.0" +
					"<br><br>Desenvolvedores: Ismayle de Sousa; Alcemir Rodrigues<br><br> Site: http://www.ufpi.br/pasn</html>");
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes functionalTestScriptTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JScrollPane getFunctionalTestScriptTextArea() {
		if (functionalTestScriptTextArea == null) {
			functionalTestScriptTextArea = new JTextArea(15,70);
			functionalTestScriptTextArea.setText("");
			functionalTestScriptTextArea.setEditable(false);
		}
		JScrollPane scroll = new JScrollPane(functionalTestScriptTextArea);
		return scroll;
	}
	
	/**
	 * This method initializes abstractRepresentationPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getAbstractRepresentationPane() {
		if (abstractRepresentationPane == null) {
			abstractRepresentationPane = new JTabbedPane();
			abstractRepresentationPane.addTab("Teste Funcional", null, getFunctionalPanel(), null);
			abstractRepresentationPane.addTab("Teste de Desempenho", null, getPerformancePanel(), null);
			abstractRepresentationPane.addTab("Teste de Estresse", null, getStressPanel(), null);
		}
		return abstractRepresentationPane;
	}

	/**
	 * This method initializes functionalPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getFunctionalPanel() {
		if (functionalPanel == null) {
			functionalTestRequirementImageLabel = new JLabel();
			functionalTestRequirementImageLabel.setText("<html><img src=" + "file:images/alertImage.png" + "/></html>");
			functionalPanel = new JPanel();			
			functionalPanel.setLayout(new MigLayout("","[center,grow][][]","[center,grow]"));
			functionalPanel.add(getFunctionalTestRepresentationTextArea(), "grow");
			functionalPanel.add(getFunctionalTestRequirementButton(), null);
			functionalPanel.add(functionalTestRequirementImageLabel, null);
		}
		return functionalPanel;
	}
		
	/**
	 * This method initializes performancePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPerformancePanel() {
		if (performancePanel == null) {
			performanceTestScriptImageLabel = new JLabel();
			performanceTestScriptImageLabel.setText("<html><img src=" + "file:images/alertImage.png" + "/></html>");
			performanceTestRequirementImageLabel = new JLabel();
			performanceTestRequirementImageLabel.setText("<html><img src=" + "file:images/alertImage.png" + "/></html>");
			performancePanel = new JPanel();
			performancePanel.setLayout(new MigLayout("","[center,grow][center][center]","[center,grow][grow]"));
			performancePanel.add(getPerformanceTestRepresentationTextArea(), "span 1 2,grow");
			performancePanel.add(getPerformanceTestRequirementButton(), "gaptop 30");
			performancePanel.add(performanceTestRequirementImageLabel, "gaptop 30,wrap");
			performancePanel.add(getPerformanceTestScriptButton(), "growx");		
			performancePanel.add(performanceTestScriptImageLabel, null);
		}
		return performancePanel;
	}

	/**
	 * This method initializes stressPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getStressPanel() {
		if (stressPanel == null) {
			stressTestScriptImageLabel = new JLabel();
			stressTestScriptImageLabel.setText("<html><img src=" + "file:images/alertImage.png" + "/></html>");
			stressTestRequirementImageLabel = new JLabel();
			stressTestRequirementImageLabel.setText("<html><img src=" + "file:images/alertImage.png" + "/></html>");
			stressPanel = new JPanel();
			stressPanel.setLayout(new MigLayout("","[center,grow][center][center]","[center,grow][grow]"));
			stressPanel.add(getStressTestRepresentationTextArea(), "span 1 2,grow");
			stressPanel.add(getStressTestRequirementsButton(), "gaptop 30");
			stressPanel.add(stressTestRequirementImageLabel, "gaptop 30,wrap");
			stressPanel.add(getStressTestScriptButton(), "growx");			
			stressPanel.add(stressTestScriptImageLabel, null);
		}
		return stressPanel;
	}

	/**
	 * This method initializes functionalTestRepresentationTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JScrollPane getFunctionalTestRepresentationTextArea() {
		if (functionalTestRepresentationTextArea == null) {
			functionalTestRepresentationTextArea = new JTextArea(15,57);
			functionalTestRepresentationTextArea.setEditable(false);
		}
		JScrollPane scroll = new JScrollPane(functionalTestRepresentationTextArea);
		return scroll;
	}

	/**
	 * This method initializes functionalTestRequirementButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getFunctionalTestRequirementButton() {
		if (functionalTestRequirementButton == null) {
			functionalTestRequirementButton = new JButton();
			functionalTestRequirementButton.setText("Requisitos");
			functionalTestRequirementButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(battery == null){
						JOptionPane.showMessageDialog(null, "Representação abstrata do teste funcional vazia!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);						
					}else{
						System.out.println(battery);
						for (TestCase tc : battery.getTestCases()){
							  System.out.println("\ntestCase");
							  for (Field f : tc.getTestProcedure().getFields()){
								  System.out.println(f.getName());
								  //System.out.println(">"+f.getRequirements());
							  }
						}
						///
						FunctionalRequirementFrame requirementFunctionalDialog = new FunctionalRequirementFrame(battery,ferrareView);
			        	requirementFunctionalDialog.setLocationRelativeTo(null);
			        	requirementFunctionalDialog.setModal(true);
			        	requirementFunctionalDialog.setVisible(true);
			    		functionalTestRequirementImageLabel.setText("<html><img src=" + "file:images/okImage.png" + "/></html>");
					}
				}
			});        	
		}
		return functionalTestRequirementButton;
	}

	/**
	 * This method initializes performanceTestRepresentationTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JScrollPane getPerformanceTestRepresentationTextArea() {
		if (performanceTestRepresentationTextArea == null) {
			performanceTestRepresentationTextArea = new JTextArea(15,57);
			performanceTestRepresentationTextArea.setEditable(false);
		}
		JScrollPane scroll = new JScrollPane(performanceTestRepresentationTextArea);
		return scroll;
	}

	/**
	 * This method initializes performanceTestRequirementButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getPerformanceTestRequirementButton() {
		if (performanceTestRequirementButton == null) {
			performanceTestRequirementButton = new JButton();
			performanceTestRequirementButton.setText("Requisitos");
			performanceTestRequirementButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(performanceTest == null){
						JOptionPane.showMessageDialog(null, "Representação abstrata do teste de desempenho vazia!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);						
					}else{
						TestPlanRequirementFrame performanceRequirementDialog = new TestPlanRequirementFrame(performanceTest,"Teste de Desempenho",ferrareView);
			        	performanceRequirementDialog.setLocationRelativeTo(null);
			        	performanceRequirementDialog.setModal(true);
			        	performanceRequirementDialog.setVisible(true);
			        	performanceTestRequirementImageLabel.setText("<html><img src=" + "file:images/okImage.png" + "/></html>");
					}
				}
			});     
		}
		return performanceTestRequirementButton;
	}

	/**
	 * This method initializes performanceTestScriptButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getPerformanceTestScriptButton() {
		if (performanceTestScriptButton == null) {
			performanceTestScriptButton = new JButton();
			performanceTestScriptButton.setText("Roteiro");
			performanceTestScriptButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(performanceTest == null){
						JOptionPane.showMessageDialog(null, "Representação abstrata do teste de desempenho vazia!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);						
					}else{
						TestPlanScriptFrame performanceTestScriptDialog = new TestPlanScriptFrame(performanceTest,"Teste de Desempenho",ferrareView);
						performanceTestScriptDialog.setLocationRelativeTo(null);
						performanceTestScriptDialog.setModal(true);
						performanceTestScriptDialog.setVisible(true);
						performanceTestScriptImageLabel.setText("<html><img src=" + "file:images/okImage.png" + "/></html>");
					}
				}
			});    
		}
		return performanceTestScriptButton;
	}

	/**
	 * This method initializes stressTestRepresentationTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JScrollPane getStressTestRepresentationTextArea() {
		if (stressTestRepresentationTextArea == null) {
			stressTestRepresentationTextArea = new JTextArea(15,57);
			stressTestRepresentationTextArea.setEditable(false);
		}
		JScrollPane scroll = new JScrollPane(stressTestRepresentationTextArea);
		return scroll;
	}

	/**
	 * This method initializes stressTestRequirementsButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getStressTestRequirementsButton() {
		if (stressTestRequirementsButton == null) {
			stressTestRequirementsButton = new JButton();			
			stressTestRequirementsButton.setText("Requisitos");
			stressTestRequirementsButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(stressTest == null){
						JOptionPane.showMessageDialog(null, "Representação abstrata do teste de estresse vazia!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);						
					}else{
						TestPlanRequirementFrame stressRequirementDialog = new TestPlanRequirementFrame(stressTest,"Teste de Estresse",ferrareView);
			        	stressRequirementDialog.setLocationRelativeTo(null);
			        	stressRequirementDialog.setModal(true);
			        	stressRequirementDialog.setVisible(true);
			        	stressTestRequirementImageLabel.setText("<html><img src=" + "file:images/okImage.png" + "/></html>");
					}
				}
			});   
		}
		return stressTestRequirementsButton;
	}

	/**
	 * This method initializes stressTestScriptButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getStressTestScriptButton() {
		if (stressTestScriptButton == null) {
			stressTestScriptButton = new JButton();
			stressTestScriptButton.setText("Roteiro");
			stressTestScriptButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(stressTest == null){
						JOptionPane.showMessageDialog(null, "Representação abstrata do teste de estresse vazia!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE);						
					}else{
						TestPlanScriptFrame stressTestScriptDialog = new TestPlanScriptFrame(stressTest,"Teste de Estresse",ferrareView);
						stressTestScriptDialog.setLocationRelativeTo(null);
						stressTestScriptDialog.setModal(true);
						stressTestScriptDialog.setVisible(true);
						stressTestScriptImageLabel.setText("<html><img src=" + "file:images/okImage.png" + "/></html>");
					}
				}
			});   
		}
		return stressTestScriptButton;
	}

	/**
	 * This method initializes genesisMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getGenesisMenuItem() {
		if (genesisMenuItem == null) {
			genesisMenuItem = new JMenuItem();
			genesisMenuItem.setText("Iniciar");
			genesisMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0));
			genesisMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("genesisMenuItem - actionPerformed()");
					
					if (battery != null) {
						CardLayout cl = (CardLayout)(wrapper.getLayout());
					    cl.show(wrapper, GENESIS);
					    
					    genesisPanel.fillTblMap(battery);		
					    genesisPanel.setPerformanceTest(performanceTest);
					    genesisPanel.setStressTest(stressTest);
					}else{
						JOptionPane.showMessageDialog(null,"Representação abstrata do teste funcional vazia!", "Mensagem de Erro", JOptionPane.ERROR_MESSAGE,null);
					}
					
				}
			});
		}
		return genesisMenuItem;
	}

	/**
	 * This method initializes genesisWrapper	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getGenesisWrapper() {
		if (genesisWrapper == null) {
			genesisWrapper = new JPanel();
			genesisWrapper.setLayout(new BoxLayout(getGenesisWrapper(), BoxLayout.Y_AXIS));
			genesisWrapper.add(getGenesisPanel(), null);
			genesisWrapper.add(getBtnExit(), null);
		}
		return genesisWrapper;
	}
	/**
	 * This method initializes btnExit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton();
			btnExit.setText("Voltar ao Extrator");
			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("btnExit - actionPerformed()"); 
					
					CardLayout cl = (CardLayout)(wrapper.getLayout());
				    cl.show(wrapper, FERRARE);
				}
			});
		}
		return btnExit;
	}
	/**
	 * Launches this application
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {		
				SplashScreen splashScreen = new SplashScreen();
				SplashThread t = new SplashThread(splashScreen); 
		        t.start();
				/*FerrareView application = new FerrareView();				
				application.getJFrame().setVisible(true);*/
			}
		});
	}

}

