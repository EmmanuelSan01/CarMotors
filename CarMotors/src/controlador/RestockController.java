/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class RestockController {
    public void generatePurchaseOrder(List<Repuesto> repuestos) { System.out.println("Orden generada para " + repuestos.size() + " repuestos."); }
    public List<Order> trackPendingOrders() { return new ArrayList<>(); }
    public void updateOrderStatus(Order order, String status) { order.setStatus(status); }
    public List<Order> getPendingReorders() { return new ArrayList<>(); }
    public int calculateReorderLevel(Repuesto repuesto) { return repuesto.getNivelMinimo() * 2; }
}