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
    
    public Cliente (String nombre, String apellido1, String apellido2)
    {
        super (nombre, apellido1, apellido2);
    }
    
    @Override
    public String info()
    {
        String info = super.info();
        
        return info;
    }
    
    public void comprar(){
        
    }
    
    public void alquilar(){
        
    }
        
    public void vender(){
        
    }
    
}
