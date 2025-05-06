/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DatabaseConnection.DatabaseConnection;
import model.Repuesto;
import model.Order;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RestockController {

    public void generatePurchaseOrder(int idProveedor, List<Repuesto> repuestos) {
        String insertOrden = "INSERT INTO orden_compra (id_proveedor, fecha_orden, estado) VALUES (?, ?, ?)";
        String insertDetalle = "INSERT INTO detalle_orden_compra (id_orden, id_repuesto, cantidad) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Transacción manual

            // 1. Insertar orden_compra
            int idOrdenGenerada = -1;
            try (PreparedStatement stmtOrden = conn.prepareStatement(insertOrden, Statement.RETURN_GENERATED_KEYS)) {
                stmtOrden.setInt(1, idProveedor);
                stmtOrden.setDate(2, Date.valueOf(LocalDate.now()));
                stmtOrden.setString(3, "Pendiente");
                stmtOrden.executeUpdate();

                try (ResultSet rs = stmtOrden.getGeneratedKeys()) {
                    if (rs.next()) {
                        idOrdenGenerada = rs.getInt(1);
                    }
                }
            }

            // 2. Insertar detalle_orden_compra
            try (PreparedStatement stmtDetalle = conn.prepareStatement(insertDetalle)) {
                for (Repuesto r : repuestos) {
                    stmtDetalle.setInt(1, idOrdenGenerada);
                    stmtDetalle.setInt(2, r.getIdRepuesto());
                    stmtDetalle.setInt(3, calculateReorderLevel(r));
                    stmtDetalle.addBatch();
                }
                stmtDetalle.executeBatch();
            }

            conn.commit();
            System.out.println("Orden de compra #" + idOrdenGenerada + " generada con " + repuestos.size() + " repuestos.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getProveedorNombre(int idProveedor) {
        String sql = "SELECT nombre FROM proveedor WHERE id_proveedor = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProveedor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Desconocido";
    }

    public List<Order> trackPendingOrders() {
        List<Order> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM orden_compra WHERE estado = 'Pendiente'";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order orden = new Order();
                orden.setIdOrden(rs.getInt("id_orden"));
                orden.setIdProveedor(rs.getInt("id_proveedor"));
                orden.setFechaOrden(rs.getDate("fecha_orden").toLocalDate());
                orden.setEstado(rs.getString("estado"));
                ordenes.add(orden);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ordenes;
    }

    public void updateOrderStatus(Order order, String status) {
        String sql = "UPDATE orden_compra SET estado = ? WHERE id_orden = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, order.getIdOrden());
            stmt.executeUpdate();
            order.setEstado(status); // Actualiza en el objeto también

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int calculateReorderLevel(Repuesto repuesto) {
        return repuesto.getNivelMinimo() * 2;
    }
}
