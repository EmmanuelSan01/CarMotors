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
import dao.RepuestoDAO;
import model.Proveedor;
import model.Repuesto;
import model.Repuesto.EstadoRepuesto;
import model.Repuesto.MarcaVehiculo;
import model.Repuesto.TipoRepuesto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RepuestoFrame extends JFrame {
    private RepuestoDAO repuestoDAO;
    private ProveedorDAO proveedorDAO;
    private JTable tablaRepuestos;
    private DefaultTableModel tableModel;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public RepuestoFrame() {
        repuestoDAO = new RepuestoDAO();
        proveedorDAO = new ProveedorDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Registro de Repuestos - CarMotors");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        String[] columnas = {"ID Repuesto", "Nombre", "Tipo", "Marca", "Modelo", "Stock", "Nivel Mínimo", "Fecha Ingreso", "Vida Útil", "Estado", "Proveedor"};
        tableModel = new DefaultTableModel(columnas, 0);
        tablaRepuestos = new JTable(tableModel);
        cargarRepuestos();

        JScrollPane scrollPane = new JScrollPane(tablaRepuestos);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar Repuesto");
        JButton btnEditar = new JButton("Editar Repuesto");
        JButton btnEliminar = new JButton("Eliminar Repuesto");
        JButton btnAlertas = new JButton("Ver Alertas");

        btnAgregar.addActionListener(e -> mostrarFormularioAgregar());
        btnEditar.addActionListener(e -> mostrarFormularioEditar());
        btnEliminar.addActionListener(e -> eliminarRepuesto());
        btnAlertas.addActionListener(e -> mostrarAlertas());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnAlertas);
        panel.add(panelBotones, BorderLayout.SOUTH);

        add(panel);
    }

    private void cargarRepuestos() {
        try {
            tableModel.setRowCount(0);
            List<Repuesto> repuestos = repuestoDAO.obtenerRepuestos();
            for (Repuesto repuesto : repuestos) {
                tableModel.addRow(new Object[]{
                    repuesto.getIdRepuesto(),
                    repuesto.getNombre(),
                    repuesto.getTipo().name(),
                    repuesto.getMarca() != null ? repuesto.getMarca().name() : "",
                    repuesto.getModelo(),
                    repuesto.getCantidadStock(),
                    repuesto.getNivelMinimo(),
                    repuesto.getFechaIngreso() != null ? repuesto.getFechaIngreso().format(dateFormatter) : "",
                    repuesto.getVidaUtilMeses(),
                    repuesto.getEstado().name().replace("_", " "),
                    repuesto.getProveedor() != null ? repuesto.getProveedor().getNombre() : ""
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar repuestos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarFormularioAgregar() {
        JDialog dialog = new JDialog(this, "Agregar Repuesto", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(11, 2, 10, 10));

        JTextField txtNombre = new JTextField();
        JComboBox<TipoRepuesto> cbTipo = new JComboBox<>(TipoRepuesto.values());
        JComboBox<MarcaVehiculo> cbMarca = new JComboBox<>(MarcaVehiculo.values());
        cbMarca.insertItemAt(null, 0);
        cbMarca.setSelectedIndex(0);
        JTextField txtModelo = new JTextField();
        JComboBox<Proveedor> cbProveedor = new JComboBox<>();
        try {
            List<Proveedor> proveedores = proveedorDAO.obtenerProveedores();
            proveedores.forEach(cbProveedor::addItem);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        JTextField txtCantidadStock = new JTextField("0");
        JTextField txtNivelMinimo = new JTextField("0");
        JTextField txtFechaIngreso = new JTextField(LocalDate.now().format(dateFormatter));
        JTextField txtVidaUtil = new JTextField();
        JComboBox<EstadoRepuesto> cbEstado = new JComboBox<>(EstadoRepuesto.values());

        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombre);
        dialog.add(new JLabel("Tipo:"));
        dialog.add(cbTipo);
        dialog.add(new JLabel("Marca:"));
        dialog.add(cbMarca);
        dialog.add(new JLabel("Modelo:"));
        dialog.add(txtModelo);
        dialog.add(new JLabel("Proveedor:"));
        dialog.add(cbProveedor);
        dialog.add(new JLabel("Cantidad Stock:"));
        dialog.add(txtCantidadStock);
        dialog.add(new JLabel("Nivel Mínimo:"));
        dialog.add(txtNivelMinimo);
        dialog.add(new JLabel("Fecha Ingreso:"));
        dialog.add(txtFechaIngreso);
        dialog.add(new JLabel("Vida Útil (Meses):"));
        dialog.add(txtVidaUtil);
        dialog.add(new JLabel("Estado:"));
        dialog.add(cbEstado);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        btnGuardar.addActionListener(e -> {
            try {
                Integer modelo = txtModelo.getText().isEmpty() ? null : Integer.parseInt(txtModelo.getText());
                LocalDate fechaIngreso = LocalDate.parse(txtFechaIngreso.getText(), dateFormatter);
                Repuesto repuesto = new Repuesto(
                    0,
                    txtNombre.getText(),
                    (TipoRepuesto) cbTipo.getSelectedItem(),
                    (MarcaVehiculo) cbMarca.getSelectedItem(),
                    modelo,
                    (Proveedor) cbProveedor.getSelectedItem(),
                    Integer.parseInt(txtCantidadStock.getText()),
                    Integer.parseInt(txtNivelMinimo.getText()),
                    fechaIngreso,
                    Integer.parseInt(txtVidaUtil.getText()),
                    (EstadoRepuesto) cbEstado.getSelectedItem()
                );
                repuestoDAO.agregarRepuesto(repuesto);
                cargarRepuestos();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Repuesto agregado exitosamente");
            } catch (SQLException | NumberFormatException | java.time.format.DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.add(btnGuardar);
        dialog.add(btnCancelar);
        dialog.setVisible(true);
    }

    private void mostrarFormularioEditar() {
        int filaSeleccionada = tablaRepuestos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idRepuesto = (int) tableModel.getValueAt(filaSeleccionada, 0);
            JDialog dialog = new JDialog(this, "Editar Repuesto", true);
            dialog.setSize(400, 400);
            dialog.setLocationRelativeTo(this);
            dialog.setLayout(new GridLayout(11, 2, 10, 10));

            JTextField txtNombre = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 1));
            JComboBox<TipoRepuesto> cbTipo = new JComboBox<>(TipoRepuesto.values());
            cbTipo.setSelectedItem(TipoRepuesto.valueOf((String) tableModel.getValueAt(filaSeleccionada, 2)));
            JComboBox<MarcaVehiculo> cbMarca = new JComboBox<>(MarcaVehiculo.values());
            cbMarca.insertItemAt(null, 0);
            cbMarca.setSelectedItem(tableModel.getValueAt(filaSeleccionada, 3).equals("") ? null : MarcaVehiculo.valueOf((String) tableModel.getValueAt(filaSeleccionada, 3)));
            JTextField txtModelo = new JTextField(tableModel.getValueAt(filaSeleccionada, 4) != null ? tableModel.getValueAt(filaSeleccionada, 4).toString() : "");
            JComboBox<Proveedor> cbProveedor = new JComboBox<>();
            try {
                List<Proveedor> proveedores = proveedorDAO.obtenerProveedores();
                proveedores.forEach(cbProveedor::addItem);
                cbProveedor.setSelectedItem(proveedores.stream()
                    .filter(p -> p.getNombre().equals(tableModel.getValueAt(filaSeleccionada, 10)))
                    .findFirst().orElse(null));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar proveedores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            JTextField txtCantidadStock = new JTextField(tableModel.getValueAt(filaSeleccionada, 5).toString());
            JTextField txtNivelMinimo = new JTextField(tableModel.getValueAt(filaSeleccionada, 6).toString());
            JTextField txtFechaIngreso = new JTextField((String) tableModel.getValueAt(filaSeleccionada, 7));
            JTextField txtVidaUtil = new JTextField(tableModel.getValueAt(filaSeleccionada, 8).toString());
            JComboBox<EstadoRepuesto> cbEstado = new JComboBox<>(EstadoRepuesto.values());
            cbEstado.setSelectedItem(EstadoRepuesto.valueOf(((String) tableModel.getValueAt(filaSeleccionada, 9)).replace(" ", "_")));

            dialog.add(new JLabel("Nombre:"));
            dialog.add(txtNombre);
            dialog.add(new JLabel("Tipo:"));
            dialog.add(cbTipo);
            dialog.add(new JLabel("Marca:"));
            dialog.add(cbMarca);
            dialog.add(new JLabel("Modelo:"));
            dialog.add(txtModelo);
            dialog.add(new JLabel("Proveedor:"));
            dialog.add(cbProveedor);
            dialog.add(new JLabel("Cantidad Stock:"));
            dialog.add(txtCantidadStock);
            dialog.add(new JLabel("Nivel Mínimo:"));
            dialog.add(txtNivelMinimo);
            dialog.add(new JLabel("Fecha Ingreso:"));
            dialog.add(txtFechaIngreso);
            dialog.add(new JLabel("Vida Útil (Meses):"));
            dialog.add(txtVidaUtil);
            dialog.add(new JLabel("Estado:"));
            dialog.add(cbEstado);

            JButton btnGuardar = new JButton("Guardar");
            JButton btnCancelar = new JButton("Cancelar");
            btnGuardar.addActionListener(e -> {
                try {
                    Integer modelo = txtModelo.getText().isEmpty() ? null : Integer.parseInt(txtModelo.getText());
                    LocalDate fechaIngreso = LocalDate.parse(txtFechaIngreso.getText(), dateFormatter);
                    Repuesto repuesto = new Repuesto(
                        idRepuesto,
                        txtNombre.getText(),
                        (TipoRepuesto) cbTipo.getSelectedItem(),
                        (MarcaVehiculo) cbMarca.getSelectedItem(),
                        modelo,
                        (Proveedor) cbProveedor.getSelectedItem(),
                        Integer.parseInt(txtCantidadStock.getText()),
                        Integer.parseInt(txtNivelMinimo.getText()),
                        fechaIngreso,
                        Integer.parseInt(txtVidaUtil.getText()),
                        (EstadoRepuesto) cbEstado.getSelectedItem()
                    );
                    repuestoDAO.actualizarRepuesto(repuesto);
                    cargarRepuestos();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Repuesto actualizado exitosamente");
                } catch (SQLException | NumberFormatException | java.time.format.DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            btnCancelar.addActionListener(e -> dialog.dispose());

            dialog.add(btnGuardar);
            dialog.add(btnCancelar);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un repuesto para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarRepuesto() {
        int filaSeleccionada = tablaRepuestos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idRepuesto = (int) tableModel.getValueAt(filaSeleccionada, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este repuesto?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    repuestoDAO.eliminarRepuesto(idRepuesto);
                    cargarRepuestos();
                    JOptionPane.showMessageDialog(this, "Repuesto eliminado exitosamente");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar repuesto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un repuesto para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void mostrarAlertas() {
        try {
            List<Repuesto> stockBajo = repuestoDAO.obtenerRepuestosConStockBajo();
            List<Repuesto> proximosCaducar = repuestoDAO.obtenerRepuestosProximosACaducar();
            StringBuilder mensaje = new StringBuilder();
            if (!stockBajo.isEmpty()) {
                mensaje.append("Repuestos con stock bajo:\n");
                stockBajo.forEach(r -> mensaje.append("- ").append(r.getNombre()).append(" (Stock: ").append(r.getCantidadStock()).append(")\n"));
            }
            if (!proximosCaducar.isEmpty()) {
                mensaje.append("\nRepuestos próximos a caducar:\n");
                proximosCaducar.forEach(r -> mensaje.append("- ").append(r.getNombre()).append("\n"));
            }
            if (mensaje.length() == 0) {
                mensaje.append("No hay alertas de stock bajo ni caducidad.");
            }
            JOptionPane.showMessageDialog(this, mensaje.toString(), "Alertas de Inventario", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al verificar alertas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}