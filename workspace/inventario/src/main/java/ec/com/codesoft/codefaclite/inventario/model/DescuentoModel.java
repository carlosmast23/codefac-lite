/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.inventario.DescuentoControlador;
import ec.com.codesoft.codefaclite.controlador.vista.inventario.LoteControlador;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ITableBindingAddData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.inventario.panel.DescuentoPanel;
import ec.com.codesoft.codefaclite.inventario.panel.LotePanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Descuento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoCondicionPrecio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoProductoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RutaDetalle;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class DescuentoModel extends DescuentoPanel implements DialogInterfacePanel<Descuento>,InterfazPostConstructPanel,ControladorVistaIf,DescuentoControlador.ISwing{
    
    private DescuentoControlador controlador;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        controlador=new DescuentoControlador(DialogoCodefac.intefaceMensaje, session, this, ModelControladorAbstract.TipoVista.ESCRITORIO);
        crearModeloTablaProductos();
        crearModeloTablaCondicionPrecios();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
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

    public DescuentoControlador getControlador() {
        return controlador;
    }

    public void setControlador(DescuentoControlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }

    @Override
    public void limpiarPantalla() {
        limpiar();
    }

    @Override
    public Descuento getResult() throws ExcepcionCodefacLite {
        try {
            controlador.grabar();
            return controlador.getDescuento();
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(DescuentoModel.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (RemoteException ex) {
            Logger.getLogger(DescuentoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void postConstructorExterno(Object[] parametros) {
        
        /*if(parametros[0]!=null)
        {
            Producto productoTmp=(Producto) parametros[0];
            controlador.getLote().setProducto(productoTmp);            
        }
        actualizarBindingCompontValues();*/
    }
    
    public void crearModeloTablaProductos()
    {   
        String titulo[]=new String[]{"Objeto","Código","Nombre","Categoria"};
        DefaultTableModel modelo=UtilidadesTablas.crearModeloTabla(titulo, new Class[]{Object.class,Object.class,Object.class,Object.class});
        getTblProductos().setModel(modelo);
        UtilidadesTablas.definirTamanioColumnas(getTblProductos(),new Integer[]{0});
    }
    
    public ITableBindingAddData getTableBindingAddData()
    {
        return new ITableBindingAddData<DescuentoProductoDetalle>() {
            @Override
            public Object[] addData(DescuentoProductoDetalle value) {
                
                String categoria="";
                if(value.getProducto().getCatalogoProducto().getCategoriaProducto()!=null)
                {
                    categoria=value.getProducto().getCatalogoProducto().getCategoriaProducto().getNombre();
                }
                
                if(value.getProducto()==null)
                {
                    return new Object[]{value,null,null,null};
                }
                
                return new Object[]{
                    value,
                    value.getProducto().getCodigoPersonalizado(),
                    value.getProducto().getNombre(),
                    categoria,
                };
            }

            @Override
            public void setData(DescuentoProductoDetalle objetoOriginal, Object objetoModificado, Integer columnaModificada) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    };
    
    
    public void crearModeloTablaCondicionPrecios()
    {   
        String titulo[]=new String[]{"Objeto","#Pvp","% Desc"};
        DefaultTableModel modelo=UtilidadesTablas.crearModeloTabla(titulo, new Class[]{Object.class,Object.class,Object.class});
        getTblCondicionPrecios().setModel(modelo);
        UtilidadesTablas.definirTamanioColumnas(getTblCondicionPrecios(),new Integer[]{0});
    }
    
    public ITableBindingAddData getTableBindingAddDataCondicion()
    {
        return new ITableBindingAddData<DescuentoCondicionPrecio>() {
            @Override
            public Object[] addData(DescuentoCondicionPrecio value) {
                
                Integer numeroPrecio=0;
                if(value.getNumeroPrecio()!=null)
                {
                    numeroPrecio=value.getNumeroPrecio();
                }
                
                BigDecimal porceBigDecimal=BigDecimal.ZERO;
                if(value.getPorcentajeDescuento()!=null)
                {
                    porceBigDecimal=value.getPorcentajeDescuento();
                }
                
                
                
                return new Object[]{
                    value,
                    numeroPrecio,
                    porceBigDecimal,
                };
            }

            @Override
            public void setData(DescuentoCondicionPrecio objetoOriginal, Object objetoModificado, Integer columnaModificada) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    };
    
}
