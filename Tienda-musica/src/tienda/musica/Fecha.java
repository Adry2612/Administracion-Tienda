/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tienda.musica;

import java.util.Calendar;

/**
 *
 * @author DAW
 */
public class Fecha {

    private int dia;
    private int mes;
    private int anyo;

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public Fecha(int dia, int mes, int anyo) {
        this.dia = dia;
        this.mes = mes;
        this.anyo = anyo;
    }

    public int obtenerAnyos() {
        Calendar cal = Calendar.getInstance();
        int diaAct = cal.get(Calendar.DAY_OF_MONTH);
        int mesAct = cal.get(Calendar.MONTH);
        int anyoAct = cal.get(Calendar.YEAR);

        int edad = anyoAct - anyo;

        if (mes < mesAct) {
            edad = edad - 1;
        } else {
            if ((mes == mesAct) && (dia < diaAct)) {
                edad = edad - 1;
            }

        }

        return edad;

    }

    public boolean menorQue(Fecha f) {

        boolean menor = false;
        if (this.anyo < f.anyo) {
            menor = true;
        } else if (this.anyo == f.anyo) {
            if (this.mes < f.mes) {
                menor = true;
            } else if (this.mes == f.mes) {
                if (this.dia < f.dia) {
                    menor = true;
                }
            }

        }
        return menor;
    }

    public boolean mayorQue(Fecha f) {

        boolean mayor = false;
        if (this.anyo > f.anyo) {
            mayor = true;
        } 
        
        else if (this.anyo == f.anyo) {
            if (this.mes > f.mes) {
                mayor = true;
            } 
            
            else if (this.mes == f.mes) {
                if (this.dia > f.dia) {
                    mayor = true;
                }
            }

        }
        return mayor;
    }

    public String fechaCorta() {
        String dia0 = "0";
        String mes0 = "0";

        if (this.dia < 10) {
            dia0 += dia;
        } else {
            dia0 = "" + dia;
        }

        if (this.mes < 10) {
            mes0 += mes;
        } else {
            mes0 = "" + mes;
        }

        return dia0 + " - " + mes0 + " - " + this.anyo;
    }

}
