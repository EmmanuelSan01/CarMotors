/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.components;

import javax.swing.*;

/**
 *
 * @author Emmanuel
 */
public class FilterComponent extends JPanel {
    private JComboBox<String> comboBox;

    public void initialize(String[] filterOptions) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        comboBox = new JComboBox<>(filterOptions);
        add(new JLabel("Filtrar por: "));
        add(comboBox);
    }

    public String getSelectedFilter() {
        return (String) comboBox.getSelectedItem();
    }

    public void resetFilters() {
        if (comboBox.getItemCount() > 0) {
            comboBox.setSelectedIndex(0);
        }
    }
}