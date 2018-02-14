/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.MetodosEnvioInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionTributaria;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;
import ec.com.codesoft.ejemplo.utilidades.imagen.UtilidadImagen;
import ec.com.codesoft.ejemplo.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.ejemplo.utilidades.varios.UtilidadVarios;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class ComprobantesService extends ServiceAbstract implements ComprobanteServiceIf{

    /**
     * Graba una lista e clientes suscritos para responder
     */
    private Vector<ClienteInterfaceComprobante> clientesLista;
    
    public ComprobantesService() throws RemoteException {
        super();
        clientesLista=new Vector<ClienteInterfaceComprobante>();
    }
    
    /**
     * Regresa un jasperPrint para que pueda generar un reporte
     * @param claveAcceso
     * @return
     * @throws RemoteException 
     */
    public byte[] getReporteComprobante(String claveAcceso) throws RemoteException
    {
        try {
            ComprobanteElectronicoService comprobanteElectronico = new ComprobanteElectronicoService();
            //Cargar recursos para el reporte
            cargarDatosRecursos(comprobanteElectronico);
            mapReportePlantilla(null);
            cargarConfiguraciones(comprobanteElectronico);
            comprobanteElectronico.setClaveAcceso(claveAcceso);
            JasperPrint jasperPrint=comprobanteElectronico.getPrintJasper();
            return UtilidadesRmi.serializar(jasperPrint);
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param comprobante el comprobante a procesar facturas, notas de credito
     * con los datos finales implementados
     */
    public void procesarComprobante(ComprobanteDataInterface comprobanteData,Factura factura,Usuario usuario,ClienteInterfaceComprobante callbackClientObject) throws RemoteException {
        /**
         * Metodo del modulo de facturacion electronica que contiene la interfaz
         * para facturar electronicamente
         */
        ComprobanteElectronico comprobante= comprobanteData.getComprobante();
        ComprobanteElectronicoService comprobanteElectronico = new ComprobanteElectronicoService();

        //Agregando informacionTributaria
        comprobante.setInformacionTributaria(getInfoInformacionTributaria(comprobanteData));

        //Validacion para verificar que si no existen datos adicionales no se agregue nada
        List<InformacionAdicional> informacionAdicional = getInformacionAdicional(comprobanteData);
        if (informacionAdicional != null && informacionAdicional.size() > 0) {
            comprobante.setInformacionAdicional(informacionAdicional);
        }
        
        //Agregar datos adicionales del Reporte
        comprobanteElectronico.setMapAdicionalReporte(mapReportePlantilla(usuario));
        
        //Cargar los correos que se van a usar para enviar los datos
        comprobanteElectronico.setCorreosElectronicos(comprobanteData.getCorreos());

        comprobanteElectronico.setComprobante(comprobante);
        
        //Cargar configuraciones por defecto para los comprobantes
        cargarConfiguraciones(comprobanteElectronico);

        //Etapa desde la cual va a procesar los comprobantes
        comprobanteElectronico.setEtapaActual(ComprobanteElectronicoService.ETAPA_GENERAR);
        
        //Agregar el listener
        comprobanteElectronico.addActionListerComprobanteElectronico(new ListenerComprobanteElectronico() {
            @Override
            public void termino() {
                try {
                    //Si la factura termina corectamente grabo el estado y numero de autorizacion
                    FacturacionService facturacionService=new FacturacionService();
                   
                    factura.setClaveAcceso(comprobanteElectronico.getClaveAcceso());
                    factura.setEstado(FacturaEnumEstado.FACTURADO.getEstado());
                    entityManager.merge(factura);
                    //facturacionService.editar(factura);
                    //cargarDatosRecursos(comprobanteElectronico);
                    //mapReportePlantilla(usuario);
                    byte[] serializedPrint= getReporteComprobante(comprobanteElectronico.getClaveAcceso());                   
                    callbackClientObject.termino(serializedPrint);
                    
                    //doCallbacks();
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void iniciado(ComprobanteElectronico comprobante) {
                try {
                    callbackClientObject.iniciado();
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void procesando(int etapa, ClaveAcceso clave) {
                try {
                    callbackClientObject.procesando(etapa, clave);
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }

            @Override
            public void error(ComprobanteElectronicoException cee) {
                try {
                    callbackClientObject.error(cee,comprobanteElectronico.getClaveAcceso());
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        //Proceso el comprobante
        comprobanteElectronico.procesar();

    }

    private InformacionTributaria getInfoInformacionTributaria(ComprobanteDataInterface comprobanteData) throws RemoteException {
        InformacionTributaria infoTributaria = new InformacionTributaria();
        ParametroCodefacService parametroCodefacService = new ParametroCodefacService();
        EmpresaService empresaService = new EmpresaService();
        Empresa empresa = empresaService.obtenerTodos().get(0);
        Map<String, ParametroCodefac> parametroCodefacMap = parametroCodefacService.getParametrosMap();

        if (parametroCodefacMap.get(ParametroCodefac.MODO_FACTURACION).valor.equals(ComprobanteElectronicoService.MODO_PRODUCCION)) {
            infoTributaria.setAmbiente(ComprobanteElectronicoService.CODIGO_SRI_MODO_PRODUCCION + "");
        } else {
            infoTributaria.setAmbiente(ComprobanteElectronicoService.CODIGO_SRI_MODO_PRUEBAS + "");
        }

        infoTributaria.setClaveAcceso("");
        infoTributaria.setCodigoDocumento(comprobanteData.getCodigoComprobante());
        infoTributaria.setDirecionMatriz(empresa.getDireccion());

        String establecimiento = parametroCodefacMap.get(ParametroCodefac.ESTABLECIMIENTO).valor;
        infoTributaria.setEstablecimiento(establecimiento);
        infoTributaria.setNombreComercial(empresa.getNombreLegal());

        String puntoEmision = parametroCodefacMap.get(ParametroCodefac.PUNTO_EMISION).valor;
        infoTributaria.setPuntoEmision(puntoEmision);

        infoTributaria.setRazonSocial(empresa.getRazonSocial());
        infoTributaria.setRuc(empresa.getIdentificacion());
        infoTributaria.setSecuencial(comprobanteData.getSecuencial());
        infoTributaria.setTipoEmision(ComprobanteElectronico.MODO_FACTURACION_NORMAL);
        return infoTributaria;
    }

    private List<InformacionAdicional> getInformacionAdicional(ComprobanteDataInterface comprobanteData) {
        List<InformacionAdicional> listaInfoAdicional = new ArrayList<InformacionAdicional>();

        Map<String, String> map = comprobanteData.getMapAdicional();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                InformacionAdicional info = new InformacionAdicional();
                info.setNombre(key);
                info.setValor(value);
                listaInfoAdicional.add(info);
            }
        }
        return listaInfoAdicional;
    }

    private void cargarConfiguraciones(ComprobanteElectronicoService servicio) {
        try {
            ParametroCodefacService parametroCodefacService = new ParametroCodefacService();
            Map<String, ParametroCodefac> parametroCodefacMap = parametroCodefacService.getParametrosMap();
            //String pathBase de los directorios

            //servicio.setPathBase(parametroCodefacMap.get(ParametroCodefac.DIRECTORIO_RECURSOS).valor);
            servicio.setPathBase(UtilidadesServidor.pathRecursos);
            servicio.setNombreFirma(parametroCodefacMap.get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).valor);
            servicio.setClaveFirma(parametroCodefacMap.get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).valor);
            String modoFacturacion = parametroCodefacMap.get(ParametroCodefac.MODO_FACTURACION).valor;
            servicio.setModoFacturacion(modoFacturacion);

            cargarDatosRecursos(servicio);

            /**
             * Cargar los web services dependiendo el modo de facturacion
             */
            if (ComprobanteElectronicoService.MODO_PRODUCCION.equals(modoFacturacion)) {
                String autorizacion = parametroCodefacMap.get(ParametroCodefac.SRI_WS_AUTORIZACION).valor;
                servicio.setUriAutorizacion(autorizacion);

                String recepcion = parametroCodefacMap.get(ParametroCodefac.SRI_WS_RECEPCION).valor;
                servicio.setUriRecepcion(recepcion);

            } else {
                String autorizacion = parametroCodefacMap.get(ParametroCodefac.SRI_WS_AUTORIZACION_PRUEBA).valor;
                servicio.setUriAutorizacion(autorizacion);

                String recepcion = parametroCodefacMap.get(ParametroCodefac.SRI_WS_RECEPCION_PRUEBA).valor;
                servicio.setUriRecepcion(recepcion);
            }

            /**
             * Cargar variables para el envio del correo
             */
            cargarConfiguracionesCorreo(servicio);
            String footer = UtilidadVarios.getStringHtmltoUrl(RecursoCodefac.HTML.getResourceInputStream("footer_codefac.html"));
            servicio.setFooterMensajeCorreo(footer);

            /**
             * Cargar datos de las formas de pago
             */
            SriServiceIf service = ServiceFactory.getFactory().getSriServiceIf();
            List<SriFormaPago> formasPagoSri = service.obtenerFormasPagoActivo();
            Map<String, String> mapFormasPago = new HashMap<String, String>();
            for (SriFormaPago sriFormaPago : formasPagoSri) {
                mapFormasPago.put(sriFormaPago.getCodigo(), sriFormaPago.getNombre());
            }
            servicio.setMapCodeAndNameFormaPago(mapFormasPago);
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void cargarDatosRecursos(ComprobanteElectronicoService servicio) throws RemoteException {
        ParametroCodefacService parametroCodefacService = new ParametroCodefacService();
        EmpresaService empresaService = new EmpresaService();
        Empresa empresa = empresaService.obtenerTodos().get(0);
        Map<String, ParametroCodefac> parametroCodefacMap = parametroCodefacService.getParametrosMap();
        /**
         * Setear variables de configuracion para los reportes
         */
        servicio.setPathFacturaJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceInputStream("facturaReporte.jrxml"));
        servicio.setPathNotaCreditoJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceInputStream("notaCreditoReporte.jrxml"));
        //String imagenLogo=session.getParametrosCodefac().get(ParametroCodefac.LOGO_EMPRESA).getValor();
        //TODO Este parametro debe ser configurable cuando se la version de pago para que permita seleccionar la imagen del cliente
        //servicio.setLogoImagen(DirectorioCodefac.IMAGENES.getArchivoStream(session,imagenLogo));
        //BufferedImage image = ImageIO.read(RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg"));
        //servicio.setLogoImagen(image);
        servicio.setPathParentJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourcesParentPath("facturaReporte.jrxml"));
        servicio.setReporteFormaPago(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceInputStream("forma_pago.jasper"));
        servicio.setReporteInfoAdicional(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceInputStream("datos_adicionales.jasper"));
        servicio.setMapAdicionalReporte(mapReportePlantilla(null));
        //servicio.pathLogoImagen = RecursoCodefac.IMAGENES_GENERAL.getResourceURL("sin_imagen.jpg").getPath();
        //Segun el tipo de licencia cargar los recursos
        servicio.pathLogoImagen = RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg");
        if (UtilidadesServidor.tipoLicenciaEnum.equals(TipoLicenciaEnum.GRATIS)) {

            InputStream inputStream = null;
            try {

                String imagenLogo = empresa.getImagenLogoPath();
                //String pathImagen = parametroCodefacMap.get(ParametroCodefac.DIRECTORIO_RECURSOS).valor + "/" + DirectorioCodefac.IMAGENES.getNombre() + "/" + imagenLogo;
                String pathImagen = UtilidadesServidor.pathRecursos+ DirectorioCodefac.IMAGENES.getNombre() + "/" + imagenLogo;

                inputStream = new FileInputStream(pathImagen);
                //Si no existe imagen en la version de pago setea un imagen por defecto
                if (inputStream == null) {
                    RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg");
                }
                //BufferedInputStream bufferStream=new BufferedInputStream(inputStream);
                servicio.pathLogoImagen = UtilidadImagen.castImputStreamForReport(inputStream);
            } catch (FileNotFoundException ex) {
                servicio.pathLogoImagen = RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg");
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private Map<String, Object> mapReportePlantilla(Usuario usuario) throws RemoteException {
        ParametroCodefacService parametroCodefacService = new ParametroCodefacService();
        EmpresaService empresaService = new EmpresaService();
        Empresa empresa = empresaService.obtenerTodos().get(0);
        Map<String, ParametroCodefac> parametroCodefacMap = parametroCodefacService.getParametrosMap();

        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("pl_fecha_hora", formateador.format(new Date()));
        parametros.put("pl_usuario", (usuario==null)?"":usuario.getNick());
        parametros.put("pl_direccion", empresa.getDireccion());
        parametros.put("pl_nombre_empresa", empresa.getNombreLegal());
        parametros.put("pl_telefonos", empresa.getTelefonos());

        parametros.put("pl_url_img1", (RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("codefac-logotipo.png")));
        parametros.put("pl_img_facebook", (RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceInputStream("facebook.png")));
        parametros.put("pl_img_whatsapp", (RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceInputStream("whatsapp.png")));
        parametros.put("pl_img_telefono", (RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceInputStream("telefono.png")));
        parametros.put("pl_img_logo_pie", (RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("codesoft-logo.png")));

        parametros.put("pl_url_img1_url", (RecursoCodefac.IMAGENES_GENERAL.getResourceURL("codefac-logotipo.png").getPath()));
        parametros.put("pl_img_facebook_url", (RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceURL("facebook.png").getPath()));
        parametros.put("pl_img_whatsapp_url", (RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceURL("whatsapp.png").getPath()));
        parametros.put("pl_img_telefono_url", (RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceURL("telefono.png").getPath()));
        parametros.put("pl_img_logo_pie_url", (RecursoCodefac.IMAGENES_GENERAL.getResourceURL("codesoft-logo.png").getPath()));

        parametros.put("pl_url_cabecera", RecursoCodefac.JASPER.getResourceInputStream("encabezado.jasper"));
        parametros.put("pl_url_piepagina", RecursoCodefac.JASPER.getResourceInputStream("pie_pagina.jasper"));

        //System.out.println(parametros.get("SUBREPORT_DIR"));
        return parametros;
    }
    
    private void cargarConfiguracionesCorreo(ComprobanteElectronicoService servicio)
    {
        servicio.setMetodoEnvioInterface(new MetodosEnvioInterface() {
            @Override
            public void enviarCorreo(String mensaje, String subject, List<String> destinatorios, Map<String,String> pathFiles) throws Exception {
                /*CorreoCodefac correo=new CorreoCodefac(session) {
                    @Override
                    public String getMensaje() {
                        return mensaje;
                    }
                    
                    @Override
                    public String getTitulo() {
                        return subject;
                    }
                    
                    @Override
                    public Map<String,String> getPathFiles() {
                        return pathFiles;
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
                }*/
            }
        });
    }

    @Override
    public void registerForCallback(ClienteInterfaceComprobante callbackClientObject) throws RemoteException {
        if (!(clientesLista.contains(callbackClientObject))) {
            clientesLista.addElement(callbackClientObject);            
        }
    }
    
    private synchronized void doCallbacks() throws RemoteException {
        for (int i = 0; i < clientesLista.size(); i++) {
            ClienteInterfaceComprobante nextClient= (ClienteInterfaceComprobante) clientesLista.elementAt(i);
            //nextClient.termino();
        } // for
    } // function

    @Override
    public void unregisterForCallback(ClienteInterfaceComprobante callbackClientObject) throws RemoteException {
        if (clientesLista.removeElement(callbackClientObject)) {
            System.out.println("Quitando registro cliente ");
        } else {
            System.out.println("El cliente no estaba registrado");
        }
    }    

}
