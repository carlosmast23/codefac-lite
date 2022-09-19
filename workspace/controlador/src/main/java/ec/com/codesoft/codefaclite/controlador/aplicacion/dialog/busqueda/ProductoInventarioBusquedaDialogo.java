/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ComponenteFiltro;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.FiltroDialogoIf;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ProductoInventarioBusquedaDialogo implements InterfaceModelFind<Kardex> , InterfacesPropertisFindWeb,FiltroDialogoIf  {
    
    private Empresa empresa;
    //private EnumSiNo isManejoInvetario;
    private Bodega bodega;
    
    private Boolean mostrarStockNegativo;
    
    public ProductoInventarioBusquedaDialogo(EnumSiNo isManejoInvetario, Empresa empresa, Bodega bodega,Boolean mostrarStockNegativo) 
    {
        //this.isManejoInvetario = isManejoInvetario;
        this.empresa = empresa;
        this.bodega = bodega;
        this.mostrarStockNegativo=mostrarStockNegativo;
    }

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Codigo", 0.2d));
        titulo.add(new ColumnaDialogo("Codigo Aux", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.5d));
        //titulo.add(new ColumnaDialogo("Lote", 0.3d));
        titulo.add(new ColumnaDialogo("Marca", 0.3d));
        titulo.add(new ColumnaDialogo("Aplicación", 0.3d));
        titulo.add(new ColumnaDialogo("Ubicación", 0.2d));
        titulo.add(new ColumnaDialogo("Pvp", 0.10d));
        titulo.add(new ColumnaDialogo("Pvp+Iva", 0.10d));
        titulo.add(new ColumnaDialogo("IVA", 0.05d));        
        titulo.add(new ColumnaDialogo("Stock", 0.05d));        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        
        Kardex kardex;
        //kardex.getLote().getFechaVencimiento();
        String whereManejaInventario="";
        
        String whereBodega="";
        
        String whereStockNegativo="";
        
        //if(isManejoInvetario!=null)
        //{
        //    whereManejaInventario=" and u.manejarInventario=?98 ";
        //}
        
        String queryFiltroEmpresa=" and u.empresa=?4";
        Boolean datosCompartidosEmpresas=false;
        try {
            datosCompartidosEmpresas=ParametroUtilidades.comparar(empresa,ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA,EnumSiNo.SI);           
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteEstablecimientoBusquedaDialogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (datosCompartidosEmpresas) 
        {
            //Si los datos son compratidos entre empresas entoces no hago ningun filtro
            queryFiltroEmpresa = "";
        }
        
        if(bodega!=null)
        {
            whereBodega=" and k.bodega=?5 ";
        }
        
        
        if(!mostrarStockNegativo)
        {
            whereStockNegativo=" and ( k.stock>0 and k.lote is not null or k.lote is null)  ";
        }
        
        String filtroMarca=" AND ( u.marcaProducto=?97 ) ";
        
        String queryString = "SELECT k FROM Kardex k JOIN k.producto u  WHERE 1=1 "+filtroMarca+" AND k.producto.tipoProductoCodigo<>?6  "+queryFiltroEmpresa+" and (u.estado=?1)"+whereManejaInventario+whereBodega+whereStockNegativo;      
        
        queryString+=" and ( LOWER(u.nombre) like ?2 OR LOWER(u.codigoPersonalizado) like ?2 OR LOWER(u.codigoUPC) like ?2 OR LOWER(u.nombreGenerico) like ?2 ) ORDER BY u.nombre, u.codigoPersonalizado,k.lote";
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,filter);
        
        if (!datosCompartidosEmpresas) 
        {
           queryDialog.agregarParametro(4,empresa);
        }
        
        if(bodega!=null)
        {
           queryDialog.agregarParametro(5,bodega);
        }
        
        //Solo mostrar los productos que son distintos de empaque
        queryDialog.agregarParametro(6, TipoProductoEnum.EMPAQUE.getLetra());
        
        /*if(isManejoInvetario!=null)
        {
            queryDialog.agregarParametro(98,isManejoInvetario.getLetra());
        }*/
       
        return queryDialog;
    }
    
    
    
    @Override
    public void agregarObjeto(Kardex kardex, Vector vector) {
        //KardexServiceIf servicio = ServiceFactory.getFactory().getKardexServiceIf();
        //Kardex kardex = servicio.buscarKardexPorProductoyBodega(this.bodega, producto);
        Producto producto=kardex.getProducto();
        vector.add(producto.getCodigoPersonalizado());
        vector.add(producto.getCodigoUPC());
        vector.add(producto.getNombre());
        //vector.add((kardex.getLote()!=null)?kardex.getLote().getCodigo():"");
        vector.add((producto.getMarcaProducto()!=null)?producto.getMarcaProducto().getNombre():"");
        vector.add((producto.getAplicacionProducto()!=null)?producto.getAplicacionProducto():"");
        vector.add((producto.getUbicacion()!=null)?producto.getUbicacion():"");
        vector.add(producto.getValorUnitario().setScale(3,RoundingMode.HALF_UP));
        vector.add(producto.getValorUnitarioConIva().setScale(3,RoundingMode.HALF_UP));
        vector.add((producto.getCatalogoProducto()!=null && producto.getCatalogoProducto().getIva()!=null)?producto.getCatalogoProducto().getIva().getTarifa().toString():"SN");        
        vector.add((kardex.getStock()!=null)?kardex.getStock().setScale(2,RoundingMode.HALF_UP):"");
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }

    @Override
    public Vector<ComponenteFiltro> getfiltrosList() {
        try {
            
            Vector<ComponenteFiltro> filtroList=new Vector<ComponenteFiltro>();            
            
            List<MarcaProducto> marcaList=ServiceFactory.getFactory().getMarcaProductoServiceIf().obtenerActivosPorEmpresa(empresa);
            ComponenteFiltro componenteFiltro=new ComponenteFiltro(ComponenteFiltro.TipoFiltroEnum.COMBO_BOX,"marca: ",97,marcaList);
            
            
            
            filtroList.add(componenteFiltro);
            
            return filtroList;
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProductoBusquedaDialogo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ProductoBusquedaDialogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
