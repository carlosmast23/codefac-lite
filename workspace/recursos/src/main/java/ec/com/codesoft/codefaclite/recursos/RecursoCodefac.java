/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.recursos;

import java.net.URL;

/**
 *
 * @author Carlos
 */
public enum RecursoCodefac {
    IMAGENES_ICONOS("img/iconos"),
    IMAGENES_GENERAL("img/general"),
    
    JASPER_CRM("reportes/crm");
    
    private String subPathResource;

    private RecursoCodefac(String subPathResource) {
        this.subPathResource = subPathResource;
    }
    
    public URL getURL()
    {
        return getClass().getResource(subPathResource);
    }
    
    public String getPath()
    {
        return getClass().getResource(subPathResource).getPath();
    }
    
    public URL getResourceURL(String file)
    {
        return getClass().getResource("/"+subPathResource+"/"+file);
    }
    
    public String getResourcePath(String file)
    {
        return getClass().getResource("/"+subPathResource+"/"+file).getPath();
    }
    
}
