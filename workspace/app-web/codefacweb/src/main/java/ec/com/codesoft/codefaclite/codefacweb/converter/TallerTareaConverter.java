/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.codefacweb.converter;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import java.rmi.RemoteException;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author DellWin10
 */
@FacesConverter("tallerTareaConverter")
public class TallerTareaConverter extends AbstractConverter implements Converter{

    @Override
    public Object buscarObjetoPorId(String id) throws RemoteException {
        System.out.println("ejecutando tallerTareaConverter ...");
        return ServiceFactory.getFactory().getTallerTareaServiceIf().buscarPorId(id);
    }
    
}

