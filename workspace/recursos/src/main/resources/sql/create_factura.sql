/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 15/11/2017
 */

create table FACTURA
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    CLAVE_ACCESO varchar(70),
    /*EMPRESA_ID varchar(70),*/
    EMPRESA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.8.0)*/
    SUCURSAL_ID BIGINT,
    CLIENTE_ID BIGINT,
    TIPO_IDENTIFICACION_ID varchar(1),
    SECUENCIAL varchar(100),
    PUNTO_ESTABLECIMIENTO decimal,
    PUNTO_EMISION decimal,
    FECHA_EMISION date,
    FECHA_CREACION TIMESTAMP,
    DESCUENTO_IVA decimal(13,2),
    DESCUENTO_IVA_CERO decimal(13,2),
    SUBTOTAL_IVA decimal(13,2),
    SUBTOTAL_IVA_CERO decimal(13,2),
    IVA decimal(13,2),
    IVA_SRI_ID decimal,
    TOTAL decimal(13,2),
    USUARIO_ID decimal,
    ESTADO varchar(1),
    RAZON_SOCIAL varchar(256),
    IDENTIFICACION varchar(15),
    DIRECCION varchar(1024),
    TELEFONO varchar(64),
    TIPO_FACTURACION varchar(1),
    CODIGO_DOCUMENTO varchar(3),
    ESTADO_NOTA_CREDITO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.1)*/
    OBLIGADO_LLEVAR_CONTABILIDAD varchar(2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.2)*/
    REFERIDO_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.4)*/
    TIPO_IDENTIFICACION_CODIGO_SRI varchar(3),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.9)*/
    FECHA_VENCIMIENTO_FACTURA date,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.9)*/
    VENDEDOR_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.0.0)*/
    DIRECCION_ESTABLECIMIENTO varchar(1024),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.0.0)*/
    DIRECCION_MATRIZ varchar(1024),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.0.1)*/
    ESTADO_ENVIADO_GUIA_REMISION varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.0.4)*/
    TIPO_AMBIENTE varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.0.4)*/
    FECHA_AUTORIZACION_SRI TIMESTAMP,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.5)*/
    VALOR_ICE decimal(13,2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.8)*/
    SUCURSAL_EMPRESA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.2.5)*/
    TOTAL_SUBSIDIO decimal(13,2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.3.8)*/
    PEDIDO_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.6.3)*/
    COD_ORIGEN_TRANSACCION varchar(3),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.6.5)*/
    CONTRIBUYENTE_ESPECIAL varchar(14),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.7.2)*/
    ZONA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.7.2)*/
    RUTA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.7.2)*/
    ZONA_NOMBRE varchar(128),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.7.2)*/
    RUTA_NOMBRE varchar(128),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.7.2)*/
    VENTA_CREDITO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.7.2)*/
    DIAS_CREDITO INTEGER,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.7.5)*/
    PUNTO_EMISION_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.3.8)*/
    MESA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.0.2)*/
    CODIGO_TIPO_DOCUMENTO varchar(5),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.0.2)*/
    NOMBRE_TIPO_DOCUMENTO varchar(128),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.1.7)*/
    AHORRO decimal(13,2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.5.5)*/
    IRBPNR decimal(13,2),
    primary key (ID)
    /*CONSTRAINT id_cliente_factura_fk FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE(CLIENTE_ID)*/

);

create table FACTURA_DETALLE
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    REFERENCIA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.8.8)*/
    CATALOGO_PRODUCTO_ID BIGINT,
    TIPO_REFERENCIA varchar(5),
    FACTURA_ID BIGINT,
    CANTIDAD decimal(13,5),
    PRECIO_UNITARIO decimal(13,6),
    DESCUENTO decimal(13,2),
    VALOR_ICE decimal(13,2),
    IVA decimal(13,2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    IVA_PORCENTAJE INT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    ICE_PORCENTAJE INT,
    TOTAL decimal(13,2),
    DESCRIPCION varchar(300),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.2.1)*/
    CODIGO_PRINCIPAL varchar(100),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.2.5)*/
    PRECIO_SIN_SUBSIDIO decimal(13,2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.0.6)*/
    LOTE_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.0.8)*/
    COSTO_PROMEDIO decimal(13,6),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.3.3)*/
    CANTIDAD_PRESENTACION decimal(13,5),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.3.7)*/
    KARDEX_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.3.7)*/
    RESERVADO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.9.4)*/
    KARDEX_ITEM_ESPECIFICO_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.0.0)*/
    RESPONSABLE_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.1.7)*/
    PRECIO_SIN_AHORRO decimal(13,2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.1.9)*/
    NUMERO_PVP INT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.3.0)*/
    PRESENTACION_CODIGO varchar(50),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.5.5)*/
    IRBPNR decimal(13,2),
    primary key (ID)
);

create table FACTURA_FORMA_PAGO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    SRI_FORMA_PAGO_ID BIGINT,
    FACTURA_ID BIGINT,
    PLAZO integer ,
    TOTAL decimal(13,2),
    UNIDAD_TIEMPO varchar(150),
    primary key (ID)
);

create table FACTURA_ADICIONAL
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    FACTURA_ID BIGINT,
    CAMPO varchar(150),
    VALOR varchar(150),
    TIPO varchar(1),
    NUMERO integer ,
    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.3.0.5.6)*/
create table REEMBOLSO_DETALLE
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    FACTURA_ID BIGINT,
    PROVEEDOR_ID BIGINT,
    PUNTO_ESTABLECIMIENTO INT,
    PUNTO_EMISION INT,
    SECUENCIAL BIGINT,
    FECHA_EMISION TIMESTAMP,
    NUMERO_AUTORIZACION varchar(150),
    DESCRIPCION varchar(300),
    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.3.0.5.6)*/
create table REEMBOLSO_IMPUESTO_DETALLE
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    IMPUESTO_ID BIGINT,
    PORCENTAJE_IVA INT,
    BASE_IMPONIBLE decimal(13,2),
    primary key (ID)
);