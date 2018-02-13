package br.ufpi.genesis.view;

import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Event;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.KeyStroke;
import java.awt.Point;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JTable;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import br.ufpi.datagenerator.creator.PersistenceGenerator;
import br.ufpi.datagenerator.creator.RequiredTables;
import br.ufpi.datagenerator.domain.Table;
import br.ufpi.datagenerator.domain.connectors.ConnectionFactory;
import br.ufpi.datagenerator.initialconfiguration.DataBaseParameters;
import br.ufpi.datagenerator.initialconfiguration.InitialValues;
import br.ufpi.datagenerator.tablescans.PersistenceDiscoverer;
import br.ufpi.datagenerator.util.IrregularDateException;
import br.ufpi.datagenerator.util.NotKnowTypeException;
import br.ufpi.datagenerator.view.TableComponents;
import br.ufpi.mapper.beans.BDEntity;
import br.ufpi.mapper.beans.MappedInput;
import br.ufpi.mapper.control.MapperController;

public class View extends JFrame{

	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem htmlMenuItem = null;
	private JDialog aboutDialog = null;
	private JPanel aboutContentPane = null;
	private JLabel aboutVersionLabel = null;
	private JPanel pnlConectar = null;
	private JPanel pnlMapa = null;
	private JPanel pnlReplicar = null;
	private JLabel lblSchema = null;
	private JTextField txtSchema = null;
	private JLabel lblUser = null;
	private JTextField txtUser = null;
	private JLabel lblPassword = null;
	private JPasswordField txtPassword = null;
	private JButton btnConectar = null;
	private JButton btnResetar = null;
	private JScrollPane jScrollPane = null;
	private JLabel lblQuantidade = null;
	private JTextField txtQuantidade = null;
	private JPanel jPanel3 = null;
	private JProgressBar jProgressBar = null;
	private JButton btnMapear = null;
	private JButton btnReplicar = null;
	private Pattern p;
	private Matcher m;
	private HashMap<String, Table> map = null;
	private Connection connection = null;
	private Object[][] data = null;
	private JTable tblTabMap = null;
	private ArrayList<TableComponents> tableComponentsArrayList = new ArrayList<TableComponents>();  //  @jve:decl-index=0:
	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(911, 362);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("Genesis");
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
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getPnlConectar(), BorderLayout.NORTH);
			jContentPane.add(getPnlMapa(), BorderLayout.CENTER);
			jContentPane.add(getPnlReplicar(), BorderLayout.SOUTH);
			
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
			fileMenu.add(getHTMLMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
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
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Sair");
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
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
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
			aboutDialog.setTitle("About");
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
			aboutContentPane.setLayout(new BorderLayout());
			aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
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
			aboutVersionLabel.setText("Version 1.0");
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getHTMLMenuItem() {
		if (htmlMenuItem == null) {
			htmlMenuItem = new JMenuItem();
			htmlMenuItem.setText("HTML");
			htmlMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
					Event.CTRL_MASK, true));
			htmlMenuItem
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("htmlMetuItem - actionPerformed()"); 
					JFileChooser chooser = new JFileChooser();
					// Note: source for ExampleFileFilter can be found
					// in FileChooserDemo,
					// under the demo/jfc directory in the Java 2 SDK,
					// Standard Edition.
					// ExampleFileFilter filter = new
					// ExampleFileFilter();
					// filter.addExtension("jpg");
					// filter.addExtension("gif");
					// filter.setDescription("JPG & GIF Images");
					// chooser.setFileFilter(filter);
					int returnVal = chooser.showOpenDialog(View.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						System.out
								.println("You chose to open this file: "
										+ chooser.getSelectedFile()
												.getName());
					}

					mostra(
							chooser.getSelectedFile().getPath()
									.replaceAll(
											chooser.getSelectedFile()
													.getName(), ""),
							chooser.getSelectedFile().getName());

					lblQuantidade.setEnabled(true);
					txtQuantidade.setEnabled(true);
					btnMapear.setEnabled(true);
					jScrollPane.setEnabled(true);
					tblTabMap.setEnabled(true);

				}

			});
		}
		return htmlMenuItem;
	}

	/**
	 * This method initializes pnlConectar	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlConectar() {
		if (pnlConectar == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Banco de dados:");
			jLabel = new JLabel();
			jLabel.setText("Host:");
			lblPassword = new JLabel();
			lblPassword.setText("Senha:");
			lblUser = new JLabel();
			lblUser.setText("Usuário:");
			lblSchema = new JLabel();
			lblSchema.setText("Schema:");
			pnlConectar = new JPanel();
			pnlConectar.setLayout(new FlowLayout());
			pnlConectar.add(jLabel, null);
			pnlConectar.add(getTxtHost(), null);
			pnlConectar.add(jLabel1, null);
			pnlConectar.add(getCmbDatabases(), null);
			pnlConectar.add(lblSchema, null);
			pnlConectar.add(getTxtSchema(), null);
			pnlConectar.add(lblUser, null);
			pnlConectar.add(getTxtUser(), null);
			pnlConectar.add(lblPassword, null);
			pnlConectar.add(getPwdPassword(), null);
			pnlConectar.add(getBtnConectar(), null);
			pnlConectar.add(getBtnResetar(), null);
		}
		return pnlConectar;
	}

	/**
	 * This method initializes pnlMapa	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlMapa() {
		if (pnlMapa == null) {
			pnlMapa = new JPanel();
			pnlMapa.setLayout(new BorderLayout());
			pnlMapa.add(getJScrollPane(), BorderLayout.CENTER);
			pnlMapa.repaint();
		}
		return pnlMapa;
	}

	/**
	 * This method initializes pnlReplicar	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlReplicar() {
		if (pnlReplicar == null) {
			lblQuantidade = new JLabel();
			lblQuantidade.setText("Quantidade de Cópias:");
			lblQuantidade.setHorizontalAlignment(SwingConstants.RIGHT);
			pnlReplicar = new JPanel();
			pnlReplicar.setLayout(new BorderLayout());
			pnlReplicar.add(lblQuantidade, BorderLayout.CENTER);
			pnlReplicar.add(getTxtQuantidade(), BorderLayout.EAST);
			pnlReplicar.add(getJPanel3(), BorderLayout.SOUTH);
		}
		return pnlReplicar;
	}

	/**
	 * This method initializes txtSchema	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtSchema() {
		if (txtSchema == null) {
			txtSchema = new JTextField();
			txtSchema.setText("biblioteca");
			txtSchema.setPreferredSize(new Dimension(80, 20));
		}
		return txtSchema;
	}

	/**
	 * This method initializes txtUser	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtUser() {
		if (txtUser == null) {
			txtUser = new JTextField();
			txtUser.setText("root");
			txtUser.setPreferredSize(new Dimension(80, 20));
		}
		return txtUser;
	}

	/**
	 * This method initializes pwdPassword	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getPwdPassword() {
		if (txtPassword == null) {
			txtPassword = new JPasswordField();
			txtPassword.setText("askano88");
			txtPassword.setPreferredSize(new Dimension(80, 20));
		}
		return txtPassword;
	}

	/**
	 * This method initializes btnConectar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnConectar() {
		if (btnConectar == null) {
			btnConectar = new JButton();
			btnConectar.setText("Conectar");
			
			btnConectar
			.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("btnConectar - actionPerformed()");
					
					if (txtSchema.getText().equals("")) {
						JOptionPane
								.showMessageDialog(
										null,
										"CUIDADO: você não deve deixar o campo 'Schema' em branco.",
										"Erro",
										JOptionPane.INFORMATION_MESSAGE);
					} else {
						MapperController
								.setBDSchematoConnect(txtSchema.getText());

						MapperController.setUserOfBD(txtUser.getText());
						MapperController.setBDUserPassoword(txtPassword.getText());
						txtSchema.setEditable(false);
						txtUser.setEditable(false);
						
						txtPassword.setEditable(false);
						htmlMenuItem.setEnabled(true);
						btnResetar.setEnabled(true);
						btnConectar.setEnabled(false);
						btnMapear.setEnabled(true);
						jScrollPane.setEnabled(true);
						
						
						String errorMessage = null;

						String rootName = txtUser.getText();
						String senha = txtPassword.getText();
						String hostName = txtHost.getText();
						String schemaName = txtSchema.getText();
						String dbName = (String) cmbDatabases.getSelectedItem();

						DataBaseParameters dataBaseParameters = null;
						
						for (DataBaseParameters dataBaseParameters2 : values) {
							if (dataBaseParameters2.getDataBaseName()
									.equals(dbName)) {
								dataBaseParameters = dataBaseParameters2;
								break;
							}
						}

						try {
							if (connection != null)
								connection.close();

							connection = ConnectionFactory.getConnection(
									dataBaseParameters.getDriver(), hostName,
									schemaName, rootName, senha, dataBaseParameters
											.getJdbcName());

							InitialValues.initValues(dbName);

							PersistenceDiscoverer persistenceDiscoverer = new PersistenceDiscoverer(
									schemaName, connection);

							HashMap<String, Table> mapping = persistenceDiscoverer
									.dependenceDiscoverer();

							setMapping(mapping);

							map = mapping;

						} catch (SQLException e1) {
							errorMessage = "Não foi possivel Conectar, verifique o preenchimento dos campos ou suas configurações.";
						} catch (NotKnowTypeException e2) {
							errorMessage = "existem dados não conhecidos no mapeamento, refazer o mapeamento";
						} catch (IOException e3) {
							errorMessage = "Não foi possivel Abrir o Arquivo com as configurações";
						}
						
					}
				}
			});
		}
		return btnConectar;
	}

	/**
	 * This method initializes btnResetar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnResetar() {
		if (btnResetar == null) {
			btnResetar = new JButton();
			btnResetar.setText("Resetar");
			btnResetar.setEnabled(false);
			
			btnResetar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("btnResetar - actionPerformed()");

					txtSchema.setEditable(true);
					txtUser.setEditable(true);
					txtPassword.setEditable(true);
					htmlMenuItem.setEnabled(false);
					btnResetar.setEnabled(false);
					btnConectar.setEnabled(true);
					btnMapear.setEnabled(false);
					btnReplicar.setEnabled(false);
					jScrollPane.setEnabled(false);
					txtQuantidade.setEnabled(false);
					
					data = null;


				}
			});
		}
		return btnResetar;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane(tblTabMap);
		}
		
		return jScrollPane;
	}

	/**
	 * This method initializes txtQuantidade	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtQuantidade() {
		if (txtQuantidade == null) {
			txtQuantidade = new JTextField();
			txtQuantidade.setPreferredSize(new Dimension(200, 20));
			txtQuantidade.setText("0");
			txtQuantidade.setEditable(false);
			txtQuantidade.setHorizontalAlignment(JTextField.RIGHT);
		}
		return txtQuantidade;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new FlowLayout());
			jPanel3.add(getJProgressBar(), null);
			jPanel3.add(getBtnMapear(), null);
			jPanel3.add(getBtnReplicar(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setPreferredSize(new Dimension(400, 15));
			jProgressBar.setEnabled(false);
		}
		return jProgressBar;
	}

	/**
	 * This method initializes btnMapear	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnMapear() {
		if (btnMapear == null) {
			btnMapear = new JButton();
			btnMapear.setText("Mapear");
			btnMapear.setEnabled(false);
			btnMapear.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("btnMapear - actionPerformed()"); 
					
					MapperController.doMappingEntityInputField(
								getDataOfMapTable());
										
					btnReplicar.setEnabled(true);
					txtQuantidade.setEditable(true);
				}
			});
		}
		return btnMapear;
	}

	private String[][] getDataOfMapTable() {
		String[][] dataOfTable = new String[tblTabMap.getRowCount()][3];

		for (int i = 0; i < dataOfTable.length; i++) {
			dataOfTable[i][0] = (String) tblTabMap.getValueAt(i, 0);
			dataOfTable[i][1] = (String) tblTabMap.getValueAt(i, 1);
			dataOfTable[i][2] = (String) tblTabMap.getValueAt(i, 2);
		}

		return dataOfTable;
	}
	
	
	/**
	 * This method initializes btnReplicar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnReplicar() {
		if (btnReplicar == null) {
			btnReplicar = new JButton();
			btnReplicar.setText("Replicar");
			btnReplicar.setEnabled(false);
			btnReplicar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("btnReplicar - actionPerformed()");
					
					p = Pattern.compile("\\d+");
					m = p.matcher(txtQuantidade.getText());
					
					if (txtQuantidade.getText() == null) {
						JOptionPane
								.showMessageDialog(
										null,
										"CUIDADO: você não deve deixar o campo em branco",
										"Erro", JOptionPane.INFORMATION_MESSAGE);
					} else if (!m.matches()) {
						JOptionPane
								.showMessageDialog(
										null,
										"CUIDADO: você não deve digitar apenas números na Quantidade de Cópias.",
										"Erro", JOptionPane.INFORMATION_MESSAGE);
					}
					
					String msg = null;
					long qtdeDeReplicacoes = Long.parseLong(txtQuantidade.getText());
					RequiredTables requiredTables = new RequiredTables();
					requiredTables.setNumberOfReplications(qtdeDeReplicacoes);
					String schemaName = txtSchema.getText();
					requiredTables.setSchema(schemaName);

					try {
						requiredTables.setTables( getTableNames() );

						PersistenceGenerator persistenceGenerator = null;

						try {
							persistenceGenerator = new PersistenceGenerator(
									connection);
						} catch (SQLException e1) {
							msg = "não foi possivel Conectar ao banco de dados";
							e1.printStackTrace();
						} catch (NotKnowTypeException e1) {
							msg = "Foram encontrados tipos não conhecidos do dataBase, reveja a base no arquivo xml";
							e1.printStackTrace();
						}

						try {
							persistenceGenerator.generateData(requiredTables);
						} catch (SQLException e1) {
							msg = "Problemas na conecxão com o banco de dados";
						} catch (NotKnowTypeException e1) {
							e1.printStackTrace();
							msg = "Foram encontrados tipos não conhecidos do dataBase, reveja a base no arquivo xml";
						} catch (IrregularDateException e1) {
							msg = " o formato da data não pode ser replicado reveja, com no manual do seu banco de dados";
							e1.printStackTrace();
						} catch (IOException e1) {
							msg = "não foi possivel criar o arquivo de saida";
							e1.printStackTrace();
						}

						if (msg != null)
							JOptionPane.showMessageDialog(null, msg,
									"Input Error", JOptionPane.ERROR_MESSAGE);

					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),
								"Input Error", JOptionPane.ERROR_MESSAGE);

					}

					setEditable(false);
					
				}
			});
		}
		return btnReplicar;
	}

	private void setEditable(boolean editable) {
		btnReplicar.setEnabled(editable);
		txtQuantidade.setEditable(editable);
	}
	
	public void setMapping(HashMap<String, Table> hashmap) {
		for (TableComponents tableComponents : tableComponentsArrayList) {
			tableComponents.sethashMap(hashmap);
		}
	}
	
	public HashMap<String, Collection<String>> getTableNames() throws Exception {

		HashMap<String, Collection<String>> tables = new HashMap<String, Collection<String>>();

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
	
	/**
	 * This method initializes tblTabMap
	 * 
	 * @param data
	 * @param columnNames
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getTblTabMap(String[] columnNames, Object[][] data) {
		if (tblTabMap == null) {
			tblTabMap = new JTable(data, columnNames);
		}
		return tblTabMap;
	}
	/**
	 * This method initializes txtHost	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtHost() {
		if (txtHost == null) {
			txtHost = new JTextField();
			txtHost.setText("localhost");
		}
		return txtHost;
	}

	private Collection<DataBaseParameters> values = null;
	/**
	 * This method initializes cmbDatabases	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getCmbDatabases() {
		if (cmbDatabases == null) {
			
			String[] confgNames = null;
			try {
				values = InitialValues.getDataBaseParameters();
				confgNames = new String[values.size()];
				for (int i = 0; i < values.size(); i++) {

					confgNames[i] = ((ArrayList<DataBaseParameters>) values)
							.get(i).getDataBaseName();
				}

			} catch (IOException e) {

				JOptionPane
						.showMessageDialog(
								null,
								"Deve existir um arquivo de configuração com o nome dataBaseParameters.xml, na pasta dataBases",
								"Input Error", JOptionPane.ERROR_MESSAGE);

			}
			cmbDatabases = new JComboBox(confgNames);
			cmbDatabases.setPreferredSize(new Dimension(90, 25));
		}
		return cmbDatabases;
	}

	/**
	 * Launches this application
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				View application = new View();
				application.getJFrame().setVisible(true);
				application.getJFrame().setLocationRelativeTo(null);
			}
		});
	}
	
	private String[] columnNames = { "Campos", "Tabela", "Coluna" };
	private JLabel jLabel = null;
	private JTextField txtHost = null;
	private JLabel jLabel1 = null;
	private JComboBox cmbDatabases = null;
	private void mostra(String path, String fileName) {

		tblTabMap = null;

		
		/*
		 * o método na classe está comentado... 
		 * 
		 * MapperController.lookForInputs(path, fileName);
		 */

		data = new Object[MapperController.getFields().size()][3];

		for (int i = 0; i < MapperController.getFields().size(); i++) {
			data[i][0] = MapperController.getFields().get(i).getName();
		}

		
		jScrollPane.setViewportView(getTblTabMap(columnNames, data));

		setUpEntitiesColumn(tblTabMap, tblTabMap.getColumnModel().getColumn(1),tblTabMap.getColumnModel().getColumn(2));

		jScrollPane.setViewportView(getTblTabMap(columnNames, data));
	}

	public void setUpEntitiesColumn(JTable table, TableColumn tableColumn,TableColumn columnColumn) {
		// Set up the editor for the sport cells.
		JComboBox comboBoxTables = new JComboBox();
		JComboBox comboBoxColumns = new JComboBox();
		MapperController.lookForColumns();
		HashMap<Integer, BDEntity> myEntities = MapperController.getEntities();

		String[] itens = new String[myEntities.size()];
		itens[0] = myEntities.get(0).getTableName();
		int pos = 0;
		for (int i = 1; i < myEntities.size(); i++) {			
			if( itens[pos].equals( myEntities.get(i).getTableName()) ){
				continue;
			}else{
				pos += 1;
				itens[pos] = myEntities.get(i).getTableName();
			}
		}
		
		for (int i = 0; i < myEntities.size(); i++) {
			if(itens[i] == null){
				break;
			}else{
				comboBoxTables.addItem(itens[i]);			
			}
		}

		tableColumn.setCellEditor(new DefaultCellEditor(comboBoxTables));
		

		// Set up tool tips for the sport cells.
		DefaultTableCellRenderer rendererTable = new DefaultTableCellRenderer();
		rendererTable.setToolTipText("Clique para ver as tabelas");
		tableColumn.setCellRenderer(rendererTable);
		
		for (int i = 0; i < myEntities.size(); i++) {
			comboBoxColumns.addItem(myEntities.get(i).getColumnName());
		}
		columnColumn.setCellEditor(new DefaultCellEditor(comboBoxColumns));
		DefaultTableCellRenderer rendererColumn = new DefaultTableCellRenderer();
		rendererColumn.setToolTipText("Clique para ver as colunas");
		columnColumn.setCellRenderer(rendererColumn);
	}

//	/**
//	 * 
//	 * Faz o mapeamento entre campos de entrada de dados da Tela e as COLUNAS
//	 * encontradas na INFORMATION_SCHEMA.
//	 */
//	public static void maps(String path, String fileName) {
//
//		MapperController.lookForInputs(path, fileName);
//
//	}

}
