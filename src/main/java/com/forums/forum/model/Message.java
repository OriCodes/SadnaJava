package com.forums.forum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "messages")
@Table(name = "messages")
public class Message {
    @Id
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "message_sequence"
    )
    @Column(name = "message_id")
    private long messageId;
    @Column(name = "content")
    private String content;
    @Column(name = "created_time_stamp")
    private Timestamp createdTimeStamp;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender; // the user who posted the post
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver; // the user who posted the post

    public Message(String content, Timestamp createdTimeStamp, User sender, User receiver) {
        this.content = content;
        this.createdTimeStamp = createdTimeStamp;
        this.sender = sender;
        this.receiver = receiver;
    }

}
