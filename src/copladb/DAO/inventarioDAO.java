/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DAO;

import copladb.DTO.inventario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author i7
 */
public class inventarioDAO {
    
    public List<inventario> consultarInventario( List<String> where){
        List<inventario>  lstInventario= new ArrayList<>();

        //StringBuilder sql= null;
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }
        
        // idProducto, CodigoProducto, Existencia, Descripcion, UnidadMedida, PrecioContado, Precio_crediContado, Precio_credito, Proveedor, idCategoria, 
        Conexion conecta = new Conexion("cobranzaDB.db");
        String sql = "SELECT idProducto, CodigoProducto, Existencia, Descripcion, UnidadMedida, PrecioContado, Precio_crediContado, "
                + "Precio_credito, Proveedor, idCategoria  from inventario where " +Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                inventario inv = new inventario();
                inv.setIdProducto(rs.getInt(1));
                inv.setCodigoProducto(rs.getString(2));
                inv.setExistencia(rs.getInt(3));
                inv.setDescripcion(rs.getString(4));
                inv.setUnidadMedida(rs.getString(5));
                inv.setPrecioContado(rs.getFloat(6));
                inv.setPrecio_crediContado(rs.getFloat(7));
                inv.setPrecio_credito(rs.getFloat(8));
                inv.setProveedor(rs.getString(9));
                inv.setIdCategoria(rs.getString(10));
                lstInventario.add(inv);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstInventario;
    }  

    public int insertarInventario (String CodigoProducto, int Existencia, String Descripcion, String UnidadMedida, 
            float PrecioContado, float Precio_crediContado, float Precio_credito, String Proveedor, String idCategoria ){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("INSERT INTO inventario(CodigoProducto, Existencia, Descripcion, UnidadMedida, "
                + "PrecioContado, Precio_crediContado, Precio_credito, Proveedor, idCategoria) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, CodigoProducto);
             pstmt.setInt(2, Existencia);
             pstmt.setString(3, Descripcion);
             pstmt.setString(4, UnidadMedida);
             pstmt.setFloat(5, PrecioContado);
             pstmt.setFloat(6, Precio_crediContado);
             pstmt.setFloat(7, Precio_credito);
             pstmt.setString(8, Proveedor);
             pstmt.setString(9, idCategoria);
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

    public void modificarInventario(int idProducto, String CodigoProducto, int Existencia, String Descripcion, String UnidadMedida, 
            float PrecioContado, float Precio_crediContado, float Precio_credito, String Proveedor, String idCategoria) {
            String sql = "UPDATE inventario SET CodigoProducto = ?, "
                    + "Existencia = ?, "
                    + "Descripcion = ?, "
                    + "UnidadMedida = ?, "
                    + "PrecioContado = ?, "
                    + "Precio_crediContado = ?, "
                    + "Precio_credito = ?, "
                    + "Proveedor = ?, "
                    + "idCategoria = ? "
                    + "WHERE idProducto = ? ";
          Conexion conecta = new Conexion("cobranzaDB.db");
            try (Connection con = conecta.conectaDB();
                    PreparedStatement pstmt = con.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(1, CodigoProducto);
                pstmt.setInt(2, Existencia);
                pstmt.setString(3, Descripcion);
                pstmt.setString(4, UnidadMedida);
                pstmt.setFloat(5, PrecioContado);
                pstmt.setFloat(6, Precio_crediContado);
                pstmt.setFloat(7, Precio_credito);
                pstmt.setString(8, Proveedor);
                pstmt.setString(9, idCategoria);
                pstmt.setInt(10, idProducto);
                // update 
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    
    public void modificarExistenciaProducto(int idProducto, int existenciaP) {
      System.out.println("Id Producto:"+idProducto+" Existencia:"+ existenciaP);
      String sql = "UPDATE Inventario SET "
                + "Existencia = ? "
                + "WHERE idProducto = ?";  
      Conexion conecta = new Conexion("cobranzaDB.db");
        try (Connection con = conecta.conectaDB();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, existenciaP);
            pstmt.setInt(2, idProducto);
            // update 
            pstmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }    
    
    public void modificarExistenciaProducto(String CodigoProductoP, int existenciaP) {
      System.out.println("codigo Producto:"+CodigoProductoP+" Existencia:"+ existenciaP);
      String sql = "UPDATE Inventario SET "
                + "Existencia = ? "
                + "WHERE CodigoProducto = ?";  
      Conexion conecta = new Conexion("cobranzaDB.db");
        try (Connection con = conecta.conectaDB();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, existenciaP);
            pstmt.setString(2, CodigoProductoP);
            // update 
            pstmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }    

    public void eliminarProducto(int id) {
        String sql = "DELETE FROM inventario WHERE idProducto = ?";
        Conexion conecta = new Conexion("cobranzaDB.db");
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args){
       inventarioDAO invDAO = new inventarioDAO();
       List<inventario> lstInventario = new ArrayList<>();
       inventario prodNvo = new inventario();
       
        //prodNvo.setN("Carlos Noe Dominguez Perez");
        //prodNvo.setDireccion("Norte 77");
        //prodNvo.setTelefono("2294384923");
        //prodNvo.setTipoCLiente("AlCorriente");
        //float fp = 500.161035156f;
        //DecimalFormat df= new DecimalFormat("#.00");
        //System.out.println("Con Formato: "+df.format(fp));
        //System.out.println("Sin Formato: "+fp);
        //invDAO.insertarInventario("PROD_202011290332", 10, "Ventilador Pedestal Mca. Decker", null, 500.10f, 750.45f, 950.95f, null, 0);
        //invDAO.modificarInventario(3,"PROD_202011290334", 10, "Ventiladores Pedestal Mca. Decker", null, 580.10f, 750.45f, 950.95f, null, 0);
       invDAO.eliminarProducto(4);
    }    
    
}
