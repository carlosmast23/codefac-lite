/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.converter;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import java.rmi.RemoteException;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Carlos
 */
@FacesConverter("sucursalConverter")
public class SucursalConverter extends AbstractConverter implements Converter {

    @Override
    public Object buscarObjetoPorId(Long id) throws RemoteException {
       return ServiceFactory.getFactory().getSucursalServiceIf().buscarPorId(id);
    }
}
