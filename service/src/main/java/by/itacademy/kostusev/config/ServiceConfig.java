package by.itacademy.kostusev.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DatabaseConfig.class)
@ComponentScan({"by.itacademy.kostusev.service", "by.itacademy.kostusev.mapper"})
public class ServiceConfig {
}
