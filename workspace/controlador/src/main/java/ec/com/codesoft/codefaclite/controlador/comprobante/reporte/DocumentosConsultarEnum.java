/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

/**
 *
 * @author Carlos
 */
public enum DocumentosConsultarEnum 
    {
        VENTAS("Ventas"),NOTA_CREDITO("Notas de Crédito");
        
        public String nombre;

        private DocumentosConsultarEnum(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
        
        
    }