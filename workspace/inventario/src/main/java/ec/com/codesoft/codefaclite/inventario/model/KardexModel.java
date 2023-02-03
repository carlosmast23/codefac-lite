/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.LoteBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogoFactory;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
//import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
//import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.inventario.busqueda.CompraBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.data.KardexData;
import ec.com.codesoft.codefaclite.inventario.panel.KardexPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadBigDecimal;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import es.mityc.firmaJava.ocsp.config.ServidorOcsp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.collections4.map.HashedMap;

/**
 *
 * @author Carlos
 */
public class KardexModel extends KardexPanel {

    /**
     * Referencia del producto para consultar el kardex
     */
    private Producto productoSeleccionado;

    /**
     * Variable donde se alamcena todos los kardex consultados
     */
    private Kardex kardex;
    
    /**
     * Variable donde se almacena la consulta de los detalles del kardex para imprimir
     */
    private List<KardexDetalle> detalleKardex;

    /**
     * Lista de resultado de los datos para imprimir
     */
    private List<KardexData> listaKardex;

    
    private TotalesAcumulado totalesAcumulado;
    
    private Bodega bodegaSeleccionada;
    
    private Lote lote;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarListernerBotones();
        listenerCheckBox();
        valoresIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void setearValores()
    {
        BigDecimal costoPromedio=new BigDecimal(getTxtCostoPromedio().getText());
        kardex.setCostoPromedio(costoPromedio);
        
        BigDecimal ultimoCosto=new BigDecimal(getTxtUltimoCosto().getText());
        kardex.setPrecioUltimo(ultimoCosto);
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        
        try 
        {
            setearValores();
            ServiceFactory.getFactory().getKardexServiceIf().actualizarKardex(kardex);
            DialogoCodefac.mensaje(new CodefacMsj("Datos Actualizados Correctamente", CodefacMsj.TipoMensajeEnum.CORRECTO));
        } catch (RemoteException ex) {
            Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite(ex.getMessage());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
        
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if(kardex!=null)
        {
            if(DialogoCodefac.dialogoPregunta("Esta seguro que desea anular el Kardex ?",DialogoCodefac.MENSAJE_ADVERTENCIA))
            {
                try 
                {
                    ServiceFactory.getFactory().getKardexServiceIf().anularInventario(kardex);
                    DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
                } 
                catch (RemoteException ex) 
                {
                    Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ExcepcionCodefacLite("Cancelar eliminar");
                } 
                catch (ServicioCodefacException ex) 
                {
                    Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje(ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                    throw new ExcepcionCodefacLite("Cancelar eliminar");
                }
            }
        }
        else
        {
            DialogoCodefac.mensaje("Advertencia","Seleccione un kardex para eliminar",DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("cancelado eliminar");
        }
    }

    @Override
    public void imprimir() {

        InputStream path = RecursoCodefac.JASPER_INVENTARIO.getResourceInputStream("kardex.jrxml");

        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
            @Override
            public void excel() {
                try {
                    Excel excel = new Excel();
                    String[] titulo = {"Fecha Kardex","Documento", "Preimpreso","Fecha Doc", "Proveedor", "Cant", "P.Unit", "P.Total", "Cant", "P.Unit", "P.Total", "Cant", "P.Unit", "P.Total"};
                    excel.gestionarIngresoInformacionExcel(titulo, listaKardex);
                    excel.abrirDocumento();
                } catch (IOException ex) {
                    Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void pdf() {
                Map<String, Object> parameters = new HashMap<String, Object>();
                List<KardexData> listaConDecimales=redondearListaKardex(listaKardex);
                ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, listaConDecimales, panelPadre, "Kardex " + productoSeleccionado.getNombre(), OrientacionReporteEnum.HORIZONTAL);
            }
        });
    }
    
    //TODO:Solucion temporal para tener todos los calculos con todos los decimales y mostrar solo con 2    
    public List<KardexData> redondearListaKardex(List<KardexData> kardexDataOriginal)
    {
        List<KardexData> resultado=new ArrayList<KardexData>();
        for (KardexData kardexData : kardexDataOriginal) {
            
            try {
                KardexData kardexDataTemp=(KardexData) kardexData.clone();
                
                kardexDataTemp.setIngreso_precio(kardexDataTemp.getIngreso_precioFormat());
                kardexDataTemp.setIngreso_total(kardexDataTemp.getIngreso_totalFormat());

                kardexDataTemp.setEgreso_precio(kardexDataTemp.getEgreso_precioFormat());
                kardexDataTemp.setEgreso_total(kardexDataTemp.getEgreso_totalFormat());

                kardexDataTemp.setSaldo_precio(kardexDataTemp.getSaldo_precioFormat());
                kardexDataTemp.setSaldo_total(kardexDataTemp.getSaldo_totalFormat());
                
                resultado.add(kardexDataTemp);

            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return resultado;
        
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        listaKardex = new ArrayList<KardexData>();
    }

//    @Override
    public String getNombre() {
        return "Kardex";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, false);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void cargarDatosPantalla()
    {
        if (lote != null) {
            getTxtLoteNombre().setText(lote.getCodigo());
        } else {
            getTxtLoteNombre().setText("");
        }
    }
    
    private ActionListener listenerBuscarLote=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoteBusqueda busqueda=new LoteBusqueda(session.getEmpresa(),productoSeleccionado);
            BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(busqueda);            
            buscarDialogo.setVisible(true);

            if (buscarDialogo.getResultado() != null) 
            {
                lote= (Lote) buscarDialogo.getResultado();
            }
            
            cargarDatosPantalla();
        }
    };

    private void agregarListernerBotones() {
        
        getBtnBuscarLote().addActionListener(listenerBuscarLote);        
        
        
        getBtnProductoBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo(EnumSiNo.SI,session.getEmpresa(),true,true);
                    //BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                    //buscarDialogo.setVisible(true);
                    //productoSeleccionado = (Producto) buscarDialogo.getResultado();
                    ProductoBusquedaDialogoFactory dialogoFactory=new ProductoBusquedaDialogoFactory(session.getSucursal(),true, ProductoBusquedaDialogoFactory.ResultadoEnum.PRODUCTO);
                    productoSeleccionado = (Producto) dialogoFactory.ejecutarDialogo();
                    if (productoSeleccionado != null) {
                        getTxtProducto().setText(productoSeleccionado.getNombre());
                    }
                } catch (ServicioCodefacException ex) {                    
                    Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
                    new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR);
                }
            }
        });

        getBtnConsultar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //Date fechaInicio=new Date(getCmbFechaInicial().getDate().getTime());
                    //Date fechaFin=new Date(getCmbFechaFinal().getDate().getTime());
                    Bodega bodega = (Bodega) getCmbBodega().getSelectedItem();
                    

                    KardexServiceIf kardexService = ServiceFactory.getFactory().getKardexServiceIf();

                    /*Map<String, Object> parametrosMap = new HashMap<String, Object>();
                    parametrosMap.put("bodega", bodega);
                    parametrosMap.put("producto", productoSeleccionado);*/

                    //List<Kardex> resultados = kardexService.obtenerPorMap(parametrosMap);
                    Date fechaInicial=null;
                    Date fechaFinal=null;
                            
                    if(getCmbFechaInicial().getDate()!=null)
                    {
                        fechaInicial=new Date(getCmbFechaInicial().getDate().getTime());
                    }
                    
                    if(getCmbFechaFinal().getDate()!=null)
                    {
                        fechaFinal=new Date(getCmbFechaFinal().getDate().getTime());
                    }
                    
                    Integer cantidadMovimientos=null;
                    if(!getChkTodosMovimientos().isSelected())
                    {
                        cantidadMovimientos=(Integer) getTxtMovimientos().getValue();
                    }                    
                    
                    detalleKardex=kardexService.obtenerConsultaPorFecha(fechaInicial,fechaFinal, productoSeleccionado,bodega,lote,cantidadMovimientos);
                    if (detalleKardex != null && detalleKardex.size() > 0) {
                        kardex = detalleKardex.get(detalleKardex.size()-1).getKardex();
                        cargarTablaKardex();
                        UtilidadesTablas.ubicarFinalTabla(getTblKardexDetalle());
                        
                        getTxtCostoPromedio().setText(kardex.getCostoPromedio()+"");
                        getTxtUltimoCosto().setText(kardex.getPrecioUltimo()+"");
                    }
                    else
                    {
                        //Cuando no encuentra nada seteo un kardex vacio
                        //kardexService.getKardexModificados(productoSeleccionado, cantidadMovimientos, bodega, ProductoEnsamble.EnsambleAccionEnum.AGREGAR)
                        kardex=ServiceFactory.getFactory().getKardexServiceIf().buscarKardexPorDefectoVenta(bodega, productoSeleccionado);
                        if(kardex==null)
                        {
                            kardex=kardexService.construirKardexVacioSinPersistencia();
                        }
                        cargarTablaKardex();
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje(MensajeCodefacSistema.ErrorComunicacion.ERROR_COMUNICACION_SERVIDOR);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
    }

    private void cargarTablaKardex() {
        BigDecimal cantidadAcumulada = BigDecimal.ZERO;
        BigDecimal precioUnitarioPromedio = BigDecimal.ZERO;
        BigDecimal precioTotalAcumulado = BigDecimal.ZERO;

        listaKardex = new ArrayList<KardexData>();
        //List<KardexDetalle> detalles = kardex.getDetallesKardex();
        for (int i = 0; i < detalleKardex.size(); i++) {
            KardexData kardexData = new KardexData();
            //Vector<String> fila=new Vector<String>();
            KardexDetalle kardexDetalle = detalleKardex.get(i);

            //fila.add(i+""); //numeracion
            kardexData.setDocumento(kardexDetalle.getCodigoTipoDocumentoEnum().getNombre());
            //fila.add(kardexDetalle.getCodigoTipoDocumentoEnum().getNombre()); //tipo del documento
            TipoDocumentoEnum tipoDocumentoEnum = kardexDetalle.getCodigoTipoDocumentoEnum();

            //llenar los datos adionales
            kardexData.setPreimpreso(kardexDetalle.getPreimpreso());

            kardexData.setProveedor(kardexDetalle.getRazonSocial());
            
            kardexData.setFechaDocumento((kardexDetalle.getFechaDocumento()!=null)?kardexDetalle.getFechaDocumento().toString():"");
            
            kardexData.setDescripcion(kardexDetalle.getDescripcion());

            //Restar o sumar la cantidad segun omo afecte el detalle en los kardex
            if (!kardexDetalle.getCodigoTipoDocumentoEnum().getSignoInventario().equals(TipoDocumentoEnum.NO_AFECTA_INVETARIO)) {
                //Cuando el inventario afecta de forma positiva
                if (kardexDetalle.getCodigoTipoDocumentoEnum().getSignoInventario().equals(TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO)) {

                    cantidadAcumulada = cantidadAcumulada.add(kardexDetalle.getCantidad());
                    precioTotalAcumulado = precioTotalAcumulado.add(kardexDetalle.getPrecioTotal());

                    if (i == 0) //Si es el primer registro el precio es el mismo
                    {
                        precioUnitarioPromedio = kardexDetalle.getPrecioUnitario();
                    } else //Cuando es el segundo registro empiezo a calcular el promedio
                    {
                        //if(cantidadAcumulada>0)
                        if(cantidadAcumulada.compareTo(BigDecimal.ZERO)>0)
                        {
                            precioUnitarioPromedio = precioTotalAcumulado.divide(cantidadAcumulada,2,BigDecimal.ROUND_HALF_UP);
                        }
                    }

                    completarFila(kardexData, tipoDocumentoEnum.getModuloEnum(), kardexDetalle, cantidadAcumulada, precioUnitarioPromedio, precioTotalAcumulado, true);
                } else //Cuando afecta de forma negativa
                {
                    
                    cantidadAcumulada=cantidadAcumulada.subtract(kardexDetalle.getCantidad());
                    precioTotalAcumulado = precioTotalAcumulado.subtract(kardexDetalle.getPrecioTotal());
                    
                    if (i == 0) //Si es el primer registro el precio es el mismo
                    {
                        precioUnitarioPromedio = kardexDetalle.getPrecioUnitario();
                    } else //Cuando es el segundo registro empiezo a calcular el promedio
                    {
                        if(cantidadAcumulada.compareTo(BigDecimal.ZERO)>0)
                        //if(cantidadAcumulada>0)
                        {
                            precioUnitarioPromedio = precioTotalAcumulado.divide(cantidadAcumulada,2,BigDecimal.ROUND_HALF_UP);
                        }
                    }
                    
                    completarFila(kardexData, tipoDocumentoEnum.getModuloEnum(), kardexDetalle, cantidadAcumulada, precioUnitarioPromedio, precioTotalAcumulado, false);
                }
            }

            listaKardex.add(kardexData);//Agregar los items a la lista de consulta

        }

        construirModeloTabla();

    }
    
    /*private KardexData calcularSaldoFila(KardexDetalle kardexDetalle,TotalesAcumulado totalesAcumulado)
    {
        Integer cantidadAcumulada = totalesAcumulado.cantidadAcumulada;
        BigDecimal precioUnitarioPromedio = totalesAcumulado.precioUnitarioPromedio;
        BigDecimal precioTotalAcumulado = totalesAcumulado.precioTotalAcumulado;
        
        KardexData kardexData = new KardexData();
        
         //fila.add(i+""); //numeracion
            kardexData.setDocumento(kardexDetalle.getCodigoTipoDocumentoEnum().getNombre());
            //fila.add(kardexDetalle.getCodigoTipoDocumentoEnum().getNombre()); //tipo del documento
            TipoDocumentoEnum tipoDocumentoEnum = kardexDetalle.getCodigoTipoDocumentoEnum();

            //llenar los datos adionales
            kardexData.setPreimpreso(kardexDetalle.getPreimpreso());

            kardexData.setProveedor(kardexDetalle.getRazonSocial());

            //Restar o sumar la cantidad segun omo afecte el detalle en los kardex
            if (!kardexDetalle.getCodigoTipoDocumentoEnum().getSignoInventario().equals(TipoDocumentoEnum.NO_AFECTA_INVETARIO)) {
                //Cuando el inventario afecta de forma positiva
                Integer signoAfectaInventario=kardexDetalle.getCodigoTipoDocumentoEnum().getSignoInventarioNumero();
                
                
                if (kardexDetalle.getCodigoTipoDocumentoEnum().getSignoInventario().equals(TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO)) {

                    cantidadAcumulada += kardexDetalle.getCantidad();
                    precioTotalAcumulado = precioTotalAcumulado.add(kardexDetalle.getPrecioTotal());

                    if (i == 0) //Si es el primer registro el precio es el mismo
                    {
                        precioUnitarioPromedio = kardexDetalle.getPrecioUnitario();
                    } else //Cuando es el segundo registro empiezo a calcular el promedio
                    {
                        if(cantidadAcumulada>0)
                        {
                            precioUnitarioPromedio = precioTotalAcumulado.divide(new BigDecimal(cantidadAcumulada),2,BigDecimal.ROUND_HALF_UP);
                        }
                    }

                    completarFila(kardexData, tipoDocumentoEnum.getModuloEnum(), kardexDetalle, cantidadAcumulada, precioUnitarioPromedio, precioTotalAcumulado, true);
                } else //Cuando afecta de forma negativa
                {

                    cantidadAcumulada -= kardexDetalle.getCantidad();
                    precioTotalAcumulado = precioTotalAcumulado.subtract(kardexDetalle.getPrecioTotal());
                    
                    if (i == 0) //Si es el primer registro el precio es el mismo
                    {
                        precioUnitarioPromedio = kardexDetalle.getPrecioUnitario();
                    } else //Cuando es el segundo registro empiezo a calcular el promedio
                    {
                        if(cantidadAcumulada>0)
                        {
                            precioUnitarioPromedio = precioTotalAcumulado.divide(new BigDecimal(cantidadAcumulada),2,BigDecimal.ROUND_HALF_UP);
                        }
                    }
                    
                    completarFila(kardexData, tipoDocumentoEnum.getModuloEnum(), kardexDetalle, cantidadAcumulada, precioUnitarioPromedio, precioTotalAcumulado, false);
                }
            }

        
        return kardexData;
    }*/

    private void construirModeloTabla() {
        String[] titulo = {"#", "Fecha", "Documento", "Preimpreso", "Proveedor", "Cant", "P.Unit", "P.Total", "Cant", "P.Unit", "P.Total", "Cant", "P.Unit", "P.Total"};
        String[] primeraFila = {" ", "", "KARDEX", "", "", "", "INGRESO", "", "", "EGRESO", "", "", "SALDO", ""};

        DefaultTableModel modeloTabla = new DefaultTableModel(primeraFila, 0);
        modeloTabla.addRow(titulo);

        int indice = 1;
        for (KardexData kardexData : listaKardex) {

            Vector fila = new Vector();
            fila.add(indice++);
            fila.add(kardexData.getFecha());

            fila.add(kardexData.getDocumento());
            fila.add(kardexData.getPreimpreso());
            fila.add(kardexData.getProveedor());

            fila.add(kardexData.getIngreso_cantidad());
            fila.add(kardexData.getIngreso_precioFormat());
            fila.add(kardexData.getIngreso_totalFormat());

            fila.add(kardexData.getEgreso_cantidad());
            fila.add(kardexData.getEgreso_precioFormat());
            fila.add(kardexData.getEgreso_totalFormat());

            fila.add(kardexData.getSaldo_cantidad());
            fila.add(kardexData.getSaldo_precioFormat());
            fila.add(kardexData.getSaldo_totalFormat());

            modeloTabla.addRow(fila);
        }

        //modeloTabla.addRow(fila);
        getTblKardexDetalle().setModel(modeloTabla);

        getLblCantidad().setText(kardex.getStock() + "");
        getLblPrecioPromedio().setText(kardex.calcularPrecioPromedio() + "");
        getLblPrecioUltimo().setText(kardex.getPrecioUltimo() + "");
        getLblTotal().setText(kardex.getPrecioTotal() + "");

        //Configuraciones del tamanio de las columnas para la tabla
        Map<Integer, Integer> tamaniosMap = new HashedMap<>();
        tamaniosMap.put(0, 15);
        tamaniosMap.put(4, 15);
        tamaniosMap.put(7, 15);
        tamaniosMap.put(10, 15);

        UtilidadesTablas.definirTamanioColumnasPorMap(getTblKardexDetalle(), tamaniosMap);

    }
    
    private Integer obtenerCantidadDecimales()
    {
        //Por defecto si no tiene un valor redondea al numero de decimales
        Integer decimalesCantidadRedondear = null;
        try {
            decimalesCantidadRedondear = ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(), ParametroCodefac.NUMERO_DECIMAL_PRODUCTO, new ParametroUtilidades.ComparadorInterface() {
                @Override
                public Object consultarParametro(String nombreParametro) {
                    return Integer.parseInt(nombreParametro);
                }
            });
        } catch (RemoteException ex) {
            Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(decimalesCantidadRedondear==null)
        {
            decimalesCantidadRedondear=2;
        }
        
        return decimalesCantidadRedondear;
    }

    private void completarFila(KardexData kardexData, ModuloCodefacEnum moduloEnum, KardexDetalle kardexDetalle, BigDecimal cantidadAcumulada, BigDecimal precioUnitarioAcumulado, BigDecimal precioTotalAcumulado, boolean agregar) {
        
        Integer decimalesCantidadRedondear = obtenerCantidadDecimales();
        
        //Agregar la fecha UtilidadesFecha
        if (kardexDetalle.getFechaIngreso() != null) {
            kardexData.setFecha(UtilidadesFecha.formatoDiaMesAño(UtilidadesFecha.getFechaDeTimeStamp(kardexDetalle.getFechaIngreso())));
        }

        if (agregar) {      
            BigDecimal cantidadIngreso=kardexDetalle.getCantidad().setScale(decimalesCantidadRedondear,ParametrosSistemaCodefac.REDONDEO_POR_DEFECTO);
                        
            kardexData.setIngreso_cantidad(cantidadIngreso+"");
            kardexData.setIngreso_precio(kardexDetalle.getPrecioUnitario() + "");
            kardexData.setIngreso_total(kardexDetalle.getPrecioTotal() + "");

            kardexData.setEgreso_cantidad("");
            kardexData.setEgreso_precio("");
            kardexData.setEgreso_total("");

        } else {
            kardexData.setIngreso_cantidad("");
            kardexData.setIngreso_precio("");
            kardexData.setIngreso_total("");

            kardexData.setEgreso_cantidad(kardexDetalle.getCantidad().setScale(decimalesCantidadRedondear,ParametrosSistemaCodefac.REDONDEO_POR_DEFECTO) + "");
            kardexData.setEgreso_precio(kardexDetalle.getPrecioUnitario() + "");
            kardexData.setEgreso_total(kardexDetalle.getPrecioTotal() + "");
          

        }

        //Agregar los saldos
        kardexData.setSaldo_cantidad(cantidadAcumulada.setScale(decimalesCantidadRedondear,ParametrosSistemaCodefac.REDONDEO_POR_DEFECTO) + "");
        kardexData.setSaldo_precio(precioUnitarioAcumulado + "");
        kardexData.setSaldo_total(precioTotalAcumulado + "");
        /*fila.add(cantidadAcumulada + "");
        fila.add(precioUnitarioAcumulado+ "");
        fila.add(precioTotalAcumulado+"");*/

    }

    private void valoresIniciales() {

        try {
            
            bodegaSeleccionada=ServiceFactory.getFactory().getBodegaServiceIf().obtenerUnicaBodegaPorSucursal(session.getSucursal());
            
            UtilidadesComboBox.llenarComboBox(getCmbBodega(),ServiceFactory.getFactory().getBodegaServiceIf().obtenerActivosPorSucursal(session.getSucursal()));
            getCmbBodega().setSelectedItem(bodegaSeleccionada);
            //getCmbBodega().removeAllItems();
            //BodegaServiceIf servicioBodega = ServiceFactory.getFactory().getBodegaServiceIf();
            //List<Bodega> bodegas = servicioBodega.obtenerActivosPorEmpresa(session.getEmpresa());
            //for (Bodega bodega : bodegas) {
            //    getCmbBodega().addItem(bodega);
            //}
        } catch (RemoteException ex) {
            Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listenerCheckBox() {
        getChkTodosMovimientos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getChkTodosMovimientos().isSelected())
                {
                    getTxtMovimientos().setEnabled(false);
                }
                else
                {
                    getTxtMovimientos().setEnabled(true);
                }
            }
        });
    }
    
    public class TotalesAcumulado
    {
        public Integer cantidadAcumulada = 0;
        public BigDecimal precioUnitarioPromedio = BigDecimal.ZERO;
        public BigDecimal precioTotalAcumulado = BigDecimal.ZERO;
    }

}
