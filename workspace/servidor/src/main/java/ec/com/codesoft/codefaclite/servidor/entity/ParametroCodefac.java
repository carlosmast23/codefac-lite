/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import ec.com.codesoft.codefaclite.servidor.facade.*;

/**
 *
 * @author Carlos
 */
public class ParametroCodefac {
    
    public static final String NOMBRE_FIRMA_ELECTRONICA= "firma_electronica";
    public static final String CLAVE_FIRMA_ELECTRONICA= "clave_firma_electronica";
    public static final String DIRECTORIO_RECURSOS= "directorio_recursos";
    public static final String MODO_FACTURACION= "modo_facturacion";
    
    public static final String SRI_WS_RECEPCION= "sri_ws_recepcion";
    public static final String SRI_WS_AUTORIZACION= "sri_ws_autorizacion";
    public static final String SRI_WS_RECEPCION_PRUEBA= "sri_ws_recepcion_prueba";
    public static final String SRI_WS_AUTORIZACION_PRUEBA= "sri_ws_autorizacion_prueba";
    
    public static final String SECUENCIAL_FACTURA= "secuencial_factura";
    
    public static final String CORREO_USUARIO= "correo_usuario";
    public static final String CORREO_CLAVE= "correo_clave";
    
    public long id;
    public String nombre;
    public String valor;

    public ParametroCodefac() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    
    
    
}
