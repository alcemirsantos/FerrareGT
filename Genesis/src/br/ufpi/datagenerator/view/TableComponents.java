package br.ufpi.datagenerator.view;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.SoftBevelBorder;

import br.ufpi.datagenerator.domain.Table;

public class TableComponents extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel jLabel = null;
	private JButton jButton = null;
	private JScrollPane jScrollPane = null;

	HashMap<String, Table> mapping = null; // @jve:decl-index=0:

	public void sethashMap(HashMap<String, Table> mapping) {
		this.mapping = mapping;

		Set<String> tableNames = mapping.keySet();
		int i = 0;
		String tablesNames[] = new String[mapping.size()];
		for (String string : tableNames) {
			tablesNames[i] = string;
			i++;
		}

		jComboBox.setEditable(true);

		this.remove(jComboBox);

		jComboBox = null;

		this.add(getJComboBox(tablesNames), null);

		internTableComponents.putColumnNames(mapping.get((String) jComboBox
				.getSelectedItem()));

		jComboBox.repaint();

		jButton.setEnabled(true);

		this.repaint();
	}

	public String getTableName() {
		return (String) jComboBox.getSelectedItem();
	}

	public Collection<String> getColumnNames() throws Exception {
		return internTableComponents.getColumnNames();

	}

	/**
	 * This is the default constructor
	 */
	public TableComponents() {
		super();
		initialize();
	}

	public TableComponents(HashMap<String, Table> map) {
		super();
		this.mapping = map;
		initialize();
	}

	private InternTableComponents internTableComponents = null;
	private JComboBox jComboBox = null;

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(11, 9, 131, 25));
		jLabel.setText("Nome da Tabela :");
		this.setSize(601, 161);
		this.setLayout(null);
		this.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		this.add(jLabel, null);

		this.add(getJButton(), null);
		this.add(getJScrollPane(), null);
		internTableComponents = new InternTableComponents();

		if (mapping == null)
			this.add(getJComboBox(), null);
		else {

			Set<String> tableNames = mapping.keySet();
			int i = 0;
			String tablesNames[] = new String[mapping.size()];
			for (String string : tableNames) {
				tablesNames[i] = string;
				i++;
			}
			this.add(getJComboBox(tablesNames), null);
			getJButton().setEnabled(true);
			internTableComponents.putColumnNames(mapping.get(tablesNames[0]));
		}

		jScrollPane.setViewportView(internTableComponents);
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setEnabled(false);
			jButton.setBounds(new Rectangle(251, 39, 143, 20));
			jButton.setText("adicionar Coluna");
			jButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					Table table = mapping.get((String) jComboBox
							.getSelectedItem());

					internTableComponents.putComponents(table);

					internTableComponents.repaint();

					jScrollPane.scrollRectToVisible(new Rectangle(10, 10, 1000,
							1000));
					jScrollPane.repaint();
					repaint();

				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(28, 65, 571, 89));
			jScrollPane.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setEnabled(false);
			jComboBox.setBounds(new Rectangle(146, 10, 364, 24));
			jComboBox.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					Table table = mapping.get((String) jComboBox
							.getSelectedItem());

					internTableComponents.putColumnNames(table);
				}
			});
		}
		return jComboBox;
	}

	private JComboBox getJComboBox(String[] values) {

		jComboBox = new JComboBox(values);

		jComboBox.setBounds(new Rectangle(146, 10, 364, 24));

		jComboBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				Table table = mapping.get((String) jComboBox.getSelectedItem());

				internTableComponents.putColumnNames(table);
			}
		});

		return jComboBox;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
