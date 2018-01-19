/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.facturacion.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.facturacion.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.main.panel.WidgetVentasDiarias;
import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
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
    
    public VentasDiariasModel(JDesktopPane parentPanel) {
        super(parentPanel);
        agregarListenerBotones();
        agregarListenerMovimiento();
        //getDateFechaEmision().setDate(new java.util.Date());
    }

    @Override
    public JPanel getPanelMovimiento() {
         JPanel panel=getPanelTitulo();
        return panel;
    }
    
    private void agregarListenerBotones() {
        
        getBtnAgregarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                productoSeleccionado = (Producto) buscarDialogoModel.getResultado();
                if (productoSeleccionado == null) {
                    return;
                }
                setearValoresProducto();
                //banderaAgregar = true;

            }
        });
        
    }
    
    public void setearValoresCamposTextos()
    {
        getTxtCantidadProducto().setText("");
        getTxtDescripcionProducto().setText("");
        getTxtCantidadProducto().setText("");
    }
    
    public void setearValoresProducto()
    {
        getTxtDescripcionProducto().setText(productoSeleccionado.getNombre());
        getTxtValorUnitarioProducto().setText(""+productoSeleccionado.getValorUnitario());
        getTxtCantidadProducto().requestFocus();
    }
    
    public void setarValoresVariables()
    {
        this.factura.setSubtotalImpuestos(BigDecimal.ZERO);
        this.factura.setSubtotalSinImpuestos(BigDecimal.ZERO);
        this.factura.setIva(BigDecimal.ZERO);
        this.factura.setTotal(BigDecimal.ZERO);
        this.factura.setDescuentoImpuestos(new BigDecimal(0));
        this.factura.setDescuentoImpuestos(new BigDecimal(0));
    }
    
    public void cargarCliente()
    {
        PersonaService cliente = new PersonaService();
        try
        {
            for(Persona p : cliente.buscar())
            {
                if(p.getRazonSocial().equals("Razon Social"))
                {
                    this.factura.setCliente(p);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Error cargando cliente");
        }
    }
    
    public void cargarFecha()
    {
        factura.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        factura.setFechaFactura((Date) UtilidadesFecha.hoy());
    }
        
    
}
