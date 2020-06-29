/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.transporte;

import ec.com.codesoft.codefaclite.servidor.facade.FacturaDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.transporte.GuiaRemisionFacade;
import ec.com.codesoft.codefaclite.servidor.service.ComprobantesService;
import ec.com.codesoft.codefaclite.servidor.service.FacturacionService;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceConsulta;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DetalleProductoGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionServiceIf;
 ;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionService extends ServiceAbstract<GuiaRemision,GuiaRemisionFacade> implements GuiaRemisionServiceIf{

    public GuiaRemisionService()    {
        super(GuiaRemisionFacade.class);
    }

    public GuiaRemision grabar(GuiaRemision entity) throws ServicioCodefacException   {
        //Validaciones previas antes de grabar
        for (DestinatarioGuiaRemision destinatario : entity.getDestinatarios()) {
            if(destinatario.getAutorizacionNumero()==null || destinatario.getAutorizacionNumero().isEmpty())
            {
                throw new ServicioCodefacException("No se puede grabar una guia de remisión sin autorización de la factura");
            }
        }
        
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
                   //CAMBIAR ESTADOS DE LAS FACTURAS QUE VAN A SER ENVIADAS
                    for (DestinatarioGuiaRemision destinatario : entity.getDestinatarios()) {
                        for (DetalleProductoGuiaRemision detallesProducto : destinatario.getDetallesProductos()) {
                            Long facturaId=detallesProducto.getReferenciaId();
                            FacturaDetalleFacade facturaDetalleFacade=new FacturaDetalleFacade();
                            FacturaDetalle facturaDetalle= facturaDetalleFacade.find(facturaId);;
                            
                            Factura facturaEditar=facturaDetalle.getFactura();
                            System.out.println("Factura editar: "+facturaEditar.getPreimpreso());
                            facturaEditar.setEstadoEnviadoGuiaRemisionEnum(EnumSiNo.SI);
                            entityManager.merge(facturaEditar);
                        }
                        
                    }
                    
                    entityManager.persist(entity);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(GuiaRemisionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return entity;
    }
    
    public void editarGuiaRemision(GuiaRemision guiaRemision) throws ServicioCodefacException  
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException   {
                entityManager.merge(guiaRemision);
            }
        });
    }
    
    public List<GuiaRemision> obtenerConsulta(Date fechaInicial,Date fechaFinal,ComprobanteEntity.ComprobanteEnumEstado estado,Transportista transportista,Persona destinatario,String codigoProducto,Empresa empresa) throws ServicioCodefacException  
    {
        return (List<GuiaRemision>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException   {
                return getFacade().obtenerConsultaFacade(fechaInicial, fechaFinal,estado,transportista,destinatario,codigoProducto,empresa);
            }
        });
                
    }

    @Override
    public void eliminar(GuiaRemision entity)    {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException   {
                    ComprobantesService comprobanteService = new ComprobantesService();
                    comprobanteService.eliminarComprobanteSinTransaccion(entity);
                    
                    //cambiar el estado de la factura para que vuelvan volver a reenviar en otra guia de remision
                    for (DestinatarioGuiaRemision destinatario : entity.getDestinatarios()) {
                        if(destinatario.getFacturaReferencia()!=null)
                        {   //
                            destinatario.getFacturaReferencia().setEstadoEnviadoGuiaRemisionEnum(EnumSiNo.NO);
                            entityManager.merge(destinatario.getFacturaReferencia()); 
                        }
                    }

                    //entity.setEstado(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(entity);
                    //entity.setEstado(estado);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GuiaRemisionService.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }
    
    
    
    
}
