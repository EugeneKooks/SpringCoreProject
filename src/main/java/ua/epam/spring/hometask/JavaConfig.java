package ua.epam.spring.hometask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration 
@ComponentScan("spring.hometask.*")
@PropertySource("auditoriums.properties")
@EnableAspectJAutoProxy
public class JavaConfig {
 
 @Bean
 public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
     return new PropertySourcesPlaceholderConfigurer();
 }
}
