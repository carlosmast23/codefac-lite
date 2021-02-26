/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.cartera;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import ec.com.codesoft.codefaclite.servidor.facade.cartera.CarteraFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceConsulta;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidor.service.UtilidadesService;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera.TipoOrdenamientoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CarteraEstadoReporteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ntp.TimeStamp;

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
                
                grabarCarteraSinTransaccion(cartera, cruces,CrudEnum.CREAR);
            }
        });
        
        return cartera;
    }
    
    /**
     * Metodo generico que me permite grabar una cartera 
     * @param cartera
     * @param cruces
     * @throws ServicioCodefacException
     * @throws java.rmi.RemoteException 
     */
    private void grabarCarteraSinTransaccion(Cartera cartera,List<CarteraCruce> cruces,CrudEnum crudEnum) throws ServicioCodefacException,java.rmi.RemoteException
    {
        /**
         * ===========================================================
         *                   VALIDAR LA CARTERA
         * ===========================================================
         */        
        validacionCartera(cartera,cruces);
        
        //TODO: Solucion para no tener problemas con las referencias de datos similares
        //TODO: Analizar el por que se hace esto ??????????
        clonarCruces(cruces);
        
        /**
         * =================================================================
         * OBTENER EL NUEVO CODIGO DE LA CARTERA
         * =================================================================
         */
        if(crudEnum.equals(CrudEnum.CREAR))
        {
            String codigoCartera = generarCodigoCartera(cartera.getSucursal(), cartera.getCodigoDocumento());
            cartera.setCodigo(codigoCartera);
        }

        /**
         * ==================================================================
         *              GRABAR LOS DETALLES Y CRUCES NUEVOS
         * ==================================================================
         */
        grabarDetallesCarteraSinTransaccion(cartera, cruces);
        
       
        //Actuaizar Saldo de las entidades de cartera afectada en los cruces
        actualizarSaldosCarteraSinTrasaccion(cruces);        
        
        //TODO:Metodo temporal para actualizar las referencias de los cruces y que esten actualizadas las listas que tienen referencias
        actualizarReferenciasCartera(cartera);
    }
    
    private void grabarDetallesCarteraSinTransaccion(Cartera cartera,List<CarteraCruce> cruces) throws ServicioCodefacException,java.rmi.RemoteException
    {
        //BigDecimal valorCruzadoCarteraQueAfecta=  getFacade().obtenerValorCruceCarteraAfectados(cartera);
        List<CarteraDetalle> detallesOriginalTemporalCartera=cartera.getDetalles();
        cartera.setDetalles(null); //Elimino temporalmente los detalles para no grabar 
        cartera.setCruces(null); //Anulo la referencia a los cruces por que se estan grabando 2 veces los cruces
        
        //Grabo o edito la cartera dependiendo si tiene o no un id generado porque necesito para enlazar los detalles
        if(cartera.getId()==null)
        {           
            entityManager.persist(cartera);
        }else
        {
            entityManager.merge(cartera);
        }
        
        //TODO: Actualizo las referencias de los datos guardos
        entityManager.flush();
        //valorCruzadoCarteraQueAfecta=  getFacade().obtenerValorCruceCarteraAfectados(cartera);

        
         Map<Long, CarteraDetalle> mapDetallesGrabados = new HashMap<Long, CarteraDetalle>();
         
        //grabar los detalles de la cartera
        
        long idAuxiliar=-1;        
        List<CarteraDetalle> detallesCarteraTmp=new ArrayList<CarteraDetalle>();
        
        for (CarteraDetalle detalle : detallesOriginalTemporalCartera) {
                                    
            /**
             * Esto funciona de esta manera porque cuando se usan cruces tienes ids negativos auxiliares , pero cuando vienen desde otro modulos tiene id null porque son nuevos
             */
            Long idTemporal=idAuxiliar--;// en caso de no tener un id porque no viene de cruces le genero uno automatico
            if(detalle.getId()!=null)
            {
                idTemporal=detalle.getId(); //Si tiene previamente un id utilizo para luego enlazar los cruces
            }
            else
            {
                detalle.setId(idTemporal); //Si no tiene ningun id le grabo un negativo temporal
            }                        
            
            if(idTemporal<0)
            {
                CarteraDetalle carteraDetalleTmp= detalle.clone(); //Grabo una copia del detalle para saber como enlazar con los id temporales en los cruces
                carteraDetalleTmp.setId(null); //Queda en null para poder grabar con un nuevo Id en la base de datos
                entityManager.persist(carteraDetalleTmp); //grabo el nuevo detalle
                entityManager.flush();
                //Grabar los antiguos id con los nuevos que despues me sirve para poder grabar los cruces
                mapDetallesGrabados.put(idTemporal, carteraDetalleTmp);
                
                //granbo en una nueva lista los datos persistentes para luego enlazar con la cartera
                detallesCarteraTmp.add(carteraDetalleTmp);
                
            }
            else
            {
                mapDetallesGrabados.put(detalle.getId(),detalle); //Si ya existe grabado el detallo solo agrego al mapa para luego terminazar de hacer los cruces
            }

        }
        
        //valorCruzadoCarteraQueAfecta=  getFacade().obtenerValorCruceCarteraAfectados(cartera);
        
        //Grabo la nueva referencia de los datos ya modificados
        cartera.setDetalles(detallesCarteraTmp);

        //Grabar los cruces con al referencia de los nuevos detalles ya grabados
        for (CarteraCruce carteraCruce : cruces) 
        {
            CarteraDetalle carteraDetalleGrabada = mapDetallesGrabados.get(carteraCruce.getCarteraDetalle().getId());
            
            //Solo grabo una nueva referencia si es distinta de null porque puede ser que este enviando una cartera que ya estaba grabada
            if(carteraDetalleGrabada!=null)
            {
                carteraCruce.setCarteraDetalle(carteraDetalleGrabada);
            }
            
            //Despues de terminar de actualizar las referencias de los cruces edito o grabo segun el caso
            if(carteraCruce.getId()==null)
            {
                entityManager.persist(carteraCruce); //Si no existe la referencia solo le grabo
                entityManager.flush();
            }
            else
            {
                entityManager.merge(carteraCruce); //Si existe la referencia solo le edito
            }
        }
        //valorCruzadoCarteraQueAfecta=  getFacade().obtenerValorCruceCarteraAfectados(cartera);
        
    }
    
    private void  clonarCruces(List<CarteraCruce> cruces) throws RemoteException, ServicioCodefacException
    {
        for (CarteraCruce cruce : cruces) 
        {
            if(cruce.getCarteraDetalle().getId()==null || cruce.getCarteraDetalle().getId()<0 )
            {
                cruce.setCarteraDetalle(cruce.getCarteraDetalle().clone());
            }
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
    
    
    /**
     * Metodo que permite actualizar las nuevas referencias de cruces para tener actualizada en las listas de las rferencias
     * @param cartera
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
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
    
    /**
     * Validaciones generales que toda cartera debe cumplir para poder grabar
     * @param cartera
     * @param cruces
     * @throws ServicioCodefacException
     * @throws java.rmi.RemoteException 
     */
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
    
    /**
     * Metodo que me permite actualizar los saldos de los cruces
     * @param cruces 
     */
    private void actualizarSaldosCarteraSinTrasaccion(List<CarteraCruce> cruces) throws ServicioCodefacException
    {
        if(cruces.size()>0)
        {
            for (CarteraCruce cruce : cruces) 
            {
                //TODO: En los 2 casos asumo que los cruces siempre son con 2 carteras , pero analizar si pueden haber mas de 2 carteras que esten siendo afectadas
                Cartera carteraAfectada=cruce.getCarteraAfectada(); //Solo busco el primer dato de la cartera que afecta porque en los demas debe apuntar al mismo
                Cartera carteraQueAfecta= cruce.getCarteraDetalle().getCartera();
                
                ///Generar el valor del saldo 
                BigDecimal valorCruzadoCarteraAfectada=  getFacade().obtenerValorCruceCarteraAfecta(carteraAfectada);
                BigDecimal saldocarteraAfectada=carteraAfectada.getTotal().subtract(valorCruzadoCarteraAfectada);
                validarSaldoNegativo(saldocarteraAfectada);
                carteraAfectada.setSaldo(carteraAfectada.getTotal().subtract(valorCruzadoCarteraAfectada));            
                entityManager.merge(carteraAfectada);

                //Generar el valor del saldo del documento que esta afectando
                BigDecimal valorCruzadoCarteraQueAfecta=  getFacade().obtenerValorCruceCarteraAfectados(carteraQueAfecta);
                BigDecimal saldoCarteraQueAfecta=carteraQueAfecta.getTotal().subtract(valorCruzadoCarteraQueAfecta);
                validarSaldoNegativo(saldoCarteraQueAfecta);
                carteraQueAfecta.setSaldo(saldoCarteraQueAfecta);            
                entityManager.merge(carteraQueAfecta);
            }
            
            //Modificar los saldos de los detalles
            for (CarteraCruce cruce : cruces) 
            {
                //Recalcular los saldo de cada detalle
                BigDecimal valorCruzadoDetalle=getFacade().obtenerValorCruceCarteraDetalle(cruce.getCarteraDetalle());
                BigDecimal saldoCarteraDetalle=cruce.getCarteraDetalle().getTotal().subtract(valorCruzadoDetalle);
                validarSaldoNegativo(saldoCarteraDetalle);
                cruce.getCarteraDetalle().setSaldo(saldoCarteraDetalle);
                entityManager.merge(cruce.getCarteraDetalle());
                
            }
            
        }
        
    }
    
    private void validarSaldoNegativo(BigDecimal saldo) throws ServicioCodefacException 
    {
        if(saldo.compareTo(BigDecimal.ZERO)<0)
        {
            throw new ServicioCodefacException("Error al procesar la cartera .Motivo: saldo negativo [ "+saldo+" ]");
        }
    }
    
    /**
     * Metodo que me permite almacenar los documentos en la tabla de cartera
     */
    public void grabarDocumentoCartera(ComprobanteEntity comprobante,Cartera.TipoCarteraEnum tipo,CarteraParametro carteraParametro,CrudEnum crudEnum) throws RemoteException, ServicioCodefacException 
    {
        //Si no esta activo el modulo de cartera no continua
        if(!ParametroUtilidades.comparar(comprobante.getEmpresa(), ParametroCodefac.ACTIVAR_CARTERA, EnumSiNo.SI))
        {
            return;
        }
        //CarteraRe
        //comprobante.getFechaEmision()
        Cartera cartera=crearCarteraSinTransaccion(tipo, carteraParametro, comprobante.getSucursalEmpresa(),comprobante.getCodigoDocumento(), comprobante.getFechaCreacion(), comprobante.getFechaEmision(), comprobante.getPuntoEmision().toString(), comprobante.getPuntoEstablecimiento().toString(), comprobante.getSecuencial());
        
        DocumentoEnum documentoEnum = comprobante.getCodigoDocumentoEnum();
        
        List<CarteraCruce> cruces=new ArrayList<CarteraCruce>();
        switch (documentoEnum) {
            case FACTURA_REEMBOLSO:
            case NOTA_VENTA_INTERNA:
            case NOTA_VENTA:
            case FACTURA:
                //if(crudEnum.equals(CrudEnum.CREAR))
                //{
                    crearCarteraFactura(comprobante, cartera, cruces, tipo,carteraParametro);
                //}
                //else if (crudEnum.equals(CrudEnum.ELIMINAR))
                //{
                //    crearCarteraEliminarVenta(comprobante, cartera, cruces, tipo, carteraParametro);
                //}
                break;

            case RETENCIONES:
                crearCarteraRetencion(comprobante, cartera, cruces);                              
                break;

            case NOTA_CREDITO:
                crearCarteraNotaCredito(comprobante, cartera, cruces);
                break;
                
            default:
                throw new ServicioCodefacException("Documento no configurado en cartera");
                
        }
        
        //Grabar el documento con los cruces generados
        
        grabarCarteraSinTransaccion(cartera, cruces,CrudEnum.CREAR);
    }
    
    
    private Cartera crearCarteraSinTransaccion(
            Cartera.TipoCarteraEnum tipo,
            CarteraParametro carteraParametro,
            Sucursal sucursal,
            String codigoDocumento,
            Timestamp fechaCreacion,
            Date fechaEmision,
            String puntoEmision,
            String puntoEstablecimiento,
            Integer secuencial)
    {
    /**
         * ====================================================================
         *      CREANDO EL DOCUMENTO DE LA FACTURA EN LA CARTERA
         * ====================================================================
         */
        Cartera cartera = new Cartera();
        cartera.setCodigoDocumento(codigoDocumento);
        cartera.setFechaCreacion(UtilidadesFecha.getFechaDeTimeStamp(fechaCreacion));
        cartera.setFechaEmision(new java.sql.Date(fechaEmision.getTime()));
        cartera.setPuntoEmision(puntoEmision);
        cartera.setPuntoEstablecimiento(puntoEstablecimiento);
        cartera.setSecuencial(secuencial);
        cartera.setTipoCartera(tipo.getLetra());
        cartera.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
        cartera.setSucursal(sucursal);
        cartera.setDíasCredito((carteraParametro != null) ? carteraParametro.diasCredito : null);
        //Calculando el campo de fecha de credito si esta activado esa opción para facturar
        if (carteraParametro != null && carteraParametro.diasCredito != null) {
            java.util.Date fechaFinCredito = UtilidadesFecha.sumarDiasFecha(
                    cartera.getFechaEmision(),
                    carteraParametro.diasCredito);
            cartera.setFechaFinCredito(UtilidadesFecha.castDateUtilToSql(fechaFinCredito));
        }
        return cartera;
    }
    
    /**
     * Permite crear cruces automaticos cuando no se quiere generar la cuenta por cobrar en la venta
     * @param factura documento facturar
     * @param carteraFactura cartera de la factura
     * @param cruces referencia donde se van a ir grabando los cruces generados
     * @param carteraParametro parametro donde especifica si se debe generar un cruce automatico
     * @throws ServicioCodefacException
     * @throws RemoteException 
     */
    private void crearCrucesFactura(Factura factura,Cartera carteraFactura,List<CarteraCruce> cruces,CarteraParametro carteraParametro) throws ServicioCodefacException, RemoteException
    {
        /**
         * =====================================================================
         * CREAR CRUCE DE LA FACTURA CUANDO SE PAGA CON CREDITO CARTERA
         * Esto aplica cuando se quiere pagar con algun abono de los clientes
         * TODO: MODULO DE FINANCIAMIENTO PERO NO ESTA ACTIVO
         * =====================================================================
         */
        
        FormaPago formaPagoConCartera=factura.buscarFormaPagoConCartera();
        if(formaPagoConCartera!=null)
        {
            try {
                //Verificar que el cliente tiene el saldo disponible para cruzar
                BigDecimal saldoDisponibleCliente=obtenerSaldoDisponibleCruzar(factura.getCliente(),factura.getEmpresa());
                if(saldoDisponibleCliente.compareTo(formaPagoConCartera.getTotal())<0)
                {
                    new ServicioCodefacException("El cliente no tiene suficiente saldo para pagar con cartera");
                }
                
                List<Cartera> carteraAbonos=obtenerCarteraPorCobrar(factura.getCliente(),factura.getEmpresa());
                BigDecimal totalCruzado=BigDecimal.ZERO; //Acumulador para saber hasta cuantos cruces hacer de cartera
                for (Cartera carteraAbono : carteraAbonos) {
                    for (CarteraDetalle detalle : carteraAbono.getDetalles()) 
                    {
                        if(totalCruzado.compareTo(formaPagoConCartera.getTotal())<0)
                        {
                            //Obtener el valor que se puede cruzar
                            BigDecimal valorCruzar=detalle.getSaldo();
                            BigDecimal valorFaltaCruzar=formaPagoConCartera.getTotal().subtract(totalCruzado);
                            if(valorCruzar.compareTo(valorFaltaCruzar)>0)
                            {
                                valorCruzar=valorFaltaCruzar;
                            }
                            
                            //Crear los datos del cruce
                            CarteraCruce cruce = new CarteraCruce();
                            cruce.setCarteraAfectada(carteraFactura);
                            cruce.setCarteraDetalle(detalle);
                            cruce.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                            cruce.setFechaCruce(UtilidadesFecha.getFechaHoy());
                            cruce.setValor(valorCruzar);
                            cruces.add(cruce);
                            //Aumentar el contador del total que falta por cruzar
                            totalCruzado=totalCruzado.add(valorCruzar);                            
                        } else {
                            break; //Si el total ya es igual o superio termino los cruces
                        }
                    }                   
                    
                }
                
                
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CarteraService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(CarteraService.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }    
        
        /**
         * =================================================================
         * CREAR CARTERA DEL RESTO DE FORMAS DE PAGO
         * =================================================================
         * Si esta habilitado el tema de credito no hago ningun cruce
         */        
        if(carteraParametro!=null && carteraParametro.habilitarCredito)
            return;
        
        //Si no esta habilitado se genera un CRUCE AUTOMATICO como ABONO
        List<FormaPago> formasPagoOtros = factura.buscarListaFormasPagoDistintaDeCartera();
        for (FormaPago formaPago : formasPagoOtros) {
                
                //Crear la cartera para los abonos
                Cartera carteraAbono = new Cartera(
                        factura.getCliente(),
                        formaPago.getTotal(),
                        formaPago.getTotal(),
                        factura.getPuntoEstablecimiento().toString(),
                        factura.getPuntoEmision().toString(),
                        DocumentoEnum.ABONOS.getCodigo(),
                        Cartera.TipoCarteraEnum.CLIENTE.getLetra(),
                        factura.getSucursalEmpresa(),
                        factura.getUsuario(),
                        GeneralEnumEstado.ACTIVO);

                //Crear la cartera detalle del abono
                CarteraDetalle carteraDetalleAbono = new CarteraDetalle();
                carteraDetalleAbono.setCartera(carteraAbono);
                carteraDetalleAbono.setCruces(new ArrayList<CarteraCruce>());
                carteraDetalleAbono.setDescripcion("cruce automatica");
                carteraDetalleAbono.setSaldo(formaPago.getTotal());
                carteraDetalleAbono.setTotal(formaPago.getTotal());
                
                carteraAbono.addDetalle(carteraDetalleAbono);
                
                //Grabar la NUEVA CARTERA DEL ABONO
                grabarCarteraSinTransaccion(carteraAbono, new ArrayList<CarteraCruce>(),CrudEnum.CREAR);
                
                
                //TODO: Este artificio toca hacer porque aunque se supone que el detalle debe estar relacionado por referencia al mismo objeto
                //internamienta el metodo grabarCarteraSinTransaccion hace clonar los detalles y se pierde la referencia y los detalles dejan de estar enlazados 
                //TODO: Tambien obtengo el primer dato con la seguridad que para este caso siempre solo agrega un detalle
                carteraDetalleAbono=carteraAbono.getDetalles().get(0);
                
                //grabo la referencia del CRUCE AUTOMATICO con la Factura
                CarteraCruce cruce = new CarteraCruce(formaPago.getTotal(), carteraFactura, carteraDetalleAbono);
                cruces.add(cruce);
            //}
        }
    }
    
    private void crearCarteraFactura(ComprobanteEntity comprobante,Cartera cartera,List<CarteraCruce> cruces,Cartera.TipoCarteraEnum tipo,CarteraParametro carteraParametro) throws ServicioCodefacException, RemoteException
    {
        //Termina de generar los campos restantes para facturas de venta y compra
        //TODO: Unir la misma logica tanto para facturas de venta como de compra
        if (tipo.equals(tipo.CLIENTE)) {
            
            Factura factura = (Factura) comprobante;
            cartera.setPersona(factura.getCliente());
            cartera.setReferenciaID(factura.getId());
            cartera.setSaldo(factura.getTotal());
            cartera.setTotal(factura.getTotal());

            for (FacturaDetalle detalle : factura.getDetalles()) {
                CarteraDetalle carteraDetalle = new CarteraDetalle();
                carteraDetalle.setDescripcion(detalle.getDescripcion());
                carteraDetalle.setSaldo(detalle.getTotal());
                carteraDetalle.setTotal(detalle.getTotal());
                cartera.addDetalle(carteraDetalle);
            }
            
            //Metodo que permite crear los cruces automaticos cuando sea el caso
            crearCrucesFactura(factura, cartera, cruces,carteraParametro);
            
        } else if (tipo.equals(tipo.PROVEEDORES)) {
            
            entityManager.flush();
            Compra compra = (Compra) comprobante;
            cartera.setPersona(compra.getProveedor());
            cartera.setReferenciaID(compra.getId());
            cartera.setSaldo(compra.getTotal());
            cartera.setTotal(compra.getTotal());

            for (CompraDetalle detalle : compra.getDetalles()) {
                CarteraDetalle carteraDetalle = new CarteraDetalle();
                carteraDetalle.setDescripcion(detalle.getDescripcion());
                carteraDetalle.setSaldo(detalle.getTotal());
                carteraDetalle.setTotal(detalle.getTotal());
                cartera.addDetalle(carteraDetalle);
            }
        }
    }
    
    private void crearCarteraEliminarVenta(ComprobanteEntity comprobante,Cartera cartera,List<CarteraCruce> cruces,Cartera.TipoCarteraEnum tipo,CarteraParametro carteraParametro) throws ServicioCodefacException, RemoteException
    {
        //Termina de generar los campos restantes para facturas de venta y compra
        //TODO: Unir la misma logica tanto para facturas de venta como de compra
        if (tipo.equals(tipo.CLIENTE)) {
            
            Factura factura = (Factura) comprobante;
            cartera.setPersona(factura.getCliente());
            cartera.setReferenciaID(factura.getId());
            cartera.setSaldo(factura.getTotal());
            cartera.setTotal(factura.getTotal());

            for (FacturaDetalle detalle : factura.getDetalles()) {
                CarteraDetalle carteraDetalle = new CarteraDetalle();
                carteraDetalle.setDescripcion(detalle.getDescripcion());
                carteraDetalle.setSaldo(detalle.getTotal());
                carteraDetalle.setTotal(detalle.getTotal());
                cartera.addDetalle(carteraDetalle);
            }
            
            //Metodo que permite crear los cruces automaticos cuando sea el caso
            //crearCrucesFactura(factura, cartera, cruces,carteraParametro);
            
        } else if (tipo.equals(tipo.PROVEEDORES)) {
            
            entityManager.flush();
            Compra compra = (Compra) comprobante;
            cartera.setPersona(compra.getProveedor());
            cartera.setReferenciaID(compra.getId());
            cartera.setSaldo(compra.getTotal());
            cartera.setTotal(compra.getTotal());

            for (CompraDetalle detalle : compra.getDetalles()) {
                CarteraDetalle carteraDetalle = new CarteraDetalle();
                carteraDetalle.setDescripcion(detalle.getDescripcion());
                carteraDetalle.setSaldo(detalle.getTotal());
                carteraDetalle.setTotal(detalle.getTotal());
                cartera.addDetalle(carteraDetalle);
            }
        }
                
    }
    
    private void crearCarteraNotaCredito(ComprobanteEntity comprobante,Cartera cartera,List<CarteraCruce> cruces)
    {
        try {
            NotaCredito notaCredito = (NotaCredito) comprobante;
            cartera.setPersona(notaCredito.getCliente());
            cartera.setReferenciaID(notaCredito.getId());
            cartera.setSaldo(notaCredito.getTotal());
            cartera.setTotal(notaCredito.getTotal());
            
            /**
             * ==========================================================================
             * Buscar la factura de la cartera para poder hacer el cruce
             * ==========================================================================
             */
            CarteraService carteraService = new CarteraService();
            //carteraService.buscarCarteraPorReferencia(Long.MIN_VALUE, documentoEnum, GeneralEnumEstado.ACTIVO, tipo, sucursal)
            Cartera carteraFactura = null;
            if (notaCredito.getFactura() != null) {
                carteraFactura = carteraService.buscarCarteraPorReferencia(
                        notaCredito.getFactura().getId(),
                        notaCredito.getFactura().getCodigoDocumentoEnum(),
                        GeneralEnumEstado.ACTIVO,
                        Cartera.TipoCarteraEnum.CLIENTE,
                        notaCredito.getSucursalEmpresa());
            }
            
            for (NotaCreditoDetalle detalle : notaCredito.getDetalles()) {
                CarteraDetalle carteraDetalle = new CarteraDetalle();
                carteraDetalle.setDescripcion(detalle.getDescripcion());
                carteraDetalle.setSaldo(detalle.getTotal());
                carteraDetalle.setTotal(detalle.getTotal());
                carteraDetalle.setId(carteraDetalle.hashCode() * -1l);
                cartera.addDetalle(carteraDetalle);
                
                /**
                 * ==========================================================================
                 * CREAR EL CRUCE DE LA FACTURA
                 * ==========================================================================
                 * Solo hacer un cruce si existe la referencia de la factura en el
                 * sistema
                 */
                if (carteraFactura != null) {
                    CarteraCruce carteraCruceRenta = new CarteraCruce();
                    carteraCruceRenta.setCarteraAfectada(carteraFactura);
                    carteraCruceRenta.setCarteraDetalle(carteraDetalle);
                    carteraCruceRenta.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                    carteraCruceRenta.setFechaCruce(UtilidadesFecha.getFechaHoy());
                    carteraCruceRenta.setValor(detalle.calcularTotalFinal());
                    cruces.add(carteraCruceRenta);
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(CarteraService.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    private void crearCarteraAnularCompra(ComprobanteEntity comprobante,Cartera cartera,List<CarteraCruce> cruces)
    {
        try {
            NotaCredito notaCredito = (NotaCredito) comprobante;
            cartera.setPersona(notaCredito.getCliente());
            cartera.setReferenciaID(notaCredito.getId());
            cartera.setSaldo(notaCredito.getTotal());
            cartera.setTotal(notaCredito.getTotal());
            
            /**
             * ==========================================================================
             * Buscar la factura de la cartera para poder hacer el cruce
             * ==========================================================================
             */
            CarteraService carteraService = new CarteraService();
            //carteraService.buscarCarteraPorReferencia(Long.MIN_VALUE, documentoEnum, GeneralEnumEstado.ACTIVO, tipo, sucursal)
            Cartera carteraFactura = null;
            if (notaCredito.getFactura() != null) {
                carteraFactura = carteraService.buscarCarteraPorReferencia(
                        notaCredito.getFactura().getId(),
                        notaCredito.getFactura().getCodigoDocumentoEnum(),
                        GeneralEnumEstado.ACTIVO,
                        Cartera.TipoCarteraEnum.CLIENTE,
                        notaCredito.getSucursalEmpresa());
            }
            
            for (NotaCreditoDetalle detalle : notaCredito.getDetalles()) {
                CarteraDetalle carteraDetalle = new CarteraDetalle();
                carteraDetalle.setDescripcion(detalle.getDescripcion());
                carteraDetalle.setSaldo(detalle.getTotal());
                carteraDetalle.setTotal(detalle.getTotal());
                carteraDetalle.setId(carteraDetalle.hashCode() * -1l);
                cartera.addDetalle(carteraDetalle);
                
                /**
                 * ==========================================================================
                 * CREAR EL CRUCE DE LA FACTURA
                 * ==========================================================================
                 * Solo hacer un cruce si existe la referencia de la factura en el
                 * sistema
                 */
                if (carteraFactura != null) {
                    CarteraCruce carteraCruceRenta = new CarteraCruce();
                    carteraCruceRenta.setCarteraAfectada(carteraFactura);
                    carteraCruceRenta.setCarteraDetalle(carteraDetalle);
                    carteraCruceRenta.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                    carteraCruceRenta.setFechaCruce(UtilidadesFecha.getFechaHoy());
                    carteraCruceRenta.setValor(detalle.calcularTotalFinal());
                    cruces.add(carteraCruceRenta);
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(CarteraService.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    
    private void crearCarteraRetencion(ComprobanteEntity comprobante,Cartera cartera,List<CarteraCruce> cruces)
    {
        Retencion retencion = (Retencion) comprobante;
        cartera.setPersona(retencion.getProveedor());
        cartera.setReferenciaID(retencion.getId());

        BigDecimal retencionIva = retencion.getTotalValorRetenido(SriRetencionRenta.CODIGO_RETENCION_IVA);
        BigDecimal retencionRenta = retencion.getTotalValorRetenido(SriRetencionRenta.CODIGO_RETENCION_RENTA);
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
        CarteraDetalle carteraDetallIva = new CarteraDetalle();
        carteraDetallIva.setDescripcion("Retención del iva");
        carteraDetallIva.setSaldo(retencionIva);
        carteraDetallIva.setTotal(retencionIva);
        cartera.addDetalle(carteraDetallIva);
        
        //persistir las entidades creadas
        entityManager.persist(cartera);
        entityManager.persist(carteraDetallIva);
        entityManager.persist(carteraDetalleRenta);
        entityManager.flush();

        /**
         * CONSULTAR LA CARTERA QUE AFECTA DE UNA RETENCION
         */
        if (retencion.getCompra() != null) {
            Cartera carteraCompra = buscarCarteraPorReferencia(
                    retencion.getCompra().getId(),
                    DocumentoEnum.FACTURA,
                    GeneralEnumEstado.ACTIVO,
                    Cartera.TipoCarteraEnum.PROVEEDORES,
                    cartera.getSucursal());

            if (carteraCompra != null) {
                /**
                 * Generar el cruce de la cartera con la renta
                 */
                CarteraCruce carteraCruceRenta = new CarteraCruce();
                carteraCruceRenta.setCarteraAfectada(carteraCompra);
                carteraCruceRenta.setCarteraDetalle(carteraDetalleRenta);
                carteraCruceRenta.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                carteraCruceRenta.setFechaCruce(UtilidadesFecha.getFechaHoy());
                carteraCruceRenta.setValor(retencionRenta);
                cruces.add(carteraCruceRenta);

                /**
                 * Generar el cruce de la cartera con el iva
                 */
                CarteraCruce carteraCruceIva = new CarteraCruce();
                carteraCruceIva.setCarteraAfectada(carteraCompra);
                carteraCruceIva.setCarteraDetalle(carteraDetalleRenta);
                carteraCruceIva.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                carteraCruceIva.setFechaCruce(UtilidadesFecha.getFechaHoy());
                carteraCruceIva.setValor(retencionIva);
                cruces.add(carteraCruceIva);
            }
        }
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
    public List<Cartera> listaCarteraSaldoCero(Persona persona, Date fi, Date ff,DocumentoCategoriaEnum categoriaMenuEnum,Cartera.TipoCarteraEnum tipoCartera,Cartera.TipoSaldoCarteraEnum tipoSaldoEnum,TipoOrdenamientoEnum tipoOrdenamientoEnum,CarteraEstadoReporteEnum carteraEstadoReporteEnum,Sucursal sucursal) throws ServicioCodefacException, RemoteException {
        return carteraFacade.getCarteraSaldoCero(persona, fi, ff,categoriaMenuEnum,tipoCartera,tipoSaldoEnum,tipoOrdenamientoEnum,carteraEstadoReporteEnum,sucursal);
    }
    
    /*public List<Cartera> listaCartera(Empresa empresa,Date fechaInicial,Date fechaFinal,DocumentoCategoriaEnum categoriaMenuEnum,Cartera.TipoCarteraEnum tipoCartera,)
    {
        
    }*/

    
    public void editar(Cartera entity,List<CarteraCruce> cruces) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //Falta agregar validaciones porque no siempre se puede editar cualquier dato
                grabarCarteraSinTransaccion(entity, cruces,CrudEnum.EDITAR);
            }
        });
    }
    
    
    private void procesarEliminacionesSinTransaccion(Cartera cartera,List<CarteraCruce> cruces) throws RemoteException
    {        
        CarteraDetalleService serviceDetalle=new CarteraDetalleService();
        
        List<CarteraDetalle> detalleEditado=cartera.getDetalles();
        List<CarteraDetalle> detalleEliminados=serviceDetalle.consultarPorcartera(cartera);
        
        //quitar los detalles editados para si existe alguno eliminado
        detalleEliminados.removeAll(detalleEditado);
        
        //Si el detalle es mayor que cero entonces se tiene que eliminar
        if(detalleEliminados.size()>0)
        {
            
        }        
    }
    
    /**
     * Eliminar el detalle de una cartera
     * @param carteraDetalle 
     */
    private void eliminarDetalleCartera(CarteraDetalle carteraDetalle)
    {
        carteraDetalle.getCartera();
    }
    

    @Override
    public void eliminar(Cartera entity,ModoProcesarEnum modo) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() 
        {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                eliminarCarteraSinTransaccion(entity, modo);
                
            }
        });
    }
    
    public void eliminarCarteraSinTransaccion(Cartera entity,ModoProcesarEnum modo) throws ServicioCodefacException, RemoteException 
    {
        if (modo.NORMAL.equals(modo)) {
            if (entity.getCruces() != null && entity.getCruces().size() > 0) {
                throw new ServicioCodefacException("No se puede eliminar el documento porque le afectan cruces");
            }

            for (CarteraDetalle detalle : entity.getDetalles()) {
                if (detalle.getCruces() != null && detalle.getCruces().size() > 0) {
                    throw new ServicioCodefacException("No se puede eliminar el documento porque afecta cruces a otro documento");
                }
            }
        }

        //Elimino la cartera principal cambiando de estado           
        entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
        entityManager.merge(entity);

        //TODO: Ver alguna manera de identificar cuales son carteras principales (factura) y cuales son cartera que afectan (abonos)
        quitarCruceCarteraPrincipal(entity);
        quitarCruceCarteraAfectan(entity);
        entityManager.flush();                
                
    }
    
    /**
     * Metodo que me permite recalcular y quitar cruces cuando la cartera eliminada es una principal
     * es decir uan factura de venta o compra , olagun tipo similar
     */
    private void quitarCruceCarteraPrincipal(Cartera cartera) throws RemoteException, ServicioCodefacException
    {
        CarteraCruceService cruceService=new CarteraCruceService();
        //Obtener las relaciones de carteras que le esten afectando, ejemplo: abonos
        List<CarteraCruce> relacionesCarteraAfecta=cruceService.buscarPorCarteraAfecta(cartera);
        
        //Se supone que que si ya tengo los cruces involucrados ya no necesito esta lista
        //Esto lo hago de esta forma por que si existe alguna referencia de los cruces luego no elimina el cruce
        cartera.getCruces().clear();
        entityManager.merge(cartera);
        
        for (CarteraCruce carteraCruce : relacionesCarteraAfecta) {
            CarteraDetalle carteraDetalleAfecta=carteraCruce.getCarteraDetalle();
            
            //Volver a aumentar los saldos de la cartera relacionada
            carteraDetalleAfecta.setSaldo(carteraDetalleAfecta.getSaldo().add(carteraCruce.getValor()));
            carteraDetalleAfecta.getCartera().setSaldo(carteraDetalleAfecta.getCartera().getSaldo().add(carteraCruce.getValor()));
                        
            carteraDetalleAfecta.getCruces().remove(carteraCruce);
            //Actualizar los valores en la base de datos
            entityManager.merge(carteraDetalleAfecta);
            entityManager.merge(carteraDetalleAfecta.getCartera());            
            
            //Eliminar la relacion del cruce en la cartera
            entityManager.remove(carteraCruce);            
        }
    }
    
    private void quitarCruceCarteraAfectan(Cartera cartera) throws RemoteException, ServicioCodefacException
    {
        CarteraCruceService cruceService=new CarteraCruceService();
        for (CarteraDetalle carteraDetallaAfecta : cartera.getDetalles()) {
            //obtener los cruces de esas facturas
            List<CarteraCruce> cruces=cruceService.buscarPorCarteraDetalle(carteraDetallaAfecta);
            for (CarteraCruce cruce : cruces) 
            {
                Cartera carteraPrincipal=cruce.getCarteraAfectada();
                
                //Agregar nuevamente los valores de los saldos tanto a la cartera principal como al detalle
                carteraPrincipal.restaurarSaldo(cruce.getValor());
                
                
                //Actualizo las referencias en la base de datos                
                carteraPrincipal.getCruces().remove(cruce);
                for (CarteraDetalle detalleTmp : carteraPrincipal.getDetalles()) {
                    entityManager.merge(detalleTmp);
                }
                entityManager.merge(carteraPrincipal);

                //Elimino  la referencia del cruce
                entityManager.remove(cruce);
                
            }
        }
        
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
    
    public List<Cartera> obtenerCarteraPorCobrar(Persona cliente,Empresa empresa) throws ServicioCodefacException, RemoteException 
    {
        return (List<Cartera>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                return getFacade().obtenerCarteraPorCobrarFacade(cliente, empresa);
            }
        });
        
    }
    
    public BigDecimal obtenerSaldoDisponibleCruzar(Persona cliente,Empresa empresa) throws ServicioCodefacException, RemoteException 
    {
        return (BigDecimal) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                return getFacade().obtenerSaldoDisponibleCruzarFacade(cliente, empresa);
            }
        });
    }
    
            
}
