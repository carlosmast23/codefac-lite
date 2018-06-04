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
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoFacturacionEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
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
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        listener();        
        cargarDatosIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
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
        for (RetencionAdicional datoAdicional : retencion.getDatosAdicionales()) 
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
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        
        cargarDatosCompra(retencion.getCompra());
        cargarTablaDatosAdicionales(retencion);
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
        listenerBotones();
        listenerTabla();
    }

    private void listenerBotones() {
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
                    cargarDatosCompra(compraTmp);
                    //Cargar dato por defecto del correo
                    cargarCorreoPorDefecto(compraTmp);
                    cargarTablaDatosAdicionales(retencion);
                    
                    
                }
            }
            
        });
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
        
        for (RetencionAdicional retencionAdicional :  retencion.getDatosAdicionales())
        {
            String[] fila={retencionAdicional.getCampo(),retencionAdicional.getValor()};
            modelTable.addRow(fila);
        }
        
        getTblDatosAdicionales().setModel(modelTable);
    }
    
    /**
     * Cargar los datos de la compra en la vista
     * @param compra 
     */
    private void cargarDatosCompra(Compra compra)
    {
        getTxtReferenciaFactura().setText(compra.getPreimpreso());
        getLblNombreCliente().setText(compra.getProveedor().getNombresCompletos());
        getLblTelefonoCliente().setText(compra.getProveedor().getTelefonoCelular());
        getLblDireccionCliente().setText(compra.getProveedor().getDireccion());
        
        ///Cargar los detalles de la compra
        cargarTablayTotalesRetenciones(compra);
        
       
    }
    
    private void cargarTablayTotalesRetenciones(Compra compra)
    {
        List<CompraDetalle> compraDetalles = compra.getDetalles();
        DefaultTableModel datos = UtilidadesTablas.crearModeloTabla(new String[]{"Obj", "Nombre", "Retencia Iva", "Retención Renta"},
                new Class[]{CompraDetalle.class, String.class, String.class, String.class});

        BigDecimal totalRetencionIva = new BigDecimal(BigInteger.ZERO);
        BigDecimal totalRetencionRenta = new BigDecimal(BigInteger.ZERO);
        BigDecimal totalRetenciones = new BigDecimal(BigInteger.ZERO);

        for (CompraDetalle compraDetalle : compraDetalles) {
            Vector<Object> fila = new Vector<>();
            fila.add(compraDetalle);
            fila.add(compraDetalle.getDescripcion());
            fila.add(compraDetalle.getValorSriRetencionIVA());
            fila.add(compraDetalle.getValorSriRetencionRenta());
            totalRetencionIva = totalRetencionIva.add(compraDetalle.getValorSriRetencionIVA());
            totalRetencionRenta = totalRetencionRenta.add(compraDetalle.getValorSriRetencionRenta());
            datos.addRow(fila);
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
        TipoFacturacionEnumEstado enumTipoFacturacion=TipoFacturacionEnumEstado.getEnumByEstado(paramTipoFacturacion.getValor());
        
        String secuencial="";
        if(enumTipoFacturacion.equals(TipoFacturacionEnumEstado.NORMAL))
        {
            secuencial=session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_RETENCION_FISICA).getValor();
        }
        else
        {
            if(enumTipoFacturacion.equals(TipoFacturacionEnumEstado.ELECTRONICA))
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
        
        
        //Cuando la facturacion es electronica
        if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).getValor().equals(TipoFacturacionEnumEstado.ELECTRONICA.getLetra()))
        {
            retencion.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_RETENCION).valor));
        }
        else //cuando la facturacion es normal
        {
            retencion.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_RETENCION_FISICA).valor));
        }
        
        //Llenar los detalles de la retencion
        for (CompraDetalle compraDetalle : retencion.getCompra().getDetalles()) {
            
            //Todo: Falta hacer validaciones cuando hay detalles que no requerien enviar retencion con iva 0
            
            //Detalle para la retencion del iva
            RetencionDetalle retencionDetalleIva=new RetencionDetalle();
            retencionDetalleIva.setBaseImponible(compraDetalle.getBaseImponibleIva());
            retencionDetalleIva.setCodigoSri(compraDetalle.getSriRetencionIva().getRetencion().getCodigo());
            retencionDetalleIva.setCodigoRetencionSri(compraDetalle.getSriRetencionIva().getCodigo().toString());
            retencionDetalleIva.setPorcentajeRetener(compraDetalle.getSriRetencionIva().getPorcentaje().setScale(2,BigDecimal.ROUND_HALF_UP));
            retencionDetalleIva.setRetencion(retencion);
            retencionDetalleIva.setValorRetenido(compraDetalle.getValorSriRetencionIVA());
            System.out.println(compraDetalle.getValorSriRetencionIVA());
            
            //Detalle para la retencion de la renta
            RetencionDetalle retencionDetalleRenta=new RetencionDetalle();
            retencionDetalleRenta.setBaseImponible(compraDetalle.getBaseImponibleRenta());
            retencionDetalleRenta.setCodigoSri(compraDetalle.getSriRetencionRenta().getRetencion().getCodigo());
            retencionDetalleRenta.setCodigoRetencionSri(compraDetalle.getSriRetencionRenta().getCodigo().toString());
            retencionDetalleRenta.setPorcentajeRetener(compraDetalle.getSriRetencionRenta().getPorcentaje().setScale(2,BigDecimal.ROUND_HALF_UP));
            retencionDetalleRenta.setRetencion(retencion);
            retencionDetalleRenta.setValorRetenido(compraDetalle.getValorSriRetencionRenta());
            
            retencion.addDetalle(retencionDetalleIva);
            retencion.addDetalle(retencionDetalleRenta);
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
                    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                        CompraDetalle compraDetalle=(CompraDetalle) modeloTabla.getValueAt(0,i);
                        getCmbRetencionIva().setSelectedItem(compraDetalle.getSriRetencionIva());
                        getCmbRetencionRenta().setSelectedItem(compraDetalle.getSriRetencionRenta());
                    }
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

}
