
import java.util.Locale;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TestImpresora {
    
    public static void main(String[] args)
    {
        PrintService[] printServiceTmp=PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServiceTmp) 
        {
            System.out.println(printService.getName());
        }
        
        /*AttributeSet attributeSet=new HashPrintServiceAttributeSet(new PrinterName(string, Locale.ITALY));
        PrintService[] printService=PrintServiceLookup.lookupPrintServices(null, attributes);*/
    }
    
}
