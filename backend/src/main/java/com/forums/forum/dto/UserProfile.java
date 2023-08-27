package com.forums.forum.dto;

import com.forums.forum.model.Post;
import com.forums.forum.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserProfile {
    private User user;
    private List<Post> pagePosts;
    private int page;
}
