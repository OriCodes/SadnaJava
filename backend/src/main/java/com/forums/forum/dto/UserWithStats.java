package com.forums.forum.dto;

import com.forums.forum.model.Gender;
import lombok.Data;

import java.time.LocalDate;


@Data
public class UserWithStats{

    private  Long userId;
    private String username;
    private LocalDate dob;
    private String profileUrl;
    private Gender gender;
    private int followerCount;
    private int followedCount;
}
