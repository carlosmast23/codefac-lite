/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.transporte;

import ec.com.codesoft.codefaclite.servidor.facade.transporte.GuiaRemisionFacade;
import ec.com.codesoft.codefaclite.servidor.service.ComprobantesService;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DetalleProductoGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionServiceIf;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionService extends ServiceAbstract<GuiaRemision,GuiaRemisionFacade> implements GuiaRemisionServiceIf{

    public GuiaRemisionService() throws RemoteException {
        super(GuiaRemisionFacade.class);
    }

    public GuiaRemision grabar(GuiaRemision entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() {
                try {
                    ComprobantesService servicioComprobante = new ComprobantesService();
                    entity.setCodigoDocumento(DocumentoEnum.GUIA_REMISION.getCodigo());
                    
                    servicioComprobante.setearSecuencialComprobanteSinTransaccion(entity);
                    
                    if(entity.getDestinatarios()!=null)
                    {
                        for (DestinatarioGuiaRemision destinatario : entity.getDestinatarios()) 
                        {                            
                            if(destinatario.getDetallesProductos()!=null)
                            {
                                for (DetalleProductoGuiaRemision detalleProducto : destinatario.getDetallesProductos()) {
                                    entityManager.persist(detalleProducto);
                                }
                            }
                            entityManager.persist(destinatario);
                        }
                    }                    
                    //entityManager.merge(entity.getTransportista());
                    
                    entityManager.persist(entity);
                } catch (RemoteException ex) {
                    Logger.getLogger(GuiaRemisionService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(GuiaRemisionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return entity;
    }
    
    public List<GuiaRemision> obtenerConsulta(Date fechaInicial,Date fechaFinal,ComprobanteEntity.ComprobanteEnumEstado estado) throws ServicioCodefacException, RemoteException
    {
        return getFacade().obtenerConsultaFacade(fechaInicial, fechaFinal,estado);
    }

    @Override
    public void eliminar(GuiaRemision entity) throws RemoteException {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {
                    entity.setEstado(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(entity);
                    //entity.setEstado(estado);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GuiaRemisionService.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }
    
    
    
    
}
