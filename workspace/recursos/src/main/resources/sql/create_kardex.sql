/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 1/02/2018
 */
create table KARDEX( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    PRODUCTO_ID BIGINT,  
    BODEGA_ID BIGINT,
    FECHA_CREACION date, 
    FECHA_MODIFICACION date,
    PRECIO_PROMEDIO decimal(7,2),
    PRECIO_ULTIMO decimal(7,2),
    PRECIO_TOTAL decimal(7,2),
    STOCK BIGINT,
    primary key (ID)
);

create table KARDEX_DETALLE( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    KARDEX_ID BIGINT,  
    REFERENCIA_DOCUMENTO_ID BIGINT,
    CODIGO_TIPO_DOCUMENTO varchar(3),
    CANTIDAD Integer,
    PRECIO_UNITARIO decimal(7,2),
    PRECIO_TOTAL decimal(7,2),
    primary key (ID)
);

create table KARDEX_ITEM_ESPECIFICO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    KARDEX_DETALLE_ID BIGINT,  
    CODIGO_ESPECIFICO varchar(128),
    OBSERVACIONES varchar(128), 
    ESTADO varchar(1),
    primary key (ID)
);