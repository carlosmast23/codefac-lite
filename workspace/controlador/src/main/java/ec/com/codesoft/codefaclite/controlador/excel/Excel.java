/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel;

import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class Excel<T> 
{
    private Workbook libro;
    private CreationHelper crearAyuda;
    private Sheet hoja1;
    private Row fila;
    private Cell celda;
    private List <Integer> posicionesColumnas;
    private String archivo;
    private String nombreArchivExcel;
    
    public Excel()
    {
        this.libro = new XSSFWorkbook(); //H para xls y X para xlsx
        this.crearAyuda = libro.getCreationHelper();
        this.hoja1 = libro.createSheet("Hoja1");
        this.posicionesColumnas = new ArrayList<>();
        Date date = new Date();
        String nombreArchivoExcel = "" + date;
        nombreArchivoExcel = nombreArchivoExcel.replaceAll(" ", "");
        nombreArchivoExcel = nombreArchivoExcel.replaceAll(":", "");
        this.archivo = "\\tmp\\"+nombreArchivoExcel+".xlsx";
    }
    
    public void gestionarIngresoInformacionExcel(String[] cabeceraDatosDinamicos, List<ExcelDatosInterface> datosDinamicos) throws FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException
    {
        Map<String, CellStyle> obtenerEstilo = crearEstilos(libro);
        int numeroDatos = cabeceraDatosDinamicos.length;
        
        //Realizo la cabecera en base a los Datos obtenidos
        crearCabeceraHoja(cabeceraDatosDinamicos, obtenerEstilo);
        /**
         * Colocar el valor de los atributos por cada objeto en las celdas correspondientes 
         * y la ultima referencia de filas ingresadas 
        */
        int f = ingresarDatosCeldasHoja(datosDinamicos, obtenerEstilo);
        
        fila = hoja1.createRow(f);
             
        //Ajustar el tamaño de las celdas en base al tamaño del texto
        for(int i=0;i<numeroDatos;i++)
        {
            hoja1.autoSizeColumn((short)i);
        }
        
        //Sumar las columnas en base a formula Excel, si las filas lo permiten
        sumarCeldasHoja(f);
                
        File archivoSalida = new File(archivo);
        if(!archivoSalida.exists())
        {
            archivoSalida.getParentFile().mkdirs();
        }
        
        FileOutputStream fileOutputStream = new FileOutputStream(archivoSalida);
        libro.write(fileOutputStream);
        fileOutputStream.close();
    }
    
    public void crearCabeceraHoja(String[] cabeceraDatosDinamicos, Map<String, CellStyle> obtenerEstilo)
    {
        fila = hoja1.createRow(0);       
            for(int i = 0; i< cabeceraDatosDinamicos.length; i++)
            {
                celda = fila.createCell(i);
                celda.setCellValue(cabeceraDatosDinamicos[i]);
                celda.setCellStyle(obtenerEstilo.get("titulo"));
                celda.setCellStyle(obtenerEstilo.get("color"));
                celda.setCellStyle(obtenerEstilo.get("centrar"));
            }
            
    }
 
    public int ingresarDatosCeldasHoja(List<ExcelDatosInterface> datos, Map<String,CellStyle> estilo) throws FileNotFoundException, IOException
    {
        int f = 1;
        int c = 0;
        boolean b = true;
        for (ExcelDatosInterface dato : datos) 
        {
            fila = hoja1.createRow(f); 
            List<TipoDato> valores = dato.getDatos();
            c = 0;
            for (TipoDato valorDato: valores) 
            {
                celda = fila.createCell(c++);
                Object valor = valorDato.valor;
                TipoDataEnum tipo = valorDato.tipoData;
                switch(tipo)
                {
                    case TEXTO:
                        celda.setCellValue(valor.toString());
                    break;
                    case FECHA:
                        celda.setCellValue((Date) devolverTipoDato(valor.toString()));
                        celda.setCellStyle(estilo.get("fecha"));
                    break;
                    case NUMERO:
                        celda.setCellValue(Double.parseDouble(valor.toString()));
                        if(b)
                        {
                            posicionesColumnas.add(c-1);
                        }
                    break; 
                }  
            }
            f+=1;
            b = false;
        }
        return f;
    }
 
    public void sumarCeldasHoja(int f)
    {
        for(Integer posicionColumna: posicionesColumnas)
        {
            String formula = obtenerFormulaSuma(posicionColumna, f);
            celda = fila.createCell((int)posicionColumna);
            celda.setCellType(CellType.FORMULA);
            celda.setCellFormula(formula);
        }
    }
    
    public static Object devolverTipoDato(String dato)
    {
        
        if(UtilidadesTextos.transformarStringDouble(dato)!= null)
        {
            return UtilidadesTextos.transformarStringDouble(dato);
        }
        else if(UtilidadesTextos.transformarStringDate(dato)!= null)
        {
            return UtilidadesTextos.transformarStringDate(dato);
        }else
        {
            return dato;
        }
    }
    
    public String obtenerFormulaSuma(int columna, int fila)
    {
        String letraColumna = UtilidadesTextos.obtenerLetra(columna);
        return "SUM("+letraColumna+"2:"+letraColumna+""+fila+")";
    }
    
    public Map<String, CellStyle> crearEstilos(Workbook libro)
    {
        Map<String,CellStyle> estilos = new HashMap<>();
        CellStyle estiloCelda = libro.createCellStyle();
        
        Font formatoCabecera = libro.createFont();
        formatoCabecera.setFontHeightInPoints((short)12);
        formatoCabecera.setFontName("Calibri");
        formatoCabecera.setBold(true);
        estiloCelda.setFont(formatoCabecera);  
        estilos.put("titulo", estiloCelda);
        
        estiloCelda.setAlignment(HorizontalAlignment.CENTER);
        estilos.put("centrar", estiloCelda);
        
        estiloCelda.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        estiloCelda.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estilos.put("color", estiloCelda);
        
        estiloCelda = libro.createCellStyle();
        estiloCelda.setDataFormat(libro.createDataFormat().getFormat("mm/dd/yyyy"));
        estilos.put("fecha", estiloCelda);
        return estilos;
    }

    public void abrirDocumento()
    {
        try 
        {
            File path = new File (archivo);
            Desktop.getDesktop().open(path);
        }
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
    }   
    
    public void eliminarDocumento()
    {
        File archivo = new File(this.archivo);
        try{
            if(archivo.delete())
            {
                System.out.println("Se elimino el archivo");
            }
            else
            {
                System.out.println("No se puede eliminar el archivo");
            }
        }catch(Exception exc)
        {
            System.out.println("Error al eliminar el archivo");
        }
                
    }
    
    public enum TipoDataEnum
    {
        TEXTO(String.class),
        NUMERO(Double.class),
        FECHA(Date.class);
        
        private Class clase;
        
        private TipoDataEnum(Class clase)
        {
            this.clase = clase;
        }
        
        public Object convertir(Object valor)
        {
            return clase.cast(valor);
        }
              
    };
    
    
}
