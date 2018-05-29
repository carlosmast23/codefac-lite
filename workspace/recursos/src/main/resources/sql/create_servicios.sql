/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CodesoftDesarrollo 1
 * Created: 14/05/2018
 */
create table ORDEN_TRABAJO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) ,
    CLIENTE_ID BIGINT,
    CODIGO varchar(15),
    DESCRIPCION varchar(150),
    ESTADO varchar(10),
    FECHA_INGRESO date,
    primary key(ID)
);

create table DETALLE_ORDEN_TRABAJO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    EMPLEADO_ID BIGINT,
    ORDEN_TRABAJO_ID BIGINT,
    DESCRIPCION varchar(150),
    NOTAS varchar(150),
    FECHA_ENTREGA date,
    ESTADO varchar(10),
    TIPO_ORDEN_TRABAJO varchar(10),
    PRIORIDAD varchar(15),
    primary key(ID)
);

create table EMPLEADO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    PERSONA_ID BIGINT,
    DEPARTAMENTO_ID BIGINT,
    CARGO varchar(30),
    ESTADO varchar(1),
    primary key(ID)
);

create table DEPARTAMENTO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    CODIGO varchar(10),
    NOMBRE varchar(30),
    DESCRIPCION varchar(150),
    ESTADO varchar(1),
    primary key(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2)*/
create table PRESUPUESTO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTIFY(START WITH 1),
    CODIGO varchar(10),
    DESCRIPCION varchar(150),
    OBSERVACIONES varchar(150),
    ESTADO varchar(1),
    FECHA_INGRESO date,
    ORDEN_TRABAJO_DETALLE_ID BIGINT,
    CLIENTE_ID BIGINT,
    primary key(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2)*/
create table PRESUPUESTO_DETALLE
(
    ID BIGINT not null generated always as identify(start with 1),
    PRECIO_COMPRA decimal(7,2),
    DESCUENTO_COMPRA decimal(7,2),
    PRECIO_VENTA decimal(7,2),
    DESCUENTO_VENTA decimal(7,2),
    CANTIDAD decimal(7,2),
    ESTADO varchar(1),
    PROVEEDOR_ID BIGINT,
    PRODUCTO_ID BIGINT,
    primary key(ID)
);


