package com.forums.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import java.sql.Timestamp;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "comments")
@Table(name = "comments")
public class Comment {
    @Id
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_sequence"
    )
    @Column(name = "comment_id")
    private long commentId;
    @Column(name = "text")
    private String text;
    @Column(name = "created_time_stamp")
    @CreationTimestamp
    private Timestamp createdTimeStamp;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // the user who wrote the comment
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post; // the post which the comment belong to

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("comment")
    private List<CommentLike> likes = List.of();

    @Formula("(SELECT COUNT(cl.comment_like_id) FROM comment_like cl WHERE cl.comment_id = comment_id)")
    private int likesCount;

    public Comment(String text, User user, Post post) {
        this.text = text;
        this.user = user;
        this.post = post;
    }


}
