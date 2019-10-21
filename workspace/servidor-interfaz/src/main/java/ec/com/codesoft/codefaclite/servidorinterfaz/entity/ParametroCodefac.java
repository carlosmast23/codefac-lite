/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "PARAMETRO")
public class ParametroCodefac implements Serializable {
    /**
     * NOTA: NO MODIFICAR ESTOS NOMBRES PORQUE YA ESTAN EN LA BASE DE DATOS , Y MODIFICARLOS PUEDE OCACIONAR PROBLEMAS
     */
    public static final String NOMBRE_FIRMA_ELECTRONICA= "firma_electronica";
    public static final String CLAVE_FIRMA_ELECTRONICA= "clave_firma_electronica";
    public static final String DIRECTORIO_RECURSOS= "directorio_recursos";
    public static final String LOGO_EMPRESA= "logo_empresa";
    public static final String MODO_FACTURACION= "modo_facturacion";
    public static final String IVA_DEFECTO= "iva_defecto";
    
    public static final String EDITAR_DESCRIPCION_FACTURA= "edit_descripcion_factura";
    public static final String EDITAR_DESCUENTO_FACTURA= "edit_descuento_factura";
    public static final String EDITAR_PRECIO_UNIT_FACTURA= "edit_precio_unit_factura";
    
    
    //public static final String SRI_WS_RECEPCION= "sri_ws_recepcion";
    //public static final String SRI_WS_AUTORIZACION= "sri_ws_autorizacion";
    //public static final String SRI_WS_RECEPCION_PRUEBA= "sri_ws_recepcion_prueba";
    //public static final String SRI_WS_AUTORIZACION_PRUEBA= "sri_ws_autorizacion_prueba";
    
    /*
    public static final String SECUENCIAL_FACTURA= "secuencial_factura";
    public static final String SECUENCIAL_NOTA_CREDITO= "secuencial_nota_credito";
    public static final String SECUENCIAL_NOTA_DEBITO= "secuencial_nota_debito";
    public static final String SECUENCIAL_GUIA_REMISION= "secuencial_guia_remision";
    public static final String SECUENCIAL_RETENCION= "secuencial_retencion";*/
    
    public static final String CORREO_USUARIO= "correo_usuario";
    public static final String CORREO_CLAVE= "correo_clave";
    public static final String SMTP_HOST= "smtp_host";
    public static final String SMTP_PORT= "smtp_port";
    
    public static final String CELULAR_VIRTUAL_MALL="celular_virtual_mall";
    public static final String IMAGEN_FONDO="imagen_fondo";
    public static final String ULTIMA_FECHA_VALIDACION="ultima_fecha_validacion";
    public static final String TIPO_FACTURACION="tipo_facturacion";

    public static final String DIRECTORIO_RESPALDO = "directorio_respaldo";
    public static final String DEFECTO_TIPO_DOCUMENTO_FACTURA = "defecto_tipo_documento_factura";
    public static final String DEFECTO_TIPO_DOCUMENTO_COMPRA = "defecto_tipo_documento_compra";
    
    public static final String SECUENCIAL_LOTE= "secuencial_lote";
    
    public static final String ORDEN_TRABAJO_OBSERVACIONES = "orden_trabajo_observaciones";
    public static final String FORMATO_ORDEN_TRABAJO="formato_orden_trabajo";
    public static final String ACTIVAR_CARTERA="cartera_activa";
    
    /**
     * Parametro para activar los comporbante de venta para imprimir en las facturas electronicas
     */
    public static final String COMPROBANTE_VENTA_ACTIVAR="comprobante_venta_activar";
    
    /** Parametro para activar los comporbante de venta para imprimir en las facturas electronicas
     *
    */
    public static final String COMPROBANTE_GUIA_REMISION_ACTIVAR="comprobante_guia_remision_activar";
    
    /**
     * Tipo de envio de los comprobantes 
     */
    public static final String TIPO_ENVIO_COMPROBANTE="tipo_envio_comprobante";
    /**
     * Nombre de parametro para saber si en la pantalla de facutura tiene que cargar los precios unitarios con iva o no
     */
    public static final String CARGAR_PRODUCTO_IVA_FACTURA = "cargar_producto_iva_factura";
    
    public static final String MOTIVO_TRASLADO_GUIA_REMISION = "motivo_traslado_guia_remision";
    
    public static final String VALOR_DEFECTO_RETENCION_IVA = "defecto_retencion_iva";
    public static final String VALOR_DEFECTO_RETENCION_RENTA = "defecto_retencion_renta";
    
    public static final String IMPRESORA_TICKETS_VENTAS = "impresora_tickets_venta";
    
    public static final String FORMATO_MENSAJE_COMPROBANTE_ELECTRONICO = "formato_mensaje_comprobante_electronico";
    /**
     * Tipo de envio de los comprobantes 
     */
    public static final String VARIABLES_GENERAL_COMPROBANTES_ELECTRONICOS="variables_generales_comprobantes_electronicos";
    
    /**
     * Variable que me sirve para activar la nota de venta cuando estoy realizando facturacion electronica
     */
    public static final String ACTIVAR_NOTA_VENTA="activar_nota_venta";
    
    /**
     * Variable que me permite saber si se puede realizar una facturacion cuando no existe el suficiente stock requerido 
     * @result EnumSiNO
     */
    public static final String FACTURAR_INVENTARIO_NEGATIVO="facturar_inventario_negativo";
    
    /**
     * Esta opcion me permite generar ensambles si no existe al momento de facturar
     * @result EnumSiNO
     */
    public static final String CONSTRUIR_ENSAMBLES_FACTURAR="construir_ensamble_facturar";
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    @Column(name = "NOMBRE")
    public String nombre;
    
    @Column(name = "VALOR")    
    public String valor;

    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;

    public ParametroCodefac() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParametroCodefac other = (ParametroCodefac) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    public enum TipoEnvioComprobanteEnum
    {
        ENVIAR_FIRMADO("Enviar firmados","f"),
        ENVIAR_AUTORIZADO("Enviar autorizados","a");

        private TipoEnvioComprobanteEnum(String nombre, String letra) {
            this.nombre = nombre;
            this.letra = letra;
        }
       
        private String nombre;
        private String letra;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getLetra() {
            return letra;
        }

        public void setLetra(String letra) {
            this.letra = letra;
        }

        @Override
        public String toString() {
            return nombre;
        }
        
        public static TipoEnvioComprobanteEnum buscarPorLetra(String letra)
        {
            for (TipoEnvioComprobanteEnum enumerador : TipoEnvioComprobanteEnum.values()) 
            {
                if(enumerador.getLetra().equals(letra))
                {
                    return enumerador;
                }
            }
            return null;
        }
        
    }
    
    /**
     * Metodo experimental para ver si no tengo que validar 
     * @param valorComporar
     * @return 
     */
    public Boolean compararEnumSiNo( EnumSiNo valorComporar)
    {
        if (this != null) {
            //Solo si tiene parametro positivo intento construir el ensamble
            if (getValor() != null && EnumSiNo.getEnumByLetra(getValor()).equals(valorComporar)) {
                return true;
            }
        }
        return false;
    }
    
    
}
