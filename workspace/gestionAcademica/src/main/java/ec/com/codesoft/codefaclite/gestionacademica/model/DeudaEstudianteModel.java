/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.EstudianteBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EstudianteInscritoBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.panel.DeudaEstudiantePanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class DeudaEstudianteModel extends DeudaEstudiantePanel{

    private EstudianteInscrito estudianteInscrito;
    
    /**
     * Guarda el listado de los rubros ya cargados del estudiante
     */
    private List<RubroEstudiante> rubrosEstudiante;
    /**
     * Guarda el listado de los rubros agregados al estudiante
     */
    private List<RubroEstudiante> rubrosEstudianteEliminado;

    
    private final Integer COLUMNA_OBJETO=0;
    private final Integer COLUMNA_VALOR=3;

    public DeudaEstudianteModel() {
        //Setear propiedad para actualizar valores de editar apenas pierda el focus        
    }
    
    
    
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        listenerBotones();
        listenerCombos();    
        listenerTablas();        
        
        //Quitar validaciones para esta pantalla
        validacionDatosIngresados=false;
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        
        //Artificio para setear los datos de la tabla si no presiono enter
        UtilidadesTablas.setearValoresEditadosTabla(getTblDatos());
        
        List<RubroEstudiante> rubrosActualizar = getRubroEstudianteSinAcciones();        
       
        List<RubroEstudiante> rubrosEstudianteNuevos=getRubroEstudianteSinGrabar();
        
        if(rubrosEstudianteNuevos.size()>0 || rubrosEstudianteEliminado.size()>0 || rubrosActualizar.size()>0)
        {
            try {
                
                ServiceFactory.getFactory().getRubroEstudianteServiceIf().actualizarRubrosEstudiante(rubrosActualizar);
                
                //Graba los nuevos rubros y elimina rubros seleccionados
                ServiceFactory.getFactory().getRubroEstudianteServiceIf().crearRubrosEstudiantes(rubrosEstudianteNuevos);
                
                //Elimina rubros seleccionados
                ServiceFactory.getFactory().getRubroEstudianteServiceIf().eliminarRubrosEstudiantes(rubrosEstudianteEliminado);
                
                DialogoCodefac.mensaje("Correcto","El estudiante fue actualizado correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            } catch (RemoteException ex) {
                Logger.getLogger(DeudaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje("Incorrecto","No se pueden grabar los rubros",DialogoCodefac.MENSAJE_INCORRECTO);
                 throw new ExcepcionCodefacLite("Cancelar ...");
            }
        }
        else
        {
            DialogoCodefac.mensaje("Advertencia","No existen modificaciones para grabar",DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("Cancelar ...");
        }
    }
    
    private List<RubroEstudiante> getRubroEstudianteSinGrabar()
    {
        List<RubroEstudiante> rubrosEstudianteNuevos=new ArrayList<RubroEstudiante>();
        for (RubroEstudiante rubroEstudiante : rubrosEstudiante) {
            //Si estan con id null significa que aun no estan grabados
            if(rubroEstudiante.getId()==null)
            {
                rubrosEstudianteNuevos.add(rubroEstudiante);
            }
        }
        return rubrosEstudianteNuevos;
    }
    
    /**
     * Esta lista devuelve todos los datos que van a ser creados ni eliminados
     * TODO: mejorar esta lista para obtener solo los datos modificados que no van a ser eliminados
     * @return 
     */
    private List<RubroEstudiante> getRubroEstudianteSinAcciones()
    {
        List<RubroEstudiante> rubrosEstudianteSinAcciones=new ArrayList<RubroEstudiante>();
        for (RubroEstudiante rubroEstudiante : rubrosEstudiante) {
            //Si estan con id null significa que aun no estan grabados
            if(rubroEstudiante.getId()!=null)
            {
                if(!rubrosEstudianteEliminado.contains(rubroEstudiante))
                {
                    System.out.println("valor->"+rubroEstudiante.getValor());
                    rubrosEstudianteSinAcciones.add(rubroEstudiante);
                }
                
            }
        }
        return rubrosEstudianteSinAcciones;
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
       
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if(estudianteInscrito!=null)
        {
        
        }
        else
        {
            DialogoCodefac.mensaje("Advetencia","Seleccione un estudiane para elminar los rubros",DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
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
        cargarValoresIniciales();
        cargarDatosTabla();
        //Limpiar el combo de los rubros
        getCmbRubro().removeAllItems();
    }

//    @Override
    public String getNombre() {
        return "Deudas de Estudiante";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listenerBotones() {

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Periodo periodoSeleccionado=(Periodo) getCmbPeriodo().getSelectedItem();
                EstudianteInscritoBusquedaDialogo aulaBusquedaDialogo = new EstudianteInscritoBusquedaDialogo(periodoSeleccionado);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(aulaBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                EstudianteInscrito estudiante = (EstudianteInscrito) buscarDialogoModel.getResultado();
                
                if(estudiante!=null)
                {
                    try {
                        estudianteInscrito=estudiante;
                        
                        Map<String,Object> parametroMap=new HashMap<String,Object>();
                        parametroMap.put("estudianteInscrito",estudianteInscrito);
                        parametroMap.put("estadoFactura",RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
                        parametroMap.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
                        
                        
                        List<RubroEstudiante> listaRubros=ServiceFactory.getFactory().getRubroEstudianteServiceIf().obtenerPorMap(parametroMap);
                        rubrosEstudiante=listaRubros; 
                        cargarDatosTabla();
                        
                        getTxtEstudianteMatriculado().setText(estudianteInscrito.getEstudiante().getNombreCompleto() + "-" + estudianteInscrito.getNivelAcademico().getNombre());

                        Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
                        if (periodo != null) {
                            cargarRubrosAsociados(estudianteInscrito, periodo);
                        }
                        
                    } catch (RemoteException ex) {
                        Logger.getLogger(DeudaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(DeudaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }


                
            }
        });
        
        getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (estudianteInscrito != null) {
                    Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
                    if (periodo != null) {
                        RubrosNivel rubroNivel=(RubrosNivel) getCmbRubro().getSelectedItem();
                        RubroEstudiante rubroEstudiante=new RubroEstudiante();
                        rubroEstudiante.setEstudianteInscrito(estudianteInscrito);
                        rubroEstudiante.setRubroNivel(rubroNivel);
                        rubroEstudiante.setValor(rubroNivel.getValor());
                        rubroEstudiante.setSaldo(rubroNivel.getValor());
                        rubroEstudiante.setEstadoFactura(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR.getLetra());
                        
                        //Si el rubro que se va a agregar ya esta en la lista de eliminados le quito de esa lista
                        if(rubrosEstudianteEliminado.contains(rubroEstudiante))
                        {
                            rubrosEstudianteEliminado.remove(rubrosEstudiante);
                        }
                        //Agregado a la lista de datos sin grabar
                        rubrosEstudiante.add(rubroEstudiante);
                        
                        cargarDatosTabla();
                    }
                }
            }
        });
        
        
        getBtnQuitar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaTabla=getTblDatos().getSelectedRow();
                RubroEstudiante rubroEstudiante=(RubroEstudiante) getTblDatos().getValueAt(filaTabla,COLUMNA_OBJETO);

                if(rubroEstudiante!=null)
                {
                    //Si tiene el id en null significa que solo esta en memoria y puedo eliminar directamente porque aun no esta grabado
                    if(rubroEstudiante.getId()==null)
                    {
                        rubrosEstudiante.remove(rubroEstudiante);
                    }
                    else //Esta caso se ejecuta cuando ya existe grabado y toca analizar que rubros se pueden eliminar
                    {
                        if(rubroEstudiante.getEstadoFacturaEnum().equals(RubroEstudiante.FacturacionEstadoEnum.SIN_FACTURAR))
                        {
                            rubrosEstudianteEliminado.add(rubroEstudiante);                        
                        }
                        else
                        {
                            DialogoCodefac.mensaje("Advertencia","El rubro ya se encuentra facturado no se puede facturar \n Si desea eliminar primero tiene que anular la factura",DialogoCodefac.MENSAJE_ADVERTENCIA);
                        }
                        //TODO: Verificar en que caso se pueden eliminar rubros ya agegados
                    }
                    cargarDatosTabla();
                }
                
            }
        });
    }
    
    private void cargarDatosTabla()
    {
        Object[] titulo={"Objeto","Nombre","Curso","Valor"};
        DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
        
        if(rubrosEstudiante!=null)
        {
            for (RubroEstudiante rubroEstudiante : rubrosEstudiante) {
                
                //Verificar si el elemento se le tiene que eliminar
                if(!rubrosEstudianteEliminado.contains(rubroEstudiante))
                {
                    Vector<Object> fila = new Vector<Object>();
                    fila.add(rubroEstudiante);
                    fila.add(rubroEstudiante.getEstudianteInscrito().getEstudiante().getNombreCompleto());
                    fila.add(rubroEstudiante.getRubroNivel().getNombre());
                    fila.add(rubroEstudiante.getValor());
                    
                     modeloTabla.addRow(fila);
                }
                
               
            }
        }
      
        getTblDatos().setModel(modeloTabla);
        
        //Ocultar columnas que no se deben mostrar al usuario
        UtilidadesTablas.ocultarColumna(getTblDatos(),0);
        
        
        //Listener de la tabla creada
        modeloTabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int fila= e.getFirstRow();
                int colummna=e.getColumn();
                
                //Solo editar el valor del objeto si es de la columna del valor
                if(colummna==COLUMNA_VALOR)
                {
                    
                    RubroEstudiante rubroEstudiante=(RubroEstudiante) getTblDatos().getModel().getValueAt(fila,COLUMNA_OBJETO);
                    
                    try
                    {
                        
                        BigDecimal nuevoValor = new BigDecimal(getTblDatos().getValueAt(fila, COLUMNA_VALOR).toString());
                        
                        //Si el valor ingresado es 0 no permite cambiar
                        if(nuevoValor.compareTo(BigDecimal.ZERO)==0)
                        {
                            getTblDatos().getModel().setValueAt(rubroEstudiante.getValor().toString(),fila,COLUMNA_VALOR);
                            return ;
                        }   
                        
                        
                        //Verificar que los valores ingresados y del objeto son diferente para actualizar
                        if (nuevoValor.compareTo(rubroEstudiante.getValor()) != 0) {
                            
                            if (rubroEstudiante.getValor().compareTo(rubroEstudiante.getSaldo()) == 0) 
                            {
                                rubroEstudiante.setValor(nuevoValor);
                                rubroEstudiante.setSaldo(nuevoValor); //Todo: Verificar este caso porque si tiene saldo ingresado no me deberia dejar setear
                            }
                            else
                            {
                                DialogoCodefac.mensaje("Advertencia", "No se puede modificar el rubro porque tiene valores que le afectan ", DialogoCodefac.MENSAJE_ADVERTENCIA);
                                getTblDatos().getModel().setValueAt(rubroEstudiante.getValor().toString(),fila,COLUMNA_VALOR);
                            }
                            
                        }
                    }
                    catch(java.lang.NumberFormatException nfe)
                    {
                        getTblDatos().getModel().setValueAt(rubroEstudiante.getValor().toString(),fila,COLUMNA_VALOR);
                        nfe.printStackTrace();
                    }

                }
            }
        });
        
        //UtilidadesTablas.editarTablaEditarCuandoPierdeFoco(getTblDatos()); 

    }
    
    private void cargarRubrosAsociados(EstudianteInscrito estudiante,Periodo periodo)
    {
        try {
            Map<String,Object> parametrosMap=new HashMap<String,Object>();
            parametrosMap.put("periodo",periodo);
            parametrosMap.put("nivel",null);
            parametrosMap.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
            
            //Agregar todos los rubros que no estan asigandos a un nivel
            List<RubrosNivel> rubrosGeneral=ServiceFactory.getFactory().getRubrosNivelServiceIf().obtenerPorMap(parametrosMap);
            getCmbRubro().removeAllItems();
            
            for (RubrosNivel rubro : rubrosGeneral) {
                getCmbRubro().addItem(rubro);
            }
            
            
            parametrosMap.put("periodo",periodo);
            parametrosMap.put("nivel",estudiante.getNivelAcademico().getNivel());
            parametrosMap.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
            
            //Agregar solo los niveles disponibles para el curso del estudiante en especifico
            List<RubrosNivel> rubrosNivel=ServiceFactory.getFactory().getRubrosNivelServiceIf().obtenerPorMap(parametrosMap);
            for (RubrosNivel rubroNivel : rubrosNivel) 
            {
                getCmbRubro().addItem(rubroNivel);
            }
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(DeudaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(DeudaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarValoresIniciales() {
        try {
            List<Periodo> periodosActivos=ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodoActivo();
            getCmbPeriodo().removeAllItems();
            for (Periodo periodosActivo : periodosActivos) {
                getCmbPeriodo().addItem(periodosActivo);
            }
        
            rubrosEstudiante=new ArrayList<RubroEstudiante>();
            rubrosEstudianteEliminado=new ArrayList<RubroEstudiante>();
            estudianteInscrito=null;
        
        } catch (RemoteException ex) {
            Logger.getLogger(DeudaEstudianteModel.class.getName()).log(Level.SEVERE, null, ex);
            
            
            
        }
       
    }

    private void listenerCombos() {
        getCmbPeriodo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*if (estudianteInscrito != null) {
                    Periodo periodo = (Periodo) getCmbPeriodo().getSelectedItem();
                    if (periodo != null) {
                        cargarRubrosAsociados(estudianteInscrito, periodo);
                    }
                }*/
            }
        });
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void listenerTablas() {
        
    }
    
}
