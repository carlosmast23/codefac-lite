/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 17/02/2018
 */

create table Aula( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    NOMBRE varchar(100),  
    UBICACION varchar(256),
    CAPACIDAD varchar(100), 
    ESTADO varchar(1),
    primary key (BODEGA_ID)
)

