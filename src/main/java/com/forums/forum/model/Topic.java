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
@Entity(name = "topics")
@Table(name = "topics")
public class Topic {
    @Id
    @SequenceGenerator(
            name = "topic_sequence",
            sequenceName = "topic_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "topic_sequence"
    )
    @Column(name = "topic_id")
    private long topicId;
    @Column(name = "topic_name")
    private String topicName;
    @Column(name = "created_time_stamp")
    private Timestamp createdTimeStamp;
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
    @OneToMany(mappedBy = "topic")
    private List<Post> posts;

    public Topic(String topicName, Timestamp createdTimeStamp, String thumbnailUrl) {
        this.topicName = topicName;
        this.createdTimeStamp = createdTimeStamp;
        this.thumbnailUrl = thumbnailUrl;
    }

}
