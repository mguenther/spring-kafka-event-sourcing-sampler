package net.mguenther.gtd;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class GtdApiGatewayApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(GtdApiGatewayApplication.class).web(WebApplicationType.SERVLET).run(args);
    }

    @Bean
    public OnMethodFilter onMethodZuulFilter() {
        return new OnMethodFilter();
    }
}
