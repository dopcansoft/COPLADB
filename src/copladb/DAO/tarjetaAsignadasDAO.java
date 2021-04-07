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
import copladb.DAO.clienteDAO;
import copladb.DTO.cliente;
import copladb.DTO.tarjetaAsignadas;
import copladb.DTO.tarjetasAsigPorSemanaConPagosDTO;
import copladb.DAO.cobradorDAO;
import copladb.DTO.cobrador;

/**
 *
 * @author i7
 */
public class tarjetaAsignadasDAO {
    public List<tarjetaAsignadas> consultarTarjetasAsignadas( List<String> where){
        List<tarjetaAsignadas>  lstTarjeta= new ArrayList<>();
        List<String> lstWhere = new ArrayList<>();
        List<vendedor> lstVendedor = new ArrayList<>();
        List<cliente> lstCliente = new ArrayList<>();
        List<cobrador> lstCobrador = new ArrayList<>();
        vendedorDAO vendD = new vendedorDAO();
        clienteDAO clienD = new clienteDAO();
        cobradorDAO cobraDAO = new cobradorDAO();
        cobrador cobraDTO = new cobrador();
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
                tarjetaAsignadas tarj = new tarjetaAsignadas();
                tarj.setIdTarjeta(rs.getInt(1));
                tarj.setFolio(rs.getString(2));
                tarj.setIdCliente(rs.getInt(3));
                    lstWhere.clear();
                    lstWhere.add("idCliente = "+rs.getInt(3));
                    lstCliente = clienD.consultarClientes(lstWhere);
                if (!lstCliente.isEmpty()) tarj.setNomCliente(lstCliente.get(0).getNombre());
                tarj.setPrecio(rs.getFloat(4));
                tarj.setEnganche(rs.getFloat(5));
                tarj.setIdVendedor(rs.getInt(6));
                    lstWhere.clear();
                    lstWhere.add("idVendedor = "+rs.getInt(6));
                    lstVendedor = vendD.consultarVendedor(lstWhere);
                if(!lstVendedor.isEmpty()) tarj.setNomVendedor(lstVendedor.get(0).getNombre());
                else tarj.setNomVendedor(" ");
                    
                tarj.setClasificacion(rs.getString(7));
                tarj.setTipoPago(rs.getString(8));
                tarj.setRegion(rs.getString(9));
                tarj.setDiaCobro(rs.getString(10));
                tarj.setEnganchePend(rs.getFloat(11));
                tarj.setSaldo(rs.getFloat(12));
                tarj.setFecha(rs.getString(13));
                tarj.setPagos(rs.getFloat(14));
                tarj.setTipoPrecio(rs.getString(15));
                tarj.setIdAsignado(rs.getInt(16));
                //tarj.setIdAsignado(rs.getInt(17));
                tarj.setIdCobrador(rs.getInt(18));
                    lstWhere.clear();
                    lstWhere.add("idCobrador = "+rs.getInt(18));
                    lstCobrador = cobraDAO.consultarCobradores(lstWhere);
                if(!lstCobrador.isEmpty())tarj.setNombreCobrador(lstCobrador.get(0).getNombre());
                tarj.setFechaAsignado(rs.getString(19));
                
                lstTarjeta.add(tarj);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstTarjeta;
    }
    
    public List<tarjetasAsigPorSemanaConPagosDTO> consultarTarjetasPagosPorSemana( List<String> where){
        List<tarjetasAsigPorSemanaConPagosDTO>  lstTarjetaPagos= new ArrayList<>();
        List<String> lstWhere = new ArrayList<>();
        List<vendedor> lstVendedor = new ArrayList<>();
        List<cliente> lstCliente = new ArrayList<>();
        List<cobrador> lstCobrador = new ArrayList<>();
        vendedorDAO vendD = new vendedorDAO();
        clienteDAO clienD = new clienteDAO();
        cobradorDAO cobraDAO = new cobradorDAO();
        cobrador cobraDTO = new cobrador();
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
        
        //select ta.idCobrador, ta.EstadoRecibido, ta.EstadoEntregado, ta.idTarjeta as numTarjeta, pr.idTarjeta, strfTime('%Y',pr.Fecha) as year,  
        //strfTime('%W',pr.Fecha) as semana, sum(pr.Monto) as sumaMontos, count(pr.Monto) as cuantosPagos, pr.Tipo 
        //from tarjetasAsignadas ta left join Pagos_realizados pr on ta.idTarjeta = pr.idTarjeta where pr.idTarjeta is not null and pr.Tipo in('Parcialidad', 'Abono') 
        //and ta.idCobrador = 1 and cast(semana as integer ) between 48 and 50 
        //group by semana
        String sql = "select ta.idCobrador, ta.EstadoRecibido, ta.EstadoEntregado, ta.idTarjeta as numTarjeta, pr.idTarjeta, strfTime('%Y',pr.Fecha) as year,  "
                + "strfTime('%W',pr.Fecha) as semana, sum(pr.Monto) as sumaMontos, count(pr.Monto) as cuantosPagos, pr.Tipo  "
                + "from tarjetasAsignadas ta left join Pagos_realizados pr on ta.idTarjeta = pr.idTarjeta where "+Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                tarjetasAsigPorSemanaConPagosDTO tarjPagosSem = new tarjetasAsigPorSemanaConPagosDTO();
                tarjPagosSem.setIdCobrador(rs.getInt(1));
                tarjPagosSem.setEstadoRecibido(rs.getString(2));
                tarjPagosSem.setEstadoEntregado(rs.getString(3));
                tarjPagosSem.setIdTarjeta(rs.getInt(4));
                tarjPagosSem.setYear(rs.getString(6));
                tarjPagosSem.setSemana(rs.getString(7));
                tarjPagosSem.setSumaMontos(rs.getFloat(8));
                tarjPagosSem.setCuantosPagos(rs.getInt(9));
                tarjPagosSem.setTipo(rs.getString(10));
                
                lstTarjetaPagos.add(tarjPagosSem);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstTarjetaPagos;
    }

