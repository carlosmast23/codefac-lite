/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.mensajes;

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

    public CodefacMsj(String titulo, String mensaje, int modoMensaje) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.modoMensaje = modoMensaje;
    }
}
