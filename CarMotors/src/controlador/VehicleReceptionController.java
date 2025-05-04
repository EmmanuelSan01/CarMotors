/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.Map;
import model.Vehiculo;

/**
 *
 * @author Emmanuel
 */
public class VehicleReceptionController {
    public void registerVehiculoEntry(Vehiculo vehicle) { System.out.println("Vehículo registrado: " + vehicle.getPlaca()); }
    public void performInitialInspection(int id, Map<String, String> data) { System.out.println("Inspección inicial completada para vehículo ID: " + id); }
    public double calculateEstimatedCost(int serviceId) { return 150.0; }
    public void createServiceFromReception(VehiculoReception reception) { System.out.println("Servicio creado desde recepción"); }
}