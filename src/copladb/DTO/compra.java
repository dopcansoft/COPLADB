package copladb.DTO;



/**
 * Almacena informacion de las compras de la sucursal
 * @author Marcos
 * @version 1.0
 * @created 24-ene.-2020 10:32:29 p. m.
 */
public class compra {

	private int id_compra;
	private String fecha;
        private String folio;
	private String codigo_factura;
        private int bandera;

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    
    public String getCodigo_factura() {
        return codigo_factura;
    }

    public void setCodigo_factura(String codigo_factura) {
        this.codigo_factura = codigo_factura;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }
        
        

}//end compra