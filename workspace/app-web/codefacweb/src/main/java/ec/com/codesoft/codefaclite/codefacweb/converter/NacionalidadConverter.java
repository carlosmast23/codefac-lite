/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.converter;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
 ;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Carlos
 */
@FacesConverter("nacionalidadConverter")
public class NacionalidadConverter extends AbstractConverter implements Converter {

    @Override
    public Object buscarObjetoPorId(String valor)    {
        return ServiceFactory.getFactory().getNacionalidadServiceIf().buscarPorId(Long.parseLong(valor));
    }
    
}
