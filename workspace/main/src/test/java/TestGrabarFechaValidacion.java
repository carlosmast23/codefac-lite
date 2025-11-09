
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.service.ParametroCodefacService;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class TestGrabarFechaValidacion {

    private static final Logger LOG = Logger.getLogger(TestGrabarFechaValidacion.class.getName());
    
    public static void main(String[] args)
    {
        try {
            AbstractFacade.usuarioDb = "root";
            AbstractFacade.claveDb = "1234";
            String fecha="2019-12-01";
           AbstractFacade.cargarEntityManagerFactory();
            
            EntityManager em = AbstractFacade.nuevoEntityManager();
            EntityTransaction et = em.getTransaction();
            
            
            LOG.log(Level.INFO,"Fecha Actualizada correctamente");
            em.close();
            System.exit(0);
            
        } catch (PersistenceException ex) {
            Logger.getLogger(TestGrabarFechaValidacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PersistenciaDuplicadaException ex) {
            Logger.getLogger(TestGrabarFechaValidacion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
