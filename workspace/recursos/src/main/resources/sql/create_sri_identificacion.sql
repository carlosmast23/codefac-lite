/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 15/11/2017
 */
create table 
    SRI_IDENTIFICACION( 
    ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) , 
    CODIGO varchar(256),
    TIPO_TRANSACCION varchar(60), 
    TIPO_IDENTIFICACION varchar(60), 
    FECHA_INICIO date,
    FECHA_FIN date,
    primary key (ID))