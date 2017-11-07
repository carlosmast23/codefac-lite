/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 7/11/2017
 */

create table 
    CLIENTE( ID_CLIENTE integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) , 
    NOMBRE_SOCIAL varchar(256),
    TIPO_IDENTIFICACION varchar(64), 
    CEDULA int, 
    TIPO_CLIENTE varchar(12),
    DIRECCION varchar(1024), 
    TELEFONO_CONVENCIONAL varchar(12),
    EXTENSION_TELEFONO varchar(256), 
    TELEFONO_CELULAR varchar(12), 
    CORREO_ELECTRONICO varchar(60),  
    primary key (ID_CLIENTE))