/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import model.Proveedor;

import javax.swing.*;

/**
 *
 * @author Emmanuel
 */
public class SupplierView extends JFrame {

    public SupplierView() {
        setTitle("Gestión de Proveedores");
        setSize(650, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        showProveedorsList();
    }

    public void showProveedorsList() {
        JOptionPane.showMessageDialog(this, "Listado de proveedores (pendiente)");
    }

    public void showProveedorDetailsView(Proveedor supplier) {
        JOptionPane.showMessageDialog(this, "Detalles del proveedor: " + supplier.getNombre());
    }

    public void showAddProveedorForm() {
        JOptionPane.showMessageDialog(this, "Formulario para agregar proveedor (pendiente)");
    }

    public void showEvaluationForm(Proveedor supplier) {
        JOptionPane.showMessageDialog(this, "Formulario de evaluación del proveedor: " + supplier.getNombre());
    }

    public void refreshProveedorsList() {
        showProveedorsList();
    }
}