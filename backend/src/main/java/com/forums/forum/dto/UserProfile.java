package com.forums.forum.dto;

import com.forums.forum.model.Post;
import lombok.Data;

import java.util.List;

@Data
public class UserProfile {
    private UserWithStats user;
    private List<Post> pagePosts;
    private int page;
}
