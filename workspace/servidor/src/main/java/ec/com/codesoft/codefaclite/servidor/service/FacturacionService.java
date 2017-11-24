/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaFacade;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class FacturacionService {
    FacturaFacade facturaFacade;
    FacturaDetalleFacade facturaDetalleFacade;

    public FacturacionService() {
        this.facturaFacade=new FacturaFacade();
        this.facturaDetalleFacade=new FacturaDetalleFacade();
        
    }
    
    public void grabar(Factura factura)
    {
        facturaFacade.create(factura);
    }
    
    public List<Factura> obtenerTodos()
    {
        return facturaFacade.findAll();
    }
    
}
