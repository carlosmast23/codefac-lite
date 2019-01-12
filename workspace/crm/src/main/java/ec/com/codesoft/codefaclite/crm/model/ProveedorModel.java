/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.crm.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;

/**
 *
 * @author Carlos
 */
public class ProveedorModel extends ClienteModel {

    public ProveedorModel() {
        super();
        setTitle("Proveedor");
        getCmbTipoOperador().setSelectedItem(OperadorNegocioEnum.PROVEEDOR);
        super.operadorNegocioDefault=OperadorNegocioEnum.PROVEEDOR;
        
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        ProveedorBusquedaDialogo proveedorBusquedaDialogo = new ProveedorBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(proveedorBusquedaDialogo);
        return buscarDialogoModel;
    }
}
