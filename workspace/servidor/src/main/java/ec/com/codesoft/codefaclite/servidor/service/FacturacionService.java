/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.TipoFacturacionEnumEstado;
import ec.com.codesoft.codefaclite.servidor.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaFacade;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Carlos
 */
public class FacturacionService extends ServiceAbstract<Factura, FacturaFacade>{

    FacturaFacade facturaFacade;
    FacturaDetalleFacade facturaDetalleFacade;
    ParametroCodefacService parametroService;

    public FacturacionService() {
        super(FacturaFacade.class);
        this.facturaFacade = new FacturaFacade();
        this.facturaDetalleFacade = new FacturaDetalleFacade();
        this.parametroService = new ParametroCodefacService();

    }

    public void grabar(Factura factura) {
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
            
            
            facturaFacade.create(factura);
            /**
             * Aumentar el codigo de la numeracion en los parametros
             */
            
            
            parametro.valor = (Integer.parseInt(parametro.valor) + 1) + "";
            parametroService.editar(parametro);
            
            
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
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
    }
    
    public void eliminarFactura(Factura factura)
    {
        factura.setEstado(FacturaEnumEstado.ELIMINADO.getEstado());
        facturaFacade.edit(factura);
    }
    

}
