/* 
 * Actualizat los tipos de identificacion de la tabla de cliente
 */
/**
 * Author:  Carlos
 * Created: 24/07/2018
 */
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.5.6)*/
UPDATE CLIENTE SET IDENTIFICACION='9999999999999',RAZON_SOCIAL='Consumidor Final',NOMBRE_LEGAL='Consumidor Final',NOMBRES='Consumidor',APELLIDOS='Final',TIPO_OPERADOR='A' WHERE IDENTIFICACION='9999999999' or IDENTIFICACION='9999999999999' ;
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.5.6)*/
UPDATE CLIENTE SET TIPO_IDENTIFICACION='R' WHERE LENGTH(IDENTIFICACION)=13;
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.5.6)*/
UPDATE CLIENTE SET TIPO_IDENTIFICACION='C' WHERE LENGTH(IDENTIFICACION)=10;
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.5.6)*/
UPDATE CLIENTE SET TIPO_IDENTIFICACION='F' WHERE IDENTIFICACION='9999999999999';
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.5.6)*/
UPDATE SRI_IDENTIFICACION SET TIPO_IDENTIFICACION_LETRA='R' WHERE TIPO_IDENTIFICACION='RUC';
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.5.6)*/
UPDATE SRI_IDENTIFICACION SET TIPO_IDENTIFICACION_LETRA='C' WHERE TIPO_IDENTIFICACION='CEDULA';
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.5.6)*/
UPDATE SRI_IDENTIFICACION SET TIPO_IDENTIFICACION_LETRA='F' WHERE TIPO_IDENTIFICACION='CONSUMIDOR FINAL';
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.5.6)*/
UPDATE SRI_IDENTIFICACION SET TIPO_IDENTIFICACION_LETRA='P' WHERE TIPO_IDENTIFICACION='PASAPORTE / IDENTIFICACION TRIBUTARIA DEL EXTERIOR';
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.1)*/
ALTER TABLE EMPRESA ALTER RAZON_SOCIAL SET DATA TYPE VARCHAR(300);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.1)*/
ALTER TABLE EMPRESA ALTER NOMBRE_LEGAL SET DATA TYPE VARCHAR(300);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.1)*/
ALTER TABLE EMPRESA ALTER DIRECCION SET DATA TYPE VARCHAR(300);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.3)*/
UPDATE RUBROS_NIVEL  SET ESTADO='A' ;

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.4)*/
UPDATE CLIENTE SET ESTADO='A' WHERE ESTADO IS NULL; /* Actualizacion con algunos clientes que se migraron sin estado especial para MyGoldenWorld*/

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.5)*/
DELETE FROM RUBRO_PLANTILLA_ESTUDIANTE  WHERE ESTUDIANTE_INSCRITO_ID IN (SELECT EI.ID FROM RUBRO_PLANTILLA_ESTUDIANTE PE,ESTUDIANTE_INSCRITO EI WHERE PE.ESTUDIANTE_INSCRITO_ID=EI.ID AND EI.ESTADO='E');/*Eliminar datos erroneos creados en las plantillas mensuales con estudiantes_inscritos eliminados*/

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.6)*/
UPDATE SRI_FORMA_PAGO SET ALIAS='Efectivo' WHERE CODIGO='01';
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.6)*/
UPDATE SRI_FORMA_PAGO SET ALIAS='Compensación' WHERE CODIGO='15';
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.6)*/
UPDATE SRI_FORMA_PAGO SET ALIAS='Tarjeta débito' WHERE CODIGO='16';
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.6)*/
UPDATE SRI_FORMA_PAGO SET ALIAS='Dinero electrónico' WHERE CODIGO='17';
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.6)*/
UPDATE SRI_FORMA_PAGO SET ALIAS='Tarjeta prepago' WHERE CODIGO='18';
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.6)*/
UPDATE SRI_FORMA_PAGO SET ALIAS='Tarjeta crédito' WHERE CODIGO='19';
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.6)*/
UPDATE SRI_FORMA_PAGO SET ALIAS='Otros' WHERE CODIGO='20';
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.6)*/
UPDATE SRI_FORMA_PAGO SET ALIAS='Endoso títulos' WHERE CODIGO='21';
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.6)*/
UPDATE RUBROS_NIVEL SET ESTADO='A' WHERE ESTADO IS NULL;/*Actualizando rubros del nivel que se crearon desde la plantilla y se estaban generando sin estado*/
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.7)*/
UPDATE RUBRO_PLANTILLA SET ESTADO='A' WHERE ESTADO IS NULL;/*Actualizar con el estado activo a todos los rubros de la plantilla creados*/

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.7)*/
CREATE FUNCTION TEXTO_ESTANDAR(texto VARCHAR(5000)) RETURNS VARCHAR(5000) 
    PARAMETER STYLE JAVA NO SQL LANGUAGE JAVA 
    EXTERNAL NAME 'ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesDerby.normalizarTextoDerby'; /*Agregar funcion para normalizar los texto y poder hacer busquedas sin importar acentos*/

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
ALTER TABLE SRI_RETENCION_RENTA ALTER NOMBRE SET DATA TYPE VARCHAR(256);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'304','SERVICIOS DONDE PREDOMINA EL INTELECTO NO RELACIONADOS CON EL TITULO',8.0000,'2009-03-20 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'307','SERVICIOS DONDE PREDOMINA MANO DE OBRA',2.0000,'2009-03-20 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'308','UTILIZACION O APROVECHAMIENTO DE LA IMAGEN O RENOMBRE',2.0000,'2009-03-20 00:00:00','2015-02-28 00:00:00');
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'309','SERVICIOS PRETADOS POR MEDIOS DE COMUNICACION Y AGENCIAS DE PUBLICRETENCION_IDAD',1.0000,'2009-03-20 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'310','SERVICIOS DE TRANSPORTE PRIVADO DE PASAJEROS O SERVICIO PÚBLICO O PRIVADO DE CARGA',1.0000,'2009-03-20 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'312','TRANSFERENCIA DE BIENES MUEBLES DE NATURALEZA CORPORAL',1.0000,'2009-03-20 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'319','ARRENDAMIENTO MERCANTIL',1.0000,'2009-03-20 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'320','POR ARRENDAMIENTO DE BIENES INMUEBLES',8.0000,'2009-03-20 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'322','SEGUROS Y REASEGUROS (PRIMAS Y CESIONES)',1.0000,'2009-03-20 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'323','RENDIMIENTOS FINANCIEROS',2.0000,'2009-03-20 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'332','PAGOS DE BIENES O SERVICIOS NO SUJETOS A RETENCION',0.0000,'2009-03-20 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'340','OTRAS RETENCIONES APLICABLES EL 1%',1.0000,'2009-03-20 00:00:00','2015-02-28 00:00:00');
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'344','OTRAS RETENCIONES APLICABLES EL 2%',2.0000,'2009-03-20 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'342','OTRAS RETENCIONES APLICABLES EL 8%',8.0000,'2009-03-20 00:00:00','2015-02-28 00:00:00');
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'421','PAGOS AL EXTERIOR SIN CONVENIO DE DOBLE TRIBUTACIÓN',25.0000,'2009-03-20 00:00:00','2016-11-01 00:00:00');
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'427','PAGOS AL EXTERIOR NO SUJETOS A RETENCIÓN',0.0000,'2009-03-20 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'303','HONORARIOS PROFESIONALES Y DEMAS PAGOS POR SERVICIOS RELACIONADOS CON EL TITULO PROFESIONAL',10.0000,'2010-06-01 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'343','APLICABLE A LA TARIFA DE IMPUESTO A LA RENTA PREVISTA PARA SOCIEDADES',22.0000,'2013-01-01 00:00:00','2015-02-28 00:00:00');
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'343B','POR ACTIVRETENCION_IDADES DE CONSTRUCCION DE OBRA MATERIAL INMUEBLE, URBANIZACION, LOTIZACION O ACTIVRETENCION_IDADES SIMILARES',1.0000,'2015-03-04 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'304A','COMISIONES Y DEMAS PAGOS POR SERVICIOS PREDOMINA EL INTELECTO NO RELACIONADOS CON EL TITULO PROFESIONAL',8.0000,'2015-03-01 00:00:00','2016-10-31 00:00:00');
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'304B','PAGOS A NOTARIOS Y REGISTRADORES DE LA PROPIEDAD Y MERCANTIL POR SUS ACTIVRETENCION_IDADES EJERCRETENCION_IDAS COMO TALES',8.0000,'2015-03-01 00:00:00','2016-10-31 00:00:00');
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'304C','PAGOS A DEPORTISTAS, ENTRENADORES, ARBITROS, MIEMBROS DEL CUERPO TECNICO PRO SUS ACTIVRETENCION_IDADES EJERCRETENCION_IDAS COMO TALES',8.0000,'2015-03-01 00:00:00','2016-10-31 00:00:00');
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'304D','PAGOS A ARTISTAS POR SUS ACTIVRETENCION_IDADES EJERCRETENCION_IDAS COMO TALES',8.0000,'2015-03-01 00:00:00','2016-10-31 00:00:00');
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'308','UTILIZARION O APROVECHAMIENTO DE LA IMAGEN O RENOMBRE',10.0000,'2015-03-01 00:00:00','2016-10-31 00:00:00');
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'311','POR PAGOS A TRAVES DE LIQURETENCION_IDACION DE COMPRA (NIVEL CULTURAL O RUSTICRETENCION_IDAD)',2.0000,'2015-03-01 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'314A','REGALIAS POR NOMBRE DE FRANQUICIAS DE ACUERDO A LA LEY DE PROPIEDAD INTELECTUAL PAGO A PERSONAS NATURALES',8.0000,'2015-03-01 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'314B','CANONES, DERECHOS DE AUTOR, MARCAS, PATENTES Y SIMILARES DE ACUERDO A LEY DE PROPIEDAD INTELECTUAL PAGO A PERSONAS NATURALES',8.0000,'2015-03-01 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'314C','REGALIAS POR NOMBRE DE FRANQUICIAS DE ACUERDO A LA LEY DE PROPIEDAD INTELECTUAL PAGO A SOCIEDADES',8.0000,'2015-03-01 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'314D','CANONES, DERECHOS DE AUTOR, MARCAS, PATENTES Y SIMILARES DE ACUERDO A LEY DE PROPIEDAD INTELECTUAL PAGO A SOCIEDADES',8.0000,'2015-03-01 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'343A','POR ENERGIA ELECTRICA',1.0000,'2015-03-01 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'344A','PAGO LOCAL TARJETA DE CREDITO REPORTADA POR LA EMISORA DE TARJETA DE CREDITO, SOLO RECAP',2.0000,'2015-03-01 00:00:00',NULL);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.8)*/
insert into SRI_RETENCION_RENTA (RETENCION_ID, CODIGO_SRI, NOMBRE, PORCENTAJE, FECHA_INICIO, FECHA_FIN) values(1,'308','UTILIZACION O APROVECHAMIENTO DE LA IMAGEN O RENOMBRE ',10.0000,'2015-03-01 00:00:00',NULL);


