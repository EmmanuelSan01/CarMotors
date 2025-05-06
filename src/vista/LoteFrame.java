/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author sebas
 */
import dao.LoteDAO;
import model.Lote;
import model.Proveedor;
import model.Repuesto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LoteFrame extends JFrame {
    private LoteDAO loteDAO;
    private JTable tablaLotes;
    private DefaultTableModel tableModel;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LoteFrame() {
        loteDAO = new LoteDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Registro de Lotes - CarMotors");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla de lotes
        String[] columnas = {"ID Lote", "ID Repuesto", "Fecha Ingreso", "ID Proveedor", "Fecha Caducidad"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaLotes = new JTable(tableModel);
        cargarLotes();

        JScrollPane scrollPane = new JScrollPane(tablaLotes);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar Lote");
        JButton btnEditar = new JButton("Editar Lote");
        JButton btnEliminar = new JButton("Eliminar Lote");

        btnAgregar.addActionListener(e -> mostrarFormularioAgregar());
        btnEditar.addActionListener(e -> mostrarFormularioEditar());
        btnEliminar.addActionListener(e -> eliminarLote());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }

    private void cargarLotes() {
        try {
            tableModel.setRowCount(0);
            List<Lote> lotes = loteDAO.obtenerLotes();
            for (Lote lote : lotes) {
                Object[] fila = {
                    lote.getIdLote(),
                    lote.getRepuesto().getIdRepuesto(),
                    lote.getFechaIngreso().format(formatter),
                    lote.getProveedor().getIdProveedor(),
                    lote.getFechaCaducidad() != null ? lote.getFechaCaducidad().format(formatter) : ""
                };
                tableModel.addRow(fila);
            }
            System.out.println("Tabla de lotes actualizada con " + lotes.size() + " elementos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar lotes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error al cargar lotes: " + e.getMessage());
        }
    }

    private void mostrarFormularioAgregar() {
        JDialog dialog = new JDialog(this, "Agregar Lote", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(5, 2, 10, 10));

        JTextField txtIdRepuesto = new JTextField();
        JTextField txtFechaIngreso = new JTextField(LocalDate.now().format(formatter));
        JTextField txtIdProveedor = new JTextField();
        JTextField txtFechaCaducidad = new JTextField();

        dialog.add(new JLabel("ID Repuesto:"));
        dialog.add(txtIdRepuesto);
        dialog.add(new JLabel("Fecha Ingreso (yyyy-MM-dd):"));
        dialog.add(txtFechaIngreso);
        dialog.add(new JLabel("ID Proveedor:"));
        dialog.add(txtIdProveedor);
        dialog.add(new JLabel("Fecha Caducidad (yyyy-MM-dd, opcional):"));
        dialog.add(txtFechaCaducidad);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                // Validar entradas
                if (txtIdRepuesto.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El ID del repuesto es obligatorio.");
                }
                if (txtFechaIngreso.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("La fecha de ingreso es obligatoria.");
                }
                if (txtIdProveedor.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El ID del proveedor es obligatorio.");
                }

                Lote lote = new Lote(
                    0, // ID se genera automáticamente
                    new Repuesto(Integer.parseInt(txtIdRepuesto.getText())),
                    LocalDate.parse(txtFechaIngreso.getText(), formatter),
                    new Proveedor(Integer.parseInt(txtIdProveedor.getText())),
                    txtFechaCaducidad.getText().isEmpty() ? null : LocalDate.parse(txtFechaCaducidad.getText(), formatter)
                );
                loteDAO.agregarLote(lote);
                cargarLotes();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Lote agregado exitosamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos numéricos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error en los datos numéricos: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error en los datos: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar lote: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al agregar lote: " + ex.getMessage());
            }
        });

        dialog.add(btnGuardar);
        dialog.setVisible(true);
    }

    private void mostrarFormularioEditar() {
        int filaSeleccionada = tablaLotes.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idLote = (int) tableModel.getValueAt(filaSeleccionada, 0);
            try {
                JDialog dialog = new JDialog(this, "Editar Lote", true);
                dialog.setSize(400, 300);
                dialog.setLocationRelativeTo(this);
                dialog.setLayout(new GridLayout(5, 2, 10, 10));

                JTextField txtIdRepuesto = new JTextField(tableModel.getValueAt(filaSeleccionada, 1).toString());
                JTextField txtFechaIngreso = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 2));
                JTextField txtIdProveedor = new JTextField(tableModel.getValueAt(filaSeleccionada, 3).toString());
                JTextField txtFechaCaducidad = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 4));

                dialog.add(new JLabel("ID Repuesto:"));
                dialog.add(txtIdRepuesto);
                dialog.add(new JLabel("Fecha Ingreso (yyyy-MM-dd):"));
                dialog.add(txtFechaIngreso);
                dialog.add(new JLabel("ID Proveedor:"));
                dialog.add(txtIdProveedor);
                dialog.add(new JLabel("Fecha Caducidad (yyyy-MM-dd, opcional):"));
                dialog.add(txtFechaCaducidad);

                JButton btnGuardar = new JButton("Guardar");
                btnGuardar.addActionListener(e -> {
                    try {
                        // Validar entradas
                        if (txtIdRepuesto.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El ID del repuesto es obligatorio.");
                        }
                        if (txtFechaIngreso.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("La fecha de ingreso es obligatoria.");
                        }
                        if (txtIdProveedor.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El ID del proveedor es obligatorio.");
                        }

                        Lote lote = new Lote(
                            idLote,
                            new Repuesto(Integer.parseInt(txtIdRepuesto.getText())),
                            LocalDate.parse(txtFechaIngreso.getText(), formatter),
                            new Proveedor(Integer.parseInt(txtIdProveedor.getText())),
                            txtFechaCaducidad.getText().isEmpty() ? null : LocalDate.parse(txtFechaCaducidad.getText(), formatter)
                        );
                        loteDAO.actualizarLote(lote);
                        cargarLotes();
                        dialog.dispose();
                        JOptionPane.showMessageDialog(this, "Lote actualizado exitosamente");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Error en los datos numéricos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error en los datos numéricos: " + ex.getMessage());
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error en los datos: " + ex.getMessage());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error al actualizar lote: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error al actualizar lote: " + ex.getMessage());
                    }
                });

                dialog.add(btnGuardar);
                dialog.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar lote: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al cargar lote: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un lote para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarLote() {
        int filaSeleccionada = tablaLotes.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idLote = (int) tableModel.getValueAt(filaSeleccionada, 0);
            try {
                loteDAO.eliminarLote(idLote);
                cargarLotes();
                JOptionPane.showMessageDialog(this, "Lote eliminado exitosamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar lote: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al eliminar lote: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un lote para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}