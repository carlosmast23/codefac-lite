/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;

/**
 *
 * @author Carlos
 */
public enum VentanaEnum {

    CLIENTE("ec.com.codesoft.codefaclite.crm.model.ClienteModel", "CLIE", "Cliente", ModuloCodefacEnum.CRM, CategoriaMenuEnum.GESTIONAR, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS),
    PROVEEDOR("ec.com.codesoft.codefaclite.crm.model.ProveedorModel", "PROV", "Proveedor", ModuloCodefacEnum.CRM, CategoriaMenuEnum.GESTIONAR, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS),
    PRODUCTO("ec.com.codesoft.codefaclite.crm.model.ProductoModel", "PROD", "Producto", ModuloCodefacEnum.CRM, CategoriaMenuEnum.GESTIONAR, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS),
    FACTURACION("ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel", "FACT", "Facturación", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.PROCESOS,TipoLicenciaEnum.GRATIS),
    EMPRESA("ec.com.codesoft.codefaclite.crm.model.EmpresaModel", "EMPR", "Empresa", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.GESTIONAR, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS),
    COMPROBANTE_CONFIGURACION("ec.com.codesoft.codefaclite.configuraciones.model.ComprobantesConfiguracionModel", "CONF", "Configuración Comprobantes", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.GESTIONAR,TipoLicenciaEnum.GRATIS),
    UTILIDAD_COMPROBANTE("ec.com.codesoft.codefaclite.facturacion.model.UtilidadComprobanteModel", "UTIL", "Utilidad Comprobante", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.UTILIDADES,TipoLicenciaEnum.GRATIS),
    NOTA_CREDITO("ec.com.codesoft.codefaclite.facturacion.model.NotaCreditoModel", "NOTC", "Nota de Credito", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.PROCESOS,TipoLicenciaEnum.GRATIS),
    FACTURA_REPORTE("ec.com.codesoft.codefaclite.facturacion.model.FacturaReporteModel", "FACR", "Factura Reporte", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.REPORTES,TipoLicenciaEnum.GRATIS),
    CLIENTE_REPORTE("ec.com.codesoft.codefaclite.crm.model.ClienteReporte", "CLIR", "Cliente reporte", ModuloCodefacEnum.CRM, CategoriaMenuEnum.REPORTES, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS),
    PRODUCTO_REPORTE("ec.com.codesoft.codefaclite.crm.model.ProductoReporte", "PROR", "Producto Reporte", ModuloCodefacEnum.CRM, CategoriaMenuEnum.REPORTES, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS),
    FACTURA_DISENIO("ec.com.codesoft.codefaclite.facturacion.model.FacturaDisenioModel", "FACD", "Factura Diseño", ModuloCodefacEnum.FACTURACION, CategoriaMenuEnum.GESTIONAR,TipoLicenciaEnum.GRATIS),
    COMPRA("ec.com.codesoft.codefaclite.compra.model.CompraModel", "COMP", "Compra", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.PROCESOS),
    COMPRA_REPORTE("ec.com.codesoft.codefaclite.compra.model.CompraReporteModel", "CMPR", "Compra Reporte", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.REPORTES),
    COMPRA_REPORTE_PRODUCTO("ec.com.codesoft.codefaclite.compra.model.CompraReporteProductoModel", "CRPM", "Compra Reporte Producto", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.REPORTES),
    ASOCIAR_PRODUCTO("ec.com.codesoft.codefaclite.inventario.model.AsociarProductoProveedorModel", "ASOP", "Asociar Producto", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.PROCESOS),
    BODEGA("ec.com.codesoft.codefaclite.inventario.model.BodegaModel", "BODG", "Bodega", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.GESTIONAR),
    CATEGORIA_PRODUCTO("ec.com.codesoft.codefaclite.inventario.model.CategoriaProductoModel", "CATG", "Categoria", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.GESTIONAR,TipoLicenciaEnum.GRATIS),
    INGRESO_INVENTARIO("ec.com.codesoft.codefaclite.inventario.model.IngresoInventarioModel", "ININ", "Ingreso Inventario", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.PROCESOS),
    KARDEX("ec.com.codesoft.codefaclite.inventario.model.KardexModel", "KARD", "Kardex", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.REPORTES),
    INVENTARIO_ENSAMBLE("ec.com.codesoft.codefaclite.inventario.model.InventarioEnsambleModel", "INVE", "Inventario Ensamble", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.PROCESOS),
    AULA("ec.com.codesoft.codefaclite.gestionacademica.model.AulaModel", "AULA", "Aula", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.GESTIONAR),
    NIVEL("ec.com.codesoft.codefaclite.gestionacademica.model.NivelModel", "NIVE", "Nivel", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.GESTIONAR),
    PERIODO("ec.com.codesoft.codefaclite.gestionacademica.model.PeriodoModel", "PERI", "Periodo", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.GESTIONAR),
    PERFILES("ec.com.codesoft.codefaclite.configuraciones.model.PerfilModel", "PERF", "Perfiles", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.GESTIONAR, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION}),
    USUARIOS("ec.com.codesoft.codefaclite.configuraciones.model.PerfilUsuarioModel", "PEUM", "Usuario", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.GESTIONAR, true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS),
    MATRICULA("ec.com.codesoft.codefaclite.gestionacademica.model.MatriculaModel", "MATR", "Matricula Grupo", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.MATRICULA),
    NIVEL_ACADEMICO("ec.com.codesoft.codefaclite.gestionacademica.model.NivelAcademicoModel", "NIAC", "Cursos", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.GESTIONAR),
    ESTUDIANTES("ec.com.codesoft.codefaclite.gestionacademica.model.EstudianteModel", "ESTU", "Estudiante", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.GESTIONAR),
    RUBRO_ACADEMICO("ec.com.codesoft.codefaclite.gestionacademica.model.RubrosPeriodoModel", "RUAC", "Rubro", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.DEUDAS_ACADEMICOS),
    GESTIONAR_ACADEMICO("ec.com.codesoft.codefaclite.gestionacademica.model.GestionarDeudasModel", "RUAC", "Deudas Curso", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.DEUDAS_ACADEMICOS),
    FACTURA_ACADEMICO_LOTE("ec.com.codesoft.codefaclite.facturacion.model.FacturaAcademicoLoteModel", "FACL", "Factura Lote", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.PROCESOS),
    REPORTE_MATRICULA("ec.com.codesoft.codefaclite.gestionacademica.model.ReporteAcademicoModel", "RMAT", "Reporte Matricula", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.REPORTES),
    REPORTE_DEUDAS("ec.com.codesoft.codefaclite.gestionacademica.model.ReporteDeudasModel", "RDES", "Reporte Deudas", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.REPORTES),
    REPORTE_DEUDASESTUDIANTE("ec.com.codesoft.codefaclite.gestionacademica.model.ReporteDeudasEstudianteModel", "RDEI", "Reporte Deudas Estudiante", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.REPORTES),
    REPORTE_DEUDASCURSO("ec.com.codesoft.codefaclite.gestionacademica.model.ReporteDeudasCursoModel", "RDEC", "Reporte Deudas Curso", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.REPORTES),
    DEUDA_ESTUDIANTE("ec.com.codesoft.codefaclite.gestionacademica.model.DeudaEstudianteModel", "DEML", "Deuda Estudiante", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.DEUDAS_ACADEMICOS),
    CATALOGO_PRODUCTO("ec.com.codesoft.codefaclite.inventario.model.CatalogoProductoModel", "CAPR", "Catalogo Producto", ModuloCodefacEnum.INVENTARIO, CategoriaMenuEnum.GESTIONAR,TipoLicenciaEnum.GRATIS),
    RUBRO_PLANTILLA("ec.com.codesoft.codefaclite.gestionacademica.model.RubroPlantillaModel", "RUBR", "Rubros Mensuales", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.DEUDAS_ACADEMICOS),
    NOTIFICACION_DEUDAS("ec.com.codesoft.codefaclite.gestionacademica.model.NotificacionesDeudasModel", "NODE", "Notificaciones", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.UTILIDADES),
    MATRICULA_ESTUDIANTE("ec.com.codesoft.codefaclite.gestionacademica.model.MatriculaEstudianteModel", "MAES", "Matricula", ModuloCodefacEnum.GESTIONA_ACADEMICA, CategoriaMenuEnum.MATRICULA),
    RESPALDAR_INFORMACION("ec.com.codesoft.codefaclite.configuraciones.model.RespaldarInformacionModel", "RESP", "Respaldar Informacion", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.PROCESOS,true, new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION},TipoLicenciaEnum.GRATIS),
    CONFIGURACION_DEFECTO("ec.com.codesoft.codefaclite.configuraciones.model.ConfiguracionDefectoModel", "CFDF", "Configuraciones por Defecto", ModuloCodefacEnum.SISTEMA, CategoriaMenuEnum.PROCESOS),
    RETENCIONES_PENDIENTES("ec.com.codesoft.codefaclite.compra.model.RetencionesPendienteModel", "RETP", "Retenciones Pendientes", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.PROCESOS),
    RETENCIONES("ec.com.codesoft.codefaclite.compra.model.RetencionModel", "RETC", "Retención", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.PROCESOS),
    RETENCION_REPORTE("ec.com.codesoft.codefaclite.compra.model.RetencionReporteModel", "CRET", "Reporte Retenciones", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.REPORTES),
    CARTERA("ec.com.codesoft.codefaclite.cartera.model.CarteraModel", "CART", "Cartera", ModuloCodefacEnum.CARTERA, CategoriaMenuEnum.PROCESOS),
    ORDEN_TRABAJO("ec.com.codesoft.codefaclite.servicios.model.OrdenTrabajoModel", "ORDT", "Orden de Trabajo", ModuloCodefacEnum.SERVICIOS, CategoriaMenuEnum.PROCESOS),
    PRESUPUESTO("ec.com.codesoft.codefaclite.servicios.model.PresupuestoModel", "PRES", "Presupuesto", ModuloCodefacEnum.SERVICIOS, CategoriaMenuEnum.PROCESOS),
    ORDEN_COMPRA("ec.com.codesoft.codefaclite.compra.model.OrdenCompraModel", "ODCP", "Orden de Compra", ModuloCodefacEnum.COMPRA, CategoriaMenuEnum.PROCESOS);

    private VentanaEnum(String clase, String codigo, String nombre, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu) {
        this.claseNombre = clase;
        this.codigo = codigo;
        this.nombre = nombre;
        this.modulo = modulo;
        this.categoriaMenu = categoriaMenu;
        this.maximizado = true;
        this.tipoLicenciaEnum=TipoLicenciaEnum.PRO;
    }
    
    private VentanaEnum(String clase, String codigo, String nombre, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu,TipoLicenciaEnum tipoLicenciaEnum) {
        this.claseNombre = clase;
        this.codigo = codigo;
        this.nombre = nombre;
        this.modulo = modulo;
        this.categoriaMenu = categoriaMenu;
        this.maximizado = true;
        this.tipoLicenciaEnum = tipoLicenciaEnum;
    }

    private VentanaEnum(String clase, String codigo, String nombre, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu, boolean maximizado) {
        this.claseNombre = clase;
        this.codigo = codigo;
        this.nombre = nombre;
        this.modulo = modulo;
        this.categoriaMenu = categoriaMenu;
        this.maximizado = maximizado;
        this.tipoLicenciaEnum=TipoLicenciaEnum.PRO;
    }

    private VentanaEnum(String clase, String codigo, String nombre, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu, boolean maximizado, ModuloCodefacEnum[] modulosPermitidos) {
        this.claseNombre = clase;
        this.codigo = codigo;
        this.nombre = nombre;
        this.modulo = modulo;
        this.maximizado = true;
        this.categoriaMenu = categoriaMenu;
        this.categoriaMenu = categoriaMenu;
        this.modulosPermitidos = modulosPermitidos;
        this.tipoLicenciaEnum=TipoLicenciaEnum.PRO;
    }
    
    private VentanaEnum(String clase, String codigo, String nombre, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu, boolean maximizado, ModuloCodefacEnum[] modulosPermitidos,TipoLicenciaEnum tipoLicenciaEnum) {
        this.claseNombre = clase;
        this.codigo = codigo;
        this.nombre = nombre;
        this.modulo = modulo;
        this.maximizado = true;
        this.categoriaMenu = categoriaMenu;
        this.categoriaMenu = categoriaMenu;
        this.modulosPermitidos = modulosPermitidos;
        this.tipoLicenciaEnum=tipoLicenciaEnum;
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

    public Boolean verificarPermisoModuloAdicional(Map<ModuloCodefacEnum, Boolean> modulos) {
        //Si no existe ningun dato en modulo permitidos asumo que no tiene acceso para  los modulos

        if (modulosPermitidos == null) {
            return false;
        }

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

        }
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

}
