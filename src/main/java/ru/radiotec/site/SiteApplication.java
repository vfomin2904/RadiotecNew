package ru.radiotec.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class SiteApplication {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setMaxFileSize("128KB");
//        factory.setMaxRequestSize("128KB");
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

}
