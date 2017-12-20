/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaFacade;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import java.sql.Date;
import java.util.List;

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
        facturaFacade.create(factura);
        /**
         * Aumentar el codigo de la numeracion en los parametros
         */
        ParametroCodefac parametro = parametroService.getParametroByNombre(ParametroCodefac.SECUENCIAL_FACTURA);
        parametro.valor = (Integer.parseInt(parametro.valor) + 1) + "";
        parametroService.grabar(parametro);

    }

    public void editar(Factura factura) {
        facturaFacade.edit(factura);
    }

    public List<Factura> obtenerTodos() {
        return facturaFacade.findAll();
    }

    public List<Factura> obtenerFacturasReporte(Persona persona,Date fi,Date ff) {
        return facturaFacade.lista(persona,fi,ff);
    }

    public List<Factura> obtenerFacturasActivas()
    {
        return facturaFacade.getFacturaEnable();
    }
    
    public String getPreimpresoSiguiente() {
        Integer secuencialSiguiente = Integer.parseInt(parametroService.getParametroByNombre(ParametroCodefac.SECUENCIAL_FACTURA).valor) + 1;
        String secuencial = UtilidadesTextos.llenarCarateresIzquierda(secuencialSiguiente.toString(), 8, "0");
        String establecimiento = parametroService.getParametroByNombre(ParametroCodefac.PUNTO_EMISION).valor;
        String puntoEmision = parametroService.getParametroByNombre(ParametroCodefac.PUNTO_EMISION).valor;
        return puntoEmision + "-" + establecimiento + "-" + secuencial;
    }
    

}
