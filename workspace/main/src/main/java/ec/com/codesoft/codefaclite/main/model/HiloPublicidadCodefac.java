/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.main.panel.PublicidadCodefacDialogo;
import ec.com.codesoft.codefaclite.main.panel.publicidad.Publicidad;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
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
    private List<Publicidad> publicidades;
    
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
        Publicidad publicidad=obtenerPublicidadConProbabilidad();
        dialogo.setLocationRelativeTo(null);
        
        dialogo.getLblImagenPublicidad().setIcon(publicidad.getImage());
        dialogo.getLblImagenPublicidad().setToolTipText(publicidad.getToolTipText());
        dialogo.getLblImagenPublicidad().addMouseListener(new ListenerMousePublicidad(publicidad.getLink()));

        dialogo.setVisible(true);
    }
    /**
     * Obtiene una publicidad al azar segun el valor ponderado de cada publicidad
     */
    private Publicidad obtenerPublicidadConProbabilidad()
    {
        int valorTotal=0;
        for (Publicidad publicidad : publicidades) {
            valorTotal+=publicidad.getProbabilidad();
        }
        
        Random rnd=new Random();
        int valorRandom=rnd.nextInt(valorTotal+1);
        
        int acumulador = 0;
        for (Publicidad publicidad : publicidades) {
            acumulador+=publicidad.getProbabilidad();
            if (valorRandom <= acumulador) 
            {
                return publicidad;
            }
        }
        return null;
    }

    private class ListenerMousePublicidad implements MouseListener
    {
        private String url;

        public ListenerMousePublicidad(String url) {
            this.url = url;
        }
        
        

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                Desktop dk = Desktop.getDesktop();
                dk.browse(new URI(url));
            } catch (IOException ex) {
                Logger.getLogger(HiloPublicidadCodefac.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(HiloPublicidadCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

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

    public void setPublicidades(List<Publicidad> publicidades) {
        this.publicidades = publicidades;
    }
    
    
    
}
