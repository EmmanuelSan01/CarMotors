/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Factura;
import DatabaseConnection.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class FacturaDAO {

    public void agregarFactura(Factura factura) throws SQLException {
        String sql = "INSERT INTO factura (id_servicio, fecha_emision, total, cufe, numero_factura) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, factura.getIdServicio());
            stmt.setTimestamp(2, Timestamp.valueOf(factura.getFechaEmision()));
            stmt.setDouble(3, factura.getTotal());
            stmt.setString(4, factura.getCUFE());

            // Temporariamente usa un valor placeholder
            stmt.setString(5, "TEMP"); // será actualizado luego

            stmt.executeUpdate();

            // Obtener ID generado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    String numeroFactura = "FE" + (100 + idGenerado);

                    // Actualizar número de factura real
                    try (PreparedStatement update = conn.prepareStatement("UPDATE factura SET numero_factura = ? WHERE id_factura = ?")) {
                        update.setString(1, numeroFactura);
                        update.setInt(2, idGenerado);
                        update.executeUpdate();
                    }
                }
            }
        }
    }

    public List<Factura> obtenerFacturas() throws SQLException {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT id_factura, id_servicio, fecha_emision, total, cufe FROM factura";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Factura f = new Factura(
                        rs.getInt("id_factura"),
                        rs.getInt("id_servicio"),
                        rs.getTimestamp("fecha_emision").toLocalDateTime(),
                        rs.getDouble("total")
                );
                lista.add(f);
            }
        }
        return lista;
    }
}