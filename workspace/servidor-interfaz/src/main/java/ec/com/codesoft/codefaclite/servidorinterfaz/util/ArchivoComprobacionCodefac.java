/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.util;


import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase me va a permitir representar un archivo que va a tener datos de comprobacion para poder verificar la integridad de la informacion por ejjemplo como el último secuencial de las facturas
 * @author home
 */
public class ArchivoComprobacionCodefac extends ArchivoConfiguracion{
    
    private final static String SEPARADOR="-"; 
    private static ArchivoComprobacionCodefac instanciaEstatica;
    
    private static final String NOMBRE_ARCHIVO_CONFIGURACION = "comprobacion.codefac";
    
    private List<PuntoEmision> puntoEmisionCorregidoList;
    
    @Override
    public String getNombreArchivo() {
        return NOMBRE_ARCHIVO_CONFIGURACION;
    }
    /**
     * Metodo que por el momento va a corregir en la base de datos los secuenciales para que puedan seguir trabajando
     */
    public void corregirDatosComprobacion()
    {
        //TODO: Mejorar esta parte creando un servicio para mandar directamente una lista al servicio en el servidor
        for (PuntoEmision puntoEmision : puntoEmisionCorregidoList) {
            try {
                ServiceFactory.getFactory().getPuntoVentaServiceIf().editar(puntoEmision);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(ArchivoComprobacionCodefac.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(ArchivoComprobacionCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public List<String> comprobarIntegridadDatos()
    {
        puntoEmisionCorregidoList=new ArrayList<PuntoEmision>();
        List<String> errores=new ArrayList<String>();
        HashMap<String,String> mapValores= obtenerTodosLosValores();
         
        //PuntoEmision puntoEmision=null;
        for (Map.Entry<String, String> entry : mapValores.entrySet()) 
        {
            try {
                String clave = entry.getKey();
                String valor = entry.getValue();
                
                Integer secuencialArchivo=Integer.valueOf(valor);
                //Bigde
                String[] claveDatos= clave.split(SEPARADOR);
                Long idPuntoEmision=Long.valueOf(claveDatos[0]);
                String nombreDocumento=claveDatos[1];
                
                //Consultar si es el mismo punto de emision o tengo que consultar otro
                PuntoEmision puntoEmision=buscarPuntoEmisionPorId(idPuntoEmision);
                if(puntoEmision==null)
                {               
                    //Si no existe el punto de emision entonces consulto de nuevo
                    puntoEmision=ServiceFactory.getFactory().getPuntoVentaServiceIf().buscarPorId(idPuntoEmision);
                }
                
                
                
                Integer secuencial=null;
                if(nombreDocumento.equals(DocumentoEnum.FACTURA.getNombre()))
                {
                    secuencial=puntoEmision.getSecuencialFactura();
                    puntoEmision.setSecuencialFactura(secuencialArchivo);
                }
                else if(nombreDocumento.equals(DocumentoEnum.NOTA_VENTA_INTERNA.getNombre()))
                {
                    secuencial=puntoEmision.getSecuencialNotaVentaInterna();
                    puntoEmision.setSecuencialNotaVentaInterna(secuencialArchivo);
                }
                else if(nombreDocumento.equals(DocumentoEnum.NOTA_VENTA.getNombre()))
                {
                    secuencial=puntoEmision.getSecuencialNotaVenta();
                    puntoEmision.setSecuencialNotaVenta(secuencialArchivo);
                }
                else if(nombreDocumento.equals(DocumentoEnum.RETENCIONES.getNombre()))
                {
                    secuencial=puntoEmision.getSecuencialRetenciones();
                    puntoEmision.setSecuencialRetenciones(secuencialArchivo);
                }
                else if(nombreDocumento.equals(DocumentoEnum.GUIA_REMISION.getNombre()))
                {
                    secuencial=puntoEmision.getSecuencialGuiaRemision();
                    puntoEmision.setSecuencialGuiaRemision(secuencialArchivo);
                }
                else if(nombreDocumento.equals(DocumentoEnum.NOTA_CREDITO.getNombre()))
                {
                    secuencial=puntoEmision.getSecuencialNotaCredito();
                    puntoEmision.setSecuencialNotaCredito(secuencialArchivo);
                }
                
                compararSecuencialConDocumento(nombreDocumento, secuencialArchivo, secuencial, errores);
                
                //Gragar el punto de emision corregido para ver si luego quiere actualizar esos datos
                agregarPuntoEmisionCorregido(puntoEmision);

                
            } catch (RemoteException ex) {
                Logger.getLogger(ArchivoComprobacionCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return errores;
    }
    
    private PuntoEmision buscarPuntoEmisionPorId(Long id)
    {
        for (PuntoEmision puntoEmision : puntoEmisionCorregidoList) {
            if(puntoEmision.getId().equals(id))
            {
                return puntoEmision;
            }
        }
        return null;
    }
   
    private void agregarPuntoEmisionCorregido(PuntoEmision puntoEmision)
    {
        int indiceElemento=puntoEmisionCorregidoList.indexOf(puntoEmision);
        if(indiceElemento!=-1)
        {
            puntoEmisionCorregidoList.remove(indiceElemento);
            //Si ya encuentra el dato actualizar
            //puntoEmisionCorregidoList.set(indiceElemento, puntoEmision);
        }
        puntoEmisionCorregidoList.add(puntoEmision);
        //else
        //{
            
        //}
    }
    
    private void compararSecuencialConDocumento(String nombreDocumento,Integer secuencialArchivo,Integer secuencialBaseDatos,List<String> errores)
    {
        if (!secuencialBaseDatos.equals(secuencialArchivo)) {
            Logger.getLogger(ArchivoComprobacionCodefac.class.getName()).log(Level.SEVERE,"Error de comprobacion secuencial: "+nombreDocumento+" | SecuencialDB: "+secuencialBaseDatos+" | SecuencialArchivo: "+secuencialArchivo);
            errores.add(nombreDocumento + "[" + (secuencialArchivo-secuencialBaseDatos) + "]");
        }
    }
    
    public void grabarDatosComprobacion()
    {
        try {
            List<PuntoEmision> puntoEmisionList= ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerTodosActivos();
            
            for (PuntoEmision puntoEmision : puntoEmisionList) 
            {
                agregarCampo(puntoEmision.getId().toString().concat(SEPARADOR).concat(DocumentoEnum.FACTURA.getNombre()),puntoEmision.getSecuencialFactura()+"");
                agregarCampo(puntoEmision.getId().toString().concat(SEPARADOR).concat(DocumentoEnum.NOTA_VENTA_INTERNA.getNombre()),puntoEmision.getSecuencialNotaVentaInterna()+"");
                agregarCampo(puntoEmision.getId().toString().concat(SEPARADOR).concat(DocumentoEnum.NOTA_VENTA.getNombre()),puntoEmision.getSecuencialNotaVenta()+"");
                agregarCampo(puntoEmision.getId().toString().concat(SEPARADOR).concat(DocumentoEnum.RETENCIONES.getNombre()),puntoEmision.getSecuencialRetenciones()+"");
                agregarCampo(puntoEmision.getId().toString().concat(SEPARADOR).concat(DocumentoEnum.GUIA_REMISION.getNombre()),puntoEmision.getSecuencialGuiaRemision()+"");
                agregarCampo(puntoEmision.getId().toString().concat(SEPARADOR).concat(DocumentoEnum.NOTA_CREDITO.getNombre()),puntoEmision.getSecuencialNotaCredito()+"");
                
            }
            guardar();
            
        } catch (ServicioCodefacException | RemoteException ex) {
            Logger.getLogger(ArchivoComprobacionCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArchivoComprobacionCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArchivoComprobacionCodefac getInstance()
    {
        if(instanciaEstatica==null)
        {
            instanciaEstatica=new ArchivoComprobacionCodefac();
        }
        return instanciaEstatica;
    }
    
    public enum CampoEnum implements CampoIf
    {
        SECUENCIAL_FACTURA("factura");
        
        private String nombre;

        private CampoEnum(String nombre) {
            this.nombre = nombre;
        }
        

        @Override
        public String getNombreCampo() {
            return nombre;
        }
    }
    
    
}
