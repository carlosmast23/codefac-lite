/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.9.9.5)*/
create table DESCUENTO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    NOMBRE varchar(128), 
    DESCRIPCION varchar(1024), 
    FECHA_INICIO timestamp, 
    FECHA_FIN timestamp, 
    ALCANCE varchar(1), 

    FECHA_CREACION timestamp, 
    FECHA_ULTIMA_EDICION timestamp, 
    USUARIO_CREACION_ID BIGINT,  
    USUARIO_ULTIMA_EDICION_ID BIGINT,  
    ESTADO varchar(1),
 
    primary key (ID)
);
