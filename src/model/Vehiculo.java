/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sebas
 */
public class Vehiculo {
    private int idVehiculo;
    private String placa;
    private String marca;
    private int modelo; // Cambiado de 'anio' a 'modelo' (año del vehículo)
    private String tipo; // Nuevo campo para el ENUM 'tipo'
    private Cliente cliente;

    // Constructor completo
    public Vehiculo(int idVehiculo, String placa, String marca, int modelo, String tipo, Cliente cliente) {
        this.idVehiculo = idVehiculo;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.tipo = tipo;
        this.cliente = cliente;
    }

    // Constructor simplificado para relaciones
    public Vehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    // Getters y Setters
    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public int getModelo() { return modelo; }
    public void setModelo(int modelo) { this.modelo = modelo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}