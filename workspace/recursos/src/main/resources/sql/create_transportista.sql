/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CodesoftDesarrollo
 * Created: 26/07/2018
 */
/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.6.0)*/
create table TRANSPORTISTA
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    IDENTIFICACIN varchar(1),
    NOMBRES varchar(30),
    APELLIDOS varchar(30),
    RAZON_SOCIAL varchar(100),
    NOMBRE_COMERCIAL varchar(100),
    CORREO_ELECTRONICO varchar(100),
    DIRECCION varchar(150),
    PLACA_VEHICULO varchar(10),
    TELEFONO_CELULAR varchar(15),
    TELEFONO_CONVENCIONAL varchar(15),
    ESTADO varchar(1),
    primary key (ID)
);
