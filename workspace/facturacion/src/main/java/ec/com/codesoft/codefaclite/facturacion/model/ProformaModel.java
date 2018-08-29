/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProformaBusqueda;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ProformaModel extends FacturacionModel{

    public ProformaModel() {
        super();
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        super.iniciar(); //To change body of generated methods, choose Tools | Templates.
        valoresIniciales();
    }
    
    

    private void valoresIniciales() {
        try {
            //nombre de la pantalla
            setTitle("Proforma");
            
            //cargar el documento de proforma
            getCmbDocumento().removeAllItems();
            getCmbDocumento().addItem(DocumentoEnum.PROFORMA);
            
            //desactivar el panel formas de pago porque no utilizo
            getPanelFormasPago().setVisible(false);
            
            //Obtener los secuenciales para las proformas
            Integer secuencial=ServiceFactory.getFactory().getFacturacionServiceIf().obtenerSecuencialProformas();
            getLblSecuencial().setText(secuencial.toString());
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @Override
    public void cargarSecuencial() {
        ServiceFactory.getFactory().getFacturacionServiceIf();
    }

    @Override
    public InterfaceModelFind getBusquedaInterface() {
        return new ProformaBusqueda();
    }

    @Override
    protected void setearValoresDefaultFactura() {
        super.setearValoresDefaultFactura(); //To change body of generated methods, choose Tools | Templates.
        factura.setSecuencial(Integer.parseInt(getLblSecuencial().getText()));
        //facrur
        
    }
    
    
    

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            validacionesGrabar(); //Metodo que realiza validaciones previas antes de grabar
            FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
            setearValoresDefaultFactura();
            factura = servicio.grabarProforma(factura);
            DialogoCodefac.mensaje("Correcto","Proforma generada correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
        }        

    }
    
    
    
    
    
}