/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9)*/
INSERT INTO SRI_RETENCION_IVA (RETENCION_ID,NOMBRE, CODIGO_SRI,PORCENTAJE,DESCRIPCION,FECHA_INICIO) VALUES (2,'NO APLICA' , '0' ,0,'NO APLICA','01/01/2000');

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9.2)*/
UPDATE BODEGA SET ESTADO='A' WHERE ESTADO='a';

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9.3)*/
UPDATE CLIENTE SET RAZON_SOCIAL= APELLIDOS || ' ' || NOMBRES  WHERE LENGTH (IDENTIFICACION)=10;

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9.3)*/
UPDATE CLIENTE SET ESTADO='A'  WHERE ESTADO IS NULL;

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9.5)*/
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR) VALUES(1,'PVOR','s','s','s','s','s');


/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9.6)*/
INSERT INTO 
SUCURSAL (EMPRESA_ID,NOMBRE,TIPO,ESTADO,TELEFONO,DIRECCION,EMAIL,COD_SUCURSAL,CELULAR) 
VALUES 
((SELECT ID FROM EMPRESA),'MATRIZ','m','A',(SELECT TELEFONOS FROM EMPRESA),(SELECT DIRECCION FROM EMPRESA),'',(SELECT CAST(VALOR AS BIGINT) FROM PARAMETRO WHERE NOMBRE='establecimiento'),(SELECT CELULAR FROM EMPRESA));


