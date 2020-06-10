/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.mensajes;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;

/**
 *
 * @author Carlos
 */
public class CodefacMsj {
    
    
    public static final String TITULO_ADVERTENCIA="Advertencia";
    public static final String TITULO_CORRECTO="Correcto";
    public static final String TITULO_ERROR="Error";
    
    public String titulo;
    public String mensaje;
    public int modoMensaje;
    
    public CodefacMsj(String mensaje,TipoMensajeEnum tipoMensajeEnum)
    {
        this.mensaje=mensaje;
        this.titulo=tipoMensajeEnum.getTitulo();
        this.modoMensaje=tipoMensajeEnum.getTipo();
        
    }

    @Deprecated
    public CodefacMsj(String titulo, String mensaje, int modoMensaje) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.modoMensaje = modoMensaje;
    }
    
    public enum TipoMensajeEnum
    {
        CORRECTO(TITULO_CORRECTO,DialogoCodefac.MENSAJE_CORRECTO),
        ADVERTENCIA(TITULO_ADVERTENCIA,DialogoCodefac.MENSAJE_ADVERTENCIA),
        ERROR(TITULO_ERROR,DialogoCodefac.MENSAJE_INCORRECTO);
        
        private TipoMensajeEnum(String titulo,Integer tipo) 
        {
            this.titulo = titulo;
            this.tipo=tipo;
        }
        
        private String titulo;
        private int tipo;

        public String getTitulo() {
            return titulo;
        }

        public int getTipo() {
            return tipo;
        }
        
        
        
        
    }
}
