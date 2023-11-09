/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.converter;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author CARLOS_CODESOFT
 */
@FacesConverter("tipoCarteraEnumConverter")
public class TipoCarteraEnumConverter implements Converter 
{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return Cartera.TipoCarteraEnum.buscarPorLetra(value);
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        if(value==null)
        {
            return "";
        }
        else
        {
            return value.toString();
        }
    }
    
}
