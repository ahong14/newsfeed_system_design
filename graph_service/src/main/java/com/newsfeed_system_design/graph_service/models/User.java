//package com.newsfeed_system_design.graph_service.models;
//
//import org.springframework.data.neo4j.core.schema.GeneratedValue;
//import org.springframework.data.neo4j.core.schema.Id;
//import org.springframework.data.neo4j.core.schema.Node;
//import org.springframework.data.neo4j.core.schema.Relationship;
//
//import java.util.Set;
//import java.util.UUID;
//
//
//// reference: https://medium.com/javarevisited/microservices-in-practice-developing-instagram-clone-graph-service-193364c062df
//@Node("User")
//public class User {
//    @Id
//    @GeneratedValue
//    UUID id;
//
//    private String email;
//    private String firstName;
//    private String lastName;
//
//
//    @Relationship(type = "IS_FOLLOWING")
//    private Set<Following> followings;
//}
