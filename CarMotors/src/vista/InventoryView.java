/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import model.Repuesto;
import dao.RepuestoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class InventoryView extends JFrame {
    private JTable tablaInventario;
    private DefaultTableModel tableModel;
    private RepuestoDAO repuestoDAO;

    public InventoryView() {
        setTitle("Inventario de Repuestos");
        setSize(900, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        repuestoDAO = new RepuestoDAO();

        JPanel panel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{
            "ID", "Nombre", "Tipo", "Marca", "Modelo", "Cantidad", "Estado"
        }, 0);

        tablaInventario = new JTable(tableModel);
        panel.add(new JScrollPane(tablaInventario), BorderLayout.CENTER);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> showAddRepuestoForm());

        JPanel botones = new JPanel();
        botones.add(btnAgregar);

        panel.add(botones, BorderLayout.SOUTH);
        add(panel);
        showInventoryList();
    }

    public void showInventoryList() {
        List<Repuesto> repuestoes = repuestoDAO.getAllRepuestos();
        tableModel.setRowCount(0);
        for (Repuesto repuesto : repuestoes) {
            tableModel.addRow(new Object[]{
                repuesto.getId(), repuesto.getNombre(), repuesto.getTipo(),
                repuesto.getMarca(), repuesto.getModelo(),
                repuesto.getCantidad(), repuesto.getEstado()
            });
        }
    }

    public void filterInventoryByType(String type) {
        List<Repuesto> filtrado = repuestoDAO.filterByType(type);
        updateTable(filtrado);
    }

    public void filterInventoryByBrand(String brand) {
        List<Repuesto> filtrado = repuestoDAO.filterByBrand(brand);
        updateTable(filtrado);
    }

    public void filterInventoryByStatus(String status) {
        List<Repuesto> filtrado = repuestoDAO.filterByStatus(status);
        updateTable(filtrado);
    }

    private void updateTable(List<Repuesto> data) {
        tableModel.setRowCount(0);
        for (Repuesto repuesto : data) {
            tableModel.addRow(new Object[]{
                repuesto.getId(), repuesto.getNombre(), repuesto.getTipo(),
                repuesto.getMarca(), repuesto.getModelo(),
                repuesto.getCantidad(), repuesto.getEstado()
            });
        }
    }

    public void showAddRepuestoForm() {
        JOptionPane.showMessageDialog(this, "Formulario para agregar repuesto (implementación pendiente)");
    }

    public void showEditRepuestoForm(Repuesto repuesto) {
        JOptionPane.showMessageDialog(this, "Formulario para editar repuesto (implementación pendiente)");
    }

    public void refreshInventoryList() {
        showInventoryList();
    }
}