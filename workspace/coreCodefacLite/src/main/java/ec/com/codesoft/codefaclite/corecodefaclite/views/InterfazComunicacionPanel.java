/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.views;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public interface InterfazComunicacionPanel 
{
    public void crearReportePantalla(JasperPrint jasperPrint,String nombrePantalla);
    public void crearVentanaCodefac(GeneralPanelInterface panel,boolean maximizado);
    
    /**
     * Agregado metodo que envia parametros al Post Constructor de la ventana a iniciar
     */
    public void crearVentanaCodefac(VentanaEnum ventanEnum,boolean maximizado,Object[] parametrosPostConstructor);
    /**
     * 
     * @param panel formulario que se va a actualizar los datos
     * @param namePanel nombre del panel que se quiere abrir para obtener el dato
     * @param maximizado opcion para saber si quieres que se abra maximizado o mimimizado
     */
    public void crearDialogoCodefac(ObserverUpdateInterface panel,String namePanel,boolean maximizado,GeneralPanelInterface panelPadre);
    
    /**
     * Me permite crear una un dialogo pasando como referencia un enum con la ventana del formulario
     * @param ventanEnum
     * @param namePanel
     * @param maximizado 
     */
    public void crearDialogoCodefac(ObserverUpdateInterface panel,VentanaEnum ventanEnum,boolean maximizado,GeneralPanelInterface panelPadre);
    
    /**
     * Metodo que permite crear un dialogo pero con datos para el postCostructorExterno
     * @param panel
     * @param ventanEnum
     * @param maximizado 
     */
    public void crearDialogoCodefac(ObserverUpdateInterface panel,VentanaEnum ventanEnum,boolean maximizado,Object[] parametrosPostConstructor,GeneralPanelInterface panelPadre);
    
    public Map<String,Object> mapReportePlantilla(OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte);
    /**
     * Metodo que permite validar todos los componentes del formulario
     * @param nombre
     * @return 
     */
    public boolean validarPorGrupo(String nombre);
    /**
     * Si solo se desea validar un solo componente
     * @param nombre nombre del grupo de componentes a validar
     * @param componente nombre del componente a validar
     * @return 
     */
    public boolean validarPorGrupo(String nombre,String componente);
    
    /**
     * Funcion que permite establecer el fondo de pantalla
     */
    public void establecerImagenFondo();
    
    /**
     * Interfaz que me va a permitir poner el cursor de toda la aplicacione en espera
     * @param frame 
     */
    public void cambiarCursorEspera();
    
    /**
     * Interfaz que me permite volver a establecer el curso de forma normal
     * @param frame 
     */
    public void cambiarCursorNormal();
    
    public void cambiarEstadoFormularioEditar(GeneralPanelInterface frame);
    public void cambiarEstadoFormularioNuevo(GeneralPanelInterface frame);
    
    /**
     * Metodo que permite actualizar las notificaciones en la pantalla del escritorio
     */
    public void actualizarNotificacionesCodefac();
    


}
