/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author sebas
 */
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Gestión - CarMotors");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botones para cada módulo
        JButton btnClientes = new JButton("Registro de Clientes");
        JButton btnProveedores = new JButton("Registro de Proveedores");
        JButton btnRepuestos = new JButton("Registro de Repuestos");
        JButton btnLotes = new JButton("Registro de Lotes");
        JButton btnVehiculos = new JButton("Registro de Vehículos");
        JButton btnServicios = new JButton("Seguimiento de Servicios");
        JButton btnSalir = new JButton("Salir");

        btnClientes.addActionListener(e -> new ClienteFrame().setVisible(true));
        btnProveedores.addActionListener(e -> new ProveedorFrame().setVisible(true));
        btnRepuestos.addActionListener(e -> new RepuestoFrame().setVisible(true));
        btnLotes.addActionListener(e -> new LoteFrame().setVisible(true));
        btnVehiculos.addActionListener(e -> new VehiculoFrame().setVisible(true));
        btnServicios.addActionListener(e -> new ServicioFrame().setVisible(true));
        btnSalir.addActionListener(e -> System.exit(0));

        panel.add(btnClientes);
        panel.add(btnProveedores);
        panel.add(btnRepuestos);
        panel.add(btnLotes);
        panel.add(btnVehiculos);
        panel.add(btnServicios);
        panel.add(btnSalir);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}