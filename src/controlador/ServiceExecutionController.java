/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import model.Repuesto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Emmanuel
 */
public class ServiceExecutionController {
    public void assignTaskToTechnician(int serviceId, int technicianId) { System.out.println("Técnico asignado al servicio"); }
    public void recordRepuestoUsage(int serviceId, List<Repuesto> repuestos) { System.out.println("Repuestos registrados para el servicio"); }
    public void updateWorkProgress(int serviceId, int progress) { System.out.println("Progreso del servicio: " + progress + "%"); }
    public void verifyCompletedWork(int serviceId, Map<String, Boolean> checklist) { System.out.println("Trabajo verificado para el servicio"); }
}