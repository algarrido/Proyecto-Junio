/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Objects;

/**
 *
 * @author lidia
 */
public class Planeta {

    private String nombre;
    private String nucleo;
    private double diametro;
    private int satelites;
    public Sistema s;
    public int id;
    
   // public int id_sistema;

    public Planeta(String nombre, String nucleo, double diametro, int satelites,Sistema s) {
        this.s=s;
        this.nombre = nombre;
        this.nucleo = nucleo;
        this.diametro = diametro;
        this.satelites = satelites;
    }

  
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNucleo() {
        return nucleo;
    }

    public void setNucleo(String nucleo) {
        this.nucleo = nucleo;
    }

    public double getDiametro() {
        return diametro;
    }

    public void setDiametro(double diametro) {
        this.diametro = diametro;
    }

    public int getSatelites() {
        return satelites;
    }

    public void setSatelites(int satelites) {
        this.satelites = satelites;
    }

    @Override
    public String toString() {
        return this.nombre + " " + this.nucleo + " " + this.diametro + " " + this.satelites;
    }

    @Override
    public boolean equals(Object obj) {
        boolean resultado = false;
        if (obj instanceof Planeta) {
            Planeta tmp = (Planeta) obj;
            if (this.nombre.equals(tmp.nombre)) {
                resultado = true;
            }
        }
        return resultado;
    }

}
