/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.license;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import ec.com.codesoft.ejemplo.utilidades.varios.UtilidadVarios;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Carlos
 */
public class Licencia {
    
    public static final String PROPIEDAD_USUARIO="usuario";
    public static final String PROPIEDAD_LICENCIA="licencia";
    public static final String PROPIEDAD_TIPO_LICENCIA="tipo";
    public static final String PROPIEDAD_CANTIDAD_CLIENTES="clientes";
    
    //Modulos adicionales que puede tener la licencia
    public static ModuloCodefacEnum MODULO_INVENTARIO=ModuloCodefacEnum.INVENTARIO;
    public static ModuloCodefacEnum MODULO_GESTION_ACADEMICA=ModuloCodefacEnum.GESTIONA_ACADEMICA;
    public static ModuloCodefacEnum MODULO_FACTURACION=ModuloCodefacEnum.FACTURACION;
    public static ModuloCodefacEnum MODULO_CRM=ModuloCodefacEnum.CRM;
    
    
    private String mac;
    private Properties propiedades;
    private TipoLicenciaEnum tipoLicenciaEnum;
    
    
    private String usuario;
    private String licencia;
    //private EnumTipo tipoLicencia;
    private Integer cantidadClientes;
    
    private Boolean moduloInventario;
    private Boolean moduloGestionAcademica;
    private Boolean moduloFacturacion;
    private Boolean moduloCrm;

    public Licencia() {
    }
    
    

    public Licencia(Properties propiedades) {
        this.propiedades = propiedades;
        this.mac=obtenerMac();
        this.tipoLicenciaEnum=TipoLicenciaEnum.getEnumByNombre(propiedades.getProperty(PROPIEDAD_TIPO_LICENCIA));        
    }
    
    
    public String obtenerMac()
    {
        //UtilidadVarios.obtenerMac();
        return UtilidadVarios.obtenerMacSinInternet();
    }
    
    public boolean validarLicencia()
    {
        cargarLicenciaFisica(propiedades);
        String tipoLicencia=propiedades.getProperty(PROPIEDAD_TIPO_LICENCIA);

        //cargarModulosOffline();ver si cargado todos los datos oofline

        String modulosStr=getModulosStr(); //Obtiene los valores de los modulos activos con una cadena para validar la licencia
               
        try
        {
            if(tipoLicencia.equals(TipoLicenciaEnum.GRATIS.getNombre()))
            {

                //Validacion cuando el usuario si esta registrado con licencia gratis 
                this.tipoLicenciaEnum=TipoLicenciaEnum.GRATIS;
                return BCrypt.checkpw(usuario + ":" + mac + ":" + TipoLicenciaEnum.GRATIS.getLetra()+":"+cantidadClientes+":"+modulosStr, licencia);
                                
            }
            else
            {
                if(tipoLicencia.equals(TipoLicenciaEnum.PRO.getNombre()))
                {
                    this.tipoLicenciaEnum=TipoLicenciaEnum.PRO;
                    return BCrypt.checkpw(usuario+":"+mac+":"+TipoLicenciaEnum.PRO.getLetra()+":"+cantidadClientes+":"+modulosStr,licencia);
                }
            }
            
            //Validacion por defecto cuando no existe tipo licencia
            return false;
        }
        catch(java.lang.IllegalArgumentException iae)
        {
            return false;
        }

    }
    
    /**
     * Devuelve una cadena construida con los modulos para verificar la licencia
     * @return 
     */
    public String getModulosStr()
    {
        ModuloCodefacEnum[] modulos=ModuloCodefacEnum.values();
        String modulosStr="";
        Map<ModuloCodefacEnum,Boolean> modulosMap=getModulosSistema();
        //El orden de verificar los modulos es importante porque siempre lee y verifica la licencia de esa forma
        for (ModuloCodefacEnum modulo : modulos) 
        {
            Boolean activo=modulosMap.get(modulo);
            if(activo!=null)
            {
                modulosStr+=(activo?"s":"n");
            }   
            
        }       

        return modulosStr;
    }
    
    /*
    private String getRespuestaModulo(String modulo)
    {
        String valorPropiedad= propiedades.getProperty(modulo);
        
        String respuesta="n";
        if(valorPropiedad!=null && valorPropiedad.equals("si"))
        {
            respuesta="s";
        }
        return respuesta;
    }*/

    /*
    public TipoLicenciaEnum getTipoLicenciaPropertyEnum() {
        //String usuario=propiedades.getProperty(PROPIEDAD_USUARIO);
        //String licencia=propiedades.getProperty(PROPIEDAD_LICENCIA);
        String tipoLicencia=propiedades.getProperty(PROPIEDAD_TIPO_LICENCIA);
        
        return TipoLicenciaEnum.getEnumByNombre(tipoLicencia);
        
    }*/

    public void cargarLicenciaOnline(String usuario)
    {
        this.usuario=usuario;
        this.licencia = WebServiceCodefac.getLicencia(usuario);
        this.tipoLicenciaEnum =tipoLicenciaEnum.getEnumByLetra(WebServiceCodefac.getTipoLicencia(usuario));
        this.cantidadClientes = WebServiceCodefac.getCantidadClientes(usuario);
        cargarModulosOnline();
    }
    
    public void cargarModulosOnline() {
        this.moduloInventario = castStringBooleanOnline(WebServiceCodefac.getModuloInventario(usuario));
        this.moduloGestionAcademica = castStringBooleanOnline(WebServiceCodefac.getModuloGestionAcademica(usuario));
        this.moduloFacturacion = castStringBooleanOnline(WebServiceCodefac.getModuloFacturacion(usuario));
        this.moduloCrm = castStringBooleanOnline(WebServiceCodefac.getModuloCrm(usuario));
    }
    
