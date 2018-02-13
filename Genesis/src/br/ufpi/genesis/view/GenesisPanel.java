package br.ufpi.genesis.view;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker.StateValue;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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
import br.ufpi.datagenerator.util.UtilMethods;
import br.ufpi.datagenerator.view.TableComponents;
import br.ufpi.genesis.util.JTableX;
import br.ufpi.genesis.util.RowEditorModel;
import br.ufpi.mapper.beans.BDEntity;
import br.ufpi.mapper.beans.MappedInput;
import br.ufpi.mapper.control.MapperController;

import functionalTestRepresentation.Input;
import functionalTestRepresentation.TestBattery;
import functionalTestRepresentation.TestCase;

import performanceTestRepresentation.TestPlan;
import javax.swing.JTextArea;

public class GenesisPanel extends JPanel implements ActionListener, PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	/*
	 * Variáveis próprias
	 */
	
	private Collection<DataBaseParameters> values = null;  //  @jve:decl-index=0:
	private String[] columnNames = { "Campos", "Tabela", "Atributo", "Path" };
	private Connection connection = null;
	private HashMap<String, Table> map = null;  //  @jve:decl-index=0:
	private Object[][] data = null;
	private Pattern p;
	private Matcher m;
	private ArrayList<TableComponents> tableComponentsArrayList = new ArrayList<TableComponents>();  //  @jve:decl-index=0:
	private String filterTerm = "";  //  @jve:decl-index=0:
	private boolean withFilter = false;
	private JComboBox comboBox;
	private JComboBox comboBoxTables = null;
	private RowEditorModel rm = new RowEditorModel();  //  @jve:decl-index=0:
	private HashMap<Integer, BDEntity> myEntities = MapperController.getEntities();  //  @jve:decl-index=0:
	private TestBattery battery = null;  //  @jve:decl-index=0:
	private TestPlan performanceTest = null;
	private TestPlan stressTest = null;
	private PersistenceGenerator generatorTask = null;  //  @jve:decl-index=0:
	/*
	 * Variáveis geradas
	 */
	private JPanel pnlConnect = null;
	private JPanel pnlMap = null;
	private JPanel pnlSchema = null;
	private JLabel lblHost = null;
	private JTextField txtHost = null;
	private JLabel lblDatabases = null;
	private JComboBox cmbDatabases = null;
	private JLabel lblSchema = null;
	private JTextField txtSchema = null;
	private JLabel lblUser = null;
	private JTextField txtUser = null;
	private JPasswordField txtPassword = null;
	private JButton btnConnect = null;
	private JLabel lblPassword = null;
	private JButton btnReset = null;
	private JScrollPane jScrollPane = null;
	private JTableX	 tblMap = null;
	private DefaultTableModel tableXModel;
	private JCheckBox chkActiveFilter = null;
	private JPanel jPanel = null;
	private JPanel pnlProgress = null;
	private JLabel lblCopyQuantity = null;
	private JTextField txtCopyQuantity = null;
	private JButton btnReplicate = null;
	private JButton btnMap = null;
	private JProgressBar jProgressBar = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;

	private JTextArea taskOutput = null;

	private JScrollPane jScrollPane1 = null;
	

	/**
	 * This is the full constructor
	 */
	public GenesisPanel() {
		super();
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 479);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(getPnlConnect(), null);
		this.add(getPnlMap(), null);
		this.add(getPnlSchema(), null);
	}

	public JProgressBar getjProgressBar() {
		return jProgressBar;
	}

	public void setjProgressBar(JProgressBar jProgressBar) {
		this.jProgressBar = jProgressBar;
	}

	public TestPlan getPerformanceTest() {
		return performanceTest;
	}

	public void setPerformanceTest(TestPlan performanceTest) {
		this.performanceTest = performanceTest;
	}

	public TestPlan getStressTest() {
		return stressTest;
	}

	public void setStressTest(TestPlan stressTest) {
		this.stressTest = stressTest;
	}

	public void fillTblMap(TestBattery batt) {
		boolean fieldAdded = false;
		String fieldName = "";
		String fieldPath = "";
		Object[][] map = null; 
		int fieldQuantity = 0;
		battery = batt;
		MapperController.lookForInputs(batt);
		map = new Object[MapperController.getFields().size()][4];

		for (int i = 0; i < MapperController.getFields().size(); i++) {
			fieldName = MapperController.getFields().get(i).getName();
			fieldPath = MapperController.getPaths().get(i);
			
			for (int j = 0; j < map.length; j++) {
				if (fieldName.equals( map[j][0] ) && fieldPath.equals( map[j][3] )) {
					fieldAdded = true;
				}
			}
			
			if (!fieldAdded) {
				map[i][0] = fieldName;
				map[i][3] = fieldPath;
				fieldQuantity += 1;
			}
			fieldAdded = false; 
		}
		
		data = new Object[fieldQuantity][4];
		
		for (int i = 0; i < fieldQuantity; i++) {
			data[i][0] = map[i][0];
			data[i][3] = map[i][3];
		}
		
		jScrollPane.setViewportView(getTblMap(columnNames, data));

	}


	/**
	 * This method initializes pnlConnect	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlConnect() {
		if (pnlConnect == null) {
			lblPassword = new JLabel();
			lblPassword.setText("Senha: ");
			lblPassword.setPreferredSize(new Dimension(50, 20));
			lblUser = new JLabel();
			lblUser.setText("Usuário: ");
			lblUser.setPreferredSize(new Dimension(60, 20));
			lblSchema = new JLabel();
			lblSchema.setText("Schema: ");
			lblSchema.setPreferredSize(new Dimension(60, 20));
			lblDatabases = new JLabel();
			lblDatabases.setText("Banco de dados: ");
			lblDatabases.setPreferredSize(new Dimension(110, 20));
			lblHost = new JLabel();
			lblHost.setText("Host: ");
			lblHost.setPreferredSize(new Dimension(40, 20));
			pnlConnect = new JPanel();
			pnlConnect.setLayout(new FlowLayout());
			pnlConnect.setPreferredSize(new Dimension(1055, 55));
			pnlConnect.add(lblHost, null);
			pnlConnect.add(getTxtHost(), null);
			pnlConnect.add(lblDatabases, null);
			pnlConnect.add(getCmbDatabases(), null);
			pnlConnect.add(lblSchema, null);
			pnlConnect.add(getTxtSchema(), null);
			pnlConnect.add(lblUser, null);
			pnlConnect.add(getTxtUser(), null);
			pnlConnect.add(lblPassword, null);
			pnlConnect.add(getTxtPassword(), null);
			pnlConnect.add(getBtnConnect(), null);
			pnlConnect.add(getBtnReset(), null);
		}
		return pnlConnect;
	}

	/**
	 * This method initializes pnlMap	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlMap() {
		if (pnlMap == null) {
			pnlMap = new JPanel();
			pnlMap.setLayout(new BoxLayout(getPnlMap(), BoxLayout.Y_AXIS));
			pnlMap.add(getJScrollPane(), null);
			pnlMap.add(getJPanel2(), null);
		}
		return pnlMap;
	}

	/**
	 * This method initializes pnlSchema	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlSchema() {
		if (pnlSchema == null) {
			pnlSchema = new JPanel();
			pnlSchema.setLayout(new BoxLayout(getPnlSchema(), BoxLayout.Y_AXIS));
			pnlSchema.add(getJPanel(), null);
			pnlSchema.add(getPnlProgress(), null);
		}
		return pnlSchema;
	}

	/**
	 * This method initializes txtHost	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtHost() {
		if (txtHost == null) {
			txtHost = new JTextField();
			txtHost.setPreferredSize(new Dimension(70, 20));
		}
		return txtHost;
	}

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
				cmbDatabases.setPreferredSize(new Dimension(100, 25));
		}
		
		return cmbDatabases;
	}

	/**
	 * This method initializes txtSchema	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtSchema() {
		if (txtSchema == null) {
			txtSchema = new JTextField();
			txtSchema.setPreferredSize(new Dimension(85, 20));
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
			txtUser.setPreferredSize(new Dimension(85, 20));
		}
		return txtUser;
	}

	/**
	 * This method initializes txtPassword	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getTxtPassword() {
		if (txtPassword == null) {
			txtPassword = new JPasswordField();
			txtPassword.setPreferredSize(new Dimension(90, 20));
		}
		return txtPassword;
	}

	/**
	 * This method initializes btnConnect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnConnect() {
		if (btnConnect == null) {
			btnConnect = new JButton();
			btnConnect.setPreferredSize(new Dimension(90, 25));
			btnConnect.setText("Conectar");
			btnConnect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("btnConnect - actionPerformed()");
					
					String errorMessage = null;

					String rootName = txtUser.getText();
					String senha = txtPassword.getText();
					String hostName = txtHost.getText();
					String schemaName = txtSchema.getText();
					String dbName = (String) cmbDatabases.getSelectedItem();
					
					if (txtSchema.getText().equals("")) {
						JOptionPane
								.showMessageDialog(
										null,
										"CUIDADO: você não deve deixar o campo 'Schema' em branco.",
										"Erro",
										JOptionPane.INFORMATION_MESSAGE);
					} else {
						MapperController.setBDSchematoConnect(schemaName);

						MapperController.setUserOfBD(rootName);
						MapperController.setBDUserPassoword(senha);
						
						setConnectPanelActive(false);
						setMapPanelActive(true);

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
						
						setUpEntitiesColumn(tblMap, tblMap.getColumnModel().getColumn(1),tblMap.getColumnModel().getColumn(2));

						jScrollPane.setViewportView(getTblMap(columnNames, data));
					}

				}

				
			});
		}
		return btnConnect;
	}


	/**
	 * This method initializes btnReset	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnReset() {
		if (btnReset == null) {
			btnReset = new JButton();
			btnReset.setPreferredSize(new Dimension(90, 25));
			btnReset.setEnabled(false);
			btnReset.setText("Resetar");
			btnReset.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("btnReset - actionPerformed()");

					setConnectPanelActive(true);
					setReplicatePanelActive(false);
					setMapPanelActive(false);

					data = null;
				}
			});
		}
		return btnReset;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setPreferredSize(new Dimension(3, 200));
			jScrollPane.setEnabled(false);
		}
		return jScrollPane;
	}

	/**
	 * This method initializes tblMap	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTableX getTblMap(String[] columnNames, Object[][] data) {
//		tableXModel = new DefaultTableModel(columnNames,data.length)
//        {
//			public String[] prop_names = { "Name", "Anchor", "Fill",
//                    "GridHeight", "GridWidth",
//                    "GridX", "GridY", "Insets",
//                    "Ipadx", "Ipady",
//                    "WeightX", "WeightY" };
//            public Object getValueAt(int row, int col)
//            {
//                if (col==0)
//                    return prop_names[row];
//                return super.getValueAt(row,col);
//            }
//            public boolean isCellEditable(int row, int col)
//            {
//                if (col==0)
//                    return false;
//                return true;
//            }
//        };
       
		if (tblMap == null) {
			tblMap = new JTableX(data,columnNames);
			tblMap.setRowSelectionAllowed(false);
	        tblMap.setColumnSelectionAllowed(false);
			tblMap.setAutoCreateColumnsFromModel(false);
			tblMap.getColumnModel().getColumn(2).setIdentifier("columnColumn");
		}
		return tblMap;
	}

	/**
	 * This method initializes chkActiveFilter	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getChkActiveFilter() {
		if (chkActiveFilter == null) {
			chkActiveFilter = new JCheckBox();
			chkActiveFilter.setText("Ativar Filtro de Mapeamento");
			chkActiveFilter.setEnabled(false);
			chkActiveFilter.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					System.out.println("chkbox - itemStateChanged()");
					
					JCheckBox ckb = (JCheckBox) e.getSource();
					if (ckb.isSelected()) {
						withFilter = true;
					}else {
						withFilter = false;
					}
					
					Thread updateCombo = new Thread(){
						public void run(){
							updateColumnCombo();	
						}
					};
					updateCombo.start();
					
				}
			});
		}
		return chkActiveFilter;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints.weightx = 1.0;
			lblCopyQuantity = new JLabel();
			lblCopyQuantity.setText("Quantidade de Cópias: ");
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			jPanel.add(lblCopyQuantity, null);
			jPanel.add(getJPanel3(), null);
			jPanel.add(getBtnReplicate(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes pnlProgress	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlProgress() {
		if (pnlProgress == null) {
			pnlProgress = new JPanel();
			pnlProgress.setLayout(new BoxLayout(getPnlProgress(), BoxLayout.Y_AXIS));
			pnlProgress.setPreferredSize(new Dimension(710, 20));
			pnlProgress.add(getJProgressBar(), null);
			pnlProgress.add(getJScrollPane1(), null);
		}
		return pnlProgress;
	}

	/**
	 * This method initializes txtCopyQuantity	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTxtCopyQuantity() {
		if (txtCopyQuantity == null) {
			txtCopyQuantity = new JTextField();
			txtCopyQuantity.setPreferredSize(new Dimension(200, 20));
			txtCopyQuantity.setEnabled(true);
			txtCopyQuantity.setEditable(false);
			txtCopyQuantity.setText("0");
		}
		return txtCopyQuantity;
	}

	/**
	 * This method initializes btnReplicate	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnReplicate() {
		if (btnReplicate == null) {
			btnReplicate = new JButton();
			btnReplicate.setText("Replicar");
			btnReplicate.setEnabled(false);
			btnReplicate.setPreferredSize(new Dimension(85, 25));
			btnReplicate.addActionListener(this);
		}
		
		return btnReplicate;
	}
	
	public void actionPerformed(java.awt.event.ActionEvent e) {
		System.out.println("btnReplicar - actionPerformed()");
		
		p = Pattern.compile("\\d+");
		m = p.matcher(txtCopyQuantity.getText());
		
		if (txtCopyQuantity.getText() == null) {
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
		long copiesQuantity = Long.parseLong(txtCopyQuantity.getText());
		RequiredTables requiredTables = new RequiredTables();
		requiredTables.setNumberOfReplications(copiesQuantity);
		String schemaName = txtSchema.getText();
		requiredTables.setSchema(schemaName);
		
		jProgressBar.setMaximum((int)copiesQuantity);
		jProgressBar.setValue(0);
		jProgressBar.setStringPainted(true);
		
		btnReplicate.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		try {
			requiredTables.setTables( getTableNames() );

			try {
				generatorTask = new PersistenceGenerator(
						connection);
			} catch (SQLException e1) {
				msg = "não foi possivel Conectar ao banco de dados";
				e1.printStackTrace();
			} catch (NotKnowTypeException e1) {
				msg = "Foram encontrados tipos não conhecidos do dataBase, reveja a base no arquivo xml";
				e1.printStackTrace();
			}
			
			generatorTask.addPropertyChangeListener(this);

			try {
				generatorTask.generateData(requiredTables,taskOutput, copiesQuantity);
				generatorTask.execute();
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
			
			while (true) {
				if (generatorTask.getState()== StateValue.DONE) {
					addCSVReferenceToAbstractRepresentation(requiredTables, PersistenceGenerator.getInitialBDValues());
					addCSVDataToTestPlanRepresentation(requiredTables, PersistenceGenerator.getInitialBDValues());
					break;
				}
			}

			if (msg != null)
				JOptionPane.showMessageDialog(null, msg,
						"Input Error1", JOptionPane.ERROR_MESSAGE);

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Input Error2", JOptionPane.ERROR_MESSAGE);
		}

		setEditable(false);
		btnReplicate.setEnabled(true);
		setCursor(null);
		
		JOptionPane.showMessageDialog(null, "Replicação concluída com sucesso!",
				"Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            jProgressBar.setValue(progress);
            taskOutput.append(String.format(
                    " %d%% da replicação completa.\n", generatorTask.getProgress()));
        } 
		
	}
	/**
	 * This method initializes btnMap	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBtnMap() {
		if (btnMap == null) {
			btnMap = new JButton();
			btnMap.setText("Mapear");
			btnMap.setEnabled(false);
			btnMap.setPreferredSize(new Dimension(80, 25));
			btnMap.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("btnMapear - actionPerformed()"); 
					
					MapperController.doMappingEntityInputField(
								getDataOfMapTable());
					
					JOptionPane
					.showMessageDialog(
							null,
							"SUCESSO: Mapeamento concluído com sucesso.",
							"Sucesso", JOptionPane.INFORMATION_MESSAGE);
					btnReplicate.setEnabled(true);
					txtCopyQuantity.setEditable(true);
					
				}
			});
		}
		return btnMap;
	}

	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar();
			jProgressBar.setPreferredSize(new Dimension(700, 25));
		}
		return jProgressBar;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = -1;
			gridBagConstraints1.gridy = -1;
			jPanel2 = new JPanel();
			jPanel2.setLayout(new FlowLayout());
			jPanel2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jPanel2.add(getBtnMap(), null);
			jPanel2.add(getChkActiveFilter(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.weightx = 1.0;
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
			jPanel3.add(getTxtCopyQuantity(), gridBagConstraints2);
		}
		return jPanel3;
	}

	/*
	 * 
	 * Métodos próprios
	 * 
	 */
	
	private void setConnectPanelActive(boolean active) {
		txtHost.setEditable(active);
		cmbDatabases.setEnabled(active);
		txtSchema.setEditable(active);
		txtUser.setEditable(active);						
		txtPassword.setEditable(active);
		btnReset.setEnabled(!active);
		btnConnect.setEnabled(active);
	}
	
	private void setMapPanelActive(boolean active){
		btnMap.setEnabled(active);
		chkActiveFilter.setEnabled(active);
		jScrollPane.setEnabled(active);
	}
	
	private void setReplicatePanelActive(boolean active){
		btnReplicate.setEnabled(active);
		txtCopyQuantity.setEditable(active);
		btnMap.setEnabled(!active);
		chkActiveFilter.setEnabled(!active);
		
	}

	private String[][] getDataOfMapTable() {
		String[][] dataOfTable = new String[tblMap.getRowCount()][3];

		for (int i = 0; i < dataOfTable.length; i++) {
			dataOfTable[i][0] = (String) tblMap.getValueAt(i, 0);
			dataOfTable[i][1] = (String) tblMap.getValueAt(i, 1);
			dataOfTable[i][2] = (String) tblMap.getValueAt(i, 2);
		}

		return dataOfTable;
	}
	
	private void setEditable(boolean editable) {
		btnReplicate.setEnabled(editable);
		txtCopyQuantity.setEditable(editable);
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

	public void setUpEntitiesColumn(JTable table, TableColumn tableColumn,TableColumn columnColumn) {
		// Set up the editor for the sport cells.
		comboBoxTables = new JComboBox();		
		
		MapperController.lookForColumns();

		String[] tableNames = new String[myEntities.size()];
		tableNames[0] = myEntities.get(0).getTableName();
		int pos = 0;
		for (int i = 1; i < myEntities.size(); i++) {			
			if( tableNames[pos].equals( myEntities.get(i).getTableName()) ){
				continue;
			}else{
				pos += 1;
				tableNames[pos] = myEntities.get(i).getTableName();
			}
		}
		for (int i = 0; i < tableNames.length; i++) {
			if ( tableNames[i] == null) {
				continue;
			}else{
				comboBoxTables.addItem(tableNames[i]);
			}
		}

		comboBoxTables.setSelectedIndex(0); // seleciona o primeiro da lista

		comboBoxTables.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				System.out.println("comboBoxTables - itemStateChanged()");
				JComboBox cb = (JComboBox) e.getSource();
				
				if (e.getStateChange() == ItemEvent.SELECTED) {
					
					filterTerm = (String)cb.getSelectedItem();
					
					Thread updateCombo = new Thread(){
						public void run(){
							updateColumnCombo();	
						}
					};
					updateCombo.start();
				}
			}
		});

		tableColumn.setCellEditor(new DefaultCellEditor(comboBoxTables));

		// Set up tool tips for the sport cells.
		DefaultTableCellRenderer rendererTable = new DefaultTableCellRenderer();
		rendererTable.setToolTipText("Clique para ver as tabelas");
		tableColumn.setCellRenderer(rendererTable);
		
	}

	/**
	 * Atualiza o combo de uma dada linha da tabela de mapeamento 
	 * de acordo com o item selecionado.
	 */
	private void updateColumnCombo(){
		SwingUtilities.invokeLater(new Runnable(){
		      public void run(){
		    	  
		    	  int index = tblMap.getSelectedRow();
		    	  
		    	  if (index != -1 ) { // index == -1 -> there is nothing happening!
				
			    	  fillComboFiltering(tblMap.getColumn("columnColumn"), index, filterTerm);					   
		    	  }
		      }
		    });
	}
	
