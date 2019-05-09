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

public class Viento extends Instrumento {
    
    private String tipoTubo;
    private String modoExcitacion;

    public String getTipoTubo() {
        return tipoTubo;
    }

    public void setTipoTubo(String tipoTubo) {
        this.tipoTubo = tipoTubo;
    }

    public String getModoExcitacion() {
        return modoExcitacion;
    }

    public void setModoExcitacion(String modoExcitacion) {
        this.modoExcitacion = modoExcitacion;
    }
    
    public Viento (int id, String nombre, String marca, Fecha fechaFabricacion, double precio, String tipoTubo, String modoExcitacion)
    {
        super (id, nombre, marca, fechaFabricacion, precio);
        this.tipoTubo = tipoTubo;
        this.modoExcitacion = modoExcitacion;
    }
    
    @Override
    public String info()
    {
        String info = super.info();
        info += "Tipo de tubo: " +this.tipoTubo+ "\n"
                + "Modo de excitación: " +this.modoExcitacion+ "\n";
        
        return info;
    }
    
}
