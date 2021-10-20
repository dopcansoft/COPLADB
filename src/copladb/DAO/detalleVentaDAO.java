/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DAO;

import copladb.DTO.detalle_venta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author i7
 */
public class detalleVentaDAO {
    public List<detalle_venta> consultarDetalleVenta( List<String> where){
        List<detalle_venta>  lstDetalleVenta= new ArrayList<>();

        //StringBuilder sql= null;
        StringBuilder Filtro = new StringBuilder();
        Filtro.append(where.get(0));
        where.remove(0);
        if (!where.isEmpty()){
                for (String i:where){
                        Filtro.append(" AND "+i);
                }			
        }
        Conexion conecta = new Conexion("cobranzaDB.db");
        //codigo_cliente, nombre, razon_social, domicilio_fiscal, telefono, rfc, email
        String sql = "SELECT id_detalle_venta, descprod, cantidad, precio_venta, idTarjeta, codigo_prod from detalle_venta where "+Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                detalle_venta detVta = new detalle_venta();
                detVta.setId_detalle_venta(rs.getInt(1));
                detVta.setDescprod(rs.getString(2));
                detVta.setCantidad(rs.getInt(3));
                detVta.setPrecio_venta(rs.getFloat(4));
                detVta.setIdTarjeta(rs.getInt(5));
                detVta.setCodigo_prod(rs.getString(6));
                lstDetalleVenta.add(detVta);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstDetalleVenta;
    }  


    public int insertarDetalleVenta(String descprodP, int cantidadP, float precio_ventaP, int idTarjetaP, String codigo_prodP){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("INSERT INTO detalle_venta(descprod, cantidad, precio_venta, idTarjeta, codigo_prod ) "
                + "VALUES (?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, descprodP);
             pstmt.setInt(2, cantidadP);
             pstmt.setFloat(3, precio_ventaP);
             pstmt.setInt(4, idTarjetaP);
             pstmt.setString(5, codigo_prodP);
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

    public void modificarDetalleVenta(int id_detalle_ventaP, String descprodP, int cantidadP, float precio_ventaP, 
            int idTarjetaP, String codigo_prodP) {
            String sql = "UPDATE detalle_venta SET descprod = ?, "
                    + "cantidad = ?, "
                    + "precio_venta = ?, "
                    + "idTarjeta = ?,"
                    + "codigo_prod = ?"
                    + "WHERE id_detalle_venta = ? ";
          Conexion conecta = new Conexion("cobranzaDB.db");
            try (Connection con = conecta.conectaDB();
                    PreparedStatement pstmt = con.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(1, descprodP);
                pstmt.setInt(2, cantidadP);
                pstmt.setFloat(3, precio_ventaP);
                pstmt.setInt(4, idTarjetaP);
                pstmt.setString(5, codigo_prodP);
                pstmt.setInt(6, id_detalle_ventaP);
                // update 
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public void eliminarCliente(int id) {
        String sql = "DELETE FROM detalle_venta WHERE idCliente = ?";
        Conexion conecta = new Conexion("cobranzaDB.db");
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /*public static void main(String[] args){
       clienteDAO clteDAO = new clienteDAO();
       List<cliente> lstClientes = new ArrayList<>();
       cliente clteNvo = new cliente();
       
       clteNvo.setNombre("Carlos Noe Dominguez Perez");
       clteNvo.setDireccion("Norte 77");
       clteNvo.setTelefono("2294384923");
       clteNvo.setTipoCLiente("AlCorriente");
       
       clteDAO.insertarCliente(clteNvo.getNombre(), clteNvo.getDireccion(), clteNvo.getTelefono(), clteNvo.getTipoCLiente());
       
    }*/
}
