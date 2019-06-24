/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.sistema;

import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.UtilidadWeb;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Carlos
 */
public class UtilidadesWeb {
    /*
    public void exportarPDF(ActionEvent actionEvent) throws JRException, IOException{
		Map<String,Object> parametros= new HashMap<String,Object>();
		parametros.put("txtUsuario", "MitoCode");
		
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/rptJSF.jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(),parametros, new JRBeanCollectionDataSource(this.getPersonas()));
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition","attachment; filename=jsfReporte.pdf");
		ServletOutputStream stream = response.getOutputStream();
		
		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
		
		stream.flush();
		stream.close();
		FacesContext.getCurrentInstance().responseComplete();
	}*/
    
        public static void exportarPDF(JasperPrint jasperPrint) throws JRException, IOException{

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		//response.addHeader("Content-disposition","attachment; filename=jsfReporte.pdf"); //Con este metodo descarga en el navegador
                response.setContentType("application/pdf");  //Con este metodo abre en el navegador
		ServletOutputStream stream = response.getOutputStream();
		
		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
		
		stream.flush();
		stream.close();
		FacesContext.getCurrentInstance().responseComplete();
	}
        
    public static void redirigirPaginaInterna(String pagina) {
        FacesContext contex = FacesContext.getCurrentInstance();
        //contex.getExternalContext().redirect(pagina);
        contex.getApplication().getNavigationHandler().handleNavigation(contex, null, pagina);
    }
    
    public static void redirigirPaginaExterna(String pagina) {
        try {
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect(pagina);
            //contex.getApplication().getNavigationHandler().handleNavigation(contex, null, pagina);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
