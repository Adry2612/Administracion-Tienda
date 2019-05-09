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

public class Percusion extends Instrumento{
    
    private String materialMembrana;
    private int numPiezas;
    private double altura;

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

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }
    
    public Percusion (int id, String nombre, String marca, Fecha fechaFabricacion, double precio, String materialMembrana, int numPiezas, double altura)
    {
        super (id, nombre, marca, fechaFabricacion, precio);
        this.materialMembrana = materialMembrana;
        this.numPiezas = numPiezas;
        this.altura = altura;  
    }
    
    @Override
    public String info()
    {
        String info = super.info();
        info += "Material membrana: " +this.materialMembrana+ "\n"
                + "Número de piezas: " +this.numPiezas+ "\n"
                + "Altura: " +this.altura+ "\n";
        
        return info;
    }
    
}
