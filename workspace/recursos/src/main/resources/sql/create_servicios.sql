/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  CodesoftDesarrollo 1
 * Created: 14/05/2018
 */
create table ORDEN_TRABAJO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1) ,
    CLIENTE_ID BIGINT,
    CODIGO varchar(15),
    DESCRIPCION varchar(150),
    ESTADO varchar(10),
    FECHA_INGRESO date,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.1.4)*/
    OBJECTO_MANTENIMIENTO_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.5.2)*/
    CATALOGO_PRODUCTO_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.4.2)*/
    USUARIO_ID BIGINT,
    primary key(ID)
);

create table DETALLE_ORDEN_TRABAJO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.1)*/
    DEPARTAMENTO_ID BIGINT,
    EMPLEADO_ID BIGINT,
    ORDEN_TRABAJO_ID BIGINT,
    DESCRIPCION varchar(150),
    NOTAS varchar(150),
    FECHA_ENTREGA date,
    ESTADO varchar(10),
    TIPO_ORDEN_TRABAJO varchar(10),
    PRIORIDAD varchar(15),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.5.2)*/
    TITULO varchar(60),
    primary key(ID)
);

create table EMPLEADO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    DEPARTAMENTO_ID BIGINT,
    /*@ELIMINAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    PERSONA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    NACIONALIDAD_ID BIGINT,
    CARGO varchar(30),
    ESTADO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.8.3.7)*/
    CODIGO varchar(100),    
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    NOMBRES varchar(100),    
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    APELLIDOS varchar(100),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    CEDULA varchar(13),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    DIRECCION varchar(150),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    TELEFONO_CONVENCIONAL varchar(15),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    TELEFONO_CELULAR varchar(15),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    CORREO_ELECTRONICO varchar(100),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    GENERO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.9.6)*/
    PLACA varchar(64),
    primary key(ID)
);

create table DEPARTAMENTO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1),
    CODIGO varchar(10),
    NOMBRE varchar(100),
    DESCRIPCION varchar(1024),
    ESTADO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.6.9.9)*/
    TIPO varchar(1),
    primary key(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2)*/
create table PRESUPUESTO
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY(START WITH 1),
    EMPRESA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.7.7)*/
    SUCURSAL_ID BIGINT,
    USUARIO_ID BIGINT,
    CLIENTE_ID BIGINT,
    CATALOGO_PRODUCTO_ID BIGINT,
    CODIGO varchar(10),
    DESCRIPCION varchar(150),
    OBSERVACIONES varchar(150),
    ESTADO varchar(1),
    FECHA_CREACION timestamp,
    FECHA_PRESUPUESTO date,
    FECHA_VALIDEZ date,
    DESCUENTO_COMPRA decimal(13,2),
    DESCUENTO_VENTA decimal(13,2),
    TOTAL_COMPRA decimal(13,2),
    TOTAL_VENTA decimal(13,2),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.0.7)*/
    FACTURA_ID BIGINT,
    
    ORDEN_TRABAJO_DETALLE_ID BIGINT,
    primary key(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2)*/
create table PRESUPUESTO_DETALLE
(
    ID BIGINT not null generated always as IDENTITY(start with 1),
    PRECIO_COMPRA decimal(13,2),
    DESCUENTO_COMPRA decimal(13,2),
    PRECIO_VENTA decimal(13,2),
    DESCUENTO_VENTA decimal(13,2),
    CANTIDAD decimal(13,2),
    NUMERO_ORDEN_COMPRA INT,
    ESTADO varchar(1),
    PROVEEDOR_ID BIGINT,
    PRODUCTO_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2)*/
    PRESUPUESTO_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.3)*/
    PRODUCTO_PROVEEDOR_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.1.5)*/
    BODEGA_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.4.2)*/
    RESERVADO varchar(1),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.4.3)*/
    KARDEX_ID BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.4.9)*/
    EMPLEADO_ID BIGINT,
    primary key(ID),
    CONSTRAINT PRESUPUESTO_DETALLE_EMPLEADO_FK FOREIGN KEY (EMPLEADO_ID) REFERENCES EMPLEADO(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.9.1.3)*/
create table OBJETO_MANTENIMIENTO( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    CODIGO varchar(100),
    NOMBRE varchar(100),
    DESCRIPCION varchar(100),
    TIPO varchar(1),   
    EMPRESA_ID BIGINT,  
    PROPIETARIO_ID BIGINT,  

    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.5.0)*/
    TIPO_COMBUSTIBLE varchar(100),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.5.0)*/
    MARCA_ID BIGINT,

    FECHA_CREACION timestamp, 
    FECHA_ULTIMA_EDICION timestamp, 
    USUARIO_CREACION_ID BIGINT,  
    USUARIO_ULTIMA_EDICION_ID BIGINT,  
    ESTADO varchar(1), 
    
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.5.1)*/
    KILOMETRAJE BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.5.3)*/
    ANIO BIGINT,
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.2.9.5.3)*/
    COLOR varchar(100),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.3.2)*/
    VIN varchar(32),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.3.2)*/
    MODELO varchar(32),

    primary key (ID),
    CONSTRAINT id_propietario_fk FOREIGN KEY (PROPIETARIO_ID) REFERENCES CLIENTE(CLIENTE_ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.2.9.4.9)*/
