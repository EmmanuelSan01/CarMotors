/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Inspeccion;
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
        String sql = """
        SELECT i.*, t.nombre AS tecnico_nombre
        FROM inspeccion i
        JOIN tecnico t ON i.id_tecnico = t.id_tecnico
        """;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tecnico tecnico = new Tecnico(rs.getInt("id_tecnico"));
                tecnico.setNombre(rs.getString("tecnico_nombre")); // aquí se fija el nombre

                Inspeccion ins = new Inspeccion(
                        rs.getInt("id_inspeccion"),
                        new Servicio(rs.getInt("id_servicio")),
                        tecnico,
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

    public void insert(Inspeccion ins) {
        String sql = "INSERT INTO inspeccion (id_servicio, id_tecnico, tipo_inspeccion, fecha_inspeccion, resultado, fecha_proxima) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ins.getServicio().getIdServicio());
            stmt.setInt(2, ins.getTecnico().getIdTecnico());
            stmt.setString(3, ins.getTipoInspeccion());
            stmt.setDate(4, Date.valueOf(ins.getFechaInspeccion()));
            stmt.setString(5, ins.getResultado().name().replace("_", " "));
            if (ins.getFechaProxima() != null) {
                stmt.setDate(6, Date.valueOf(ins.getFechaProxima()));
            } else {
                stmt.setNull(6, Types.DATE);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
