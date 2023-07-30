package com.forums.forum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name= "posts")
@Table(name = "posts")
public class Post {
    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )
    @Column(name = "post_id")
    private long postId;
    @Column(name = "title")
    private String title;
    @Column(name = "text")
    private String text;
    @Column(name = "created_time_stamp")
    private Timestamp createdTimeStamp;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user; // the user who posted the post
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic; // the topic

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
    @OneToMany(mappedBy = "post")
    private List<PostLike> likes;

    public Post(String title, String text, Timestamp createdTimeStamp, User user, Topic topic) {
        this.title = title;
        this.text = text;
        this.createdTimeStamp = createdTimeStamp;
        this.user = user;
        this.topic = topic;
    }
}
