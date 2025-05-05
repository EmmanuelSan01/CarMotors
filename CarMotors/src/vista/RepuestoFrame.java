/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author sebas
 */
import dao.RepuestoDAO;
import model.Proveedor;
import model.Repuesto;
import model.Repuesto.EstadoRepuesto;
import model.Repuesto.MarcaVehiculo;
import model.Repuesto.TipoRepuesto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class RepuestoFrame extends JFrame {
    private RepuestoDAO repuestoDAO;
    private JTable tablaRepuestos;
    private DefaultTableModel tableModel;

    public RepuestoFrame() {
        repuestoDAO = new RepuestoDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Registro de Repuestos - CarMotors");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla de repuestos
        String[] columnas = {"ID", "Nombre", "Tipo", "Marca", "Modelo", "Stock", "Nivel Mínimo", "Estado"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaRepuestos = new JTable(tableModel);
        cargarRepuestos();

        JScrollPane scrollPane = new JScrollPane(tablaRepuestos);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar Repuesto");
        JButton btnEditar = new JButton("Editar Repuesto");
        JButton btnEliminar = new JButton("Eliminar Repuesto");

        btnAgregar.addActionListener(e -> mostrarFormularioAgregar());
        btnEditar.addActionListener(e -> mostrarFormularioEditar());
        btnEliminar.addActionListener(e -> eliminarRepuesto());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }

    private void cargarRepuestos() {
        try {
            tableModel.setRowCount(0);
            List<Repuesto> repuestos = repuestoDAO.obtenerRepuestos();
            for (Repuesto rep : repuestos) {
                Object[] fila = {
                    rep.getIdRepuesto(),
                    rep.getNombre(),
                    rep.getTipo(),
                    rep.getMarca(),
                    rep.getModelo(),
                    rep.getCantidadStock(),
                    rep.getNivelMinimo(),
                    rep.getEstado()
                };
                tableModel.addRow(fila);
            }
            System.out.println("Tabla de repuestos actualizada con " + repuestos.size() + " elementos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar repuestos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error al cargar repuestos: " + e.getMessage());
        }
    }

    private void mostrarFormularioAgregar() {
        JDialog dialog = new JDialog(this, "Agregar Repuesto", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(10, 2, 10, 10));

        JTextField txtNombre = new JTextField();
        JComboBox<TipoRepuesto> cmbTipo = new JComboBox<>(TipoRepuesto.values());
        JComboBox<MarcaVehiculo> cmbMarca = new JComboBox<>(MarcaVehiculo.values());
        JTextField txtModelo = new JTextField();
        JTextField txtProveedorId = new JTextField();
        JTextField txtStock = new JTextField();
        JTextField txtNivelMinimo = new JTextField();
        JTextField txtVidaUtil = new JTextField();
        JComboBox<EstadoRepuesto> cmbEstado = new JComboBox<>(EstadoRepuesto.values());

        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("Tipo:"));
        dialog.add(cmbTipo);
        dialog.add(new JLabel("Marca:"));
        dialog.add(cmbMarca);
        dialog.add(new JLabel("Modelo:"));
        dialog.add(txtModelo);
        dialog.add(new JLabel("ID Proveedor:"));
        dialog.add(txtProveedorId);
        dialog.add(new JLabel("Stock:"));
        dialog.add(txtStock);
        dialog.add(new JLabel("Nivel Mínimo:"));
        dialog.add(txtNivelMinimo);
        dialog.add(new JLabel("Vida Útil (meses):"));
        dialog.add(txtVidaUtil);
        dialog.add(new JLabel("Estado:"));
        dialog.add(cmbEstado);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                // Validar entradas
                if (txtNombre.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El nombre del repuesto es obligatorio.");
                }
                if (txtStock.getText().trim().isEmpty() || Integer.parseInt(txtStock.getText()) < 0) {
                    throw new IllegalArgumentException("El stock debe ser un número no negativo.");
                }
                if (txtNivelMinimo.getText().trim().isEmpty() || Integer.parseInt(txtNivelMinimo.getText()) < 0) {
                    throw new IllegalArgumentException("El nivel mínimo debe ser un número no negativo.");
                }
                if (txtVidaUtil.getText().trim().isEmpty() || Integer.parseInt(txtVidaUtil.getText()) <= 0) {
                    throw new IllegalArgumentException("La vida útil debe ser un número positivo.");
                }
                if (txtProveedorId.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El ID del proveedor es obligatorio.");
                }

                Repuesto repuesto = new Repuesto(
                    0, // ID se genera automáticamente
                    txtNombre.getText(),
                    (TipoRepuesto) cmbTipo.getSelectedItem(),
                    (MarcaVehiculo) cmbMarca.getSelectedItem(),
                    txtModelo.getText().isEmpty() ? null : Integer.parseInt(txtModelo.getText()),
                    new Proveedor(Integer.parseInt(txtProveedorId.getText())),
                    Integer.parseInt(txtStock.getText()),
                    Integer.parseInt(txtNivelMinimo.getText()),
                    LocalDate.now(),
                    Integer.parseInt(txtVidaUtil.getText()),
                    (EstadoRepuesto) cmbEstado.getSelectedItem()
                );
                repuestoDAO.agregarRepuesto(repuesto);
                cargarRepuestos();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Repuesto agregado exitosamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos numéricos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error en los datos numéricos: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error en los datos: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar repuesto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al agregar repuesto: " + ex.getMessage());
            }
        });

        dialog.add(btnGuardar);
        dialog.setVisible(true);
    }

    private void mostrarFormularioEditar() {
        int filaSeleccionada = tablaRepuestos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idRepuesto = (int) tableModel.getValueAt(filaSeleccionada, 0);
            try {
                JDialog dialog = new JDialog(this, "Editar Repuesto", true);
                dialog.setSize(400, 500);
                dialog.setLocationRelativeTo(this);
                dialog.setLayout(new GridLayout(10, 2, 10, 10));

                JTextField txtNombre = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 1));
                JComboBox<TipoRepuesto> cmbTipo = new JComboBox<>(TipoRepuesto.values());
                cmbTipo.setSelectedItem(tableModel.getValueAt(filaSeleccionada, 2));
                JComboBox<MarcaVehiculo> cmbMarca = new JComboBox<>(MarcaVehiculo.values());
                cmbMarca.setSelectedItem(tableModel.getValueAt(filaSeleccionada, 3));
                JTextField txtModelo = new JTextField(tableModel.getValueAt(filaSeleccionada, 4) != null ? tableModel.getValueAt(filaSeleccionada, 4).toString() : "");
                JTextField txtProveedorId = new JTextField("1"); // Simplificado
                JTextField txtStock = new JTextField(tableModel.getValueAt(filaSeleccionada, 5).toString());
                JTextField txtNivelMinimo = new JTextField(tableModel.getValueAt(filaSeleccionada, 6).toString());
                JTextField txtVidaUtil = new JTextField("12"); // Simplificado
                JComboBox<EstadoRepuesto> cmbEstado = new JComboBox<>(EstadoRepuesto.values());
                cmbEstado.setSelectedItem(tableModel.getValueAt(filaSeleccionada, 7));

                dialog.add(new JLabel("Nombre:"));
                dialog.add(txtNombre);
                dialog.add(new JLabel("Tipo:"));
                dialog.add(cmbTipo);
                dialog.add(new JLabel("Marca:"));
                dialog.add(cmbMarca);
                dialog.add(new JLabel("Modelo:"));
                dialog.add(txtModelo);
                dialog.add(new JLabel("ID Proveedor:"));
                dialog.add(txtProveedorId);
                dialog.add(new JLabel("Stock:"));
                dialog.add(txtStock);
                dialog.add(new JLabel("Nivel Mínimo:"));
                dialog.add(txtNivelMinimo);
                dialog.add(new JLabel("Vida Útil (meses):"));
                dialog.add(txtVidaUtil);
                dialog.add(new JLabel("Estado:"));
                dialog.add(cmbEstado);

                JButton btnGuardar = new JButton("Guardar");
                btnGuardar.addActionListener(e -> {
                    try {
                        // Validar entradas
                        if (txtNombre.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El nombre del repuesto es obligatorio.");
                        }
                        if (txtStock.getText().trim().isEmpty() || Integer.parseInt(txtStock.getText()) < 0) {
                            throw new IllegalArgumentException("El stock debe ser un número no negativo.");
                        }
                        if (txtNivelMinimo.getText().trim().isEmpty() || Integer.parseInt(txtNivelMinimo.getText()) < 0) {
                            throw new IllegalArgumentException("El nivel mínimo debe ser un número no negativo.");
                        }
                        if (txtVidaUtil.getText().trim().isEmpty() || Integer.parseInt(txtVidaUtil.getText()) <= 0) {
                            throw new IllegalArgumentException("La vida útil debe ser un número positivo.");
                        }
                        if (txtProveedorId.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El ID del proveedor es obligatorio.");
                        }

                        Repuesto repuesto = new Repuesto(
                            idRepuesto,
                            txtNombre.getText(),
                            (TipoRepuesto) cmbTipo.getSelectedItem(),
                            (MarcaVehiculo) cmbMarca.getSelectedItem(),
                            txtModelo.getText().isEmpty() ? null : Integer.parseInt(txtModelo.getText()),
                            new Proveedor(Integer.parseInt(txtProveedorId.getText())),
                            Integer.parseInt(txtStock.getText()),
                            Integer.parseInt(txtNivelMinimo.getText()),
                            LocalDate.now(),
                            Integer.parseInt(txtVidaUtil.getText()),
                            (EstadoRepuesto) cmbEstado.getSelectedItem()
                        );
                        repuestoDAO.actualizarRepuesto(repuesto);
                        cargarRepuestos();
                        dialog.dispose();
                        JOptionPane.showMessageDialog(this, "Repuesto actualizado exitosamente");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Error en los datos numéricos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error en los datos numéricos: " + ex.getMessage());
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error en los datos: " + ex.getMessage());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error al actualizar repuesto: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error al actualizar repuesto: " + ex.getMessage());
                    }
                });

                dialog.add(btnGuardar);
                dialog.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar repuesto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al cargar repuesto: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un repuesto para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarRepuesto() {
        int filaSeleccionada = tablaRepuestos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idRepuesto = (int) tableModel.getValueAt(filaSeleccionada, 0);
            try {
                repuestoDAO.eliminarRepuesto(idRepuesto);
                cargarRepuestos();
                JOptionPane.showMessageDialog(this, "Repuesto eliminado exitosamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar repuesto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al eliminar repuesto: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un repuesto para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}