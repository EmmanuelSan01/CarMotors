/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author sebas
 */
import model.Cliente;
import DatabaseConnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void agregarCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nombre, identificacion, telefono, correo) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getIdentificacion());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getCorreo());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Cliente agregado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al agregar cliente: " + e.getMessage());
            throw e;
        }
    }

    public List<Cliente> obtenerClientes() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre, identificacion, telefono, correo FROM cliente";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("identificacion"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                );
                clientes.add(cliente);
            }
            System.out.println("Clientes obtenidos: " + clientes.size());
        } catch (SQLException e) {
            System.err.println("Error al cargar clientes: " + e.getMessage());
            throw e;
        }
        return clientes;
    }

    public void actualizarCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nombre = ?, identificacion = ?, telefono = ?, correo = ? WHERE id_cliente = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getIdentificacion());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getCorreo());
            stmt.setInt(5, cliente.getIdCliente());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Cliente actualizado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            throw e;
        }
    }

    public void eliminarCliente(int idCliente) throws SQLException {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Cliente eliminado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Devuelve el cliente asociado a un servicio a través del vehículo.
     *
     * @param idServicio el ID del servicio
     * @return Cliente asociado, o null si no se encuentra
     */
    public Cliente obtenerClientePorServicio(int idServicio) {
        String sql = """
            SELECT c.id_cliente, c.nombre, c.identificacion, c.telefono, c.correo
            FROM cliente c
            JOIN vehiculo v ON v.id_cliente = c.id_cliente
            JOIN servicio s ON s.id_vehiculo = v.id_vehiculo
            WHERE s.id_servicio = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idServicio);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setIdentificacion(rs.getString("identificacion"));
                    cliente.setTelefono(rs.getString("telefono"));
                    cliente.setCorreo(rs.getString("correo"));
                    return cliente;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por servicio: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}