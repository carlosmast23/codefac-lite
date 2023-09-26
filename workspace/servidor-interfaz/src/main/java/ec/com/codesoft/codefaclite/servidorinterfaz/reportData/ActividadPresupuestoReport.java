/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.reportData;

import java.util.Vector;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ActividadPresupuestoReport extends ReportDataAbstract<ActividadPresupuestoData>
{

    public ActividadPresupuestoReport(String tituloReporte) {
        super(tituloReporte);
    }

    @Override
    public String[] getTitulos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void construirFilaTabla(ActividadPresupuestoData dato, Vector<Object> fila) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
