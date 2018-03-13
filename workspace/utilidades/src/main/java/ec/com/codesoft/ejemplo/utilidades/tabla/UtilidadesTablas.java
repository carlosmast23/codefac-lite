/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.tabla;

import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesTablas {
    
    public static int alinearCentro = SwingConstants.CENTER;
    public static int alinearDerecha = SwingConstants.RIGHT;
    public static int alinearIzquierda = SwingConstants.LEFT;
    
    public static void ocultarColumna(JTable jtable,int columna)
    {
        jtable.getColumnModel().getColumn(columna).setMaxWidth(0);
        jtable.getColumnModel().getColumn(columna).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(columna).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(columna).setMinWidth(0);
    }
    
    public static void alinearTodasColumnasTabla(JTable tabla, int alinear)
    {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(alinear);
        for(int c=0 ; c<tabla.getColumnModel().getColumnCount(); c++)
        {
            tabla.getColumnModel().getColumn(c).setCellRenderer(tcr);
        }
    }
    
    public static void alinearColumnasTabla(JTable tabla, int alinear, Integer[] columnas)
    {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(alinear);
        for(int columna : columnas )
        {
            tabla.getColumnModel().getColumn(columna).setCellRenderer(tcr);
        }

    }
   
}
