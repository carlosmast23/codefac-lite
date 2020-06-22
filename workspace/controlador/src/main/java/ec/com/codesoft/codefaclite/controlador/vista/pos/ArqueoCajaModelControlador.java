/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.pos;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ArqueoCajaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.ArqueoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class ArqueoCajaModelControlador extends ModelControladorAbstract<ArqueoCajaModelControlador.CommonIf, ArqueoCajaModelControlador.SwingIf, ArqueoCajaModelControlador.WebIf> implements VistaCodefacIf
{

    public ArqueoCajaModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, ArqueoCajaModelControlador.CommonIf interfaz, TipoVista tipoVista) 
    {
        super(mensajeVista, session, interfaz, tipoVista);
    }
     /**
    * Metodo iniciar
    * @throws java.rmi.RemoteException
    * @throws ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException
    */ 
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        
        getInterfaz().setArqueoCaja(new ArqueoCaja());
        getInterfaz().getArqueoCaja().setEstado("aaa");
        System.out.println("Arqueo caja iniciar: " + getInterfaz().getArqueoCaja().getEstado());
        long date = new java.util.Date().getTime();
        getInterfaz().setFechaRevision(new Date(date));
        getInterfaz().setEstadosGeneralesVista(GeneralEnumEstado.values());
        getInterfaz().setValorFisico(BigDecimal.ZERO);
        getInterfaz().setValorTeorico("");
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        getInterfaz().setArqueoCaja(new ArqueoCaja());
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        System.out.println("Arqueo caja guardar: " + getInterfaz().getArqueoCaja().getEstado());
        try
        {
            if(getInterfaz().getArqueoCaja() != null){
                throw new ServicioCodefacException("Arqueo caja error");
            }
            if(getInterfaz().getValorFisico()!=null ){
                throw new ServicioCodefacException("Valor fisico error");
            }
            if(getInterfaz().getValorTeorico() != null){
                throw new ServicioCodefacException("Valor Teorico error");
            }
            if(getInterfaz().getHoraRevision() != null){
                throw new ServicioCodefacException("Fecha y Hora Revision error");
            }
            if(getInterfaz().getEstado() != null){
                throw new ServicioCodefacException("Estado error");
            }
            //Obtener datos
            obtenerDatos();
            //Grabar
            ServiceFactory.getFactory().getArqueoCajaServiceIf().grabar(getInterfaz().getArqueoCaja());
            //Mensaje
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            
        }catch(ServicioCodefacException e)
        {
            mostrarMensaje(new CodefacMsj("Error", e.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO));
            try {
                throw e;
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
        
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {
            //Datos
            obtenerDatos();
            //Editar
            ServiceFactory.getFactory().getArqueoCajaServiceIf().editar(getInterfaz().getArqueoCaja());
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ArqueoCajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try {
            Boolean respuesta = dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO);
            if(!respuesta){
                throw new ServicioCodefacException("Error eliminando Arqueo Caja");
            }
            ServiceFactory.getFactory().getArqueoCajaServiceIf().eliminar(getInterfaz().getArqueoCaja());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ArqueoCajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        ArqueoCajaBusquedaDialogo arqueoCajaBusquedaDialogo = new ArqueoCajaBusquedaDialogo();
        return arqueoCajaBusquedaDialogo;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        ArqueoCaja arqueoCaja = (ArqueoCaja) entidad;
        getInterfaz().setArqueoCaja(arqueoCaja);
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
    public interface CommonIf
    {
        public Date getFechaRevision();
        public void setFechaRevision(Date fechaRevision);
        public Date getHoraRevision();
        public void setHoraRevision(Date fechaRevision);
        //public Timestamp getFechaHoraRevision();
        //public void setFechaHoraRevision(Timestamp timestamp);
        
        public String getValorTeorico();
        public void setValorTeorico(String valorTeorico);
        public BigDecimal getValorFisico();
        public void setValorFisico(BigDecimal valorFisico);
        
        public GeneralEnumEstado getEstado();
        public void setEstado(GeneralEnumEstado estado);
        
        //Cargar Informacion necesaria
        public void setEstadosGeneralesVista(GeneralEnumEstado[] estados);
        
        public ArqueoCaja getArqueoCaja();
        public void setArqueoCaja(ArqueoCaja arqueoCaja);
        
    }
    
    public interface SwingIf extends ArqueoCajaModelControlador.CommonIf
    {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
    }
    
    public interface WebIf extends ArqueoCajaModelControlador.CommonIf
    {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
    public void obtenerDatos(){
        Date fecha = getInterfaz().getFechaRevision();
        Date hora = getInterfaz().getHoraRevision();
         
        SimpleDateFormat localTimeFormat = new SimpleDateFormat("HH:mm:ss");
        String horaTemp = localTimeFormat.format(hora);
        long horaLong = Long.parseLong(horaTemp);
        fecha.setTime(horaLong);
        
        Timestamp fechaHora = new Timestamp(fecha.getTime());
        
        getInterfaz().getArqueoCaja().setEstadoEnum(getInterfaz().getEstado());
        getInterfaz().getArqueoCaja().setFechaHora(fechaHora);
        getInterfaz().getArqueoCaja().setValorFisico(getInterfaz().getValorFisico());
        getInterfaz().getArqueoCaja().setValorTeorico(getInterfaz().getValorTeorico());
        getInterfaz().getArqueoCaja().setFechaHora(fechaHora);
    }
}
