/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.controller;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PermisoVentana;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AccesoDirectoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AulaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CatalogoProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CategoriaProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteFisicoDisenioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpresaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexItemEspecificoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NacionalidadServiceIf;
//import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.MatriculaEstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilServicioIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PeriodoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PermisoVentanaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoEnsambleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubrosNivelServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriIdentificacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UtilidadesServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraCruceServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.RubroPlantillaEstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.RubroPlantillaServiceIf;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public abstract class ServiceFactory {
    
    /**
     * Puerto mediante el cual nos vamos a comunicar con el servidor por RMI
     */
    public static final int PUERTO_SERVIDOR=1099;
    public String ipServidor="192.168.1.3";
    public static ServiceFactory serviceController;
    
    /**
     * Obtiene la instancia del controlador actual
     * @return 
     */
    public static ServiceFactory getFactory()
    {
        return serviceController;
    }
    
        /**
     * Metodos que devuelven los servicios     *      * 
     */
    public ProductoServiceIf getProductoServiceIf(){return (ProductoServiceIf) getRecursosRMI(ProductoServiceIf.class);};
    public PersonaServiceIf getPersonaServiceIf(){return (PersonaServiceIf) getRecursosRMI(PersonaServiceIf.class);};
    public ParametroCodefacServiceIf getParametroCodefacServiceIf(){return (ParametroCodefacServiceIf) getRecursosRMI(ParametroCodefacServiceIf.class);};
    public UtilidadesServiceIf getUtilidadesServiceIf(){return (UtilidadesServiceIf) getRecursosRMI(UtilidadesServiceIf.class);};
    public AccesoDirectoServiceIf getAccesoDirectoServiceIf(){return (AccesoDirectoServiceIf) getRecursosRMI(AccesoDirectoServiceIf.class);};
    public BodegaServiceIf getBodegaServiceIf(){return (BodegaServiceIf) getRecursosRMI(BodegaServiceIf.class);};
    public CategoriaProductoServiceIf getCategoriaProductoServiceIf(){return (CategoriaProductoServiceIf) getRecursosRMI(CategoriaProductoServiceIf.class);};
    public CompraDetalleServiceIf getCompraDetalleServiceIf(){return (CompraDetalleServiceIf) getRecursosRMI(CompraDetalleServiceIf.class);};
    public CompraServiceIf getCompraServiceIf(){return (CompraServiceIf) getRecursosRMI(CompraServiceIf.class);};
    public ComprobanteFisicoDisenioServiceIf getComprobanteFisicoDisenioServiceIf(){return (ComprobanteFisicoDisenioServiceIf) getRecursosRMI(ComprobanteFisicoDisenioServiceIf.class);};
    public EmpresaServiceIf getEmpresaServiceIf(){return (EmpresaServiceIf) getRecursosRMI(EmpresaServiceIf.class);};
    public FacturacionServiceIf getFacturacionServiceIf(){return (FacturacionServiceIf) getRecursosRMI(FacturacionServiceIf.class);};
    public ImpuestoDetalleServiceIf getImpuestoDetalleServiceIf(){return (ImpuestoDetalleServiceIf) getRecursosRMI(ImpuestoDetalleServiceIf.class);};
    public ImpuestoServiceIf getImpuestoServiceIf(){return (ImpuestoServiceIf) getRecursosRMI(ImpuestoServiceIf.class);};
    public KardexDetalleServiceIf getKardexDetalleServiceIf(){return (KardexDetalleServiceIf) getRecursosRMI(KardexDetalleServiceIf.class);};
    public KardexItemEspecificoServiceIf getItemEspecificoServiceIf(){return (KardexItemEspecificoServiceIf) getRecursosRMI(KardexItemEspecificoServiceIf.class);};
    public KardexServiceIf getKardexServiceIf(){return (KardexServiceIf) getRecursosRMI(KardexServiceIf.class);};
    public NotaCreditoServiceIf getNotaCreditoServiceIf(){return (NotaCreditoServiceIf) getRecursosRMI(NotaCreditoServiceIf.class);};
    public PerfilServicioIf getPerfilServicioIf(){return (PerfilServicioIf) getRecursosRMI(PerfilServicioIf.class);};
    public ProductoEnsambleServiceIf getProductoEnsambleServiceIf(){return (ProductoEnsambleServiceIf) getRecursosRMI(ProductoEnsambleServiceIf.class);};
    public ProductoProveedorServiceIf getProductoProveedorServiceIf(){return (ProductoProveedorServiceIf) getRecursosRMI(ProductoProveedorServiceIf.class);};
    public SriIdentificacionServiceIf getSriIdentificacionServiceIf(){return (SriIdentificacionServiceIf) getRecursosRMI(SriIdentificacionServiceIf.class);};
    public SriServiceIf getSriServiceIf(){return (SriServiceIf) getRecursosRMI(SriServiceIf.class);};
    public UsuarioServicioIf getUsuarioServicioIf(){return (UsuarioServicioIf) getRecursosRMI(UsuarioServicioIf.class);};
    public ComprobanteServiceIf getComprobanteServiceIf(){return (ComprobanteServiceIf) getRecursosRMI(ComprobanteServiceIf.class);};
    public RecursosServiceIf getRecursosServiceIf(){return (RecursosServiceIf) getRecursosRMI(RecursosServiceIf.class);};
    public AulaServiceIf getAulaServiceIf(){return (AulaServiceIf) getRecursosRMI(AulaServiceIf.class);};
    public EstudianteServiceIf getEstudianteServiceIf(){return (EstudianteServiceIf) getRecursosRMI(EstudianteServiceIf.class);};
    //public MatriculaEstudianteServiceIf getMatriculaEstudianteServiceIf(){return (MatriculaEstudianteServiceIf) getRecursosRMI(MatriculaEstudianteServiceIf.class);};
    public NivelAcademicoServiceIf getNivelAcademicoServiceIf(){return (NivelAcademicoServiceIf) getRecursosRMI(NivelAcademicoServiceIf.class);};
    public NivelServiceIf getNivelServiceIf(){return (NivelServiceIf) getRecursosRMI(NivelServiceIf.class);};
    public PeriodoServiceIf getPeriodoServiceIf(){return (PeriodoServiceIf) getRecursosRMI(PeriodoServiceIf.class);};
    public RubrosNivelServiceIf getRubrosNivelServiceIf(){return (RubrosNivelServiceIf) getRecursosRMI(RubrosNivelServiceIf.class);};
    public PermisoVentanaServiceIf getPermisoVentanaServiceIf(){return (PermisoVentanaServiceIf) getRecursosRMI(PermisoVentanaServiceIf.class);};
    public EstudianteInscritoServiceIf getEstudianteInscritoServiceIf(){return (EstudianteInscritoServiceIf) getRecursosRMI(EstudianteInscritoServiceIf.class);};
    public RubroEstudianteServiceIf getRubroEstudianteServiceIf(){return (RubroEstudianteServiceIf) getRecursosRMI(RubroEstudianteServiceIf.class);};
    public NacionalidadServiceIf getNacionalidadServiceIf(){return (NacionalidadServiceIf) getRecursosRMI(NacionalidadServiceIf.class);};
    public CatalogoProductoServiceIf getCatalogoProductoServiceIf(){return (CatalogoProductoServiceIf) getRecursosRMI(CatalogoProductoServiceIf.class);};
    public CarteraServiceIf getCarteraServiceIf(){return (CarteraServiceIf) getRecursosRMI(CarteraServiceIf.class);};
    public CarteraDetalleServiceIf getCarteraDetalleServiceIf(){return (CarteraDetalleServiceIf) getRecursosRMI(CarteraDetalleServiceIf.class);};
    public CarteraCruceServiceIf getCarteraCruceServiceIf(){return (CarteraCruceServiceIf) getRecursosRMI(CarteraCruceServiceIf.class);};
    public RubroPlantillaServiceIf getRubroPlantillaServiceIf(){return (RubroPlantillaServiceIf) getRecursosRMI(RubroPlantillaServiceIf.class);};
    public RubroPlantillaEstudianteServiceIf getRubroPlantillaEstudianteServiceIf(){return (RubroPlantillaEstudianteServiceIf) getRecursosRMI(RubroPlantillaEstudianteServiceIf.class);};
    
    
    /**
     * Crea una nueva instancia el controlados para manejar por el cliente
     * @param ipServidor 
     */
    public static void newController(String ipServidor)
    {
        serviceController=new ServiceFactory(ipServidor) {};
    }
    
    
    private Map<Class,Remote> mapRecursosRMI;

    private ServiceFactory(String ipServidor) 
    {
        this.ipServidor=ipServidor;
        this.mapRecursosRMI=new HashMap<Class,Remote>();
    }
    
    
    /**
     * Metodo que permite crear u obtener el servicio atravez de RMI
     * @param clase
     * @return 
     */
    public Remote getRecursosRMI(Class clase)
    {
        Remote remote= mapRecursosRMI.get(clase);
        
        if(remote==null)
        {
            try {
                Registry registro= LocateRegistry.getRegistry(ipServidor,PUERTO_SERVIDOR);    
                remote= registro.lookup("rmi://"+ipServidor+":"+PUERTO_SERVIDOR+"/"+clase.getSimpleName());
                mapRecursosRMI.put(clase,remote);
                
                
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null,"Error de conexión con el servidor","Error",JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ServiceFactory.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(0);
            } catch (NotBoundException ex) {
                Logger.getLogger(ServiceFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return remote;
    }
    
     
    
    
}
