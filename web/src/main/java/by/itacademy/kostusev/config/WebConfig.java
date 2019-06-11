package by.itacademy.kostusev.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ServiceConfig.class)
@ComponentScan({"by.itacademy.kostusev.servlet"})
public class WebConfig {
}
