/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.ucem.webapi.web.inventario;

import ni.edu.ucem.webapi.core.ApiResponse;
import ni.edu.ucem.webapi.core.ApiResponse.Status;
import ni.edu.ucem.webapi.modelo.Cuarto;
import ni.edu.ucem.webapi.modelo.Cupo;
import ni.edu.ucem.webapi.modelo.Paginacion;
import ni.edu.ucem.webapi.serviceImpl.InventarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dbojorge
 */
@RestController
@RequestMapping("/v1/disponibilidad/cupos")
public class CupoResource {
    
    private final InventarioServiceImpl inventarioService;
    
    @Autowired
    public CupoResource(final InventarioServiceImpl inventarioService)
    {
        this.inventarioService = inventarioService;
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ApiResponse consultaCupo(
        @RequestParam(value = "desde", required = true) final String desde,
        @RequestParam(value = "hasta", required = true) final String hasta,
        @RequestParam(value = "categoria", required = false, defaultValue="0") final int categoria,
        @RequestParam(value = "offset", required = false, defaultValue="0") final int offset,
        @RequestParam(value = "limit", required = false, defaultValue="0") final int limit)
    {
      
      final Paginacion paginacion = new Paginacion.Builder(offset, limit,"","","","").build();
      Cupo<Cuarto> cupo;
      
      /*String dsd,hst;
      Date dI,dF;
      
      SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
      SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
      dI = format1.parse(desde);

      dF = formateador.format(dF);
      hst = formateador.format(hasta);*/
      
      cupo = this.inventarioService.obtenerCupo(desde, hasta, categoria, paginacion);
      
      //return new ApiResponse(Status.OK, cupo);      
      return new ApiResponse(Status.OK, cupo);
    }
}
