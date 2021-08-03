/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DAO;

import copladb.DTO.cobrador;
import copladb.DTO.gasto;
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
public class gastoDAO {

public List<gasto> consultarGastos(List<String> where){
    List<gasto> lstGastos = new ArrayList<>();
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
        String sql = "SELECT idGasto, Fecha, Descripcion, monto from Gastos where "+Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                gasto gas = new gasto();
                gas.setIdGasto(rs.getInt(1));
                gas.setFecha(rs.getString(2));
                gas.setDescripcion(rs.getString(3));
                gas.setMonto(rs.getString(4));
                lstGastos.add(gas);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstGastos;
    
}

public int insertarGasto(String descripcionP, String fechaP, float montoP){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("INSERT INTO Gastos(Descripcion, Fecha, monto) "
                + "VALUES (?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, descripcionP);
             pstmt.setString(2, fechaP);
             pstmt.setFloat(3, montoP);
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

public void modificarGasto(int idGastoP, String descripcionP, String fechaP, float montoP){
            String sql = "UPDATE Gastos SET Descripcion = ?, "
                    + "Fecha = ?, "
                    + "monto = ? "
                    + "WHERE idGasto = ? ";
          Conexion conecta = new Conexion("cobranzaDB.db");
            try (Connection con = conecta.conectaDB();
                    PreparedStatement pstmt = con.prepareStatement(sql)) {

                // set the corresponding param
                pstmt.setString(1, descripcionP);
                pstmt.setString(2, fechaP);
                pstmt.setFloat(3, montoP);
                pstmt.setInt(4, idGastoP);
                // update 
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }    
}

public void eliminarGasto(int id){
    
        String sql = "DELETE FROM Gastos WHERE idGasto = ?";
        Conexion conecta = new Conexion("cobranzaDB.db");
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

}
    
}
