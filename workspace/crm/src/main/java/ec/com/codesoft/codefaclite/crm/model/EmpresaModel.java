/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpresaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.validacionPersonalizadaAnotacion;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.utilidades.UtilidadesImagenesCodefac;
import ec.com.codesoft.codefaclite.crm.panel.EmpresaForm;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpresaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.utilidades.archivos.UtilidadesDirectorios;
import ec.com.codesoft.codefaclite.utilidades.imagen.UtilidadImagen;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesJuridicas;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwing;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author PC
 */
public class EmpresaModel extends EmpresaForm
{
    private Empresa empresa;
    private EmpresaServiceIf empresaService;
    //private JFileChooser jFileChooser;
    private Path origen = null;
    private Path logoPequenioPath = null;
    //private Path destino = null;


    public EmpresaModel() 
    {
        
        //this.empresa = new Empresa();
        this.empresaService = ServiceFactory.getFactory().getEmpresaServiceIf();
        agregarListener();
         /**
         * Desactivo el ciclo de vida para controlar manualmente
         */
        //super.cicloVida = false;
        
        //Todo: Por el momento oculto estos 2 campos porque se los va a coger de la sucursal matriz
        getLblCelular().setVisible(false); 
        getTxtCelular().setVisible(false);
        
        //getjComboEstado().setSelectedItem(this.empresa.getEstadoEnum().);
        getjComboEstado().removeAllItems();
        for (GeneralEnumEstado enumerador : GeneralEnumEstado.values()) {
            getjComboEstado().addItem(enumerador);
        }
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
            //getTxtCelular().setText(e.getCelular());
            getTxtFacebook().setText(e.getFacebook());
            getTxtInstagram().setText(e.getInstagram());
            getTxtAdicional().setText(e.getAdicional());
            getjTextNumContribuyente().setText(e.getContribuyenteEspecial());
            getTxtAgenteRetencionResolucion().setText(e.getAgenteRetencionResolucion());
            
            if(e.getObligadoLlevarContabilidad().equals(Empresa.SI_LLEVA_CONTABILIDAD))
            {
                getjCheckBLlevaContabilidad().setSelected(true);
            }else
            {
                getjCheckBLlevaContabilidad().setSelected(false);
            }
            
            getjTextLogo().setText(e.getImagenLogoPath());
            getjTextLogoPequeño().setText(e.getImagenLogoPequenioPath());
            
            if(e.getOrden()!=null)
            {
                getTxtOrden().setValue(e.getOrden());
            }
            
        }  
    }
    
    @Override
    public void grabar() throws ExcepcionCodefacLite 
    {
        //if(session.getEmpresa()==null)
        //{
            try {
                empresa=empresaService.grabar(setDatosEmisor());
                //session.setEmpresa(empresa);
                moverArchivo();

                DialogoCodefac.mensaje("Exito","Empresa grabada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                throw  new ExcepcionCodefacLite(ex.getMessage());
            } catch (Exception ex) {
                Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        /*}
        else
        {
            try {
                empresaService.editar(setDatosEmisor());
                session.setEmpresa(empresa);
                moverArchivo();
                DialogoCodefac.mensaje("Exito","Empresa editada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            } catch (Exception ex) {
                Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dispose();*/
    }
    
    private void moverArchivo()
    {
        grabarArchivoServidor(origen);
        grabarArchivoServidor(logoPequenioPath);
    }

    @Override
    public void editar() throws ExcepcionCodefacLite 
    {
        try {
            
            empresaService.editar(setDatosEmisor());
            moverArchivo();
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
            
            //Si estoy editando la misma empresa que tengo la seccion actualizo la referencia
            if(session.getEmpresa().getId().equals(empresa.getId()))
            {
                session.setEmpresa(empresa);
            }
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() 
    {
        if(DialogoCodefac.dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO))
        {
            try {
                ServiceFactory.getFactory().getEmpresaServiceIf().eliminar(empresa);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
            } catch (Exception ex) {
                Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje(ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);                
            }
        }
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
        getjTextLogo().setText("");    
        getjTextLogoPequeño().setText("");
        getTxtOrden().setValue(0);
        getChkContribuyenteRegimenMicroempresas().setSelected(false);
        getChkRIMPEEmprendedores().setSelected(false);
        getChkRIMPENegociosPopulares().setSelected(false);
        getjCheckBLlevaContabilidad().setSelected(false);
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
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_REFRESCAR, true);
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
        empresa.setAgenteRetencionResolucion(getTxtAgenteRetencionResolucion().getText());
        //empresa.setCelular(getTxtCelular().getText());
        empresa.setFacebook(getTxtFacebook().getText());
        empresa.setInstagram(getTxtInstagram().getText());
        empresa.setAdicional(getTxtAdicional().getText());
        empresa.setCodigo(getjTextCodigoEmpresa().getText());
        empresa.setOrden((Integer) getTxtOrden().getValue());
        
        if(getjCheckBLlevaContabilidad().isSelected())
        {
            empresa.setObligadoLlevarContabilidad(Empresa.SI_LLEVA_CONTABILIDAD);
        }else
        {
            empresa.setObligadoLlevarContabilidad(Empresa.NO_LLEVA_CONTABILIDAD);
        }
        
        
        if(getChkContribuyenteRegimenMicroempresas().isSelected())
        {
            empresa.setContribuyenteRegimenMicroempresasEnum(EnumSiNo.SI);
        }
        else
        {
            empresa.setContribuyenteRegimenMicroempresasEnum(EnumSiNo.NO);
        }
        
        
        if(getChkRIMPEEmprendedores().isSelected())
        {
            empresa.setRimpeEmprendedoresEnum(EnumSiNo.SI);
        }
        else
        {
            empresa.setRimpeEmprendedoresEnum(EnumSiNo.NO);
        }
        
        if(getChkRIMPENegociosPopulares().isSelected())
        {
            empresa.setRimpeNegociosPopularesEnum(EnumSiNo.SI);
        }
        else
        {
            empresa.setRimpeNegociosPopularesEnum(EnumSiNo.NO);
        }
        
        
        empresa.setImagenLogoPath(getjTextLogo().getText());
        empresa.setImagenLogoPequenioPath(getjTextLogoPequeño().getText());
        //empresa.setContribuyenteEspecial("");
        empresa.setEstado(((GeneralEnumEstado)getjComboEstado().getSelectedItem()).getEstado());
        return empresa;
    }

    @Override
    public void iniciar() {
        this.empresa=new Empresa();
        /*if(session.getEmpresa()!=null)
        {
            empresa=session.getEmpresa();
            cargarDatosEmpresa();
        }
        else
        {
            this.empresa=new Empresa();
        }*/
        
    }

    @Override
    public void nuevo() {
        this.empresa=new Empresa();
        cargarImagenEmpresa(empresa);
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
                File archivo= listenerBotonBuscarImagen(getjTextLogo());
                if(archivo!=null)
                {
                    origen=archivo.toPath();
                }
            }
        });
        
        getBtnCargarImagenPequeña().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File archivo= listenerBotonBuscarImagen(getjTextLogoPequeño());
                if(archivo!=null)
                {
                    logoPequenioPath=archivo.toPath();
                }
            }
        });
    }
    
    private File listenerBotonBuscarImagen(JTextField textField)
    {
        File archivo= UtilidadesDirectorios.buscarArchivo("Elegir archivo",new String[]{"Logo Imagen", "png", "jpg","jpeg","bmp","gif"});
        if(archivo!=null)
        {
            cargarDatosArchivos(archivo,textField);
            return archivo;
        }
        return null;
        /*JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Elegir archivo");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Logo Imagen", "png", "jpg","jpeg","bmp","gif"));   */
        
        /*int seleccion = jFileChooser.showDialog(null, "Abrir");
        switch (seleccion) {
            case JFileChooser.APPROVE_OPTION:
                cargarDatosArchivos(jFileChooser.getSelectedFile());
                break;
            case JFileChooser.CANCEL_OPTION:

                break;
            case JFileChooser.ERROR_OPTION:

                break;
        }*/
    }
    
    public void cargarDatosArchivos(File archivoEscogido,JTextField jtextField) 
    {
        File archivo = archivoEscogido;
        String nombreArchivo = archivo.getName();
        jtextField.setText(nombreArchivo);
        //origen=archivoEscogido.toPath();
    }

    
    @validacionPersonalizadaAnotacion(errorTitulo = "Formato de ruc")
    public boolean validarRuc()
    {
        Boolean respuesta= UtilidadesJuridicas.validarTodosRuc(getjTextRuc().getText());
        //Cuando no valida 
        if(!respuesta)
        {
            if(DialogoCodefac.dialogoPregunta(new CodefacMsj("El RUC ingresado no es valido, desea continuar de todos modos? ", CodefacMsj.TipoMensajeEnum.ADVERTENCIA)))
            {
                return true;
            }
        }
        return respuesta;
    }
    
    public void grabarArchivoServidor(Path origen)
    {
        try {
            //Verifica que solo cuando exista un origen y destino exista se copien los datos
            if (origen == null) {
                return;
            }
            
            SimpleRemoteInputStream istream = new SimpleRemoteInputStream(
                    new FileInputStream(origen.toFile()));
            
            ServiceFactory.getFactory().getRecursosServiceIf().uploadFileServer(DirectorioCodefac.IMAGENES, istream,origen.getFileName().toString(),session.getEmpresa());
            
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*public void moverArchivo() {
        try {
            //Verifica que solo cuando exista un origen y destino exista se copien los datos
            if (origen == null) {
                return;
            }
            
            SimpleRemoteInputStream istream = new SimpleRemoteInputStream(
                    new FileInputStream(origen.toFile()));
            
            ServiceFactory.getFactory().getRecursosServiceIf().uploadFileServer(DirectorioCodefac.IMAGENES, istream,origen.getFileName().toString(),session.getEmpresa());
            
            getjTextLogo().setText(origen.getFileName().toString());
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmpresaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        return new EmpresaBusquedaDialogo();
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        empresa=(Empresa) entidad;
        getjTextRuc().setText(empresa.getIdentificacion());
        getjTextNombreSocial().setText(empresa.getRazonSocial());
        getjTextNombreComercial().setText(empresa.getNombreLegal());
        getjTextNumContribuyente().setText(empresa.getContribuyenteEspecial());
        getTxtAgenteRetencionResolucion().setText(empresa.getAgenteRetencionResolucion());
        getjCheckBLlevaContabilidad().setSelected(empresa.getObligadoLlevarContabilidadEnum().getBool());
        getjTextLogo().setText(empresa.getImagenLogoPath());
        getjTextLogoPequeño().setText(empresa.getImagenLogoPequenioPath());
        getTxtFacebook().setText(empresa.getFacebook());
        getTxtInstagram().setText(empresa.getInstagram());
        getTxtAdicional().setText(empresa.getAdicional());
        getjTextCodigoEmpresa().setText(empresa.getCodigo());
        getChkContribuyenteRegimenMicroempresas().setSelected(empresa.getContribuyenteRegimenMicroempresasEnum().getBool());
        
        getChkRIMPEEmprendedores().setSelected((empresa.getRimpeEmprendedoresEnum())!=null?empresa.getRimpeEmprendedoresEnum().getBool():false);
        getChkRIMPENegociosPopulares().setSelected((empresa.getRimpeNegociosPopularesEnum())!=null?empresa.getRimpeNegociosPopularesEnum().getBool():false);
        
        if (empresa.getOrden() != null) {
            getTxtOrden().setValue(empresa.getOrden());
        }
        cargarImagenEmpresa(empresa);
    }
    
    private void cargarImagenEmpresa(Empresa empresa)
    {
        ImageIcon imageIcon= UtilidadesImagenesCodefac.buscarImagenServidor(empresa, empresa.getImagenLogoPath());
        getLblFoto().setIcon(imageIcon);
    }

    
    
    
}
