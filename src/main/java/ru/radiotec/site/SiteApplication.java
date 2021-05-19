package ru.radiotec.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class SiteApplication extends SpringBootServletInitializer {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.of(100, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(100, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

}
