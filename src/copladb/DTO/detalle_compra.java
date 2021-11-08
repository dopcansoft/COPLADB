package copladb.DTO;



/**
 * Almacena informacion de los productos comprados
 * @author Marcos
 * @version 1.0
 * @created 24-ene.-2020 10:32:35 p. m.
 */
public class detalle_compra {

	private int id_detalle_compra;
	private int id_compra;
        private String descripcion;
	private String codigo_prod;
	private int cantidad;
	private float costo_compra;
        private int bandera;

    public int getId_detalle_compra() {
        return id_detalle_compra;
    }

    public void setId_detalle_compra(int id_detalle_compra) {
        this.id_detalle_compra = id_detalle_compra;
    }

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    
    public String getCodigo_prod() {
        return codigo_prod;
    }

    public void setCodigo_prod(String codigo_prod) {
        this.codigo_prod = codigo_prod;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getCosto_compra() {
        return costo_compra;
    }

    public void setCosto_compra(float costo_compra) {
        this.costo_compra = costo_compra;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }

}//end detalle_compra