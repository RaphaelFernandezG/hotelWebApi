/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.ucem.webapi.daoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import ni.edu.ucem.webapi.dao.ReservaDAO;
import ni.edu.ucem.webapi.modelo.Huesped;
import ni.edu.ucem.webapi.modelo.Reserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author dbojorge
 */
@Repository
public class ReservaDAOImpl implements ReservaDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Reserva obtenerReserva(int pId) {
        final String sql = "select * from reservacion where id = ?";
        Reserva reserva;
        try {
            reserva = jdbcTemplate.queryForObject(sql, new Object[]{pId},
                    new BeanPropertyRowMapper<Reserva>(Reserva.class));
        } catch (Exception ex) {
            reserva = null;
        }
        return reserva;
    }

    @Override
    public Huesped obtenerHuespedReserva(int pId) {
        final String sql = "select * from huesped where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{pId},
                new BeanPropertyRowMapper<Huesped>(Huesped.class));
    }

    @Override
    public void grabarReserva(Reserva reserva) {
        System.out.print("Desde: ");
        System.out.println(reserva.getDesde());

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.sss'Z'");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dsd, hst;
        try {
            dsd = format2.parse(reserva.getDesde());
            hst = format2.parse(reserva.getHasta());
        } catch (Exception ex) {
            try {
                dsd = format2.parse(reserva.getDesde().substring(10));
                hst = format2.parse(reserva.getHasta().substring(10));
            }catch (Exception ex2) {
                System.out.println("Error en fecha " + ex2.getMessage());
                throw new IllegalArgumentException("Error en fecha, listo para grabar " + ex.getMessage());
            }
        }

        final String sql = new StringBuilder()
                .append("INSERT INTO reservacion")
                .append(" (desde,hasta,cuarto,huesped)")
                .append(" values(?,?,?,?) last id into variable")
                .toString();

        final Object[] param = new Object[4];
        param[0] = reserva.getDesde();
        param[1] = reserva.getHasta();
        //param[0] = dsd;
        //param[1] = hst;
        param[2] = reserva.getCuarto();
        param[3] = reserva.getHuesped();
        this.jdbcTemplate.update(sql, param);
    }

    @Override
    public Huesped obtenerHuesped(int pId) {
        final String sql = "select * from huesped where id = ?";
        Huesped huesped;
        try {
            huesped = jdbcTemplate.queryForObject(sql, new Object[]{pId},
                    new BeanPropertyRowMapper<Huesped>(Huesped.class));
        } catch (Exception ex) {
            huesped = null;
        }
        return huesped;
    }

    @Override
    public Reserva consultaReserva(String desde, String hasta, int cuarto) {
        final String sql = "select * from reservacion where desde >= ? and hasta <= ? and cuarto = ?";
        Reserva reserva;
        try {
            reserva = jdbcTemplate.queryForObject(sql, new Object[]{desde, hasta, cuarto},
                    new BeanPropertyRowMapper<Reserva>(Reserva.class));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            reserva = null;
        }
        return reserva;
    }
}
