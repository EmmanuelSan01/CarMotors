/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import model.Campana;
import dao.CampanaDAO;
import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class CampaignController {
    private CampanaDAO dao = new CampanaDAO();

    public List<Campana> getAllCampanas() { return dao.getAll(); }
    public Campana getCampanaById(int id) { return dao.getById(id); }
    public void createCampana(Campana campaign) { dao.insert(campaign); }
    public void updateCampana(Campana campaign) { dao.update(campaign); }
    public void deleteCampana(int id) { dao.delete(id); }
    public void assignClientsAutomatically(int id, String criteria) { dao.assignClients(id, criteria); }
    public String generateCampanaReport(int id) { return dao.generateReport(id); }
}