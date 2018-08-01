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
import javax.swing.table.TableColumnModel;

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
        for (int i = 0; i < tamanios.length; i++) {
            jtable.getColumnModel().getColumn(i).setMaxWidth(tamanios[i]);
        }        

    }
    
    public static <T> T obtenerDatoSeleccionado(JTable tabla,int columna)
    {
        int fila=tabla.getSelectedRow();
        if(fila>=0)
        {
            return (T)tabla.getValueAt(fila, columna);
        }
        return null;
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
    
    public static void bloquerTodasColumnasTabla(JTable table)
    {
        
    }
    
    public static void cambiarTamanioColumnas(JTable table , Integer[] valoresColumnas)
    {
        int total=0;
        for (int i = 0; i < valoresColumnas.length; i++) 
        {
           total+=valoresColumnas[i];
        }
        
        double porcentajes[]=new double[valoresColumnas.length];        
        for (int i = 0; i < porcentajes.length; i++) {
            porcentajes[i]=(double)((double)valoresColumnas[i]/(double)total);
        }
        
        TableColumnModel columnModel = table.getColumnModel();        
        for (int i=0;i<columnModel.getColumnCount();i++) 
        {
            TableColumn columna=columnModel.getColumn(i);
            int tamanioTabla=table.getSize().width;
            double tamanioColumna=porcentajes[i]*tamanioTabla;
            columna.setPreferredWidth((int) tamanioColumna);
            columna.setMaxWidth((int) tamanioColumna);
            //columna.se
        }
        
        
        
    }
   
}
