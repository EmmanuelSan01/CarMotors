/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import model.Factura;

import javax.swing.*;

/**
 *
 * @author Emmanuel
 */
public class InvoiceView extends JFrame {

    public void initialize() {
        setTitle("Factura");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void showInvoicePreview(Factura invoice) {
        JOptionPane.showMessageDialog(this, "Vista previa de factura ID: " + invoice.getId());
    }

    public void printInvoice(Factura invoice) {
        JOptionPane.showMessageDialog(this, "Imprimiendo factura ID: " + invoice.getId());
    }

    public void sendInvoiceByEmail(Factura invoice, String emailAddress) {
        JOptionPane.showMessageDialog(this, "Enviando factura ID " + invoice.getId() + " al correo: " + emailAddress);
    }

    public void downloadInvoicePDF(Factura invoice) {
        JOptionPane.showMessageDialog(this, "Descargando PDF de factura ID: " + invoice.getId());
    }
}