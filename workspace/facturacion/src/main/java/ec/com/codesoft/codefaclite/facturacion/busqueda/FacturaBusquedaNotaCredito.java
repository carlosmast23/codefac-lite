/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class FacturaBusquedaNotaCredito implements InterfaceModelFind<Factura> {

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Id", 0.2d));
        titulo.add(new ColumnaDialogo("preimpreso", 0.2d));
        titulo.add(new ColumnaDialogo("cliente", 0.3d));
        titulo.add(new ColumnaDialogo("estado", 0.15d));
        titulo.add(new ColumnaDialogo("fecha", 0.15d));
        titulo.add(new ColumnaDialogo("total", 0.1d));        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //NotaCredito nota;
        //nota.getTotal();
        //Factura f=new Factura();
        //f.getTotal();
        //f.getEstadoNotaCredito()
        
        String queryString = "SELECT u FROM Factura u WHERE ( u.estado<>?1 and u.estadoNotaCredito<>?2 and u.estado<>?3 ) AND ";
        queryString+=" ( LOWER(u.cliente.razonSocial) like ?4 OR CONCAT(u.secuencial,'') like ?4 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,FacturaEnumEstado.ELIMINADO.getEstado());
        queryDialog.agregarParametro(2,Factura.EstadoNotaCreditoEnum.ANULADO_TOTAL.getEstado());
        queryDialog.agregarParametro(3,FacturaEnumEstado.SIN_AUTORIZAR.getEstado());
        queryDialog.agregarParametro(4,filter);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Factura t, Vector dato) {
        dato.add(t.getId());
        dato.add(t.getPreimpreso());
        System.out.println(t.getPreimpreso());
        dato.add(t.getCliente().getRazonSocial());
        FacturaEnumEstado estadoEnum= FacturaEnumEstado.getEnum(t.getEstado());
        dato.add(estadoEnum.getNombre());
        dato.add(t.getFechaFactura());
        dato.add(t.getTotal());
    }

    /*
    @Override
    public Boolean buscarObjeto(Factura t, Object valor) {
        //if(t.getCliente().getIdentificacion().indexOf(valor.toString())>=0 || t.getPreimpreso().indexOf(valor.toString())>=0)
        String preimpreso=t.getPreimpreso().toLowerCase();
        String valorBuscar=valor.toString().toLowerCase();
        if(preimpreso.indexOf(valorBuscar)>=0)
        {
            return true;
        }   
        else
        {
            return false;
        }  
        //return true;
    }*/
    
}
