/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.FacturaDAO;
import dao.ServicioDAO;
import model.Factura;
import model.Servicio;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class FacturaController {

    private FacturaDAO facturaDAO = new FacturaDAO();
    private ServicioDAO servicioDAO = new ServicioDAO();

    public List<Factura> listarFacturas() {
        try {
            return facturaDAO.obtenerFacturas();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void crearFactura(int idServicio) {
        try {
            Servicio servicio = servicioDAO.obtenerServicios().stream()
                    .filter(s -> s.getIdServicio() == idServicio)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

            double total = servicio.getCostoManoObra() * 1.19;
            Factura factura = new Factura(0, idServicio, LocalDateTime.now(), total);
            facturaDAO.agregarFactura(factura);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}