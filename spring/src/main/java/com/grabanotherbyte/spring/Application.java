package com.grabanotherbyte.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.grabanotherbyte.spring.config.ReportConfig;
import com.grabanotherbyte.spring.config.ReportConfigValidator;

@SpringBootApplication
@EnableConfigurationProperties(ReportConfig.class)
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public static ReportConfigValidator configurationPropertiesValidator() {
    return new ReportConfigValidator();
  }
}
