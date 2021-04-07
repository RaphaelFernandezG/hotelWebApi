package ni.edu.ucem.webapi.modelo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriaCuarto 
{
    private Integer id;
    
    @NotEmpty(message="El nombre de la categoría es requerida")
    @NotNull
    @Pattern(regexp = "^[\\w ]+$")
    private String nombre;
    
    @NotEmpty(message="Descripción de la categoría es requerida")
    @NotNull
    //@Pattern(regexp = "^[\\w ]+$")
    private String descripcion;
    
    @Min(1)
    @NotNull
    private BigDecimal precio;
    
    public CategoriaCuarto()
    {
    }
    
    public CategoriaCuarto(final Integer id, final String nombre, 
            final String descripcion,final BigDecimal precio) 
    {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public BigDecimal getPrecio() {
        return precio;
    }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
