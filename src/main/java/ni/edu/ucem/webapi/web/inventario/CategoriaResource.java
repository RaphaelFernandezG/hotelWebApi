/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.ucem.webapi.web.inventario;

import java.util.Arrays;
import javax.validation.Valid;
import ni.edu.ucem.webapi.core.ApiResponse;
import ni.edu.ucem.webapi.core.ListApiResponse;
import ni.edu.ucem.webapi.modelo.CategoriaCuarto;
import ni.edu.ucem.webapi.modelo.Pagina;
import ni.edu.ucem.webapi.modelo.Paginacion;
import ni.edu.ucem.webapi.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author pc21
 */
@RestController
@RequestMapping("/v1/inventario/categorias")
public class CategoriaResource {

    private final InventarioService inventarioService;

    @Autowired
    public CategoriaResource(final InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    //Función para validar si los campos enviados existen en la tabla
    //Si no exist
    private Boolean ValidarCampos(String pFields) {
        Boolean r;

        if (pFields == "*" || pFields == "id") {
            r = true;
        } else {
            r = true;
            /*Lista de Campos:  id,nombre,descripcion,precio*/
            String[] listaCampos = {"id", "nombre", "descripcion", "precio"};
            String[] listaFields = pFields.split(",");
            for (int x = 0; x < listaFields.length; x++) {
                System.out.println("obtenerFiltroCagetoriaCuartos: " + listaFields[x]);
                if (!Arrays.asList(listaCampos).contains(listaFields[x]) || !Arrays.asList(listaCampos).contains(listaFields[x])
                        || !Arrays.asList(listaCampos).contains(listaFields[x]) || !Arrays.asList(listaCampos).contains(listaFields[x])) {
                    r = false;
                }
            }
            /*if(!Arrays.asList(listaCampos).contains("id") || !Arrays.asList(listaCampos).contains("nombre") ||
                    !Arrays.asList(listaCampos).contains("descripcion") || !Arrays.asList(listaCampos).contains("precio"))
                r = false;
             */
        }

        return r;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ListApiResponse<CategoriaCuarto> obtenerCategorias(
            @RequestParam(value = "nombre", required = false) final String nombre,
            @RequestParam(value = "descripcion", required = false) final String descripcion,
            @RequestParam(value = "offset", required = false, defaultValue = "0") final Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "0") final Integer limit,
            @RequestParam(value = "fields", required = false, defaultValue = "*") final String fields,
            @RequestParam(value = "sort", required = false, defaultValue = "id") final String sort,
            @RequestParam(value = "search", required = false, defaultValue = "") final String search,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "ASC") String sortOrder) {
        
        /*
        if(!ValidarCampos(fields))
            throw new IllegalArgumentException("Problema con los campos a mostrar, alguno no existe");
        
        if(!ValidarCampos(sort))
            throw new IllegalArgumentException("Problema con los campos a ordenar, alguno no existe");
        */
        String[] listaCampos = {"id", "nombre", "descripcion", "precio"};
        /*if(!this.inventarioService.ValidarCampos(fields, listaCampos))
            throw new IllegalArgumentException("Problema con los campos a mostrar, alguno no existe");
        
        if(!this.inventarioService.ValidarCampos(sort, listaCampos))
            throw new IllegalArgumentException("Problema con los campos a ordenar, alguno no existe");
        */
        String campos = this.inventarioService.ValidarCampos(fields, CategoriaCuarto.class, "*");
        String orden  = this.inventarioService.ValidarCampos(sort, CategoriaCuarto.class, "id");
        
        if(!sortOrder.toUpperCase().equals("DESC") && !sortOrder.toUpperCase().equals("DESC"))
            sortOrder = "ASC";
        
        final Paginacion paginacion = new Paginacion.Builder(offset, limit, campos, orden, search, sortOrder).build();
        Pagina<CategoriaCuarto> pagina;
        if (nombre != null || descripcion != null) {
            pagina = this.inventarioService.obtenerFiltroCagetoriaCuartos(nombre, descripcion, paginacion);
        } else {
            pagina = this.inventarioService.obtenerTodosCategoriaCuartos(paginacion);
        }

        /*if(pagina.getCount()<=0)
            throw new EmptyResultDataAccessException(404);
         */
        return new ListApiResponse<CategoriaCuarto>(ApiResponse.Status.OK, pagina);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ApiResponse obtener(@PathVariable("id") final int id,
            @RequestParam(value = "fields", required = false, defaultValue = "*") final String fields) {
        
        String campos = this.inventarioService.ValidarCampos(fields,CategoriaCuarto.class,"*");
        
        final CategoriaCuarto categoria = this.inventarioService.obtenerCategoriaCuarto(id,campos);
        if(categoria == null)
        {
           throw new IllegalArgumentException("Categoría NO EXISTE");
        }
        return new ApiResponse(ApiResponse.Status.OK, categoria);
    }

    /**
     * Para que la validación funcione solo es necesario agregar la anotación
     *
     * @valid al método. Spring se asegura que la petición entrante sea validado
     * con las reglas definidas en el POJO.
     *
     * @param cuarto
     * @param result
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse guardarCategoriaCuarto(@Valid @RequestBody final CategoriaCuarto categoria, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException(result.getFieldError().getDefaultMessage());
        }

        this.inventarioService.agregarCategoriaCuarto(categoria);
        return new ApiResponse(ApiResponse.Status.OK, categoria);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            produces = "application/json")
    public ApiResponse guardarCategoriaCuarto(@PathVariable("id") final int id,
            @RequestBody final CategoriaCuarto cuartoActualizado) {
        final CategoriaCuarto categoria = new CategoriaCuarto(id,
                cuartoActualizado.getNombre(),
                cuartoActualizado.getDescripcion(),
                cuartoActualizado.getPrecio());
        this.inventarioService.guardarCategoriaCuarto(categoria);
        return new ApiResponse(ApiResponse.Status.OK, categoria);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = "application/json")
    public ApiResponse eliminarCuarto(@PathVariable("id") final int id) {
        final CategoriaCuarto categoria = this.inventarioService.obtenerCategoriaCuarto(id,"*");
        this.inventarioService.eliminarCategoriaCuarto(categoria.getId());
        return new ApiResponse(ApiResponse.Status.OK, null);
    }

}
