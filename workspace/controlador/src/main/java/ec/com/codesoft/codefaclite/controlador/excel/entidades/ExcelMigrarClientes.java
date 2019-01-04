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
public class ExcelMigrarClientes extends ExcelMigrar {

    @Override
    public CampoMigrarInterface[] obtenerCampos() {
        return ExcelMigrarClientes.Enum.values();
    }

    @Override
    public CampoMigrarInterface getCampoEstado() {
        return ExcelMigrarClientes.Enum.ESTADO;
    }
    
     public enum Enum implements CampoMigrarInterface
    {
        IDENTIFICACION("Identificación",0,String.class),
        NOMBRES("Nombres",1,String.class),
        APELLIDOS("Apellidos",2,String.class),
        RAZON_SOCIAL("Razon Social",3,String.class),
        DIRECCION("Direccion",4,String.class),
        TELEFONO("Telefono",5,String.class),
        CELULAR("Celular",6,String.class),
        CORREO("Correo",7,String.class),
        ESTADO("Estado",8,String.class);

        private Enum(String nombre,Integer posicion,Class tipoDato) {
            this.nombre = nombre;
            this.posicion=posicion;
            this.tipoDato=tipoDato;
        }
        
        public String nombre;
        public Integer posicion;
        public Class tipoDato;

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
        
    }
    
}
