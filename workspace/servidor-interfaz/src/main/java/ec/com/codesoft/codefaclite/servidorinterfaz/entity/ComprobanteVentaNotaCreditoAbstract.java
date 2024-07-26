/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ReferenciaDetalleFacturaRespuesta;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

/**
 *
 * @author CARLOS_CODESOFT
 */
@MappedSuperclass
public abstract class ComprobanteVentaNotaCreditoAbstract<T extends ComprobanteAdicional> extends ComprobanteEntity<T> {

    public abstract List<DetalleFacturaNotaCeditoAbstract> getDetallesComprobante();
    /**
     * Valor del iva cobrado
     */
    @Column(name = "IVA")
    protected BigDecimal iva;

    @Column(name = "TOTAL")
    protected BigDecimal total;
    
    @Column(name = "AHORRO")
    protected BigDecimal ahorro;
    
    /**
     * Valor del descuento de los productos que no cobran iva
     */    
    @Column(name = "DESCUENTO_IVA_CERO")
    protected BigDecimal descuentoSinImpuestos;
    
    @Column(name = "DESCUENTO_IVA_ADICIONAL")
    protected BigDecimal descuentoImpuestosAdicional;
    
    /**
     * Valor del descuento de los productos que cobran iva
     */
    @Column(name = "DESCUENTO_IVA")
    protected BigDecimal descuentoImpuestos;
    
    @Column(name = "SUBTOTAL_IVA_CERO")
    protected BigDecimal subtotalSinImpuestos;
    
    @Column(name = "SUBTOTAL_IVA")
    private BigDecimal subtotalImpuestos;
    /**
     * Valor del iva cobrado
     */
    @Column(name = "VALOR_ICE")
    private BigDecimal ice;
    
    @Column(name = "IRBPNR")
    private BigDecimal irbpnr;
    
    @Column(name = "TIPO_IDENTIFICACION_CODIGO_SRI")
    private String tipoIdentificacionCodigoSri;
    
    @JoinColumn(name = "CLIENTE_ID")
    @ManyToOne
    private Persona cliente;
    
    @Column(name = "FECHA_ULTIMA_EDICION")
    protected Timestamp fechaUltimaEdicion;
    
    @JoinColumn(name = "USUARIO_ULTIMA_EDICION_ID")
    protected Usuario usuarioUltimaEdicion;
    
    /**
     * Se refiere a la sucucursal del cliente
     */
    @JoinColumn(name = "SUCURSAL_ID")
    @ManyToOne    
    private PersonaEstablecimiento sucursal;


    public ComprobanteVentaNotaCreditoAbstract() {
    }
    
    

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDescuentoSinImpuestos() {
        return descuentoSinImpuestos;
    }

    public void setDescuentoSinImpuestos(BigDecimal descuentoSinImpuestos) {
        this.descuentoSinImpuestos = descuentoSinImpuestos;
    }

    public BigDecimal getDescuentoImpuestos() {
        return descuentoImpuestos;
    }

    public void setDescuentoImpuestos(BigDecimal descuentoImpuestos) {
        this.descuentoImpuestos = descuentoImpuestos;
    }

    public BigDecimal getSubtotalSinImpuestos() {
        return subtotalSinImpuestos;
    }

    public void setSubtotalSinImpuestos(BigDecimal subtotalSinImpuestos) {
        this.subtotalSinImpuestos = subtotalSinImpuestos;
    }

    public BigDecimal getSubtotalImpuestos() {
        return subtotalImpuestos;
    }

    public void setSubtotalImpuestos(BigDecimal subtotalImpuestos) {
        this.subtotalImpuestos = subtotalImpuestos;
    }

    public BigDecimal getIce() {
        return ice;
    }

    public void setIce(BigDecimal ice) {
        this.ice = ice;
    }

    public Persona getCliente() {
        return cliente;
    }
    
    public Persona getClienteOriginal() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    public PersonaEstablecimiento getSucursal() {
        return sucursal;
    }

    public void setSucursal(PersonaEstablecimiento sucursal) {
        this.sucursal = sucursal;
    }

    public BigDecimal getAhorro() {
        return ahorro;
    }

