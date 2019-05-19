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
        PreparedStatement stmt2= null;
        ResultSet rs2 = null;
        
        try
        {
            stmt = con.prepareStatement("SELECT * FROM Ventas");
            rs = stmt.executeQuery();
            
            while (rs.next())
            {
                stmt2 = con.prepareStatement("SELECT * FROM Instrumentos WHERE Id = ?");
                rs2 = stmt2.executeQuery();
                rs2.next();
                Instrumento instrumentos = new Instrumento (rs2.getInt("Id"), rs2.getString ("Nombre"), rs.getString("Marca"), rs.getDate("fechaFabricacion"), rs.getDouble("Precio"));
                Cliente clientes = new Cliente (rs2.getInt("Id"), rs2.getString ("Nombre"), rs.getString("Marca"), rs.getString("Marca"));
                
                tvVenta.add(new Venta (rs.getInt("Id"), instrumentos, clientes, rs.getDate("FechaCompra"), rs.getDouble("Precio")));
            }
        }
        
        catch (SQLException ex)
        {
            
        }
        
    }
    
}
