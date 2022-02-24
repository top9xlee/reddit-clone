package com.Website.Step2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import com.Website.Step2.config.SwaggerConfiguration;

@EnableAsync
@SpringBootApplication
@Import(SwaggerConfiguration.class)
public class Step2Application {

    public static void main(String[] args) {
        SpringApplication.run(Step2Application.class, args);
    }

}
