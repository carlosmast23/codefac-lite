/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class TestVarios {
    public static void main(String[] args) {
        if(funcion1()==null)
        {
            System.out.println("Es null");
        }else
        {
            System.out.println("NO es null");
        }
    }
    
    public static Integer funcion1()
    {
        return funcion2();
    }
    
    public static Integer funcion2()
    {
        return null;
    }
}
