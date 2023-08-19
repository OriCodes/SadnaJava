package com.forums.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "posts")
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
    @CreationTimestamp
    private Timestamp createdTimeStamp;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // the user who posted the post
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic; // the topic

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("post")
    private List<PostLike> likes;

    @Formula("(SELECT COUNT(pl.post_like_id) FROM post_like pl WHERE pl.post_id = post_id)")
    private int likesCount;

    public Post(String title, String text, User user, Topic topic) {
        this.title = title;
        this.text = text;
        this.user = user;
        this.topic = topic;
        this.comments = Collections.emptyList();
        this.likes = Collections.emptyList();
    }
}
