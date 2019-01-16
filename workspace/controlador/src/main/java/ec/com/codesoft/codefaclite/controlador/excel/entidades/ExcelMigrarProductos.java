/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel.entidades;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;

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
        CODIGO("Identificación",0,String.class),
        NOMBRE("Nombres",1,String.class),
        PRECIO_VENTA_PUBLICO("Precio 1",2,String.class),
        CATALOGO_PRODUCTO("Catalogo producto",3,String.class),
        
        ESTADO("Estado",4,String.class);

        private Enum(String nombre,Integer posicion,Class tipoDato) {
            this.nombre = nombre;
            this.posicion=posicion;
            this.tipoDato=tipoDato;
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
