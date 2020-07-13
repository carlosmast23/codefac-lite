/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.compra.callback.RetencionImplCallBack;
import ec.com.codesoft.codefaclite.compra.panel.RetencionesPendientePanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.utilidades.ComprobanteElectronicoComponente;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.eclipse.jdt.internal.compiler.lookup.MostSpecificExceptionMethodBinding;

/**
 *
 * @author Carlos
 */
public class RetencionesPendienteModel extends RetencionesPendientePanel{
    
    private DefaultTableModel modeloTablaComprasPendientes;
    private Retencion retencion;
    private Compra.RetencionEnumCompras estadoRetencion;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        retencion = new Retencion();
        cargarComprasPendientes();
        addListener();
        
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
        ComprobanteElectronicoComponente.cargarSecuencial(session.getUsuario(),DocumentoEnum.RETENCIONES,session.getSucursal(), getCmbPuntoEmision(), getLblEstablecimiento(), getLblSecuencial());
    }

//    @Override
    public String getNombre() {
        return "Retenciones Pendientes";
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

    private void cargarComprasPendientes() 
    {
        try {
            CompraServiceIf service=ServiceFactory.getFactory().getCompraServiceIf();
            List<Compra> compras = service.obtenerCompraDisenable();
            //List<Compra> compras = service.getCompraRetencionDisenable(); 
            DefaultTableModel datos= UtilidadesTablas.crearModeloTabla(new String[]{"obj","Preimpreso","Ruc","Proveedor"},new Class[]{Compra.class,String.class,String.class,String.class});
            for (Compra compra : compras) {
                Vector<Object> fila = new Vector<>();
                fila.add(compra);
                fila.add(compra.getPreimpreso());
                fila.add(compra.getProveedor().getIdentificacion());
                fila.add(compra.getProveedor().getNombresCompletos());
                datos.addRow(fila);
            }
            getTblComprasPendientes().setModel(datos);
            UtilidadesTablas.ocultarColumna(getTblComprasPendientes(), 0);
        } catch (RemoteException ex) {
            Logger.getLogger(RetencionesPendienteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addListener()
    {
        getCmbPuntoEmision().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComprobanteElectronicoComponente.cargarSecuencial(session.getUsuario(),DocumentoEnum.RETENCIONES,session.getSucursal(), getCmbPuntoEmision(), getLblEstablecimiento(), getLblSecuencial());
            }
        });
        
        //Evento para botones
        getBtnEnviarRetencion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int filaCompraPendiente = getTblComprasPendientes().getSelectedRow();
                Compra compra = (Compra) getTblComprasPendientes().getValueAt(filaCompraPendiente,0);
                retencion.setCompra(compra);
                cargarCorreoPorDefecto(compra);
                Boolean confirmacion = DialogoCodefac.dialogoPregunta("Alerta", "Está seguro que desea realizar la retención?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                if (confirmacion) {
                    enviar();
                    ComprobanteElectronicoComponente.cargarSecuencial(session.getUsuario(),DocumentoEnum.RETENCIONES,session.getSucursal(), getCmbPuntoEmision(), getLblEstablecimiento(), getLblSecuencial());
                }
            
            }
        });
        //Evento para tabla Compras Pendientes
        getTblComprasPendientes().addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) 
            {
                int filaComprasPendientes = getTblComprasPendientes().getSelectedRow();
                Compra compra = (Compra) getTblComprasPendientes().getValueAt(filaComprasPendientes,0);
                List<CompraDetalle> compraDetalles = compra.getDetalles();
                DefaultTableModel datos= UtilidadesTablas.crearModeloTabla( new String[]{"Obj","Nombre","Retencia Iva","Retención Renta"},
                                                                            new Class[]{CompraDetalle.class,String.class,String.class,String.class});
                
                BigDecimal totalRetencionIva = new BigDecimal(BigInteger.ZERO);
                BigDecimal totalRetencionRenta = new BigDecimal(BigInteger.ZERO);
                BigDecimal totalRetenciones = new BigDecimal(BigInteger.ZERO);
                
                for (CompraDetalle compraDetalle : compraDetalles) 
                {
                    Vector<Object> fila = new Vector<>();
                    fila.add(compraDetalle);
                    fila.add(compraDetalle.getDescripcion());
                    fila.add(compraDetalle.getValorSriRetencionIVA());
                    fila.add(compraDetalle.getValorSriRetencionRenta());
                    totalRetencionIva = totalRetencionIva.add(compraDetalle.getValorSriRetencionIVA());
                    totalRetencionRenta = totalRetencionRenta.add(compraDetalle.getValorSriRetencionRenta());
                    datos.addRow(fila);
                }
                
                //Suma de retencion Iva y retencion Renta
                totalRetenciones = totalRetenciones.add(totalRetencionIva).add(totalRetencionRenta);
                
                //Redondear valores a mostrar en pantalla a dos decimales
                totalRetencionIva=totalRetencionIva.setScale(2,BigDecimal.ROUND_HALF_UP);
                totalRetencionRenta= totalRetencionRenta.setScale(2,BigDecimal.ROUND_HALF_UP);
                totalRetenciones=totalRetenciones.setScale(2,BigDecimal.ROUND_HALF_UP);
                
                //Mostrar valores de retencion en pantall
                getLabelTotalRetencionIva().setText(totalRetencionIva+"");
                getLabelTotalRetencionRenta().setText(totalRetencionRenta+"");
                getLabelTotalRetenciones().setText(totalRetenciones+"");
                
                //Cargar detalles de cada compra y valores de retencion en las tablas
                getTblDetalleRetenciones().setModel(datos);
                
                //Ocultar la primera columna de la tabla
                UtilidadesTablas.ocultarColumna(getTblDetalleRetenciones(), 0);
                
            }
        });
    }
    
    private void enviar()
    {
        try {
            setearDatos();
            estadoRetencion = Compra.RetencionEnumCompras.EMITIDO;
            retencion.getCompra().setEstadoRetencion(estadoRetencion.getEstado());
            retencion=ServiceFactory.getFactory().getRetencionServiceIf().grabar(retencion);
            DialogoCodefac.mensaje("Correcto","La retenecion fue grabada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            CompraServiceIf service=ServiceFactory.getFactory().getCompraServiceIf();
            service.editar(retencion.getCompra()); //Permite hacer mofici
            
            cargarComprasPendientes();
            RetencionImplCallBack ric=new RetencionImplCallBack(retencion, this);
            ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
            ComprobanteDataRetencion comprobanteData = new ComprobanteDataRetencion(retencion);
            comprobanteData.setMapInfoAdicional(getMapAdicional(retencion));
            comprobanteServiceIf.procesarComprobante(comprobanteData, retencion, session.getUsuario(), ric);
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RetencionesPendienteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(RetencionesPendienteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Map<String,String> getMapAdicional(Retencion retencion)
    {
        Map<String,String> parametroMap=new HashMap<String ,String>();
        for (RetencionAdicional datoAdicional : retencion.getDatosAdicionales()) 
        {
            parametroMap.put(datoAdicional.getCampo(),datoAdicional.getValor());
        }
        return parametroMap;
    }
    
    private void cargarCorreoPorDefecto(Compra compra)
    {
        //limpiar el detalle de datos adicionales
        if(retencion.getDatosAdicionales()!=null)
            retencion.getDatosAdicionales().clear();
        
        //Solo cargar si existe el correo y es distinto de vacio        
        if(compra.getProveedor().getCorreoElectronico()!=null && !compra.getProveedor().getCorreoElectronico().equals(""))
        {
            retencion.addDatosAdicionalCorreo(compra.getProveedor().getCorreoElectronico());
        }
    }
    
    private PuntoEmision obtenerPuntoEmisionSeleccionado()
    {
        return (PuntoEmision) getCmbPuntoEmision().getSelectedItem();
    }
    
       
    
    private void setearDatos() {
        //retencion=new Retencion();
        //retencion.setCompra(compra);
        retencion.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        retencion.setFechaEmision(new java.sql.Date(getjDateFechaEmision().getDate().getTime()));
        retencion.setProveedor(retencion.getCompra().getProveedor());
        retencion.setEmpresa(session.getEmpresa());
        
        PuntoEmision puntoEmisionSeleccionado= obtenerPuntoEmisionSeleccionado();
        retencion.setPuntoEmision(puntoEmisionSeleccionado.getPuntoEmision());
        retencion.setPuntoEstablecimiento(new BigDecimal(puntoEmisionSeleccionado.getSucursal().getCodigoSucursal().toString()));
        retencion.setTipoDocumento(TipoDocumentoEnum.COMPRA.getCodigo()); //TODO: Falta definir exactamente el tipo de documento que se va a usar
        retencion.setFechaEmisionDocumento(retencion.getCompra().getFechaFactura());
        retencion.setUsuario(session.getUsuario());
        
        //Cuando la facturacion es electronica
        /*if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).getValor().equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra()))
        {
            retencion.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_RETENCION).valor));
        }
        else //cuando la facturacion es normal
        {
            retencion.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_RETENCION_FISICA).valor));
        }*/
        
        //Llenar los detalles de la retencion
        for (CompraDetalle compraDetalle : retencion.getCompra().getDetalles()) {
            
            //Todo: Falta hacer validaciones cuando hay detalles que no requerien enviar retencion con iva 0
            
            //Detalle para la retencion del iva
            RetencionDetalle retencionDetalleIva=new RetencionDetalle();
            retencionDetalleIva.setBaseImponible(compraDetalle.getBaseImponibleIva());
            retencionDetalleIva.setCodigoSri(compraDetalle.getSriRetencionIva().getRetencion().getCodigo());
            retencionDetalleIva.setCodigoRetencionSri(compraDetalle.getSriRetencionIva().getCodigo().toString());
            retencionDetalleIva.setPorcentajeRetener(compraDetalle.getSriRetencionIva().getPorcentaje().setScale(2,BigDecimal.ROUND_HALF_UP));
            retencionDetalleIva.setRetencion(retencion);
            retencionDetalleIva.setValorRetenido(compraDetalle.getValorSriRetencionIVA());
            System.out.println(compraDetalle.getValorSriRetencionIVA());
            
            //Detalle para la retencion de la renta
            RetencionDetalle retencionDetalleRenta=new RetencionDetalle();
            retencionDetalleRenta.setBaseImponible(compraDetalle.getBaseImponibleRenta());
            retencionDetalleRenta.setCodigoSri(compraDetalle.getSriRetencionRenta().getRetencion().getCodigo());
            retencionDetalleRenta.setCodigoRetencionSri(compraDetalle.getSriRetencionRenta().getCodigo().toString());
            retencionDetalleRenta.setPorcentajeRetener(compraDetalle.getSriRetencionRenta().getPorcentaje().setScale(2,BigDecimal.ROUND_HALF_UP));
            retencionDetalleRenta.setRetencion(retencion);
            retencionDetalleRenta.setValorRetenido(compraDetalle.getValorSriRetencionRenta());
            
            retencion.addDetalle(retencionDetalleIva);
            retencion.addDetalle(retencionDetalleRenta);
        }
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}
