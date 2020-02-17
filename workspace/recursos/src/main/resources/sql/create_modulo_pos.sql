/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CARLOS_CODESOFT
 * Created: 14/02/2020
 */

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.2.4)*/
create table CAJA( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    SUCURSAL_ID BIGINT,
    PUNTO_EMISION_ID BIGINT,
    NOMBRE varchar(256),
    DESCRIPCION varchar(1024),
    ESTADO varchar(1),
    
    primary key (ID),
    CONSTRAINT id_caja_sucursal_fk FOREIGN KEY (SUCURSAL_ID) REFERENCES SUCURSAL(ID),
    CONSTRAINT id_caja_punto_emision_fk FOREIGN KEY (PUNTO_EMISION_ID) REFERENCES PUNTO_EMISION(ID),
);