/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;

/**
 *
 * @author Emmanuel
 */
public class VehicleReceptionView extends JFrame {

    public void initialize() {
        setTitle("Recepción de Vehículo");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void showVehicleForm() {
        JOptionPane.showMessageDialog(this, "Formulario de datos del vehículo (pendiente)");
    }

    public void showInitialInspectionForm() {
        JOptionPane.showMessageDialog(this, "Formulario de inspección inicial (pendiente)");
    }

    public void showEstimatedCostsForm() {
        JOptionPane.showMessageDialog(this, "Formulario de costos estimados (pendiente)");
    }
}