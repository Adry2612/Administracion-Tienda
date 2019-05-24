/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.musica;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;

/**
 *
 * @author Adrian Vidal
 * @since 08/05/2019
 * @version 1.0
 */

public class Percusion extends Instrumento{
    
    private String materialMembrana;
    private int numPiezas;

    public String getMaterialMembrana() {
        return materialMembrana;
    }

    public void setMaterialMembrana(String materialMembrana) {
        this.materialMembrana = materialMembrana;
    }

    public int getNumPiezas() {
        return numPiezas;
    }

    public void setNumPiezas(int numPiezas) {
        this.numPiezas = numPiezas;
    }
    
    public Percusion (int id, String nombre, String marca, double precio, String materialMembrana, int numPiezas)
    {
        super (id, nombre, marca, precio);
        this.materialMembrana = materialMembrana;
        this.numPiezas = numPiezas;
    }
    
    static public void rellenarTabla (ObservableList <Percusion> ol_percusion)
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = con.prepareStatement("SELECT * FROM Percusion;");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                ol_percusion.add (new Percusion (rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Fabricante"), rs.getDouble("Precio"), rs.getString("MaterialMembrana"), rs.getInt("NoPiezas")));
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
    
    @Override
    public String info()
    {
        String info = super.info();
        info += "Material membrana: " +this.materialMembrana+ "\n"
                + "Número de piezas: " +this.numPiezas+ "\n";
        
        return info;
    }
    
}
