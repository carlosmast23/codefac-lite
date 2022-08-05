/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.dialogmodel;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.sistema.UtilidadesWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class FormaPagoDialogoMb implements Serializable{
    private FormaPago formaPago;
    private List<SriFormaPago> formaPagoList;
    private SriFormaPago formaPagoSeleccionado;
    
    @PostConstruct
    public void init()
    {
        formaPago=new FormaPago();        
        cargarFormasPagoSri();
    }    
    
    public void cargarFormasPagoSri()
    {
        try {
            SriServiceIf service=ServiceFactory.getFactory().getSriServiceIf();
            formaPagoList=service.obtenerFormasPagoActivo();
        } catch (RemoteException ex) {
            Logger.getLogger(FormaPagoDialogoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void retornarValor()
    {
        System.out.println("forma pago seleccionada: "+formaPagoSeleccionado);
        formaPago.setSriFormaPago(formaPagoSeleccionado);
        formaPago.setPlazo(0);
        UtilidadesWeb.retornarResultadoDialogo(formaPago);
    }
    
    /////////////////////////////////////////////////////////
    ///             GET AND SET
    /////////////////////////////////////////////////////////
    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public List<SriFormaPago> getFormaPagoList() {
        return formaPagoList;
    }

    public void setFormaPagoList(List<SriFormaPago> formaPagoList) {
        this.formaPagoList = formaPagoList;
    }

    public SriFormaPago getFormaPagoSeleccionado() {
        return formaPagoSeleccionado;
    }

    public void setFormaPagoSeleccionado(SriFormaPago formaPagoSeleccionado) {
        this.formaPagoSeleccionado = formaPagoSeleccionado;
    }
    
    
    
    
        
    
    
}
