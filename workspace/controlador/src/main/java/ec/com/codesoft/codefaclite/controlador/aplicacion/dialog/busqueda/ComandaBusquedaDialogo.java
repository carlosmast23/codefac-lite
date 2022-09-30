/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ComandaBusquedaDialogo extends ProformaBusqueda{

    public ComandaBusquedaDialogo(Empresa empresa) {
        super(empresa);
    }

    public ComandaBusquedaDialogo(Empresa empresa, Boolean mostrarFacturados) {
        super(empresa, mostrarFacturados);
    }

    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Secuencial", 0.2d));
        titulo.add(new ColumnaDialogo("Mesa", 0.3d));
        titulo.add(new ColumnaDialogo("Mesero", 0.15d));        
        titulo.add(new ColumnaDialogo("fecha", 0.15d));
        titulo.add(new ColumnaDialogo("total", 0.1d));
        return titulo;
    }

    @Override
    public DocumentoEnum getDocumentoBuscar() {
        return DocumentoEnum.COMANDA;
    }

    
    
    @Override
    public void agregarObjeto(Factura t, Vector dato) {
        dato.add(t.getSecuencial());
        dato.add((t.getMesa()!=null)?t.getMesa().toString():"");
        dato.add((t.getUsuario()!=null)?t.getUsuario().getNick():"");
        if(t.getFechaEmision()!=null)
        {
            dato.add(ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA.format(t.getFechaEmision()));
        }
        else
        {
            dato.add("");
            Logger.getLogger(ProformaBusqueda.class.getName()).log(Level.WARNING,"Proforma grabada sin fecha de emision: id=>"+t.getId());
        }
        dato.add(t.getTotal());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("secuencial");
        propiedades.add("mesa");
        propiedades.add("usuario");//TODO: Ver como puedo hacer para establecer una propiedad personalizada        
        propiedades.add("fechaEmision");
        propiedades.add("total");
        return propiedades;
    }
    
}
