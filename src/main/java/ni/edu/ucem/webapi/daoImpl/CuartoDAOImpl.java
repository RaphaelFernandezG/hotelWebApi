package ni.edu.ucem.webapi.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ni.edu.ucem.webapi.dao.CuartoDAO;
import ni.edu.ucem.webapi.modelo.Cuarto;

@Repository
public class CuartoDAOImpl implements CuartoDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CuartoDAOImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cuarto obtenerPorId(final int pId, final String pFields) {

        Cuarto cuarto = null;
        /*
        String sql = "select * from cuarto where id = ?";
        try {
            cuarto = jdbcTemplate.queryForObject(sql, new Object[]{pId},
                    new BeanPropertyRowMapper<Cuarto>(Cuarto.class));
        } catch (Exception ex) {
            cuarto = null;
        }*/

        String sql = new StringBuilder()
                .append("Select modificado,").append(pFields)
                .append(" from cuarto where id=").append(pId)
                .toString();
        System.out.println(sql);
        try {
            cuarto = jdbcTemplate.queryForObject(sql, new Object[]{},
                    new BeanPropertyRowMapper<Cuarto>(Cuarto.class));
        } catch (Exception ex) {
            cuarto = null;
        }
        return cuarto;
    }

    @Override
    public int contar() {
        final String sql = "select count(*) from cuarto";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public int contarPorCategoria(final int categoriaId) {
        final String sql = "select count(*) from cuarto where categoria=?";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{categoriaId}, Integer.class);
        //return this.jdbcTemplate.queryForInt(sql,new Object[]{categoriaId});
    }

    @Override
    public List<Cuarto> obtenerTodos(final int pOffset, final int pLimit,
            final String pFields, final String pSort, final String pSearch,
            final String pSortOrder) {
        /*String sql = "select * from cuarto offset ? limit ?";
        return this.jdbcTemplate.query(sql, new Object[]{pOffset, pLimit},
                new BeanPropertyRowMapper<Cuarto>(Cuarto.class));*/
        String sql, where;
        if (pSearch.isEmpty()) {
            where = "1=1";
        } else {
            where = new StringBuilder()
                    .append(" upper(descripcion) like '%").append(pSearch.toUpperCase()).append("%'")
                    .toString();
        }

        sql = new StringBuilder()
                .append("Select ").append(pFields)
                .append(" from cuarto")
                .append(" where ").append(where)
                .append(" order by ").append(pSort).append(" ").append(pSortOrder)
                .append(" offset ").append(pOffset)
                .append(" limit ").append(pLimit)
                .toString();
        System.out.println(sql);
        List<Cuarto> lista = this.jdbcTemplate.query(sql, new Object[]{},
                new BeanPropertyRowMapper<Cuarto>(Cuarto.class));
        return lista;
    }

    @Override
    public List<Cuarto> obtenerTodosPorCategoriaId(int pCategoriaId, int pOffset, int pLimit) {
        final String sql = "select * from cuarto where categoria = ? offset ? limit ?";
        return this.jdbcTemplate.query(sql, new Object[]{pCategoriaId, pOffset, pLimit},
                new BeanPropertyRowMapper<Cuarto>(Cuarto.class));
    }

    @Override
    public void agregar(final Cuarto pCuarto) {
        final String sql = new StringBuilder()
                .append("INSERT INTO cuarto")
                .append(" ")
                .append("(numero, descripcion, categoria)")
                .append(" ")
                .append("VALUES (?, ?, ?)")
                .toString();
        final Object[] parametros = new Object[3];
        parametros[0] = pCuarto.getNumero();
        parametros[1] = pCuarto.getDescripcion();
        parametros[2] = pCuarto.getCategoria();
        this.jdbcTemplate.update(sql, parametros);

    }

    @Override
    public void guardar(final Cuarto pCuarto) {
        //SimpleDateFormat formateador = new SimpleDateFormat("yyyyMMdd")
        final String sql = new StringBuilder()
                .append("UPDATE cuarto")
                .append(" ")
                .append("set numero = ?")
                .append(",descripcion = ?")
                .append(",categoria = ?")
                .append(",modificado = ?")
                .append(" ")
                .append("where id = ?")
                .toString();
        final Object[] parametros = new Object[4];
        parametros[0] = pCuarto.getNumero();
        parametros[1] = pCuarto.getDescripcion();
        parametros[2] = pCuarto.getCategoria();
        parametros[4] = pCuarto.getId();
        parametros[3] = new Date();
        this.jdbcTemplate.update(sql, parametros);
    }

    @Override
    public void eliminar(final int pId) {
        final String sql = "delete from cuarto where id = ?";
        this.jdbcTemplate.update(sql, new Object[]{pId});
    }

    @Override
    public List<Cuarto> consultaCupo(final String desde, final String hasta, final int categoria, final int offset, final int limit) {
        /*String sql = "select * from cuarto where id not in (Select cuarto from reservacion where desde >= ? and hasta <=? ) offset ? limit ?";
        return this.jdbcTemplate.query(sql, new Object[]{desde, hasta,offset,limit},
                new BeanPropertyRowMapper<Cuarto>(Cuarto.class));
         */
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dsd, hst;
        String sql, f1, f2;
        try {
            dsd = formatter.parse(desde);
            hst = formatter.parse(hasta);
            f1 = formatter.format(dsd);
            f2 = formatter.format(hst);

            if (categoria == 0) {
                sql = new StringBuilder()
                        .append("select * from cuarto where id not in (Select cuarto from reservacion")
                        .append(" where desde >= '").append(f1).append("' and hasta <= '").append(f2).append("')")
                        .append(" offset ").append(offset)
                        .append(" limit ").append(limit)
                        .toString();
            } else {
                sql = new StringBuilder()
                        .append("select * from cuarto where id not in (Select cuarto from reservacion")
                        .append(" where desde >= '").append(f1).append("' and hasta <= '").append(f2).append("')")
                        .append(" AND categoria=").append(categoria)
                        .append(" offset ").append(offset)
                        .append(" limit ").append(limit)
                        .toString();
            }
        } catch (ParseException ex) {
            sql = new StringBuilder()
                    .append("select * from cuarto where desde=null and hasta=null")
                    .toString();
        }

        System.out.println(sql);
        List<Cuarto> cuarto = this.jdbcTemplate.query(sql, new Object[]{},
                new BeanPropertyRowMapper<Cuarto>(Cuarto.class));
        //System.out.println(cuarto);
        return cuarto;
    }

    @Override
    public Cuarto getCuartoReserva(final int idReserva) {
        String sql;
        Cuarto cuarto = null;
        sql = new StringBuilder()
                .append("Select * from cuarto where id in (Select cuarto from reservacion")
                .append(" where id = ").append(idReserva).append(")")
                .toString();
        System.out.println(sql);
        cuarto = jdbcTemplate.queryForObject(sql, new Object[]{},
                new BeanPropertyRowMapper<Cuarto>(Cuarto.class));
        return cuarto;
    }

    @Override
    public int contarCuartosReserva(int idReserva) {
        final String sql = "select count(*) from cuarto where id in (Select cuarto from reservacion where id=?)";
        return this.jdbcTemplate.queryForObject(sql, new Object[]{idReserva}, Integer.class);
    }
}
