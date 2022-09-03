/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.enumerador;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import net.sf.jasperreports.components.list.HorizontalFillList;

/**
 *
 * @author Carlos
 */
public enum OrientacionReporteEnum {
    HORIZONTAL("h"),VERTICAL("v");
    
    private String letra;

    private OrientacionReporteEnum(String letra) {
        this.letra = letra;
    }

    public String getLetra() {
        return letra;
    }
    
    public static OrientacionReporteEnum buscarPorLetra(String letra)
    {
        for (OrientacionReporteEnum orientacion : OrientacionReporteEnum.values()) {
            if(orientacion.getLetra().equals(letra))
            {
                return orientacion;
            }
        }
        
        return null;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
