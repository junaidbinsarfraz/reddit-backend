package com.demo.redditbackend.mapper;

import com.demo.redditbackend.dto.PostDto;
import com.demo.redditbackend.dto.PostRequest;
import com.demo.redditbackend.model.Post;
import com.demo.redditbackend.model.Subreddit;
import com.demo.redditbackend.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostDto mapToDto(Post post);
}
