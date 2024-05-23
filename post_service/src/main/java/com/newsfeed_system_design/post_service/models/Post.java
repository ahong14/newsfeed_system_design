package com.newsfeed_system_design.post_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(min = 2)
    @Column(nullable = false)
    private String content;

    @Column(name="post_user_id", nullable = false)
    private UUID postUserId;

    @Past
    @Column(name="created_date", nullable = false)
    private Date createdDate;

    @Past
    @Column(name = "updated_date", nullable = false)
    private Date updatedDate;

    public Post(UUID id, String content, UUID postUserId, Date createdDate, Date updatedDate) {
        this.id = id;
        this.content = content;
        this.postUserId = postUserId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Post() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(UUID postUserId) {
        this.postUserId = postUserId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", postUserId=" + postUserId +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