/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9.6)*/
INSERT INTO PUNTO_EMISION(SUCURSAL_ID,DESCRIPCION,PUNTO_EMISION,TIPO_FACTURACION,ESTADO,
SECUENCIAL_FACTURA,
SECUENCIAL_NOTA_CREDITO,
SECUENCIAL_NOTA_DEBITO,
SECUENCIAL_GUIA_REMISION,
SECUENCIAL_RETENCIONES,
SECUENCIAL_NOTA_VENTA)
VALUES
(1,'punto_emision',(SELECT CAST(VALOR AS BIGINT) FROM PARAMETRO WHERE NOMBRE='punto_emision'),
'e',
'A',
(SELECT CAST(VALOR AS BIGINT) FROM PARAMETRO WHERE NOMBRE='secuencial_factura'),
(SELECT CAST(VALOR AS BIGINT) FROM PARAMETRO WHERE NOMBRE='secuencial_nota_credito'),
(SELECT CAST(VALOR AS BIGINT) FROM PARAMETRO WHERE NOMBRE='secuencial_nota_debito'),
(SELECT CAST(VALOR AS BIGINT) FROM PARAMETRO WHERE NOMBRE='secuencial_guia_remision'),
(SELECT CAST(VALOR AS BIGINT) FROM PARAMETRO WHERE NOMBRE='secuencial_retencion'),
1
);

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9.7,MOSTRAR_ERROR=NO)*/
ALTER TABLE FACTURA ADD TIPO_IDENTIFICACION_CODIGO_SRI varchar(3);

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9.8)*/
UPDATE RUBRO_ESTUDIANTE SET ESTADO='E' WHERE VALOR=0;

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9.8)*/
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR) VALUES(1,'SUCU','s','s','s','s','s');
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9.8)*/
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR) VALUES(1,'PUVE','s','s','s','s','s');

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9.8,MOSTRAR_ERROR=SI)*/
ALTER TABLE DESTINATARIO_GUIA_REMISION ADD IDENTIFICACION varchar(32);

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9.9,MOSTRAR_ERROR=SI)*/
INSERT INTO PARAMETRO (NOMBRE,VALOR) VALUES('smtp_host','');
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.6.9.9,MOSTRAR_ERROR=SI)*/
INSERT INTO PARAMETRO (NOMBRE,VALOR) VALUES('smtp_port','587');


