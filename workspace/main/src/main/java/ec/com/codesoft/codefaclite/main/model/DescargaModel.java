/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.main.other.ArchivoDescarga;
import ec.com.codesoft.codefaclite.main.panel.DescargaDialog;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Carlos
 */
public class DescargaModel extends DescargaDialog implements Runnable{
    
    /**
     * Map que va a contener como clave el nombre de archivo , y como valor la url del archivo a descargar
     * @return 
     */
    private List<ArchivoDescarga> archivosDescargar;
    
    private DefaultListModel<String> modeloLista;
    
    private Boolean descargaCompleta;


    public DescargaModel(List<ArchivoDescarga> archivosDescargar) {
        super(null,true);
        this.archivosDescargar=archivosDescargar;
        setLocationRelativeTo(null);
        cargarListaArchivos();
        descargaCompleta=false;
    }
    
    
    private void cargarListaArchivos()
    {
        modeloLista=new DefaultListModel<String>();
        for (ArchivoDescarga archivoDescarga : archivosDescargar) {
            String nombreArchivo = archivoDescarga.nombreArchivo;
            modeloLista.addElement(nombreArchivo); 
        }
        getLstArchivosPendientes().setModel(modeloLista);
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
        for (ArchivoDescarga archivoDescarga : archivosDescargar) {
            String name = archivoDescarga.nombreArchivo;  //nombre del archivo destino
            String url = archivoDescarga.url; //dirección url del recurso a descargar
            
            
            try {

                //Directorio destino para las descargas
                String folder = archivoDescarga.destino + "/";

                //Crea el directorio de destino en caso de que no exista
                File dir = new File(folder);

                if (!dir.exists()) {
                    if (!dir.mkdir()) {
                        return; // no se pudo crear la carpeta de destino
                    }
                }

                File file = new File(folder + name+".tmp");//Cuando empiece la descarga esta con punto tmp si se cancela y no termina de descargar

                URLConnection conn = new URL(url).openConnection();
                conn.connect();
                int tamanioTotal = conn.getContentLength();

                BigDecimal tamanioArchivo = new BigDecimal((float) tamanioTotal / (float) (1024));

                getLblNombreArchivo().setText(name);
                getLblTamanio().setText(tamanioArchivo.setScale(2, BigDecimal.ROUND_HALF_UP) + " kb");
                getLblSitio().setText(url);
                getBarraProgreso().setValue(0); //Empiezo una nueva descarga y seteo el valor del porcentaje en 0

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

                    if (i % 10000 == 0) {
                        BigDecimal tamanio = new BigDecimal((float) size / (float) (1024));
                        //System.out.println(tamanio.setScale(3, RoundingMode.HALF_UP) + " kb");
                        int porcentaje = (int) ((float) size * 100 / (float) tamanioTotal);
                        getBarraProgreso().setValue(porcentaje);
                    }
                }

                out.close();
                in.close();
                UtilidadesArchivos.renombrarArchivo(file,name); //Nombre final sin la extension tmp
            } catch (IOException ex) {
                Logger.getLogger(DescargaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            modeloLista.removeElement(name);
        }
        descargaCompleta=true;
        
    }

    @Override
    public void run() {
        empezarDescargaHilo();
        dispose();
    }

    public Boolean getDescargaCompleta() {
        return descargaCompleta;
    }
    
    
    
}
