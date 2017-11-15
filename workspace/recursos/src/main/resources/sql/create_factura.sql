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
    CLIENTE_ID varchar(70),
    TIPO_IDENTIFICACION_ID varchar(1),
    SECUENCIAL varchar(100),
    PUNTO_ESTABLECIMIENTO decimal,
    PUNTO_EMISION decimal,
    FECHA_FACTURA date,
    FECHA_CREACION date,
    SUBTOTAL_SIN_IMPUESTOS decimal,
    SUBTOTAL_DOCE decimal,
    SUBTOTAL_CERO decimal,
    VALOR_IVA_DOCE decimal,
    VALOR_IVA_CERO decimal,
    IVA_SRI_ID decimal,
    TOTAL decimal,primary key (ID_CLIENTE),
    USUARIO_ID decimal,
    ESTADO,
)

create table DETALLE_FACTURA
(
    ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    PRODUCTO_ID,
    CANTIDAD ,
    PRECIO_UNITARIO,
    DESCUENTO,
    VALOR_ICE
)

create table FORMA_PAGO_FACTURA
(
    ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    FORMA_PAGO_ID,
    TOTAL,
    PLAZO,
    UNIDAD_TIEMPO
)

create table FORMA_PAGO_FACTURA
(
    ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    FORMA_PAGO_ID,
    TOTAL,
    PLAZO,
    UNIDAD_TIEMPO
)





