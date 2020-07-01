/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.converter;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
 ;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author CARLOS_CODESOFT
 */
@FacesConverter("tipoProductoConverter")
public class TipoProductoConverter extends AbstractConverter implements Converter
{

    @Override
    public Object buscarObjetoPorId(String valor)    {
        return TipoProductoEnum.getEnumByLetra(valor);
        //return ServiceFactory.getFactory().getTipoDocumentoServiceIf().buscarPorId(Long.parseLong(valor));
    }
    
}
