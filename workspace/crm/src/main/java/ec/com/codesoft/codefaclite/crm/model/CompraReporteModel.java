/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.CompraReportePanel;
import ec.com.codesoft.codefaclite.crm.reportdata.CompraDataReporte;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.ejemplo.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CodesoftDesarrollo 1
 */

public class CompraReporteModel extends CompraReportePanel
{
    
    private DefaultTableModel modeloTablaDetallesCompras;
    private Persona proveedor;
    private Date fechaInicio;
    private Date fechaFinal;
    
    private DocumentoEnum documentoEnum;
    private TipoDocumentoEnum tipoDocumentoEnum;
    
    private boolean banderaBusqueda;
    
    //Variables que me permiten realizar la sumatoria de los totales de cada compra
    private BigDecimal subtotal;
    private BigDecimal subtotal0;
    private BigDecimal subtotal12;
    private BigDecimal descuento0;
    private BigDecimal descuento12;
    private BigDecimal descuento;
    private BigDecimal iva;
    private BigDecimal total;
    
    //Servicio que me permite realizar la busqueda según los filtros de la ventana
    private CompraServiceIf compraServiceIf;
    
    //Lista que permite almacenar los datos de la busqueda
    private List<Compra> compras;
    
    //Mapa que almacena
    private Map parametros;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarValoresVentana();
        agregarListener();
        crearVariables();
        this.compraServiceIf = ServiceFactory.getFactory().getCompraServiceIf();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Si desea continuar se perderan los cambios realizados?", DialogoCodefac.MENSAJE_ADVERTENCIA);
        if (!respuesta) {
            throw new ExcepcionCodefacLite("Cancelacion usuario");
        }else
        {
            iniciarValoresVentana();
            crearVariables();
            limpiarTotalesComprasIndiviales();
        }
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
        try{
            if(compras == null)
            {
                DialogoCodefac.mensaje("Advertencia", "Se debe realizar una busqueda, para imprimir un reporte", DialogoCodefac.MENSAJE_ADVERTENCIA);
            }else{
            
                InputStream path = RecursoCodefac.JASPER_COMPRA.getResourceInputStream("reporte_compra.jrxml");
                List<CompraDataReporte> compraDataReportes = new ArrayList<>();

                if(banderaBusqueda)
                {
                    parametros.put("identificacion", "TODOS");
                    parametros.put("nombre", "TODOS");
                }
                else
                {
                    parametros.put("identificacion", this.proveedor.getIdentificacion()+"");
                    parametros.put("nombre", this.proveedor.getRazonSocial()+"");

                }
                
                //Parametros estaticos que se envian para realizar el Reporte
                parametros.put("tipodocumento", getCmbTipoDocumento().getSelectedItem() + "");
                parametros.put("documento", getCmbDocumento().getSelectedItem() + "");
                parametros.put("fechainicio", this.fechaInicio + "");
                parametros.put("fechafin", this.fechaFinal + "");
                parametros.put("subtotal", this.subtotal + "");
                parametros.put("subtotal12", this.subtotal12 + "");
                parametros.put("subtotal0", this.subtotal0 + "");
                parametros.put("descuento", this.descuento + "");
                parametros.put("iva", this.iva + "");
                parametros.put("total", this.total + "");

                //Parametros dinámicos que se envian para realizar el Reporte
                for(Compra compra : this.compras)
                {
                    CompraDataReporte cdr = new CompraDataReporte();
                    cdr.setPreimPreso(compra.getPuntoEmision() + compra.getSecuencial() + compra.getPuntoEstablecimiento()+"");
                    cdr.setIdentificacion(compra.getProveedor().getIdentificacion());
                    cdr.setNombre(compra.getProveedor().getRazonSocial());
                    cdr.setFecha(compra.getFechaFactura()+"");
                    cdr.setSubtotal(sumarValores(compra.getSubtotalImpuestos(), compra.getSubtotalSinImpuestos())+"");
                    cdr.setSubtotal0(compra.getSubtotalSinImpuestos()+"");
                    cdr.setSubtotal12(compra.getSubtotalImpuestos()+"");
                    cdr.setDescuento(sumarValores(compra.getDescuentoImpuestos(),compra.getDescuentoSinImpuestos())+"");
                    cdr.setDescuento0(compra.getDescuentoSinImpuestos()+"");
                    cdr.setDescuento12(compra.getDescuentoImpuestos()+"");
                    cdr.setIva(compra.getIva()+"");
                    cdr.setTotal(compra.getTotal()+"");
                    compraDataReportes.add(cdr);
                }

                ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, compraDataReportes, panelPadre, "Reporte Compra");
            }
        }catch(Exception e)
        {
            DialogoCodefac.mensaje("Error","No se pudo crear el reporte correctamente", DialogoCodefac.MENSAJE_ADVERTENCIA);
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

    @Override
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
    private void crearVariables()
    {
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
    public void iniciarValoresVentana()
    {
        initValoresCombos();
        initModelTablaDetalleCompras();
        getTxtProveedor().setEnabled(false);
    }
    
    //Valores iniciales que tendra la tabla detalle compras
    public void initModelTablaDetalleCompras()
    {
        String titulos[] = {"Preimpreso", "Identificacion", "Nombre", "Fecha", "Subtotal 12%", "Subtotal 0%", "Descuento", "IVA", "TOTAL"};
        modeloTablaDetallesCompras = new DefaultTableModel(titulos,0);
        getTableDetalleCompras().setModel(modeloTablaDetallesCompras);
    }
    
    public void initValoresCombos()
    {
        //Iniciar valores del combo Documento
        getCmbDocumento().removeAllItems();
        List <DocumentoEnum> documentos = DocumentoEnum.obtenerDocumentoPorModulo(ModuloEnum.COMPRAS);
        for( DocumentoEnum documento : documentos)
        {
            getCmbDocumento().addItem(documento);
        }
        
        //Iniciar valores del combo TipoDocumento
        getCmbTipoDocumento().removeAllItems();
        List <TipoDocumentoEnum> tipoDocumentoEnums = TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloEnum.COMPRAS);
        for(TipoDocumentoEnum tipoDocumentoEnum : tipoDocumentoEnums)
        {
            getCmbTipoDocumento().addItem(tipoDocumentoEnum);
        }
    }
    
    public void agregarListener()
    {
        //Permite buscar a un proveedor mediante una ventana
        getBtnBuscarProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorBusquedaDialogo proveedorBusquedaDialogo = new ProveedorBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(proveedorBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                proveedor = (Persona) buscarDialogoModel.getResultado();
                if(proveedor != null)
                {
                    String identificacion = proveedor.getIdentificacion();
                    String nombre = proveedor.getRazonSocial();
                    getTxtProveedor().setText(identificacion + " - " + nombre);
                }
            }
        });
        
        //Permite establecer la busqueda por un proveedor o todos los proveedores
        getChkTodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getChkTodos().isSelected())
                {
                    getBtnBuscarProveedor().setEnabled(false);
                    getTxtProveedor().setText("");
                    banderaBusqueda = true;
                }
                else
                {
                    getBtnBuscarProveedor().setEnabled(true);
                    banderaBusqueda = false;
                }
            }
        });
        
        getBtcConsultar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setearValores();
                    visualizarTotalesComprasIndividuales();
                } catch (RemoteException ex) {
                    Logger.getLogger(CompraReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void setearValores() throws RemoteException
    {
        //Obtener valores de combos
        this.documentoEnum = (DocumentoEnum) getCmbDocumento().getSelectedItem();
        this.tipoDocumentoEnum = (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        //Obtener las fecha seleccionadas de los combos
        this.fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
        this.fechaFinal = new Date(getDateFechaFinal().getDate().getTime());
        
        if(banderaBusqueda)
        {
            this.compras = compraServiceIf.obtenerTodos();
            mostrarDatosEnTabla(this.compras);
            sumarTotalesComprasIndividuales(this.compras);
        }
        else
        {
            if(proveedor != null)
            {
                this.compras = compraServiceIf.obtenerCompraReporte(proveedor, fechaInicio, fechaFinal, documentoEnum, tipoDocumentoEnum);
                mostrarDatosEnTabla(this.compras);
                sumarTotalesComprasIndividuales(this.compras);
            }
            else
            {
                DialogoCodefac.mensaje("Advertencia", "Debe seleccionar un proveedor", DialogoCodefac.MENSAJE_ADVERTENCIA);
            }
        }
            
        
    }
    
    public void sumarTotalesComprasIndividuales(List<Compra> compras)
    {
        for(Compra compra : compras)
        {
            this.subtotal = sumarValores(compra.getSubtotalImpuestos(), compra.getSubtotalSinImpuestos());
            this.subtotal0 = this.subtotal0.add(compra.getSubtotalSinImpuestos());
            this.subtotal12 = this.subtotal12.add(compra.getDescuentoImpuestos());
            this.descuento = sumarValores(compra.getDescuentoImpuestos(), compra.getDescuentoSinImpuestos());
            this.descuento0 =  this.descuento0.add(compra.getDescuentoSinImpuestos());
            this.descuento12 = this.descuento12.add(compra.getDescuentoImpuestos());
            this.iva = this.iva.add(compra.getIva());
            this.total = this.total.add(compra.getTotal());
        }
    }
    
    public BigDecimal sumarValores(BigDecimal d1, BigDecimal d2)
    {
        return d1.add(d2);
    }
    
    public void mostrarDatosEnTabla(List<Compra> compras)
    {
        String titulos[] = {"Preimpreso", "Identificacion", "Nombre", "Fecha", "Subtotal 12%", "Subtotal 0%", "Descuento", "IVA", "TOTAL"};
        this.modeloTablaDetallesCompras = new DefaultTableModel(titulos, 0);
       
        for(Compra compra : compras)
        {
            Vector<String> fila = new Vector();
            fila.add(compra.getPuntoEmision() + compra.getSecuencial() + compra.getPuntoEstablecimiento());
            fila.add(compra.getProveedor().getIdentificacion());
            fila.add(compra.getProveedor().getRazonSocial());
            fila.add(compra.getFechaFactura()+"");
            fila.add(compra.getSubtotalImpuestos()+"");
            fila.add(compra.getSubtotalSinImpuestos()+"");
            BigDecimal descuento = new BigDecimal(BigInteger.ZERO);
            descuento = descuento.add(compra.getDescuentoImpuestos()).add(compra.getDescuentoSinImpuestos());
            fila.add(descuento+"");
            fila.add(compra.getIva()+"");
            fila.add(compra.getTotal()+"");
            this.modeloTablaDetallesCompras.addRow(fila);;
        }
        getTableDetalleCompras().setModel(modeloTablaDetallesCompras);
        alinearColumnasTablas();
          
    }
    
    public void alinearColumnasTablas()
    {
        UtilidadesTablas.alinearTodasColumnasTabla(getTableDetalleCompras(), UtilidadesTablas.alinearIzquierda);
        UtilidadesTablas.alinearColumnasTabla(getTableDetalleCompras(), UtilidadesTablas.alinearDerecha, new Integer[]{4,5,6,7,8});
    }
    
    public void visualizarTotalesComprasIndividuales()
    {
        getLblSubtotal().setText(this.subtotal + "");
        getLblSubtotal12().setText(this.subtotal12 + "");
        getLblSubtotal0().setText(this.subtotal0 + "");
        getLblDescuento().setText(this.descuento + "");
        getLblIva12().setText(this.iva + "");
        getLblTotal().setText(this.total + "");
        
    }
    
    public void limpiarTotalesComprasIndiviales()
    {
        getLblTotal().setText("0.00");
        getLblSubtotal().setText("0.00");
        getLblSubtotal12().setText("0.00");
        getLblSubtotal0().setText("0.00");
        getLblDescuento().setText("0.00");
        getLblIva12().setText("0.00");
        getLblTotal().setText("0.00");
        getTxtProveedor().setText("");
    }
}
