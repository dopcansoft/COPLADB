/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;
import copladb.DAO.Conexion.*;
import copladb.DTO.cliente;

/**
 *
 * @author dopcan
 */
public class clienteDAO {
    
    public List<cliente> consultarClientes( List<String> where){
        List<cliente>  lstClientes= new ArrayList<>();

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
        String sql = "SELECT idCliente, Nombre, Direccion, Telefono, TipoCLiente from Clientes where "+Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                cliente clte = new cliente();
                clte.setIdCliente(rs.getInt(1));
                clte.setNombre(rs.getString(2));
                clte.setDireccion(rs.getString(3));
                clte.setTelefono(rs.getString(4));
                clte.setTipoCLiente(rs.getString(5));
                lstClientes.add(clte);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstClientes;
    }  

    public int insertarCliente(String nombreP, String direccionP, String telefonoP, String tipoCLienteP){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("INSERT INTO Clientes(nombre, Direccion, Telefono, TipoCLiente) "
                + "VALUES (?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, nombreP);
             pstmt.setString(2, direccionP);
             pstmt.setString(3, telefonoP);
             pstmt.setString(4, tipoCLienteP);
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

    public void modificarCliente(int idClienteP, String nombreP, String direccionP, String telefonoP, String tipoCLienteP) {
            String sql = "UPDATE Clientes SET nombre = ?, "
                    + "Direccion = ?, "
                    + "Telefono = ?, "
                    + "TipoCLiente = ?"
                    + "WHERE idCliente = ? ";
          Conexion conecta = new Conexion("cobranzaDB.db");
            try (Connection con = conecta.conectaDB();
                    PreparedStatement pstmt = con.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(1, nombreP);
                pstmt.setString(2, direccionP);
                pstmt.setString(3, telefonoP);
                pstmt.setString(4, tipoCLienteP);
                pstmt.setInt(5, idClienteP);
                // update 
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public void eliminarCliente(int id) {
        String sql = "DELETE FROM Clientes WHERE idCliente = ?";
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
       clienteDAO clteDAO = new clienteDAO();
       List<cliente> lstClientes = new ArrayList<>();
       cliente clteNvo = new cliente();
       
       clteNvo.setNombre("Carlos Noe Dominguez Perez");
       clteNvo.setDireccion("Norte 77");
       clteNvo.setTelefono("2294384923");
       clteNvo.setTipoCLiente("AlCorriente");
       
       clteDAO.insertarCliente(clteNvo.getNombre(), clteNvo.getDireccion(), clteNvo.getTelefono(), clteNvo.getTipoCLiente());
       
    }
    
}
