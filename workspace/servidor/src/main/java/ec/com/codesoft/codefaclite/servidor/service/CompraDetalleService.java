/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidor.facade.CompraDetalleFacade;

/**
 *
 * @author Carlos
 */
public class CompraDetalleService extends ServiceAbstract<CompraDetalle, CompraDetalleFacade>{
    
    public CompraDetalleService() {
        super(CompraDetalleFacade.class);
    }
    
}
