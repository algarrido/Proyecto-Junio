/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author lidia
 */
public class CBasedeDatos {

    public static Repositorio rep = Repositorio.getInstance();
    public static FuncionesBD bd = FuncionesBD.getInstance();

    /**
     * Carga los datos de la base de datos
     */
    
    public void cargarDatos() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/planetario", "root", "");
            Statement statement = conn.createStatement();
            String sql = "create database IF NOT EXISTS planetario";
            statement.executeUpdate(sql);
            for (Sistema sis : bd.cargarSistema()) {
                rep.añadirSistema(sis, rep.getSistemas());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error a conectar con la BD");

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error, no se encuentra la libreria");

        }
    }

}
