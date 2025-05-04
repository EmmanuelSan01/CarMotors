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

public class Inspeccion {
    private int idInspeccion;
    private Servicio servicio;
    private Tecnico tecnico;
    private String tipoInspeccion;
    private LocalDate fechaInspeccion;
    private ResultadoInspeccion resultado;
    private LocalDate fechaProxima;

    public enum ResultadoInspeccion {
        APROBADO, REQUIERE_REPARACIONES, RECHAZADO
    }

    // Constructor
    public Inspeccion(int idInspeccion, Servicio servicio, Tecnico tecnico, String tipoInspeccion,
                      LocalDate fechaInspeccion, ResultadoInspeccion resultado, LocalDate fechaProxima) {
        this.idInspeccion = idInspeccion;
        this.servicio = servicio;
        this.tecnico = tecnico;
        this.tipoInspeccion = tipoInspeccion;
        this.fechaInspeccion = fechaInspeccion;
        this.resultado = resultado;
        this.fechaProxima = fechaProxima;
    }

    // Getters y Setters
    public int getIdInspeccion() { return idInspeccion; }
    public void setIdInspeccion(int idInspeccion) { this.idInspeccion = idInspeccion; }
    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }
    public Tecnico getTecnico() { return tecnico; }
    public void setTecnico(Tecnico tecnico) { this.tecnico = tecnico; }
    public String getTipoInspeccion() { return tipoInspeccion; }
    public void setTipoInspeccion(String tipoInspeccion) { this.tipoInspeccion = tipoInspeccion; }
    public LocalDate getFechaInspeccion() { return fechaInspeccion; }
    public void setFechaInspeccion(LocalDate fechaInspeccion) { this.fechaInspeccion = fechaInspeccion; }
    public ResultadoInspeccion getResultado() { return resultado; }
    public void setResultado(ResultadoInspeccion resultado) { this.resultado = resultado; }
    public LocalDate getFechaProxima() { return fechaProxima; }
    public void setFechaProxima(LocalDate fechaProxima) { this.fechaProxima = fechaProxima; }
}