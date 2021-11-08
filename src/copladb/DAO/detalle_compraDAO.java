package copladb.DAO;

import copladb.DTO.detalle_compra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class detalle_compraDAO {
    
    public static detalle_compraDAO instance = null;
    public static detalle_compraDAO getInstance(){
        if (instance == null) instance = new detalle_compraDAO();
        return instance;
    }
    private Connection conn;
    private detalle_compra identidadDetComp;
    
    public List<detalle_compra> consultaDetComp( List<String> where){

        List<detalle_compra>  lstDetalle= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_detalle_compra, id_compra, codigo_prod, cantidad, costo_compra FROM detalle_compra where flag != 3 AND "+Filtro.toString();
        Conexion conecta = new Conexion("cobranzaDB.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadDetComp = new detalle_compra();
               identidadDetComp.setId_detalle_compra(rs.getInt("id_detalle_compra"));
               identidadDetComp.setId_compra(rs.getInt("id_compra"));
               identidadDetComp.setCodigo_prod(rs.getString("codigo_prod"));
               identidadDetComp.setCantidad(rs.getInt("cantidad"));
               identidadDetComp.setCosto_compra(rs.getFloat("costo_compra"));
               lstDetalle.add(identidadDetComp);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstDetalle;
    }    
    
    public int insertarDetComp(detalle_compra detalle){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("INSERT INTO detalle_compra(id_compra, codigo_prod, cantidad, costo_compra, flag) "
                + "VALUES (?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setInt(1, detalle.getId_compra());
             pstmt.setString(2, detalle.getCodigo_prod());
             pstmt.setInt(3, detalle.getCantidad());
             pstmt.setFloat(4, detalle.getCosto_compra());
             pstmt.setInt(5, 1);
             regs = pstmt.executeUpdate();
             ResultSet rs = pstmt.getGeneratedKeys();
             if (regs== 1){
               regs = rs.getInt(1);
             }

             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
    }
    
    public int modificarDetComp(detalle_compra detalle){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("UPDATE detalle_compra SET id_compra = ?,"
                + " codigo_prod = ?, "
                + "cantidad = ?, "
                + "costo_compra = ?, "
                + "flag = ? "
                + "WHERE id_detalle_compra = ?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setInt(1, detalle.getId_compra());
             pstmt.setString(2, detalle.getCodigo_prod());
             pstmt.setInt(3, detalle.getCantidad());
             pstmt.setFloat(4, detalle.getCosto_compra());
             pstmt.setInt(5, 2);
             pstmt.setInt(6, detalle.getId_detalle_compra());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarDetComp(int id){
        String sql = "UPDATE detalle_compra SET flag = 3 WHERE id_detalle_compra = ?";
        Conexion conecta = new Conexion("cobranzaDB.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteDetComp(int id){
        String sql = "DELETE FROM detalle_compra WHERE id_detalle_compra = ?";
        Conexion conecta = new Conexion("cobranzaDB.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] Args){
      detalle_compraDAO detalleDAO = new detalle_compraDAO();

      List<detalle_compra> lstDetalle = new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_detalle_compra  = 1");
      lstDetalle = detalleDAO.consultaDetComp(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstDetalle.size()));
      for (detalle_compra i:lstDetalle){
           System.out.println(i.getId_detalle_compra()+":"+i.getId_compra()+":"+i.getCodigo_prod()+":"+i.getCantidad()+":"+i.getCosto_compra());
      }
    }
    
}
