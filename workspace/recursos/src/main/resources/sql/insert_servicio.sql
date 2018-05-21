/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CodesoftDesarrollo 1
 * Created: 17/05/2018
 */
INSERT INTO DEPARTAMENTO (CODIGO,NOMBRE,DESCRIPCION,ESTADO) VALUES('VT01','Ventas','Departemento de Ventas','A');
INSERT INTO DEPARTAMENTO (CODIGO,NOMBRE,DESCRIPCION,ESTADO) VALUES('CT01','Contabilidad','Departemento de Contabilidad General','A');
INSERT INTO DEPARTAMENTO (CODIGO,NOMBRE,DESCRIPCION,ESTADO) VALUES('CT02','Contabilidad','Departemento de Contabilidad Area Finanzas','A');

INSERT INTO EMPLEADO(PERSONA_ID,DEPARTAMENTO_ID,CARGO,ESTADO) VALUES(2,1,'Cobrador 1','A');
INSERT INTO EMPLEADO(PERSONA_ID,DEPARTAMENTO_ID,CARGO,ESTADO) VALUES(3,1,'Cobrador 2','A');
INSERT INTO EMPLEADO(PERSONA_ID,DEPARTAMENTO_ID,CARGO,ESTADO) VALUES(4,3,'Cobrador 3','A');