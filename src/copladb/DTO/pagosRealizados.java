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
    private String Fecha;
    private float Monto;
    private String Tipo;
    private int idTarjeta;

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
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public float getMonto() {
        return Monto;
    }

    public void setMonto(float Monto) {
        this.Monto = Monto;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }
    
    
    
}
