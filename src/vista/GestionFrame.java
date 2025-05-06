/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;


/**
 *
 * @author Emmanuel
 */
public class GestionFrame extends JFrame {

    public GestionFrame() {
        setTitle("Gestión");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        // Crear panel con diseño de grid
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botones con sus respectivos listeners
        JButton btnInventario = new JButton("Inventario");
        btnInventario.addActionListener(e -> new InventoryView().setVisible(true));

        JButton btnReabastecimiento = new JButton("Reabastecimiento");
        btnReabastecimiento.addActionListener(e -> new RestockView().setVisible(true));

        JButton btnServicios = new JButton("Servicios");
        btnServicios.addActionListener(e -> new ServiceView().setVisible(true));

        JButton btnClientes = new JButton("Clientes");
        btnClientes.addActionListener(e -> new ClientView().setVisible(true));

        JButton btnProveedores = new JButton("Proveedores");
        btnProveedores.addActionListener(e -> new SupplierView().setVisible(true));

        JButton btnCampañas = new JButton("Campañas");
        btnCampañas.addActionListener(e -> new CampaignView().setVisible(true));

        JButton btnInspecciones = new JButton("Inspecciones");
        btnInspecciones.addActionListener(e -> new InspectionView().setVisible(true));

        // Agregar botones al panel
        panel.add(btnInventario);
        panel.add(btnReabastecimiento);
        panel.add(btnServicios);
        panel.add(btnClientes);
        panel.add(btnProveedores);
        panel.add(btnCampañas);
        panel.add(btnInspecciones);

        add(panel);
    }
}