package com.forums.forum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
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

}
