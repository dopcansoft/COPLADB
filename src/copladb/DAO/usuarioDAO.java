package copladb.DAO;

import copladb.DTO.usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Alexandra
 */
public class usuarioDAO {
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Conexion conecta = new Conexion("cobranzaDB.db");     


    public usuario consultaUnUsuario(String where) {
//      usuario datosUsuario= new ArrayList<>();
        usuario us = new usuario();
        StringBuilder queryString = new StringBuilder();
        
        
        queryString.append("SELECT id_usuario, nombre, usuario, clave_acceso, puesto, direccion, telefono, status "
                + "FROM usuario WHERE usuario=?");
        System.out.println(queryString.toString());

        try 
        {
            connection = conecta.conectaDB();
            pstmt = connection.prepareStatement(queryString.toString(), Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, where);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                
                us.setId_usuario(rs.getInt("id_usuario"));
                us.setNombre(rs.getString("nombre"));
                us.setUsuario(rs.getString("usuario"));
                us.setClaveAcceso(rs.getString("clave_acceso"));
                us.setPuesto(rs.getString("puesto"));
                us.setDireccion(rs.getString("direccion"));
                us.setTelefono(rs.getString("telefono"));
                us.setEstatus(rs.getString("status"));
                //datosUsuario.add(us);
            }            
            rs.close();
            pstmt.close();
            connection.close(); 
        } catch (SQLException e) {
                e.printStackTrace();
        }
        
        return us;
    }

    
    public List<usuario> consultaUsuario(List<String> where) {
        List<usuario>  lstLlamadas= new ArrayList<>();
        
        StringBuilder Filtro = new StringBuilder();
        if (!where.isEmpty()){
            for (String i:where){
                Filtro.append(" AND "+i);
            }			
        }        
        try 
        {
            //String queryString = "SELECT id_usuario, nombre, usuario, clave_acceso, puesto, direccion, telefono"
            //        + " FROM usuario WHERE id_usuario IS NOT NULL "+Filtro.toString();
            String queryString = "SELECT id_usuario, nombre, usuario, clave_acceso, puesto, direccion, telefono"
                    + " FROM usuario WHERE id_usuario IS NOT NULL "+Filtro.toString();
            connection = conecta.conectaDB();
            System.out.println(queryString.toString());
            pstmt = connection.prepareStatement(queryString.toString(), Statement.RETURN_GENERATED_KEYS);
            //pstmt = connection.prepareStatement(queryString);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                usuario us = new usuario();
                us.setId_usuario(rs.getInt("id_usuario"));
                us.setNombre(rs.getString("nombre"));
                us.setUsuario(rs.getString("usuario"));
                us.setClaveAcceso(rs.getString("clave_acceso"));
                us.setPuesto(rs.getString("puesto"));
                us.setDireccion(rs.getString("direccion"));
                us.setTelefono(rs.getString("telefono"));
                lstLlamadas.add(us);
            }
            
            rs.close();
            pstmt.close();
            connection.close(); 
        } catch (SQLException e) {
                e.printStackTrace();
        }
        
        return lstLlamadas;
    }
    
    public int insertarUsuario(usuario us){
        int regs=0;
        StringBuilder queryString= new StringBuilder(); 
        queryString.append("INSERT INTO usuario(nombre, usuario, clave_acceso, puesto, direccion, telefono, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)");
        System.out.print(queryString.toString());
        
        try{
            connection = conecta.conectaDB();
            //pstmt = connection.prepareStatement(queryString.toString());
            pstmt = connection.prepareStatement(queryString.toString(), Statement.RETURN_GENERATED_KEYS);
             
            pstmt.setString(1, us.getNombre());
            pstmt.setString(2, us.getUsuario());
            pstmt.setString(3, us.getClaveAcceso());
            pstmt.setString(4, us.getPuesto());
            pstmt.setString(5, us.getDireccion());
            pstmt.setString(6, us.getTelefono());
            pstmt.setString(7, us.getEstatus());
            
            regs = pstmt.executeUpdate();
            //regs = pstmt.executeUpdate(pstmt.toString(), Statement.RETURN_GENERATED_KEYS);
            rs = pstmt.getGeneratedKeys();
            if (regs== 1){
                regs = rs.getInt(1);
            }            
            rs.close();
            pstmt.close();
            connection.close();

        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }         
        return regs;
    }

    public int suspenderUsuario(usuario us){
        int regs=0;
        StringBuilder queryString= new StringBuilder(); 
        queryString.append("UPDATE usuario SET status = ? "
                + "WHERE usuario = ?");
        System.out.print(queryString.toString());
        
        try{
            connection = conecta.conectaDB();
            pstmt = connection.prepareStatement(queryString.toString(), Statement.RETURN_GENERATED_KEYS);
            //pstmt = connection.prepareStatement(queryString.toString());
              
            pstmt.setString(1, us.getEstatus());
            pstmt.setString(2, us.getUsuario());
            
            regs = pstmt.executeUpdate();
            
            pstmt.close();
            connection.close(); 

        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        
        return regs;
    }

    public int modificarUsuario(usuario us){
        int regs=0;
        StringBuilder queryString= new StringBuilder(); 
        queryString.append("UPDATE usuario SET nombre=?, clave_acceso=?, puesto=?, direccion=?, telefono=?, status=? "
                + " WHERE usuario = ?");
        System.out.println(queryString.toString());
        
        try{
            connection = conecta.conectaDB();
            pstmt = connection.prepareStatement(queryString.toString());
              
            pstmt.setString(1, us.getNombre());
            pstmt.setString(2, us.getClaveAcceso());
            pstmt.setString(3, us.getPuesto());
            pstmt.setString(4, us.getDireccion());
            pstmt.setString(5, us.getTelefono());
            pstmt.setString(6, us.getEstatus());
            pstmt.setString(7, us.getUsuario());
            
            regs = pstmt.executeUpdate();
            
            pstmt.close();
            connection.close(); 

        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        
        return regs;
    }
    
    public void borrarUsuario(String usuarioelim){
        String queryString = "DELETE FROM usuario WHERE usuario = ?";
        
        try {
            connection = conecta.conectaDB();
            pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, usuarioelim);
            pstmt.executeUpdate();
            
            pstmt.close();
            connection.close();
            
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
    }
  
    public static void main(String[] Args){
      usuarioDAO usDAO = new usuarioDAO();

      List<usuario> lstUsuario= new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_usuario  = 1");
      lstUsuario = usDAO.consultaUsuario(lstWhere);
      System.out.println("Registro: "+String.valueOf(lstUsuario.size()));
      for (usuario i:lstUsuario){
           System.out.println(i.getId_usuario()+":"+i.getUsuario()+":"+i.getClaveAcceso()+":"+i.getPuesto()+":"+i.getDireccion()+":"+i.getTelefono());
      }
    }
    
}
