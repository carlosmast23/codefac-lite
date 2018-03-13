create table BODEGA( 
    BODEGA_ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    NOMBRE varchar(100),  
    DESCRIPCION varchar(256),
    ENCARGADO varchar(100), 
    IMAGEN_PATH varchar(512),
    ESTADO varchar(1),
    primary key (BODEGA_ID)
);