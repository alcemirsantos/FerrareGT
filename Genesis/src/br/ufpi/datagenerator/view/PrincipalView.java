package br.ufpi.datagenerator.view;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class PrincipalView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu jMenu = null;
	private JMenu jMenu1 = null;
	private JPanel jPanel = null; // @jve:decl-index=0:visual-constraint="10,581"

	/**
	 * This is the default constructor
	 */
	public PrincipalView() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(771, 652);
		this.setJMenuBar(getJJMenuBar());
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		jPanel.repaint();
		inputView.repaint();
		this.repaint();

		inputView.getInternInputView().getTables().get(0).repaint();

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJPanel());

			jContentPane.add(getInputView(), null);
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
			jJMenuBar.add(getJMenu());
			jJMenuBar.add(getJMenu1());
		}
		return jJMenuBar;
	}

	InputView inputView = null;

	/**
	 * This method initializes jMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenu() {
		if (jMenu == null) {
			jMenu = new JMenu();
			jMenu.setText("Principal");
			jMenu.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					jPanel.add(inputView);
					jPanel.repaint();
					inputView.repaint();

					inputView.getInternInputView().getTables().get(0).repaint();

					System.out.println("mouseClicked()"); // TODO Auto-generated
					// Event stub
					// mouseClicked()
				}
			});
		}
		return jMenu;
	}

	/**
	 * This method initializes jMenu1
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenu1() {
		if (jMenu1 == null) {
			jMenu1 = new JMenu();
			jMenu1.setText("Sobre");
		}
		return jMenu1;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(1, 2, 748, 589));
		}
		return jPanel;
	}

	private JPanel getInputView() {
		if (inputView == null) {
			inputView = new InputView();

			inputView.setToolTipText("");
			inputView.setBounds(new Rectangle(45, -2, 751, 587));
		}
		return inputView;
	}

} // @jve:decl-index=0:visual-constraint="10,10"
