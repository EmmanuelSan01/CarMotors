/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.FacturaController;
import model.Factura;
import dao.ClienteDAO;
import controlador.ServiceController;
import util.FacturaHTMLGenerator;
import model.Cliente;
import model.Servicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;

/**
 *
 * @author Emmanuel
 */
public class FacturaView extends JFrame {

    private ServiceController serviceController = new ServiceController();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private FacturaController controller = new FacturaController();
    private JTable table;
    private DefaultTableModel model;

    public FacturaView() {
        setTitle("Gestión de Facturas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        model = new DefaultTableModel(new String[]{"ID", "Número", "Servicio", "Fecha Emisión", "Total"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnAgregar = new JButton("Agregar Factura");
        JButton btnPrevisualizar = new JButton("Previsualizar Factura");

        btnAgregar.addActionListener(e -> agregarFactura());
        btnPrevisualizar.addActionListener(e -> previsualizarFactura());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnPrevisualizar);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarFacturas();
    }

    private void cargarFacturas() {
        model.setRowCount(0);
        List<Factura> facturas = controller.listarFacturas();

        for (Factura f : facturas) {
            Servicio servicio = serviceController.getServiceById(f.getIdServicio());
            String descripcionServicio = servicio != null ? servicio.getDescripcion() : "(Servicio no encontrado)";

            model.addRow(new Object[]{
                f.getIdFactura(),
                f.getNumeroFactura(),
                descripcionServicio,
                f.getFechaEmision(),
                f.getTotal()
            });
        }
    }

    private void agregarFactura() {
        String input = JOptionPane.showInputDialog(this, "Ingrese ID del Servicio:");
        if (input != null && !input.isEmpty()) {
            try {
                int idServicio = Integer.parseInt(input);
                controller.crearFactura(idServicio);
                cargarFacturas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar factura: " + ex.getMessage());
            }
        }
    }

    private void previsualizarFactura() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una factura.");
            return;
        }

        try {
            int idFactura = (int) model.getValueAt(row, 0);

            // Obtener la factura real desde el controlador
            Factura factura = controller.listarFacturas().stream()
                    .filter(f -> f.getIdFactura() == idFactura)
                    .findFirst()
                    .orElse(null);

            if (factura == null) {
                JOptionPane.showMessageDialog(this, "Factura no encontrada.");
                return;
            }

            Servicio servicio = serviceController.getServiceById(factura.getIdServicio());
            Cliente cliente = clienteDAO.obtenerClientePorServicio(factura.getIdServicio());

            if (servicio == null || cliente == null) {
                JOptionPane.showMessageDialog(this, "No se pudieron obtener los datos del servicio o cliente.");
                return;
            }

            // Generar HTML
            String html = FacturaHTMLGenerator.generarHTML(factura, servicio, cliente);

            // Guardar y abrir en navegador
            File archivo = new File("factura_" + factura.getNumeroFactura() + ".html");
            Files.writeString(archivo.toPath(), html, StandardCharsets.UTF_8);
            Desktop.getDesktop().browse(archivo.toURI());

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar previsualización: " + ex.getMessage());
        }
    }
}