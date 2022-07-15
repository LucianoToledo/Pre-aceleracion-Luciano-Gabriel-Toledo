package com.disney.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "characters")
@Getter
@Setter
public class CharacterEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "history", nullable = false)
    private String history;

    @Column(name = "image")
    private String image;

    @ManyToMany(mappedBy = "characters",cascade = CascadeType.ALL)
    private List<MovieEntity> movies = new ArrayList<>();

    @Column(name = "soft_delete", nullable = false)
    private Boolean softDelete = false;

    public boolean isEnabled() { return !softDelete; }
}
