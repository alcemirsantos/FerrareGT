package util;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class JTextFieldModified extends JTextField {

	public JTextFieldModified (){
		super();
		addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub			
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {				
				selectAll();				
			}
		});
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {				
				Character ch = arg0.getKeyChar();  
				if (!Character.isDigit(ch) || getText().length() > 10 ) {
					arg0.consume();
				}
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
