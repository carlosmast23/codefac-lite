/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.cartera;

import ec.com.codesoft.codefaclite.servidor.facade.cartera.CarteraFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ParametroCodefacService;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class CarteraService extends ServiceAbstract<Cartera,CarteraFacade> implements CarteraServiceIf{

    public CarteraService() throws RemoteException {
        super(CarteraFacade.class);
    }
    
    public Cartera grabarCartera(Cartera cartera,List<CarteraCruce> cruces) throws ServicioCodefacException,java.rmi.RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() {
                
                Map<Long,CarteraDetalle> mapDetallesGrabados=new HashMap<Long,CarteraDetalle>();
                //grabar los detalles de la cartera
                for (CarteraDetalle detalle : cartera.getDetalles()) {
                    Long idTemporal=detalle.getId(); //Valor de id temporal para poder guardar los cruces
                    detalle.setId(null); //Esta variable dejo en null para que al grabar genere el nuevo objeto
                    entityManager.persist(detalle);
                    
                    //Grabar los antiguos id con los nuevos que despues me sirve para poder grabar los cruces
                    mapDetallesGrabados.put(idTemporal,detalle);
                    
                }
                
                //Grabar los cruces con al referencia de los nuevos detalles ya grabados
                for (CarteraCruce carteraCruce : cruces) {
                    CarteraDetalle carteraDetalleGrabada=mapDetallesGrabados.get(carteraCruce.getCarteraDetalle().getId());
                    carteraCruce.setCarteraDetalle(carteraDetalleGrabada);
                    entityManager.persist(carteraCruce);
                }
                
                //grabar la cartera
                entityManager.persist(cartera);
                
            }
        });
        
        return cartera;
    }
    
    /**
     * Metodo que me permite almacenar los documentos en la tabla de cartera
     */
    public void grabarDocumentoCartera(ComprobanteEntity comprobante,Cartera.TipoCarteraEnum tipo) throws RemoteException 
    {
        ParametroCodefacService parametroCodefacService=new ParametroCodefacService();
        ParametroCodefac parametro=parametroCodefacService.getParametroByNombre(ParametroCodefac.ACTIVAR_CARTERA);
        
        //Si no existe este dato asumo que esta trabajando sin cartera
        if(parametro==null )
        {
            return;
        }
        else
        {
            //Si esta seleccinado la opcion no cancelo la creacion del inventario
            if(EnumSiNo.getEnumByLetra(parametro.getValor()).equals(EnumSiNo.NO))
            {
                return;
            }
        }
        
        Cartera cartera = new Cartera();
        cartera.setCodigoDocumento(comprobante.getCodigoDocumento());
        cartera.setFechaCreacion(comprobante.getFechaCreacion());
        cartera.setFechaEmision(comprobante.getFechaEmision());
        cartera.setPuntoEmision(comprobante.getPuntoEmision());
        cartera.setPuntoEstablecimiento(comprobante.getPuntoEstablecimiento());
        cartera.setSecuencial(comprobante.getSecuencial());
        cartera.setTipoCartera(tipo.getLetra());
        cartera.setEstado(GeneralEnumEstado.ACTIVO.getEstado());

        DocumentoEnum documentoEnum = comprobante.getCodigoDocumentoEnum();
        //TODO: Mandar una alerta o una excepcion cuando no este configurado para algun documento
        switch (documentoEnum) {
            case NOTA_VENTA:
            case FACTURA:
                Factura factura = (Factura) comprobante;
                cartera.setPersona(factura.getCliente());
                cartera.setReferenciaID(factura.getId());
                cartera.setSaldo(factura.getTotal());
                cartera.setTotal(factura.getTotal());
                
                for (FacturaDetalle detalle : factura.getDetalles()) {
                    CarteraDetalle carteraDetalle=new CarteraDetalle();
                    carteraDetalle.setDescripcion(detalle.getDescripcion());
                    carteraDetalle.setSaldo(detalle.getTotal());
                    carteraDetalle.setTotal(detalle.getTotal());
                    cartera.addDetalle(carteraDetalle);
                }
                break;

            case RETENCIONES:
                Retencion retencion = (Retencion) comprobante;
                cartera.setPersona(retencion.getProveedor());
                cartera.setReferenciaID(retencion.getId());
                cartera.setSaldo(retencion.obtenerTotalNotaCredito());
                cartera.setTotal(retencion.obtenerTotalNotaCredito());
                
                for (RetencionDetalle detalle : retencion.getDetalles()) {
                    CarteraDetalle carteraDetalle=new CarteraDetalle();
                    carteraDetalle.setDescripcion(detalle.getCodigoRetencionSri()+"/"+detalle.getRetencion());
                    carteraDetalle.setSaldo(detalle.getValorRetenido());
                    carteraDetalle.setTotal(detalle.getValorRetenido());
                    cartera.addDetalle(carteraDetalle);
                }
                break;

            case NOTA_CREDITO:
                NotaCredito notaCredito = (NotaCredito) comprobante;
                cartera.setPersona(notaCredito.getCliente());
                cartera.setReferenciaID(notaCredito.getId());
                cartera.setSaldo(notaCredito.getTotal());
                cartera.setTotal(notaCredito.getTotal());
                
                for (NotaCreditoDetalle detalle : notaCredito.getDetalles()) {
                    CarteraDetalle carteraDetalle=new CarteraDetalle();
                    carteraDetalle.setDescripcion(detalle.getDescripcion());
                    carteraDetalle.setSaldo(detalle.getTotal());
                    carteraDetalle.setTotal(detalle.getTotal());
                    cartera.addDetalle(carteraDetalle);
                }
                
                break;
        }
        
        /**
         * Grabar la cartera y cartera detalle generado
         */
        
        for (CarteraDetalle carteraDetalle : cartera.getDetalles()) 
        {
            entityManager.persist(carteraDetalle);
        }
        entityManager.persist(cartera);
            

    }
}
