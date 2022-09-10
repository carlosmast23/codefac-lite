/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.dialog;

import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.util.List;

/**
 * @author CARLOS_CODESOFT
 */
public class ComponenteFiltro {
    public TipoFiltroEnum tipoFiltroEnum;
    public String titulo;
    public Integer numeroParametro;
    public List listaDatos;
    public Object componenteVista;
    public FiltroParametroIf filtroParametroIf;

    public ComponenteFiltro(TipoFiltroEnum tipoFiltroEnum, String titulo,Integer numeroParametro, List listaDatos) 
    {
        this.tipoFiltroEnum = tipoFiltroEnum;
        this.titulo = titulo;
        this.numeroParametro=numeroParametro;
        this.listaDatos = listaDatos;
    }
    
    /*public ComponenteFiltro(TipoFiltroEnum tipoFiltroEnum, String titulo,Integer numeroParametro, List[] listaDatos) 
    {
        this.tipoFiltroEnum = tipoFiltroEnum;
        this.titulo = titulo;
        this.numeroParametro=numeroParametro;
        this.listaDatos = UtilidadesLista.arrayToList(listaDatos);
    }*/
    
    
    
    
    public enum TipoFiltroEnum
    {
        COMBO_BOX,
        TEXTO,
        CHECK;
    }
    
    public interface FiltroParametroIf<T>
    {
        public Object getValor(T dato);
    }
}
