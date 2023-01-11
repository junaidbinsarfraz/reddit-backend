package com.demo.redditbackend.controller;

import com.demo.redditbackend.dto.PostDto;
import com.demo.redditbackend.dto.PostRequest;
import com.demo.redditbackend.exception.PostNotFoundException;
import com.demo.redditbackend.exception.SubredditNotFoundException;
import com.demo.redditbackend.exception.UserNotFoundException;
import com.demo.redditbackend.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody PostRequest postRequest) throws UserNotFoundException, SubredditNotFoundException {
        this.postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAll() {
        return status(HttpStatus.OK).body(this.postService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable Long id) throws PostNotFoundException {
        return status(HttpStatus.OK).body(this.postService.getById(id));
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<List<PostDto>> getBySubredditId(@PathVariable Long id) throws SubredditNotFoundException {
        return status(HttpStatus.OK).body(this.postService.getBySubreddit(id));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<PostDto>> getByUserId(@PathVariable String username) throws UserNotFoundException {
        return status(HttpStatus.OK).body(this.postService.getByUsername(username));
    }

}
