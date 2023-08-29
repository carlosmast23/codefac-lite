/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadBigDecimal;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 *
 * @author CARLOS_CODESOFT
 */
@MappedSuperclass
public class DetalleFacturaNotaCeditoAbstract implements Serializable {

    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    @Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;
    
    /**
     * Esta variable me va permitir utilizar para grabar los precios sin ahorro cuando luego tenga que hacer calculos para ver cuanto fue mi ahorro
     */
    @Column(name = "PRECIO_SIN_AHORRO")
    private BigDecimal precioSinAhorro;
    
    @Column(name = "DESCUENTO")
    private BigDecimal descuento;
    @Column(name = "VALOR_ICE")
    private BigDecimal valorIce;
    
    @Column(name = "DESCRIPCION", length = 150)
    private String descripcion;

    @Column(name = "TOTAL")
    private BigDecimal total;
    @Column(name = "IVA")
    private BigDecimal iva;

    @Column(name = "IVA_PORCENTAJE")
    private Integer ivaPorcentaje;



    @Column(name = "REFERENCIA_ID")
    private Long referenciaId;

    @Column(name = "TIPO_REFERENCIA")
    private String tipoDocumento;
    
    @Column(name = "CODIGO_PRINCIPAL")
    private String codigoPrincipal;
    
    @Column(name = "PRESENTACION_CODIGO")
    private String presentacionCodigo;
    /**
     * El total del detalle corresonde a la siguiente formular
     * Total=cantidad*valorUnitario-descuento
     */

    @Column(name = "ICE_PORCENTAJE")
    private BigDecimal icePorcentaje;
    
    @Column(name ="IRBPNR")
    private BigDecimal irbpnr;
    
    @JoinColumn(name = "LOTE_ID")
    private Lote lote;
    
    @Column(name = "COSTO_PROMEDIO")
    private BigDecimal costoPromedio;
    
    @Column(name = "CANTIDAD_PRESENTACION")
    private BigDecimal cantidadPresentacion;
    
    @JoinColumn(name = "CATALOGO_PRODUCTO_ID")
    private CatalogoProducto catalogoProducto;
    
    @JoinColumn(name = "KARDEX_ITEM_ESPECIFICO_ID")
    private KardexItemEspecifico kardexItemEspecifico;
    
    @Column(name = "KARDEX_ID")
    private Long kardexId;

    public DetalleFacturaNotaCeditoAbstract() {
        this.valorIce = BigDecimal.ZERO;
        this.icePorcentaje = BigDecimal.ZERO;
        this.irbpnr=BigDecimal.ZERO;
    }

