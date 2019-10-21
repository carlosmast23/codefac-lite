/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.util;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import java.rmi.RemoteException;

/**
 *
 * @author Carlos
 */
public abstract class ParametroUtilidades {
    /**
     * Metodo que me permite comparar un parametro con un enum para ver si se cumple con un condición enviada por parametro
     * @param empresa
     * @param nombreParametro nombre dado por la clase de ParemetroCodefac
     * @param valorComparar
     * @return
     * @throws RemoteException 
     */
    /*public static Boolean comparar(Empresa empresa,String nombreParametro,EnumSiNo valorComparar) throws RemoteException
    {
        ParametroCodefac parametroCodefac = ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(nombreParametro,empresa);
        if (parametroCodefac != null) {
            //Solo si tiene parametro positivo intento construir el ensamble
            if (parametroCodefac.getValor() != null && EnumSiNo.getEnumByLetra(parametroCodefac.getValor()).equals(valorComparar)) {
                return true;
            }
        }
        return false;
    }*/
    
    public static <T extends ComparadorInterface> Boolean comparar(Empresa empresa,String nombreParametro,T valorComparar) throws RemoteException
    {
        ParametroCodefac parametroCodefac = ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(nombreParametro,empresa);
        if (parametroCodefac != null) {
            //Solo si tiene parametro positivo intento construir el ensamble
            String valorParametro=parametroCodefac.getValor();
            T resultadoValor=(T) valorComparar.
                    consultarParametro(valorParametro);
            
            if(resultadoValor!=null && resultadoValor.equals(valorComparar))
            {
                return true;
            }            
            /*if (parametroCodefac.getValor() != null && EnumSiNo.getEnumByLetra(parametroCodefac.getValor()).equals(valorComparar)) {
                return true;
            }*/
        }
        return false;
    }
    
    public interface ComparadorInterface<T>
    {
        /**
         * Metodo que me permite consultar el resultado 
         * @param nombreParametro
         * @return 
         */
        public T consultarParametro(String nombreParametro);
    }
}
