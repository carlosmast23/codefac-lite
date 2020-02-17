/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author Carlos
 */
public enum VentanaEnum {

    CLIENTE("ec.com.codesoft.codefaclite.crm.model.ClienteModel", "CLIE", "Cliente", ModuloCodefacEnum.CRM, CategoriaMenuEnum.GESTIONAR, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,KeyEvent.VK_C),
    SUCURSAL("ec.com.codesoft.codefaclite.configuraciones.model.SucursalModel", "SUCU", "Sucursal", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.PROCESOS, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,null),
    UTILIDAD_ENVIO_COMPROBANTES("ec.com.codesoft.codefaclite.configuraciones.model.UtilidadEnvioReportesModel", "UECT", "Utilidad envio reportes", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.UTILIDADES, false, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.PRO,null),
    PUNTO_EMISION("ec.com.codesoft.codefaclite.configuraciones.model.PuntoEmisionModel", "PUVE", "Punto de Emision", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.PROCESOS, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,null),
    PROVEEDOR("ec.com.codesoft.codefaclite.crm.model.ProveedorModel", "PROV", "Proveedor", ModuloCodefacEnum.CRM, CategoriaMenuEnum.GESTIONAR, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,null),
    PRODUCTO("ec.com.codesoft.codefaclite.crm.model.ProductoModel", "PROD", "Producto", ModuloCodefacEnum.CRM, CategoriaMenuEnum.GESTIONAR, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,KeyEvent.VK_P),
    IMPRIMIR_CODIGO_BARRAS("ec.com.codesoft.codefaclite.inventario.model.ImprimirCodidoBarrasModel", "ICDB", "Imprimir Códigos Barras", ModuloCodefacEnum.INVENTARIO , CategoriaMenuEnum.GESTIONAR, false, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,null),
    FACTURACION("ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel", "FACT", "Facturación", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.PROCESOS,TipoLicenciaEnum.GRATIS,KeyEvent.VK_F),
    EMPRESA("ec.com.codesoft.codefaclite.crm.model.EmpresaModel", "EMPR", "Empresa", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.GESTIONAR, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,KeyEvent.VK_E),
    COMPROBANTE_CONFIGURACION("ec.com.codesoft.codefaclite.configuraciones.model.ComprobantesConfiguracionModel", "CONF", "Configuración Comprobantes", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.GESTIONAR,TipoLicenciaEnum.GRATIS,null),
    UTILIDAD_COMPROBANTE("ec.com.codesoft.codefaclite.facturacion.model.UtilidadComprobanteSimpleModel", "UTIL", "Utilidad Comprobante", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.UTILIDADES,TipoLicenciaEnum.GRATIS,null),
    UTILIDAD_COMPROBANTE_AVANZADO("ec.com.codesoft.codefaclite.facturacion.model.UtilidadComprobanteAvanzadoModel", "UTIA", "Utilidad Comprobante Avanzado", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.UTILIDADES,TipoLicenciaEnum.GRATIS,null),
    NOTA_CREDITO("ec.com.codesoft.codefaclite.facturacion.model.NotaCreditoModel", "NOTC", "Nota de Credito", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.PROCESOS,TipoLicenciaEnum.GRATIS,KeyEvent.VK_N),
    FACTURA_REPORTE("ec.com.codesoft.codefaclite.facturacion.model.FacturaReporteModel", "FACR", "Factura Reporte", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.REPORTES,TipoLicenciaEnum.GRATIS,null),
    CLIENTE_REPORTE("ec.com.codesoft.codefaclite.crm.model.ClienteReporte", "CLIR", "Cliente Reporte", ModuloCodefacEnum.CRM, CategoriaMenuEnum.REPORTES, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,null),
    PROVEEDOR_REPORTE("ec.com.codesoft.codefaclite.crm.model.ProveedorReporte", "PVOR", "Proveedor Reporte", ModuloCodefacEnum.CRM, CategoriaMenuEnum.REPORTES, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,null),
    PRODUCTO_REPORTE("ec.com.codesoft.codefaclite.crm.model.ProductoReporte", "PROR", "Producto Reporte", ModuloCodefacEnum.CRM, CategoriaMenuEnum.REPORTES, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,null),
    FACTURA_DISENIO("ec.com.codesoft.codefaclite.facturacion.model.FacturaDisenioModel", "FACD", "Factura Diseño", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.GESTIONAR,TipoLicenciaEnum.GRATIS,null),
    COMPRA("ec.com.codesoft.codefaclite.compra.model.CompraModel", "COMP", "Compra", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.PROCESOS,true,new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.PRO,KeyEvent.VK_B),
    COMPRA_REPORTE("ec.com.codesoft.codefaclite.compra.model.CompraReporteModel", "CMPR", "Compra Reporte", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.REPORTES,true,new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.PRO,null),
    COMPRA_REPORTE_PRODUCTO("ec.com.codesoft.codefaclite.compra.model.CompraReporteProductoModel", "CRPM", "Compra Reporte Producto", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.REPORTES,true,new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.PRO,null),
    ASOCIAR_PRODUCTO("ec.com.codesoft.codefaclite.inventario.model.AsociarProductoProveedorModel", "ASOP", "Asociar Producto", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.PROCESOS,null),
    BODEGA("ec.com.codesoft.codefaclite.inventario.model.BodegaModel", "BODG", "Bodega", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.GESTIONAR,null),
    CATEGORIA_PRODUCTO("ec.com.codesoft.codefaclite.inventario.model.CategoriaProductoModel", "CATG", "Categoria", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.GESTIONAR,TipoLicenciaEnum.GRATIS,null),
    INGRESO_INVENTARIO("ec.com.codesoft.codefaclite.inventario.model.IngresoInventarioModel", "ININ", "Ingreso Inventario", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.PROCESOS,null),
    KARDEX("ec.com.codesoft.codefaclite.inventario.model.KardexModel", "KARD", "Kardex", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.REPORTES,null),
    INVENTARIO_ENSAMBLE("ec.com.codesoft.codefaclite.inventario.model.InventarioEnsambleModel", "INVE", "Inventario Ensamble", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.PROCESOS,null),
    AULA("ec.com.codesoft.codefaclite.gestionacademica.model.AulaModel", "AULA", "Aula", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.GESTIONAR,null),
    NIVEL("ec.com.codesoft.codefaclite.gestionacademica.model.NivelModel", "NIVE", "Nivel", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.GESTIONAR,null),
    MIGRAR_ESTUDIANTES("ec.com.codesoft.codefaclite.gestionacademica.model.MigrarEstudiantesModel", "MIES", "Migrar Estudiantes", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.MIGRAR,null),
    MIGRAR_REPRESENTANTES("ec.com.codesoft.codefaclite.gestionacademica.model.MigrarRepresentantesModel", "MIRE", "Migrar Representantes", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.MIGRAR,null),
    MIGRAR_CLIENTES("ec.com.codesoft.codefaclite.crm.model.MigrarClientesModel", "MICL", "Migrar Clientes", ModuloCodefacEnum.CRM, CategoriaMenuEnum.MIGRAR,null),    
    MIGRAR_PROVEEDORES("ec.com.codesoft.codefaclite.crm.model.MigrarProveedorModel", "MIPR", "Migrar Proveedores", ModuloCodefacEnum.CRM, CategoriaMenuEnum.MIGRAR,null),    
    MIGRAR_CURSOS("ec.com.codesoft.codefaclite.gestionacademica.model.MigrarCursosModel", "MICU", "Migrar Cursos", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.MIGRAR,null),    
    MIGRAR_TRANSPORTISTA("ec.com.codesoft.codefaclite.transporte.model.TrasporteMigrarModel", "MITR", "Migrar Transportista", ModuloCodefacEnum.TRANSPORTE, CategoriaMenuEnum.MIGRAR,null),    
    MIGRAR_PRODUCTOS("ec.com.codesoft.codefaclite.inventario.model.MigrarProductoModel", "MIPO", "Migrar Productos", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.MIGRAR,null),    
    MIGRAR_COMPRAS("ec.com.codesoft.codefaclite.compra.model.CompraMigrarModel", "MICO", "Migrar Compras", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.MIGRAR,null),     
    PERIODO("ec.com.codesoft.codefaclite.gestionacademica.model.PeriodoModel", "PERI", "Periodo", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.GESTIONAR,null),
    PERFILES("ec.com.codesoft.codefaclite.configuraciones.model.PerfilModel", "PERF", "Perfiles", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.GESTIONAR, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,null),
    USUARIOS("ec.com.codesoft.codefaclite.configuraciones.model.PerfilUsuarioModel", "PEUM", "Usuario", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.GESTIONAR, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,null),
    MATRICULA("ec.com.codesoft.codefaclite.gestionacademica.model.MatriculaModel", "MATR", "Matricula Grupo", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.MATRICULA,null),
    NIVEL_ACADEMICO("ec.com.codesoft.codefaclite.gestionacademica.model.NivelAcademicoModel", "NIAC", "Cursos", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.GESTIONAR,null),
    ESTUDIANTES("ec.com.codesoft.codefaclite.gestionacademica.model.EstudianteModel", "ESTU", "Estudiante", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.GESTIONAR,null),
    RUBRO_ACADEMICO("ec.com.codesoft.codefaclite.gestionacademica.model.RubrosPeriodoModel", "RUAC", "Rubro", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.DEUDAS_ACADEMICOS,null),
    GESTIONAR_ACADEMICO("ec.com.codesoft.codefaclite.gestionacademica.model.GestionarDeudasModel", "GEAC", "Deudas Curso", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.DEUDAS_ACADEMICOS,null),
    FACTURA_ACADEMICO_LOTE("ec.com.codesoft.codefaclite.facturacion.model.FacturaAcademicoLoteModel", "FACL", "Factura Lote", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.PROCESOS,null),
    REPORTE_MATRICULA("ec.com.codesoft.codefaclite.gestionacademica.model.ReporteAcademicoModel", "RMAT", "Reporte Matricula", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.REPORTES,null),
    REPORTE_DEUDAS("ec.com.codesoft.codefaclite.gestionacademica.model.ReporteDeudasModel", "RDES", "Reporte Deudas", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.REPORTES,null),
    REPORTE_DEUDASESTUDIANTE("ec.com.codesoft.codefaclite.gestionacademica.model.ReporteDeudasEstudianteModel", "RDEI", "Reporte Deudas Estudiante", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.REPORTES,null),
    REPORTE_DEUDASCURSO("ec.com.codesoft.codefaclite.gestionacademica.model.DeudasAgrupadasPorCursoModel", "RDEC", "Deudas Agrupadas por Curso", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.REPORTES,null),
    DEUDA_ESTUDIANTE("ec.com.codesoft.codefaclite.gestionacademica.model.DeudaEstudianteModel", "DEML", "Deuda Estudiante", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.DEUDAS_ACADEMICOS,null),
    CATALOGO_PRODUCTO("ec.com.codesoft.codefaclite.inventario.model.CatalogoProductoModel", "CAPR", "Catalogo Producto", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.GESTIONAR,TipoLicenciaEnum.GRATIS,null),
    RUBRO_PLANTILLA("ec.com.codesoft.codefaclite.gestionacademica.model.RubroPlantillaModel", "RUBR", "Rubros Mensuales", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.DEUDAS_ACADEMICOS,null),
    NOTIFICACION_DEUDAS("ec.com.codesoft.codefaclite.gestionacademica.model.NotificacionesDeudasModel", "NODE", "Notificaciones", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.UTILIDADES,null),
    NOTIFICACION_ESTUDIANTES("ec.com.codesoft.codefaclite.gestionacademica.model.NotificacionEstudiantesModel", "NOEM", "Notificaciones Estudiantes", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.UTILIDADES,null),
    MATRICULA_ESTUDIANTE("ec.com.codesoft.codefaclite.gestionacademica.model.MatriculaEstudianteModel", "MAES", "Matrícula", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.MATRICULA,null),
    RESPALDAR_INFORMACION("ec.com.codesoft.codefaclite.configuraciones.model.RespaldarInformacionModel", "RESP", "Respaldar Información", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.PROCESOS,true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,null),
    CONFIGURACION_DEFECTO("ec.com.codesoft.codefaclite.configuraciones.model.ConfiguracionDefectoModel", "CFDF", "Configuraciones por Defecto", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.PROCESOS,true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS,null),
    RETENCIONES_PENDIENTES("ec.com.codesoft.codefaclite.compra.model.RetencionesPendienteModel", "RETP", "Retenciones Pendientes", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.PROCESOS,null),
    RETENCIONES("ec.com.codesoft.codefaclite.compra.model.RetencionModel", "RETC", "Retención", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.PROCESOS,true,new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.PRO,null),
    RETENCION_REPORTE("ec.com.codesoft.codefaclite.compra.model.RetencionReporteModel", "CRET", "Reporte Retenciones", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.REPORTES,true,new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.PRO,null),
    CARTERA("ec.com.codesoft.codefaclite.cartera.model.CarteraModel", "CART", "Cartera", ModuloCodefacEnum.CARTERA, CategoriaMenuEnum.PROCESOS,null),
    ORDEN_TRABAJO("ec.com.codesoft.codefaclite.servicios.model.OrdenTrabajoModel", "ORDT", "Orden de Trabajo", ModuloCodefacEnum.SERVICIOS, CategoriaMenuEnum.PROCESOS,KeyEvent.VK_O),
    PRESUPUESTO("ec.com.codesoft.codefaclite.servicios.model.PresupuestoModel", "PRES", "Presupuesto", ModuloCodefacEnum.SERVICIOS, CategoriaMenuEnum.PROCESOS,KeyEvent.VK_P),
    PRESUPUESTO_REPORTE("ec.com.codesoft.codefaclite.servicios.model.ReportePresupuestosModel", "PRES", "Presupuesto Reporte", ModuloCodefacEnum.SERVICIOS, CategoriaMenuEnum.REPORTES,null),
    ORDEN_COMPRA("ec.com.codesoft.codefaclite.compra.model.OrdenCompraModel", "ODCP", "Orden de Compra", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.PROCESOS,null),
    DEPARTAMENTO("ec.com.codesoft.codefaclite.configuraciones.model.DepartamentoModel", "DEPA", "Departamento", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.GESTIONAR,true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.PRO,null),
    ORDEN_TRABAJO_REPORTE("ec.com.codesoft.codefaclite.servicios.model.ReporteOrdenTrabajoModel", "ORTR", "Orden Trabajo Reporte", ModuloCodefacEnum.SERVICIOS, CategoriaMenuEnum.REPORTES,false, new ModuloCodefacEnum[]{ModuloCodefacEnum.SERVICIOS},TipoLicenciaEnum.PRO,null),
    EMPLEADO("ec.com.codesoft.codefaclite.configuraciones.model.GestionEmpleadosModel", "GEEM", "Gestión Empleado", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.GESTIONAR,true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.PRO,null),
    TRANSPORTISTA("ec.com.codesoft.codefaclite.transporte.model.TransportistaModel", "TRTA", "Transportista", ModuloCodefacEnum.TRANSPORTE, CategoriaMenuEnum.GESTIONAR,true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.PRO,null),
    GUIA_REMISION("ec.com.codesoft.codefaclite.transporte.model.GuiaRemisionModel", "GURE", "Guía de Remisión", ModuloCodefacEnum.TRANSPORTE, CategoriaMenuEnum.PROCESOS,true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.PRO,KeyEvent.VK_R),
    REPORTE_GUIAREMISIO("ec.com.codesoft.codefaclite.transporte.model.GuiasRemisionReporteModel", "RGUR", "Reporte Guia Remision", ModuloCodefacEnum.TRANSPORTE, CategoriaMenuEnum.REPORTES,true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.PRO,null),
    PROFORMA_VENTA("ec.com.codesoft.codefaclite.facturacion.model.ProformaModel", "PROF", "Proforma", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.PROCESOS,true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.PRO,null),
    PROFORMA_REPORTE("ec.com.codesoft.codefaclite.facturacion.model.ProformaReporteModel", "PRRE", "Reporte Proforma", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.REPORTES,true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.PRO,null),
    REPORTE_ACADEMICO_CLIENTE("ec.com.codesoft.codefaclite.gestionacademica.model.ClienteReporte", "RACL", "Reporte Representante", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.REPORTES,null),
    PLAN_CUENTAS("ec.com.codesoft.codefaclite.contabilidad.model.PlanCuentasModel", "PLCU", "Plan de Cuentas", ModuloCodefacEnum.CONTABILIDAD, CategoriaMenuEnum.GESTIONAR,null),
    ASIENTOS("ec.com.codesoft.codefaclite.contabilidad.model.AsientoModel", "ASIE", "Asientos", ModuloCodefacEnum.CONTABILIDAD, CategoriaMenuEnum.PROCESOS,null),
    CARTERA_CUENTAS_X_COBRAR("ec.com.codesoft.codefaclite.cartera.model.CuentasPorCobrarReporteModel", "CXCR", "Reporte Cuentas por Cobrar", ModuloCodefacEnum.CARTERA, CategoriaMenuEnum.REPORTES,null),
    CARTERA_CUENTAS_X_PAGAR("ec.com.codesoft.codefaclite.cartera.model.CuentasPorPagarReporteModel", "CXPR", "Reporte Cuentas por Pagar", ModuloCodefacEnum.CARTERA, CategoriaMenuEnum.REPORTES,null),
    DEUDA_POR_RUBRO("ec.com.codesoft.codefaclite.gestionacademica.model.DeudaPorRubroModel", "DPRU", "Deuda por rubro", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.DEUDAS_ACADEMICOS,null),
    MOVIMIENTO_CARTERA("ec.com.codesoft.codefaclite.cartera.model.MovimientoCarteraModel", "MOCA", "Movimiento de Cartera", ModuloCodefacEnum.CARTERA, CategoriaMenuEnum.REPORTES,null),
    GESTION_INVENTARIO("ec.com.codesoft.codefaclite.inventario.model.GestionInventarioModel", "GIMO", "Gestión Inventario", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.PROCESOS,null),
    PERIODO_CONTABILIDAD("ec.com.codesoft.codefaclite.contabilidad.model.PeriodoModel", "PECO", "Periodo", ModuloCodefacEnum.CONTABILIDAD, CategoriaMenuEnum.PROCESOS,null),
    BALANCE_GENERAL("ec.com.codesoft.codefaclite.contabilidad.model.BalanceGeneralModel", "BAGE", "Balance General", ModuloCodefacEnum.CONTABILIDAD, CategoriaMenuEnum.REPORTES,null),
    ESTADO_RESULTADO("ec.com.codesoft.codefaclite.contabilidad.model.EstadoResultadoModel", "ESRM", "Estado Resultado", ModuloCodefacEnum.CONTABILIDAD, CategoriaMenuEnum.REPORTES,null),
    FLUJO_EFECTIVO("ec.com.codesoft.codefaclite.contabilidad.model.FlujoEfectivoModel", "ESRM", "Flujo de Efectivo", ModuloCodefacEnum.CONTABILIDAD, CategoriaMenuEnum.REPORTES,null),
    REPORTE_INVENTARIO_STOCK("ec.com.codesoft.codefaclite.inventario.model.ReporteInventarioStockModel", "RIST", "Reporte Inventario", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.REPORTES,null),
    TRANSFERENCIA_BODEGA("ec.com.codesoft.codefaclite.inventario.model.TransferenciaBodegasModel", "TRBG", "Transferencia de Bodegas", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.PROCESOS,null),
    STOCK_MINIMO("ec.com.codesoft.codefaclite.inventario.model.StockMinimoModel", "STMN", "Stock minimo", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.REPORTES,null),
    STOCK_REPORTE("ec.com.codesoft.codefaclite.inventario.model.StockReporteModel", "STRE", "Stock reporte", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.REPORTES,null),
    REFERIDO_REPORTE("ec.com.codesoft.codefaclite.facturacion.model.ReferidosReporteModel", "RERM", "Referido Reporte", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.REPORTES,TipoLicenciaEnum.PRO,null),
    LIQUIDACION_COMPRA("ec.com.codesoft.codefaclite.facturacion.model.LiquidacionCompraModel", "LICO", "Liquidación Compra", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.PROCESOS,TipoLicenciaEnum.PRO,KeyEvent.VK_L),
    REPORTE_LIQUIDACION_COMPRA("ec.com.codesoft.codefaclite.facturacion.model.LiquidacionCompraReporteModel", "LICR", "Reporte Liquidación Compra", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.REPORTES,TipoLicenciaEnum.PRO,null),
    TABLA_INTERES("ec.com.codesoft.codefaclite.prestamos.model.TablaInteresModel", "TAIN", "Tabla Interes", ModuloCodefacEnum.CARTERA, CategoriaMenuEnum.GESTIONAR,TipoLicenciaEnum.PRO,null,false),
    PRESTAMO_MODEL("ec.com.codesoft.codefaclite.prestamos.model.PrestamoModel", "PRST", "Prestamo", ModuloCodefacEnum.CARTERA, CategoriaMenuEnum.PROCESOS,TipoLicenciaEnum.PRO,null,true),
    ATS("ec.com.codesoft.codefaclite.impuestos.model.AtsModel", "ATSM", "ATS", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.PROCESOS,TipoLicenciaEnum.PRO,null),
    CAJA("ec.com.codesoft.codefaclite.pos.model.CajaModel", "CAJA", "CAJA", ModuloCodefacEnum.POS, CategoriaMenuEnum.GESTIONAR,TipoLicenciaEnum.PRO,null);

    
    private VentanaEnum(String clase, String codigo, String nombre, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu,Integer teclaAtajo) {
        this.claseNombre = clase;
        this.codigo = codigo;
        this.nombre = nombre;
        this.modulo = modulo;
        this.categoriaMenu = categoriaMenu;
        this.maximizado = true;
        this.tipoLicenciaEnum=TipoLicenciaEnum.PRO;
        this.teclaAtajo=teclaAtajo;
    }
    
    
    private VentanaEnum(String clase, String codigo, String nombre, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu,TipoLicenciaEnum tipoLicenciaEnum,Integer teclaAtajo) {
        this.claseNombre = clase;
        this.codigo = codigo;
        this.nombre = nombre;
        this.modulo = modulo;
        this.categoriaMenu = categoriaMenu;
        this.maximizado = true;
        this.tipoLicenciaEnum = tipoLicenciaEnum;
        this.teclaAtajo=teclaAtajo;
    }
    