    /**
     * ======================================================== METODOS PERSONALIZADOS ========================================================
     */
    public void calcularValorIce(BigDecimal porcentajeIce) {
        if (porcentajeIce.compareTo(BigDecimal.ZERO) > 0) {
            this.icePorcentaje = porcentajeIce;
            this.valorIce = UtilidadBigDecimal.calcularValorPorcentaje(porcentajeIce, getSubtotalSinDescuentos(), 5).setScale(5, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        
        if(cantidad==null)
        {
            cantidad=BigDecimal.ZERO;
        }
        
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        
        if(precioUnitario==null)
        {
            precioUnitario=BigDecimal.ZERO;
        }
        
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        if(descuento==null)
        {
            descuento=BigDecimal.ZERO;
        }
        
        this.descuento = descuento;
    }

    public BigDecimal getValorIce() {
        return valorIce;
    }

    public void setValorIce(BigDecimal valorIce) {
        this.valorIce = valorIce;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public Integer getIvaPorcentaje() {
        return ivaPorcentaje;
    }

    public void setIvaPorcentaje(Integer ivaPorcentaje) {
        this.ivaPorcentaje = ivaPorcentaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getIcePorcentaje() {
        return icePorcentaje;
    }

    public void setIcePorcentaje(BigDecimal icePorcentaje) {
        this.icePorcentaje = icePorcentaje;
    }

    public Long getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Long referenciaId) {
        this.referenciaId = referenciaId;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

        
    public TipoDocumentoEnum getTipoDocumentoEnum() {
        return TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(this.tipoDocumento);
    }

    public void setTipoDocumentoEnum(TipoDocumentoEnum tipoDocumentoEnum) {
        this.tipoDocumento = tipoDocumentoEnum.getCodigo();
    }

    /*public Long getLoteId() {
        return loteId;
    }

    public void setLoteId(Long loteId) {
        this.loteId = loteId;
    }*/

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }
    
    

    public Long getKardexId() {
        return kardexId;
    }

    public void setKardexId(Long kardexId) {
        this.kardexId = kardexId;
    }
    
    

    public CatalogoProducto getCatalogoProducto() {
        return catalogoProducto;
    }

    public BigDecimal getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(BigDecimal costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    public void setCatalogoProducto(CatalogoProducto catalogoProducto) {
        this.catalogoProducto = catalogoProducto;
    }

    public BigDecimal getCantidadPresentacion() {
        return cantidadPresentacion;
    }

    public void setCantidadPresentacion(BigDecimal cantidadPresentacion) {
        this.cantidadPresentacion = cantidadPresentacion;
    }

    public KardexItemEspecifico getKardexItemEspecifico() {
        return kardexItemEspecifico;
    }

    public void setKardexItemEspecifico(KardexItemEspecifico kardexItemEspecifico) {
        this.kardexItemEspecifico = kardexItemEspecifico;
    }

    public BigDecimal getIrbpnr() {
        //TODO: Codigo para hacer compatible con versiones anteriores que no tenian el irbpnr
        if(irbpnr==null)
        {
            this.irbpnr=BigDecimal.ZERO;
        }        
        return irbpnr;
    }

    public void setIrbpnr(BigDecimal irbpnr) {
        this.irbpnr = irbpnr;
    }
    
    
    
    
    public Kardex getKardex()
    {
        if(kardexId!=null)
        {
            try {
                return ServiceFactory.getFactory().getKardexServiceIf().buscarPorId(kardexId);
            } catch (RemoteException ex) {
                Logger.getLogger(DetalleFacturaNotaCeditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return null;
    }
    /**
     * PrecioUnitario*cantidad
     *
     * @return
     */
    public BigDecimal getSubtotalSinDescuentos() {
        return precioUnitario.multiply(cantidad);
    }

    /**
     * Metodos que devuelve el subtotal restado impuestos
     *
     * @return
     */
    public BigDecimal getSubtotalRestadoDescuentos() {
        return precioUnitario.multiply(cantidad).subtract(descuento);
    }
    
    /**
     * Metodo que me permite calcular el iva
     * @param porcentajeDescuento
     * @param incluidoIvaSiNo
     * @param ivaDefecto 
     */
    public void calcularDescuentoConPorcentaje(BigDecimal porcentajeDescuento,EnumSiNo incluidoIvaSiNo,BigDecimal ivaDefecto)
    {
        porcentajeDescuento = porcentajeDescuento.divide(new BigDecimal(100));
        BigDecimal precioUnitario=getPrecioUnitario();
        BigDecimal total = getCantidad().multiply(precioUnitario.setScale(5, BigDecimal.ROUND_HALF_UP)); //Escala a 2 decimales el valor del valor unitario porque algunos proveedores tienen 3 decimales
        
        //descuento = total.multiply(porcentajeDescuento).setScale(2,BigDecimal.ROUND_HALF_UP); //Si esta seleccionada la opcion asumo que el descuento se esta aplicando incluido iva
        if (incluidoIvaSiNo.equals(EnumSiNo.SI)) {
            
            //Para el caso que incluya iva le hago que calcule el porcentaje de descuento del total inlcuido iva
            total=UtilidadesImpuestos.agregarValorIva(ivaDefecto, total);
            
            descuento = total.multiply(porcentajeDescuento).setScale(2,BigDecimal.ROUND_HALF_UP); //Si esta seleccionada la opcion asumo que el descuento se esta aplicando incluido iva
            
            descuento = UtilidadesImpuestos.quitarValorIva(ivaDefecto, descuento, 2);
        }
        else
        {
            descuento = total.multiply(porcentajeDescuento).setScale(2,BigDecimal.ROUND_HALF_UP); //Si esta seleccionada la opcion asumo que el descuento se esta aplicando incluido iva
        }

        //facturaDetalle.setDescuento(descuento.setScale(2, BigDecimal.ROUND_HALF_UP));
        //facturaDetalle.setDescuento(descuento);
    }
    

    public void calculaIva() {
        //Verifico que el valor del ice no cea null para no tener errores
        //TODO: Mejorar esta parte de setear el valor del Ice
        /*if (valorIce == null) {
            valorIce = BigDecimal.ZERO;
        }

        BigDecimal valorIvaDecimal = new BigDecimal(ivaPorcentaje.toString()).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
        iva = getTotal().add(valorIce).multiply(valorIvaDecimal).setScale(2, BigDecimal.ROUND_HALF_UP); //Para calcular el iva tomo en cuenta el vlaor del Ice*/
        iva=recalcularIva().setScale(2, BigDecimal.ROUND_HALF_UP); //Para calcular el iva tomo en cuenta el vlaor del Ice;
    }
    
    public BigDecimal recalcularIva()
    {
        //Verifico que el valor del ice no cea null para no tener errores
        //TODO: Mejorar esta parte de setear el valor del Ice
        if (valorIce == null) {
            valorIce = BigDecimal.ZERO;
        }

        BigDecimal valorIvaDecimal = new BigDecimal(ivaPorcentaje.toString()).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal iva = getCalcularTotalDetalleConTodosDecimales().add(valorIce).multiply(valorIvaDecimal);
        return iva;
    }
    
    public void calcularTotalesDetallesFactura()
    {
        //Calular el total despues del descuento porque necesito esa valor para grabar
        
        calcularTotalDetalle();
        /**
         * Revisar este calculo del iva para no calcular 2 veces al mostrar
         */
        setIvaPorcentaje(getIvaPorcentaje());
        if(getIcePorcentaje()!=null)
        {
            calcularValorIce(getIcePorcentaje());
        }
        
             
        calculaIva();
    }
    public BigDecimal obtenerPrecioUnitarioConIva()
    {
        return getPrecioUnitario().add(getIva());
    }
    
    public void calcularValorIce() {
        calcularValorIce(icePorcentaje);
    }

    public void calcularTotalDetalle() {        
        total = getCalcularTotalDetalle();
    }
    
    public BigDecimal getCalcularTotalDetalleConTodosDecimales() {
        BigDecimal setTotal = getCantidad().multiply(getPrecioUnitario()).subtract(getDescuento());
        return setTotal;
        //return setTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    public BigDecimal getCalcularTotalDetalle() {
        BigDecimal setTotal = getCantidad().multiply(getPrecioUnitario()).subtract(getDescuento());
        return setTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal totalSinImpuestosConIce() {
        return total.add(valorIce);
    }

    /**
     * Valor final del producto incluido impuestos
     * @return 
     */
    public BigDecimal calcularTotalFinal() {
        BigDecimal totalFinal=this.total.add(iva).add(this.valorIce);        
        return totalFinal;
    }
    
    public BigDecimal calcularTotalFinalConTodosDecimales() 
    {
        BigDecimal totalDetalle=getCalcularTotalDetalleConTodosDecimales();
        BigDecimal iva= recalcularIva();
        //return this.total.add(iva).add(this.valorIce);
        return totalDetalle.add(iva).add(this.valorIce);
    }


    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }

    public BigDecimal getPrecioSinAhorro() {
        return precioSinAhorro;
    }

    public void setPrecioSinAhorro(BigDecimal precioSinAhorro) {
        this.precioSinAhorro = precioSinAhorro;
    }

    public String getPresentacionCodigo() {
        return presentacionCodigo;
    }

    public void setPresentacionCodigo(String presentacionCodigo) {
        this.presentacionCodigo = presentacionCodigo;
    }
    
    
    

}
