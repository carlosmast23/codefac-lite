/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacion.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.facturacion.other.FacturacionElectronica;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturacionPanel;
import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class FacturacionModel extends FacturacionPanel{

    private Persona persona;
    private Factura factura;
    
    public FacturacionModel() {
        addListenerButtons();
    }
    
    private void addListenerButtons() {
        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ClienteFacturacionBusqueda clienteBusquedaDialogo= new ClienteFacturacionBusqueda();
                BuscarDialogoModel buscarDialogoModel=new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                persona=(Persona) buscarDialogoModel.getResultado();
                if(persona!=null)
                {
                    getTxtCliente().setText(persona.getIdentificacion());
                    getLblNombreCliente().setText(persona.getNombreSocial());
                    getLblDireccionCliente().setText(persona.getDireccion());
                    getLblTelefonoCliente().setText(persona.getTelefonoConvencional());                
                }
            }
        });
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        //Despues de implemetar todo el metodo de grabar
        FacturacionElectronica facturaElectronica=new FacturacionElectronica(factura, session);
        facturaElectronica.procesarComprobante();//listo se encarga de procesar el comprobante
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        getLblRuc().setText(session.getEmpresa().getIdentificacion());
        getLblDireccion().setText(session.getEmpresa().getDireccion());
        getLblTelefonos().setText(session.getEmpresa().getTelefonos());
        getLblNombreComercial().setText(session.getEmpresa().getNombreLegal());
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
        Map<Integer,Boolean> permisos=new HashMap<Integer,Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO,true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR,true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    
}
