/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

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
    private DefaultTableModel modeloTabla;
    private String[] titulo={"Componente","Observacion"};
    private List<DatoTabla> modeloDatos;

    public ConsolaGeneral() {
        crearTabla();
        modeloDatos=new ArrayList<DatoTabla>();
    }
    
    public void crearTabla()
    {
        this.modeloTabla=new DefaultTableModel(titulo,0);
    }
    
    public void agregarDatos(String titulo,String observacion,Component componente)
    {
        Vector<String> fila=new Vector<>();
        fila.add(titulo);
        fila.add(observacion);
        this.modeloTabla.addRow(fila);
        this.modeloDatos.add( new DatoTabla(fila, (JTextComponent) componente));
        
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public void setModeloTabla(DefaultTableModel modeloTabla) {
        this.modeloTabla = modeloTabla;
    }
    
    public void seleccionarFila(int fila)
    {
        DatoTabla dato=modeloDatos.get(fila);
        dato.componente.requestFocus();
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
