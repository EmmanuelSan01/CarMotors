/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import model.Repuesto;
import dao.RepuestoDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Emmanuel
 */
public class InventoryController {
    private RepuestoDAO dao = new RepuestoDAO();

    public List<Repuesto> getAllRepuestos() {
        try {
            return dao.obtenerRepuestos();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // Filtrado localmente desde la lista de repuestos obtenidos
    public List<Repuesto> getRepuestosByType(String type) {
        return getAllRepuestos().stream()
                .filter(r -> r.getTipo().name().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<Repuesto> getRepuestosByBrand(String brand) {
        return getAllRepuestos().stream()
                .filter(r -> r.getMarca() != null && r.getMarca().name().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    public List<Repuesto> getRepuestosByStatus(String status) {
        return getAllRepuestos().stream()
                .filter(r -> r.getEstado().name().replace("_", " ").equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    public void addRepuesto(Repuesto part) {
        try {
            dao.agregarRepuesto(part);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRepuesto(Repuesto part) {
        try {
            dao.actualizarRepuesto(part);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRepuesto(int partId) {
        try {
            dao.eliminarRepuesto(partId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Repuesto> checkLowStockItems() {
        return getAllRepuestos().stream()
                .filter(p -> p.getCantidadStock() <= p.getNivelMinimo())
                .collect(Collectors.toList());
    }

    // Este método requiere implementar un método "reducirStock" en el DAO si se desea mantener
    public void updateStockAfterServiceUse(List<Repuesto> usedRepuestos) {
        System.err.println("Método updateStockAfterServiceUse no implementado completamente: falta lógica DAO.");
        // Aquí puedes implementar lógica específica si decides agregar una función en el DAO.
    }
}