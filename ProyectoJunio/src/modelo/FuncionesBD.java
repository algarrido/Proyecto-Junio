/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author lidia
 */
public class FuncionesBD {

    private static final FuncionesBD instancia = new FuncionesBD();

    private Connection conexion = null;

    public FuncionesBD() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/planetario", "root", "");

        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error a conectar con la BD");
        }
    }

    public static FuncionesBD getInstance() {
        return instancia;
    }
    public static Repositorio rep = Repositorio.getInstance();

    public Connection getConexion() {
        return conexion;
    }

    public void desconectar() throws SQLException {
        conexion.close();
        conexion = null;
    }

    /**
     * Guarda en la BD el sistema pasado por parametro con sus planetas
     *
     * @param e
     */
    public void guardarS(Sistema e) {

        Statement stm = null;
        try {

            stm = getConexion().createStatement();
            stm.execute("INSERT INTO sistema (id, nombre) VALUES (" + e.getId() + ", '" + e.getNombre() + "')");
            stm.close();
            if (e.getCuerpoCeleste() != null) {
                for (Planeta a : e.getCuerpoCeleste()) {
                    guardarP(a);
                }
            }
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }

    }

    /**
     * Guarda el planeta que se le pasa por parametro
     *
     * @param a
     */
    public void guardarP(Planeta a) {
        Statement stm = null;
        try {
            stm = getConexion().createStatement();
            stm.execute("INSERT INTO planeta (nombre, nucleo, diametro, satelites,id_sistema) VALUES ('" + a.getNombre() + "', '" + a.getNucleo() + "', " + a.getDiametro() + ", " + a.getSatelites() + ", " + a.s.id + ")");
            stm.close();

        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }

    }

    /**
     * Carga los sistemas de la Base de datos junto con sus planetas
     *
     * @return listaSistema
     */
    public ArrayList<Sistema> cargarSistema() {

        //rep.getCuerpoCeleste().clear();
        Statement stm = null;
        ArrayList<Sistema> listaSistema = new ArrayList<>();

        try {
            stm = getConexion().createStatement();
            ResultSet resultados = stm.executeQuery("SELECT * FROM Sistema");

            while (resultados.next()) {
                int id = resultados.getInt(1);
                String nombre = resultados.getString(2);

                Sistema e = new Sistema(nombre, id);
                //carga los Planetas de este sistema (de cada sistema)
                ArrayList<Planeta> listaPlaneta = new ArrayList<>();
                listaPlaneta = cargarPlanetas(e);
                e.setCuerpoCeleste(listaPlaneta);
                listaSistema.add(e);

            }
            resultados.close();
            stm.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }

        return listaSistema;

    }
 /**
     * Carga los planetas correspondientes al sistema de la Base de datos
     *
     * @param e
     * @return lista
     */
    public ArrayList<Planeta> cargarPlanetas(Sistema e) {
        Statement stmt = null;
        ArrayList<Planeta> lista = new ArrayList<>();

        try {
            stmt = getConexion().createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM planeta WHERE id_sistema = " + e.getId());

            while (results.next()) {

                String nombre = results.getString(1);
                String nucleo = results.getString(2);
                double diametro = results.getDouble(3);
                int satelites = results.getInt(4);
                int id_sistema = results.getInt(5);

                Planeta a = new Planeta( nombre, nucleo, diametro, satelites,e);
                rep.getCuerpoCeleste().add(a);
                lista.add(a);
            }
            results.close();
            stmt.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
        return lista;
    }

    /**
     * Elimina todos los elementos de la Base de datos
     *
     * @param conn
     */
    public void limpiar() {

        Statement stmt = null;

        try {
            stmt = getConexion().createStatement();

            stmt.execute("DELETE FROM Sistema");
            stmt.execute("DELETE FROM Planeta");

            stmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (NullPointerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }
}
