/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.busqueda.ProductoProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.CompraPanel;
import ec.com.codesoft.codefaclite.servidor.entity.Compra;
import ec.com.codesoft.codefaclite.servidor.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.ModuloEnum;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.TipoFacturacionEnumEstado;
import ec.com.codesoft.codefaclite.servidor.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.service.CompraService;
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;

/**
 *
 * @author Carlos
 */
public class CompraModel extends CompraPanel{

    /**
     * Referencia donde se va a almacenar la compra gestionado
     */
    private Compra compra;
    private ProductoProveedor productoProveedor;
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarCombos();
        agregarListenerBotones();
        crearVariables();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            CompraService servicio=new CompraService();
            setearValores();
            servicio.grabar(compra);
            DialogoCodefac.mensaje("Correcto","La compra fue guardada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Incorrecto","No se puede gurdar la compra",DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(CompraModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setearValores()
    {
        compra.setClaveAcceso("");
        DocumentoEnum documentoEnum= (DocumentoEnum) getCmbDocumento().getSelectedItem();
        compra.setCodigoDocumento(documentoEnum.getCodigo());
        compra.setDireccion("");
        compra.setEstado("a"); //TODO: cambiar el estado de las ordenes de compra
        compra.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        compra.setFechaFactura(new Date(getCmbFechaCompra().getDate().getTime()));
        compra.setIdentificacion("");
        compra.setProveedor(productoProveedor.getProveedor());
        
        compra.setPuntoEmision(getTxtPuntoEmision().getText());
        compra.setPuntoEstablecimiento(getTxtEstablecimiento().getText());
        compra.setSecuencial(Integer.parseInt(getTxtSecuencial().getText()));
        
        compra.setRazonSocial("");
        compra.setTelefono("");
        compra.setTipoFacturacion(""); //TODO: Establecer el metodo de facturacion manual y electronica
        
        //Seteando el tipo de documento 
        TipoDocumentoEnum tipoDocumentoEnum= (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        compra.setCodigoTipoDocumento(tipoDocumentoEnum.getCodigo());
 
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void imprimir() {
        
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void limpiar() {
        
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

    private void iniciarCombos() {
        //Agregar los documentos del sri
        getCmbDocumento().removeAllItems();
        List<DocumentoEnum> documentos= DocumentoEnum.obtenerDocumentoPorModulo(ModuloEnum.COMPRAS);
        for (DocumentoEnum documento : documentos) {
            getCmbDocumento().addItem(documento);
        }
        
        //Agregar los tipos de documentos disponibles
        getCmbTipoDocumento().removeAllItems();
        List<TipoDocumentoEnum> tipoDocumentos= TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloEnum.COMPRAS);
        for (TipoDocumentoEnum tipoDocumento : tipoDocumentos) {
            getCmbTipoDocumento().addItem(tipoDocumento);
        }
    }

    private void agregarListenerBotones() {
        getBtnProveedorBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorBusquedaDialogo buscarBusquedaDialogo = new ProveedorBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                Persona proveedor = (Persona) buscarDialogo.getResultado();
                if (proveedor != null) {
                    String identificacion=proveedor.getIdentificacion();
                    String nombre =proveedor.getRazonSocial();
                    getTxtProveedor().setText(identificacion+" - "+nombre);
                }
            }
        });
        
        getBtnBuscarProductoProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoProveedorBusquedaDialogo buscarBusquedaDialogo = new ProductoProveedorBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                productoProveedor = (ProductoProveedor) buscarDialogo.getResultado();
                if(productoProveedor!=null)
                {
                    getTxtProductoItem().setText(productoProveedor.getProducto().getNombre());
                    getTxtDescripcionItem().setText(productoProveedor.getProducto().getNombre());
                    getTxtPrecionUnitarioItem().setText(productoProveedor.getCosto()+"");
                }
            }
        });
        
        getBtnAgregarItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompraDetalle compraDetalle=new CompraDetalle();
                compraDetalle.setCantidad(Integer.parseInt(getTxtCantidadItem().getText()));
                compraDetalle.setPrecioUnitario(new BigDecimal(getTxtPrecionUnitarioItem().getText()));
                compraDetalle.setCompra(compra);
                compraDetalle.setDescripcion(getTxtDescripcionItem().getText());
                compraDetalle.setDescuento(BigDecimal.ZERO);
                compraDetalle.setIva(compraDetalle.calcularValorIva());
                compraDetalle.setProductoProveedor(productoProveedor);
                compraDetalle.setTotal(compraDetalle.calcularTotal());
                compraDetalle.setValorIce(BigDecimal.ZERO);
                compra.addDetalle(compraDetalle);
                
                /**
                 * Cargar los otros valores como el descuento
                 */
                compra.setDescuentoImpuestos(new BigDecimal(getTxtDescuentoImpuestos().getText()));
                compra.setDescuentoSinImpuestos(new BigDecimal(getTxtDescuentoSinImpuestos().getText()));
                actualizarTotales();
                mostrarDatosTabla();
                mostrarDatosTotales();
            }
        });
    }
    
    private void actualizarTotales()
    {
        List<CompraDetalle> detalles= compra.getDetalles();
        
        //Encerado Valores       
        compra.setIva(BigDecimal.ZERO);
        compra.setSubtotalImpuestos(BigDecimal.ZERO);
        compra.setSubtotalSinImpuestos(BigDecimal.ZERO);
        compra.setTotal(BigDecimal.ZERO);
                
        for (CompraDetalle detalle : detalles) {
             compra.setIva(compra.getIva().add(detalle.getIva()));
             compra.setSubtotalImpuestos(compra.getSubtotalImpuestos());
             compra.setSubtotalSinImpuestos(compra.getSubtotalSinImpuestos());
             compra.setTotal(compra.getTotal().add(detalle.getTotal()));
        }
    }
    
    /**
     * Actualiza los datos de la tabla segun los datos grabados en los detalles de la tabla
     * de compras
     */
    private void mostrarDatosTabla()
    {
        String[] titulo={"Cantidad","Descripcion","Valor unitario","Valor Total"};
        DefaultTableModel modeloTablaCompra=new DefaultTableModel(titulo,0);
        
        List<CompraDetalle> detalles= compra.getDetalles();
        for (CompraDetalle detalle : detalles) {
            Vector<String> fila=new Vector<String>();
            fila.add(detalle.getCantidad()+"");
            fila.add(detalle.getDescripcion()+"");
            fila.add(detalle.getPrecioUnitario()+"");
            fila.add(detalle.getSubtotal()+"");
            modeloTablaCompra.addRow(fila);
        }
        
        getTblDetalleProductos().setModel(modeloTablaCompra);
        
    }
    
    private void mostrarDatosTotales()
    {
        getLblIva().setText(compra.getIva()+"");
        getLblSubtotalImpuesto().setText(compra.getSubtotalImpuestos()+"");
        getLblSubtotalSinImpuesto().setText(compra.getSubtotalSinImpuestos()+"");
        getLblTotal().setText(compra.getTotal()+"");        
    }

    private void crearVariables() {
        this.compra=new Compra();        
    }
    
}
