/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Carlos
 */
@Target(value={ElementType.METHOD} )
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacionCodefacAnotacion {
    public static final String GRUPO_FORMULARIO="form"; 
    
    
    boolean requerido() default true;
    int min() default 0;
    int max() default 100;
    String expresionRegular() default "";
    String nombre() default "campo";
    /**
     * Conjunto de nombres de metodos que tienen que estar implementado en la misma
     * clase que hereda y sirven para tener validadores personalizados
     * @return 
     */
    String[] personalizado() default "";
    /**
     * El grupo sirve para permitir clasificar validadores del formulario
     * y pesonalizados por ejemplo si el grupo es form , los validadores funcionan
     * a nivel de todo el formulario pero si tiene otro nombre el usuario puede agrupar
     * y tener validadores personalidos para usar cuando los desee
     * @return  nombre del grupo
     */
    String grupo() default GRUPO_FORMULARIO;
    
}
