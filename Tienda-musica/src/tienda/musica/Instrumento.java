/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.musica;

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
    protected boolean disponible;
    protected Fecha fechaFabricacion;

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

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Fecha getFechaFabricacion() {
        return fechaFabricacion;
    }

    public void setFechaFabricacion(Fecha fechaFabricacion) {
        this.fechaFabricacion = fechaFabricacion;
    }
    
    public Instrumento (int id, String nombre, String marca, Fecha fechaFabricacion)
    {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.fechaFabricacion = fechaFabricacion;
    }
    
    public String info()
    {
        String info = "ID: " +this.id+ "\n"
                + "Nombre: " +this.nombre+ "\n"
                + "Marca: " +this.marca+ "\n"
                + "Fecha de fabricación: " +this.fechaFabricacion+ "\n";
        
        return info;
    }
    
}
