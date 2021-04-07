package ni.edu.ucem.webapi.web.error;

import static ni.edu.ucem.webapi.core.ApiResponse.ApiError;
import static ni.edu.ucem.webapi.core.ApiResponse.Status;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ni.edu.ucem.webapi.core.ApiResponse;

/**
 * 
 * Le indica a apring que esta clase hará el manejo de las excepciones en nuestra
 * aplicación.
 * @author fmedina
 *
 */
@ControllerAdvice
@RestController
public class GlobalExceptionHandler 
{
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ApiResponse manejarRecursoNoEncontrado(Exception e)
    {
        return new ApiResponse(Status.BAD_REQUEST,null, new ApiError(Status.BAD_REQUEST.getValue(), 
                e.getMessage().toString()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse manejarArgumentoInvalido(Exception e)
    {
        return new ApiResponse(Status.BAD_REQUEST,null, new ApiError(Status.BAD_REQUEST.getValue(), 
                e.getMessage().toString()));
    }
}
