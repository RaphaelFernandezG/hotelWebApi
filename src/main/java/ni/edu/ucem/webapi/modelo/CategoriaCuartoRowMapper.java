/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.ucem.webapi.modelo;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author dbojorge
 */
public class CategoriaCuartoRowMapper  implements RowMapper<CategoriaCuarto>
{

    @Override
    public CategoriaCuarto mapRow(ResultSet rs, int i) throws SQLException 
    {
        return null;
    }
    
}
