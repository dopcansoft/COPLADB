
package copladb.DTO;

public class categoria {
    
    private int id_categoria;
    private String categoria;
    private int parent_id;
    private int bandera;
    
    public int getId_categoria(){
		return id_categoria;
    }


    public void setId_categoria(int newVal){
            id_categoria = newVal;
    }
    
    public String getCategoria(){
		return categoria;
    }


    public void setCategoria(String newVal){
            categoria = newVal;
    }
    
    public int getParent_id(){
		return parent_id;
    }


    public void setParent_id(int newVal){
            parent_id = newVal;
    }

    public int getBandera() {
        return bandera;
    }

    public void setBandera(int bandera) {
        this.bandera = bandera;
    }
  
}
