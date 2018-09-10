/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 11/11/2017
 */
INSERT INTO SRI_FORMA_PAGO (NOMBRE,ALIAS, CODIGO,FECHA_INICIO)
    VALUES  ('SIN UTILIZACION DEL SISTEMA FINANCIERO','Efectivo', '01', '2013-01-01'),
            ('COMPENSACION DE DEUDAS','Compensación', '15', '2013-01-01'),
            ('TARJETA DE DEBITO','Tarjeta débito', '16', '2016-06-01'),
            ('DINERO ELECTRONICO','Dinero electrónico', '17', '2016-06-01'),
            ('TARJETA PREPAGO','Tarjeta prepago', '18', '2016-06-01'),
            ('TARJETA DE CREDITO','Tarjeta crédito', '19', '2016-06-01'),
            ('OTROS CON UTILIZACION DEL SISTEMA FINANCIERO','Otros', '20', '2016-06-01'),
            ('ENDOSO DE TITULOS','Endoso títulos', '21', '2016-06-01');

INSERT INTO SRI_IDENTIFICACION (CODIGO,TIPO_IDENTIFICACION_LETRA, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('01','R', 'COMPRA', 'RUC','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO,TIPO_IDENTIFICACION_LETRA, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('02','C', 'COMPRA', 'CEDULA','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO,TIPO_IDENTIFICACION_LETRA, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('03','P', 'COMPRA', 'PASAPORTE / IDENTIFICACION TRIBUTARIA DEL EXTERIOR','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO,TIPO_IDENTIFICACION_LETRA, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('04','R', 'VENTA', 'RUC','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO,TIPO_IDENTIFICACION_LETRA, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('05','C', 'VENTA', 'CEDULA','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO,TIPO_IDENTIFICACION_LETRA, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('06','P', 'VENTA', 'PASAPORTE / IDENTIFICACION TRIBUTARIA DEL EXTERIOR','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO,TIPO_IDENTIFICACION_LETRA, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('07','F', 'VENTA', 'CONSUMIDOR FINAL','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO,TIPO_IDENTIFICACION_LETRA, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('19','O', 'VENTA', 'PLACA o RAMV/CPN ','01/01/2000');

INSERT INTO SRI_RETENCION (NOMBRE, CODIGO_SRI)
    VALUES ('RENTA','1'),
           ('IVA','2'),
           ('ISD','6');


INSERT INTO SRI_RETENCION_IVA (RETENCION_ID,NOMBRE, CODIGO_SRI,PORCENTAJE,DESCRIPCION,FECHA_INICIO)
    VALUES (2,'10%' , '9' ,10,'','01/01/2000'),
           (2,'20%' , '10',20,'','01/01/2000'),
           (2,'30%' , '1' ,30,'','01/01/2000'),
           (2,'50%' , '11',50,'','01/01/2000'),
           (2,'70%' , '2' ,70,'','01/01/2000'),
           (2,'100%', '3' ,100,'','01/01/2000'),
           (2,'0%'  , '7' ,0,'Retencion en 0%','01/01/2000'),
           (2,'0%'  , '8' ,0,'Retencion en 12%','01/01/2000');


INSERT INTO SRI_RETENCION_RENTA (RETENCION_ID,CODIGO_SRI, NOMBRE,PORCENTAJE,DESCRIPCION,FECHA_INICIO)
    VALUES (1,'520','Pago al exterior - Otros conceptos de ingresos gravados',22,'','01/01/2000'),
           (1,'333','CONVENIO DE DÉBITO O RECAUDACIÓN',0,'','01/01/2000'),
           (1,'334','PAGO CON TARJETA DE CRÉDITO',0,'','01/01/2000');



INSERT INTO ACCESO_DIRECTO (NOMBRE, X,Y)
    VALUES  ('ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel', 30, 50),
            ('ec.com.codesoft.codefaclite.crm.model.ClienteModel', 30, 150),
            ('ec.com.codesoft.codefaclite.crm.model.ProductoModel', 30, 250),
            ('ec.com.codesoft.codefaclite.configuraciones.model.CalculadoraModel', 30, 350),
            ('ec.com.codesoft.codefaclite.configuraciones.model.ComprobantesConfiguracionModel', 30, 450),
            ('WidgetVirtualMall',120,60),
            ('WidgetVentasDiarias',120,120);

INSERT INTO COMPROBANTE_FISICO_DISENIO (NOMBRE,CODIGO_DOCUMENTO,ANCHO,ALTO)
    VALUES('Factura','FAC',595,842),
          ('Nota de Venta','NVT',595,842);


INSERT INTO BANDA_COMPROBANTE (COMPROBANTE_FISICO_ID, NOMBRE,TITULO,ORDEN,ALTO)
    VALUES(1,'pageHeader','Cabecera',1,84),
          (1,'detail','Detalles',3,23),
          (1,'columnFooter','Pie Pagina',4,104),
          (2,'pageHeader','Cabecera',1,84),
          (2,'detail','Detalles',3,23),
          (2,'columnFooter','Pie Pagina',4,104);

INSERT INTO COMPONENTE_COMPROBANTE_FISICO (BANDA_COMPROBANTE_ID,NOMBRE,UUID,X,Y,ANCHO,ALTO,TAMANIO_LETRA,NEGRITA,OCULTO)
    VALUES
          (1,'identificacion','e165fa85-1cb1-47e9-9af2-f89f0ff2c065',389,50,133,15,10,'n','n'),
          (1,'fechaEmision','2915f0d2-afa9-4aba-8f9a-6bc7a4ac49c7',18,5,133,15,10,'n','n'),
          (1,'razonSocial','a93fd1ad-f710-4b35-ad52-4a4a75b14b83',18,20,244,15,10,'n','n'),
          (1,'direccion','0ee1f0c6-f914-4ffc-9a43-874e4dc6e8af',18,35,188,15,10,'n','n'),
          (1,'telefono','649694ec-4c36-45e9-b405-4cc54a640582',18,50,133,15,10,'n','n'),
          (1,'correoElectronico','bf6539d9-3e3f-4f8b-b9be-917a103e0796',389,35,133,15,10,'n','n'),
          (2,'cantidad','4187fe71-1b90-4ecb-a317-b1515b77fe14',18,0,81,15,10,'n','n'),
          (2,'descripcion','63c13824-dd49-434e-a00d-7c356f78688b',99,0,270,15,10,'n','n'),
          (2,'valorUnitario','4352aef2-1d77-425e-95bd-736cd2211c25',369,0,93,15,10,'n','n'),
          (2,'valorTotal','3119d150-a3dc-4e39-9e60-4e349cc1daf9',462,0,93,15,10,'n','n'),
          (3,'subtotalSinImpuesto','b9a3a0cb-774d-485f-8933-f4ca3b41fba7',451,21,100,15,10,'n','n'),
          (3,'subtotalImpuesto','86054d4c-ae41-4430-9510-2d8749561874',451,6,100,15,10,'n','n'),
          (3,'descuento','0e91accb-5609-49f8-9878-4f77c27b8491',451,36,100,15,10,'n','n'),
          (3,'subtotalConDescuento','32b039ed-968e-4b31-acd0-943e1063e6df',451,51,100,15,10,'n','n'),
          (3,'valorIva','ad8a6e6b-030a-4490-9630-ee0dadf104db',451,66,100,15,10,'n','n'),
          (3,'iva','bfa48f42-e1e5-4bda-84a5-41af89f66937',422,66,19,15,10,'n','n'),
          (3,'total','e8b582dc-3b95-4f26-a1b6-dddde4a42378',451,81,100,15,10,'n','n'),

          (4,'identificacion','e165fa85-1cb1-47e9-9af2-f89f0ff2c065',389,50,133,15,10,'n','n'),
          (4,'fechaEmision','2915f0d2-afa9-4aba-8f9a-6bc7a4ac49c7',18,5,133,15,10,'n','n'),
          (4,'razonSocial','a93fd1ad-f710-4b35-ad52-4a4a75b14b83',18,20,244,15,10,'n','n'),
          (4,'direccion','0ee1f0c6-f914-4ffc-9a43-874e4dc6e8af',18,35,188,15,10,'n','n'),
          (4,'telefono','649694ec-4c36-45e9-b405-4cc54a640582',18,50,133,15,10,'n','n'),
          (4,'correoElectronico','bf6539d9-3e3f-4f8b-b9be-917a103e0796',389,35,133,15,10,'n','n'),
          (5,'cantidad','4187fe71-1b90-4ecb-a317-b1515b77fe14',18,0,81,15,10,'n','n'),
          (5,'descripcion','63c13824-dd49-434e-a00d-7c356f78688b',99,0,270,15,10,'n','n'),
          (5,'valorUnitario','4352aef2-1d77-425e-95bd-736cd2211c25',369,0,93,15,10,'n','n'),
          (5,'valorTotal','3119d150-a3dc-4e39-9e60-4e349cc1daf9',462,0,93,15,10,'n','n'),
          (6,'subtotal','b9a3a0cb-774d-485f-8933-f4ca3b41fba7',451,21,100,15,10,'n','n'),
          (6,'descuento','0e91accb-5609-49f8-9878-4f77c27b8491',451,36,100,15,10,'n','n'),
          (6,'total','e8b582dc-3b95-4f26-a1b6-dddde4a42378',451,81,100,15,10,'n','n');

INSERT INTO BODEGA (NOMBRE,DESCRIPCION,ENCARGADO,IMAGEN_PATH,ESTADO)
    VALUES('Bodega','Bodega por defecto','','','a');

INSERT INTO CATEGORIA_PRODUCTO(NOMBRE,DESCRIPCION)
    VALUES('Ninguna','defecto');

INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Afganistán','AFGANA','AFG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Albania','ALBANESA','ALB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Alemania','ALEMANA','DEU');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Andorra','ANDORRANA','AND');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Angola','ANGOLEÑA','AGO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('AntiguayBarbuda','ANTIGUANA','ATG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('ArabiaSaudita','SAUDÍ','SAU');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Argelia','ARGELINA','DZA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Argentina','ARGENTINA','ARG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Armenia','ARMENIA','ARM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Aruba','ARUBEÑA','ABW');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Australia','AUSTRALIANA','AUS');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Austria','AUSTRIACA','AUT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Azerbaiyán','AZERBAIYANA','AZE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bahamas','BAHAMEÑA','BHS');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bangladés','BANGLADESÍ','BGD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Barbados','BARBADENSE','BRB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Baréin','BAREINÍ','BHR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bélgica','BELGA','BEL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Belice','BELICEÑA','BLZ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Benín','BENINÉSA','BEN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bielorrusia','BIELORRUSA','BLR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Birmania','BIRMANA','MMR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bolivia','BOLIVIANA','BOL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('BosniayHerzegovina','BOSNIA','BIH');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Botsuana','BOTSUANA','BWA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Brasil','BRASILEÑA','BRA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Brunéi','BRUNEANA','BRN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bulgaria','BÚLGARA','BGR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('BurkinaFaso','BURKINÉS','BFA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Burundi','BURUNDÉSA','BDI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Bután','BUTANÉSA','BTN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('CaboVerde','CABOVERDIANA','CPV');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Camboya','CAMBOYANA','KHM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Camerún','CAMERUNESA','CMR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Canadá','CANADIENSE','CAN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Catar','CATARÍ','QAT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Chad','CHADIANA','TCD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Chile','CHILENA','CHL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('China','CHINA','CHN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Chipre','CHIPRIOTA','CYP');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('CiudaddelVaticano','VATICANA','VAT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Colombia','COLOMBIANA','COL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Comoras','COMORENSE','COM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('CoreadelNorte','NORCOREANA','PRK');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('CoreadelSur','SURCOREANA','KOR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('CostadeMarfil','MARFILEÑA','CIV');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('CostaRica','COSTARRICENSE','CRI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Croacia','CROATA','HRV');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Cuba','CUBANA','CUB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Dinamarca','DANÉSA','DNK');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Dominica','DOMINIQUÉS','DMA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Ecuador','ECUATORIANA','ECU');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Egipto','EGIPCIA','EGY');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('ElSalvador','SALVADOREÑA','SLV');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('EmiratosÁrabesUnidos','EMIRATÍ','ARE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Eritrea','ERITREA','ERI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Eslovaquia','ESLOVACA','SVK');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Eslovenia','ESLOVENA','SVN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('España','ESPAÑOLA','ESP');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('EstadosUnidos','ESTADOUNIDENSE','USA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Estonia','ESTONIA','EST');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Etiopía','ETÍOPE','ETH');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Filipinas','FILIPINA','PHL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Finlandia','FINLANDÉSA','FIN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Fiyi','FIYIANA','FJI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Francia','FRANCÉSA','FRA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Gabón','GABONÉSA','GAB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Gambia','GAMBIANA','GMB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Georgia','GEORGIANA','GEO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Gibraltar','GIBRALTAREÑA','GIB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Ghana','GHANÉSA','GHA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Granada','GRANADINA','GRD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Grecia','GRIEGA','GRC');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Groenlandia','GROENLANDÉSA','GRL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Guatemala','GUATEMALTECA','GTM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Guineaecuatorial','ECUATOGUINEANA','GNQ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Guinea','GUINEANA','GIN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Guinea-Bisáu','GUINEANA','GNB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Guyana','GUYANESA','GUY');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Haití','HAITIANA','HTI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Honduras','HONDUREÑA','HND');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Hungría','HÚNGARA','HUN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('India','HINDÚ','IND');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Indonesia','INDONESIA','IDN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Irak','IRAQUÍ','IRQ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Irán','IRANÍ','IRN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Irlanda','IRLANDÉSA','IRL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Islandia','ISLANDÉSA','ISL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('IslasCook','COOKIANA','COK');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('IslasMarshall','MARSHALÉSA','MHL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('IslasSalomón','SALOMONENSE','SLB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Israel','ISRAELÍ','ISR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Italia','ITALIANA','ITA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Jamaica','JAMAIQUINA','JAM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Japón','JAPONÉSA','JPN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Jordania','JORDANA','JOR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Kazajistán','KAZAJA','KAZ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Kenia','KENIATA','KEN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Kirguistán','KIRGUISA','KGZ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Kiribati','KIRIBATIANA','KIR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Kuwait','KUWAITÍ','KWT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Laos','LAOSIANA','LAO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Lesoto','LESOTENSE','LSO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Letonia','LETÓNA','LVA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Líbano','LIBANÉSA','LBN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Liberia','LIBERIANA','LBR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Libia','LIBIA','LBY');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Liechtenstein','LIECHTENSTEINIANA','LIE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Lituania','LITUANA','LTU');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Luxemburgo','LUXEMBURGUÉSA','LUX');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Madagascar','MALGACHE','MDG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Malasia','MALASIA','MYS');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Malaui','MALAUÍ','MWI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Maldivas','MALDIVA','MDV');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Malí','MALIENSE','MLI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Malta','MALTÉSA','MLT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Marruecos','MARROQUÍ','MAR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Martinica','MARTINIQUÉS','MTQ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Mauricio','MAURICIANA','MUS');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Mauritania','MAURITANA','MRT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('México','MEXICANA','MEX');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Micronesia','MICRONESIA','FSM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Moldavia','MOLDAVA','MDA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Mónaco','MONEGASCA','MCO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Mongolia','MONGOLA','MNG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Montenegro','MONTENEGRINA','MNE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Mozambique','MOZAMBIQUEÑA','MOZ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Namibia','NAMIBIA','NAM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Nauru','NAURUANA','NRU');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Nepal','NEPALÍ','NPL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Nicaragua','NICARAGÜENSE','NIC');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Níger','NIGERINA','NER');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Nigeria','NIGERIANA','NGA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Noruega','NORUEGA','NOR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('NuevaZelanda','NEOZELANDÉSA','NZL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Omán','OMANÍ','OMN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('PaísesBajos','NEERLANDÉSA','NLD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Pakistán','PAKISTANÍ','PAK');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Palaos','PALAUANA','PLW');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Palestina','PALESTINA','PSE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Panamá','PANAMEÑA','PAN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('PapúaNuevaGuinea','PAPÚ','PNG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Paraguay','PARAGUAYA','PRY');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Perú','PERUANA','PER');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Polonia','POLACA','POL');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Portugal','PORTUGUÉSA','PRT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('PuertoRico','PUERTORRIQUEÑA','PRI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('ReinoUnido','BRITÁNICA','GBR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicaCentroafricana','CENTROAFRICANA','CAF');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicaCheca','CHECA','CZE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicadeMacedonia','MACEDONIA','MKD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicadelCongo','CONGOLEÑA','COG');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicaDemocráticadelCongo','CONGOLEÑA','COD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicaDominicana','DOMINICANA','DOM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('RepúblicaSudafricana','SUDAFRICANA','ZAF');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Ruanda','RUANDÉSA','RWA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Rumanía','RUMANA','ROU');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Rusia','RUSA','RUS');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Samoa','SAMOANA','WSM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SanCristóbalyNieves','CRISTOBALEÑA','KNA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SanMarino','SANMARINENSE','SMR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SanVicenteylasGranadinas','SANVICENTINA','VCT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SantaLucía','SANTALUCENSE','LCA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SantoToméyPríncipe','SANTOTOMENSE','STP');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Senegal','SENEGALÉSA','SEN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Serbia','SERBIA','SRB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Seychelles','SEYCHELLENSE','SYC');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SierraLeona','SIERRALEONÉSA','SLE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Singapur','SINGAPURENSE','SGP');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Siria','SIRIA','SYR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Somalia','SOMALÍ','SOM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SriLanka','CEILANÉSA','LKA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Suazilandia','SUAZI','SWZ');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('SudándelSur','SURSUDANÉSA','SSD');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Sudán','SUDANÉSA','SDN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Suecia','SUECA','SWE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Suiza','SUIZA','CHE');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Surinam','SURINAMESA','SUR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Tailandia','TAILANDÉSA','THA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Tanzania','TANZANA','TZA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Tayikistán','TAYIKA','TJK');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('TimorOriental','TIMORENSE','TLS');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Togo','TOGOLÉSA','TGO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Tonga','TONGANA','TON');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('TrinidadyTobago','TRINITENSE','TTO');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Túnez','TUNECINA','TUN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Turkmenistán','TURCOMANA','TKM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Turquía','TURCA','TUR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Tuvalu','TUVALUANA','TUV');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Ucrania','UCRANIANA','UKR');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Uganda','UGANDÉSA','UGA');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Uruguay','URUGUAYA','URY');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Uzbekistán','UZBEKA','UZB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Vanuatu','VANUATUENSE','VUT');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Venezuela','VENEZOLANA','VEN');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Vietnam','VIETNAMITA','VNM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Yemen','YEMENÍ','YEM');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Yibuti','YIBUTIANA','DJI');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Zambia','ZAMBIANA','ZMB');
INSERT INTO NACIONALIDAD(PAIS_NAC,GENTILICIO_NAC,ISO_NAC)VALUES('Zimbabue','ZIMBABUENSE','ZWE');











