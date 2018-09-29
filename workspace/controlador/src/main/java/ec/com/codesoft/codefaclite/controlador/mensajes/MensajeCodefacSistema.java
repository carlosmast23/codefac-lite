/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.mensajes;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import static ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj.TITULO_ADVERTENCIA;

/**
 *
 * @author Carlos
 */
public abstract class MensajeCodefacSistema {
    
    public static abstract class Impresiones {

        public static final CodefacMsj IMPRESION_SECCION_INCORRECTA = new CodefacMsj(TITULO_ADVERTENCIA,"Porfavor cargue un registro para poder imprimir",DialogoCodefac.MENSAJE_ADVERTENCIA);
    }
    
    public static abstract class AccionesFormulario {

        public static final CodefacMsj GUARDADO = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"La información fue guardada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        public static final CodefacMsj NO_PERMITE_EDITAR = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"La pantalla no permite modificaciones",DialogoCodefac.MENSAJE_INCORRECTO);
    }
    
    public static abstract  class ErrorComunicacion
    {
        public static final CodefacMsj ERROR_COMUNICACION_SERVIDOR = new CodefacMsj(CodefacMsj.TITULO_ERROR,"Erro de comunicación con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
    }
    
    /*
    public class  MensajeCodefacEnum {
        public String titulo;
        public String mensaje;
        public int modoMensaje;

        public MensajeCodefacEnum(String titulo, String mensaje, int modoMensaje) {
            this.titulo = titulo;
            this.mensaje = mensaje;
            this.modoMensaje = modoMensaje;
        }
    }*/
}
