/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.ComprobantesConfiguracionPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidor.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidor.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoService;
import ec.com.codesoft.codefaclite.servidor.service.ParametroCodefacService;
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
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Carlos
 */
public class ComprobantesConfiguracionModel extends ComprobantesConfiguracionPanel
{
    private Map<String,ParametroCodefac> parametros ;
    private ParametroCodefacService parametroCodefacService;
    private ImpuestoDetalleService impuestoDetalleService;
    private JFileChooser jFileChooser;
    private Path origen = null;
    private Path destino = null;
    
    public ComprobantesConfiguracionModel() {
        impuestoDetalleService = new ImpuestoDetalleService();
        this.parametroCodefacService=new ParametroCodefacService();
        cargarDatosIva();
        cargarDatosConfiguraciones();
        jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Elegir archivo");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Firma Electronica SRI", "p12"));
        this.addListenerButtons();
        /**
         * Desactivo el ciclo de vida para controlar manualmente
         */
        super.cicloVida=false;
        
    }
    
    

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        actualizarDatosVista();
        this.parametroCodefacService.editarParametros(parametros);
        moverArchivo();
        /**
         * Establesco el ciclo de vida en el cual me encuentro
         */
        this.estadoFormulario=GeneralPanelInterface.ESTADO_GRABAR;
        DialogoCodefac.mensaje("Actualizado datos","Los datos de los parametros fueron actualizados",DialogoCodefac.MENSAJE_CORRECTO);
        //DialogoCodefac.mensaje("Firma", "Datos actualizados correctamente", DialogoCodefac.MENSAJE_CORRECTO);
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
        super.estadoFormulario=GeneralPanelInterface.ESTADO_EDITAR;
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
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer,Boolean> permisos=new HashMap<Integer,Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR,true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }
    
    private void actualizarDatosVista()
    {
        parametros.get(ParametroCodefac.SECUENCIAL_FACTURA).setValor(getTxtFacturaSecuencial().getText());
        parametros.get(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).setValor(getTxtNotaCreditoSecuencial().getText());
        parametros.get(ParametroCodefac.SECUENCIAL_NOTA_DEBITO).setValor(getTxtNotaDebitoSecuencial().getText());
        parametros.get(ParametroCodefac.SECUENCIAL_GUIA_REMISION).setValor(getTxtGuiaRemisionSecuencial().getText());
        parametros.get(ParametroCodefac.SECUENCIAL_RETENCION).setValor(getTxtRetencionesSecuencial().getText());
        parametros.get(ParametroCodefac.ESTABLECIMIENTO).setValor(getTxtEstablecimiento().getText());
        parametros.get(ParametroCodefac.PUNTO_EMISION).setValor(getTxtPuntoEmision().getText());
        String ivaDefacto=((ImpuestoDetalle)getCmbIvaDefault().getSelectedItem()).getTarifa().toString();
        parametros.get(ParametroCodefac.IVA_DEFECTO).setValor(ivaDefacto); 
        parametros.get(ParametroCodefac.CORREO_USUARIO).setValor(getTxtCorreoElectronico().getText());
        parametros.get(ParametroCodefac.CORREO_CLAVE).setValor(getTxtPasswordCorreo().getText());
    }
    
    private void cargarDatosConfiguraciones()
    {        
        parametros=parametroCodefacService.getParametrosMap();
        ParametroCodefac param=parametros.get(ParametroCodefac.SECUENCIAL_FACTURA);
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
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("tarifa",Integer.parseInt(parametros.get(ParametroCodefac.IVA_DEFECTO).getValor()));
        List<ImpuestoDetalle> lista= impuestoDetalleService.buscarImpuestoDetallePorMap(map);
        getCmbIvaDefault().getModel().setSelectedItem(lista.get(0));
        
    }
    
    private void cargarDatosIva()
    {
        ImpuestoService impuestoService=new ImpuestoService();
        Impuesto iva=impuestoService.obtenerImpuestoPorCodigo(Impuesto.IVA);
        for (ImpuestoDetalle impuesto : iva.getDetalleImpuestos()) {
            getCmbIvaDefault().addItem(impuesto);
        }
    }
     
    public void cargarDatosArchivos(File archivoEscogido)
    {
        File archivo = archivoEscogido;
        String rutaArchivo = archivo.getPath();
        String nombreArchivo = archivo.getName();
        String rutaDestino = session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).valor + "/" + ComprobanteElectronicoService.CARPETA_CONFIGURACION + "/";              
        rutaDestino += nombreArchivo;
        establecerDondeMoverArchivo(rutaArchivo, rutaDestino);
    }
    
    public void establecerDondeMoverArchivo(String rutaArchivo, String rutaDestino)
    {
        this.origen = FileSystems.getDefault().getPath(rutaArchivo);
        this.destino = FileSystems.getDefault().getPath(rutaDestino);
    }
    
    public void moverArchivo()
    {
        //Verifica que solo cuando exista un origen y destino exista se copien los datos
        if(origen==null || destino==null)
        {
            return ;
        }
        
        try 
        {
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            getTxtNombreFirma().setText(""+destino.getFileName());
        } catch (IOException ex) {
            DialogoCodefac.mensaje("Firma", "Problema en guardar firma", DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }
    
    private void addListenerButtons() 
    { 
        getBtnFirmaElectronica().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                int seleccion = jFileChooser.showDialog(null, "Abrir");
                switch(seleccion)
                {
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

    @Override
    public void iniciar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}   
