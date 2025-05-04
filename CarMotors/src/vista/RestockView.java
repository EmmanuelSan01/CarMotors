/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.InventoryController;
import controlador.RestockController;
import model.Repuesto;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Vista para la gestión de reabastecimientos.
 * Incluye generación y seguimiento de órdenes de compra.
 */
public class RestockView extends JFrame {
    private JTable tablaOrdenes;
    private DefaultTableModel ordenesModel;
    private RestockController restockController;
    private InventoryController inventoryController;

    public RestockView() {
        setTitle("Gestión de Reabastecimientos");
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        restockController = new RestockController();
        inventoryController = new InventoryController();

        // Tabla de órdenes
        ordenesModel = new DefaultTableModel(new String[]{"ID", "Proveedor", "Fecha", "Estado"}, 0);
        tablaOrdenes = new JTable(ordenesModel);
        add(new JScrollPane(tablaOrdenes), BorderLayout.CENTER);

        // Botones
        JButton btnGenerarOrden = new JButton("Generar Orden");
        btnGenerarOrden.addActionListener(e -> generarOrden());

        JButton btnActualizarEstado = new JButton("Actualizar Estado");
        btnActualizarEstado.addActionListener(e -> actualizarEstado());

        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> cargarOrdenes());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGenerarOrden);
        panelBotones.add(btnActualizarEstado);
        panelBotones.add(btnRefrescar);

        add(panelBotones, BorderLayout.SOUTH);

        cargarOrdenes();
    }

    private void cargarOrdenes() {
        ordenesModel.setRowCount(0);
        List<Order> ordenes = restockController.trackPendingOrders(); // puedes adaptar para todos los estados
        for (Order o : ordenes) {
            ordenesModel.addRow(new Object[]{
                    o.getIdOrden(), o.getIdProveedor(), o.getFechaOrden(), o.getEstado()
            });
        }
    }

    private void generarOrden() {
        try {
            String input = JOptionPane.showInputDialog(this, "ID del proveedor:");
            if (input == null) return;
            int idProveedor = Integer.parseInt(input);

            List<Repuesto> bajoStock = inventoryController.checkLowStockItems();
            if (bajoStock.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay repuestos con stock bajo.");
                return;
            }

            restockController.generatePurchaseOrder(idProveedor, bajoStock);
            JOptionPane.showMessageDialog(this, "Orden generada correctamente.");
            cargarOrdenes();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar orden: " + ex.getMessage());
        }
    }

    private void actualizarEstado() {
        int fila = tablaOrdenes.getSelectedRow();
        if (fila >= 0) {
            int id = (int) tablaOrdenes.getValueAt(fila, 0);
            String nuevoEstado = JOptionPane.showInputDialog(this, "Nuevo estado (Pendiente, Enviado, Recibido, Cancelado):");
            if (nuevoEstado == null || nuevoEstado.isBlank()) return;

            Order orden = new Order();
            orden.setIdOrden(id);
            restockController.updateOrderStatus(orden, nuevoEstado);
            cargarOrdenes();
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una orden para actualizar.");
        }
    }
}