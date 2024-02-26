package org.example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class HelpServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(HelpServiceApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(HelpServiceApplication.class, args);
        logger.info("Start running");
    }
}
