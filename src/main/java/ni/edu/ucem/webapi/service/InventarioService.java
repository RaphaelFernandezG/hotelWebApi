package ni.edu.ucem.webapi.service;

import ni.edu.ucem.webapi.modelo.CategoriaCuarto;
import ni.edu.ucem.webapi.modelo.Cuarto;
import ni.edu.ucem.webapi.modelo.Cupo;
import ni.edu.ucem.webapi.modelo.Huesped;
import ni.edu.ucem.webapi.modelo.Pagina;
import ni.edu.ucem.webapi.modelo.Paginacion;
import ni.edu.ucem.webapi.modelo.Reserva;

public interface InventarioService 
{
    public void agregarCategoriaCuarto(final CategoriaCuarto pCategoriaCuarto);

    public void guardarCategoriaCuarto(final CategoriaCuarto pCategoriaCuarto);

    public void eliminarCategoriaCuarto(int id);
    
    public CategoriaCuarto obtenerCategoriaCuarto(final int id,final String fields);
    
    public Pagina<CategoriaCuarto> obtenerFiltroCagetoriaCuartos(final String nombre,final String descripcion,final Paginacion paginacion);

    public Pagina<CategoriaCuarto> obtenerTodosCategoriaCuartos(final Paginacion paginacion);

    public void agregarCuarto(final Cuarto pCuarto);

    public void guardarCuarto(final Cuarto pCuarto);

    public void eliminarCuarto(final int pCuarto);

    public Cuarto obtenerCuarto(final int pCuarto,final String pFields);

    public Pagina<Cuarto> obtenerTodosCuarto(final Paginacion paginacion);

    public Pagina<Cuarto> obtenerTodosCuartoEnCategoria(final int pCategoriaCuarto, final Paginacion paginacion);
    
    //Cupo
    public Cupo<Cuarto> obtenerCupo(final String desde,final String hasta,final int categoria,final Paginacion paginacion);
    
    //Reservacion
    public Reserva obtenerReserva(final int id);
    public Cuarto obtenerCuartoReservacion(final int id);
    public void guardarReserva(final Reserva reserva);
    public Reserva consultaReserva(String desde, String hasta, int cuarto);
    
    //Huesped
    public Huesped obtenerHuespedReserva(final int id);
    public Huesped obtenerHuesped(final int pId);
    
    //Función general para validar campos
    public String ValidarCampos(String pInput,String[] pCampos,final String pDefault);
    public String ValidarCampos(String pInput,Class pObj,final String pDefault);
    
}
