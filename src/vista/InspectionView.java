/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.InspectionController;
import java.awt.BorderLayout;
import javax.swing.*;

/**
 *
 * @author Emmanuel
 */
public class InspectionView extends JFrame {
    private JScrollPane scrollPane;

    public InspectionView() {
        setTitle("Inspecciones");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior con botón
        JPanel panelTop = new JPanel();
        JButton btnNueva = new JButton("Nueva inspección");
        btnNueva.addActionListener(e -> showNewInspectionForm());
        panelTop.add(btnNueva);
        add(panelTop, BorderLayout.NORTH);

        // Panel central con la tabla
        showInspectionsList();
    }

    public void showInspectionsList() {
        if (scrollPane != null) {
            getContentPane().remove(scrollPane);
        }

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
        scrollPane = new JScrollPane(tabla);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showNewInspectionForm() {
        try {
            int servicioId = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del servicio:"));
            int tecnicoId = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del técnico:"));
            String tipo = JOptionPane.showInputDialog(this, "Tipo de inspección:");
            String resultadoStr = JOptionPane.showInputDialog(this, "Resultado (Aprobado / Requiere reparaciones / Rechazado):");

            model.Inspeccion.ResultadoInspeccion resultado
                    = model.Inspeccion.ResultadoInspeccion.valueOf(resultadoStr.toUpperCase().replace(" ", "_"));

            model.Inspeccion nueva = new model.Inspeccion(
                    0,
                    new model.Servicio(servicioId),
                    new model.Tecnico(tecnicoId),
                    tipo,
                    java.time.LocalDate.now(),
                    resultado,
                    null
            );

            new InspectionController().createInspeccion(nueva);
            JOptionPane.showMessageDialog(this, "Inspección registrada.");
            refreshInspectionsList();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar inspección: " + ex.getMessage());
        }
    }

    public void refreshInspectionsList() {
        showInspectionsList();
    }
}
