/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "PARAMETRO")
public class ParametroCodefac implements Serializable {
    
    public static final String NOMBRE_FIRMA_ELECTRONICA= "firma_electronica";
    public static final String CLAVE_FIRMA_ELECTRONICA= "clave_firma_electronica";
    public static final String DIRECTORIO_RECURSOS= "directorio_recursos";
    public static final String LOGO_EMPRESA= "logo_empresa";
    public static final String MODO_FACTURACION= "modo_facturacion";
    public static final String IVA_DEFECTO= "iva_defecto";
    
    public static final String SRI_WS_RECEPCION= "sri_ws_recepcion";
    public static final String SRI_WS_AUTORIZACION= "sri_ws_autorizacion";
    public static final String SRI_WS_RECEPCION_PRUEBA= "sri_ws_recepcion_prueba";
    public static final String SRI_WS_AUTORIZACION_PRUEBA= "sri_ws_autorizacion_prueba";
    
    public static final String SECUENCIAL_FACTURA= "secuencial_factura";
    public static final String SECUENCIAL_NOTA_CREDITO= "secuencial_nota_credito";
    public static final String SECUENCIAL_NOTA_DEBITO= "secuencial_nota_debito";
    public static final String SECUENCIAL_GUIA_REMISION= "secuencial_guia_remision";
    public static final String SECUENCIAL_RETENCION= "secuencial_retencion";
    
    public static final String CORREO_USUARIO= "correo_usuario";
    public static final String CORREO_CLAVE= "correo_clave";
    
    public static final String ESTABLECIMIENTO= "establecimiento";
    public static final String PUNTO_EMISION= "punto_emision";
    
    public static final String CELULAR_VIRTUAL_MALL="celular_virtual_mall";
    public static final String IMAGEN_FONDO="imagen_fondo";
    public static final String ULTIMA_FECHA_VALIDACION="ultima_fecha_validacion";
    public static final String TIPO_FACTURACION="tipo_facturacion";
    
    public static final String SECUENCIAL_FACTURA_FISICA= "secuencial_factura_fisica";
    public static final String SECUENCIAL_NOTA_CREDITO_FISICA= "secuencial_nota_credito_fisica";
    public static final String SECUENCIAL_NOTA_DEBITO_FISICA= "secuencial_nota_debito_fisica";
    public static final String SECUENCIAL_GUIA_REMISION_FISICA= "secuencial_guia_remision_fisica";
    public static final String SECUENCIAL_RETENCION_FISICA= "secuencial_retencion_fisica";
    public static final String SECUENCIAL_NOTA_VENTA_FISICA= "secuencial_nota_venta_fisica";
    public static final String DIRECTORIO_RESPALDO = "directorio_respaldo";
    
    public static final String SECUENCIAL_LOTE= "secuencial_lote";
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    
    @Column(name = "NOMBRE")
    public String nombre;
    
    @Column(name = "VALOR")    
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
