/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  PC
 * Created: 14-nov-2017
 */
create table IMPUESTO 
(
    ID_IMPUESTO BIGINT not null GENERATED ALWAYS AS IDENTITY( START WITH 1),
    NOMBRE varchar(10),
    CODIGO_SRI varchar(4),
    DESCRIPCION varchar(60),
    primary key(ID_IMPUESTO)
)
