/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sebas
 */
import java.time.LocalDate;
import java.util.List;

public class Campana {
    private int idCampana;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private List<Cliente> clientesAsignados;

    // Constructor
    public Campana(int idCampana, String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, List<Cliente> clientesAsignados) {
        this.idCampana = idCampana;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.clientesAsignados = clientesAsignados;
    }

    // Getters y Setters
    public int getIdCampana() { return idCampana; }
    public void setIdCampana(int idCampana) { this.idCampana = idCampana; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public List<Cliente> getClientesAsignados() { return clientesAsignados; }
    public void setClientesAsignados(List<Cliente> clientesAsignados) { this.clientesAsignados = clientesAsignados; }
}