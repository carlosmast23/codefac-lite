/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.recursosweb.mgbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class LoginMb implements Serializable{
    
        
    @PostConstruct
    public void init() 
    {
        //titulo = "PERO ESTA NO 9";
    }
    
    public String login()
    {
        System.out.println("Llamando al metodo Login 5()");
        return "sistema/index.xhtml";
    }
    
}
