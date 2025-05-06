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
public class ServiceExecutionView extends JFrame {

    public void initialize() {
        setTitle("Ejecución del Servicio");
        setSize(650, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void showTechnicianAssignment(Servicio servicio) {
        JOptionPane.showMessageDialog(this, "Asignar técnico al servicio ID: " + servicio.getIdServicio());
    }

    public void showPartsSelection(Servicio servicio) {
        JOptionPane.showMessageDialog(this, "Seleccionar repuestos para servicio ID: " + servicio.getIdServicio());
    }

    public void updateWorkProgress(Servicio servicio, int progressPercentage) {
        JOptionPane.showMessageDialog(this, "Progreso del servicio ID " + servicio.getIdServicio() + ": " + progressPercentage + "%");
    }
}