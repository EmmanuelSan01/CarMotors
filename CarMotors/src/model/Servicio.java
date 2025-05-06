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

public class Servicio {
    private int idServicio;
    private TipoServicio tipo;
    private Vehiculo vehiculo;
    private int idTecnico;
    private Tecnico tecnico;
    private String descripcion;
    private int tiempoEstimado;
    private double costoManoObra;
    private EstadoServicio estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public enum EstadoServicio {
        PENDIENTE, EN_PROCESO, COMPLETADO, ENTREGADO
    }

    public enum TipoServicio {
        CORRECTIVO, PREVENTIVO
    }

    public Servicio(int idServicio) {
        this.idServicio = idServicio;
    }

    // Constructor completo
    public Servicio(int idServicio, TipoServicio tipo, Vehiculo vehiculo, Tecnico tecnico, String descripcion,
            int tiempoEstimado, double costoManoObra, EstadoServicio estado,
            LocalDate fechaInicio, LocalDate fechaFin) {
        this.idServicio = idServicio;
        this.tipo = tipo;
        this.vehiculo = vehiculo;
        this.idTecnico = tecnico.getIdTecnico();
        this.tecnico = tecnico;
        this.descripcion = descripcion;
        this.tiempoEstimado = tiempoEstimado;
        this.costoManoObra = costoManoObra;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Servicio(int idServicio, TipoServicio tipo, Vehiculo vehiculo, int idTecnico, String descripcion, 
                    int tiempoEstimado, double costoManoObra, EstadoServicio estado, 
                    LocalDate fechaInicio, LocalDate fechaFin) {
        this.idServicio = idServicio;
        this.tipo = tipo;
        this.vehiculo = vehiculo;
        this.idTecnico = idTecnico;
        this.descripcion = descripcion;
        this.tiempoEstimado = tiempoEstimado;
        this.costoManoObra = costoManoObra;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y Setters
    public int getIdServicio() { return idServicio; }
    public void setIdServicio(int idServicio) { this.idServicio = idServicio; }
    public TipoServicio getTipo() { return tipo; }
    public void setTipo(TipoServicio tipo) { this.tipo = tipo; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
    public int getIdTecnico() { return idTecnico; }
    public void setIdTecnico(int idTecnico) { this.idTecnico = idTecnico; }
    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
        if (tecnico != null) {
            this.idTecnico = tecnico.getIdTecnico();
        }
    }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getTiempoEstimado() { return tiempoEstimado; }
    public void setTiempoEstimado(int tiempoEstimado) { this.tiempoEstimado = tiempoEstimado; }
    public double getCostoManoObra() { return costoManoObra; }
    public void setCostoManoObra(double costoManoObra) { this.costoManoObra = costoManoObra; }
    public EstadoServicio getEstado() { return estado; }
    public void setEstado(EstadoServicio estado) { this.estado = estado; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}