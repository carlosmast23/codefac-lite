/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.data;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class StockMinimoData implements ExcelDatosInterface{
    private String codigo;
    private String producto;
    private String stock;
    private String cantidadMinima;
    private String categoria;
    private String ubicacion;

    public StockMinimoData() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    
    

    @Override
    public List<TipoDato> getDatos() {
        
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();        
        tiposDatos.add(new TipoDato(this.codigo,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.producto, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.categoria, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.ubicacion, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.stock,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.cantidadMinima, Excel.TipoDataEnum.NUMERO));
        
        
        return tiposDatos;
    }
    
    
    
    
}
