/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.converter;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
 ;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author CARLOS_CODESOFT
 */
@FacesConverter("siNoEnumConverter")
public class SiNoEnumConverter extends AbstractConverter implements Converter {

    @Override
    public Object buscarObjetoPorId(String valor)    {
        return EnumSiNo.getEnumByLetra(valor);
    }
    
}
