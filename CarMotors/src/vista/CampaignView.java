/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

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
        JOptionPane.showMessageDialog(this, "Listado de campañas activas (pendiente)");
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
