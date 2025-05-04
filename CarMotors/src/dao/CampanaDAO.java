/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Campana;
import model.Cliente;
import DatabaseConnection.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CampanaDAO {

    public void insert(Campana campana) {
        String sql = "INSERT INTO campana (nombre, descripcion, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, campana.getNombre());
            stmt.setString(2, campana.getDescripcion());
            stmt.setDate(3, Date.valueOf(campana.getFechaInicio()));
            stmt.setDate(4, Date.valueOf(campana.getFechaFin()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Campana campana) {
        String sql = "UPDATE campana SET nombre = ?, descripcion = ?, fecha_inicio = ?, fecha_fin = ? WHERE id_campana = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, campana.getNombre());
            stmt.setString(2, campana.getDescripcion());
            stmt.setDate(3, Date.valueOf(campana.getFechaInicio()));
            stmt.setDate(4, Date.valueOf(campana.getFechaFin()));
            stmt.setInt(5, campana.getIdCampana());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM campana WHERE id_campana = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Campana> getAll() {
        List<Campana> campanas = new ArrayList<>();
        String sql = "SELECT * FROM campana";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Campana c = new Campana(
                        rs.getInt("id_campana"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_fin").toLocalDate(),
                        new ArrayList<>()
                );
                campanas.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return campanas;
    }

    public Campana getById(int id) {
        String sql = "SELECT * FROM campana WHERE id_campana = ?";
        Campana c = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    c = new Campana(
                            rs.getInt("id_campana"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDate("fecha_inicio").toLocalDate(),
                            rs.getDate("fecha_fin").toLocalDate(),
                            new ArrayList<>()
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }

    public void assignClients(int idCampana, String criterio) {
        // Este ejemplo asocia todos los clientes sin asignación previa
        String sql = """
            INSERT INTO campana_cliente (id_campana, id_cliente, fecha_asignacion)
            SELECT ?, id_cliente, CURRENT_DATE()
            FROM cliente
            WHERE id_cliente NOT IN (
                SELECT id_cliente FROM campana_cliente WHERE id_campana = ?
            )
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCampana);
            stmt.setInt(2, idCampana);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String generateReport(int idCampana) {
        String sql = "SELECT COUNT(*) AS total FROM campana_cliente WHERE id_campana = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCampana);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int total = rs.getInt("total");
                    return "Clientes asignados a campaña #" + idCampana + ": " + total;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "No se pudo generar el reporte.";
    }
}