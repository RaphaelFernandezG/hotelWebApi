package ni.edu.ucem.webapi.dao;

import java.util.List;

import ni.edu.ucem.webapi.modelo.Cuarto;

public interface CuartoDAO 
{
    public Cuarto obtenerPorId(final int pId,final String pFields);

    public int contar();
    
    public int contarPorCategoria(final int pCategoriaId);
    
    public List<Cuarto> obtenerTodos(final int offset, final int limit,final String pFields,final String pSort,final String pSearch,
            final String pSortOrder);

    public List<Cuarto> obtenerTodosPorCategoriaId(final int pCategoriaId, final int offset, final int limit);

    public void agregar(final Cuarto pCuarto);

    public void guardar(final Cuarto pCuarto);

    public void eliminar(final int pId);
    
    public List<Cuarto> consultaCupo(final String desde,final String hasta,final int categoria,final int offset,final int limit);
    
    public Cuarto getCuartoReserva(final int idReserva);
    
    public int contarCuartosReserva(final int idReserva);
}
