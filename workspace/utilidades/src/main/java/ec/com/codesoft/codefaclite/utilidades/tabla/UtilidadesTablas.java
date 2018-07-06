/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.tabla;

import java.util.Enumeration;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesTablas {
    
    public static int alinearCentro = SwingConstants.CENTER;
    public static int alinearDerecha = SwingConstants.RIGHT;
    public static int alinearIzquierda = SwingConstants.LEFT;
    
    public static void definirTamanioColumnas(JTable jtable,Integer[] tamanios)
    {
        for (int i = 0; i < jtable.getColumnCount(); i++) {
            jtable.getColumnModel().getColumn(i).setMaxWidth(tamanios[i]);
        }        

    }
      
    
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
    
    public static DefaultTableModel crearModeloTabla(String titulos[],Class[] tipoDatoFilas)
    {
         DefaultTableModel defaultTableModel=new javax.swing.table.DefaultTableModel(titulos,0) 
         {
             public Class getColumnClass(int columnIndex) {
                return tipoDatoFilas [columnIndex];
            }
        };    
         return defaultTableModel;
    }
    
    public static void cambiarColorFila(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new CellRenderer());
        }
    }
    
    public static void bloquearColumnasTabla(JTable table, Boolean[] puedeEditar)
    {
        table = new JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return puedeEditar[columnIndex];
            }
        };
    }
     
   
}
