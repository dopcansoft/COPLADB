/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DTO;

import java.text.DecimalFormat;

/**
 *
 * @author i7
 */
public class detalle_venta {
    private int id_detalle_venta;
    private String descprod;
    private int cantidad;
    private float precio_venta;
    private float subTotal;
    private int idTarjeta;
    private String codigo_prod;

    public int getId_detalle_venta() {
        return id_detalle_venta;
    }

    public void setId_detalle_venta(int id_detalle_venta) {
        this.id_detalle_venta = id_detalle_venta;
    }

    public String getDescprod() {
        return descprod;
    }

    public void setDescprod(String descprod) {
        this.descprod = descprod;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(float precio_venta) {
        this.precio_venta = precio_venta;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(float subTotal) {
        DecimalFormat f = new DecimalFormat("0.00");
        String strTemp = f.format(subTotal);
        this.subTotal = Float.parseFloat(strTemp);
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getCodigo_prod() {
        return codigo_prod;
    }

    public void setCodigo_prod(String codigo_prod) {
        this.codigo_prod = codigo_prod;
    }
    
    
}
