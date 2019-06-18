/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.FuncionesBD;
import modelo.Planeta;
import modelo.Sistema;
import modelo.Repositorio;
import vista.planetas.VentanaPlanetas;
import vista.sistemas.JDialogAñadirSistema;
import vista.sistemas.JDialogModificarSistema;
import vista.sistemas.VentanaSistemas;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lidia
 */
public class ControladorSistemas {

    private VentanaSistemas ve;

    public ControladorSistemas(VentanaSistemas ve) {
        this.ve = ve;
        cargarTabla();
    }

    public static Repositorio rep = Repositorio.getInstance();
    public static FuncionesBD bd = FuncionesBD.getInstance();

    /**
     * Carga la tabla sistemas
     *
     */
    public void cargarTabla() {

        String[] columnas = {"ID", "Nombre"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Sistema e : rep.getSistemas()) {
            modelo.addRow(new Object[]{e.getId(), e.getNombre()});
        }
        ve.getjTableSistemas().setModel(modelo);
    }

    /**
     * Añade el sistema si no es nulo
     *
     * @param e
     */
    public void añadir(Sistema e) {
        if (e != null) {
            rep.añadirSistema(e, rep.getSistemas());
        }

    }

    /**
     * Guarda en la BD los datos del repositorio
     */
    public void guardar() {
        for (Sistema sis : rep.getSistemas()) {
            bd.guardarS(sis);
        }

    }

    /**
     * Elimina del repositorio el elemento seleccionado de la tabla
     *
     * @param tabla
     */
    public void eliminarElementoTabla(JTable tabla) {

        int idSeleccionado = Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0).toString());
        String nombre = tabla.getValueAt(tabla.getSelectedRow(), 1).toString();

        Sistema e = new Sistema(nombre, idSeleccionado);

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.removeRow(tabla.getSelectedRow());

        rep.eliminarSistema(e, rep.getSistemas());

    }

    /**
     * Elimina
     */
    public void limpiar() {
        bd.limpiar();
    }

    /**
     * Si contiene el sistema lo devuelve
     *
     * @param e
     * @return
     */
    public boolean contieneSistema(Sistema e) {
        if (rep.getSistemas().contains(e)) {
            return true;
        }
        return false;
    }

    /**
     * Si el sistema no es nulo lo modifica
     *
     * @param posicion
     * @param e
     */
    public void modificarSistema(int posicion, Sistema e) {
        if (e != null) {
            rep.getSistemas().set(posicion, e);
        }
    }

    /**
     * Obtiene el planeta del repositorio
     *
     * @param e
     * @return
     */
    public ArrayList<Planeta> obtenerPlanetas(Sistema e) {
        ArrayList<Planeta> lista = new ArrayList<>();

        for (Planeta a : rep.getCuerpoCeleste()) {
            if (a.s.getId()== e.getId()) {
                lista.add(a);
            }
        }
        return lista;
    }

    /**
     * Evento de la tabla
     */
    public void jTableSistemasMouseClicked() {
        String id_evento;
        JTable jTableEventos = ve.getjTableSistemas();
        int row=jTableEventos.getSelectedRow();
        String id=(jTableEventos.getModel().getValueAt(row, 0)).toString();
        String nombre=(String)jTableEventos.getModel().getValueAt(row, 1);
        Sistema sistemaSeleccionado=new Sistema(nombre,Integer.parseInt(id));
        
        VentanaPlanetas va = new VentanaPlanetas(sistemaSeleccionado);
        va.setLocationRelativeTo(null);
        va.setVisible(true);
        ve.dispose();

    }

    /**
     * Boton de guardar cambios, elimina y vuelve a añadir los elementos de la
     * tabla
     */
    public void JButonGuardarCambios() {
        try {
            limpiar();
            guardar();
            JOptionPane.showMessageDialog(null, "Se ha guardado correctamente en la base de datos.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar en la base de datos.");
        }
    }

    /**
     * Boton añadir
     */
    public void JButtonAñadir() {

        JDialogAñadirSistema añadirJDialog = new JDialogAñadirSistema(ve, true);
        añadirJDialog.setTitle("Añadir Sistema");
        añadirJDialog.setLocationRelativeTo(null);
        añadirJDialog.setVisible(true);
        System.out.println("Se cierra la ventana para añadir sistema");

        try {
            añadir(añadirJDialog.getSis());
            cargarTabla();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.err.println("Excepción controlada: " + e);
        }

    }

    /**
     * Boton modificar
     */
    public void JButtonModificar() {
        String id_sistema;

        JTable jTableSistemas = ve.getjTableSistemas();
        if (jTableSistemas.getSelectedRow() != -1) {

            id_sistema = jTableSistemas.getValueAt(jTableSistemas.getSelectedRow(), 0).toString();
            String nombre = jTableSistemas.getValueAt(jTableSistemas.getSelectedRow(), 1).toString();
            JDialogModificarSistema mod = new JDialogModificarSistema(ve, true);
            mod.setTitle("Modificar Sistema");
            mod.getjTextFieldID().setText(id_sistema);
            mod.getjTextFieldNombre().setText(nombre);
            mod.setLocationRelativeTo(null);
            mod.setVisible(true);
            System.out.println("id_sistema: " + id_sistema);
            try {
                modificarSistema(Integer.parseInt(id_sistema), mod.getSis());
                cargarTabla();
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.err.println("Excepción controlada: " + e);
            } catch (ArrayIndexOutOfBoundsException ea) {
                ea.printStackTrace();
                System.err.println("Excepción controlada: " + ea);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un sistema para poder modificar");
        }
    }

    /**
     * Boton eliminar
     */
    public void JbuttonEliminar() {
        JTable jTableSistemas = ve.getjTableSistemas();
        if (jTableSistemas.getSelectedRow() != -1) {
            JDialog.setDefaultLookAndFeelDecorated(true);
            int response = JOptionPane.showConfirmDialog(null, "¿Esta seguro de que desea eliminar el sistema seleccionado?", "Eliminar Sistema",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.NO_OPTION) {
                System.out.println("No button clicked");
            } else if (response == JOptionPane.YES_OPTION) {
                eliminarElementoTabla(jTableSistemas);
                System.out.println("Yes button clicked");
            } else if (response == JOptionPane.CLOSED_OPTION) {
                System.out.println("JOptionPane closed");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un sistema para poder eliminar.");
        }
    }

}
