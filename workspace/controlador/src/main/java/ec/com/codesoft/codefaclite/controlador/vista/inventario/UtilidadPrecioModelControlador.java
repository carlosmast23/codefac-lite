/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.inventario;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ProductoPrecioDataTable;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaPedidoLoteModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.TableBindingImp;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.CostoProductoRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import static ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades.obtenerValorBaseDatos;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesPorcentajes;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class UtilidadPrecioModelControlador extends ModelControladorAbstract<UtilidadPrecioModelControlador.CommonIf,UtilidadPrecioModelControlador.SwingIf,UtilidadPrecioModelControlador.WebIf> implements VistaCodefacIf {

    private Integer pvp1Porcentaje=0;
    private Integer pvp2Porcentaje=0;
    private Integer pvp3Porcentaje=0;
    private Integer pvp4Porcentaje=0;
    private Integer pvp5Porcentaje=0;
    private Integer pvp6Porcentaje=0;
    
    
    private List<ProductoPrecioDataTable> productoList;
    private List<ProductoPrecioDataTable> productoSeleccionadoList;
    
    private List<CostoCalculoEnum> costoCalculoList;
    private List<EnumSiNo> opcionIvaList;
    private CostoCalculoEnum costoCalculoEnum;
    private EnumSiNo incluidoIva;
    
    private ProductoPrecioDataTable productoSeleccionado;
    private TableBindingImp tableBindingControlador;
    
    private Producto productoFiltro;
    
    
    public UtilidadPrecioModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }
    
    private List<Producto> listDataTableToObject(List<ProductoPrecioDataTable> productoDataList)
    {
        List<Producto> productoList=new ArrayList<Producto>();
        for (ProductoPrecioDataTable productoData : productoDataList) 
        {
            productoList.add(productoData.producto);            
        }
        return productoList;
    }

    public void listenerActualizarPorcentaje()
    {
        castListDataTable(productoList);
        getInterazEscritorio().limpiarTablaDetalle();
    }
    
    public void listenerConsultarProductos()
    {
        try {
            List<Producto> productoTmpList= ServiceFactory.getFactory().getProductoServiceIf().reporteProducto(productoFiltro,getInterfaz().pendientesActualizarPrecio());
            castListProducto(productoTmpList);
            
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadPrecioModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(UtilidadPrecioModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void castListProducto(List<Producto> productoTmpList)
    {
        List<ProductoPrecioDataTable> listTmp=new ArrayList<ProductoPrecioDataTable>();
        for (Producto producto : productoTmpList) 
        {
            listTmp.add(new ProductoPrecioDataTable(producto));
        }
        
        castListDataTable(listTmp);
    }
    
    
    //todo: Cambiar el nombre de esta funcion para algo más descriptivo, lo que hace es coger datos inconclusos y terminar de llenar con datos por defecto
    public void castListDataTable(List<ProductoPrecioDataTable> productoTmpList)
    {
        productoList=new ArrayList<ProductoPrecioDataTable>();
        for (ProductoPrecioDataTable productoData : productoTmpList) 
        {

            Producto producto=productoData.producto;
            BigDecimal costoPromedio = BigDecimal.ZERO;
            BigDecimal costoUltimo = BigDecimal.ZERO;
            
            try {
                //Kardex kardexProducto = ServiceFactory.getFactory().getKardexServiceIf().buscarKardexPorProducto(producto);
                CostoProductoRespuesta costoProducto=ServiceFactory.getFactory().getKardexServiceIf().buscarCostoProductoRespuesta(productoData.producto);
                
                if (costoProducto != null) 
                {
                    costoPromedio = costoProducto.costoPromedio;
                    costoUltimo = costoProducto.costoUltimo;
                }
                
                //Si manda un ultimo costo cojo el de los datos enviados
                if(productoData.costoUltimoOriginal!=null)
                {
                    costoUltimo=productoData.costoUltimoOriginal;
                }
                
                
            } catch (RemoteException ex) {
                Logger.getLogger(UtilidadPrecioModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            ProductoPrecioDataTable productoDataTable=new ProductoPrecioDataTable
            (
                    productoData.producto, 
                    BigDecimal.ZERO,
                    costoPromedio,
                    costoUltimo,
                    new BigDecimal(pvp1Porcentaje), 
                    new BigDecimal(pvp2Porcentaje), 
                    new BigDecimal(pvp3Porcentaje), 
                    new BigDecimal(pvp4Porcentaje), 
                    new BigDecimal(pvp5Porcentaje), 
                    new BigDecimal(pvp6Porcentaje)
            );
            
            if(incluidoIva.equals(EnumSiNo.NO))
            {
                productoDataTable.pvp1=producto.getValorUnitario();
                productoDataTable.pvp2=producto.getPrecioDistribuidor();
                productoDataTable.pvp3=producto.getPrecioTarjeta();
                productoDataTable.pvp4=producto.getPvp4();
                productoDataTable.pvp5=producto.getPvp5();
                productoDataTable.pvp6=producto.getPvp6();
            }
            else if(incluidoIva.equals(EnumSiNo.SI))
            {
                productoDataTable.pvp1=producto.getValorUnitarioConIva();
                productoDataTable.pvp2=producto.getPrecioDistribuidorConIva();
                productoDataTable.pvp3=producto.getPrecioTarjetaConIva();
                productoDataTable.pvp4=producto.getPvp4ConIva();
                productoDataTable.pvp5=producto.getPvp5ConIva();
                productoDataTable.pvp6=producto.getPvp6ConIva();
                productoDataTable.costoPromedio=UtilidadesImpuestos.agregarValorIva(ParametrosSistemaCodefac.obtenerIvaDefecto(), costoPromedio);
                productoDataTable.costoUltimo=UtilidadesImpuestos.agregarValorIva(ParametrosSistemaCodefac.obtenerIvaDefecto(), costoUltimo);
                //productoData.costoCalculo=UtilidadesImpuestos.agregarValorIva(ParametrosSistemaCodefac.obtenerIvaDefecto(), costoCalculo);
            }
            
            BigDecimal costoCalculo=BigDecimal.ZERO;
            if(costoCalculoEnum.equals(costoCalculoEnum.COSTO_PROMEDIO))
            {
                costoCalculo=productoDataTable.costoPromedio;
            }
            else if(costoCalculoEnum.equals(costoCalculoEnum.ULTIMO_COSTO))
            {
                costoCalculo=productoDataTable.costoUltimo;
            }
            productoDataTable.costoCalculo=costoCalculo;
            
            productoList.add(productoDataTable);
        }
        
    }
    
    public void listenerBuscarProducto()
    {
        ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo(EnumSiNo.SI, session.getEmpresa(),true,true);
        BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
        buscarDialogo.setVisible(true);
        productoFiltro = (Producto) buscarDialogo.getResultado();
    }
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        costoCalculoList=UtilidadesLista.arrayToList(CostoCalculoEnum.values());
        opcionIvaList=UtilidadesLista.arrayToList(EnumSiNo.values());
        costoCalculoEnum=CostoCalculoEnum.ULTIMO_COSTO;
        incluidoIva=EnumSiNo.NO;
        cargarValorPorDefectoMargenUtilidades();
    }
    
    private void cargarValorPorDefectoMargenUtilidades()
    {
        try {
            ParametroUtilidades.ComparadorInterface<Integer>comparador=new ParametroUtilidades.ComparadorInterface<Integer>() {
                @Override
                public Integer consultarParametro(String nombreParametro) 
                {
                    if(!UtilidadesTextos.verificarNullOVacio(nombreParametro))
                    {
                        try
                        {
                            return Integer.parseInt(nombreParametro+"");
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }                        
                    }
                    return 0; //Si no tiene valores por defecto devuelvo cero
                }
            };
            
            
            pvp1Porcentaje= ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(), ParametroCodefac.PVP1_MARGEN_UTILIDAD,comparador);
            pvp2Porcentaje= ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(), ParametroCodefac.PVP2_MARGEN_UTILIDAD,comparador);
            pvp3Porcentaje= ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(), ParametroCodefac.PVP3_MARGEN_UTILIDAD,comparador);
            pvp4Porcentaje= ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(), ParametroCodefac.PVP4_MARGEN_UTILIDAD,comparador);
            pvp5Porcentaje= ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(), ParametroCodefac.PVP5_MARGEN_UTILIDAD,comparador);
            pvp6Porcentaje= ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(), ParametroCodefac.PVP6_MARGEN_UTILIDAD,comparador);
            
            if (pvp1Porcentaje == null) {
                pvp1Porcentaje = -1;
            }

            if (pvp2Porcentaje == null) {
                pvp2Porcentaje = -1;
            }

            if (pvp3Porcentaje == null) {
                pvp3Porcentaje = -1;
            }

            if (pvp4Porcentaje == null) {
                pvp4Porcentaje = -1;
            }

            if (pvp5Porcentaje == null) {
                pvp5Porcentaje = -1;
            }
            if (pvp6Porcentaje == null) {
                pvp6Porcentaje = -1;
            }
                                                            
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadPrecioModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException 
    {
        try {
            validarProducto();
            if(!dialogoPregunta(new CodefacMsj("Esta seguro que quiere actualizar los datos ?", CodefacMsj.TipoMensajeEnum.ADVERTENCIA)))
            {
                throw new ExcepcionCodefacLite("Cancelar grabar utilidad ...");
            }
            //List<ProductoPrecioDataTable> productosSelecionadoList= completarCostoCalculoLista(productoSeleccionadoList);
            normalizarDatosProcesar(productoSeleccionadoList);
            ServiceFactory.getFactory().getProductoServiceIf().actualizarPrecios(productoSeleccionadoList);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            mostrarMensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            Logger.getLogger(UtilidadPrecioModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite("Cancelar grabar utilidad ...");
        }
    }
    
    private void normalizarDatosProcesar(List<ProductoPrecioDataTable> lista)
    {
        for (ProductoPrecioDataTable productoDataTable : lista) 
        {
            if(incluidoIva.equals(EnumSiNo.SI))
            {
                productoDataTable.pvp1= UtilidadesImpuestos.quitarValorIva(ParametrosSistemaCodefac.obtenerIvaDefecto(), productoDataTable.pvp1,6);
                productoDataTable.pvp2= UtilidadesImpuestos.quitarValorIva(ParametrosSistemaCodefac.obtenerIvaDefecto(), productoDataTable.pvp2,6);
                productoDataTable.pvp3= UtilidadesImpuestos.quitarValorIva(ParametrosSistemaCodefac.obtenerIvaDefecto(), productoDataTable.pvp3,6);
                productoDataTable.pvp4= UtilidadesImpuestos.quitarValorIva(ParametrosSistemaCodefac.obtenerIvaDefecto(), productoDataTable.pvp4,6);
                productoDataTable.pvp5= UtilidadesImpuestos.quitarValorIva(ParametrosSistemaCodefac.obtenerIvaDefecto(), productoDataTable.pvp5,6);
                productoDataTable.pvp6= UtilidadesImpuestos.quitarValorIva(ParametrosSistemaCodefac.obtenerIvaDefecto(), productoDataTable.pvp6,6);
            }
        }
    }
    
    public void validarProducto() throws ServicioCodefacException
    {
        if(productoSeleccionadoList==null || productoSeleccionadoList.size()==0 )
        {
            throw new ServicioCodefacException("Seleccione uno o varios productos para actualizar el sistema");
        }
    }
    
    
    /*public List<ProductoPrecioDataTable> completarCostoCalculoLista(List<ProductoPrecioDataTable> lista)
    {
        for (ProductoPrecioDataTable productoData : lista) 
        {
            if(costoCalculoEnum.equals(CostoCalculoEnum.ULTIMO_COSTO))
            {
                productoData.costoCalculo=productoData.costoUltimo;
            }
            else if(costoCalculoEnum.equals(costoCalculoEnum.COSTO_PROMEDIO))
            {
                productoData.costoCalculo=productoData.costoPromedio;
            }
        }
        return lista;
    }*/
    
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
        this.productoList=new ArrayList<ProductoPrecioDataTable>();
        this.productoSeleccionadoList=new ArrayList<ProductoPrecioDataTable>();
        this.productoFiltro=null;
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
    
    ////////////////////////////////////////////////////////////////////////////
    //                          METODOS GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public List<ProductoPrecioDataTable> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<ProductoPrecioDataTable> productoList) {
        this.productoList = productoList;
    }

    public List<ProductoPrecioDataTable> getProductoSeleccionadoList() {
        return productoSeleccionadoList;
    }

    public void setProductoSeleccionadoList(List<ProductoPrecioDataTable> productoSeleccionadoList) {
        this.productoSeleccionadoList = productoSeleccionadoList;
    }
    
    public ProductoPrecioDataTable getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(ProductoPrecioDataTable productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public TableBindingImp getTableBindingControlador() {
        return tableBindingControlador;
    }

    public void setTableBindingControlador(TableBindingImp tableBindingControlador) {
        this.tableBindingControlador = tableBindingControlador;
    }

    public List<CostoCalculoEnum> getCostoCalculoList() {
        return costoCalculoList;
    }

    public void setCostoCalculoList(List<CostoCalculoEnum> costoCalculoList) {
        this.costoCalculoList = costoCalculoList;
    }

    public CostoCalculoEnum getCostoCalculoEnum() {
        return costoCalculoEnum;
    }

    public void setCostoCalculoEnum(CostoCalculoEnum costoCalculoEnum) {
        this.costoCalculoEnum = costoCalculoEnum;
    }


    
    
    
    
    ///////////////////////////////////////////////////////////////////////////
    //             METODOS QUE CONTIENEN INTERFACES PARA LA IMPLEMTACION
    ///////////////////////////////////////////////////////////////////////////
    public interface CommonIf {
        //TODO: Implementacion de todas las interfaces comunes
        public Boolean pendientesActualizarPrecio();
        
    }

    public interface SwingIf extends CommonIf {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
        public void limpiarTablaDetalle();
        
    }

    public interface WebIf extends CommonIf {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                       GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public Integer getPvp1Porcentaje() {
        return pvp1Porcentaje;
    }

    public void setPvp1Porcentaje(Integer pvp1Porcentaje) {
        this.pvp1Porcentaje = pvp1Porcentaje;
    }

    public Integer getPvp2Porcentaje() {
        return pvp2Porcentaje;
    }

    public void setPvp2Porcentaje(Integer pvp2Porcentaje) {
        this.pvp2Porcentaje = pvp2Porcentaje;
    }

    public Integer getPvp3Porcentaje() {
        return pvp3Porcentaje;
    }

    public void setPvp3Porcentaje(Integer pvp3Porcentaje) {
        this.pvp3Porcentaje = pvp3Porcentaje;
    }

    public Integer getPvp4Porcentaje() {
        return pvp4Porcentaje;
    }

    public void setPvp4Porcentaje(Integer pvp4Porcentaje) {
        this.pvp4Porcentaje = pvp4Porcentaje;
    }

    public Integer getPvp5Porcentaje() {
        return pvp5Porcentaje;
    }

    public void setPvp5Porcentaje(Integer pvp5Porcentaje) {
        this.pvp5Porcentaje = pvp5Porcentaje;
    }

    public Integer getPvp6Porcentaje() {
        return pvp6Porcentaje;
    }

    public void setPvp6Porcentaje(Integer pvp6Porcentaje) {
        this.pvp6Porcentaje = pvp6Porcentaje;
    }

    public Producto getProductoFiltro() {
        return productoFiltro;
    }

    public void setProductoFiltro(Producto productoFiltro) {
        this.productoFiltro = productoFiltro;
    }

    public List<EnumSiNo> getOpcionIvaList() {
        return opcionIvaList;
    }

    public void setOpcionIvaList(List<EnumSiNo> opcionIvaList) {
        this.opcionIvaList = opcionIvaList;
    }

    public EnumSiNo getIncluidoIva() {
        return incluidoIva;
    }

    public void setIncluidoIva(EnumSiNo incluidoIva) {
        this.incluidoIva = incluidoIva;
    }
    
    
    
    
    
    public enum CostoCalculoEnum
    {
        COSTO_PROMEDIO,
        ULTIMO_COSTO;
    }
    

}
