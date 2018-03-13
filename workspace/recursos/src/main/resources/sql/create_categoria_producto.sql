create table CATEGORIA_PRODUCTO( 
    CATPRODUCTO_ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    NOMBRE varchar(100),  
    DESCRIPCION varchar(256),
    IMAGEN_PATH varchar(512),
    ESTADO varchar(1),
    primary key (CATPRODUCTO_ID)
);
