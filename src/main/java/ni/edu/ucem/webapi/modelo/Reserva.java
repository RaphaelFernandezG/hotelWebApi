/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.ucem.webapi.modelo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dbojorge
 */
public class Reserva {
    private int id;
    @NotNull(message = "Fecha Inicio es requerida")
    private String desde;
    @NotNull(message = "Fecha Final es requerida")
    private String hasta;
    @NotNull(message = "Id Cuarto Requerido")
    @Min(1)
    private int cuarto;
    @NotNull(message = "Id Huesped Requerido")
    @Min(1)
    private int huesped;

    public Reserva() {
    }

    public Reserva(int id,String desde, String hasta, int cuarto, int huesped) {
        this.id = id;
        this.desde = desde;
        this.hasta = hasta;
        this.cuarto = cuarto;
        this.huesped = huesped;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public int getCuarto() {
        return cuarto;
    }

    public void setCuarto(int cuarto) {
        this.cuarto = cuarto;
    }

    public int getHuesped() {
        return huesped;
    }

    public void setHuesped(int huesped) {
        this.huesped = huesped;
    }
    
}
