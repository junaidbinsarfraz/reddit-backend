package com.demo.redditbackend.service;

import com.demo.redditbackend.dto.SubredditDto;
import com.demo.redditbackend.mapper.SubredditMapper;
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
    private final SubredditMapper subredditMapper;

    @Transactional
    public Subreddit save(SubredditDto subredditDto) {
        return this.subredditRepository.save(this.subredditMapper.mapDtoToSubreddit(subredditDto));
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return this.subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubredditDto getById(Long id) {
        return this.subredditRepository.findById(id).map(subredditMapper::mapSubredditToDto).orElse(new SubredditDto());
    }
}
