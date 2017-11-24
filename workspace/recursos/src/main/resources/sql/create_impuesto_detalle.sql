/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  PC
 * Created: 16-nov-2017
 */

create table IMPUESTO_DETALLE
(
    ID_IMPUESTO_DETALLE integer not null GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
    ID_IMPUESTO integer,
    CODIGO integer,
    NOMBRE varchar(100),
    PORCENTAJE decimal(5,3),
    TARIFA integer,
    DESCRIPCION varchar(100),
    FECHA_INICIO date,
    FECHA_FIN date,
    primary key (ID_IMPUESTO_DETALLE),
    CONSTRAINT id_impuesto_fk FOREIGN KEY (ID_IMPUESTO) REFERENCES IMPUESTO(ID_IMPUESTO)
)