/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
ALTER TABLE PRODUCTO ADD COLUMN NEW_COLUMN decimal(13,5);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
UPDATE PRODUCTO SET NEW_COLUMN=VALOR_UNITARIO;
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
ALTER TABLE PRODUCTO DROP COLUMN VALOR_UNITARIO;
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
RENAME COLUMN PRODUCTO.NEW_COLUMN TO VALOR_UNITARIO;

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
ALTER TABLE PRODUCTO ADD COLUMN NEW_COLUMN decimal(13,5);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
UPDATE PRODUCTO SET NEW_COLUMN=PRECIO_DISTRIBUIDOR;
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
ALTER TABLE PRODUCTO DROP COLUMN PRECIO_DISTRIBUIDOR;
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
RENAME COLUMN PRODUCTO.NEW_COLUMN TO PRECIO_DISTRIBUIDOR;

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
ALTER TABLE PRODUCTO ADD COLUMN NEW_COLUMN decimal(13,5);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
UPDATE PRODUCTO SET NEW_COLUMN=PRECIO_TARJETA;
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
ALTER TABLE PRODUCTO DROP COLUMN PRECIO_TARJETA;
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
RENAME COLUMN PRODUCTO.NEW_COLUMN TO PRECIO_TARJETA;

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
ALTER TABLE GUIA_REMISION ADD DIRECCION_ESTABLECIMIENTO varchar(1024);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.0,MOSTRAR_ERROR=NO)*/
ALTER TABLE GUIA_REMISION ADD DIRECCION_MATRIZ varchar(1024);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.1,MOSTRAR_ERROR=NO)*/
ALTER TABLE DESTINATARIO_GUIA_REMISION ADD CODIGO_ESTABLECIMIENTO varchar(4);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.3,MOSTRAR_ERROR=SI)*/
INSERT INTO PARAMETRO (NOMBRE,VALOR) VALUES('tipo_envio_comprobante','f');

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.4)*/
ALTER TABLE GUIA_REMISION ADD TIPO_AMBIENTE varchar(1);
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.4)*/
ALTER TABLE GUIA_REMISION ADD FECHA_AUTORIZACION_SRI date;

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.5,MOSTRAR_ERROR=SI)*/
INSERT INTO ACTUALIZAR_SISTEMA (NOMBRE_METODO,VERSION,CAMBIO_ACTUALIZADO,DESCRIPCION) VALUES('actualizaComprobantesElectronicos','1.2.7.0.5','n','Actuliza todos los comprobantes anteriores para saber cuales fueron eliminados por el Sri');

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.5,MOSTRAR_ERROR=NO)*/
INSERT INTO PERMISO_VENTANA(PERFIL_ID,NOMBRE_CLASE,PERMISO_GRABAR,PERMISO_ELIMINAR,PERMISO_IMPRIMIR,PERMISO_EDITAR,PERMISO_BUSCAR) VALUES(1,'UECT','s','s','s','s','s');

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.5,MOSTRAR_ERROR=SI)*/
ALTER TABLE CLIENTE ALTER CORREO_ELECTRONICO SET DATA TYPE VARCHAR(300);

/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.9,MOSTRAR_ERROR=NO)*/
ALTER TABLE GUIA_REMISION ADD FECHA_AUTORIZACION_SRI date;
/*@AGREGAR_SCRIPT (VERSION_SISTEMA=1.2.7.0.9,MOSTRAR_ERROR=NO)*/
ALTER TABLE GUIA_REMISION ADD TIPO_AMBIENTE varchar(1);