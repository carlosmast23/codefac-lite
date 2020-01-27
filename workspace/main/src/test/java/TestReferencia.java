/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TestReferencia {
    
    public static void main(String[] args) {
        Ejemplo otro=new Ejemplo("original");
        crearNombre(otro);
        System.out.println(otro);
    }
    
       
    public static void crearNombre(Ejemplo ejemplo)
    {
        //ejemplo.nombre="edit";
       ejemplo=new Ejemplo("creado");
    }
    
    public static class Ejemplo {

        public String nombre;

        public Ejemplo(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return "Ejemplo{" + "nombre=" + nombre + '}';
        }
        
        
    }

}
    
    



