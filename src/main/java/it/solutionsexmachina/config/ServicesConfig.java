package it.solutionsexmachina.config;

import it.solutionsexmachina.services.ContactService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
})
public class ServicesConfig {

    @Bean
    public ContactService contactService() {
        return new ContactService();
    }

}

