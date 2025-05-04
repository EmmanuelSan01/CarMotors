/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.SupplierController;
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
        getContentPane().removeAll();

        SupplierController controller = new SupplierController();
        java.util.List<Proveedor> proveedores = controller.getAllProveedors();

        String[] columnas = {"ID", "Nombre", "NIT", "Contacto", "Frecuencia de Visita"};
        Object[][] datos = new Object[proveedores.size()][columnas.length];

        for (int i = 0; i < proveedores.size(); i++) {
            Proveedor p = proveedores.get(i);
            datos[i][0] = p.getIdProveedor();
            datos[i][1] = p.getNombre();
            datos[i][2] = p.getNit();
            datos[i][3] = p.getContacto();
            datos[i][4] = p.getFrecuenciaVisita();
        }

        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);

        getContentPane().add(scrollPane);
        revalidate();
        repaint();
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