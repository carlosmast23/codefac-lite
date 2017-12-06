/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacion.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.facturacion.panel.NotaCreditoPanel;
import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidor.entity.NotaCreditoDetalle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class NotaCreditoModel extends NotaCreditoPanel {

    private DefaultTableModel modeloTablaDetalle = new DefaultTableModel();
    private NotaCredito notaCredito;

    public NotaCreditoModel() {
        addListenerButtons();
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
        /**
         * Cargar Datos de la empresa
         */
        getLblRuc().setText(session.getEmpresa().getIdentificacion());
        getLblDireccion().setText(session.getEmpresa().getDireccion());
        getLblTelefonos().setText(session.getEmpresa().getTelefonos());
        getLblNombreComercial().setText(session.getEmpresa().getRazonSocial());

        notaCredito = new NotaCredito();
        crearDetalleTabla();
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

    private void addListenerButtons() {
        getBtnBuscarFactura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacturaBusqueda facturaBusqueda = new FacturaBusqueda();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(facturaBusqueda);
                buscarDialogoModel.setVisible(true);
                Factura factura = (Factura) buscarDialogoModel.getResultado();
                if (factura != null) {
                    notaCredito.setFactura(factura);
                    cargarDatosNotaCredito();
                }
            }
        });
    }

    private void cargarDatosNotaCredito() {
        
        /**
         * Setear datos de la factura a la nota de credito
         */
        notaCredito.setTotal(notaCredito.getFactura().getTotal());
        notaCredito.setValorIvaDoce(notaCredito.getFactura().getValorIvaDoce());
        notaCredito.setSubtotalCero(notaCredito.getFactura().getSubtotalCero());
        notaCredito.setSubtotalDoce(notaCredito.getFactura().getSubtotalDoce());
        
        
        /**
         * CargarDetallesNotaCredito
         */
        List<FacturaDetalle> detallesFactura = notaCredito.getFactura().getDetalles();
        for (FacturaDetalle facturaDetalle : detallesFactura) {
            NotaCreditoDetalle notaDetalle = new NotaCreditoDetalle();
            notaDetalle.setCantidad(facturaDetalle.getCantidad());
            notaDetalle.setDescripcion(facturaDetalle.getDescripcion());
            notaDetalle.setDescuento(facturaDetalle.getDescuento());
            notaDetalle.setIva(facturaDetalle.getIva());
            notaDetalle.setPrecioUnitario(facturaDetalle.getPrecioUnitario());
            notaDetalle.setProducto(facturaDetalle.getProducto());
            notaDetalle.setTotal(facturaDetalle.getTotal());
            notaDetalle.setValorIce(facturaDetalle.getValorIce());
            notaCredito.addDetalle(notaDetalle);
        }
        
        actualizarDatosTablaDetalle();
        mostrarDatosNotaCredito();

    }

    private void mostrarDatosNotaCredito() {
        getTxtReferenciaFactura().setText(notaCredito.getFactura().getPreimpreso());
        getLblNombreCliente().setText(notaCredito.getFactura().getCliente().getRazonSocial());
        getLblDireccionCliente().setText(notaCredito.getFactura().getCliente().getDireccion());
        getLblTelefonoCliente().setText(notaCredito.getFactura().getCliente().getTelefonoCelular());

        /**
         * Cargar Los calculos de los totales
         */
        getTxtValorTotal().setText(notaCredito.getTotal() + "");
        getLblIva12().setText(notaCredito.getSubtotalDoce() + "");
        getLblSubtotal0().setText(notaCredito.getSubtotalCero() + "");
        getLblSubtotal12().setText(notaCredito.getSubtotalDoce() + "");
    }

    private void crearDetalleTabla() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Codigo");
        titulo.add("Valor Uni");
        titulo.add("Cantidad");
        titulo.add("Descripcion");
        titulo.add("Valor Total");

        this.modeloTablaDetalle = new DefaultTableModel(titulo, 0);
        //this.modeloTablaDetallesProductos.isCellEditable
        getTblDetalleNotaCredito().setModel(modeloTablaDetalle);
    }

    private void actualizarDatosTablaDetalle() {
        crearDetalleTabla();
        List<NotaCreditoDetalle> detalles = notaCredito.getDetalles();
        for (NotaCreditoDetalle detalle : detalles) {
            Vector<String> fila = new Vector<String>();
            fila.add(detalle.getProducto().getCodigoPrincipal());
            fila.add(detalle.getProducto().getValorUnitario() + "");
            fila.add(detalle.getCantidad() + "");
            fila.add(detalle.getDescripcion());
            fila.add(detalle.getTotal() + "");
            this.modeloTablaDetalle.addRow(fila);
        }

    }

}
