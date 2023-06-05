/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.common.AlertaResponse;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CarteraEstadoReporteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AlertaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.email.CorreoElectronico;
import ec.com.codesoft.codefaclite.utilidades.email.PropiedadCorreo;
import ec.com.codesoft.codefaclite.utilidades.email.SmtpNoExisteException;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class AlertaService extends UnicastRemoteObject implements Serializable,AlertaServiceIf {
    
    

    public AlertaService() throws RemoteException {
        super(ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED);
    }
    
    public void procesoBloqueado(Empresa empresaIf) throws RemoteException,ServicioCodefacException
    {
        ParametroCodefacService service=new ParametroCodefacService();
        while(true)
        {
            //service.getParametrosMap(empresaIf);
            service.getParametroByNombre(ParametroCodefac.ACTIVAR_NOTA_VENTA, empresaIf);
            //System.out.println("Consultando map");
        }
    }
    
    public List<AlertaResponse> actualizarNotificacionesCargaRapida(Sucursal sucursal) throws RemoteException,ServicioCodefacException
    {
        List<AlertaResponse> alertas=new ArrayList<AlertaResponse>();
        alertas.add(obtenerNotificacionComprobantesElectronicos(sucursal.getEmpresa()));
        alertas.add(obtenerNotificacionComprobantesElectronicosBaseDatos(sucursal.getEmpresa()));
        alertas.add(obtenerNotificacionFechaLimiteFirma(sucursal.getEmpresa()));
        alertas.add(obtenerCantidadInventarioMinimo(sucursal.getEmpresa()));
        alertas.add(obtenerAlertaDirectorioRespaldo(sucursal.getEmpresa()));
        alertas.add(obtenerAlertaIvaSinConfigurar(sucursal.getEmpresa()));
        alertas.add(obtenerAlertaStockProductosPorCaducar(sucursal));
        alertas.add(obtenerCuentasPorCobrarPorCaducar(sucursal));
        //alertas.add(obtenerCuentasPorCobrarPorCaducar(sucursal));
        alertas.add(obtenerNotificacionProblemasConEnvioRespaldo(sucursal.getEmpresa()));
        alertas.add(obtenerNotificacionFechaRespaldo(sucursal.getEmpresa()));
        alertas=UtilidadesLista.eliminarReferenciaNulas(alertas);
        
        
        
        return alertas;        
    }
    
    private AlertaResponse obtenerAlertaStockProductosPorCaducar(Sucursal sucursal)
    {
        String diasStr=ParametroUtilidades.obtenerValorParametro(sucursal.getEmpresa(),ParametroCodefac.ALERTA_DIAS_POR_CADUCAR_PRODUCTO);
        if(!UtilidadesTextos.verificarNullOVacio(diasStr))
        {
            try {
                Integer dias=Integer.parseInt(diasStr)+1;
                java.sql.Date fechaHoy=UtilidadesFecha.getFechaHoy();
                java.sql.Date fechaComparar=UtilidadesFecha.castDateUtilToSql(UtilidadesFecha.sumarDiasFecha(fechaHoy,dias));
                
                Integer cantidadProductoCaducados=ServiceFactory.getFactory().getLoteSeviceIf().reporteFechaCaducidadTotal(sucursal,null, fechaComparar);
                if(cantidadProductoCaducados!=null && cantidadProductoCaducados>0)
                {
                    AlertaResponse alertaRespuesta=new AlertaResponse(AlertaResponse.TipoAdvertenciaEnum.ALERTA,cantidadProductoCaducados+" Productos por caducar"," productos por caducar");
                    return alertaRespuesta;
                }                
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(AlertaService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(AlertaService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
        
    }
    
    private AlertaResponse obtenerCuentasPorCobrarPorCaducar(Sucursal sucursal)
    {
    
        try {
            Integer numeroDias= ParametroUtilidades.obtenerValorParametroInteger(sucursal.getEmpresa(), ParametroCodefac.DIAS_CREDITO_ALERTA,0);
            
            Long cantidadCartera = ServiceFactory.getFactory().getCarteraServiceIf().listaCarteraSaldoCeroTamanio(
                    null,
                    null,
                    null,
                    null,
                    DocumentoCategoriaEnum.COMPROBANTES_VENTA,
                    Cartera.TipoCarteraEnum.CLIENTE,
                    Cartera.TipoSaldoCarteraEnum.CON_SALDO,
                    Cartera.TipoOrdenamientoEnum.POR_PREIMPRESO,
                    CarteraEstadoReporteEnum.VENCIDA,
                    sucursal,
                    null,
                    numeroDias);
            
            if(cantidadCartera>0)
            {
                AlertaResponse alertaRespuesta=new AlertaResponse(AlertaResponse.TipoAdvertenciaEnum.ALERTA,cantidadCartera+" Cuentas de cobrar "," por caducar");
                return alertaRespuesta;
            }
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(AlertaService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(AlertaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    private AlertaResponse obtenerAlertaIvaSinConfigurar(Empresa empresa)
    {
        try {
            ParametroCodefacService parametroCodefacService=new ParametroCodefacService();
            ParametroCodefac parametroCodefac= parametroCodefacService.getParametroByNombre(ParametroCodefac.IVA_DEFECTO, empresa);
            if(parametroCodefac!=null)
            {
                if(parametroCodefac.valor!=null && !parametroCodefac.valor.isEmpty())
                {
                    return null;
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(AlertaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        AlertaResponse alertaRespuesta=new AlertaResponse(AlertaResponse.TipoAdvertenciaEnum.GRAVE,"IVA sin configurar","Configurar el IVA");
        return alertaRespuesta;
    }
    
    private AlertaResponse obtenerAlertaDirectorioRespaldo(Empresa empresa) throws RemoteException,ServicioCodefacException 
    {
        String directorioEmpresa=ParametroUtilidades.obtenerValorParametro(empresa, ParametroCodefac.ParametrosRespaldoDB.DIRECTORIO_RESPALDO);
        
        if(directorioEmpresa==null || directorioEmpresa.trim().isEmpty())
        {
            AlertaResponse alertaRespuesta=new AlertaResponse(AlertaResponse.TipoAdvertenciaEnum.GRAVE,"Respaldos sin configurar","No se encuentra configurado los respaldos");
            return alertaRespuesta;
        }
        return null;
         
    }
    
    
    private AlertaResponse obtenerCantidadInventarioMinimo(Empresa empresa) throws RemoteException,ServicioCodefacException 
    {
        Integer cantidadStockMinimo=ServiceFactory.getFactory().getKardexServiceIf().consultarCantidadStockMinimo(empresa);
        if(cantidadStockMinimo>0)
        {
            AlertaResponse alertaRespuesta=new AlertaResponse(AlertaResponse.TipoAdvertenciaEnum.ALERTA,cantidadStockMinimo+" productos con STOCK BAJO","Realizar Pedido");
            return alertaRespuesta;
        }
        
        return null;
         
    }
    
    /**
     * Esta notificacion se encarga de verificar que no pase más de 5 días sin enviar los respaldos 
     * TODO: Por el momento hago que escoja de la misma empresa para evitar problemas cuando se tiene muchas empresas
     * @return 
     */
    private AlertaResponse obtenerNotificacionFechaRespaldo(Empresa empresa)
    {
        String fechaStr=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.ParametrosRespaldoDB.FECHA_ULTIMO_ENVIO_RESPALDO_SISTEMA);
        if(!UtilidadesTextos.verificarNullOVacio(fechaStr))
        {
            java.util.Date fechaUltimaVerificacion = UtilidadesFecha.castStringToDate(fechaStr, ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA);
            int diasDiferencia=UtilidadesFecha.obtenerDistanciaConLaFechaActual(fechaUltimaVerificacion);
            if(diasDiferencia>ParametrosSistemaCodefac.TOLERACION_DIAS_SIN_RESPALDO)
            {
                AlertaResponse alertaRespuesta=new AlertaResponse(AlertaResponse.TipoAdvertenciaEnum.ALERTA,ParametrosSistemaCodefac.TOLERACION_DIAS_SIN_RESPALDO+" días sin enviar RESPALDOS","Llamar Soporte");
                return alertaRespuesta;
            }
        }
        return null;
        //ParametrosSistemaCodefac.TOLERACION_DIAS_SIN_RESPALDO;
    }
    
    private AlertaResponse obtenerNotificacionProblemasConEnvioRespaldo(Empresa empresa) throws RemoteException,ServicioCodefacException 
    {
        if(ParametroUtilidades.comparar(empresa, ParametroCodefac.ParametrosRespaldoDB.PROBLEMA_ULTIMO_ENVIO_RESPALDO,EnumSiNo.SI))
        {
            AlertaResponse alerta=new AlertaResponse(AlertaResponse.TipoAdvertenciaEnum.GRAVE,"Error Enviar Último Respaldo","Llamar Soporte");
            return alerta;
        }
                
        return null;
         
    }
        
    public List<AlertaResponse> actualizarNotificacionesCargaLenta(Empresa empresa,ModoProcesarEnum modoEnum) throws RemoteException,ServicioCodefacException
    {
        List<AlertaResponse> alertas=new ArrayList<AlertaResponse>();
        alertas.add(verificarProblemasCorreo(empresa,modoEnum));
        alertas.add(verificarConexionSri(empresa));
        
        alertas=UtilidadesLista.eliminarReferenciaNulas(alertas);
        
        return alertas;  
    }
    
    public List<AlertaResponse> actualizarNotificaciones(Sucursal sucursal,ModoProcesarEnum modoEnum) throws RemoteException,ServicioCodefacException
    {
        List<AlertaResponse> alertas=new ArrayList<AlertaResponse>();
        alertas.addAll(actualizarNotificacionesCargaRapida(sucursal));
        alertas.addAll(actualizarNotificacionesCargaLenta(sucursal.getEmpresa(),modoEnum)); 
        
        return alertas;        
    }
    
    private AlertaResponse verificarProblemasCorreo(Empresa empresa,ModoProcesarEnum modoEnum) throws RemoteException,ServicioCodefacException
    {
        // Si el tipo de proceso es normal verifico que paso un día para volver a verificar el correo
        if(modoEnum.equals(ModoProcesarEnum.NORMAL))
        {
            String fechaStr=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.FECHA_VALIDACION_CORREO);
            if(fechaStr!=null && !fechaStr.isEmpty())
            {
                java.util.Date fechaUltimaVerificacion= UtilidadesFecha.castStringToDate(fechaStr,ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA);
                int diasDiferencia=UtilidadesFecha.obtenerDistanciaConLaFechaActual(fechaUltimaVerificacion);
                //Si la fecha corresponde al mismo día no hago el resto de la validación
                if(diasDiferencia==0)
                {
                    return null;
                }

            }
            
        }
         
        String correo=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.CORREO_USUARIO);
        String clave=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.CORREO_CLAVE);
        String host=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.SMTP_HOST);
        String puerto=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.SMTP_PORT);
        
        if(correo==null || correo.isEmpty() || clave==null || clave.isEmpty())
        {
            return new AlertaResponse(
                    AlertaResponse.TipoAdvertenciaEnum.GRAVE, 
                    "Sin configurar correo para enviar comprobantes ", 
                    "Configurar correo");
        }
        
        AlertaResponse alertaRespuesta=new AlertaResponse(AlertaResponse.TipoAdvertenciaEnum.GRAVE,"Error desconocido","Verificar credenciales");
        try {
            clave=UtilidadesEncriptar.desencriptar(clave,ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
        } catch (Exception ex) {
            ex.printStackTrace();
            alertaRespuesta.descripcion="Error desencriptando la clave";
            return alertaRespuesta;
        }
                
        PropiedadCorreo propiedadCorreo=new PropiedadCorreo(host, Integer.valueOf(puerto));
        
        
        final String textoVerificacion="Mensaje de comprobación del sistema codefac para verificar que esta sincronizado con su correo, por favor no responder este correo";
        try {
            List<String> correos = new ArrayList<String>();
            correos.add(ParametrosSistemaCodefac.CORREO_COMPROBACION_CORREOS);
            CorreoElectronico correoElectronico = new CorreoElectronico(correo,"Codefac Sistema", new String(clave),textoVerificacion, correos, "Validación Correo Codefac",propiedadCorreo);
            correoElectronico.sendMail();
            //Grabar la fecha de la ultima validación correcta del correo
            ServiceFactory.getFactory().getParametroCodefacServiceIf().grabarOEditar(
                    empresa,
                    ParametroCodefac.FECHA_VALIDACION_CORREO,
                    UtilidadesFecha.formatDate(UtilidadesFecha.hoy(),ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA));
            
        } catch (AuthenticationFailedException ex) {
            ex.printStackTrace();
            alertaRespuesta.descripcion="Las credenciales de su correo son incorrectas";
            return alertaRespuesta;

        } catch (MessagingException ex) {
            ex.printStackTrace();
            alertaRespuesta.descripcion="Los datos del correo ingresados son incorrectos.\n"+ex.getMessage();
            return alertaRespuesta;
        } catch (SmtpNoExisteException ex) {
            ex.printStackTrace();
            alertaRespuesta.descripcion="Fallo en el correo al autentificar el usuario";
            return alertaRespuesta;
        }
        return null;
    }
    
    private AlertaResponse obtenerNotificacionFechaLimiteFirma(Empresa empresa) throws RemoteException,ServicioCodefacException
    {
        
        try {
            String valorFechaEmision=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.FIRMA_FECHA_EMISION);
            if(valorFechaEmision!=null && !valorFechaEmision.isEmpty())
            {
                Date fechaEmisionFirma=ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA.parse(valorFechaEmision);
                
                String duracionFirmaStr=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.FIRMA_TIEMPO_EXPIRACION_AÑOS);
                if(duracionFirmaStr!=null && !duracionFirmaStr.isEmpty())
                {
                    Integer anios=Integer.parseInt(duracionFirmaStr);
                    if(anios>0)
                    {
                        Date fechaLimite=UtilidadesFecha.sumarAniosFecha(fechaEmisionFirma,anios);
                        Date fechaActual=UtilidadesFecha.getFechaHoy();
                        Integer diasFaltantes=UtilidadesFecha.obtenerDistanciaDias(fechaActual, fechaLimite);
                        if(diasFaltantes<30 && diasFaltantes>=0)
                        {
                            return new AlertaResponse(
                                    AlertaResponse.TipoAdvertenciaEnum.ADVERTENCIA,
                                    "Faltan "+diasFaltantes+" días para caducar la firma",
                                    "Tramitar la firma");
                        } else if(diasFaltantes<0)
                        {
                            return new AlertaResponse(
                                    AlertaResponse.TipoAdvertenciaEnum.GRAVE,
                                    "Error "+Math.abs(diasFaltantes)+" días que la firma ya caduco",
                                    "Tramitar la firma");
                        }
                        
                    }
                    
                }
                
            }
            
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * TODO: Este metodo buscaba en el directorio de la computadora el cual estaba demorando mucho
     * y presumiblemente genereba errores porque se quedaban los archivos como abiertos
     * Pueda que me sirva este metodo si por ejemplo el sistema debe funcionar de forma independiente para autorizar otro tipo de documentos
     * 
     * @param empresa
     * @return
     * @throws RemoteException
     * @throws ServicioCodefacException
     * @deprecated
     */
    @Deprecated
    private AlertaResponse obtenerNotificacionComprobantesElectronicos(Empresa empresa) throws RemoteException,ServicioCodefacException
    {
        ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
        //Integer comprobantesFirmadoSinEnviar = comprobanteServiceIf.getComprobantesObjectByFolderCantidad(ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR, empresa);
        //Integer comprobantesEnviadosSinRespuesta = comprobanteServiceIf.getComprobantesObjectByFolderCantidad(ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA, empresa);
         Integer totalComprobantesSinEnviar =comprobanteServiceIf.obtenerTotalComprobantesSinTerminarProcesar(empresa);
        //Integer totalComprobantesSinEnviar = comprobantesFirmadoSinEnviar + comprobantesEnviadosSinRespuesta;

        if (totalComprobantesSinEnviar > 0) {
            //modeloTabla.addRow(new Object[]{TipoAdvertenciaEnum.ADVERTENCIA,totalComprobantesSinEnviar+" Comprobantes de enviar al Sri","Utilizar herramienta enviar"});
            return new AlertaResponse(
                    AlertaResponse.TipoAdvertenciaEnum.ADVERTENCIA,
                    totalComprobantesSinEnviar +" "+ AlertaResponse.ALERTA_COMPROBANTES_PENDIENTES_AUTORIZAR,
                    "Utilizar herramienta enviar");
        }

        return null;
    }
    
    private AlertaResponse obtenerNotificacionComprobantesElectronicosBaseDatos(Empresa empresa) throws RemoteException,ServicioCodefacException
    {
        Integer diasAlerta=ParametroUtilidades.obtenerValorParametroInteger(empresa,ParametroCodefac.DIAS_ALERTA_COMPROBANTES_PENDIENTES,null);
        
        java.sql.Date fechaInicial=null;
        if(diasAlerta!=null)
        {
            java.util.Date fechaTmp=UtilidadesFecha.restarDiasFecha(UtilidadesFecha.getFechaHoy(),diasAlerta);
            fechaInicial=UtilidadesFecha.castDateUtilToSql(fechaTmp);
        }
         
        
        Long totalComprobantesSinProcesar=ServiceFactory.getFactory().getFacturacionServiceIf().obtenerFacturasReporteTamanio(
                    null,
                    fechaInicial,
                    null,
                    ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR,
                    Boolean.FALSE,
                    null,
                    Boolean.FALSE,
                    null,
                    empresa,
                    DocumentoEnum.FACTURA,
                    null,
                    null,
                    null,
                    null);
        
        
        
        if (totalComprobantesSinProcesar > 0) {
            //modeloTabla.addRow(new Object[]{TipoAdvertenciaEnum.ADVERTENCIA,totalComprobantesSinEnviar+" Comprobantes de enviar al Sri","Utilizar herramienta enviar"});
            return new AlertaResponse(
                    AlertaResponse.TipoAdvertenciaEnum.ALERTA,
                    totalComprobantesSinProcesar + " Comprobantes SIN AUTORIZAR",
                    "Utilizar herramienta enviar");
        }

        return null;
    }
    
    private AlertaResponse verificarConexionSri(Empresa empresa) throws RemoteException,ServicioCodefacException
    {
        if(!ServiceFactory.getFactory().getComprobanteServiceIf().verificarDisponibilidadSri(empresa))
        {
            return new AlertaResponse(
                    AlertaResponse.TipoAdvertenciaEnum.ADVERTENCIA, 
                    "Sri no disponible", 
                    "Intentar más tarde");
        }
        return null;
    }
}
