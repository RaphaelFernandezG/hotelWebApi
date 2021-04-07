/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.ucem.webapi.auth;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.aop.ste
/**
 *
 * @author pc21
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)  //Esto es para poner seguridad a nivel de método (en CuartoResource, lo que está comentado - PreAuthorized)
public class ConfigSeguridad extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;

    
    public ConfigSeguridad(final DataSource dataSource,
            final AuthenticationManagerBuilder auth)
    {
        this.dataSource = dataSource;

    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET).permitAll() //Cualquier persona que esté autenticada puede usar los endpoint de lectura
                //.antMatchers(HttpMethod.POST,"/v1/reservaciones").permitAll()
                //.antMatchers(HttpMethod.GET,"/v1/inventario/cuartos/**").permitAll() //Está permitida consulta sólo sobre el ENDPOINT cuartos y cualquier específico (fijado con **)
                .antMatchers(HttpMethod.POST,"/v1/inventario/cuartos","/v1/inventario/categorias").hasAnyRole("ADMIN") //Se puede especificar para desde la seguridad que solo accede el ADMIN al POST
                .antMatchers(HttpMethod.DELETE).hasAnyRole("ADMIN") //Sólo ADMIN puede borrar recursos
                .anyRequest().authenticated() //Todos deben estar autenticados, debe ir de último
            .and()
                .httpBasic()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .csrf().disable()
            .headers().cacheControl().disable();  //Agrega control de cache
        
    }
            
    @Autowired
    //jperez:1234 Authorization Basic anBlcmV6OjEyMzQ=
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder(10))
                .usersByUsernameQuery("select username,password,enabled from usuarios where username = ?")
                .authoritiesByUsernameQuery("select username,role from usuarios_roles where username = ?");
                
    }
}
