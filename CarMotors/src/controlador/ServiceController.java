/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import model.Servicio;
import dao.ServicioDAO;
import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class ServiceController {
    private ServicioDAO dao = new ServicioDAO();

    public List<Servicio> getAllServices() { return dao.obtenerServicios(); }
    public Servicio getServiceById(int serviceId) { return dao.obtenerPorId(serviceId); }
    public void createService(Servicio service) { dao.agregarServicio(service); }
    public void updateService(Servicio service) { dao.actualizarServicio(service); }
    public void updateServiceStatus(int serviceId, String status) { dao.actualizarEstado(serviceId, status); }
    public void assignTechnician(int serviceId, int technicianId) { dao.asignarTecnico(serviceId, technicianId); }
    public void recordUsedParts(int serviceId, List<Part> parts) { dao.registrarRepuestos(serviceId, parts); }
    public void markServiceComplete(int serviceId) { dao.marcarCompleto(serviceId); }
    public List<String> getServiceTypes() { return Arrays.asList("Mecánico", "Eléctrico", "Diagnóstico", "General"); }
}