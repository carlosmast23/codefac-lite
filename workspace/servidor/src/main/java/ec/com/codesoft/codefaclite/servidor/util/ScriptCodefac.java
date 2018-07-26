/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.util;

/**
 *
 * @author Carlos
 */
public class ScriptCodefac {
    private String query;
    private PrioridadQueryEnum prioridad;

    public ScriptCodefac(String query, PrioridadQueryEnum prioridad) {
        this.query = query;
        this.prioridad = prioridad;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public PrioridadQueryEnum getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadQueryEnum prioridad) {
        this.prioridad = prioridad;
    }
    
    
    
        
    public enum PrioridadQueryEnum
    {
        CREATE_TABLE(1),
        INSERT_COLUMN(2),
        OTHER_SCRIPT(3);
        
        private int numero;

        private PrioridadQueryEnum(int numero) {
            this.numero = numero;
        }

        public int getNumero() {
            return numero;
        }
        
    }
}
