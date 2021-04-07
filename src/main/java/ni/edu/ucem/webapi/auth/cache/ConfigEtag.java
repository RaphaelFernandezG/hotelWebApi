/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ni.edu.ucem.webapi.auth.cache;


import javax.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;


import org.springframework.web.filter.ShallowEtagHeaderFilter;

/**
 *
 * @author pc21
 */
@Configuration
public class ConfigEtag {
    @Bean
    public Filter etagFilter()
    {
        return new ShallowEtagHeaderFilter();
    }
}
