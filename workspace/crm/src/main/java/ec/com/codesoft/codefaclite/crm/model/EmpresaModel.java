/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.busqueda.EmpresaBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.EmpresaForm;
import ec.com.codesoft.codefaclite.servidor.entity.Empresa;
import ec.com.codesoft.codefaclite.servidor.service.EmpresaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PC
 */
public class EmpresaModel extends EmpresaForm
{
    private Empresa empresa;
    private EmpresaService empresaService;

    public EmpresaModel() 
    {
        this.empresa = new Empresa();
        this.empresaService = new EmpresaService();
        cargarDatosEmpresa();
    }
    
    public Empresa obtenerDatosEmpresa()
    {
       try{
           for(Empresa e: empresaService.buscar())
           {
                return e;
           }
       }
       catch(Exception e)
       {
           System.out.println("No se encontro datos en emisor");
       }
       return null;
    }
    
    public void cargarDatosEmpresa()
    {
        Empresa e = obtenerDatosEmpresa();
        if(e != null)
        {
            getjTextRuc().setText(e.getIdentificacion());
            getjTextNombreSocial().setText(e.getRazonSocial());
            getjTextNombreComercial().setText(e.getNombreLegal());
            getjTextADirEstablecimiento().setText(e.getDireccion());
            getjTextTelefono().setText(e.getTelefonos());
            if(e.getContribuyenteEspecial().equals("SI"))
            {
                getjCheckBLlevaContabilidad().setSelected(true);
            }else
            {
                getjCheckBLlevaContabilidad().setSelected(false);
            }
            getjTextLogo().setText("Valor establecido por defecto");
        }  
    }
    
    @Override
    public void grabar() throws ExcepcionCodefacLite 
    {
        empresaService.grabar(setDatosEmisor()); 
    }

    @Override
    public void editar() throws ExcepcionCodefacLite 
    {
        empresaService.editar(setDatosEmisor());
    }

    @Override
    public void eliminar() 
    {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() 
    {
        return "http://www.cf.codesoft-ec.com/ayuda#eemisor";
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() 
    {
        Map<Integer,Boolean> permisos=new HashMap<Integer,Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO,true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR,true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }
    
    public Empresa setDatosEmisor()
    {
        empresa = new Empresa();
        empresa.setTelefonos(getjTextTelefono().getText());
        empresa.setRazonSocial(getjTextNombreSocial().getText());
        empresa.setNombreLegal(getjTextNombreComercial().getText());
        empresa.setDireccion(getjTextADirEstablecimiento().getText());
        empresa.setIdentificacion(getjTextRuc().getText());
        if(getjCheckBLlevaContabilidad().isSelected())
        {
            empresa.setObligadoLlevarContabilidad(Empresa.SI_LLEVA_CONTABILIDAD);
        }else
        {
            empresa.setObligadoLlevarContabilidad(Empresa.NO_LLEVA_CONTABILIDAD);
        }
        empresa.setContribuyenteEspecial("");
        return empresa;
    }

    @Override
    public void iniciar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