//	/**
//	 * 
//	 * Atualiza os combos com as colunas existentes no banco. É utilizado no primeiro preenchimento.
//	 * 
//	 * @param columnColumn
//	 * @param myEntities
//	 */
//	private void fillComboColumns(TableColumn columnColumn, HashMap<Integer, BDEntity> myEntities){
//		JComboBox comboBoxColumns = new JComboBox();
//		
//		for (int i = 0; i < myEntities.size(); i++) {
//			comboBoxColumns.addItem(myEntities.get(i).getColumnName());
//		}
//		
//		for (int i = 0; i < data.length; i++) {
//			JComboBox novo= new JComboBox();
//			novo = comboBoxColumns;
//			columnColumn.setCellEditor(new DefaultCellEditor(novo));
//			combosColumn.add(novo);
//		}
//		
//		DefaultTableCellRenderer rendererColumn = new DefaultTableCellRenderer();
//		rendererColumn.setToolTipText("Clique para ver as colunas");
//		columnColumn.setCellRenderer(rendererColumn);
//	}
	
	/**
	 * 
	 * Atualiza os combos com as colunas existentes no banco com filtro. É utilizado após a ferramenta ter iniciada.
	 * 
	 * @param combo
	 * @param filter
	 */
	private void fillComboFiltering(TableColumn columnColumn, int row, String filter){
		String column="";
		boolean filtered = false;
		
		// tell the JTableX which RowEditorModel we are using
        tblMap.setRowEditorModel(rm);
        comboBox = new JComboBox();
        
        String[] columnItens = new String[myEntities.size()];
        columnItens = getActualColumnItens(columnItens);
        
		if (withFilter) {
			for (int i = 0; i < myEntities.size(); i++) {
				
				column = (String) data[row][0];

				if (columnItens[i].equals(column) ) {
					comboBox.addItem(columnItens[i]); 
					filtered = true;
				}
			}
			if (!filtered) {
				System.out.println("nada encontrado com o filtro.......!!");
				fillItens(comboBox);
			}
		}else{
			fillItens(comboBox);
		}
		
		DefaultCellEditor de = new DefaultCellEditor(comboBox);
		
		rm.addEditorForCell(row,2, de);
		
		tblMap.validate();
		tblMap.repaint();
		
		DefaultTableCellRenderer rendererColumn = new DefaultTableCellRenderer();
		rendererColumn.setToolTipText("Clique para ver as colunas");
		columnColumn.setCellRenderer(rendererColumn);
	}
	
	private String[] getActualColumnItens(String[] columnItens){
		for (int i = 0; i < myEntities.size(); i++) {
			if (myEntities.get(i).getTableName().contains(filterTerm)) {
				columnItens[i] = myEntities.get(i).getColumnName();				
			}else{
				columnItens[i] = "";
			}
		}
		return columnItens;
	}
	private void fillItens(JComboBox cb){
		for (int i = 0; i < myEntities.size(); i++) {
			if (myEntities.get(i).getTableName().contains(filterTerm)) {
				cb.addItem( myEntities.get(i).getColumnName() );				
			}
		}
	}
	
	private void addCSVReferenceToAbstractRepresentation( RequiredTables requiredTables, HashMap<String, ArrayList<HashMap<String, String>>> initialBDValues){

		ArrayList<String> requiredFields = new ArrayList<String>();  // itens do XXXXHeader.txt

		requiredFields = UtilMethods.getRequiredFields(requiredTables, initialBDValues);
		
		LinkedList<TestCase> testCases = battery.getTestCases();
		int count=0;
		
		for (TestCase testCase : testCases) { // cada caso de teste
			for (Input input : testCase.getInputs()) { // cada Input do caso de teste
				String inputFieldName = input.getField().getName();
				String valueUsed = input.getValue();
				String inputPath = input.getTestCase().getTestProcedure().getPath();
				System.out.println("nome do input: "+inputFieldName);
				
				loop: for (MappedInput mappedInput : MapperController.getMapedInputs()) { // cada entrada mapeada
					String mappedInputFieldName = mappedInput.getInputFieldName();
					String mappedInputTable = mappedInput.getEntity().getTableName();
					String mappedInputColumn = mappedInput.getEntity().getColumnName();
					ArrayList<HashMap<String, String>> initBDValues = initialBDValues.get(mappedInput.getEntity().getTableName());
					
					if (mappedInputFieldName.equals(inputFieldName) && MapperController.getPaths().get(count).equals(inputPath)) {
						
						for (HashMap<String, String> bdRegister : initBDValues ) { // cada registro inicial do banco de dados
							int id = initBDValues.indexOf(bdRegister);
							
							// valor do campo com os valores que tem na coluna.
							if (valueUsed.equals(bdRegister.get( mappedInput.getEntity().getColumnName() ))) {
								
								input.getField().setReferenceToCSV( mappedInputTable+"_"+mappedInputColumn+id );
								System.out.println( mappedInputTable+"_"+mappedInputColumn+id );
								break loop;
							}
							System.out.println("valueUsed:"+valueUsed+" valor de bdregister...:"+bdRegister.get(inputFieldName));
						}
					}
				}
				count += 1;
			}
		}
		
	}
	
	private void addCSVDataToTestPlanRepresentation( RequiredTables requiredTables,HashMap<String, ArrayList<HashMap<String, String>>> initialBDValues) {
		ArrayList<String> requiredFields = new ArrayList<String>();  // itens do XXXXHeader.txt
		String header = "";

		requiredFields = UtilMethods.getRequiredFields(requiredTables, initialBDValues);
		
		performanceTest.setCsvFileName(txtSchema.getText()+".csv");
		stressTest.setCsvFileName(txtSchema.getText()+".csv");
		
		for (String string : requiredFields) {
			header += string+",";
		}
		header = header.substring(0, header.length()-1);
		performanceTest.setCsvVariablesNames(header);
		stressTest.setCsvVariablesNames(header);
	}

	/**
	 * This method initializes taskOutput	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTaskOutput() {
		if (taskOutput == null) {
			taskOutput = new JTextArea();
			taskOutput.setEditable(false);
		}
		return taskOutput;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane(getTaskOutput());
			jScrollPane1.setPreferredSize(new Dimension(300, 70));
		}
		return jScrollPane1;
	}	
} // fim da classe

