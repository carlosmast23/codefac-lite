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

INSERT INTO COMPROBANTE_FISICO_DISENIO (NOMBRE, ANCHO,ALTO)
    VALUES('Factura',595,842);

INSERT INTO BANDA_COMPROBANTE (COMPROBANTE_FISICO_ID, NOMBRE,TITULO,ORDEN,ALTO)
    VALUES(1,'pageHeader','Cabecera',1,63),
          (1,'columnHeader','Cabecera Detalle',2,61),
          (1,'detail','Detalles',3,125),
          (1,'columnFooter','Pie Pagina',4,45);

INSERT INTO COMPONENTE_COMPROBANTE_FISICO (BANDA_COMPROBANTE_ID,NOMBRE,UUID,X,Y,ANCHO,ALTO,TAMANIO_LETRA,NEGRITA)
    VALUES(1,'identificacion','e165fa85-1cb1-47e9-9af2-f89f0ff2c065',15,13,100,20,11,'s'),
          (1,'razonSocial','cd88ab17-33e8-4da8-a3c6-b5d4be380f10',18,38,100,20,11,'s'),
          (3,'cantidad','4187fe71-1b90-4ecb-a317-b1515b77fe14',0,0,69,20,11,'s'),
          (3,'descripcion','63c13824-dd49-434e-a00d-7c356f78688b',69,0,300,20,11,'s'),
          (3,'valorUnitario','4352aef2-1d77-425e-95bd-736cd2211c25',369,0,93,20,11,'s'),
          (3,'valorTotal','3119d150-a3dc-4e39-9e60-4e349cc1daf9',462,0,93,20,11,'s');
