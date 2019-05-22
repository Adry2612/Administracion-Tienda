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

public class Instrumento {
    
    protected int id;
    protected String nombre;
    protected String marca;
    protected double precio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public Instrumento (int id, String nombre, String marca, double precio)
    {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
    }
    
    public void rellenarTabla (ObservableList <Instrumento> ol_instrumentos)
    {
        
    }
    
    @Override
    public String toString()
    {
        return nombre;
    }
    public String info()
    {
        String info = "ID: " +this.id+ "\n"
                + "Nombre: " +this.nombre+ "\n"
                + "Marca: " +this.marca+ "\n"
                + "Precio: " +this.precio + "\n";
        
        return info;
    }
    
}
