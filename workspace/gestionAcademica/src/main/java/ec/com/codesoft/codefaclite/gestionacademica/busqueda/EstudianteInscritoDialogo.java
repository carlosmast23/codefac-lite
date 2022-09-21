/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class EstudianteInscritoDialogo implements InterfaceModelFind<EstudianteInscrito>{
    
    private Periodo periodo;

    public EstudianteInscritoDialogo(Periodo periodo) {
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
    public QueryDialog getConsulta(String filter,Map<Integer,Object> mapFiltro) {
        //EstudianteInscrito e;
        //e.getNivelAcademico().getPeriodo();
        String queryString = "SELECT u FROM EstudianteInscrito u WHERE (u.estado=?1) AND u.nivelAcademico.periodo=?3 AND";
        queryString += "( function ('TEXTO_ESTANDAR',LOWER(CONCAT(u.estudiante.nombres,' ',u.estudiante.apellidos))) LIKE LOWER(?2) OR LOWER(u.estudiante.cedula) LIKE LOWER(?2) ) ";
        //queryString +=" AND (SELECT COUNT(1) FROM EstudianteInscrito ei WHERE ei.nivelAcademico.periodo=?3 and ei.estado<>?4 and ei.estudiante=u)=0 ";
        
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,filter);
        queryDialog.agregarParametro(3,periodo);
        //queryDialog.agregarParametro(3,periodoFiltro);
        //queryDialog.agregarParametro(4,GeneralEnumEstado.ELIMINADO.getEstado());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(EstudianteInscrito t, Vector dato) {
       dato.add(t.getEstudiante().getCedula());
       dato.add(t.getEstudiante().getNombres());
       dato.add(t.getEstudiante().getApellidos());
       dato.add(t.getNivelAcademico().getNombre());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
