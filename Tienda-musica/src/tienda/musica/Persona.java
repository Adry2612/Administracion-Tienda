/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.musica;

/**
 *
 * @author Adrian Vidal
 * @since 06/05/2019
 * @version 1.0
 */

public class Persona {
    
    protected String nombre;
    protected String apellido1;
    protected String apellido2;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    
    public Persona (String nombre, String apellido1, String apellido2)
    {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }
    
    public String info()
    {
        String info = "Nombre: " +this.nombre+ " " +this.apellido1+ " " +this.apellido2+ "\n";
        
        return info;
    }
    
}
