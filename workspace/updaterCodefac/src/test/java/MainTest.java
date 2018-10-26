
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class MainTest {
    public static void main(String[] args) {
        try {
            //ClienteParametros.jar
            //JOptionPane.showMessageDialog(null,"Pid:"+args[0]);
            
            List<String> comando = Arrays.asList(
                    "javaw",
                    "-jar",
                    "ClienteParametros.jar",
                    "87653");
            //Ejecuta el comando como si estuviera directamente ejecutando en el cmd del sistema
            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.start();
            //System.out.println(args[0]);
        } catch (IOException ex) {
            Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
