/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.DescuentoProductoDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Descuento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoCondicionPrecio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoProductoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.DescuentoProductoDetalleServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class DescuentoProductoDetalleService extends ServiceAbstract<DescuentoProductoDetalle,DescuentoProductoDetalleFacade> implements DescuentoProductoDetalleServiceIf{

    public DescuentoProductoDetalleService() throws RemoteException 
    {
        super(DescuentoProductoDetalleFacade.class);
    }
    
    public List<DescuentoProductoDetalle>  consultarPorDescuento(Descuento descuento)  throws ServicioCodefacException,java.rmi.RemoteException
    {
        //DescuentoCondicionPrecio dcp;
        //dcp.getDescuento();
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("descuento", descuento);
        return getFacade().findByMap(mapParametros);
    }
    
}
