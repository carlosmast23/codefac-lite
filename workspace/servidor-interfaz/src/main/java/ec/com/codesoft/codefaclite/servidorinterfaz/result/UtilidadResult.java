/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.result;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author DellWin10
 */
public class UtilidadResult extends AbstractResult{
    
    private String secuencial;
    
    public String fechaEmision;
    
    private String razonSocial;
            
    public String identificacion;
    
    public Long facturaId;
    
    public BigDecimal subtotal;
    
    public BigDecimal costo;
    
    public BigDecimal utilidad;

    
    @Override
    public void constructor(Object[] dato) {
        secuencial=(String) dato[0];
        fechaEmision=(String) dato[1];
        razonSocial=(String) dato[2];
        identificacion=(String)dato[3];
        facturaId=(Long) dato[4];
        subtotal=(BigDecimal) dato[5];
        costo=(BigDecimal) dato[6];
        utilidad=(BigDecimal) dato[7];
    }

    
}

