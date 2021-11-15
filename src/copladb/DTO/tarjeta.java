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
public class tarjeta {

    private int idTarjeta;
    private String folio;
    private Integer idCliente;
    private String nomCliente;
    private String dirCliente;
    private float precio;
    private float enganche;
    private Integer idVendedor;
    private String nomVendedor;
    private String clasificacion;
    private String tipoPago;
    private String region;
    private String diaCobro;
    private float enganchePend;
    private float saldo;
    private String fecha;
    private float pagos;
    private String tipoPrecio;
    private String fechaUltimoPago;
    private float ultimoPago;
    private String estado;

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomCliente() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente = nomCliente;
    }

    public String getDirCliente() {
        return dirCliente;
    }

    public void setDirCliente(String dirCliente) {
        this.dirCliente = dirCliente;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getEnganche() {
        return enganche;
    }

    public void setEnganche(float enganche) {
        this.enganche = enganche;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNomVendedor() {
        return nomVendedor;
    }

    public void setNomVendedor(String nomVendedor) {
        this.nomVendedor = nomVendedor;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDiaCobro() {
        return diaCobro;
    }

    public void setDiaCobro(String diaCobro) {
        this.diaCobro = diaCobro;
    }

    public float getEnganchePend() {
        return enganchePend;
    }

    public void setEnganchePend(float enganchePend) {
        this.enganchePend = enganchePend;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getPagos() {
        return pagos;
    }

    public void setPagos(float pagos) {
        this.pagos = pagos;
    }

    public String getTipoPrecio() {
        return tipoPrecio;
    }

    public void setTipoPrecio(String tipoPrecio) {
        this.tipoPrecio = tipoPrecio;
    }

    public String getFechaUltimoPago() {
        return fechaUltimoPago;
    }

    public void setFechaUltimoPago(String fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }

    public float getUltimoPago() {
        return ultimoPago;
    }

    public void setUltimoPago(float ultimoPago) {
        this.ultimoPago = ultimoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}
