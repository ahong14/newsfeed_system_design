package src.main.java.com.newsfeed_system_design.post_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    private UUID id;

    @Column
    private String description;

    @Column
    private UUID posterId;
}
