/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.other;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Carlos
 */
public class RowsRenderer extends DefaultTableCellRenderer {

    private int columna;

    public RowsRenderer(int Colpatron) {
        this.columna = Colpatron;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        setBackground(Color.white);
        table.setForeground(Color.black);
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        Object celda= table.getValueAt(row, columna);
        if (celda!=null && celda.equals("No")) {
            this.setForeground(new Color(0,153,0));
        } 
        return this;
    }
}
