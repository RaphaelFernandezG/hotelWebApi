package ni.edu.ucem.webapi.core;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * See {@linktourl https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html}
 * @author fmedina
 */
@JsonInclude(Include.NON_NULL)
public class ApiResponse
{

    public static enum Status
    {
        OK(200),
        BAD_REQUEST(400),
        INTERNAL_SERVER_ERROR(500),
        NOT_FOUND(404);
        private final int value;
        
        private Status(final int value){
            this.value = value;
        }
        
        public int getValue(){
            return this.value;
        }
    }
    
    public static final class ApiError
    {
        private final int codigoError;
        private final String descripcion;
        
        public ApiError(final int codigoError, final String descripcion)
        {
            this.codigoError = codigoError;
            this.descripcion = descripcion;
        }
        
        public int getCodigoError() {
            return codigoError;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }
    
    private final Status status;
    private final Object result;
    private final ApiError error;
    
    public ApiResponse(final Status status)
    {
        this(status,null,null);
    }
    
    public ApiResponse(final Status status, final Object data)
    {
        this(status,data,null);
    }

    public ApiResponse(final Status status, 
            final Object result, final ApiError error)
    {
        this.status = status;
        this.result = result;
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public Object getResult() {
        return result;
    }

    public ApiError getError() {
        return error;
    }
}
    
