/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import com.sun.mail.handlers.multipart_mixed;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.facade.KardexFacade;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.auxiliar.KardexDetalleTmp;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FechaFormatoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.ObtenerFecha;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Carlos
 */
public class KardexService extends ServiceAbstract<Kardex,KardexFacade> implements KardexServiceIf
{
    
    /**
     * Entidad de control para manejar transacciones con la base de datos
     */
    private EntityManager em;
    
    public KardexService() throws RemoteException {
        super(KardexFacade.class);
        em=AbstractFacade.entityManager;
    }
    
    /**
     * Metodo que permite buscar el kardex por bodega y el el producto
     * @return 
     */
    
    public Kardex buscarKardexPorProductoyBodega(Bodega bodega,Producto producto) throws java.rmi.RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("bodega",bodega);
        mapParametros.put("producto",producto);   
        List<Kardex> listaKardex=getFacade().findByMap(mapParametros);
        //List<Kardex> listaKardex=obtenerPorMap(mapParametros);
        
        if(listaKardex!=null && listaKardex.size()>0)
        {
            return listaKardex.get(0);
        }
        
        return null;
    }
    
    /**
     * Obtiene los valores modificos del stock y la reserva para grabar en el Kardex
     * @return 
     */
    public List<Kardex> getKardexModificados(Producto productoEnsamble,Integer cantidadEnsamble,Bodega bodega,ProductoEnsamble.EnsambleAccionEnum accion) throws java.rmi.RemoteException,ServicioCodefacException
    {
        //Integer cantidadEnsamble=Integer.parseInt(getTxtCantidad().getText());
        
        //Bodega bodega = (Bodega) getCmbBodega().getSelectedItem();
        //String accion = getCmbAccion().getSelectedItem().toString();
        List<Kardex> kardeList=new ArrayList<Kardex>();
        
        for(ProductoEnsamble componenteProducto: productoEnsamble.getDetallesEnsamble())
        {
            try {
                Vector<String> fila=new Vector<String>();
                Integer cantidadProducto=componenteProducto.getCantidad();
                
                Producto componente=componenteProducto.getComponenteEnsamble();
                //Map<String,Object> mapParametros=new HashMap<String,Object>();
                //mapParametros.put("producto",componente);
                //mapParametros.put("bodega",bodega);
                KardexServiceIf servicioKardex=ServiceFactory.getFactory().getKardexServiceIf();
                Kardex kardexComponente= servicioKardex.buscarKardexPorProductoyBodega(bodega,componente);
                if(kardexComponente!=null)
                {
                    Integer cantidadTotal=cantidadEnsamble*cantidadProducto;
                    //Kardex kardexComponente=listaKardex.get(0);
                    //Este paso lo hago porque cuando seteo un valor a una entidad cuando esta asociado automaticamente se refleja en la base de datos
                    //ServiceAbstract.desasociarEntidadRecursivo(kardexComponente);
                    
                    if(accion.equals(ProductoEnsamble.EnsambleAccionEnum.AGREGAR) 
                            || accion.equals(ProductoEnsamble.EnsambleAccionEnum.CONSTRUIR_FACTURA))
                    {
                        kardexComponente.setReserva(kardexComponente.getReserva()+cantidadTotal);
                        kardexComponente.setStock(kardexComponente.getStock()-cantidadTotal);
                    }
                    else
                    {
                        kardexComponente.setReserva(kardexComponente.getReserva() - cantidadTotal);
                        kardexComponente.setStock(kardexComponente.getStock() + cantidadTotal);
                    }
                    
                    //Agregar el detalle de kardex
                    KardexDetalle kardexDetalle=new KardexDetalle();
                    kardexDetalle.setCantidad(cantidadTotal);
                    
                    if(accion.equals(ProductoEnsamble.EnsambleAccionEnum.AGREGAR) ||
                            accion.equals(ProductoEnsamble.EnsambleAccionEnum.CONSTRUIR_FACTURA))
                    {
                        kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_EGRESO.getCodigo());
                    }
                    else
                    {
                        kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_INGRESO.getCodigo());
                    }
                    
                    kardexDetalle.setPrecioTotal(new BigDecimal(cantidadTotal).multiply(kardexComponente.getPrecioUltimo()));
                    kardexDetalle.setPrecioUnitario(kardexComponente.getPrecioUltimo());
                    kardexDetalle.setReferenciaDocumentoId(null);
                    kardexDetalle.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                    kardexDetalle.setFechaDocumento(UtilidadesFecha.getFechaHoy());
                    kardexDetalle.setFechaIngreso(UtilidadesFecha.getFechaHoy());
                    
                    kardexComponente.addDetalleKardex(kardexDetalle);
                    
                    kardeList.add(kardexComponente);
                    
                }
            } catch (RemoteException ex) {
                Logger.getLogger(KardexService.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        
        return kardeList;
        
    }
    
    
    public void ingresoEgresoInventarioEnsamble(Bodega bodega, Producto productoEnsamble,Integer cantidad,ProductoEnsamble.EnsambleAccionEnum accion,Boolean validarStockComponentes) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                ingresoEgresoInventarioEnsambleSinTransaccion(bodega, productoEnsamble,cantidad, ProductoEnsamble.EnsambleAccionEnum.AGREGAR,validarStockComponentes);
            }
        });
        
        
    }
    
    public void ingresoEgresoInventarioEnsambleSinTransaccion(Bodega bodega, Producto productoEnsamble,Integer cantidad,ProductoEnsamble.EnsambleAccionEnum accion,Boolean validarStockComponentes) throws java.rmi.RemoteException,ServicioCodefacException
    {
        /**
         * ===============> Buscar el Ensamble de producto o crear
         * <============//
         */
        Map<String, Object> parametrosMap = new HashMap<String, Object>();
        parametrosMap.put("bodega", bodega);
        parametrosMap.put("producto", productoEnsamble);
        List<Kardex> kardexList = obtenerPorMap(parametrosMap);
        
        /**
         * ==========> Validar Disponibilidad de los producto<==========
         */
        
        //Verificar si tiene habilitada la opcion de facturar inventario negativo activado para validar esta opcion 
        if(validarStockComponentes && (accion.equals(ProductoEnsamble.EnsambleAccionEnum.CONSTRUIR_FACTURA) )
                && ParametroUtilidades.comparar(bodega.getEmpresa(),ParametroCodefac.FACTURAR_INVENTARIO_NEGATIVO,EnumSiNo.NO))
        {
            validarEnsambleComponentes(productoEnsamble, bodega, cantidad);
        }
        

        //Obtener o crear el kardex si no existe
        Kardex kardex = null;
        if (kardexList != null && kardexList.size() > 0) {
            kardex = kardex = kardexList.get(0);
        } else {
            kardex = crearObjeto(bodega, productoEnsamble);
            entityManager.persist(kardex);
        }

        List<Kardex> componentesKardex = getKardexModificados(productoEnsamble, cantidad, bodega, accion);
        //Actualizar los detalles de los componentes del kardex                        
        for (Kardex kardexComponente : componentesKardex) {
            entityManager.merge(kardexComponente);

        }

        //Calcular el valor del ensamble
        BigDecimal costoIndividualEnsamble = BigDecimal.ZERO;
        List<ProductoEnsamble> listaComponentes = kardex.getProducto().getDetallesEnsamble();
        for (ProductoEnsamble componenteEmsamble : listaComponentes) {
            parametrosMap = new HashMap<String, Object>();
            parametrosMap.put("bodega", bodega);
            parametrosMap.put("producto", componenteEmsamble.getComponenteEnsamble());
            kardexList = obtenerPorMap(parametrosMap);

            costoIndividualEnsamble = costoIndividualEnsamble.add(new BigDecimal(componenteEmsamble.getCantidad().toString()).multiply(kardexList.get(0).getPrecioUltimo()));
        }

        ///Actualizar los totales del emsamble
        kardex.setPrecioPromedio(kardex.getPrecioPromedio().add(costoIndividualEnsamble).divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP));
        kardex.setPrecioTotal(kardex.getPrecioTotal().add(costoIndividualEnsamble));
        kardex.setPrecioUltimo(costoIndividualEnsamble);

        //Agregar o descontar el stock cuando se ingrese o salga mercaderia
        if (accion.equals(ProductoEnsamble.EnsambleAccionEnum.AGREGAR) || accion.equals(ProductoEnsamble.EnsambleAccionEnum.CONSTRUIR_FACTURA) ) {
            kardex.setStock(kardex.getStock() + cantidad);
        } else {
            kardex.setStock(kardex.getStock() - cantidad);
        }

        //Crear el registro del kardex detalle 
        KardexDetalle kardexDetalle = new KardexDetalle();

        //Agregar o descontar el stock cuando se ingrese o salga mercaderia
        
        if (accion.equals(ProductoEnsamble.EnsambleAccionEnum.AGREGAR)) {
            kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_INGRESO.getCodigo());
        }
        else if(accion.equals(ProductoEnsamble.EnsambleAccionEnum.CONSTRUIR_FACTURA)) 
        {
            kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_CONSTRUIR_VENTA.getCodigo());
        
        }else {
            kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_EGRESO.getCodigo());
        }

        kardexDetalle.setCantidad(cantidad);
        kardexDetalle.setPrecioTotal(costoIndividualEnsamble.multiply(new BigDecimal(cantidad.toString())));
        kardexDetalle.setPrecioUnitario(costoIndividualEnsamble);
        kardexDetalle.setReferenciaDocumentoId(null);
        kardexDetalle.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        kardexDetalle.setFechaDocumento(UtilidadesFecha.getFechaHoy());
        kardexDetalle.setFechaIngreso(UtilidadesFecha.getFechaHoy());

        kardex.addDetalleKardex(kardexDetalle);
        entityManager.persist(kardexDetalle);
        entityManager.merge(kardex);
        entityManager.flush();
    }
    
    /**
     * Metodo que permite verificar si tiene el ensamble tiene la cantidad necesario de stock de sus componentes
     * @param productoEnsamble
     * @param bodega
     * @param cantidad
     * @throws java.rmi.RemoteException
     * @throws ServicioCodefacException 
     */
    private void validarEnsambleComponentes( Producto productoEnsamble,Bodega bodega,int cantidad) throws java.rmi.RemoteException,ServicioCodefacException
    {        
  
        for(ProductoEnsamble componenteProducto: productoEnsamble.getDetallesEnsamble())
        {
            Producto componente=componenteProducto.getComponenteEnsamble();
            KardexServiceIf servicioKardex=ServiceFactory.getFactory().getKardexServiceIf();
            Kardex kardexResultado= servicioKardex.buscarKardexPorProductoyBodega(bodega,componente);
            
            if(kardexResultado==null)
            {
                throw new ServicioCodefacException("El producto "+productoEnsamble.getNombre()+" no tiene sufiente stock de "+componente.getNombre()+" para construir");
            }
            else
            {
                Integer productosFaltantes=kardexResultado.getStock()-componenteProducto.getCantidad()*cantidad;
                //boolean disponible=
                //TODO:Si no existe la cantidad disponible del producto lanza una exceptcion
                if(productosFaltantes<0)
                {
                    throw new ServicioCodefacException("El producto "+productoEnsamble.getNombre()+" no tiene sufiente stock de "+componente.getNombre()+"para construir, faltante = "+Math.abs(productosFaltantes));
                }
            }
            
        }
    }
    
    public void ingresarInventario(KardexDetalle detalle) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarKardexDetallSinTransaccion(detalle);
            }
        });
    }
    
    public void transferirProductoBodegas(Producto producto,Bodega bodegaOrigen,Bodega bodegaDestino, String descripcion,Integer cantidad,BigDecimal precio,Date fechaTransaccion) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                ///========> Validaciones basicas de los datos ingresados <=========================//
                if(cantidad<=0)
                {
                    throw new ServicioCodefacException("Solo se puede hacer transferencias de cantidades positivas");
                }
                
                if(bodegaOrigen.equals(bodegaDestino))
                {
                    throw new ServicioCodefacException("No se puede hacer una transferencia a la misma bodega");
                }
                
                
                
                Empresa empresa=producto.getEmpresa();
                //==========> Buscar Primero para ver si existe el kardex del producto y la bodega <==========//
                List<Kardex> kardexResultado=buscarPorProductoYBodega(producto, bodegaOrigen);
                if(kardexResultado==null || kardexResultado.size()==0)
                {
                    throw new ServicioCodefacException("No existe un kardex para el producto en la bodega");
                }
                
                //==============> Verificar si tiene la cantidad disponible para transferir <============//
                Kardex kardexOrigen=kardexResultado.get(0);
                if(cantidad>kardexOrigen.getStock())
                {
                    throw new ServicioCodefacException("Cantidad insuficiente para hacer la transferencia");
                }
                
                
                //=============> Obtener el Kardex del producto de destino o crearlo <==================//
                kardexResultado=buscarPorProductoYBodega(producto, bodegaDestino);
                Kardex kardexDestino=null; //Referencia para guardar el kardex de destino
                if(kardexResultado==null || kardexResultado.size()==0)
                {
                    //Si no existe el kardex de destino creo uno similar con los datos del otro Kardex
                    kardexDestino=crearObjeto(bodegaDestino,producto);
                    entityManager.persist(kardexDestino);
                    
                }
                else//Si existe el kardex solo lo cargo
                {
                    kardexDestino=kardexResultado.get(0);
                }
                
                
                //===========> Crear los detalles de los kardex para hacer los movimientos <===============//
                KardexDetalle kardexDetalleOrigen=crearKardexDetalleSinPersistencia(
                        kardexOrigen, 
                        TipoDocumentoEnum.TRANSFERENCIA_MERCADERIA_ORIGEN, 
                        precio, 
                        cantidad);
                
                KardexDetalle kardexDetalleDestino=crearKardexDetalleSinPersistencia(
                        kardexDestino, 
                        TipoDocumentoEnum.TRANSFERENCIA_MERCADERIA_DESTINO, 
                        precio, 
                        cantidad);
                
                entityManager.persist(kardexDetalleOrigen);
                entityManager.persist(kardexDetalleDestino);
                
                kardexOrigen.addDetalleKardex(kardexDetalleOrigen);
                kardexDestino.addDetalleKardex(kardexDetalleDestino);                
                
                
                //===========> Recalcular los totales de los nuevos kardex <=============//
                recalcularValoresKardex(kardexOrigen, kardexDetalleOrigen);
                recalcularValoresKardex(kardexDestino, kardexDetalleDestino);
                entityManager.merge(kardexOrigen);
                entityManager.merge(kardexDestino);
                

                
                //===========> Guardar las referencias de los otros kardex detalle para algun reporte <=========//
                kardexDetalleDestino.setReferenciaDocumentoId(kardexDetalleOrigen.getId());
                kardexDetalleOrigen.setReferenciaDocumentoId(kardexDetalleDestino.getId());
                entityManager.merge(kardexDetalleDestino);
                entityManager.merge(kardexDetalleOrigen);
                
                
            }
        });
    }
    
    /**
     * Metodo que mer permite calcular el stock , los precios promedios y demas valores 
     * @param kardex
     * @param kardexDetalle 
     */
    public void recalcularValoresKardex(Kardex kardex,KardexDetalle kardexDetalle) throws java.rmi.RemoteException,ServicioCodefacException
    {

        BigDecimal costoPonderado=kardex.getPrecioPromedio(); 
        
        //Si el movimiento del kardex detalle esta clasificado como que afecta a el inventario entonces hago el resto de calculos
        if(kardexDetalle.getCodigoTipoDocumentoEnum().getAfectaCostoInventario())
        {
            //ALMACENA EL ULTIMO VALOR INGRESADO SIEMPRE QUE SEA UNA COMPRA
            kardex.setPrecioUltimo(kardexDetalle.getPrecioUnitario());
            //Calcular el precio promedio con respecto al nuevo valor
            costoPonderado=calcularPrecioPonderado(kardex,kardexDetalle);
            kardex.setPrecioPromedio(costoPonderado);            
            
            
        }
         
        Integer signo=kardexDetalle.getCodigoTipoDocumentoEnum().getSignoInventarioNumero();
        Integer stockFinal=kardex.getStock()+signo*kardexDetalle.getCantidad();
        kardex.setStock(stockFinal);
        
        //CALCULAR EL PRECIO CON EL STOCK FINAL
        kardex.calcularPrecioTotal();
        
        
    }
    
    /**
     * Formula general para calcular el precio ponderado del producto
     * @param kardex
     * @param kardexDetalle
     * @return 
     */
    private BigDecimal calcularPrecioPonderado(Kardex kardex,KardexDetalle kardexDetalle)
    {
        /**
         * =================> VALIDACIONES PARA EVITAR ERROES <===============
         */
        //S no existe stock no se hace el calculo del precio promedio porque eso solo va a generar errores
         if(kardex.getStock()<=0)
         {
             return kardex.getPrecioPromedio(); 
         }
        
        /**
         * =================> PROCESOS DE CALCULO DEL STOCK <=================
         * Para calculoar el stock se utiliza la siguiente formular
         * costo=(stock*costoActual)*(cantidad*precioUnit)/(stock+cantidad)
         */
        Integer stock=kardex.getStock();
        BigDecimal costoPonderado=kardex.getPrecioPromedio();
        
        Integer cantidadUnitaria=kardexDetalle.getCantidad();
        BigDecimal precioUnitario=kardexDetalle.getPrecioUnitario();
        
        //Primero calculo el numerador 
        BigDecimal resultadoCosto= costoPonderado.multiply(new BigDecimal(stock)).add(precioUnitario.multiply(new BigDecimal(cantidadUnitaria)));
        BigDecimal cantidadTotal=new BigDecimal(stock+cantidadUnitaria);
        //Calculo el denominador que es dividir para el total de productos
        resultadoCosto=resultadoCosto.divide(cantidadTotal,4,RoundingMode.HALF_UP); //Por defecto dejo 4 decimales porque puede ser que para productos de centavos el calculo sea muy impresiso
        
        return resultadoCosto;
    }
    
    public  KardexDetalle crearKardexDetalleSinPersistencia(Kardex kardex,TipoDocumentoEnum tipoDocumentoEnum,BigDecimal precioUnitario,Integer cantidad) throws java.rmi.RemoteException,ServicioCodefacException
    {
        KardexDetalle movimientoOrigen = new KardexDetalle();
        BigDecimal total=precioUnitario.multiply(new BigDecimal(cantidad.toString()));
        movimientoOrigen.setCantidad(cantidad);
        movimientoOrigen.setCodigoTipoDocumentoEnum(tipoDocumentoEnum);
        movimientoOrigen.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        movimientoOrigen.setFechaIngreso(UtilidadesFecha.getFechaHoy());
        movimientoOrigen.setKardex(kardex);
        //movimientoOrigen.setNombreLegal("");
        movimientoOrigen.setPrecioTotal(total);
        movimientoOrigen.setPrecioUnitario(precioUnitario);
        //movimientoOrigen.setPuntoEmision("");
        movimientoOrigen.setReferenciaDocumentoId(null);
        return movimientoOrigen;
    }
    
    public void anularInventario(Kardex kardex) throws java.rmi.RemoteException,ServicioCodefacException
    {
        /**
         * Validaciones del Stock
         */
        if(kardex.getStock()==0)
        {
            throw new ServicioCodefacException("No se puede eliminar porque el Stock esta en 0");
        }
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                Integer stockAnular= kardex.getStock()*-1;
                KardexDetalle kardexDetalle=new KardexDetalle();                
                kardexDetalle.setPrecioUnitario(kardex.getPrecioPromedio());
                
                if(stockAnular>0)
                {
                    kardexDetalle.setCantidad(stockAnular);
                    kardexDetalle.setCodigoTipoDocumentoEnum(TipoDocumentoEnum.ANULAR_MERCADERIA_POSITIVO);
                }
                else
                {
                    kardexDetalle.setCantidad(stockAnular*-1);
                    kardexDetalle.setCodigoTipoDocumentoEnum(TipoDocumentoEnum.ANULAR_MERCADERIA_NEGATIVO);
                }
                kardexDetalle.recalcularTotalSinGarantia();
                kardexDetalle.setFechaIngreso(UtilidadesFecha.getFechaHoy());
                kardexDetalle.setKardex(kardex);
                
                grabarKardexDetallSinTransaccion(kardexDetalle);
            }
        });
        
        
    }
    
    public void ingresarInventario(List<KardexDetalle> detalles) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                for (KardexDetalle detalle : detalles) {
                    grabarKardexDetallSinTransaccion(detalle);
                    
                }
                
            }
        });
    }
    
    
    public void grabarKardexDetallSinTransaccion(KardexDetalle detalle) throws RemoteException, ServicioCodefacException
    {
        /**
         * ==============================================================
         *            VALIDACIONES PARA LOS DETALLES DE KARDEX
         * ==============================================================
         */
        if(detalle.getKardex()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin referencia de Kardex vacio");
        }
        
        //Buscar si ya existe el kardex o si no existe los creamos
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bodega", detalle.getKardex().getBodega());
        map.put("producto", detalle.getKardex().getProducto());

        List<Kardex> kardexList = getFacade().findByMap(map);

        //TODO:Ver si se puede crear una sola funcion estandar de Kardex
        Kardex kardex = null;
        if (kardexList == null | kardexList.size() == 0) {
            //Si no existe completo los datos para crear el kardex
            kardex = detalle.getKardex();
            //kardex.setBodega(bodega);
            kardex.setFechaCreacion(UtilidadesFecha.getFechaHoy());
            kardex.setFechaModificacion(UtilidadesFecha.getFechaHoy());
            kardex.setPrecioPromedio((kardex.getPrecioPromedio()!=null)?kardex.getPrecioPromedio():BigDecimal.ZERO);
            kardex.setPrecioTotal(BigDecimal.ZERO);
            kardex.setPrecioUltimo(BigDecimal.ZERO);
            //kardex.setProducto(value.getProductoProveedor().getProducto());
            kardex.setStock(0);
            kardex.setReserva(0);
            em.persist(kardex); //grabando la persistencia
        } else {
            //Si existe el kardex solo creo
            kardex = kardexList.get(0);
        }

        detalle.setKardex(kardex);
        kardex.addDetalleKardex(detalle);
        em.persist(detalle);
        //em.persist(kardexDetalle); //grabando el kardex detalle

        //Esto va a depender del tipo de flujo del inventario es decir para saber si la suma o resta pero por defecto
        // el metodo es solo de ingreso asi que no considero el otro caso
        /*if(detalle.getCodigoTipoDocumentoEnum().getSignoInventario().equals(TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO))
        {
            kardex.setStock(kardex.getStock() + detalle.getCantidad());
            kardex.setPrecioTotal(kardex.getPrecioTotal().add(detalle.getPrecioTotal()));
        }
        else
        {
            kardex.setStock(kardex.getStock() - detalle.getCantidad());
            kardex.setPrecioTotal(kardex.getPrecioTotal().subtract(detalle.getPrecioTotal()));
        }
        kardex.setPrecioPromedio(kardex.getPrecioPromedio().add(detalle.getPrecioUnitario()).divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP));        
        kardex.setPrecioUltimo(detalle.getPrecioUnitario()); //Ver si grabar siempre el ultimo valor */
        recalcularValoresKardex(kardex, detalle); //Actualiza los valores desde un mismo lugar
        kardex = em.merge(kardex);

        //Actualizar la compra de referencia para saber que ya fue ingresada
        switch (detalle.getCodigoTipoDocumentoEnum()) {
            case COMPRA_INVENTARIO:
                //Actualizar referencia de la compra para que sepa que ya fue ingresado la mercaderia
                //TODO:Mejorar esta parte porque esta grabando muchas veces lo mismo cuando hay varios detalles apuntando a la misma compra
                CompraService compraService = new CompraService();
                Compra compra = compraService.buscarPorId(detalle.getReferenciaDocumentoId());
                compra.setInventarioIngreso(EnumSiNo.SI.getLetra());
                em.merge(compra);
                break;

        }
    }
    
    
    /**
     * TODO: Ver si no voy a usar este metodo borrar
     * Metodo para el ingreso de nuevos productos del kardex
     * @param detalles 
     * @deprecated 
     */
    public void ingresarInventario(Map<KardexDetalle,CompraDetalle> detalles,Bodega bodega) throws java.rmi.RemoteException,ServicioCodefacException
    {
        EntityTransaction transaction=em.getTransaction();
        try
        {            
            transaction.begin();
            Compra compra=null; //referencia para almacenar la compra ingresada a inventario

            for (Map.Entry<KardexDetalle, CompraDetalle> entry : detalles.entrySet()) {

                KardexDetalle kardexDetalle = entry.getKey();
                CompraDetalle value = entry.getValue();
                compra=value.getCompra();
                Producto producto=value.getProductoProveedor().getProducto();

                //Verificar si existe el karde o lo crea;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("bodega", bodega);
                map.put("producto",producto);
                List<Kardex> kardexList=obtenerPorMap(map);

                Kardex kardex=null;
                if(kardexList==null | kardexList.size()==0)
                {
                    //Creando el kardex cuando no existe en la base de datos
                    kardex=new Kardex();
                    crearObjeto(bodega, value.getProductoProveedor().getProducto());/*
                    kardex.setBodega(bodega);
                    kardex.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                    kardex.setFechaModificacion(UtilidadesFecha.getFechaHoy());
                    kardex.setPrecioPromedio(BigDecimal.ZERO);
                    kardex.setPrecioTotal(BigDecimal.ZERO);
                    kardex.setPrecioUltimo(BigDecimal.ZERO);
                    kardex.setProducto(value.getProductoProveedor().getProducto());
                    kardex.setStock(0);
                    kardex.setReserva(0);*/
                    em.persist(kardex); //grabando la persistencia
                }            
                else
                {
                    //Si existe el kardex solo creo
                    kardex=kardexList.get(0);
                }

                /**
                 * Grabar los detalles de los kardes y actualizar los valores en el kardex
                 */
                kardexDetalle.setKardex(kardex);
                kardex.addDetalleKardex(kardexDetalle);
                //em.persist(kardexDetalle); //grabando el kardex detalle

                //Esto va a depender del tipo de flujo del inventario es decir para saber si la suma o resta pero por defecto
                // el metodo es solo de ingreso asi que no considero el otro caso
                //kardex.setStock(kardex.getStock()+kardexDetalle.getCantidad());
                //kardex.setPrecioPromedio(kardex.getPrecioPromedio().add(kardexDetalle.getPrecioUnitario()).divide(new BigDecimal("2"),2,RoundingMode.HALF_UP));
                //kardex.setPrecioTotal(kardex.getPrecioTotal().add(kardexDetalle.getPrecioTotal()));
                //kardex.setPrecioUltimo(kardexDetalle.getPrecioUnitario());
                recalcularValoresKardex(kardex,kardexDetalle);
                kardex=em.merge(kardex);
            }
            
            if(compra!=null)
            {
                compra.setInventarioIngreso(EnumSiNo.SI.getLetra());
                em.merge(compra);
            }

            transaction.commit(); //si todo sale bien ejecuto en la base de datos
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            transaction.rollback();
            throw  new ServicioCodefacException("Error al grabar el inventario");            
        } 
    
    }
    
    public List<KardexDetalle> obtenerConsultaPorFecha(Date fechaInicial , Date fechaFinal,Producto producto,Bodega bodega,Integer cantidadMovimientos) throws java.rmi.RemoteException
    {
        List<KardexDetalle> datosConsulta=getFacade().obtenerConsultaPorFechaFacade(fechaInicial, fechaFinal, producto, bodega,cantidadMovimientos);
        //Invertir la lista porque los resultados estan invertidos
        //Collections.reverse(datosConsulta);
        
        Integer cantidadSaldo = 0;
        BigDecimal precioUnitarioSaldo = BigDecimal.ZERO;
        BigDecimal precioTotalSaldo = BigDecimal.ZERO;
        
        List<KardexDetalle> datosConsultaTemp=new ArrayList<KardexDetalle>();
        if(cantidadMovimientos!=null && (datosConsulta.size()-cantidadMovimientos)>0 )
        {
            int numeroCorte=datosConsulta.size()-cantidadMovimientos;
            List<KardexDetalle> valoresAnteriores=datosConsulta.subList(0,numeroCorte);
            List<KardexDetalle> datosConsultaTemp2=datosConsulta.subList(numeroCorte,datosConsulta.size());
            datosConsultaTemp=new ArrayList<KardexDetalle>(valoresAnteriores);
            
            //Calcular los saldos anteriores
            for (KardexDetalle valorAnterior : valoresAnteriores) {
                
                if(valorAnterior.getCodigoTipoDocumentoEnum().getSignoInventario().equals(TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO))
                {
                    cantidadSaldo+=valorAnterior.getCantidad();
                    precioTotalSaldo=precioTotalSaldo.add(valorAnterior.getPrecioTotal());
                }
                else
                {
                    cantidadSaldo-=valorAnterior.getCantidad();
                    precioTotalSaldo=precioTotalSaldo.subtract(valorAnterior.getPrecioTotal());
                }                
                
            }
            
        }
        
        //Si existe fecha de corta obtenego los saldos anteriores
        if(fechaInicial!=null)
        {
            List<Object[]> saldosAnteriores=getFacade().obtenerConsultaSaldoAnterior(fechaInicial, producto, bodega);
            
            for (Object[] saldosAnterior : saldosAnteriores) {
                String codigoTipoDocumento=(String) saldosAnterior[0];
                Integer cantidad=((Long) saldosAnterior[1]).intValue();
                BigDecimal precioUnitario=(BigDecimal) saldosAnterior[2];
                BigDecimal precioTotal=(BigDecimal) saldosAnterior[3];
                
                TipoDocumentoEnum tipoDocumento=TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(codigoTipoDocumento);
                
                if(tipoDocumento.getSignoInventario().equals(TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO))
                {
                    cantidadSaldo+=cantidad;
                    precioTotalSaldo=precioTotalSaldo.add(precioTotal);                    
                }
                else
                {
                    cantidadSaldo-=cantidad;
                    precioTotalSaldo=precioTotalSaldo.subtract(precioTotal);
                }
                
                precioUnitarioSaldo=precioTotalSaldo.divide(new BigDecimal(cantidadSaldo),2,BigDecimal.ROUND_HALF_UP); //Todo: Ver si esta varible se elimina porque el precio promedio puedo calcular del total       
            }                        
        }
        
        if(cantidadSaldo>0)
        {
            ///////////////======================> CONSTRUIR KARDEX DETALLE ADICIONAL DE LOS SALDOS <==============================//////////////////
            KardexDetalle kardexDetalle = new KardexDetalle();
            kardexDetalle.setCantidad(cantidadSaldo);
            kardexDetalle.setPrecioUnitario(precioUnitarioSaldo);
            kardexDetalle.setPrecioTotal(precioTotalSaldo);
            kardexDetalle.setFechaCreacion(fechaInicial);
            kardexDetalle.setFechaIngreso(fechaInicial);
            kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.SALDO_ANTERIOR.getCodigo());
            //kardexDetalle.set            
            datosConsulta.add(0, kardexDetalle);
        }
        
        for (int j = 0; j < datosConsultaTemp.size(); j++) 
        {
            KardexDetalle kardexDetalleTemp = datosConsultaTemp.get(j);
            if (datosConsulta.contains(kardexDetalleTemp)) 
            {
                datosConsulta.remove(kardexDetalleTemp);
            }
        }
        
        /*
        for(int i=0;i<datosConsulta.size();i++){
        //for (KardexDetalle kardexDetalle : datosConsulta) {
            KardexDetalle kardexDetalle=datosConsulta.get(i);
            
            for(int j=0;j<datosConsultaTemp.size();j++)
            {
                KardexDetalle kardexDetalleTemp=datosConsultaTemp.get(j);
                if(kardexDetalleTemp.getId().equals(kardexDetalle.getId()))
                {
                    datosConsulta.remove(kardexDetalle);
                }
            }
            //if(datosConsultaTemp.contains(kardexDetalle))
            //{
            //    datosConsulta.remove(kardexDetalle);
            //}
        }
        */
        
        return datosConsulta;
    }
    
    public List<Object[]> consultarStockMinimo(Bodega bodega,CategoriaProducto categoria) throws java.rmi.RemoteException
    {
        return getFacade().consultarStockMinimoFacade(bodega,categoria);
    }
    
    public List<Object[]> consultarStock(Bodega bodega,CategoriaProducto categoria) throws java.rmi.RemoteException
    {
        return getFacade().consultarStockFacade(bodega,categoria);
    }

    public List<Kardex> buscarPorProducto(Producto producto) throws java.rmi.RemoteException,ServicioCodefacException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        //KardexService kardexService = new KardexService();
        return getFacade().findByMap(mapParametros);
    }
    
    public List<Kardex> buscarPorProducto(Producto producto,GeneralEnumEstado estadoEnum) throws java.rmi.RemoteException,ServicioCodefacException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        mapParametros.put("estado", estadoEnum.getEstado());
        //KardexService kardexService = new KardexService();
        return getFacade().findByMap(mapParametros);
    }
    
    public List<Kardex> buscarPorProductoYBodega(Producto producto,Bodega bodega) throws java.rmi.RemoteException,ServicioCodefacException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        mapParametros.put("bodega",bodega);
        
        //KardexService kardexService = new KardexService();
        return getFacade().findByMap(mapParametros);
    }
    
    
    public Kardex crearObjeto(Bodega bodega,Producto producto)
    {
        Kardex kardex=new Kardex();
        kardex.setBodega(bodega);
        kardex.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        kardex.setFechaModificacion(UtilidadesFecha.getFechaHoy());
        kardex.setPrecioPromedio(BigDecimal.ZERO);
        kardex.setPrecioTotal(BigDecimal.ZERO);
        kardex.setPrecioUltimo(BigDecimal.ZERO);
        kardex.setProducto(producto);
        kardex.setReserva(0);
        kardex.setStock(0);
        return kardex;
        
    }
    
    public boolean obtenerSiNoExisteStockProducto(Bodega bodega, Producto producto, int cantidad)
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("bodega",bodega);
        mapParametros.put("producto",producto);   
        List<Kardex> listaKardex=getFacade().findByMap(mapParametros);
        
        if(listaKardex!=null && listaKardex.size()>0)
        {
            Kardex kardex = listaKardex.get(0);
            if(kardex.getStock()>= cantidad){
                return true;
            }else{
                return false;
            }
        }
        return false;    
    }

    @Override
    public List<Kardex> buscarPorBodega(Bodega bodega) throws RemoteException, ServicioCodefacException {
        return (List<Kardex>)ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                //Kardex k;
                //        k.get
                Map<String,Object> mapParametros=new HashMap<String,Object>();
                mapParametros.put("bodega",bodega);
                return getFacade().findByMap(mapParametros);
            }
        });
    }
    
    public Kardex construirKardexVacioSinPersistencia() throws java.rmi.RemoteException,ServicioCodefacException
    {
        Kardex kardexNuevo=new Kardex();
        kardexNuevo.setStock(0);
        kardexNuevo.setPrecioTotal(BigDecimal.ZERO);
        kardexNuevo.setPrecioPromedio(BigDecimal.ZERO);
        kardexNuevo.setPrecioUltimo(BigDecimal.ZERO);
        //kardexNuevo.se
        return kardexNuevo;
    }
    
}

