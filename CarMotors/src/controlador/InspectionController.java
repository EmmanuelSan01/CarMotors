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

    public Inspeccion getInspeccionById(int id) {
        throw new UnsupportedOperationException("getById() no implementado en InspeccionDAO.");
    }

    public void createInspeccion(Inspeccion inspection) {
        throw new UnsupportedOperationException("insert() no implementado en InspeccionDAO.");
    }

    public void updateInspeccionResult(int id, String result) {
        throw new UnsupportedOperationException("updateResult() no implementado en InspeccionDAO.");
    }

    public void scheduleFollowUpInspeccion(int id, Date date) {
        throw new UnsupportedOperationException("scheduleFollowUp() no implementado en InspeccionDAO.");
    }
}