    public List<tarjetaAsignadas> consultarTarjetasSinAsignar( List<String> where){
        List<tarjetaAsignadas>  lstTarjeta= new ArrayList<>();
        List<String> lstWhere = new ArrayList<>();
        List<vendedor> lstVendedor = new ArrayList<>();
        List<cliente> lstCliente = new ArrayList<>();
        vendedorDAO vendD = new vendedorDAO();
        clienteDAO clienD = new clienteDAO();
        

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
                + "t2.EstadoRecibido, t2.EstadoEntregado from Tarjeta as t1 left join TarjetasAsignadas as t2 on t1.idTarjeta = t2.idTarjeta where "+Filtro.toString();
        System.out.println(sql);
        try (
            Connection con = conecta.conectaDB();
             Statement stmt  = con.createStatement();
             ResultSet rs  = stmt.executeQuery(sql))
             {
             while (rs.next()){
                tarjetaAsignadas tarj = new tarjetaAsignadas();
                tarj.setIdTarjeta(rs.getInt(1));
                tarj.setFolio(rs.getString(2));
                tarj.setIdCliente(rs.getInt(3));
                    lstWhere.clear();
                    lstWhere.add("idCliente = "+rs.getInt(3));
                    lstCliente = clienD.consultarClientes(lstWhere);
                if (!lstCliente.isEmpty()) tarj.setNomCliente(lstCliente.get(0).getNombre());
                tarj.setPrecio(rs.getFloat(4));
                tarj.setEnganche(rs.getFloat(5));
                tarj.setIdVendedor(rs.getInt(6));
                    lstWhere.clear();
                    lstWhere.add("idVendedor = "+rs.getInt(6));
                    lstVendedor = vendD.consultarVendedor(lstWhere);
                if (!lstVendedor.isEmpty()) tarj.setNomVendedor(lstVendedor.get(0).getNombre());
                tarj.setClasificacion(rs.getString(7));
                tarj.setTipoPago(rs.getString(8));
                tarj.setRegion(rs.getString(9));
                tarj.setDiaCobro(rs.getString(10));
                tarj.setEnganchePend(rs.getFloat(11));
                tarj.setSaldo(rs.getFloat(12));
                tarj.setFecha(rs.getString(13));
                tarj.setPagos(rs.getFloat(14));
                tarj.setTipoPrecio(rs.getString(15));
                tarj.setIdAsignado(rs.getInt(16));
                //tarj.setIdAsignado(rs.getInt(17));
                tarj.setIdCobrador(rs.getInt(18));
                tarj.setFechaAsignado(rs.getString(19));
                
                lstTarjeta.add(tarj);
             }
             con.close();

        } catch (SQLException e) {
           System.out.println(e.getMessage());
        }
        return lstTarjeta;
    }

    public int insertarTarjetaAsignadas(int idTarjeta, int idCobrador, String Fecha, String EstadoRecibido, String EstadoEntregado){
        int regs=0;
        StringBuilder sqrString= new StringBuilder();
        Conexion conecta = new Conexion("cobranzaDB.db");    
        sqrString.append("INSERT INTO TarjetasAsignadas(idTarjeta, idCobrador, Fecha, EstadoRecibido, EstadoEntregado) "
                + "VALUES (?, ?, ?, ?, ?)");
        System.out.print(sqrString.toString());
        try{
             Connection con = conecta.conectaDB();
             PreparedStatement pstmt = con.prepareStatement(sqrString.toString());
             pstmt.setInt(1, idTarjeta);
             pstmt.setInt(2, idCobrador);
             pstmt.setString(3, Fecha);
             pstmt.setString(4, EstadoRecibido);
             pstmt.setString(5, EstadoEntregado);
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

    public void eliminarAsignacion(int id) {
        String sql = "DELETE FROM TarjetasAsignadas WHERE idAsignacion = "+String.valueOf(id)+"; ";
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
