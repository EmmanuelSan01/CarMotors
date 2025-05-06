/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.util.Map;

/**
 *
 * @author Emmanuel
 */
public class ReportView extends JFrame {

    public void initialize() {
        setTitle("Reportes Generales");
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void showInventoryReportOptions() {
        JOptionPane.showMessageDialog(this, "Opciones de reporte de inventario (pendiente)");
    }

    public void showServiceReportOptions() {
        JOptionPane.showMessageDialog(this, "Opciones de reporte de servicios (pendiente)");
    }

    public void showClientReportOptions() {
        JOptionPane.showMessageDialog(this, "Opciones de reporte de clientes (pendiente)");
    }

    public void showSupplierReportOptions() {
        JOptionPane.showMessageDialog(this, "Opciones de reporte de proveedores (pendiente)");
    }

    public void showCampaignReportOptions() {
        JOptionPane.showMessageDialog(this, "Opciones de reporte de campañas (pendiente)");
    }

    public void generateReport(String reportType, Map<String, Object> parameters) {
        JOptionPane.showMessageDialog(this, "Generando reporte de tipo: " + reportType);
    }
}