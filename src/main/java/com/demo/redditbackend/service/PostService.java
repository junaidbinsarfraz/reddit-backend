package com.demo.redditbackend.service;

import com.demo.redditbackend.dto.PostDto;
import com.demo.redditbackend.dto.PostRequest;
import com.demo.redditbackend.exception.PostNotFoundException;
import com.demo.redditbackend.exception.SubredditNotFoundException;
import com.demo.redditbackend.exception.UserNotFoundException;
import com.demo.redditbackend.mapper.PostMapper;
import com.demo.redditbackend.model.Post;
import com.demo.redditbackend.model.Subreddit;
import com.demo.redditbackend.repository.PostRepository;
import com.demo.redditbackend.repository.SubredditRepository;
import com.demo.redditbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final AuthService authService;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) throws SubredditNotFoundException, UserNotFoundException {
        Subreddit subreddit = this.subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() -> new SubredditNotFoundException("Subreddit " + postRequest.getSubredditName() + " not found"));
        User currentUser = this.authService.getCurrentUser().orElseThrow(() -> new UserNotFoundException("Login User"));
        com.demo.redditbackend.model.User user = this.userRepository.findByUsername(currentUser.getUsername()).orElseThrow(() -> new UsernameNotFoundException(currentUser.getUsername()));

        Post post = this.postMapper.map(postRequest, subreddit, user);
        this.postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public PostDto getById(Long id) throws PostNotFoundException {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
        return this.postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostDto> getAll() {
        return this.postRepository.findAll().stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> getBySubreddit(Long subredditId) throws SubredditNotFoundException {
        Subreddit subreddit = this.subredditRepository.findById(subredditId).orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = this.postRepository.findAllBySubreddit(subreddit);

        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> getByUsername(String username) throws UserNotFoundException {
        com.demo.redditbackend.model.User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        List<Post> posts = this.postRepository.findAllByUser(user);

        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }
}
