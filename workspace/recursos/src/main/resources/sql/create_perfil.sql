/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 2/01/2018
 */
create table PERFIL
(
    ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    NOMBRE VARCHAR(70),
    DESCRIPCION VARCHAR(150),
    ESTADO varchar(1),
    primary key (ID)
);

create table PERFIL_USUARIO
(
    ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    NICK  varchar(120),
    PERFIL_ID integer,
    FECHA_CREACION date,
    primary key (ID),
    CONSTRAINT id_usuario_perfil_fk FOREIGN KEY (NICK) REFERENCES USUARIO(NICK),
    CONSTRAINT id_perfil_usuario_fk FOREIGN KEY (PERFIL_ID) REFERENCES PERFIL(ID)    
)


