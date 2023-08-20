package com.forums.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(name = "password")
    private String password;



    public User(String userName, LocalDate dob, String profileUrl, Gender gender, String password) {
        this.userName = userName;
        this.dob = dob;
        this.profileUrl = profileUrl;
        this.gender = gender;
        this.password = password;
    }
}
