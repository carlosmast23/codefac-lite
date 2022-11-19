/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  PC
 * Created: 08-ene-2018
 */

create table EMPRESA
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    TELEFONOS varchar(10),
    RAZON_SOCIAL varchar(300),
    NOMBRE_LEGAL varchar(300),
    DIRECCION varchar(100),
    IDENTIFICACION varchar(50),
    LOGO_IMAGEN_PATH varchar(512),
    OBLIGADO_LLEVAR_CONTABILIDAD varchar(3),
    CONTRIBUYENTE_ESPECIAL varchar(50),
    CELULAR varchar(10),
    FACEBOOK varchar(50),
    TEXTO1 varchar(1024),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.1)*/
    CODIGO varchar(3),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.3.6)*/
    ORDEN INT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.2.9)*/
    ESTADO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.4.7)*/
    CONTRIBUYENTE_REGIMEN_MICROEMPRESAS varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.4.7)*/
    AGENTE_RETENCION_RESOLUCION varchar(50),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.5.5)*/
    INSTAGRAM varchar(200),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.0.0)*/
    RIMPE_NEGOCIOS_POPULARES varchar(20),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.0.0)*/
    RIMPE_EMPRENDEDORES varchar(20),
    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.6.9.6)*/
create table SUCURSAL
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    EMPRESA_ID BIGINT,
    NOMBRE varchar(120),
    TIPO varchar(1),
    ESTADO varchar(1),
    TELEFONO varchar(16),
    CELULAR varchar(32),    
    DIRECCION varchar(100),
    EMAIL varchar(100),
    COD_SUCURSAL BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.1)*/
    CODIGO varchar(3),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.5.4)*/
    DIRECCION_MATRIZ varchar(100),
    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.6.9.6)*/
create table PUNTO_EMISION
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    SUCURSAL_ID BIGINT,
    DESCRIPCION varchar(120),
    PUNTO_EMISION BIGINT,
    TIPO_FACTURACION varchar(1),
    ESTADO varchar(1),
    SECUENCIAL_FACTURA BIGINT,
    SECUENCIAL_NOTA_CREDITO BIGINT,
    SECUENCIAL_NOTA_DEBITO BIGINT,
    SECUENCIAL_GUIA_REMISION BIGINT,
    SECUENCIAL_RETENCIONES BIGINT,
    SECUENCIAL_NOTA_VENTA BIGINT,
    SECUENCIAL_NOTA_VENTA_INTERNA BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.6)*/
    SECUENCIAL_LIQUIDACION_COMPRA BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.5.8)*/
    SECUENCIAL_GUIA_REMISION_INTERNA BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.2.9)*/
    TIPO_NOTA_VENTA_INTERNA varchar(1),

    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.7.9.8)*/
create table PUNTO_EMISION_USUARIO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    USUARIO_ID BIGINT,
    PUNTO_EMISION_ID BIGINT,
    ESTADO varchar(1),   
    primary key (ID)
);