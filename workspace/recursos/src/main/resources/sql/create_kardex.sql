
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
    RESERVA BIGINT,
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
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.8)*/
    FECHA_CREACION date,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.8)*/
    FECHA_INGRESO date,
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