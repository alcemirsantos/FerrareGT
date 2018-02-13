package br.ufpi.genesis.util;

import javax.swing.table.*;
import java.util.*;


public class RowEditorModel {	
	private Hashtable data;

	public RowEditorModel() {
		data = new Hashtable();
	}

	public void addEditorForRow(int row, TableCellEditor e) {
		data.put(new Integer(row), e);
	}

	public void addEditorForCell(int row,int col, TableCellEditor e) {
		data.put(new EditorKey(row,col), e);
	}
	
	public void removeEditorForRow(int row) {
		data.remove(new Integer(row));
	}

	public TableCellEditor getEditor(int row) {
		return (TableCellEditor) data.get(new Integer(row));
	}
	
	public TableCellEditor getEditor(int row, int col) {
		return (TableCellEditor) data.get(new EditorKey(row,col));
	}

	public class EditorKey{
		private int row;
		private int col;

		public EditorKey(int row, int col){
			this.col = col;
			this.row = row;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof EditorKey) {
				EditorKey ek = (EditorKey)obj;
				if (ek.col== this.col && ek.row == this.row) {
					return true;
				}				
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return row+col;
		}
	}
}

