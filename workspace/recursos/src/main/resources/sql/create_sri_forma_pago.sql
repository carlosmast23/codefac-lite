/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 11/11/2017
 */
create table 
    SRI_FORMA_PAGO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    NOMBRE varchar(256),
    CODIGO varchar(30), 
    FECHA_INICIO date, 
    FECHA_FIN date,
    primary key (ID))