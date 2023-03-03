/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.util;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.CorreoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class RespaldosModelUtilidades {
    
    /**
     * 
     * @param enviarCorreo
     * @param empresa
     * @param correo
     * @param directorioTmp
     * @param cuentaInternaSistema , se refiere cuando se quiere enviar desde la cuenta del programa de gmail
     * @throws ServicioCodefacException 
     */
    public static void generarRespaldoUbicacion(Boolean enviarCorreo,Empresa empresa,String correo,Boolean cuentaInternaSistema) throws ServicioCodefacException
    {
        try
                {
                    String ubicacionRespaldo=obtenerUbicacionCarpetaRespaldo(empresa,cuentaInternaSistema);
                    if(!ubicacionRespaldo.equals(""))
                    {
                        String nombreCarpetaRelpaldo=crearNombreCarpetaRespaldo();
                        String nombreCarpetaFirma=crearNombreCarpetaRespaldo("firma");
                        
                        RecursosServiceIf service=ServiceFactory.getFactory().getRecursosServiceIf();                        
                        InputStream inputDb = RemoteInputStreamClient.wrap(service.getDataBaseResources());                        
                        InputStream inputFirma = RemoteInputStreamClient.wrap(service.getFirmaElectronicaResources(empresa));
                        
                        Path destinoPath = FileSystems.getDefault().getPath(ubicacionRespaldo+"\\"+nombreCarpetaRelpaldo+".zip");
                        Path firmaPath = FileSystems.getDefault().getPath(ubicacionRespaldo+"\\"+nombreCarpetaFirma+".zip");
                        //File recursosDirectorio = origenPath.toFile();
                        File destinoDirectorio = destinoPath.toFile();
                        
                        List<File> destinarioList=new ArrayList<File>();
                        destinarioList.add(destinoDirectorio);
                        destinarioList.add(firmaPath.toFile());
                        //Utilizar una funciona estandar en utilidades
                        copiarDirectorio(inputDb, destinoDirectorio);
                        copiarDirectorio(inputFirma, firmaPath.toFile());
                        
                        //List<File> destinoDirectorioList=new ArrayList<File>();
                        //destinoDirectorioList.add(destinoDirectorio);
                        
                        
                        if(enviarCorreo)
                        {
                            
                            enviarRespaldoCorreoEmpresa(destinarioList,empresa,correo,cuentaInternaSistema);
                        }
                    }
                    else
                    {
                        //DialogoCodefac.mensaje("Advertencia", "Debe seleccionar una ubicación para los respaldos", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        throw new ServicioCodefacException("Debe seleccionar una ubicación para los respaldos");
                    }
                }catch(Exception exc)
                {
                    //DialogoCodefac.mensaje(new CodefacMsj(exc.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                    exc.printStackTrace();                    
                    throw new ServicioCodefacException(exc.getMessage());
                }
    }
    
    public static void enviarRespaldoCorreoEmpresa(List<File> fileRespaldoList,Empresa empresa,String correoEmpresa,Boolean cuentaInternaSistema) throws ServicioCodefacException
    {

        try {
            String fechaStr=ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA.format(UtilidadesFecha.getFechaHoy());
            CorreoCodefac correoCodefac = new CorreoCodefac();
            String tituloCorreo="Respaldo BaseDatos "+fechaStr+" [ "+empresa.getIdentificacion()+" - "+empresa.obtenerNombreEmpresa()+" ]" ;
            String mensajeCorreo="El respaldo de la base de datos de Codefac de la fecha "+fechaStr+" lo puede descargar como archivo adjunto.";
            mensajeCorreo=mensajeCorreo+"\n Versión: "+ParametrosSistemaCodefac.VERSION;
            
            
            if(correoEmpresa==null)
            {
                ParametroCodefac parametroCorreo=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.CORREO_USUARIO, empresa);
                correoEmpresa=parametroCorreo.valor;
            }
            
            //String correoEmpresa=parametroCorreo.valor;
            //String correoEmpresa=session.getParametrosCodefac().get(ParametroCodefac.CORREO_USUARIO).valor;
            List correosList=Arrays.asList(correoEmpresa);
            
            //Map<String,String> mapArchivosAdjuntos=Collections.singletonMap(fileRespaldo.getName(),fileRespaldo.getPath());}
            
            Map<String,String> mapArchivosAdjuntos=new HashMap<String,String>();
            for (File file : fileRespaldoList) 
            {
                mapArchivosAdjuntos.put(file.getName(),file.getPath());
            }
            
            if(cuentaInternaSistema)
            {
                correoCodefac.enviarCorreoCuentaSistema(empresa, mensajeCorreo, tituloCorreo, correosList, mapArchivosAdjuntos);
            }
            else
            {
                correoCodefac.enviarCorreo(empresa,mensajeCorreo,tituloCorreo,correosList,mapArchivosAdjuntos);


                //Si los datos son correctos grabo cuando fue la última modificación de la fecha
                ServiceFactory.getFactory().getParametroCodefacServiceIf().grabarOEditar(
                    empresa,
                    ParametroCodefac.ParametrosRespaldoDB.FECHA_ULTIMO_ENVIO_RESPALDO_SISTEMA,
                    UtilidadesFecha.formatDate(UtilidadesFecha.hoy(),ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA));
                
                ServiceFactory.getFactory().getParametroCodefacServiceIf().grabarOEditar(
                        empresa,
                        ParametroCodefac.ParametrosRespaldoDB.PROBLEMA_ULTIMO_ENVIO_RESPALDO,
                        EnumSiNo.NO.getLetra());
                
            }
        } catch (CorreoCodefac.ExcepcionCorreoCodefac ex) {
            Logger.getLogger(RespaldosModelUtilidades.class.getName()).log(Level.SEVERE, null, ex);
            
            //GRABAR EL PARAMETRO DE ADVERTENCIA PARA INFORMAR QUE NO HAYA NOVEDADES AL HACER EL RESPALDO
            try {
                ServiceFactory.getFactory().getParametroCodefacServiceIf().grabarOEditar(
                        empresa,
                        ParametroCodefac.ParametrosRespaldoDB.PROBLEMA_ULTIMO_ENVIO_RESPALDO,
                        EnumSiNo.SI.getLetra());
            } catch (RemoteException ex2) {
                Logger.getLogger(RespaldosModelUtilidades.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            throw new ServicioCodefacException(ex.getMessage());
            //DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            
        } catch (RemoteException ex) {
            Logger.getLogger(RespaldosModelUtilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
    private static void copiarDirectorio(InputStream origen, File destino) throws IOException 
    {
        FileUtils.copyInputStreamToFile(origen,destino);        
    }
    
    private static String crearNombreCarpetaRespaldo()
    {
        return crearNombreCarpetaRespaldo("");
    }
    
    private static String crearNombreCarpetaRespaldo(String nombre)
    {
        String nombreCarpetaRelpaldo = nombre + new Date();
        nombreCarpetaRelpaldo = nombreCarpetaRelpaldo.replaceAll(" ","");
        nombreCarpetaRelpaldo = nombreCarpetaRelpaldo.replaceAll(":","");
        return nombreCarpetaRelpaldo;
    }
    
    public static String obtenerUbicacionCarpetaRespaldo(Empresa empresa,Boolean directorioTmp)
    {
        try {
            
            if(directorioTmp)
            {
                String directorioTempStr=ParametrosSistemaCodefac.CARPETA_DATOS_TEMPORALES; 
                File fileDestino = new File(directorioTempStr); 
                //crear toda la ruta si no existe
                if (!fileDestino.exists()) {
                    fileDestino.getParentFile().mkdirs();
                    //file.mkdir();
                }
                return fileDestino.getAbsolutePath();                
            }
            else
            {
            
                ParametroCodefac parametroDirectorioRespaldo= ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.ParametrosRespaldoDB.DIRECTORIO_RESPALDO, empresa);
                //this.parametro = this.parametroCodefacServiceIf.getParametrosMap(session.getEmpresa());
                //ParametroCodefac p = this.parametro.get(ParametroCodefac.DIRECTORIO_RESPALDO);
                if(parametroDirectorioRespaldo!=null && parametroDirectorioRespaldo.getValor() != null)
                {
                    String ubicacionRespaldo = parametroDirectorioRespaldo.getValor();
                    //getTxtUbicacionRespaldo().setText(parametroDirectorioRespaldo.getValor());
                    //this.existeDirectorio = true;
                    return ubicacionRespaldo;
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(RespaldosModelUtilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
}
