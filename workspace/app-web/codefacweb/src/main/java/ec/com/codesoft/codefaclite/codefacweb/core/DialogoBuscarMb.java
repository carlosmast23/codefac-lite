/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.codefacweb.mb.facturacion.ProformaMb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProformaBusqueda;
//import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProformaBusqueda;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
//import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.utilidades.sql.UtilidadSql;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class DialogoBuscarMb implements Serializable {

    private static final Logger LOG = Logger.getLogger(DialogoBuscarMb.class.getName());
    
    

    private List<Object> datosBusqueda;
    private List<String> propiedadesObjeto;
    private List<Vector<String>> datosConsulta;
    private Vector<ColumnaDialogo> columnasConsulta;
    private LazyDataModel<Object> datosBusquedaLazy;
    
    private Object objetoSeleccionado;

    @PostConstruct
    public void init() {
        //propiedadesObjeto.get(0)
        /**
         * Obtener clase que implementa la forma de busqueda en los dialogos
         * dialogo
         */
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Object busquedaClase= sessionMap.get("busquedaClase");
       
        //Despues de leer el dato de sessionMap lo elimino para evitar tener pesada la pagina
        sessionMap.remove("busquedaClase");
        
        final InterfaceModelFind controller =(InterfaceModelFind)busquedaClase;
        
        this.datosBusquedaLazy=new LazyDataModel<Object>() {
            @Override
            public List<Object> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) 
            {
                try {
                    String filtroConsulta="%%";
                    if(filters.size()>0)
                    {
                        filtroConsulta="%"+filters.get(filters.keySet().toArray()[0])+"%";
                        filtroConsulta=filtroConsulta.toLowerCase();
                    }
                    QueryDialog queryDialog = controller.getConsulta(filtroConsulta);
                    String queryTamanio= UtilidadSql.convertirConsultaEnConsultaTamanio(queryDialog.query);
                    Long tamanioConsulta=ServiceFactory.getFactory().getUtilidadesServiceIf().consultaTamanioGeneralDialogos(queryTamanio, queryDialog.getParametros());                    
                    setRowCount(tamanioConsulta.intValue());
                    
                    datosBusqueda = ServiceFactory.getFactory().getUtilidadesServiceIf().consultaGeneralDialogos(queryDialog.query, queryDialog.getParametros(),first,pageSize);
                    buscarDatos(controller, datosBusqueda);
                    return datosBusqueda;
                    //return super.load(first, pageSize, sortField, sortOrder, filters); //To change body of generated methods, choose Tools | Templates.
                } catch (RemoteException ex) {
                    Logger.getLogger(DialogoBuscarMb.class.getName()).log(Level.SEVERE, null, ex);
                }
                return new ArrayList<Object>();
            }

            @Override
            public String getRowKey(Object object) {                
                return String.valueOf(object.hashCode());
            }

            @Override
            public boolean equals(Object obj) {
                return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public int hashCode() {                
                return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
            }
            
            

            @Override
            public Object getRowData(String rowKey) {
                if(datosBusqueda!=null)
                {                    
                    for (Object datoBuscado : datosBusqueda) 
                    {
                        String datoBusquedaHashCode=String.valueOf(datoBuscado.hashCode());
                        System.out.println(datoBusquedaHashCode);
                        if(datoBusquedaHashCode.equals(rowKey))
                        {
                            return datoBuscado;
                        }
                    }
                }
                return null;                
            }
            
            
        };
       
        //buscarDatos(controller);

    }
    
   

    public void buscarDatos(InterfaceModelFind busquedaClase,List<Object> datosBusqueda) {
        InterfaceModelFind busquedaDialogo = busquedaClase;
        QueryDialog queryDialog = busquedaDialogo.getConsulta("%%");
        //datosBusqueda = ServiceFactory.getFactory().getUtilidadesServiceIf().consultaGeneralDialogos(queryDialog.query, queryDialog.getParametros(), 0, 10000);
        //Setear datos al controlador
        columnasConsulta = busquedaDialogo.getColumnas();
        datosConsulta = new ArrayList<Vector<String>>();
        //buscar si la clase implementa la interfaz para busqueda de las propiedaes
        if (busquedaClase instanceof InterfacesPropertisFindWeb) {
            System.out.println("cargando clase de las propiedades del objecto");
            InterfacesPropertisFindWeb propiedadesInterface = (InterfacesPropertisFindWeb) busquedaClase;
            propiedadesObjeto = propiedadesInterface.getNamePropertysObject();
        }
        for (Object object : datosBusqueda) {
            Vector datoFila = new Vector();
            busquedaDialogo.agregarObjeto(object, datoFila);
            datosConsulta.add(datoFila);
        }
        System.out.println("La busqueda genero " + datosConsulta.size() + " registros");

    }

    /*private Vector<String> obtenerNombresColumnas(Vector<ColumnaDialogo> columnasDialogo) 
    {
        Vector<String> propiedades = new Vector<String>();
        for (ColumnaDialogo columnaDialogo : columnasDialogo) {
            propiedades.add(columnaDialogo.getNombre());
        }
        return propiedades;
    }*/

    public Object buscarValorObjecto(Object objetoBase, String propiedad) {

        if (objetoBase == null) {
            return "";
        }

        String[] propiedades = propiedad.split("\\.");
        Object objectoBuscar = objetoBase;
        for (int i = 0; i < propiedades.length; i++) {
            String propiedadNombre = propiedades[i];
            objectoBuscar = buscarPropiedadEnObjecto(objectoBuscar, propiedadNombre);
        }

        return objectoBuscar;
    }

    private Object buscarPropiedadEnObjecto(Object objeto, String propiedadNombre) {

        Method[] metodos = objeto.getClass().getMethods();
        String metodoNombre="get"+StringUtils.capitalize(propiedadNombre);
        
        for (Method metodo : metodos) {
            if (metodo.getName().equals(metodoNombre)) {
                
                
                //if (Modifier.isPrivate(field.getModifiers())) {

                    try {
                        Object resultado=metodo.invoke(objeto);
                        //field.setAccessible(true);
                        return resultado;
                        //System.out.println(field.getName()+" : "+field.get(book));
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(DialogoBuscarMb.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(DialogoBuscarMb.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                    Logger.getLogger(DialogoBuscarMb.class.getName()).log(Level.SEVERE, null, ex);
                }

                //}
            }
        }

        return null;
    }

    public void selecccionarObjecto() {
        //Object resultado = datosBusqueda.get(indice);
        System.out.println("Seleccionado objeto del dialogo");
        if(objetoSeleccionado!=null)
        {
            System.out.println("");
            PrimeFaces.current().dialog().closeDynamic(objetoSeleccionado);
        }
        else
        {
            System.out.println("Objeto vacio no se puede seleccionar!");
        }
    }

    /*public boolean filterByName(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        String carName = value.toString().toUpperCase();
        filterText = filterText.toUpperCase();

        if (carName.contains(filterText)) {
            return true;
        } else {
            return false;
        }
    }*/

    /*public List<Object> getDatosBusqueda() {
        return datosBusqueda;
    }

    public void setDatosBusqueda(List<Object> datosBusqueda) {
        this.datosBusqueda = datosBusqueda;
    }*/

    public LazyDataModel<Object> getDatosBusquedaLazy() {
        return datosBusquedaLazy;
    }

    public void setDatosBusquedaLazy(LazyDataModel<Object> datosBusquedaLazy) {
        this.datosBusquedaLazy = datosBusquedaLazy;
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

    public Object getObjetoSeleccionado() {
        return objetoSeleccionado;
    }

    public void setObjetoSeleccionado(Object objetoSeleccionado) {
        this.objetoSeleccionado = objetoSeleccionado;
    }
    
    public void filaSeleccionada(SelectEvent event) {
        System.out.println("seleccionado objeto para devolver");
        objetoSeleccionado= event.getObject();
        PrimeFaces.current().dialog().closeDynamic(objetoSeleccionado);
    }
 
    /*public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((Car) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }*/
    
    

}
