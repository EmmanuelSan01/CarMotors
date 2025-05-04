/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import model.Repuesto;
import dao.RepuestoDAO;

import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class InventoryController {
    private RepuestoDAO dao = new RepuestoDAO();

    public List<Repuesto> getAllRepuestos() { return dao.getAllRepuestos(); }
    public List<Repuesto> getRepuestosByType(String type) { return dao.filterByType(type); }
    public List<Repuesto> getRepuestosByBrand(String brand) { return dao.filterByBrand(brand); }
    public List<Repuesto> getRepuestosByStatus(String status) { return dao.filterByStatus(status); }
    public void addRepuesto(Repuesto part) { dao.insert(part); }
    public void updateRepuesto(Repuesto part) { dao.update(part); }
    public void deleteRepuesto(int partId) { dao.delete(partId); }
    public List<Repuesto> checkLowStockItems() {
        return dao.getAllRepuestos().stream().filter(p -> p.getCantidad() <= p.getNivelMinimo()).collect(Collectors.toList());
    }
    public void updateStockAfterServiceUse(List<Repuesto> usedRepuestos) {
        for (Repuesto p : usedRepuestos) dao.reduceStock(p.getId(), p.getCantidad());
    }
}