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

public class Viento extends Instrumento {
    
    private String tipoBoquilla;
    private String modoExcitacion;

    public String getModoExcitacion() {
        return modoExcitacion;
    }

    public void setModoExcitacion(String modoExcitacion) {
        this.modoExcitacion = modoExcitacion;
    }

    public String getTipoBoquilla() {
        return tipoBoquilla;
    }

    public void setTipoBoquilla(String tipoBoquilla) {
        this.tipoBoquilla = tipoBoquilla;
    }
    
    public Viento (int id, String nombre, String marca, double precio, String tipoBoquilla, String modoExcitacion)
    {
        super (id, nombre, marca, precio);
        this.tipoBoquilla = tipoBoquilla;
        this.modoExcitacion = modoExcitacion;
    }
    
    static public void rellenarTabla (ObservableList <Viento> ol_viento)
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = con.prepareStatement("SELECT * FROM Viento;");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                ol_viento.add (new Viento (rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Fabricante"), rs.getDouble("Precio"), rs.getString("TipoBoquilla"), rs.getString("ModoExcitacion")));
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
        info += "Tipo de boquilla: " +this.tipoBoquilla+ "\n"
                + "Modo de excitación: " +this.modoExcitacion+ "\n";
        
        return info;
    }
    
}
