/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.musica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Adrian Vidal
 * @since 06/05/2019
 * @version 1.0
 */

public class Cliente extends Persona{

    private String descripción;
    private int valoracion;

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }
    
    public Cliente (int id, String nombre, String apellido1, String apellido2)
    {
        super (id, nombre, apellido1, apellido2);
    }
    
    /**
     * Metodo que rellenará el tableview del menu grafico. 
     */
    public static void rellenarTabla(ObservableList <Cliente> tvClientes)
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = con.prepareStatement("SELECT * FROM Clientes");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                tvClientes.add(new Cliente (rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Apellido1"), rs.getString("Apellido2")));
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
    
    /**
     * Se obtiene la información de los clientes.
     * @param info String que dispondrá de toda la información necesaria.
     * @return Devuelve la información en un String.
    */
    @Override
    public String info()
    {
        String info = super.info();
        
        return info;
    }
    
    /**
     * Rellena el combobox con el nombre de los clientes.
     * @param info String que dispondrá de toda la información necesaria.
     * @return Devuelve la información en un String.
    */
    @Override
    public String toString()
    {
        String infoCombo = nombre +" "+ apellido1;
        
        return infoCombo;
    }   
}
