/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;

/**
 *
 * @author Carlos
 */
public class LiquidacionCompraBusqueda extends FacturaBusqueda{
    
    public LiquidacionCompraBusqueda(Empresa empresa) {
        super(empresa);
    }

    @Override
    public void setParameterQuery(QueryDialog queryDialog) {
        queryDialog.agregarParametro(4,DocumentoEnum.LIQUIDACION_COMPRA.getCodigo());
    }

    @Override
    public String getQueryDocumentos() {
        return " AND u.codigoDocumento=?4 ";
    }
    
    
    
}
