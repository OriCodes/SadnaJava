package com.forums.forum.dto;

import com.forums.forum.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserWithStats extends User {
    int followerCount;


}
