/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import model.Cliente;
import dao.ClienteDAO;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class ClientController {
    private ClienteDAO dao = new ClienteDAO();

    public List<Cliente> getAllClientes() {
        try {
            return dao.obtenerClientes();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of(); // Retorna lista vacía si hay error
        }
    }

    public void addCliente(Cliente client) {
        try {
            dao.agregarCliente(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCliente(Cliente client) {
        try {
            dao.actualizarCliente(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCliente(int id) {
        try {
            dao.eliminarCliente(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Métodos no implementados en ClienteDAO: lanzan excepción
    public Cliente getClienteById(int id) {
        throw new UnsupportedOperationException("Método getClienteById no implementado en ClienteDAO.");
    }

    public List<Object> getClienteHistory(int id) {
        throw new UnsupportedOperationException("Método getClienteHistory no implementado en ClienteDAO.");
    }

    public void scheduleMaintenanceReminder(int id, java.util.Date date) {
        throw new UnsupportedOperationException("Método scheduleMaintenanceReminder no implementado en ClienteDAO.");
    }

    public double calculateClienteDiscount(int id) {
        throw new UnsupportedOperationException("Método calculateClienteDiscount no implementado en ClienteDAO.");
    }

    public void applyLoyaltyProgram(int id) {
        throw new UnsupportedOperationException("Método applyLoyaltyProgram no implementado en ClienteDAO.");
    }
}