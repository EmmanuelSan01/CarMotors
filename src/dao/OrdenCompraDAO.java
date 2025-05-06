/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author sebas
 */
import model.OrdenCompra;
import model.OrdenCompra.DetalleOrden;
import model.OrdenCompra.EstadoOrden;
import model.Proveedor;
import model.Repuesto;
import model.Repuesto.MarcaVehiculo;
import model.Repuesto.TipoRepuesto;
import model.Repuesto.EstadoRepuesto;
import DatabaseConnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenCompraDAO {

    public void crearOrdenCompra(OrdenCompra orden) throws SQLException {
        Connection conn = null;
        PreparedStatement stmtOrden = null;
        PreparedStatement stmtDetalle = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String sqlOrden = "INSERT INTO orden_compra (id_proveedor, fecha_orden, estado) VALUES (?, ?, ?)";
            stmtOrden = conn.prepareStatement(sqlOrden, PreparedStatement.RETURN_GENERATED_KEYS);
            stmtOrden.setInt(1, orden.getProveedor().getIdProveedor());
            stmtOrden.setDate(2, java.sql.Date.valueOf(orden.getFechaCreacion()));
            stmtOrden.setString(3, orden.getEstado().name());
            stmtOrden.executeUpdate();

            ResultSet rs = stmtOrden.getGeneratedKeys();
            if (rs.next()) {
                orden.setIdOrden(rs.getInt(1));
            }

            String sqlDetalle = "INSERT INTO orden_compra_detalle (id_orden, id_repuesto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
            stmtDetalle = conn.prepareStatement(sqlDetalle);
            for (DetalleOrden detalle : orden.getDetalles()) {
                stmtDetalle.setInt(1, orden.getIdOrden());
                stmtDetalle.setInt(2, detalle.getRepuesto().getIdRepuesto());
                stmtDetalle.setInt(3, detalle.getCantidad());
                stmtDetalle.setDouble(4, detalle.getPrecioUnitario());
                stmtDetalle.executeUpdate();
            }

            conn.commit();
            System.out.println("Orden de compra creada con ID: " + orden.getIdOrden());
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error en rollback: " + ex.getMessage());
                }
            }
            System.err.println("Error al crear orden de compra: " + e.getMessage());
            throw e;
        } finally {
            if (stmtOrden != null) stmtOrden.close();
            if (stmtDetalle != null) stmtDetalle.close();
            if (conn != null) conn.close();
        }
    }

    public void eliminarOrdenCompra(int idOrden) throws SQLException {
        Connection conn = null;
        PreparedStatement stmtDetalle = null;
        PreparedStatement stmtOrden = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String sqlDetalle = "DELETE FROM orden_compra_detalle WHERE id_orden = ?";
            stmtDetalle = conn.prepareStatement(sqlDetalle);
            stmtDetalle.setInt(1, idOrden);
            stmtDetalle.executeUpdate();

            String sqlOrden = "DELETE FROM orden_compra WHERE id_orden = ?";
            stmtOrden = conn.prepareStatement(sqlOrden);
            stmtOrden.setInt(1, idOrden);
            int rowsAffected = stmtOrden.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se encontró la orden con ID: " + idOrden);
            }

            conn.commit();
            System.out.println("Orden de compra " + idOrden + " eliminada.");
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error en rollback: " + ex.getMessage());
                }
            }
            System.err.println("Error al eliminar orden: " + e.getMessage());
            throw e;
        } finally {
            if (stmtDetalle != null) stmtDetalle.close();
            if (stmtOrden != null) stmtOrden.close();
            if (conn != null) conn.close();
        }
    }

    public List<OrdenCompra> obtenerOrdenesPendientes() throws SQLException {
        List<OrdenCompra> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM orden_compra WHERE estado = 'Pendiente'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrdenCompra orden = new OrdenCompra(
                    rs.getInt("id_orden"),
                    new Proveedor(rs.getInt("id_proveedor")),
                    rs.getDate("fecha_orden").toLocalDate(),
                    EstadoOrden.valueOf(rs.getString("estado"))
                );
                String sqlDetalles = "SELECT ocd.*, r.nombre, r.tipo, r.marca, r.modelo " +
                                    "FROM orden_compra_detalle ocd " +
                                    "JOIN repuesto r ON ocd.id_repuesto = r.id_repuesto " +
                                    "WHERE ocd.id_orden = ?";
                try (PreparedStatement stmtDet = conn.prepareStatement(sqlDetalles)) {
                    stmtDet.setInt(1, orden.getIdOrden());
                    ResultSet rsDet = stmtDet.executeQuery();
                    while (rsDet.next()) {
                        Repuesto repuesto = new Repuesto(
                            rsDet.getInt("id_repuesto"),
                            rsDet.getString("nombre"),
                            TipoRepuesto.valueOf(rsDet.getString("tipo")),
                            MarcaVehiculo.valueOf(rsDet.getString("marca")),
                            rsDet.getInt("modelo") == 0 ? null : rsDet.getInt("modelo"),
                            null, // Proveedor no necesario aquí
                            0, // Stock no necesario
                            0, // Nivel mínimo no necesario
                            null, // Fecha adquisición no necesaria
                            0, // Vida útil no necesaria
                            null // Estado no necesario
                        );
                        orden.addDetalle(new DetalleOrden(
                            rsDet.getInt("id_detalle"),
                            repuesto,
                            rsDet.getInt("cantidad"),
                            rsDet.getDouble("precio_unitario")
                        ));
                    }
                }
                ordenes.add(orden);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener órdenes pendientes: " + e.getMessage());
            throw e;
        }
        return ordenes;
    }

    public List<OrdenCompra> obtenerTodasOrdenes() throws SQLException {
        List<OrdenCompra> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM orden_compra";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                OrdenCompra orden = new OrdenCompra(
                    rs.getInt("id_orden"),
                    new Proveedor(rs.getInt("id_proveedor")),
                    rs.getDate("fecha_orden").toLocalDate(),
                    EstadoOrden.valueOf(rs.getString("estado"))
                );
                String sqlDetalles = "SELECT ocd.*, r.nombre, r.tipo, r.marca, r.modelo " +
                                    "FROM orden_compra_detalle ocd " +
                                    "JOIN repuesto r ON ocd.id_repuesto = r.id_repuesto " +
                                    "WHERE ocd.id_orden = ?";
                try (PreparedStatement stmtDet = conn.prepareStatement(sqlDetalles)) {
                    stmtDet.setInt(1, orden.getIdOrden());
                    ResultSet rsDet = stmtDet.executeQuery();
                    while (rsDet.next()) {
                        Repuesto repuesto = new Repuesto(
                            rsDet.getInt("id_repuesto"),
                            rsDet.getString("nombre"),
                            TipoRepuesto.valueOf(rsDet.getString("tipo")),
                            MarcaVehiculo.valueOf(rsDet.getString("marca")),
                            rsDet.getInt("modelo") == 0 ? null : rsDet.getInt("modelo"),
                            null, // Proveedor no necesario aquí
                            0, // Stock no necesario
                            0, // Nivel mínimo no necesario
                            null, // Fecha adquisición no necesaria
                            0, // Vida útil no necesaria
                            null // Estado no necesario
                        );
                        orden.addDetalle(new DetalleOrden(
                            rsDet.getInt("id_detalle"),
                            repuesto,
                            rsDet.getInt("cantidad"),
                            rsDet.getDouble("precio_unitario")
                        ));
                    }
                }
                ordenes.add(orden);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las órdenes: " + e.getMessage());
            throw e;
        }
        return ordenes;
    }
}