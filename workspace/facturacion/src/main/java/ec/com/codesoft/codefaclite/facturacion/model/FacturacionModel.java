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
import ec.com.codesoft.codefaclite.servidor.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Carlos
 */
public class FacturacionModel extends FacturacionPanel{

    private Persona persona;
    private Factura factura;
    private DefaultTableModel modeloTablaFormasPago;
    
    public FacturacionModel() {
        addListenerButtons();
        initModelTablaFormaPago();
        agregarFechaEmision();
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
        
        getBtnAgregarFormaPago().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormaPagoDialogModel dialog=new FormaPagoDialogModel(null,true);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                FormaPago formaPago=dialog.getFormaPago();
                agregarFormaPagoTabla(formaPago);
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
    
    private void agregarFormaPagoTabla(FormaPago formaPago)
    {
        Vector<String> fila=new Vector<>();
        fila.add(formaPago.getSriFormaPago().getNombre());
        fila.add(formaPago.getTotal().toString());
        fila.add(formaPago.getUnidadTiempo());
        fila.add(formaPago.getPlazo()+"");
        System.out.println(formaPago);
        this.modeloTablaFormasPago.addRow(fila);
    }
    
    private void initModelTablaFormaPago()
    {
        Vector<String> titulo=new Vector<>();
        titulo.add("forma pago");
        titulo.add("Valor");
        titulo.add("Tipo");
        titulo.add("Tiempo");
        
        this.modeloTablaFormasPago=new DefaultTableModel(titulo,0);
        getTblFormasPago().setModel(modeloTablaFormasPago);
    }

    private void agregarFechaEmision() {

    }

    
}
