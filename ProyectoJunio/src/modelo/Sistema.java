/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 * Clase Sistema
 *
 * @author lidia
 */
public class Sistema implements ISistema {

    protected int id;
    protected String nombre;
    private ArrayList<Planeta> cuerpoCeleste;

    /**
     * Constructor de la clase
     *
     * @param n
     * @param id
     */
    public Sistema(String n, int id) {
        this.nombre = n;
        this.id = id;
        cuerpoCeleste=new ArrayList<>();
    }
//-------------------------GETTER Y SETTER DE LA CLASE--------------------------

    public ArrayList<Planeta> getCuerpoCeleste() {
        return cuerpoCeleste;
    }

    public void setCuerpoCeleste(ArrayList<Planeta> cuerpoCeleste) {
        this.cuerpoCeleste = cuerpoCeleste;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override

    public void setId(int id) {
        this.id = id;
    }

    @Override

    public String getNombre() {
        return nombre;
    }

    @Override

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean añadirPlaneta(Planeta p) {
        if (!this.cuerpoCeleste.contains(p)) {
            return getCuerpoCeleste().add(p);
        }
        return false;
    }

    @Override
    public Planeta eliminarPlaneta(int pos) {
        return this.cuerpoCeleste.remove(pos);
    }
/*
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        return hash;
    }
*/
    @Override

    public boolean equals(Object obj) {
        boolean resultado = false;
        if (obj instanceof Sistema) {
            Sistema tmp = (Sistema) obj;
            if (this.id == tmp.id) {
                resultado = true;
            }
        }
        return resultado;
    }

}
