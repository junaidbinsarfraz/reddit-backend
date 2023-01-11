package com.demo.redditbackend.repository;

import com.demo.redditbackend.model.Post;
import com.demo.redditbackend.model.Subreddit;
import com.demo.redditbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findAllByUser(User user);
}
