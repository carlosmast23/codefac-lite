/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import static ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface.ESTADO_EDITAR;
import ec.com.codesoft.codefaclite.facturacion.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.facturacion.busqueda.FacturaBusquedaNotaCredito;
import ec.com.codesoft.codefaclite.facturacion.busqueda.NotaCreditoBusqueda;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteFacturaImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteNotaCreditoImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.panel.NotaCreditoPanel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.ServicioSri;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataNotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoFacturacionEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.ejemplo.utilidades.rmi.UtilidadesRmi;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class NotaCreditoModel extends NotaCreditoPanel {

    private DefaultTableModel modeloTablaDetalle = new DefaultTableModel();
    private NotaCredito notaCredito;

    public NotaCreditoModel() {
        addListenerButtons();
    }
    
    private void setearValoresNotaCredito()
    {
        notaCredito.setEmpresaId(0l);
        //notaCredito.setEstado(Factura.ESTADO_FACTURADO);
        notaCredito.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        notaCredito.setRazonModificado(getTxtMotivoAnulacion().getText());
        notaCredito.setFechaNotaCredito(new Date(getjDateFechaEmision().getDate().getTime()));
        
        notaCredito.setPuntoEmision(session.getParametrosCodefac().get(ParametroCodefac.PUNTO_EMISION).valor);
        notaCredito.setPuntoEstablecimiento(session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor);
        notaCredito.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).valor));
        //notaCredito.setSubtotalCero(BigDecimal.ZERO);
        
        //Verificacion para cambiar el estado de la factura
        /*if(notaCredito.getTotal().compareTo(notaCredito.getFactura().getTotal())<0)
        {
            Factura.EstadoNotaCreditoEnum.ANULADO_TOTAL.getEstado();
            notaCredito.getFactura().setEstado(FacturaEnumEstado.ANULADO_PARCIAL.getEstado());
        }
        else
        {
            notaCredito.getFactura().setEstado(FacturaEnumEstado.ANULADO_TOTAL.getEstado());
        }*/
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        
        Boolean pregunta=DialogoCodefac.dialogoPregunta("Alerta","Esta seguro que desea grabar la Nota de Crédito",DialogoCodefac.MENSAJE_ADVERTENCIA);
        if(!pregunta)
        {
            throw new ExcepcionCodefacLite("cancelar el metodo grabar ...");
        }
        
        try {
            NotaCredito notaCreditoGrabada;
            NotaCreditoServiceIf servicio=ServiceFactory.getFactory().getNotaCreditoServiceIf();
            setearValoresNotaCredito();
            servicio.grabar(notaCredito);
            notaCreditoGrabada=notaCredito;//graba una referencia con ambiento del metodo para los listener
            
            ComprobanteDataNotaCredito comprobanteData=new ComprobanteDataNotaCredito(notaCredito);

            comprobanteData.setMapInfoAdicional(getMapAdicional(notaCredito));
             
            ClienteInterfaceComprobante cic=new ClienteNotaCreditoImplComprobante(this, notaCreditoGrabada);
            ComprobanteServiceIf comprobanteServiceIf=ServiceFactory.getFactory().getComprobanteServiceIf();
            comprobanteServiceIf.procesarComprobanteNotaCredito(comprobanteData,notaCredito,session.getUsuario(),cic);        
            

        } catch (ServicioCodefacException ex) {
            Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Map<String,String> getMapAdicional(NotaCredito notaCredito)
    {
        Map<String,String> parametroMap=new HashMap<String ,String>();
        for (NotaCreditoAdicional datoAdicional : notaCredito.getDatosAdicionales()) 
        {
            parametroMap.put(datoAdicional.getCampo(),datoAdicional.getValor());
        }
        return parametroMap;
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
                
        //Solo imprimir en el modo de editar
        if(estadoFormulario==ESTADO_EDITAR)
        {
            try {
                String claveAceeso = this.notaCredito.getClaveAcceso();
                byte[] byteReporte = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAceeso);
                JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteReporte);
                panelPadre.crearReportePantalla(jasperPrint,notaCredito.getPreimpreso());
            } catch (RemoteException ex) {
                Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        NotaCreditoBusqueda notaCreditoBusqueda = new NotaCreditoBusqueda();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(notaCreditoBusqueda);
        buscarDialogoModel.setVisible(true);
        NotaCredito notaCreditoTmp = (NotaCredito) buscarDialogoModel.getResultado();
        if(notaCreditoTmp != null)
        {
            crearDetalleTabla();
            this.notaCredito = notaCreditoTmp;
            mostrarDatosNotaCredito(); 
            actualizarDatosTablaDetalle();
            cargarTablaDatosAdicionales();
        }
        else
        {
            throw new ExcepcionCodefacLite("Cancelado metodo buscar");
        }
        
    }

    @Override
    public void limpiar() {
        try {
            /**
             * Cargar Datos de la empresa
             */
            getLblRuc().setText(session.getEmpresa().getIdentificacion());
            getLblDireccion().setText(session.getEmpresa().getDireccion());
            getLblTelefonos().setText(session.getEmpresa().getTelefonos());
            getLblNombreComercial().setText(session.getEmpresa().getRazonSocial());
            
            //Cargar el secuncial correspondiente
            NotaCreditoServiceIf servicio=ServiceFactory.getFactory().getNotaCreditoServiceIf();
            getLblSecuencial().setText(servicio.getPreimpresoSiguiente());
            
            
            notaCredito = new NotaCredito();
            crearDetalleTabla();
            limpiarCampos();
        } catch (RemoteException ex) {
            Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void limpiarCampos()
    {
        getLblNombreCliente().setText("");
        getLblDireccionCliente().setText("");
        getLblTelefonoCliente().setText("");
        getTxtMotivoAnulacion().setText("");
        
        BigDecimal valorCero=new BigDecimal("0.00");
        getLblSubtotalSinImpuesto().setText(valorCero.toString());
        getLblSubtotal12().setText(valorCero.toString());
        getLblSubtotal0().setText(valorCero.toString());
        getLblSubtotalNoObjetoDeIva().setText(valorCero.toString());
        getLblSubtotalExentoDeIva().setText(valorCero.toString());
        getLblTotalDescuento().setText(valorCero.toString());
        getLblValorIce().setText(valorCero.toString());
        getLblIva12().setText(valorCero.toString());
        getLblValorIRBPNR().setText(valorCero.toString());
        getLblPropina10().setText(valorCero.toString());
        getTxtValorTotal().setText(valorCero.toString());
        getTxtReferenciaFactura().setText("");
        
        //getTblDetalleNotaCredito().removeAll();
        
    }

    @Override
    public String getNombre() {
        return "Nota de Credito";
    }

    @Override
    public String getURLAyuda() {
        return "http://www.cf.codesoft-ec.com/ayuda#enotacredito";
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

    private void addListenerButtons() {
        
        getBtnAgregarDatosAdicionales().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatoAdicionalModel datoAdicional=new DatoAdicionalModel();
                datoAdicional.setVisible(true);
                
                String valor=datoAdicional.valor;
                String campo=datoAdicional.campo;
                
                FacturaAdicional.Tipo tipoEnum=datoAdicional.tipo;
                
                if(notaCredito!=null && valor!=null && tipoEnum!=null)
                {
                    if(tipoEnum.equals(FacturaAdicional.Tipo.TIPO_CORREO))
                    {
                        notaCredito.addDatosAdicionalCorreo(valor);
                    }
                    else
                    {
                        NotaCreditoAdicional dato=new NotaCreditoAdicional();
                        dato.setCampo(campo);
                        dato.setTipo(tipoEnum.getLetra());
                        dato.setNumero(0);
                        dato.setValor(valor);
                        notaCredito.addDatoAdicional(dato);
                        //factura.addDatoAdicional(dato);
                    }
                    cargarTablaDatosAdicionales();
                }
            }
        });
        
        getBtnBuscarFactura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacturaBusquedaNotaCredito  facturaBusqueda = new FacturaBusquedaNotaCredito();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(facturaBusqueda);
                buscarDialogoModel.setVisible(true);
                Factura factura = (Factura) buscarDialogoModel.getResultado();
                if (factura != null) {
                    notaCredito.setFactura(factura);
                    cargarDatosNotaCredito();
                    cargarDatosAdicionales();
                    cargarTablaDatosAdicionales();
                }
            }
        });
    }
    
    private void cargarDatosAdicionales()
    {
        //Si vuelve a escoger otra factura se borran los datos adicionales
         if(notaCredito.getDatosAdicionales()!=null)
            notaCredito.getDatosAdicionales().clear();
        
         List<FacturaAdicional> datosAdicional=notaCredito.getFactura().getDatosAdicionales();
         if(datosAdicional!=null)
         {
             List<NotaCreditoAdicional> datosAdicionalNotaCredito=new ArrayList<NotaCreditoAdicional>();
             for (FacturaAdicional facturaDetalle : datosAdicional) {
                 NotaCreditoAdicional notaCreditoAdicional=new NotaCreditoAdicional();
                 notaCreditoAdicional.setCampo(facturaDetalle.getCampo());
                 notaCreditoAdicional.setNotaCredito(notaCredito);
                 notaCreditoAdicional.setNumero(facturaDetalle.getNumero());
                 notaCreditoAdicional.setTipo(facturaDetalle.getTipo());
                 notaCreditoAdicional.setValor(facturaDetalle.getValor());
                 datosAdicionalNotaCredito.add(notaCreditoAdicional);
             }
             notaCredito.setDatosAdicionales(datosAdicionalNotaCredito);
         }

    }

    private void cargarDatosNotaCredito() {
        
        /**
         * Setear datos de la factura a la nota de credito
         */
        notaCredito.setTotal(notaCredito.getFactura().getTotal());
        notaCredito.setValorIvaDoce(notaCredito.getFactura().getIva());
        notaCredito.setSubtotalCero(notaCredito.getFactura().getSubtotalSinImpuestos());
        notaCredito.setSubtotalDoce(notaCredito.getFactura().getSubtotalImpuestos());
        notaCredito.setCliente(notaCredito.getFactura().getCliente());
        
        /**
         * CargarDetallesNotaCredito
         */
        List<FacturaDetalle> detallesFactura = notaCredito.getFactura().getDetalles();
        if(notaCredito.getDetalles()!=null)
            notaCredito.getDetalles().clear();
        
        for (FacturaDetalle facturaDetalle : detallesFactura) {
            NotaCreditoDetalle notaDetalle = new NotaCreditoDetalle();
            notaDetalle.setCantidad(facturaDetalle.getCantidad());
            notaDetalle.setDescripcion(facturaDetalle.getDescripcion());
            notaDetalle.setDescuento(facturaDetalle.getDescuento());
            notaDetalle.setIva(facturaDetalle.getIva());
            notaDetalle.setPrecioUnitario(facturaDetalle.getPrecioUnitario());
            notaDetalle.setReferenciaId(facturaDetalle.getReferenciaId());
            notaDetalle.setTipoReferencia(facturaDetalle.getTipoDocumento());
            notaDetalle.setTotal(facturaDetalle.getTotal());
            notaDetalle.setValorIce(facturaDetalle.getValorIce());

            notaCredito.addDetalle(notaDetalle);
        }
        
        /**
         * Cargar los datos Adicionales
         */
        /*
         List<FacturaAdicional> datosAdicional=notaCredito.getFactura().getDatosAdicionales();
         if(datosAdicional!=null)
         {
             List<NotaCreditoAdicional> datosAdicionalNotaCredito=new ArrayList<NotaCreditoAdicional>();
             for (FacturaAdicional facturaDetalle : datosAdicional) {
                 NotaCreditoAdicional notaCreditoAdicional=new NotaCreditoAdicional();
                 notaCreditoAdicional.setCampo(facturaDetalle.getCampo());
                 notaCreditoAdicional.setNotaCredito(notaCredito);
                 notaCreditoAdicional.setNumero(facturaDetalle.getNumero());
                 notaCreditoAdicional.setTipo(facturaDetalle.getTipo());
                 notaCreditoAdicional.setValor(facturaDetalle.getValor());
                 datosAdicionalNotaCredito.add(notaCreditoAdicional);
             }
             notaCredito.setDatosAdicionales(datosAdicionalNotaCredito);
         }
        */
        
        actualizarDatosTablaDetalle();
        mostrarDatosNotaCredito();

    }

    private void mostrarDatosNotaCredito() {
        getTxtReferenciaFactura().setText(notaCredito.getFactura().getPreimpreso());
        getLblNombreCliente().setText(notaCredito.getFactura().getCliente().getRazonSocial());
        getLblDireccionCliente().setText(notaCredito.getFactura().getCliente().getDireccion());
        getLblTelefonoCliente().setText(notaCredito.getFactura().getCliente().getTelefonoCelular());
        getTxtMotivoAnulacion().setText(notaCredito.getRazonModificado());
        
        //Cargar el preimpreso solo cuando el estado sea editar
        if(estadoFormulario.equals(ESTADO_EDITAR))
            getLblSecuencial().setText(notaCredito.getPreimpreso());

        /**
         * Cargar Los calculos de los totales
         */
        getTxtValorTotal().setText(notaCredito.getTotal() + "");
        getLblIva12().setText(notaCredito.getSubtotalDoce() + "");
        getLblSubtotal0().setText(notaCredito.getSubtotalCero() + "");
        getLblSubtotal12().setText(notaCredito.getSubtotalDoce() + "");
    }

    private void crearDetalleTabla() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Codigo");
        titulo.add("Valor Uni");
        titulo.add("Cantidad");
        titulo.add("Descripcion");
        titulo.add("Valor Total");

        this.modeloTablaDetalle = new DefaultTableModel(titulo, 0);
        //this.modeloTablaDetallesProductos.isCellEditable
        getTblDetalleNotaCredito().setModel(this.modeloTablaDetalle);
    }

    private void actualizarDatosTablaDetalle() {
        crearDetalleTabla();
        List<NotaCreditoDetalle> detalles = notaCredito.getDetalles();
        for (NotaCreditoDetalle detalle : detalles) {
            try {
                if(detalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.ACADEMICO))
                {
                    RubroEstudiante rubroEstudiante=ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(detalle.getReferenciaId());
                    Vector<String> fila = new Vector<String>();
                    fila.add(rubroEstudiante.getId().toString());
                    fila.add(rubroEstudiante.getValor().toString());
                    fila.add(detalle.getCantidad() + "");
                    fila.add(detalle.getDescripcion());
                    fila.add(detalle.getTotal() + "");
                    this.modeloTablaDetalle.addRow(fila);                    
                }
                else
                {
                    //TODO:Terminar de implementar las otras filas
                    if (detalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.VENTA)) {
                        Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(detalle.getReferenciaId());
                        Vector<String> fila = new Vector<String>();
                        fila.add((producto.getCodigoPersonalizado() != null) ? producto.getCodigoPersonalizado() : "");
                        fila.add(producto.getValorUnitario() + "");
                        fila.add(detalle.getCantidad() + "");
                        fila.add(detalle.getDescripcion());
                        fila.add(detalle.getTotal() + "");
                        this.modeloTablaDetalle.addRow(fila);
                    }

                }
            } catch (RemoteException ex) {
                Logger.getLogger(NotaCreditoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        if(!validacionParametrosCodefac())
        {
            dispose();
            throw new ExcepcionCodefacLite("No cumple validacion inicial");
        }
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean validacionParametrosCodefac() {
        String mensajeValidacion="Esta pantalla requiere : \n";
        boolean validado=true;
        
               //Validacion cuando solo sea facturacion manual
        if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).getValor().equals(TipoFacturacionEnumEstado.NORMAL.getLetra()))
        {
            DialogoCodefac.mensaje("Advertencia","Pantalla solo dispinible para facturación electronica",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        else
        {
        
            if(session.getParametrosCodefac().get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).getValor().equals(""))
            { 
                mensajeValidacion+=" - Archivo Firma\n";
                validado= false;
            }

            if(session.getParametrosCodefac().get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).getValor().equals(""))
            { 
                mensajeValidacion+=" - Clave Firma\n";
                validado= false;
            }

            if(session.getParametrosCodefac().get(ParametroCodefac.CORREO_USUARIO).getValor().equals(""))
            { 
                mensajeValidacion+=" - Correo\n";
                validado= false;
            }

            if(session.getParametrosCodefac().get(ParametroCodefac.CORREO_USUARIO).getValor().equals(""))
            { 
                mensajeValidacion+=" - Clave Correo \n";
                validado= false;
            }

            if(session.getEmpresa() == null)
            {
                mensajeValidacion+=" - Información de Empresa \n";
                validado= false;
            }
        }
        
        if(!validado)
        {
            //mensajeValidacion=mensajeValidacion.substring(0,mensajeValidacion.length()-2);
            DialogoCodefac.mensaje("Acceso no permitido", mensajeValidacion+"\nPofavor complete estos datos en configuración para usar esta pantalla",DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
        
        
        
        
        return validado;
        
    }
    
     /**
     * Metodo que actualiza los valores en la tabla
     */    
    private void cargarTablaDatosAdicionales() 
    {
        Vector<String> titulo = new Vector<>();
        titulo.add("Nombre");
        titulo.add("Valor");

        DefaultTableModel modeloTablaDatosAdicionales = new DefaultTableModel(titulo, 0);
       
        for (NotaCreditoAdicional datoAdicional : notaCredito.getDatosAdicionales()) {
            Vector dato = new Vector();
            dato.add(datoAdicional.getCampo());
            dato.add(datoAdicional.getValor());
            
            modeloTablaDatosAdicionales.addRow(dato);
        }
        
        getTblDatosAdicionales().setModel(modeloTablaDatosAdicionales);

    }

    private void setearDatosEmpresa()
    {
        getLblRuc().setText(notaCredito.getIdentificacion());
        getLblDireccion().setText(notaCredito.getDireccion());
        getLblNombreComercial().setText(notaCredito.getRazonSocial());
        getLblTelefonos().setText(notaCredito.getTelefono());
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
