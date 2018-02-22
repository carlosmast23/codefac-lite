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
    
    CLIENTE("ec.com.codesoft.codefaclite.crm.model.ClienteModel","CLIE","Cliente",ModuloCodefacEnum.CRM,CategoriaMenuEnum.GESTIONAR,true,new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION}),
    PRODUCTO("ec.com.codesoft.codefaclite.crm.model.ProductoModel","PROD","Producto",ModuloCodefacEnum.CRM,CategoriaMenuEnum.GESTIONAR,true,new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION}),
    FACTURACION("ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel","FACT","facturacion",ModuloCodefacEnum.FACTURACION,CategoriaMenuEnum.PROCESOS),
    EMPRESA("ec.com.codesoft.codefaclite.crm.model.EmpresaModel","EMPR","Empresa",ModuloCodefacEnum.SISTEMA,CategoriaMenuEnum.GESTIONAR,true,new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION}),
    COMPROBANTE_CONFIGURACION("ec.com.codesoft.codefaclite.configuraciones.model.ComprobantesConfiguracionModel","CONF","Comprobante",ModuloCodefacEnum.FACTURACION,CategoriaMenuEnum.GESTIONAR),
    UTILIDAD_COMPROBANTE("ec.com.codesoft.codefaclite.facturacion.model.UtilidadComprobanteModel","UTIL","Utilidad Comprobante",ModuloCodefacEnum.FACTURACION,CategoriaMenuEnum.GESTIONAR),    
    NOTA_CREDITO("ec.com.codesoft.codefaclite.facturacion.model.NotaCreditoModel","NOTC","Nota de credito",ModuloCodefacEnum.FACTURACION,CategoriaMenuEnum.PROCESOS),
    FACTURA_REPORTE("ec.com.codesoft.codefaclite.facturacion.model.FacturaReporteModel","FACR","Factura Reporte",ModuloCodefacEnum.FACTURACION,CategoriaMenuEnum.REPORTES),
    CLIENTE_REPORTE("ec.com.codesoft.codefaclite.crm.model.ClienteReporte","CLIE","Cliente reporte",ModuloCodefacEnum.CRM,CategoriaMenuEnum.REPORTES),
    PRODUCTO_REPORTE("ec.com.codesoft.codefaclite.crm.model.ProductoReporte","CLIE","Producto Reporte",ModuloCodefacEnum.CRM,CategoriaMenuEnum.REPORTES),
    FACTURA_DISENIO("ec.com.codesoft.codefaclite.facturacion.model.FacturaDisenioModel","CLIE","Factura Diseño",ModuloCodefacEnum.FACTURACION,CategoriaMenuEnum.GESTIONAR),
    COMPRA("ec.com.codesoft.codefaclite.crm.model.CompraModel","CLIE","Compra",ModuloCodefacEnum.INVENTARIO,CategoriaMenuEnum.PROCESOS),
    ASOCIAR_PRODUCTO("ec.com.codesoft.codefaclite.inventario.model.AsociarProductoProveedorModel","CLIE","Asociar Producto",ModuloCodefacEnum.INVENTARIO,CategoriaMenuEnum.PROCESOS),
    BODEGA("ec.com.codesoft.codefaclite.inventario.model.BodegaModel","CLIE","Bodega",ModuloCodefacEnum.INVENTARIO,CategoriaMenuEnum.GESTIONAR),
    CATEGORIA_PRODUCTO("ec.com.codesoft.codefaclite.inventario.model.CategoriaProductoModel","CLIE","categoria",ModuloCodefacEnum.INVENTARIO,CategoriaMenuEnum.GESTIONAR),
    INGRESO_INVENTARIO("ec.com.codesoft.codefaclite.inventario.model.IngresoInventarioModel","Ingreso Inventario","Cliente model",ModuloCodefacEnum.INVENTARIO,CategoriaMenuEnum.PROCESOS),
    KARDEX("ec.com.codesoft.codefaclite.inventario.model.KardexModel","CLIE","Kardex",ModuloCodefacEnum.INVENTARIO,CategoriaMenuEnum.REPORTES),
    INVENTARIO_ENSAMBLE("ec.com.codesoft.codefaclite.inventario.model.InventarioEnsambleModel","Inventario ensamble","Cliente model",ModuloCodefacEnum.INVENTARIO,CategoriaMenuEnum.PROCESOS),
    AULA("ec.com.codesoft.codefaclite.gestionacademica.model.AulaModel","CLIE","Aula",ModuloCodefacEnum.GESTIONA_ACADEMICA,CategoriaMenuEnum.GESTIONAR),
    NIVEL("ec.com.codesoft.codefaclite.gestionacademica.model.NivelModel","CLIE","Nivel",ModuloCodefacEnum.GESTIONA_ACADEMICA,CategoriaMenuEnum.GESTIONAR),
    PERIODO("ec.com.codesoft.codefaclite.gestionacademica.model.PeriodoModel","CLIE","Periodo",ModuloCodefacEnum.GESTIONA_ACADEMICA,CategoriaMenuEnum.GESTIONAR),
    PERFILES("ec.com.codesoft.codefaclite.configuraciones.model.PerfilModel","UTIL","Utilidad Comprobante",ModuloCodefacEnum.SISTEMA,CategoriaMenuEnum.GESTIONAR,true,new ModuloCodefacEnum[]{ModuloCodefacEnum.FACTURACION});

    private VentanaEnum(String clase, String codigo, String nombre, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu) {
        this.claseNombre = clase;
        this.codigo = codigo;
        this.nombre = nombre;
        this.modulo = modulo;
        this.categoriaMenu=categoriaMenu;
        this.maximizado=true;
    }
    
    private VentanaEnum(String clase, String codigo, String nombre, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu, boolean maximizado) {
        this.claseNombre = clase;
        this.codigo = codigo;
        this.nombre = nombre;
        this.modulo = modulo;
        this.categoriaMenu=categoriaMenu;
        this.maximizado = maximizado;
    }
    
    private VentanaEnum(String clase, String codigo, String nombre, ModuloCodefacEnum modulo, CategoriaMenuEnum categoriaMenu,boolean maximizado,ModuloCodefacEnum[] modulosPermitidos) {
        this.claseNombre = clase;
        this.codigo = codigo;
        this.nombre = nombre;
        this.modulo = modulo;
        this.maximizado=true;
        this.categoriaMenu=categoriaMenu;
        this.categoriaMenu = categoriaMenu;
        this.modulosPermitidos=modulosPermitidos;
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
    
    public Class getClase()
    {
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
    
        public Boolean verificarPermisoModuloAdicional(Map<ModuloCodefacEnum, Boolean> modulos) {
        //Si no existe ningun dato en modulo permitidos asumo que tiene acceso para todos los modulos
        
        if(modulosPermitidos==null)
            return false;
        

        for (Map.Entry<ModuloCodefacEnum, Boolean> entry : modulos.entrySet()) {
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
        
    public static List<VentanaEnum> getVentanaByModuloAndCategoria(ModuloCodefacEnum modulo,CategoriaMenuEnum categoria)
    {
        List<VentanaEnum> listaVentanas=new ArrayList<VentanaEnum>();
        for (VentanaEnum ventana : VentanaEnum.values()) {
            if(ventana.getModulo().equals(modulo) && ventana.getCategoriaMenu().equals(categoria))
            {
                listaVentanas.add(ventana);
            }
        }
        return listaVentanas;
    }
    
    public static VentanaEnum buscarPorCodigo(String codigo)
    {
        for (VentanaEnum ventana : VentanaEnum.values()) {
            if(ventana.getCodigo().equals(codigo))
            {
               return ventana;
            }
        }
        return null;
    }
    
     public Object getInstance() {
        try {

            if (instance == null) {
                Class clase=getClase();
                Constructor constructor= clase.getConstructor();
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

    
    public static List<VentanaEnum> getListValues()
    {
        List<VentanaEnum> lista=new ArrayList<VentanaEnum>();
        for (VentanaEnum enumerador : VentanaEnum.values()) {
            lista.add(enumerador);            
        }
        return lista;
    }
    

}
