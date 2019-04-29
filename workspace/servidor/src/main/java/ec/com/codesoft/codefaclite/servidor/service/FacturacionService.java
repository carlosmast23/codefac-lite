/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaFacade;
import ec.com.codesoft.codefaclite.servidor.service.cartera.CarteraService;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle.EstadoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Carlos
 */
public class FacturacionService extends ServiceAbstract<Factura, FacturaFacade> implements FacturacionServiceIf
{
    

    FacturaFacade facturaFacade;
    FacturaDetalleFacade facturaDetalleFacade;
    ParametroCodefacService parametroService;
    
    public FacturacionService() throws RemoteException {
        super(FacturaFacade.class);
        this.facturaFacade = new FacturaFacade();
        this.facturaDetalleFacade = new FacturaDetalleFacade();
        this.parametroService = new ParametroCodefacService();

    }
    
    public Factura buscarPorPremimpresoYEstado(Integer secuencial,String puntoEstablecimiento,String puntoEmision,ComprobanteEntity.ComprobanteEnumEstado estadoEnum) throws RemoteException
    {
        return getFacade().buscarPorPremimpresoYEstadoFacade(secuencial, puntoEstablecimiento, puntoEmision, estadoEnum);
    }
    
    
    public Factura grabarProforma(Factura proforma) throws RemoteException
    {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() {
                    try
                    {
                        proforma.setCodigoDocumento(DocumentoEnum.PROFORMA.getCodigo());
                        grabarDetallesFactura(proforma);
                        //entityManager.flush(); //Hacer que el nuevo objeto tenga el id para retornar
                    }
                    catch (RemoteException ex)
                    {
                        Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return proforma;
    }
    

    public Factura grabar(Factura factura) throws ServicioCodefacException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                factura.setCodigoDocumento(DocumentoEnum.FACTURA.getCodigo());
                factura.setEstadoEnviadoGuiaRemisionEnum(EnumSiNo.NO);

                ComprobantesService servicioComprobante = new ComprobantesService();
                servicioComprobante.setearSecuencialComprobanteSinTransaccion(factura);            
                grabarDetallesFactura(factura);
                grabarCartera(factura);
            }
        });
        return factura;
        
    }
    
    public void editarFactura(Factura factura) throws ServicioCodefacException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.merge(factura);            
            }
        });        
        
    }
    
    
    private void grabarDetallesFactura(Factura factura) throws RemoteException,PersistenceException
    {
        try
        {
            factura.setEstadoNotaCredito(Factura.EstadoNotaCreditoEnum.SIN_ANULAR.getEstado());            
            //facturaFacade.create(factura);
            entityManager.persist(factura);
            entityManager.flush();
            
            /**
             * Actualizar valores del inventario con el kardex
             */
            for (FacturaDetalle detalle : factura.getDetalles()) {
                //Verificar a que modulo debe afectar los detalles
                switch(detalle.getTipoDocumentoEnum())
                {
                    case ACADEMICO:
                        afectarAcademico(detalle);
                        break;
                    case INVENTARIO:
                        afectarInventario(detalle);
                        break;
                    case LIBRE:
                        //NO DEBE AFECTAR A NADA;
                        break;
                    case PRESUPUESTOS:
                        afectarPresupuesto(detalle);
                        break;
                        
                }
                
            }
        }
        catch (PersistenceException ex) {
            throw ex;
        }
            
    }
    
    private void grabarCartera(Factura factura) throws RemoteException
    {
        //Grabar en la cartera si todo el proceso anterior fue correcto
        CarteraService carteraService = new CarteraService();
        carteraService.grabarDocumentoCartera(factura, Cartera.TipoCarteraEnum.CLIENTE);
    }
    
    private void afectarPresupuesto(FacturaDetalle detalle)
    {
        try {
            PresupuestoService servicio = new PresupuestoService();
            Presupuesto presupuesto = servicio.buscarPorId(detalle.getReferenciaId());
            
            //Cambiar el estado al presupuesto para saber que ya fue facturadp
            presupuesto.setEstado(Presupuesto.EstadoEnum.FACTURADO.getLetra()); 
            
            //Cambiar el estado a la orden de trabajo del detalle para saber que ya no puede usar
            presupuesto.getOrdenTrabajoDetalle().setEstado(OrdenTrabajoDetalle.EstadoEnum.TERMINADO.getLetra());//Cambio el estado a terminado
            
            //Actualiza el estado de la orde de trabajo principal
            OrdenTrabajoService ordenTrabajoService=new OrdenTrabajoService();
            ordenTrabajoService.actualizarEstadoSinTransaccion(presupuesto.getOrdenTrabajoDetalle().getOrdenTrabajo());
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void afectarAcademico(FacturaDetalle detalle)
    {
        try {
            RubroEstudiante rubroEstudiante=ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(detalle.getReferenciaId());
            //El total es sin impuestos
            BigDecimal saldoPendiente=rubroEstudiante.getSaldo().subtract(detalle.getTotal());
            
            //Cuando el saldo es 0 la factura se factura en su totalidad
            if(saldoPendiente.compareTo(BigDecimal.ZERO)==0)
            {
                rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.FACTURADO.getLetra());
                rubroEstudiante.setSaldo(BigDecimal.ZERO);
            }
            else
            {
                rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.FACTURA_PARCIAL.getLetra());
                rubroEstudiante.setSaldo(saldoPendiente);
            }
            
            //Despues de modificar los estados del rubro los modifico segun el caso
            entityManager.merge(rubroEstudiante);
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void afectarInventario(FacturaDetalle detalle)
    {
        try {
            Producto producto=ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(detalle.getReferenciaId());
            
            Map<String,Object> mapParametros=new HashMap<String,Object>();
            mapParametros.put("producto", producto);
            KardexService kardexService=new KardexService();
            List<Kardex> kardexs= kardexService.obtenerPorMap(mapParametros);
            //TODO: Definir especificamente cual es la bodega principal
            if(kardexs!=null && kardexs.size()>0)
            {
                //TODO: Analizar caso cuando se resta un producto especifico
                Kardex kardex= kardexs.get(0);
                KardexDetalle kardexDetalle=new KardexDetalle();
                kardexDetalle.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                kardexDetalle.setFechaIngreso(UtilidadesFecha.getFechaHoy());
                kardexDetalle.setCantidad(detalle.getCantidad().intValue());
                kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.VENTA_INVENTARIO.getCodigo());
                kardexDetalle.setPrecioTotal(detalle.getTotal());
                kardexDetalle.setPrecioUnitario(detalle.getPrecioUnitario());
                kardexDetalle.setReferenciaDocumentoId(detalle.getFactura().getId());
                kardexDetalle.setPuntoEmision(detalle.getFactura().getPuntoEmision());
                kardexDetalle.setPuntoEstablecimiento(detalle.getFactura().getPuntoEstablecimiento());
                kardexDetalle.setSecuencial(detalle.getFactura().getSecuencial());
                
                kardex.addDetalleKardex(kardexDetalle);
                
                //Actualizar los valores del kardex
                kardex.setStock(kardex.getStock() - kardexDetalle.getCantidad());
                //kardex.setPrecioPromedio(kardex.getPrecioPromedio().add(kardexDetalle.getPrecioUnitario()).divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP));
                kardex.setPrecioTotal(kardex.getPrecioTotal().subtract(kardexDetalle.getPrecioTotal()));
                //kardex.setPrecioUltimo(kardexDetalle.getPrecioUnitario());
                
                entityManager.merge(kardex);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public List<Factura> consultaDialogo(String param,int limiteMinimo,int limiteMaximo)
    {
        return facturaFacade.queryDialog(param,limiteMinimo,limiteMaximo);        
    }

    public void editar(Factura factura) {
        facturaFacade.edit(factura);
    }

    public List<Factura> obtenerTodos() {
        return facturaFacade.findAll();
    }

    public List<Factura> obtenerFacturasReporte(Persona persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido) throws java.rmi.RemoteException {
        return facturaFacade.lista(persona,fi,ff,estadEnum,consultarReferidos,referido,agrupadoReferido);
    }

    public List<Factura> obtenerFacturasActivas()
    {
        return facturaFacade.getFacturaEnable();
    }
    
    /*
    public String getPreimpresoSiguiente() {
        try {
            Integer secuencialSiguiente=0;
            //Obtener secuencial cuando es modo electronico
            if(parametroService.getParametroByNombre(ParametroCodefac.TIPO_FACTURACION).valor.equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra()))
            {
                secuencialSiguiente = Integer.parseInt(parametroService.getParametroByNombre(ParametroCodefac.SECUENCIAL_FACTURA).valor);
            }
            else //cuando el modo es normals
            {
                secuencialSiguiente = Integer.parseInt(parametroService.getParametroByNombre(ParametroCodefac.SECUENCIAL_FACTURA_FISICA).valor);
            }
            
            String secuencial = UtilidadesTextos.llenarCarateresIzquierda(secuencialSiguiente.toString(), 8, "0");
            String establecimiento = parametroService.getParametroByNombre(ParametroCodefac.ESTABLECIMIENTO).valor;
            String puntoEmision = parametroService.getParametroByNombre(ParametroCodefac.PUNTO_EMISION).valor;
            return puntoEmision + "-" + establecimiento + "-" + secuencial;
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }*/
    
    public void eliminarFactura(Factura factura)
    {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() {
                    try {
                        ComprobantesService comprobanteService=new ComprobantesService();
                        comprobanteService.eliminarComprobanteSinTransaccion(factura);
                        
                        //factura.setEstado(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado()); //Cambio el estado de las facturas a eliminad
                        entityManager.merge(factura); //actualizar los datos de la factura
                        
                        NotaCreditoService servicioNotaCredito=new NotaCreditoService();
                        for (FacturaDetalle detalle : factura.getDetalles()) {
                            //Anulo los datos segun el tipo de modulo relacionado
                            //servicioNotaCredito.anularProcesoFactura(detalle.getTipoDocumentoEnum(),detalle.getReferenciaId(),detalle.getTotal());
                            servicioNotaCredito.anularProcesoFactura(detalle);
                        }
                        
                    } catch (RemoteException ex) {
                        Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /*
    public Long obtenerSecuencialProforma() throws java.rmi.RemoteException
    {
        return 0;
    }
*/

    @Override
    public List<Factura> obtenerFacturasPorIdentificacion(String identificacion) throws RemoteException {
        //Factura f;
        //f.getIdentificacion();
       Map<String,Object> mapParametros=new HashMap<String,Object>();
       mapParametros.put("identificacion",identificacion);
       return getFacade().findByMap(mapParametros);
    }
    
    @Override
    public Long obtenerSecuencialProformas() throws RemoteException
    {
        Long secuencial=getFacade().getSecuencialProforma();
        return (secuencial!=null)?(secuencial+1):1; //Si no existe ningun valor por defecto retorna 1
    }
    
    public void eliminarProforma(Factura factura) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                factura.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                entityManager.merge(factura);
            }
        });
    }
    
    public List<Factura> consultarProformasReporte(Persona cliente,Date fechaInicial,Date fechaFinal,GeneralEnumEstado estado) throws java.rmi.RemoteException,ServicioCodefacException
    {
        return getFacade().consultarProformasReporteFacade(cliente, fechaInicial, fechaFinal, estado);
    }

}
