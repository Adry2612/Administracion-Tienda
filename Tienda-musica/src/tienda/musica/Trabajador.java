/*
 * Clase Trabajador que hereda de la clase Persona.
 * Consultará a la base de datos para obtener los diferentes datos de cada trabajador.
 */
package tienda.musica;

import java.sql.*;

/**
 *
 * @author Adrian Vidal
 * @since 06/05/2019
 * @version 1.0
 */

public class Trabajador extends Persona{
    
    private String contrasenya;
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Trabajador (String nombre, String apellido1, String apellido2, int id)
    {
        super (nombre, apellido1, apellido2);
        this.id = id;
    }
    
    /**
     * Metodo que nos permitirá obtener toda la información (exceptuando la contraseña) del trabajador.
     * 
    */
    @Override
    public String info()
    {
        String info = super.info();
        info += "Id: " +this.id+ "\n";
        
        if (administrador == true)
        {
            info += "Es administrador. \n";
        }
        
        return info;
    }
    
    
    public void obtenerTrabajadores(){
        
        try{
            stmt = con.prepareStatement("SELECT * FROM trabajadores");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                
            }
        }
        
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        
    }
    
    public void inscribirCliente (){
        
        
    }
    
    public void eliminarCliente (String nombre, String apellido1){
        
        try
        {
            stmt = con.prepareStatement("DELETE * FROM Clientes WHERE nombre = nombre AND apellido1 = apellido1");
        }
        
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
        
    }
    
    public void eliminarInstrumentos (){
    
    }
    
}
