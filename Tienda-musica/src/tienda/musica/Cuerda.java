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

public class Cuerda extends Instrumento{
    
    private int calibreCuerda;
    private String material;
    private String tipoPastillas;
    private String tipoPuente;

    public int getCalibreCuerda() {
        return calibreCuerda;
    }

    public void setCalibreCuerda(int calibreCuerda) {
        this.calibreCuerda = calibreCuerda;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getTipoPastillas() {
        return tipoPastillas;
    }

    public void setTipoPastillas(String tipoPastillas) {
        this.tipoPastillas = tipoPastillas;
    }

    public String getTipoPuente() {
        return tipoPuente;
    }

    public void setTipoPuente(String tipoPuente) {
        this.tipoPuente = tipoPuente;
    }
    
    public Cuerda (int id, String nombre, String marca, Fecha fechaFabricacion, int calibreCuerda, String material, String tipoPastillas, String tipoPuente)
    {
        super (id, nombre, marca, fechaFabricacion);
        this.calibreCuerda = calibreCuerda;
        this.material = material;
        this.tipoPastillas = tipoPastillas;
        this.tipoPuente = tipoPuente;
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
                + "Material: " +this.material+ "\n"
                + "Tipo de pastillas: " +this.tipoPastillas+ "\n"
                + "Tipo de puente: " +this.tipoPuente+ "\n";
        
        return info;
    }
    
    
    
}
