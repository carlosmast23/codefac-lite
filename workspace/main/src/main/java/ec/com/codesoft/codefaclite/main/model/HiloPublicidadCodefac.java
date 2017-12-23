/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.main.panel.PublicidadCodefacDialogo;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class HiloPublicidadCodefac extends Thread{
    
    /**
     * Valor en segundos que especifica el tiempo de espera
     * antes de mandar la siguiente publicidad
     */
    private static final Long DELEY_PUBLICIDAD=60l*10;
    
    /**
     * Tiempo en segundos que me sirve de contador
     */
    private Long contadorTiempo;
            
    public boolean hiloPublicidad;

    public HiloPublicidadCodefac() {
        this.hiloPublicidad=true;
        this.contadorTiempo=0l;
       
    }
       
    
    @Override
    public void run() {
        try {
            abrirPublicidad();
            while(hiloPublicidad)
            {
                Thread.sleep(1000); //Espera cada segundo
                if(contadorTiempo<DELEY_PUBLICIDAD) //Contador auxiliar para llevar el control en segundos
                {
                    contadorTiempo++;
                    continue ; //Si todavia no llega al tiempo no ejecuta la publicidad
                }
                else
                {
                    contadorTiempo=0l;
                    abrirPublicidad();
                }
                
                                //Thread.sleep(1000*60*15);
                
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloPublicidadCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void abrirPublicidad() {
        //super.run(); //To change body of generated methods, choose Tools | Templates.
        PublicidadCodefacDialogo dialogo = new PublicidadCodefacDialogo(null, true);
        dialogo.setLocationRelativeTo(null);

        dialogo.getLblImagenPublicidad().addMouseListener(listenerMouse);

        dialogo.setVisible(true);
    }

    private MouseListener listenerMouse= new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            Desktop dk = Desktop.getDesktop();
                            dk.browse(new URI("https://www.facebook.com/codefac.ec/"));
                        } catch (IOException ex) {
                            Logger.getLogger(HiloPublicidadCodefac.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (URISyntaxException ex) {
                            Logger.getLogger(HiloPublicidadCodefac.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {}

                    @Override
                    public void mouseReleased(MouseEvent e) {}

                    @Override
                    public void mouseEntered(MouseEvent e) {}

                    @Override
                    public void mouseExited(MouseEvent e) {}
                };
    
}
