/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.views;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
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


}
