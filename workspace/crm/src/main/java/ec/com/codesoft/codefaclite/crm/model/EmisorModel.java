/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.busqueda.EmisorBusquedaDialogo;
import ec.com.codesoft.codefaclite.crm.panel.ConfigurarEmisorForm;
import ec.com.codesoft.codefaclite.servidor.entity.Emisor;
import ec.com.codesoft.codefaclite.servidor.service.EmisorService;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PC
 */
public class EmisorModel extends ConfigurarEmisorForm
{
    private Emisor emisor;
    private EmisorService emisorService;

    public EmisorModel() 
    {
        this.emisorService = new EmisorService();
    }
    
    @Override
    public void grabar() throws ExcepcionCodefacLite 
    {
        emisorService.grabar(setDatosEmisor());   
    }

    @Override
    public void editar() throws ExcepcionCodefacLite 
    {
        emisorService.editar(setDatosEmisor());
    }

    @Override
    public void eliminar() 
    {
        emisorService.eliminar(emisor);
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
        this.panelPadre.crearVentanaCodefac(new EmisorModel(),true);
        EmisorBusquedaDialogo emisorBusquedaDialogo = new EmisorBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel=new BuscarDialogoModel(emisorBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        emisor = (Emisor) buscarDialogoModel.getResultado();
        
        if(emisor == null)
        {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar");
        }
        
        getjTextRuc().setText(emisor.getRuc());
        getjTextNombreSocial().setText(emisor.getRazonSocial());
        getjTextNombreComercial().setText(emisor.getNomComercial());
        getjTextADirMatriz().setText(emisor.getDireccionMatriz());
        getjTextADirEstablecimiento().setText(emisor.getDirEstablecimiento());
        getjTextCodEstablecimiento().setText(emisor.getCodEstablecimiento());
        getjTextNumContribuyente().setText(emisor.getContribuyenteEspecial());
        getjTextActividadComercial().setText(emisor.getActividadComercial());
        if(emisor.getLlevaContabilidad().equals("S"))
        {
            getjCheckBLlevaContabilidad().setSelected(true);
        }
        else
        {
            getjCheckBLlevaContabilidad().setSelected(false);
        }
        getjSpinnerTiempoEspera().setValue(emisor.getTiempoEspera());      
        System.out.println("Datos cargados ");
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
        return "https://support.office.com/es-es";
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
    
    public Emisor setDatosEmisor()
    {
        emisor = new Emisor();
        emisor.setRuc(getjTextRuc().getText());
        emisor.setRazonSocial(getjTextNombreSocial().getText());
        emisor.setNomComercial(getjTextNombreComercial().getText());
        emisor.setDireccionMatriz(getjTextADirMatriz().getText());
        emisor.setDirEstablecimiento(getjTextADirEstablecimiento().getText());
        emisor.setCodEstablecimiento(getjTextCodEstablecimiento().getText());
        emisor.setContribuyenteEspecial(getjTextNumContribuyente().getText());
        if(getjCheckBLlevaContabilidad().isSelected()==true)
        {
            emisor.setLlevaContabilidad("S");
        }
        else
        {
            emisor.setLlevaContabilidad("N");
        }
        emisor.setTiempoEspera(""+getjSpinnerTiempoEspera().getValue());
        emisor.setActividadComercial(""+getjTextActividadComercial().getText());
       
        return emisor;
    }

    @Override
    public void iniciar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
