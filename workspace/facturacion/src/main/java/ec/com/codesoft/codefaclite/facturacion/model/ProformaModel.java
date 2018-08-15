/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;

/**
 *
 * @author Carlos
 */
public class ProformaModel extends FacturacionModel{

    public ProformaModel() {
        valoresIniciales();
    }

    private void valoresIniciales() {
        //nombre de la pantalla
        setTitle("Proforma");
        
        //cargar el documento de proforma
        getCmbDocumento().removeAllItems();
        getCmbDocumento().addItem(DocumentoEnum.PROFORMA);
        
        //descativar el panel formas de pago porque no utilizo
        getPanelFormasPago().setVisible(false);
    }

    @Override
    public void cargarSecuencial() {
        
    }
    
    
    
    
}
