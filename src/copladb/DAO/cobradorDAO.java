/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DAO;

import copladb.DTO.cobrador;
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
public class cobradorDAO {

public List<cobrador> consultarCobradores(List<String> where){
    List<cobrador> lstCobrador = new ArrayList<>();
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
        String sql = "SELECT idCobrador, Nombre, Direccion, Telefono from Cobradores where "+Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                cobrador cobr = new cobrador();
                cobr.setIdCobrador(rs.getInt(1));
                cobr.setNombre(rs.getString(2));
                cobr.setDireccion(rs.getString(3));
                cobr.setTelefono(rs.getString(4));
                lstCobrador.add(cobr);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstCobrador;
    
}

public List<String> consultarNombreCobradores(List<String> where){
    List<String> lstNombreCobrador = new ArrayList<>();
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
        String sql = "SELECT Nombre from Cobradores where "+Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                String strNombreCobrador = new String();
                strNombreCobrador = rs.getString(1);
                lstNombreCobrador.add(strNombreCobrador);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstNombreCobrador;
    
}

public int insertarCobrador(String nombreP, String direccionP, String telefonoP){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("INSERT INTO Cobradores(Nombre, Direccion, Telefono) "
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

public void modificarVendedor(int idCobradorP, String nombreP, String direccionP, String telefonoP){
            String sql = "UPDATE Cobradores SET nombre = ?, "
                    + "Direccion = ?, "
                    + "Telefono = ? "
                    + "WHERE idCobrador = ? ";
          Conexion conecta = new Conexion("cobranzaDB.db");
            try (Connection con = conecta.conectaDB();
                    PreparedStatement pstmt = con.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(1, nombreP);
                pstmt.setString(2, direccionP);
                pstmt.setString(3, telefonoP);
                pstmt.setInt(4, idCobradorP);
                // update 
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }    
}

public void eliminarVendedor(int id){
    
        String sql = "DELETE FROM Cobradores WHERE idCobrador = ?";
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
       cobradorDAO venDAO = new cobradorDAO();
       List<cobrador> lstvendedores = new ArrayList<>();
       cobrador vendedorNvo = new cobrador();
       
       vendedorNvo.setNombre("Alicia Mil Maravillas");
       vendedorNvo.setDireccion("Sur 89");
       vendedorNvo.setTelefono("2294386923");
       vendedorNvo.setIdCobrador(1);
       
       venDAO.modificarVendedor(vendedorNvo.getIdCobrador(), vendedorNvo.getNombre(), vendedorNvo.getDireccion(), vendedorNvo.getTelefono());
       //venDAO.insertarVendedor(vendedorNvo.getNombre(), vendedorNvo.getDireccion(), vendedorNvo.getTelefono());
}
    
}
