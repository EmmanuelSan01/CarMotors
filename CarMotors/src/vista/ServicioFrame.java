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
import dao.VehiculoDAO;
import dao.ServicioDAO;
import model.Cliente;
import model.Vehiculo;
import model.Servicio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ServicioFrame extends JFrame {
    private ServicioDAO servicioDAO;
    private ClienteDAO clienteDAO;
    private VehiculoDAO vehiculoDAO;
    private JTable tablaServicios;
    private DefaultTableModel tableModel;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ServicioFrame() {
        servicioDAO = new ServicioDAO();
        clienteDAO = new ClienteDAO();
        vehiculoDAO = new VehiculoDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Registro de Servicios - CarMotors");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        String[] columnas = {"ID Servicio", "Tipo", "Vehículo", "ID Técnico", "Descripción", "Tiempo Estimado", "Costo Mano de Obra", "Estado", "Fecha Inicio", "Fecha Fin"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaServicios = new JTable(tableModel);
        cargarServicios();

        JScrollPane scrollPane = new JScrollPane(tablaServicios);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar Servicio");
        JButton btnEditar = new JButton("Editar Servicio");
        JButton btnEliminar = new JButton("Eliminar Servicio");
        JButton btnVerEstados = new JButton("Ver Estado de Servicios");

        btnAgregar.addActionListener(e -> mostrarFormularioAgregar());
        btnEditar.addActionListener(e -> mostrarFormularioEditar());
        btnEliminar.addActionListener(e -> eliminarServicio());
        btnVerEstados.addActionListener(e -> mostrarSeguimientoEstados());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVerEstados);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }

    private void cargarServicios() {
        try {
            tableModel.setRowCount(0);
            List<Servicio> servicios = servicioDAO.obtenerServicios();
            for (Servicio servicio : servicios) {
                tableModel.addRow(new Object[]{
                    servicio.getIdServicio(),
                    servicio.getTipo(),
                    servicio.getVehiculo().getPlaca(),
                    servicio.getIdTecnico(),
                    servicio.getDescripcion(),
                    servicio.getTiempoEstimado(),
                    servicio.getCostoManoObra(),
                    servicio.getEstado().name().replace("_", " "),
                    servicio.getFechaInicio().format(dateFormatter),
                    servicio.getFechaFin() != null ? servicio.getFechaFin().format(dateFormatter) : ""
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar servicios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error: Estado inválido en la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarFormularioAgregar() {
        JDialog dialog = new JDialog(this, "Agregar Servicio", true);
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtTipo = new JTextField(20);
        JComboBox<Vehiculo> cbVehiculo = new JComboBox<>();
        JTextField txtIdTecnico = new JTextField(20);
        JTextArea txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JTextField txtTiempoEstimado = new JTextField(20);
        JTextField txtCostoManoObra = new JTextField(20);
        JTextField txtFechaInicio = new JTextField(LocalDate.now().format(dateFormatter), 20);
        JComboBox<Servicio.EstadoServicio> cbEstado = new JComboBox<>(Servicio.EstadoServicio.values());

        // Cargar vehículos en el JComboBox
        try {
            List<Vehiculo> vehiculos = vehiculoDAO.obtenerVehiculos();
            if (vehiculos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay vehículos registrados. Por favor, agregue un vehículo primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                dialog.dispose();
                return;
            }
            // Mostrar información detallada del vehículo en el JComboBox
            for (Vehiculo vehiculo : vehiculos) {
                cbVehiculo.addItem(vehiculo);
            }
            cbVehiculo.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Vehiculo) {
                        Vehiculo vehiculo = (Vehiculo) value;
                        setText(String.format("%s - %s %d (%s)", vehiculo.getPlaca(), vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getTipo()));
                    }
                    return this;
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar vehículos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dialog.dispose();
            return;
        }

        // Configurar GridBagConstraints para los campos
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST; dialog.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; dialog.add(txtTipo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Vehículo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; dialog.add(cbVehiculo, gbc);

        gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("ID Técnico:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; dialog.add(txtIdTecnico, gbc);

        gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; dialog.add(new JScrollPane(txtDescripcion), gbc);

        gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Tiempo Estimado (min):"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; dialog.add(txtTiempoEstimado, gbc);

        gbc.gridx = 0; gbc.gridy = 5; dialog.add(new JLabel("Costo Mano de Obra:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; dialog.add(txtCostoManoObra, gbc);

        gbc.gridx = 0; gbc.gridy = 6; dialog.add(new JLabel("Fecha Inicio (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; dialog.add(txtFechaInicio, gbc);

        gbc.gridx = 0; gbc.gridy = 7; dialog.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7; dialog.add(cbEstado, gbc);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> {
            try {
                // Validar campos numéricos
                int idTecnico = txtIdTecnico.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtIdTecnico.getText().trim());
                int tiempoEstimado = txtTiempoEstimado.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtTiempoEstimado.getText().trim());
                double costoManoObra = txtCostoManoObra.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtCostoManoObra.getText().trim());

                // Validar campos requeridos
                if (txtTipo.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "El tipo de servicio es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (cbVehiculo.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(dialog, "Debe seleccionar un vehículo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Servicio servicio = new Servicio(
                    0, // idServicio
                    txtTipo.getText().trim(),
                    (Vehiculo) cbVehiculo.getSelectedItem(),
                    idTecnico,
                    txtDescripcion.getText().trim(),
                    tiempoEstimado,
                    costoManoObra,
                    (Servicio.EstadoServicio) cbEstado.getSelectedItem(),
                    LocalDate.parse(txtFechaInicio.getText(), dateFormatter),
                    null // fechaFin
                );
                servicioDAO.agregarServicio(servicio);
                cargarServicios();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Servicio agregado exitosamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Por favor, ingrese valores numéricos válidos para ID Técnico, Tiempo Estimado y Costo Mano de Obra.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Formato de fecha inválido. Use yyyy-MM-dd (ejemplo: 2025-05-01).", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error al guardar el servicio: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(panelBotones, gbc);

        dialog.setVisible(true);
    }

    private void mostrarFormularioEditar() {
        int filaSeleccionada = tablaServicios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idServicio = (int) tableModel.getValueAt(filaSeleccionada, 0);
            JDialog dialog = new JDialog(this, "Editar Servicio", true);
            dialog.setSize(400, 500);
            dialog.setLocationRelativeTo(this);
            dialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JTextField txtTipo = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 1), 20);
            JComboBox<Vehiculo> cbVehiculo = new JComboBox<>();
            JTextField txtIdTecnico = new JTextField(tableModel.getValueAt(filaSeleccionada, 3).toString(), 20);
            JTextArea txtDescripcion = new JTextArea((String) tableModel.getValueAt(filaSeleccionada, 4), 3, 20);
            txtDescripcion.setLineWrap(true);
            txtDescripcion.setWrapStyleWord(true);
            JTextField txtTiempoEstimado = new JTextField(tableModel.getValueAt(filaSeleccionada, 5).toString(), 20);
            JTextField txtCostoManoObra = new JTextField(tableModel.getValueAt(filaSeleccionada, 6).toString(), 20);
            JTextField txtFechaInicio = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 8), 20);
            JComboBox<Servicio.EstadoServicio> cbEstado = new JComboBox<>(Servicio.EstadoServicio.values());
            cbEstado.setSelectedItem(Servicio.EstadoServicio.valueOf(normalizeEstado((String) tableModel.getValueAt(filaSeleccionada, 7))));

            try {
                List<Vehiculo> vehiculos = vehiculoDAO.obtenerVehiculos();
                if (vehiculos.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No hay vehículos registrados. Por favor, agregue un vehículo primero.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    dialog.dispose();
                    return;
                }
                // Mostrar información detallada del vehículo en el JComboBox
                for (Vehiculo vehiculo : vehiculos) {
                    cbVehiculo.addItem(vehiculo);
                }
                cbVehiculo.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        if (value instanceof Vehiculo) {
                            Vehiculo vehiculo = (Vehiculo) value;
                            setText(String.format("%s - %s %d (%s)", vehiculo.getPlaca(), vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getTipo()));
                        }
                        return this;
                    }
                });
                cbVehiculo.setSelectedItem(vehiculos.stream()
                    .filter(v -> v.getPlaca().equals(tableModel.getValueAt(filaSeleccionada, 2)))
                    .findFirst().orElse(null));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar vehículos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                dialog.dispose();
                return;
            }

            // Configurar GridBagConstraints para los campos
            gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST; dialog.add(new JLabel("Tipo:"), gbc);
            gbc.gridx = 1; gbc.gridy = 0; dialog.add(txtTipo, gbc);

            gbc.gridx = 0; gbc.gridy = 1; dialog.add(new JLabel("Vehículo:"), gbc);
            gbc.gridx = 1; gbc.gridy = 1; dialog.add(cbVehiculo, gbc);

            gbc.gridx = 0; gbc.gridy = 2; dialog.add(new JLabel("ID Técnico:"), gbc);
            gbc.gridx = 1; gbc.gridy = 2; dialog.add(txtIdTecnico, gbc);

            gbc.gridx = 0; gbc.gridy = 3; dialog.add(new JLabel("Descripción:"), gbc);
            gbc.gridx = 1; gbc.gridy = 3; dialog.add(new JScrollPane(txtDescripcion), gbc);

            gbc.gridx = 0; gbc.gridy = 4; dialog.add(new JLabel("Tiempo Estimado (min):"), gbc);
            gbc.gridx = 1; gbc.gridy = 4; dialog.add(txtTiempoEstimado, gbc);

            gbc.gridx = 0; gbc.gridy = 5; dialog.add(new JLabel("Costo Mano de Obra:"), gbc);
            gbc.gridx = 1; gbc.gridy = 5; dialog.add(txtCostoManoObra, gbc);

            gbc.gridx = 0; gbc.gridy = 6; dialog.add(new JLabel("Fecha Inicio (yyyy-MM-dd):"), gbc);
            gbc.gridx = 1; gbc.gridy = 6; dialog.add(txtFechaInicio, gbc);

            gbc.gridx = 0; gbc.gridy = 7; dialog.add(new JLabel("Estado:"), gbc);
            gbc.gridx = 1; gbc.gridy = 7; dialog.add(cbEstado, gbc);

            // Botones
            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton btnGuardar = new JButton("Guardar");
            JButton btnCancelar = new JButton("Cancelar");

            btnGuardar.addActionListener(e -> {
                try {
                    // Validar campos numéricos
                    int idTecnico = txtIdTecnico.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtIdTecnico.getText().trim());
                    int tiempoEstimado = txtTiempoEstimado.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtTiempoEstimado.getText().trim());
                    double costoManoObra = txtCostoManoObra.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtCostoManoObra.getText().trim());

                    // Validar campos requeridos
                    if (txtTipo.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(dialog, "El tipo de servicio es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (cbVehiculo.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(dialog, "Debe seleccionar un vehículo.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    LocalDate fechaFin = tableModel.getValueAt(filaSeleccionada, 9).equals("") ? null : LocalDate.parse((String) tableModel.getValueAt(filaSeleccionada, 9), dateFormatter);
                    Servicio servicio = new Servicio(
                        idServicio,
                        txtTipo.getText().trim(),
                        (Vehiculo) cbVehiculo.getSelectedItem(),
                        idTecnico,
                        txtDescripcion.getText().trim(),
                        tiempoEstimado,
                        costoManoObra,
                        (Servicio.EstadoServicio) cbEstado.getSelectedItem(),
                        LocalDate.parse(txtFechaInicio.getText(), dateFormatter),
                        fechaFin
                    );
                    if (servicio.getEstado() == Servicio.EstadoServicio.COMPLETADO&& servicio.getFechaFin() == null) {
                        servicio.setFechaFin(LocalDate.now());
                    }
                    servicioDAO.actualizarServicio(servicio);
                    cargarServicios();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Servicio actualizado exitosamente");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Por favor, ingrese valores numéricos válidos para ID Técnico, Tiempo Estimado y Costo Mano de Obra.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (java.time.format.DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(dialog, "Formato de fecha inválido. Use yyyy-MM-dd (ejemplo: 2025-05-01).", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error al actualizar el servicio: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnCancelar.addActionListener(e -> dialog.dispose());

            panelBotones.add(btnGuardar);
            panelBotones.add(btnCancelar);

            gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
            dialog.add(panelBotones, gbc);

            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarServicio() {
        int filaSeleccionada = tablaServicios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idServicio = (int) tableModel.getValueAt(filaSeleccionada, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este servicio?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    servicioDAO.eliminarServicio(idServicio);
                    cargarServicios();
                    JOptionPane.showMessageDialog(this, "Servicio eliminado exitosamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar servicio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void mostrarSeguimientoEstados() {
        JDialog dialog = new JDialog(this, "Seguimiento de Estado de Servicios", true);
        dialog.setSize(800, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Crear pestañas para cada estado
        for (Servicio.EstadoServicio estado : Servicio.EstadoServicio.values()) {
            JPanel panelEstado = new JPanel(new BorderLayout());
            DefaultTableModel tableModelEstado = new DefaultTableModel(
                new String[]{"ID Servicio", "Tipo", "Vehículo", "ID Técnico", "Descripción", "Fecha Inicio", "Fecha Fin"}, 0
            );
            JTable tabla = new JTable(tableModelEstado);
            JScrollPane scrollPane = new JScrollPane(tabla);
            panelEstado.add(scrollPane, BorderLayout.CENTER);
            tabbedPane.addTab(estado.name().replace("_", " "), panelEstado);

            // Cargar servicios por estado
            try {
                List<Servicio> servicios = servicioDAO.obtenerServicios();
                for (Servicio servicio : servicios) {
                    if (servicio.getEstado() == estado) {
                        tableModelEstado.addRow(new Object[]{
                            servicio.getIdServicio(),
                            servicio.getTipo(),
                            servicio.getVehiculo().getPlaca(),
                            servicio.getIdTecnico(),
                            servicio.getDescripcion(),
                            servicio.getFechaInicio().format(dateFormatter),
                            servicio.getFechaFin() != null ? servicio.getFechaFin().format(dateFormatter) : ""
                        });
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar servicios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Error: Estado inválido en la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        dialog.add(tabbedPane, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private String normalizeEstado(String estado) {
        if (estado == null) return "Pendiente";
        return estado.trim().toUpperCase().replace(" ", "_");
    }
}