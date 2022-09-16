/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.inventario.busqueda.CompraBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.panel.InventarioEnsamblePanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;
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
 * @author Carlos
 */
public class InventarioEnsambleModel extends InventarioEnsamblePanel{

    private Producto productoEnsamble;
    private Kardex kardexEnsamble;

    public InventarioEnsambleModel() {
        super.mapDatosIngresadosDefault.put(getTxtCantidad(),"0");
    }
    
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        cargarValoresIniciales();
        agregarListenerBotones();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        validarDatos();
        
        List<Producto> productosProblemas=verificarEnsamble();
        if (productosProblemas.size() > 0) {
            String errores = "\n";
            for (Producto productosProblema : productosProblemas) {
                errores += " - " + productosProblema.getNombre() + "\n";
            }
            DialogoCodefac.mensaje("Advertencia", "No existe suficiente stock de los siguientes productos:" + errores + "\n Agregue stock para continuar", DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("No existe sufiente stock en los productos");
        } 
        else 
        {
            try {
                KardexServiceIf kardexService=ServiceFactory.getFactory().getKardexServiceIf();
                Bodega bodegaDestino=(Bodega) getCmbBodegaDestino().getSelectedItem();
                Bodega bodegaOrigen=(Bodega) getCmbBodegaOrigen().getSelectedItem();
                String accion=getCmbAccion().getSelectedItem().toString();
                BigDecimal cantidad=new BigDecimal(getTxtCantidad().getText());
                //List<Kardex> kardexList=getKardexModificados();
                Boolean ingreso=(accion.equals(InventarioEnsamblePanel.OPCION_AGREGAR)?true:false);
                
                //Por el momento cuando mando a grabar en el servidor siempre valida si existe el stock para construir
                kardexService.ingresoEgresoInventarioEnsamble(bodegaOrigen,bodegaDestino, productoEnsamble, cantidad, ProductoEnsamble.EnsambleAccionEnum.AGREGAR,true);
                //kardexService.IngresoEgresoInventarioEnsamble(bodega, productoEnsamble, cantidad,, ingreso);
                DialogoCodefac.mensaje("Correcto", "Sus datos fueron grabados correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            //} catch (ServicioCodefacException ex) {
            //    DialogoCodefac.mensaje("Error", "Existe un error en los datos", DialogoCodefac.MENSAJE_INCORRECTO);
            //    Logger.getLogger(InventarioEnsambleModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(InventarioEnsambleModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje("Error", "Existe un error en los datos", DialogoCodefac.MENSAJE_INCORRECTO);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(InventarioEnsambleModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje("Error",ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
                throw new ExcepcionCodefacLite(ex.getMessage());
            }
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, cFhoose Tools | Templates.
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
        limpiarVariables();
    }

//    @Override
    public String getNombre() {
        return "Inventario Ensamble";
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

    private void cargarValoresIniciales() {
        try {
            //Cargar las bodegas disponibles
            getCmbBodegaDestino().removeAllItems();
            getCmbBodegaOrigen().removeAllItems();
            BodegaServiceIf servicioBodega = ServiceFactory.getFactory().getBodegaServiceIf();
            List<Bodega> bodegas = servicioBodega.obtenerActivosPorEmpresa(session.getEmpresa());
            for (Bodega bodega : bodegas) {
                getCmbBodegaDestino().addItem(bodega);   
                getCmbBodegaOrigen().addItem(bodega);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(InventarioEnsambleModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(InventarioEnsambleModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void agregarListenerBotones() {
        getBtnBuscarEnsamble().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa());
                buscarBusquedaDialogo.setTipoProductoEnum(TipoProductoEnum.EMSAMBLE);
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                productoEnsamble = (Producto) buscarDialogo.getResultado();
                if (productoEnsamble != null) {
                    try {
                        getTxtEnsamble().setText(productoEnsamble.getNombre());
                        //Buscar el Kardex o crear un Kardex nuevo si no existes
                        KardexServiceIf kardexService = ServiceFactory.getFactory().getKardexServiceIf();
                        Bodega bodega = (Bodega) getCmbBodegaOrigen().getSelectedItem();
                        Kardex kardex = kardexService.buscarKardexPorProductoyBodegayLote(bodega, productoEnsamble,null);
                        if(kardex!=null)
                        {
                            getLblStockActual().setText(kardex.getStock()+"");
                        }
                        else
                        {
                            getLblStockActual().setText("0");
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(InventarioEnsambleModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        getBtnVerificar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               List<Producto> productosProblemas=verificarEnsamble();
               if(productosProblemas.size()>0)
               {
                   String errores="\n";
                   for (Producto productosProblema : productosProblemas) {
                       errores+=" - "+productosProblema.getNombre()+"\n";
                   }
                   
                   DialogoCodefac.mensaje("Advertencia","No existe suficiente stock de los siguientes productos:"+errores+"\n Agregue stock para continuar", DialogoCodefac.MENSAJE_INCORRECTO);
                   
               }
               else
               {
                   DialogoCodefac.mensaje("Correcto","El stock de los componentes si esta disponble ",DialogoCodefac.MENSAJE_CORRECTO);
               }
                
            }
        });
    }
    
    /**
     * Obtiene los valores modificos del stock y la reserva para grabar en el Kardex
     * @return 
     */
    /*private List<Kardex> getKardexModificados()
    {
        Integer cantidadEnsamble=Integer.parseInt(getTxtCantidad().getText());
        
        Bodega bodega = (Bodega) getCmbBodega().getSelectedItem();
        String accion = getCmbAccion().getSelectedItem().toString();
        List<Kardex> kardeList=new ArrayList<Kardex>();
        
        for(ProductoEnsamble componenteProducto: productoEnsamble.getDetallesEnsamble())
        {
            try {
                Vector<String> fila=new Vector<String>();
                Integer cantidadProducto=componenteProducto.getCantidad();
                
                Producto componente=componenteProducto.getComponenteEnsamble();
                //Map<String,Object> mapParametros=new HashMap<String,Object>();
                //mapParametros.put("producto",componente);
                //mapParametros.put("bodega",bodega);
                KardexServiceIf servicioKardex=ServiceFactory.getFactory().getKardexServiceIf();
                Kardex kardexComponente= servicioKardex.buscarKardexPorProductoyBodega(bodega,componente);
                if(kardexComponente!=null)
                {
                    Integer cantidadTotal=cantidadEnsamble*cantidadProducto;
                    //Kardex kardexComponente=listaKardex.get(0);
                    //Este paso lo hago porque cuando seteo un valor a una entidad cuando esta asociado automaticamente se refleja en la base de datos
                    //ServiceAbstract.desasociarEntidadRecursivo(kardexComponente);
                    
                    if(accion.equals(InventarioEnsamblePanel.OPCION_AGREGAR))
                    {
                        kardexComponente.setReserva(kardexComponente.getReserva()+cantidadTotal);
                        kardexComponente.setStock(kardexComponente.getStock()-cantidadTotal);
                    }
                    else
                    {
                        kardexComponente.setReserva(kardexComponente.getReserva() - cantidadTotal);
                        kardexComponente.setStock(kardexComponente.getStock() + cantidadTotal);
                    }
                    
                    //Agregar el detalle de kardex
                    KardexDetalle kardexDetalle=new KardexDetalle();
                    kardexDetalle.setCantidad(cantidadTotal);
                    
                    if(accion.equals(InventarioEnsamblePanel.OPCION_AGREGAR))
                        kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_EGRESO.getCodigo());
                    else
                        kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_INGRESO.getCodigo());
                    
                    kardexDetalle.setPrecioTotal(new BigDecimal(cantidadTotal).multiply(kardexComponente.getPrecioUltimo()));
                    kardexDetalle.setPrecioUnitario(kardexComponente.getPrecioUltimo());
                    kardexDetalle.setReferenciaDocumentoId(null);
                    
                    kardexComponente.addDetalleKardex(kardexDetalle);
                    
                    kardeList.add(kardexComponente);
                    
                }
            } catch (RemoteException ex) {
                Logger.getLogger(InventarioEnsambleModel.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        
        return kardeList;
        
    }*/
    
    /**
     * TODO: Ver si esta forma de validar puedo unir con el metodo de KardexService que se llama validarEnsambleComponentes donde esta un metodo similar
     * para comprobar la disponibilidad de los componentes
     * @return 
     */
    private List<Producto> verificarEnsamble()
    {
        List<Producto> productosProblemas=new ArrayList<Producto>();
        BigDecimal cantidad = new BigDecimal(getTxtCantidad().getText());
        String accion = getCmbAccion().getSelectedItem().toString();
        Bodega bodega = (Bodega) getCmbBodegaOrigen().getSelectedItem();
        
        String[] tituloIngreso={"Nombre","Cant.Nece.Indiv","Cant.Nece.Tot","Cant.Disponible","Cumple"};
        String[] tituloEgreso={"Nombre","Cantidad.Indiv","Cantidad.Tot","Stock Actual","Cumple"};
        
        DefaultTableModel tableModel=new DefaultTableModel(tituloIngreso,0);
       
        //Cundo es un egreso cambio el constructor
        if(accion.equals( InventarioEnsamblePanel.OPCION_QUITAR))
            tableModel=new DefaultTableModel(tituloEgreso,0);
            
        
        for(ProductoEnsamble componenteProducto: productoEnsamble.getDetallesEnsamble())
        {
            try {
                Vector<String> fila=new Vector<String>();
                
                Producto componente=componenteProducto.getComponenteEnsamble();
                /*Map<String,Object> mapParametros=new HashMap<String,Object>();
                mapParametros.put("producto",componente);
                mapParametros.put("bodega",bodega);*/
                KardexServiceIf servicioKardex=ServiceFactory.getFactory().getKardexServiceIf();
                Kardex kardexResultado= servicioKardex.buscarKardexPorProductoyBodegayLote(bodega,componente,null);
                if(kardexResultado!=null)
                {
                    Kardex kardexComponente=kardexResultado;
                    fila.add(componente.getNombre());
                    fila.add(componenteProducto.getCantidad()+"");
                    fila.add(componenteProducto.getCantidad().multiply(cantidad)+"");
                    fila.add(kardexComponente.getStock()+"");
                    //boolean disponible=componenteProducto.getCantidad().multiply(cantidad)<=kardexComponente.getStock().intValue();
                    
                    boolean disponible=componenteProducto.getCantidad().multiply(cantidad).compareTo(kardexComponente.getStock())<=0;
                    
                    if(disponible)
                    {
                        fila.add("SI");
                    }
                    else
                    {
                        fila.add("NO");
                        productosProblemas.add(componente);
                    }
                }
                else
                {
                    //Si no existe kardex no existe disponibilidad
                    //TODO: toca revisar si el producto es un servicio entonces esto no aplica
                    fila.add(componente.getNombre());
                    fila.add(componenteProducto.getCantidad() + "");
                    fila.add(componenteProducto.getCantidad().multiply(cantidad) + "");
                    fila.add("0");
                    fila.add("NO");
                    productosProblemas.add(componente);
                }
                tableModel.addRow(fila);
            } catch (RemoteException ex) {
                Logger.getLogger(InventarioEnsambleModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        getTblDatos().setModel(tableModel);
        return productosProblemas;
    }

    private void limpiarVariables() {
        getTxtEnsamble().setText("");
        getLblStockActual().setText("0");
        getTxtCantidad().setText("0");
        getTblDatos().setModel(new DefaultTableModel());
        
        productoEnsamble=null;
        
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void validarDatos() throws ExcepcionCodefacLite {
        
        if(getTxtCantidad().getText().isEmpty())
        {
            DialogoCodefac.mensaje("Error Validación","La cantidad no puede ser positiva",DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("cantidad vacia");
        }
        else
        {
            Integer numeroIngresado=Integer.parseInt(getTxtCantidad().getText());
            if(numeroIngresado<=0)
            {
                DialogoCodefac.mensaje("Error Validación","La cantidad no puede ser menor igual que cero",DialogoCodefac.MENSAJE_ADVERTENCIA);
                throw new ExcepcionCodefacLite("número incorrecto");
            }
        }
        
        if(productoEnsamble==null)
        {
            String mensaje="No existe un producto ingresado para grabar";
            DialogoCodefac.mensaje("Error Validación",mensaje,DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("");
        }
    }
    
        
}
