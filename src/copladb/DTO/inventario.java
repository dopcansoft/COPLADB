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
public class inventario {
    private int idProducto;
    private String CodigoProducto;
    private int Existencia;
    private String Descripcion;
    private String UnidadMedida;
    private float PrecioContado;
    private float Precio_crediContado;
    private float Precio_credito;
    private String Proveedor;
    private String idCategoria;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getCodigoProducto() {
        return CodigoProducto;
    }

    public void setCodigoProducto(String CodigoProducto) {
        this.CodigoProducto = CodigoProducto;
    }

    public int getExistencia() {
        return Existencia;
    }

    public void setExistencia(int Existencia) {
        this.Existencia = Existencia;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getUnidadMedida() {
        return UnidadMedida;
    }

    public void setUnidadMedida(String UnidadMedida) {
        this.UnidadMedida = UnidadMedida;
    }

    public float getPrecioContado() {
        return PrecioContado;
    }

    public void setPrecioContado(float PrecioContado) {
        this.PrecioContado = PrecioContado;
    }

    public float getPrecio_crediContado() {
        return Precio_crediContado;
    }

    public void setPrecio_crediContado(float Precio_crediContado) {
        this.Precio_crediContado = Precio_crediContado;
    }

    public float getPrecio_credito() {
        return Precio_credito;
    }

    public void setPrecio_credito(float Precio_credito) {
        this.Precio_credito = Precio_credito;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String Proveedor) {
        this.Proveedor = Proveedor;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }
    
       
}
