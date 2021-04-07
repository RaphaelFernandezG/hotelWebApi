/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.ucem.webapi.dao;

import ni.edu.ucem.webapi.modelo.Huesped;
import ni.edu.ucem.webapi.modelo.Reserva;

/**
 *
 * @author dbojorge
 */
public interface ReservaDAO {
    
    public Reserva obtenerReserva(final int id);
    public Huesped obtenerHuespedReserva(final int id);
    public void grabarReserva(final Reserva reserva);
    
    public Huesped obtenerHuesped(final int id);
    
    public Reserva consultaReserva(final String desde,final String hasta,final int cuarto);
    
}
