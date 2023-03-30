/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.FacturaDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.RubroEstudianteService;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturaDetalleServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class FacturaDetalleService extends ServiceAbstract<FacturaDetalle, FacturaDetalleFacade> implements FacturaDetalleServiceIf {

    RubroEstudianteService rubroEstudianteService=new RubroEstudianteService();
    PresupuestoService presupuestoService=new PresupuestoService();
    ProductoService productoService=new ProductoService();
    
    
    public FacturaDetalleService() throws RemoteException {
        super(FacturaDetalleFacade.class);
    }
    
    
    public Object getReferenciaDetalle(FacturaDetalle facturaDetalle) throws ServicioCodefacException,java.rmi.RemoteException
    {
        try {
            TipoDocumentoEnum tipoReferenciaEnum=facturaDetalle.getTipoDocumentoEnum();
            switch (tipoReferenciaEnum) {
                case ACADEMICO:
                    RubroEstudiante rubroEstudiante = rubroEstudianteService.buscarPorId(facturaDetalle.getReferenciaId());
                    return rubroEstudiante;
                    
                case PRESUPUESTOS:
                    Presupuesto presupuesto=presupuestoService.buscarPorId(facturaDetalle.getReferenciaId());
                    return presupuesto;
                    
                case INVENTARIO:
                case LIBRE:
                    Producto producto = productoService.buscarPorId(facturaDetalle.getReferenciaId());
                    return producto;
                    
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaDetalleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<FacturaDetalle> buscarPorFactura(Factura factura) throws ServicioCodefacException,java.rmi.RemoteException
    {        
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("factura",factura);
        return getFacade().findByMap(mapParametros);
    }
    
}
