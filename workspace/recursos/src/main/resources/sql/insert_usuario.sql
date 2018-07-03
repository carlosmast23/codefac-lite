/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 10/11/2017
 */



/**
* Creando un perfil por defecto que tiene las opciones necesarias para poder administrar
*/
INSERT INTO PERFIL(NOMBRE,DESCRIPCION,ESTADO)
    VALUES('Default','Perfil por defecto','A');

/**
* Creando un usuario sin perfil solo para usuarios root que ingresen a dar mantenimiento
*/
INSERT INTO USUARIO(NICK,CLAVE,ESTADO)
     VALUES ('root', '','I');


INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'CATG','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'CAPR','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'CONF','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'FACD','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'NOTC','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'FACR','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'UTIL','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'CLIE','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'PROV','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'PROD','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'CLIR','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'PROR','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'EMPR','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'RESP','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'PERF','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'FACT','s','s','s','s','s');
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'PEUM','s','s','s','s','s');