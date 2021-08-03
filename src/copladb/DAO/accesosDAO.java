package copladb.DAO;

import copladb.DTO.accesos;
//import creser.DB.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.sql.Types.NULL;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Marcos
 */
public class accesosDAO {
    Connection connection = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Conexion conecta = new Conexion("cobranzaDB.db");   
         
    
    public List<accesos> consultaAccesos(List<String> where) {
        List<accesos>  lstAccesos= new ArrayList<>();
        
        StringBuilder Filtro = new StringBuilder();
        if (!where.isEmpty()){
            for (String i:where){
                Filtro.append(" AND "+i);
            }			
        }
        
        try 
        {
            String queryString = "SELECT * FROM accesos WHERE id_accesos IS NOT NULL "+Filtro.toString();
            System.out.println(queryString.toString());
            connection = conecta.conectaDB();
            pstmt = connection.prepareStatement(queryString);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                accesos dt = new accesos();
                dt.setId_accesos(rs.getInt("id_accesos"));
                dt.setId_usuario(rs.getInt("id_usuario"));
                dt.setNueva_tarjetas(rs.getBoolean("nueva_tarjetas"));
                dt.setModificar_consultar_Tarjeta(rs.getBoolean("modificar_consultar_Tarjeta"));
                dt.setEliminar_tarjeta(rs.getBoolean("eliminar_tarjeta"));
                dt.setAsignar_tarjeta(rs.getBoolean("asignar_tarjeta"));
                dt.setEstado_cobranza(rs.getBoolean("estado_cobranza"));
                dt.setAgregar_cliente(rs.getBoolean("agregar_cliente"));
                dt.setModificar_consultar_cliente(rs.getBoolean("modificar_consultar_cliente"));
                dt.setEliminar_cliente(rs.getBoolean("eliminar_cliente"));
                dt.setLista_negra_cliente(rs.getBoolean("lista_negra_cliente"));
                dt.setAgregar_vendedor(rs.getBoolean("agregar_vendedor"));
                dt.setModificar_consultar_vendedor(rs.getBoolean("modificar_consultar_vendedor"));
                dt.setEliminar_vendedor(rs.getBoolean("eliminar_vendedor"));
                dt.setAgregar_cobrador(rs.getBoolean("agregar_cobrador"));
                dt.setModificar_consultar_cobrador(rs.getBoolean("modificar_consultar_cobrador"));
                dt.setEliminar_cobrador(rs.getBoolean("eliminar_cobrador"));
                dt.setEstado_corte_cobrador(rs.getBoolean("estado_corte_cobrador"));                
                dt.setAgregar_producto(rs.getBoolean("agregar_producto"));
                dt.setModificar_consultar_producto(rs.getBoolean("modificar_consultar_producto"));
                dt.setEliminar_producto(rs.getBoolean("eliminar_producto"));
                dt.setEstadisticas_producto(rs.getBoolean("estadisticas_producto"));
                dt.setAgregar_gasto(rs.getBoolean("agregar_gasto"));
                dt.setModificar_consultar_gasto(rs.getBoolean("modificar_consultar_gasto"));
                dt.setEliminar_gasto(rs.getBoolean("eliminar_gasto"));
                dt.setAcumulado_caja(rs.getBoolean("acumulado_caja"));
                dt.setNuevo_usuario(rs.getBoolean("nuevo_usuario"));
                dt.setModificar_usuario(rs.getBoolean("modificar_usuario"));
                dt.setEliminar_usuario(rs.getBoolean("eliminar_usuario"));
                dt.setAcceso_usuarios(rs.getBoolean("acceso_usuarios"));
                dt.setGestion_bonos(rs.getBoolean("gestion_bonos"));
                lstAccesos.add(dt);
            }            
            rs.close();
            pstmt.close();
            connection.close(); 
        } catch (SQLException e) {
                e.printStackTrace();
        }
        
