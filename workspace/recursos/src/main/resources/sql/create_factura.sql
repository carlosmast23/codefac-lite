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
    ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    CLAVE_ACCESO varchar(70),
    EMPRESA_ID varchar(70),
    CLIENTE_ID integer,
    TIPO_IDENTIFICACION_ID varchar(1),
    SECUENCIAL varchar(100),
    PUNTO_ESTABLECIMIENTO decimal,
    PUNTO_EMISION decimal,
    FECHA_FACTURA date,
    FECHA_CREACION date,
    DESCUENTO_IVA decimal(7,2),
    DESCUENTO_IVA_CERO decimal(7,2),
    SUBTOTAL_IVA decimal(7,2),
    SUBTOTAL_IVA_CERO decimal(7,2),
    IVA decimal(7,2),
    IVA_SRI_ID decimal,
    TOTAL decimal(7,2),
    USUARIO_ID decimal,
    ESTADO varchar(1),
    RAZON_SOCIAL varchar(256),
    IDENTIFICACION varchar(15),
    DIRECCION varchar(1024),
    TELEFONO varchar(10),
    primary key (ID),
    CONSTRAINT id_cliente_factura_fk FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE(CLIENTE_ID)

);

create table FACTURA_DETALLE
(
    ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    PRODUCTO_ID INTEGER,
    FACTURA_ID INTEGER,
    CANTIDAD integer ,
    PRECIO_UNITARIO decimal(7,3),
    DESCUENTO decimal(7,2),
    VALOR_ICE decimal,
    IVA decimal(7,2),
    TOTAL decimal(7,2),
    DESCRIPCION varchar(150),
    primary key (ID)
)