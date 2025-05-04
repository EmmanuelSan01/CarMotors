/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import model.Factura;

/**
 *
 * @author Emmanuel
 */
public class InvoiceController {
    public Factura generateFactura(int serviceId) { return new Factura(); }
    public void calculateTaxes(Factura invoice) { invoice.setTax(invoice.getTotal() * 0.19); }
    public void addServiceToFactura(Factura invoice, Service service) { invoice.addService(service); }
    public void finalizeFactura(Factura invoice) { invoice.setFinalized(true); }
    public void generateFacturaPDF(Factura invoice) { System.out.println("PDF generado"); }
    public void generateQRCode(Factura invoice) { invoice.setQrCode("QR123ABC"); }
    public void sendFacturaByEmail(Factura invoice, String email) { System.out.println("Factura enviada a: " + email); }
}