    public void setAhorro(BigDecimal ahorro) {
        this.ahorro = ahorro;
    }

    public String getTipoIdentificacionCodigoSri() {
        return tipoIdentificacionCodigoSri;
    }

    public void setTipoIdentificacionCodigoSri(String tipoIdentificacionCodigoSri) {
        this.tipoIdentificacionCodigoSri = tipoIdentificacionCodigoSri;
    }

    public BigDecimal getIrbpnr() {
        //TODO: Por el momento dejo este codigo para que sea retrocompatible con versiones anteriores que no tenian este dato
        if(irbpnr==null)
        {
            this.irbpnr=BigDecimal.ZERO;
        }
        
        return irbpnr;
    }

    public void setIrbpnr(BigDecimal irbpnr) {
        this.irbpnr = irbpnr;
    }

    public Timestamp getFechaUltimaEdicion() {
        return fechaUltimaEdicion;
    }

    public void setFechaUltimaEdicion(Timestamp fechaUltimaEdicion) {
        this.fechaUltimaEdicion = fechaUltimaEdicion;
    }

    public Usuario getUsuarioUltimaEdicion() {
        return usuarioUltimaEdicion;
    }

    public void setUsuarioUltimaEdicion(Usuario usuarioUltimaEdicion) {
        this.usuarioUltimaEdicion = usuarioUltimaEdicion;
    }

    public BigDecimal getDescuentoImpuestosAdicional() {
        return descuentoImpuestosAdicional;
    }

    public void setDescuentoImpuestosAdicional(BigDecimal descuentoImpuestosAdicional) {
        this.descuentoImpuestosAdicional = descuentoImpuestosAdicional;
    }
    
    
    
    
    

    /**
     * ==========================================================================
     * METODOS PERSONALIZADOS
     * ==========================================================================
     */
    
    public BigDecimal getSubtotalImpuestosMenosDescuento() {
        return subtotalImpuestos.subtract(descuentoImpuestos);
    }
    
    public BigDecimal getSubtotalSinImpuestosMenosDescuento() {
        return subtotalSinImpuestos.subtract(descuentoSinImpuestos);
    }
    
    public BigDecimal calcularDescuentosTotales()
    {
        return descuentoImpuestos.add(descuentoSinImpuestos);
    }
    
    public void aplicarDescuento(BigDecimal descuentoLeido,Boolean porcentajeDescuentoGlobal,EnumSiNo ivaDescuentoEnumSiNo)
    {
        //String descuentoStr = getTxtDescuentoGlobal().getText();

        //BigDecimal descuentoLeido = new BigDecimal(descuentoStr);

        //Esta variable va a almacenar siempre el descuento antes de impuestos
        //y cuando el usuario quiera poner un descuento incluido iva primero hago la conversion interna            
        List<DetalleFacturaNotaCeditoAbstract> detalleList= getDetallesComprobante();
        for (DetalleFacturaNotaCeditoAbstract detalle : detalleList) {

            BigDecimal descuentoValor = BigDecimal.ZERO;

            if (porcentajeDescuentoGlobal) {

                descuentoValor = descuentoLeido.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP).multiply(detalle.getSubtotalSinDescuentos());

            } else {
                BigDecimal descuentoValorGlobal = BigDecimal.ZERO;
                if (ivaDescuentoEnumSiNo.equals(EnumSiNo.NO)) {
                    //Cuando ingresa el valor que no incluye el iva, lo agrego directamente
                    descuentoValorGlobal = descuentoLeido;
                } else if (ivaDescuentoEnumSiNo.equals(EnumSiNo.SI)) {
                    ParametrosSistemaCodefac.obtenerIvaDefecto();
                    //BigDecimal ivaDefecto = new BigDecimal(session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).getValor());
                    BigDecimal ivaDefecto =ParametrosSistemaCodefac.obtenerIvaDefecto();
                    descuentoValorGlobal = UtilidadesImpuestos.quitarValorIva(ivaDefecto, descuentoLeido, 6);
                }

                //Calcular el descuento individual por cada producto
                //BigDecimal subtotalFactura=factura.getSubtotalImpuestos().add(factura.getDescuentoSinImpuestos());
                BigDecimal subtotalFactura =getSubtotalImpuestosMenosDescuento().add(getSubtotalSinImpuestosMenosDescuento());
                BigDecimal porcentajeDecimalDescuentoGeneral = descuentoValorGlobal.divide(subtotalFactura, 6, BigDecimal.ROUND_HALF_UP);

                descuentoValor = porcentajeDecimalDescuentoGeneral.multiply(detalle.getSubtotalSinDescuentos());

            }

            //Solo grabar con 2 decimales por que el Sri no permite más en los descuentos
            detalle.setDescuento(descuentoValor.setScale(2, BigDecimal.ROUND_HALF_UP));
        }

