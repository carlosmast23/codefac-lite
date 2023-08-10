/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.model;

import ec.com.codesoft.codefaclite.controlador.panel.AuditoriaInformacionPanel;
import java.awt.Frame;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class AuditoriaInformacionModel extends AuditoriaInformacionPanel{
    
    public AuditoriaInformacionModel() 
    {
         super(null,true);
    }
    
    public AuditoriaInformacionModel(String fechaIngreso, String fechaEdicion, String usuarioIngreso, String usuarioEdicion) 
    {
         super(null,true);
         setearDatos(fechaIngreso, fechaEdicion, usuarioIngreso, usuarioEdicion);
    }
    
    
    public void setearDatos(String fechaIngreso, String fechaEdicion, String usuarioIngreso, String usuarioEdicion) 
    {
        getLblFechaCreacion().setText(fechaIngreso);
        getLblFechaEdicion().setText(fechaEdicion);
        
        getLblUsuarioCreacion().setText(usuarioIngreso);
        getLblUsuarioEdicion().setText(usuarioEdicion);
        
    }
    
    
}
