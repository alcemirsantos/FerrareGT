package br.ufpi.datagenerator.view;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.ufpi.datagenerator.domain.PrimitiveAttributes;
import br.ufpi.datagenerator.domain.Relationship;
import br.ufpi.datagenerator.domain.Table;

public class ColumnComponents extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel = null;
	private JComboBox jComboBox = null;

	public String getColumnName() {
		return (String) getJComboBox().getSelectedItem();
	}

	/**
	 * This is the default constructor
	 */
	public ColumnComponents() {
		super();
		initialize();
	}

	Table table = null;

	public ColumnComponents(Table table) {
		super();
		this.table = table;
		initialize();
	}

	public void putColumnNames(Table table) {
		this.table = table;

		this.remove(jComboBox);
		this.add(getJComboBox(table), null);

		System.out.println("jdfdhgfdhfg");

		repaint();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(539, 29);
		this.setLayout(null);
		this.add(getJLabel(), null);
		if (table == null)
			this.add(getJComboBox(), null);
		else {
			this.add(getJComboBox(table), null);
		}
	}

	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new JLabel();
			jLabel.setText("Nome da Coluna");
			jLabel.setBounds(new Rectangle(11, 4, 94, 19));
		}
		return jLabel;
	}

	private String[] getColumnNames(Table table) {
		ArrayList<String> columnNames = new ArrayList<String>();

		for (PrimitiveAttributes attributes : table.getPrimitiveAtributes()) {
			columnNames.add(attributes.getName());
		}

		for (Relationship raRelationship : table.getRelationship()) {

			Set<String> names = raRelationship.getName().keySet();

			for (String string : names) {
				columnNames.add(string);
			}

		}

		String[] str = new String[columnNames.size()];

		columnNames.toArray(str);
		return str;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(110, 3, 420, 22));
		}
		return jComboBox;
	}

	private JComboBox getJComboBox(Table table) {

		jComboBox = new JComboBox(getColumnNames(table));
		jComboBox.setBounds(new Rectangle(110, 3, 420, 22));

		return jComboBox;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
