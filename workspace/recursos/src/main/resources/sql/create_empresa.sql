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
    RAZON_SOCIAL varchar(50),
    NOMBRE_LEGAL varchar(50),
    DIRECCION varchar(100),
    IDENTIFICACION varchar(50),
    LOGO_IMAGEN_PATH varchar(512),
    OBLIGADO_LLEVAR_CONTABILIDAD varchar(3),
    CONTRIBUYENTE_ESPECIAL varchar(50),
    CELULAR varchar(10),
    FACEBOOK varchar(50),
    TEXTO1 varchar(100),
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
    
    primary key (ID)
);