package com.disney.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "GENRE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(name = "movie_id", nullable = false)
    private String movieId;

    @Column(name = "soft_delete", nullable = false)
    private Boolean softDelete = false;

    public boolean isEnabled() { return !softDelete; }

}
