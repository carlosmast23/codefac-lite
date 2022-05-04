/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CARLOS_CODESOFT
 * Created: 04/05/2022
 */

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.9.1.0)*/
create table MESA
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY( START WITH 1),
    NUMERO INT,
    NOMBRE varchar(120),
    CAPACIDAD INT,
    ESTADO varchar(1),
    primary key(ID)
);