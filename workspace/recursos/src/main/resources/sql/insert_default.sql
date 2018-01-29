/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Carlos
 * Created: 11/11/2017
 */
INSERT INTO SRI_FORMA_PAGO (NOMBRE, CODIGO,FECHA_INICIO)
    VALUES  ('SIN UTILIZACION DEL SISTEMA FINANCIERO', '01', '2013-01-01'),
            ('COMPENSACION DE DEUDAS', '15', '2013-01-01'),
            ('TARJETA DE DEBITO', '16', '2016-06-01'),
            ('DINERO ELECTRONICO', '17', '2016-06-01'),
            ('TARJETA PREPAGO', '18', '2016-06-01'),
            ('TARJETA DE CREDITO', '19', '2016-06-01'),
            ('OTROS CON UTILIZACION DEL SISTEMA FINANCIERO', '20', '2016-06-01'),
            ('ENDOSO DE TITULOS', '21', '2016-06-01');

INSERT INTO SRI_IDENTIFICACION (CODIGO, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('01', 'COMPRA', 'RUC','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('02', 'COMPRA', 'CEDULA','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('03', 'COMPRA', 'PASAPORTE / IDENTIFICACION TRIBUTARIA DEL EXTERIOR','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('04', 'VENTA', 'RUC','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('05', 'VENTA', 'CEDULA','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('06', 'VENTA', 'PASAPORTE / IDENTIFICACION TRIBUTARIA DEL EXTERIOR','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('07', 'VENTA', 'CONSUMIDOR FINAL','01/01/2000');

INSERT INTO SRI_IDENTIFICACION (CODIGO, TIPO_TRANSACCION,TIPO_IDENTIFICACION,FECHA_INICIO)
    VALUES ('19', 'VENTA', 'PLACA o RAMV/CPN ','01/01/2000');

INSERT INTO PERFIL(NOMBRE,DESCRIPCION,ESTADO)
    VALUES('ADMIN','Perfil para administrador para cambiar configuraciones del sistema','A');

INSERT INTO PERFIL(NOMBRE,DESCRIPCION,ESTADO)
    VALUES('OPERADOR','Perfil normal','A');

INSERT INTO PERFIL_USUARIO(NICK,PERFIL_ID,FECHA_CREACION)
    VALUES('admin',1,CURRENT_DATE);

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

















