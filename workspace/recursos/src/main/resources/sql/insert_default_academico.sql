INSERT INTO NIVEL(NOMBRE,DESCRIPCION,ORDEN,ESTADO) 
VALUES ('Inicia1 1','Inicial 1',1,'A'),
('Inicia1 2','Inicial 2',2,'A'),
('Basica','basica',3,'A'),
('Primer grado','primer grado',4,'A'),
('Segundo grado','SEGUNDO grado',5,'A')
;

INSERT INTO PERIODO (NOMBRE, FECHA_INICIO, FECHA_FIN, ESTADO) 
VALUES 
('2017 enero - 2018 junio', '2017-01-01', '2017-06-30', 'A'),
('2017 julio - 2018 diciembre', '2017-07-01', '2017-12-24', 'I'),
('2018 enero - 2018 junio', '2018-01-01', '2018-06-30', 'I'),
('2018 julio - 2018 diciembre', '2018-07-01', '2018-12-24', 'I')
;

INSERT INTO AULA (NOMBRE, UBICACION, ESTADO) 
VALUES ('Ninguno', 'Ninguno', 'A'),
('Aula 1', 'Primer piso', 'A'),
('Aula 2', 'Segundo piso', 'A')
;

INSERT INTO CLIENTE (NOMBRES, APELLIDOS, TIPO_OPERADOR, RAZON_SOCIAL, NOMBRE_LEGAL, SRI_IDENTIFICACION_ID, IDENTIFICACION, TIPO_CLIENTE, DIRECCION, TELEFONO_CONVENCIONAL, EXTENSION_TELEFONO, TELEFONO_CELULAR, CORREO_ELECTRONICO, ACTIVIDAD_COMERCIAL, ESTADO,SRI_FORMA_PAGO_ID,NACIONALIDAD_ID) 
VALUES 
('María José', 'Pillajo', 'C', 'María José Pillajo', NULL, 5, '1718559592', 'CLIENTE', 'ARMENIA 2, LA RIVERA CONJUNTO CHIMBORAZO CASA NO. 6 ', '22093992', '0', '', 'carlosmast2301@hotmail.com', NULL, 'A',1,53),
('Carmen', 'Augusta', 'C', 'Carmen Augusta', NULL, 5, '1715922140', 'CLIENTE', 'SAN PEDRO DE TADOABA', '', '0', '0987446497', 'carlosmast2301@hotmail.com', NULL, 'A',1,53),
('Franklin', 'Lopez Acuña', 'C', 'Franklin Lopez Acuña', NULL, 5, '1715184493', 'CLIENTE', 'CONOCOTO', '', '0', '0998341606', 'carlosmast2301@hotmail.com', NULL, 'A',1,53),
('Pedro ', 'Risueño', 'C', 'Pedro Risueño', NULL, 5, '1719852124', 'CLIENTE', 'CONOCOTO', '23696011', '0', '', 'carlosmast2301@hotmail.com', NULL, 'A',1,53),
('AGENCIA DE REGULACION  Y CONTROL HIDROCARBURIFERO', 'AGENCIA DE REGULACION  Y CONTROL HIDROCARBURIFERO', 'C', 'AGENCIA DE REGULACION  Y CONTROL HIDROCARBURIFERO', NULL, 4, '1768158680001', 'CLIENTE', 'CONOCOTO', '23996500', '0', '', 'carlosmast2301@hotmail.com', NULL, 'A',1,53),
('Carlos', 'Perez', 'C', 'Carlos Perez', NULL, 4, '1719468108001', 'CLIENTE', 'Sangolqui', '022087044', '0', '0983528439', 'jcp_computer@hotmail.com', NULL, 'A',1,53),
('Juan', 'Loya', 'C', 'Juan Loya', NULL, 4, '1724218952001', 'CLIENTE', 'Sangolqui', '022087065', '0', '0983528439', 'juancarlos100pl@hotmail.com', NULL, 'A',1,53),
('Pedro Alfonso', 'Perez Sanchez', 'C', 'Pedro Alfonso Perez Sanchez', NULL, 4, '1724218953001', 'CLIENTE', 'Sangolqui', '022087065', '0', '0983528439', 'juancarlos100pl@hotmail.com', NULL, 'A',1,53)
;

INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (1, 53, 'P001', '1758007734', '', 'THOMAS ANDRES', 'PROAÑO PILLAJO', 'M', '2017-03-25', '', '', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (2, 53, 'P002', '1212121212', '', 'LUCIANA', 'PROAÑO ALDANA', 'F', '2016-03-08', '', '', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (3, 53, 'P003', '1756947535', '', 'SABRINA ANAHI', 'LOPEZ ORTIZ', 'F', '2015-07-30', '', '', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (4, 53, 'P004', '1313131313', '', 'SOFIA DANIELA', 'RISUEÑO INFANTE', 'F', '2014-05-26', '', '', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (4, 53, 'P005', '1728303080', '', 'ELIAN ALEJANDRO', 'MAYORGA BAYAS', 'M', '2013-08-22', '', '', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (8, 53, 'P006', '1717171717', '', 'LUIS DIEGO', 'PEREZ BAYAS', 'M', '2000-08-22', '', '', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (8, 53, 'P007', '1616161616', '', 'EVELYN MARIA', 'PEREZ BAYAS', 'F', '2000-08-22', '', '', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (8, 53, 'P008', '1515151515', '', 'ERIK OSCAR', 'PEREZ BAYAS', 'M', '2000-08-22', '', '', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (8, 53, 'P009', '1313131313', '', 'FACUNDO VINICIO', 'PEREZ BAYAS', 'M', '2000-08-22', '', '', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');

INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (6, 53, 'C001', '1717171717', 'jperez@gmail.com', 'JUAN', 'PEREZ', 'M', '1991-05-02', '022087044', '0983528439', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (6, 53, 'C002', '1616161616', 'dperez@gmail.com', 'DIEGO', 'PEREZ', 'F', '1991-06-22', '022087044', '0983528439', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (7, 53, 'C004', '1313131313', 'asuarez@gmail.com', 'ALFONSO', 'SUAREZ', 'M', '1991-11-02', '022087044', '0983528439', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (7, 53, 'C005', '1212121212', 'csuarez@gmail.com', 'CARMEN', 'SUAREZ', 'F', '1991-08-02', '022087044', '0983528439', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (7, 53, 'C006', '1414141414', 'clopez@gmail.com', 'CARLOS', 'LOPEZ', 'M', '1990-03-02', '022087014', '0983528439', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');
INSERT INTO ESTUDIANTE (REPRESENTANTE1_ID, NACIONALIDAD_ID, CODAUXILIAR, CEDULA, EMAIL, NOMBRES, APELLIDOS, GENERO, FECHA_NACIMIENTO, TELEFONO, CELULAR, DIRECCION, ADICIONALES, ETNIA, DISCAPACIDAD, CONADIS, TIPO_DISCAPACIDAD, OBS_DISCAPACIDAD, PORCENTAJE_DISCAPACIDAD, ESTADO) 
	VALUES (7, 53, 'C007', '1313131313', 'lgarcia@gmail.com', 'LUIS', 'GARCIA', 'M', '1991-01-02', '022123014', '0983528439', NULL, NULL, NULL, 'n', '', 'n', NULL, NULL, 'A');





INSERT INTO CATEGORIA_PRODUCTO (NOMBRE, DESCRIPCION, IMAGEN_PATH, ESTADO) 
	VALUES ('Rubros institucionales', 'Rubros institucionales', NULL, 'A');
INSERT INTO CATEGORIA_PRODUCTO (NOMBRE, DESCRIPCION, IMAGEN_PATH, ESTADO) 
	VALUES ('Rubros externos', 'Rubros externos', NULL, 'A');


INSERT INTO CATALOGO_PRODUCTO (CATEGORIA_ID, NOMBRE, DESCRIPCION, IVA_ID, ICE_ID, IRBPNR_ID, ESTADO, MODULO_COD, TIPO_COD) 
	VALUES (1,'Matriculas', NULL, 1, NULL, NULL, 'A', 'ACAS', 'MA');
INSERT INTO CATALOGO_PRODUCTO (CATEGORIA_ID, NOMBRE, DESCRIPCION, IVA_ID, ICE_ID, IRBPNR_ID, ESTADO, MODULO_COD, TIPO_COD) 
	VALUES (1,'Pensiones', NULL, 1, NULL, NULL, 'A', 'ACAS', 'NA');
INSERT INTO CATALOGO_PRODUCTO (CATEGORIA_ID, NOMBRE, DESCRIPCION, IVA_ID, ICE_ID, IRBPNR_ID, ESTADO, MODULO_COD, TIPO_COD) 
	VALUES (2,'Servicios complementarios', NULL, 1, NULL, NULL, 'A', 'ACAS', 'NA');



INSERT INTO NIVEL_ACADEMICO (NOMBRE, AULA_ID, PERIODO_ID, NIVEL_ID, ESTADO,DESCRIPCION) VALUES ('SQUIRLES', 1, 1, 1, 'A','De 3 meses a 1 año');
INSERT INTO NIVEL_ACADEMICO (NOMBRE, AULA_ID, PERIODO_ID, NIVEL_ID, ESTADO,DESCRIPCION) VALUES ('BUNNIES', 1, 1, 1, 'A','De 1 a 2 años');
INSERT INTO NIVEL_ACADEMICO (NOMBRE, AULA_ID, PERIODO_ID, NIVEL_ID, ESTADO,DESCRIPCION) VALUES ('KITTENS', 1, 1, 1, 'A','De 2 a 3 años');
INSERT INTO NIVEL_ACADEMICO (NOMBRE, AULA_ID, PERIODO_ID, NIVEL_ID, ESTADO,DESCRIPCION) VALUES ('PUPPIES', 1, 1, 2, 'A','De 3 a 4 años');
INSERT INTO NIVEL_ACADEMICO (NOMBRE, AULA_ID, PERIODO_ID, NIVEL_ID, ESTADO,DESCRIPCION) VALUES ('PONNIES', 1, 1, 2, 'A','De 4 a 5 años');
INSERT INTO NIVEL_ACADEMICO (NOMBRE, AULA_ID, PERIODO_ID, NIVEL_ID, ESTADO,DESCRIPCION) VALUES ('PRIMERO A', 2, 1, 4, 'A','5 años EN ADELANTE A');
INSERT INTO NIVEL_ACADEMICO (NOMBRE, AULA_ID, PERIODO_ID, NIVEL_ID, ESTADO,DESCRIPCION) VALUES ('PRIMERO B', 2, 1, 4, 'A','5 años EN ADELANTE B');
INSERT INTO NIVEL_ACADEMICO (NOMBRE, AULA_ID, PERIODO_ID, NIVEL_ID, ESTADO,DESCRIPCION) VALUES ('PRIMERO C', 2, 1, 4, 'A','5 años EN ADELANTE C');
INSERT INTO NIVEL_ACADEMICO (NOMBRE, AULA_ID, PERIODO_ID, NIVEL_ID, ESTADO,DESCRIPCION) VALUES ('PRIMERO D', 2, 1, 4, 'A','5 años EN ADELANTE D');
INSERT INTO NIVEL_ACADEMICO (NOMBRE, AULA_ID, PERIODO_ID, NIVEL_ID, ESTADO,DESCRIPCION) VALUES ('PRIMERO E', 2, 1, 4, 'A','5 años EN ADELANTE E');

INSERT INTO RUBROS_NIVEL (NOMBRE, DIAS_CREDITO, PERIODO_ID, NIVEL_ID, CATALOGO_PRODUCTO_ID, VALOR, MES_NUMERO) 
	VALUES ('Matricula', 0, 1, NULL,1, 325.50, 0);
INSERT INTO RUBROS_NIVEL (NOMBRE, DIAS_CREDITO, PERIODO_ID, NIVEL_ID, CATALOGO_PRODUCTO_ID, VALOR, MES_NUMERO) 
	VALUES ('Pension', 0, 1, NULL, 2, 180.50, 0);
INSERT INTO RUBROS_NIVEL (NOMBRE, DIAS_CREDITO, PERIODO_ID, NIVEL_ID, CATALOGO_PRODUCTO_ID, VALOR, MES_NUMERO) 
	VALUES ('Tareas dirigidas', 0, 1, NULL, 3, 214.50, 0);
INSERT INTO RUBROS_NIVEL (NOMBRE, DIAS_CREDITO, PERIODO_ID, NIVEL_ID, CATALOGO_PRODUCTO_ID, VALOR, MES_NUMERO) 
	VALUES ('CUIDADO TARDE 1', 0, 1, NULL, 3, 70.00, 0);
INSERT INTO RUBROS_NIVEL (NOMBRE, DIAS_CREDITO, PERIODO_ID, NIVEL_ID, CATALOGO_PRODUCTO_ID, VALOR, MES_NUMERO) 
	VALUES ('CUIDADO TARDE 2', 0, 1, NULL, 3, 35.00, 0);
INSERT INTO RUBROS_NIVEL (NOMBRE, DIAS_CREDITO, PERIODO_ID, NIVEL_ID, CATALOGO_PRODUCTO_ID, VALOR, MES_NUMERO) 
	VALUES ('TAREAS DIRIGIDAS Y CUIDADO', 0, 1, NULL, 3, 120.00, 0);
INSERT INTO RUBROS_NIVEL (NOMBRE, DIAS_CREDITO, PERIODO_ID, NIVEL_ID, CATALOGO_PRODUCTO_ID, VALOR, MES_NUMERO) 
	VALUES ('TAREAS DIRIGIDAS, ALMUERZO Y CUIDADO', 0, 1, NULL, 3, 150.00, 0);


INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (12, 6, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (15, 6, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (14, 6, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (11, 6, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (13, 6, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (10, 6, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (2, 1, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (7, 1, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (1, 1, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (3, 1, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (8, 1, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (6, 1, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (5, 1, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (9, 1, NULL, NULL, 'A');
INSERT INTO ESTUDIANTE_INSCRITO (ESTUDIANTE_ID, NIVEL_ACADEMICO_ID, TIPO_MATRICULA_COD, BECA, ESTADO) 
	VALUES (4, 1, NULL, NULL, 'A');