/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class DialogoBuscarMb implements Serializable {

    private List<Object> datosBusqueda;
    private List<String> propiedadesObjeto;
    private List<Vector<String>> datosConsulta;
    private Vector<ColumnaDialogo> columnasConsulta;

    @PostConstruct
    public void init() {
        /**
         * Obtener clase que implementa la forma de busqueda en los dialogos
         * dialogo
         */
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        InterfaceModelFind controller = (InterfaceModelFind) sessionMap.get("busquedaClase");
        sessionMap.remove("busquedaClase", controller); //Despues de obtener el dato eliminar para no tener recursos inecesarios TODO: Solucion temporal
        
        buscarDatos(controller);
        
    }

    public void buscarDatos(InterfaceModelFind busquedaClase) {
        try {
            
            InterfaceModelFind empleadoBusquedaDialogo = new EmpleadoBusquedaDialogo();
            QueryDialog queryDialog = empleadoBusquedaDialogo.getConsulta("%%");
            datosBusqueda = ServiceFactory.getFactory().getUtilidadesServiceIf().consultaGeneralDialogos(queryDialog.query, queryDialog.getParametros(), 0, 100);

            //Setear datos al controlador
            columnasConsulta = empleadoBusquedaDialogo.getColumnas();
            datosConsulta = new ArrayList<Vector<String>>();
            
            //buscar si la clase implementa la interfaz para busqueda de las propiedaes
            if(busquedaClase instanceof InterfacesPropertisFindWeb)
            {
                System.out.println("cargando clase de las propiedades del objecto");
                InterfacesPropertisFindWeb propiedadesInterface=(InterfacesPropertisFindWeb) busquedaClase;
                propiedadesObjeto=propiedadesInterface.getNamePropertysObject();
            }
            
            for (Object object : datosBusqueda) {
                Vector datoFila=new Vector();
                empleadoBusquedaDialogo.agregarObjeto(object, datoFila);
                datosConsulta.add(datoFila);
            }
            System.out.println("La busqueda genero "+datosConsulta.size()+" registros");

        } catch (RemoteException ex) {
            Logger.getLogger(ControllerCodefacMb.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void selecccionarObjecto(Integer indice) {
        Object resultado=datosBusqueda.get(indice);
        PrimeFaces.current().dialog().closeDynamic(resultado);
    }

    public List<Object> getDatosBusqueda() {
        return datosBusqueda;
    }

    public void setDatosBusqueda(List<Object> datosBusqueda) {
        this.datosBusqueda = datosBusqueda;
    }

    public List<Vector<String>> getDatosConsulta() {
        return datosConsulta;
    }

    public void setDatosConsulta(List<Vector<String>> datosConsulta) {
        this.datosConsulta = datosConsulta;
    }

    

    public Vector<ColumnaDialogo> getColumnasConsulta() {
        return columnasConsulta;
    }

    public void setColumnasConsulta(Vector<ColumnaDialogo> columnasConsulta) {
        this.columnasConsulta = columnasConsulta;
    }

    public List<String> getPropiedadesObjeto() {
        return propiedadesObjeto;
    }

    public void setPropiedadesObjeto(List<String> propiedadesObjeto) {
        this.propiedadesObjeto = propiedadesObjeto;
    }

    

    
    
}
