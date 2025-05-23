/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.ClientController;
import model.Cliente;

import javax.swing.*;

/**
 *
 * @author Emmanuel
 */
public class ClientView extends JFrame {

    public ClientView() {
        setTitle("Gestión de Clientees");
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        showClientesList();
    }

    public void showClientesList() {
        getContentPane().removeAll();

        ClientController controller = new ClientController();
        java.util.List<Cliente> clientes = controller.getAllClientes();

        String[] columnas = {"ID", "Nombre", "Identificación", "Teléfono", "Correo"};
        Object[][] datos = new Object[clientes.size()][columnas.length];

        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            datos[i][0] = c.getIdCliente();
            datos[i][1] = c.getNombre();
            datos[i][2] = c.getIdentificacion();
            datos[i][3] = c.getTelefono();
            datos[i][4] = c.getCorreo();
        }

        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);

        JButton btnRecordatorio = new JButton("Programar Recordatorio");
        btnRecordatorio.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tabla.getValueAt(fila, 0);
                controller.scheduleMaintenanceReminder(id, new java.util.Date()); // ahora
                JOptionPane.showMessageDialog(this, "Recordatorio programado.");
            }
        });

        JButton btnDescuento = new JButton("Calcular Descuento");
        btnDescuento.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tabla.getValueAt(fila, 0);
                double descuento = controller.calculateClienteDiscount(id);
                JOptionPane.showMessageDialog(this, "Descuento estimado: " + (descuento * 100) + "%");
            }
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnRecordatorio);
        panelInferior.add(btnDescuento);

        getContentPane().add(scrollPane, "Center");
        getContentPane().add(panelInferior, "South");

        revalidate();
        repaint();
    }

    public void showClienteDetailsView(Cliente client) {
        JOptionPane.showMessageDialog(this, "Detalles del cliente: " + client.getNombre());
    }

    public void showAddClienteForm() {
        JOptionPane.showMessageDialog(this, "Formulario para agregar cliente (pendiente)");
    }

    public void showEditClienteForm(Cliente client) {
        JOptionPane.showMessageDialog(this, "Formulario para editar cliente: " + client.getNombre());
    }

    public void showClienteHistory(Cliente client) {
        JOptionPane.showMessageDialog(this, "Historial de servicios del cliente: " + client.getNombre());
    }

    public void refreshClientesList() {
        showClientesList();
    }
}