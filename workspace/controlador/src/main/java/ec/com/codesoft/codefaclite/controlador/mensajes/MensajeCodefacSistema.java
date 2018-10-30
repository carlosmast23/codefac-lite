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
        public static final CodefacMsj ELIMINADO_CORRECTAMENTE = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"La información fue eliminada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        public static final CodefacMsj NO_PERMITE_EDITAR = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"La pantalla no permite modificaciones",DialogoCodefac.MENSAJE_INCORRECTO);
        public static final CodefacMsj NO_PERMITE_ELIMINAR = new CodefacMsj(CodefacMsj.TITULO_CORRECTO,"Solo se puede eliminar en modo edición",DialogoCodefac.MENSAJE_INCORRECTO);
    }
    
    public static abstract class Preguntas
    {
        public static final CodefacMsj GUARDADO = new CodefacMsj(CodefacMsj.TITULO_ADVERTENCIA,"Esta seguro que desea eliminar el registro? ",DialogoCodefac.MENSAJE_ADVERTENCIA);
        public static final CodefacMsj ELIMINAR_COMPROBANTE_ELECTRONICO = new CodefacMsj(CodefacMsj.TITULO_ADVERTENCIA,"El comprobante electronico se encuentra autorizada en el SRI , \nPorfavor elimine solo si tambien esta anulado en el SRI\nDesea eliminar la factura de todos modos?" ,DialogoCodefac.MENSAJE_INCORRECTO);
    }
    
    public static abstract class CorreoElectronico {

        public static final CodefacMsj CORREO_ENVIADO = new CodefacMsj(CodefacMsj.TITULO_CORRECTO, "El correo fue enviado correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        
    }
    
    public static abstract class FacturasMensajes
    {
        public static final CodefacMsj ERROR_ELIMINAR_AFECTA_NOTA_CREDITO = new CodefacMsj(CodefacMsj.TITULO_ERROR,"La Factura no se puede eliminar porque hay una nota de credito que le afecta",DialogoCodefac.MENSAJE_INCORRECTO);
    }
    
    public static abstract  class ErrorComunicacion
    {
        public static final CodefacMsj ERROR_COMUNICACION_SERVIDOR = new CodefacMsj(CodefacMsj.TITULO_ERROR,"Error de comunicación con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
    }
    
    public static abstract  class ErrorValidación
    {
        public static final CodefacMsj ERROR_VALIDAR_NUMEROS = new CodefacMsj(CodefacMsj.TITULO_ERROR,"El formato es incorrecto para el ingreso de números",DialogoCodefac.MENSAJE_INCORRECTO);
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
