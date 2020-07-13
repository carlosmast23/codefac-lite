/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Target(value={ElementType.METHOD} )
@Retention(RetentionPolicy.RUNTIME)
public @interface TableBinding {
    /**
     * Origen de los datos
     * @return 
     */
    public String source();
    //public String modelTable();
    /**
     * Implementacion para poder agregar los datos en la tabla
     * @return 
     */
    public String tableAddDataInterface();
    /**
     * Campo donde se va a grabar el valor seleccionado por la fila
     * @return 
     */
    public String selectValue();
}
