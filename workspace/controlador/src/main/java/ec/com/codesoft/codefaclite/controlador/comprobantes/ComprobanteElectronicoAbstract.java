/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobantes;

import ec.com.codesoft.codefaclite.controlador.aplicacion.mail.CorreoCodefac;
import ec.com.codesoft.codefaclite.controlador.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.controlador.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.MetodosEnvioInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionTributaria;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.ejemplo.utilidades.email.CorreoElectronico;
import ec.com.codesoft.ejemplo.utilidades.varios.UtilidadVarios;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public abstract class ComprobanteElectronicoAbstract <T extends ComprobanteElectronico>{
    protected SessionCodefacInterface session;
    private ComprobanteElectronicoService servicio;
    private InterfazComunicacionPanel interfazPadre;
    
    /**
     * varibale que me permite setear la clave de acceso para procesar por etapas
     */
    protected String claveAcceso;
    
    public abstract String getCodigoComprobante();
    public abstract String getSecuencial();
    public abstract Map<String,String> getMapAdicional();
    public abstract List<String> getCorreos();
    //public abstract List<InformacionAdicional> getInformacionAdicional();
    /**
     * Implementar el modelo del comprobante exeptuando
     * los valores de informacion tributaria que se implementa solo
     * @return 
     */
    public abstract T getComprobante();
    
    

    public ComprobanteElectronicoAbstract(SessionCodefacInterface session) {
        this.session = session;
    }

    public ComprobanteElectronicoAbstract(SessionCodefacInterface session, InterfazComunicacionPanel interfazPadre) {
        this.session = session;
        this.interfazPadre = interfazPadre;
        this.servicio=new ComprobanteElectronicoService();
    }

    public ComprobanteElectronicoAbstract(SessionCodefacInterface session, InterfazComunicacionPanel interfazPadre, String claveAcceso) {
        this.session = session;
        this.interfazPadre = interfazPadre;
        this.claveAcceso = claveAcceso;
        this.servicio=new ComprobanteElectronicoService();
        cargarConfiguraciones();
    }
    
    
    
    
    public InformacionTributaria getInfoInformacionTributaria()
    {
        InformacionTributaria infoTributaria=new InformacionTributaria();
        if(session.getParametrosCodefac().get(ParametroCodefac.MODO_FACTURACION).valor.equals(ComprobanteElectronicoService.MODO_PRODUCCION))
        {
           infoTributaria.setAmbiente(ComprobanteElectronicoService.CODIGO_SRI_MODO_PRODUCCION+"");
        }
        else
        {
           infoTributaria.setAmbiente(ComprobanteElectronicoService.CODIGO_SRI_MODO_PRUEBAS+"");
        }

        infoTributaria.setClaveAcceso("");
        infoTributaria.setCodigoDocumento(getCodigoComprobante());
        infoTributaria.setDirecionMatriz(session.getEmpresa().getDireccion());
        infoTributaria.setEstablecimiento("001");
        infoTributaria.setNombreComercial(session.getEmpresa().getNombreLegal());
        infoTributaria.setPuntoEmision("001");
        infoTributaria.setRazonSocial(session.getEmpresa().getRazonSocial());
        infoTributaria.setRuc(session.getEmpresa().getIdentificacion());
        infoTributaria.setSecuencial(getSecuencial());
        infoTributaria.setTipoEmision(ComprobanteElectronico.MODO_FACTURACION_NORMAL);
        return infoTributaria;
    }

    
    private void cargarConfiguraciones()
    {
        servicio.setPathBase(session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).valor);
        servicio.setNombreFirma(session.getParametrosCodefac().get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).valor);
        servicio.setClaveFirma(session.getParametrosCodefac().get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).valor);
        String modoFacturacion=session.getParametrosCodefac().get(ParametroCodefac.MODO_FACTURACION).valor;
        servicio.setModoFacturacion(modoFacturacion);
        
                /**
         * Setear variables de configuracion para los reportes
         */
        servicio.setPathFacturaJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourcePath("facturaReporte.jrxml"));
        String imagenLogo=session.getParametrosCodefac().get(ParametroCodefac.LOGO_EMPRESA).getValor();
        servicio.setLogoImagen(DirectorioCodefac.IMAGENES.getArchivoStream(session,imagenLogo));
        servicio.setPathParentJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourcesParentPath("facturaReporte.jrxml"));
        servicio.setMapAdicionalReporte(interfazPadre.mapReportePlantilla());
        servicio.pathLogoImagen=DirectorioCodefac.IMAGENES.getArchivo(session,imagenLogo);
        
        /**
         * Cargar los web services dependiendo el modo de facturacion
         */
        if(ComprobanteElectronicoService.MODO_PRODUCCION.equals(modoFacturacion))
        {
            String autorizacion=session.getParametrosCodefac().get(ParametroCodefac.SRI_WS_AUTORIZACION).valor;
            servicio.setUriAutorizacion(autorizacion);
            
            String recepcion=session.getParametrosCodefac().get(ParametroCodefac.SRI_WS_RECEPCION).valor;
            servicio.setUriRecepcion(recepcion);
            
        }
        else
        {
            String autorizacion = session.getParametrosCodefac().get(ParametroCodefac.SRI_WS_AUTORIZACION_PRUEBA).valor;
            servicio.setUriAutorizacion(autorizacion);

            String recepcion = session.getParametrosCodefac().get(ParametroCodefac.SRI_WS_RECEPCION_PRUEBA).valor;
            servicio.setUriRecepcion(recepcion);
        }
        
        /**
         * Cargar variables para el envio del correo
         */
        cargarConfiguracionesCorreo();
        servicio.setCorreosElectronicos(getCorreos());
        String footer=UtilidadVarios.getStringHtmltoUrl(RecursoCodefac.HTML.getResourceInputStream("footer_codefac.html"));
        servicio.setFooterMensajeCorreo(footer);
    
    }
    
        /**
     * Procesa el comprobante desde una determinada etapa
     */
    public void procesarComprobanteEtapa(Integer etapa,Boolean completarTodasEtapas)
    {
        servicio.setClaveAcceso(claveAcceso);
        servicio.setProcesarTodasEtapas(completarTodasEtapas);
        cargarConfiguraciones();
        servicio.procesar();
    }
    
    public JasperPrint obtenerRide()
    {
        cargarConfiguraciones();
        JasperPrint print = servicio.getPrintJasper();
        return print;
    }
    
    public void procesarComprobante()
    {
        /**
         * Crear el servicio para facturar
         */
        T comprobante=getComprobante();
        comprobante.setInformacionTributaria(getInfoInformacionTributaria());
        comprobante.setInformacionAdicional(getInformacionAdicional());        
        servicio.setComprobante(comprobante);
        servicio.setEtapaActual(ComprobanteElectronicoService.ETAPA_GENERAR);
        cargarConfiguraciones();
        servicio.setProcesarTodasEtapas(true);
        
        servicio.procesar();
        
    }
    
    private List<InformacionAdicional> getInformacionAdicional()
    {
        List<InformacionAdicional> listaInfoAdicional = new ArrayList<InformacionAdicional>();
        
        Map<String,String> map=getMapAdicional();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();            
            InformacionAdicional info=new InformacionAdicional();
            info.setNombre(key);
            info.setValor(value);
            listaInfoAdicional.add(info);
        }
        return listaInfoAdicional;        
    }

    public ComprobanteElectronicoService getServicio() {
        return servicio;
    }

    public void setServicio(ComprobanteElectronicoService servicio) {
        this.servicio = servicio;
    }
    
    
    public void cargarConfiguracionesCorreo()
    {
        this.servicio.setMetodoEnvioInterface(new MetodosEnvioInterface()  {
            @Override
            public void enviarCorreo(String mensaje, String subject, List<String> destinatorios,String pathFile) throws ComprobanteElectronicoException{
                CorreoCodefac correo=new CorreoCodefac(session) {
                    @Override
                    public String getMensaje() {
                        return mensaje;
                    }
                    
                    @Override
                    public String getTitulo() {
                        return subject;
                    }
                    
                    @Override
                    public String getPathFile() {
                        return pathFile;
                    }
                    
                    @Override
                    public List<String> getDestinatorios() {
                        return destinatorios;
                    }
                };
                
                try
                {
                    correo.enviarCorreo();
                }catch(RuntimeException e)
                {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
        this.servicio.setClaveAcceso(claveAcceso);
    }
    
    

    
    
    
}
