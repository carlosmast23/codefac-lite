/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 10/11/2017
 */


INSERT INTO USUARIO(NICK,CLAVE,ESTADO)
     VALUES ('admin', 'Code17bwbtj','A');

INSERT INTO USUARIO(NICK,CLAVE,ESTADO)
     VALUES ('root', 'Code17bwbtj','A');

INSERT INTO PERFIL(NOMBRE,DESCRIPCION,ESTADO)
    VALUES('ADMIN','Perfil para administrador para cambiar configuraciones del sistema','A');

INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'PERF','s','s','s','s','s');

INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR)
    VALUES(1,'PEUM','s','s','s','s','s');
    
INSERT INTO PERFIL_USUARIO(NICK,PERFIL_ID,FECHA_CREACION)
    VALUES('admin',1,CURRENT_DATE);