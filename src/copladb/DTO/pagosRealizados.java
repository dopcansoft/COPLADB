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
public class pagosRealizados {
    
    private int idPagoRealizado;
    private int numPago;
    private String fecha;
    private float monto;
    private String tipo;
    private int idTarjeta;
    private float saldo;

    public int getIdPagoRealizado() {
        return idPagoRealizado;
    }

    public void setIdPagoRealizado(int idPagoRealizado) {
        this.idPagoRealizado = idPagoRealizado;
    }

    public int getNumPago() {
        return numPago;
    }

    public void setNumPago(int numPago) {
        this.numPago = numPago;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    
    
   
}
