/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author lidia
 */
public class Repositorio {

    private static final Repositorio instancia = new Repositorio();

    private ArrayList<Planeta> cuerpoCeleste;
    private ArrayList<Sistema> sistemas;

    public Repositorio() {
        cuerpoCeleste = new ArrayList<>();
        sistemas = new ArrayList<>();
    }

    /**
     * Instancia del repositorio
     *
     * @return instancia
     */
    public static Repositorio getInstance() {
        return instancia;
    }
//-------------------------------GETTER Y SETTER------------------------------- 
    public ArrayList<Planeta> getCuerpoCeleste() {
        return cuerpoCeleste;
    }

    public void setCuerpoCeleste(ArrayList<Planeta> cuerpoCeleste) {
        this.cuerpoCeleste = cuerpoCeleste;
    }

    public ArrayList<Sistema> getSistemas() {
        return sistemas;
    }

    public void setSistemas(ArrayList<Sistema> sistemas) {
        this.sistemas = sistemas;
    }

    /**
     * Añade un sistema al ArrayList
     *
     * @param e
     * @param listaSistema
     * @return
     */
    public boolean añadirSistema(Sistema e, ArrayList<Sistema> listaSistema) {

        if (!listaSistema.contains(e)) {
            return listaSistema.add(e);
        }
        return false;
    }

    /**
     * Elimina un sistema del ArrayList
     *
     * @param e
     * @param listaSistema
     * @return
     */
    public boolean eliminarSistema(Sistema e, ArrayList<Sistema> listaSistema) {

        if (listaSistema.contains(e)) {
            return listaSistema.remove(e);
        }
        return false;
    }

    /**
     * Elimina un planeta de la lista
     *
     * @param a
     * @param lista
     * @return
     */
    public boolean eliminarPlaneta(Planeta a, ArrayList<Sistema> lista) {
        for (Sistema e : lista) {
            if (e.getId() == a.s.getId()) {
                instancia.cuerpoCeleste.remove(a);
                return e.getCuerpoCeleste().remove(a);
            }
        }
        return false;
    }

}
