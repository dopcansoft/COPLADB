package copladb.DTO;

/**
 *
 * @author Alexandra
 */
public class usuario {
    
    int id_usuario;
    String nombre;
    String usuario;
    String clave_acceso;
    String puesto;
    String direccion;
    String telefono;
    String estatus;
    
    public int getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getUsuario(){
        return usuario;
    }
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    
    public String getClaveAcceso(){
        return clave_acceso;
    }
    public void setClaveAcceso(String clave_acceso){
        this.clave_acceso = clave_acceso;
    }
    
    public String getPuesto(){
        return puesto;
    }
    public void setPuesto(String puesto){
        this.puesto = puesto;
    }
    
    public String getDireccion(){
        return direccion;
    }
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
    
    public String getTelefono(){
        return telefono;
    }
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public String getEstatus(){
        return estatus;
    }
    public void setEstatus(String estatus){
        this.estatus = estatus;
    }
    
}
