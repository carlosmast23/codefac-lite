/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.compra.busqueda.CompraBusquedaDialogo;
import ec.com.codesoft.codefaclite.compra.busqueda.RetencionBusquedaDialogo;
import ec.com.codesoft.codefaclite.compra.callback.RetencionImplCallBack;
import ec.com.codesoft.codefaclite.compra.panel.RetencionPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwingX;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class RetencionModel extends RetencionPanel{

    /**
     * Compra seleccionado para enviar la retencion
     */
    //private Compra compra;
    private Retencion retencion;

    public RetencionModel() {
        this.validacionDatosIngresados=false;
    }
    
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        listener();        
        cargarDatosIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            
            if(!validar())
            {
                throw new ExcepcionCodefacLite("cancelado"); //Si no realiza la validacion se cancela el proceso
            }
            
            setearDatos();
            retencion=ServiceFactory.getFactory().getRetencionServiceIf().grabar(retencion);
            DialogoCodefac.mensaje("Correcto","La retenecion fue grabada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                    
            RetencionImplCallBack ric=new RetencionImplCallBack(retencion, this);
            ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
            
            ComprobanteDataRetencion comprobanteData = new ComprobanteDataRetencion(retencion);
            comprobanteData.setMapInfoAdicional(getMapAdicional(retencion));
            
            comprobanteServiceIf.procesarComprobante(comprobanteData, retencion, session.getUsuario(), ric);
            
            
                    
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error","Error al grabar los datos",DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(RetencionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error","No existe comunicación con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(RetencionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Map<String,String> getMapAdicional(Retencion retencion)
    {
        Map<String,String> parametroMap=new HashMap<String ,String>();
        if(retencion.getDatosAdicionales()!=null)
        {
            for (RetencionAdicional datoAdicional : retencion.getDatosAdicionales()) 
            {
                parametroMap.put(datoAdicional.getCampo(),datoAdicional.getValor());
            }
        }
        return parametroMap;
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if(estadoFormulario==ESTADO_EDITAR)
        {
            Boolean respuesta=DialogoCodefac.dialogoPregunta("Eliminar","Esta seguro que desea eliminar?",DialogoCodefac.MENSAJE_INCORRECTO);
            if(!respuesta)
            {
                throw new ExcepcionCodefacLite("Cancelado eliminar");
            }
            else
            {
                
            
            
            }
            
        }
        else
        {
            DialogoCodefac.mensaje("Error","Cargue un registro para eliminar",DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }

    @Override
    public void imprimir() {
        
        //Solo imprimir en el modo de editar
        if(estadoFormulario==ESTADO_EDITAR)
        {
            try {
                String claveAceeso = this.retencion.getClaveAcceso();
                byte[] byteReporte = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAceeso);
                JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteReporte);
                panelPadre.crearReportePantalla(jasperPrint,retencion.getPreimpreso());
            } catch (RemoteException ex) {
                Logger.getLogger(RetencionModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RetencionModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RetencionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        RetencionBusquedaDialogo retencionBusqueda = new RetencionBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(retencionBusqueda);
        buscarDialogoModel.setVisible(true);

        Retencion retencionTmp = (Retencion) buscarDialogoModel.getResultado();
        if (retencionTmp != null) {
            retencion = retencionTmp;
            Compra compra =new Compra();
            //Todo: Completar la funcionalidad para la busqueda
            cargarDatosRetencion();
            
        }
        else
        {
            throw new ExcepcionCodefacLite("Cancelado abrir dato editar");
        }
    }
    
    private void cargarDatosRetencion() {
        getLblSecuencial().setText(retencion.getPreimpreso());
        getjDateFechaEmision().setDate(retencion.getFechaEmision());
        
        cargarDatosCompra();
        cargarTablaDatosAdicionales(retencion);
    }
    
    /**
     * Esta funcion permite verificar si la retencion tiene referencia y si no tiene simplementa la crea para poder mostrar es decir la compra
     */
    private void revisarCompra(Retencion retencion)
    {
        //Si la referencia es null creo una compra temporal para mostrar los datos
        if(retencion.getTipoDocumentoEnum().equals(TipoDocumentoEnum.LIBRE))
        {
            Compra compra=new Compra();
            compra.setProveedor(retencion.getProveedor());
            for (RetencionDetalle detalle : retencion.getDetalles()) {
                //detalle.get
            }
        }
    }

    @Override
    public void limpiar() {
        retencion=new Retencion();
        
        getTblDatosAdicionales().setModel(new DefaultTableModel());
        getTblDetalleRetenciones().setModel(new DefaultTableModel());
        
        getTxtReferenciaFactura().setText("");
        getLblNombreCliente().setText("");
        getLblTelefonoCliente().setText("");
        getLblDireccionCliente().setText("");
        
        getLblSubtotalRetencionIva().setText("0.00");
        getLblSubtotalRetencionRenta().setText("0.00");
        getLblRetencionTotal().setText("0.00");
        
        getjDateFechaEmision().setDate(UtilidadesFecha.getFechaHoy());
        
        UtilidadesSwingX.placeHolder("Base Imponible",getTxtBaseImponible());
        UtilidadesSwingX.placeHolder("Preimpreso Compra",getTxtPreimpreso());
        
        getTxtPreimpreso().setText("");
        getCmbFechaDocumento().setDate(UtilidadesFecha.getFechaHoy());
        getTxtProveedor().setText("");
        getTxtBaseImponible().setText("");
        getCmbRetencionIva().setSelectedIndex(0);
        getCmbRetencionRenta().setSelectedIndex(0);
        
        cargarDatosEmpresa();
    }

//    @Override
    public String getNombre() {
        return "Retención";
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

    private void listener() {
        listenerCombos();
        listenerBotones();
        listenerTabla();
    }

    private void listenerBotones() {
        
        getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                   
                                //Setear la fecha de la facutura
                //TODO:pendiente
                
                
                /////------> CREAR EL DETALLE DE LA COMPRA <--------------/////////
                CompraDetalle compraDetalle=new CompraDetalle();
                //compraDetalle.setDescripcion(getTxtDescripcion().getText());
                compraDetalle.setCantidad(1);
                
                //TODO: Revisar si solo una base imponible o debo usar 2 para mas precion con productos que no distintas base de retencion        
                BigDecimal baseImponible=new BigDecimal(getTxtBaseImponible().getText());
                SriRetencionIva sriRetencionIva=(SriRetencionIva) getCmbRetencionIva().getSelectedItem();
                SriRetencionRenta sriRetencionRenta=(SriRetencionRenta) getCmbRetencionRenta().getSelectedItem();
                
                compraDetalle.setPrecioUnitario(baseImponible);
                compraDetalle.setDescuento(BigDecimal.ZERO);
                ParametroCodefac parametroCodefacIva=session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO);
                BigDecimal ivaActual=new BigDecimal(parametroCodefacIva.getValor().toString());
                
                BigDecimal valorSriRetencionIVA=baseImponible.multiply(ivaActual).multiply(sriRetencionIva.getPorcentaje()).divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP);
                //compraDetalle.setValorSriRetencionIVA(baseImponible.multiply(sriRetencionIva.getPorcentaje()).divide(new BigDecimal("100"),BigDecimal.ROUND_HALF_UP));
                compraDetalle.setValorSriRetencionIVA(valorSriRetencionIVA);
                compraDetalle.setValorSriRetencionRenta(baseImponible.multiply(sriRetencionRenta.getPorcentaje()).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP));
                
                compraDetalle.setSriRetencionIva(sriRetencionIva);
                compraDetalle.setSriRetencionRenta(sriRetencionRenta);
                
                retencion.getCompra().addDetalle(compraDetalle);
                
                cargarDatosCompra();
                
                //retencion.getCompra().addDetalle(detalle);
            }
        });
        
        getBtnBuscarProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorBusquedaDialogo proveedorBusquedaDialogo = new ProveedorBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(proveedorBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                
                if(buscarDialogoModel.getResultado()!=null)
                {
                    Persona proveedor=(Persona) buscarDialogoModel.getResultado();
                    Compra compraTmp=new Compra();
                    compraTmp.setProveedor(proveedor);
                    retencion.setCompra(compraTmp);
                    retencion.setProveedor(proveedor);
                    setearDatosCompraRetencion();
                    
                    
                    cargarDatosCompra();
                    cargarCorreoPorDefecto(compraTmp);
                    cargarTablaDatosAdicionales(retencion);
                    
                    //imprimir el valor en el campo de texto
                    getTxtProveedor().setText(proveedor.getIdentificacion()+" - "+proveedor.getNombresCompletos());
                    
                }
            }
        });
        
        getBtnBuscarFacturaCompra().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompraBusquedaDialogo compraBusqueda = new CompraBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(compraBusqueda);
                buscarDialogoModel.setVisible(true);
                
                Compra compraTmp = (Compra) buscarDialogoModel.getResultado();
                if (compraTmp != null) 
                {                    
                    retencion.setCompra(compraTmp);
                                        
                    cargarDatosCompra();
                    //Cargar dato por defecto del correo
                    cargarCorreoPorDefecto(compraTmp);
                    cargarTablaDatosAdicionales(retencion);
                    
                    
                }
            }
            
        });
    }
    
    private void setearDatosCompraRetencion()
    {
        retencion.setTelefono(retencion.getCompra().getProveedor().getTelefonoConvencional());
        retencion.setPreimpresoDocumento(retencion.getCompra().getPreimpreso());
        retencion.setRazonSocial(retencion.getProveedor().getNombresCompletos());
        retencion.setDireccion(retencion.getProveedor().getDireccion());
    }
    
    private void cargarCorreoPorDefecto(Compra compra)
    {
        //limpiar el detalle de datos adicionales
        if(retencion.getDatosAdicionales()!=null)
            retencion.getDatosAdicionales().clear();
        
        //Solo cargar si existe el correo y es distinto de vacio        
        if(compra.getProveedor().getCorreoElectronico()!=null && !compra.getProveedor().getCorreoElectronico().equals(""))
        {
            retencion.addDatosAdicionalCorreo(compra.getProveedor().getCorreoElectronico());
        }
    }
    
    private void cargarTablaDatosAdicionales(Retencion retencion)
    {
        String[] titulo={"Nombre","Valor"};
        DefaultTableModel modelTable=new DefaultTableModel(titulo,0);
        
        if(retencion.getDatosAdicionales()!=null)
        {
            for (RetencionAdicional retencionAdicional :  retencion.getDatosAdicionales())
            {
                String[] fila={retencionAdicional.getCampo(),retencionAdicional.getValor()};
                modelTable.addRow(fila);
            }
        }
        
        getTblDatosAdicionales().setModel(modelTable);
    }
    
    /**
     * Cargar los datos de la compra en la vista
     * @param compra 
     */
    private void cargarDatosCompra()
    {
        getTxtReferenciaFactura().setText(retencion.getPreimpresoDocumento());
        getLblNombreCliente().setText(retencion.getRazonSocial());
        getLblTelefonoCliente().setText(retencion.getTelefono());
        getLblDireccionCliente().setText(retencion.getDireccion());
        
        ///Cargar los detalles de la compra
        cargarTablaRetencion();
        
       
    }
    
    private void cargarTablaRetencion()
    {
        //Validar que solo cambia la forma de cargar cuando se carga en modo de edicion
        if(estadoFormulario.equals(ESTADO_EDITAR))
        {
            if(retencion.getTipoDocumentoEnum().equals(TipoDocumentoEnum.LIBRE))
            {
                cargarTablaSinReferencias();
            }
            else
            {
                cargarTablayTotalesRetenciones();
            }
        }
        else
        {
            cargarTablayTotalesRetenciones();
        }
    }
    
    private void cargarTablaSinReferencias()
    {
        DefaultTableModel datos = UtilidadesTablas.crearModeloTabla(new String[]{"Código","Base Imponible", "%Porcentaje", "Valor"},
                new Class[]{String.class,String.class, String.class, String.class});
        
        BigDecimal totalRetencionIva = new BigDecimal(BigInteger.ZERO);
        BigDecimal totalRetencionRenta = new BigDecimal(BigInteger.ZERO);
        BigDecimal totalRetenciones = new BigDecimal(BigInteger.ZERO);
        
         for (RetencionDetalle detalle : retencion.getDetalles()) {
             Vector<Object> fila = new Vector<>();
             fila.add(detalle.getCodigoRetencionSri());
             fila.add(detalle.getBaseImponible().toString());
             fila.add(detalle.getPorcentajeRetener());
             fila.add(detalle.getValorRetenido());
             datos.addRow(fila);
             
            // totalRetencionIva = totalRetencionIva.add(compraDetalle.getValorSriRetencionIVA());
            //    totalRetencionRenta = totalRetencionRenta.add(compraDetalle.getValorSriRetencionRenta());
            totalRetenciones=totalRetenciones.add(detalle.getValorRetenido());
         }
        
        getLblRetencionTotal().setText(totalRetenciones.toString());
        getLblSubtotalRetencionIva().setText("");
        getLblSubtotalRetencionRenta().setText("");
        
        getTblDetalleRetenciones().setModel(datos);
         
        /*
        DefaultTableModel datos = UtilidadesTablas.crearModeloTabla(new String[]{,"Base Imponible", "Codigo", "Retención Renta"},
                new Class[]{String.class,String.class, String.class, String.class});
        
        for (RetencionDetalle detalle : retencion.getDetalles()) {
            Vector<Object> fila = new Vector<>();
            //detalle.get
        }*/
    }
    
    private void cargarTablayTotalesRetenciones()
    {
        List<CompraDetalle> compraDetalles = retencion.getCompra().getDetalles();
        DefaultTableModel datos = UtilidadesTablas.crearModeloTabla(new String[]{"Obj", "Nombre","Base Imponible", "Retencia Iva", "Retención Renta"},
                new Class[]{CompraDetalle.class, String.class,String.class, String.class, String.class});

        BigDecimal totalRetencionIva = new BigDecimal(BigInteger.ZERO);
        BigDecimal totalRetencionRenta = new BigDecimal(BigInteger.ZERO);
        BigDecimal totalRetenciones = new BigDecimal(BigInteger.ZERO);

        if(compraDetalles!=null)
        {
            for (CompraDetalle compraDetalle : compraDetalles) {
                Vector<Object> fila = new Vector<>();
                fila.add(compraDetalle);
                fila.add(compraDetalle.getDescripcion());
                fila.add(compraDetalle.getBaseImponibleRenta().toString());
                fila.add(compraDetalle.getValorSriRetencionIVA());
                fila.add(compraDetalle.getValorSriRetencionRenta());
                totalRetencionIva = totalRetencionIva.add(compraDetalle.getValorSriRetencionIVA());
                totalRetencionRenta = totalRetencionRenta.add(compraDetalle.getValorSriRetencionRenta());
                datos.addRow(fila);
            }
        }

        //Suma de retencion Iva y retencion Renta
        totalRetenciones = totalRetenciones.add(totalRetencionIva).add(totalRetencionRenta);

        //Redondear valores a mostrar en pantalla a dos decimales
        totalRetencionIva = totalRetencionIva.setScale(2, BigDecimal.ROUND_HALF_UP);
        totalRetencionRenta = totalRetencionRenta.setScale(2, BigDecimal.ROUND_HALF_UP);
        totalRetenciones = totalRetenciones.setScale(2, BigDecimal.ROUND_HALF_UP);

        //Mostrar valores de retencion en pantall
        getLblSubtotalRetencionIva().setText(totalRetencionIva + "");
        getLblSubtotalRetencionRenta().setText(totalRetencionRenta + "");
        getLblRetencionTotal().setText(totalRetenciones + "");

        //Cargar detalles de cada compra y valores de retencion en las tablas
        getTblDetalleRetenciones().setModel(datos);

        //Ocultar la primera columna de la tabla
        UtilidadesTablas.ocultarColumna(getTblDetalleRetenciones(), 0);
    }

    private void cargarDatosEmpresa() {
        Empresa empresa=session.getEmpresa();
        getLblRuc().setText(empresa.getIdentificacion());
        getLblNombreComercial().setText(empresa.getNombreLegal());
        getLblDireccion().setText(empresa.getDireccion());
        getLblTelefonos().setText(empresa.getTelefonos());                

        getLblSecuencial().setText(obtenerSecuencial());
        
    }
    
    private String obtenerSecuencial()
    {
         /**
         * Cargar el secuencial segun el modo de facturacion seleccionado
         */
        ParametroCodefac paramTipoFacturacion=session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION);

        ComprobanteEntity.TipoEmisionEnum enumTipoFacturacion=ComprobanteEntity.TipoEmisionEnum.getEnumByEstado(paramTipoFacturacion.getValor());
        
        String secuencial="";
        if(enumTipoFacturacion.equals(ComprobanteEntity.TipoEmisionEnum.NORMAL))
        {
            secuencial=session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_RETENCION_FISICA).getValor();
        }
        else
        {
            if(enumTipoFacturacion.equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA))
            {
                secuencial=session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_RETENCION).getValor();
            }
        }
        
        String preimpreso = UtilidadesTextos.llenarCarateresIzquierda(secuencial.toString(), 8, "0");
        String establecimiento = session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor;
        String puntoEmision = session.getParametrosCodefac().get(ParametroCodefac.PUNTO_EMISION).valor;
        preimpreso=puntoEmision + "-" + establecimiento + "-" + preimpreso;
        
        return preimpreso;
    }

    private void cargarDatosIniciales() {
        
        try {
            //Agregar los tipos de retencion Iva
            getCmbRetencionIva().removeAllItems();
            SriRetencionIvaServiceIf sriRetencionIvaService = ServiceFactory.getFactory().getSriRetencionIvaServiceIf();
            List<SriRetencionIva> tipoRetencionesIva = sriRetencionIvaService.obtenerTodos();
            for (SriRetencionIva tipoRetencione : tipoRetencionesIva)
            {
                getCmbRetencionIva().addItem(tipoRetencione);
            }
            
            //Agregar los tipos de retencion renta
            getCmbRetencionRenta().removeAllItems();
            SriRetencionRentaServiceIf sriRetencionRentaService = ServiceFactory.getFactory().getSriRetencionRentaServiceIf();
            List<SriRetencionRenta> tipoRetencionesRenta = sriRetencionRentaService.obtenerTodos();
            for (SriRetencionRenta sriRetencionRenta : tipoRetencionesRenta) 
            {
                getCmbRetencionRenta().addItem(sriRetencionRenta);
            }
            
            ///Agregar los tipos de datos para emitir las retenciones
            getCmbTipoDocumento().removeAllItems();
            getCmbTipoDocumento().addItem(TipoDocumentoEnum.LIBRE);
            getCmbTipoDocumento().addItem(TipoDocumentoEnum.COMPRA);
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(RetencionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void setearDatos() {
        //retencion=new Retencion();
        //retencion.setCompra(compra);
        retencion.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        retencion.setFechaEmision(new java.sql.Date(getjDateFechaEmision().getDate().getTime()));
        retencion.setProveedor(retencion.getCompra().getProveedor());
        
        retencion.setPuntoEmision(session.getParametrosCodefac().get(ParametroCodefac.PUNTO_EMISION).valor);
        retencion.setPuntoEstablecimiento(session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor);
        retencion.setObligadoLlevarContabilidad(session.getEmpresa().getObligadoLlevarContabilidad());
        TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        retencion.setTipoDocumento(tipoDocumentoEnum.getCodigo());
        retencion.setPreimpresoDocumento(getTxtPreimpreso().getText().replaceAll("-",""));
        retencion.setFechaEmisionDocumento(new java.sql.Date(getCmbFechaDocumento().getDate().getTime()));
        
        //Cuando la facturacion es electronica
        if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).getValor().equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra()))
        {
            retencion.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_RETENCION).valor));
        }
        else //cuando la facturacion es normal
        {
            retencion.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_RETENCION_FISICA).valor));
        }
        
        //Llenar los detalles de la retencion
        if(retencion.getCompra().getDetalles()!=null)
        {
            for (CompraDetalle compraDetalle : retencion.getCompra().getDetalles()) {
                
                //Validaciones cuando hay detalles que no requerien enviar retencion con iva 0
                if(!compraDetalle.getSriRetencionIva().getCodigo().equals(SriRetencionIva.CODIGO_IVA_NO_APLICA))
                {
                    //Detalle para la retencion del iva
                    RetencionDetalle retencionDetalleIva = new RetencionDetalle();
                    retencionDetalleIva.setBaseImponible(compraDetalle.getBaseImponibleIva());
                    retencionDetalleIva.setCodigoSri(compraDetalle.getSriRetencionIva().getRetencion().getCodigo());
                    retencionDetalleIva.setCodigoRetencionSri(compraDetalle.getSriRetencionIva().getCodigo().toString());
                    retencionDetalleIva.setPorcentajeRetener(compraDetalle.getSriRetencionIva().getPorcentaje().setScale(2, BigDecimal.ROUND_HALF_UP));
                    retencionDetalleIva.setRetencion(retencion);
                    retencionDetalleIva.setValorRetenido(compraDetalle.getValorSriRetencionIVA());
                    System.out.println(compraDetalle.getValorSriRetencionIVA());       
                    retencion.addDetalle(retencionDetalleIva);
                }


                //Detalle para la retencion de la renta
                RetencionDetalle retencionDetalleRenta=new RetencionDetalle();
                retencionDetalleRenta.setBaseImponible(compraDetalle.getBaseImponibleRenta());
                retencionDetalleRenta.setCodigoSri(compraDetalle.getSriRetencionRenta().getRetencion().getCodigo());
                retencionDetalleRenta.setCodigoRetencionSri(compraDetalle.getSriRetencionRenta().getCodigo().toString());
                retencionDetalleRenta.setPorcentajeRetener(compraDetalle.getSriRetencionRenta().getPorcentaje().setScale(2,BigDecimal.ROUND_HALF_UP));
                retencionDetalleRenta.setRetencion(retencion);
                retencionDetalleRenta.setValorRetenido(compraDetalle.getValorSriRetencionRenta());

                
                retencion.addDetalle(retencionDetalleRenta);
            }
        }
                
    }

    private void listenerTabla() {
        getTblDetalleRetenciones().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada=getTblDetalleRetenciones().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    DefaultTableModel modeloTabla=(DefaultTableModel) getTblDetalleRetenciones().getModel();
                    CompraDetalle compraDetalle=(CompraDetalle) modeloTabla.getValueAt(filaSeleccionada,0); //Obtiene el valor de la fila seleccioanda
                    getCmbRetencionIva().setSelectedItem(compraDetalle.getSriRetencionIva());
                    getCmbRetencionRenta().setSelectedItem(compraDetalle.getSriRetencionRenta());
                    /*
                    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                        CompraDetalle compraDetalle=(CompraDetalle) modeloTabla.getValueAt(0,i);
                        
                        if()
                        
                        getCmbRetencionIva().setSelectedItem(compraDetalle.getSriRetencionIva());
                        getCmbRetencionRenta().setSelectedItem(compraDetalle.getSriRetencionRenta());
                    }*/
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listenerCombos() {
        getCmbTipoDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                TipoDocumentoEnum tipoDocumentoEnum = (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
                //tipoDocumentoEnum=getTi
                switch(tipoDocumentoEnum)
                {
                    case COMPRA:
                        getTabTipoDocumentos().setSelectedIndex(0);
                        habilitarEdicionRetencion(false);                        
                        habilitarTab(0);
                        break;
                    
                    case LIBRE:
                        getTabTipoDocumentos().setSelectedIndex(1);
                        habilitarEdicionRetencion(true);
                        habilitarTab(1);
                        break;
                
                }
                
                
            }
        });
    }
    
    private void habilitarEdicionRetencion(Boolean opcion)
    {
        getTxtBaseImponible().setEnabled(opcion);
        getCmbRetencionIva().setEnabled(opcion);
        getCmbRetencionRenta().setEnabled(opcion);
        getBtnAgregar().setEnabled(opcion);
        getBtnEditar().setEnabled(opcion);
    }
    
    
    private void habilitarTab(int numeroTab)
    {
        getTabTipoDocumentos().setEnabledAt(0, false);
        getTabTipoDocumentos().setEnabledAt(1, false);
        
        getTabTipoDocumentos().setEnabledAt(numeroTab,true);
        
    }

    private boolean validar() {
        
        if(getTblDetalleRetenciones().getRowCount()==0)
        {
            DialogoCodefac.mensaje("Error","No se puede enviar retenciones sin detalles", DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        
        TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        if(tipoDocumentoEnum.equals(TipoDocumentoEnum.LIBRE))
        {
            //System.out.println("<<"+getTxtPreimpreso().getText().replaceAll("-","")+"<<");
            if(getTxtPreimpreso().getText().replaceAll("-","").trim().length()==0)
            {
                DialogoCodefac.mensaje("Error","El preimpreso de la compra no debe ser vacio",DialogoCodefac.MENSAJE_INCORRECTO);
                return false;
            }
        }
        return true;
    }

}
