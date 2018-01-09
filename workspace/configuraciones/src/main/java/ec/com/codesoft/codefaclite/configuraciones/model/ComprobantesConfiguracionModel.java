/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.ComprobantesConfiguracionPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.dialog.ProcesoSegundoPlano;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.FirmaElectronica;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidor.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidor.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Perfil;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoService;
import ec.com.codesoft.codefaclite.servidor.service.ParametroCodefacService;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.ejemplo.utilidades.email.CorreoElectronico;
import ec.com.codesoft.ejemplo.utilidades.email.SmtpNoExisteException;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Carlos
 */
public class ComprobantesConfiguracionModel extends ComprobantesConfiguracionPanel {

    private Map<String, ParametroCodefac> parametros;
    private ParametroCodefacService parametroCodefacService;
    private ImpuestoDetalleService impuestoDetalleService;
    private JFileChooser jFileChooser;
    private Path origen = null;
    private Path destino = null;
    private Persona cliente;
    private PersonaService clienteService;

    public ComprobantesConfiguracionModel() {
        impuestoDetalleService = new ImpuestoDetalleService();
        this.parametroCodefacService = new ParametroCodefacService();
        cargarDatosIva();
        cargarModosFacturacion();
        cargarDatosConfiguraciones();
        jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Elegir archivo");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Firma Electronica SRI", "p12"));
        this.addListenerButtons();
        /**
         * Desactivo el ciclo de vida para controlar manualmente
         */
        super.cicloVida = false;

    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        actualizarDatosVista();
        moverArchivo();
        this.parametroCodefacService.editarParametros(parametros);
        
        /**
         * Establesco el ciclo de vida en el cual me encuentro
         */
        this.estadoFormulario = GeneralPanelInterface.ESTADO_GRABAR;
        DialogoCodefac.mensaje("Actualizado datos", "Los datos de los parametros fueron actualizados", DialogoCodefac.MENSAJE_CORRECTO);
        //DialogoCodefac.mensaje("Firma", "Datos actualizados correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        dispose();
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
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
        super.estadoFormulario = GeneralPanelInterface.ESTADO_EDITAR;
        
        //Validaciones adicionales para validar segun el tipo de usuario Logueado
        if(!session.verificarExistePerfil(Perfil.PERFIl_ADMINISTRADOR))
        {
            getCmbModoFacturacion().setEnabled(false);
            getCmbIvaDefault().setEnabled(false);
        }
    }

    @Override
    public String getNombre() {
        return "Configuración de comprobantes";
    }

    @Override
    public String getURLAyuda() {
        return "http://www.cf.codesoft-ec.com/ayuda#ecomprobantes";
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    private void actualizarDatosVista() {
        parametros.get(ParametroCodefac.SECUENCIAL_FACTURA).setValor(getTxtFacturaSecuencial().getText());
        parametros.get(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).setValor(getTxtNotaCreditoSecuencial().getText());
        parametros.get(ParametroCodefac.SECUENCIAL_NOTA_DEBITO).setValor(getTxtNotaDebitoSecuencial().getText());
        parametros.get(ParametroCodefac.SECUENCIAL_GUIA_REMISION).setValor(getTxtGuiaRemisionSecuencial().getText());
        parametros.get(ParametroCodefac.SECUENCIAL_RETENCION).setValor(getTxtRetencionesSecuencial().getText());
        parametros.get(ParametroCodefac.ESTABLECIMIENTO).setValor(getTxtEstablecimiento().getText());
        parametros.get(ParametroCodefac.PUNTO_EMISION).setValor(getTxtPuntoEmision().getText());
        String ivaDefacto = ((ImpuestoDetalle) getCmbIvaDefault().getSelectedItem()).getTarifa().toString();
        parametros.get(ParametroCodefac.IVA_DEFECTO).setValor(ivaDefacto);
        parametros.get(ParametroCodefac.CORREO_USUARIO).setValor(getTxtCorreoElectronico().getText());
        parametros.get(ParametroCodefac.CORREO_CLAVE).setValor(new String(getTxtPasswordCorreo().getPassword()));
        
        parametros.get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).setValor(new String(getTxtClaveFirma().getPassword()));
        
        parametros.get(ParametroCodefac.MODO_FACTURACION).setValor(getCmbModoFacturacion().getSelectedItem().toString());
        //verificarFirmaElectronica();
    }



    private void cargarDatosConfiguraciones() {
        parametros = parametroCodefacService.getParametrosMap();
        ParametroCodefac param = parametros.get(ParametroCodefac.SECUENCIAL_FACTURA);
        getTxtFacturaSecuencial().setText(parametros.get(ParametroCodefac.SECUENCIAL_FACTURA).getValor());
        getTxtGuiaRemisionSecuencial().setText(parametros.get(ParametroCodefac.SECUENCIAL_GUIA_REMISION).getValor());
        getTxtNotaCreditoSecuencial().setText(parametros.get(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).getValor());
        getTxtNotaDebitoSecuencial().setText(parametros.get(ParametroCodefac.SECUENCIAL_NOTA_DEBITO).getValor());
        getTxtGuiaRemisionSecuencial().setText(parametros.get(ParametroCodefac.SECUENCIAL_GUIA_REMISION).getValor());
        getTxtRetencionesSecuencial().setText(parametros.get(ParametroCodefac.SECUENCIAL_RETENCION).getValor());
        getTxtDirectorioRecurso().setText(parametros.get(ParametroCodefac.DIRECTORIO_RECURSOS).getValor());
        getTxtEstablecimiento().setText(parametros.get(ParametroCodefac.ESTABLECIMIENTO).getValor());
        getTxtPuntoEmision().setText(parametros.get(ParametroCodefac.PUNTO_EMISION).getValor());
        getTxtCorreoElectronico().setText(parametros.get(ParametroCodefac.CORREO_USUARIO).getValor());
        getTxtPasswordCorreo().setText(parametros.get(ParametroCodefac.CORREO_CLAVE).getValor());
        getTxtNombreFirma().setText(parametros.get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).getValor());
        getTxtClaveFirma().setText(parametros.get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).getValor());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tarifa", Integer.parseInt(parametros.get(ParametroCodefac.IVA_DEFECTO).getValor()));
        List<ImpuestoDetalle> lista = impuestoDetalleService.buscarImpuestoDetallePorMap(map);
        getCmbIvaDefault().getModel().setSelectedItem(lista.get(0));
        /**
         * Cargar el modo de facturacion por defecto
         */
        String modoProduccion=parametros.get(ParametroCodefac.MODO_FACTURACION).getValor();
        getCmbModoFacturacion().setSelectedItem(modoProduccion);
        

    }

    private void cargarDatosIva() {
        ImpuestoService impuestoService = new ImpuestoService();
        Impuesto iva = impuestoService.obtenerImpuestoPorCodigo(Impuesto.IVA);
        for (ImpuestoDetalle impuesto : iva.getDetalleImpuestos()) {
            getCmbIvaDefault().addItem(impuesto);
        }
    }
    
    private void cargarModosFacturacion() {
        getCmbModoFacturacion().addItem(ComprobanteElectronicoService.MODO_PRODUCCION);
        getCmbModoFacturacion().addItem(ComprobanteElectronicoService.MODO_PRUEBAS);
    }


    public void cargarDatosArchivos(File archivoEscogido) {
        File archivo = archivoEscogido;
        String rutaArchivo = archivo.getPath();
        String nombreArchivo = archivo.getName();
        getTxtNombreFirma().setText(nombreArchivo);
        String rutaDestino = session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).valor + "/" + ComprobanteElectronicoService.CARPETA_CONFIGURACION + "/";
        rutaDestino += nombreArchivo;
        establecerDondeMoverArchivo(rutaArchivo, rutaDestino);
    }

    public void establecerDondeMoverArchivo(String rutaArchivo, String rutaDestino) {
        this.origen = FileSystems.getDefault().getPath(rutaArchivo);
        this.destino = FileSystems.getDefault().getPath(rutaDestino);
    }

    public void moverArchivo() {
        //Verifica que solo cuando exista un origen y destino exista se copien los datos
        if (origen == null || destino == null) {
            return;
        }
        
        File file=destino.toFile();
        //crear toda la ruta si no existe
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            //file.mkdir();
        }

        try {
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            getTxtNombreFirma().setText("" + destino.getFileName());
            ParametroCodefac parametro=parametros.get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA);
            parametro.setValor(destino.getFileName().toString());
        } catch (IOException ex) {
            ex.printStackTrace();
            DialogoCodefac.mensaje("Firma", "Problema en guardar firma", DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }

    private void addListenerButtons() {
        getBtnFirmaElectronica().addActionListener(new ActionListener() {
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
        
        getTxtClaveFirma().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
              //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void focusLost(FocusEvent e) {
                verificarFirmaElectronica();
            }
        });
        
        getTxtPasswordCorreo().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void focusLost(FocusEvent e) {
                //No validar cuando el password esta vacio
                if((new String(getTxtPasswordCorreo().getPassword())).length()==0)
                return ;
        
                DialogoCodefac.mostrarDialogoCargando(new ProcesoSegundoPlano() {
                    @Override
                    public void procesar() {
                        verificarCredencialesCorreo();
                    }

                    @Override
                    public String getMensaje() {
                        return "Validando Correo";
                    }
                });
                
            }
        });
    }
    
    private void verificarCredencialesCorreo()
    {

        
        try {
            List<String> correos=new ArrayList<String>();
            correos.add(getTxtCorreoElectronico().getText());
            CorreoElectronico correoElectronico=new CorreoElectronico(getTxtCorreoElectronico().getText(),new String(getTxtPasswordCorreo().getPassword()),"Este correo es de prueba para configurar el sistema de Codefac",correos, "Prueba Codefac");
            correoElectronico.sendMail();
            configurarCorreoDeConsumidorFinal();
            //DialogoCodefac.mensaje("Exito","El correo y la clave son correctos",DialogoCodefac.MENSAJE_CORRECTO);
        }catch (AuthenticationFailedException ex) {
            System.out.println("Fallo al autentificar el usuario");
            getTxtPasswordCorreo().setText("");
            DialogoCodefac.mensaje("Error Correo","Las credenciales de su correo son incorrectas",DialogoCodefac.MENSAJE_INCORRECTO);
                        
        } catch (MessagingException ex) {
            Logger.getLogger(ComprobantesConfiguracionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SmtpNoExisteException ex) {
            System.out.println("Fallo al autentificar el usuario");
            getTxtPasswordCorreo().setText("");
            DialogoCodefac.mensaje("Error Correo","Ingrese un correo valido",DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }
    
    private void verificarFirmaElectronica()
    {
        String claveFirma=new String(getTxtClaveFirma().getPassword());
        String nombreArchivo=getTxtNombreFirma().getText();
        String rutaDestino = session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).valor + "/" + ComprobanteElectronicoService.CARPETA_CONFIGURACION + "/";
        
        String pathFirma="";
        
        if(origen!=null)
        {
            pathFirma=origen.toString();
        }
        else
        {
            pathFirma=rutaDestino+nombreArchivo;
        }
        
        if(!claveFirma.equals("") && !pathFirma.equals(""))
        {
            if(!FirmaElectronica.FirmaVerificar(pathFirma,claveFirma))
            {
                DialogoCodefac.mensaje("Error Clave","La Clave de la firma es incorrecta",DialogoCodefac.MENSAJE_INCORRECTO);
                getTxtClaveFirma().setText("");
            }
        }    


    }

    @Override
    public void iniciar() {
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        List<String> permisosPerfil=new ArrayList<String>();
        permisosPerfil.add(Perfil.PERFIl_OPERADOR);
        permisosPerfil.add(Perfil.PERFIl_ADMINISTRADOR);
        return permisosPerfil;
    }

    public void configurarCorreoDeConsumidorFinal()
    {
        clienteService = new PersonaService();
        for(Persona c :clienteService.buscar())
        {
            if(c.getRazonSocial().equals("Cliente Final")){
                cliente = c;
            }
        }
        cliente.setCorreoElectronico(getTxtCorreoElectronico().getText());
        clienteService.editar(cliente);
    }
}
