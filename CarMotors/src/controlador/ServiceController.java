/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DatabaseConnection.DatabaseConnection;
import model.Servicio;
import dao.ServicioDAO;

import java.sql.*;
import java.util.List;
import java.util.Arrays;

/**
 *
 * @author Emmanuel
 */
public class ServiceController {

    private ServicioDAO dao = new ServicioDAO();

    public List<Servicio> getAllServices() {
        try {
            return dao.obtenerServicios();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of(); // Retorna lista vacía en caso de error
        }
    }

    public void createService(Servicio service) {
        try {
            dao.agregarServicio(service);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateService(Servicio service) {
        try {
            dao.actualizarServicio(service);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteService(int serviceId) {
        try {
            dao.eliminarServicio(serviceId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Servicio getServiceById(int serviceId) {
        return getAllServices().stream()
                .filter(s -> s.getIdServicio() == serviceId)
                .findFirst()
                .orElse(null);
    }

    public void updateServiceStatus(int serviceId, String status) {
        try {
            Servicio servicio = getServiceById(serviceId);
            if (servicio != null) {
                servicio.setEstado(Servicio.EstadoServicio.valueOf(status.toUpperCase()));
                if (status.equalsIgnoreCase("COMPLETADO")) {
                    servicio.setFechaFin(java.time.LocalDate.now());
                }
                dao.actualizarServicio(servicio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void assignTechnician(int serviceId, int technicianId) {
        try {
            Servicio servicio = getServiceById(serviceId);
            if (servicio != null) {
                servicio.setIdTecnico(technicianId);
                dao.actualizarServicio(servicio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markServiceComplete(int serviceId) {
        updateServiceStatus(serviceId, "COMPLETADO");
    }

    public String getNombreTecnico(int idTecnico) {
        String sql = "SELECT nombre FROM tecnico WHERE id_tecnico = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTecnico);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "—";
    }
}