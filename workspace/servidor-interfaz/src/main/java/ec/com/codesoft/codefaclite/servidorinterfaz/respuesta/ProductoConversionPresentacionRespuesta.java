/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.respuesta;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ProductoConversionPresentacionRespuesta implements Serializable{
    public Producto productoPresentacionSecundario;
    public Producto productoPresentacionPrincipal;
    public BigDecimal cantidad;
    public BigDecimal precioUnitario;

    public ProductoConversionPresentacionRespuesta(Producto productoPresentacionSecundario, Producto productoPresentacionPrincipal, BigDecimal cantidad, BigDecimal precioUnitario) {
        this.productoPresentacionSecundario = productoPresentacionSecundario;
        this.productoPresentacionPrincipal = productoPresentacionPrincipal;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }
    
    
    
}
