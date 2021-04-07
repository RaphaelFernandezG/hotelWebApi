package ni.edu.ucem.webapi.dao;

import java.util.List;

import ni.edu.ucem.webapi.modelo.CategoriaCuarto;
import ni.edu.ucem.webapi.modelo.Pagina;

public interface CategoriaCuartoDAO 
{
    public CategoriaCuarto obtenerPorId(final int pId,final String fields);

    public int contar();
    
    public int contarFiltro(final String nombre,final String descripcion);
    
    public List<CategoriaCuarto> obtenerTodos(final int offset, final int limit,
            final String fields,final String sort,final String search,
            final String sortOrder);

    public void agregar(final CategoriaCuarto pCategoriaCuarto);

    public void guardar(final CategoriaCuarto pCategoriaCuarto);

    public void eliminar(final int pId);
    
    public List<CategoriaCuarto> obtenerFiltroCagetoriaCuartos(final String nombre,final String descripcion,
            final int offset, final int limit,
            final String fields,final String sort,final String sortOrder);
    

}
