/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.RubrosNivelFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubrosNivelServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class RubrosNivelService extends ServiceAbstract<RubrosNivel,RubrosNivelFacade> implements RubrosNivelServiceIf{

    public RubrosNivelService() throws RemoteException {
        super(RubrosNivelFacade.class);
    }
    
    public List<RubrosNivel> obtenerPorCatalogoCatagoriaYNivel(CatalogoProducto.TipoEnum tipoEnum,Nivel nivel) throws RemoteException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("catalogoProducto.tipoCod", tipoEnum.getCodigo());
        mapParametros.put("nivel", nivel);
        List<RubrosNivel> rubrosDelNivel = getFacade().findByMap(mapParametros);

        mapParametros = new HashMap<String, Object>();
        mapParametros.put("catalogoProducto.tipoCod", tipoEnum.getCodigo());
        mapParametros.put("nivel", null);

        List<RubrosNivel> rubrosTodosLosNiveles = getFacade().findByMap(mapParametros);
        rubrosTodosLosNiveles.addAll(rubrosDelNivel);
        return rubrosTodosLosNiveles;
    }
    
    public List<RubrosNivel> obtenerPorCatalogoCatagoriaYNivelPeriodo(CatalogoProducto.TipoEnum tipoEnum,Nivel nivel,Periodo periodo) throws RemoteException
    {

        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("catalogoProducto.tipoCod", tipoEnum.getCodigo());
        mapParametros.put("nivel", nivel);
        mapParametros.put("periodo",periodo);
        
        List<RubrosNivel> rubrosDelNivel = getFacade().findByMap(mapParametros);

        mapParametros = new HashMap<String, Object>();
        mapParametros.put("catalogoProducto.tipoCod", tipoEnum.getCodigo());
        mapParametros.put("nivel", null);
        mapParametros.put("periodo", periodo);

        List<RubrosNivel> rubrosTodosLosNiveles = getFacade().findByMap(mapParametros);
        rubrosTodosLosNiveles.addAll(rubrosDelNivel);
        return rubrosTodosLosNiveles;
   
    }
    
    public List<RubrosNivel> buscarPorCatalogoYNivel(CatalogoProducto catalogoProducto,Nivel nivel) throws RemoteException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("catalogoProducto", catalogoProducto);
        mapParametros.put("nivel",nivel);
        List<RubrosNivel> rubrosDelNivel=getFacade().findByMap(mapParametros);
        
        mapParametros = new HashMap<String, Object>();
        mapParametros.put("catalogoProducto", catalogoProducto);
        mapParametros.put("nivel",null);
        
        List<RubrosNivel> rubrosTodosLosNiveles=getFacade().findByMap(mapParametros);
        rubrosTodosLosNiveles.addAll(rubrosDelNivel);
        return rubrosTodosLosNiveles;

    }
    
    public List<RubrosNivel> buscarPorCatalogo(CatalogoProducto catalogoProducto) throws RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("catalogoProducto", catalogoProducto);
        return getFacade().findByMap(mapParametros);
    }
    
    public List<RubrosNivel> buscarPorPeriodoYMeses(Periodo periodo,CatalogoProducto catalogoProducto,List<MesEnum> meses) throws RemoteException
    {
       return getFacade().findPorPeriodoYMeses(periodo, catalogoProducto, meses);
       
    }
    
    
    
    
            
}
