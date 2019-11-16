package it.solutionsexmachina.boot;

import it.solutionsexmachina.config.CorsFilterConfiguration;
import it.solutionsexmachina.config.ServicesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("it.solutionsexmachina.repositories")
@EntityScan("it.solutionsexmachina.entities")
@ComponentScan({"it.solutionsexmachina.webcontrollers"})
@Import({CorsFilterConfiguration.class, ServicesConfig.class})
public class ContactApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactApplication.class, args);
    }
}
