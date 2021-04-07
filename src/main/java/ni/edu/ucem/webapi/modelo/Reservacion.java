/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.ucem.webapi.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dbojorge
 */
public class Reservacion<T> {

    private int id;
    @NotNull(message = "Fecha Inicio es requerida")
    private String desde;
    @NotNull(message = "Fecha Final es requerida")
    private String hasta;
    private Cuarto cuarto;
    private Huesped huesped;

    public Reservacion() {
    }

    public Reservacion(final int id, String desde, String hasta, Cuarto cuarto, Huesped huesped) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss.SSS'Z'");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dsd, hst;
        try {
            dsd = format2.parse(desde);
            hst = format2.parse(hasta);
        } catch (Exception ex) {
            try {
                dsd = format2.parse(desde.substring(10));
                hst = format2.parse(hasta.substring(10));
            } catch (Exception ex2) {
                dsd = new Date();
                hst = new Date();
                System.err.println("Error en la clase: " + ex.getMessage());
            }
        }
        this.id = id;
        this.desde = format1.format(dsd);
        this.hasta = format1.format(hst);
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

    public Cuarto getCuarto() {
        return cuarto;
    }

    public void setCuarto(Cuarto cuarto) {
        this.cuarto = cuarto;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

}
