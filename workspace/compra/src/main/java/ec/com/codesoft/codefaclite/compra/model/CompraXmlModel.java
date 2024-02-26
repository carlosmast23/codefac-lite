/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.compra.panel.CompraXmlPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ITableBindingAddData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CompraXmlModel extends CompraXmlPanel implements DialogInterfacePanel<Compra>, InterfazPostConstructPanel {

    private static final Integer COLUMNA_OBJETO = 0;
    private Compra compra;
    private Producto productoSeleccionado;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        crearModeloTabla();
        listenerBotones();
        addListenerPopUps();
    }
    
    private void addListenerPopUps()
    {
        JPopupMenu jPopupMenu = new JPopupMenu();
        
        //MENU PARA ENLAZAR DE UN PRODUCTO EXISTENTE
        JMenuItem jMenuItemEnlazarProveedor = new JMenuItem("Enlazar producto");
        jMenuItemEnlazarProveedor.addActionListener(listenerEnlazarProveedor);
        jPopupMenu.add(jMenuItemEnlazarProveedor);
        
        //MENU PARA CREAR UN PRODUCTO NUEVO        
        JMenuItem jMenuItemNuevoProveedor = new JMenuItem("Crear Producto");
        jMenuItemNuevoProveedor.addActionListener(listenerCrearProducto);
        jPopupMenu.add(jMenuItemNuevoProveedor);
        
        //MENU PARA CREAR O AGREGAR UN NUEVO LOTE
        JMenuItem jMenuItemNuevoLote = new JMenuItem("Crear Lote");
        jMenuItemNuevoLote.addActionListener(listenerCrearLote);
        jPopupMenu.add(jMenuItemNuevoLote);
        //jMenuItemNuevoLote.addActionListener();
                
        
        getTblDetalles().setComponentPopupMenu(jPopupMenu);
        
        
        
    }
    
    private ActionListener listenerCrearLote=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            int fila=getTblDetalles().getSelectedRow();
            if(fila<0)
            {
                return ;
            }
            
            CompraDetalle compraDetalle = (CompraDetalle) getTblDetalles().getValueAt(fila, COLUMNA_OBJETO);
            
            Object[] paramPostConstruct = new Object[1];
            Producto productoCrearLote= compraDetalle.getProductoProveedor().getProducto();
            paramPostConstruct[0] = productoCrearLote;
            
            ObserverUpdateInterface observerCreate=new ObserverUpdateInterface<Lote>() 
            {
                @Override
                public void updateInterface(Lote entity) {
                    if(entity!=null)
                    {
                        compraDetalle.setLote(entity);
                        actualizarBindingCompontValues();
                        //enlazarProductoTabla(entity, fila);
                    }

                }
            };
            panelPadre.crearDialogoCodefac(observerCreate, VentanaEnum.LOTE, false,paramPostConstruct,formularioActual);        
        }
    };
            
    
    private ActionListener listenerEnlazarProveedor=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            int[] indices=getTblDetalles().getSelectedRows();
            if(indices.length<=0)
            {
                return ;
            }
            
            Producto productoSeleccionado= enlazarProveedor();
            enlazarProductoTabla(productoSeleccionado, indices);
            
        }
    };
    
    private void enlazarProductoTabla(Producto productoSeleccionado,int[] fila)
    {
        for (int i = 0; i < fila.length; i++) 
        {
            enlazarProductoTabla(productoSeleccionado, fila[i]);
        }
    }
    
    private void enlazarProductoTabla(Producto productoSeleccionado,int fila)
    {
        if (productoSeleccionado != null) {
            try {
                //Enlazarel producto con la columna de la compra
                CompraDetalle compraDetalle = (CompraDetalle) getTblDetalles().getValueAt(fila, COLUMNA_OBJETO);
                //TODO: NO debe construir siempre, primero toca verificar si existe y luego edtar el codigo de enlazar con el proveedor
                List<ProductoProveedor> productoProveedorList = ServiceFactory.getFactory().getProductoProveedorServiceIf().buscarProductoProveedorActivo(productoSeleccionado, compra.getProveedor());

                ProductoProveedor productoProveedor = null;
                //TODO: La segunda verificacion es porque 
                if (productoProveedorList.size() > 0 && productoProveedorList.get(0).getProducto().getIdProducto()!=null ) {
                    //Si existe el enlace solo consulto el smimo para editar el CODIGO_PROVEEDOR
                    productoProveedor = productoProveedorList.get(0);
                } else {
                    productoProveedor = ServiceFactory.getFactory().getProductoProveedorServiceIf().construirSinTransaccion(productoSeleccionado, compra.getProveedor());
                }

                productoProveedor.setCodigoProveedor(compraDetalle.getCodigoProveedor());
                compraDetalle.setProductoProveedor(productoProveedor);
                actualizarBindingCompontValues();
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CompraXmlModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(CompraXmlModel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    private ActionListener listenerCrearProducto=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int fila=getTblDetalles().getSelectedRow();
            if(fila<0)
            {
                return ;
            }
            
            CompraDetalle compraDetalle = (CompraDetalle) getTblDetalles().getValueAt(fila, COLUMNA_OBJETO);
            
            
            ObserverUpdateInterface observer = new ObserverUpdateInterface<Producto>() {
                    @Override
                    public void updateInterface(Producto entity) {
                        if (entity != null) {
                            System.out.println("Agregado producto nuevo que fue creado");
                            enlazarProductoTabla(entity, fila);
                            //actualizarBindingCompontValues();
                        }
                    }
                };

                Object[] parametros={
                    null,
                    compraDetalle.getCodigoProveedor(),
                    compraDetalle.getDescripcion(),
                    compraDetalle.getPrecioUnitario(),
                    compraDetalle.getProductoProveedor().getProducto().getCatalogoProducto().getIva(),
                };
                
                panelPadre.crearDialogoCodefac(observer, VentanaEnum.PRODUCTO, false,parametros,formularioActual);
            
        }
    };
    
    
    public Producto enlazarProveedor() {
        ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa(),false,true);
        BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
        buscarDialogo.setVisible(true);
        Producto productoTmp = (Producto) buscarDialogo.getResultado();
        //this.productoSeleccionado = productoTmp;
        return productoTmp;
    }
    

    private void listenerBotones() {
        /*getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Compra:" + compra.getRazonSocial());
                actualizarBindingCompontValues();
            }
        });*/
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Compra getResult() throws ExcepcionCodefacLite {
        
        validacionGrabar();
        return compra;
    }
    
    private void validacionGrabar() throws ExcepcionCodefacLite
    {
        for (CompraDetalle detalle : compra.getDetalles()) {            
           
            if(detalle.getProductoProveedor()==null || detalle.getProductoProveedor().getProveedor()==null)
            {
                throw new ExcepcionCodefacLite("El producto con código: "+detalle.getCodigoProveedor()+" no tiene un ENLACE con un PRODUCTO INTERNO ");
            }
            
            //Enlazar el codigo del producto con el campo codigo del detalle de la compra
            //TODO: Este paso es necesario porque al momento der grabar la compra verifica que tenga este dato
            detalle.setCodigoPrincipal(detalle.getProductoProveedor().getProducto().getCodigoPersonalizado());
        }
    }

    public void crearModeloTabla() {
        String titulo[] = new String[]{"Objeto", "Cod Sistema","Nombre Sistema", "Cod Xml", "Descripción compra","Iva","Ice","Cantidad","Precio","Desc","Lote"};
        DefaultTableModel modelo = UtilidadesTablas.crearModeloTabla(titulo, new Class[]{Object.class, String.class, String.class,String.class, String.class, String.class, String.class,String.class,String.class,String.class,String.class});
        getTblDetalles().setModel(modelo);
        UtilidadesTablas.definirTamanioColumnas(getTblDetalles(), new Integer[]{0});
    }

    public ITableBindingAddData getTableBindingAddData() {

        return new ITableBindingAddData<CompraDetalle>() {
            @Override
            public Object[] addData(CompraDetalle value) {

                String codigoSistema="";
                String nombreProductoSistema="";
                String codigoProveedor="";
                String ivaPorcentaje=value.getIvaPorcentaje()+"";
                if(value.getProductoProveedor()!=null)
                {
                    codigoSistema=value.getProductoProveedor().getProducto().getCodigoPersonalizado();
                    nombreProductoSistema=value.getProductoProveedor().getProducto().getNombre();
                    
                }
                
                String codigoLote="";
                if(value.getLote()!=null)
                {
                    codigoLote=value.getLote().getCodigo(); 
                }
                

                return new Object[]{
                    value,
                    codigoSistema,
                    nombreProductoSistema,
                    value.getCodigoProveedor(),
                    value.getDescripcion(),
                    ivaPorcentaje,
                    value.getValorIce(),
                    value.getCantidad(),
                    value.getPrecioUnitario() + "",
                    value.getDescuento()+"",
                    codigoLote
                };
            }

            @Override
            public void setData(CompraDetalle objetoOriginal, Object objetoModificado, Integer columnaModificada) {

            }

        };

    }

    @Deprecated
    public void listenerBtnBuscarProveedorDialogo() {
        ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa(),false,true);
        BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
        buscarDialogo.setVisible(true);
        Producto productoTmp = (Producto) buscarDialogo.getResultado();
        this.productoSeleccionado = productoTmp;
    }

    public void listenerBtnEditarDetalle() {
        Integer filaSeleccionada = getTblDetalles().getSelectedRow();
        if (filaSeleccionada > 0) {
            if (this.productoSeleccionado != null) {
                //CompraDetalle compraDetalle = (CompraDetalle) getTblDetalles().getValueAt(filaSeleccionada, COLUMNA_OBJETO);
                //ProductoProveedor productoProveedor= buscarProductoProveedor(productoSeleccionado, compra.getProveedor());
                //compraDetalle.setProductoProveedor(productoProveedor);                
            }
        } else {
            //MensajeCodefacSistema
        }

    }
    
    
    @Override
    public void postConstructorExterno(Object[] parametros) {
        Compra compraXml = (Compra) parametros[0];
        this.compra = compraXml;
        cargarProductosPorDefecto(compra);
        //getTxtProveedor().setText(this.compra.getRazonSocial());
        System.out.println("Compra: " + compraXml);
        actualizarBindingCompontValues();
    }
    
    //Este proceso deberia estar en el servidor al momento de constuir la compra y no en la vista
    @Deprecated
    private void cargarProductosPorDefecto(Compra compraXml)
    {
        for(CompraDetalle compraDetalle : compraXml.getDetalles())
        {            
            //Si no tiene un enlace entonces le busco directamente el producto
            if(compraDetalle.getProductoProveedor()==null || compraDetalle.getProductoProveedor().getId()==null )
            {
                try {
                    //Verificar si existe el PRODUCTO POR EL CODIGO
                    Producto producto= ServiceFactory.getFactory().getProductoServiceIf().buscarProductoActivoPorCodigo(compraDetalle.getCodigoProveedor(), session.getEmpresa());
                    if(producto!=null)
                    {
                        producto=ServiceFactory.getFactory().getProductoServiceIf().buscarProductoDefectoCompras(producto);
                        ProductoProveedor productoProveedor= ServiceFactory.getFactory().getProductoProveedorServiceIf().construirSinTransaccion(producto, compraXml.getProveedor());
                        compraDetalle.setProductoProveedor(productoProveedor);
                    }
                    else
                    {
                        //Verificar si existe el PRODUCTO POR EL NOMBRE
                        producto= ServiceFactory.getFactory().getProductoServiceIf().buscarPorNombreyEstado(compraDetalle.getDescripcion(), GeneralEnumEstado.ACTIVO, session.getEmpresa());
                        if(producto!=null)
                        {
                            producto=ServiceFactory.getFactory().getProductoServiceIf().buscarProductoDefectoCompras(producto);
                            ProductoProveedor productoProveedor= ServiceFactory.getFactory().getProductoProveedorServiceIf().construirSinTransaccion(producto, compraXml.getProveedor());
                            compraDetalle.setProductoProveedor(productoProveedor);
                        }
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(CompraXmlModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(CompraXmlModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(Producto productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    /**
     * ========================================================================
     * METODOS GET AND SET
     * ========================================================================
     */
}
