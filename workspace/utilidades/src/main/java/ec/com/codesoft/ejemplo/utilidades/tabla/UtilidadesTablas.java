/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.tabla;

import javax.swing.JTable;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesTablas {
    public static void ocultarColumna(JTable jtable,int columna)
    {
        jtable.getColumnModel().getColumn(columna).setMaxWidth(0);
        jtable.getColumnModel().getColumn(columna).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(columna).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(columna).setMinWidth(0);
    }
}
