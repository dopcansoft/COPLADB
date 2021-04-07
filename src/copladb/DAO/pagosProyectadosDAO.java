/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DAO;

import copladb.DTO.pagosProyectados;
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
public class pagosProyectadosDAO {
    public List<pagosProyectados> consultarPagosProyectados( List<String> where){
        List<pagosProyectados>  lstPagosPro= new ArrayList<>();

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
        String sql = "SELECT idPagoProyectado, FechaPago, monto, idTarjeta, estado from Pagos_proyectados where "+Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                pagosProyectados pagPro = new pagosProyectados();
                pagPro.setIdPagoProyectado(rs.getInt(1));
                pagPro.setFechaPago(rs.getString(2));
                pagPro.setMonto(rs.getFloat(3));
                pagPro.setIdTarjeta(rs.getInt(4));
                pagPro.setEstado(rs.getString(5));
                lstPagosPro.add(pagPro);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstPagosPro;
    }  


    public int insertarPagoProyectado(String FechaPagoP, float montoP, int idTarjetaP, String estadoP){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("INSERT INTO Pagos_proyectados(FechaPago, monto, idTarjeta, estado) "
                + "VALUES (?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, FechaPagoP);
             pstmt.setFloat(2, montoP);
             pstmt.setInt(3, idTarjetaP);
             pstmt.setString(4, estadoP);
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

    public void modificarPagoProyectado(int idPagoProP, String FechaPagoP, float montoP, int idTarjetaP, String estadoP) {
            String sql = "UPDATE Pagos_proyectados SET FechaPago = ?, "
                    + "monto = ?, "
                    + "idTarjeta = ?, "
                    + "estado = ? "
                    + "WHERE idPagoProyectado = ? ";
          Conexion conecta = new Conexion("cobranzaDB.db");
            try (Connection con = conecta.conectaDB();
                    PreparedStatement pstmt = con.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(1, FechaPagoP);
                pstmt.setFloat(2, montoP);
                pstmt.setInt(3, idTarjetaP);
                pstmt.setString(4, estadoP);
                pstmt.setInt(5, idPagoProP);
                // update 
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public void eliminarPagoProyectado(int id) {
        String sql = "DELETE FROM Pagos_proyectados WHERE idPagoProyectado = ?";
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
       /*clienteDAO clteDAO = new clienteDAO();
       List<cliente> lstClientes = new ArrayList<>();
       cliente clteNvo = new cliente();
       
       clteNvo.setNombre("Carlos Noe Dominguez Perez");
       clteNvo.setDireccion("Norte 77");
       clteNvo.setTelefono("2294384923");
       clteNvo.setTipoCLiente("AlCorriente");
       
       clteDAO.insertarCliente(clteNvo.getNombre(), clteNvo.getDireccion(), clteNvo.getTelefono(), clteNvo.getTipoCLiente());*/
       
    }
    
}
