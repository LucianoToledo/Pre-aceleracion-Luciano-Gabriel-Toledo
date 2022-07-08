package com.disney.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "movie_id")
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "timeStamp", nullable = false)
    private LocalDateTime timeStamp;

    @Column(name = "qualification", nullable = false)
    private String qualification;

    @Column(name = "image")
    private String image;

    @OneToMany
    @JoinColumn(name = "movie_id")
    private List<Character> characters;

    @OneToMany
    @JoinColumn(name = "movie_id")
    private List<Genre> genres;

    @Column(name = "soft_delete", nullable = false)
    private Boolean softDelete = false;

    public boolean isEnabled() { return !softDelete; }


}
