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

public class Lote {
    private int idLote;
    private Repuesto repuesto;
    private LocalDate fechaIngreso;
    private Proveedor proveedor;
    private LocalDate fechaCaducidad;

    // Constructor completo
    public Lote(int idLote, Repuesto repuesto, LocalDate fechaIngreso, Proveedor proveedor, LocalDate fechaCaducidad) {
        this.idLote = idLote;
        this.repuesto = repuesto;
        this.fechaIngreso = fechaIngreso;
        this.proveedor = proveedor;
        this.fechaCaducidad = fechaCaducidad;
    }

    // Constructor simplificado para relaciones
    public Lote(int idLote) {
        this.idLote = idLote;
    }

    // Getters y Setters
    public int getIdLote() { return idLote; }
    public void setIdLote(int idLote) { this.idLote = idLote; }
    public Repuesto getRepuesto() { return repuesto; }
    public void setRepuesto(Repuesto repuesto) { this.repuesto = repuesto; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
    public LocalDate getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(LocalDate fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }
}