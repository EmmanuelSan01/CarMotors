/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author Emmanuel
 */
public class VehicleDeliveryController {
    public void verifyServiceCompletion(int serviceId) { System.out.println("Servicio verificado para entrega"); }
    public void prepareDeliveryOrder(int serviceId) { System.out.println("Orden de entrega preparada"); }
    public void recordClientSignature(int serviceId, String signature) { System.out.println("Firma del cliente registrada"); }
    public void markVehicleAsDelivered(int serviceId) { System.out.println("Vehículo entregado"); }
}
