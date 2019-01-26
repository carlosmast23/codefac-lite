/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.controller;

import ec.com.codesoft.codefaclite.servidor.service.AccesoDirectoService;
import ec.com.codesoft.codefaclite.servidor.service.ActualizarSistemaService;
import ec.com.codesoft.codefaclite.servidor.service.AtsService;
import ec.com.codesoft.codefaclite.servidor.service.BodegaService;
import ec.com.codesoft.codefaclite.servidor.service.CategoriaProductoService;
import ec.com.codesoft.codefaclite.servidor.service.CompraDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.CompraService;
import ec.com.codesoft.codefaclite.servidor.service.ComprobanteFisicoDisenioService;
import ec.com.codesoft.codefaclite.servidor.service.ComprobantesService;
import ec.com.codesoft.codefaclite.servidor.service.DepartamentoService;
import ec.com.codesoft.codefaclite.servidor.service.EmpleadoService;
import ec.com.codesoft.codefaclite.servidor.service.EmpresaService;
import ec.com.codesoft.codefaclite.servidor.service.FacturacionService;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoService;
import ec.com.codesoft.codefaclite.servidor.service.KardexDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.KardexItemEspecificoService;
import ec.com.codesoft.codefaclite.servidor.service.KardexService;
import ec.com.codesoft.codefaclite.servidor.service.NacionalidadService;
import ec.com.codesoft.codefaclite.servidor.service.NotaCreditoService;
import ec.com.codesoft.codefaclite.servidor.service.OrdenTrabajoDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.OrdenTrabajoService;
import ec.com.codesoft.codefaclite.servidor.service.ParametroCodefacService;
import ec.com.codesoft.codefaclite.servidor.service.PerfilService;
import ec.com.codesoft.codefaclite.servidor.service.PerfilUsuarioService;
import ec.com.codesoft.codefaclite.servidor.service.PermisoVentanaService;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.codefaclite.servidor.service.PresupuestoDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.PresupuestoService;
import ec.com.codesoft.codefaclite.servidor.service.ProductoEnsambleService;
import ec.com.codesoft.codefaclite.servidor.service.ProductoProveedorService;
import ec.com.codesoft.codefaclite.servidor.service.ProductoService;
import ec.com.codesoft.codefaclite.servidor.service.PuntoEmisionService;
import ec.com.codesoft.codefaclite.servidor.service.RecursosService;
import ec.com.codesoft.codefaclite.servidor.service.RetencionService;
import ec.com.codesoft.codefaclite.servidor.service.SmsService;
import ec.com.codesoft.codefaclite.servidor.service.SriFormaPagoService;
import ec.com.codesoft.codefaclite.servidor.service.SriIdentificacionService;
import ec.com.codesoft.codefaclite.servidor.service.SriRetencionIvaService;
import ec.com.codesoft.codefaclite.servidor.service.SriRetencionRentaService;
import ec.com.codesoft.codefaclite.servidor.service.SriRetencionService;
import ec.com.codesoft.codefaclite.servidor.service.SriService;
import ec.com.codesoft.codefaclite.servidor.service.SucursalService;
import ec.com.codesoft.codefaclite.servidor.service.UsuarioServicio;
import ec.com.codesoft.codefaclite.servidor.service.UtilidadesService;
import ec.com.codesoft.codefaclite.servidor.service.cartera.CarteraCruceService;
import ec.com.codesoft.codefaclite.servidor.service.cartera.CarteraDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.cartera.CarteraService;
import ec.com.codesoft.codefaclite.servidor.service.compra.OrdenCompraDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.compra.OrdenCompraService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.AulaService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.CatalogoProductoService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.EstudianteInscritoService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.EstudianteService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.NivelAcademicoService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.NivelService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.PeriodoService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.RubroEstudianteService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.RubroPlantillaEstudianteService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.RubroPlantillaService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.RubrosNivelService;
import ec.com.codesoft.codefaclite.servidor.service.transporte.DestinatarioGuiaRemisionService;
import ec.com.codesoft.codefaclite.servidor.service.transporte.DetalleProductoGuiaRemisionService;
import ec.com.codesoft.codefaclite.servidor.service.transporte.GuiaRemisionAdicionalService;
import ec.com.codesoft.codefaclite.servidor.service.transporte.GuiaRemisionService;
import ec.com.codesoft.codefaclite.servidor.service.transporte.TransportistaService;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceControllerServer;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AccesoDirectoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ActualizarSistemaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AtsServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AulaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CatalogoProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CategoriaProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteFisicoDisenioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.DepartamentoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpleadoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpresaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexItemEspecificoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NacionalidadServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenCompraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenCompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilUsuarioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PeriodoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PermisoVentanaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoEnsambleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RetencionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubrosNivelServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SmsServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriFormaPagoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriIdentificacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SucursalServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UtilidadesServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraCruceServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.RubroPlantillaEstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.RubroPlantillaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.DestinatarioGuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.DetalleProductoGuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionAdicionalServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.TransportistaServiceIf;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PuntoEmisionServiceIf;

