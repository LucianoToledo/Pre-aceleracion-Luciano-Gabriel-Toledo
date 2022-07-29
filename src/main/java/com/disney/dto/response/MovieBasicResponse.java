package com.disney.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieBasicResponse {
    private String tittle;
    private String creationDate;
    private String image;
    private List<CharacterBasicResponse> characterResponsesList;
    private GenreResponse genreResponse;
}
