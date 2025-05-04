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
import model.Repuesto;
import model.Repuesto.EstadoRepuesto;
import model.Repuesto.MarcaVehiculo;
import model.Repuesto.TipoRepuesto;
import DatabaseConnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepuestoDAO {

    public void agregarRepuesto(Repuesto repuesto) throws SQLException {
        String sql = "INSERT INTO repuesto (nombre, tipo, marca, modelo, id_proveedor, cantidad_stock, nivel_minimo, fecha_ingreso, vida_util_meses, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, repuesto.getNombre());
            stmt.setString(2, repuesto.getTipo().name());
            stmt.setString(3, repuesto.getMarca() != null ? repuesto.getMarca().name() : null);
            stmt.setObject(4, repuesto.getModelo());
            stmt.setObject(5, repuesto.getProveedor() != null ? repuesto.getProveedor().getIdProveedor() : null);
            stmt.setInt(6, repuesto.getCantidadStock());
            stmt.setInt(7, repuesto.getNivelMinimo());
            stmt.setDate(8, repuesto.getFechaIngreso() != null ? java.sql.Date.valueOf(repuesto.getFechaIngreso()) : null);
            stmt.setInt(9, repuesto.getVidaUtilMeses());
            stmt.setString(10, repuesto.getEstado().name().replace("_", " "));
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Repuesto agregado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al agregar repuesto: " + e.getMessage());
            throw e;
        }
    }

    public List<Repuesto> obtenerRepuestos() throws SQLException {
        List<Repuesto> repuestos = new ArrayList<>();
        String sql = "SELECT * FROM repuesto";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                try {
                    String tipoStr = rs.getString("tipo");
                    String estadoStr = rs.getString("estado");
                    String marcaStr = rs.getString("marca");

                    // Validar y convertir tipo
                    TipoRepuesto tipo;
                    try {
                        tipo = TipoRepuesto.valueOf(tipoStr);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Valor de tipo inválido: " + tipoStr);
                        continue; // Saltar este registro
                    }

                    // Validar y convertir estado
                    EstadoRepuesto estado;
                    try {
                        estado = EstadoRepuesto.valueOf(estadoStr.replace(" ", "_"));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Valor de estado inválido: " + estadoStr);
                        continue; // Saltar este registro
                    }

                    // Validar y convertir marca
                    MarcaVehiculo marca = null;
                    if (marcaStr != null) {
                        try {
                            marca = MarcaVehiculo.valueOf(marcaStr);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Valor de marca inválido: " + marcaStr);
                            continue; // Saltar este registro
                        }
                    }

                    Repuesto repuesto = new Repuesto(
                        rs.getInt("id_repuesto"),
                        rs.getString("nombre"),
                        tipo,
                        marca,
                        rs.getObject("modelo") != null ? rs.getInt("modelo") : null,
                        new Proveedor(rs.getInt("id_proveedor")),
                        rs.getInt("cantidad_stock"),
                        rs.getInt("nivel_minimo"),
                        rs.getDate("fecha_ingreso") != null ? rs.getDate("fecha_ingreso").toLocalDate() : null,
                        rs.getInt("vida_util_meses"),
                        estado
                    );
                    repuestos.add(repuesto);
                } catch (Exception e) {
                    System.err.println("Error al procesar registro de repuesto: " + e.getMessage());
                }
            }
            System.out.println("Repuestos obtenidos: " + repuestos.size());
        } catch (SQLException e) {
            System.err.println("Error al obtener repuestos: " + e.getMessage());
            throw e;
        }
        return repuestos;
    }

    public void actualizarRepuesto(Repuesto repuesto) throws SQLException {
        String sql = "UPDATE repuesto SET nombre = ?, tipo = ?, marca = ?, modelo = ?, id_proveedor = ?, cantidad_stock = ?, nivel_minimo = ?, fecha_ingreso = ?, vida_util_meses = ?, estado = ? WHERE id_repuesto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, repuesto.getNombre());
            stmt.setString(2, repuesto.getTipo().name());
            stmt.setString(3, repuesto.getMarca() != null ? repuesto.getMarca().name() : null);
            stmt.setObject(4, repuesto.getModelo());
            stmt.setObject(5, repuesto.getProveedor() != null ? repuesto.getProveedor().getIdProveedor() : null);
            stmt.setInt(6, repuesto.getCantidadStock());
            stmt.setInt(7, repuesto.getNivelMinimo());
            stmt.setDate(8, repuesto.getFechaIngreso() != null ? java.sql.Date.valueOf(repuesto.getFechaIngreso()) : null);
            stmt.setInt(9, repuesto.getVidaUtilMeses());
            stmt.setString(10, repuesto.getEstado().name().replace("_", " "));
            stmt.setInt(11, repuesto.getIdRepuesto());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Repuesto actualizado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al actualizar repuesto: " + e.getMessage());
            throw e;
        }
    }

    public void eliminarRepuesto(int idRepuesto) throws SQLException {
        String sql = "DELETE FROM repuesto WHERE id_repuesto = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idRepuesto);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Repuesto eliminado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al eliminar repuesto: " + e.getMessage());
            throw e;
        }
    }
}