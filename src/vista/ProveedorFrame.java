/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author sebas
 */
import dao.ProveedorDAO;
import model.Proveedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProveedorFrame extends JFrame {
    private ProveedorDAO proveedorDAO;
    private JTable tablaProveedores;
    private DefaultTableModel tableModel;

    public ProveedorFrame() {
        proveedorDAO = new ProveedorDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Registro de Proveedores - CarMotors");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla de proveedores
        String[] columnas = {"ID Proveedor", "Nombre", "NIT", "Contacto", "Frecuencia Visita"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaProveedores = new JTable(tableModel);
        cargarProveedores();

        JScrollPane scrollPane = new JScrollPane(tablaProveedores);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar Proveedor");
        JButton btnEditar = new JButton("Editar Proveedor");
        JButton btnEliminar = new JButton("Eliminar Proveedor");

        btnAgregar.addActionListener(e -> mostrarFormularioAgregar());
        btnEditar.addActionListener(e -> mostrarFormularioEditar());
        btnEliminar.addActionListener(e -> eliminarProveedor());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }

    private void cargarProveedores() {
        try {
            tableModel.setRowCount(0);
            List<Proveedor> proveedores = proveedorDAO.obtenerProveedores();
            for (Proveedor proveedor : proveedores) {
                Object[] fila = {
                    proveedor.getIdProveedor(),
                    proveedor.getNombre(),
                    proveedor.getNit(),
                    proveedor.getContacto(),
                    proveedor.getFrecuenciaVisita()
                };
                tableModel.addRow(fila);
            }
            System.out.println("Tabla de proveedores actualizada con " + proveedores.size() + " elementos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error al cargar proveedores: " + e.getMessage());
        }
    }

    private void mostrarFormularioAgregar() {
        JDialog dialog = new JDialog(this, "Agregar Proveedor", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));

        JTextField txtNombre = new JTextField();
        JTextField txtNit = new JTextField();
        JTextField txtContacto = new JTextField();
        JTextField txtFrecuenciaVisita = new JTextField();

        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("NIT:"));
        dialog.add(txtNit);
        dialog.add(new JLabel("Contacto:"));
        dialog.add(txtContacto);
        dialog.add(new JLabel("Frecuencia Visita (días):"));
        dialog.add(txtFrecuenciaVisita);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        btnGuardar.addActionListener(e -> {
            try {
                if (txtNombre.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El nombre es obligatorio.");
                }
                if (txtNit.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El NIT es obligatorio.");
                }
                if (txtFrecuenciaVisita.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("La frecuencia de visita es obligatoria.");
                }
                Proveedor proveedor = new Proveedor(
                    0, // ID se genera automáticamente
                    txtNombre.getText(),
                    txtNit.getText(),
                    txtContacto.getText(),
                    Integer.parseInt(txtFrecuenciaVisita.getText())
                );
                proveedorDAO.agregarProveedor(proveedor);
                cargarProveedores();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Proveedor agregado exitosamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La frecuencia de visita debe ser un número: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error en los datos numéricos: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error en los datos: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al agregar proveedor: " + ex.getMessage());
            }
        });
        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.add(btnGuardar);
        dialog.add(btnCancelar);
        dialog.setVisible(true);
    }

    private void mostrarFormularioEditar() {
        int filaSeleccionada = tablaProveedores.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idProveedor = (int) tableModel.getValueAt(filaSeleccionada, 0);
            try {
                JDialog dialog = new JDialog(this, "Editar Proveedor", true);
                dialog.setSize(400, 300);
                dialog.setLocationRelativeTo(this);
                dialog.setLayout(new GridLayout(6, 2, 10, 10));

                JTextField txtNombre = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 1));
                JTextField txtNit = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 2));
                JTextField txtContacto = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 3));
                JTextField txtFrecuenciaVisita = new JTextField(tableModel.getValueAt(filaSeleccionada, 4).toString());

                dialog.add(new JLabel("Nombre:"));
                dialog.add(txtNombre);
                dialog.add(new JLabel("NIT:"));
                dialog.add(txtNit);
                dialog.add(new JLabel("Contacto:"));
                dialog.add(txtContacto);
                dialog.add(new JLabel("Frecuencia Visita (días):"));
                dialog.add(txtFrecuenciaVisita);

                JButton btnGuardar = new JButton("Guardar");
                JButton btnCancelar = new JButton("Cancelar");
                btnGuardar.addActionListener(e -> {
                    try {
                        if (txtNombre.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El nombre es obligatorio.");
                        }
                        if (txtNit.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El NIT es obligatorio.");
                        }
                        if (txtFrecuenciaVisita.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("La frecuencia de visita es obligatoria.");
                        }
                        Proveedor proveedor = new Proveedor(
                            idProveedor,
                            txtNombre.getText(),
                            txtNit.getText(),
                            txtContacto.getText(),
                            Integer.parseInt(txtFrecuenciaVisita.getText())
                        );
                        proveedorDAO.actualizarProveedor(proveedor);
                        cargarProveedores();
                        dialog.dispose();
                        JOptionPane.showMessageDialog(this, "Proveedor actualizado exitosamente");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "La frecuencia de visita debe ser un número: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error en los datos numéricos: " + ex.getMessage());
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error en los datos: " + ex.getMessage());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error al actualizar proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error al actualizar proveedor: " + ex.getMessage());
                    }
                });
                btnCancelar.addActionListener(e -> dialog.dispose());

                dialog.add(btnGuardar);
                dialog.add(btnCancelar);
                dialog.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar proveedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al cargar proveedor: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarProveedor() {
        int filaSeleccionada = tablaProveedores.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idProveedor = (int) tableModel.getValueAt(filaSeleccionada, 0);
            int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de que desea eliminar este proveedor?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    proveedorDAO.eliminarProveedor(idProveedor);
                    cargarProveedores();
                    JOptionPane.showMessageDialog(this, "Proveedor eliminado exitosamente");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar proveedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    System.err.println("Error al eliminar proveedor: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}