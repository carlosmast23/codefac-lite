/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.component.Component;
import org.apache.commons.lang3.ArrayUtils;
import static org.apache.poi.hssf.usermodel.HeaderFooter.file;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Carlos
 */
public abstract class ExcelMigrar {
    
    private static final String ESTADO_MIGRADO="Migrado";
    private static final String ESTADO_SIN_MIGRADO="Sin Migrar";
    /**
     * Archivo excel donde va a contener los datos para migrar
     */
    private File archivoExel;
    
    /**
     * Cabecera con los tipos de datos
     */
    private Class[] tiposDatosCabecera;
    
    /**
     * Nombre de la hoja actual para trabajar
     */
    private int hojaActual;
    
    /**
     * Metodo que va a almacenar la cabecera de las tablas
     */
    private String[] tituloTabla;
    
    /**
     * Datos de la consulta donde se van a almacenar
     */
    private List<FilaResultado> datosResultado;
    
    public abstract int getIndiceEstado();
    

    public ExcelMigrar(File archivoExel) {
        this.archivoExel = archivoExel;
        this.hojaActual=0;
    }    
    
    private void validacionInicial(XSSFSheet sheet ) throws ExcepcionMigrar
    {
        if(sheet.getLastRowNum()<1)
        {
            new ExcepcionMigrar("El documento no tiene datos para migrar");
        }
        
        /**
         * Validar que los tipos de datos ingresados sean los correctos con los formatos del excel
         */
        Row primeraFila = sheet.getRow(1);        
        for (int i = 0; i < primeraFila.getLastCellNum(); i++) {
            Cell celda = primeraFila.getCell(i);
            //System.out.println(celda.getCellTypeEnum().name());
            switch (celda.getCellTypeEnum()) {
                case STRING:
                    //System.out.println(celda.getStringCellValue());
                    if(!tiposDatosCabecera[i].equals(String.class))
                    {
                       throw new ExcepcionMigrar("La columna "+i+" tiene un tipo de dato diferente de "+tiposDatosCabecera[i].getName());
                    }
                    break;

                case NUMERIC:
                    //System.out.println(celda.getNumericCellValue());
                    if(!tiposDatosCabecera[i].equals(Double.class))
                    {
                       throw new ExcepcionMigrar("La columna "+i+" tiene un tipo de dato diferente de "+tiposDatosCabecera[i].getName());
                    }
                    break;
            }

        }
        
        
    }
    
    public void migrar(MigrarInterface interfaz)
    {
        for (FilaResultado filaResultado : datosResultado) {
            try {
                boolean procesado=interfaz.procesar(filaResultado);
                filaResultado.migrado=procesado;
            } catch (Exception ex) {
                Logger.getLogger(ExcelMigrar.class.getName()).log(Level.SEVERE, null, ex);
                filaResultado.migrado=false;
                filaResultado.error=ex.getMessage();
            }
        }
    }
    
