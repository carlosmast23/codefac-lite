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
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.compra.panel.CompraReportePanel;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.CompraAgrupadoCategoriaData;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.CompraDataReporte;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteCompra;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
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
    //private Date fechaInicio;
    //private Date fechaFinal;

    //private DocumentoEnum documentoEnum;
    //private TipoDocumentoEnum tipoDocumentoEnum;

    private boolean banderaBusqueda;


    //Servicio que me permite realizar la busqueda según los filtros de la ventana
    //private CompraServiceIf compraServiceIf;

    //Lista que permite almacenar los datos de la busqueda
    //private List<Compra> compras;

    //Mapa que almacena
    //private Map parametros;
    private ControladorReporteCompra controladorReporteCompra;

    public CompraReporteModel() {

    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        super.validacionDatosIngresados = false;

        agregarListener();
        agregarListenerChecks();
        agregaValoresDefecto();
        //crearVariables();
        //this.compraServiceIf = ServiceFactory.getFactory().getCompraServiceIf();
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
            if (controladorReporteCompra == null) {
                DialogoCodefac.mensaje("Advertencia", "Se debe realizar una busqueda, para imprimir un reporte", DialogoCodefac.MENSAJE_ADVERTENCIA);
            } else {

                
                

                DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
                    @Override
                    public void excel() {
                        try {
                            
                            switch((TipoReporteEnum)getCmbTipoReporte().getSelectedItem())
                            {
                                case NORMAL:
                                    controladorReporteCompra.reporteCompraExcel();
                                    break;
                                    
                                case AGRUPADO_POR_CATEGORIA:
                                    controladorReporteCompra.reporteCompraAgrupadaCategoriaExcel();
                                    break;
                                    
                                case AGRUPADO_POR_SUSTENTO_SRI:
                                    controladorReporteCompra.reporteCompraAgrupadaCategoriaExcel();
                                    break;
                                    
                            }
                                                 
                            
                            //excel.abrirDocumento();

                        } catch (Exception exc) {
                            exc.printStackTrace();
                            DialogoCodefac.mensaje("Error", "El archivo Excel se encuentra abierto", DialogoCodefac.MENSAJE_INCORRECTO);
                        }
                    }

                    @Override
                    public void pdf() {
                        try {
                            
                            switch((TipoReporteEnum)getCmbTipoReporte().getSelectedItem())
                            {
                                case NORMAL:
                                    controladorReporteCompra.reporteCompraPdf(panelPadre);
                                    break;
                                    
                                case AGRUPADO_POR_CATEGORIA:
                                    controladorReporteCompra.reporteCompraAgrupadaCategoriaPdf(panelPadre);
                                    break;
                                    
                                case AGRUPADO_POR_SUSTENTO_SRI:
                                    controladorReporteCompra.reporteCompraAgrupadaSustentoSriPdf(panelPadre);
                                    break;
                                    
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
                ProveedorBusquedaDialogo proveedorBusquedaDialogo = new ProveedorBusquedaDialogo(session.getEmpresa());
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
                    //encerarValoresTotales();
                    setearValores();
                    visualizarTotalesComprasIndividuales();
                } catch (RemoteException ex) {
                    Logger.getLogger(CompraReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }


    public void setearValores() throws RemoteException {
        //Obtener valores de combos
        DocumentoEnum documentoEnum=null;
        if (!getChkDocumento().isSelected()) {
            documentoEnum = (DocumentoEnum) getCmbDocumento().getSelectedItem();
        } else {
            documentoEnum = null;
        }

        TipoDocumentoEnum tipoDocumentoEnum=null;
        if (!getChkTipoDocumento().isSelected()) {
            tipoDocumentoEnum = (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        } else {
            tipoDocumentoEnum = null;
        }

        //Persona proveedor=null;
        if (getChkTodos().isSelected()) {
            this.proveedor = null;
        }

        //Obtener las fecha seleccionadas de los combos
        Date fechaInicio = (getDateFechaInicio().getDate() != null) ? new Date(getDateFechaInicio().getDate().getTime()) : null;
        Date fechaFinal = (getDateFechaFinal().getDate() != null) ? new Date(getDateFechaFinal().getDate().getTime()) : null;

        GeneralEnumEstado estadoEnum = (GeneralEnumEstado) getCmbEstado().getSelectedItem();


        controladorReporteCompra = new ControladorReporteCompra(
                proveedor,
                fechaInicio,
                fechaFinal,
                documentoEnum,
                tipoDocumentoEnum,
                estadoEnum,
                banderaBusqueda,
                session.getEmpresa());
        controladorReporteCompra.generarReporte();
                        
        //this.compras = compraServiceIf.obtenerCompraReporte(proveedor, fechaInicio, fechaFinal, documentoEnum, tipoDocumentoEnum, estadoEnum);
        mostrarDatosEnTabla(controladorReporteCompra.getCompras());
        //sumarTotalesComprasIndividuales(controladorReporteCompra.getCompras());
        //} else {
        //    DialogoCodefac.mensaje("Advertencia", "Debe seleccionar un proveedor", DialogoCodefac.MENSAJE_ADVERTENCIA);
        //}
        //}

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
        getLblSubtotal().setText(controladorReporteCompra.getSubtotal().toString());
        getLblSubtotal12().setText(controladorReporteCompra.getSubtotal12().toString());
        getLblSubtotal0().setText(controladorReporteCompra.getSubtotal0().toString());
        getLblDescuento().setText(controladorReporteCompra.getDescuento().toString());
        getLblIva12().setText(controladorReporteCompra.getIva().toString());
        getLblTotal().setText(controladorReporteCompra.getTotal().toString());

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
    public InterfaceModelFind obtenerDialogoBusqueda() {
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

    private void agregaValoresDefecto() {
        
        getCmbTipoReporte().removeAllItems();
        for (TipoReporteEnum tipo : TipoReporteEnum.values()) {
            getCmbTipoReporte().addItem(tipo);
        }
        
    }
    
    public enum TipoReporteEnum
    {
        NORMAL("Normal"),
        AGRUPADO_POR_CATEGORIA("Agrupado por categoria"),
        AGRUPADO_POR_SUSTENTO_SRI("Agrupado por sustento Sri");

        private TipoReporteEnum(String nombre) {
            this.nombre = nombre;
        }
        
        private String nombre;

        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
        
    }
}
