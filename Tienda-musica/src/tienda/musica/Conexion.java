/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.musica;
import java.sql.*;

/**
 *
 * @author Adrian Vidal Lopez
 */

public class Conexion {
    
    public Connection conectar(){
        
        Connection con=null;
        
        try{            
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://10.2.1.34:3306/TiendaMusica?useSSL=false";
            String user="bd";
            String pass="Adri_2612";
            con = DriverManager.getConnection(url, user, pass);
        }
        catch(Exception ex){            
            System.out.println("Ha sido imposible crear la conexion");
        }
        
       return con; 
    }
    
    public void desconectar(Connection con){
        
        try{
            if (con!=null) con.close();
            
        } catch(Exception ex){
            
            System.out.println("Ha sido imposible cerrar la conexion");
        }
        
    }
    
}
