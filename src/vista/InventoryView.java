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
import java.sql.*;

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

        // Tabla
        tableModel = new DefaultTableModel(new String[]{
                "ID", "Nombre", "Tipo", "Marca", "Modelo", "Cantidad", "Estado"
        }, 0);

        tablaInventario = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tablaInventario);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botón de agregar
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> showAddRepuestoForm());
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        // Panel superior con filtros
        JPanel filtros = new JPanel();

        JTextField txtTipo = new JTextField(10);
        JButton btnFiltroTipo = new JButton("Filtrar por Tipo");
        btnFiltroTipo.addActionListener(e -> filterInventoryByType(txtTipo.getText()));

        JTextField txtMarca = new JTextField(10);
        JButton btnFiltroMarca = new JButton("Filtrar por Marca");
        btnFiltroMarca.addActionListener(e -> filterInventoryByBrand(txtMarca.getText()));

        JTextField txtEstado = new JTextField(10);
        JButton btnFiltroEstado = new JButton("Filtrar por Estado");
        btnFiltroEstado.addActionListener(e -> filterInventoryByStatus(txtEstado.getText()));

        filtros.add(new JLabel("Tipo:")); filtros.add(txtTipo); filtros.add(btnFiltroTipo);
        filtros.add(new JLabel("Marca:")); filtros.add(txtMarca); filtros.add(btnFiltroMarca);
        filtros.add(new JLabel("Estado:")); filtros.add(txtEstado); filtros.add(btnFiltroEstado);

        panel.add(filtros, BorderLayout.NORTH);

        add(panel);
        showInventoryList();
    }

public void showInventoryList() {
    try {
        List<Repuesto> repuestos = repuestoDAO.obtenerRepuestos();
        updateTable(repuestos);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al obtener repuestos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                    repuesto.getIdRepuesto(),
                    repuesto.getNombre(),
                    repuesto.getTipo(),
                    repuesto.getMarca(),
                    repuesto.getModelo(),
                    repuesto.getCantidadStock(),
                    repuesto.getEstado()
            });
        }
    }

    public void showAddRepuestoForm() {
        JOptionPane.showMessageDialog(this, "Formulario para agregar repuesto (implementación pendiente)");
    }

    public void showEditRepuestoForm(Repuesto repuesto) {
        JOptionPane.showMessageDialog(this, "Formulario para editar repuesto: " + repuesto.getNombre());
    }

    public void refreshInventoryList() {
        showInventoryList();
    }
}