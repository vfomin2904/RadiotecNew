package ru.radiotec.site.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Settings {
    public static final String uploadPath = "C:/Users/vova-/IdeaProjects/site/uploads/";

    @Bean(name="uploadPath")
    public String getUploadPath(){
        return uploadPath;
    }
}
