package br.ufpi.datagenerator.view;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.ufpi.datagenerator.domain.Table;

public class InternTableComponents extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton jButton = null;
	ArrayList<ColumnComponents> colums = new ArrayList<ColumnComponents>();
	private int lin = 10;
	private Table table;

	public Collection<String> getColumnNames() throws Exception {
		Collection<String> collection = new ArrayList<String>();

		for (ColumnComponents columnComponents : colums) {
			if (collection.contains(columnComponents.getColumnName()))
				throw new Exception("nomes de coluna Repetidos");
			collection.add(columnComponents.getColumnName());
		}
		return collection;
	}

	public void putColumnNames(Table table) {
		this.table = table;
		for (ColumnComponents columnComponents : colums) {

			columnComponents.putColumnNames(table);

		}
	}

	/**
	 * This is the default constructor
	 */
	public InternTableComponents() {
		super();
		initialize();

	}

	public void putComponents(Table table) {

		ColumnComponents columnComponents = null;
		if (table == null)
			columnComponents = new ColumnComponents();
		else {
			columnComponents = new ColumnComponents(table);

		}

		colums.add(columnComponents);

		columnComponents.setLocation(0, lin);

		columnComponents.setSize(new Dimension(534, 24));

		this.setPreferredSize(new Dimension(492, lt));

		this.setSize(292, lt);

		System.out.println(lt);

		lin += 30;

		this.add(columnComponents);

		lt += 30;

		System.out.println(lt);

		this.repaint();

	}

	int lt = 40;

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(492, 300);
		this.setLayout(null);

		putComponents(null);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */

} // @jve:decl-index=0:visual-constraint="10,10"
