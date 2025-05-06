/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author Emmanuel
 */
public class Factura {

    private int idFactura;
    private int idServicio;
    private LocalDateTime fechaEmision;
    private double total;
    private String cufe;
    private String numeroFactura;

    public Factura(int idFactura, int idServicio, LocalDateTime fechaEmision, double total) {
        this.idFactura = idFactura;
        this.idServicio = idServicio;
        this.fechaEmision = fechaEmision;
        this.total = total;
        this.numeroFactura = "FE" + (100 + idFactura);
        this.cufe = generarCUFE();
    }

    public String getNumeroFactura() {
        return "FE" + (100 + idFactura);
    }

    private String generarCUFE() {
        return UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "").substring(0, 32);
    }

    public int getIdFactura() {
        return idFactura;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public double getTotal() {
        return total;
    }

    public String getCUFE() {
        return cufe;
    }
}