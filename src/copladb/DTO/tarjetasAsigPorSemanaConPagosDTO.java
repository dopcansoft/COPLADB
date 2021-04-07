/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DTO;

/**
 *
 * @author dopc781i
 */
public class tarjetasAsigPorSemanaConPagosDTO {
    private int idCobrador;
    private int idTarjeta;
    private String fechaAsignado;
    private String EstadoRecibido;
    private String EstadoEntregado;
    private int numTarjeta;
    private String year;
    private String semana;
    private String fechaPago;
    private float sumaMontos;
    private float cuantosPagos;
    private String Tipo;

    public int getIdCobrador() {
        return idCobrador;
    }

    public void setIdCobrador(int idCobrador) {
        this.idCobrador = idCobrador;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getFechaAsignado() {
        return fechaAsignado;
    }

    public void setFechaAsignado(String fechaAsignado) {
        this.fechaAsignado = fechaAsignado;
    }

    public String getEstadoRecibido() {
        return EstadoRecibido;
    }

    public void setEstadoRecibido(String EstadoRecibido) {
        this.EstadoRecibido = EstadoRecibido;
    }

    public String getEstadoEntregado() {
        return EstadoEntregado;
    }

    public void setEstadoEntregado(String EstadoEntregado) {
        this.EstadoEntregado = EstadoEntregado;
    }

    public int getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(int numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public float getSumaMontos() {
        return sumaMontos;
    }

    public void setSumaMontos(float sumaMontos) {
        this.sumaMontos = sumaMontos;
    }

    public float getCuantosPagos() {
        return cuantosPagos;
    }

    public void setCuantosPagos(float cuantosPagos) {
        this.cuantosPagos = cuantosPagos;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }
     
    
}
