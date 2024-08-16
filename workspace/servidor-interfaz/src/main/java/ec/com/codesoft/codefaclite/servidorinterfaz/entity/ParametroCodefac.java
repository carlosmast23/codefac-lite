/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades.ComparadorInterface;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

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
    /**
     * Este paramtro me sirve para elegir cuando existe varios ivas vigentes con cual el sistema debe trabajar por defecto
     * No confundir con el IVA_DEFECTO de parametro del sistema que solo es un valor referencial para saber el iva actual
     * En este caso se puede SELECCIONAR IVA 12 O IVA CERO
     */
    @Deprecated
    public static final String IVA_DEFECTO= "iva_defecto";
    
    public static final String EDITAR_DESCRIPCION_FACTURA= "edit_descripcion_factura";
    public static final String EDITAR_DESCUENTO_FACTURA= "edit_descuento_factura";
    public static final String EDITAR_PRECIO_UNIT_FACTURA= "edit_precio_unit_factura";
    public static final String MOSTRAR_COSTOS_FACTURAR="mostrar_costos_facturar";
    public static final String MOSTRAR_AHORRO_VENTA="mostrar_ahorro_venta";
    public static final String MOSTRAR_STOCK_VENTA="mostrar_stock_venta";
    public static final String EDITAR_FECHA_FACTURA= "edit_fecha_factura";
    
    /**
     * Esta opcion me permite controlar en las ventas que no puedan ingresar un valor inferior al precio original
     */
    public static final String MODIFICAR_PRECIO_MENOR="modificar_precio_menor";
    
    public static final String MOSTRAR_COSTO_CON_IVA="mostrar_costo_con_iva";
    
    
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
    //@Deprecated
    //public static final String TIPO_FACTURACION="tipo_facturacion";

    
    public static final String DEFECTO_TIPO_DOCUMENTO_FACTURA = "defecto_tipo_documento_factura";
    public static final String DEFECTO_TIPO_DOCUMENTO_COMPRA = "defecto_tipo_documento_compra";
    
    public static final String SECUENCIAL_LOTE= "secuencial_lote";
    
    public static final String ORDEN_TRABAJO_OBSERVACIONES = "orden_trabajo_observaciones";
    public static final String FORMATO_ORDEN_TRABAJO="formato_orden_trabajo";
    public static final String ACTIVAR_CARTERA="cartera_activa";
    
    //public static final String GENERAR_CARTERA_VENTA_SIN_CREDITO="generar_cartera_venta_sin_credito";
    
    public static final String NUMERO_PRECIO_PRESUPUESTO="numero_precio_presupuesto";
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
    
    public static final String ALERTA_DIAS_POR_CADUCAR_PRODUCTO = "alerta_dias_por_caducar_producto";
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
    
    public static final String AGREGAR_PRODUCTO_DIRECTO_LECTOR_BARRAS="agregar_producto_directo_lector_barras";
    
    public static final String VENDEDOR_OBLIGATORIO_VENTA="vendedor_obligatorio_venta" ;
    
    public static final String ADVERTENCIA_UTILIDAD_NEGATIVA="advertencia_utilidad_negativa";
    
    /**
     * Permite que se agregue los detalles nuevos de las facturas al inicio y no al final
     */
    public static final String AGREGAR_DETALLE_INICIO="agregar_detalle_inicio";
    
    /**
     * Parametro parametro que me permite elegir si en los detalles me permiten agregar la información del lote del producto
     */
    public static final String AGREGAR_LOTE_FACTURA="agregar_lote_factura";
    
    public static final String AGREGAR_INFO_ADICIONAL_LOTE_FACTURA="agregar_info_adicional_lote_factura";
    
    /**
     * Configuracion que me permite establecer que se abran varias ventanas al mismo tiempo
     */
    public static final String VENTANAS_MULTIPLES="ventanas_multiples";
    
    
    /**
     * Esta opcion me permite generar ensambles si no existe al momento de facturar
     * @result EnumSiNO
     */
    public static final String CONSTRUIR_ENSAMBLES_FACTURAR="construir_ensamble_facturar";
    
    /**
     * Esta opcion me permite generar ensambles si no existe al momento de facturar
     * @result EnumSiNO
     */
    public static final String PERMITIR_DESCUENTO_MENOR_COSTO="permitir_descuento_menor_costo";
    
    /**
     * Fecha inicial cuando sacaron la firma para que el sistema pueda emitir una alerta cuando falte poco tiempo para renovar
     */
    public static final String FIRMA_FECHA_EMISION="firma_fecha_emision";
    
    /**
     * Tiempo en años de vigencia de la firma electronica para qe el sistema pueda mandar alertas
     */
    public static final String FIRMA_TIEMPO_EXPIRACION_AÑOS="firma_tiempo_expiracion_años";
    
    /**
     * Datos adicionales para controlar que datos adicionales imprimir en el RIDE
     */
    public static final String FACTURACION_RIDE_RUC_EMPLEADO="facturacion_ride_ruc_empleado";
    public static final String FACTURACION_RIDE_RAZON_SOCIAL_EMPLEADO="facturacion_ride_razon_social_empleado";
    public static final String FACTURACION_RIDE_DIRECCION_EMPLEADO="facturacion_ride_direccion_empleado";
    public static final String FACTURACION_RIDE_PUNTO_EMISION_EMPLEADO="facturacion_ride_punto_emision_empleado";
    public static final String FACTURACION_RIDE_REFERENCIA_DIRECCION="facturacion_ride_referencia_direccion";
    public static final String FACTURACION_RIDE_CODIGO_PERSONALIZADO="facturacion_ride_codigo_personalizado";
    public static final String FACTURACION_RIDE_PLACA_EMPLEADO="facturacion_ride_placa_empleado";
    
    
    
    public static final String HABILITAR_RETENCION_COMPRAS="habilitar_retencion_compras";
    
    public static final String DIAS_ALERTA_PAGO="dias_alerta_pago";
    
    public static final String DIAS_ALERTA_COMPROBANTES_PENDIENTES="dias_alerta_comprobantes_pendientes";
    
    /**
     * Variable que identifica que algunos campos como clientes, proveedores , proveedores pueden usar entre diferentres empresas
     */
    public static final String DATOS_COMPARTIDOS_EMPRESA="datos_compartidos_empresa";
    
    public static final String CONFIGURACION_IMPRESORA_FACTURA="config_impresora_factura";
    
    public static final String PRECIO_VENTA_DEFECTO="precio_venta_defecto";
    
    public static final String FORMA_PAGO_POR_DEFECTO_PANTALLA_CLIENTE="formal_pago_defecto_pantalla_cliente";
    
    /**
     * Esta variable me permite grabar cual fue la fecha de la ultima validación de las credenciales del correo
     * Bastante util para no validar a cada momento el correo
     */
    public static final String FECHA_VALIDACION_CORREO="fecha_validacion_correo";
    

    
    
    public static final String DOCUMENTO_DEFECTO_VISTA_FACTURA="documento_defecto_vista_factura";
    
    /**
     * Parametro que me permite identificar si las facturas para generar las guias de remision deben estar autorizadas o sin autrorizar
     */
    public static final String MODO_FACTURACION_GUIA_REMISION="modo_factura_guia_remision";
    
    /**
     * Esta variable me permite filtrar las facturas por el usuario ingresado
     */
    public static final String FILTRAR_FACTURAS_POR_USUARIO="filtar_facturas_por_usuario" ;

    public static final String REPORTE_DEFECTO_VENTA="reporte_defecto_venta";
    
    public static final String REPORTE_ORIENTACION_VENTA="reporte_orientacion_venta";
    
    public static final String REPORTE_DEFECTO_PEDIDO="reporte_defecto_pedido";
    
    public static final String IMPRESORA_DEFECTO_COMANDA="impresora_defecto_comanda";
    public static final String IMPRESORA_DEFECTO_COMANDA_2="impresora_defecto_comanda_2";
    
    public static final String IMPRESORA_DEFECTO_FACTURA="impresora_defecto_factura";
    
    public static final String COPIAS_IMPRESORA_COMANDA="copias_impresora_comanda";
    
    public static final String COPIAS_IMPRESORA_VENTA="copias_impresora_venta";
    
    public static final String AJUSTAR_MARGEN_TICKET="ajustar_margen_ticket";
    
    public static final String DIAS_CREDITO_ALERTA="dias_credito_alerta";
    
    public static final String PUNTO_EMISION_DEFECTO_PRESUPUESTOS="punto_emision_defecto_presupuestos";
    
    /**
     * Activar credito por defecto para la pantalla de ventas para saber si tiene que ir las ventas a cartera o no
     */
    public static final String CREDITO_DEFECTO_VENTAS="credito_defecto_venta";
    
    /**
     * Permite establecer cual es el limite de items en el detalle cuando se hace facturación manual
     */
    public static final String NUMERO_MAXIMO_DETALLES_FACTURA="numero_max_det_factura";
    
    /**
     * Mostrar titulo facturacion fisica
     */
    public static final String MOSTRAR_TITULO_FACT_FISICA="mostrar_titulo_fact_fisica";
    
    /**
     * Variable que me permite contralar si la nota de venta interna debe incluir el iva al momento de realziar el calculo
     */
    public static final String NOTA_VENTA_INTERNA_IVA="NOTA_VENTA_INTERNA_IVA";
    
    /**
     * Variable que me permite editar una nota de venta interna
     */
    public static final String PERMITIR_EDITAR_NVI="PERMITIR_EDITAR_NVI";
    
    /**
     * Variable que me permite controlar si se debe tomar en cuenta el valor del iva cuando se hacen notas de venta interna
     */
    public static final String NVI_TOTAL_CON_IVA="NVI_TOTAL_CON_IVA";
    
    public static final String ENVIAR_NVI_CORREO="ENVIAR_NVI_CORREO";
    
    /**
     * Filtro rapido que me permite hacer la busqueda de forma rapida
     */
    public static final String FILTRO_RAPIDO_BUSQUEDA="FILTRO_RAPIDO_BUSQUEDA";
    
    /**
     * Filtro rapido que me permite hacer la busqueda de forma rapida
     */
    public static final String INGRESO_MAYUSCULAS="INGRESO_MAYUSCULAS";
    
    /**
     * Venta producto en modo de descuento
     */
    public static final String VENTA_PRODUCTO_MODO_DESCUENTO="VENTA_PRODUCTO_MODO_DESCUENTO";
    
    /**
     * Parametros para la firma de la factura
     */
    public static final String LEYENDA_FIRMA_FACTURA_1 = "leyenda_firma_factura_1";
    public static final String LEYENDA_FIRMA_FACTURA_2 = "leyenda_firma_factura_2";
    public static final String LEYENDA_FIRMA_FACTURA_3 = "leyenda_firma_factura_3";
    public static final String NUMERO_DECIMALES_RIDE = "numero_decimales_ride";
    
    public static final String DOCUMENTO_GUIA_REMISION_DEFECTO = "documento_guia_remision_defecto";
    
    public static final String PROFORMA_FACTURAR_VARIAS_VECES="proforma_facturar_varias_veces";
    
    public static final String NUMERO_DECIMAL_PRODUCTO = "numero_decimal_producto";
    
    public static final String MARGEN_MINIMO_DESCUENTO_VENTA = "margen_minimo_descuento_venta";
    
    public static final String VERSION_SISTEMA="version_sistema";
    
    /**
     * Metodo que me permite imprimir el codigo interno del producto al momento de generar la factura
     */
    public static final String IMPRIMIR_CODIGO_INTERNO_PRODUCTO="imprimir_codigo_interno_producto";
    
    public static final String RIDE_NOMBRE_PRINCIPAL="ride_nombre_principal";
    
    public static final String TIPO_NEGOCIO="tipo_negocio";
    
    public static final String DATO_ADICIONAL_TITULO="dato_adicional_titulo";
    
    public static final String PERMITIR_EDITAR_CODIGO="permitir_editar_codigo";
    
    public static final String ACTIVAR_IVA_FERIADO="activar_iva_feriado";
    
    /**
     * Varaible que me permite configurar que en la pantalla de facturacion se quede grabado el precio por defecto (pvp1,pvp2,pvp3,pvp4) anterior para seguir agregando el resto de productos con el mismo pvp
     */
    public static final String AGREGAR_PVP_ANTERIOR="agregar_pvp_anterior";
    
    public static final String LEYENDA_ADICIONAL_COMPROBANTE="leyenda_adicional_comprobante";
    
    /**
     * Metodo que me permite activar o desactivar la opcion para poder actualizar los precios de venta cuando se modifican los costos
     */
    public static final String ADVERTENCIA_ACTUALIZAR_COSTO="advertencia_actualizar_costo";
    
    /**
     * Variable que me permite mostrar un único dialogo siempre de inventario para todos los buscadores
     */
    public static final String BUSCADOR_SOLO_INVENTARIO="buscador_solo_inventario";
    
    /**
     * Variable que permite activar si se quieren tener en cuenta los descuentos al momento de calcular los costos
     */
    public static final String CALCULAR_DESCUENTO_COSTO="calcular_descuento_costo";
    
    /**
     * Calcular la utilidad de con el ultimo costo y no con el costo promedio
     */
    public static final String CALCULAR_UTILIDAD_ULTIMO_COSTO="calcular_utilidad_ultimo_costo";
    
    /**
     * Mostrar precios con iva en las pantallas
     */
    public static final String MOSTRAR_PRECIO_CON_IVA="mostrar_precio_con_iva";
    
    public static final String RECALCULA_PRECIO_PORCENTAJE="recalcular_precio_porcentaje";
    
    public static final String RECALCULA_EDITAR_PRECIO_EMPAQUE="recalcular_editar_precio_porcentaje";
    
    public static final String PVP1_MARGEN_UTILIDAD = "pvp1_margen_utilidad";
    public static final String PVP2_MARGEN_UTILIDAD = "pvp2_margen_utilidad";
    public static final String PVP3_MARGEN_UTILIDAD = "pvp3_margen_utilidad";
    public static final String PVP4_MARGEN_UTILIDAD = "pvp4_margen_utilidad";
    public static final String PVP5_MARGEN_UTILIDAD = "pvp5_margen_utilidad";
    public static final String PVP6_MARGEN_UTILIDAD = "pvp6_margen_utilidad";
    

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

    public ParametroCodefac(String nombre) {
        this.nombre = nombre;
    }
    
    public ParametroCodefac(String nombre,String valor) {
        this.nombre = nombre;
        this.valor=valor;
    }
    
    

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
    
    public enum TipoEnvioComprobanteEnum implements ComparadorInterface
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

        @Override
        public Object consultarParametro(String nombreParametro) {
            return buscarPorLetra(nombreParametro);
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
    
    public static class ParametrosRespaldoDB
    {
        public static String DB_RESPALDO_AUTOMATICO_SALIR="DB_RESPALDO_AUTOMATICO_SALIR";
        public static String DB_RESPALDO_HORA_PROGRAMADA="DB_RESPALDO_HORA_PROGRAMADA";
        public static final String DIRECTORIO_RESPALDO = "directorio_respaldo";
        /**
         * Variable que me va a permitir llevar el control para que el sistema
         * automaticamenta saque respaldos cada determinado tiempo y se manden a
         * nuestro correo para siempre tener un respaldo
         */
        public static final String FECHA_ULTIMO_ENVIO_RESPALDO_SISTEMA = "fecha_ultimo_envio_respaldo_sistema";
        /**
         * Variable que me va a permitir saber si los respaldos fueron enviados la última vez o tiene algún problema con el envio
         */
        public static final String PROBLEMA_ULTIMO_ENVIO_RESPALDO="problema_ultimo_envio_respaldo";
    }
    
    public static class AliasNombresDocumentos
    {
        public static String NOTA_VENTA_INTERNA_ALIAS="NOTA_VENTA_INTERNA_ALIAS";
    }
    
    public static class Inventario
    {
        public static String F1_PRODUCTO="f1_producto";
        public static String F2_PRODUCTO="f2_producto";
        public static String F3_PRODUCTO="f3_producto";
        public static String F4_PRODUCTO="f4_producto";
        public static String F5_PRODUCTO="f5_producto";
        public static String F6_PRODUCTO="f6_producto";
        public static String F7_PRODUCTO="f7_producto";
        public static String F8_PRODUCTO="f8_producto";
        public static String F9_PRODUCTO="f9_producto";
        public static String F10_PRODUCTO="f10_producto";
        public static String F11_PRODUCTO="f11_producto";
        public static String F12_PRODUCTO="f12_producto";
        
    }
}
