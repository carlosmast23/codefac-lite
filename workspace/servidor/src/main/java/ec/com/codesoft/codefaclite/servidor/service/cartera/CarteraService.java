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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
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
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class CarteraService extends ServiceAbstract<Cartera,CarteraFacade> implements CarteraServiceIf{
    
    CarteraFacade carteraFacade;
    
    public CarteraService() throws RemoteException {
        super(CarteraFacade.class);
        carteraFacade = new CarteraFacade();
    }
    
    public List<CarteraCruce> consultarMovimientoCartera(Persona persona) throws java.rmi.RemoteException
    {
        return getFacade().getMovimientoCartera(persona);
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
                 
                //Actuaizar Saldo de las entidades de cartera afectada en los cruces
                actualizarSaldosCarteraSinTrasaccion(cruces);
                
            }
        });
        
        return cartera;
    }
    
    private void actualizarSaldosCarteraSinTrasaccion(List<CarteraCruce> cruces)
    {
        if(cruces.size()>0)
        {
            //TODO: En los 2 casos asumo que los cruces siempre son con 2 carteras , pero analizar si pueden haber mas de 2 carteras que esten siendo afectadas
            Cartera carteraAfectada=cruces.get(0).getCarteraAfectada(); //Solo busco el primer dato de la cartera que afecta porque en los demas debe apuntar al mismo
            Cartera carteraQueAfecta= cruces.get(0).getCarteraDetalle().getCartera();
            
            ///Generar el valor del saldo 
            BigDecimal valorCruzadoCarteraAfectada=  getFacade().obtenerValorCruceCarteraAfecta(carteraAfectada);
            carteraAfectada.setSaldo(carteraAfectada.getTotal().subtract(valorCruzadoCarteraAfectada));            
            entityManager.merge(carteraAfectada);
            
            //Generar el valor del saldo del documento que esta afectando
            BigDecimal valorCruzadoCarteraQueAfecta=  getFacade().obtenerValorCruceCarteraAfectados(carteraQueAfecta);
            carteraQueAfecta.setSaldo(carteraQueAfecta.getTotal().subtract(valorCruzadoCarteraQueAfecta));            
            entityManager.merge(carteraQueAfecta);
            
            
            //Modificar los saldos de los detalles
            for (CarteraCruce cruce : cruces) 
            {
                //Recalcular los saldo de cada detalle
                BigDecimal valorCruzadoDetalle=getFacade().obtenerValorCruceCarteraDetalle(cruce.getCarteraDetalle());
                cruce.getCarteraDetalle().setSaldo(cruce.getCarteraDetalle().getTotal().subtract(valorCruzadoDetalle));
                entityManager.merge(cruce.getCarteraDetalle());
            }
        }
        
    }
    
    /**
     * Metodo que me permite almacenar los documentos en la tabla de cartera
     */
    public void grabarDocumentoCartera(ComprobanteEntity comprobante,Cartera.TipoCarteraEnum tipo) throws RemoteException 
    {
        ParametroCodefacService parametroCodefacService=new ParametroCodefacService();
        ParametroCodefac parametro=parametroCodefacService.getParametroByNombre(ParametroCodefac.ACTIVAR_CARTERA,comprobante.getEmpresa());
        
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
        cartera.setFechaEmision(new java.sql.Date(comprobante.getFechaEmision().getTime()));
        cartera.setPuntoEmision(comprobante.getPuntoEmision().toString());
        cartera.setPuntoEstablecimiento(comprobante.getPuntoEstablecimiento().toString());
        cartera.setSecuencial(comprobante.getSecuencial());
        cartera.setTipoCartera(tipo.getLetra());
        cartera.setEstado(GeneralEnumEstado.ACTIVO.getEstado());

        DocumentoEnum documentoEnum = comprobante.getCodigoDocumentoEnum();
        //TODO: Mandar una alerta o una excepcion cuando no este configurado para algun documento
        switch (documentoEnum) {
            case NOTA_VENTA_INTERNA:
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
    
    public List<Cartera> listaCarteraSaldoCero(Persona persona, Date fi, Date ff) throws ServicioCodefacException, RemoteException {
        return carteraFacade.getCarteraSaldoCero(persona, fi, ff);
    }
            
}
