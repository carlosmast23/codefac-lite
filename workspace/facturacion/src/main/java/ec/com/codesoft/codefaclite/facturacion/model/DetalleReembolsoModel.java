/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ComponentBindingAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacion.panel.DetalleReembolsoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ReembolsoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RembolsoImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class DetalleReembolsoModel extends DetalleReembolsoPanel implements DialogInterfacePanel<ReembolsoDetalle>{
    
    private ReembolsoDetalle rembolsoDetalle;
    private Impuesto impuestoIva;

    public DetalleReembolsoModel() 
    {
        
    }
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException 
    {
        listenerBotones();
        cargarDatosIniciales();
    }
    
    public void cargarDatosDefecto()
    {
        try 
        {
            impuestoIva=ServiceFactory.getFactory().getImpuestoServiceIf().obtenerImpuestoPorCodigo("2");            
        } catch (RemoteException ex) {
            Logger.getLogger(DetalleReembolsoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listenerBotones()
    {
        getBtnBuscarProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                ProveedorBusquedaDialogo buscarBusquedaDialogo = new ProveedorBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                Persona proveedor = ((PersonaEstablecimiento) buscarDialogo.getResultado()).getPersona();                
                rembolsoDetalle.setProveedor(proveedor);
                actualizarVista();
                
            }
        });
        
        getBtnAgregarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                ImpuestoDetalle impuestoDetalle=(ImpuestoDetalle) getCmbPorcentaje().getSelectedItem();                
                RembolsoImpuestoDetalle detalle=new RembolsoImpuestoDetalle();
                detalle.setImpuesto(impuestoIva);
                detalle.setPorcentajeIva(impuestoDetalle.getTarifa()); //Dejo ingresado por defecto
                detalle.setBaseImponible(new BigDecimal(getTxtBase().getText()));
                
                rembolsoDetalle.agregarImpuestoRembolso(detalle);
                actualizarVista();
                
            }
        });
        
        getBtnEliminarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                rembolsoDetalle.getDetalleList().clear();
                actualizarVista();
                
            }
        });
    }
    
    private void actualizarVista()
    {
        String titulo[]={"Impuesto","Porcentaje","Base"};
        DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
        
        if(rembolsoDetalle!=null)
        {
            Persona proveedor=rembolsoDetalle.getProveedor();
            if(proveedor!=null)
            {
                getTxtProveedorDescripcion().setText(proveedor.getIdentificacion()+" - "+proveedor.getNombreSimple());
            }
            
            
            //Actualizar datos de los detalles
            if(rembolsoDetalle.getDetalleList()!=null)
            {
                for (RembolsoImpuestoDetalle impuestoDetalle : rembolsoDetalle.getDetalleList()) 
                {                   
                    
                    Vector filaDatos=new Vector();
                    filaDatos.add("IVA");
                    filaDatos.add(impuestoDetalle.getPorcentajeIva()+"%");
                    filaDatos.add(impuestoDetalle.getBaseImponible());
                    
                    modeloTabla.addRow(filaDatos);
                    
                }
            }
            else
            {
                getTblImpuestoDetalle().setModel(new DefaultTableModel());
            }
        }
        getTblImpuestoDetalle().setModel(modeloTabla);
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        setearDatos();
    }
    
    private void setearDatos()
    {
        rembolsoDetalle.setPuntoEstablecimiento(Integer.parseInt(getTxtEstablecimientoCompra().getText()));
        rembolsoDetalle.setPuntoEmision(Integer.parseInt(getTxtPuntoEmisionCompra().getText()));
        rembolsoDetalle.setSecuencial(Long.parseLong(getTxtSecuencialCompra().getText()));
        
        rembolsoDetalle.setFechaEmision(getCmbFechaCompra().getDate());
        rembolsoDetalle.setNumeroAutorizacion(getTxtAutorizacion().getText());
        
        rembolsoDetalle.setDescripcion(getTxtDescripcionReembolso().getText());
        
        
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void limpiar() {
        this.rembolsoDetalle=new ReembolsoDetalle();
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    //-- GET AND SET

    public ReembolsoDetalle getRembolsoDetalle() {
        return rembolsoDetalle;
    }

    public void setRembolsoDetalle(ReembolsoDetalle rembolsoDetalle) {
        this.rembolsoDetalle = rembolsoDetalle;
    }

    @Override
    public ReembolsoDetalle getResult() throws ExcepcionCodefacLite 
    {
        setearDatos();
        validarDatos();
        System.out.println(" retorando objeto del rembolso");
        return rembolsoDetalle;
    }
    
    private void validarDatos() throws ExcepcionCodefacLite 
    {
        if(rembolsoDetalle.getProveedor()==null)
        {
            throw new ExcepcionCodefacLite("No se puede grabar sin ingresar un proveedor");
        }
        
        if(UtilidadesTextos.verificarNullOVacio(rembolsoDetalle.getNumeroAutorizacion()))
        {
            throw new ExcepcionCodefacLite("No se puede grabar sin ingresar un número de autorización");
        }
        
        if(rembolsoDetalle.getNumeroAutorizacion().length()<=9)
        {
            throw new ExcepcionCodefacLite("El número de autorización es muy corto");
        }
        
        if(rembolsoDetalle.getFechaEmision()==null)
        {
            throw new ExcepcionCodefacLite("No se puede grabar sin ingresar una fecha");
        }
        
        if(rembolsoDetalle.getDetalleList()==null || rembolsoDetalle.getDetalleList().size()==0)
        {
            throw new ExcepcionCodefacLite("No se puede grabar sin ingresar detalles de los impuestos");
        }
        
        
        
    }

    private void cargarDatosIniciales() {
        try {
            List<ImpuestoDetalle> impuestoDetalleList = ServiceFactory.getFactory().getImpuestoDetalleServiceIf().obtenerIvaVigente();
            UtilidadesComboBox.llenarComboBox(getCmbPorcentaje(), impuestoDetalleList);
        } catch (RemoteException ex) {
            Logger.getLogger(DetalleReembolsoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}