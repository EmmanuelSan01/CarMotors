/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sebas
 */
public class Proveedor {
    private int idProveedor;
    private String nombre;
    private String nit;
    private String contacto;
    private int frecuenciaVisita;

    // Constructor completo
    public Proveedor(int idProveedor, String nombre, String nit, String contacto, int frecuenciaVisita) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.nit = nit;
        this.contacto = contacto;
        this.frecuenciaVisita = frecuenciaVisita;
    }

    // Constructor simplificado para relaciones
    public Proveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    // Getters y Setters
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public int getFrecuenciaVisita() { return frecuenciaVisita; }
    public void setFrecuenciaVisita(int frecuenciaVisita) { this.frecuenciaVisita = frecuenciaVisita; }
}