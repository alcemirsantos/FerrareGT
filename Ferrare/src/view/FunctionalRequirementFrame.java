package view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import functionalTestRepresentation.Field;
import functionalTestRepresentation.TestBattery;
import functionalTestRepresentation.TestCase;
import functionalTestRepresentation.requirements.Requirement;

/**
 * Classe para o frame que adiciona requisitos aos testes funcionais  
 * @author Ismayle de Sousa Santos
 */
public class FunctionalRequirementFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel pagesLabel = null;
	private JLabel fieldsLabel = null;
	private JLabel requirementsLabel = null;	
	private JList pagesList = null;
	private JList fieldsList = null;
	private JList requirementsList = null;
	private JButton addButton = null;
	private JButton removeButton = null;
	private FunctionalRequirementFrame reqFrame = this;
	
	
	private FerrareView jFrame = null;
	private TestBattery	battery = null;
	private HashMap<String, List<Field>>  hmpPagesFields = new LinkedHashMap<String, List<Field>>(); 
	private String fieldSelected;
	private String pageSelected;
	
	public void setjFrame(FerrareView jFrame) {
		this.jFrame = jFrame;
	}

	public FerrareView getjFrame() {
		return jFrame;
	}
	
	public void addRequirementToField(Requirement<?> req){
		for(TestCase tc : battery.getTestCases()){
    		String page = tc.getTestProcedure().getURL()+"/"+tc.getTestProcedure().getPath();
    		if(page.equals(pageSelected)){
    			for(Field tcField : tc.getTestProcedure().getFields()){    		
    	    		if(tcField.getName().equals(fieldSelected)){    			
    	    			tcField.addRequirement(req);
    	    			break;
    	    		}
    	    	}
    		}
    	}
    	updateRequirementList(pageSelected, fieldSelected);
	}
	
	public void removeRequirementToField(int reqIndex){
		
		for(TestCase tc : battery.getTestCases()){    		
    		String page = tc.getTestProcedure().getURL()+"/"+tc.getTestProcedure().getPath();
    		if(page.equals(pageSelected)){
    			for(Field tcField : tc.getTestProcedure().getFields()){    		
    	    		if(tcField.getName().equals(fieldSelected)){    			
    	    			tcField.removeRequirement(reqIndex);
    	    			System.out.println("removido");
    	    			break;
    	    		}
    	    	}
    		}
    	}
    	updateRequirementList(pageSelected, fieldSelected);
	}
	
	public void addPage(String page, List<Field> fields){
		if(!hmpPagesFields.containsKey(page)){
			hmpPagesFields.put(page, fields);
		}else{
			for(Field field : fields){
				addFieldToPage(field, page);
			}			
		}	
    }

	public boolean containsFieldName(List<Field> fieldList, String fieldName){
		boolean answer = false;
		for (Field f : fieldList) {
			if(f.getName().equals(fieldName)){
				answer = true;
				break;
			}
		}
		return answer;
	}
	
    public void addFieldToPage(Field field, String page){
    	List<Field> fieldList = (List<Field>) hmpPagesFields.get(page);
    	if(!containsFieldName(fieldList,field.getName()))
    		fieldList.add(field);
    	hmpPagesFields.put(page, fieldList);
    }

    public void setPageFields(LinkedList<Field> fields, String page){
    	hmpPagesFields.put(page, fields);
    }
    
    public List<Field> getFields(String page){
        return (List<Field>) hmpPagesFields.get(page);
    }
    
    private void updatePageList(){
	   	DefaultListModel list = new DefaultListModel();
    	Set<String> pageskeys = (Set<String>) hmpPagesFields.keySet();		
		Iterator<String> it = pageskeys.iterator();
		while(it.hasNext()){			
			list.addElement(it.next());
		}
		pagesList = new JList();
    	pagesList.setModel(list);	
    }
    
    private void updateFieldsList(String page){    	
    	DefaultListModel list = new DefaultListModel();
    	List<Field> fields = (List<Field>) hmpPagesFields.get(page);
    	for(Field field : fields){    		
    		list.addElement(field.getName());
    	}
    	fieldsList.setModel(list);
    	requirementsList.setModel(new DefaultListModel());
    }
    
    private void updateRequirementList(String page, String fieldName){    	
    	DefaultListModel list = new DefaultListModel();
    	List<Field> fields = (List<Field>) hmpPagesFields.get(page);
    	for(Field field : fields){    		
    		if(field.getName().equals(fieldName)){    			
    			for(Requirement<?> req: field.getRequirements()){
    				list.addElement(req.toString());
    			}
    			break;
    		}
    	}
    	requirementsList.setModel(list);
    }
    
	/**
	 * This is the default constructor
	 */
	public FunctionalRequirementFrame(TestBattery battery, FerrareView jFrame) {
		super();
		this.battery = battery;
		this.setjFrame(jFrame);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		initializePageList();
		this.setSize(800, 600);
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Requisitos dos campos do teste funcional");
		this.setLocationRelativeTo(null);
	}

	private void initializePageList() {
		if(battery != null){
			for(TestCase testcase : battery.getTestCases()){
	            String url = testcase.getTestProcedure().getURL();
	            String path = testcase.getTestProcedure().getPath();            
	            addPage(url+"/"+path, testcase.getTestProcedure().getFields());            
	        }
			updatePageList();
		}
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			requirementsLabel = new JLabel();
			requirementsLabel.setText("Requisitos");
			fieldsLabel = new JLabel();
			fieldsLabel.setText("Campos");
			pagesLabel = new JLabel();
			pagesLabel.setText("Páginas");
			jContentPane = new JPanel();
			jContentPane.setLayout(new MigLayout("","[grow][][]","[][grow][][grow][]"));
			jContentPane.add(pagesLabel, "wrap");
			jContentPane.add(getPagesList(), "span,grow,wrap");
			jContentPane.add(fieldsLabel, null);
			jContentPane.add(requirementsLabel, "wrap");
			jContentPane.add(getFieldsList(), "span 1 2,grow");			
			jContentPane.add(getRequirementsList(), "span, grow,wrap");			
			jContentPane.add(getAddButton(), "grow,align right");
			jContentPane.add(getRemoveButton(), "align left");
		}
		return jContentPane;
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
				 if (e.getValueIsAdjusting())
		              //ainda selecionando
		         return;                
				 if (pagesList.isSelectionEmpty()) {                  
                        System.out.println("nenhuma selecao");
				 } else {
						System.out.println(pagesList.getSelectedValue());
						pageSelected = pagesList.getSelectedValue().toString();
						updateFieldsList(pagesList.getSelectedValue().toString());
				 }
			}  
        });
		pagesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(pagesList);
		return scroll;
	}

	/**
	 * This method initializes pagesList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JScrollPane getFieldsList() {
		if (fieldsList == null) {			
			fieldsList = new JList();	
		}
		fieldsList.addListSelectionListener(new ListSelectionListener() {  
			@Override
			public void valueChanged(ListSelectionEvent e) {
				 if (e.getValueIsAdjusting())
		              //ainda selecionando
		         return;                
				 if (fieldsList.isSelectionEmpty()) {                  
                        System.out.println("nenhuma selecao");
				 } else {
						updateRequirementList(pagesList.getSelectedValue().toString(),fieldsList.getSelectedValue().toString());
						fieldSelected = fieldsList.getSelectedValue().toString();
				 }
			}  
        });
		fieldsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(fieldsList);
		return scroll;
	}
	
	/**
	 * This method initializes pagesList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JScrollPane getRequirementsList() {
		if (requirementsList == null) {
			requirementsList = new JList();
		}
		requirementsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(requirementsList);		
		return scroll;
	}

	/**
	 * This method initializes addButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton("Adicionar");
			addButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					 if(fieldsList.getSelectedIndex() >= 0){
						 AddRequirementFrame addFrame = new AddRequirementFrame(reqFrame);
						 addFrame.setLocationRelativeTo(null);
						 addFrame.setModal(true);
						 addFrame.setVisible(true);
					 }else{
						 JOptionPane.showMessageDialog(null,"Primeiro selecione um campo na lista de campos!","Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
					 }
				}
			});
		}
		return addButton;
	}

	/**
	 * This method initializes removeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRemoveButton() {
		if (removeButton == null) {
			removeButton = new JButton("Remover");
			removeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					 if(requirementsList.getSelectedIndex() >= 0){
						 removeRequirementToField(requirementsList.getSelectedIndex());
					 }else{
						 JOptionPane.showMessageDialog(null,"Primeiro selecione um requisito na lista de requisitos!","Mensagem de Erro",JOptionPane.ERROR_MESSAGE);
					 }
				}
			});
		}
		return removeButton;
	}

}
