/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel.entidades;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ExcelMigrarVehiculosMantenimiento extends ExcelMigrar{

    @Override
    public CampoMigrarInterface[] obtenerCampos() {
        return ExcelMigrarCursos.Enum.values();
    }

    @Override
    public CampoMigrarInterface getCampoEstado() {
         return ExcelMigrarCursos.Enum.ESTADO;
    }
    
    public enum Enum implements CampoMigrarInterface
    {
        NUMERO("NUMERO",0),
        MODELO("MODELO",1),
        COLOR("COLOR",2),
        VIN("VIN",3),
        CLIENTE("CLIENTE",4),
        FECHA_INGRESO("F.INGRESO",5),
        ESTADO("ESTADO",6);

        private Enum(String nombre,Integer posicion) {
            this.nombre = nombre;
            this.posicion=posicion;
        }
        
        public String nombre;
        public Integer posicion;
        private boolean requerido;

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
            return requerido;
        }

        @Override
        public void setCampoRequerido(boolean requerido) {
            this.requerido=requerido;
        }
        
    }
    
}
