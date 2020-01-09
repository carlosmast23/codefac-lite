/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 15/03/2018
 */

create table CARTERA( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    PERSONA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.6)*/
    SECUENCIAL varchar(100),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.6)*/
    PUNTO_ESTABLECIMIENTO decimal,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.6)*/
    PUNTO_EMISION decimal,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.6)*/
    FECHA_EMISION date,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.6)*/
    ESTADO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.6)*/
    TIPO_CARTERA varchar(1),
    FECHA_CREACION date,
    REFERENCIA_ID BIGINT,  
    CODIGO_DOCUMENTO varchar(3),  
    TOTAL decimal(13,2),
    SALDO decimal(13,2),  
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.1)*/
    CODIGO varchar(25),  
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.1)*/
    SUCURSAL_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.1)*/
    USUARIO_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.1)*/
    AUTORIZACION varchar(70),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.1)*/
    REFERENCIA_MANUAL varchar(250),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.1.1)*/
    CODIGO_AUXILIAR varchar(150),
    primary key (ID)
);

create table CARTERA_DETALLE( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    CARTERA_ID BIGINT,
    TIPO_REFERENCIA varchar(5),  
    REFERENCIA_ID BIGINT,  
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.6)*/
    DESCRIPCION varchar(300),
    TOTAL decimal(13,2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.6)*/
    SALDO decimal(13,2),  
    primary key (ID)
);

create table CARTERA_CRUCE( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    CARTERA_DETALLE_ID BIGINT,
    CARTERA_AFECTA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.6)*/
    FECHA_CREACION  date,
    FECHA_CRUCE date,
    VALOR decimal(13,2),
    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.1.8)*/
create table PRESTAMO_TABLA_INTERES( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    SUCURSAL_ID BIGINT,
    MESES INT,
    PORCENTAJE BIGINT,
    FECHA_CREACION  date,
    ESTADO varchar(1),
    primary key (ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.1.8)*/
create table PRESTAMO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    FACTURA_ID BIGINT,
    CARTERA_ID BIGINT,
    PERSONA_ID BIGINT,
    CAPITAL decimal(13,2),
    INTERES decimal(13,2),
    SALDO_CAPITAL decimal(13,2),
    SALDO_INTERES decimal(13,2),
    TAZA_INTERES decimal(13,2),
    PLAZO INT,
    DIA_PAGO_CUOTA INT,
    CUOTA_INICIAL decimal(13,2),
    TOTAL_PRESTAMO decimal(13,2),
    FECHA_CREACION  date,
    ESTADO varchar(1),
    primary key (ID),
    CONSTRAINT id_prestamo_factura_fk FOREIGN KEY (FACTURA_ID) REFERENCES FACTURA(ID),
    CONSTRAINT id_prestamo_cartera_fk FOREIGN KEY (CARTERA_ID) REFERENCES CARTERA(ID),
    CONSTRAINT id_prestamo_persona_fk FOREIGN KEY (PERSONA_ID) REFERENCES CLIENTE(CLIENTE_ID)
);


/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.1.8)*/
create table PRESTAMO_CUOTA( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    PRESTAMO_ID BIGINT, 
    NUMERO_CUOTA INT,    
    TIPO varchar(1),
    VALOR_CUOTA decimal(13,2),
    SALDO_CUOTA decimal(13,2),
    VALOR_PAGO decimal(13,2),    
    FECHA_PAGO_GENERADA  date,
    FECHA_PAGO date,
    FECHA_CREACION  date,
    ESTADO varchar(1),
    primary key (ID),
    CONSTRAINT id_prestamo_cuota_prestamo_fk FOREIGN KEY (PRESTAMO_ID) REFERENCES PRESTAMO(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.1.8)*/
create table PRESTAMO_CUOTA_CARGO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    PRESTAMO_CUOTA_ID BIGINT,
    TIPO varchar(1),
    REFERENCIA_ID BIGINT,
    VALOR decimal(13,2),
    DESCRIPCION varchar(512),
    FECHA_CREACION  date,
    ESTADO varchar(1),
    primary key (ID),
    CONSTRAINT id_prestamo_cuota_cargo_cuota_prestamo_fk FOREIGN KEY (PRESTAMO_CUOTA_ID) REFERENCES PRESTAMO_CUOTA(ID)
);