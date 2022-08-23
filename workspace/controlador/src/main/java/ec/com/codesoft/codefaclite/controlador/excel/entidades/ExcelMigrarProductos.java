/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel.entidades;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import java.util.Date;

/**
 *
 * @author Carlos
 */
public class ExcelMigrarProductos extends ExcelMigrar{
        

    @Override
    public CampoMigrarInterface[] obtenerCampos() {
        return Enum.values();
    }

    @Override
    public CampoMigrarInterface getCampoEstado() {
        return Enum.ESTADO;
    }
    
    public enum Enum implements CampoMigrarInterface
    {
        CODIGO("Código",0,String.class,true),
        CODIGO_2("Código2",1,String.class,false),
        NOMBRE("Nombres",2,String.class,true),
        COSTO("Costo",3,Double.class,true),
        PRECIO_VENTA_PUBLICO("Precio PVP",4,Double.class,true),
        PRECIO_VENTA_OFERTA("Precio DISTRIB",5,Double.class,false),
        PRECIO_VENTA_PROMEDIO("Precio PROMEDIO",6,Double.class,false),
        PRECIO_VENTA_4("PVP4",7,Double.class,false),
        PRECIO_VENTA_5("PVP5",8,Double.class,false),
        PRECIO_VENTA_6("PVP6",9,Double.class,false),
        CATEGORIA("Categoria",10,String.class,false),
        IVA_PORCENTAJE("IVA Porcentaje",11,Double.class,false),
        MANEJA_INVENTARIO("Maneja Inventario",12,String.class,false),
        BODEGA("Bodega",13,String.class,false),
        STOCK("Stock",14,Double.class,false),
        STOCK_MINIMO("Stock Min",15,Double.class,false),
        MARCA("Marca",16,String.class,false),
        UBICACION("Ubicación",17,String.class,false),
        APLICACION("Aplicación",18,String.class,false),
        TIPO("Tipo",19,String.class,false),
        SEGMENTO("Segmento",20,String.class,false),
        CASA_COMERCIAL("Casa Comercial",21,String.class,false),
        NOMBRE_GENERICO("Nombre Generico",22,String.class,false),
        NOMBRE_PROVEEDOR("Nombre Proveedor",23,String.class,false),
        FECHA_CADUCIDAD("Fecha Caducidad",24,Date.class,false),
        CANTIDAD_CAJA("Cantidad Caja",25,Double.class,false),
        NOMBRE_EMPAQUETADP("Empaquetado",26,String.class,false),
        ESTADO("Estado",27,String.class,false);

        private Enum(String nombre,Integer posicion,Class tipoDato,Boolean requerido) {
            this.nombre = nombre;
            this.posicion=posicion;
            this.tipoDato=tipoDato;
            this.requerido=requerido;
        }
        
        public String nombre;
        public Integer posicion;
        public Class tipoDato;
        private Boolean requerido;

        @Override
        public String getNombre() {
            return nombre;
        }

        @Override
        public int getPosicion() {
            return posicion;
        }

        @Override
        public Class getTipoDato() {
            return tipoDato;
        }

        @Override
        public Boolean getCampoRequerido() {
            return requerido;
        }

        @Override
        public void setCampoRequerido(boolean requerido) {
            this.requerido=requerido;
        }
        
    }
    
}
