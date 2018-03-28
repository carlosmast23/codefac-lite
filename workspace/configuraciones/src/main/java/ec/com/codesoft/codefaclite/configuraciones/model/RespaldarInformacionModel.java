/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.RespaldarInformacionPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class RespaldarInformacionModel extends RespaldarInformacionPanel
{
    private Path origenPath;
    private Path destinoPath;
    private JFileChooser jFileChooser;
    private String ubicacionRespaldo;
    private String nombreCarpetaRelpaldo;
    private ParametroCodefacServiceIf parametroCodefacServiceIf;
    private Map<String, ParametroCodefac> parametro;
    private Boolean b = false;
           
    @Override
    public void iniciar() throws ExcepcionCodefacLite 
    {
        this.parametroCodefacServiceIf = ServiceFactory.getFactory().getParametroCodefacServiceIf();
        this.ubicacionRespaldo = "";
        this.jFileChooser = new JFileChooser();
        this.jFileChooser.setDialogTitle("Elegir ubicación para respaldar información");
        this.jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
        agregarListener();
        obtenerUbicacionCarpetaRespaldo();

    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
            /**
             * Grabar la ubicación de respaldos de la información
             */
            Boolean respuesta = null;
            try{
                if(!getTxtUbicacionRespaldo().getText().equals(""))
                {
                    ParametroCodefac p = this.parametro.get(ParametroCodefac.DIRECTORIO_RESPALDO);
                    p.setValor(this.ubicacionRespaldo+"");
                    if(b){
                        respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea cambiar el lugar de respaldos?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        if (!respuesta) {
                            throw new ExcepcionCodefacLite("Cancelacion usuario");
                        }
                    }
                    else
                    {
                        this.parametroCodefacServiceIf.editarParametros(this.parametro);
                    }
                    if(respuesta)
                    {
                        this.parametroCodefacServiceIf.editarParametros(this.parametro);
                    }
                    
                        
                    
                    DialogoCodefac.mensaje("Actualizado datos", "Los datos de los parametros fueron actualizados", DialogoCodefac.MENSAJE_CORRECTO);
                    dispose();
                }
                else
                {
                    DialogoCodefac.mensaje("Advertencia", "Necesita establecer una ruta para los respaldos", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
            }
            catch(RemoteException exc)
            {
                System.out.println("Error guardando parametro de path respaldo");
            }
    } 
    

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        return "Respaldar Información";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     private void copiarDirectorio(File origen, File destino) throws IOException 
    {
        if (!destino.exists()) 
        {
            destino.mkdir();
        }
        for (String f : origen.list())
        {
            copiar(new File(origen, f), new File(destino, f));
        }
    }
    
    public void copiar(File origenLocalizacion, File destinoLocalizacion) throws IOException 
    {
        if (origenLocalizacion.isDirectory())
        {
            copiarDirectorio(origenLocalizacion, destinoLocalizacion);
        } 
        else 
        {
            copiarArchivo(origenLocalizacion, destinoLocalizacion);
        }
    }

    private void copiarArchivo(File source, File target) throws IOException 
    {        
        try 
        (
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(target)
        ) 
        {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) 
            {
                out.write(buf, 0, length);
            }
        }
    }
    
    public static void copiarArchivoXml(String pathOrigen, String pathDestino) {
        try {
            Path origenPath = FileSystems.getDefault().getPath(pathOrigen);
            Path destinoPath = FileSystems.getDefault().getPath(pathDestino);
            
            File file = destinoPath.toFile();
            //crear toda la ruta si no existe
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                //file.mkdir();
            }    
            Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            
        }
    }
    
    public void agregarListener()
    {
        getBtnRespaldar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {
                    if(!ubicacionRespaldo.equals(""))
                    {
                        crearNombreCarpetaRespaldo();
                        origenPath = FileSystems.getDefault().getPath("DB");   
                        destinoPath = FileSystems.getDefault().getPath(ubicacionRespaldo+"\\"+nombreCarpetaRelpaldo);
                        File recursosDirectorio = origenPath.toFile();
                        File destinoDirectorio = destinoPath.toFile();
                        copiarDirectorio(recursosDirectorio, destinoDirectorio);
                    }
                    else
                    {
                        DialogoCodefac.mensaje("Advertencia", "Debe seleccionar una ubicación para los respaldos", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }
                }catch(Exception exc)
                {
                    System.out.println("Error al respaldar información: " + exc);
                }
            }    
        });
        
        getBtnGuardarLocalizacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccion = jFileChooser.showDialog(null, "Seleccionar");
                switch (seleccion) {
                    case JFileChooser.APPROVE_OPTION:
                        ubicacionRespaldo = "" + jFileChooser.getSelectedFile();
                        getTxtUbicacionRespaldo().setText(""+ubicacionRespaldo);
                        DialogoCodefac.mensaje("Advertencia", "Debe guardar la nueva ubicación para que se actualice el lugar de respaldo", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        break;
                    case JFileChooser.CANCEL_OPTION:

                        break;
                    case JFileChooser.ERROR_OPTION:

                        break;
                }
            }
        });
    }
    
    public void crearNombreCarpetaRespaldo()
    {
        this.nombreCarpetaRelpaldo = "" + new Date();
        this.nombreCarpetaRelpaldo = this.nombreCarpetaRelpaldo.replaceAll(" ","");
        this.nombreCarpetaRelpaldo = this.nombreCarpetaRelpaldo.replaceAll(":","");
    }
    
    public void obtenerUbicacionCarpetaRespaldo()
    {
        try {
            this.parametro = this.parametroCodefacServiceIf.getParametrosMap();
            ParametroCodefac p = this.parametro.get(ParametroCodefac.DIRECTORIO_RESPALDO);
            if(p.getValor() != null)
            {
                this.ubicacionRespaldo = p.getValor();
                getTxtUbicacionRespaldo().setText(p.getValor());
                this.b = true;
            }
            else
            {
                getTxtUbicacionRespaldo().setText("");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(RespaldarInformacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
