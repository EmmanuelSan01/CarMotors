/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import model.Inspeccion;
import dao.InspeccionDAO;
import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class InspectionController {
    private InspeccionDAO dao = new InspeccionDAO();

    public List<Inspeccion> getAllInspeccions() { return dao.getAll(); }
    public Inspeccion getInspeccionById(int id) { return dao.getById(id); }
    public void createInspeccion(Inspeccion inspection) { dao.insert(inspection); }
    public void updateInspeccionResult(int id, String result) { dao.updateResult(id, result); }
    public void scheduleFollowUpInspeccion(int id, Date date) { dao.scheduleFollowUp(id, date); }
}
