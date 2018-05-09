/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.compra.panel.RetencionesPendientePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.ejemplo.utilidades.tabla.UtilidadesTablas;
import java.awt.event.MouseAdapter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.eclipse.jdt.internal.compiler.lookup.MostSpecificExceptionMethodBinding;

/**
 *
 * @author Carlos
 */
public class RetencionesPendienteModel extends RetencionesPendientePanel{
    
    private DefaultTableModel modeloTablaComprasPendientes;
  
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        cargarComprasPendientes();
        addListener();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        return "Retenciones Pendientes";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void cargarComprasPendientes() 
    {
        try {
            CompraServiceIf service=ServiceFactory.getFactory().getCompraServiceIf();
            //todo: Falta seleccionar que solo se carguen las compras sin enviar retenciones
            List<Compra> compras= service.obtenerTodos();
            DefaultTableModel datos= UtilidadesTablas.crearModeloTabla(new String[]{"obj","Preimpreso","Ruc","Proveedor"},new Class[]{Compra.class,String.class,String.class,String.class});
            for (Compra compra : compras) {
                Vector<Object> fila = new Vector<>();
                fila.add(compra);
                fila.add(compra.getPreimpreso());
                fila.add(compra.getProveedor().getIdentificacion());
                fila.add(compra.getProveedor().getNombresCompletos());
                datos.addRow(fila);
            }
            getTblComprasPendientes().setModel(datos);
            UtilidadesTablas.ocultarColumna(getTblComprasPendientes(), 0);
        } catch (RemoteException ex) {
            Logger.getLogger(RetencionesPendienteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addListener()
    {
        getTblComprasPendientes().addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) 
            {
                int filaComprasPendientes = getTblComprasPendientes().getSelectedRow();
                Compra compra = (Compra) getTblComprasPendientes().getValueAt(filaComprasPendientes,0);
                List<CompraDetalle> compraDetalles = compra.getDetalles();
                DefaultTableModel datos= UtilidadesTablas.crearModeloTabla( new String[]{"Obj","Nombre","Retencia Iva","Retención Renta"},
                                                                            new Class[]{CompraDetalle.class,String.class,String.class,String.class});
                
                BigDecimal totalRetencionIva = new BigDecimal(BigInteger.ZERO);
                BigDecimal totalRetencionRenta = new BigDecimal(BigInteger.ZERO);
                BigDecimal totalRetenciones = new BigDecimal(BigInteger.ZERO);
                
                for (CompraDetalle compraDetalle : compraDetalles) 
                {
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
                totalRetencionIva=totalRetencionIva.setScale(2,BigDecimal.ROUND_HALF_UP);
                totalRetencionRenta= totalRetencionRenta.setScale(2,BigDecimal.ROUND_HALF_UP);
                totalRetenciones=totalRetenciones.setScale(2,BigDecimal.ROUND_HALF_UP);
                
                //Mostrar valores de retencion en pantall
                getLabelTotalRetencionIva().setText(totalRetencionIva+"");
                getLabelTotalRetencionRenta().setText(totalRetencionRenta+"");
                getLabelTotalRetenciones().setText(totalRetenciones+"");
                
                //Cargar detalles de cada compra y valores de retencion en las tablas
                getTblDetalleRetenciones().setModel(datos);
                
                //Ocultar la primera columna de la tabla
                UtilidadesTablas.ocultarColumna(getTblDetalleRetenciones(), 0);
                
            }
        });
    }
    
        
}
