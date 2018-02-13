package view;
import java.awt.BorderLayout;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Graphics;

public class SplashScreen extends JDialog {

	private static final long serialVersionUID = 1L;

	public SplashScreen() {
		super();
		setTitle("Spash!!!");
		setResizable(false);
		setUndecorated(true);
		JLabel label = new JLabel(getIcon());
		getContentPane().add(label, BorderLayout.CENTER);
		setSize(getIcon().getIconWidth(), getIcon().getIconHeight());
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private static ImageIcon getIcon() {
		URL url = null;
		try {
			File f = new File("images/ferrareSplash.JPG");
			url = f.toURI().toURL();
			return new ImageIcon(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (url == null) {
			return null;
		}else
			return new ImageIcon(url);
	}
	
	public void paint(Graphics g) {
		g.drawImage(getIcon().getImage(), 0, 0,
				getBackground(), this);
	}

	public void close() throws InterruptedException, InvocationTargetException {
		Runnable closerRunner = new Runnable() {
			public void run() {
				setVisible(false);
				dispose();
			}
		};
		SwingUtilities.invokeAndWait(closerRunner);
	}
}
