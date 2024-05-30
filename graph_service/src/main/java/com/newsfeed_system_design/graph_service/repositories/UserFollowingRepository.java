package com.newsfeed_system_design.graph_service.repositories;

import com.newsfeed_system_design.graph_service.models.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserFollowingRepository extends Neo4jRepository<User, UUID> {
}
