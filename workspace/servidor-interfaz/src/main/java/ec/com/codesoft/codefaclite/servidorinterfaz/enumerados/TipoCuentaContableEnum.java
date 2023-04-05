/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.banco.CuentaBanco;

/**
 *
 * @author CARLOS_CODESOFT
 */
public enum TipoCuentaContableEnum 
{
    ACTIVO("Activo","A"),
    PASIVO("Pasivo","P"),
    CAPITAL("Capital","C"),
    UTILIDADES_RETENIDAS("Utilidades Retenidas","U"),
    INGRESOS("Ingresos","I"),
    GASTOS("Gastos","A"),
    OTROS_INGRESOS("Otros Ingresos","OI"),
    OTROS_EGRESOS("Otros Egresos","OO"),;
    
    private String nombre;
    private String codigo;
    

    private TipoCuentaContableEnum(String nombre, String codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }
    
    
    
    
    public static TipoCuentaContableEnum getByCodigo(String codigo)
    {
        for (TipoCuentaContableEnum enumerador : TipoCuentaContableEnum.values()) 
        {
            if(enumerador.getCodigo().equals(codigo))
            {
                return enumerador;
            }
        }
        return null;
    }
    
    
}
