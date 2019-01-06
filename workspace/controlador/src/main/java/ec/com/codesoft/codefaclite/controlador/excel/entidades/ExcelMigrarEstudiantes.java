/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel.entidades;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import java.io.File;

/**
 *
 * @author Carlos
 */
public class ExcelMigrarEstudiantes extends ExcelMigrar{

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
        IDENTIFICACION("Identificacion",0),
        NOMBRES("Nombres",1),
        APELLIDOS("Apellidos",2),
        GENERO("genero",3),
        IDENTIFICACION_REPRESENTATE_1("representante1",4),
        IDENTIFICACION_REPRESENTATE_2("representante2",5),
        CURSO_ACTUAL("curso",6),
        ESTADO("estado",7);

        private Enum(String nombre,Integer posicion) {
            this.nombre = nombre;
            this.posicion=posicion;
            this.requerido=true;
        }
        
        private boolean requerido;
        public String nombre;
        public Integer posicion;

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
            return String.class;
        }

        @Override
        public Boolean getCampoRequerido() {
            return this.requerido;
        }

        @Override
        public void setCampoRequerido(boolean requerido){
            this.requerido=requerido;
        }
        
    }
    
}