    private VentanaEnum(String clase, String codigo, String nombre, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu,TipoLicenciaEnum tipoLicenciaEnum,Integer teclaAtajo,Boolean maximizado) {
        this.claseNombre = clase;
        this.codigo = codigo;
        this.nombre = nombre;
        this.modulo = modulo;
        this.categoriaMenu = categoriaMenu;
        this.maximizado = maximizado;
        this.tipoLicenciaEnum = tipoLicenciaEnum;
        this.teclaAtajo=teclaAtajo;
    }

    
    private VentanaEnum(String clase, String codigo, String nombre, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu, boolean maximizado, ModuloCodefacEnum[] modulosPermitidos,TipoLicenciaEnum tipoLicenciaEnum,Integer teclaAtajo) {
        this.claseNombre = clase;
        this.codigo = codigo;
        this.nombre = nombre;
        this.modulo = modulo;
        this.maximizado = true;
        this.categoriaMenu = categoriaMenu;
        this.categoriaMenu = categoriaMenu;
        this.modulosPermitidos = modulosPermitidos;
        this.tipoLicenciaEnum=tipoLicenciaEnum;
        this.teclaAtajo=teclaAtajo;
        this.maximizado=maximizado;
    }

    private String claseNombre;
    private String codigo;
    private String nombre;
    private ModuloCodefacEnum modulo;
    private CategoriaMenuEnum categoriaMenu;
    private boolean maximizado;
    private ModuloCodefacEnum[] modulosPermitidos;
    private JMenuItem jmenuItem;
    private Object instance;
    private TipoLicenciaEnum tipoLicenciaEnum;
    private Integer teclaAtajo;
    

