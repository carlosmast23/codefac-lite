create table CATEGORIA_PRODUCTO( 
    CATPRODUCTO_ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    /*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.9.7)*/
    EMPRESA_ID BIGINT,
    NOMBRE varchar(100),  
    DESCRIPCION varchar(256),
    IMAGEN_PATH varchar(512),
    ESTADO varchar(1),
    primary key (CATPRODUCTO_ID)
);
