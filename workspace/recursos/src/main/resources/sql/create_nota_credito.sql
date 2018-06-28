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
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    CLAVE_ACCESO varchar(70),
    EMPRESA_ID BIGINT,
    CLIENTE_ID BIGINT,
    FACTURA_ID BIGINT,
    TIPO_IDENTIFICACION_ID varchar(1),
    SECUENCIAL varchar(100),
    PUNTO_ESTABLECIMIENTO decimal,
    PUNTO_EMISION decimal,
    FECHA_EMISION date,
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
    RAZON_SOCIAL varchar(256),
    IDENTIFICACION varchar(15),
    DIRECCION varchar(1024),
    TELEFONO varchar(10),
    TIPO_FACTURACION varchar(1),
    CODIGO_DOCUMENTO varchar(3),
    primary key (ID),
    CONSTRAINT id_cliente_nota_credito_fk FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE(CLIENTE_ID),
    CONSTRAINT id_factura_fk FOREIGN KEY (FACTURA_ID) REFERENCES FACTURA(ID)

);
create table NOTA_CREDITO_DETALLE
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1,INCREMENT BY 1),
    REFERENCIA_ID BIGINT,
    TIPO_REFERENCIA varchar(5),
    NOTA_CREDITO_ID BIGINT,
    CANTIDAD integer ,
    PRECIO_UNITARIO decimal(7,3),
    DESCUENTO decimal(7,2),
    VALOR_ICE decimal,
    IVA decimal(7,2),
    TOTAL decimal(7,2),
    DESCRIPCION varchar(150),
    primary key (ID)
);

create table NOTA_CREDITO_ADICIONAL
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    NOTA_CREDITO_ID BIGINT,
    CAMPO varchar(150),
    VALOR varchar(150),
    TIPO varchar(1),
    NUMERO integer ,
    primary key (ID)
)