/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import model.Proveedor;
import dao.ProveedorDAO;
import java.util.List;
import java.sql.SQLException;

/**
 *
 * Autor: Emmanuel
 */
public class SupplierController {
    private ProveedorDAO dao = new ProveedorDAO();

    public List<Proveedor> getAllProveedors() {
        try {
            return dao.obtenerProveedores();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public void addProveedor(Proveedor supplier) {
        try {
            dao.agregarProveedor(supplier);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProveedor(Proveedor supplier) {
        try {
            dao.actualizarProveedor(supplier);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProveedor(int id) {
        try {
            dao.eliminarProveedor(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Métodos no implementados en DAO
    public Proveedor getProveedorById(int id) {
        throw new UnsupportedOperationException("getProveedorById no implementado en ProveedorDAO.");
    }

    public void evaluateProveedor(int id, Object eval) {
        try {
            if (eval instanceof ProveedorDAO.EvaluacionProveedor ev) {
                dao.evaluarProveedor(ev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getProveedorPerformanceReport(int id) {
        try {
            return dao.obtenerReporteDesempeno(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al generar reporte.";
        }
    }
}