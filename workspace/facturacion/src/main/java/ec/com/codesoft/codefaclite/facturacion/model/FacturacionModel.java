/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacion.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.facturacion.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.facturacion.other.FacturacionElectronica;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturacionPanel;
import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidor.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoDetalleService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Carlos
 */
public class FacturacionModel extends FacturacionPanel{

    private Persona persona;
    private Factura factura;
    private DefaultTableModel modeloTablaFormasPago;
    private DefaultTableModel modeloTablaDetallesProductos;
    private Producto productoSeleccionado;
    private int fila;
    private boolean bandera;
    private boolean banderaAgregar;
    private BigDecimal subtotalSinImpuestos;
    private BigDecimal subtotal12;
    private BigDecimal iva;
    private BigDecimal valorTotal;
    
    
    public FacturacionModel() {
        addListenerButtons();
        initModelTablaFormaPago();
        initModelTablaDetalleFactura();
        agregarFechaEmision();
        subtotalSinImpuestos = new BigDecimal(0);
        subtotal12 = new BigDecimal(0);
        iva = new BigDecimal(0);
        valorTotal = new BigDecimal(0);
        bandera = false;
        banderaAgregar = true;
        calcularIva12();
    }
    
    private void addListenerButtons() {
        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ClienteFacturacionBusqueda clienteBusquedaDialogo= new ClienteFacturacionBusqueda();
                BuscarDialogoModel buscarDialogoModel=new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                persona=(Persona) buscarDialogoModel.getResultado();
                if(persona!=null)
                {
                    getTxtCliente().setText(persona.getIdentificacion());
                    getLblNombreCliente().setText(persona.getNombreLegal());
                    getLblDireccionCliente().setText(persona.getDireccion());
                    getLblTelefonoCliente().setText(persona.getTelefonoConvencional());                
                };
            }
        });
        
        getBtnAgregarFormaPago().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormaPagoDialogModel dialog=new FormaPagoDialogModel(null,true);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                FormaPago formaPago=dialog.getFormaPago();
                agregarFormaPagoTabla(formaPago);
            }
        });
        
        getBtnAgregarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                productoSeleccionado = (Producto) buscarDialogoModel.getResultado();

                if (productoSeleccionado == null) {
                    return ;
                    //throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar producto vacio");
                }

                setearValoresProducto();

                banderaAgregar = true;

            }
        });
        
        getTblDetalleFactura().addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fila=getTblDetalleFactura().getSelectedRow();
                System.out.println(fila);
                bandera=true;
            }
        });
        
        getBtnAgregarDetalleFactura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( banderaAgregar ){   
                    FacturaDetalle facturaDetalle=new FacturaDetalle();
                    facturaDetalle.setCantidad(new BigDecimal(getTxtCantidad().getText()));
                    facturaDetalle.setDescripcion(getTxtDescripcion().getText());
                    facturaDetalle.setPrecioUnitario(new BigDecimal(getTxtValorUnitario().getText()));
                    facturaDetalle.setProducto(productoSeleccionado);
                    System.out.println(productoSeleccionado);
                    facturaDetalle.setTotal(facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario()));
                    facturaDetalle.setValorIce(BigDecimal.ZERO);
                    factura.getDetalles().add(facturaDetalle);
                    cargarDatosDetalles();
                    limpiarCampos();
                    banderaAgregar = false;
                    //getLblSubtotalSinImpuesto().setText();
                }
            }
        });
        
        getBtnQuitarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(bandera)
                {
                    bandera=false;
                    modeloTablaDetallesProductos.removeRow(fila);
                    factura.getDetalles().remove(fila);
                }
            }
        });
        
        getBtnEditarDetalle().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bandera)
                {
                    BigDecimal valorUnitario = new BigDecimal(String.valueOf(modeloTablaDetallesProductos.getValueAt(getTblDetalleFactura().getSelectedRow(),1)));
                    BigDecimal cantidad = new BigDecimal (String.valueOf(modeloTablaDetallesProductos.getValueAt(getTblDetalleFactura().getSelectedRow(),2)));
                    factura.getDetalles().get(fila).setPrecioUnitario(valorUnitario);
                    factura.getDetalles().get(fila).setCantidad(cantidad);
                    factura.getDetalles().get(fila).setTotal(valorUnitario.multiply(cantidad));
                    cargarDatosDetalles();
                    
                }
            }
                    
        });
        
        getBtnAgregarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Persona>() {
                    @Override
                    public void updateInterface(Persona entity) {
                        persona=entity;
                        if(persona!=null)
                        {
                            setearValoresCliente();
                        }
                    }
                },DialogInterfacePanel.CLIENTE_PANEL, false);
            }
        });
        
        getBtnCrearProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ObserverUpdateInterface observer = new ObserverUpdateInterface<Producto>() {
                    @Override
                    public void updateInterface(Producto entity) {
                        if (entity != null) {
                            productoSeleccionado=entity;
                            setearValoresProducto();
                        }
                    }
                };
                
                panelPadre.crearDialogoCodefac(observer, DialogInterfacePanel.PRODUCTO_PANEL, false);
                
            }
        });
        
       
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        //Despues de implemetar todo el metodo de grabar
        FacturacionElectronica facturaElectronica=new FacturacionElectronica(factura, session);
        facturaElectronica.procesarComprobante();//listo se encarga de procesar el comprobante
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        this.factura=new Factura();
        this.factura.setDetalles(new ArrayList<FacturaDetalle>());
        
        getLblRuc().setText(session.getEmpresa().getIdentificacion());
        getLblDireccion().setText(session.getEmpresa().getDireccion());
        getLblTelefonos().setText(session.getEmpresa().getTelefonos());
        getLblNombreComercial().setText(session.getEmpresa().getNombreLegal());
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
        Map<Integer,Boolean> permisos=new HashMap<Integer,Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO,true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR,true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }
    
    private void agregarFormaPagoTabla(FormaPago formaPago)
    {
        Vector<String> fila=new Vector<>();
        fila.add(formaPago.getSriFormaPago().getNombre());
        fila.add(formaPago.getTotal().toString());
        fila.add(formaPago.getUnidadTiempo());
        fila.add(formaPago.getPlazo()+"");
        System.out.println(formaPago);
        this.modeloTablaFormasPago.addRow(fila);
    }
    
    private void initModelTablaFormaPago()
    {
        Vector<String> titulo = new Vector<>();
        titulo.add("forma pago");
        titulo.add("Valor");
        titulo.add("Tipo");
        titulo.add("Tiempo");
        
        this.modeloTablaFormasPago=new DefaultTableModel(titulo,0);
        getTblFormasPago().setModel(modeloTablaFormasPago);
    }
    
    private void initModelTablaDetalleFactura()
    {
        Vector<String> titulo = new Vector<>();
        titulo.add("Codigo");
        titulo.add("Valor Uni");
        titulo.add("Cantidad");
        titulo.add("Descripcion");
        titulo.add("Valor Total");
        
        this.modeloTablaDetallesProductos = new DefaultTableModel(titulo,0);
        //this.modeloTablaDetallesProductos.isCellEditable
        getTblDetalleFactura().setModel(modeloTablaDetallesProductos);
    }
    private void cargarDatosAdicionales()
    {
        
    }
    
    private void cargarDatosDetalles()
    {
        Vector<String> titulo=new Vector<>();
        titulo.add("Codigo");
        titulo.add("ValorUni");
        titulo.add("Cantidad");
        titulo.add("Descripcion");
        titulo.add("Valor Total");        
        
        this.modeloTablaDetallesProductos=new DefaultTableModel(titulo,0);
        
        List<FacturaDetalle> detalles= factura.getDetalles();
        for (FacturaDetalle detalle : detalles) {
            Vector<String> fila=new Vector<String>();
            fila.add(detalle.getProducto().getCodigoPrincipal());
            fila.add(detalle.getProducto().getValorUnitario().toString());
            fila.add(detalle.getCantidad().toString());
            fila.add(detalle.getDescripcion());
            fila.add(detalle.getTotal().toString());
            modeloTablaDetallesProductos.addRow(fila);
            
        }
        getTblDetalleFactura().setModel(this.modeloTablaDetallesProductos);
    }

    private void agregarFechaEmision() {

    }
    
    private void limpiarCampos()
    {
        getTxtCantidad().setText("");
        getTxtCliente().setText("");
        getTxtDescripcion().setText("");
        getTxtValorUnitario().setText("");
    }
    
    public void calcularSubtotalSinImpuestos(List<FacturaDetalle> facturaDetalles)
    {
        facturaDetalles.forEach((facturaDetalle) -> {
            this.subtotalSinImpuestos = this.subtotalSinImpuestos.add(facturaDetalle.getTotal());
        });
    }
    
    public void calcularSubtotal12(List<FacturaDetalle> facturaDetalles)
    {
        facturaDetalles.forEach((facturaDetalle) -> {
            this.subtotal12 = this.subtotal12.add(facturaDetalle.getTotal());
        });
    }        
    
    public void calcularIva12()
    {
        Map<String,Object> map = new HashMap<String, Object>();
        ImpuestoDetalleService impuestoDetalleService = new ImpuestoDetalleService();
        //map.put("tarifa", Integer.parseInt(this.session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).getValor()));
        map.put("tarifa", 12);
        List<ImpuestoDetalle> listaImpuestoDetalles = impuestoDetalleService.buscarImpuestoDetallePorMap(map);
        listaImpuestoDetalles.forEach((iD) -> {
            BigDecimal ivaa = iD.getPorcentaje();
            System.out.println("Porcentaje a usar->" + ivaa);
            System.out.println(iD.getDescripcion());
            
        });               
    }
    public void cargarTotales()
    {
        calcularSubtotalSinImpuestos(factura.getDetalles());
        calcularSubtotal12(factura.getDetalles());
        getLblSubtotalSinImpuesto().setText(""+this.subtotalSinImpuestos);
        getLblSubtotal12().setText(""+this.subtotal12);
        
    }
    private void setearValoresCliente()
    {
        getTxtCliente().setText(persona.getIdentificacion());
        getLblNombreCliente().setText(persona.getNombreLegal());
        getLblDireccionCliente().setText(persona.getDireccion());
        getLblTelefonoCliente().setText(persona.getTelefonoConvencional());  
    }
    
    private void setearValoresProducto()
    {
        getTxtValorUnitario().setText(productoSeleccionado.getValorUnitario().toString());
        getTxtDescripcion().setText(productoSeleccionado.getNombre());
    }

}
