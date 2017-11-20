/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CodesoftDesarrollo 1
 * Created: 16/11/2017
 */
create table 
    PARAMETRO( ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) , 
    NOMBRE varchar(200),
    VALOR varchar(200), 
    primary key (ID))
