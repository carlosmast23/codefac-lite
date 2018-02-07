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
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoFacturacionEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
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

    public void grabar(Factura factura) {
        EntityTransaction transaction= entityManager.getTransaction();
        transaction.begin();
        try {
            ParametroCodefac parametro =null;
            //Cuando la factura es electronica
            if(parametroService.getParametroByNombre(ParametroCodefac.TIPO_FACTURACION).valor.equals(TipoFacturacionEnumEstado.ELECTRONICA.getLetra()))
            {
                factura.setTipoFacturacion(TipoFacturacionEnumEstado.ELECTRONICA.getLetra());
                parametro = parametroService.getParametroByNombre(ParametroCodefac.SECUENCIAL_FACTURA);
            }
            else
            {
                //Estableciendo estado de facturacion manual
                factura.setEstado(FacturaEnumEstado.FACTURADO.getEstado());
                factura.setTipoFacturacion(TipoFacturacionEnumEstado.NORMAL.getLetra());
                if(factura.getCodigoDocumento().equals(DocumentoEnum.FACTURA.getCodigo()))
                {
                    parametro = parametroService.getParametroByNombre(ParametroCodefac.SECUENCIAL_FACTURA_FISICA);
                }
                else
                {
                    parametro = parametroService.getParametroByNombre(ParametroCodefac.SECUENCIAL_NOTA_VENTA_FISICA);
                }
            }
            
            
            //facturaFacade.create(factura);
            entityManager.persist(factura);
            entityManager.flush();
            
            /**
             * Actualizar valores del inventario con el kardex
             */
            for (FacturaDetalle detalle : factura.getDetalles()) {
                Producto producto= detalle.getProducto();
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
                    kardexDetalle.setCantidad(detalle.getCantidad().intValue());
                    kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.VENTA.getCodigo());
                    kardexDetalle.setPrecioTotal(detalle.getTotal());
                    kardexDetalle.setPrecioUnitario(detalle.getPrecioUnitario());
                    kardexDetalle.setReferenciaDocumentoId(factura.getId());
                    kardex.addDetalleKardex(kardexDetalle);
                    
                    //Actualizar los valores del kardex
                    kardex.setStock(kardex.getStock() - kardexDetalle.getCantidad());
                    //kardex.setPrecioPromedio(kardex.getPrecioPromedio().add(kardexDetalle.getPrecioUnitario()).divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP));
                    kardex.setPrecioTotal(kardex.getPrecioTotal().subtract(kardexDetalle.getPrecioTotal()));
                    //kardex.setPrecioUltimo(kardexDetalle.getPrecioUnitario());
                    
                    entityManager.merge(kardex);
                }
                
            }
            /**
             * Aumentar el codigo de la numeracion en los parametros
             */            
            
            parametro.valor = (Integer.parseInt(parametro.valor) + 1) + "";
            //parametroService.editar(parametro);
            entityManager.merge(parametro);
            
        transaction.commit();
        } catch (DatabaseException ex) {
            transaction.rollback();
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<Factura> obtenerFacturasReporte(Persona persona,Date fi,Date ff,String estado) {
        return facturaFacade.lista(persona,fi,ff,estado);
    }

    public List<Factura> obtenerFacturasActivas()
    {
        return facturaFacade.getFacturaEnable();
    }
    
    public String getPreimpresoSiguiente() {
        try {
            Integer secuencialSiguiente=0;
            //Obtener secuencial cuando es modo electronico
            if(parametroService.getParametroByNombre(ParametroCodefac.TIPO_FACTURACION).valor.equals(TipoFacturacionEnumEstado.ELECTRONICA.getLetra()))
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
    }
    
    public void eliminarFactura(Factura factura)
    {
        factura.setEstado(FacturaEnumEstado.ELIMINADO.getEstado());
        facturaFacade.edit(factura);
    }
    

}
