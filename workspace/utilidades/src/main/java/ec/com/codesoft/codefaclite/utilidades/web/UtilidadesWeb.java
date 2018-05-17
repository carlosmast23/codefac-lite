/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.web;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class UtilidadesWeb {

    public static void abrirPaginaWebNavigador(String url) {
        try {
            Desktop dk = Desktop.getDesktop();
            dk.browse(new URI(url));
        } catch (URISyntaxException ex) {
            Logger.getLogger(UtilidadesWeb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
