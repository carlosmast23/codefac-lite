/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity.ComprobanteEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;

/**
 * Dialogo de busqueda para seleccionar una Nota de Venta Interna y convertirla en Factura.
 * Reutiliza la consulta/columnas de ProformaBusqueda (misma entidad Factura, mismo formato de listado),
 * solo cambia el DocumentoEnum a buscar.
 * @author Carlos
 */
public class NotaVentaInternaBusqueda extends ProformaBusqueda {

    public NotaVentaInternaBusqueda(Empresa empresa) {
        super(empresa);
    }

    public NotaVentaInternaBusqueda(Empresa empresa, Boolean mostrarFacturados) {
        super(empresa, mostrarFacturados);
    }

    @Override
    public DocumentoEnum getDocumentoBuscar() {
        return DocumentoEnum.NOTA_VENTA_INTERNA;
    }

    @Override
    public ComprobanteEnumEstado getEstadoBuscar() {
        //La Nota de Venta Interna no pasa por ningun proceso de autorizacion, se graba directamente como SIN_AUTORIZAR
        return ComprobanteEnumEstado.SIN_AUTORIZAR;
    }

}
