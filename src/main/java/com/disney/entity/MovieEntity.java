package com.disney.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MOVIE")
@Getter
@Setter
public class MovieEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(name = "creation_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate creationDate = LocalDate.now();

    @Column(nullable = false)
    private Integer ranking;

    private String image;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "movie_character",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id"))
    private Set<CharacterEntity> characters = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id", insertable = false, updatable = false)
    private GenreEntity genre;

    @Column(name = "genre_id")
    private String genreId;
    @Column(name = "soft_delete", nullable = false)
    private Boolean softDelete = false;

    public boolean isEnabled() {
        return !softDelete;
    }
}
