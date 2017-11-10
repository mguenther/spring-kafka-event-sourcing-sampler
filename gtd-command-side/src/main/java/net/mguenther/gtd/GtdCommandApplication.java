package net.mguenther.gtd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableWebMvc
public class GtdCommandApplication {

    public static void main(String[] args) {
        SpringApplication.run(GtdCommandApplication.class, args);
    }
}
