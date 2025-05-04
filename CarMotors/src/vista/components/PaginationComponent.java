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
public class PaginationComponent extends JPanel {
    private int totalItems;
    private int itemsPerPage;
    private int currentPage;

    private JLabel lblPage;
    private JButton btnPrev, btnNext;

    public void initialize(int totalItems, int itemsPerPage) {
        this.totalItems = totalItems;
        this.itemsPerPage = itemsPerPage;
        this.currentPage = 1;

        setLayout(new FlowLayout(FlowLayout.CENTER));
        btnPrev = new JButton("<");
        btnNext = new JButton(">");

        lblPage = new JLabel("Página 1 de " + getTotalPages());

        btnPrev.addActionListener(e -> goToPreviousPage());
        btnNext.addActionListener(e -> goToNextPage());

        add(btnPrev);
        add(lblPage);
        add(btnNext);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return (int) Math.ceil((double) totalItems / itemsPerPage);
    }

    public void goToNextPage() {
        if (currentPage < getTotalPages()) {
            currentPage++;
            updateLabel();
        }
    }

    public void goToPreviousPage() {
        if (currentPage > 1) {
            currentPage--;
            updateLabel();
        }
    }

    public void goToPage(int pageNumber) {
        if (pageNumber >= 1 && pageNumber <= getTotalPages()) {
            currentPage = pageNumber;
            updateLabel();
        }
    }

    private void updateLabel() {
        lblPage.setText("Página " + currentPage + " de " + getTotalPages());
    }
}