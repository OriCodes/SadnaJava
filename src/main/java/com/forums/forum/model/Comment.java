package com.forums.forum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comments")
@Table(name = "comments")
public class Comment
{
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
    private Timestamp createdTimeStamp;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // the user who wrote the comment
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post; // the post which the comment belong to

    public Comment(String text, Timestamp createdTimeStamp, User user, Post post) {
        this.text = text;
        this.createdTimeStamp = createdTimeStamp;
        this.user = user;
        this.post = post;
    }

    // Getters and setters
    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Timestamp createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", text='" + text + '\'' +
                ", createdTimeStamp=" + createdTimeStamp +
                ", user=" + user +
                ", post=" + post +
                '}';
    }
}
