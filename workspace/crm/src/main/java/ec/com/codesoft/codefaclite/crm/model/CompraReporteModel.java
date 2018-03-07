/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.crm.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.CompraReportePanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CodesoftDesarrollo 1
 */

public class CompraReporteModel extends CompraReportePanel
{
    
    private DefaultTableModel modeloTablaDetallesCompras;
    private Persona proveedor;
    private Date fechaInicio;
    private Date fechaFinal;
    
    private DocumentoEnum documentoEnum;
    private TipoDocumentoEnum tipoDocumentoEnum;
    
    private boolean banderaBusqueda;
    
    //Variables que me permiten realizar la sumatoria de los totales de cada compra
    private BigDecimal subtotal;
    private BigDecimal subtotal0;
    private BigDecimal subtotal12;
    private BigDecimal descuento;
    private BigDecimal iva;
    private BigDecimal total;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarValoresVentana();
        agregarListener();
        crearVariables();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    
    //Permiten inicializar las variables
    private void crearVariables()
    {
        subtotal = new BigDecimal(BigInteger.ZERO);
        subtotal0 = new BigDecimal(BigInteger.ZERO);
        subtotal12 = new BigDecimal(BigInteger.ZERO);
        descuento = new BigDecimal(BigInteger.ZERO);
        iva = new BigDecimal(BigInteger.ZERO);
        total = new BigDecimal(BigInteger.ZERO);
    }
    
    //Valores cargados para presentacion de ventana
    public void iniciarValoresVentana()
    {
        initValoresCombos();
        initModelTablaDetalleCompras();
        getTxtProveedor().setEnabled(false);
    }
    
    //Valores iniciales que tendra la tabla detalle compras
    public void initModelTablaDetalleCompras()
    {
        String titulos[] = {"Preimpreso", "Identificacion", "Nombre", "Fecha", "Subtotal 12%", "Subtotal 0%", "Descuento", "IVA", "TOTAL"};
        modeloTablaDetallesCompras = new DefaultTableModel(titulos,0);
        getTableDetalleCompras().setModel(modeloTablaDetallesCompras);
    }
    
    public void initValoresCombos()
    {
        //Iniciar valores del combo Documento
        getCmbDocumento().removeAllItems();
        List <DocumentoEnum> documentos = DocumentoEnum.obtenerDocumentoPorModulo(ModuloEnum.COMPRAS);
        for( DocumentoEnum documento : documentos)
        {
            getCmbDocumento().addItem(documento);
        }
        
        //Iniciar valores del combo TipoDocumento
        getCmbTipoDocumento().removeAllItems();
        List <TipoDocumentoEnum> tipoDocumentoEnums = TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloEnum.COMPRAS);
        for(TipoDocumentoEnum tipoDocumentoEnum : tipoDocumentoEnums)
        {
            getCmbTipoDocumento().addItem(tipoDocumentoEnum);
        }
    }
    
    public void agregarListener()
    {
        //Permite buscar a un proveedor mediante una ventana
        getBtnBuscarProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorBusquedaDialogo proveedorBusquedaDialogo = new ProveedorBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(proveedorBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                proveedor = (Persona) buscarDialogoModel.getResultado();
                if(proveedor != null)
                {
                    String identificacion = proveedor.getIdentificacion();
                    String nombre = proveedor.getRazonSocial();
                    getTxtProveedor().setText(identificacion + " - " + nombre);
                }
            }
        });
        
        //Permite establecer la busqueda por un proveedor o todos los proveedores
        getChkTodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getChkTodos().isSelected())
                {
                    getBtnBuscarProveedor().setEnabled(false);
                    banderaBusqueda = true;
                }
                else
                {
                    getBtnBuscarProveedor().setEnabled(true);
                    banderaBusqueda = false;
                }
            }
        });
        
        getBtcConsultar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setearValores();
            }
        });
    }
    
    public void setearValores()
    {
        //Obtener valores de combos
        this.documentoEnum = (DocumentoEnum) getCmbDocumento().getSelectedItem();
        this.tipoDocumentoEnum = (TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        //Obtener las fecha seleccionadas de los combos
        this.fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
        this.fechaFinal = new Date(getDateFechaFinal().getDate().getTime());
        
        if(banderaBusqueda)
        {
            
        }
        else
        {
            if(proveedor != null)
            {
                
            }
            else
            {
                DialogoCodefac.mensaje("Advertencia", "Debe seleccionar un proveedor", DialogoCodefac.MENSAJE_ADVERTENCIA);
            }
        }
            
        
    }
    
}
