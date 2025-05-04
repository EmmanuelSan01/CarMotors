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
import model.Vehiculo;
import DatabaseConnection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    public void agregarVehiculo(Vehiculo vehiculo) throws SQLException {
        String sql = "INSERT INTO vehiculo (id_cliente, marca, modelo, placa, tipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vehiculo.getCliente().getIdCliente());
            stmt.setString(2, vehiculo.getMarca());
            stmt.setInt(3, vehiculo.getModelo());
            stmt.setString(4, vehiculo.getPlaca());
            stmt.setString(5, vehiculo.getTipo());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Vehículo agregado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al agregar vehículo: " + e.getMessage());
            throw e;
        }
    }

    public List<Vehiculo> obtenerVehiculos() throws SQLException {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String sql = "SELECT id_vehiculo, id_cliente, marca, modelo, placa, tipo FROM vehiculo";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Vehiculo vehiculo = new Vehiculo(
                        rs.getInt("id_vehiculo"),
                        rs.getString("placa"),
                        rs.getString("marca"),
                        rs.getInt("modelo"),
                        rs.getString("tipo"),
                        new Cliente(rs.getInt("id_cliente"))
                );
                vehiculos.add(vehiculo);
            }
            System.out.println("Vehículos obtenidos: " + vehiculos.size());
        } catch (SQLException e) {
            System.err.println("Error al obtener vehículos: " + e.getMessage());
            throw e;
        }
        return vehiculos;
    }

    public void actualizarVehiculo(Vehiculo vehiculo) throws SQLException {
        String sql = "UPDATE vehiculo SET id_cliente = ?, marca = ?, modelo = ?, placa = ?, tipo = ? WHERE id_vehiculo = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vehiculo.getCliente().getIdCliente());
            stmt.setString(2, vehiculo.getMarca());
            stmt.setInt(3, vehiculo.getModelo());
            stmt.setString(4, vehiculo.getPlaca());
            stmt.setString(5, vehiculo.getTipo());
            stmt.setInt(6, vehiculo.getIdVehiculo());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Vehículo actualizado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al actualizar vehículo: " + e.getMessage());
            throw e;
        }
    }

    public void eliminarVehiculo(int idVehiculo) throws SQLException {
        String sql = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVehiculo);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Vehículo eliminado, filas afectadas: " + rowsAffected);
        } catch (SQLException e) {
            System.err.println("Error al eliminar vehículo: " + e.getMessage());
            throw e;
        }
    }
}