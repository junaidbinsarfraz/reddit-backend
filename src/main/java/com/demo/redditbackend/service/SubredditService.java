package com.demo.redditbackend.service;

import com.demo.redditbackend.dto.SubredditDto;
import com.demo.redditbackend.model.Subreddit;
import com.demo.redditbackend.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SubredditService {
    private final SubredditRepository subredditRepository;

    @Transactional
    public Subreddit save(SubredditDto subredditDto) {
        Subreddit subreddit = new Subreddit();

        subreddit.setName(subredditDto.getName());
        subreddit.setDescription(subredditDto.getDescription());
        subreddit.setCreatedDate(Instant.now());

        return this.subredditRepository.save(subreddit);
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return this.subredditRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
        SubredditDto subredditDto = new SubredditDto();
        subredditDto.setDescription(subreddit.getDescription());
        subredditDto.setName(subreddit.getName());
        subredditDto.setId(subreddit.getId());
        subredditDto.setNumberOfPosts(subreddit.getPosts().size());

        return subredditDto;
    }

}