        return lstAccesos;
    }
    
    public int insertarAccesos(accesos dc){
        int regs=0;
        StringBuilder queryString= new StringBuilder(); 

    

        queryString.append("INSERT INTO accesos(id_usuario, nueva_tarjetas, modificar_consultar_Tarjeta,"
                + " eliminar_tarjeta, asignar_tarjeta, estado_cobranza, agregar_cliente,"
                + " modificar_consultar_cliente, eliminar_cliente, lista_negra_cliente,"
                + " agregar_vendedor, modificar_consultar_vendedor, eliminar_vendedor,"
                + " agregar_cobrador, modificar_consultar_cobrador, eliminar_cobrador,"
                + " estado_corte_cobrador, agregar_producto, modificar_consultar_producto,"
                + " eliminar_producto, estadisticas_producto, agregar_gasto, "
                + " modificar_consultar_gasto, eliminar_gasto, acumulado_caja, nuevo_usuario, "
                + " modificar_usuario, eliminar_usuario, acceso_usuarios, gestion_bonos) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        System.out.println(queryString.toString());
        
        try{
            connection = conecta.conectaDB();
            pstmt = connection.prepareStatement(queryString.toString(), Statement.RETURN_GENERATED_KEYS);
             
            pstmt.setInt(1, dc.getId_usuario());
            pstmt.setBoolean(2, dc.getNueva_tarjetas());
            pstmt.setBoolean(3, dc.getModificar_consultar_Tarjeta());
            pstmt.setBoolean(4, dc.getEliminar_tarjeta());
            pstmt.setBoolean(5, dc.getAsignar_tarjeta());
            pstmt.setBoolean(6, dc.getEstado_cobranza());
            pstmt.setBoolean(7, dc.getAgregar_cliente());
            pstmt.setBoolean(8, dc.getModificar_consultar_cliente());
            pstmt.setBoolean(9, dc.getEliminar_cliente());
            pstmt.setBoolean(10, dc.getLista_negra_cliente());
            pstmt.setBoolean(11, dc.getAgregar_vendedor());
            pstmt.setBoolean(12, dc.getModificar_consultar_vendedor());
            pstmt.setBoolean(13, dc.getEliminar_vendedor());
            pstmt.setBoolean(14, dc.getAgregar_cobrador());
            pstmt.setBoolean(15, dc.getModificar_consultar_cobrador());
            pstmt.setBoolean(16, dc.getEliminar_cobrador());
            pstmt.setBoolean(17, dc.getEstado_corte_cobrador());
            pstmt.setBoolean(18, dc.getAgregar_producto());
            pstmt.setBoolean(19, dc.getModificar_consultar_producto());            
            pstmt.setBoolean(20, dc.getEliminar_producto());
            pstmt.setBoolean(21, dc.getEstadisticas_producto());
            pstmt.setBoolean(22, dc.getAgregar_gasto());
            pstmt.setBoolean(23, dc.getModificar_consultar_gasto());
            pstmt.setBoolean(24, dc.getEliminar_gasto());
            pstmt.setBoolean(25, dc.getAcumulado_caja());
            pstmt.setBoolean(26, dc.getNuevo_usuario());
            pstmt.setBoolean(27, dc.getModificar_usuario());
            pstmt.setBoolean(28, dc.getEliminar_usuario());
            pstmt.setBoolean(29, dc.getAcceso_usuarios());
            pstmt.setBoolean(30, dc.getGestion_bonos());
            regs = pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()&& regs==1) {
                regs = rs.getInt(1);
                System.out.println("Valor--> "+String.valueOf(regs));
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
    
    public int modificarAccesos(accesos dc){
        int regs=0;
        StringBuilder queryString= new StringBuilder(); 
      
        queryString.append("UPDATE accesos SET nueva_tarjetas=?,"
                + " modificar_consultar_Tarjeta=?,"
                + " eliminar_tarjeta=?,"
                + " asignar_tarjeta=?,"
                + " estado_cobranza=?,"
                + " agregar_cliente=?,"
                + " modificar_consultar_cliente=?,"
                + " eliminar_cliente=?,    "
                + " lista_negra_cliente=?,"
                + " agregar_vendedor=?,"
                + " modificar_consultar_vendedor=?,  "
                + " eliminar_vendedor=?,"
                + " agregar_cobrador=?,"
                + " modificar_consultar_cobrador=?,  "
                + " eliminar_cobrador=?,"
                + " estado_corte_cobrador=?,"
                + " agregar_producto=?,  "
                + " modificar_consultar_producto=?,  "
                + " eliminar_producto=?,"
                + " estadisticas_producto=?,"
                + " agregar_gasto=?,"
                + " modificar_consultar_gasto=?,"
                + " eliminar_gasto=?,"
                + " acumulado_caja=?,"
                + " nuevo_usuario=?,"
                + " modificar_usuario=?,"
                + " eliminar_usuario=?, "
                + " acceso_usuarios=?,"
                + " gestion_bonos=?"                
                + " WHERE id_usuario = ?");
        System.out.println(queryString.toString());
        
        try{
            connection = conecta.conectaDB();
            pstmt = connection.prepareStatement(queryString.toString());
              
            pstmt.setBoolean(1, dc.getNueva_tarjetas());
            pstmt.setBoolean(2, dc.getModificar_consultar_Tarjeta());
            pstmt.setBoolean(3, dc.getEliminar_tarjeta());
            pstmt.setBoolean(4, dc.getAsignar_tarjeta());
            pstmt.setBoolean(5, dc.getEstado_cobranza());
            pstmt.setBoolean(6, dc.getAgregar_cliente());
            pstmt.setBoolean(7, dc.getModificar_consultar_cliente());
            pstmt.setBoolean(8, dc.getEliminar_cliente());
            pstmt.setBoolean(9, dc.getLista_negra_cliente());
            pstmt.setBoolean(10, dc.getAgregar_vendedor());
            pstmt.setBoolean(11, dc.getModificar_consultar_vendedor());
            pstmt.setBoolean(12, dc.getEliminar_vendedor());
            pstmt.setBoolean(13, dc.getAgregar_cobrador());
            pstmt.setBoolean(14, dc.getModificar_consultar_cobrador());
            pstmt.setBoolean(15, dc.getEliminar_cobrador());
            pstmt.setBoolean(16, dc.getEstado_corte_cobrador());
            pstmt.setBoolean(17, dc.getAgregar_producto());
            pstmt.setBoolean(18, dc.getModificar_consultar_producto());            
            pstmt.setBoolean(19, dc.getEliminar_producto());
            pstmt.setBoolean(20, dc.getEstadisticas_producto());
            pstmt.setBoolean(21, dc.getAgregar_gasto());
            pstmt.setBoolean(22, dc.getModificar_consultar_gasto());
            pstmt.setBoolean(23, dc.getEliminar_gasto());
            pstmt.setBoolean(24, dc.getAcumulado_caja());
            pstmt.setBoolean(25, dc.getNuevo_usuario());
            pstmt.setBoolean(26, dc.getModificar_usuario());
            pstmt.setBoolean(27, dc.getEliminar_usuario());
            pstmt.setBoolean(28, dc.getAcceso_usuarios());
            pstmt.setBoolean(29, dc.getGestion_bonos());
            pstmt.setInt(30, dc.getId_usuario());
            regs = pstmt.executeUpdate();
            
            pstmt.close();
            connection.close(); 

        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        
        return regs;
    }
    
    public void borrarAccesos(int id){
        String queryString = "DELETE FROM accesos WHERE id_accesos = ?";
        
        try {
            connection = conecta.conectaDB();
            pstmt = connection.prepareStatement(queryString);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            
            pstmt.close();
            connection.close();
            
        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] Args){
      accesosDAO dcDAO = new accesosDAO();

      List<accesos> lstAccesos = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_accesos  = 1");
      lstAccesos = dcDAO.consultaAccesos(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstAccesos.size()));
      for (accesos i:lstAccesos){
           System.out.println(i.getId_accesos()+":"+i.getId_usuario()+":"+i.getAcceso_usuarios());
      }
    }
    
}
