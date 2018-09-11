/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.contabilidad.model;

import ec.com.codesoft.codefaclite.contabilidad.panel.PlanCuentasPanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Carlos
 */
public class PlanCuentasModel extends PlanCuentasPanel {

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        
        DefaultMutableTreeNode abuelo = new DefaultMutableTreeNode("plan contable");
        DefaultTreeModel modelo = new DefaultTreeModel(abuelo);
        getTreePlanCuentas().setModel(modelo);
        
        //Crear entidades 
        DefaultMutableTreeNode activos = new DefaultMutableTreeNode("activos");
        DefaultMutableTreeNode pasivos = new DefaultMutableTreeNode("pasivos");
        DefaultMutableTreeNode patrimonio = new DefaultMutableTreeNode("patrimonio");
        
        DefaultMutableTreeNode cuenta1 = new DefaultMutableTreeNode("cuenta1");
        DefaultMutableTreeNode cuenta2 = new DefaultMutableTreeNode("cuenta2");
        DefaultMutableTreeNode cuenta3 = new DefaultMutableTreeNode("cuenta3");
        DefaultMutableTreeNode cuenta4 = new DefaultMutableTreeNode("cuenta4");
        DefaultMutableTreeNode cuenta5 = new DefaultMutableTreeNode("cuenta5");
        
        //Relacionar entidades
        modelo.insertNodeInto(activos,abuelo,0);
        modelo.insertNodeInto(pasivos, abuelo, 1);
        modelo.insertNodeInto(patrimonio, abuelo, 2);

        modelo.insertNodeInto(cuenta1, activos, 0);
        modelo.insertNodeInto(cuenta2, activos, 0);
        modelo.insertNodeInto(cuenta3, pasivos, 0);
        modelo.insertNodeInto(cuenta4, pasivos, 0);
        modelo.insertNodeInto(cuenta5, patrimonio, 0);
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
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

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
