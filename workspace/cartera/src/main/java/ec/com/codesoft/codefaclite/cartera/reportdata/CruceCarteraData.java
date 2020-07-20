/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.reportdata;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class CruceCarteraData {
    private String carteraId;
    private String identificacion;
    private String razonSocial;
    private String nombreLegal;
    private String documento;
    private String fechaEmision;
    private String debe;
    private String haber;
    private String preimpreso;
    private String codigo;

    public CruceCarteraData() {
    }

    
    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreLegal() {
        return nombreLegal;
    }

    public void setNombreLegal(String nombreLegal) {
        this.nombreLegal = nombreLegal;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getDebe() {
        return debe;
    }

    public void setDebe(String debe) {
        this.debe = debe;
    }

    public String getHaber() {
        return haber;
    }

    public void setHaber(String haber) {
        this.haber = haber;
    }

    public String getPreimpreso() {
        return preimpreso;
    }

    public void setPreimpreso(String preimpreso) {
        this.preimpreso = preimpreso;
    }

    public String getCarteraId() {
        return carteraId;
    }

    public void setCarteraId(String carteraId) {
        this.carteraId = carteraId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    
    
    
    public static List<CruceCarteraData> castData(List<Cartera> carteraLista)
    {
        List<CruceCarteraData> resultadoData=new ArrayList<CruceCarteraData>();
        for (Cartera cartera : carteraLista) {
            CruceCarteraData data=new CruceCarteraData();
            
            data.setIdentificacion(cartera.getPersona().getIdentificacion());
            String nombreLegal=(cartera.getPersona().getNombreLegal()!=null)?cartera.getPersona().getNombreLegal():"";
            data.setNombreLegal(nombreLegal);
            data.setRazonSocial(cartera.getPersona().getRazonSocial());
            data.setDocumento(cartera.getCarteraDocumentoEnum().getNombre());
            data.setFechaEmision(cartera.getFechaEmision().toString());
            data.setDebe(cartera.getTotal().toString());
            data.setHaber("0");
            data.setPreimpreso(cartera.getPreimpreso());
            data.setCarteraId(cartera.getId().toString());
            data.setCodigo(cartera.getCodigo().toString());
            resultadoData.add(data);
            
            for (CarteraCruce cruce : cartera.getCruces()) 
            {
                data=new CruceCarteraData();
                //TODO:Optmizar codigo repetido
                data.setIdentificacion(cartera.getPersona().getIdentificacion());
                String nombreLegal2=(cartera.getPersona().getNombreLegal()!=null)?cartera.getPersona().getNombreLegal():"";
                data.setNombreLegal(nombreLegal2);
                data.setRazonSocial(cartera.getPersona().getRazonSocial());
                data.setDocumento(cruce.getCarteraDetalle().getCartera().getCarteraDocumentoEnum().getNombre());
                data.setFechaEmision(cruce.getCarteraDetalle().getCartera().getFechaEmision()+"");
                data.setDebe("0");
                data.setHaber(cruce.getValor()+"");
                data.setCodigo(cruce.getCarteraDetalle().getCartera().getCodigo());
                //data.setPreimpreso(cruce.getCarteraDetalle().getCartera().getPreimpreso());
                data.setPreimpreso(cruce.getCarteraDetalle().getCartera().getPreimpreso()); //TODO: PUEDE SER QUE SI TENGA PREIMPRESO POR EJEMPLOE RETENCIONES 
                //data.setPreimpreso(cruce.getCarteraDetalle().getCartera().getPreimpreso());
                data.setCarteraId(cartera.getId().toString());
                resultadoData.add(data);
            }
            
        }
        return resultadoData;
    }
    
    
}