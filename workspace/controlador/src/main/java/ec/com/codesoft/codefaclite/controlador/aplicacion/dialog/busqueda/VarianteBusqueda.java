package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Variante;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Map;
import java.util.Vector;

public class VarianteBusqueda implements InterfaceModelFind<Variante> {

    private Empresa empresa;

    public VarianteBusqueda(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> columnas = new Vector<>();
        columnas.add(new ColumnaDialogo("Id", 0.1d));
        columnas.add(new ColumnaDialogo("Talla", 0.2d));
        columnas.add(new ColumnaDialogo("Color", 0.2d));
        return columnas;
    }

    @Override
    public QueryDialog getConsulta(String filter, Map<Integer, Object> mapFiltro) {
        String queryString = "SELECT v FROM Variante v WHERE v.estado = ?1 AND v.empresa = ?2 AND (v.talla LIKE ?3 OR v.color LIKE ?3)";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, empresa);
        queryDialog.agregarParametro(3, filter);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Variante variante, Vector dato) {
        dato.add(variante.getId());
        dato.add(variante.getTalla());
        dato.add(variante.getColor());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
