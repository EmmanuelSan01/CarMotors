/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import model.Proveedor;
import dao.ProveedorDAO;
import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class SupplierController {
    private ProveedorDAO dao = new ProveedorDAO();

    public List<Proveedor> getAllProveedors() { return dao.getAll(); }
    public Proveedor getProveedorById(int id) { return dao.getById(id); }
    public void addProveedor(Proveedor supplier) { dao.insert(supplier); }
    public void updateProveedor(Proveedor supplier) { dao.update(supplier); }
    public void deleteProveedor(int id) { dao.delete(id); }
    public void evaluateProveedor(int id, ProveedorEvaluation eval) { dao.evaluate(id, eval); }
    public Report getProveedorPerformanceReport(int id) { return dao.getPerformanceReport(id); }
}