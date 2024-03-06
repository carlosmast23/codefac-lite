/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import java.math.BigDecimal;

/**
 *
 * @author CARLOS_CODESOFT
 */
public enum SignoEnum {
    
    POSITIVO(1),
    NEGATIVO(-1),
    NEUTRO(0);
    
    private Integer valor;

    private SignoEnum(Integer valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
    
    public BigDecimal getValorBigDecimal() {
        return new BigDecimal(valor+"");
    }

    @Override
    public String toString() {
        return (valor<0)?"-":"+";
    }
    
    
    
    public static SignoEnum consultarPorValor(Integer valor)
    {
        if(valor==null)
        {
            return NEUTRO;
        }
        else if(valor.equals(0))
        {
            return NEUTRO;
        }
        else if(valor>0)
        {
            return POSITIVO;
        }
        else if(valor<0)
        {
            return NEGATIVO;
        }
        else
        {
            return null;
        }
        
    }
    
}
