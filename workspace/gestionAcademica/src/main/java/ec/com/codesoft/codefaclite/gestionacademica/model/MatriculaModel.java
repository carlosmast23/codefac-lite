/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.gestionacademica.panel.MatriculaPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.NivelEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author Carlos
 */
public class MatriculaModel extends MatriculaPanel{

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarListener();
        cargarValoresIniciales();
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
        return "Matricula";
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

    private void iniciarListener() {
        iniciarListenerBotones();
        iniciarListenerCombos();
    }

    private void iniciarListenerBotones() {
        
    }

    private void cargarValoresIniciales() {
        try {
            //Cargar los combos de los niveles
            List<Periodo> niveles=ServiceFactory.getFactory().getPeriodoServiceIf().obtenerTodos();
            getCmbPeriodoAnterior().removeAllItems();
            getCmbPeriodoSiguiente().removeAllItems();
            
            for (Periodo nivel : niveles) 
            {
                if(NivelEnumEstado.getEnum(nivel.getEstado()).equals(NivelEnumEstado.INACTIVO))
                {
                    getCmbPeriodoAnterior().addItem(nivel);
                }
                
                //Solo cargar los niveles activos en este combo
                if(NivelEnumEstado.getEnum(nivel.getEstado()).equals(NivelEnumEstado.ACTIVO))
                {
                    getCmbPeriodoSiguiente().addItem(nivel);
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciarListenerCombos() {
        getCmbPeriodoSiguiente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodo= (Periodo) getCmbPeriodoSiguiente().getSelectedItem();
                if(periodo!=null)
                {
                    cargarNivelesPeriodo(periodo,getCmbNivelMatricula());
                }
            }
        });
    }
    
    private void cargarNivelesPeriodo(Periodo periodo,JComboBox<NivelAcademico> comboNivel)
    {
        try {
            NivelAcademico na=new NivelAcademico();
            na.getPeriodo();
            NivelAcademicoServiceIf servicio= ServiceFactory.getFactory().getNivelAcademicoServiceIf();
            Map<String,Object> mapBusqueda=new HashMap<String,Object>();
            mapBusqueda.put("periodo",periodo);
            List<NivelAcademico> resultados=servicio.obtenerPorMap(mapBusqueda);
            
            comboNivel.removeAllItems();
            for (NivelAcademico resultado : resultados) {
                comboNivel.addItem(resultado);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(MatriculaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
