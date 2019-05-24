/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.musica;


import java.sql.*;
import javafx.collections.ObservableList;

/**
 *
 * @author Adrian Vidal
 * @since 08/05/2019
 * @version 1.0
 */

public class Cuerda extends Instrumento{
    
    private int calibreCuerda;
    private String tipoPuente;

    public int getCalibreCuerda() {
        return calibreCuerda;
    }

    public void setCalibreCuerda(int calibreCuerda) {
        this.calibreCuerda = calibreCuerda;
    }

    public String getTipoPuente() {
        return tipoPuente;
    }

    public void setTipoPuente(String tipoPuente) {
        this.tipoPuente = tipoPuente;
    }
    
    public Cuerda (int id, String nombre, String marca, double precio, int calibreCuerda, String tipoPuente)
    {
        super (id, nombre, marca, precio);
        this.calibreCuerda = calibreCuerda;
        this.tipoPuente = tipoPuente;
    }
    
    static public void rellenarTabla (ObservableList <Cuerda> ol_cuerda)
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = con.prepareStatement("SELECT * FROM Cuerda;");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                ol_cuerda.add (new Cuerda (rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Fabricante"), rs.getDouble("Precio"), rs.getInt("CalibreCuerda"), rs.getString("TipoPuente")));
            }
        }
        
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
    }
    
    @Override
    /**
     * Se obtiene la información de los instrumentos de cuerda.
     * @param info String que dispondrá de toda la información necesaria.
     * @return Devuelve la información de los instrumentos de cuerda en un String.
    */
    public String info()
    {
        String info = super.info();
        info += "Calibre de cuerda: " +this.calibreCuerda+ "\n"
                + "Tipo de puente: " +this.tipoPuente+ "\n";
        
        return info;
    }  
}
