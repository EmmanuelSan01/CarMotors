/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import model.Servicio;
import dao.ServicioDAO;

import java.sql.SQLException;
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

    // Métodos no implementados en DAO: se marcan con advertencia
    public Servicio getServiceById(int serviceId) {
        throw new UnsupportedOperationException("Método getServiceById no implementado en ServicioDAO.");
    }

    public void updateServiceStatus(int serviceId, String status) {
        throw new UnsupportedOperationException("Método updateServiceStatus no implementado en ServicioDAO.");
    }

    public void assignTechnician(int serviceId, int technicianId) {
        throw new UnsupportedOperationException("Método assignTechnician no implementado en ServicioDAO.");
    }

    public void recordUsedParts(int serviceId, List<Object> parts) {
        throw new UnsupportedOperationException("Método recordUsedParts no implementado en ServicioDAO.");
    }

    public void markServiceComplete(int serviceId) {
        throw new UnsupportedOperationException("Método markServiceComplete no implementado en ServicioDAO.");
    }

    public List<String> getServiceTypes() {
        return Arrays.asList("Mecánico", "Eléctrico", "Diagnóstico", "General");
    }
}