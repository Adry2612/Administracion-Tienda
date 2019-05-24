/*
 * Clase Trabajador que hereda de la clase Persona.
 * Consultará a la base de datos para obtener los diferentes datos de cada trabajador.
 */
package tienda.musica;

import java.sql.*;
import javafx.collections.ObservableList;

/**
 *
 * @author Adrian Vidal
 * @since 06/05/2019
 * @version 1.0
 */

public class Trabajador extends Persona{

    
    private String contrasenya;
    private boolean administrador;
    
    Conexion conexion = new Conexion();
    Connection con = conexion.conectar();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }
    
    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
    
    public Trabajador (int id, String nombre, String apellido1, String apellido2, boolean administrador)
    {
        super (id, nombre, apellido1, apellido2);
        this.administrador = administrador;
    }
    
    /**
     * Metodo que nos permitirá obtener toda la información (exceptuando la contraseña) del trabajador.
     * 
    */
    @Override
    public String info()
    {
        String info = super.info();
        
        if (this.administrador == true)
        {
            info += "Es administrador. \n";
        }
        
        return info;
    }
    
     public static void rellenarTabla (ObservableList <Trabajador> tvTrabajadores)
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = con.prepareStatement("SELECT * FROM Empleado");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                tvTrabajadores.add(new Trabajador (rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Apellido1"), rs.getString("Apellido2"), rs.getBoolean("Administrador")));
            }
        }
        
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        finally
        {
            try
            {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();  
            }
            
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    
    
}
