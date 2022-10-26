/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.dataExport;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ProductoExportar implements Serializable{
    public List<Producto> productoList;
    public String empresaOrigen;
    public String fechaExportacion;

    public ProductoExportar(List<Producto> productoList) {
        this.productoList = productoList;
    }
    
    

    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    public String getEmpresaOrigen() {
        return empresaOrigen;
    }

    public void setEmpresaOrigen(String empresaOrigen) {
        this.empresaOrigen = empresaOrigen;
    }

    public String getFechaExportacion() {
        return fechaExportacion;
    }

    public void setFechaExportacion(String fechaExportacion) {
        this.fechaExportacion = fechaExportacion;
    }
    
}
