/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.contabilidad.model;

import ec.com.codesoft.codefaclite.contabilidad.other.NodoTreeCuenta;
import ec.com.codesoft.codefaclite.contabilidad.panel.PlanCuentasPanel;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.contabilidad.CuentaContable;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.contabilidad.PlanCuenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Carlos
 */
public class PlanCuentasModel extends PlanCuentasPanel {
    
    /**
     * Referencia del plan de cuentas que tiene el arbol de las cuentas y su organizacion
     */
    private PlanCuenta planCuenta;
    
    /**
     * Modelo para el arbol grafico del plan de cuentas
     */
    private DefaultTreeModel modeloTree;
    
    /**
     * Nodo que contiene la referencia por defecto de la raiz para empezar a construir el plan de cuentas
     */
    private NodoTreeCuenta nodoCuentaRaiz;
    
    /**
     * Nodo de la cuenta seleccionada para editar o grabar
     */
    private NodoTreeCuenta nodoCuentaSeleccionada;
    
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        
        listenerBotones();
        listenerPopUp();
        listenerTree();
        componentesIniciales();
        //cargarValoresPorDefectoTemp();
        //construirPlanCuentas();
        construirArbolPlanCuentaVista();
    }
    
    private void construirPlanCuentas()
    {
        planCuenta=new PlanCuenta();
        List<CuentaContable> cuentas=new ArrayList<CuentaContable>();
    
        //Cuentas Principales //
        CuentaContable cuentaActivo=new CuentaContable();
        cuentaActivo.setCodigo("1");
        cuentaActivo.setNombre("Activo");
        cuentaActivo.setImputable(EnumSiNo.NO.getLetra());
        
        CuentaContable cuentaPasivo=new CuentaContable();
        cuentaPasivo.setCodigo("2");
        cuentaPasivo.setNombre("Pasivo");
        cuentaPasivo.setImputable(EnumSiNo.NO.getLetra());
        
        CuentaContable cuentaPatrimonio=new CuentaContable();
        cuentaPatrimonio.setCodigo("3");
        cuentaPatrimonio.setNombre("Patrimonio");
        cuentaPatrimonio.setImputable(EnumSiNo.NO.getLetra());
        
        //Sub Cuentas
        CuentaContable cuentaActivoCorriente=new CuentaContable();
        cuentaActivoCorriente.setCodigo("11");
        cuentaActivoCorriente.setNombre("Activo Corriente");
        cuentaActivoCorriente.setImputable(EnumSiNo.NO.getLetra());
        cuentaActivoCorriente.setCuentaPadre(cuentaActivo);
        
        CuentaContable cuentaActivoNoCorriente=new CuentaContable();
        cuentaActivoNoCorriente.setCodigo("12");
        cuentaActivoNoCorriente.setNombre("Activo No Corriente");
        cuentaActivoNoCorriente.setImputable(EnumSiNo.NO.getLetra());
        cuentaActivoNoCorriente.setCuentaPadre(cuentaActivo);
        
        
        //Cuentas Imputables
        CuentaContable cuenta1=new CuentaContable();
        cuenta1.setCodigo("1101");
        cuenta1.setNombre("Cuenta Activo 1");
        cuenta1.setImputable(EnumSiNo.SI.getLetra());
        cuenta1.setCuentaPadre(cuentaActivoCorriente);
        
        
        CuentaContable cuenta2=new CuentaContable();
        cuenta2.setCodigo("1102");
        cuenta2.setNombre("Cuenta Activo 2");
        cuenta2.setImputable(EnumSiNo.SI.getLetra());
        cuenta2.setCuentaPadre(cuentaActivoCorriente);
        
        //Agregar cuentas a la lista
        cuentas.add(cuentaActivo);
        cuentas.add(cuentaPasivo);
        cuentas.add(cuentaPatrimonio);
        cuentas.add(cuentaActivoCorriente);
        cuentas.add(cuentaActivoNoCorriente);
        cuentas.add(cuenta1);
        cuentas.add(cuenta2);
        

        planCuenta.setCuentaContableList(cuentas);
    }
    
    private NodoTreeCuenta buscarCuentaEnNodo(CuentaContable cuenta)
    {
        TreeNode nodoRaiz=(TreeNode) modeloTree.getRoot();
        return buscarNodo(nodoRaiz,cuenta);
    }
    
    /**
     * Busco un nodo en el arbol jtree
     * @param nodo
     * @param cuentaBuscar
     * @return 
     */
    private  NodoTreeCuenta buscarNodo(TreeNode nodo, CuentaContable cuentaBuscar) {

        if (nodo.getChildCount() >= 0) {
            for (Enumeration e = nodo.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                NodoTreeCuenta nodoCuenta = (NodoTreeCuenta) n;

                if (nodoCuenta.cuenta.getCodigo().equals(cuentaBuscar.getCodigo())) {
                    return nodoCuenta;
                }
                else
                {
                    NodoTreeCuenta nodoBuscado=buscarNodo(n,cuentaBuscar);
                    if(nodoBuscado!=null)
                    {
                        return nodoBuscado;
                    }
                    
                }

            }
        }
        return null;
    }
     
    public void construirArbolPlanCuentaVista()
    {
        CuentaContable cuentaRaiz=new CuentaContable();
        cuentaRaiz.setCodigo("");
        cuentaRaiz.setNombre("Plan de Cuenta");        
        
        //Construye un cuenta por defecto para que contenga a todas las demas cuentas
        nodoCuentaRaiz=new NodoTreeCuenta(cuentaRaiz);
        modeloTree=new DefaultTreeModel(nodoCuentaRaiz);
        
        List<CuentaContable> cuentas=planCuenta.getCuentaContableList();
        
        //Ordenera cuentas para imprimir en orden de menor a mayor
        Collections.sort(cuentas,new Comparator<CuentaContable>() {
            @Override
            public int compare(CuentaContable o1, CuentaContable o2) {
                return o2.getCodigo().compareTo(o1.getCodigo());
            }
        });
        
        
        //Va a construir todos los nodos partiendo de los principal
        for (CuentaContable cuenta : cuentas) {
            NodoTreeCuenta nodoCuenta=buscarCuentaEnNodo(cuenta);
            
            //Si me devuelve null significa que este nodo aun no esta insertado
            if(nodoCuenta==null)
            {
                construirNodoCuenta(cuenta);
            }
            
        }
        
        //Setear el modelo construido en el jtree
        getTreePlanCuentas().setModel(modeloTree);
    }
    
    private NodoTreeCuenta construirNodoCuenta(CuentaContable cuentaCrear)
    {
        //Si no tiene referencia le ligo al nodo por defecto de la raiz
        NodoTreeCuenta nodoCuentaPadre=null;
        if(cuentaCrear.getCuentaPadre()==null)
        {
            nodoCuentaPadre=nodoCuentaRaiz;
        }
        else
        {
            //Si el nodo existe verifico si ya fue insertado o para crearlo
            nodoCuentaPadre=buscarCuentaEnNodo(cuentaCrear.getCuentaPadre());
        }
        //NodoTreeCuenta nodoCuentaPadre=buscarCuentaEnNodo(cuentaCrear);
        
        //Si no existe el nodo padre , primero toca insertar ese
        if (nodoCuentaPadre == null) 
        {
            nodoCuentaPadre=construirNodoCuenta(cuentaCrear.getCuentaPadre());
        }
        
        NodoTreeCuenta nodoCuentaCrear=new NodoTreeCuenta(cuentaCrear);
        modeloTree.insertNodeInto(nodoCuentaCrear, nodoCuentaPadre, 0);
        System.out.println("Creando: "+nodoCuentaCrear+"->"+nodoCuentaPadre);
        return nodoCuentaCrear; //Retornar el nodo creado
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
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void componentesIniciales() {
        getCmbImputable().removeAllItems();
        getCmbImputable().addItem(EnumSiNo.SI);
        getCmbImputable().addItem(EnumSiNo.NO);
        getBtnEditar().setEnabled(false);
        getBtnGuardar().setEnabled(false);
    }

    private void listenerPopUp() {
        JPopupMenu jpopMenu=new JPopupMenu();
        JMenuItem nuevoItem=new JMenuItem("Nuevo");
        JMenuItem eliminarItem=new JMenuItem("Eliminar");
        JMenuItem editarItem=new JMenuItem("Editar");
        jpopMenu.add(nuevoItem);
        jpopMenu.add(editarItem);
        jpopMenu.add(eliminarItem);
        
        nuevoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NodoTreeCuenta nodoSeleccionado=(NodoTreeCuenta) getTreePlanCuentas().getSelectionPath().getLastPathComponent();                
                //DefaultMutableTreeNode nodoNuevo = new DefaultMutableTreeNode("nuevo");
                //modeloTree.insertNodeInto(nodoNuevo,nodoSeleccinado,0);*/
                nodoCuentaSeleccionada=nodoSeleccionado;
                getBtnGuardar().setEnabled(true);
                
                
            }
        });
        
        editarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NodoTreeCuenta nodoSeleccionado=(NodoTreeCuenta) getTreePlanCuentas().getSelectionPath().getLastPathComponent();   
                nodoCuentaSeleccionada=nodoSeleccionado;
                getBtnEditar().setEnabled(true);
                
                
            }
        });
        
        eliminarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                NodoTreeCuenta nodoSeleccionado=(NodoTreeCuenta) getTreePlanCuentas().getSelectionPath().getLastPathComponent();                
                //a
                
            }
        });
        
        
        getTreePlanCuentas().setComponentPopupMenu(jpopMenu);
        
    }

    private void listenerTree() {
        getTreePlanCuentas().addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode nodoSeleccionado=(DefaultMutableTreeNode) e.getPath().getLastPathComponent();
                //nodoSeleccionado.get
            }
        });
    }

    private void listenerBotones() {
        
        getBtnCargarPlantilla().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlanCuenta.PlanCuentaPlantillaEnum planillaEnum= (PlanCuenta.PlanCuentaPlantillaEnum) getCmbPlantilla().getSelectedItem();
                planCuenta.setCuentaContableList(planillaEnum.getPlanCuenta().getCuentaContableList());
                construirArbolPlanCuentaVista();
            }
        });
        
        getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getBtnEditar().isEnabled())
                {
                    setearDatosPantalla(nodoCuentaSeleccionada.cuenta);
                    DialogoCodefac.mensaje("Correcto","La cuenta se edito correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                    construirArbolPlanCuentaVista();
                    getBtnEditar().setEnabled(false);
                }
            }
        });
        
        
        getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Solo dejar agregar si esta activo el boton
                if(getBtnGuardar().isEnabled())
                {
                    CuentaContable cuenta=new CuentaContable();
                    setearDatosPantalla(cuenta);
                    cuenta.setCuentaPadre(nodoCuentaSeleccionada.cuenta); //Setear la cuenta del padre
                    planCuenta.getCuentaContableList().add(cuenta);
                    DialogoCodefac.mensaje("Correcto","La cuenta se agrego correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                    construirArbolPlanCuentaVista();
                    getBtnGuardar().setEnabled(false);
                   
                }
            }
        });
    }
    
    private void setearDatosPantalla(CuentaContable cuenta)
    {
        cuenta.setCodigo(getTxtCodigo().getText());
        cuenta.setNombre(getTxtNombre().getText());
        EnumSiNo estadoEnum = (EnumSiNo) getCmbImputable().getSelectedItem();
        cuenta.setImputable(estadoEnum.getLetra());
        
    }

}
