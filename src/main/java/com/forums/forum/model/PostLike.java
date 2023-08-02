package com.forums.forum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "post_like")
@Table(name = "post_like",uniqueConstraints = @UniqueConstraint(columnNames = { "post_id", "user_id"}))
public class PostLike {
    @Id
    @SequenceGenerator(
            name = "post_like_sequence",
            sequenceName = "post_like_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_like_sequence"
    )
    @Column(name = "post_like_id")
    private Long postLikeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name="like_time")
    private Timestamp likeTime;
    public PostLike(User user, Post post, Timestamp likeTime) {
        this.user = user;
        this.post = post;
        this.likeTime = likeTime;
    }
}
