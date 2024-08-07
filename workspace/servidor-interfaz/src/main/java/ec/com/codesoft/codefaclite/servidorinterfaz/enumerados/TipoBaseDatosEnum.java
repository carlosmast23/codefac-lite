/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;

/**
 *
 * @author CARLOS_CODESOFT
 */
public enum TipoBaseDatosEnum
    {
        MYSQL("MySql"),
        DERBY("Derby");
        
        private String nombre;

        private TipoBaseDatosEnum(String nombre) {
            this.nombre = nombre;
        }

        

        public String getNombre() {
            return nombre;
        }
        
        public static TipoBaseDatosEnum buscarDato(String nombre)
        {
            if(UtilidadesTextos.verificarNullOVacio(nombre))
            {
                return null;
            }
            
            TipoBaseDatosEnum[] datoList= TipoBaseDatosEnum.values();
            for (TipoBaseDatosEnum tipoBaseDatosEnum : datoList) 
            {
                if(nombre.equals(tipoBaseDatosEnum.getNombre()))
                {
                    return tipoBaseDatosEnum;
                }
            }
            return null;
            
        }
        
    }