    public void cargarModulosOffline() {
        this.moduloInventario = castStringBooleanOnline(WebServiceCodefac.getModuloInventario(usuario));
        this.moduloGestionAcademica = castStringBooleanOnline(WebServiceCodefac.getModuloGestionAcademica(usuario));
        this.moduloFacturacion = castStringBooleanOnline(WebServiceCodefac.getModuloFacturacion(usuario));
        this.moduloCrm = castStringBooleanOnline(WebServiceCodefac.getModuloCrm(usuario));
    }
    
    
    public void cargarLicenciaFisica(Properties propiedades)
    {      
        this.usuario = propiedades.getProperty(Licencia.PROPIEDAD_USUARIO);
        this.licencia = propiedades.getProperty(Licencia.PROPIEDAD_LICENCIA);
        this.tipoLicenciaEnum = TipoLicenciaEnum.getEnumByNombre(propiedades.getProperty(Licencia.PROPIEDAD_TIPO_LICENCIA));
        this.cantidadClientes = Integer.parseInt(propiedades.getProperty(Licencia.PROPIEDAD_CANTIDAD_CLIENTES));
        this.moduloInventario = castStringBooleanProperty(propiedades.getProperty(Licencia.MODULO_INVENTARIO.getNombrePropiedad()));
        this.moduloGestionAcademica = castStringBooleanProperty(propiedades.getProperty(Licencia.MODULO_GESTION_ACADEMICA.getNombrePropiedad()));
        this.moduloFacturacion = castStringBooleanProperty(propiedades.getProperty(Licencia.MODULO_FACTURACION.getNombrePropiedad()));
        this.moduloCrm = castStringBooleanProperty(propiedades.getProperty(Licencia.MODULO_CRM.getNombrePropiedad()));
    }
    
    /**
     * Comparar la licencia con otra
     * @param licencia
     * @return 
     */
    public boolean compararOtraLicencia(Licencia licencia)
    {
        if (this.licencia.equals(licencia.getLicencia())
                && this.tipoLicenciaEnum.equals(licencia.getTipoLicenciaEnum())
                && this.cantidadClientes.equals(licencia.getCantidadClientes())
                && this.moduloInventario.equals(licencia.isModuloInventario())
                && this.moduloGestionAcademica.equals(licencia.isModuloGestionAcademica())
                && this.moduloFacturacion.equals(licencia.isModuloFacturacion())
                && this.moduloCrm.equals(licencia.isModuloCrm())) {
            return true;
        }
        return false;
    }
    
    //Metodo para converitir de String a boolean
    private boolean castStringBooleanOnline(String opcion)
    {
        if(opcion!=null && opcion.equals("s"))
        {
            return true;
        }
        return false;
    }
    
        //Metodo para converitir de String a boolean
    private boolean castStringBooleanProperty(String opcion)
    {
        if(opcion!=null && opcion.equals("si"))
        {
            return true;
        }
        return false;
    }
    
    public Map<ModuloCodefacEnum,Boolean> getModulosSistema()
    {
        Map<ModuloCodefacEnum,Boolean> modulosMap=new HashMap<ModuloCodefacEnum,Boolean>();
        modulosMap.put(MODULO_CRM, moduloCrm);
        modulosMap.put(MODULO_FACTURACION, moduloFacturacion);
        modulosMap.put(MODULO_GESTION_ACADEMICA, moduloGestionAcademica);
        modulosMap.put(MODULO_INVENTARIO, moduloInventario);
        return modulosMap;    
    }
    
    public void llenarPropertiesModulo(Properties propiedades)
    {
        Map<ModuloCodefacEnum,Boolean> modulosMap=getModulosSistema();
        ModuloCodefacEnum[] modulos=ModuloCodefacEnum.values();
        
        //Llenar en orden dependiendo que modulos estan activos
        for (ModuloCodefacEnum modulo : modulos) {
            Boolean activo=modulosMap.get(modulo);
            if(activo!=null)
            {
                propiedades.setProperty(modulo.getNombrePropiedad(),(activo?"si":"no"));
            }            
        }
        
    }
    
    
    
    ////METODOS GET AND SET //////////////////////
    
    public Properties getPropiedades() {
        return propiedades;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }
/*
    public String getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }*/

    public Integer getCantidadClientes() {
        return cantidadClientes;
    }

    public void setCantidadClientes(Integer cantidadClientes) {
        this.cantidadClientes = cantidadClientes;
    }

    public boolean isModuloInventario() {
        return moduloInventario;
    }

    public void setModuloInventario(boolean moduloInventario) {
        this.moduloInventario = moduloInventario;
    }

    public boolean isModuloGestionAcademica() {
        return moduloGestionAcademica;
    }

    public void setModuloGestionAcademica(boolean moduloGestionAcademica) {
        this.moduloGestionAcademica = moduloGestionAcademica;
    }

    public boolean isModuloFacturacion() {
        return moduloFacturacion;
    }

    public void setModuloFacturacion(boolean moduloFacturacion) {
        this.moduloFacturacion = moduloFacturacion;
    }

    public boolean isModuloCrm() {
        return moduloCrm;
    }

    public void setModuloCrm(boolean moduloCrm) {
        this.moduloCrm = moduloCrm;
    }

    public void setTipoLicenciaEnum(TipoLicenciaEnum tipoLicenciaEnum) {
        this.tipoLicenciaEnum = tipoLicenciaEnum;
    }

    public TipoLicenciaEnum getTipoLicenciaEnum() {
        return tipoLicenciaEnum;
    }
    
    
    
    
    
    

    
}
