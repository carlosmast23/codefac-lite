/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import com.sun.xml.internal.bind.v2.model.core.Adapter;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacion.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.facturacion.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturacionPanel;
import ec.com.codesoft.codefaclite.main.panel.WidgetVentasDiarias;
import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC
 */
public class VentasDiariasModel extends WidgetVentasDiarias
{
    private Factura factura;
    private DefaultTableModel modeloTablaDetallesProductos;
    private Producto productoSeleccionado;
    private int fila;
    private boolean banderaProducto;
    
    public VentasDiariasModel(JDesktopPane parentPanel) {
        super(parentPanel);
        iniciarValores();
        agregarListenerBotones();
        agregarListenerMovimiento();
    }

    @Override
    public JPanel getPanelMovimiento() {
         JPanel panel=getPanelTitulo();
        return panel;
    }
    
    private void agregarListenerBotones() {
        
        getBtnBuscarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                productoSeleccionado = (Producto) buscarDialogoModel.getResultado();
                banderaProducto = true;
                
                if (productoSeleccionado == null) {
                    return;
                }
                
                setearValoresProducto();
            }
        });
        
        getBtnAgregarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if(banderaProducto){
                        if(validarCamposIngresar())
                        {
                            agregarDetallesFactura(null);
                            limpiarValoresCamposTextos();
                            cargarDatosDetalles();
                            procesarTotales();
                            banderaProducto = false;
                            getjTabbedPanel().setSelectedIndex(1);
                        }else
                        {
                            DialogoCodefac.mensaje("Validacion", "Verificar los campos ingresados", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        }
                    }
                    else
                    {
                        DialogoCodefac.mensaje("Producto", "Se debe seleccionar un producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }
                
                
            }
        });
        
        getTblDetalleFactura().addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(java.awt.event.MouseEvent evt) {
               fila = getTblDetalleFactura().getSelectedRow();
            }
        });
        
        getBtnEliminarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    if(!factura.getDetalles().isEmpty() ){
                        if( fila != -1){
                            factura.getDetalles().remove(fila);
                            fila = -1;
                            procesarTotales();
                            cargarDatosDetalles();
                            
                        }
                        else{
                            DialogoCodefac.mensaje("Detalles Producto", "Seleccione el objeto a eliminar", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        }
                    }
                    else
                    {
                        DialogoCodefac.mensaje("Detalles Producto", "No existe registro detalles productos", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        System.out.println("Factura Detalles ->> " + factura.getDetalles());
                    }
                }
                catch(Exception exc)
                {
                    DialogoCodefac.mensaje("Detalles Producto", "No existe registro detalles productos", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
            }
        });
        
        getBtnFacturar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean permitirFacturar = false;
                Boolean respuesta = null;
                if (factura.getDetalles() == null) {
                    DialogoCodefac.mensaje("Alerta", "No se puede facturar sin detalles", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    try {
                        throw new ExcepcionCodefacLite("Necesita seleccionar detalles ");
                    } catch (ExcepcionCodefacLite ex) {
                        Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    permitirFacturar = true;
                    respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea continuar con la facturación?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    if (!respuesta) {
                        try {
                            throw new ExcepcionCodefacLite("Cancelacion usuario");
                        } catch (ExcepcionCodefacLite ex) {
                            Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                if(permitirFacturar && respuesta)
                {
                    cargarCliente();
                    definirFechaFacturacion();
                    FacturacionModel facturacionModel = new FacturacionModel();

                        panelPadre.crearVentanaCodefac(facturacionModel, true);
                        try {
                            facturacionModel.setFactura(factura);
                            facturacionModel.cargarSecuencial();
                        } catch (ExcepcionCodefacLite ex) {
                            Logger.getLogger(VentasDiariasModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        setarValoresVariables();
                        facturacionModel.revalidate();
                        facturacionModel.repaint();
                }
                    
            }
        });
    }            
    
    public void iniciarValores()
    {
        factura = new Factura();
        fila = -1;
        banderaProducto = false;
        factura.setFechaCreacion(UtilidadesFecha.getFechaHoy());
    }
    
    public void agregarDetallesFactura (FacturaDetalle facturaDetalle)
    {
        boolean agregar = true;
        if (facturaDetalle != null) {
            agregar = false;
        } else {
            facturaDetalle = new FacturaDetalle();
        }
        
        facturaDetalle.setCantidad(new BigDecimal(getTxtCantidadProducto().getText()));
        facturaDetalle.setDescripcion(getTxtDescripcionProducto().getText());
        BigDecimal valorTotalUnitario = new BigDecimal(getTxtValorUnitarioProducto().getText());
        facturaDetalle.setPrecioUnitario(valorTotalUnitario.setScale(2, BigDecimal.ROUND_HALF_UP));
        facturaDetalle.setProducto(productoSeleccionado);
        facturaDetalle.setValorIce(BigDecimal.ZERO);
        facturaDetalle.setDescuento(BigDecimal.ZERO);
            
            //Calular el total despues del descuento porque necesito esa valor para grabar
            BigDecimal setTotal = facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario()).subtract(facturaDetalle.getDescuento());
            facturaDetalle.setTotal(setTotal.setScale(2, BigDecimal.ROUND_HALF_UP));
            /**
             * Revisar este calculo del iva para no calcular 2 veces al mostrar
             */
            if (facturaDetalle.getProducto().getIva().getTarifa().equals(0)) {
                facturaDetalle.setIva(BigDecimal.ZERO);
            } else {
                BigDecimal iva = facturaDetalle.getTotal().multiply(obtenerValorIva()).setScale(2, BigDecimal.ROUND_HALF_UP);
                facturaDetalle.setIva(iva);
            }
           //Solo agregar si se enviar un dato vacio
        if (agregar) {
            factura.addDetalle(facturaDetalle);
            limpiarValoresCamposTextos();
            cargarDatosDetalles();
            procesarTotales();
        } 
    }
    
    public void limpiarValoresCamposTextos()
    {
        getTxtCantidadProducto().setText("");
        getTxtDescripcionProducto().setText("");
        getTxtValorUnitarioProducto().setText("");
    }
    
    public void setearValoresProducto()
    {
        getTxtDescripcionProducto().setText(productoSeleccionado.getNombre());
        getTxtValorUnitarioProducto().setText(""+productoSeleccionado.getValorUnitario());
        getTxtCantidadProducto().requestFocus();
    }
    
    public void setarValoresVariables()
    {
        this.factura = new Factura();
        this.factura.setDetalles(new ArrayList<FacturaDetalle>());
        this.factura.setSubtotalImpuestos(BigDecimal.ZERO);
        this.factura.setSubtotalSinImpuestos(BigDecimal.ZERO);
        this.factura.setIva(BigDecimal.ZERO);
        this.factura.setTotal(BigDecimal.ZERO);
        this.factura.setDescuentoImpuestos(new BigDecimal(0));
        //Limpiar detalles
        initModelTablaDetalleFactura();
        //Limpiar valores totales
        getLblSubtotal12().setText("");
        getLblSubtotal0().setText("");
        getLblIva12().setText("");
        getLblValorTotal().setText("");
    }
    
    public void cargarCliente()
    {
        PersonaService cliente = new PersonaService();
        Map<String,Object> clienteMap = new HashMap<String, Object>();
        clienteMap.put("razonSocial", "Cliente Final");
        this.factura.setCliente(cliente.obtenerPorMap(clienteMap).get(0));
    }
    
    public void cargarFecha()
    {
        factura.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        factura.setFechaFactura((Date) UtilidadesFecha.hoy());
    }
    
    private void cargarDatosDetalles() {
        
        Vector<String> titulo = new Vector<>();
        titulo.add("Nombre");
        titulo.add("#");
        titulo.add("Valor");
        titulo.add("Total");

        List<FacturaDetalle> detalles = factura.getDetalles();

        this.modeloTablaDetallesProductos = new DefaultTableModel(titulo, 0);
        for (FacturaDetalle detalle : detalles) {
            Vector<String> fila = new Vector<String>();
            fila.add(detalle.getDescripcion());
            fila.add(detalle.getCantidad().toString());
            fila.add(detalle.getPrecioUnitario().toString());
            fila.add(detalle.getTotal().toString());
            modeloTablaDetallesProductos.addRow(fila);
        }
        getTblDetalleFactura().setModel(this.modeloTablaDetallesProductos);
    }
    
    public void calcularSubtotal12y0(List<FacturaDetalle> facturaDetalles) {
        this.factura.setSubtotalImpuestos(BigDecimal.ZERO);
        this.factura.setSubtotalSinImpuestos(BigDecimal.ZERO);        
        this.factura.setDescuentoImpuestos(new BigDecimal(0));
        
        
        for (FacturaDetalle facturaDetalle : facturaDetalles) {
            if (facturaDetalle.getProducto().getIva().getTarifa() == 12) {
                this.factura.setSubtotalImpuestos(factura.getSubtotalImpuestos().add(facturaDetalle.getPrecioUnitario().multiply(facturaDetalle.getCantidad())));                         
            }      
                this.factura.setSubtotalSinImpuestos(this.factura.getSubtotalSinImpuestos().add(facturaDetalle.getPrecioUnitario().multiply(facturaDetalle.getCantidad())));
        }
        
        this.factura.setSubtotalImpuestos(factura.getSubtotalImpuestos().setScale(2, BigDecimal.ROUND_HALF_UP));
        this.factura.setSubtotalSinImpuestos(factura.getSubtotalSinImpuestos().setScale(2,BigDecimal.ROUND_HALF_UP));
    }
    
     public void calcularSubtotales() {
     
        this.factura.setSubtotalSinImpuestos(this.factura.getSubtotalSinImpuestos().subtract(this.factura.getSubtotalImpuestos()));
        this.factura.setDescuentoSinImpuestos(new BigDecimal(0));
    }

    public void calcularIva12() {
        this.factura.setIva(this.factura.getSubtotalImpuestos().subtract(this.factura.getDescuentoImpuestos()).multiply(obtenerValorIva()));
        this.factura.setIva(this.factura.getIva().setScale(2, BigDecimal.ROUND_HALF_UP));
    }

    public void calcularValorTotal() {
        this.factura.setTotal(
                factura.getSubtotalImpuestos().subtract(this.factura.getDescuentoImpuestos()).
                        add(factura.getSubtotalSinImpuestos().subtract(factura.getDescuentoSinImpuestos())).
                        add(factura.getIva()));
    }
    
    public void procesarTotales()
    {
        calcularSubtotal12y0(factura.getDetalles());
        calcularSubtotales();
        calcularIva12();
        calcularValorTotal();
        mostrarTotales();
    }
    
    public void mostrarTotales()
    {
        this.getLblSubtotal12().setText(""+this.factura.getSubtotalImpuestos());
        this.getLblSubtotal0().setText(""+this.factura.getSubtotalSinImpuestos());
        this.getLblIva12().setText(""+this.factura.getIva());
        this.getLblValorTotal().setText(""+this.factura.getTotal());
    }
    
    public BigDecimal obtenerValorIva() {
        Map<String, Object> map = new HashMap<>();
        ImpuestoDetalleService impuestoDetalleService = new ImpuestoDetalleService();
        map.put("tarifa", 12); //TODO Parametrizar el iva con la variable del sistema
        List<ImpuestoDetalle> listaImpuestoDetalles = impuestoDetalleService.buscarImpuestoDetallePorMap(map);
        listaImpuestoDetalles.forEach((iD) -> {
            BigDecimal iva = iD.getPorcentaje();
        });
        return new BigDecimal(0.120);
    }
    
    public void definirFechaFacturacion()
    {   
        factura.setFechaFactura(UtilidadesFecha.getFechaHoy());
    }
    
    private void initModelTablaDetalleFactura() 
    {
        Vector<String> titulo = new Vector<>();
        titulo.add("Nombre");
        titulo.add("#");
        titulo.add("Valor");
        titulo.add("Total");
        this.modeloTablaDetallesProductos = new DefaultTableModel(titulo, 0);
        getTblDetalleFactura().setModel(modeloTablaDetallesProductos);
    }
    
    private boolean validarCamposIngresar()
    {
        String cantidadProducto = getTxtCantidadProducto().getText();
        String valorTotal = getTxtValorUnitarioProducto().getText();
        
        if(verificarNumeroEntero(cantidadProducto) && verificarNumeroDoble(valorTotal))
        {
            return true;
        }
        
        return false;
    }
    
    private boolean verificarNumeroEntero(String Numero)
    {
        try{
            int numero = Integer.parseInt(Numero);
        }
        catch(NumberFormatException e){
            return false;
        }

        return true;
    }
    
    private boolean verificarNumeroDoble(String Numero)
    {
        try{
            float numero = Float.parseFloat(Numero);
        }
        catch(NumberFormatException e){
            return false;
        }

        return true;
    }

            
}
