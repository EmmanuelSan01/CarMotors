/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.CampaignController;
import model.Campana;

import javax.swing.*;

/**
 *
 * @author Emmanuel
 */
public class CampaignView extends JFrame {

    public CampaignView() {
        setTitle("Campañas Promocionales");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        showCampanasList();
    }

    public void showCampanasList() {
        getContentPane().removeAll();

        CampaignController controller = new CampaignController();
        java.util.List<Campana> campanas = controller.getAllCampanas();

        String[] columnas = {"ID", "Nombre", "Descripción", "Fecha Inicio", "Fecha Fin"};
        Object[][] datos = new Object[campanas.size()][columnas.length];

        for (int i = 0; i < campanas.size(); i++) {
            Campana c = campanas.get(i);
            datos[i][0] = c.getIdCampana();
            datos[i][1] = c.getNombre();
            datos[i][2] = c.getDescripcion();
            datos[i][3] = c.getFechaInicio();
            datos[i][4] = c.getFechaFin();
        }

        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);

        getContentPane().add(scrollPane);
        revalidate();
        repaint();
    }


    public void showNewCampanaForm() {
        JOptionPane.showMessageDialog(this, "Formulario para nueva campaña (pendiente)");
    }

    public void showCampanaDetailsView(Campana campaign) {
        JOptionPane.showMessageDialog(this, "Detalles de campaña: " + campaign.getNombre());
    }

    public void refreshCampanasList() {
        showCampanasList();
    }
}
