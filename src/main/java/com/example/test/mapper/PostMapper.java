package com.example.test.mapper;

import com.example.test.model.dto.Post;
import com.example.test.model.entity.Posts;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public Post toPost(Posts posts) {
        return Post.builder()
                .postId(posts.getPostId())
                .dateCreated(posts.getDateCreated())
                .userId(posts.getUserId())
                .title(posts.getTitle())
                .body(posts.getBody())
                .build();
    }
}
