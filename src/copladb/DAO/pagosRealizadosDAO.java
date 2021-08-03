/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DAO;

import copladb.DTO.pagosRealizados;
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
public class pagosRealizadosDAO {
    
    public List<pagosRealizados> consultarPagosRealizados( List<String> where){
        List<pagosRealizados>  lstPagosRea= new ArrayList<>();

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
        String sql = "SELECT idPagoRealizado, Fecha, monto, Tipo, idTarjeta from Pagos_realizados where "+Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                pagosRealizados pagoRea = new pagosRealizados();
                pagoRea.setIdPagoRealizado(rs.getInt(1));
                pagoRea.setFecha(rs.getString(2));
                pagoRea.setMonto(rs.getFloat(3));
                pagoRea.setTipo(rs.getString(4));
                pagoRea.setIdTarjeta(rs.getInt(5));
                lstPagosRea.add(pagoRea);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstPagosRea;
    }  


    public int insertarPagoRealizado(String FechaP, float montoP, String tipoP, int idTarjetaP){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("INSERT INTO Pagos_realizados(Fecha, monto, Tipo, idTarjeta) "
                + "VALUES (?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, FechaP);
             pstmt.setFloat(2, montoP);
             pstmt.setString(3, tipoP);
             pstmt.setInt(4, idTarjetaP);
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

    public void modificarPagoRealizados(int idPagoReaP, String FechaP, float montoP, String tipoP, int idTarjetaP) {
            String sql = "UPDATE Pagos_realizados SET Fecha = ?, "
                    + "monto = ?, "
                    + "Tipo = ?, "
                    + "idTarjeta = ? "
                    + "WHERE idPagoRealizado = ? ";
          Conexion conecta = new Conexion("cobranzaDB.db");
            try (Connection con = conecta.conectaDB();
                    PreparedStatement pstmt = con.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(1, FechaP);
                pstmt.setFloat(2, montoP);
                pstmt.setString(3, tipoP);
                pstmt.setInt(4, idTarjetaP);
                pstmt.setInt(5, idPagoReaP);
                // update 
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public void eliminarPagoRealizado(int id) {
        String sql = "DELETE FROM Pagos_realizados WHERE idPagoRealizado = ?";
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
