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
 * @since 17/05/2019
 * @version 1.0
 */
public class Venta {
    
    private Integer id;
    private Instrumento instrumento;
    private Cliente cliente;
    private Date fechaCompra;
    private double precio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public Venta (Integer id, Instrumento instrumento, Cliente cliente, Date fechaCompra, double precio)
    {
        this.id = id;
        this.instrumento = instrumento;
        this.cliente = cliente;
        this.fechaCompra = fechaCompra;
        this.precio = precio;
    }
    
    public static void rellenarTabla (ObservableList <Venta> tvVenta)
    {
        Conexion conexion = new Conexion();
        Connection con = conexion.conectar();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try
        {
            stmt = con.prepareStatement("SELECT * FROM Venta");
            rs = stmt.executeQuery();
            Instrumento ins = null;
            
            while (rs.next())
            {
                
                int instrumento = rs.getInt("Instrumento");
                int cliente = rs.getInt("Cliente");
                
                PreparedStatement stmt2 = con.prepareStatement("SELECT * FROM Cliente WHERE Id = ?;");
                stmt2.setInt(1, cliente);
                ResultSet rs2 = stmt.executeQuery();
                rs2.next();
                Cliente cli = new Cliente (rs2.getInt("Id"), rs2.getString("Nombre"), rs2.getString("Apellido1"), rs2.getString("Apellido2"));
                
                stmt2 = con.prepareStatement("SELECT * FROM Cuerda;");
                rs2 = stmt.executeQuery();
            
                    while (rs.next())                

                    {
                        ins = new Cuerda (rs2.getInt("Id"), rs2.getString("Nombre"), rs2.getString("Fabricante"), rs2.getDouble("Precio"), rs2.getInt("CalibreCuerda"), rs2.getString("TipoPuente"));
                    }

                    stmt = con.prepareStatement ("SELECT * FROM Viento;");
                    rs2 = stmt.executeQuery();

                    while (rs.next())
                    {
                        ins = new Viento (rs2.getInt("Id"), rs2.getString("Nombre"), rs2.getString("Fabricante"), rs2.getDouble("Precio"), rs2.getString("ModoExcitacion"), rs2.getString("TipoBoquilla")); 
                    }

                    stmt = con.prepareStatement ("SELECT * FROM Percusion;");
                    rs2 = stmt.executeQuery();

                    while (rs.next())
                    {
                       ins = new Percusion (rs2.getInt("Id"), rs2.getString("Nombre"), rs2.getString("Fabricante"), rs2.getDouble("Precio"), rs2.getString("MaterialMembrana"), rs2.getInt("NoPiezas")); 
                    }
                   
                    tvVenta.add (new Venta (rs.getInt("IdVenta"), ins, cli, rs.getDate("FechaVenta"), rs.getDouble("Precio")));
                    
            }
            
        }
        
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        
    }
    
}
