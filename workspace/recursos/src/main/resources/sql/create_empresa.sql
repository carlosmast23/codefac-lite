/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  PC
 * Created: 08-ene-2018
 */

create table EMPRESA
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    TELEFONOS varchar(10),
    RAZON_SOCIAL varchar(50),
    NOMBRE_LEGAL varchar(50),
    DIRECCION varchar(100),
    IDENTIFICACION varchar(50),
    OBLIGADO_LLEVAR_CONTABILIDAD varchar(3),
    CONTRIBUYENTE_ESPECIAL varchar(50),
    primary key (ID)
)