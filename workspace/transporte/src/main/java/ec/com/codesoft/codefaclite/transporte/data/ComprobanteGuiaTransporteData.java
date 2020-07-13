/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.transporte.data;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Carlos
 */
public class ComprobanteGuiaTransporteData {
    
    private String preimpreso;
    private String razon_social_persona;
    private String identificacion_persona;
    private String motivo_traslado;
    private String destino;
    
    private JRBeanCollectionDataSource productos;

    public ComprobanteGuiaTransporteData() {
    }

    public String getPreimpreso() {
        return preimpreso;
    }

    public void setPreimpreso(String preimpreso) {
        this.preimpreso = preimpreso;
    }

    public String getRazon_social_persona() {
        return razon_social_persona;
    }

    public void setRazon_social_persona(String razon_social_persona) {
        this.razon_social_persona = razon_social_persona;
    }

    public String getIdentificacion_persona() {
        return identificacion_persona;
    }

    public void setIdentificacion_persona(String identificacion_persona) {
        this.identificacion_persona = identificacion_persona;
    }

    public String getMotivo_traslado() {
        return motivo_traslado;
    }

    public void setMotivo_traslado(String motivo_traslado) {
        this.motivo_traslado = motivo_traslado;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public JRBeanCollectionDataSource getProductos() {
        return productos;
    }

    public void setProductos(JRBeanCollectionDataSource productos) {
        this.productos = productos;
    }
    
    
    
    
    
}
