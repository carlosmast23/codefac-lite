/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class RubroPlantillaBusquedaDialog implements InterfaceModelFind<RubroPlantilla>{

    private Periodo periodoSeleccion;

    public RubroPlantillaBusquedaDialog(Periodo periodoSeleccion) {
        this.periodoSeleccion = periodoSeleccion;
    }
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Periodo", 0.3d));
        titulo.add(new ColumnaDialogo("Valor", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM RubroPlantilla u WHERE u.periodo=?2 AND ";
        queryString += " ( LOWER(u.nombre) like ?1 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        queryDialog.agregarParametro(2,periodoSeleccion);
        return queryDialog;
        
    }

    @Override
    
    public void agregarObjeto(RubroPlantilla t, Vector dato) {
        dato.add(t.getNombre());
        dato.add(t.getPeriodo().getNombre());
        dato.add(t.getValor().toString());
    }
    
}
