/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author Robert
 */
public class GestionEmpleadosBusquedaDialogo implements InterfaceModelFind<Empleado>
{
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Cédula",100));
        titulo.add(new ColumnaDialogo("Nombres",100));
        titulo.add(new ColumnaDialogo("Departamento",100));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT e FROM Empleado e WHERE (LOWER(e.nombres) like ?1) or ";
        queryString+="(LOWER(e.apellidos) like ?2) or e.identificacion like ?3  and e.estado=?4";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        queryDialog.agregarParametro(2,filter);
        queryDialog.agregarParametro(3,filter);
        queryDialog.agregarParametro(4,GeneralEnumEstado.ACTIVO.getEstado());
        return queryDialog;    
    }

    @Override
    public void agregarObjeto(Empleado e, Vector dato) {
        dato.add(e.getIdentificacion());
        String nombreCompleto = e.getApellidos() + " " + e.getNombres();
        dato.add(nombreCompleto);
        if(e.getDepartamento() != null){
            dato.add(e.getDepartamento());
        }else{
            dato.add("Sin departamento");
        }  
    }
    
}
