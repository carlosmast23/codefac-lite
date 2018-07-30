/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.transporte.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.TransportistaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion.DetalleGuiaRemisionComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DetalleProductoGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.transporte.panel.GuiaRemisionPanel;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwingX;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.collections4.map.HashedMap;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionModel extends GuiaRemisionPanel{
    
    private GuiaRemision guiaRemision;
    private Transportista transportista;
    private DestinatarioGuiaRemision destinatarioGuiaRemision;
    private Persona destinatario;
    private Factura facturaSeleccionada;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerTextBox();
        listenerBotones();
        iniciarComponentesPantalla();
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
        getLblRuc().setText(session.getEmpresa().getIdentificacion());
         getLblTelefonos().setText(session.getEmpresa().getTelefonos());
        getLblNombreComercial().setText(session.getEmpresa().getNombreLegal());
        getLblDireccion().setText(session.getEmpresa().getDireccion());
        getLblCantidadProductos().setText("0");
        cargarSecuencial();
        
        ///Limpiar Variables
        guiaRemision=new GuiaRemision();
        transportista=new Transportista();
        destinatarioGuiaRemision=new DestinatarioGuiaRemision();
        destinatario=new Persona();
    }
    
     public void cargarSecuencial()
    {
        String secuencial="";
        
        boolean facturacionElectronica = session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).valor.equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra());
        if (facturacionElectronica) {
            secuencial = session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_GUIA_REMISION).valor;
        } else {
            secuencial = session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_GUIA_REMISION_FISICA).valor;
        }


        String preimpreso = UtilidadesTextos.llenarCarateresIzquierda(secuencial.toString(), 8, "0");
        String establecimiento = session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor;
        String puntoEmision = session.getParametrosCodefac().get(ParametroCodefac.PUNTO_EMISION).valor;
        preimpreso=puntoEmision + "-" + establecimiento + "-" + preimpreso;
        getLblSecuencial().setText(preimpreso);
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

    private void iniciarComponentesPantalla() {
        agregarPlaceHolder();
    }

    private void agregarPlaceHolder() {
        UtilidadesSwingX.placeHolder("Identificación",getTxtIdentificacionDestinatario());
        UtilidadesSwingX.placeHolder("Identificación",getTxtIdentificacionTransportista());
        UtilidadesSwingX.placeHolder("Autorización", getTxtAutorizacion());
        UtilidadesSwingX.placeHolder("Preimpreso", getTxtPreimpreso());
        UtilidadesSwingX.placeHolder("Doc.Aduanero", getTxtDocAduanero());
        
        UtilidadesSwingX.placeHolder("Dirección Destino", getTxtDireccionDestino());
        UtilidadesSwingX.placeHolder("Motivo Traslado", getTxtMotivoTraslado());
        UtilidadesSwingX.placeHolder("Ruta", getTxtRuta());
        
        
        
    }

    private void listenerTextBox() {
        
        getTxtIdentificacionDestinatario().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                String identificacion=getTxtIdentificacionDestinatario().getText();
                if(!identificacion.equals(""))
                {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            Map<String, Object> mapParametros = new HashedMap<String, Object>();
                            mapParametros.put("identificacion", identificacion);
                            List<Persona> resultados=ServiceFactory.getFactory().getPersonaServiceIf().obtenerPorMap(mapParametros); //Todo crear mejor un metodo que ya obtener filtrado los datos
                            if(resultados.size()==0)
                            {
                                if(DialogoCodefac.dialogoPregunta("Crear Cliente","No existe el Cliente, lo desea crear?",DialogoCodefac.MENSAJE_ADVERTENCIA))
                                {
                                    btnListenerAgregarCliente();
                                }
                            }
                            else
                            {
                                cargarCliente(resultados.get(0));
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ServicioCodefacException ex) {
                            Logger.getLogger(GuiaRemisionModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    
    private void btnListenerAgregarCliente()
    {
        Object[] parametros={OperadorNegocioEnum.CLIENTE,getTxtIdentificacionDestinatario().getText()};
        panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Persona>() {
            @Override
            public void updateInterface(Persona entity) {
                if(entity!=null)
                {
                    destinatario=entity;
                }
            }
        },VentanaEnum.CLIENTE, false,parametros,this);
    }
   
    private void cargarCliente(Persona cliente)
    {
        if (cliente != null) {
            //factura.setCliente(cliente);
            //Elimino datos adicionales del anterior cliente si estaba seleccionado
            //factura.eliminarTodosDatosAdicionales();
            
            //cargarFormaPago();
            getTxtIdentificacionDestinatario().setText(cliente.getIdentificacion());
            getLblNombresCompletosDestinatarios().setText(cliente.getNombresCompletos());
            getTxtDireccionDestino().setText(cliente.getDireccion());             
        };
    }
    
    private void btnListenerBuscarCliente() {
        ClienteFacturacionBusqueda clienteBusquedaDialogo = new ClienteFacturacionBusqueda();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        //factura.setCliente((Persona) buscarDialogoModel.getResultado());      
        if(buscarDialogoModel.getResultado()!=null)
        {
            destinatario=(Persona) buscarDialogoModel.getResultado();
            cargarCliente(destinatario);        
        }
        
    }
    
    private void cargarDatoFactura(Factura factura)
    {
        getTxtPreimpreso().setText(factura.getPreimpreso());
        getTxtAutorizacion().setText(factura.getClaveAcceso());
        getCmbFechaFactura().setDate(factura.getFechaEmision());
   }
    
    private DestinatarioGuiaRemision crearDestinatario()
    {
        DestinatarioGuiaRemision destinatario=new DestinatarioGuiaRemision();
        destinatario.setAutorizacionNumero(getTxtAutorizacion().getText());
        destinatario.setCodDucumentoSustento(""); //TODO: falta ver si solo pongo el codigo de la factura o pueden haber otros documentos que se pueden transportar
        destinatario.setDestinatorio(this.destinatario);
        destinatario.setDireccionDestino(getTxtDireccionDestino().getText());
        destinatario.setFechaEmision(new java.sql.Date(getCmbFechaFactura().getDate().getTime()));
        destinatario.setGuiaRemision(guiaRemision);
        destinatario.setMotivoTranslado(getTxtMotivoTraslado().getText());
        destinatario.setPreimpreso(getTxtPreimpreso().getText());
        destinatario.setRazonSocial(destinatario.getRazonSocial());
        destinatario.setRuta(destinatario.getRuta());
        destinatario.setReferenciaDocumentoId(facturaSeleccionada.getId());
        
        ///Agregado detalle  de los productos de la factura enlazada
        for (FacturaDetalle facturaDetalle : facturaSeleccionada.getDetalles()) {
            DetalleProductoGuiaRemision detalle=new DetalleProductoGuiaRemision();
            detalle.setCantidad(facturaDetalle.getCantidad().intValue());
            detalle.setCodigoAdicional("");
            detalle.setCodigoInterno(facturaDetalle.getReferenciaId()+"");
            detalle.setDescripcion(facturaDetalle.getDescripcion());
            detalle.setReferenciaId(facturaDetalle.getReferenciaId());
            destinatario.addProducto(detalle);
        }        
        
        return destinatario;
    }
    
    private void cargarDatosTransportista() {
        if(guiaRemision.getTransportista()!=null)
        {
            getLblNombresTransportista().setText(guiaRemision.getTransportista().getRazonSocial());
            getLblPlacaTransportista().setText(guiaRemision.getTransportista().getPlacaVehiculo());
        }
    }
    
    
    private void listenerBotones() {
        
        getBtnBuscarTransportista().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransportistaBusquedaDialogo transportistaBusquedaDialogo = new TransportistaBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(transportistaBusquedaDialogo);
                Transportista transportistaTemp = (Transportista) buscarDialogoModel.getResultado();
                if (transportistaTemp != null) {                    
                    guiaRemision.setTransportista(transportistaTemp);
                    cargarDatosTransportista();
                }
            }

        });
        
        getBtnAgregarDestinarioGuiaRemision().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DestinatarioGuiaRemision destinatarioGuiaRemision=crearDestinatario();
                guiaRemision.addDestinario(destinatarioGuiaRemision); 
                cargarDestinatariosAgregados();
                imprimirTabla();
            }
        });
        
        getBtnAgregarDestinatario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerAgregarCliente();
            }
        });
        
        getBtnBuscarDestinatario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerBuscarCliente();
            }
        });
        
        getBtnBuscarFactura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacturaBusqueda facturaBusqueda = new FacturaBusqueda();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(facturaBusqueda);
                buscarDialogoModel.setVisible(true);
                Factura facturaTmp = (Factura) buscarDialogoModel.getResultado();
                if(facturaTmp!=null)
                {
                    facturaSeleccionada=facturaTmp;
                    cargarDatoFactura(facturaTmp);
                }
                else
                {
                    facturaSeleccionada=null;
                }
                
            }
        });
    }
    
    private void imprimirTabla()
    {
        String[] titulos={"","Factura","FechaFact","Destinatario","Código Producto","Descripción","Cantidad"};
        
        DefaultTableModel modeloTabla=UtilidadesTablas.crearModeloTabla(titulos,
        new Class[]{DetalleProductoGuiaRemision.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class,
        String.class});
        
        for (DestinatarioGuiaRemision destinatarios : guiaRemision.getDestinatarios()) {
            for (DetalleProductoGuiaRemision detalle : destinatarios.getDetallesProductos()) {
                modeloTabla.addRow(new Object[]{
                    detalle,
                    detalle.getDestinatario().getPreimpreso(),
                    detalle.getDestinatario().getFechaEmision().toString(),
                    detalle.getDestinatario().getDestinatorio().getNombresCompletos(),
                    detalle.getCodigoInterno(),
                    detalle.getDescripcion(),
                    detalle.getCantidad(),
                });
              
            }            
        }
        
        //Imprimir el total de la cantidad de productos a transportar
        getLblCantidadProductos().setText(guiaRemision.obtenerTotalProductos().toString());
        
        getTblGuiaRemision().setModel(modeloTabla);
        UtilidadesTablas.ocultarColumna(getTblGuiaRemision(),0);
        
    }
    
    private void cargarDestinatariosAgregados()
    {
        getCmbDestinatarios().removeAllItems();
        for (DestinatarioGuiaRemision destinatario : guiaRemision.getDestinatarios()) {
            getCmbDestinatarios().addItem(destinatario);
        }
        
    }
    
    
}
