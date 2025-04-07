/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoPresentacionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import jakarta.persistence.Query;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author DellWin10
 */
public class ProductoPresentacionDetalleFacade extends AbstractFacade<ProductoPresentacionDetalle> {
    
    public ProductoPresentacionDetalleFacade() {
        super(ProductoPresentacionDetalle.class);
    }
    
    
   
}
