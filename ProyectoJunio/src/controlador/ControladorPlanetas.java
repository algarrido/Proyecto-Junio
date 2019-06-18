/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Planeta;
import modelo.Sistema;
import modelo.Repositorio;
import vista.planetas.JDialogAñadirPlaneta;
import vista.planetas.JDialogModificarPlaneta;
import vista.planetas.VentanaPlanetas;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lidia
 */
public class ControladorPlanetas {

    private VentanaPlanetas vp;

    public ControladorPlanetas(VentanaPlanetas va) {
        this.vp = va;
        cargarTabla();
    }
    public static Repositorio rep = Repositorio.getInstance();

    /**
     * Carga la tabla con los planetas guardados en el repositorio
     */
    public void cargarTabla() {

        String[] columnas = {"Nombre", "Nucleo", "Diametro", "Satelites"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Planeta a : rep.getCuerpoCeleste()) {
            System.out.println(a);
            System.out.println(a.s);
            System.out.println(a.s.getId());
            System.out.println(vp.getId_sistema());
            if (a.s.getId() == vp.getId_sistema().getId()) {
                modelo.addRow(new Object[]{a.getNombre(), a.getNucleo(), a.getDiametro(), a.getSatelites()});
            }
        }
        vp.getjTablePlanetas().setModel(modelo);
    }

    /**
     * Devuelve el sistema pasandole el id
     *
     * @param id
     * @return s
     */
    public Sistema obtenerSistema(int id) {
        Sistema s = new Sistema("error", -1);
        for (Sistema e : rep.getSistemas()) {
            if (e.getId() == id) {
                s = e;
            }
        }
        return s;
    }

    /**
     * Añade planeta a la Clase repositorio y a la lista de planetas del sistema
     *
     * @param p
     * @return
     */
    public boolean añadirPlaneta(Planeta p) {
        for (Planeta pl : rep.getCuerpoCeleste()) {
            if (pl.getNombre().equals(p.getNombre())) {
                return false;
            }
        }

        for (Sistema e : rep.getSistemas()) {

            if (e.getId() == p.s.getId()) {
                rep.getCuerpoCeleste().add(p);
                e.añadirPlaneta(p);
            }
        }
        return true;
    }

