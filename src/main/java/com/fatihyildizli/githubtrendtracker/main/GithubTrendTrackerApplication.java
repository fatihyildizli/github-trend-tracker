package com.fatihyildizli.githubtrendtracker.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.fatihyildizli.*"})
public class GithubTrendTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GithubTrendTrackerApplication.class, args);
    }

}
