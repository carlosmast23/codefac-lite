/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Descuento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoCondicionPrecio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public interface DescuentoCondicionPrecioServiceIf extends ServiceAbstractIf<DescuentoCondicionPrecio>{
    
    public List<DescuentoCondicionPrecio>  consultarPorDescuento(Descuento descuento)  throws ServicioCodefacException,java.rmi.RemoteException;
}
