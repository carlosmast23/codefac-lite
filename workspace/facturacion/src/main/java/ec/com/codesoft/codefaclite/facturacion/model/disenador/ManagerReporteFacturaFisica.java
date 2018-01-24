/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model.disenador;

import ec.com.codesoft.codefaclite.servidor.entity.BandaComprobante;
import ec.com.codesoft.codefaclite.servidor.entity.ComponenteComprobanteFisico;
import ec.com.codesoft.codefaclite.servidor.entity.ComprobanteFisicoDisenio;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Carlos
 */
public class ManagerReporteFacturaFisica {
    
    private final String NOMBRE_ANCHO_DOCUMENTO="pageWidth";
    private final String NOMBRE_ALTO_DOCUMENTO="pageHeight";
    private final String NOMBRE_ALTO_BANDA="height";
    private final String NOMBRE_ID_COMPONENTE="uuid";
    private final String NOMBRE_X_COMPONENTE="x";
    private final String NOMBRE_Y_COMPONENTE="y";
    private final String NOMBRE_ANCHO_COMPONENTE="width";
    private final String NOMBRE_ALTO_COMPONENTE="height";
    private final String NOMBRE_FONT_COMPONENTE="font";
    
    /**
     * Reporte original sobre el cual se va a trabajar
     */
    InputStream reporteOriginal;
    /**
     * Documento en memoria que contiene la estrucutura del xml
     */
    Document document;
    
    /**
     * Raiz Principal del archivo XML
     */
    Element rootNode;

    public ManagerReporteFacturaFisica(InputStream reporteOriginal) {
        this.reporteOriginal = reporteOriginal;
    }
    
    private void cargarDocumento()
    {
        try {
            SAXBuilder builder = new SAXBuilder();
            document = (Document) builder.build(reporteOriginal);
            rootNode = document.getRootElement();
        } catch (JDOMException ex) {
            Logger.getLogger(ManagerReporteFacturaFisica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ManagerReporteFacturaFisica.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    private Element buscarEtiquetaPorNombre(Element root, String nombre)
    {
        for (Object element : root.getChildren()) {
            if(((Element)element).getName().equals(nombre))
            {
                return (Element) element;
            }
        }
        return null;
    
    }
    
    private void setearComponente(Element columna,ComponenteComprobanteFisico componente)
    {
        Element bandaElemento=(Element) columna.getChildren().get(0); //el componente 0 es la banda
        //Va a recorrer todos los componentes de la banda
        for (Object object : bandaElemento.getChildren()) {
            Element tipoElemento=(Element) object;
            Element elementoReporte=buscarEtiquetaPorNombre(tipoElemento, "reportElement");
            //Si encuentra el elemento que busca setea los valores
            if(elementoReporte.getAttribute(NOMBRE_ID_COMPONENTE).getName().equals(componente.getUuid()))
            {
                elementoReporte.getAttribute(NOMBRE_X_COMPONENTE).setValue(componente.getX()+"");
                elementoReporte.getAttribute(NOMBRE_Y_COMPONENTE).setValue(componente.getX()+"");
                elementoReporte.getAttribute(NOMBRE_ANCHO_COMPONENTE).setValue(componente.getAncho()+"");
                elementoReporte.getAttribute(NOMBRE_ALTO_COMPONENTE).setValue(componente.getAlto()+"");
                
                Element elementoCaracteristica=buscarEtiquetaPorNombre(tipoElemento, "textElement");
                Element elementoFont=buscarEtiquetaPorNombre(elementoCaracteristica, "font");
                elementoFont.getAttribute("NOMBRE_FONT_COMPONENTE").setValue("12");//TODO: Falta grabar este valor
                
                
            }
            
        }
    }
    
    public void setearNuevosValores(ComprobanteFisicoDisenio comprobante)
    {
       //Establecer propiedades de ancho y alto del documento
        rootNode.getAttribute(NOMBRE_ANCHO_DOCUMENTO).setValue(comprobante.getAncho()+"");
        rootNode.getAttribute(NOMBRE_ALTO_DOCUMENTO).setValue(comprobante.getAlto()+"");
        
        //Establecer propiedades de las bandas
        for (BandaComprobante banda : comprobante.getSecciones()) {
            Element columnaElemento=buscarEtiquetaPorNombre(rootNode,banda.getNombre());
            Element bandaElemento=(Element) columnaElemento.getChildren().get(0); //el componente 0 es la banda
            bandaElemento.getAttribute(NOMBRE_ALTO_BANDA).setValue(banda.getAlto()+"");
            
            //Establcer propiedades de los componentes
            for (ComponenteComprobanteFisico componente : banda.getComponentes()) {
                setearComponente(columnaElemento,componente);
            }
        }
        
    }

    
}
