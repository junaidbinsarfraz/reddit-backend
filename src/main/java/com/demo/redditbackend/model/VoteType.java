package com.demo.redditbackend.model;

import java.util.Arrays;
import java.util.Optional;

public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1),
    ;

    private int direction;

    VoteType(int direction) {
    }

    public static VoteType lookup(Integer direction) throws Exception {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new Exception("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}
