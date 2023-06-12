package serv.co.documentservice.connection;

import org.springframework.web.client.RestTemplate;
import serv.co.documentservice.model.Doc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class MyConfiguration implements RepositoryRestConfigurer {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(Doc.class);
        });
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}