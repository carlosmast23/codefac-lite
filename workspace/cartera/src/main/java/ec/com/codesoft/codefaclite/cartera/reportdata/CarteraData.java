/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.reportdata;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class CarteraData {
    /**
     * Datos de la cartera
     */
    private String id;
    private String descripcion;
    private String valor;
    private String saldo;
    
    /**
     * Datos de los cruces
     */
    private String fecha;
    private String documento;
    private String preimpreso;
    private String valorCruzado;
    private String totalDocCruzado;
    private String saldoDocCruzado;
    
    

    public CarteraData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getPreimpreso() {
        return preimpreso;
    }

    public void setPreimpreso(String preimpreso) {
        this.preimpreso = preimpreso;
    }

    public String getValorCruzado() {
        return valorCruzado;
    }

    public void setValorCruzado(String valorCruzado) {
        this.valorCruzado = valorCruzado;
    }

    public String getTotalDocCruzado() {
        return totalDocCruzado;
    }

    public void setTotalDocCruzado(String totalDocCruzado) {
        this.totalDocCruzado = totalDocCruzado;
    }

    public String getSaldoDocCruzado() {
        return saldoDocCruzado;
    }

    public void setSaldoDocCruzado(String saldoDocCruzado) {
        this.saldoDocCruzado = saldoDocCruzado;
    }
    
    
    
    
    
    public static List<CarteraData> castCarteraData(Cartera cartera)
    {
        List<CarteraData> resultadoData=new ArrayList<CarteraData>();
        
        Boolean ningunCruce=true;
        for (CarteraDetalle detalle : cartera.getDetalles()) 
        {                        
            if(detalle.getCruces()!=null && detalle.getCruces().size()>0)
            {
                for (CarteraCruce cruce : detalle.getCruces()) 
                {
                    CarteraData data = llenarDatosCartera(detalle);
                    data.setDocumento(cruce.getCarteraAfectada().getCarteraDocumentoEnum().getNombre());
                    data.setFecha(cruce.getCarteraAfectada().getFechaEmision().toString());
                    data.setPreimpreso(cruce.getCarteraAfectada().getPreimpreso());
                    data.setSaldoDocCruzado(cruce.getCarteraAfectada().getSaldo().setScale(2, RoundingMode.HALF_UP).toString());
                    data.setTotalDocCruzado(cruce.getCarteraAfectada().getTotal().setScale(2, RoundingMode.HALF_UP).toString());
                    data.setValorCruzado(cruce.getValor().toString());
                    resultadoData.add(data);                    
                    ningunCruce=false;
                }
                
            }           
            
            //Si no encontro ningun cruce creo una fila vacia
            if(ningunCruce)
            {
                CarteraData data = llenarDatosCartera(detalle);
                data.setDocumento("");
                data.setFecha("");
                data.setPreimpreso("");
                data.setSaldoDocCruzado("");
                data.setTotalDocCruzado("");
                data.setValorCruzado("");
                resultadoData.add(data);
            }
        }
        return resultadoData;
    }
    
    private static CarteraData llenarDatosCartera(CarteraDetalle detalle)
    {
        CarteraData data = new CarteraData();
        data.setId(detalle.getId().toString());
        data.setDescripcion(detalle.getDescripcion());
        data.setValor(detalle.getTotal().setScale(2, RoundingMode.HALF_UP).toString());
        data.setSaldo(detalle.getSaldo().setScale(2,RoundingMode.HALF_UP).toString());
        return data;
    }
    
    public static Map<String,Object> mapParametros(Cartera cartera)
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("cedula", cartera.getPersona().getIdentificacion());
        mapParametros.put("cliente", cartera.getPersona().getRazonSocial());
        mapParametros.put("telefonos", cartera.getPersona().imprimirTelefonoPorDefecto());
        mapParametros.put("direccion", cartera.getPersona().imprimirDireccionPorDefecto());
        mapParametros.put("estado", cartera.getEstadoEnum().getNombre());
        mapParametros.put("fechaIngreso", cartera.getFechaEmision().toString());
        mapParametros.put("codigo", cartera.getCodigo());
        
        String empleado="";
        if(cartera.getUsuario()!=null)
        {
            empleado=cartera.getUsuario().getNick();
            if(cartera.getUsuario().getEmpleado()!=null)
            {
                empleado=cartera.getUsuario().getEmpleado().getNombresCompletos();
            }
        }
        mapParametros.put("empleado",empleado);
        
        
        return mapParametros;
    }
    
}
