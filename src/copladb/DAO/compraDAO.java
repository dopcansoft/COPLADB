package copladb.DAO;

import copladb.DTO.compra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class compraDAO {
    
    public static compraDAO instance = null;
    public static compraDAO getInstance(){
        if (instance == null) instance = new compraDAO();
        return instance;
    }
    private Connection conn;
    private compra identidadCompra;
    
    public List<compra> consultaCompra( List<String> where){
        
        List<compra>  lstCompra= new ArrayList<>();
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }		
        String sql = "SELECT id_compra, fecha, folio, codigo_factura FROM compra where flag !=3 AND "+Filtro.toString();
        Conexion conecta = new Conexion("cobranzaDB.db");
        System.out.println(sql);

        try (Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql);){
             while (rs.next()){
               identidadCompra = new compra();
               identidadCompra.setId_compra(rs.getInt("id_compra"));
               identidadCompra.setFecha(rs.getString("fecha"));
               identidadCompra.setFolio(rs.getString("folio"));
               identidadCompra.setCodigo_factura(rs.getString("codigo_factura"));

               lstCompra.add(identidadCompra);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstCompra;
    }    
    
    public int insertarCompra(compra comp){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("INSERT INTO compra( fecha, folio, codigo_factura, flag) "
                + "VALUES (?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();

             PreparedStatement pstmt = con.prepareStatement(sqrString.toString(), Statement.RETURN_GENERATED_KEYS);
             pstmt.setString(1, comp.getFecha());
             pstmt.setString(2, comp.getFolio());
             pstmt.setString(3, comp.getCodigo_factura());
             pstmt.setInt(4, 1);
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
    
    public int modificarCompra(compra comp){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("UPDATE compra(fecha, folio, codigo_factura, flag) "
                + "VALUES (?, ?, ?, ?) WHERE id_compra=?");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, comp.getFecha());
             pstmt.setString(2, comp.getFolio());
             pstmt.setString(3, comp.getCodigo_factura());
             pstmt.setInt(4, 2);
             pstmt.setInt(5, comp.getId_compra());
             regs = pstmt.executeUpdate();
             
             con.close();
        }catch (SQLException e){
           System.out.println(e.getMessage());
           return -1;
        }
        return regs;
      }
    
    public void borrarCompra(int id){
        String sql = "UPDATE compra SET flag = 3 WHERE id_compra = ?";
        Conexion conecta = new Conexion("cobranzaDB.db");
        
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void deleteCompra(int id){
        String sql = "DELETE FROM compra WHERE id_compra = ?";
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
      compraDAO compDAO = new compraDAO();

      List<compra> lstCompra= new ArrayList<>();
      List<String> lstWhere = new ArrayList<>();
      lstWhere.add("id_compra  = 1");
      lstCompra = compDAO.consultaCompra(lstWhere);
      System.out.print("Registro: "+String.valueOf(lstCompra.size()));
      for (compra i:lstCompra){
           System.out.println(i.getId_compra()+":"+i.getFecha()+":"+i.getCodigo_factura());
      }
    }
    
}
