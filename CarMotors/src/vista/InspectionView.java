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
public class InspectionView extends JFrame {

    public InspectionView() {
        setTitle("Inspecciones");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        showInspectionsList();
    }

    public void showInspectionsList() {
        JOptionPane.showMessageDialog(this, "Lista de inspecciones registradas (pendiente)");
    }

    public void showNewInspectionForm() {
        JOptionPane.showMessageDialog(this, "Formulario de nueva inspección (pendiente)");
    }

    public void refreshInspectionsList() {
        showInspectionsList();
    }
}
