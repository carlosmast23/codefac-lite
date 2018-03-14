/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.views;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.util.Map;
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
     * 
     * @param panel formulario que se va a actualizar los datos
     * @param namePanel nombre del panel que se quiere abrir para obtener el dato
     * @param maximizado opcion para saber si quieres que se abra maximizado o mimimizado
     */
    public void crearDialogoCodefac(ObserverUpdateInterface panel,String namePanel,boolean maximizado);
    
    /**
     * Me permite crear una un dialogo pasando como referencia un enum con la ventana del formulario
     * @param ventanEnum
     * @param namePanel
     * @param maximizado 
     */
    public void crearDialogoCodefac(ObserverUpdateInterface panel,VentanaEnum ventanEnum,boolean maximizado);
    
    public Map<String,Object> mapReportePlantilla();
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


}
