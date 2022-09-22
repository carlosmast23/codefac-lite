/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ComponenteFiltro;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TallerMecanicoInventarioBusquedaDialogo extends ProductoInventarioBusquedaDialogo{
    
    
    
    public TallerMecanicoInventarioBusquedaDialogo(EnumSiNo isManejoInvetario, Empresa empresa, Bodega bodega) {
        super(isManejoInvetario, empresa, bodega, true);
    }
    
        @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Item", 0.2d));
        titulo.add(new ColumnaDialogo("Descripción", 0.4d));
        titulo.add(new ColumnaDialogo("Aplicación", 0.4d));
        titulo.add(new ColumnaDialogo("Marca", 0.2d));            
        titulo.add(new ColumnaDialogo("Ubicación", 0.3d));       
        titulo.add(new ColumnaDialogo("Stock", 0.05d));        
        titulo.add(new ColumnaDialogo("Reserva", 0.05d));        
        titulo.add(new ColumnaDialogo("Disp", 0.05d));        
        return titulo;
    }
    
        @Override
    public void agregarObjeto(Kardex kardex, Vector vector) {
        Producto producto=kardex.getProducto();
        vector.add(producto.getCodigoPersonalizado());
        vector.add(producto.getNombre());
        vector.add(producto.getAplicacionProducto());
        vector.add(producto.getMarcaProducto());
        vector.add((producto.getUbicacion()!=null)?producto.getUbicacion():"");
        vector.add(kardex.getStock().setScale(0,RoundingMode.HALF_UP));
        vector.add("0");
        vector.add(kardex.getStock().setScale(0,RoundingMode.HALF_UP));
    }

    @Override
    public Vector<ComponenteFiltro> getfiltrosList() {
        try {
            Vector<ComponenteFiltro> filtroList=new Vector<ComponenteFiltro>();
            
            ComponenteFiltro componenteFiltro=new ComponenteFiltro(ComponenteFiltro.TipoFiltroEnum.TEXTO,"Código: ",96);
            filtroList.add(componenteFiltro);
            
            ComponenteFiltro componenteFiltroAplicacion=new ComponenteFiltro(ComponenteFiltro.TipoFiltroEnum.TEXTO,"Aplicación: ",95);
            filtroList.add(componenteFiltroAplicacion);
            
            
            List<SegmentoProducto> segmentoList=ServiceFactory.getFactory().getSegmentoProductoServiceIf().obtenerActivosPorEmpresa(empresa);
            ComponenteFiltro componenteFiltroSegmento=new ComponenteFiltro(ComponenteFiltro.TipoFiltroEnum.COMBO_BOX,"Segmento: ",94,segmentoList);
            filtroList.add(componenteFiltroSegmento);
            
            List<TipoStockEnum> tipoStockList=UtilidadesLista.arrayToList(TipoStockEnum.values());
            ComponenteFiltro tipoStockSegmento=new ComponenteFiltro(ComponenteFiltro.TipoFiltroEnum.COMBO_BOX,"Tipo Stock: ",PARAMETRO_FILTRO_STOCK,tipoStockList);
            filtroList.add(tipoStockSegmento);
            
            
            
            //filtroList.add(e);
            return filtroList;
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TallerMecanicoInventarioBusquedaDialogo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TallerMecanicoInventarioBusquedaDialogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String getFiltroPorCodigo() {
        //Producto p;p.getCodigoPersonalizado()
        
        return " AND LOWER(u.codigoPersonalizado) like ?96 ";
    }
    
    @Override
    public String getFiltroPorAplicacion()
    {
        return " AND LOWER(u.aplicacionProducto) like LOWER(?95) ";
    }
    
    @Override
    public String getFiltroPorSegmento()
    {
        //Producto p;
        //p.getSegmentoProducto().;
        return " AND ( u.segmentoProducto=?94 ) ";
    }
    
    

    @Override
    public String getFiltroPorMarca() {
        return "";
    }
    
    public enum TipoStockEnum
    {
        TODOS("Todos"),
        CON_STOCK("Con Stock"),
        SIN_STOCK("Sin Stock");
        
        private String nombre;

        private TipoStockEnum(String nombre) {
            this.nombre = nombre;
        }        

        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
        
        
        
    }
    
}
