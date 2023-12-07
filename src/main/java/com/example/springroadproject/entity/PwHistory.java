package com.example.springroadproject.entity;

import com.example.springroadproject.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class PwHistory extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PwHistory(User user,String newPassword) {
        this.password = newPassword;
        this.user = user;
    }
}
