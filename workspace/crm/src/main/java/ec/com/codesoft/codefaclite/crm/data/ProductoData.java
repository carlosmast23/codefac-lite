/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.data;

/**
 *
 * @author Carlos
 */
public class ProductoData {
    private String codigoPrincipal;
    private String tipoProducto;
    private String nombre;
    private String valorUnitario;
    private String impuestoIva;

    public ProductoData() {
    }

    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getImpuestoIva() {
        return impuestoIva;
    }

    public void setImpuestoIva(String impuestoIva) {
        this.impuestoIva = impuestoIva;
    }
    
    
}
