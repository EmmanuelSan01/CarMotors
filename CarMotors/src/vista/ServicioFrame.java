/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author sebas
 */
import dao.ServicioDAO;
import model.Servicio;
import model.Servicio.EstadoServicio;
import model.Vehiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ServicioFrame extends JFrame {
    private ServicioDAO servicioDAO;
    private JTable tablaServicios;
    private DefaultTableModel tableModel;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ServicioFrame() {
        servicioDAO = new ServicioDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Registro de Servicios - CarMotors");
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Tabla de servicios
        String[] columnas = {"ID Servicio", "Tipo", "ID Vehículo", "ID Técnico", "Descripción", 
                           "Tiempo Estimado", "Costo Mano de Obra", "Estado", "Fecha Inicio", "Fecha Fin"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaServicios = new JTable(tableModel);
        cargarServicios();

        JScrollPane scrollPane = new JScrollPane(tablaServicios);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar Servicio");
        JButton btnEditar = new JButton("Editar Servicio");
        JButton btnEliminar = new JButton("Eliminar Servicio");

        btnAgregar.addActionListener(e -> mostrarFormularioAgregar());
        btnEditar.addActionListener(e -> mostrarFormularioEditar());
        btnEliminar.addActionListener(e -> eliminarServicio());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }

    private void cargarServicios() {
        try {
            tableModel.setRowCount(0);
            List<Servicio> servicios = servicioDAO.obtenerServicios();
            for (Servicio servicio : servicios) {
                Object[] fila = {
                    servicio.getIdServicio(),
                    servicio.getTipo(),
                    servicio.getVehiculo().getIdVehiculo(),
                    servicio.getIdTecnico(),
                    servicio.getDescripcion(),
                    servicio.getTiempoEstimado(),
                    servicio.getCostoManoObra(),
                    servicio.getEstado(),
                    servicio.getFechaInicio(),
                    servicio.getFechaFin()
                };
                tableModel.addRow(fila);
            }
            System.out.println("Tabla de servicios actualizada con " + servicios.size() + " elementos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar servicios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error al cargar servicios: " + e.getMessage());
        }
    }

    private void mostrarFormularioAgregar() {
        JDialog dialog = new JDialog(this, "Agregar Servicio", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(10, 2, 10, 10));

        JTextField txtTipo = new JTextField();
        JTextField txtIdVehiculo = new JTextField();
        JTextField txtIdTecnico = new JTextField();
        JTextField txtDescripcion = new JTextField();
        JTextField txtTiempoEstimado = new JTextField();
        JTextField txtCostoManoObra = new JTextField();
        JComboBox<EstadoServicio> comboEstado = new JComboBox<>(EstadoServicio.values());
        JTextField txtFechaInicio = new JTextField(LocalDate.now().format(DATE_FORMATTER));
        JTextField txtFechaFin = new JTextField();

        dialog.add(new JLabel("Tipo:"));
        dialog.add(txtTipo);
        dialog.add(new JLabel("ID Vehículo:"));
        dialog.add(txtIdVehiculo);
        dialog.add(new JLabel("ID Técnico:"));
        dialog.add(txtIdTecnico);
        dialog.add(new JLabel("Descripción:"));
        dialog.add(txtDescripcion);
        dialog.add(new JLabel("Tiempo Estimado (min):"));
        dialog.add(txtTiempoEstimado);
        dialog.add(new JLabel("Costo Mano de Obra:"));
        dialog.add(txtCostoManoObra);
        dialog.add(new JLabel("Estado:"));
        dialog.add(comboEstado);
        dialog.add(new JLabel("Fecha Inicio (yyyy-MM-dd):"));
        dialog.add(txtFechaInicio);
        dialog.add(new JLabel("Fecha Fin (yyyy-MM-dd, opcional):"));
        dialog.add(txtFechaFin);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                // Validar entradas
                if (txtTipo.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El tipo es obligatorio.");
                }
                if (txtIdVehiculo.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El ID del vehículo es obligatorio.");
                }
                if (txtIdTecnico.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El ID del técnico es obligatorio.");
                }
                if (txtTiempoEstimado.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El tiempo estimado es obligatorio.");
                }
                if (txtCostoManoObra.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("El costo de mano de obra es obligatorio.");
                }
                if (txtFechaInicio.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("La fecha de inicio es obligatoria.");
                }

                LocalDate fechaFin = null;
                if (!txtFechaFin.getText().trim().isEmpty()) {
                    fechaFin = LocalDate.parse(txtFechaFin.getText(), DATE_FORMATTER);
                }

                Servicio servicio = new Servicio(
                    0, // ID se genera automáticamente
                    txtTipo.getText(),
                    new Vehiculo(Integer.parseInt(txtIdVehiculo.getText())),
                    Integer.parseInt(txtIdTecnico.getText()),
                    txtDescripcion.getText(),
                    Integer.parseInt(txtTiempoEstimado.getText()),
                    Double.parseDouble(txtCostoManoObra.getText()),
                    (EstadoServicio) comboEstado.getSelectedItem(),
                    LocalDate.parse(txtFechaInicio.getText(), DATE_FORMATTER),
                    fechaFin
                );
                servicioDAO.agregarServicio(servicio);
                cargarServicios();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Servicio agregado exitosamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos numéricos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error en los datos numéricos: " + ex.getMessage());
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha inválido (use yyyy-MM-dd): " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error en el formato de fecha: " + ex.getMessage());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error en los datos: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar servicio: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al agregar servicio: " + ex.getMessage());
            }
        });

        dialog.add(btnGuardar);
        dialog.setVisible(true);
    }

    private void mostrarFormularioEditar() {
        int filaSeleccionada = tablaServicios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idServicio = (int) tableModel.getValueAt(filaSeleccionada, 0);
            try {
                JDialog dialog = new JDialog(this, "Editar Servicio", true);
                dialog.setSize(400, 400);
                dialog.setLocationRelativeTo(this);
                dialog.setLayout(new GridLayout(10, 2, 10, 10));

                JTextField txtTipo = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 1));
                JTextField txtIdVehiculo = new JTextField(tableModel.getValueAt(filaSeleccionada, 2).toString());
                JTextField txtIdTecnico = new JTextField(tableModel.getValueAt(filaSeleccionada, 3).toString());
                JTextField txtDescripcion = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 4));
                JTextField txtTiempoEstimado = new JTextField(tableModel.getValueAt(filaSeleccionada, 5).toString());
                JTextField txtCostoManoObra = new JTextField(tableModel.getValueAt(filaSeleccionada, 6).toString());
                JComboBox<EstadoServicio> comboEstado = new JComboBox<>(EstadoServicio.values());
                comboEstado.setSelectedItem(tableModel.getValueAt(filaSeleccionada, 7));
                JTextField txtFechaInicio = new JTextField(tableModel.getValueAt(filaSeleccionada, 8).toString());
                JTextField txtFechaFin = new JTextField(tableModel.getValueAt(filaSeleccionada, 9) != null ? tableModel.getValueAt(filaSeleccionada, 9).toString() : "");

                dialog.add(new JLabel("Tipo:"));
                dialog.add(txtTipo);
                dialog.add(new JLabel("ID Vehículo:"));
                dialog.add(txtIdVehiculo);
                dialog.add(new JLabel("ID Técnico:"));
                dialog.add(txtIdTecnico);
                dialog.add(new JLabel("Descripción:"));
                dialog.add(txtDescripcion);
                dialog.add(new JLabel("Tiempo Estimado (min):"));
                dialog.add(txtTiempoEstimado);
                dialog.add(new JLabel("Costo Mano de Obra:"));
                dialog.add(txtCostoManoObra);
                dialog.add(new JLabel("Estado:"));
                dialog.add(comboEstado);
                dialog.add(new JLabel("Fecha Inicio (yyyy-MM-dd):"));
                dialog.add(txtFechaInicio);
                dialog.add(new JLabel("Fecha Fin (yyyy-MM-dd, opcional):"));
                dialog.add(txtFechaFin);

                JButton btnGuardar = new JButton("Guardar");
                btnGuardar.addActionListener(e -> {
                    try {
                        // Validar entradas
                        if (txtTipo.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El tipo es obligatorio.");
                        }
                        if (txtIdVehiculo.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El ID del vehículo es obligatorio.");
                        }
                        if (txtIdTecnico.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El ID del técnico es obligatorio.");
                        }
                        if (txtTiempoEstimado.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El tiempo estimado es obligatorio.");
                        }
                        if (txtCostoManoObra.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("El costo de mano de obra es obligatorio.");
                        }
                        if (txtFechaInicio.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("La fecha de inicio es obligatoria.");
                        }

                        LocalDate fechaFin = null;
                        if (!txtFechaFin.getText().trim().isEmpty()) {
                            fechaFin = LocalDate.parse(txtFechaFin.getText(), DATE_FORMATTER);
                        }

                        Servicio servicio = new Servicio(
                            idServicio,
                            txtTipo.getText(),
                            new Vehiculo(Integer.parseInt(txtIdVehiculo.getText())),
                            Integer.parseInt(txtIdTecnico.getText()),
                            txtDescripcion.getText(),
                            Integer.parseInt(txtTiempoEstimado.getText()),
                            Double.parseDouble(txtCostoManoObra.getText()),
                            (EstadoServicio) comboEstado.getSelectedItem(),
                            LocalDate.parse(txtFechaInicio.getText(), DATE_FORMATTER),
                            fechaFin
                        );
                        servicioDAO.actualizarServicio(servicio);
                        cargarServicios();
                        dialog.dispose();
                        JOptionPane.showMessageDialog(this, "Servicio actualizado exitosamente");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Error en los datos numéricos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error en los datos numéricos: " + ex.getMessage());
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(this, "Formato de fecha inválido (use yyyy-MM-dd): " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error en el formato de fecha: " + ex.getMessage());
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error en los datos: " + ex.getMessage());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error al actualizar servicio: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        System.err.println("Error al actualizar servicio: " + ex.getMessage());
                    }
                });

                dialog.add(btnGuardar);
                dialog.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar servicio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al cargar servicio: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarServicio() {
        int filaSeleccionada = tablaServicios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idServicio = (int) tableModel.getValueAt(filaSeleccionada, 0);
            try {
                servicioDAO.eliminarServicio(idServicio);
                cargarServicios();
                JOptionPane.showMessageDialog(this, "Servicio eliminado exitosamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar servicio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error al eliminar servicio: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
}