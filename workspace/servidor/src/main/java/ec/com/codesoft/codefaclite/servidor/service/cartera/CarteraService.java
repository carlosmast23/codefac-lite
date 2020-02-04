/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.cartera;

import ec.com.codesoft.codefaclite.servidor.facade.cartera.CarteraFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceConsulta;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ParametroCodefacService;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidor.service.UtilidadesService;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NombresEntidadesJPA;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
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
    
    /**
     * TODO: Verificar que los cruces solo deben actualizar ahora directo en el detalle
     * @param cartera
     * @param cruces
     * @return
     * @throws ServicioCodefacException
     * @throws java.rmi.RemoteException 
     */
    public Cartera grabarCartera(Cartera cartera,List<CarteraCruce> cruces) throws ServicioCodefacException,java.rmi.RemoteException
    {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws RemoteException, ServicioCodefacException {
                
                grabarCarteraSinTransaccion(cartera, cruces);
            }
        });
        
        return cartera;
    }
    
    private void grabarCarteraSinTransaccion(Cartera cartera,List<CarteraCruce> cruces) throws ServicioCodefacException,java.rmi.RemoteException
    {
        validacionCartera(cartera,cruces);
        //TODO: Solucion para tener problemas con las referencias de datos similares
        clonarCruces(cruces);
        
        Map<Long, CarteraDetalle> mapDetallesGrabados = new HashMap<Long, CarteraDetalle>();

        //ObtenerCodigoNuevoCartera
        String codigoCartera = generarCodigoCartera(cartera.getSucursal(), cartera.getCodigoDocumento());
        cartera.setCodigo(codigoCartera);

        //grabar los detalles de la cartera
        for (CarteraDetalle detalle : cartera.getDetalles()) {
            Long idTemporal = detalle.getId(); //Valor de id temporal para poder guardar los cruces
            detalle.setId(null); //Esta variable dejo en null para que al grabar genere el nuevo objeto
            entityManager.persist(detalle);

            //Grabar los antiguos id con los nuevos que despues me sirve para poder grabar los cruces
            mapDetallesGrabados.put(idTemporal, detalle);
        }

        //Grabar los cruces con al referencia de los nuevos detalles ya grabados
        for (CarteraCruce carteraCruce : cruces) {
            CarteraDetalle carteraDetalleGrabada = mapDetallesGrabados.get(carteraCruce.getCarteraDetalle().getId());
            carteraCruce.setCarteraDetalle(carteraDetalleGrabada);
            entityManager.persist(carteraCruce);

            ////Actualizar en los detalles de la cartera TODO: Revisar
            //carteraCruce.getCarteraAfectada().getCruces().add(carteraCruce);
        }

        //Actualizar reerencias de los cruces
        //grabar la cartera
        entityManager.persist(cartera);

        //Actuaizar Saldo de las entidades de cartera afectada en los cruces
        actualizarSaldosCarteraSinTrasaccion(cruces);

        //TODO:Metodo temporal para actualizar las referencias de los cruces
        actualizarReferenciasCartera(cartera);
    }
    
    private void  clonarCruces(List<CarteraCruce> cruces) throws RemoteException, ServicioCodefacException
    {
        for (CarteraCruce cruce : cruces) {
            cruce.setCarteraDetalle(cruce.getCarteraDetalle().clone());
        }
    }
    
    private void actualizarReferenciasCartera(Cartera cartera) throws RemoteException, ServicioCodefacException
    {
        actualizarReferenciasCrucesSinTransaccion(cartera);
        for (CarteraDetalle carteraDetalle : cartera.getDetalles()) 
        {
            for (CarteraCruce cruce : carteraDetalle.getCruces()) {
                actualizarReferenciasCrucesSinTransaccion(cruce.getCarteraAfectada());
            }
        }
    }
    
    private void actualizarReferenciasCrucesSinTransaccion(Cartera cartera) throws RemoteException, ServicioCodefacException
    {
        CarteraCruceService carteraCruceService=new CarteraCruceService();
        
        //Actualizar los detalles de las carteras
        CarteraDetalleService carteraDetalleService=new CarteraDetalleService();
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("cartera",cartera);
        List<CarteraDetalle> detallesCartera=carteraDetalleService.obtenerPorMap(mapParametros);
        cartera.setDetalles(detallesCartera);
        
        //Actualizar cruces de las carteraas
        List<CarteraCruce> crucesAfecta=carteraCruceService.buscarPorCarteraAfecta(cartera);
        cartera.setCruces(crucesAfecta);
        
        //Actualizar los cruces de los detalles de los carteras
        for (CarteraDetalle detalle : cartera.getDetalles()) {
            List<CarteraCruce> cruces=carteraCruceService.buscarPorCarteraDetalle(detalle);
            detalle.setCruces(cruces);
        }
        
        entityManager.merge(cartera);
        
    }
    
    private String generarCodigoCartera(Sucursal sucursal,String codigoDocumento) throws RemoteException, ServicioCodefacException
    {
        UtilidadesService utilidadesService=new UtilidadesService();
        String codigo=utilidadesService.crearCodigoPorEmpresaYSucursalSinTransaccion(sucursal,codigoDocumento,Cartera.class.getSimpleName());
        return codigo;        
    }
    
    private void validacionCartera(Cartera cartera,List<CarteraCruce> cruces) throws ServicioCodefacException,java.rmi.RemoteException 
    {
        if(cartera.getDetalles()==null || cartera.getDetalles().size()==0)
        {
            throw new ServicioCodefacException("No se puede grabar con detalles vacios"); 
        }
        
        if(cartera.getPersona()==null)
        {
            throw new ServicioCodefacException("No se puede grabar la cartera sin un cliente o proveedor asignado"); 
        }
        
        if(cartera.getSucursal()==null)
        {
            throw new ServicioCodefacException("No se puede grabar la cartera sin asignar una sucursal"); 
        }
            
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
    public void grabarDocumentoCartera(ComprobanteEntity comprobante,Cartera.TipoCarteraEnum tipo) throws RemoteException, ServicioCodefacException 
    {
        //Si no esta activo el modulo de cartera no continua
        if(!ParametroUtilidades.comparar(comprobante.getEmpresa(), ParametroCodefac.ACTIVAR_CARTERA, EnumSiNo.SI))
        {
            return;
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
        cartera.setSucursal(comprobante.getSucursalEmpresa());
        

        DocumentoEnum documentoEnum = comprobante.getCodigoDocumentoEnum();
        //TODO: Mandar una alerta o una excepcion cuando no este configurado para algun documento
        List<CarteraCruce> cruces=new ArrayList<CarteraCruce>();
        switch (documentoEnum) {
            case NOTA_VENTA_INTERNA:
            case NOTA_VENTA:
            case FACTURA:
                //TODO: Unir la misma logica tanto para facturas de venta como de compra
                if(tipo.equals(tipo.CLIENTE))
                {
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
                }else if(tipo.equals(tipo.PROVEEDORES))
                {
                    entityManager.flush();
                    Compra compra = (Compra) comprobante;
                    cartera.setPersona(compra.getProveedor());
                    cartera.setReferenciaID(compra.getId());
                    cartera.setSaldo(compra.getTotal());
                    cartera.setTotal(compra.getTotal());

                    for (CompraDetalle detalle : compra.getDetalles()) {
                        CarteraDetalle carteraDetalle=new CarteraDetalle();
                        carteraDetalle.setDescripcion(detalle.getDescripcion());
                        carteraDetalle.setSaldo(detalle.getTotal());
                        carteraDetalle.setTotal(detalle.getTotal());
                        cartera.addDetalle(carteraDetalle);
                    }
                }
                
                break;

            case RETENCIONES:
                
                Retencion retencion = (Retencion) comprobante;
                cartera.setPersona(retencion.getProveedor());
                cartera.setReferenciaID(retencion.getId());
                
                BigDecimal retencionIva=retencion.getTotalValorRetenido(SriRetencionRenta.CODIGO_RETENCION_IVA);
                BigDecimal retencionRenta=retencion.getTotalValorRetenido(SriRetencionRenta.CODIGO_RETENCION_RENTA);
                cartera.setSaldo(retencionIva.add(retencionRenta));
                cartera.setTotal(retencionIva.add(retencionRenta));
                
                /**
                 * RETENCION DE LA RENTA
                 */
                CarteraDetalle carteraDetalleRenta = new CarteraDetalle();
                carteraDetalleRenta.setDescripcion("Retención de la renta");
                carteraDetalleRenta.setSaldo(retencionRenta);
                carteraDetalleRenta.setTotal(retencionRenta);
                cartera.addDetalle(carteraDetalleRenta);
                
                /**
                 * RETENCION DEL IVA
                 */
                CarteraDetalle carteraDetallIva= new CarteraDetalle();
                carteraDetallIva.setDescripcion("Retención del iva");
                carteraDetallIva.setSaldo(retencionIva);
                carteraDetallIva.setTotal(retencionIva);
                cartera.addDetalle(carteraDetallIva);
                
                /**
                 * CONSULTAR LA CARTERA QUE AFECTA DE UNA RETENCION
                 */
                if(retencion.getCompra()!=null)
                {
                    Cartera carteraCompra=buscarCarteraPorReferencia(
                            retencion.getCompra().getId(),
                            DocumentoEnum.FACTURA, 
                            GeneralEnumEstado.ACTIVO,
                            Cartera.TipoCarteraEnum.PROVEEDORES,
                            cartera.getSucursal());
                    

                    if(carteraCompra!=null)
                    {
                        /**
                         * Generar el cruce de la cartera con la renta
                         */
                        CarteraCruce carteraCruceRenta=new CarteraCruce();
                        carteraCruceRenta.setCarteraAfectada(carteraCompra);
                        carteraCruceRenta.setCarteraDetalle(carteraDetalleRenta);
                        carteraCruceRenta.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                        carteraCruceRenta.setFechaCruce(UtilidadesFecha.getFechaHoy());
                        carteraCruceRenta.setValor(retencionRenta);
                        cruces.add(carteraCruceRenta);

                        /**
                         * Generar el cruce de la cartera con el iva
                         */
                        CarteraCruce carteraCruceIva=new CarteraCruce();
                        carteraCruceIva.setCarteraAfectada(carteraCompra);
                        carteraCruceIva.setCarteraDetalle(carteraDetalleRenta);
                        carteraCruceIva.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                        carteraCruceIva.setFechaCruce(UtilidadesFecha.getFechaHoy());
                        carteraCruceIva.setValor(retencionIva);
                        cruces.add(carteraCruceIva);
                    }
                }
                
                break;

            case NOTA_CREDITO:
                NotaCredito notaCredito = (NotaCredito) comprobante;
                cartera.setPersona(notaCredito.getCliente());
                cartera.setReferenciaID(notaCredito.getId());
                cartera.setSaldo(notaCredito.getTotal());
                cartera.setTotal(notaCredito.getTotal());
                
                /**
                 * ==========================================================================
                 *          Buscar la factura de la cartera para poder hacer el cruce
                 * ==========================================================================
                 */
                CarteraService carteraService=new CarteraService();
                //carteraService.buscarCarteraPorReferencia(Long.MIN_VALUE, documentoEnum, GeneralEnumEstado.ACTIVO, tipo, sucursal)
                Cartera carteraFactura=null;
                if(notaCredito.getFactura()!=null)
                {
                    carteraFactura=carteraService.buscarCarteraPorReferencia(
                            notaCredito.getFactura().getId(),
                            notaCredito.getFactura().getCodigoDocumentoEnum(), 
                            GeneralEnumEstado.ACTIVO, 
                            Cartera.TipoCarteraEnum.CLIENTE, 
                            notaCredito.getSucursalEmpresa());
                }
                
                for (NotaCreditoDetalle detalle : notaCredito.getDetalles()) {
                    CarteraDetalle carteraDetalle=new CarteraDetalle();
                    carteraDetalle.setDescripcion(detalle.getDescripcion());
                    carteraDetalle.setSaldo(detalle.getTotal());
                    carteraDetalle.setTotal(detalle.getTotal());
                    cartera.addDetalle(carteraDetalle);
                                        
                    /**
                     * ==========================================================================
                     * CREAR EL CRUCE DE LA FACTURA
                     * ==========================================================================
                     * Solo hacer un cruce si existe la referencia de la factura en el sistema
                     */
                    if(carteraFactura!=null)
                    {
                        CarteraCruce carteraCruceRenta = new CarteraCruce();
                        carteraCruceRenta.setCarteraAfectada(carteraFactura);
                        carteraCruceRenta.setCarteraDetalle(carteraDetalle);
                        carteraCruceRenta.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                        carteraCruceRenta.setFechaCruce(UtilidadesFecha.getFechaHoy());
                        carteraCruceRenta.setValor(detalle.calcularTotalFinal());
                        cruces.add(carteraCruceRenta);
                    }
                }
                
                
                break;
        }
        
        
        grabarCarteraSinTransaccion(cartera, cruces);

    }
    
    /**
     * Obtiene la cartera que esta pendiente de cancelar
     * @param persona
     * @param fi
     * @param ff
     * @param categoriaMenuEnum
     * @param tipoCartera
     * @return
     * @throws ServicioCodefacException
     * @throws RemoteException 
     */
    public List<Cartera> listaCarteraSaldoCero(Persona persona, Date fi, Date ff,DocumentoCategoriaEnum categoriaMenuEnum,Cartera.TipoCarteraEnum tipoCartera,Boolean carteraConSaldo) throws ServicioCodefacException, RemoteException {
        return carteraFacade.getCarteraSaldoCero(persona, fi, ff,categoriaMenuEnum,tipoCartera,carteraConSaldo);
    }

    @Override
    public void editar(Cartera entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //Falta agregar validaciones porque no siempre se puede editar cualquier dato
                entityManager.merge(entity);
            }
        });
    }
    
    

    @Override
    public void eliminar(Cartera entity,ModoProcesarEnum modo) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() 
        {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                if(entity.getCruces()!=null && entity.getCruces().size()>0)
                {
                    throw new ServicioCodefacException("No se puede eliminar el documentos porque le afectan cruces");
                }
                
                for (CarteraDetalle detalle : entity.getDetalles()) {
                    if(detalle.getCruces()!=null && detalle.getCruces().size()>0)
                    {
                        throw new ServicioCodefacException("No se puede eliminar el documentos porque afecta cruces a otro documento");
                    }
                }
                
                
                entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(entity);
                
                //TODO , Falta implementar para borrar los cruces
            }
        });
    }

    public Cartera buscarCarteraPorReferencia(Long referenciaId,DocumentoEnum documento,GeneralEnumEstado estadoEnum,Cartera.TipoCarteraEnum tipoCarteraEnum,Sucursal sucursal)
    {
        if(referenciaId==null)
        {
            return null;
        }
        //Cartera c;
        //c.getTipoCartera()
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("referenciaID", referenciaId);
        mapParametros.put("codigoDocumento", documento.getCodigo());
        mapParametros.put("estado",estadoEnum.getEstado());
        mapParametros.put("sucursal",sucursal);
        mapParametros.put("tipoCartera",tipoCarteraEnum.getLetra());
        List<Cartera> cartera=getFacade().findByMap(mapParametros);
        if(cartera.size()>0)
        {
            return cartera.get(0);
        }
        return null;
    }
    
    /*public Cartera buscarPorVenta(Factura venta) throws ServicioCodefacException, RemoteException
    {
        return (Cartera) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Cartera c;
                c.getCodigoDocumento();
                c.getReferenciaID();
                
            }
        });
    }*/
    
    
            
}
