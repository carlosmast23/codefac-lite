/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class NivelAcademicoDialogo implements InterfaceModelFind<NivelAcademico>{

    /**
     * Campo para filtrar los datos por el periodo y no se acumulen
     */
    private Periodo periodoFiltro;

    public NivelAcademicoDialogo(Periodo periodoFiltro) {
        this.periodoFiltro = periodoFiltro;
    }
    
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Periodo", 0.3d));
        titulo.add(new ColumnaDialogo("Nivel", 0.3d));
        titulo.add(new ColumnaDialogo("Estado", 0.2d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter,Map<Integer,Object> mapFiltro) {

        String queryString = "SELECT u FROM NivelAcademico u WHERE u.periodo=?2 and u.estado<>?3 and ";
        queryString += " ( LOWER(u.nombre) like ?1 )";
        
        
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        queryDialog.agregarParametro(2,periodoFiltro);
        queryDialog.agregarParametro(3,GeneralEnumEstado.ELIMINADO.getEstado());
        
        return queryDialog;
    }

    @Override
    public void agregarObjeto(NivelAcademico t, Vector dato) {
        dato.add(t.getNombre());
        dato.add(t.getPeriodo().getNombre());
        dato.add((t.getNivel()!=null)?t.getNivel().getNombre():"");
        dato.add(t.getEstadoEnum().getNombre());
    }

    /*
    @Override
    public Boolean buscarObjeto(NivelAcademico t, Object valor) {
        return t.getNombre().equals(valor.toString());
    }*/

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
