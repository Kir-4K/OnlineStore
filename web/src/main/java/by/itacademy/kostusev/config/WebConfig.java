package by.itacademy.kostusev.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({"by.itacademy.kostusev.controller", "by.itacademy.kostusev.saving", "by.itacademy.kostusev.util"})
@Import(value = {InternationalizationConfig.class, ThymeleafConfig.class})
public class WebConfig implements WebMvcConfigurer {

    private static final String[] PATH_PATTERNS = {
            "/img/**",
            "/css/**"
    };

    private static final String[] CLASSPATH = {
            "classpath:/static/img/",
            "classpath:/static/css/"
    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PATH_PATTERNS)
                .addResourceLocations(CLASSPATH);
    }
}
