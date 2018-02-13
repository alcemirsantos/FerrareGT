package br.ufpi.datagenerator.view;
import java.awt.Rectangle;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.SoftBevelBorder;

import br.ufpi.datagenerator.creator.PersistenceGenerator;
import br.ufpi.datagenerator.creator.RequiredTables;
import br.ufpi.datagenerator.domain.Table;
import br.ufpi.datagenerator.domain.connectors.ConnectionFactory;
import br.ufpi.datagenerator.initialconfiguration.DataBaseParameters;
import br.ufpi.datagenerator.initialconfiguration.InitialValues;
import br.ufpi.datagenerator.tablescans.PersistenceDiscoverer;
import br.ufpi.datagenerator.util.IrregularDateException;
import br.ufpi.datagenerator.util.NotKnowTypeException;

public class InputView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;
	private JComboBox jComboBox = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	private JLabel jLabel3 = null;
	private JTextField txtNomeUsuarioRoot = null;
	private JTextField txtHost = null;
	private JLabel jLabel4 = null;
	private JScrollPane jScrollPane = null;

	/**
	 * This is the default constructor
	 */
	public InputView() {
		super();
		initialize();
	}

	private InternInputView inputView = new InternInputView();
	private JButton btnAdicionarTabela = null;
	private JLabel jLabel5 = null;
	private JTextField txtNomeDoSchema = null;
	private JButton btnReplicar = null;
	private JButton btnConectar = null;

	public InternInputView getInternInputView() {
		return inputView;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel6 = new JLabel();
		jLabel6.setBounds(new Rectangle(25, 532, 178, 26));
		jLabel6.setText("Quantidade de Replicas");
		jLabel4 = new JLabel();
		jLabel4.setBounds(new Rectangle(297, 7, 191, 30));
		jLabel4.setText("DATA REPLICATOR");
		this.setLayout(null);
		this.setBounds(new Rectangle(2, 1, 751, 573));
		this.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		this.setName("Conex�o");
		this.setToolTipText("Conex�o");
		this.add(getJPanel(), null);
		this.add(jLabel4, null);
		this.add(getJScrollPane(), null);

		this.add(getBtnAdicionarTabela(), null);

		this.add(getBtnReplicar(), null);
		this.add(jLabel6, null);
		this.add(getTxtQuantidadeDeCopias(), null);
		jScrollPane.setViewportView(inputView);
		jScrollPane.repaint();
		inputView.repaint();

	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new Rectangle(14, 84, 137, 24));
			jLabel5.setText("Schema");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new Rectangle(350, 47, 135, 26));
			jLabel3.setText("Host");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(351, 15, 135, 26));
			jLabel2.setText("Senha");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(10, 51, 142, 26));
			jLabel1.setText("Nome do Root");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(12, 11, 145, 26));
			jLabel.setText("Configuração");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(20, 45, 718, 116));
			jPanel.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
			jPanel.setName("Connection");
			jPanel.setToolTipText("");
			jPanel.add(getJComboBox(), null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(getTxtNomeUsuarioRoot(), null);
			jPanel.add(getTxtHost(), null);
			jPanel.add(jLabel5, null);
			jPanel.add(getTxtNomeDoSchema(), null);
			jPanel.add(getBtnConectar(), null);
			jPanel.add(getJPasswordField(), null);
		}
		return jPanel;
	}

	private Collection<DataBaseParameters> values = null; // @jve:decl-index=0:
	private JPasswordField pswSenhaRoot = null;

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
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
								"Deve existir um arquivo de configura��o com o nome dataBaseParameters.xml, na pasta dataBases",
								"Input Error", JOptionPane.ERROR_MESSAGE);

			}
			jComboBox = new JComboBox(confgNames);
			jComboBox.setBounds(new Rectangle(175, 14, 147, 22));
		}
		return jComboBox;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtNomeUsuarioRoot() {
		if (txtNomeUsuarioRoot == null) {
			txtNomeUsuarioRoot = new JTextField();
			txtNomeUsuarioRoot.setBounds(new Rectangle(173, 49, 151, 26));
			txtNomeUsuarioRoot.setText("root");
		}
		return txtNomeUsuarioRoot;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtHost() {
		if (txtHost == null) {
			txtHost = new JTextField();
			txtHost.setBounds(new Rectangle(526, 51, 143, 22));
			txtHost.setText("localhost");
		}
		return txtHost;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(24, 215, 711, 303));
		}
		return jScrollPane;
	}

	/**
	 * This method initializes btnAdicionarTabela
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdicionarTabela() {
		if (btnAdicionarTabela == null) {
			btnAdicionarTabela = new JButton();
			btnAdicionarTabela.setBounds(new Rectangle(309, 183, 143, 21));
			btnAdicionarTabela.setText("Adicionar Tabela");
			btnAdicionarTabela.setEnabled(false);
			btnAdicionarTabela.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					inputView.putTables(map);
				}
			});
		}
		return btnAdicionarTabela;
	}

	/**
	 * This method initializes jTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtNomeDoSchema() {
		if (txtNomeDoSchema == null) {
			txtNomeDoSchema = new JTextField();
			txtNomeDoSchema.setBounds(new Rectangle(171, 85, 154, 22));
			txtNomeDoSchema.setText("biblioteca");
		}
		return txtNomeDoSchema;
	}

	/**
	 * This method initializes btnReplicar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnReplicar() {
		if (btnReplicar == null) {
			btnReplicar = new JButton();
			btnReplicar.setBounds(new Rectangle(531, 535, 149, 26));
			btnReplicar.setText("Replicar");
			btnReplicar.setEnabled(false);
			btnReplicar.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					String msg = null;

					long numberReplication = Long.parseLong(getTxtQuantidadeDeCopias()
							.getText());

					RequiredTables requiredTables = new RequiredTables();

					requiredTables.setNumberOfReplications(numberReplication);

					String schemaName = getTxtNomeDoSchema().getText();

					requiredTables.setSchema(schemaName);

					try {
						requiredTables.setTables(inputView.getTableNames());

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

					System.out.println("mouseClicked()"); 

					setEditable(false);
				}
			});
		}
		return btnReplicar;
	}

	private HashMap<String, Table> map = null;
	private JLabel jLabel6 = null;
	private JTextField txtQuantidadeDeCopias = null;
	private Connection connection = null;

	private void setEditable(boolean editable) {
		btnReplicar.setEnabled(editable);
		txtQuantidadeDeCopias.setEditable(editable);
		btnAdicionarTabela.setEnabled(editable);
	}

	/**
	 * This method initializes btnConectar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnConectar() {
		if (btnConectar == null) {
			btnConectar = new JButton();
			btnConectar.setBounds(new Rectangle(525, 85, 137, 21));
			btnConectar.setText("Conectar");
			btnConectar.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {

					String errorMessage = null;

					String rootName = getTxtNomeUsuarioRoot().getText();
					String senha = getJPasswordField().getText();
					String hostName = getTxtHost().getText();
					String schemaName = getTxtNomeDoSchema().getText();
					String dbName = (String) getJComboBox().getSelectedItem();

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

						inputView.setMapping(mapping);

						btnAdicionarTabela.setEnabled(true);

						btnReplicar.setEnabled(true);
						txtQuantidadeDeCopias.setEditable(true);

						map = mapping;

						inputView.repaint();

					} catch (SQLException e1) {

						errorMessage = "Não foi possivel Conectar, verifique o preenchimento dos campos ou suas configurações.";
					} catch (NotKnowTypeException e2) {
						errorMessage = "existem dados não conhecidos no mapeamento, refazer o mapeamento";
					} catch (IOException e3) {
						errorMessage = "Não foi possivel Abrir o Arquivo com as configurações";
					}

					if (errorMessage != null)
						JOptionPane.showMessageDialog(null, errorMessage,
								"Input Error", JOptionPane.ERROR_MESSAGE);

					System.out.println("mousePressed()"); 
				}
			});
		}
		return btnConectar;
	}

	/**
	 * This method initializes jPasswordField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private JPasswordField getJPasswordField() {
		if (pswSenhaRoot == null) {
			pswSenhaRoot = new JPasswordField();
			pswSenhaRoot.setBounds(new Rectangle(524, 23, 144, 21));
			pswSenhaRoot.setText("eruife");
		}
		return pswSenhaRoot;
	}

	/**
	 * This method initializes txtQuantidadeDeCopias
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTxtQuantidadeDeCopias() {
		if (txtQuantidadeDeCopias == null) {
			txtQuantidadeDeCopias = new JTextField();
			txtQuantidadeDeCopias.setEditable(false);
			txtQuantidadeDeCopias.setBounds(new Rectangle(213, 532, 111, 27));
		}
		return txtQuantidadeDeCopias;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
