/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.NotaCreditoDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.NotaCreditoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class NotaCreditoService extends ServiceAbstract<NotaCredito,NotaCreditoFacade> implements NotaCreditoServiceIf
{

    NotaCreditoFacade notaCreditoFacade;
    NotaCreditoDetalleFacade notaCreditoDetalleFacade;
    ParametroCodefacService parametroCodefacService;

    public NotaCreditoService() throws RemoteException {
        super(NotaCreditoFacade.class);
        this.notaCreditoFacade = new NotaCreditoFacade();
        this.notaCreditoDetalleFacade = new NotaCreditoDetalleFacade();
        parametroCodefacService = new ParametroCodefacService();
    }

    public NotaCredito grabar(NotaCredito notaCredito) throws ServicioCodefacException {      
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {

                   //Validacion para evitar hacer notas de credito al consumidor final lo que no permite el Sri
                if (notaCredito.getCliente().isClienteFinal()) {
                    throw new ServicioCodefacException("No se puede emitir Notas de Crédito al Consumidor Final , Anule la factura en el Portal del Sri");
                }

                notaCredito.setCodigoDocumento(DocumentoEnum.NOTA_CREDITO.getCodigo());

                ComprobantesService servicioComprobante = new ComprobantesService();
                servicioComprobante.setearSecuencialComprobanteSinTransaccion(notaCredito);

                //notaCredito.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
                entityManager.persist(notaCredito);

                /**
                 * Actualizar la logica de cada modulo dependiendo del tipo de
                 * documento de cada detalle
                 */
                for (NotaCreditoDetalle detalle : notaCredito.getDetalles()) {
                    anularProcesoNotCredito(detalle);

                }

                /**
                 * Actualizar el estado de la nota de credito de la factura
                 * dependiendo del tipo anuluacion parcial o total Y validando
                 * si tiene referencia a la factura o fue creada en la modalidad
                 * libre
                 */
                if (notaCredito.getTipoDocumento() != null && notaCredito.getTipoDocumentoEnum().equals(TipoDocumentoEnum.VENTA)) {
                    if (notaCredito.getTotal().compareTo(notaCredito.getFactura().getTotal()) < 0) {
                        notaCredito.getFactura().setEstadoNotaCredito(Factura.EstadoNotaCreditoEnum.ANULADO_PARCIAL.getEstado());
                    } else {
                        notaCredito.getFactura().setEstadoNotaCredito(Factura.EstadoNotaCreditoEnum.ANULADO_TOTAL.getEstado());
                    }
                    //Actualizar la referencia de la factura con el nuevo estado
                    entityManager.merge(notaCredito.getFactura());
                }


            }
        });
        
        return notaCredito;
    }
    
    private void anularRubroEstudiante(Long referenciaId,BigDecimal total) throws RemoteException
    {
        RubroEstudiante rubroEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(referenciaId);
        rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
        rubroEstudiante.setSaldo(rubroEstudiante.getSaldo().add(total));
        entityManager.merge(rubroEstudiante);
    }
    
    private void anularPresupuesto(Long referenciaId) throws RemoteException
    {
        PresupuestoService presupuestoServicio = new PresupuestoService();
        Presupuesto presupuesto = presupuestoServicio.buscarPorId(referenciaId);
        presupuesto.setEstado(Presupuesto.EstadoEnum.ANULADO.getLetra());
        entityManager.merge(presupuesto);
    }
    /**
     * Metodo que me permite anular el proceso adicional que esta relacionado con las documentos como las facturas o notas de credito
     * @param tipoDocumento
     * @param referenciaId
     * @param total
     * @throws RemoteException 
     */
    public void anularProcesoFactura(FacturaDetalle facturaDetalle) throws RemoteException, ServicioCodefacException
    {
        //TipoDocumentoEnum tipoDocumento,Long referenciaId,BigDecimal total
        switch (facturaDetalle.getTipoDocumentoEnum()) {
            case ACADEMICO:
                anularRubroEstudiante(facturaDetalle.getReferenciaId(),facturaDetalle.getTotal());
                break;

            case PRESUPUESTOS:
                anularPresupuesto(facturaDetalle.getReferenciaId());
                break;
                
            case INVENTARIO:
                Bodega bodega=obtenerBodegaAfecta(facturaDetalle.getFactura());
                afectarInventario(
                        bodega,
                        facturaDetalle.getCantidad().intValue(), 
                        facturaDetalle.getPrecioUnitario(),
                        facturaDetalle.getTotal(), 
                        facturaDetalle.getId(),
                        facturaDetalle.getReferenciaId(), 
                        TipoDocumentoEnum.ELIMINADO_FACTURA, 
                        facturaDetalle.getFactura().getPuntoEmision().toString(),
                        facturaDetalle.getFactura().getPuntoEstablecimiento().toString(),
                        facturaDetalle.getFactura().getSecuencial(),
                        facturaDetalle.getFactura().getFechaEmision());
                
                break;
        }
    }
    
    /**
     * TODO:Unir este metodo con Factura Service porque utilizo 2 veces
     * @param comprobante
     * @return
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
    private Bodega obtenerBodegaAfecta(ComprobanteEntity comprobante) throws RemoteException, ServicioCodefacException
    {
        BodegaService bodegaService = new BodegaService();
        Bodega bodegaVenta = bodegaService.obtenerBodegaVenta(comprobante.getSucursalEmpresa());
        if (bodegaVenta == null) {
            throw new ServicioCodefacException("No existe un tipo de Bodega de Venta Configurado");
        }
        return bodegaVenta;
    }
    
    public void anularProcesoNotCredito(NotaCreditoDetalle notaDetalle) throws RemoteException, ServicioCodefacException
    {
        switch (notaDetalle.getTipoDocumentoEnum()) {
            case ACADEMICO:
                anularRubroEstudiante(notaDetalle.getReferenciaId(),notaDetalle.getTotal());
                break;

            case PRESUPUESTOS:
                anularPresupuesto(notaDetalle.getReferenciaId());
                break;
                
            case INVENTARIO:
                Bodega bodega=obtenerBodegaAfecta(notaDetalle.getNotaCredito());
                afectarInventario(
                        bodega,
                        notaDetalle.getCantidad().intValue(), 
                        notaDetalle.getPrecioUnitario(),
                        notaDetalle.getTotal(), 
                        notaDetalle.getId(),
                        notaDetalle.getReferenciaId(), 
                        TipoDocumentoEnum.NOTA_CREDITO_INVENTARIO, 
                        notaDetalle.getNotaCredito().getPuntoEmision().toString(),
                        notaDetalle.getNotaCredito().getPuntoEstablecimiento().toString(),
                        notaDetalle.getNotaCredito().getSecuencial(),
                        notaDetalle.getNotaCredito().getFechaEmision());
                
                break;
        }
    
    }
    
    /**
     * TODO: Unificar este metodo con la de factura que existe un metodo similar
     * @param detalle 
     */
    private void afectarInventario(Bodega bodega,int cantidad,BigDecimal precioUnitario,BigDecimal total,Long referenciaKardexId,Long referenciaProductoId,TipoDocumentoEnum tipoDocumento,String puntoEmision,String puntoEstablecimiento,Integer secuencial,Date fechaDocumento)
    {
        try {
            Producto producto=ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(referenciaProductoId);
            
            //Map<String,Object> mapParametros=new HashMap<String,Object>();
            //mapParametros.put("producto", producto);
            KardexService kardexService=new KardexService();
            Kardex kardexProducto=kardexService.buscarKardexPorProductoyBodega(bodega, producto);
            //List<Kardex> kardexs= kardexService.getFacade().findByMap(mapParametros);
            //TODO: Definir especificamente cual es la bodega principal
            //if(kardexProducto!=null && kardexProducto.size()>0)
            //{
                //TODO: Analizar caso cuando se resta un producto especifico
                Kardex kardex= kardexProducto;
                KardexDetalle kardexDetalle=new KardexDetalle();
                kardexDetalle.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                kardexDetalle.setFechaIngreso(UtilidadesFecha.getFechaHoy());
                kardexDetalle.setCantidad(cantidad);
                kardexDetalle.setCodigoTipoDocumento(tipoDocumento.getCodigo());
                kardexDetalle.setPrecioTotal(total);
                kardexDetalle.setPrecioUnitario(precioUnitario);
                kardexDetalle.setReferenciaDocumentoId(referenciaKardexId);
                kardexDetalle.setPuntoEmision(puntoEmision);
                kardexDetalle.setPuntoEstablecimiento(puntoEstablecimiento);
                kardexDetalle.setSecuencial(secuencial);
                kardexDetalle.setFechaDocumento(fechaDocumento);
                
                kardex.addDetalleKardex(kardexDetalle);
                
                //Actualizar los valores del kardex
                kardex.setStock(kardex.getStock() + kardexDetalle.getCantidad());
                //kardex.setPrecioPromedio(kardex.getPrecioPromedio().add(kardexDetalle.getPrecioUnitario()).divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP));
                kardex.setPrecioTotal(kardex.getPrecioTotal().add(kardexDetalle.getPrecioTotal()));
                //kardex.setPrecioUltimo(kardexDetalle.getPrecioUnitario());
                
                entityManager.merge(kardex);
            //}
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    /*
    public String getPreimpresoSiguiente() {
        try {
            Integer secuencialSiguiente = Integer.parseInt(parametroCodefacService.getParametroByNombre(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).valor);
            String secuencial = UtilidadesTextos.llenarCarateresIzquierda(secuencialSiguiente.toString(), 8, "0");
            String establecimiento = parametroCodefacService.getParametroByNombre(ParametroCodefac.ESTABLECIMIENTO).valor;
            String puntoEmision = parametroCodefacService.getParametroByNombre(ParametroCodefac.PUNTO_EMISION).valor;
            return  establecimiento + "-" + puntoEmision + "-" + secuencial;
        } catch (RemoteException ex) {
            Logger.getLogger(NotaCreditoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }*/

    public void editar(NotaCredito notaCredito) {
        notaCreditoFacade.edit(notaCredito);
    }

    public List<NotaCredito> obtenerTodos() {
        return notaCreditoFacade.findAll();
    }

    public List<NotaCredito> obtenerNotasReporte(Persona persona, Date fi, Date ff,ComprobanteEntity.ComprobanteEnumEstado estado,Empresa empresa) throws RemoteException {
        return notaCreditoFacade.lista(persona, fi, ff,estado,empresa);
    }

    @Override
    public void eliminar(NotaCredito entity) throws RemoteException {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {
                   
                    entity.getFactura().setEstadoNotaCredito(Factura.EstadoNotaCreditoEnum.SIN_ANULAR.getEstado());
                    entityManager.merge(entity.getFactura());
                    
                    //entity.setEstado(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
                    ComprobantesService comprobanteService = new ComprobantesService();
                    comprobanteService.eliminarComprobanteSinTransaccion(entity);
                    entityManager.merge(entity);
                    
                }
            });
        } catch (ServicioCodefacException ex) { //TODO: FALTA IMPLEMENTAR QUE LOS METODOS ELIMINAR PUEDAN DEVOLVER ALGUNA ALERTA AL CLIENTE
            Logger.getLogger(NotaCreditoService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
