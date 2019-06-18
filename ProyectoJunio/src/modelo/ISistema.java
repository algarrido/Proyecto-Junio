/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author lidia
 */
public interface ISistema {

    int getId();

    void setId(int id);

    String getNombre();

    void setNombre(String nombre);

    boolean añadirPlaneta(Planeta p);

    Planeta eliminarPlaneta(int pos);
}
