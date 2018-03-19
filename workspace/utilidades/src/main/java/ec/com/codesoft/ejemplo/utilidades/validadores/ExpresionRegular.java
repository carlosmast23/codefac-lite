/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.validadores;

/**
 *
 * @author robert
 */
public final class ExpresionRegular 
{
    /**
     * Texto simple 
     * Ejm: Texto simple que tiene caracteres como - guion, . punto, _línea baja y , coma
    */
    public static final String textoSimple = "^[A-Za-z0-9\\s.\\_\\-\\,\\ ]*$";
    
    /**
     * Texto simple 
     * Ejm: Texto simple que permite todo lo que tiene su primera opcion menos coma
    */
    public static final String textoSimple2 ="^[A-Za-z0-9\\s.\\_\\-\\ ]*$";
    
    /**
     * Número celular de 10 digitos
     * Ejm: 0697426212
    */
    public static final String telefonoCelular = "^[0][0-9]{9}$";
    
    /**
     * Formato estandar de correo electrónico
     * Ejm: correo@algo.ecu
    */
    public static final String email = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$";
    
    /**
     * Solo numeros positivos 
     * Ejm: 972
    */
    public static final String soloNumeros = "^[0-9]*$";
    
    /**
     * Número de teléfono convencional anteponiendo el código provincial
     * Ejm: 022625072 
    */
    public static final String telefonoConvencional = "^[0][0-9]{8}$";
    
    /**
     * Texto Simple que permite la validacion de un nombre de razón social sin caracteres especiales
     * Ejm: Codesoft Desarrollo 1
    */
    public static final String nombreSocial = "^[A-Za-z0-9\\s]*$";
    
    /**
     * Número que permite validar el ingreso de numeros enteros para precios
     * Ejm: 98.89 - 9 - 100.1
     */
    public static final String numerosRealesPositivos = "^[0-9]+([.][0-9]+)?$"; 
    
    /**
     * Número     
    */
    public static final String soloNumeros2 = "^[0-9]+$";

}
