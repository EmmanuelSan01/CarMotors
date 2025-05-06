/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author sebas
 */
import dao.VehiculoDAO;
import model.Cliente;
import model.Vehiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VehiculoFrame extends JFrame {
    private VehiculoDAO vehiculoDAO;
    private JTable tablaVehiculos;
    private DefaultTableModel tableModel;
    private static final String[] MARCAS = {"Renault", "Chevrolet", "Mazda", "Kia", "Toyota", "Nissan", "Ford", "Hyundai", "Suzuki", "Volkswagen"};
    private static final String[] TIPOS = {"Automóvil", "SUV", "Motocicleta", "Otro"};

    public VehiculoFrame() {
        vehiculoDAO = new VehiculoDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Registro de Vehículos - CarMotors");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla de vehículos
        String[] columnas = {"ID Vehículo", "Placa", "Marca", "Modelo", "Tipo", "ID Cliente"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaVehiculos = new JTable(tableModel);
        cargarVehiculos();

        JScrollPane scrollPane = new JScrollPane(tablaVehiculos);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar Vehículo");
        JButton btnEditar = new JButton("Editar Vehículo");
        JButton btnEliminar = new JButton("Eliminar Vehículo");

        btnAgregar.addActionListener(e -> mostrarFormularioAgregar());
        btnEditar.addActionListener(e -> mostrarFormularioEditar());
        btnEliminar.addActionListener(e -> eliminarVehiculo());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }

    private void cargarVehiculos() {
        try {
            tableModel.setRowCount(0);
            List<Vehiculo> vehiculos = vehiculoDAO.obtenerVehiculos();
            for (Vehiculo vehiculo : vehiculos) {
                Object[] fila = {
                    vehiculo.getIdVehiculo(),
                    vehiculo.getPlaca(),
                    vehiculo.getMarca(),
                    vehiculo.getModelo(),
                    vehiculo.getTipo(),
                    vehiculo.getCliente().getIdCliente()
                };
                tableModel.addRow(fila);
            }
            System.out.println("Tabla de vehículos actualizada con " + vehiculos.size() + " elementos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar vehículos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error al cargar vehículos: " + e.getMessage());
        }
    }

    private void mostrarFormularioAgregar() {
        JDialog dialog = new JDialog(this, "Agregar Vehículo", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(7, 2, 10, 10));

        JTextField txtPlaca = new JTextField();
        JComboBox<String> cbMarca = new JComboBox<>(MARCAS);
        JTextField txtModelo = new JTextField();
        JComboBox<String> cbTipo = new JComboBox<>(TIPOS);
        JTextField txtIdCliente = new JTextField();

        dialog.add(new JLabel("Placa:"));
        dialog.add(txtPlaca);
        dialog.add(new JLabel("Marca:"));
        dialog.add(cbMarca);
        dialog.add(new JLabel("Modelo (Año):"));
        dialog.add(txtModelo);
        dialog.add(new JLabel("Tipo:"));
        dialog.add(cbTipo);
        dialog.add(new JLabel("ID Cliente:"));
        dialog.add(txtIdCliente);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        btnGuardar.addActionListener(e -> {
            try {
                if (txtPlaca.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("La placa es obligatoria.");
                }
                if (txtModelo.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El modelo (año) es obligatorio.");
                }
                if (txtIdCliente.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El ID del cliente es obligatorio.");
                }
                Vehiculo vehiculo = new Vehiculo(
                    0, // ID se genera automáticamente
                    txtPlaca.getText(),
                    (String) cbMarca.getSelectedItem(),
                    Integer.parseInt(txtModelo.getText()),
                    (String) cbTipo.getSelectedItem(),
                    new Cliente(Integer.parseInt(txtIdCliente.getText()))
                );
                vehiculoDAO.agregarVehiculo(vehiculo);
                cargarVehiculos();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Vehículo agregado exitosamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El modelo (año) y el ID del cliente deben ser números: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error en los datos numéricos: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error en los datos: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar vehículo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al agregar vehículo: " + ex.getMessage());
            }
        });
        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.add(btnGuardar);
        dialog.add(btnCancelar);
        dialog.setVisible(true);
    }

    private void mostrarFormularioEditar() {
        int filaSeleccionada = tablaVehiculos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idVehiculo = (int) tableModel.getValueAt(filaSeleccionada, 0);
            try {
                JDialog dialog = new JDialog(this, "Editar Vehículo", true);
                dialog.setSize(400, 350);
                dialog.setLocationRelativeTo(this);
                dialog.setLayout(new GridLayout(7, 2, 10, 10));

                JTextField txtPlaca = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 1));
                JComboBox<String> cbMarca = new JComboBox<>(MARCAS);
                cbMarca.setSelectedItem(tableModel.getValueAt(filaSeleccionada, 2));
                JTextField txtModelo = new JTextField(tableModel.getValueAt(filaSeleccionada, 3).toString());
                JComboBox<String> cbTipo = new JComboBox<>(TIPOS);
                cbTipo.setSelectedItem(tableModel.getValueAt(filaSeleccionada, 4));
                JTextField txtIdCliente = new JTextField(tableModel.getValueAt(filaSeleccionada, 5).toString());

                dialog.add(new JLabel("Placa:"));
                dialog.add(txtPlaca);
                dialog.add(new JLabel("Marca:"));
                dialog.add(cbMarca);
                dialog.add(new JLabel("Modelo (Año):"));
                dialog.add(txtModelo);
                dialog.add(new JLabel("Tipo:"));
                dialog.add(cbTipo);
                dialog.add(new JLabel("ID Cliente:"));
                dialog.add(txtIdCliente);

                JButton btnGuardar = new JButton("Guardar");
                JButton btnCancelar = new JButton("Cancelar");
                btnGuardar.addActionListener(e -> {
                    try {
                        if (txtPlaca.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("La placa es obligatoria.");
                        }
                        if (txtModelo.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El modelo (año) es obligatorio.");
                        }
                        if (txtIdCliente.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El ID del cliente es obligatorio.");
                        }
                        Vehiculo vehiculo = new Vehiculo(
                            idVehiculo,
                            txtPlaca.getText(),
                            (String) cbMarca.getSelectedItem(),
                            Integer.parseInt(txtModelo.getText()),
                            (String) cbTipo.getSelectedItem(),
                            new Cliente(Integer.parseInt(txtIdCliente.getText()))
                        );
                        vehiculoDAO.actualizarVehiculo(vehiculo);
                        cargarVehiculos();
                        dialog.dispose();
                        JOptionPane.showMessageDialog(this, "Vehículo actualizado exitosamente");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "El modelo (año) y el ID del cliente deben ser números: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error en los datos numéricos: " + ex.getMessage());
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error en los datos: " + ex.getMessage());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error al actualizar vehículo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error al actualizar vehículo: " + ex.getMessage());
                    }
                });
                btnCancelar.addActionListener(e -> dialog.dispose());

                dialog.add(btnGuardar);
                dialog.add(btnCancelar);
                dialog.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar vehículo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al cargar vehículo: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un vehículo para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarVehiculo() {
        int filaSeleccionada = tablaVehiculos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idVehiculo = (int) tableModel.getValueAt(filaSeleccionada, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este vehículo?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    vehiculoDAO.eliminarVehiculo(idVehiculo);
                    cargarVehiculos();
                    JOptionPane.showMessageDialog(this, "Vehículo eliminado exitosamente");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar vehículo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    System.err.println("Error al eliminar vehículo: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un vehículo para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}