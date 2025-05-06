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
import java.util.ArrayList;
import java.util.List;

public class OrdenCompra {
    private int idOrden;
    private Proveedor proveedor;
    private LocalDate fechaCreacion;
    private EstadoOrden estado;
    private List<DetalleOrden> detalles;

    public OrdenCompra(int idOrden, Proveedor proveedor, LocalDate fechaCreacion, EstadoOrden estado) {
        this.idOrden = idOrden;
        this.proveedor = proveedor;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.detalles = new ArrayList<>();
    }

    public int getIdOrden() { return idOrden; }
    public void setIdOrden(int idOrden) { this.idOrden = idOrden; }
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public EstadoOrden getEstado() { return estado; }
    public void setEstado(EstadoOrden estado) { this.estado = estado; }
    public List<DetalleOrden> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleOrden> detalles) { this.detalles = detalles; }
    public void addDetalle(DetalleOrden detalle) { this.detalles.add(detalle); }

    public enum EstadoOrden {
        Pendiente, Enviado, Recibido, Cancelado
    }

    public static class DetalleOrden {
        private int idDetalle;
        private Repuesto repuesto;
        private int cantidad;
        private double precioUnitario;

        public DetalleOrden(int idDetalle, Repuesto repuesto, int cantidad, double precioUnitario) {
            this.idDetalle = idDetalle;
            this.repuesto = repuesto;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
        }

        public int getIdDetalle() { return idDetalle; }
        public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }
        public Repuesto getRepuesto() { return repuesto; }
        public void setRepuesto(Repuesto repuesto) { this.repuesto = repuesto; }
        public int getCantidad() { return cantidad; }
        public void setCantidad(int cantidad) { this.cantidad = cantidad; }
        public double getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    }
}