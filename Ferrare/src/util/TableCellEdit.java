package util;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import performanceTestRepresentation.URLAccess;
  
/**
 * Classe para manipular as células da tabela 
 * @author Ismayle de Sousa Santos
 */
public class TableCellEdit extends AbstractCellEditor implements TableCellEditor {  
  
	private static final long serialVersionUID = 1L;
	JComponent component = new JTextField();  
       URLAccess urlAccess = null;
       int position = -1;

       public TableCellEdit(URLAccess urlAccess){
    	   this.urlAccess = urlAccess;
       }
       
       public Component getTableCellEditorComponent(JTable table, Object value,  
               boolean isSelected, int rowIndex, int vColIndex) {  
    	   ((JTextField)component).setText((String)value);
    	   position = rowIndex;
           return component;  
       }  
     
       public Object getCellEditorValue() {
    	   	if(position >= 0 && urlAccess != null && position <urlAccess.getFields().size()){
    	   		System.out.println(position+";"+urlAccess.getFields().get(position).getName());
    	   		int lastPositon = urlAccess.getFields().get(position).getInputs().size();
    	   		if(lastPositon > 0)
    	   			urlAccess.getFields().get(position).getInputs().get(lastPositon-1).setValue(((JTextField)component).getText().toString());    	   		
    	   	}
            System.out.println(((JTextField)component).getText());
            return ((JTextField)component).getText();  
       }  
  }   