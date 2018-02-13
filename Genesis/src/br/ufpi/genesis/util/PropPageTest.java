package br.ufpi.genesis.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;

public class PropPageTest extends JPanel
{
    private JComboBox b;
    private JTableX table;
    private DefaultTableModel model;
    private String[] col_names = {"Name", "Value"};
    private String[] anchor_values = { "CENTER", "NORTH", "NORTHEAST",
           "EAST", "SOUTHEAST", "SOUTH",
           "SOUTHWEST", "WEST", "NORTHWEST" };
    private String[] fill_values = { "NONE", "HORIZONTAL", "VERTICAL",
            "BOTH" };
    private void createGUI()
    {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        b = new JComboBox();
        model = new DefaultTableModel(col_names,12)
        {
            public String[] prop_names = { "Name", "Anchor", "Fill",
                                           "GridHeight", "GridWidth",
                                           "GridX", "GridY", "Insets",
                                           "Ipadx", "Ipady",
                                           "WeightX", "WeightY" };
            public Object getValueAt(int row, int col)
            {
                if (col==0)
                    return prop_names[row];
                return super.getValueAt(row,col);
            }
            public boolean isCellEditable(int row, int col)
            {
                if (col==0)
                    return false;
                return true;
            }
        };
        table = new JTableX(model);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        // create a RowEditorModel... this is used to hold the extra
        // information that is needed to deal with row specific editors
        RowEditorModel rm = new RowEditorModel();
        // tell the JTableX which RowEditorModel we are using
        table.setRowEditorModel(rm);
        // create a new JComboBox and DefaultCellEditor to use in the
        // JTableX column
        JComboBox cb = new JComboBox(anchor_values);
        DefaultCellEditor ed = new DefaultCellEditor(cb);
        // tell the RowEditorModel to use ed for row 1
        rm.addEditorForRow(1,ed);
        // create a new JComboBox and editor for a different row
        cb = new JComboBox(fill_values);
        ed = new DefaultCellEditor(cb);
        // inform the RowEditorMode of the situation
        rm.addEditorForRow(2,ed);
        add(b, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
    public PropPageTest()
    {
        createGUI();
    }
    public static void main(String[] args)
    {
        JFrame f = new JFrame("test");
        f.setSize(300,350);
        f.getContentPane().add(new PropPageTest(), BorderLayout.CENTER);
        f.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        f.setVisible(true);
    }
}
