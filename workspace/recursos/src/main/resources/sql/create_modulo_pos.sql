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
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    SUCURSAL_ID BIGINT,
    PUNTO_EMISION_ID BIGINT,
    NOMBRE varchar(256),
    DESCRIPCION varchar(1024),
    ESTADO varchar(1),
    
    primary key (ID),
    CONSTRAINT id_caja_sucursal_fk FOREIGN KEY (SUCURSAL_ID) REFERENCES SUCURSAL(ID),
    CONSTRAINT id_caja_punto_emision_fk FOREIGN KEY (PUNTO_EMISION_ID) REFERENCES PUNTO_EMISION(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.2.9)*/
create table VENTA(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    primary key(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.2.9)*/
create table ARQUEO_CAJA(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),

    FECHA_HORA timestamp,
    VALOR_TEORICO varchar(256),
    VALOR_FISICO decimal(13,2),
    ESTADO varchar(1),
    
    primary key (ID)
);


/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.2.9)*/
create table CAJA_SESSION(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) ,
    
    CAJA_ID BIGINT,
    ARQUEO_CAJA_ID BIGINT,
    USUARIO_ID BIGINT,
    VENTA_ID BIGINT,
    
    ESTADO varchar(1),
    FECHA_HORA_APERTURA timestamp,
    FECHA_HORA_CIERRE timestamp,
    VALOR_APERTURA decimal(13,2),
    VALOR_CIERRE decimal(13,2),
    ESTADO_CIERRE_CAJA varchar(1),

    primary key (ID),
    constraint id_caja_session_caja_fk foreign key(CAJA_ID) references CAJA(ID),
    constraint id_caja_session_arqueo_caja_fk foreign key(ARQUEO_CAJA_ID) references ARQUEO_CAJA(ID),
    constraint id_caja_session_usuario_fk foreign key(USUARIO_ID) references USUARIO(ID),
    constraint id_caja_session_venta_fk foreign key(VENTA_ID) references VENTA(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.8.2.9)*/
create table INGRESO_CAJA(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    
    CAJA_SESSION_ID BIGINT,
    VENTA_ID BIGINT,

    primary key (ID),
    constraint id_ingreso_caja_caja_session_fk foreign key(CAJA_SESSION_ID) references CAJA_SESSION(ID),
    constraint id_ingreso_caja_venta_fk foreign key(VENTA_ID) references VENTA(ID)
);

