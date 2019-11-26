/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TestTexto {
    public static void main(String[] args) {
        String descripcion="ejemplo[Hideen]";
        System.out.println(new Boolean(descripcion.toString().indexOf("[Hidden]")>=0));
    } 
}
