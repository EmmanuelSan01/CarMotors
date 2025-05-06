/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

public class Order {
    private int idOrden;
    private int idProveedor;
    private LocalDate fechaOrden;
    private String estado; // Pendiente, Enviado, etc.

    public Order() {
    }

    public Order(int idOrden, int idProveedor, LocalDate fechaOrden, String estado) {
        this.idOrden = idOrden;
        this.idProveedor = idProveedor;
        this.fechaOrden = fechaOrden;
        this.estado = estado;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public LocalDate getFechaOrden() {
        return fechaOrden;
    }

    public void setFechaOrden(LocalDate fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Orden #" + idOrden + " | Proveedor ID: " + idProveedor + " | Estado: " + estado + " | Fecha: " + fechaOrden;
    }
}