/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CodesoftDesarrollo 1
 * Created: 14/05/2018
 */
create table 
    ORDEN_TRABAJO(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) ,
    CLIENTE_ID BIGINT,
    CODIGO varchar(15),
    DESCRIPCION varchar(150),
    ESTADO varchar(10),
    FECHA_INGRESO date,
    primary key(ID));

create table
    DETALLE_ORDEN_TRABAJO(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    CLIENTE_ID BIGINT,
    ORDEN_TRABAJO_ID BIGINT,
    DESCRIPCION varchar(150),
    NOTAS varchar(150),
    FECHA_ENTREGA date,
    ESTADO varchar(10),
    TIPO_ORDEN_TRABAJO varchar(10),
    PRIORIDAD varchar(15),
    primary key(ID));

