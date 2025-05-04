/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.InspectionController;
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
        getContentPane().removeAll();

        InspectionController controller = new InspectionController();
        java.util.List<model.Inspeccion> inspecciones = controller.getAllInspeccions();

        String[] columnas = {"ID", "Tipo", "Técnico", "Fecha", "Resultado", "Próxima"};
        Object[][] datos = new Object[inspecciones.size()][columnas.length];

        for (int i = 0; i < inspecciones.size(); i++) {
            model.Inspeccion ins = inspecciones.get(i);
            datos[i][0] = ins.getIdInspeccion();
            datos[i][1] = ins.getTipoInspeccion();
            datos[i][2] = ins.getTecnico() != null ? ins.getTecnico().getNombre() : "—";
            datos[i][3] = ins.getFechaInspeccion();
            datos[i][4] = ins.getResultado() != null ? ins.getResultado().name() : "—";
            datos[i][5] = ins.getFechaProxima() != null ? ins.getFechaProxima() : "—";
        }

        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);

        getContentPane().add(scrollPane);
        revalidate();
        repaint();
    }

    public void showNewInspectionForm() {
        JOptionPane.showMessageDialog(this, "Formulario de nueva inspección (pendiente)");
    }

    public void refreshInspectionsList() {
        showInspectionsList();
    }
}
