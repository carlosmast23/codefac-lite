create table NACIONALIDAD( 
    NACIONALIDAD_ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    PAIS_NAC varchar(50),
    GENTILICIO_NAC varchar(50),  
    ISO_NAC varchar(3),  
    primary key (NACIONALIDAD_ID)
);

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
    REPRESENTANTE1_ID bigint,
    REPRESENTANTE2_ID bigint,
    NACIONALIDAD_ID bigint,    
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
    ETNIA varchar(100),      
    DISCAPACIDAD varchar(1),     
    CONADIS varchar(10),     
    TIPO_DISCAPACIDAD varchar(1),  
    OBS_DISCAPACIDAD varchar(256),  
    PORCENTAJE_DISCAPACIDAD integer,   
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
    MES_NUMERO int,
    DESCRIPCION varchar(256), 
    ESTADO varchar(1),
    primary key (ID)
);


create table ESTUDIANTE_INSCRITO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    ESTUDIANTE_ID bigint,
    NIVEL_ACADEMICO_ID bigint,
    TIPO_MATRICULA_COD varchar(3),
    BECA varchar(1),
    ESTADO varchar(1),
    primary key (ID)
);

create table RUBROS_NIVEL( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NOMBRE varchar(100),
    MES_NUMERO int,
    DIAS_CREDITO int,
    ANIO int,
    PERIODO_ID bigint,
    NIVEL_ID bigint,
    CATALOGO_PRODUCTO_ID BIGINT,
    TIPO_RUBRO varchar(1),
    VALOR decimal(7,2),
    primary key (ID)
);

create table RUBRO_ESTUDIANTE( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    VALOR decimal(7,2),
    TIPO_DESCUENTO varchar(1),
    NOMBRE_DESCUENTO varchar(128),
    PORCENTAJE_DESCUENTO int,  
    SALDO decimal(7,2),
    ESTADO_FACTURA varchar(1),
    ESTADO varchar(1),
    RUBRO_NIVEL_ID BIGINT,
    ESTUDIANTE_INSCRITO_ID BIGINT,
    primary key (ID)
);

create table RUBRO_PLANTILLA( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) ,
    CATALOGO_PRODUCTO_ID BIGINT,
    PERIODO_ID BIGINT,
    NOMBRE varchar(100),
    VALOR decimal(7,2),
    DIAS_CREDITO int,
    ENERO varchar(1),
    FEBRERO varchar(1),
    MARZO varchar(1),
    ABRIL varchar(1),
    MAYO varchar(1),
    JUNIO varchar(1),
    JULIO varchar(1),
    AGOSTO varchar(1),
    SEPTIEMBRE varchar(1),
    OCTUBRE varchar(1),
    NOVIEMBRE varchar(1),
    DICIEMBRE varchar(1),
    ESTADO_FACTURA varchar(1),
    RUBRO_NIVEL_ID BIGINT,
    ESTUDIANTE_INSCRITO_ID BIGINT,
    primary key (ID)
);

create table RUBRO_PLANTILLA_MES( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    RUBRO_PLANTILLA_ID BIGINT,
    NUMERO_MES int,
    ANIO int,
    primary key (ID)
);


create table RUBRO_PLANTILLA_ESTUDIANTE( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    RUBRO_PLANTILLA_ID BIGINT,
    ESTUDIANTE_INSCRITO_ID BIGINT,
    primary key (ID)
);

