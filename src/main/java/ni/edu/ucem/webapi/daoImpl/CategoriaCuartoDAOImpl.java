package ni.edu.ucem.webapi.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ni.edu.ucem.webapi.dao.CategoriaCuartoDAO;
import ni.edu.ucem.webapi.modelo.CategoriaCuarto;
import ni.edu.ucem.webapi.modelo.Cuarto;
import ni.edu.ucem.webapi.modelo.Pagina;

@Repository
public class CategoriaCuartoDAOImpl implements CategoriaCuartoDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CategoriaCuartoDAOImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CategoriaCuarto obtenerPorId(final int pId, final String pFields) {
        /*final String sql = "select * from categoria_cuarto where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{pId}, 
                new BeanPropertyRowMapper<CategoriaCuarto>(CategoriaCuarto.class));
         */
        CategoriaCuarto categoria = null;
        final String sql = new StringBuilder()
                .append("select ").append(pFields)
                .append(" from categoria_cuarto ")
                .append("where id=").append(pId)
                .toString();
        System.out.println(sql);
        try {
            categoria = jdbcTemplate.queryForObject(sql, new Object[]{},
                    new BeanPropertyRowMapper<CategoriaCuarto>(CategoriaCuarto.class));
        } catch (Exception ex) {
            categoria = null;
        }
        return categoria;
    }

    @Override
    public int contar() {
        final String sql = "select count(*) from categoria_cuarto";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public int contarFiltro(String nombre, String descripcion) {
        final String sql = "select count(*)  from categoria_cuarto where (lower(nombre) like ? and lower(descripcion) like ?)";
        //final String sql = String.format("select count(*)  from categoria_cuarto where (nombre like '%%%s%%' and descripcion like '%%%s%%')",nombre,descripcion);
        //return jdbcTemplate.queryForObject(sql,Integer.class); 
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new Object[]{"%" + nombre.toLowerCase() + "%", "%" + descripcion.toLowerCase() + "%"}, Integer.class);
    }

    @Override
    public List<CategoriaCuarto> obtenerFiltroCagetoriaCuartos(final String pNombre, final String pDescripcion,
            final int pOffset, final int pLimit,
            final String pFields, final String pSort, final String pSortOrder) {
        //String sql;

        /*sql = String.format("select %s  from categoria_cuarto where (nombre like '%%%s%%' and descripcion like '%%%s%%') order by %s %s offset %s limit %s "
                                ,pFields,pNombre,pDescripcion,pSort,pSortOrder,pOffset,pLimit);*/
 /*List<CategoriaCuarto> lista = this.jdbcTemplate.query(sql, new Object[]{},
                    new BeanPropertyRowMapper<CategoriaCuarto>(CategoriaCuarto.class));*/
        //sql = "select ? from categoria_cuarto ";
        final String sql = new StringBuilder()
                .append("select ").append(pFields)
                .append(" from categoria_cuarto ")
                .append("where (lower(nombre) like '%").append(pNombre.toLowerCase())
                .append("%' and lower(descripcion) like '%").append(pDescripcion.toLowerCase())
                .append("%') order by ").append(pSort).append(" ").append(pSortOrder)
                .append(" offset ").append(pOffset)
                .append(" limit ").append(pLimit)
                .toString();
        List<CategoriaCuarto> lista = this.jdbcTemplate.query(sql, new Object[]{/*pFields,"%"+pNombre+"%","%"+pDescripcion+"%",pSort,pSortOrder,pOffset,pLimit*/},
                new BeanPropertyRowMapper<CategoriaCuarto>(CategoriaCuarto.class));

        System.out.println(sql);

        return lista;

    }

    @Override
    public List<CategoriaCuarto> obtenerTodos(final int pOffset, final int pLimit,
            final String pFields, final String pSort, final String pSearch,
            final String pSortOrder) {
        String sql;
        if (pSearch.isEmpty()) {
            sql = String.format("select %s  from categoria_cuarto order by %s %s offset %s limit %s ",
                     pFields, pSort, pSortOrder, pOffset, pLimit);

        } else {
            sql = String.format("select %s  from categoria_cuarto where "
                    + "(upper(nombre) like '%%%s%%' or upper(descripcion) like '%%%s%%') order by %s %s offset %s limit %s ",
                     pFields, pSearch.toUpperCase(), pSearch.toUpperCase(), pSort, pSortOrder, pOffset, pLimit);
        }
        List<CategoriaCuarto> lista = this.jdbcTemplate.query(sql, new Object[]{},
                new BeanPropertyRowMapper<CategoriaCuarto>(CategoriaCuarto.class));

        return lista;
    }

    @Override
    public void agregar(final CategoriaCuarto pCategoriaCuarto) {
        final String sql = new StringBuilder()
                .append("INSERT INTO categoria_cuarto")
                .append(" ")
                .append("(nombre, descripcion, precio)")
                .append(" ")
                .append("VALUES(?,?,?)")
                .toString();
        final Object[] parametros = new Object[3];
        parametros[0] = pCategoriaCuarto.getNombre();
        parametros[1] = pCategoriaCuarto.getDescripcion();
        parametros[2] = pCategoriaCuarto.getPrecio();
        this.jdbcTemplate.update(sql, parametros);
    }

    @Override
    public void guardar(final CategoriaCuarto pCategoriaCuarto) {
        final String sql = new StringBuilder()
                .append("UPDATE categoria_cuarto")
                .append(" ")
                .append("SET nombre = ?, descripcion = ?")
                .append(",precio = ?")
                .append(" ")
                .append("WHERE id = ?")
                .toString();
        final Object[] parametros = new Object[4];
        parametros[0] = pCategoriaCuarto.getNombre();
        parametros[1] = pCategoriaCuarto.getDescripcion();
        parametros[2] = pCategoriaCuarto.getPrecio();
        parametros[3] = pCategoriaCuarto.getId();
        this.jdbcTemplate.update(sql, parametros);
    }

    @Override
    public void eliminar(final int pId) {
        final String sql = "delete from categoria_cuarto where id = ?";
        this.jdbcTemplate.update(sql, new Object[]{pId});
    }

}
