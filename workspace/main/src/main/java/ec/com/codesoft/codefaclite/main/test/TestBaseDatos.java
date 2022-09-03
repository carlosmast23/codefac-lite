/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteFisicoDisenio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.service.ComprobanteFisicoDisenioService;
import ec.com.codesoft.codefaclite.servidor.service.KardexDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.KardexService;
import ec.com.codesoft.codefaclite.servidor.service.OrdenTrabajoService;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.codefaclite.servidor.service.PresupuestoService;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidor.service.UsuarioServicio;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

/**
 *
 * @author Carlos
 */
public class TestBaseDatos {
    public static void main(String[] args) {
        try {
            AbstractFacade.usuarioDb = "root";
            AbstractFacade.claveDb = "1234";
            AbstractFacade.cargarEntityManager();
            
            EntityManager em= AbstractFacade.entityManager;
            EntityTransaction et= em.getTransaction();
            
            KardexDetalleService kardexService=new KardexDetalleService();
            List<KardexDetalle> detalleList= kardexService.obtenerTodos();
            
            et.begin();
            for (KardexDetalle kd:detalleList) 
            {
                Integer signo= kd.getCodigoTipoDocumentoEnum().getSignoInventarioNumero();
                kd.setSigno(signo);
                em.merge(kd);
            }
            
            et.commit();

            //Permitir actualizar de nuevo la suma de los valores por cada kardex
            et.begin();
            KardexService service=new KardexService();
            List<Kardex> kardexList= service.obtenerTodos();
            for (Kardex kardex : kardexList) {
                
                kardex.setStock(kardex.recalcularStock());
                em.merge(kardex);                
            }
            
            et.commit();
            
            em.close();
            System.exit(0);
            
        } catch (PersistenceException ex) {
            Logger.getLogger(TestBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PersistenciaDuplicadaException ex) {
            Logger.getLogger(TestBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TestBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
