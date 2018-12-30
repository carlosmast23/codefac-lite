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
    
    private String[] tituloTabla= {"cedula","nombres","apellidos","genero","representante1","representante2","curso","estado"};
    
    public ExcelMigrarEstudiantes(File archivoExel) {
        super(archivoExel);
        init();
    }
    
    private void init()
    {
        setTituloTabla(tituloTabla);
        setTiposDatosCabecera(new Class[]{String.class, String.class, String.class, String.class, String.class, String.class, String.class});
    }

    @Override
    public int getIndiceEstado() {
        return 7; //Indice final
    }
    
    public enum Enum
    {
        IDENTIFICACION("Identificacion",0),
        NOMBRES("Nombres",1),
        APELLIDOS("Apellidos",2),
        GENERO("genero",3),
        IDENTIFICACION_REPRESENTATE_1("representante1",4),
        IDENTIFICACION_REPRESENTATE_2("representante2",5),
        CURSO_ACTUAL("curso",6);

        private Enum(String nombre,Integer posicion) {
            this.nombre = nombre;
            this.posicion=posicion;
        }
        
        public String nombre;
        public Integer posicion;
        
    }
    
}
