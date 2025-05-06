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
public class NotificationComponent extends JPanel {
    private JLabel label;
    private Timer timer;

    public enum NotificationType {
        SUCCESS(Color.GREEN),
        ERROR(Color.RED),
        WARNING(Color.ORANGE),
        INFO(Color.BLUE);

        private final Color color;

        NotificationType(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    public NotificationComponent() {
        setLayout(new BorderLayout());
        label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setVisible(false);
        add(label, BorderLayout.CENTER);
    }

    public void showNotification(String message, NotificationType type) {
        label.setText(message);
        label.setBackground(type.getColor());
        label.setVisible(true);

        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        timer = new Timer(4000, e -> hideNotification());
        timer.setRepeats(false);
        timer.start();
    }

    public void hideNotification() {
        label.setVisible(false);
        label.setText("");
    }
}