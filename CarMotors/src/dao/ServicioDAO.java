/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author sebas
 */
import model.Servicio;
import model.Servicio.EstadoServicio;
import model.Vehiculo;
import DatabaseConnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {

    private String toHex(String str) {
        StringBuilder hex = new StringBuilder();
        for (char c : str.toCharArray()) {
            hex.append(String.format("%02X", (int) c));
        }
        return hex.toString();
    }

    public void agregarServicio(Servicio servicio) throws SQLException {
        String sql = "INSERT INTO servicio (tipo, id_vehiculo, id_tecnico, descripcion, tiempo_estimado, costo_manoobra, estado, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servicio.getTipo());
            stmt.setInt(2, servicio.getVehiculo().getIdVehiculo());
            stmt.setInt(3, servicio.getIdTecnico());
            stmt.setString(4, servicio.getDescripcion());
            stmt.setInt(5, servicio.getTiempoEstimado());
            stmt.setDouble(6, servicio.getCostoManoObra());
            stmt.setString(7, servicio.getEstado().name());
            stmt.setDate(8, java.sql.Date.valueOf(servicio.getFechaInicio()));
            if (servicio.getFechaFin() != null) {
                stmt.setDate(9, java.sql.Date.valueOf(servicio.getFechaFin()));
            } else {
                stmt.setNull(9, java.sql.Types.DATE);
            }
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Servicio agregado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al agregar servicio: " + e.getMessage());
            throw e;
        }
    }

    public List<Servicio> obtenerServicios() throws SQLException {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT id_servicio, tipo, id_vehiculo, id_tecnico, descripcion, tiempo_estimado, costo_manoobra, estado, fecha_inicio, fecha_fin FROM servicio";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                try {
                    String estadoStr = rs.getString("estado");
                    System.out.println("Valor original de estado: '" + estadoStr + "' (length: " + estadoStr.length() + ")");
                    System.out.println("Bytes de estado: " + toHex(estadoStr));
                    estadoStr = estadoStr.trim();
                    System.out.println("Valor después de trim: '" + estadoStr + "' (length: " + estadoStr.length() + ")");
                    EstadoServicio estado = EstadoServicio.valueOf(estadoStr.toUpperCase().replace(" ", "_"));
                    System.out.println("Valor normalizado de estado: '" + estado + "' (length: " + estado.toString().length() + ")");

                    Servicio servicio = new Servicio(
                        rs.getInt("id_servicio"),
                        rs.getString("tipo"),
                        new Vehiculo(rs.getInt("id_vehiculo")),
                        rs.getInt("id_tecnico"),
                        rs.getString("descripcion"),
                        rs.getInt("tiempo_estimado"),
                        rs.getDouble("costo_manoobra"),
                        estado,
                        rs.getDate("fecha_inicio").toLocalDate(),
                        rs.getDate("fecha_fin") == null ? null : rs.getDate("fecha_fin").toLocalDate()
                    );
                    servicios.add(servicio);
                } catch (Exception e) {
                    System.err.println("Error al procesar registro de servicio id_servicio: " + rs.getInt("id_servicio") + ": " + e.getMessage());
                }
            }
            System.out.println("Servicios obtenidos: " + servicios.size());
        } catch (SQLException e) {
            System.err.println("Error al obtener servicios: " + e.getMessage());
            throw e;
        }
        return servicios;
    }

    public void actualizarServicio(Servicio servicio) throws SQLException {
        String sql = "UPDATE servicio SET tipo = ?, id_vehiculo = ?, id_tecnico = ?, descripcion = ?, tiempo_estimado = ?, costo_manoobra = ?, estado = ?, fecha_inicio = ?, fecha_fin = ? WHERE id_servicio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, servicio.getTipo());
            stmt.setInt(2, servicio.getVehiculo().getIdVehiculo());
            stmt.setInt(3, servicio.getIdTecnico());
            stmt.setString(4, servicio.getDescripcion());
            stmt.setInt(5, servicio.getTiempoEstimado());
            stmt.setDouble(6, servicio.getCostoManoObra());
            stmt.setString(7, servicio.getEstado().name().substring(0, 1).toUpperCase() +
                servicio.getEstado().name().substring(1).toLowerCase().replace("_", " "));
            stmt.setDate(8, java.sql.Date.valueOf(servicio.getFechaInicio()));
            if (servicio.getFechaFin() != null) {
                stmt.setDate(9, java.sql.Date.valueOf(servicio.getFechaFin()));
            } else {
                stmt.setNull(9, java.sql.Types.DATE);
            }
            stmt.setInt(10, servicio.getIdServicio());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Servicio actualizado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al actualizar servicio: " + e.getMessage());
            throw e;
        }
    }

    public void eliminarServicio(int idServicio) throws SQLException {
        String sql = "DELETE FROM servicio WHERE id_servicio = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idServicio);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Servicio eliminado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al eliminar servicio: " + e.getMessage());
            throw e;
        }
    }
}