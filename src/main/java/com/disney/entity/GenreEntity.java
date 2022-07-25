package com.disney.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "genre")
@Getter
@Setter
@SQLDelete(sql="UPDATE genre SET soft_delete = true WHERE id=?")
@Where(clause = "soft_delete=false")
public class GenreEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(name = "soft_delete", nullable = false)
    private Boolean softDelete = false;

    public boolean isEnabled() { return !softDelete; }

}
