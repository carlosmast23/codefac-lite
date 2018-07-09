/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 10/11/2017
 */
create table 
    USUARIO( 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.1)*/
    EMPLEADO_ID BIGINT ,
    NICK varchar(120) ,
    CLAVE varchar(120), 
    ESTADO varchar(1), 
    primary key (NICK))
