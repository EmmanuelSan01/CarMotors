/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import model.Inspeccion;
import dao.InspeccionDAO;

import java.util.List;
import java.util.Date;

/**
 *
 * Autor: Emmanuel
 */
public class InspectionController {

    private InspeccionDAO dao = new InspeccionDAO();

    public List<Inspeccion> getAllInspeccions() {
        return dao.getAll();
    }

    public void createInspeccion(Inspeccion inspection) {
        // Programar automáticamente seguimiento en 6 meses si fue aprobada
        if (inspection.getResultado() == Inspeccion.ResultadoInspeccion.APROBADO) {
            inspection.setFechaProxima(inspection.getFechaInspeccion().plusMonths(6));
        }
        dao.insert(inspection);
    }
}