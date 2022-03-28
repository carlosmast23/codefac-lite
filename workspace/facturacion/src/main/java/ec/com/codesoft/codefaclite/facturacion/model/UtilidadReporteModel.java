/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DellWin10
 */
public class UtilidadReporteModel extends FacturaReporteModel
{

    @Override
    protected void generarReporteConLecturaParametros(Date fechaInicio, Date fechaFin) 
    {
        try {
            ServiceFactory.getFactory().getFacturacionServiceIf().consultaUtilidadVentas();
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(UtilidadReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