        for (DetalleFacturaNotaCeditoAbstract detalle : detalleList) {
            //Solo grabar con 2 decimales por que el Sri no permite más en los descuentos
            detalle.calcularTotalesDetallesFactura();
        }

        //cargarDatosDetalles();
        //controlador.cargarTotales();
    }
    
    //TODO: Mejorar esta parte
    public ResultadoTotales calcularTotalesConPrecioUnitario(List<DetalleFacturaNotaCeditoAbstract> detalles,Boolean precioNormal)
    {
        ResultadoTotales resultado=new ResultadoTotales();
        
        for (DetalleFacturaNotaCeditoAbstract detalle : detalles) 
        {
            if(detalle.getDescripcion().equals("ASPIRINA 100MG TAB. "))
            {
                System.out.println("probar...");
            }
            System.out.println(detalle.getDescripcion());
            //Sumar el valor del Ice
            resultado.ice = resultado.ice.add(detalle.getValorIce());
            resultado.irbpnr=resultado.irbpnr.add(detalle.getIrbpnr());
            
            BigDecimal precio=(precioNormal?detalle.getPrecioUnitario():detalle.getPrecioSinAhorro());
            //Sumar los subtotales
            //TODO: Ver si estos calculos los puede hacer internamente en la clase FacturaDetalle
            if (detalle.getIvaPorcentaje().equals(0)) {
                
                resultado.subTotalSinImpuestos = resultado.subTotalSinImpuestos.add(precio.multiply(detalle.getCantidad()));
                resultado.descuentoSinImpuestos = resultado.descuentoSinImpuestos.add((detalle.getDescuento() != null) ? detalle.getDescuento() : BigDecimal.ZERO);
            } else {
                
                //Obtener el subtotal y el iva a partir del total del detalle
                
                
                BigDecimal descuentoTmp=(detalle.getDescuento() != null) ? detalle.getDescuento() : BigDecimal.ZERO;
                resultado.descuentoConImpuestos = resultado.descuentoConImpuestos.add(descuentoTmp);
                
                //Artificio para no perder la referencia
                resultado.ivaDecimal = new BigDecimal(detalle.getIvaPorcentaje().toString()).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
                
                //Esto hago porque datos anteriores no pueden tener esa información
                //TODO: Revisar
                if(detalle.getTotalFinal()!=null)
                {
                    System.out.println("TotalFinal: " + detalle.getTotalFinal());
                    System.out.println("Total: " + detalle.getTotal());
                    System.out.println("Iva: " + detalle.getIva());                    
                    
                    //System.out.println("Total: " + detalle.());
                    
                    BigDecimal ivaTmp=detalle.getTotalFinal().subtract(detalle.getTotal());
                    resultado.impuestoIva=resultado.impuestoIva.add(ivaTmp);
                    
                    //resultado.subTotalConImpuestos = resultado.subTotalConImpuestos.add(precio.multiply(detalle.getCantidad()));
                    //resultado.subTotalConImpuestos = resultado.subTotalConImpuestos.add(detalle.getTotal());
                    //resultado.subTotalConImpuestos = resultado.subTotalConImpuestos.add(detalle.getSubtotalSinDescuentos());
                    resultado.subTotalConImpuestos = resultado.subTotalConImpuestos.add(detalle.getTotalFinal().subtract(ivaTmp)).add(descuentoTmp);
                }
                else
                {
                    resultado.impuestoIva = resultado.impuestoIva.add(detalle.getIva());
                    //TODO: Queda esta solucion de forma temporal
                    resultado.subTotalConImpuestos = resultado.subTotalConImpuestos.add(precio.multiply(detalle.getCantidad()));
                }   
                //resultado.impuestoIva = resultado.impuestoIva.add(detalle.getIva());
                //resultado.impuestoIva = resultado.subTotalConImpuestos.add(resultado.ice).subtract(resultado.descuentoConImpuestos).multiply(resultado.ivaDecimal);
                //resultado.impuestoIva = resultado.subTotalConImpuestos.add(resultado.ice).subtract(resultado.descuentoConImpuestos).multiply(resultado.ivaDecimal);
            }

        }

    
        //Calcula el total de los totales
        resultado.total = resultado.subTotalSinImpuestos.subtract(resultado.descuentoSinImpuestos)
                .add(resultado.subTotalConImpuestos.subtract(resultado.descuentoConImpuestos))
                .add(resultado.impuestoIva)
                .add(resultado.ice)
                .add(resultado.irbpnr);
        
        //TODO: por el momento hago este artificio pero esta de revisar
        //@Deprecated: hago esto porque si necesito tener el valor exacto pero del iva no necesito sabe el valor 
        resultado.total=resultado.total.setScale(2, RoundingMode.HALF_UP);
        resultado.impuestoIva=resultado.impuestoIva.setScale(2, RoundingMode.HALF_UP);
    
        return resultado;
    }
    
    public void calcularTotalesDesdeDetalles() {
        List<DetalleFacturaNotaCeditoAbstract> detalles=(List<DetalleFacturaNotaCeditoAbstract>) getDetallesComprobante();
        //Solo calcular si la variables de detalles fue creada
        if (detalles == null || detalles.size() == 0) {
            this.total = BigDecimal.ZERO;
            this.ahorro=BigDecimal.ZERO;
            this.descuentoSinImpuestos = BigDecimal.ZERO;
            this.descuentoImpuestos = BigDecimal.ZERO;
            this.subtotalSinImpuestos = BigDecimal.ZERO;
            this.subtotalImpuestos = BigDecimal.ZERO;
            this.iva = BigDecimal.ZERO;
            this.ice = BigDecimal.ZERO;
            this.irbpnr=BigDecimal.ZERO;
            
            return;
        }

        //Calcular el total de la compra
        ResultadoTotales resultado =calcularTotalesConPrecioUnitario(detalles,true);
        
        BigDecimal total = resultado.total; //total de la factura        
        //BigDecimal ahorro=B;
        BigDecimal subTotalSinImpuestos = resultado.subTotalSinImpuestos;//Sin el descuento
        BigDecimal subTotalConImpuestos = resultado.subTotalConImpuestos;//Sin los descuentos        
        BigDecimal descuentoSinImpuestos = resultado.descuentoSinImpuestos; //
        BigDecimal descuentoConImpuestos = resultado.descuentoConImpuestos; //        
        BigDecimal impuestoIva = resultado.impuestoIva; //        
        BigDecimal ivaDecimal = resultado.ivaDecimal; //Todo: Variable donde se almacena el iva de uno de los detalles (pero si tuviera varias ivas distintos de 0 , se generaria poroblemas)
        BigDecimal ice = resultado.ice;
        BigDecimal irbpnr=resultado.irbpnr;
        
        //TODO: Me parece que ese codigo se debe setear directo
        this.ice = ice;
        this.irbpnr=irbpnr;
        
        //Antes de hacer calculos verifico si el sistema tiene grabados esos datos para no hacer calculos inecesarios
        if(detalles.get(0).getPrecioSinAhorro()!=null)
        {
            //Calcular el total de la compra pero sin ahorro cuando sea el caso para calcular
            ResultadoTotales resultadoTotales =calcularTotalesConPrecioUnitario(detalles,false);
            this.ahorro=resultadoTotales.total.subtract(total).setScale(2, RoundingMode.HALF_UP);
        }

        /*for (DetalleFacturaNotaCeditoAbstract detalle : detalles) {

            //Sumar el valor del Ice
            ice = ice.add(detalle.getValorIce());
            //Sumar los subtotales
            //TODO: Ver si estos calculos los puede hacer internamente en la clase FacturaDetalle
            if (detalle.getIvaPorcentaje().equals(0)) 
            {
                subTotalSinImpuestos = subTotalSinImpuestos.add(detalle.getPrecioUnitario().multiply(detalle.getCantidad()));
                descuentoSinImpuestos = descuentoSinImpuestos.add((detalle.getDescuento()!=null)?detalle.getDescuento():BigDecimal.ZERO);
            } 
            else 
            {
                subTotalConImpuestos = subTotalConImpuestos.add(detalle.getPrecioUnitario().multiply(detalle.getCantidad()));
                descuentoConImpuestos = descuentoConImpuestos.add((detalle.getDescuento()!=null)?detalle.getDescuento():BigDecimal.ZERO);

                ivaDecimal = new BigDecimal(detalle.getIvaPorcentaje().toString()).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
                impuestoIva = subTotalConImpuestos.add(ice).subtract(descuentoConImpuestos).multiply(ivaDecimal);
            }

        }

        this.ice = ice;

        //Calcula el total de los totales
        total = subTotalSinImpuestos.subtract(descuentoSinImpuestos)
                .add(subTotalConImpuestos.subtract(descuentoConImpuestos))
                .add(impuestoIva)
                .add(ice);*/
        

        /**
         * ============================================================================================================
         * 1.- primero redondear el TOTAL con 2 decimales para hacer el calculo
         * inverso y que los demas valores cuadren
         * ============================================================================================================
         */
        this.total = total.setScale(2, BigDecimal.ROUND_HALF_UP); //valor final con 2 decimales

        /**
         * ============================================================================================================
         * 2.- redondear el DESCUENTO Y SUBTOTAL de los totales que no voy a
         * calcular impuestos
         * ============================================================================================================
         */
        this.descuentoSinImpuestos = descuentoSinImpuestos.setScale(2, BigDecimal.ROUND_HALF_UP); //Este valor no se mueve porque debe ser fijo de 2 decimales segun el sri
        this.subtotalSinImpuestos = subTotalSinImpuestos.setScale(2, BigDecimal.ROUND_HALF_UP);// Este valor se redondea y tampoco ya no se mueve porque no interfiere con el iva donde se descuadra //TODO: PERO REVISAR ESTA AFIRMACION

        /**
         * ==================================================================================
         * 3.- obtener el SUBTOTAL CON IMPUESTOS el cual contiene el iva y el
         * ice Nota: No debo redondear porque los calculos anteriores ya estan
         * redondeados a 2 decimales
         * ==================================================================================
         */
        //TODO: Analizar si tiene que ver que se agregue el tema de los impuestos con el IRBPNR
        BigDecimal totalConImpuestos = this.total.subtract(this.subtotalSinImpuestos).add(this.descuentoSinImpuestos).add(this.irbpnr);
        //BigDecimal totalConImpuestos = this.total.subtract(this.subtotalSinImpuestos).add(this.descuentoSinImpuestos);

        /**
         * ==================================================================================
         * 4.- Redondear el valor del Ice directo TODO: Revisar si este paso no
         * genera problemas
         * ==================================================================================
         */
        this.ice = this.ice.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.irbpnr=this.irbpnr.setScale(2,BigDecimal.ROUND_HALF_UP);

        /**
         * ==================================================================================
         * 4.- Calcular el valor antes del Iva es decir el subtotal con
         * descuento + ice
         * ==================================================================================
         */
        BigDecimal ivaDecimalTmp = ivaDecimal.add(BigDecimal.ONE); //1.12 por ejemplo

        //TODO: Por el momento obtengo el valor directo de los datos calculados desde el detalle
        //BigDecimal subtotalMenosImpuestosConIce = totalConImpuestos.divide(ivaDecimalTmp, 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal subtotalMenosImpuestosConIce = totalConImpuestos.subtract(resultado.impuestoIva);

        /**
         * ==================================================================================
         * 6.- Redondear el valor del descuento con impuestos porque este valor
         * no influye con otro valores previos
         * ==================================================================================
         */
        this.descuentoImpuestos = descuentoConImpuestos.setScale(2, BigDecimal.ROUND_HALF_UP);

        /**
         * ==================================================================================
         * 7.- Calcular el valor del SUBTOTAL IMPUESTOS SIN TOMAR EN CUENTA
         * DESCUENTOS
         * ==================================================================================
         */
        //this.subtotalImpuestos = subtotalMenosImpuestosConIce.subtract(this.ice).add(this.descuentoImpuestos);
        this.subtotalImpuestos = subtotalMenosImpuestosConIce.subtract(this.ice).add(this.descuentoImpuestos);

        /**
         * =========================================================================================
         * 8.- Calcular el valor del IVA que simplemente es restar del SUBTOTAL
         * TOTAL MENOS SUBTOTAL CON IMPUESTOS EL ICE
         * ==========================================================================================
         */
        //Calcular el iva de la resta del del total -subtotal
        this.iva = totalConImpuestos.subtract(subtotalMenosImpuestosConIce);
        /*this.iva = BigDecimal.ZERO;
        for (DetalleFacturaNotaCeditoAbstract detalle : detalles) {
            this.iva=this.iva.add(detalle.getIva());
        }*/
        //this.iva=this.iva.setScale(2,BigDecimal.ROUND_HALF_UP);        

    }
    
    public Map<ImpuestoDetalle,List<DetalleFacturaNotaCeditoAbstract>> obtenerIceMap()
    {
        Map<ImpuestoDetalle,List<DetalleFacturaNotaCeditoAbstract>> mapResultado=new HashMap<ImpuestoDetalle,List<DetalleFacturaNotaCeditoAbstract>>();
        try {
            for (DetalleFacturaNotaCeditoAbstract detalle : this.getDetallesComprobante()) {
                CatalogoProducto catalogoProducto = detalle.getCatalogoProducto();
                
                //TODO: Solucion Temporal cuando no tengo grabado ese dato para hacer retrocompatible el nuevo codigo
                if (detalle.getCatalogoProducto() == null) 
                {
                    ReferenciaDetalleFacturaRespuesta detalleData=ServiceFactory.getFactory().getFacturacionServiceIf().obtenerReferenciaDetalleFactura(detalle.getTipoDocumentoEnum(), detalle.getReferenciaId());
                    catalogoProducto=detalleData.catalogoProducto;
                }
                
                
                //Obtener una lista de map con el TIPO DE IMPUESTO
                if(catalogoProducto.getIce()!=null)
                {                    
                    if(catalogoProducto.getIce().getImpuesto().getDetalleImpuestos().size()>0)
                    {
                        ImpuestoDetalle impuestoDetalle=catalogoProducto.getIce().getImpuesto().getDetalleImpuestos().get(0);                                
                        
                        List<DetalleFacturaNotaCeditoAbstract> listTmp=mapResultado.get(impuestoDetalle);                        
                        if(listTmp==null)
                        {
                            listTmp=new ArrayList<DetalleFacturaNotaCeditoAbstract>();
                        }
                        
                        listTmp.add(detalle);
                        mapResultado.put(impuestoDetalle, listTmp);
                    }
                }
                
            }
            //return mapResultado;
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteVentaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobanteVentaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapResultado;
    }
    
    
    public class ResultadoTotales
    {
        BigDecimal total = BigDecimal.ZERO; //total de la factura        
        BigDecimal subTotalSinImpuestos = BigDecimal.ZERO;//Sin el descuento
        BigDecimal subTotalConImpuestos = BigDecimal.ZERO;//Sin los descuentos        
        BigDecimal descuentoSinImpuestos = BigDecimal.ZERO; //
        BigDecimal descuentoConImpuestos = BigDecimal.ZERO; //        
        BigDecimal impuestoIva = BigDecimal.ZERO; //        
        BigDecimal ivaDecimal = BigDecimal.ZERO; //Todo: Variable donde se almacena el iva de uno de los detalles (pero si tuviera varias ivas distintos de 0 , se generaria poroblemas)
        BigDecimal ice = BigDecimal.ZERO;
        BigDecimal irbpnr=BigDecimal.ZERO;

        public ResultadoTotales() 
        {
            
        }
        
    }

}
