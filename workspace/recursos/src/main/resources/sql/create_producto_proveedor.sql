/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 30/01/2018
 */
create table PRODUCTO_PROVEEDOR
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    PRODUCTO_ID BIGINT,
    PROVEEDOR_ID BIGINT,
    ESTADO varchar(1),
    DESCRIPCION varchar(128),
    CON_IVA varchar(1),
    COSTO_ACTUAL decimal(7,3),    
    primary key (ID)
);