    /**
     * Funcion que se encarga de leer los datos del archivo de excel
     * @return
     * @throws ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar.ExcepcionMigrar 
     */
    public List<FilaResultado> leerDatos() throws ExcepcionMigrar
    {
        datosResultado=new ArrayList<FilaResultado>();
        
        FileInputStream file = null;
        try {
            file = new FileInputStream(archivoExel);
            // leer archivo excel
            XSSFWorkbook worbook = new XSSFWorkbook(file);
            //obtener la hoja que se va leer
            XSSFSheet sheet = worbook.getSheetAt(hojaActual);
            //worbook.getSheet(hojaActual)
            //obtener todas las filas de la hoja excel
            validacionInicial(sheet);
            
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            Row row;
            // se recorre cada fila hasta el final
            while (rowIterator.hasNext()) {
                
                row = rowIterator.next();
                //se obtiene las celdas por fila
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell;
                //se recorre cada celda
                FilaResultado filaResultado=new FilaResultado();
                while (cellIterator.hasNext()) {
                    
                    // se obtiene la celda en específico y se la imprime
                    cell = cellIterator.next();
                    
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            filaResultado.agregarDato(new CampoResultado<String>(String.class,cell.getStringCellValue()),false);
                            //System.out.print(cell.getStringCellValue() + " | ");
                            break;
                        case NUMERIC:
                            filaResultado.agregarDato(new CampoResultado<Double>(Double.class,cell.getNumericCellValue()),false);
                            System.out.print(cell.getNumericCellValue() + " | ");
                            break;
                            
                    }
                    
                }
                datosResultado.add(filaResultado);
                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelMigrar.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ExcepcionMigrar ex)
        {
            throw ex; 
        }
        catch (IOException ex) {
            Logger.getLogger(ExcelMigrar.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelMigrar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return datosResultado;
    }
    
    public void construirTabla(JTable tabla)
    {
        DefaultTableModel  modeloDefecto=new DefaultTableModel(tituloTabla,0);
                
        for (FilaResultado fila : datosResultado) {
            Object[] datos=fila.fila.toArray();
            
            
            if(!fila.migrado)
            {
                //Cuando no existe ni ningun error mostrar el texto de sin migrar
                if(fila.error.isEmpty())
                {
                    datos=ArrayUtils.add(datos,ESTADO_SIN_MIGRADO);
                }
                else //Cuando existe un error mostrar dicho error
                {
                    datos=ArrayUtils.add(datos,fila.error);
                }   
            }
            else
            {
                datos=ArrayUtils.add(datos,ESTADO_MIGRADO);
            }
            
            
            modeloDefecto.addRow(datos);
            //modeloDefecto.add
            //ArrayUtils.add(fila.fila.toArray(), hojaActual, fila)
            
            
        }
        
        tabla.setModel(modeloDefecto);
        
        /**
         * Pintar la ultima columna segun el estado
         */
        tabla.setDefaultRenderer(tabla.getColumnClass(0), new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                
                if (column == getIndiceEstado()) {
                    if (value.toString().equals(ESTADO_SIN_MIGRADO)) {
                        setBackground(Color.white);
                    } else if (value.toString().equals(ESTADO_MIGRADO)) {
                        setBackground(Color.GREEN);
                    } else {
                        setBackground(Color.orange);
                    }
                } else {
                    setBackground(Color.white);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
            }
            
        });

    }
    
    
    
    public void setTiposDatosCabecera(Class[] tiposDatosCabecera) {
        this.tiposDatosCabecera = tiposDatosCabecera;
    }

    public void setTituloTabla(String[] tituloTabla) {
        this.tituloTabla = tituloTabla;
    }
    
    
    
    
    public class ExcepcionMigrar extends Exception
    {

        public ExcepcionMigrar(String message) {
            super(message);
        }
        
    }
    

    /**
     * Clase que obtiene los resultados de la fila
     */
    public class FilaResultado
    {
        public List<CampoResultado> fila;
        public boolean migrado;
        public String error;

        public FilaResultado() {
            fila=new ArrayList<CampoResultado>();
            migrado=false;
            error="";
        }
        
        public void agregarDato(CampoResultado campoResultado,boolean migrado)
        {
            fila.add(campoResultado);
            this.migrado=migrado;
        }
        
        public CampoResultado get(int posicion)
        {
            return fila.get(posicion);
        }
        
        
    }
    
    /**
     * Clase que obtiene cada celda
     * @param <T> 
     */
    public class CampoResultado<T>
    {
        public Class clase;
        public T valor;

        public CampoResultado(Class clase, T valor) {
            this.clase = clase;
            this.valor = valor;
        }

        @Override
        public String toString() {
            return valor.toString();
        }
        
    }
    
    public interface MigrarInterface 
    {
        public boolean procesar(FilaResultado fila) throws Exception; 
    }
}
