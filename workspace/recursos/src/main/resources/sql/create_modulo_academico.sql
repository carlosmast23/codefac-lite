create table AULA( 
    AULA_ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NOMBRE varchar(256),
    UBICACION varchar(256),    
    CAPACIDAD integer,
    ESTADO varchar(1),
    primary key (AULA_ID)
);

create table ESTUDIANTE( 
    ESTUDIANTE_ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    PERSONA_ID bigint,    
    CODSISTEMA varchar(100),
    CODAUXILIAR varchar(100),    
    CEDULA varchar(13),
    EMAIL varchar(100),    
    NOMBRES varchar(100),    
    APELLIDOS varchar(100),
    GENERO varchar(1),
    FECHA_NACIMIENTO date,
    TELEFONO varchar(15),
    CELULAR varchar(15),
    DIRECCION varchar(150),  
    ADICIONALES varchar(256),   
    ESTADO varchar(1),
    primary key (ESTUDIANTE_ID)
);

create table NIVEL( 
    NIVEL_ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NIVELPOSTERIOR_ID bigint,     
    NOMBRE varchar(100),
    DESCRIPCION varchar(256),  
    ORDEN integer,   
    ESTADO varchar(1),
    primary key (NIVEL_ID)
);

create table PERIODO( 
    PERIODO_ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NOMBRE varchar(100),
    FECHA_INICIO date,
    FECHA_FIN date,
    OBSERVACION varchar(256),  
    ESTADO varchar(1),
    primary key (PERIODO_ID)
);

create table NIVEL_ACADEMICO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NOMBRE varchar(100),
    AULA_ID bigint,
    PERIODO_ID bigint,
    NIVEL_ID bigint,
    primary key (ID)
);


create table ESTUDIANTE_INSCRITO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    ESTUDIANTE_ID bigint,
    NIVEL_ACADEMICO_ID bigint,
    primary key (ID)
);

create table RUBROS_NIVEL( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NOMBRE varchar(100),
    PERIODO_ID bigint,
    NIVEL_ID bigint,
    PRODUCTO_ID bigint,
    TIPO_RUBRO varchar(1),
    VALOR decimal(7,2),
    primary key (ID)
);

