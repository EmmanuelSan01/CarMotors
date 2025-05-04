/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.ServiceController;
import model.Servicio;

import javax.swing.*;

/**
 *
 * @author Emmanuel
 */
public class ServiceView extends JFrame {

    public ServiceView() {
        setTitle("Gestión de Servicios");
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        showServicesList();
    }

    public void showServicesList() {
        getContentPane().removeAll();

        ServiceController controller = new ServiceController();
        java.util.List<Servicio> servicios = controller.getAllServices();

        String[] columnas = {"ID", "Tipo", "Técnico", "Estado", "Fecha inicio", "Fecha fin"};
        Object[][] datos = new Object[servicios.size()][columnas.length];

        for (int i = 0; i < servicios.size(); i++) {
            Servicio s = servicios.get(i);
            datos[i][0] = s.getIdServicio();
            datos[i][1] = s.getTipo();
            datos[i][2] = s.getTecnico().getNombre();
            datos[i][3] = s.getEstado().name();
            datos[i][4] = s.getFechaInicio();
            datos[i][5] = s.getFechaFin() != null ? s.getFechaFin() : "—";
        }

        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);

        JButton avanzarEstado = new JButton("Avanzar Estado");
        avanzarEstado.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tabla.getValueAt(fila, 0);
                String estadoActual = (String) tabla.getValueAt(fila, 3);

                String nuevoEstado = switch (estadoActual) {
                    case "PENDIENTE" -> "EN_PROCESO";
                    case "EN_PROCESO" -> "COMPLETADO";
                    default -> estadoActual;
                };

                if (!estadoActual.equals(nuevoEstado)) {
                    controller.updateServiceStatus(id, nuevoEstado);
                    refreshServicesList();
                }
            }
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(avanzarEstado);

        getContentPane().add(scrollPane, "Center");
        getContentPane().add(panelInferior, "South");

        revalidate();
        repaint();
    }

    public void showNewServiceForm() {
        JOptionPane.showMessageDialog(this, "Formulario para registrar un nuevo servicio (pendiente)");
    }

    public void showServiceDetailView(Servicio servicio) {
        JOptionPane.showMessageDialog(this, "Detalles del servicio ID: " + servicio.getIdServicio());
    }

    public void showServiceWorkflow(Servicio servicio) {
        JOptionPane.showMessageDialog(this, "Flujo de trabajo para el servicio ID: " + servicio.getIdServicio());
    }

    public void refreshServicesList() {
        showServicesList();
    }

    public void updateServiceStatus(Servicio servicio, String status) {
        JOptionPane.showMessageDialog(this, "Actualizar estado de servicio ID " + servicio.getIdServicio() + " a: " + status);
    }
}