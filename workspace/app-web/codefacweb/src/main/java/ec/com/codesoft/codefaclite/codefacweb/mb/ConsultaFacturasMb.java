/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class ConsultaFacturasMb implements Serializable{
    private String identificacion;
    
    private List<Factura> listaFacturas;
    
    @PostConstruct
    public void init()  
    {
        
    }
    
    public void consultarCedula()
    {
        try {
            //Factura f;
            //f.getIdentificacion();
            FacturacionServiceIf facturaService = ServiceFactory.getFactory().getFacturacionServiceIf();

            Map<String, Object> mapParametros = new HashMap<String, Object>();
            //mapParametros.put("identifacion", "1724218951001");

            listaFacturas = facturaService.obtenerFacturasPorIdentificacion(identificacion);

            //Usuarios
            mapParametros = new HashMap<String, Object>();
            mapParametros.put("nick", "root");
            UsuarioServicioIf usuarioServiceIf=ServiceFactory.getFactory().getUsuarioServicioIf();
            List<Usuario> usuarios= usuarioServiceIf.obtenerPorMap(mapParametros);
            for (Usuario usuario : usuarios) {
                System.out.println(usuario.getNick());
            }

        } catch (RemoteException ex) {
            Logger.getLogger(ConsultaFacturasMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ConsultaFacturasMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public List<Factura> getListaFacturas() {
        return listaFacturas;
    }

    public void setListaFacturas(List<Factura> listaFacturas) {
        this.listaFacturas = listaFacturas;
    }
    
    
}
