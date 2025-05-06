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
public class VehicleDeliveryView extends JFrame {

    public void initialize() {
        setTitle("Entrega del Vehículo");
        setSize(600, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void showServiceVerificationForm(Servicio servicio) {
        JOptionPane.showMessageDialog(this, "Verificación del servicio ID: " + servicio.getIdServicio());
    }

    public void showDeliveryForm(Servicio servicio) {
        JOptionPane.showMessageDialog(this, "Formulario de entrega para el servicio ID: " + servicio.getIdServicio());
    }

    public void generateDeliveryOrder(Servicio servicio) {
        JOptionPane.showMessageDialog(this, "Generando orden de entrega para servicio ID: " + servicio.getIdServicio());
    }
}