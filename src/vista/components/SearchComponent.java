/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.components;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Emmanuel
 */
public class SearchComponent extends JPanel {
    private JTextField searchField;

    public void initialize(String placeholderText) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchField.setToolTipText(placeholderText);
        add(new JLabel("Buscar:"));
        add(searchField);
    }

    public String getSearchQuery() {
        return searchField.getText().trim();
    }

    public void clearSearch() {
        searchField.setText("");
    }
}