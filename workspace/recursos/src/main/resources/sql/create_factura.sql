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
    FECHA_CREACION date,
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
    TELEFONO varchar(10),
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
    FECHA_AUTORIZACION_SRI date,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.5)*/
    VALOR_ICE decimal(13,2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.8)*/
    SUCURSAL_EMPRESA_ID BIGINT,
    
    primary key (ID)
    /*CONSTRAINT id_cliente_factura_fk FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE(CLIENTE_ID)*/

);

create table FACTURA_DETALLE
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    REFERENCIA_ID BIGINT,
    TIPO_REFERENCIA varchar(5),
    FACTURA_ID BIGINT,
    CANTIDAD integer ,
    PRECIO_UNITARIO decimal(13,3),
    DESCUENTO decimal(13,2),
    VALOR_ICE decimal(13,2),
    IVA decimal(13,2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    IVA_PORCENTAJE INT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    ICE_PORCENTAJE INT,
    TOTAL decimal(13,2),
    DESCRIPCION varchar(300),
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
)