/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.list;

//import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesLista {
    
    public static <T> T obtenerDatoMayor(List<T> lista,Comparator<T> comparador)
    {
        T resultado= Collections.max(lista, comparador);
        return resultado;
    }
    
    public static <T> Set<T> buscarDuplicados(List<T> list) {
        return list.stream().distinct()
                .filter(i -> Collections.frequency(list, i) > 1)
                .collect(Collectors.toSet());
    }

    public static String castListToString(List lista,String caracter)
    {
        if(lista==null || lista.size()==0)
        {
            return "";
        }
        
        return StringUtils.join(lista,caracter);
    }
    
    //TODO: Unificar este metodo con el de más abajo
    public static List<String> castListToListString(List lista,CastListInterface interfaz)
    {
        List<String> datos=new ArrayList<String>();
        for (Object object : lista) {
            String textoNuevo=interfaz.getString(object);
            datos.add(textoNuevo);
        }
        return datos;
    }
    
    public static String castListToString(List lista,String caracter,CastListInterface interfaz)
    {
        List<String> datos=new ArrayList<String>();
        for (Object object : lista) {
            String textoNuevo=interfaz.getString(object);
            //Si el texto es null entonces no le agrego a la lista
            if(textoNuevo!=null)
            {
                datos.add(textoNuevo);
            }
        }
        return StringUtils.join(datos,caracter);
    }
    
    public static <T> List<T> eliminarReferenciaNulas(List<T> datos)
    {
        List<T> listWithoutNulls = datos.stream().filter(Objects::nonNull).collect(Collectors.toList());        
        return listWithoutNulls;
    }

    public static <T> List<T> restarListas(List<T> datosOriginales,List<T> datosNuevos)
    {
        List<T> listaEliminados=new ArrayList<T>();
        for (T datoOriginal : datosOriginales) {
            
            Boolean datoEliminado=true;
            for (T datoNuevo : datosNuevos) {
                if(datoNuevo.equals(datoOriginal))
                {
                    datoEliminado=false;
                }
            }
            
            if(datoEliminado)
            {
                listaEliminados.add(datoOriginal);
            }
            
        }
       
        return listaEliminados;
    }
    
    public static void ordenarLista(List lista,Comparator comparator)
    {
        Collections.sort(lista,comparator);
    }
    
    public static Boolean verificarListaVaciaONull(List lista)
    {
        if(lista==null)
        {
            return true;
        }
        
        if(lista.size()==0)
        {
            return true;
        }
        
        return false;
    }
    
    
    
    
    public static <T> List<T> arrayToList(T[] array){
        List<T> lista = Arrays.asList(array);
        return lista;
    }
    
    public static void invertir(List lista)
    {
        Collections.reverse(lista);
    }
    
    public static List<List> dividirLista(Integer tamanioDividir,List listaOriginal)
    {
        //TODO: Comentado por que algo esta afectando al proyecto
        //return Lists.partition(listaOriginal,tamanioDividir);
        return null;
        
    }
    
    public static boolean  verificarListadoDuplicadoPorCriterio(List datos,DatoCompararIf compararIf)
    {
        Set<Object> comprobacionListSet = new HashSet<Object>();
        if(datos!=null)
        {
            for (Object dato : datos) 
            {
                Object datoComparar= compararIf.getDato(dato);
                if(comprobacionListSet.contains(datoComparar))
                {
                    return true;
                }
                else
                {
                    comprobacionListSet.add(datoComparar);
                }
            }
        }
        return false;
    }
    
    public interface CastListInterface<T> 
    {

        public String getString(T dato);
    }

    public interface DatoCompararIf<T>
    {
        public Object getDato(T objeto);
    }
        
    
    
}
