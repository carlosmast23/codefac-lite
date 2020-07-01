/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.converter;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
 ;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Robert
 */
@FacesConverter("comprobanteEnumConverter")
public class ComprobanteEnumConverter  extends AbstractConverter implements Converter {
    
    @Override
    public Object buscarObjetoPorId(String valor)    {
        return ComprobanteEntity.ComprobanteEnumEstado.getEnum(valor); 
    }
    
}
