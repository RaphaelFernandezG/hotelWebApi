/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.ucem.webapi.web.inventario;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.validation.Valid;
import ni.edu.ucem.webapi.core.ApiResponse;
import ni.edu.ucem.webapi.core.ApiResponse.Status;
import ni.edu.ucem.webapi.modelo.Cuarto;
import ni.edu.ucem.webapi.modelo.Huesped;
import ni.edu.ucem.webapi.modelo.Reserva;
import ni.edu.ucem.webapi.modelo.Reservacion;
import ni.edu.ucem.webapi.serviceImpl.InventarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dbojorge
 */
@RestController
@RequestMapping("/v1/reservaciones")
public class ReservacionResource {

    private final InventarioServiceImpl inventarioService;

    @Autowired
    public ReservacionResource(final InventarioServiceImpl inventarioService) {
        this.inventarioService = inventarioService;
    }

    @RequestMapping(value = "/{reservacion-id}", method = RequestMethod.GET, produces = "application/json")
    public ApiResponse getReservacion(@PathVariable("reservacion-id") final int id) {
        Reserva reserva = this.inventarioService.obtenerReserva(id);
        Reservacion reservacion = null;
        Status estado = Status.BAD_REQUEST;
        if (reserva != null) {
            Cuarto cuarto = this.inventarioService.obtenerCuartoReservacion(reserva.getId());
            Huesped huesped = this.inventarioService.obtenerHuespedReserva(reserva.getHuesped());
            reservacion = new Reservacion(reserva.getId(), reserva.getDesde(), reserva.getHasta(), cuarto, huesped);
            estado = Status.OK;
        } else {
            throw new IllegalArgumentException("Reservación no existe");
        }

        //final int conteo = this.
        return new ApiResponse(estado, reservacion);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse setReservacion(@Valid @RequestBody final Reserva reserva, BindingResult result) {

        if (result.hasErrors()) {
            throw new IllegalArgumentException("Error no controlado" + result.getFieldError().getDefaultMessage());
        }
        Reservacion reservacion = null;
        /*Validaciones
        1. Fecha de ingreso >= fecha Actual
        2. Fecha de Salida > fecha de Ingreso
        
        Retorno
        404 Bad Request si alguno de los parámetros tiene un tipo de dato inválido, o bien si fechaIngreso o fechaSalida no satisfacen las reglas indicadas.
         */

        //Variable que controla que si todo va bien proceder a guardar la reservación
        Boolean continuar = true;
        //Controlar Respuesta
        Status estado;

        //Validando fechas
        //Convertir a fecha para poder comparar
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.sss'Z'");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dsd, hst, hoy;
        hoy = new Date();
        try {
            dsd = format2.parse(reserva.getDesde());
            hst = format2.parse(reserva.getHasta());
        } catch (Exception ex) {
            dsd = new Date();
            hst = new Date();
            System.out.println("Error en fecha " + ex.getMessage());
            continuar = false;
            throw new IllegalArgumentException("Error en fecha " + ex.getMessage());

        }

        //Si todo bien, comparar fechas
        if (continuar) {
            if (dsd.compareTo(hoy) < 0) {
                //Fecha inicio es anterior a la fecha de hoy
                System.err.println("Fecha Inicio es menor al día de hoy");
                continuar = false;
                throw new IllegalArgumentException("Fecha Inicio es menor al día de hoy");
            } else if (hst.compareTo(dsd) < 0) {
                System.err.println("Fecha Final es menor a fecha inicial");
                continuar = false;
                throw new IllegalArgumentException("Fecha Final es menor a fecha inicial");
            }
        }

        //Si todo bien, valida que cuarto y huesped existan
        if (continuar) {
            if (this.inventarioService.obtenerCuarto(reserva.getCuarto(),"*") == null) {
                System.out.println("Cuato no existe");
                continuar = false;  //Cuarto no existe
                throw new IllegalArgumentException("Cuarto no existe");
            }

            if (this.inventarioService.obtenerHuesped(reserva.getHuesped()) == null) {
                System.out.println("Huesped no existe");
                continuar = false;  //Huesped no existe
                throw new IllegalArgumentException("Huesped no existe");
            }
        }
        
        //Validar que reservación no exista ya
        if (this.inventarioService.consultaReserva(reserva.getDesde(), reserva.getHasta(), reserva.getCuarto()) != null) {
            System.out.println("Reservación ya existente");
            continuar = false;  //Huesped no existe
            throw new IllegalArgumentException("Reservación ya existente");
        }

        //Si todo bien ejecutar acción y setear estado
        if (continuar) {
            this.inventarioService.guardarReserva(reserva);
            //Determino el último ID generado, consultando nuevamente con los mismos valores
            Reserva reservaNueva = this.inventarioService.consultaReserva(reserva.getDesde(), reserva.getHasta(), reserva.getCuarto());
            Cuarto cuarto = this.inventarioService.obtenerCuartoReservacion(reservaNueva.getId());
            Huesped huesped = this.inventarioService.obtenerHuespedReserva(reservaNueva.getHuesped());
            reservacion = new Reservacion(reservaNueva.getId(), reservaNueva.getDesde(), reservaNueva.getHasta(), cuarto, huesped);
            estado = Status.OK;
        } else {
            estado = Status.BAD_REQUEST;
        }
        return new ApiResponse(estado, reservacion);
    }
}