    public Class getClase() {
        try {
            return Class.forName(claseNombre);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentanaEnum.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getClaseNombre() {
        return claseNombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public ModuloCodefacEnum getModulo() {
        return modulo;
    }

    public CategoriaMenuEnum getCategoriaMenu() {
        return categoriaMenu;
    }

    public JMenuItem getJmenuItem() {
        return jmenuItem;
    }

    public void setJmenuItem(JMenuItem jmenuItem) {
        this.jmenuItem = jmenuItem;
    }

    public boolean isMaximizado() {
        return maximizado;
    }

    public TipoLicenciaEnum getTipoLicenciaEnum() {
        return tipoLicenciaEnum;
    }

    public Integer getTeclaAtajo() {
        return teclaAtajo;
    }
    
    
    
    
    /**
     * Metodo que devuelve una lista de categorias por un modulo pero solo de los que tienen datos
     * @param moduloEnum
     * @return 
     */
    public static List<CategoriaMenuEnum> obtenerCategoriasConDatosPorModulo(ModuloCodefacEnum moduloEnum)
    {
        List<CategoriaMenuEnum> categorias=new ArrayList<CategoriaMenuEnum>();
        
        for (VentanaEnum ventanaEnum : VentanaEnum.values()) {
            if(ventanaEnum.getModulo().equals(moduloEnum))
            {
                if(!categorias.contains(ventanaEnum.getCategoriaMenu()))
                {
                    categorias.add(ventanaEnum.getCategoriaMenu());
                }
            }            
        }
        return categorias;
    }

    public Boolean verificarPermisoModuloAdicional(List<ModuloCodefacEnum> modulos) {
        //Si no existe ningun dato en modulo permitidos asumo que no tiene acceso para  los modulos

        if (modulosPermitidos == null) {
            return false;
        }
        
        for (ModuloCodefacEnum moduloSistema : modulos) {
            //Verifico si indirectamente otro modulo necesita de esta pantalla
            for (ModuloCodefacEnum modulosPermitido : modulosPermitidos) 
            {
                if (moduloSistema.equals(modulosPermitido)) 
                {
                    return true;
                }
            }
        }

        /*
        for (Map.Entry<ModuloCodefacEnum, Boolean> entry : modulos.entrySet()) 
        {
            ModuloCodefacEnum moduloSistema = entry.getKey();
            Boolean value = entry.getValue();

            if (value) //Verificar solo para los modulos activos
            {
                //Verifico si indirectamente otro modulo necesita de esta pantalla
                for (ModuloCodefacEnum modulosPermitido : modulosPermitidos) {
                    if (moduloSistema.equals(modulosPermitido)) {
                        return true;
                    }
                }

            }

        }*/
        return false;
    }

    public static List<VentanaEnum> getVentanaByModuloAndCategoria(ModuloCodefacEnum modulo, CategoriaMenuEnum categoria) {
        List<VentanaEnum> listaVentanas = new ArrayList<VentanaEnum>();
        for (VentanaEnum ventana : VentanaEnum.values()) {
            if (ventana.getModulo().equals(modulo) && ventana.getCategoriaMenu().equals(categoria)) {
                listaVentanas.add(ventana);
            }
        }
        return listaVentanas;
    }

    public static VentanaEnum buscarPorCodigo(String codigo) {
        for (VentanaEnum ventana : VentanaEnum.values()) {
            if (ventana.getCodigo().equals(codigo)) {
                return ventana;
            }
        }
        return null;
    }
    
    /**
     * @deprecated
     * @return 
     */    
    public Object getInstance() {
        try {

            if (instance == null) {
                Class clase = getClase();
                Constructor constructor = clase.getConstructor();
                instance = constructor.newInstance();
            }

            return instance;

        } catch (NoSuchMethodException ex) {
            Logger.getLogger(VentanaEnum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(VentanaEnum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(VentanaEnum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(VentanaEnum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(VentanaEnum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(VentanaEnum.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Object createNewInstance() {
        try {
            instance = this.getClase().getConstructor().newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(VentanaEnum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(VentanaEnum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(VentanaEnum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(VentanaEnum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(VentanaEnum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(VentanaEnum.class.getName()).log(Level.SEVERE, null, ex);
        }
        return instance;
    }

    public static List<VentanaEnum> getListValues() {
        List<VentanaEnum> lista = new ArrayList<VentanaEnum>();
        for (VentanaEnum enumerador : VentanaEnum.values()) {
            lista.add(enumerador);
        }
        return lista;
    }
    
    /**
     * Metodo que retorna el objeto enum dependiendo la clase que se pase del objeto
     * @param clase
     * @return 
     */
    public static VentanaEnum getByClass(Class clase)
    {
        for (VentanaEnum object : VentanaEnum.values()) {
            if(clase.equals(object.getClase()))
            {
                return object;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    

}
