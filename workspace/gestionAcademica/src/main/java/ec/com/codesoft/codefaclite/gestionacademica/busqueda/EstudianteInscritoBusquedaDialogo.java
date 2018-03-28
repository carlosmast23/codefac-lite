/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author CodesoftDesarrollo
 */
public class EstudianteInscritoBusquedaDialogo implements InterfaceModelFind<EstudianteInscrito> {

    /*
    Periodo Mediante el cual van a filtrar los estudiates inscritos
    */
    private Periodo periodo;

    public EstudianteInscritoBusquedaDialogo(Periodo periodo) {
        this.periodo = periodo;
    }
    
    
            
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Cedula", 0.2d));
        titulo.add(new ColumnaDialogo("Nombres", 0.3d));
        titulo.add(new ColumnaDialogo("Apellidos", 0.3d));
        titulo.add(new ColumnaDialogo("Curso", 0.2d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {    
        String queryString = "SELECT u FROM EstudianteInscrito u WHERE u.nivelAcademico.periodo=?1 AND ";
        queryString += " ( LOWER(u.estudiante.apellidos) LIKE " + filter + " )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,periodo);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(EstudianteInscrito e, Vector dato) {
       dato.add(e.getEstudiante().getCedula());
       dato.add(e.getEstudiante().getNombres());
       dato.add(e.getEstudiante().getApellidos());
       dato.add(e.getNivelAcademico().getNombre());
    }

    /*
    @Override
    public Boolean buscarObjeto(EstudianteInscrito e, Object valor) {
        return e.getEstudiante().getApellidos().equals(valor.toString());
    }*/

}
