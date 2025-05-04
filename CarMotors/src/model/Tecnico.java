/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sebas
 */
public class Tecnico {
    private int idTecnico;
    private String nombre;
    private String identificacion;
    private String especialidad;
    private String telefono;

    // Constructor
    public Tecnico(int idTecnico, String nombre, String identificacion, String especialidad, String telefono) {
        this.idTecnico = idTecnico;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.especialidad = especialidad;
        this.telefono = telefono;
    }

    // Getters y Setters
    public int getIdTecnico() { return idTecnico; }
    public void setIdTecnico(int idTecnico) { this.idTecnico = idTecnico; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}