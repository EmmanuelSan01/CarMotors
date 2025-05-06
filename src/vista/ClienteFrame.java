/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author sebas
 */
import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteFrame extends JFrame {
    private ClienteDAO clienteDAO;
    private JTable tablaClientes;
    private DefaultTableModel tableModel;

    public ClienteFrame() {
        clienteDAO = new ClienteDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Registro de Clientes - CarMotors");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla de clientes
        String[] columnas = {"ID Cliente", "Nombre", "Identificación", "Teléfono", "Correo"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(tableModel);
        cargarClientes();

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar Cliente");
        JButton btnEditar = new JButton("Editar Cliente");
        JButton btnEliminar = new JButton("Eliminar Cliente");

        btnAgregar.addActionListener(e -> mostrarFormularioAgregar());
        btnEditar.addActionListener(e -> mostrarFormularioEditar());
        btnEliminar.addActionListener(e -> eliminarCliente());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }

    private void cargarClientes() {
        try {
            tableModel.setRowCount(0);
            List<Cliente> clientes = clienteDAO.obtenerClientes();
            for (Cliente cliente : clientes) {
                Object[] fila = {
                    cliente.getIdCliente(),
                    cliente.getNombre(),
                    cliente.getIdentificacion(),
                    cliente.getTelefono(),
                    cliente.getCorreo()
                };
                tableModel.addRow(fila);
            }
            System.out.println("Tabla de clientes actualizada con " + clientes.size() + " elementos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error al cargar clientes: " + e.getMessage());
        }
    }

    private void mostrarFormularioAgregar() {
        JDialog dialog = new JDialog(this, "Agregar Cliente", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));

        JTextField txtNombre = new JTextField();
        JTextField txtIdentificacion = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtCorreo = new JTextField();

        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("Identificación:"));
        dialog.add(txtIdentificacion);
        dialog.add(new JLabel("Teléfono:"));
        dialog.add(txtTelefono);
        dialog.add(new JLabel("Correo:"));
        dialog.add(txtCorreo);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        btnGuardar.addActionListener(e -> {
            try {
                if (txtNombre.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El nombre es obligatorio.");
                }
                if (txtIdentificacion.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("La identificación es obligatoria.");
                }
                Cliente cliente = new Cliente(
                    0, // ID se genera automáticamente
                    txtNombre.getText(),
                    txtIdentificacion.getText(),
                    txtTelefono.getText(),
                    txtCorreo.getText()
                );
                clienteDAO.agregarCliente(cliente);
                cargarClientes();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Cliente agregado exitosamente");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error en los datos: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al agregar cliente: " + ex.getMessage());
            }
        });
        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.add(btnGuardar);
        dialog.add(btnCancelar);
        dialog.setVisible(true);
    }

    private void mostrarFormularioEditar() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idCliente = (int) tableModel.getValueAt(filaSeleccionada, 0);
            try {
                JDialog dialog = new JDialog(this, "Editar Cliente", true);
                dialog.setSize(400, 300);
                dialog.setLocationRelativeTo(this);
                dialog.setLayout(new GridLayout(6, 2, 10, 10));

                JTextField txtNombre = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 1));
                JTextField txtIdentificacion = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 2));
                JTextField txtTelefono = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 3));
                JTextField txtCorreo = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 4));

                dialog.add(new JLabel("Nombre:"));
                dialog.add(txtNombre);
                dialog.add(new JLabel("Identificación:"));
                dialog.add(txtIdentificacion);
                dialog.add(new JLabel("Teléfono:"));
                dialog.add(txtTelefono);
                dialog.add(new JLabel("Correo:"));
                dialog.add(txtCorreo);

                JButton btnGuardar = new JButton("Guardar");
                JButton btnCancelar = new JButton("Cancelar");
                btnGuardar.addActionListener(e -> {
                    try {
                        if (txtNombre.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El nombre es obligatorio.");
                        }
                        if (txtIdentificacion.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("La identificación es obligatoria.");
                        }
                        Cliente cliente = new Cliente(
                            idCliente,
                            txtNombre.getText(),
                            txtIdentificacion.getText(),
                            txtTelefono.getText(),
                            txtCorreo.getText()
                        );
                        clienteDAO.actualizarCliente(cliente);
                        cargarClientes();
                        dialog.dispose();
                        JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente");
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error en los datos: " + ex.getMessage());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error al actualizar cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error al actualizar cliente: " + ex.getMessage());
                    }
                });
                btnCancelar.addActionListener(e -> dialog.dispose());

                dialog.add(btnGuardar);
                dialog.add(btnCancelar);
                dialog.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al cargar cliente: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idCliente = (int) tableModel.getValueAt(filaSeleccionada, 0);
            int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de que desea eliminar este cliente?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    clienteDAO.eliminarCliente(idCliente);
                    cargarClientes();
                    JOptionPane.showMessageDialog(this, "Cliente eliminado exitosamente");
                } catch ( Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    System.err.println("Error al eliminar cliente: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}