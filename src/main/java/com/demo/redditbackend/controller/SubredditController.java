package com.demo.redditbackend.controller;

import com.demo.redditbackend.dto.SubredditDto;
import com.demo.redditbackend.model.Subreddit;
import com.demo.redditbackend.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping("/create")
    public ResponseEntity createSubreddit(@RequestBody SubredditDto subredditDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getSubreddits() {
        return ResponseEntity.status(HttpStatus.OK).body(this.subredditService.getAll());
    }
}
