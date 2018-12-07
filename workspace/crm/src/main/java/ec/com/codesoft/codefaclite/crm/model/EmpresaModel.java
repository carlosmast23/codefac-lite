/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.validacionPersonalizadaAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.panel.EmpresaForm;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpresaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesJuridicas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author PC
 */
public class EmpresaModel extends EmpresaForm
{
    private Empresa empresa;
    private EmpresaServiceIf empresaService;
    private JFileChooser jFileChooser;
    private Path origen = null;
    //private Path destino = null;


    public EmpresaModel() 
    {
        jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Elegir archivo");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Logo Imagen", "png", "jpg", "bmp"));   
        //this.empresa = new Empresa();
        this.empresaService = ServiceFactory.getFactory().getEmpresaServiceIf();
        agregarListener();
         /**
         * Desactivo el ciclo de vida para controlar manualmente
         */
        super.cicloVida = false;
        
        //Todo: Por el momento oculto estos 2 campos porque se los va a coger de la sucursal matriz
        getLblCelular().setVisible(false); 
        getTxtCelular().setVisible(false);
 
    }
    
    public Empresa obtenerDatosEmpresa()
    {
       try{
           for(Empresa e: empresaService.buscar())
           {
                return e;
           }
       }
       catch(Exception e)
       {
           System.out.println("No se encontro datos en emisor");
       }
       return null;
    }
    
    public void cargarDatosEmpresa()
    {
        Empresa e = obtenerDatosEmpresa();
        if(e != null)
        {
            getjTextRuc().setText(e.getIdentificacion());
            getjTextNombreSocial().setText(e.getRazonSocial());
            getjTextNombreComercial().setText(e.getNombreLegal());
            //getTxtDireccion().setText(e.getDireccion());
            //getjTextTelefono().setText(e.getTelefonos());
            getTxtCelular().setText(e.getCelular());
            getTxtFacebook().setText(e.getFacebook());
            getTxtAdicional().setText(e.getAdicional());
            getjTextNumContribuyente().setText(e.getContribuyenteEspecial());
            
            if(e.getObligadoLlevarContabilidad().equals(Empresa.SI_LLEVA_CONTABILIDAD))
            {
                getjCheckBLlevaContabilidad().setSelected(true);
            }else
            {
                getjCheckBLlevaContabilidad().setSelected(false);
            }
            
            getjTextLogo().setText(e.getImagenLogoPath());
            
        }  
    }
    
    @Override
    public void grabar() throws ExcepcionCodefacLite 
    {
        if(session.getEmpresa()==null)
        {
            try {
                empresa=empresaService.grabar(setDatosEmisor());
                session.setEmpresa(empresa);
                moverArchivo();
                DialogoCodefac.mensaje("Exito","Empresa grabada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            try {
                empresaService.editar(setDatosEmisor());
                session.setEmpresa(empresa);
                moverArchivo();
                DialogoCodefac.mensaje("Exito","Empresa editada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            } catch (RemoteException ex) {
                Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dispose();
    }

    @Override
    public void editar() throws ExcepcionCodefacLite 
    {
        try {
            empresaService.editar(setDatosEmisor());
        } catch (RemoteException ex) {
            Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() {
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

//    @Override
    public String getNombre() {
        return "Empresa";
    }

    @Override
    public String getURLAyuda() 
    {
        return "http://www.cf.codesoft-ec.com/ayuda#eemisor";
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() 
    {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }
    
    public Empresa setDatosEmisor()
    {
        //empresa = new Empresa();
        //empresa.setTelefonos(getjTextTelefono().getText());
        empresa.setRazonSocial(getjTextNombreSocial().getText());
        empresa.setNombreLegal(getjTextNombreComercial().getText());
        //empresa.setDireccion(getTxtDireccion().getText());
        empresa.setIdentificacion(getjTextRuc().getText());
        empresa.setContribuyenteEspecial(getjTextNumContribuyente().getText());
        empresa.setCelular(getTxtCelular().getText());
        empresa.setFacebook(getTxtFacebook().getText());
        empresa.setAdicional(getTxtAdicional().getText());
        
        if(getjCheckBLlevaContabilidad().isSelected())
        {
            empresa.setObligadoLlevarContabilidad(Empresa.SI_LLEVA_CONTABILIDAD);
        }else
        {
            empresa.setObligadoLlevarContabilidad(Empresa.NO_LLEVA_CONTABILIDAD);
        }
        empresa.setImagenLogoPath(getjTextLogo().getText());
        //empresa.setContribuyenteEspecial("");
        return empresa;
    }

    @Override
    public void iniciar() {
        if(session.getEmpresa()!=null)
        {
            empresa=session.getEmpresa();
            cargarDatosEmpresa();
        }
        else
        {
            this.empresa=new Empresa();
        }
        
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void agregarListener() {
        /*
        getTxtDireccion().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                String texto=getTxtDireccion().getText();
                String textoNuevo=UtilidadesTextos.llenarCarateresIzquierda(texto,3,"0");
                getTxtDireccion().setText(textoNuevo);
           }
        });*/
        
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
    
    public void cargarDatosArchivos(File archivoEscogido) {
        File archivo = archivoEscogido;
        String rutaArchivo = archivo.getPath();
        String nombreArchivo = archivo.getName();
        getjTextLogo().setText(nombreArchivo);
        origen=archivoEscogido.toPath();
        //TODO:Cambiar la copia de archivos por un servicio de transferencia de archivos
        //String rutaDestino ="";
        //String rutaDestino = session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).valor + "/" + DirectorioCodefac.IMAGENES.getNombre() + "/";
        //rutaDestino += nombreArchivo;
        //establecerDondeMoverArchivo(rutaArchivo, rutaDestino);
    }

    
    @validacionPersonalizadaAnotacion(errorTitulo = "Formato de ruc")
    public boolean validarRuc()
    {
        return UtilidadesJuridicas.validarTodosRuc(getjTextRuc().getText());
    }
    
    public void moverArchivo() {
        try {
            //Verifica que solo cuando exista un origen y destino exista se copien los datos
            if (origen == null) {
                return;
            }
            
            SimpleRemoteInputStream istream = new SimpleRemoteInputStream(
                    new FileInputStream(origen.toFile()));
            
            ServiceFactory.getFactory().getRecursosServiceIf().uploadFileServer(DirectorioCodefac.IMAGENES, istream,origen.getFileName().toString());
            
            getjTextLogo().setText(origen.getFileName().toString());
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean salirSinGrabar() {
        return true ;  //Esta pantalla necesito que siempre salga sin preguntar
    }

    

    
    
}
