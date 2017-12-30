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
    CLIENTE_ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) , 
    NOMBRES varchar(256),
    APELLIDOS varchar(256),      
    RAZON_SOCIAL varchar(256),
    NOMBRE_LEGAL varchar(256),
    TIPO_IDENTIFICACION varchar(30), 
    IDENTIFICACION varchar(15) not null, 
    TIPO_CLIENTE varchar(12),
    DIRECCION varchar(1024), 
    TELEFONO_CONVENCIONAL varchar(9),
    EXTENSION_TELEFONO varchar(4), 
    TELEFONO_CELULAR varchar(10), 
    CORREO_ELECTRONICO varchar(60),
    ACTIVIDAD_COMERCIAL varchar(100),
    ESTADO varchar(1),
    primary key (CLIENTE_ID),
    UNIQUE(IDENTIFICACION)
)