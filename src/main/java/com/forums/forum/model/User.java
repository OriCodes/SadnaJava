package com.forums.forum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


// Changed entity and table name to "users" instead of "user" because of a bug.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(name = "user_id")
    private long userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "dob")
    private LocalDate dob;
    @Column(name = "profile_url")
    private String profileUrl;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "auth0_id")
    private String auth0Id;

    //Foreign key constrains
    @OneToMany(mappedBy = "user")
    private List<Post> posts;
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;
    @OneToMany(mappedBy = "receiver")
    private List<Message> receivedMessages;
    @OneToMany(mappedBy = "follower")
    private List<Follow> following;
    @OneToMany(mappedBy = "followed")
    private List<Follow> followers;
    @OneToMany(mappedBy = "user")
    private List<CommentLike> commentLikes;
    @OneToMany(mappedBy = "user")
    private List<PostLike> postLikes;


    public User(String userName, LocalDate dob, String profileUrl, Gender gender, String auth0Id) {
        this.userName = userName;
        this.dob = dob;
        this.profileUrl = profileUrl;
        this.gender = gender;
        this.auth0Id = auth0Id;
    }
}
