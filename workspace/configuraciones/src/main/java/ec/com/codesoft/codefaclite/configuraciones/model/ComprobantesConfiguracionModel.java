/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.ComprobantesConfiguracionPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidor.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidor.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoService;
import ec.com.codesoft.codefaclite.servidor.service.ParametroCodefacService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class ComprobantesConfiguracionModel extends ComprobantesConfiguracionPanel
{
    private Map<String,ParametroCodefac> parametros ;
    private ParametroCodefacService servicio;

    public ComprobantesConfiguracionModel() {
        this.servicio=new ParametroCodefacService();
        cargarDatosIva();
        cargarDatosConfiguraciones();
        
        /**
         * Desactivo el ciclo de vida para controlar manualmente
         */
        super.cicloVida=false;
        
    }
    
    

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        actualizarDatosVista();
        this.servicio.editarParametros(parametros);
        /**
         * Establesco el ciclo de vida en el cual me encuentro
         */
        this.estadoFormulario=GeneralPanelInterface.ESTADO_GRABAR;
        DialogoCodefac.mensaje("Actualizado datos","Los datos de los parametros fueron actualizados",DialogoCodefac.MENSAJE_CORRECTO);
        
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        super.estadoFormulario=GeneralPanelInterface.ESTADO_EDITAR;
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer,Boolean> permisos=new HashMap<Integer,Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR,true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }
    
    private void actualizarDatosVista()
    {
        //parametros.put(ParametroCodefac.SECUENCIAL_FACTURA,getTxtFacturaSecuencial().getText());
        ParametroCodefac param=null;
        parametros.get(ParametroCodefac.SECUENCIAL_FACTURA).setValor(getTxtFacturaSecuencial().getText());
        parametros.get(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).setValor(getTxtNotaCreditoSecuencial().getText());
        parametros.get(ParametroCodefac.SECUENCIAL_NOTA_DEBITO).setValor(getTxtNotaDebitoSecuencial().getText());
        parametros.get(ParametroCodefac.SECUENCIAL_GUIA_REMISION).setValor(getTxtGuiaRemisionSecuencial().getText());
        parametros.get(ParametroCodefac.SECUENCIAL_RETENCION).setValor(getTxtRetencionesSecuencial().getText());
        
        String ivaDefacto=((ImpuestoDetalle)getCmbIvaDefault().getSelectedItem()).getTarifa().toString();
        
        parametros.get(ParametroCodefac.IVA_DEFECTO).setValor(ivaDefacto);
        
        
    }
    
    private void cargarDatosConfiguraciones()
    {        
        parametros=servicio.getParametrosMap();
        ParametroCodefac param=parametros.get(ParametroCodefac.SECUENCIAL_FACTURA);
        getTxtFacturaSecuencial().setText(parametros.get(ParametroCodefac.SECUENCIAL_FACTURA).getValor());
        getTxtGuiaRemisionSecuencial().setText(parametros.get(ParametroCodefac.SECUENCIAL_GUIA_REMISION).getValor());
        getTxtNotaCreditoSecuencial().setText(parametros.get(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).getValor());
        getTxtNotaDebitoSecuencial().setText(parametros.get(ParametroCodefac.SECUENCIAL_NOTA_DEBITO).getValor());
        getTxtGuiaRemisionSecuencial().setText(parametros.get(ParametroCodefac.SECUENCIAL_GUIA_REMISION).getValor());
        getTxtRetencionesSecuencial().setText(parametros.get(ParametroCodefac.SECUENCIAL_RETENCION).getValor());
        getTxtDirectorioRecurso().setText(parametros.get(ParametroCodefac.DIRECTORIO_RECURSOS).getValor());
        
        //ImpuestoDetalle id=new ImpuestoDetalle();
        //id.getImpuesto().getIdImpuesto();
        
        ImpuestoDetalleService servicioImpuesto=new ImpuestoDetalleService();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("tarifa",Integer.parseInt(parametros.get(ParametroCodefac.IVA_DEFECTO).getValor()));
        //map.put("tarifa",12);
        List<ImpuestoDetalle> lista= servicioImpuesto.buscarImpuestoDetallePorMap(map);
        getCmbIvaDefault().getModel().setSelectedItem(lista.get(0));
        
        
        /*
        getTxtCorreoElectronico().setText(parametros.get(ParametroCodefac.CORREO_USUARIO).getValor());
        getTxtPasswordCorreo().setText(parametros.get(ParametroCodefac.CORREO_CLAVE).getValor());
        getTxtNombreFirma().setText(parametros.get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).getValor());
        getTxtClaveFirma().setText(parametros.get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).getValor());
        */
        
    }
    
    private void cargarDatosIva()
    {
        ImpuestoService impuestoService=new ImpuestoService();
        Impuesto iva=impuestoService.obtenerImpuestoPorCodigo(Impuesto.IVA);
        for (ImpuestoDetalle impuesto : iva.getDetalleImpuestos()) {
            getCmbIvaDefault().addItem(impuesto);
        }
    }
    
}
