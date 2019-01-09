//test_feature
package com.plmakas.movies;

import com.plmakas.movies.mock.MoviesWireMockServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@EnableWebSecurity
public class MoviesRESTFacadeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviesRESTFacadeApplication.class, args);

        MoviesWireMockServer.runWireMock();
    }
}
