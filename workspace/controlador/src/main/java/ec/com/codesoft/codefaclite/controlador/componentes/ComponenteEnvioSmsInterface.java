/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.componentes;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PlantillaSmsEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public interface ComponenteEnvioSmsInterface {
    
    public abstract boolean getValidacionEnvioSms();
    public abstract String getNumeroTelefono();
    public abstract Map<PlantillaSmsEnum.EtiquetaEnum,String> getPlantillaTags();
    public abstract VentanaEnum getVentanaEnum();
    
}
