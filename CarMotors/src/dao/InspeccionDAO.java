/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Inspeccion;
import model.Inspeccion.ResultadoInspeccion;
import model.Servicio;
import model.Tecnico;
import DatabaseConnection.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Emmanuel
 */
public class InspeccionDAO {

    public List<Inspeccion> getAll() {
        List<Inspeccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM inspeccion";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Inspeccion ins = new Inspeccion(
                    rs.getInt("id_inspeccion"),
                    new Servicio(rs.getInt("id_servicio")),   // requiere constructor simplificado
                    new Tecnico(rs.getInt("id_tecnico")),     // requiere constructor simplificado
                    rs.getString("tipo_inspeccion"),
                    rs.getDate("fecha_inspeccion").toLocalDate(),
                    Inspeccion.ResultadoInspeccion.valueOf(rs.getString("resultado").toUpperCase().replace(" ", "_")),
                    rs.getDate("fecha_proxima") != null ? rs.getDate("fecha_proxima").toLocalDate() : null
                );
                lista.add(ins);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}