/**
 *
 * @author Carlos
 */
public abstract class ControllerServiceUtil {

    private static final Logger LOG = Logger.getLogger(ControllerServiceUtil.class.getName());
    
    
    public static void cargarRecursosServidor() {
        try {
            //AbstractFacade.cargarEntityManager();

            Map<Class, Class> mapRecursos = new HashMap<Class, Class>();

            mapRecursos.put(ProductoService.class, ProductoServiceIf.class);
            mapRecursos.put(PersonaService.class, PersonaServiceIf.class);
            mapRecursos.put(AccesoDirectoService.class, AccesoDirectoServiceIf.class);
            mapRecursos.put(BodegaService.class, BodegaServiceIf.class);
            mapRecursos.put(CategoriaProductoService.class, CategoriaProductoServiceIf.class);
            mapRecursos.put(CompraDetalleService.class, CompraDetalleServiceIf.class);
            mapRecursos.put(CompraService.class, CompraServiceIf.class);
            mapRecursos.put(ComprobanteFisicoDisenioService.class, ComprobanteFisicoDisenioServiceIf.class);
            mapRecursos.put(EmpresaService.class, EmpresaServiceIf.class);
            mapRecursos.put(FacturacionService.class, FacturacionServiceIf.class);
            mapRecursos.put(ImpuestoDetalleService.class, ImpuestoDetalleServiceIf.class);
            mapRecursos.put(ImpuestoService.class, ImpuestoServiceIf.class);
            mapRecursos.put(KardexDetalleService.class, KardexDetalleServiceIf.class);
            mapRecursos.put(KardexItemEspecificoService.class, KardexItemEspecificoServiceIf.class);
            mapRecursos.put(KardexService.class, KardexServiceIf.class);
            mapRecursos.put(NotaCreditoService.class, NotaCreditoServiceIf.class);
            mapRecursos.put(ParametroCodefacService.class, ParametroCodefacServiceIf.class);
            mapRecursos.put(PerfilService.class, PerfilServiceIf.class);
            mapRecursos.put(ProductoEnsambleService.class, ProductoEnsambleServiceIf.class);
            mapRecursos.put(ProductoProveedorService.class, ProductoProveedorServiceIf.class);
            mapRecursos.put(SriIdentificacionService.class, SriIdentificacionServiceIf.class);
            mapRecursos.put(SriService.class, SriServiceIf.class);
            mapRecursos.put(UsuarioServicio.class, UsuarioServicioIf.class);
            mapRecursos.put(UtilidadesService.class, UtilidadesServiceIf.class);
            mapRecursos.put(ComprobantesService.class, ComprobanteServiceIf.class);
            mapRecursos.put(RecursosService.class, RecursosServiceIf.class);
            mapRecursos.put(AulaService.class, AulaServiceIf.class);
            mapRecursos.put(EstudianteService.class, EstudianteServiceIf.class);
            mapRecursos.put(NivelService.class, NivelServiceIf.class);
            mapRecursos.put(PeriodoService.class, PeriodoServiceIf.class);
            mapRecursos.put(NivelAcademicoService.class, NivelAcademicoServiceIf.class);
            mapRecursos.put(PermisoVentanaService.class, PermisoVentanaServiceIf.class);
            mapRecursos.put(EstudianteInscritoService.class,EstudianteInscritoServiceIf.class);
            mapRecursos.put(RubrosNivelService.class,RubrosNivelServiceIf.class);
            mapRecursos.put(RubroEstudianteService.class,RubroEstudianteServiceIf.class);
            mapRecursos.put(NacionalidadService.class,NacionalidadServiceIf.class);
            mapRecursos.put(CatalogoProductoService.class,CatalogoProductoServiceIf.class);
            mapRecursos.put(CarteraService.class,CarteraServiceIf.class);
            mapRecursos.put(CarteraDetalleService.class,CarteraDetalleServiceIf.class);
            mapRecursos.put(CarteraCruceService.class, CarteraCruceServiceIf.class);
            mapRecursos.put(RubroPlantillaService.class, RubroPlantillaServiceIf.class);
            mapRecursos.put(RubroPlantillaEstudianteService.class, RubroPlantillaEstudianteServiceIf.class);
            mapRecursos.put(PerfilService.class, PerfilServiceIf.class);
            mapRecursos.put(PerfilUsuarioService.class, PerfilUsuarioServiceIf.class);
            mapRecursos.put(SriFormaPagoService.class, SriFormaPagoServiceIf.class);
            mapRecursos.put(SriRetencionIvaService.class, SriRetencionIvaServiceIf.class);
            mapRecursos.put(SriRetencionRentaService.class, SriRetencionRentaServiceIf.class);
            mapRecursos.put(RetencionService.class, RetencionServiceIf.class);
            mapRecursos.put(SriRetencionService.class,SriRetencionServiceIf.class);
            mapRecursos.put(OrdenCompraService.class,OrdenCompraServiceIf.class);
            mapRecursos.put(OrdenCompraDetalleService.class,OrdenCompraDetalleServiceIf.class);
            mapRecursos.put(DepartamentoService.class,DepartamentoServiceIf.class);
            mapRecursos.put(EmpleadoService.class,EmpleadoServiceIf.class);
            mapRecursos.put(OrdenTrabajoService.class,OrdenTrabajoServiceIf.class);
            mapRecursos.put(OrdenTrabajoDetalleService.class,OrdenTrabajoDetalleServiceIf.class);
            mapRecursos.put(PresupuestoService.class, PresupuestoServiceIf.class);
            mapRecursos.put(PresupuestoDetalleService.class, PresupuestoDetalleServiceIf.class);
            mapRecursos.put(TransportistaService.class, TransportistaServiceIf.class);
            
            mapRecursos.put(DestinatarioGuiaRemisionService.class, DestinatarioGuiaRemisionServiceIf.class);
            mapRecursos.put(DetalleProductoGuiaRemisionService.class, DetalleProductoGuiaRemisionServiceIf.class);
            mapRecursos.put(GuiaRemisionService.class, GuiaRemisionServiceIf.class);
            mapRecursos.put(GuiaRemisionAdicionalService.class, GuiaRemisionAdicionalServiceIf.class);
            mapRecursos.put(SmsService.class, SmsServiceIf.class);
            mapRecursos.put(AtsService.class, AtsServiceIf.class);
            mapRecursos.put(SucursalService.class, SucursalServiceIf.class);
            mapRecursos.put(PuntoEmisionService.class, PuntoEmisionServiceIf.class);
            mapRecursos.put(ActualizarSistemaService.class, ActualizarSistemaServiceIf.class);
            
            ServiceControllerServer.cargarRecursos(mapRecursos);
            LOG.log(Level.INFO,"Servidor Iniciado");

        } catch (PersistenceException ex) {
            Logger.getLogger(ControllerServiceUtil.class.getName()).log(Level.SEVERE, null, ex);
        } //catch (PersistenciaDuplicadaException ex) {
        //    Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }
}
