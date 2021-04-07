/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DAO;

import copladb.DTO.tarjeta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import copladb.DAO.vendedorDAO;
import copladb.DTO.vendedor;
import copladb.DTO.cliente;
import copladb.DAO.clienteDAO;
import copladb.DTO.pagosRealizados;
import copladb.DAO.pagosRealizadosDAO;

/**
 *
 * @author i7
 */
public class tarjetaDAO {
    public List<tarjeta> consultarTarjetas( List<String> where){
        List<tarjeta>  lstTarjeta= new ArrayList<>();
        List<String> lstWhere = new ArrayList<>();
        List<vendedor> lstVendedor = new ArrayList<>();
        vendedorDAO vendD = new vendedorDAO();
        
        

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
        String sql = "SELECT idTarjeta, Folio, idCliente, Precio, Enganche, idVendedor, Clasificacion, TipoPago, Region, DiaCobro, "
                + "EnganchePend, Saldo, Fecha, Pagos, TipoPrecio from Tarjeta where "+Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                tarjeta tarj = new tarjeta();
                tarj.setIdTarjeta(rs.getInt(1));
                tarj.setFolio(rs.getString(2));
                tarj.setIdCliente(rs.getInt(3));
                tarj.setPrecio(rs.getFloat(4));
                tarj.setEnganche(rs.getFloat(5));
                tarj.setIdVendedor(rs.getInt(6));
                    lstWhere.clear();
                    lstWhere.add("idVendedor = "+rs.getInt(6));
                    lstVendedor = vendD.consultarVendedor(lstWhere);
                tarj.setNomVendedor(lstVendedor.get(0).getNombre());
                    
                tarj.setClasificacion(rs.getString(7));
                tarj.setTipoPago(rs.getString(8));
                tarj.setRegion(rs.getString(9));
                tarj.setDiaCobro(rs.getString(10));
                tarj.setEnganchePend(rs.getFloat(11));
                tarj.setSaldo(rs.getFloat(12));
                tarj.setFecha(rs.getString(13));
                tarj.setPagos(rs.getFloat(14));
                tarj.setTipoPrecio(rs.getString(15));

             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstTarjeta;
    }

    public List<tarjeta> consultarTarjetasPorCobrador( List<String> where){
        List<tarjeta>  lstTarjeta= new ArrayList<>();
        List<String> lstWhere = new ArrayList<>();
        List<vendedor> lstVendedor = new ArrayList<>();
        List<cliente> lstCliente = new ArrayList<>();
        cliente cliDTO = new cliente();
        vendedorDAO vendD = new vendedorDAO();
        clienteDAO cliDAO = new clienteDAO();
        List<pagosRealizados> lstPagosRealizados = new ArrayList<>();
        pagosRealizadosDAO pagRealizados = new pagosRealizadosDAO();        

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
        
        String sql = "select t1.idTarjeta, t1.Folio, t1.idCliente, t1.Precio, t1.Enganche, t1.idVendedor, t1.Clasificacion, t1.TipoPago, t1.Region, "
                + "t1.DiaCobro, t1.EnganchePend, t1.Saldo, t1.Fecha, t1.Pagos, t1.TipoPrecio, t2.idAsignacion, t2.idTarjeta, t2.idCobrador, t2.fecha, "
                + "t2.EstadoRecibido, t2.EstadoEntregado from Tarjeta as t1 inner join TarjetasAsignadas as t2 on t1.idTarjeta = t2.idTarjeta where "+Filtro.toString();       
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                tarjeta tarj = new tarjeta();
                tarj.setIdTarjeta(rs.getInt(1));
                tarj.setFolio(rs.getString(2));
                tarj.setIdCliente(rs.getInt(3));
                    lstWhere.clear();
                    lstWhere.add("idCliente = "+rs.getInt(3));
                    cliDTO = cliDAO.consultarClientes(lstWhere).get(0);
                tarj.setNomCliente(cliDTO.getNombre());
                
                tarj.setPrecio(rs.getFloat(4));
                tarj.setEnganche(rs.getFloat(5));
                tarj.setIdVendedor(rs.getInt(6));
                    lstWhere.clear();
                    lstWhere.add("idVendedor = "+rs.getInt(6));
                    lstVendedor = vendD.consultarVendedor(lstWhere);
                if (!lstVendedor.isEmpty()){
                   tarj.setNomVendedor(lstVendedor.get(0).getNombre());
                }
                tarj.setClasificacion(rs.getString(7));
                tarj.setTipoPago(rs.getString(8));
                tarj.setRegion(rs.getString(9));
                tarj.setDiaCobro(rs.getString(10));
                tarj.setEnganchePend(rs.getFloat(11));
                tarj.setSaldo(rs.getFloat(12));
                tarj.setFecha(rs.getString(13));
                tarj.setPagos(rs.getFloat(14));
                tarj.setTipoPrecio(rs.getString(15));
                    lstWhere.clear();
                    lstWhere.add("idTarjeta= "+rs.getInt(1));
                lstPagosRealizados=pagRealizados.consultarPagosRealizados(lstWhere);
                if (!lstPagosRealizados.isEmpty()){
                  tarj.setFechaUltimoPago(lstPagosRealizados.get(lstPagosRealizados.size()-1).getFecha());
                  tarj.setUltimoPago(lstPagosRealizados.get(lstPagosRealizados.size()-1).getMonto());
                }               
                
                lstTarjeta.add(tarj);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstTarjeta;
    }

