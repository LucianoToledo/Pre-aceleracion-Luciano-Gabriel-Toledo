package com.disney.dto.response;

import com.disney.entity.MovieEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CharacterResponse {
    private String id;
    private String name;
    private Integer age;
    private Float weight;
    private String history;
    private String image;
    private List<MovieEntity> movies;
}
