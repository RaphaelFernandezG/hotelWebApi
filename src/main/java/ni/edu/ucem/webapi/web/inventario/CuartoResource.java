package ni.edu.ucem.webapi.web.inventario;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ni.edu.ucem.webapi.core.ApiResponse;
import ni.edu.ucem.webapi.core.ApiResponse.Status;
import ni.edu.ucem.webapi.core.ListApiResponse;
import ni.edu.ucem.webapi.modelo.Cuarto;
import ni.edu.ucem.webapi.modelo.Pagina;
import ni.edu.ucem.webapi.modelo.Paginacion;
import ni.edu.ucem.webapi.serviceImpl.InventarioServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.context.request.WebRequest;
//import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/v1/inventario/cuartos")
public class CuartoResource 
{
    private final InventarioServiceImpl inventarioService;
    
    @Autowired
    public CuartoResource(final InventarioServiceImpl inventarioService)
    {
        this.inventarioService = inventarioService;
    }
    
    @ApiOperation(value = "/v1/inventario/cuartos")
    @RequestMapping(method = RequestMethod.GET, produces="application/json")
    @ApiImplicitParams({
        @ApiImplicitParam(name ="categoria", value ="Categoria ID", required = false, dataType = "integer")
    })
    @ApiResponses(
        value = {
                @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = Cuarto.class),
                @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    public ListApiResponse<Cuarto> obtenerCuartos(
            @RequestParam(value = "categoria", required = false) final Integer categoria,
            @RequestParam(value = "offset", required = false, defaultValue ="0") final Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue="0") final Integer limit,
            @RequestParam(value = "fields", required = false, defaultValue="*") final String fields,
            @RequestParam(value = "sort", required = false, defaultValue="id") final String sort,
            @RequestParam(value = "search", required = false, defaultValue="") final String search,
            @RequestParam(value = "sortOrder", required = false, defaultValue="DESC") String sortOrder)
    {
        //String[] listaCampos = {"id", "numero", "descripcion", "categoria"};
        /*if(!this.inventarioService.ValidarCampos(fields, listaCampos))
            throw new IllegalArgumentException("Problema con los campos a mostrar, alguno no existe");
        
        if(!this.inventarioService.ValidarCampos(sort, listaCampos))
            throw new IllegalArgumentException("Problema con los campos a ordenar, alguno no existe");
        */
        String campos = this.inventarioService.ValidarCampos(fields, Cuarto.class, "*");
        String orden  = this.inventarioService.ValidarCampos(sort, Cuarto.class, "id");
        
        if(!sortOrder.toUpperCase().equals("DESC") && !sortOrder.toUpperCase().equals("DESC"))
            sortOrder = "ASC";
        
        final Paginacion paginacion = new Paginacion.Builder(offset, limit,campos,orden,search,sortOrder).build();
        Pagina<Cuarto> pagina;
        if(categoria != null)
        {
            pagina = this.inventarioService.obtenerTodosCuartoEnCategoria(categoria, paginacion);
        }
        else
        {
            pagina = this.inventarioService.obtenerTodosCuarto(paginacion);
        }
        return new ListApiResponse<Cuarto>(Status.OK, pagina);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces="application/json")
    public ApiResponse obtener(@PathVariable("id") final int id,
            @RequestParam(value = "fields", required = false, defaultValue="*") final String pFields, WebRequest webRequest)
    {
        final String campos = this.inventarioService.ValidarCampos(pFields, Cuarto.class, "*");
        final Cuarto cuarto = this.inventarioService.obtenerCuarto(id,campos);
        
        if(cuarto == null)
        {
           throw new IllegalArgumentException("Cuarto NO EXISTE");
        }
        System.err.println("FECHA MODIFICADO: " + cuarto.getModificado());
        if(webRequest.checkNotModified(cuarto.getModificado().getTime()))
        {
            System.out.println(cuarto.getModificado());
            return null;
        }
                
        return new ApiResponse(Status.OK, cuarto);
    }
    
    /**
     * Para que la validación funcione solo es necesario agregar la anotación @valid al método.
     * Spring se asegura que la petición entrante sea validado con las reglas definidas en el POJO. 
     * @param cuarto
     * @param result
     * @return
     */
    //@PreAuthorize("hasRole('ADMIN')")  //Habilitar a nivel del servicio sólo para usuario ADMIN
    @RequestMapping(method = RequestMethod.POST,
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse guardarCuarto(@Valid @RequestBody final Cuarto cuarto, BindingResult result) 
    {
        if(result.hasErrors())
        {
            throw new IllegalArgumentException(result.getFieldError().getDefaultMessage());
        }
        
        this.inventarioService.agregarCuarto(cuarto);
        return new ApiResponse(Status.OK, cuarto);
    }
    
    /**
     * Negociación de contenido. Aceptamos form-parameters para la creación de un nuevo recurso.
     * @param numero
     * @param descripcion
     * @param categoria
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse guardarCuartoConFormData(final Short numero, final String descripcion, final Integer categoria) 
    {
        Cuarto cuarto = new Cuarto(numero, descripcion, categoria);
        this.inventarioService.agregarCuarto(cuarto);
        return new ApiResponse(Status.OK, cuarto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
            produces="application/json")
    public ApiResponse guardarCuarto(@PathVariable("id") final int id, 
            @RequestBody final Cuarto cuartoActualizado) 
    {
        final Cuarto cuarto = new Cuarto(id,
                cuartoActualizado.getNumero(), 
                cuartoActualizado.getDescripcion(),
                cuartoActualizado.getCategoria());
        this.inventarioService.guardarCuarto(cuarto);
        return new ApiResponse(Status.OK, cuarto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces="application/json")
    public ApiResponse eliminarCuarto(@PathVariable("id") final int id) 
    {
        final Cuarto cuarto = this.inventarioService.obtenerCuarto(id,"*");
        this.inventarioService.eliminarCuarto(cuarto.getId());
        return new ApiResponse(Status.OK,null);
    }
}
