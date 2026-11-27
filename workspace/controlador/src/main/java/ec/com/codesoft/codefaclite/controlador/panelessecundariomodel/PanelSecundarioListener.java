/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.panelessecundariomodel;

/**
 *
 * @author Carlos
 */
public interface PanelSecundarioListener {
    public void mostrar();

    /**
     * Oculta el panel secundario (inverso de mostrar()). Agregado para que un panel secundario
     * (ej. MonitorComprobanteModel) pueda auto-ocultarse solo cuando ya no necesita el espacio en
     * pantalla, sin depender de que el usuario presione el boton del toolbar principal.
     */
    public void ocultar();
}