create table PRESUPUESTO_DETALLE_ACTIVIDAD
(
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY( START WITH 1),
    PRODUCTO_ACTIVIDAD_ID INT,
    TERMINADO varchar(1),

    FECHA_CREACION timestamp, 
    FECHA_ULTIMA_EDICION timestamp, 
    USUARIO_CREACION_ID BIGINT,  
    USUARIO_ULTIMA_EDICION_ID BIGINT,  
    ESTADO varchar(1),
    
    PRESUPUESTO_DETALLE_ID BIGINT,  
    primary key(ID),
    CONSTRAINT PRODUCTO_ACTIVIDAD_PRESUPUESTO_FK FOREIGN KEY (PRESUPUESTO_DETALLE_ID) REFERENCES PRESUPUESTO_DETALLE(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.3.0.3.2)*/
create table MANTENIMIENTO
( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.3.3)*/
    OBJETO_MANTENIMIENTO_ID BIGINT,
    CODIGO varchar(100),
    COMENTARIO varchar(512),    
    SUPERVISOR_ID BIGINT,  
    FECHA_INGRESO timestamp, 
    FECHA_SALIDA timestamp, 

    FECHA_CREACION timestamp, 
    FECHA_ULTIMA_EDICION timestamp, 
    USUARIO_CREACION_ID BIGINT,  
    USUARIO_ULTIMA_EDICION_ID BIGINT,  
    ESTADO varchar(1),

    primary key (ID),
    CONSTRAINT id_mantenimiento_vehiculo_fk FOREIGN KEY (OBJETO_MANTENIMIENTO_ID) REFERENCES OBJETO_MANTENIMIENTO(ID),    
    CONSTRAINT id_mantenimiento_empleado_fk FOREIGN KEY (SUPERVISOR_ID) REFERENCES EMPLEADO(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.3.0.3.3)*/
create table MANTENIMIENTO_TAREA_DETALLE
( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    OPERADOR_ID BIGINT,    
    OBSERVACION varchar(512),        
    FECHA_INICIO timestamp, 
    FECHA_FIN timestamp,

    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.3.5)*/
    MANTENIMIENTO_ID BIGINT,

    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.3.5)*/
    TAREA_MANTENIMIENTO_ID BIGINT,

    FECHA_CREACION timestamp, 
    FECHA_ULTIMA_EDICION timestamp, 
    USUARIO_CREACION_ID BIGINT,  
    USUARIO_ULTIMA_EDICION_ID BIGINT,  
    ESTADO varchar(1),

    primary key (ID),
    CONSTRAINT id_mantenimiento_tarea_empleado_fk FOREIGN KEY (OPERADOR_ID) REFERENCES EMPLEADO(ID),
    CONSTRAINT id_mantenimiento_fk FOREIGN KEY (MANTENIMIENTO_ID) REFERENCES MANTENIMIENTO(ID),
    CONSTRAINT id_mantenimiento_tarea_fk FOREIGN KEY (TAREA_MANTENIMIENTO_ID) REFERENCES TAREA_MANTENIMIENTO(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.3.0.3.2)*/
create table TAREA_MANTENIMIENTO
( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    OPERARIO_ID BIGINT,  
    NOMBRE varchar(100),    
    ORDEN INT,  
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.3.3)*/
    TIPO varchar(1),

    FECHA_CREACION timestamp, 
    FECHA_ULTIMA_EDICION timestamp, 
    USUARIO_CREACION_ID BIGINT,  
    USUARIO_ULTIMA_EDICION_ID BIGINT,  
    ESTADO varchar(1),

    primary key (ID),
    CONSTRAINT id_tarea_mantenimiento_empleado_fk FOREIGN KEY (OPERARIO_ID) REFERENCES EMPLEADO(ID)
);

/*@AGREGAR_TABLA(VERSION_SISTEMA=1.3.0.3.2)*/
create table VEHICULO
( 
    ID BIGINT not null GENERATED ALWAYS AS IDENTITY (START WITH 1), 
    CODIGO varchar(100),
    NOMBRE varchar(100),
    DESCRIPCION varchar(100),
    TIPO varchar(1),   
    EMPRESA_ID BIGINT,  
    PROPIETARIO_ID BIGINT,  
    TIPO_COMBUSTIBLE varchar(100),
    MARCA_ID BIGINT,

    FECHA_CREACION timestamp, 
    FECHA_ULTIMA_EDICION timestamp, 
    USUARIO_CREACION_ID BIGINT,  
    USUARIO_ULTIMA_EDICION_ID BIGINT,  
    ESTADO varchar(1), 
    KILOMETRAJE BIGINT,
    ANIO BIGINT,
    COLOR varchar(100),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.3.2)*/
    VIN varchar(32),
    /*@AGREGAR_COLUMNA(VERSION_SISTEMA=1.3.0.3.2)*/
    MODELO varchar(32),

    primary key (ID),
    CONSTRAINT id_duenio_fk FOREIGN KEY (PROPIETARIO_ID) REFERENCES CLIENTE(CLIENTE_ID)
);