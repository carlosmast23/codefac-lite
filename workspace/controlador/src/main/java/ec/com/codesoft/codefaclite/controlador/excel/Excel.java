/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel;

import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
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
    
    public Excel()
    {
        this.libro = new XSSFWorkbook(); //H para xls y X para xlsx
        this.crearAyuda = libro.getCreationHelper();
        this.hoja1 = libro.createSheet("Hoja1");
        posicionesColumnas = new ArrayList<>();
    }
    
    public void gestionarIngresoInformacionExcel(String[] cabeceraDatosDinamicos, List<T> datosDinamicos) throws FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException
    {
        Map<String, CellStyle> obtenerEstilo = crearEstilos(libro);
        int numeroDatos = cabeceraDatosDinamicos.length;
        
        //Realizo la cabecera en base a los Datos obtenidos
        crearCabeceraHoja(cabeceraDatosDinamicos, obtenerEstilo);
        /**
         * Colocar el valor de los atributos por cada objeto en las celdas correspondientes 
         * y la ultima referencia de filas ingresadas 
        */
        int f = ingresarDatosCeldasHoja((List<ExcelDatosInterface>) datosDinamicos, obtenerEstilo);
        
        fila = hoja1.createRow(f);
        
        //Sumar las columnas en base a formula Excel, si las filas lo permiten
        sumarCeldasHoja(f);
        
        //Ajustar el tamaño de las celdas en base al tamaño del texto
        for(int i=0;i<numeroDatos;i++)
        {
            System.out.println("Columna a justar " + i );
            hoja1.autoSizeColumn((short)i);
        }
        
        File archivoSalida = new File("tmp\\libro.xlsx");
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
    
    public int ingresarDatosCeldasHoja(List<ExcelDatosInterface> datosDinamicos, Map<String, CellStyle> obtenerEstilo) throws IllegalArgumentException, IllegalAccessException
    {
        Class<?> infoClase;
        Field[] variables;
        Object valorVariable = null;
        int f=1;
        int c=0;
        Boolean b = false;
        Boolean b1 = true;
        
        for(Object dato : datosDinamicos)
        {

            infoClase = dato.getClass();
            variables = infoClase.getDeclaredFields();
            fila = hoja1.createRow(f);
            c=0;
            for(Field variable : variables)
            {
                variable.setAccessible(true);
                valorVariable = variable.get(dato);
                celda = fila.createCell(c);
                
                if(String.class.equals(devolverTipoDato(valorVariable.toString()).getClass()))
                {
                    celda.setCellValue((String)devolverTipoDato(valorVariable.toString()));
                }else if(Double.class.equals(devolverTipoDato(valorVariable.toString()).getClass()))
                {
                    celda.setCellValue((Double)devolverTipoDato(valorVariable.toString()));
                    b = true;
                }else if(Date.class.equals(devolverTipoDato(valorVariable.toString()).getClass()))
                {
                    celda.setCellValue((Date)devolverTipoDato(valorVariable.toString()));
                    celda.setCellStyle(obtenerEstilo.get("fecha"));
                }
                if(b1)
                {
                    if(b)
                    {
                        posicionesColumnas.add(c);
                        b = false;
                    }
                }
                c+=1;    
            }
            b1 = false;
            f+=1;
        }
        return f;
    }
    
    public void ingresarDatosCeldasHoja2(List<ExcelDatosInterface> datos) throws FileNotFoundException, IOException
    {
        Workbook libro = new XSSFWorkbook(); //H para xls y X para xlsx
        CreationHelper crearAyuda = libro.getCreationHelper();
        Sheet hoja1 = libro.createSheet("Hoja1");
        Row fila;
        Cell celda;
        int f = 0;
        int c = 0;
        for (ExcelDatosInterface dato : datos) 
        {
            fila = hoja1.createRow(f++); 
            List<TipoDato> valores = dato.getDatos();
            
            c = 0;
            System.out.println("-----------------------");
            for (TipoDato valorDato: valores) 
            {
                celda = fila.createCell(c++);
                Object valor = valorDato.valor;
                TipoDataEnum tipo = valorDato.tipoData;
                System.out.print(valor.toString()+"----");
                switch(tipo)
                {
                    case TEXTO:
                        celda.setCellValue(valor.toString());
                    break;
                    case FECHA:
                        celda.setCellValue((Date) devolverTipoDato(valor.toString()));
                    break;
                    case NUMERO:
                        celda.setCellValue(Double.parseDouble(valor.toString()));
                    break;
                    
                }  
            }
        }
        
        File archivoSalida = new File("tmp\\libroCarlos.xlsx");
        if(!archivoSalida.exists())
        {
            archivoSalida.getParentFile().mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(archivoSalida);
        libro.write(fileOutputStream);
        fileOutputStream.close();
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
    
    public static String obtenerFormulaSuma(int columna, int fila)
    {
        String letraColumna = UtilidadesTextos.obtenerLetra(columna);
        return "SUM("+letraColumna+"2:"+letraColumna+""+fila+")";
    }
    
    public static Map<String, CellStyle> crearEstilos(Workbook libro)
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
