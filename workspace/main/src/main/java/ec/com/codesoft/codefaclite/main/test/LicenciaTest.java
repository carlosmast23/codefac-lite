/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.main.license.BCrypt;
import ec.com.codesoft.codefaclite.main.license.ValidacionLicenciaCodefac;

/**
 *
 * @author Carlos
 */
public class LicenciaTest {
    public static void main(String[] args) {
        ValidacionLicenciaCodefac validacion=new ValidacionLicenciaCodefac();
        validacion.setPath("E:\\codefac\\licencia\\");
        validacion.validar();
        
        String  originalPassword = "carlos:9C-AD-97-81-A7-BE";
		String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
		System.out.println(generatedSecuredPasswordHash);
		
		boolean matched = BCrypt.checkpw(originalPassword, generatedSecuredPasswordHash);
		System.out.println(matched);
    }
}
