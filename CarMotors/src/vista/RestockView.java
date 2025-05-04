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
public class RestockView extends JFrame {

    public RestockView() {
        setTitle("Reposiciones Pendientes");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        showPendingReorders();
    }

    public void showPendingReorders() {
        JOptionPane.showMessageDialog(this, "Mostrar lista de órdenes de reposición pendientes (pendiente)");
    }

    public void showReorderForm() {
        JOptionPane.showMessageDialog(this, "Formulario para generar nueva orden de reposición (pendiente)");
    }

    public void showOrderTracking() {
        JOptionPane.showMessageDialog(this, "Seguimiento de órdenes en curso (pendiente)");
    }

    public void refreshPendingOrdersList() {
        showPendingReorders();
    }
}