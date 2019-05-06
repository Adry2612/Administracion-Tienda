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

public class Trabajador extends Persona{
    
    protected String contrasenya;
    protected int id;

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
    
    @Override
    public String info()
    {
        String info = super.info();
        info += "Id: " +this.id+ "\n";
        
        return info;
    }
    
}
