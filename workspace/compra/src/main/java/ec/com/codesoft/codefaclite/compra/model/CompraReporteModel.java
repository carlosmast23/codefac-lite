/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.compra.panel.CompraReportePanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.compra.panel.CompraReportePanel;
import ec.com.codesoft.codefaclite.compra.reportdata.CompraAgrupadoCategoriaData;
import ec.com.codesoft.codefaclite.compra.reportdata.CompraDataReporte;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class CompraReporteModel extends CompraReportePanel {

    private static final Logger LOG = Logger.getLogger(CompraReporteModel.class.getName());

    private DefaultTableModel modeloTablaDetallesCompras;
    private Persona proveedor;
    private Date fechaInicio;
    private Date fechaFinal;

    private DocumentoEnum documentoEnum;
    private TipoDocumentoEnum tipoDocumentoEnum;

    private boolean banderaBusqueda;

    //Variables que me permiten realizar la sumatoria de los totales de cada compra
    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal subtotal0 = BigDecimal.ZERO;
    private BigDecimal subtotal12 = BigDecimal.ZERO;
    private BigDecimal descuento0 = BigDecimal.ZERO;
    private BigDecimal descuento12 = BigDecimal.ZERO;
    private BigDecimal descuento = BigDecimal.ZERO;
    private BigDecimal iva = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;

    //Servicio que me permite realizar la busqueda según los filtros de la ventana
    private CompraServiceIf compraServiceIf;

    //Lista que permite almacenar los datos de la busqueda
    private List<Compra> compras;

    //Mapa que almacena
    private Map parametros;

    public CompraReporteModel() {

    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        super.validacionDatosIngresados = false;

        agregarListener();
        agregarListenerChecks();
        crearVariables();
        this.compraServiceIf = ServiceFactory.getFactory().getCompraServiceIf();
        iniciarValoresVentana();

    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        /*
        Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Si desea continuar se perderan los cambios realizados?", DialogoCodefac.MENSAJE_ADVERTENCIA);
        if (!respuesta) {
            throw new ExcepcionCodefacLite("Cancelacion usuario");
        } else {
            iniciarValoresVentana();
            crearVariables();
            limpiarTotalesComprasIndiviales();
        }*/
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        try {
            if (compras == null) {
                DialogoCodefac.mensaje("Advertencia", "Se debe realizar una busqueda, para imprimir un reporte", DialogoCodefac.MENSAJE_ADVERTENCIA);
            } else {

                
                
                if (banderaBusqueda) {
                    parametros.put("identificacion", "TODOS");
                    parametros.put("nombre", "TODOS");
                } else {
                    parametros.put("identificacion", this.proveedor.getIdentificacion() + "");
                    parametros.put("nombre", this.proveedor.getRazonSocial() + "");

                }

                //Parametros estaticos que se envian para realizar el Reporte
                parametros.put("tipodocumento", getCmbTipoDocumento().getSelectedItem() + "");
                parametros.put("documento", getCmbDocumento().getSelectedItem() + "");
                parametros.put("fechainicial", this.fechaInicio + "");
                parametros.put("fechafinal", this.fechaFinal + "");
                parametros.put("subtotal", this.subtotal + "");
                parametros.put("subtotal12", this.subtotal12 + "");
                parametros.put("subtotal0", this.subtotal0 + "");
                parametros.put("descuento", this.descuento + "");
                parametros.put("iva", this.iva + "");
                parametros.put("total", this.total + "");
                

                DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
                    @Override
                    public void excel() {
                        try {
                            Excel excel = new Excel();
                            
                            
                            if(getChkReporteAgrupadoPorCategoria().isSelected())
                            {
                                String[] nombreCabeceras = new String[] {"Categoria","Producto","Compra","Fecha","Subtotal12", "Sutotal0", "Descuento","IVA","Total"};
                                excel.gestionarIngresoInformacionExcel(nombreCabeceras,construirDataAgrupado(compras));
                            }
                            else 
                            {
                                String[] nombreCabeceras = {"Preimpreso","Autorización", "Identificación", "Nombre", "Fecha", "Subtotal12", "Sutotal0", "Descuento","IVA","Total"};
                                excel.gestionarIngresoInformacionExcel(nombreCabeceras, compraDataReportes(compras));
                            }                           
                            
                            excel.abrirDocumento();

                        } catch (Exception exc) {
                            exc.printStackTrace();
                            DialogoCodefac.mensaje("Error", "El archivo Excel se encuentra abierto", DialogoCodefac.MENSAJE_INCORRECTO);
                        }
                    }

                    @Override
                    public void pdf() {
                        try {
                            if(getChkReporteAgrupadoPorCategoria().isSelected())
                            {
                                InputStream path = RecursoCodefac.JASPER_COMPRA.getResourceInputStream("reporte_compra_agrupado_categoria.jrxml");
                                ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, construirDataAgrupado(compras), panelPadre, "Reporte Compra Agrupado");
                            }else
                            {
                                InputStream path = RecursoCodefac.JASPER_COMPRA.getResourceInputStream("reporte_compra.jrxml");
                                ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, compraDataReportes(compras), panelPadre, "Reporte Compra");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
            DialogoCodefac.mensaje("Error", "No se pudo crear el reporte correctamente", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }

    }
    
    public List<CompraDataReporte> compraDataReportes(List<Compra> compras)
    {
        List<CompraDataReporte> compraDataReportes = new ArrayList<>();
        //Parametros dinámicos que se envian para realizar el Reporte
        for (Compra compra : compras) {
            CompraDataReporte cdr = new CompraDataReporte();
            cdr.setPreimpreso(compra.getPreimpreso());
            cdr.setAutorizacion(compra.getAutorizacion());
            cdr.setIdentificacion(compra.getProveedor().getIdentificacion());
            cdr.setNombre(compra.getProveedor().getRazonSocial());
            cdr.setFecha(compra.getFechaFactura() + "");
            cdr.setSubtotal(sumarValores(compra.getSubtotalImpuestos(), compra.getSubtotalSinImpuestos()) + "");
            cdr.setSubtotal0(compra.getSubtotalSinImpuestos() + "");
            cdr.setSubtotal12(compra.getSubtotalImpuestos() + "");
            cdr.setDescuento(sumarValores(compra.getDescuentoImpuestos(), compra.getDescuentoSinImpuestos()) + "");
            cdr.setDescuento0(compra.getDescuentoSinImpuestos() + "");
            cdr.setDescuento12(compra.getDescuentoImpuestos() + "");
            cdr.setIva(compra.getIva() + "");
            cdr.setTotal(compra.getTotal() + "");
            compraDataReportes.add(cdr);
        }

        Collections.sort(compraDataReportes, new Comparator<CompraDataReporte>() {
            public int compare(CompraDataReporte obj1, CompraDataReporte obj2) {
                return obj1.getNombre().compareTo(obj2.getNombre());
            }
        });
        return compraDataReportes;
    }

    public List<CompraAgrupadoCategoriaData> construirDataAgrupado(List<Compra> compras) {
        Map<CategoriaProducto, List<CompraDetalle>> mapAgrupadoCategoria = new HashMap<CategoriaProducto, List<CompraDetalle>>();

        //List<CompraAgrupadoCategoriaData> resultado=new ArrayList<CompraAgrupadoCategoriaData>();
        //Agrupar todos los detalles segun el tipo de categoria en un map
        for (Compra compra : compras) {
            for (CompraDetalle compraDetalle : compra.getDetalles()) {
                CategoriaProducto categoria = compraDetalle.getProductoProveedor().getProducto().getCatalogoProducto().getCategoriaProducto();
                List<CompraDetalle> detalles = mapAgrupadoCategoria.get(categoria);
                if (detalles == null) {
                    detalles= new ArrayList<CompraDetalle>();
                    detalles.add(compraDetalle);
                    mapAgrupadoCategoria.put(categoria, detalles);
                } else {
                    detalles.add(compraDetalle);
                }
            }
        }

        //Crear la nueva lista para el reporte con el map clasificado
        List<CompraAgrupadoCategoriaData> resultado = new ArrayList<CompraAgrupadoCategoriaData>();
        for (Map.Entry<CategoriaProducto, List<CompraDetalle>> entry : mapAgrupadoCategoria.entrySet()) {
            CategoriaProducto categoria = entry.getKey();
            List<CompraDetalle> detallesCompra = entry.getValue();

            for (CompraDetalle compraDetalle : detallesCompra) {

                CompraAgrupadoCategoriaData compraAgrupadoData = new CompraAgrupadoCategoriaData();
                compraAgrupadoData.setCategoria(categoria.getNombre());
                compraAgrupadoData.setProducto(compraDetalle.getProductoProveedor().getProducto().getNombre());
                compraAgrupadoData.setCompra(compraDetalle.getCompra().getPreimpreso());
                compraAgrupadoData.setFecha(compraDetalle.getCompra().getFechaFactura().toString());
                compraAgrupadoData.setIva(compraDetalle.getIva());
                compraAgrupadoData.setSubtotalDescuento(compraDetalle.getDescuento());
                compraAgrupadoData.setTotal(compraDetalle.getTotalCalculado());
                
                if(compraDetalle.isImpuestoIvaCero())
                {
                    compraAgrupadoData.setSubtotalCero(compraDetalle.getSubtotal());
                    compraAgrupadoData.setSubtotalDoce(BigDecimal.ZERO);
                }
                else
                {
                    compraAgrupadoData.setSubtotalCero(BigDecimal.ZERO);
                    compraAgrupadoData.setSubtotalDoce(compraDetalle.getSubtotal());
                }
                
                resultado.add(compraAgrupadoData);
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
        getDateFechaInicio().setDate(new java.util.Date());
        getDateFechaFinal().setDate(new java.util.Date());
    }

//    @Override
    public String getNombre() {
        return "Compra Reporte";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, false);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //Permiten inicializar las variables
    private void crearVariables() {
        this.subtotal = new BigDecimal(BigInteger.ZERO);
        this.subtotal0 = new BigDecimal(BigInteger.ZERO);
        this.subtotal12 = new BigDecimal(BigInteger.ZERO);
        this.descuento = new BigDecimal(BigInteger.ZERO);
        this.descuento0 = new BigDecimal(BigInteger.ZERO);
        this.descuento12 = new BigDecimal(BigInteger.ZERO);
        this.iva = new BigDecimal(BigInteger.ZERO);
        this.total = new BigDecimal(BigInteger.ZERO);
        this.parametros = new HashMap();
    }

    //Valores cargados para presentacion de ventana
    public void iniciarValoresVentana() {
        initValoresCombos();
        initModelTablaDetalleCompras();
        getTxtProveedor().setEnabled(false);

        getChkDocumento().doClick();
        getChkDocumento().setSelected(true);
        getChkTipoDocumento().doClick();
        getChkTipoDocumento().setSelected(true);
        getChkTodos().doClick();
        getChkTodos().setSelected(true);
    }

    //Valores iniciales que tendra la tabla detalle compras
    public void initModelTablaDetalleCompras() {
        String titulos[] = {"Preimpreso", "Identificacion", "Nombre", "Fecha", "Subtotal 12%", "Subtotal 0%", "Descuento", "IVA", "TOTAL"};
        modeloTablaDetallesCompras = new DefaultTableModel(titulos, 0);
        getTableDetalleCompras().setModel(modeloTablaDetallesCompras);
    }

    public void initValoresCombos() {
        //Iniciar valores del combo Documento
        getCmbDocumento().removeAllItems();
        List<DocumentoEnum> documentos = DocumentoEnum.obtenerDocumentoPorModulo(ModuloCodefacEnum.COMPRA);
        for (DocumentoEnum documento : documentos) {
            getCmbDocumento().addItem(documento);
        }

        //Iniciar valores del combo TipoDocumento
        getCmbTipoDocumento().removeAllItems();
        List<TipoDocumentoEnum> tipoDocumentoEnums = TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloCodefacEnum.COMPRA);
        for (TipoDocumentoEnum tipoDocumentoEnum : tipoDocumentoEnums) {
            getCmbTipoDocumento().addItem(tipoDocumentoEnum);
        }

        //Inicar valores del combo de Estados
        getCmbEstado().removeAllItems();
        getCmbEstado().addItem(GeneralEnumEstado.ACTIVO);
        getCmbEstado().addItem(GeneralEnumEstado.ELIMINADO);

    }

    public void agregarListener() {

        getBtnLimpiarFechaInicial().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaInicio().setDate(null);
            }
        });

        getBtnLimpiarFechaFinal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaFinal().setDate(null);
            }
        });

        //Permite buscar a un proveedor mediante una ventana
        getBtnBuscarProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorBusquedaDialogo proveedorBusquedaDialogo = new ProveedorBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(proveedorBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                proveedor = ((PersonaEstablecimiento) buscarDialogoModel.getResultado()).getPersona();
                if (proveedor != null) {
                    String identificacion = proveedor.getIdentificacion();
                    String nombre = proveedor.getRazonSocial();
                    getTxtProveedor().setText(identificacion + " - " + nombre);
                }
            }
        });

        getBtcConsultar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    encerarValoresTotales();
                    setearValores();
                    visualizarTotalesComprasIndividuales();
                } catch (RemoteException ex) {
                    Logger.getLogger(CompraReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    private void encerarValoresTotales() {
        subtotal = BigDecimal.ZERO;
        subtotal0 = BigDecimal.ZERO;
        subtotal12 = BigDecimal.ZERO;
        descuento0 = BigDecimal.ZERO;
        descuento12 = BigDecimal.ZERO;
        descuento = BigDecimal.ZERO;
        iva = BigDecimal.ZERO;
        total = BigDecimal.ZERO;
    }

    public void setearValores() throws RemoteException {
        //Obtener valores de combos
        if (!getChkDocumento().isSelected()) {
            this.documentoEnum = (DocumentoEnum) getCmbDocumento().getSelectedItem();
        } else {
            this.documentoEnum = null;
        }

        if (!getChkTipoDocumento().isSelected()) {
            this.tipoDocumentoEnum = (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        } else {
            this.tipoDocumentoEnum = null;
        }

        if (getChkTodos().isSelected()) {
            this.proveedor = null;
        }

        //Obtener las fecha seleccionadas de los combos
        this.fechaInicio = (getDateFechaInicio().getDate() != null) ? new Date(getDateFechaInicio().getDate().getTime()) : null;
        this.fechaFinal = (getDateFechaFinal().getDate() != null) ? new Date(getDateFechaFinal().getDate().getTime()) : null;

        GeneralEnumEstado estadoEnum = (GeneralEnumEstado) getCmbEstado().getSelectedItem();

        //if (banderaBusqueda) {
        //    this.compras = compraServiceIf.obtenerTodos(null,);
        //    mostrarDatosEnTabla(this.compras);
        //    sumarTotalesComprasIndividuales(this.compras);
        //} else {
        //if (proveedor != null) {
        this.compras = compraServiceIf.obtenerCompraReporte(proveedor, fechaInicio, fechaFinal, documentoEnum, tipoDocumentoEnum, estadoEnum);
        mostrarDatosEnTabla(this.compras);
        sumarTotalesComprasIndividuales(this.compras);
        //} else {
        //    DialogoCodefac.mensaje("Advertencia", "Debe seleccionar un proveedor", DialogoCodefac.MENSAJE_ADVERTENCIA);
        //}
        //}

    }

    public void sumarTotalesComprasIndividuales(List<Compra> compras) {
        for (Compra compra : compras) {
            BigDecimal subtotalImpuestos = (compra.getSubtotalImpuestos() != null) ? compra.getSubtotalImpuestos() : BigDecimal.ZERO;
            BigDecimal subtotalSinImpuestos = (compra.getSubtotalSinImpuestos() != null) ? compra.getSubtotalSinImpuestos() : BigDecimal.ZERO;
            BigDecimal descuentoImpuestos = (compra.getDescuentoImpuestos() != null) ? compra.getDescuentoImpuestos() : BigDecimal.ZERO;
            BigDecimal descuentoSinImpuestos = (compra.getDescuentoSinImpuestos() != null) ? compra.getDescuentoSinImpuestos() : BigDecimal.ZERO;
            BigDecimal iva = (compra.getIva() != null) ? compra.getIva() : BigDecimal.ZERO;
            BigDecimal total = (compra.getTotal() != null) ? compra.getTotal() : BigDecimal.ZERO;

            this.subtotal = this.subtotal.add(subtotalImpuestos.add(subtotalSinImpuestos));
            //this.subtotal = sumarValores(compra.getSubtotalImpuestos(), compra.getSubtotalSinImpuestos());
            this.subtotal0 = this.subtotal0.add(subtotalSinImpuestos);
            this.subtotal12 = this.subtotal12.add(subtotalImpuestos);
            this.descuento = this.descuento.add(descuentoImpuestos.add(descuentoSinImpuestos));
//            this.descuento = sumarValores(compra.getDescuentoImpuestos(), compra.getDescuentoSinImpuestos());
            this.descuento0 = this.descuento0.add(descuentoSinImpuestos);
            this.descuento12 = this.descuento12.add(descuentoImpuestos);
            this.iva = this.iva.add(iva);
            this.total = this.total.add(total);
        }
    }

    public BigDecimal sumarValores(BigDecimal d1, BigDecimal d2) {
        if (d1 == null) {
            d1 = BigDecimal.ZERO;
        }

        if (d2 == null) {
            d2 = BigDecimal.ZERO;
        }
        return d1.add(d2);
    }

    public void mostrarDatosEnTabla(List<Compra> compras) {
        String titulos[] = {"Preimpreso", "Identificacion", "Nombre", "Fecha", "Subtotal 12%", "Subtotal 0%", "Descuento", "IVA", "TOTAL"};
        this.modeloTablaDetallesCompras = new DefaultTableModel(titulos, 0);

        for (Compra compra : compras) {
            Vector<String> fila = new Vector();
            fila.add(compra.getPreimpreso());
            fila.add(compra.getProveedor().getIdentificacion());
            fila.add(compra.getProveedor().getRazonSocial());
            fila.add(compra.getFechaFactura() + "");
            fila.add(compra.getSubtotalImpuestos() + "");
            fila.add(compra.getSubtotalSinImpuestos() + "");
            BigDecimal descuento = new BigDecimal(BigInteger.ZERO);
            BigDecimal descuentoImpuestos = (compra.getDescuentoImpuestos() != null) ? compra.getDescuentoImpuestos() : BigDecimal.ZERO;
            BigDecimal descuentoSinImpuestos = (compra.getDescuentoSinImpuestos() != null) ? compra.getDescuentoSinImpuestos() : BigDecimal.ZERO;
            descuento = descuento.add(descuentoImpuestos).add(descuentoSinImpuestos);
            fila.add(descuento + "");
            fila.add(compra.getIva() + "");
            fila.add(compra.getTotal() + "");
            this.modeloTablaDetallesCompras.addRow(fila);;
        }
        getTableDetalleCompras().setModel(modeloTablaDetallesCompras);
        alinearColumnasTablas();

    }

    public void alinearColumnasTablas() {
        UtilidadesTablas.alinearTodasColumnasTabla(getTableDetalleCompras(), UtilidadesTablas.alinearIzquierda);
        UtilidadesTablas.alinearColumnasTabla(getTableDetalleCompras(), UtilidadesTablas.alinearDerecha, new Integer[]{4, 5, 6, 7, 8});
    }

    public void visualizarTotalesComprasIndividuales() {
        getLblSubtotal().setText(this.subtotal + "");
        getLblSubtotal12().setText(this.subtotal12 + "");
        getLblSubtotal0().setText(this.subtotal0 + "");
        getLblDescuento().setText(this.descuento + "");
        getLblIva12().setText(this.iva + "");
        getLblTotal().setText(this.total + "");

    }

    public void limpiarTotalesComprasIndiviales() {
        getLblTotal().setText("0.00");
        getLblSubtotal().setText("0.00");
        getLblSubtotal12().setText("0.00");
        getLblSubtotal0().setText("0.00");
        getLblDescuento().setText("0.00");
        getLblIva12().setText("0.00");
        getLblTotal().setText("0.00");
        getTxtProveedor().setText("");
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void agregarListenerChecks() {
        //Permite establecer la busqueda por un proveedor o todos los proveedores
        getChkTodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getChkTodos().isSelected()) {
                    getBtnBuscarProveedor().setEnabled(false);
                    getTxtProveedor().setText("");
                    banderaBusqueda = true;
                } else {
                    getBtnBuscarProveedor().setEnabled(true);
                    banderaBusqueda = false;
                }
            }
        });

        getChkDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCmbDocumento().setEnabled(!getChkDocumento().isSelected());
            }
        });

        getChkTipoDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCmbTipoDocumento().setEnabled(!getChkTipoDocumento().isSelected());
            }
        });
    }
}
