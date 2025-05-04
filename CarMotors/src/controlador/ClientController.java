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

    public Cliente getClienteById(int id) {
        // Simulación temporal
        return getAllClientes().stream()
                .filter(c -> c.getIdCliente() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Object> getClienteHistory(int id) {
        // Lógica simulada: normalmente se consultaría la tabla de servicios del cliente
        throw new UnsupportedOperationException("Método getClienteHistory no implementado aún.");
    }

    public void scheduleMaintenanceReminder(int id, java.util.Date date) {
        // Guardar recordatorio en base de datos o enviar notificación
        System.out.println("Recordatorio programado para cliente ID " + id + " en: " + date);
    }

    public double calculateClienteDiscount(int id) {
        // Simulación de puntos de fidelidad: 5% por cada 5 servicios realizados
        int serviciosCompletados = getHistorialPreventivoPorCliente(id).size(); // ejemplo
        return Math.min(serviciosCompletados * 0.05, 0.25); // máximo 25% de descuento
    }

    public void applyLoyaltyProgram(int id) {
        // Aquí podrías registrar la entrega de puntos o aplicar beneficios
        System.out.println("Programa de fidelización aplicado al cliente ID " + id);
    }

    // Método auxiliar simulado (debería estar en ClienteDAO)
    private List<Object> getHistorialPreventivoPorCliente(int id) {
        // Lógica ficticia
        return List.of(new Object(), new Object()); // Ej: 2 servicios
    }
}