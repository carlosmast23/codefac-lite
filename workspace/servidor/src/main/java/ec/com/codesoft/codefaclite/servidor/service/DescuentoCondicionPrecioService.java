/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.DescuentoCondicionPrecioFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Descuento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoCondicionPrecio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.DescuentoCondicionPrecioServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class DescuentoCondicionPrecioService extends ServiceAbstract<DescuentoCondicionPrecio,DescuentoCondicionPrecioFacade> implements DescuentoCondicionPrecioServiceIf {

    public DescuentoCondicionPrecioService() throws RemoteException 
    {
        super(DescuentoCondicionPrecioFacade.class);
    }
    
    
    public List<DescuentoCondicionPrecio>  consultarPorDescuento(Descuento descuento)  throws ServicioCodefacException,java.rmi.RemoteException
    {
        //DescuentoCondicionPrecio dcp;
        //dcp.getDescuento();
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("descuento", descuento);
        return getFacade().findByMap(mapParametros);
    }
    
}
