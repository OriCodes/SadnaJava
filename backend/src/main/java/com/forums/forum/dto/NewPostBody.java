package com.forums.forum.dto;

import lombok.Data;

@Data
public class NewPostBody {
    private Long topicId;
    private String title;
    private String text;
}
