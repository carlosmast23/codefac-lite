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
 * @author CARLOS_CODESOFT
 */
public class ExcelMigrarVehiculosMantenimiento extends ExcelMigrar{

    @Override
    public CampoMigrarInterface[] obtenerCampos() {
        return ExcelMigrarVehiculosMantenimiento.Enum.values();
    }

    @Override
    public CampoMigrarInterface getCampoEstado() {
         return ExcelMigrarCursos.Enum.ESTADO;
    }
    
    public enum Enum implements CampoMigrarInterface
    {
        NUMERO("NUMERO",0,Double.class,false),
        MODELO("MODELO",1,String.class,false),
        COLOR("COLOR",2,String.class,false),
        VIN("VIN",3,String.class,true),
        ESTADO_VEHICULO("ESTADO_VEHICULO",4,String.class,false),
        FECHA_INGRESO("F.INGRESO",5,Date.class,false),
        ESTADO("ESTADO",6,String.class,false),;

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
