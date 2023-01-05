package com.demo.redditbackend.mapper;

import com.demo.redditbackend.dto.SubredditDto;
import com.demo.redditbackend.model.Post;
import com.demo.redditbackend.model.Subreddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer getPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }
}
