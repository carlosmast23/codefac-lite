/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.imagen;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadCodigoBarras {
    
    public static Image obtenerImagenCodigoBarras(String codigo)
    {
        try {
            Code39Bean bean39 = new Code39Bean();
            final int dpi = 160;

            //Configure the barcode generator
            bean39.setModuleWidth(UnitConv.in2mm(2.8f / dpi));

            bean39.doQuietZone(false);

            //Open output file
            //File outputFile = new File(barCodePath + fileName + ".JPG");

            //FileOutputStream out = new FileOutputStream(outputFile);
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //Set up the canvas provider for monochrome PNG output
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                    out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

            //Generate the barcode
            bean39.generateBarcode(canvas, codigo);

            //Signal end of generation
            canvas.finish();

            return UtilidadImagen.convertirOutPutStreamToImage(out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    
}
