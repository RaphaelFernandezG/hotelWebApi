package ni.edu.ucem.webapi;

import ni.edu.ucem.webapi.core.ApiResponse.Status;
import java.util.List;
import java.util.Map;

import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import ni.edu.ucem.webapi.core.ApiResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/*
 * La anotación @SpringBootApplication carga varias dependencias por defecto (tal como el contenedor de servlet 
 * envevido), activa el escaneo de componentes (@ComponentScan) y los EndPoints web (@EnableWebMvc), entre otras tareas.
 */
@SuppressWarnings("unused")
@SpringBootApplication
public class HotelWebApi
{
    private static final Logger log = LoggerFactory.getLogger(HotelWebApi.class);
    public static void main( String[] args )
    {
        /*
         * Con Maven or Gradle, es posible empaquetar la aplicación en formato WAR, sin embargo, para iniciar 
         * rapidamente la aplicación durante el desarrollo, implementamos una clase java ejecutable.
         */
        SpringApplication.run(HotelWebApi.class, args);
        ApiResponse.Status test = Status.BAD_REQUEST;
    }
    
    /*Implementando swagger-ui*/
    private ApiInfo apiInfo() 
    {
        return new ApiInfoBuilder()
                .title("hotel-web-api")
                .description("Proyectos y documentos del curso 'Diseño de servicios Web Restful con java'.")
                .version("1.0.0")
                .build();
    }

    @Bean
    public Docket swaggerSettings() 
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                    .paths(PathSelectors.ant("/v1/**"))
                .build();
    }
    //Agregar filtro que permita usar etag. Es otra forma
    //de hacerlo, la otra opción es crear una clase de configuración
    //ConfigEtag.java
    /*@Bean
    public FilterRegistrationBean shallowEtagBean ()
    {
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setFilter(new ShallowEtagHeaderFilter());
        frb.addUrlPatterns("/*");
        return frb;
    }*/
}
