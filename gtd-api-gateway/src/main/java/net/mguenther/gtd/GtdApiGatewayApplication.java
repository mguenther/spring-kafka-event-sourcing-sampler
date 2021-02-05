package net.mguenther.gtd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class GtdApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GtdApiGatewayApplication.class, args);
    }
}
