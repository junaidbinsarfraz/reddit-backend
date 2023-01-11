package com.demo.redditbackend.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;
    @NotBlank(message = "Post name cannot be empty or null.")
    private String postName;
    @Nullable
    private String url;
    @Lob
    private String description;
    private Integer voteCount;
    private Instant createdDate;
    @ManyToOne
    private User user;
    @ManyToOne
    private Subreddit subreddit;
}
