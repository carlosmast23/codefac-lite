/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class CarteraBusqueda implements InterfaceModelFind<Cartera> 
{
    /**
     * Variable para filtrar solo los que tengan saldo pendiente
     */
    private Boolean soloConSaldoPendiente;
    
    private Boolean buscarTipoCliente;
    private Boolean buscarTipoProveedor;
    private Persona persona;
    private List<DocumentoEnum> documentosEnum;

    public CarteraBusqueda() {
        this.buscarTipoCliente = false;
        this.buscarTipoProveedor = false;
        this.documentosEnum = null;    
        this.soloConSaldoPendiente=false;
    }

    
    public CarteraBusqueda(Boolean buscarTipoCliente, Boolean buscarTipoProveedor, List<DocumentoEnum> documentosEnum, Persona persona,Boolean soloConSaldoPendiente ) {
        this.buscarTipoCliente = buscarTipoCliente;
        this.buscarTipoProveedor = buscarTipoProveedor;
        this.documentosEnum = documentosEnum;
        this.persona=persona;
        this.soloConSaldoPendiente=soloConSaldoPendiente;
    }

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Código", 0.31d));
        titulo.add(new ColumnaDialogo("Preimpreso", 0.15d));
        titulo.add(new ColumnaDialogo("Documento", 0.15d));
        titulo.add(new ColumnaDialogo("Estado", 0.1d));
        titulo.add(new ColumnaDialogo("Identificacion", 0.15d));
        titulo.add(new ColumnaDialogo("Nombre Completo", 0.20d));
        titulo.add(new ColumnaDialogo("Saldo", 0.1d));
        titulo.add(new ColumnaDialogo("Total", 0.1d));
        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        Cartera cartera;
        //cartera.getCodigoDocumento()
        //cartera.getSecuencial();
        //cartera.getPersona().getRazonSocial();
        //cartera.getPreimpreso();
        
        
        String queryString = "SELECT u FROM Cartera u WHERE 1=1 ";
        
        if(documentosEnum!=null)
        {
            if(documentosEnum.size()>0)
            {
                queryString=queryString+"AND ( ";
            }
            
            for (int i = 0; i < documentosEnum.size(); i++) {
                
                //Si es el ultimo parametro entonces ya no pongo el parametro OR
                if(i==documentosEnum.size()-1)
                {
                    queryString=queryString+"u.codigoDocumento=?"+(i+1000)+") ";
                }
                else
                {
                    queryString=queryString+"u.codigoDocumento=?"+(i+1000)+" OR ";
                }
                
            }

        }
        
        
        if(buscarTipoCliente && buscarTipoProveedor)
        {
            queryString=queryString+" AND  u.tipoCartera=?3 or u.tipoCartera=?4 ";
        }
        else
        {
            if(buscarTipoCliente)
            {
                queryString=queryString+" AND  u.tipoCartera=?3 ";
            }
            
            if(buscarTipoProveedor)
            {
                queryString=queryString+" AND  u.tipoCartera=?4 ";
            }
        }
        
        if(persona!=null)
        {
            queryString=queryString+" AND  u.persona=?5 ";
        }
        
        //Consulta adicional cuando necesita consultar solo los que tienen saldo faltante
        if(soloConSaldoPendiente)
        {
            queryString=queryString+" AND  u.saldo>0 ";
        }
        
        queryString += " AND ( LOWER(u.persona.razonSocial) like ?1 or u.persona.identificacion like ?1 or CAST(u.secuencial CHAR(64)) like ?1 )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, filter.toLowerCase());
        
        if(documentosEnum!=null)
        {
            for (int i = 0; i < documentosEnum.size();i++)
            {                
                queryDialog.agregarParametro(1000+i,documentosEnum.get(i).getCodigo());
            }
            
        }
        
        if(persona!=null)
        {
            queryDialog.agregarParametro(5,persona);
        }
        
        if (buscarTipoCliente) 
        {
            queryDialog.agregarParametro(3,Cartera.TipoCarteraEnum.CLIENTE.getLetra());
        }

        if (buscarTipoProveedor) 
        {
            queryDialog.agregarParametro(4,Cartera.TipoCarteraEnum.PROVEEDORES.getLetra());
        }
        
        return queryDialog;
    }
        

    @Override
    public void agregarObjeto(Cartera t, Vector dato) {
        dato.add(t.getCodigo());
        dato.add(t.getPreimpreso());
        dato.add((t.getCarteraDocumentoEnum()!=null)?t.getCarteraDocumentoEnum().getNombre():"");
        dato.add((t.getEstado()!=null)?t.getEstadoEnum().getEstado():"sin especificar");
        dato.add(t.getPersona().getIdentificacion());
        dato.add(t.getPersona().getNombresCompletos());
        dato.add(t.getSaldo());
        dato.add(t.getTotal());
    }
    
}
