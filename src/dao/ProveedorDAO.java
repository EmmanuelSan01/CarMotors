/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author sebas
 */
import model.Proveedor;
import DatabaseConnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    public void agregarProveedor(Proveedor proveedor) throws SQLException {
        String sql = "INSERT INTO proveedor (nombre, nit, contacto, frecuencia_visita) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getNit());
            stmt.setString(3, proveedor.getContacto());
            stmt.setInt(4, proveedor.getFrecuenciaVisita());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Proveedor agregado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al agregar proveedor: " + e.getMessage());
            throw e;
        }
    }

    public List<Proveedor> obtenerProveedores() throws SQLException {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT id_proveedor, nombre, nit, contacto, frecuencia_visita FROM proveedor";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Proveedor proveedor = new Proveedor(
                    rs.getInt("id_proveedor"),
                    rs.getString("nombre"),
                    rs.getString("nit"),
                    rs.getString("contacto"),
                    rs.getInt("frecuencia_visita")
                );
                proveedores.add(proveedor);
            }
            System.out.println("Proveedores obtenidos: " + proveedores.size());
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedores: " + e.getMessage());
            throw e;
        }
        return proveedores;
    }

    public void actualizarProveedor(Proveedor proveedor) throws SQLException {
        String sql = "UPDATE proveedor SET nombre = ?, nit = ?, contacto = ?, frecuencia_visita = ? WHERE id_proveedor = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getNit());
            stmt.setString(3, proveedor.getContacto());
            stmt.setInt(4, proveedor.getFrecuenciaVisita());
            stmt.setInt(5, proveedor.getIdProveedor());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Proveedor actualizado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al actualizar proveedor: " + e.getMessage());
            throw e;
        }
    }

    public void eliminarProveedor(int idProveedor) throws SQLException {
        String sql = "DELETE FROM proveedor WHERE id_proveedor = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProveedor);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Proveedor eliminado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
            throw e;
        }
    }
    
    // Clase auxiliar para evaluación (puedes convertirla en clase real si prefieres)
    public static class EvaluacionProveedor {
        public int proveedorId;
        public int puntualidad; // 1–5
        public int calidad;
        public int costo;

        public EvaluacionProveedor(int proveedorId, int puntualidad, int calidad, int costo) {
            this.proveedorId = proveedorId;
            this.puntualidad = puntualidad;
            this.calidad = calidad;
            this.costo = costo;
        }
    }

    public void evaluarProveedor(EvaluacionProveedor evaluacion) throws SQLException {
        String sql = "INSERT INTO evaluacion_proveedor (id_proveedor, puntualidad, calidad, costo, fecha) VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, evaluacion.proveedorId);
            stmt.setInt(2, evaluacion.puntualidad);
            stmt.setInt(3, evaluacion.calidad);
            stmt.setInt(4, evaluacion.costo);
            stmt.executeUpdate();
        }
    }

    public String obtenerReporteDesempeno(int idProveedor) throws SQLException {
        String sql = """
            SELECT 
                AVG(puntualidad) AS promedio_puntualidad,
                AVG(calidad) AS promedio_calidad,
                AVG(costo) AS promedio_costo,
                COUNT(*) AS total_evaluaciones
            FROM evaluacion_proveedor
            WHERE id_proveedor = ?
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProveedor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return String.format(
                        "Evaluaciones: %d\nPuntualidad: %.2f\nCalidad: %.2f\nCosto: %.2f",
                        rs.getInt("total_evaluaciones"),
                        rs.getDouble("promedio_puntualidad"),
                        rs.getDouble("promedio_calidad"),
                        rs.getDouble("promedio_costo")
                    );
                }
            }
        }
        return "No hay evaluaciones registradas.";
    }
}