/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author sebas
 */
import dao.OrdenCompraDAO;
import dao.RepuestoDAO;
import model.OrdenCompra;
import model.OrdenCompra.DetalleOrden;
import model.OrdenCompra.EstadoOrden;
import model.Proveedor;
import model.Repuesto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class OrdenCompraFrame extends JFrame {
    private OrdenCompraDAO ordenCompraDAO;
    private RepuestoDAO repuestoDAO;
    private JTable tablaOrdenes;
    private DefaultTableModel tableModel;

    public OrdenCompraFrame() {
        ordenCompraDAO = new OrdenCompraDAO();
        repuestoDAO = new RepuestoDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Registro de Órdenes de Compra - CarMotors");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        String[] columnas = {"ID Orden", "Proveedor ID", "Fecha Orden", "Estado"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaOrdenes = new JTable(tableModel);
        cargarOrdenes();

        JScrollPane scrollPane = new JScrollPane(tablaOrdenes);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnEliminar = new JButton("Eliminar Orden");
        JButton btnVerDetalles = new JButton("Ver Detalles");

        btnEliminar.addActionListener(e -> eliminarOrden());
        btnVerDetalles.addActionListener(e -> verDetallesOrden());

        panelBotones.add(btnEliminar);
        panelBotones.add(btnVerDetalles);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }

    private void cargarOrdenes() {
        try {
            tableModel.setRowCount(0);
            List<OrdenCompra> ordenes = ordenCompraDAO.obtenerTodasOrdenes();
            for (OrdenCompra orden : ordenes) {
                Object[] fila = {
                    orden.getIdOrden(),
                    orden.getProveedor().getIdProveedor(),
                    orden.getFechaCreacion(),
                    orden.getEstado()
                };
                tableModel.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar órdenes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarOrden() {
        int filaSeleccionada = tablaOrdenes.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idOrden = (int) tableModel.getValueAt(filaSeleccionada, 0);
            try {
                ordenCompraDAO.eliminarOrdenCompra(idOrden);
                cargarOrdenes();
                JOptionPane.showMessageDialog(this, "Orden eliminada exitosamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar orden: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una orden para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void verDetallesOrden() {
        int filaSeleccionada = tablaOrdenes.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idOrden = (int) tableModel.getValueAt(filaSeleccionada, 0);
            try {
                List<OrdenCompra> ordenes = ordenCompraDAO.obtenerTodasOrdenes();
                OrdenCompra orden = ordenes.stream().filter(o -> o.getIdOrden() == idOrden).findFirst().orElse(null);
                if (orden != null) {
                    JDialog dialog = new JDialog(this, "Detalles de Orden " + idOrden, true);
                    dialog.setSize(800, 400);
                    dialog.setLocationRelativeTo(this);

                    DefaultTableModel detalleModel = new DefaultTableModel(
                        new String[]{"ID Detalle", "ID Repuesto", "Nombre", "Tipo", "Marca", "Modelo", "Cantidad", "Precio Unitario"},
                        0
                    );
                    JTable tablaDetalles = new JTable(detalleModel);
                    for (DetalleOrden detalle : orden.getDetalles()) {
                        Repuesto repuesto = detalle.getRepuesto();
                        detalleModel.addRow(new Object[]{
                            detalle.getIdDetalle(),
                            repuesto.getIdRepuesto(),
                            repuesto.getNombre(),
                            repuesto.getTipo(),
                            repuesto.getMarca(),
                            repuesto.getModelo() != null ? repuesto.getModelo() : "N/A",
                            detalle.getCantidad(),
                            detalle.getPrecioUnitario()
                        });
                    }
                    JScrollPane scrollPane = new JScrollPane(tablaDetalles);
                    dialog.add(scrollPane, BorderLayout.CENTER);
                    dialog.setVisible(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar detalles: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una orden para ver detalles", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}