    /**
     * Elimina el elemento selecionado de la tabla y del repositorio
     *
     * @param tabla
     * @param id_sistema
     */
    public void eliminarElementoTabla(JTable tabla, Sistema id_sistema) {

        int idSeleccionado = tabla.getSelectedRow();
        //String nombre_sistema = tabla.getValueAt(tabla.getSelectedRow(), 1).toString();
        String nombre = tabla.getValueAt(tabla.getSelectedRow(), 0).toString();
        String nucleo = tabla.getValueAt(tabla.getSelectedRow(), 1).toString();
        double diametro = Double.parseDouble(tabla.getValueAt(tabla.getSelectedRow(), 2).toString());
        int satelites = Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 3).toString());

        Planeta a = new Planeta(nombre, nucleo, diametro, satelites, id_sistema);

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.removeRow(tabla.getSelectedRow());

        Sistema e = obtenerSistema(id_sistema.getId());

        rep.eliminarPlaneta(a, rep.getSistemas());

    }

    /**
     * Modifica un planeta elegido en la tabla
     *
     * @param tabla
     * @param nuevoP
     * @param id_sistema
     */
    public void modificarPlaneta(JTable tabla, Planeta nuevoP, Sistema id_sistema) {
        if (nuevoP != null) {

            String nombre = tabla.getValueAt(tabla.getSelectedRow(), 0).toString();
            String nucleo = tabla.getValueAt(tabla.getSelectedRow(), 1).toString();
            double diametro = Double.parseDouble(tabla.getValueAt(tabla.getSelectedRow(), 2).toString());
            int satelites = Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 3).toString());

            Planeta a = new Planeta( nombre, nucleo, diametro, satelites,id_sistema);

            Sistema e = obtenerSistema(id_sistema.getId());
            int idSeleccionado = tabla.getSelectedRow();
            e.getCuerpoCeleste().set(idSeleccionado, nuevoP);
            rep.getCuerpoCeleste().set(buscaPosicion(rep.getCuerpoCeleste(), a), nuevoP);

        }
    }

    /**
     * Comprueba que el planeta existe
     *
     * @param tabla
     * @param nuevoP
     * @param id_sistema
     * @return
     */
    public boolean PlanetaCorrecto(JTable tabla, Planeta nuevoP, Sistema id_sistema) {

        String nombre = tabla.getValueAt(tabla.getSelectedRow(), 1).toString();
        String nucleo = tabla.getValueAt(tabla.getSelectedRow(), 2).toString();
        double diametro = Double.parseDouble(tabla.getValueAt(tabla.getSelectedRow(), 3).toString());
        int satelites = Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 3).toString());

        Planeta a = new Planeta( nombre, nucleo, diametro, satelites,id_sistema);

        Sistema e = obtenerSistema(id_sistema.getId());

        for (Planeta as : rep.getCuerpoCeleste()) {
            if (!a.getNombre().equals(nuevoP.getNombre())) {
                if (as.equals(nuevoP)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Busca la posicion del planeta en la lista
     *
     * @param lista
     * @param a
     * @return posicion
     */
    public int buscaPosicion(ArrayList<Planeta> lista, Planeta a) {
        int posicion = -1;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).equals(a)) {
                posicion = i;
            }
        }
        return posicion;
    }

    /**
     * Boton anañir
     */
    public void JButtonAñadir() {

        JTable jTablePlanetas = vp.getjTablePlanetas();

        JDialogAñadirPlaneta añadir = new JDialogAñadirPlaneta(vp, true, vp.getId_sistema());
        añadir.setTitle("Añadir Planeta");
        añadir.setLocationRelativeTo(null);
        añadir.setVisible(true);
        cargarTabla();

    }

    /**
     * Boton eiminar
     */
    public void JButtonEliminar() {
        JTable jTablePlanetas = vp.getjTablePlanetas();

        if (jTablePlanetas.getSelectedRow() != -1) {

            JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null, "¿Esta seguro de que desea eliminar el sistema seleccionado?", "Eliminar Sistema",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.NO_OPTION) {
                System.out.println("No button clicked");
            } else if (response == JOptionPane.YES_OPTION) {
                eliminarElementoTabla(jTablePlanetas, vp.getId_sistema());
                System.out.println("Yes button clicked");
            } else if (response == JOptionPane.CLOSED_OPTION) {
                System.out.println("JOptionPane closed");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un planeta para poder eliminar.");
        }
    }

    /**
     * Boton modificar
     */
    public void JButtonModificar() {
        boolean incorrecto = true;
        JTable jTablePlanetas = vp.getjTablePlanetas();
        if (jTablePlanetas.getSelectedRow() != -1) {
            while (incorrecto) {
                JDialogModificarPlaneta mod = new JDialogModificarPlaneta(vp, true, vp.getId_sistema());
                mod.setTitle("Modificar Planeta");
                mod.getjTextFieldNmbre().setText(jTablePlanetas.getValueAt(jTablePlanetas.getSelectedRow(), 0).toString());
                mod.getjTextFieldNucleo().setText(jTablePlanetas.getValueAt(jTablePlanetas.getSelectedRow(), 1).toString());
                mod.getjTextFieldDiametro().setText(jTablePlanetas.getValueAt(jTablePlanetas.getSelectedRow(), 2).toString());
                mod.getjTextFieldSatelites().setText(jTablePlanetas.getValueAt(jTablePlanetas.getSelectedRow(), 3).toString());

                mod.setLocationRelativeTo(null);
                mod.setVisible(true);
                if (mod.getNuevoP() != null) {
                    if (PlanetaCorrecto(jTablePlanetas, mod.getNuevoP(), vp.getId_sistema())) {

                        modificarPlaneta(jTablePlanetas, mod.getNuevoP(), vp.getId_sistema());
                        JOptionPane.showMessageDialog(null, "Planeta modificado correctamente.");
                        incorrecto = false;

                    } else {
                        JOptionPane.showMessageDialog(null, "Error, el el nombre introducido ya existe.");
                        incorrecto = true;
                    }

                } else {
                    incorrecto = false;
                }
            }

            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un planeta para poder modificar.");
        }

    }
}
