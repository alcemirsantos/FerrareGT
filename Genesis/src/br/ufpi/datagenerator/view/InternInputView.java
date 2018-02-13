package br.ufpi.datagenerator.view;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.ufpi.datagenerator.domain.Table;
import br.ufpi.mapper.beans.MappedInput;
import br.ufpi.mapper.control.MapperController;

public class InternInputView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton jButton = null;
	private ArrayList<TableComponents> tableComponentsArrayList = new ArrayList<TableComponents>();

	private int lim = 10;

	public void setMapping(HashMap<String, Table> hashmap) {
		for (TableComponents tableComponents : tableComponentsArrayList) {
			tableComponents.sethashMap(hashmap);
		}
	}

	public HashMap<String, Collection<String>> getTableNames() throws Exception {

		HashMap<String, Collection<String>> tables = new HashMap<String, Collection<String>>();
//
//		for (TableComponents tableComponents : tableComponentsArrayList) {
//
//			if (tables.containsKey(tableComponents.getTableName())) {
//				throw new Exception("Nomes da tabelas repetidos");
//			}
//
//			tables.put(tableComponents.getTableName(), tableComponents
//					.getColumnNames());
//		}
//		
// inicio da integração...
		
		ArrayList<MappedInput> mappedImputs = MapperController.getMapedInputs();
		String tableName, atributo;
		
		for (MappedInput mappedInput : mappedImputs) {
			tableName = mappedInput.getEntity().getTableName();
			atributo = mappedInput.getEntity().getColumnName();
			
			if( !tables.containsKey( tableName ) ){
				tables.put(tableName, new ArrayList<String>());				
			}
			
			tables.get(tableName).add( atributo );	
		}
		
		return tables;
	}

	public ArrayList<TableComponents> getTables() {
		return tableComponentsArrayList;
	}

	/**
	 * This is the default constructor
	 */
	public InternInputView() {
		super();
		initialize();
	}

	private int lt = 180;

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(null);
		this.setLocation(new Point(24, 135));
		this.setPreferredSize(new Dimension(700, 300));
		this.setSize(new Dimension(700, 300));
		putTables(null);
		this.repaint();
	}

	public void putTables(HashMap<String, Table> hashmap) {

		TableComponents tableComponents = null;
		if (hashmap != null)
			tableComponents = new TableComponents(hashmap);
		else
			tableComponents = new TableComponents();
		tableComponentsArrayList.add(tableComponents);
		tableComponents.setLocation(40, lim);
		tableComponents.setSize(new Dimension(620, 161));
		this.setPreferredSize(new Dimension(680, lt));
		this.setSize(292, lt);

		this.add(tableComponents);

		lim = lim + 170;

		System.out.println("nova  tabela");

		lt += 170;
		this.repaint();
		tableComponents.repaint();

	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(243, 11, 97, 10));
		}
		return jButton;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
