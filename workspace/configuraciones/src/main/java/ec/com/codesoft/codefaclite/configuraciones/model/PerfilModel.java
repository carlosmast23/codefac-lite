/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.PerfilPanel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class PerfilModel extends PerfilPanel{

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarListener();
        //Cargar los modulos disponibles
        ModuloCodefacEnum[] modulos= ModuloCodefacEnum.values();
        getCmbModulo().removeAllItems();
        
        for (ModuloCodefacEnum modulo :modulos) 
        {
            getCmbModulo().addItem(modulo);
        }
        
        //Cargar las categorias disponibles
        CategoriaMenuEnum[] categorias= CategoriaMenuEnum.values();
        getCmbCategoria().removeAllItems();
        
        for (CategoriaMenuEnum categoria : categorias) {
            getCmbCategoria().addItem(categoria);
        }
        
        cargarVentanasSeleccionadas();
        
    }
    
    private void cargarVentanasSeleccionadas()
    {
            //Cargar las ventanas por categoria
        ModuloCodefacEnum modulo=(ModuloCodefacEnum) getCmbModulo().getSelectedItem();
        CategoriaMenuEnum categoria=(CategoriaMenuEnum) getCmbCategoria().getSelectedItem();
        List<VentanaEnum> ventanas= VentanaEnum.getVentanaByModuloAndCategoria(modulo,categoria);
        
        getCmbVentana().removeAllItems();
        for (VentanaEnum ventana : ventanas) {
            getCmbVentana().addItem(ventana);
        }
    
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void agregarListener() {
        
        getCmbModulo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarVentanasSeleccionadas();
            }
        });
        
        getCmbCategoria().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarVentanasSeleccionadas();
            }
        });
        
        getCmbVentana().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarVentanasSeleccionadas();
            }
        });
    }
    
}
