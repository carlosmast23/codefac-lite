/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.utilidades;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class UtilidadesDialogoBusqueda 
{
    public static void abrirDialogo(InterfaceModelFind dialogo,DialogoBusquedaIf dialogoIf)
    {
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(dialogo);
                buscarDialogoModel.setVisible(true);
                Object objetoResultado = (Object) buscarDialogoModel.getResultado();
                if (objetoResultado != null) {
                    dialogoIf.ejecutar(objetoResultado);
                }
    }
    
    public interface DialogoBusquedaIf<T>
    {
        public void ejecutar(T objeto);
    }
    
}
