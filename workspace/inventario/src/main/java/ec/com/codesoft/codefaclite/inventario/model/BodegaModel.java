package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.inventario.busqueda.BodegaBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.panel.BodegaPanel;
import ec.com.codesoft.codefaclite.servidor.entity.Bodega;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.BodegaEnumEstado;
import ec.com.codesoft.codefaclite.servidor.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.service.BodegaService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BodegaModel extends BodegaPanel implements DialogInterfacePanel<Bodega> {

    private Bodega bodega;
    private BodegaService bodegaService;
    private JFileChooser jFileChooser;
    private Path origen = null;
    private Path destino = null;

    public BodegaModel() {
        bodegaService = new BodegaService();

        jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Elegir archivo");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Foto", "png", "jpg", "bmp"));
        agregarListenerBotones();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            bodega = new Bodega();
            setearValoresBodega(bodega);
            bodegaService.grabar(bodega);
            DialogoCodefac.mensaje("Datos correctos", "El Producto se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar");
        }
    }

    private void setearValoresBodega(Bodega bodega) {
        bodega.setEstado(BodegaEnumEstado.ACTIVO.getEstado());
        bodega.setNombre(getTxtNombre().getText());
        bodega.setDescripcion(getTxtDescripcion().getText());
        bodega.setEncargado(getTxtEncargado().getText());
        bodega.setImagenPath(getTxtFoto().getText());
        
        moverArchivo();
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        setearValoresBodega(bodega);
        bodegaService.editar(bodega);
        DialogoCodefac.mensaje("Datos correctos", "La bodega se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if (estadoFormulario.equals(GeneralPanelInterface.ESTADO_EDITAR)) {
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea eliminar la bodega?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (!respuesta) {
                throw new ExcepcionCodefacLite("Cancelacion bodega");
            }
            bodegaService.eliminar(bodega);
            DialogoCodefac.mensaje("Datos correctos", "La bodega se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        }
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
        BodegaBusquedaDialogo bodegaBusquedaDialogo = new BodegaBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(bodegaBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        bodega = (Bodega) buscarDialogoModel.getResultado();

        if (bodega == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar bodega vacio");
        }

        getTxtNombre().setText(bodega.getNombre());
        getTxtDescripcion().setText(bodega.getDescripcion());
        getTxtEncargado().setText(bodega.getEncargado());
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Bodega getResult() {
        try {
            grabar();
            return bodega;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(BodegaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cargarDatosArchivos(File archivoEscogido) {
        File archivo = archivoEscogido;
        String rutaArchivo = archivo.getPath();
        String nombreArchivo = archivo.getName();
        getTxtFoto().setText(nombreArchivo);
        String rutaDestino = session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).valor + "/" + DirectorioCodefac.IMAGENES.getNombre() + "/";
        rutaDestino += nombreArchivo;
        establecerDondeMoverArchivo(rutaArchivo, rutaDestino);
    }

    public void establecerDondeMoverArchivo(String rutaArchivo, String rutaDestino) {
        this.origen = FileSystems.getDefault().getPath(rutaArchivo);
        this.destino = FileSystems.getDefault().getPath(rutaDestino);
    }

    private void agregarListenerBotones() {
        getBtnCargarImagen().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccion = jFileChooser.showDialog(null, "Abrir");
                switch (seleccion) {
                    case JFileChooser.APPROVE_OPTION:
                        cargarDatosArchivos(jFileChooser.getSelectedFile());
                        break;
                    case JFileChooser.CANCEL_OPTION:

                        break;
                    case JFileChooser.ERROR_OPTION:

                        break;
                }
            }
        });

    }

    public void moverArchivo() {
        //Verifica que solo cuando exista un origen y destino exista se copien los datos
        if (origen == null || destino == null) {
            return;
        }
        File file = destino.toFile();
        //crear toda la ruta si no existe
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            //file.mkdir();
        }
        try {
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            getTxtFoto().setText("" + destino.getFileName());
        } catch (IOException ex) {
            ex.printStackTrace();
            DialogoCodefac.mensaje("Firma", "Problema en guardar firma", DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }

}
