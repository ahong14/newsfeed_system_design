package com.newsfeed_system_design.post_service.repositories;

import com.newsfeed_system_design.post_service.models.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    Post findByPostUserIdAndId(UUID userId, UUID postId);

    @Transactional
    void deleteByPostUserIdAndId(UUID userId, UUID postId);

}
