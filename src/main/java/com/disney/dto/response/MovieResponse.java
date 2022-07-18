package com.disney.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieResponse {
    private String id;
    private String tittle;
    private Integer ranking;
    private String image;
    private List<CharacterResponse> characterResponse;
}
