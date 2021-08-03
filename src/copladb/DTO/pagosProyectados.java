/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DTO;

/**
 *
 * @author i7
 */
public class pagosProyectados {
    
    private int idPagoProyectado;
    private int numPago;
    private String fechaPago;
    private float monto;
    private int idTarjeta;
    private String estado;    

    public int getIdPagoProyectado() {
        return idPagoProyectado;
    }

    public void setIdPagoProyectado(int idPagoProyectado) {
        this.idPagoProyectado = idPagoProyectado;
    }

    public int getNumPago() {
        return numPago;
    }

    public void setNumPago(int numPago) {
        this.numPago = numPago;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
