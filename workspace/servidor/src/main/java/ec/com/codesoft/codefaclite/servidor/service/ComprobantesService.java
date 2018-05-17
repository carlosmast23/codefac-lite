/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import autorizacion.ws.sri.gob.ec.Autorizacion;
import autorizacion.ws.sri.gob.ec.Mensaje;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.FirmaElectronica;
import ec.com.codesoft.codefaclite.facturacionelectronica.MetodosEnvioInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.ServicioSri;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronicoLote;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionTributaria;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteData;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteMensaje;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.CorreoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.NotaCreditoEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;
import ec.com.codesoft.codefaclite.ws.recepcion.Comprobante;
import ec.com.codesoft.ejemplo.utilidades.imagen.UtilidadImagen;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

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
     * Obtiene el siguiente secuencial y setea ese valor
     * @return 
     */
    public Integer obtenerSecuencialFacturaYAvanzar() throws RemoteException
    {
        try {
            ParametroCodefacService servicio=new ParametroCodefacService();
            ParametroCodefac parametroCodefac=servicio.getParametroByNombre(ParametroCodefac.SECUENCIAL_FACTURA);
            String secuencialStr=parametroCodefac.getValor();
            Integer secuencialInt=Integer.parseInt(secuencialStr)+1;
            parametroCodefac.setValor(secuencialInt.toString());
            
            servicio.editar(parametroCodefac);
            return secuencialInt;
                    
                    } catch (RemoteException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean verificarCredencialesFirma(String claveFirma) throws RemoteException
    {
        
        try {
            ParametroCodefacService servicioParametros=new ParametroCodefacService();
            Map<String,ParametroCodefac> parametrosMap=  servicioParametros.getParametrosMap();
            
            String pathFirma=parametrosMap.get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).getValor();
            String rutaDestino = parametrosMap.get(ParametroCodefac.DIRECTORIO_RECURSOS).valor + "/" + ComprobanteElectronicoService.CARPETA_CONFIGURACION + "/"+pathFirma;
            
            if (!claveFirma.equals("") && !pathFirma.equals("")) {
                if (FirmaElectronica.FirmaVerificar(rutaDestino, claveFirma)) {
                    return true;
                }
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean procesarComprobantesLotePendiente(Integer etapaInicial,Integer etapaLimite,List<String> clavesAcceso,String ruc,ClienteInterfaceComprobanteLote callbackClientObject) throws RemoteException
    {
        ComprobanteElectronicoService comprobanteElectronico= new ComprobanteElectronicoService();
        cargarConfiguraciones(comprobanteElectronico);
        comprobanteElectronico.setEtapaActual(etapaInicial);
        //comprobanteElectronico.setClaveAcceso(claveAcceso);
        comprobanteElectronico.setEtapaLimiteProcesar(etapaLimite);
        comprobanteElectronico.setClavesAccesoLote(clavesAcceso);
        comprobanteElectronico.setRuc(ruc);
        
        Integer secuencialLote=obtenerSecuencialLote(); //Verificar que solo debe dar un secuencial si la etapa es superior a enviar comprobante
        
        comprobanteElectronico.setSecuencialLote(secuencialLote);
        
        comprobanteElectronico.addActionListerComprobanteElectronicoLote(new ListenerComprobanteElectronicoLote() {
            @Override
            public void iniciado() {
                try {
                    callbackClientObject.iniciado();
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void clavesGeneradas(List<ClaveAcceso> listaClaves) {
                //TODO: Metodo que devuelve las claves generadas
            }

            @Override
            public void datosAutorizados(List<Autorizacion> autorizaciones) {
                //TODOS: Lista de los documentos autorizados
            }

            @Override
            public void procesando(int etapa) {
                try {
                    callbackClientObject.procesando(etapa);
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void error() {
                try {
                    callbackClientObject.error();
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void termino(List<Autorizacion> autorizaciones) {
                try {
                    callbackClientObject.termino(castDatosComprobanteElectronico(autorizaciones,comprobanteElectronico.getServicioSri()));
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });        
        
        //comprobanteElectronico
        comprobanteElectronico.procesar(true);
        return true;
    }
    
    public boolean procesarComprobantesPendiente(Integer etapaInicial,Integer etapaLimite,String claveAcceso, List<String> correos,ClienteInterfaceComprobante callbackClientObject) throws RemoteException
    {
        
        ComprobanteElectronicoService comprobanteElectronico= new ComprobanteElectronicoService();
        cargarConfiguraciones(comprobanteElectronico);
        
        comprobanteElectronico.setCorreosElectronicos(correos);

        comprobanteElectronico.addActionListerComprobanteElectronico(new ListenerComprobanteElectronico() {
            @Override
            public void termino() {
                try {
                    callbackClientObject.termino(null);
                } catch (RemoteException ex) {
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
                    callbackClientObject.error(cee,"");
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        comprobanteElectronico.setEtapaActual(etapaInicial);
        comprobanteElectronico.setClaveAcceso(claveAcceso);
        comprobanteElectronico.setEtapaLimiteProcesar(etapaLimite);
        //comprobanteElectronico
        comprobanteElectronico.procesar(false);
        return true;
    }
    
    public List<ComprobanteElectronico> getComprobantesObjectByFolder(String carpetaConfiguracion) throws RemoteException
    {
        ParametroCodefacService parametroService=new ParametroCodefacService();
        String path= parametroService.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS).valor;
        String modoFacturacion=parametroService.getParametroByNombre(ParametroCodefac.MODO_FACTURACION).valor;
        String pathComprobantes="";
        
        if (modoFacturacion.equals(ComprobanteElectronicoService.MODO_PRODUCCION)) {
            pathComprobantes =path+"/"+ DirectorioCodefac.COMPROBANTES_PRODUCCION.getNombre();
        } else {
            pathComprobantes =path+"/"+ DirectorioCodefac.COMPROBANTES_PRUEBAS.getNombre();
        }
        return ComprobantesElectronicosUtil.getComprobantesObjectByFolder(pathComprobantes,carpetaConfiguracion);
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
    public void procesarComprobanteNotaCredito(ComprobanteDataInterface comprobanteData,NotaCredito notaCredito,Usuario usuario,ClienteInterfaceComprobante callbackClientObject) throws RemoteException {
        /**
         * Metodo del modulo de facturacion electronica que contiene la interfaz
         * para facturar electronicamente
         */
        ComprobanteElectronicoService comprobanteElectronico= cargarConfiguracionesInicialesComprobantes(comprobanteData, usuario);
        
        //Agregar el listener
        comprobanteElectronico.addActionListerComprobanteElectronico(new ListenerComprobanteElectronico() {
            @Override
            public void termino() {
                try {
                    //Si la factura termina corectamente grabo el estado y numero de autorizacion
                    NotaCreditoService notaCreditoService=new NotaCreditoService();
                   
                    notaCredito.setClaveAcceso(comprobanteElectronico.getClaveAcceso());
                    notaCredito.setEstado(NotaCreditoEnumEstado.TERMINADO.getEstado());
                    entityManager.merge(notaCredito);
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
        comprobanteElectronico.procesar(false);

    }
    
    private Integer obtenerSecuencialLote() throws RemoteException
    {
        //Obtener el numero de secuencial siguiente
        ParametroCodefacServiceIf servicio=new ParametroCodefacService();
        ParametroCodefac parametroCodefac = servicio.getParametroByNombre(ParametroCodefac.SECUENCIAL_LOTE);
        Integer secuencialLote = Integer.parseInt(parametroCodefac.getValor());
        parametroCodefac.setValor((secuencialLote + 1) + "");
        entityManager.merge(parametroCodefac);
        return secuencialLote;
    }
    /**
     * Metodo que permite procesar varios comprobante en Lote
     * @param comprobanteData 
     */
    public void procesarComprobanteLote(List<ComprobanteDataInterface> comprobantesData,Usuario usuario,String ruc,ClienteInterfaceComprobanteLote callbackClientObject) throws RemoteException
    {
        ComprobanteElectronicoService comprobanteElectronico= cargarConfiguracionesInicialesComprobantesLote(comprobantesData, usuario);
        
        Integer secuencialLote=obtenerSecuencialLote();
        
        
        comprobanteElectronico.setSecuencialLote(secuencialLote);
        comprobanteElectronico.setRuc(ruc);
        
        comprobanteElectronico.addActionListerComprobanteElectronicoLote(new ListenerComprobanteElectronicoLote() {
            @Override
            public void iniciado() {
                 try {
                     //Seteado las claves de acceso                     
                    cambiarEstadoLotes(comprobantesData,FacturaEnumEstado.SIN_AUTORIZAR);
                    callbackClientObject.iniciado();
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void procesando(int etapa) {
                try {
                    callbackClientObject.procesando(etapa);
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void error() {
                try {
                    callbackClientObject.error();
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void termino(List<Autorizacion> autorizaciones) {
                try {
                    //comprobanteElectronico.getServicioSri();
                    cambiarEstadoLotes(comprobantesData,FacturaEnumEstado.FACTURADO);
                    callbackClientObject.termino(castDatosComprobanteElectronico(autorizaciones,comprobanteElectronico.getServicioSri()));
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void clavesGeneradas(List<ClaveAcceso> listaClaves) {
                setClaveAccesoLotes(comprobantesData,listaClaves);
            }

            @Override
            public void datosAutorizados(List<Autorizacion> autorizaciones) {
                    cambiarAutorizadoLotes(autorizaciones);
            }
        });
        
    
        comprobanteElectronico.procesar(true);
    }
    
        /**
     * Setear los valores del las claves de acceso con su comprobante en la base de datos
     * @param comprobantesData
     * @param listaClaves 
     */
    protected void setClaveAccesoLotes(List<ComprobanteDataInterface> comprobantesData,List<ClaveAcceso> listaClaves)
    {
        for (ComprobanteDataInterface comprobanteDataInterface : comprobantesData) {
            try {
                String codigoComprobante=comprobanteDataInterface.getCodigoComprobante();
                ComprobanteEnum comprobanteEnum=ComprobanteEnum.getEnumByCodigo(codigoComprobante);
   
                switch(comprobanteEnum)
                {
                    case  FACTURA:
                        Long id=comprobanteDataInterface.getComprobanteId();
                        FacturacionService servicio=new FacturacionService();
                        Factura factura=servicio.buscarPorId(id);
                        ClaveAcceso claveAcceso=buscarClaveAccesoPorFactura(factura, listaClaves);
                        factura.setClaveAcceso(claveAcceso.clave);
                        servicio.editar(factura);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    
    /**
     * Busca una clave
     * @param factura
     * @param listaClaves
     * @return 
     */
    protected ClaveAcceso buscarClaveAccesoPorFactura(Factura factura,List<ClaveAcceso> listaClaves)
    {
        //TODO: Falta implementar una busqueda mas detallada por punto de emision, sucursal y tipo de documento
        for (ClaveAcceso claveAcceso : listaClaves) {
            System.out.println(claveAcceso.getSecuencialInteger());
            System.out.println(factura.getSecuencial());
            if(claveAcceso.getSecuencialInteger().equals(factura.getSecuencial()))
            {
                return claveAcceso;
            }
        }
        return null;
    }
    
    protected void cambiarEstadoLotes(List<ComprobanteDataInterface> comprobantesData,FacturaEnumEstado estado)
    {
        for (ComprobanteDataInterface comprobanteDataInterface : comprobantesData) {
            try {
                String codigoComprobante=comprobanteDataInterface.getCodigoComprobante();
                ComprobanteEnum comprobanteEnum=ComprobanteEnum.getEnumByCodigo(codigoComprobante);
                
                switch(comprobanteEnum)
                {
                    case  FACTURA:
                        Long id=comprobanteDataInterface.getComprobanteId();
                        FacturacionService servicio=new FacturacionService();
                        Factura factura=servicio.buscarPorId(id);
                        factura.setEstado(estado.getEstado());
                        servicio.editar(factura);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    protected void cambiarAutorizadoLotes(List<Autorizacion> autorizaciones)
    {
        for (Autorizacion autorizacion : autorizaciones) {
            if (autorizacion.getEstado().equals("AUTORIZADO")) 
            {
                try {
                    FacturacionService servicio = new FacturacionService();
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("claveAcceso",autorizacion.getNumeroAutorizacion());
                    List<Factura> facturas= servicio.obtenerPorMap(mapParametros);
                    if(facturas.size()>0)
                    {
                        Factura facturaEditar=facturas.get(0);
                        facturaEditar.setEstado(FacturaEnumEstado.FACTURADO.getEstado());
                        servicio.editar(facturaEditar);
                        
                        //Crear cartera de los comprobantes autorizados
                        Cartera cartera=crearCarteraFactura(facturaEditar);
                        entityManager.persist(cartera);                        
                    }
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
        }
    }
    
    private Cartera crearCarteraFactura(Factura factura)
    {
        Cartera cartera=new Cartera();
        cartera.setCodigoDocumento(factura.getCodigoDocumento());
        cartera.setFechaCreacion(new java.sql.Date(new java.util.Date().getTime()));
        cartera.setPersona(factura.getCliente());
        cartera.setReferenciaID(factura.getId());
        cartera.setSaldo(factura.getTotal().subtract(factura.getIva()));
        cartera.setTotal(factura.getTotal().subtract(factura.getIva()));
        
        for (FacturaDetalle detalle : factura.getDetalles()) {
            CarteraDetalle carteraDetalle=new CarteraDetalle();
            carteraDetalle.setCartera(cartera);
            carteraDetalle.setReferenciaId(detalle.getReferenciaId());
            carteraDetalle.setTipoReferencia(detalle.getTipoDocumento());
            carteraDetalle.setTotal(detalle.getTotal().subtract(detalle.getIva()));
        }
        return cartera;
    }   

    
    private List<ComprobanteData> castDatosComprobanteElectronico(List<Autorizacion> autorizaciones,ServicioSri servicioSri)
    {
        List<ComprobanteData> comprobantes=new ArrayList<ComprobanteData>();
        
        for (Autorizacion autorizacion : autorizaciones) {
            ComprobanteData comprobanteData=new ComprobanteData();
            comprobanteData.setAmbiente(autorizacion.getAmbiente());
            comprobanteData.setEstado(autorizacion.getEstado());
            comprobanteData.setFechaAutorizacion(autorizacion.getFechaAutorizacion()+"");
            //comprobanteData.setNumeroAutorizacion(autorizacion.getNumeroAutorizacion());
            
            //Convertir de texto a un comprobante fisico
            ComprobanteElectronico comprobanteElectronico=servicioSri.castComprobanteToAutorizacion(autorizacion);
                        
            if(comprobanteElectronico!=null)
            {
                comprobanteData.setNumeroAutorizacion(comprobanteElectronico.getInformacionTributaria().getClaveAcceso());
                comprobanteData.setPreimpreso(comprobanteElectronico.getInformacionTributaria().getPreimpreso());
                comprobanteData.setComprobante(comprobanteElectronico);
            }
            else
            {
                System.out.println("No se puede transformar el comprobante");
                System.out.println(autorizacion.getComprobante());
            }
            
            //Compiar los mensajes
            Autorizacion.Mensajes mensajes=autorizacion.getMensajes();
            List<ComprobanteMensaje> mensajesData=new ArrayList<ComprobanteMensaje>();
                    
            for (Mensaje mensajeAutorizacion : mensajes.getMensaje()) 
            {
                ComprobanteMensaje comprobanteMensaje=new ComprobanteMensaje();
                comprobanteMensaje.setIdentificador(mensajeAutorizacion.getIdentificador());
                comprobanteMensaje.setInformacionAdicional(mensajeAutorizacion.getInformacionAdicional());
                comprobanteMensaje.setMensaje(mensajeAutorizacion.getMensaje());
                comprobanteMensaje.setTipo(mensajeAutorizacion.getTipo());
                
                mensajesData.add(comprobanteMensaje);
            }
            comprobanteData.setMensajes(mensajesData);
            comprobantes.add(comprobanteData);
        }
        
        return comprobantes;
    }
    
    public void procesarComprobanteOffline(ComprobanteDataInterface comprobanteData,Factura factura,Usuario usuario,ClienteInterfaceComprobante callbackClientObject) throws RemoteException {
        ComprobanteElectronicoService comprobanteElectronico= cargarConfiguracionesInicialesComprobantes(comprobanteData, usuario);
        //Generar proceso hasta el envio del comprobante
        comprobanteElectronico.setEtapaLimiteProcesar(ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE);
        
        comprobanteElectronico.addActionListerComprobanteElectronico(new ListenerComprobanteElectronico() {
            @Override
            public void termino() {
                try {
                    //Si la factura termina corectamente grabo el estado y numero de autorizacion
                    FacturacionService facturacionService=new FacturacionService();
                   
                    factura.setClaveAcceso(comprobanteElectronico.getClaveAcceso());
                    factura.setEstado(FacturaEnumEstado.FACTURADO.getEstado());
                    entityManager.merge(factura);
                    byte[] serializedPrint= getReporteComprobante(comprobanteElectronico.getClaveAcceso());                   
                    callbackClientObject.termino(serializedPrint);

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
                    callbackClientObject.procesando(etapa,clave);
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void error(ComprobanteElectronicoException cee) {
                try {
                    callbackClientObject.error(cee, comprobanteElectronico.getClaveAcceso());
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        comprobanteElectronico.procesar(false);
        

    }
            
    
    /**
     *
     * @param comprobante el comprobante a procesar facturas, notas de credito
     * con los datos finales implementados
     */
    public void procesarComprobante(ComprobanteDataInterface comprobanteData,ec.com.codesoft.codefaclite.servidorinterfaz.entity.Comprobante comprobante,Usuario usuario,ClienteInterfaceComprobante callbackClientObject) throws RemoteException {
                
        ComprobanteElectronicoService comprobanteElectronico= cargarConfiguracionesInicialesComprobantes(comprobanteData, usuario);
        procesarComprobanteExtend(comprobanteElectronico, comprobante, callbackClientObject);

    }
    
    private void procesarComprobanteExtend(ComprobanteElectronicoService comprobanteElectronico,ec.com.codesoft.codefaclite.servidorinterfaz.entity.Comprobante comprobante,ClienteInterfaceComprobante callbackClientObject)
    {
                //Agregar el listener
        comprobanteElectronico.addActionListerComprobanteElectronico(new ListenerComprobanteElectronico() {
            @Override
            public void termino() {
                try {
                    //Si la factura termina corectamente grabo el estado y numero de autorizacion
                    //FacturacionService facturacionService=new FacturacionService();
                   
                    comprobante.setClaveAcceso(comprobanteElectronico.getClaveAcceso());
                    comprobante.setEstado(FacturaEnumEstado.FACTURADO.getEstado());
                    entityManager.merge(comprobante);
                    byte[] serializedPrint= getReporteComprobante(comprobanteElectronico.getClaveAcceso());                   
                    callbackClientObject.termino(serializedPrint);

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
        comprobanteElectronico.procesar(false);
    
    }
    
    private ComprobanteElectronicoService cargarConfiguracionesInicialesComprobantesLote(List<ComprobanteDataInterface> comprobantesData,Usuario usuario) throws RemoteException
    {
        ComprobanteElectronicoService comprobanteElectronico = new ComprobanteElectronicoService();
        
        List<ComprobanteElectronico> comprobantesElectronico=new ArrayList<ComprobanteElectronico>();
        
        for (ComprobanteDataInterface comprobanteData : comprobantesData) {
            ComprobanteElectronico comprobante= comprobanteData.getComprobante();
            //Agregando informacionTributaria
            comprobante.setInformacionTributaria(getInfoInformacionTributaria(comprobanteData));
            
            //Validacion para verificar que si no existen datos adicionales no se agregue nada
            List<InformacionAdicional> informacionAdicional = getInformacionAdicional(comprobanteData);
            if (informacionAdicional != null && informacionAdicional.size() > 0) {
                comprobante.setInformacionAdicional(informacionAdicional);
            }
            
            comprobantesElectronico.add(comprobante);
        }        
        //Agregar datos adicionales del Reporte
        comprobanteElectronico.setMapAdicionalReporte(mapReportePlantilla(usuario));
        
        //Cargar los correos que se van a usar para enviar los datos
        //comprobanteElectronico.setCorreosElectronicos(comprobanteData.getCorreos());

        
        comprobanteElectronico.setComprobantesLote(comprobantesElectronico);
        
        //Cargar configuraciones por defecto para los comprobantes
        cargarConfiguraciones(comprobanteElectronico);

        //Etapa desde la cual va a procesar los comprobantes
        comprobanteElectronico.setEtapaActual(ComprobanteElectronicoService.ETAPA_GENERAR);
    
        return comprobanteElectronico;
    }
    
    private ComprobanteElectronicoService cargarConfiguracionesInicialesComprobantes(ComprobanteDataInterface comprobanteData,Usuario usuario) throws RemoteException
    {
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
    
        return comprobanteElectronico;
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
            
            /**
             * Cargar los tipos de documentos para las facturas
             */
            
            List<SriRetencion> retenciones=ServiceFactory.getFactory().getSriRetencionServiceIf().obtenerTodos();
            Map<String, String> mapTipoRetencion = new HashMap<String, String>();
            for (SriRetencion retencion : retenciones) {
                mapTipoRetencion.put(retencion.getCodigo(), retencion.getNombre());
            }
            servicio.setMapCodeAndNameTipoRetecion(mapTipoRetencion);
            
            /**
             * Cargar el nombre de los documentos
             */
            ComprobanteEnum[] codigosComprobante= ComprobanteEnum.values();
            Map<String, String> mapTipoDocumento = new HashMap<String, String>();
            for (ComprobanteEnum comprobanteEnum : codigosComprobante) 
            {
                mapTipoDocumento.put(comprobanteEnum.getCodigo(),comprobanteEnum.getNombre());
            }
            servicio.setMapCodeAndNameTipoDocumento(mapTipoDocumento);
            
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void cargarDatosRecursos(ComprobanteElectronicoService servicio) throws RemoteException {
        ParametroCodefacService parametroCodefacService = new ParametroCodefacService();
        RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
        
        EmpresaService empresaService = new EmpresaService();
        Empresa empresa = empresaService.obtenerTodos().get(0);
        Map<String, ParametroCodefac> parametroCodefacMap = parametroCodefacService.getParametrosMap();
        /**
         * Setear variables de configuracion para los reportes
         */
        servicio.setPathFacturaJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceURL("facturaReporte.jrxml"));
        servicio.setPathNotaCreditoJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceURL("notaCreditoReporte.jrxml"));
        servicio.setPathRetencionJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceURL("retencion.jrxml"));
        //String imagenLogo=session.getParametrosCodefac().get(ParametroCodefac.LOGO_EMPRESA).getValor();
        //TODO Este parametro debe ser configurable cuando se la version de pago para que permita seleccionar la imagen del cliente
        //servicio.setLogoImagen(DirectorioCodefac.IMAGENES.getArchivoStream(session,imagenLogo));
        //BufferedImage image = ImageIO.read(RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg"));
        //servicio.setLogoImagen(image);
        servicio.setPathParentJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourcesParentPath("facturaReporte.jrxml"));
        
        InputStream inputStreamJasper=null;    
        JasperReport reportFormaPago=null;
        try {
            inputStreamJasper = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS, "forma_pago.jrxml"));
            reportFormaPago = JasperCompileManager.compileReport(inputStreamJasper);
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        servicio.setReporteFormaPago(reportFormaPago);
        //servicio.setReporteFormaPago(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceURL("forma_pago.jasper"));
        
        JasperReport reportDatosAdicionales=null;
        try {
            inputStreamJasper = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS, "datos_adicionales.jrxml"));
            reportDatosAdicionales = JasperCompileManager.compileReport(inputStreamJasper);
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }

        servicio.setReporteInfoAdicional(reportDatosAdicionales);
        servicio.setMapAdicionalReporte(mapReportePlantilla(null));
        //servicio.pathLogoImagen = RecursoCodefac.IMAGENES_GENERAL.getResourceURL("sin_imagen.jpg").getPath();
        //Segun el tipo de licencia cargar los recursos
        servicio.pathLogoImagen = UtilidadImagen.castInputStreamToImage(RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg"));
        if (UtilidadesServidor.tipoLicenciaEnum.equals(TipoLicenciaEnum.PRO)) {

            InputStream inputStream = null;
            try {

                String imagenLogo = empresa.getImagenLogoPath();
                //String pathImagen = parametroCodefacMap.get(ParametroCodefac.DIRECTORIO_RECURSOS).valor + "/" + DirectorioCodefac.IMAGENES.getNombre() + "/" + imagenLogo;
                String pathImagen = UtilidadesServidor.pathRecursos+"/"+ DirectorioCodefac.IMAGENES.getNombre() + "/" + imagenLogo;
                

                inputStream = new FileInputStream(pathImagen);
                //Si no existe imagen en la version de pago setea un imagen por defecto
                if (inputStream == null) {
                    RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg");
                }
                //BufferedInputStream bufferStream=new BufferedInputStream(inputStream);
                //servicio.pathLogoImagen = UtilidadImagen.castImputStreamForReport(inputStream);
                servicio.pathLogoImagen =UtilidadImagen.castInputStreamToImage(inputStream);
                //servicio.pathLogoImagen = new File(pathImagen.get;
            } catch (FileNotFoundException ex) {
                servicio.pathLogoImagen = UtilidadImagen.castInputStreamToImage(RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg"));
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
        RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
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
        
        parametros.put("pl_celular", empresa.getCelular());
        parametros.put("pl_facebook", empresa.getFacebook());
        parametros.put("pl_adicional", empresa.getAdicional());


        InputStream input=null;
        try {
            input = RemoteInputStreamClient.wrap(service.getResourceInputStreamByFile(DirectorioCodefac.IMAGENES, empresa.getImagenLogoPath()));
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        parametros.put("pl_url_img1", UtilidadImagen.castInputStreamToImage(input));
        //parametros.put("pl_url_img1", (RecursoCodefac.IMAGENES_GENERAL.getResourceURL("codefac-logotipo.png")));
        
        parametros.put("pl_img_facebook", (UtilidadImagen.castInputStreamToImage(RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceInputStream("facebook.png"))));
        parametros.put("pl_img_whatsapp", (UtilidadImagen.castInputStreamToImage(RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceInputStream("whatsapp.png"))));
        parametros.put("pl_img_telefono", (UtilidadImagen.castInputStreamToImage(RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceInputStream("telefono.png"))));
        //parametros.put("pl_img_logo_pie", (RecursoCodefac.IMAGENES_GENERAL.getResourceURL("codesoft-logo.png")));
        
        //parametros.put("pl_url_cabecera", RecursoCodefac.JASPER.getResourceURL("encabezado.jasper"));
        
        InputStream inputStream=null;
        JasperReport reportPiePagina=null;
        try {
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER, "pie_pagina.jrxml"));
            reportPiePagina = JasperCompileManager.compileReport(inputStream);
            
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        parametros.put("pl_url_piepagina", reportPiePagina);
        //parametros.put("pl_url_piepagina", RecursoCodefac.JASPER.getResourceURL("pie_pagina.jasper"));
        
        

        //System.out.println(parametros.get("SUBREPORT_DIR"));
        return parametros;
    }
    
    private void cargarConfiguracionesCorreo(ComprobanteElectronicoService servicio)
    {
        servicio.setMetodoEnvioInterface(new MetodosEnvioInterface() {
            @Override
            public void enviarCorreo(String mensaje, String subject, List<String> destinatorios, Map<String,String> pathFiles) throws Exception {
                CorreoCodefac correo=new CorreoCodefac() {
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
                }
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
