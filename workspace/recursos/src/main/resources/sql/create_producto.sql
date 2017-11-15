/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  PC
 * Created: 14-nov-2017
 */
create table PRODUCTO
(
    ID_PRODUCTO integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    CODIGO_PRINCIPAL varchar(70),
    CODIGO_AUXILIAR varchar(70),
    TIPO_PRODUCTO varchar(1),
    NOMBRE varchar(100),
    VALOR_UNITARIO decimal,
    primary key (ID_CLIENTE)
)
