/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.validation;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Carlos
 */
public class ConsolaGeneral {
    private String[] titulo={"Componente","Observacion"};
    private List<DatoTabla> modeloDatos;

    public ConsolaGeneral() {
       modeloDatos=new ArrayList<DatoTabla>();
    }
    
    
    public DefaultTableModel getModeloTabla()
    {       
        DefaultTableModel modeloTabla = new DefaultTableModel(titulo, 0);
        for (DatoTabla dato : modeloDatos) {
            modeloTabla.addRow(dato.fila);
        }
        return modeloTabla;
       
    }
    
    public void agregarDatos(String titulo,String observacion,Component componente)
    {
        quitarDato(componente);
        Vector<String> fila= new Vector<String>();
        fila.add(titulo);
        fila.add(observacion);    
        this.modeloDatos.add( new DatoTabla(fila, (JTextComponent) componente));
        
    }
    
    public void quitarDato(Component componente)
    {
        for (DatoTabla dato : modeloDatos) {
            if(dato.componente.equals(componente))
            {
                modeloDatos.remove(dato);
                break;
            }
        }
              
    }
    
    private boolean verificarExiste(Component componente)
    {
        for (DatoTabla dato : modeloDatos) {
            if(dato.componente.equals(componente))
            {
                return true;
            }
        }
        return false;
              
    }
    
    
    
    
    public void seleccionarFila(int fila)
    {
        DatoTabla dato=modeloDatos.get(fila);
        dato.componente.requestFocus();
        dato.componente.setBackground(new Color(255,255,102));
    }
    
    
    private class DatoTabla
    {
        public Vector<String> fila;
        public JTextComponent componente;

        public DatoTabla(Vector<String> fila, JTextComponent componente) {
            this.fila = fila;
            this.componente = componente;
        }
        
        
        
    }
    
    
}
