/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

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
        JOptionPane.showMessageDialog(this, "Lista de clientes (pendiente)");
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