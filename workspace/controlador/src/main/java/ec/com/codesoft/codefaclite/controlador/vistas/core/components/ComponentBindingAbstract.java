/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesReflexion;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;

/**
 *Las 2 implemntacion genericas son T=componente de la vista ejemplo: jtextField
 * y el segundo corresponde al tipo de la anotacion
 * @author CARLOS_CODESOFT
 */
public abstract class ComponentBindingAbstract<T,A> {
    /**
     * Variable que va a tener los datos del tipo de clase y el Method qu implenta el get para obtener el componente
     */
    //private UtilidadesReflexion.ResponseAnotacionMetodo anotacion;
    /**
     * Nombre de la propiedad en el controlador
     */
    //private String nombrePropiedadControlador;
    /**
     * Objeto del contexto del controlador
     */
    private VistaCodefacIf controlador;
    
    private T componenteVista;
    
    private A anotacionBinding;
    
    public abstract void getAccionesComponente(List<ComponentBindingIf> lista);
    /**
     * Obtiene la clase de los componentes de la vista
     * @return 
     */
    public abstract Class<T> getClassComponente();
    
    /**
     * Metodo que me permite corregir un problema con los listener
     * @return 
     */
    //public List<String> listaListenersCache=new ArrayList<String>();
    
    public void init(ControladorCodefacInterface panel,VistaCodefacIf controlador,UtilidadesReflexion.ResponseAnotacionMetodo anotacion)
    {
        //Obtengo el componente de la vista
        componenteVista=UtilidadesReflexion.obtenerValorDelMetodo(anotacion.metodo,panel,getClassComponente());  
        anotacionBinding=(A) anotacion.anotacion;
        this.controlador=controlador;
        //nombrePropiedadControlador=anotacion.anotacion.value();
            
        //    String valorPropiedad= UtilidadesReflexion.buscarComponentePorNombrePropiedad(controlador, nombrePropiedadControlador,String.class);
        //    jtextField.setText(valorPropiedad);
    }

    /*public void init(UtilidadesReflexion.ResponseAnotacionMetodo anotacion,ControladorCodefacInterface panel)
    {
        T componente = UtilidadesReflexion.obtenerValorDelMetodo(anotacion.metodo, panel, getClassComponente());
        
        String nombrePropiedadControlador = anotacion.anotacion.value();

        String valorPropiedad = UtilidadesReflexion.buscarComponentePorNombrePropiedad(controlador, nombrePropiedadControlador, String.class);
        //jtextField.setText(valorPropiedad);
    }*/
    
    public void ejecutar()
    {
        List<ComponentBindingIf> listaComponentes=new ArrayList<ComponentBindingIf>();
        getAccionesComponente(listaComponentes);
        for (ComponentBindingIf componentBindingIf : listaComponentes) 
        {            
            //Obtiene el nombre de la propiedad vinculada akl controlador
            String nombrePropiedad=componentBindingIf.getNombrePropiedadControlador(anotacionBinding);            
            
            //Validacion cuando no tiene asignada ningun dato que vincula al controlador
            if(nombrePropiedad==null || nombrePropiedad.isEmpty())
            {
                continue;
            }
            
            //Vincula el componente de la vista para poder setear al controlador cuando se produsca un evento
            componentBindingIf.getAccion(nombrePropiedad);

            //Obtiene le valor que alamacena la propiedad en la vista
            Object valorPropiedadControlador = getValorCampoControlador(nombrePropiedad);
            
            

            //Envia los del controlador para setar con una propiedad de los componentes de la vista
            componentBindingIf.setAccion(valorPropiedadControlador);

            
                        
        }
        
    }
    
    public Object getValorCampoControlador(String nombrePropiedad)
    {
        String getMetodoPropiedadStr=nombrePropiedad;
        
        //Solo buscar propiedades en metodos cuando son propiedades
        if(nombrePropiedad.toLowerCase().indexOf("listener")>=0)
        {
            return null;
            //
        }
        getMetodoPropiedadStr=UtilidadesReflexion.castNameMethodGetOrSet(nombrePropiedad, UtilidadesReflexion.GetSetEnum.GET);
        Method getMetodoPropiedad=UtilidadesReflexion.buscarMetodoPorNombre(controlador.getClass(), getMetodoPropiedadStr);
        //return UtilidadesReflexion.buscarComponentePorNombrePropiedad(controlador,nombrePropiedad,Object.class);
        return UtilidadesReflexion.obtenerValorDelMetodo(getMetodoPropiedad,controlador,Object.class);
    }
    
    public void setValoresAlControlador(Object value,String nombrePropiedadControlador)
    {
        String nombreMetodoSet=UtilidadesReflexion.castNameMethodGetOrSet(nombrePropiedadControlador,UtilidadesReflexion.GetSetEnum.SET);;
        Method metodoSetPropiedad=UtilidadesReflexion.buscarMetodoPorNombre(controlador.getClass(),nombreMetodoSet);
        UtilidadesReflexion.setearValorDelMetodo(metodoSetPropiedad, controlador,value);
    }
    
    /**
     * TODO: Este metodo va ser usado directamente solo los listener para corregir un problema que se
     * activan al inicio del aplicativo sin querer
     * @param nombreMetodo 
     */
    public void ejecutarMetodoControlador(String nombreMetodo)
    {
        //if(listaListenersCache.contains(nombreMetodo))
        //{
            Method metodoEjecutar=UtilidadesReflexion.buscarMetodoPorNombre(controlador.getClass(), nombreMetodo);
            UtilidadesReflexion.ejcutarMetodo(metodoEjecutar,controlador);
        //}
        //else
        //{
        //    listaListenersCache.add(nombreMetodo);
        //}
    }

    public T getComponente() {
        return componenteVista;
    }

    public void setComponente(T Componente) {
        this.componenteVista = Componente;
    }
    
    
}
