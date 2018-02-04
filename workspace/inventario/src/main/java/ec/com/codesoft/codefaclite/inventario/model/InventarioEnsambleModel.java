/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.inventario.busqueda.CompraBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.panel.InventarioEnsamblePanel;
import ec.com.codesoft.codefaclite.servidor.entity.Bodega;
import ec.com.codesoft.codefaclite.servidor.entity.Compra;
import ec.com.codesoft.codefaclite.servidor.entity.Kardex;
import ec.com.codesoft.codefaclite.servidor.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidor.service.BodegaService;
import ec.com.codesoft.codefaclite.servidor.service.KardexService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class InventarioEnsambleModel extends InventarioEnsamblePanel{

    private Producto productoEnsamble;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        cargarValoresIniciales();
        agregarListenerBotones();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    private void cargarValoresIniciales() {
        //Cargar las bodegas disponibles
        getCmbBodega().removeAllItems();
        BodegaService servicioBodega = new BodegaService();
        List<Bodega> bodegas = servicioBodega.obtenerTodos();
        for (Bodega bodega : bodegas) {
            getCmbBodega().addItem(bodega);
        }        
    }

    private void agregarListenerBotones() {
        getBtnBuscarEnsamble().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo();
                buscarBusquedaDialogo.setTipoProductoEnum(TipoProductoEnum.EMSAMBLE);
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                productoEnsamble = (Producto) buscarDialogo.getResultado();
                if (productoEnsamble != null) {
                    getTxtEnsamble().setText(productoEnsamble.getNombre());
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
                       errores+=productosProblema.getNombre()+"\n";
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
    
    private List<Producto> verificarEnsamble()
    {
        List<Producto> productosProblemas=new ArrayList<Producto>();
        Integer cantidad = Integer.parseInt(getTxtCantidad().getText());
        String accion = getCmbAccion().getSelectedItem().toString();
        Bodega bodega = (Bodega) getCmbBodega().getSelectedItem();
        
        String[] titulo={"Nombre","Cant.Nece.Ind","Cant.Nece.Tot","Cant.Disponible","Cumple"};
        
        for(ProductoEnsamble componenteProducto: productoEnsamble.getDetallesEnsamble())
        {
            Vector<String> fila=new Vector<String>();
            
            Producto componente=componenteProducto.getComponenteEnsamble();
            Map<String,Object> mapParametros=new HashMap<String,Object>();
            mapParametros.put("producto",componente);
            mapParametros.put("bodega",bodega);
            KardexService servicioKardex=new KardexService();
            List<Kardex> listaKardex= servicioKardex.obtenerPorMap(mapParametros);
            if(listaKardex!=null && listaKardex.size()>0)
            {
                Kardex kardexComponente=listaKardex.get(0);
                fila.add(componente.getNombre());
                fila.add(componenteProducto.getCantidad()+"");
                fila.add(componenteProducto.getCantidad()*cantidad+"");
                fila.add(kardexComponente.getStock()+"");
                boolean disponible=componenteProducto.getCantidad()*cantidad<=kardexComponente.getStock();
                
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
                fila.add(componenteProducto.getCantidad() * cantidad + "");
                fila.add("0");
                fila.add("NO");
                productosProblemas.add(componente);
                
            }
            
        }        
        return productosProblemas;
    }
    
        
}
