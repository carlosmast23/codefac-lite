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
    ID_PRODUCTO BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    CATALOGO_PRODUCTO_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.9.7)*/
    EMPRESA_ID BIGINT, 
    CODIGO_PERSONALIZADO varchar(70),
    CODIGO_EAN varchar(70),
    CODIGO_UPC varchar(70),
    TIPO_PRODUCTO_COD varchar(1),
    NOMBRE varchar(100),
    VALOR_UNITARIO decimal(13,5),
    ESTADO varchar(1),
    UBICACION varchar(100),
    GARANTIA varchar(1),
    CANTIDAD_MINIMA integer,
    PRECIO_DISTRIBUIDOR decimal(13,5),
    PRECIO_TARJETA decimal(13,5),
    STOCK_INICIAL bigint,
    MARCA varchar(50),
    IMAGEN varchar(100),
    CARACTERISTICAS varchar(100),
    OBSERVACIONES varchar(100),  
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    MANEJAR_INVENTARIO  varchar(1),  
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.8.1)*/
    GENERAR_CODIGO_BARRAS  varchar(1), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.7.8.2)*/
    TRANSPORTAR_EN_GUIA_REMISION  varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.0.9)*/
    OCULTAR_DETALLE_VENTA varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.2.5)*/
    PRECIO_SIN_SUBSIDIO decimal(13,5),
 

    primary key (ID_PRODUCTO)
);


create table PRODUCTO_ENSAMBLE
( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) , 
    COMPONENTE_ENSAMBLE BIGINT,
    PRODUCTO_ENSAMBLE BIGINT, 
    CANTIDAD integer, 
    primary key (ID)
);
