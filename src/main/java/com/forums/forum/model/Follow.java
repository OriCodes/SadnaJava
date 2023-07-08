package com.forums.forum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "follows")
@Table(name = "follows",uniqueConstraints = @UniqueConstraint(columnNames = { "follower_id", "followed_id"}))
public class Follow {
    @Id
    @SequenceGenerator(
            name = "follow_sequence",
            sequenceName = "follow_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "follow_sequence"
    )
    @Column(name = "follow_id")
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followed_id")
    private User followed;
    @Column(name="follow_date")
    private LocalDate followDte;

    public Follow(User follower, User followed, LocalDate followDte) {
        this.follower = follower;
        this.followed = followed;
        this.followDte = followDte;
    }
}
