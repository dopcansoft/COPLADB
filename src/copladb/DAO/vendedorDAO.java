/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DAO;

import copladb.DTO.cliente;
import copladb.DTO.vendedor;
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
public class vendedorDAO {

public List<vendedor> consultarVendedor(List<String> where){
    List<vendedor> lstVendedor = new ArrayList<>();
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
        String sql = "SELECT idVendedor, Nombre, Direccion, Telefono from Vendedores where "+Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                vendedor vend = new vendedor();
                vend.setIdVendedor(rs.getInt(1));
                vend.setNombre(rs.getString(2));
                vend.setDireccion(rs.getString(3));
                vend.setTelefono(rs.getString(4));
                lstVendedor.add(vend);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstVendedor;
    
}

public List<String> ListarVendedores(){
    List<String> lstVendedor = new ArrayList<>();
        //StringBuilder sql= null;
        StringBuilder Filtro = new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");
        String sql = "Select distinct(Nombre) from Vendedores ";
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                String strVend = new String();
                strVend = rs.getString(1);
                lstVendedor.add(strVend);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstVendedor;
    
}

public int insertarVendedor(String nombreP, String direccionP, String telefonoP){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("INSERT INTO Vendedores(Nombre, Direccion, Telefono) "
                + "VALUES (?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, nombreP);
             pstmt.setString(2, direccionP);
             pstmt.setString(3, telefonoP);
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

public void modificarVendedor(int idVendedorP, String nombreP, String direccionP, String telefonoP){
            String sql = "UPDATE Vendedores SET nombre = ?, "
                    + "Direccion = ?, "
                    + "Telefono = ? "
                    + "WHERE idVendedor = ? ";
          Conexion conecta = new Conexion("cobranzaDB.db");
            try (Connection con = conecta.conectaDB();
                    PreparedStatement pstmt = con.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(1, nombreP);
                pstmt.setString(2, direccionP);
                pstmt.setString(3, telefonoP);
                pstmt.setInt(4, idVendedorP);
                // update 
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }    
}

public void eliminarVendedor(int id){
    
        String sql = "DELETE FROM Vendedores WHERE idVendedor = ?";
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
       vendedorDAO venDAO = new vendedorDAO();
       List<vendedor> lstvendedores = new ArrayList<>();
       vendedor vendedorNvo = new vendedor();
       
       vendedorNvo.setNombre("Alicia Mil Maravillas");
       vendedorNvo.setDireccion("Sur 89");
       vendedorNvo.setTelefono("2294386923");
       vendedorNvo.setIdVendedor(1);
       
       venDAO.modificarVendedor(vendedorNvo.getIdVendedor(), vendedorNvo.getNombre(), vendedorNvo.getDireccion(), vendedorNvo.getTelefono());
       //venDAO.insertarVendedor(vendedorNvo.getNombre(), vendedorNvo.getDireccion(), vendedorNvo.getTelefono());
}
    
}