    public List<tarjeta> consultarTarjetasCliente( List<String> where){
        List<tarjeta>  lstTarjeta= new ArrayList<>();

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
        String sql = "select t.idTarjeta, t.Folio, t.idCliente, C.idCliente, C.Nombre as nomCliente, t.Precio, "
                + "t.Enganche, t.idVendedor, t.Clasificacion, t.TipoPago, t.Region, t.DiaCobro, t.EnganchePend, t.Saldo, t.Fecha, t.Pagos, t.TipoPrecio "
                + "from Tarjeta as t left join Clientes as C on t.idCliente = C.idCliente where "+Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                tarjeta tarj = new tarjeta();
                tarj.setIdTarjeta(rs.getInt(1));
                tarj.setFolio(rs.getString(2));
                tarj.setIdCliente(rs.getInt(3));
                tarj.setNomCliente(rs.getString(5));
                tarj.setPrecio(rs.getFloat(6));
                tarj.setEnganche(rs.getFloat(7));
                tarj.setIdVendedor(rs.getInt(8));
                tarj.setClasificacion(rs.getString(9));
                tarj.setTipoPago(rs.getString(10));
                tarj.setRegion(rs.getString(11));
                tarj.setDiaCobro(rs.getString(12));
                tarj.setEnganchePend(rs.getFloat(13));
                tarj.setSaldo(rs.getFloat(14));
                tarj.setFecha(rs.getString(15));
                tarj.setPagos(rs.getFloat(16));
                tarj.setTipoPrecio(rs.getString(17));
                lstTarjeta.add(tarj);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstTarjeta;
    }
    
    public List<String> consultarDistinctRegion( ){
        List<String>  lstRegiones= new ArrayList<>();

       Conexion conecta = new Conexion("cobranzaDB.db");
        String sql = "Select distinct(Region) as Reg from Tarjeta ";
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                String str = "";
                str = rs.getString(1);
                lstRegiones.add(str);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstRegiones;
    }

    public int insertarTarjeta(String Folio, int idCliente, float Precio, float Enganche, int idVendedor, String Clasificacion, 
            String TipoPago, String Region, String DiaCobro, float EnganchePend, float Saldo, String Fecha, float Pagos, String TipoPre){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("INSERT INTO Tarjeta(Folio, idCliente, Precio, Enganche, idVendedor, Clasificacion, TipoPago, Region, "
                + "DiaCobro, EnganchePend, Saldo, Fecha, Pagos, TipoPrecio) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setString(1, Folio);
             pstmt.setInt(2, idCliente);
             pstmt.setFloat(3, Precio);
             pstmt.setFloat(4, Enganche);
             pstmt.setInt(5, idVendedor);
             pstmt.setString(6, Clasificacion);
             pstmt.setString(7, TipoPago);
             pstmt.setString(8, Region);
             pstmt.setString(9, DiaCobro);
             pstmt.setFloat(10, EnganchePend);
             pstmt.setFloat(11, Saldo);
             pstmt.setString(12, Fecha);
             pstmt.setFloat(13, Pagos);
             pstmt.setString(14, TipoPre);
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

    public void modificarTarjeta(int idTarjeta, String Folio, int idCliente, float Precio, float Enganche, int idVendedor, String Clasificacion, 
            String TipoPag, String Region, String DiaCobro, float EnganchePend, float Saldo, String Fecha, float Pagos, String TipoPre) {
        
            String sql = "UPDATE Tarjeta SET Folio = ?, "
                    + "idCliente = ?, "
                    + "Precio = ?, "
                    + "Enganche = ?, "
                    + "idVendedor = ?, "
                    + "Clasificacion = ?, "
                    + "TipoPago = ?, "
                    + "Region = ?, "
                    + "DiaCobro = ?, "
                    + "EnganchePend = ?, "
                    + "Saldo = ?, "
                    + "Fecha = ?, "
                    + "Pagos = ?, "
                    + "TipoPrecio = ? "
                    + "WHERE idTarjeta = ? ";
          Conexion conecta = new Conexion("cobranzaDB.db");
            try (Connection con = conecta.conectaDB();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, Folio);
                pstmt.setInt(2, idCliente);
                pstmt.setFloat(3, Precio);
                pstmt.setFloat(4, Enganche);
                pstmt.setInt(5, idVendedor);
                pstmt.setString(6, Clasificacion);
                pstmt.setString(7, TipoPag);
                pstmt.setString(8, Region);
                pstmt.setString(9, DiaCobro);
                pstmt.setFloat(10, EnganchePend);
                pstmt.setFloat(11, Saldo);
                pstmt.setString(12, Fecha);
                pstmt.setFloat(13, Pagos);
                pstmt.setString(14, TipoPre);
                pstmt.setInt(15, idTarjeta);
                System.out.println(sql);
                // update 
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    public void eliminarTarjeta(int id) {
        String sql = "DELETE FROM Tarjeta WHERE idTarjeta = "+String.valueOf(id)+"; ";
        System.out.println(sql);
        Conexion conecta = new Conexion("cobranzaDB.db");
        try {
            Connection con = conecta.conectaDB();
            Statement stmt  = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void eliminarPagoProyectados(int id) {
        String sql = "DELETE FROM Tarjeta WHERE idTarjeta = ?";
        System.out.println(sql);
        Conexion conecta = new Conexion("cobranzaDB.db");
        try (Connection con = conecta.conectaDB();
            PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.execute();
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
