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

public class Repuesto {
    private int idRepuesto;
    private String nombre;
    private TipoRepuesto tipo;
    private MarcaVehiculo marca;
    private Integer modelo;
    private Proveedor proveedor;
    private int cantidadStock;
    private int nivelMinimo;
    private LocalDate fechaIngreso;
    private int vidaUtilMeses;
    private EstadoRepuesto estado;

    // Constructor completo
    public Repuesto(int idRepuesto, String nombre, TipoRepuesto tipo, MarcaVehiculo marca, Integer modelo,
                    Proveedor proveedor, int cantidadStock, int nivelMinimo, LocalDate fechaIngreso,
                    int vidaUtilMeses, EstadoRepuesto estado) {
        this.idRepuesto = idRepuesto;
        this.nombre = nombre;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.proveedor = proveedor;
        this.cantidadStock = cantidadStock;
        this.nivelMinimo = nivelMinimo;
        this.fechaIngreso = fechaIngreso;
        this.vidaUtilMeses = vidaUtilMeses;
        this.estado = estado;
    }

    // Constructor simplificado para relaciones
    public Repuesto(int idRepuesto) {
        this.idRepuesto = idRepuesto;
    }

    // Getters y Setters
    public int getIdRepuesto() { return idRepuesto; }
    public void setIdRepuesto(int idRepuesto) { this.idRepuesto = idRepuesto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public TipoRepuesto getTipo() { return tipo; }
    public void setTipo(TipoRepuesto tipo) { this.tipo = tipo; }
    public MarcaVehiculo getMarca() { return marca; }
    public void setMarca(MarcaVehiculo marca) { this.marca = marca; }
    public Integer getModelo() { return modelo; }
    public void setModelo(Integer modelo) { this.modelo = modelo; }
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
    public int getCantidadStock() { return cantidadStock; }
    public void setCantidadStock(int cantidadStock) { this.cantidadStock = cantidadStock; }
    public int getNivelMinimo() { return nivelMinimo; }
    public void setNivelMinimo(int nivelMinimo) { this.nivelMinimo = nivelMinimo; }
    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public int getVidaUtilMeses() { return vidaUtilMeses; }
    public void setVidaUtilMeses(int vidaUtilMeses) { this.vidaUtilMeses = vidaUtilMeses; }
    public EstadoRepuesto getEstado() { return estado; }
    public void setEstado(EstadoRepuesto estado) { this.estado = estado; }

    // Enums
    public enum TipoRepuesto {
        Mecánico, Eléctrico, Carrocería, Consumo
    }

    public enum MarcaVehiculo {
        Renault, Chevrolet, Mazda, Kia, Toyota, Nissan, Ford, Hyundai, Suzuki, Volkswagen
    }

    public enum EstadoRepuesto {
        Disponible, Reservado_para_trabajo, Fuera_de_servicio
    }
}