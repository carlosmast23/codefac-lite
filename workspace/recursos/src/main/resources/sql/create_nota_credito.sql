/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 6/12/2017
 */


create table NOTA_CREDITO
(
    ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    CLAVE_ACCESO varchar(70),
    EMPRESA_ID varchar(70),
    CLIENTE_ID integer,
    FACTURA_ID integer,
    TIPO_IDENTIFICACION_ID varchar(1),
    SECUENCIAL varchar(100),
    PUNTO_ESTABLECIMIENTO decimal,
    PUNTO_EMISION decimal,
    FECHA_NOTA_CREDITO date,
    FECHA_CREACION date,
    SUBTOTAL_SIN_IMPUESTOS decimal(7,2),
    SUBTOTAL_DOCE decimal(7,2),
    SUBTOTAL_CERO decimal(7,2),
    VALOR_IVA_DOCE decimal(7,2),
    VALOR_IVA_CERO decimal(7,2),
    IVA_SRI_ID decimal,
    TOTAL decimal(7,2),
    USUARIO_ID decimal,
    ESTADO varchar(1),
    RAZON_MODIFICADO varchar(128),
    primary key (ID),
    CONSTRAINT id_cliente_fk FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE(CLIENTE_ID)
    CONSTRAINT id_factura_fk FOREIGN KEY (FACTURA_ID) REFERENCES FACTURA(ID)

);

create table NOTA_CREDITO_DETALLE
(
    ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    PRODUCTO_ID INTEGER,
    NOTA_CREDITO_ID INTEGER,
    CANTIDAD integer ,
    PRECIO_UNITARIO decimal(7,3),
    DESCUENTO decimal(7,2),
    VALOR_ICE decimal,
    IVA decimal(7,2),
    TOTAL decimal(7,2),
    DESCRIPCION varchar(150),
    primary key (ID)
)