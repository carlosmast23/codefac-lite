/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.utilidades;

import javax.faces.application.FacesMessage;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Carlos
 */
public class MensajeMb {

    public static void mostrarMensaje(String titulo,String mensaje,FacesMessage.Severity tipoMensaje) {
        FacesMessage message = new FacesMessage(tipoMensaje,titulo, mensaje);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

}
