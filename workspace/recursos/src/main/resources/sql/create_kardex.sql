
create table KARDEX( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    PRODUCTO_ID BIGINT,  
    BODEGA_ID BIGINT,
    FECHA_CREACION date, 
    FECHA_MODIFICACION date,
    PRECIO_PROMEDIO decimal(13,2),
    PRECIO_ULTIMO decimal(13,2),
    PRECIO_TOTAL decimal(13,2),
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
    PRECIO_UNITARIO decimal(13,3),
    PRECIO_TOTAL decimal(13,2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.8)*/
    FECHA_CREACION date,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.8)*/
    FECHA_INGRESO date,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.0.3)*/
    FECHA_DOCUMENTO date,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.8)*/
    SECUENCIAL varchar(100),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.8)*/   
    PUNTO_ESTABLECIMIENTO decimal,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.8)*/
    PUNTO_EMISION decimal,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.8)*/
    RAZON_SOCIAL varchar(256),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.8)*/
    NOMBRE_LEGAL varchar(256),

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