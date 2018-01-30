/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity.enumerados;

/**
 *
 * @author Carlos
 */
public enum TipoProductoEnum {
    /**
     * Si el producto es un producto fisico y debe manejarse control del stock
     */
    PRODUCTO("Producto", "p"),
    /**
     * Si el producto es de un servicio intangible
     */
    SERVICIO("Servicio", "s"),
    /**
     * Si el producto es una mescla o esta compuesto de otros productos
     */
    EMSAMBLE("Ensamble", "e");

    private TipoProductoEnum(String nombre,String letra) {
        this.nombre = nombre;
        this.letra=letra;
    }

    
    
    private String nombre;
    private String letra;

    public String getNombre() {
        return nombre;
    }

    public String getLetra() {
        return letra;
    }
    
    

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
    
}
