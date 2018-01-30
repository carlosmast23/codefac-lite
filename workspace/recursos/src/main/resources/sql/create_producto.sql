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
    CODIGO_PERSONALIZADO varchar(70),
    CODIGO_EAN varchar(70),
    CODIGO_UPC varchar(70),
    TIPO_PRODUCTO varchar(1),
    NOMBRE varchar(100),
    VALOR_UNITARIO decimal(7,3),
    IVA_ID integer,
    ICE_ID integer,
    IRBPNR_ID integer,
    ESTADO varchar(1),

    UBICACION varchar(100),
    GARANTIA varchar(1),
    CANTIDAD_MINIMA integer,
    PRECIO_DISTRIBUIDOR decimal(7,3),
    PRECIO_TARJETA decimal(7,3),
    STOCK_INICIAL bigint,
    MARCA varchar(50),
    IMAGEN varchar(100),
    CATEGORIA varchar(1),
    CARACTERISTICAS varchar(100),
    OBSERVACIONES varchar(100),    

    primary key (ID_PRODUCTO),
    UNIQUE(CODIGO_PERSONALIZADO,CODIGO_EAN,CODIGO_UPC)
)
