/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copladb.DTO;

/**
 *
 * @author i7
 */
public class tarjetaPorSemana {

    private Integer idVendedor;
    private String NomVendedor;
    private String year;
    private String semana;
    private int cuentaEngaches ;
    private float sumaEnganches;

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNomVendedor() {
        return NomVendedor;
    }

    public void setNomVendedor(String NomVendedor) {
        this.NomVendedor = NomVendedor;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public int getCuentaEngaches() {
        return cuentaEngaches;
    }

    public void setCuentaEngaches(int cuentaEngaches) {
        this.cuentaEngaches = cuentaEngaches;
    }

    public float getSumaEnganches() {
        return sumaEnganches;
    }

    public void setSumaEnganches(float sumaEnganches) {
        this.sumaEnganches = sumaEnganches;
    }


    
    

}
