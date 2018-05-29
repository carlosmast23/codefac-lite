/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 15/11/2017
 */

create table COMPRA
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    CLAVE_ACCESO varchar(70),
    EMPRESA_ID BIGINT,
    PROVEEDOR_ID BIGINT,
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
    ESTADO_RETENCION varchar(1),
    RAZON_SOCIAL varchar(256),
    IDENTIFICACION varchar(15),
    DIRECCION varchar(1024),
    TELEFONO varchar(10),
    TIPO_FACTURACION varchar(1),
    CODIGO_DOCUMENTO varchar(3),
    CODIGO_TIPO_DOCUMENTO varchar(3),
    INVENTARIO_INGRESO varchar(1),
    primary key (ID),
    CONSTRAINT id_cliente_compra_fk FOREIGN KEY (PROVEEDOR_ID) REFERENCES CLIENTE(CLIENTE_ID)

);

create table COMPRA_DETALLE
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    PRODUCTO_PROVEEDOR_ID BIGINT,
    SRI_RETENCION_IVA_ID BIGINT,
    SRI_RETENCION_RENTA_IVA_ID BIGINT,
    COMPRA_ID BIGINT,
    CANTIDAD integer ,
    PRECIO_UNITARIO decimal(7,3),
    DESCUENTO decimal(7,2),
    VALOR_ICE decimal,
    VALOR_RETENCION_IVA decimal(7,2),
    VALOR_RETENCION_RENTA decimal(7,2),
    IVA decimal(7,2),
    TOTAL decimal(7,2),
    DESCRIPCION varchar(150),
    primary key (ID)
);

create table ORDEN_COMPRA
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    PROVEEDOR_ID BIGINT,
    EMPRESA_ID BIGINT,
    ESTADO varchar(1),
    USUARIO_ID BIGINT,
    FECHA_INGRESO date,
    FECHA_CREACION date,
    OBSERVACION varchar(256),
    CODIGO_TIPO_DOCUMENTO varchar(3),
    IVA_SRI_ID decimal,
    IVA decimal(7,2),
    TOTAL decimal(7,2),
    DESCUENTO_IVA decimal(7,2),
    DESCUENTO_IVA_CERO decimal(7,2),
    SUBTOTAL_IVA decimal(7,2),
    SUBTOTAL_IVA_CERO decimal(7,2),

    primary key (ID),
    CONSTRAINT id_cliente_orden_compra_fk FOREIGN KEY (PROVEEDOR_ID) REFERENCES CLIENTE(CLIENTE_ID)

);

create table ORDEN_COMPRA_DETALLE
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    ORDEN_COMPRA_ID BIGINT,
    PRODUCTO_PROVEEDOR_ID BIGINT,
    CANTIDAD integer ,  
    PRECIO_UNITARIO decimal(7,3),  
    DESCUENTO decimal(7,2),
    VALOR_ICE decimal,
    DESCRIPCION varchar(150),
    TOTAL decimal(7,2),
    IVA decimal(7,2),

    primary key (ID)
);























