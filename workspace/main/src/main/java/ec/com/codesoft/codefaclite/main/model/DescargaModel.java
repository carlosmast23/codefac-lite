/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.main.panel.DescargaDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class DescargaModel extends DescargaDialog implements Runnable{
    
    private String nombreArchivo;
    private String carpeta;
    private String url;

    public DescargaModel(String nombreArchivo, String carpeta, String url) {
        super(null,true);
        this.nombreArchivo = nombreArchivo;
        this.carpeta = carpeta;
        this.url = url;
        setLocationRelativeTo(null);
    }
    
    
    
    public DescargaModel(Frame parent, boolean modal) {
        super(parent, modal);
    }
    
    public void empezarDescarga()
    {
        new Thread(this).start(); //Empezar hilo para comenzar el proceso de descarga
    }
    
    private void empezarDescargaHilo()
    {
        try {
            String url = this.url; //dirección url del recurso a descargar
            String name = nombreArchivo;//nombre del archivo destino

            //Directorio destino para las descargas
            String folder = carpeta+"/";

            //Crea el directorio de destino en caso de que no exista
            File dir = new File(folder);

            if (!dir.exists()) {
                if (!dir.mkdir()) {
                    return; // no se pudo crear la carpeta de destino
                }
            }

            File file = new File(folder + name);

            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            int tamanioTotal = conn.getContentLength();
            
            BigDecimal tamanioArchivo = new BigDecimal((float) tamanioTotal / (float) (1024));
            
            getLblNombreArchivo().setText(name);
            getLblTamanio().setText(tamanioArchivo.setScale(2,BigDecimal.ROUND_HALF_UP)+" kb");
            getLblSitio().setText(url);
            
            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(file);


            long size = 0;
            int b = 0;
            int i = 0;
            while (b != -1) {
                b = in.read();
                i++;
                if (b != -1) {
                    size = out.getChannel().size();
                    out.write(b);
                }

                if (i % 100000 == 0) {
                    BigDecimal tamanio = new BigDecimal((float) size / (float) (1024));
                    //System.out.println(tamanio.setScale(3, RoundingMode.HALF_UP) + " kb");
                    int porcentaje=(int) ((float) size * 100 / (float) tamanioTotal);
                    getBarraProgreso().setValue(porcentaje);
                }
            }

            out.close();
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(DescargaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        empezarDescargaHilo();
        dispose();
    }
    
}
