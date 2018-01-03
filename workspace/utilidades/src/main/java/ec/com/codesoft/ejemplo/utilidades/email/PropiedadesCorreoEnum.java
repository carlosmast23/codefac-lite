/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.email;

/**
 *
 * @author Carlos
 */
public enum PropiedadesCorreoEnum {
    GMAIL("smtp.gmail.com","587"),
    HOTMAIL("smtp-mail.outlook.com","587");

    private String host;
    private String port;

    
    private PropiedadesCorreoEnum(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    
    
    
   
}
