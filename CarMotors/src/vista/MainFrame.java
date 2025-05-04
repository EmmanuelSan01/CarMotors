/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author sebas
 */
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Gestión - CarMotors");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con 2 botones
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Botones
        JButton btnRegistros = new JButton("Registros");
        JButton btnGestion = new JButton("Gestión");

        btnRegistros.addActionListener(e -> new RegistrosFrame().setVisible(true));
        btnGestion.addActionListener(e -> new GestionFrame().setVisible(true));

        panel.add(btnRegistros);
        panel.add(btnGestion);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}