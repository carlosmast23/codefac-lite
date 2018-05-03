/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.compra.busqueda.CompraBusquedaDialogo;
import ec.com.codesoft.codefaclite.compra.panel.RetencionPanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoFacturacionEnumEstado;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class RetencionModel extends RetencionPanel{

    /**
     * Compra seleccionado para enviar la retencion
     */
    private Compra compra;
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        listener();
        cargarDatosEmpresa();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        return "Retención";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listener() {
        listenerBotones();
    }

    private void listenerBotones() {
        getBtnBuscarFacturaCompra().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompraBusquedaDialogo compraBusqueda = new CompraBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(compraBusqueda);
                buscarDialogoModel.setVisible(true);
                
                Compra compraTmp = (Compra) buscarDialogoModel.getResultado();
                if (compraTmp != null) 
                {                    
                    compra=compraTmp;
                    cargarDatosCompra(compra);
                    
                }
            }
            
        });
    }
    
    /**
     * Cargar los datos de la compra en la vista
     * @param compra 
     */
    private void cargarDatosCompra(Compra compra)
    {
        getTxtReferenciaFactura().setText(compra.getPreimpreso());
        getLblNombreCliente().setText(compra.getProveedor().getNombresCompletos());
        getLblTelefonos().setText(compra.getProveedor().getTelefonoCelular());
        getLblDireccion().setText(compra.getProveedor().getDireccion());
        
        ///Cargar los detalles de la compra
        List<CompraDetalle> detalles=compra.getDetalles();
        
        for (CompraDetalle detalle : detalles) 
        {
            
        }
        
       
    }

    private void cargarDatosEmpresa() {
        Empresa empresa=session.getEmpresa();
        getLblRuc().setText(empresa.getIdentificacion());
        getLblNombreComercial().setText(empresa.getNombreLegal());
        getLblDireccion().setText(empresa.getDireccion());
        getLblTelefonos().setText(empresa.getTelefonos());                

        getLblSecuencial().setText(obtenerSecuencial());
        
    }
    
    private String obtenerSecuencial()
    {
         /**
         * Cargar el secuencial segun el modo de facturacion seleccionado
         */
        ParametroCodefac paramTipoFacturacion=session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION);
        TipoFacturacionEnumEstado enumTipoFacturacion=TipoFacturacionEnumEstado.getEnumByEstado(paramTipoFacturacion.getValor());
        
        String secuencial="";
        if(enumTipoFacturacion.equals(TipoFacturacionEnumEstado.NORMAL))
        {
            secuencial=session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_RETENCION_FISICA).getValor();
        }
        else
        {
            if(enumTipoFacturacion.equals(TipoFacturacionEnumEstado.ELECTRONICA))
            {
                secuencial=session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_RETENCION).getValor();
            }
        }
        
        String preimpreso = UtilidadesTextos.llenarCarateresIzquierda(secuencial.toString(), 8, "0");
        String establecimiento = session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor;
        String puntoEmision = session.getParametrosCodefac().get(ParametroCodefac.PUNTO_EMISION).valor;
        preimpreso=puntoEmision + "-" + establecimiento + "-" + preimpreso;
        
        return preimpreso;
    }

}
