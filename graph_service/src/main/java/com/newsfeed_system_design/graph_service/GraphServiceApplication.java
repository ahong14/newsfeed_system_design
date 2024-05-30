package com.newsfeed_system_design.graph_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories(basePackages = {"com.newsfeed_system_design.graph_service.repositories"})
public class GraphServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphServiceApplication.class, args);
	}

}
