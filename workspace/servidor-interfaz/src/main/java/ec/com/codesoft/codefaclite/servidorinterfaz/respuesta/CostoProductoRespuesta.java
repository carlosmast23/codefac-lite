/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.respuesta;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CostoProductoRespuesta implements Serializable{
    public BigDecimal costoPromedio;
    public BigDecimal costoUltimo;

    public CostoProductoRespuesta(BigDecimal costoPromedio, BigDecimal costoUltimo) {
        this.costoPromedio = costoPromedio;
        this.costoUltimo = costoUltimo;
    }

    public CostoProductoRespuesta() {
        this.costoPromedio=BigDecimal.ZERO;
        this.costoUltimo=BigDecimal.ZERO;
    }
    
    
    
    
    
}
