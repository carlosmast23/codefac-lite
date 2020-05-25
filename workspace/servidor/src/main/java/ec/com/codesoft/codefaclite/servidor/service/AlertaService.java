/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.common.AlertaResponse;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AlertaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AtsServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.email.CorreoElectronico;
import ec.com.codesoft.codefaclite.utilidades.email.PropiedadCorreo;
import ec.com.codesoft.codefaclite.utilidades.email.SmtpNoExisteException;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
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

/**
 *
 * @author CARLOS_CODESOFT
 */
public class AlertaService extends UnicastRemoteObject implements Serializable,AlertaServiceIf {

    public AlertaService() throws RemoteException {
    }
    
    public List<AlertaResponse> actualizarNotificacionesCargaRapida(Empresa empresa) throws RemoteException,ServicioCodefacException
    {
        List<AlertaResponse> alertas=new ArrayList<AlertaResponse>();
        alertas.add(obtenerNotificacionComprobantesElectronicos(empresa));
        alertas.add(obtenerNotificacionFechaLimiteFirma(empresa));
        alertas=UtilidadesLista.eliminarReferenciaNulas(alertas);
        
        return alertas;        
    }
    
    public List<AlertaResponse> actualizarNotificacionesCargaLenta(Empresa empresa) throws RemoteException,ServicioCodefacException
    {
        List<AlertaResponse> alertas=new ArrayList<AlertaResponse>();
        alertas.add(verificarProblemasCorreo(empresa));
        alertas.add(verificarConexionSri(empresa));
        
        alertas=UtilidadesLista.eliminarReferenciaNulas(alertas);
        
        return alertas;  
    }
    
    public List<AlertaResponse> actualizarNotificaciones(Empresa empresa) throws RemoteException,ServicioCodefacException
    {
        List<AlertaResponse> alertas=new ArrayList<AlertaResponse>();
        alertas.addAll(actualizarNotificacionesCargaRapida(empresa));
        alertas.addAll(actualizarNotificacionesCargaLenta(empresa)); 
        
        return alertas;        
    }
    
    private AlertaResponse verificarProblemasCorreo(Empresa empresa) throws RemoteException,ServicioCodefacException
    {
        String correo=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.CORREO_USUARIO);
        String clave=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.CORREO_CLAVE);
        String host=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.SMTP_HOST);
        String puerto=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.SMTP_PORT);
        
        if(correo==null || correo.isEmpty() || clave==null || clave.isEmpty())
        {
            return new AlertaResponse(
                    AlertaResponse.TipoAdvertenciaEnum.ADVERTENCIA, 
                    "Sin configurar correo para enviar comprobantes ", 
                    "Configurar correo");
        }
        
        AlertaResponse alertaRespuesta=new AlertaResponse(AlertaResponse.TipoAdvertenciaEnum.ADVERTENCIA,"Error desconocido","Verificar credenciales");
        try {
            clave=UtilidadesEncriptar.desencriptar(clave,ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
        } catch (Exception ex) {
            ex.printStackTrace();
            alertaRespuesta.descripcion="Error desencriptando la clave";
            return alertaRespuesta;
        }
                
        PropiedadCorreo propiedadCorreo=new PropiedadCorreo(host, Integer.valueOf(puerto));
        
        
        final String textoVerificacion="Mensaje de comprobación del sistema codefac, por favor no responder este correo";
        try {
            List<String> correos = new ArrayList<String>();
            correos.add(correo);
            CorreoElectronico correoElectronico = new CorreoElectronico(correo, new String(clave),textoVerificacion, correos, "Validación Correo Codefac",propiedadCorreo);
            correoElectronico.sendMail();
            //TODO: Verificar si se va a dar uso de esta funcionalidad
            //TODO: Agregar una variable para la informacion del consumidor final
            //configurarCorreoDeConsumidorFinal();
            //DialogoCodefac.mensaje("Exito","El correo y la clave son correctos",DialogoCodefac.MENSAJE_CORRECTO);
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
    
    private AlertaResponse obtenerNotificacionComprobantesElectronicos(Empresa empresa) throws RemoteException,ServicioCodefacException
    {
        ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
        List<ComprobanteElectronico> comprobantesFirmadoSinEnviar = comprobanteServiceIf.getComprobantesObjectByFolder(ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR, empresa);
        List<ComprobanteElectronico> comprobantesEnviadosSinRespuesta = comprobanteServiceIf.getComprobantesObjectByFolder(ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA, empresa);

        Integer totalComprobantesSinEnviar = comprobantesFirmadoSinEnviar.size() + comprobantesEnviadosSinRespuesta.size();

        if (totalComprobantesSinEnviar > 0) {
            //modeloTabla.addRow(new Object[]{TipoAdvertenciaEnum.ADVERTENCIA,totalComprobantesSinEnviar+" Comprobantes de enviar al Sri","Utilizar herramienta enviar"});
            return new AlertaResponse(
                    AlertaResponse.TipoAdvertenciaEnum.ADVERTENCIA,
                    totalComprobantesSinEnviar + " Comprobantes de enviar al Sri",
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
