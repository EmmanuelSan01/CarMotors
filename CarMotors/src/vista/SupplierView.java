/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.SupplierController;
import dao.ProveedorDAO;
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

        JButton btnEvaluar = new JButton("Evaluar");
        btnEvaluar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tabla.getValueAt(fila, 0);
                int p = Integer.parseInt(JOptionPane.showInputDialog(this, "Puntualidad (1-5):"));
                int q = Integer.parseInt(JOptionPane.showInputDialog(this, "Calidad (1-5):"));
                int c = Integer.parseInt(JOptionPane.showInputDialog(this, "Costo (1-5):"));                
                controller.evaluateProveedor(id, new ProveedorDAO.EvaluacionProveedor(id, p, q, c));
                JOptionPane.showMessageDialog(this, "Evaluación registrada.");
            }
        });

        JButton btnReporte = new JButton("Ver Desempeño");
        btnReporte.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tabla.getValueAt(fila, 0);                
                String reporte = controller.getProveedorPerformanceReport(id);
                JOptionPane.showMessageDialog(this, reporte);
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnEvaluar);
        panelBotones.add(btnReporte);

        getContentPane().add(scrollPane, "Center");
        getContentPane().add(panelBotones, "South");
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