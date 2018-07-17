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
    FECHA_CREACION date,
    REFERENCIA_ID BIGINT,  
    CODIGO_DOCUMENTO varchar(3),  
    TOTAL decimal(7,2),
    SALDO decimal(7,2),  
    primary key (ID)
);

create table CARTERA_DETALLE( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    CARTERA_ID BIGINT,
    TIPO_REFERENCIA varchar(5),  
    REFERENCIA_ID BIGINT,  
    TOTAL decimal(7,2),
    primary key (ID)
);

create table CARTERA_CRUCE( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    CARTERA_DETALLE_ID BIGINT,
    CARTERA_AFECTA_ID BIGINT,  
    FECHA_CRUCE date,
    VALOR decimal(7,2),
    primary key (ID)
)
