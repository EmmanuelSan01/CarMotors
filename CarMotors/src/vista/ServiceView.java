/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import model.Servicio;

import javax.swing.*;

/**
 *
 * @author Emmanuel
 */
public class ServiceView extends JFrame {

    public ServiceView() {
        setTitle("Gestión de Servicios");
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        showServicesList();
    }

    public void showServicesList() {
        JOptionPane.showMessageDialog(this, "Lista de servicios activos o históricos (pendiente)");
    }

    public void showNewServiceForm() {
        JOptionPane.showMessageDialog(this, "Formulario para registrar un nuevo servicio (pendiente)");
    }

    public void showServiceDetailView(Servicio servicio) {
        JOptionPane.showMessageDialog(this, "Detalles del servicio ID: " + servicio.getIdServicio());
    }

    public void showServiceWorkflow(Servicio servicio) {
        JOptionPane.showMessageDialog(this, "Flujo de trabajo para el servicio ID: " + servicio.getIdServicio());
    }

    public void refreshServicesList() {
        showServicesList();
    }

    public void updateServiceStatus(Servicio servicio, String status) {
        JOptionPane.showMessageDialog(this, "Actualizar estado de servicio ID " + servicio.getIdServicio() + " a: " + status);
    }
}