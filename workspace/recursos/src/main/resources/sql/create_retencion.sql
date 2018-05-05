/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 4/05/2018
 */



create table RETENCION
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    CLAVE_ACCESO varchar(70),
    EMPRESA_ID varchar(70),
    PROVEEDOR_ID BIGINT,
    COMPRA_ID BIGINT,
    TIPO_IDENTIFICACION_ID varchar(1),
    SECUENCIAL varchar(100),
    PUNTO_ESTABLECIMIENTO decimal,
    PUNTO_EMISION decimal,
    FECHA_EMISION date,
    FECHA_CREACION date,
    USUARIO_ID decimal,
    ESTADO varchar(1),
    RAZON_SOCIAL varchar(256),
    IDENTIFICACION varchar(15),
    DIRECCION varchar(1024),
    TELEFONO varchar(10),
    primary key (ID)

);
create table RETENCION_DETALLE
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    RETENCION_ID BIGINT,
    BASE_IMPONIBLE decimal(7,3),
    PORCENTAJE_RETENER decimal(7,3),
    VALOR_RETENIDO decimal(7,3),
    CODIGO_SRI varchar(5),
    CODIGO_RETENCION_SRI varchar(5),
    primary key (ID)
);

create table RETENCION_ADICIONAL
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    RETENCION_ID BIGINT,
    CAMPO varchar(150),
    VALOR varchar(150),
    TIPO varchar(1),
    NUMERO integer ,
    primary key (ID)
)