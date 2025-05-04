/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import model.Cliente;
import model.Servicio;
import dao.ClienteDAO;
import java.util.List;

/**
 *
 * @author Emmanuel
 */
public class ClientController {
    private ClienteDAO dao = new ClienteDAO();

    public List<Cliente> getAllClientes() { return dao.getAll(); }
    public Cliente getClienteById(int id) { return dao.getById(id); }
    public void addCliente(Cliente client) { dao.insert(client); }
    public void updateCliente(Cliente client) { dao.update(client); }
    public void deleteCliente(int id) { dao.delete(id); }
    public List<Servicio> getClienteHistory(int id) { return dao.getHistory(id); }
    public void scheduleMaintenanceReminder(int id, Date date) { dao.scheduleReminder(id, date); }
    public double calculateClienteDiscount(int id) { return dao.getLoyaltyPoints(id) * 0.05; }
    public void applyLoyaltyProgram(int id) { dao.incrementLoyalty(id); }
}