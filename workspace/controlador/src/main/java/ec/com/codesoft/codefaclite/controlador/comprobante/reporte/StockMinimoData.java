/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import com.sun.java.swing.plaf.motif.resources.motif;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoProducto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class StockMinimoData implements ExcelDatosInterface{
    
    public static String NOMBRE_CABECERA_EXCEL[] = {"Código","Codigo2","Lote","Bodega","Producto","Marca","Categoria","Aplicación","Ubicación","Iva","Stock","Disp","Reserv","Pvp1" ,"Cantidad Min","Costo","´Último Costo","Utilidad","F.Caducidad","Reg.Sanitario"};
    
    private Long kardexId;
    private String codigo;
    private String codigo2;
    private String lote;
    private String fechaCaducidad;
    private String registroSanitario;
    private String producto;
    private String stock;
    private String cantidadMinima;
    private String categoria;
    private String ubicacion;
    private String costo;
    private String ultimoCosto;
    private String bodega;
    private BigDecimal pvp1;
    private BigDecimal utilidad1;
    private BigDecimal reserva;
    private BigDecimal disponible;
    
    private String aplicacion;
    private TipoProducto tipo;
    private SegmentoProducto segmento;
    
    private String rucProveedor;
    private String nombreProveedor;
    private String marca;
    private Integer ivaPorcentaje;
    
    //Variable temporal que solo me sirve para saber si fue modificado un stock
    private Boolean actualizarStockTmp=false;
    
    private List<StockUnicoData> detalles;
    private List<PresentacionPrecioData> presentacionList;
    
    

    public StockMinimoData() {
        detalles=new ArrayList<StockUnicoData>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo2() {
        return codigo2;
    }

    public void setCodigo2(String codigo2) {
        this.codigo2 = codigo2;
    }
    
    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {        
        this.stock = stock;
    }

    public String getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(String cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Long getKardexId() {
        return kardexId;
    }

    public void setKardexId(Long kardexId) {
        this.kardexId = kardexId;
    }

    public Boolean getActualizarStockTmp() {
        return actualizarStockTmp;
    }

    public void setActualizarStockTmp(Boolean actualizarStockTmp) {
        this.actualizarStockTmp = actualizarStockTmp;
    }
    
    
      

    public List<StockUnicoData> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<StockUnicoData> detalles) {
        this.detalles = detalles;
    }
    
    public void agregarDetalle(StockUnicoData stockUnicoData)
    {
        if(this.detalles==null)
        {
            this.detalles=new ArrayList<StockUnicoData>();
        }
        
        this.detalles.add(stockUnicoData);
    }

    public BigDecimal getPvp1() {
        return pvp1;
    }

    public void setPvp1(BigDecimal pvp1) {
        this.pvp1 = pvp1;
    }

    public BigDecimal getUtilidad1() {
        return utilidad1;
    }

    public void setUtilidad1(BigDecimal utilidad1) {
        this.utilidad1 = utilidad1;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    public SegmentoProducto getSegmento() {
        return segmento;
    }

    public void setSegmento(SegmentoProducto segmento) {
        this.segmento = segmento;
    }

    public String getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(String rucProveedor) {
        this.rucProveedor = rucProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUltimoCosto() {
        return ultimoCosto;
    }

    public void setUltimoCosto(String ultimoCosto) {
        this.ultimoCosto = ultimoCosto;
    }

    public BigDecimal getReserva() {
        return reserva;
    }

    public void setReserva(BigDecimal reserva) {
        this.reserva = reserva;
    }

    public Integer getIvaPorcentaje() {
        return ivaPorcentaje;
    }

    public void setIvaPorcentaje(Integer ivaPorcentaje) {
        this.ivaPorcentaje = ivaPorcentaje;
    }

    public List<PresentacionPrecioData> getPresentacionList() {
        return presentacionList;
    }

    public void setPresentacionList(List<PresentacionPrecioData> presentacionList) {
        this.presentacionList = presentacionList;
    }

    public BigDecimal getDisponible() {
        return disponible;
    }

    public void setDisponible(BigDecimal disponible) {
        this.disponible = disponible;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getRegistroSanitario() {
        return registroSanitario;
    }

    public void setRegistroSanitario(String registroSanitario) {
        this.registroSanitario = registroSanitario;
    }
    
    
    
    
    
    
    
    public void agregarPresentacion(PresentacionPrecioData presentacionData)
    {
        if(this.presentacionList==null)
        {
            this.presentacionList=new ArrayList<PresentacionPrecioData>();
        }
        
        this.presentacionList.add(presentacionData);
    }
    
    

    @Override
    public List<TipoDato> getDatos() {
        
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();        
        tiposDatos.add(new TipoDato(this.codigo,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.codigo2,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.lote,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.bodega,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.producto, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.marca, Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.categoria, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.aplicacion, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.ubicacion, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.ivaPorcentaje, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.stock,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.disponible,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.reserva,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.pvp1,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.cantidadMinima, Excel.TipoDataEnum.NUMERO));        
        tiposDatos.add(new TipoDato(this.costo,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.ultimoCosto,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.utilidad1,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.fechaCaducidad,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.registroSanitario,Excel.TipoDataEnum.TEXTO));        
        
        return tiposDatos;
    }
    
    
    public static Map<Long,BigDecimal> obtenerKardexModificadoStock(List<StockMinimoData> lista)
    {
        Map<Long,BigDecimal> mapStock=new HashMap<Long, BigDecimal>();
        
        for (StockMinimoData stockMinimoData : lista) 
        {
            if(stockMinimoData.actualizarStockTmp)
            {
                mapStock.put(stockMinimoData.getKardexId(),new BigDecimal(stockMinimoData.stock));
            }
        }
        return mapStock;
    }
    
}
