/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;


import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.panel.TallerMecanicoDialogoInfo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ComponenteFiltro;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogPanelAuxIf;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogoConfigAuxIf;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.FiltroDialogoIf;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoQueryEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoStockEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Carlos
 */
public class ProductoInventarioBusquedaDialogo implements InterfaceModelFind<Kardex> , InterfacesPropertisFindWeb,FiltroDialogoIf,DialogPanelAuxIf<Kardex>  ,DialogoConfigAuxIf<Kardex>
{
    
    public static final int PARAMETRO_FILTRO_STOCK=-93;
    
    protected Empresa empresa;
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
        titulo.add(new ColumnaDialogo("Costo", 0.10d));
        titulo.add(new ColumnaDialogo("Pvp+Iva", 0.10d));
        titulo.add(new ColumnaDialogo("IVA", 0.05d));        
        titulo.add(new ColumnaDialogo("Stock", 0.05d));        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter,Map<Integer,Object> mapFiltro) {
        
        Kardex kardex;
        //kardex.getLote().getFechaVencimiento();
        String whereManejaInventario="";
        
        String whereBodega="";
        
        String whereStockNegativo="";
        

        
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
        
        TipoStockEnum tipoStockEnum= (TipoStockEnum) mapFiltro.get(PARAMETRO_FILTRO_STOCK);
        String whereFiltroStock="";
        if(tipoStockEnum!=null)
        {
            if(tipoStockEnum.equals(TipoStockEnum.CON_STOCK))
            {
                whereFiltroStock=" AND k.stock>0 ";
            }
            else if(tipoStockEnum.equals(TipoStockEnum.SIN_STOCK))
            {
                whereFiltroStock=" AND k.stock<=0 ";
            }
            else if(tipoStockEnum.equals(TipoStockEnum.TODOS))
            {
                whereFiltroStock=" ";
            }            
        }
         
        
        String filtroMarca=getFiltroPorMarca();
        
        String filtroCodigo=getFiltroPorCodigo();
        
        String filtroAplicacion=getFiltroPorAplicacion();
        
        String filtroSegmento=getFiltroPorSegmento();
        
        String queryString = "SELECT k FROM Kardex k JOIN k.producto u  WHERE 1=1 AND k.estado<>'E' "+filtroMarca+filtroCodigo+filtroAplicacion+filtroSegmento+whereFiltroStock+" AND k.producto.tipoProductoCodigo<>?6  "+queryFiltroEmpresa+" and (u.estado=?1)"+whereManejaInventario+whereBodega+whereStockNegativo;      
        
        queryString+=" and (  LOWER(u.nombre) like ?2 OR LOWER(u.codigoPersonalizado) like ?2 OR LOWER(u.codigoUPC) like ?2 OR LOWER(u.nombreGenerico) like ?2 ) ORDER BY u.nombre, u.codigoPersonalizado,k.lote";
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.tipoQuery=TipoQueryEnum.JPQL;
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
    
    
    
    
    public String getFiltroPorSegmento()
    {
        return "";
    }
    
    public String getFiltroPorCodigo()
    {
        return "";
    }
    
    public String getFiltroPorMarca()
    {
        return " AND ( u.marcaProducto=?97 ) ";
    }
    
    public String getFiltroPorAplicacion()
    {
        return "";
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
        vector.add((kardex.getCostoPromedio()!=null)?kardex.getCostoPromedio().setScale(3,RoundingMode.HALF_UP)+"":"");
        //vector.add(producto.getValorUnitario().setScale(3,RoundingMode.HALF_UP));
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

    @Override
    public JPanel getPanelAuxiliar(Kardex dato) {
        
        TallerMecanicoDialogoInfo panelAux=new TallerMecanicoDialogoInfo();
        
        if(dato!=null)
        {        
            panelAux.getLblNombreProducto().setText(dato.getProducto().getNombre());
            SegmentoProducto segmento=dato.getProducto().getSegmentoProducto();
            if(segmento!=null)
            {
                panelAux.getLblSegmento().setText(segmento.getNombre());                
            }
            
            panelAux.getLblUbicacion().setText(dato.getProducto().getUbicacion());
            panelAux.getLblAplicacion().setText(dato.getProducto().getAplicacionProducto());
        }
        
        return panelAux;
    }

    @Override
    public List<Kardex> preProcessResult(List<Kardex> datos) 
    {
        List<Kardex> listTemp=new ArrayList<Kardex>(datos);
        List<Kardex> resultadoList=new ArrayList<Kardex>(datos);
        
        
        for( Kardex kardex : datos )
        {
            //Verifico si tiene lote para eliminar el otro dato que no tenga lote
            if(kardex.getLote()!=null)
            {
                //Recorro la lista que tiene todos los datos para eliminar los que no necesito
                for (Kardex kardexTmp : listTemp) 
                {
                    //No tomo en cuenta el mismo dato del kardex
                    if(!kardex.equals(kardexTmp))
                    {
                        //Si encuentra otro dato con el mismo producto y bodega con stock en cero o negativo lo voy a eliminar
                        if(kardexTmp.getProducto().equals(kardex.getProducto()) && kardexTmp.getBodega().equals(kardex.getBodega()))
                        {
                            //Si no tiene stock entonces dejo eliminando
                            if(kardexTmp.getStock().compareTo(BigDecimal.ZERO)<=0)
                            {
                                resultadoList.remove(kardexTmp);
                            }
                        }
                    }
                }
            }
        }
        
        return resultadoList;
    }
    
}
