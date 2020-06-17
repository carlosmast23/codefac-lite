/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.pos;

import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.ArqueoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author Robert
 */
public class ArqueoCajaModelControlador extends ModelControladorAbstract<ArqueoCajaModelControlador.Interface, ArqueoCajaModelControlador.Interface, ArqueoCajaModelControlador.Interface>
{

    public ArqueoCajaModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, Interface interfaz, TipoVista tipoVista) 
    {
        super(mensajeVista, session, interfaz, tipoVista);
    }
    
    
    public interface Interface{
        
        public ArqueoCaja getArqueoCaja();
        public void setArqueoCaja(ArqueoCaja arqueoCaja);
        
        public Date getFechaRevision();
        public void setFechaRevision(Date fechaRevision);
        public Date getHoraRevision();
        public void setHoraRevision(Date fechaRevision);
        
        public String getValorTeorico();
        public void setValorTeorico(String valorTeorico);
        public BigDecimal getValorFisico();
        public void setValorFisico(BigDecimal valorFisico);
        
        public GeneralEnumEstado getEstado();
        public void setEstado(GeneralEnumEstado estado);
        
    }
}
