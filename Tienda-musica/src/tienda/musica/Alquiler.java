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

public class Alquiler {
    
    private Fecha fechaAlquiler;
    private Fecha fechaDevolucion;
    private Cliente cliente;
    private boolean retraso;

    public Fecha getFechaAlquiler() {
        return fechaAlquiler;
    }

    public void setFechaAlquiler(Fecha fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    public Fecha getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Fecha fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public boolean isRetraso() {
        return retraso;
    }

    public void setRetraso(boolean retraso) {
        this.retraso = retraso;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Alquiler (Fecha fechaAlquiler, Fecha fechaDevolucion, Cliente cliente)
    {
        this.fechaAlquiler = fechaAlquiler;
        this.fechaDevolucion = fechaDevolucion;
        this.cliente = cliente;
    }
    
    
    
}
