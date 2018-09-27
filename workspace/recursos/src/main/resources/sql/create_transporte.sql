/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CodesoftDesarrollo
 * Created: 26/07/2018
 */
/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.6.0)*/
create table TRANSPORTISTA
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    IDENTIFICACION varchar(15),
    NOMBRES varchar(30),
    APELLIDOS varchar(30),
    RAZON_SOCIAL varchar(100),
    NOMBRE_COMERCIAL varchar(100),
    CORREO_ELECTRONICO varchar(100),
    DIRECCION varchar(150),
    PLACA_VEHICULO varchar(10),
    TELEFONO_CELULAR varchar(15),
    TELEFONO_CONVENCIONAL varchar(15),
    ESTADO varchar(1),
    TIPO_IDENTIFICACION varchar(1), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.1)*/
    OBLIGADO_LLEVAR_CONTABILIDAD varchar(2),
    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.6.0)*/
create table GUIA_REMISION
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    CLAVE_ACCESO varchar(70),
    SECUENCIAL varchar(100),
    PUNTO_ESTABLECIMIENTO decimal,
    PUNTO_EMISION decimal,
    EMPRESA_ID BIGINT,
    USUARIO_ID decimal,
    FECHA_CREACION date,
    FECHA_EMISION date,
    RAZON_SOCIAL varchar(256),
    IDENTIFICACION varchar(15),
    DIRECCION varchar(1024),
    TELEFONO varchar(10),
    ESTADO varchar(1),
    TIPO_FACTURACION varchar(1),
    CODIGO_DOCUMENTO varchar(3),
    DIRECCION_PARTIDA varchar(120),
    TRANSPORTISTA_ID BIGINT,
    RISE varchar(64),
    OBLIGADO_LLEVAS_CONTABILIDAD varchar(4),
    CONTRIBUYENTE_ESPECIAL varchar(4),
    FECHA_INICIO_TRANSPORTE date,
    FECHA_FIN_TRANSPORTE date,
    PLACA varchar(32),
    primary key (ID)

);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.6.0)*/
create table DESTINATARIO_GUIA_REMISION
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    GUIA_REMISION_ID BIGINT,
    REFERENCIA_DOCUMENTO_ID BIGINT,
    PERSONA_ID BIGINT,
    RAZON_SOCIAL varchar(128),
    DIRECCION_DESTINO varchar(256),
    MOTIVO_TRANSLADO varchar(256),
    RUTA varchar(128),
    COD_DOCUMENTO_SUSTENTO varchar(32),
    PREIMPRESO varchar(32),
    AUTORIZACION_NUMERO varchar(128),
    FECHA_EMISION date,
    primary key (ID)

);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.6.0)*/
create table DETALLE_PRODUCTO_GUIA_REMISION
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    DESTINATARIO_ID BIGINT,
    CODIGO_INTERNO varchar(32),
    CODIGO_ADICIONAL varchar(64),
    DESCRIPCION varchar(256),
    CANTIDAD integer,
    REFERENCIA_ID BIGINT,
    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.6.0)*/
create table GUIA_REMISION_ADICIONAL
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    NOTA_CREDITO_ID BIGINT,
    CAMPO varchar(150),
    VALOR varchar(150),
    TIPO varchar(1),
    NUMERO integer ,
    primary key (ID)
)