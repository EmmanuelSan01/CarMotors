/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author sebas
 */
import model.Lote;
import model.Proveedor;
import model.Repuesto;
import DatabaseConnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoteDAO {

    public void agregarLote(Lote lote) throws SQLException {
        String sql = "INSERT INTO lote (id_repuesto, fecha_ingreso, id_proveedor, fecha_caducidad) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lote.getRepuesto().getIdRepuesto());
            stmt.setDate(2, java.sql.Date.valueOf(lote.getFechaIngreso()));
            stmt.setObject(3, lote.getProveedor() != null ? lote.getProveedor().getIdProveedor() : null);
            stmt.setDate(4, lote.getFechaCaducidad() != null ? java.sql.Date.valueOf(lote.getFechaCaducidad()) : null);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Lote agregado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al agregar lote: " + e.getMessage());
            throw e;
        }
    }

    public List<Lote> obtenerLotes() throws SQLException {
        List<Lote> lotes = new ArrayList<>();
        String sql = "SELECT * FROM lote";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Lote lote = new Lote(
                    rs.getInt("id_lote"),
                    new Repuesto(rs.getInt("id_repuesto")),
                    rs.getDate("fecha_ingreso").toLocalDate(),
                    new Proveedor(rs.getInt("id_proveedor")),
                    rs.getDate("fecha_caducidad") != null ? rs.getDate("fecha_caducidad").toLocalDate() : null
                );
                lotes.add(lote);
            }
            System.out.println("Lotes obtenidos: " + lotes.size());
        } catch (SQLException e) {
            System.err.println("Error al obtener lotes: " + e.getMessage());
            throw e;
        }
        return lotes;
    }

    public void actualizarLote(Lote lote) throws SQLException {
        String sql = "UPDATE lote SET id_repuesto = ?, fecha_ingreso = ?, id_proveedor = ?, fecha_caducidad = ? WHERE id_lote = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lote.getRepuesto().getIdRepuesto());
            stmt.setDate(2, java.sql.Date.valueOf(lote.getFechaIngreso()));
            stmt.setObject(3, lote.getProveedor() != null ? lote.getProveedor().getIdProveedor() : null);
            stmt.setDate(4, lote.getFechaCaducidad() != null ? java.sql.Date.valueOf(lote.getFechaCaducidad()) : null);
            stmt.setInt(5, lote.getIdLote());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Lote actualizado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al actualizar lote: " + e.getMessage());
            throw e;
        }
    }

    public void eliminarLote(int idLote) throws SQLException {
        String sql = "DELETE FROM lote WHERE id_lote = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLote);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Lote eliminado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al eliminar lote: " + e.getMessage());
            throw e;
        }
    }
}