/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 10/11/2017
 */

create table CLIENTE( 
    CLIENTE_ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NACIONALIDAD_ID bigint,  
    SRI_FORMA_PAGO_ID bigint,    
    SRI_IDENTIFICACION_ID bigint, 
    NOMBRES varchar(256),
    APELLIDOS varchar(256),
    TIPO_OPERADOR varchar(1),
    RAZON_SOCIAL varchar(256),
    NOMBRE_LEGAL varchar(256),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.6)*/
    TIPO_IDENTIFICACION varchar(1),
    IDENTIFICACION varchar(15) not null, 
    TIPO_CLIENTE varchar(12),
    DIRECCION varchar(1024), 
    TELEFONO_CONVENCIONAL varchar(9),
    EXTENSION_TELEFONO varchar(4), 
    TELEFONO_CELULAR varchar(10), 
    CORREO_ELECTRONICO varchar(300),
    ACTIVIDAD_COMERCIAL varchar(100),
    OBLIGADO_LLEVAR_CONTABILIDAD varchar(1),
    ESTADO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.2)*/
    CONTACTO_CLIENTES varchar(11), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.2)*/
    CONTACTO_CLIENTES_PORCENTAJE decimal(5,2), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.9)*/
    DIAS_CREDITO_CLIENTE int,


    primary key (CLIENTE_ID),
    UNIQUE(IDENTIFICACION)
)